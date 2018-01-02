package org.springboot.esbcollecthelper.service.xmlMsgParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bjx.helper.date.DateHelper;
import org.dom4j.DocumentException;
import org.springboot.esbcollecthelper.dao.def.EsbMsgCountDao;
import org.springboot.esbcollecthelper.service.cache.MsgInfoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyInformationPicker {
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	XmlMsgParser xmlMsgParser;
	@Autowired
	EsbMsgCountDao esbMsgCountDao;
	
	DateHelper DateHelper = new DateHelper();
	
	public List<Map<String,Object>> pickUp(String protocol,String startTime,String endTime) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String,Object> resultMap;
		if(protocol != null) {
			protocol = protocol.trim();
		}
		if(startTime == null || startTime.equals("")) {
			startTime = DateHelper.getDayString(-1);
		}
		if(endTime == null || endTime.equals("")) {
			endTime = DateHelper.getDayString(0);
		}
		for(Map<String,Object> map:esbMsgCountDao.msgDetail(protocol, startTime, endTime)){
			resultMap = new HashMap<String,Object>();
			resultMap.put("TIMES",map.get("RECEIVE_TIME_STAMP"));
			String msg  = (String) map.get("MSG_CONTENT");			
			try {
				String ftpUrl = xmlMsgParser.GetFtpInfo(msg);
		        MsgInfoCache.ftpUrlMap.put(xmlMsgParser.getMyFileName(), ftpUrl);
		        MsgInfoCache.MsgMap.put(xmlMsgParser.getMyFileName(), msg);
			} catch (DocumentException e) {
				logger.error(e.getMessage());
			}
			resultMap.put("DOWNLOAD","&nbsp;&nbsp;&nbsp;<button type=\"button\" class=\"navbar-expand-toggle\" onclick=\"download('"+xmlMsgParser.getMyFileName()+"')\"><i class=\"icon glyphicon glyphicon-download-alt fa-1x\"></i></button>");		
			resultMap.put("SENDMSG","&nbsp;&nbsp;&nbsp;<button type=\"button\" class=\"navbar-expand-toggle\" onclick=\"sendMsg('"+xmlMsgParser.getMyFileName()+"')\"><i class=\"icon glyphicon glyphicon-envelope fa-1x\"></i></button>");		
//			resultMap.put("DOWNLOAD","<i class=\"icon glyphicon glyphicon-download-alt fa-1x\" onclick=\"download('"+xmlMsgParser.getMyFileName()+"')\"></i>");		
			

			resultMap.put("FILENAME", xmlMsgParser.getMyFileName());
			resultMap.put("CHARSET", xmlMsgParser.getCharset());
			resultList.add(resultMap);
		}
		return resultList;
	}
}
