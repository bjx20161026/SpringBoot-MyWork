package org.springboot.workorder.util.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularTool {
	public static boolean IsNumber(String str){
		if(str == null||str.equals("")) return true;
		String format = "^(-?\\d+)(\\.\\d+)?$";
		 Pattern p = Pattern.compile(format,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);  
	     Matcher matcher = p.matcher(str);  
		return matcher.find();	
	}
	
	public static boolean isMatcher(String str,String format){
		if(str == null) str = "";
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
		String str = "";
		String format = "(|否|流)";
		System.out.println("匹配结果是--->>>"+RegularTool.isMatcher(str, format));
//		Map<Integer,String> map = RegularTool.MatcherValues("(to_date(substr(?,1,17),'yy-mm-dd hh24:mi:ss') - to_date('1970-01-01','yyyy-mm-dd'))*24*60*60",".*?,(\\d+),(\\d+)\\),'(.*?)'\\) -.*?");
//		System.out.println("map.get(1)--->>>"+map.get(1));
//		System.out.println("map.get(2)--->>>"+map.get(2));
//		System.out.println("map.get(3)--->>>"+map.get(3));
//		String str = RegularTool.MatcherValue("12343daf",".*?([0-9]+).*?");
//		System.out.println(str);
	}
}
