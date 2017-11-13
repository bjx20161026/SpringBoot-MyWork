package org.springboot.workorder.service.orderHandler;

import java.util.Map;

import org.springboot.workorder.service.templateParser.BasicParser;
import org.springboot.workorder.util.excel.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ApImportHandler extends OrderHandler {
	
	@Value("${sheet.ApImport}")
	String sheetName;

	
	@Override
	public void handle() {
		// TODO Auto-generated method stub
		basicParser.setPath((String)orderMap.get("localfile"));
		basicParser.setType("ApImport");
		basicParser.setSheetName(sheetName);
		basicParser.init();
//		list = basicParser.parse();
		

	}
}
