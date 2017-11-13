package org.springboot.workorder.service.orderHandler;

import java.util.List;
import java.util.Map;

import org.springboot.workorder.service.cache.TemplateProperties;
import org.springframework.stereotype.Service;

@Service
public class Dispenser {
	
	private List<Map<Object,Object>> list;

	public void handle() {
		for(Map map:list) {
			try {
				OrderHandler orderHandler = (OrderHandler) Class.forName(TemplateProperties.OrderDispenserMap.get(map.get("workjob_type"))).newInstance();
				orderHandler.setOrderMap(map);
				orderHandler.handle();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

	public List<Map<Object, Object>> getList() {
		return list;
	}

	public void setList(List<Map<Object, Object>> list) {
		this.list = list;
	}
	
}
