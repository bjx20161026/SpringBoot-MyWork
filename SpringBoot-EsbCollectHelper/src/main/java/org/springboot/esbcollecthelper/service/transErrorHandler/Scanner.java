package org.springboot.esbcollecthelper.service.transErrorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springboot.esbcollecthelper.dao.def.EsbMsgCountDao;
import org.springboot.esbcollecthelper.service.cache.TransErrorListCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author b j x
 * @Description scan record table to find error information
 * 
 */
@Service
public class Scanner {
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	EsbMsgCountDao esbMsgCountDao;
	@Autowired
	ValueTooLargeHandler valueTooLargeHandler;
	
	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	
	@Scheduled(cron = "0 1 * * * ?")
	public void scan() {
		logger.info("scan for errorReason start!");
		long start = System.currentTimeMillis();
		try {
		list = esbMsgCountDao.errorReason();
		handle();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		logger.info("-------------------------------");
		logger.info("scan for errorReanson finished!");
		logger.info("cost : "+(System.currentTimeMillis()-start)+" ms");
		logger.info("find : "+list.size());
		logger.info("-------------------------------");
	}
	
	public void handle() {
		for(Map<String, Object> map:list) {
			String errorReason = (String)map.get("ERROR_REASON");
			if(errorReason.contains("invalid number")) {
				
				continue;
			}
			if(errorReason.contains("value too large")) {
				valueTooLargeHandler.handle(map);
				continue;
			}
//			if(errorReason.contains("")) {
//				
//				continue;
//			}
			TransErrorListCache.oeList.add(map);
		}
	}
	
}
