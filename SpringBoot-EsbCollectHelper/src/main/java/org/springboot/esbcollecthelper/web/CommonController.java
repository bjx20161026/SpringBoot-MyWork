package org.springboot.esbcollecthelper.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springboot.esbcollecthelper.dao.def.EsbMsgCountDao;
import org.springboot.esbcollecthelper.service.esbMsgCount.EsbMsgCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
	@Autowired
	EsbMsgCount esbMsgCount;
	@Autowired
	EsbMsgCountDao esbMsgCountDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/api/msgCount", method = RequestMethod.GET)
	public Map MsgCount() {
		List<Map<String, Object>> countList = esbMsgCount.countEffectiveMsg();
		Map map = new HashMap<Object, Object>();
		List<String> labels = new ArrayList<String>();
		for (Map<String, Object> countMap : countList) {
			labels.add((String) countMap.get("PERIOD"));
		}
		map.put("labels", labels);
		List<Map<Object, Object>> datasets = new ArrayList<Map<Object, Object>>();
		Map dataMap = new HashMap<Object, Object>();
		dataMap.put("label", "Esb Message");
		dataMap.put("fillColor", "rgba(34, 167, 240,0.2)");
		dataMap.put("strokeColor", "#22A7F0");
		dataMap.put("pointColor", "#22A7F0");
		dataMap.put("pointStrokeColor", "#fff");
		dataMap.put("pointHighlightFill", "#fff");
		dataMap.put("pointHighlightStroke", "#22A7F0");
		List<Integer> date = new ArrayList<Integer>();
		for (Map<String, Object> countMap : countList) {
			date.add(((BigDecimal) countMap.get("RECEIVE")).intValue());
		}
		dataMap.put("data", date);
		datasets.add(dataMap);
		map.put("datasets", datasets);
		return map;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/api/messageComponents", method = RequestMethod.GET)
	public Map messageComponents() {
		return esbMsgCountDao.messageComponents();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/api/transError", method = RequestMethod.GET)
	public Map transError() {
		Map map = new HashMap<Object, Object>();
		List<Map<String, Object>> countList = esbMsgCount.transError();
		List<String> labels = new ArrayList<String>();
		for (Map<String, Object> countMap : countList) {
			labels.add((String) countMap.get("PERIOD"));
		}
		map.put("labels", labels);
		List<Map<Object, Object>> datasets = new ArrayList<Map<Object, Object>>();
		Map invMap = new HashMap<Object, Object>();
		invMap.put("label", "Invalid Number");
		invMap.put("fillColor", "rgba(26, 188, 156,0.6)");
		invMap.put("strokeColor", "#1ABC9C");
		invMap.put("pointColor", "#1ABC9C");
		invMap.put("pointStrokeColor", "#fff");
		invMap.put("pointHighlightFill", "#fff");
		invMap.put("pointHighlightStroke", "#1ABC9C");
		List<Integer> invdate = new ArrayList<Integer>();
		for (Map<String, Object> countMap : countList) {
			invdate.add(((BigDecimal) countMap.get("INRECEIVE")).intValue());
		}
		invMap.put("data", invdate);
		datasets.add(invMap);
		Map valMap = new HashMap<Object, Object>();
		valMap.put("label", "Value too Large");
		valMap.put("fillColor", "rgba(34, 167, 240,0.6)");
		valMap.put("strokeColor", "#22A7F0");
		valMap.put("pointColor", "#22A7F0");
		valMap.put("pointStrokeColor", "#fff");
		valMap.put("pointHighlightFill", "#fff");
		valMap.put("pointHighlightStroke", "#22A7F0");
		List<Integer> valdate = new ArrayList<Integer>();
		for (Map<String, Object> countMap : countList) {
			valdate.add(((BigDecimal) countMap.get("VARECEIVE")).intValue());
		}
		valMap.put("data", valdate);
		datasets.add(valMap);
		map.put("datasets", datasets);
		return map;
	}



}
