package org.springboot.workorder.service.task.temporary.timed;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springboot.workorder.dao.res.TemporaryBakDao;
import org.springboot.workorder.service.task.temporary.queue.Queue;
import org.springboot.workorder.util.delayQueue.DelayTask;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemporaryBak implements InitializingBean{
	
	@Autowired
	TemporaryBakDao temporaryBakDao;
	@Autowired
	DelayTask delayTask;
	
	public void init() {
		List<Map<String,Object>> list = temporaryBakDao.findAll();
		for(Map map:list) {
			delayTask.setExecuteTime(((Date)map.get("STARTDATE")).getTime());
			delayTask.setMap(map);
			Queue.queue.offer(delayTask);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		init();
	}

}
