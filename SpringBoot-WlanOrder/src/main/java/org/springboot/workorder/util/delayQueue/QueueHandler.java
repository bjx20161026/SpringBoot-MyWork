package org.springboot.workorder.util.delayQueue;

import java.util.concurrent.DelayQueue;

public class QueueHandler implements Runnable{
	
	public DelayQueue<DelayTask> queue;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public DelayQueue<DelayTask> getQueue() {
		return queue;
	}

	public void setQueue(DelayQueue<DelayTask> queue) {
		this.queue = queue;
	}


}
