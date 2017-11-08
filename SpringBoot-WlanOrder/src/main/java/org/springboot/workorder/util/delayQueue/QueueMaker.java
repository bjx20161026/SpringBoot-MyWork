package org.springboot.workorder.util.delayQueue;

import java.util.concurrent.DelayQueue;


public class QueueMaker {
	
	public DelayQueue<DelayTask> queue;
	
	public DelayQueue<DelayTask> getQueue() {
		return queue;
	}

	public void setQueue(DelayQueue<DelayTask> queue) {
		this.queue = queue;
	}

	public void init() {
		
	}

}
