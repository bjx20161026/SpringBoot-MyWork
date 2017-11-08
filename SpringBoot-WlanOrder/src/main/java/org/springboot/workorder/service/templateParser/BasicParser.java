package org.springboot.workorder.service.templateParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springboot.workorder.service.cache.TemplateProperties;
import org.springboot.workorder.service.dataValidity.TemplateDataValidity;
import org.springboot.workorder.util.excel.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasicParser extends TemplateParser {
	@Autowired
	ExcelReader excelReader;
	@Autowired
	TemplateDataValidity templateDataValidity;

	public int column = 0;
	public String validMsg = "";

	public List<Map<String, String>> parse() throws IOException {
		List<Map<String, String>> list = excelReader.readExcel(path);
		String[] heads = propertiesMap.get("heads").split(",");
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		for (Map<String, String> map : list) {
			Map<String, String> resultMap = new HashMap<String, String>();
			column++;
			for (String head : heads) {
				String[] headContent = head.split("*");
				check(headContent[0], propertiesMap.get(headContent[0]), map.get(headContent[0]));
				resultMap.put(headContent[1], map.get(headContent[0]));
			}
			resultList.add(resultMap);
		}
		return resultList;
	}

	public void check(String head, String rules, String data) {
		if (rules == null)
			return;
		for (String rule : rules.split("Ж")) {
			if (rule.startsWith("1") && templateDataValidity.IsBlank(data)) {
				validMsg += String.format("第 %s行 %s 不能为空;", column + startLine + "", head);
			} else if (rule.startsWith("2")
					&& templateDataValidity.IsInclude(data, rule.substring(rule.indexOf("Ф"), rule.length()))) {
				validMsg += String.format("第 %s行 %s 值 %s 不在指定范围内;", column + startLine + "", head, data);
			} else if (rule.startsWith("3")
					&& templateDataValidity.IsRegular(data, rule.substring(rule.indexOf("Ф"), rule.length()))) {
				validMsg += String.format("第 %s行 %s 值 %s 不在指定范围内;", column + startLine + "", head, data);
			}
		}
	}

	public void init() {
		propertiesMap = TemplateProperties.TemplatePropertiesMap.get(type);
		setStartLine(Integer.parseInt(propertiesMap.get("startline")));
		setHeadLine(Integer.parseInt(propertiesMap.get("headline")));
		excelReader.setSheetName(sheetName);
		excelReader.setStartLine(startLine);
		excelReader.setHeadLine(headLine);
	}
	
	public String getValidMsg() {
		return validMsg;
	}

	public void setValidMsg(String validMsg) {
		this.validMsg = validMsg;
	}

}
