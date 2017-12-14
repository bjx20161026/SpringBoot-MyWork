package org.springboot.esbcollecthelper.service.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springboot.esbcollecthelper.dao.def.EsbMsgCountDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SqlResultCache implements InitializingBean{
	Logger logger = Logger.getLogger(getClass());
	public static Map<String,Object> map = new HashMap<String,Object>();
	@Autowired
	EsbMsgCountDao esbMsgCountDao;
	
	public void init() {
		logger.info("SqlResultCache Init Start!");
		long start = System.currentTimeMillis();
		map.put("EFFECTIVEMSG", esbMsgCountDao.countEffectiveMsg());
		map.put("TRANSERROR", esbMsgCountDao.transError());
		logger.info("SqlResultCache Init Finished Cost ["+(System.currentTimeMillis()-start)+"] ms");
	}
	
	@Scheduled(cron = "0 1 0 * * ?")
	public void refreshByDay() {
		logger.info("RefreshByDay Start!");
		long start = System.currentTimeMillis();
		map.put("EFFECTIVEMSG", esbMsgCountDao.countEffectiveMsg());
		map.put("TRANSERROR", esbMsgCountDao.transError());
		logger.info("-------------------------------");
		logger.info("RefreshByDay finished!");
		logger.info("cost : "+(System.currentTimeMillis()-start)+" ms");
		logger.info("-------------------------------");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		init();
	}

}
