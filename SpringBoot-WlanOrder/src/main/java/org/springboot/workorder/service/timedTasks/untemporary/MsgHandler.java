package org.springboot.workorder.service.timedTasks.untemporary;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springboot.workorder.dao.res.FileOrderDao;
import org.springboot.workorder.dao.res.WorkOrderDao;
import org.springboot.workorder.service.templateParser.BasicParser;
import org.springboot.workorder.util.common.FileTools;
import org.springboot.workorder.util.excel.ExcelReader;
import org.springboot.workorder.util.ftp.FileOperate;
import org.springboot.workorder.util.sender.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author bjx
 * @Description scan esb message receive table to find unhandled message record
 *              parse message content and download file by ftp parse excel file
 *              and data validity
 */

@Service
public class MsgHandler {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	FileOrderDao fileOrderDao;
	@Autowired
	WorkOrderDao workOrderDao;
	@Autowired
	BasicParser basicParser;
	@Autowired
	ExcelReader excelReader;
	@Autowired
	Receipt receipt;
	@Value("${sheet.basicInformation}")
	String sheetName;
	List<Map<String, String>> list;
	String typePath = "excelTemplate/SheetNameToOrderType.properties";

	@Scheduled(fixedRate = 60000)
	public void scan() {
		logger.info("Scan recieve table to find unhandle msg start ...");
		List<Map<String, Object>> list = fileOrderDao.findUnhandled();
		for (Map<String, Object> map : list) {
			handle(map);
		}
	}

	public void handle(Map<String, Object> map) {
		try {
			Document dom = DocumentHelper.parseText((String) map.get("XMLTEXT"));
			Element root = dom.getRootElement();
			Element ftpInfo = root.element("ftpInfo");
			String connectionString = ftpInfo.element("ConnectionString").getText().replace("ftp://", "");
			String path = ftpInfo.element("Path").getText();
			String userName = ftpInfo.element("userName").getText();
			String passWord = ftpInfo.element("password").getText();
			String url = "ftp://" + userName + ":" + passWord + "@" + connectionString + path;
			Element files = ftpInfo.element("files");
			List<Element> filess = files.elements();
			for (Element file : filess) {
				String name = file.element("fileName").getText();
				String ftpUrl = url + "/" + name;
				logger.info("Down load file by ftp,ftp url is:" + ftpUrl);
				FileOperate fo = new FileOperate(ftpUrl);
				basicParser.setPath(fo.getDownloadFile());
				basicParser.setType("BasicInformation");
				basicParser.setSheetName(sheetName);
				basicParser.init();
				list = basicParser.parse();
				Map<String, String> resultMap = list.get(0);
				resultMap.put("workjob_id", name.substring(0, name.lastIndexOf("_")));
				if (!basicParser.getValidMsg().equals("")) {
					receipt.SendReceipt(resultMap.get("workjob_id"), "失败", basicParser.getValidMsg());
					workOrderDao.insertFailRecord(basicParser.getValidMsg(), resultMap.get("workjob_id"));
					logger.error("Excel Parse Error :[" + basicParser.getValidMsg() + "] for sheet BasicInformation!");
					continue;
				}
				String tarSheetName = excelReader.readSheetName(fo.getDownloadFile(), 1);
				String orderType = FileTools.getProperties(typePath).getProperty(tarSheetName);
				if (orderType == null || orderType.equals("")) {
					receipt.SendReceipt(resultMap.get("workjob_id"), "失败",String.format("附件sheet[%s]名错误", tarSheetName));
					workOrderDao.insertFailRecord(String.format("附件sheet[%s]名错误", tarSheetName),resultMap.get("workjob_id"));
					logger.error(String.format("Excel Parse Error :[attachments' sheet name [%s]]!", tarSheetName));
					continue;
				}
				resultMap.put("type", orderType);
				resultMap.put("localFile", fo.getDownloadFile());
				resultMap.put("ftpUrl", ftpUrl);
				workOrderDao.insertRecord(resultMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
