package org.springboot.esbcollecthelper.service.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springboot.esbcollecthelper.dao.def.EsbMsgCountDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class SqlResultCache extends Thread implements InitializingBean{
	Logger logger = Logger.getLogger(getClass());
	public static Map<String,Object> map = new HashMap<String,Object>();
	@Autowired
	EsbMsgCountDao esbMsgCountDao;
	
	public void init() {		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("EFFECTIVEMSG");
		map.put("EFFECTIVEMSG", esbMsgCountDao.countEffectiveMsg());
		stopWatch.stop();
		stopWatch.start("TRANSERROR");
		map.put("TRANSERROR", esbMsgCountDao.transError());
		stopWatch.stop();
		stopWatch.start("MESSAGECOMPONENTS");
		map.put("MESSAGECOMPONENTS", esbMsgCountDao.messageComponents());
		stopWatch.stop();	
		logger.info(stopWatch.prettyPrint());
	}
	
	@Override
	public void run(){
		init();
	}
	
	@Scheduled(cron = "0 1 0 * * ?")
	public void refreshByDay() {
		logger.info("RefreshByDay Start!");
		init();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		start();
	}

}
