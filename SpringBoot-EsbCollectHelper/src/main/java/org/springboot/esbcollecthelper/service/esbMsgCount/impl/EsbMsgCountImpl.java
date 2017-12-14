package org.springboot.esbcollecthelper.service.esbMsgCount.impl;

import java.util.List;
import java.util.Map;

import org.springboot.esbcollecthelper.service.cache.SqlResultCache;
import org.springboot.esbcollecthelper.service.esbMsgCount.EsbMsgCount;
import org.springframework.stereotype.Service;

@Service
public class EsbMsgCountImpl implements EsbMsgCount{

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> countEffectiveMsg() {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) SqlResultCache.map.get("EFFECTIVEMSG");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> transError() {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) SqlResultCache.map.get("TRANSERROR");
	}

}
