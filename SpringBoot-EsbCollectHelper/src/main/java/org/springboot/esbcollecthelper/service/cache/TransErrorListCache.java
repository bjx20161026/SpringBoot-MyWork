package org.springboot.esbcollecthelper.service.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author b j x
 * @Description Transformation error classification cache
 * 
 */

public class TransErrorListCache {
	
	public static List<Map<String,Object>> inList = new ArrayList<Map<String,Object>>();
	public static List<Map<String,Object>> vtlList = new ArrayList<Map<String,Object>>();
	public static List<Map<String,Object>> flfList = new ArrayList<Map<String,Object>>();
	public static List<Map<String,Object>> oeList = new ArrayList<Map<String,Object>>();
	
	public static void initInList() {
		inList = new ArrayList<Map<String,Object>>();
	}

	public static void initVtlList() {
		vtlList = new ArrayList<Map<String,Object>>();
	}
	
	public static void initFlfList() {
		 flfList = new ArrayList<Map<String,Object>>();
	}
	
	public static void initOeList() {
		oeList = new ArrayList<Map<String,Object>>();
	}

}
