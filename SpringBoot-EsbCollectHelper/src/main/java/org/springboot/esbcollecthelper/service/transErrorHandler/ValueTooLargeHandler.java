package org.springboot.esbcollecthelper.service.transErrorHandler;

import java.util.Map;

import org.bjx.helper.common.RegularHelper;
import org.springboot.esbcollecthelper.service.cache.TransErrorListCache;
import org.springframework.stereotype.Service;

@Service
public class ValueTooLargeHandler {
	
	private String format = ".*? value too large for column \"(.*?)\"\\.\"(.*?)\"\\.\"(.*?)\".*?";

	public void handle(Map<String,Object> map) {
		map.put("SQL", createrAltersql((String) map.get("ERROR_REASON")));
		TransErrorListCache.vtlList.add(map);
	}

	private String createrAltersql(String str) {
		Map<Integer,String> map = RegularHelper.MatcherValues(str, format);
		return String.format("alter table %s.%s modify %s varchar2(4000);", map.get(1),map.get(2),map.get(3));
	}
	
}
