package org.springboot.workorder.service.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springboot.workorder.util.common.FileTools;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
/**
 * Parse the Excel template configuration file into the cache
 * @author lenovo
 *
 */
@Service
public class TemplateProperties implements InitializingBean{
	Logger logger = Logger.getLogger(getClass());
	
	public static Map<String,Map<String,String>> TemplatePropertiesMap = new HashMap<String, Map<String,String>>();
	
	public void init() {
		logger.info("Template configuration loading ... ...");
		parseProperties("BasicInformation","excelTemplate/Basic_Information.properties");
		logger.info("Template configuration load succeed!");
	}; 
	
	public void parseProperties(String key,String path) {
		HashMap<String, String> map = new HashMap<String,String>();
		Properties pro = FileTools.getProperties(path);
		String[] heads = pro.getProperty("heads").split(",");
		map.put("heads", pro.getProperty("heads"));
		map.put("startline", pro.getProperty("startline","0"));
		map.put("headline", pro.getProperty("headline","-1"));
		for(String head:heads) {
			map.put(head, pro.getProperty(head));
		}
		TemplatePropertiesMap.put(key, map);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		init();
	}

}
