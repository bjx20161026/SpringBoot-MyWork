package org.springboot.workorder.service.templateParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public abstract class TemplateParser {
	Logger logger = Logger.getLogger(getClass());
	
	public String sheetName;
	public int startLine;
	public int headLine;
	public Map<String,String> propertiesMap;
	public String path;
	public String type;

	public abstract  List<Map<String,String>> parse() throws IOException;
	
	public abstract void init();
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public int getStartLine() {
		return startLine;
	}
	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}
	public int getHeadLine() {
		return headLine;
	}
	public void setHeadLine(int headLine) {
		this.headLine = headLine;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


}
