package org.springboot.esbcollecthelper.service.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class MsgInfoCache {
	public static int maximumSize = 10000;  
	public static int maxiMsgSize = 100;  
	public static LinkedHashMap<String,Object> ftpUrlMap= new LinkedHashMap<String,Object>() {  
	        private static final long serialVersionUID = 1L;  
	        @Override  
	        protected boolean removeEldestEntry(Map.Entry<String,Object> eldest) {  
	            return size() > maximumSize;  
	        }  
	    }; 
	    
	    public static LinkedHashMap<String,Object> MsgMap= new LinkedHashMap<String,Object>() {  
	        private static final long serialVersionUID = 1L;  
	        @Override  
	        protected boolean removeEldestEntry(Map.Entry<String,Object> eldest) {  
	            return size() > maxiMsgSize;  
	        }  
	    }; 
}
