package org.springboot.workorder.service.timedTasks.temporary.queue;

import org.springboot.workorder.util.delayQueue.QueueMaker;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemporaryQueueMaker extends QueueMaker implements InitializingBean{
	
	@Autowired
	TemporaryQueueHandler queueHandler;
	
	@Override
	public void init(){
		queueHandler.setQueue(Queue.queue);
		new Thread(queueHandler).start();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		init();
	}

}
