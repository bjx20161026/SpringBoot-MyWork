package org.springboot.esbcollecthelper.util.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularHelper {
	public static boolean IsNumber(String str){
		 if(str == null||str.equals("")) return true;
		 String format = "^(-?\\d+)(\\.\\d+)?$";
		 Pattern p = Pattern.compile(format,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);  
	     Matcher matcher = p.matcher(str);  
		 return matcher.find();	
	}
	
	public static boolean isMatcher(String str,String format){
		Pattern p = Pattern.compile(format,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);  
	    Matcher matcher = p.matcher(str);
		return matcher.find();
	}
	
	public static String MatcherValue(String str,String format){
		Pattern p = Pattern.compile(format,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);  
	    Matcher matcher = p.matcher(str);
	    if(matcher.find()){
	    	return matcher.group(1);
	    }
		return null;
	}
	
	public static Map<Integer,String> MatcherValues(String str,String format){
		Map<Integer,String> map = new HashMap<Integer,String>();
		Pattern p = Pattern.compile(format,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);  
	    Matcher matcher = p.matcher(str);
	    if(matcher.find()){
	    	for(int i=0;i<=matcher.groupCount();i++){
	    		map.put(i,matcher.group(i));
	    	}
	    	return map;
	    }
		return null;
	}
	
	public static void main(String[] args){
		String str = "ORA-12899: value too large for column \"IPMSDW\".\"DW_FT_SE_BB_COMPLAIN_TICKET_H\".\"ORDER_STATUS\" (actual: 5, maximum: 4)";
		String format = ".*? value too large for column \"(.*?)\"\\.\"(.*?)\"\\.\"(.*?)\".*?";
		Map<Integer,String> map =  RegularHelper.MatcherValues(str, format);
		for(Object key:map.keySet()) {
			System.out.println(key+":"+map.get(key));
		}
	}
}
