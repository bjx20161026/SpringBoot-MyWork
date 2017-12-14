package org.springboot.esbcollecthelper.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListHelper {

	public static List<Map<String, String>> ObjectToString(List<Map<String, Object>> oList) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		for (Map<String, Object> oMap : oList) {
			map = new HashMap<String, String>();
			for (Object key : oMap.keySet()) {
				try {
					map.put((String) key, (String) oMap.get(key));
				} catch (ClassCastException e) {
					try {
						map.put((String) key, oMap.get(key).toString());
					} catch (Exception ee) {
						ee.printStackTrace();
					}
				}
			}
			list.add(map);
		}
		return list;
	}
}
