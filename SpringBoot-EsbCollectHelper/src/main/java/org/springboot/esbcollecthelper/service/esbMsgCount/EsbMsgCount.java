package org.springboot.esbcollecthelper.service.esbMsgCount;

import java.util.List;
import java.util.Map;

public interface EsbMsgCount {
	
	List<Map<String,Object>> countEffectiveMsg();
	
	List<Map<String,Object>> transError();

}
