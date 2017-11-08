package org.springboot.workorder.util.delayQueue;

import java.util.Map;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DelayTask implements Delayed{
	
    private long executeTime;
    
    private Map<String,Object> map;

	@Override
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		DelayTask delayTask = (DelayTask)o;
		return this.executeTime > delayTask.executeTime ? 1 : (this.executeTime < delayTask.executeTime ? -1 : 0);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		// TODO Auto-generated method stub
	    return unit.convert(this.executeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}
	
	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(long executeTime) {
		this.executeTime = executeTime;
	}

}
