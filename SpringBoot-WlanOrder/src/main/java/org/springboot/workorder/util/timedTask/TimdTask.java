package org.springboot.workorder.util.timedTask;

import java.lang.reflect.Method;

public class TimdTask extends Thread{
	public String many;
	public String callmethod;

	public String getMany() {
		return many;
	}

	public void setMany(String many) {
		this.many = many;
	}

	public String getCallmethod() {
		return callmethod;
	}

	public void setCallmethod(String callmethod) {
		this.callmethod = callmethod;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	System.out.println(many);	
		 try {
			Method method = Class.forName(many).getDeclaredMethod(callmethod);
			method.invoke(Class.forName(many).newInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
}
