package org.springboot.workorder.util.timedTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

public class Shedual implements InitializingBean{
	
	Logger logger = Logger.getLogger(getClass());
	
	public static List<String> tasks = new ArrayList<String>();
	
	public static Map<String,Integer> transMap = new HashMap<String, Integer>();

	public static void main(String[] args) throws Exception{
		Shedual shedual = new Shedual();
		shedual.init();
	}
	
	public void  init(){
		transMap.put("minute", 60);
		transMap.put("hour", 60*60);
		transMap.put("day", 60*60*24);
		transMap.put("week", 60*60*24*7);
		transMap.put("month", 60*60*24*30);
		try {
			getTask();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String task:tasks){
			logger.info(task);
					}
		DealTasks();
	}
	
	public void DealTasks(){
		for(String task:tasks){
			String[] str = task.split("\\|");
			DealTask(str[0],str[1],str[2]);
		}
	}
	
	public void DealTask(String type,String taskTime,String classPath){
		try{
			String[] times = taskTime.split("\\.");
			Date time = getTaskCalendar(type,times).getTime(); 
			Date date = new Date();
			long initialDelay = (time.getTime()-date.getTime())/1000;
			if(initialDelay<0) initialDelay += transMap.get(type);
			String many = classPath.substring(0,classPath.lastIndexOf("."));
			String callmethod = classPath.substring(classPath.lastIndexOf(".")+1,classPath.length());
			logger.info("initialDelay--->>>"+initialDelay);
			logger.info("transMap.get(type)--->>>"+transMap.get(type));
			logger.info("many--->>>"+many);
			logger.info("callmethod--->>>"+callmethod);
			TimdTask timdTask=new TimdTask();
			timdTask.setMany(many);
			timdTask.setCallmethod(callmethod);
		    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		    service.scheduleAtFixedRate(timdTask,initialDelay,transMap.get(type),TimeUnit.SECONDS);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    public Calendar getTaskCalendar(String type,String[] times){ 
       int length = times.length;
       logger.info("length--->>>"+length);
       Calendar calendar = Calendar.getInstance();  
       calendar.set(Calendar.SECOND, Integer.parseInt(times[length-1])); 
       if(type.equals("minute")) return calendar;  
       calendar.set(Calendar.MINUTE, Integer.parseInt(times[length-2])); 
       if(type.equals("hour")) return calendar;  
       calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[length-3])); 
       if(type.equals("day")) return calendar; 
       if(type.equals("week")){
    	   calendar.set(Calendar.DAY_OF_WEEK, Integer.parseInt(times[length-4])); 
       }
       if(type.equals("month")){
    	   calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(times[length-4])); 
       }
       return calendar; 
    }

	
    public Date getTime(){  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.HOUR_OF_DAY, 11);  
        calendar.set(Calendar.MINUTE, 39);  
        calendar.set(Calendar.SECOND, 00);  
        Date time = calendar.getTime();  
        return time;  
    }  
    
    public void getTask() throws Exception{
    	Shedual shedual = new Shedual();
		InputStream in = shedual.getClass().getClassLoader().getResourceAsStream("TimeTask.properties");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String temp;
		while((temp = br.readLine())!=null){
			tasks.add(temp);
		}
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		init();
	}
}

