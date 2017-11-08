package org.springboot.workorder.service.dataValidity.impl;

import org.springboot.workorder.service.dataValidity.TemplateDataValidity;
import org.springboot.workorder.util.common.RegularTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TemplateDataValidityImpl implements TemplateDataValidity {

	@Value("${valid.districts}")
	public String districts;

	@Value("${valid.cities}")
	public String cities;

	@Override
	public boolean IsBlank(String str) {
		// TODO Auto-generated method stub
		return str == null || str.equals("");
	}

	@Override
	public boolean IsRegular(String str, String regular) {
		// TODO Auto-generated method stub
		return !RegularTool.isMatcher(str, regular);
	}

	@Override
	public boolean IsInclude(String data, String substring) {
		// TODO Auto-generated method stub
		if (substring.trim().equals("cities")) {
			return !RegularTool.isMatcher(data, districts);
		} else if (substring.trim().equals("districts")) {
			return !RegularTool.isMatcher(data, cities);
		}
		return false;
	}
}
