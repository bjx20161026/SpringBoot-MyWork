package org.springboot.workorder.service.orderHandler;

import java.util.List;
import java.util.Map;

import org.springboot.workorder.dao.res.WorkOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ApImportHandler extends OrderHandler {
	
	@Value("${sheet.ApImport}")
	String sheetName;
	@Autowired
	WorkOrderDao WorkOrderDao;
	
	@Override
	public void handle() throws Exception {
		// TODO Auto-generated method stub
		basicParser.setPath((String)orderMap.get("localfile"));
		basicParser.setType("ApImport");
		basicParser.setSheetName(sheetName);
		basicParser.init();
		List<Map<String, String>> list = basicParser.parse();
		if (!basicParser.getValidMsg().equals("")) {
			WorkOrderDao.updateFailById("校验失败", basicParser.getValidMsg(), (String)orderMap.get("order_user"), (String)orderMap.get("workjob_id"));
		}
	}
}
