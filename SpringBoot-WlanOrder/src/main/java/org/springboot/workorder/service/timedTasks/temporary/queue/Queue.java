package org.springboot.workorder.service.timedTasks.temporary.queue;

import java.util.concurrent.DelayQueue;

import org.springboot.workorder.util.delayQueue.DelayTask;


public class Queue {
	
	public static DelayQueue<DelayTask> queue = new DelayQueue<DelayTask>();

}
