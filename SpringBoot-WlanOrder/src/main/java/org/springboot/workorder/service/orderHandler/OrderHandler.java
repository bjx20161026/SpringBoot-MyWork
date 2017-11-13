package org.springboot.workorder.service.orderHandler;

import java.util.Map;

import org.springboot.workorder.service.templateParser.BasicParser;
import org.springboot.workorder.util.excel.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OrderHandler {
	
	@Autowired
	BasicParser basicParser;
	@Autowired
	ExcelReader excelReader;
	
	public Map orderMap;
	
	public abstract void handle();
	
	public void setOrderMap(Map map) {
		this.orderMap = map;
	};

}
