package org.springboot.workorder.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("rest/workOrder")
public class WlanOrderController {
	Logger logger = Logger.getLogger(getClass());
	
	@RequestMapping(value = "/handleOrders", method = RequestMethod.POST)
	public int handleOrders(@RequestBody String orders) throws Exception {
		logger.info(String.format("http [post] path [handleOrders] body [%s]", orders));
		ObjectMapper mapper = new ObjectMapper();
		List<Map<Object,Object>> list = mapper.readValue(orders, new TypeReference<List<Map<Object,Object>>>() {}); 
		for(Map map:list)
		for(Object key:map.keySet()) {
			logger.info(String.format("Map key [%s] value [%s]", (String)key,map.get(key)));
		}
		return 1;
	}
}
