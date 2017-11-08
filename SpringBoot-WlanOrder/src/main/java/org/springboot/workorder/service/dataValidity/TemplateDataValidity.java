package org.springboot.workorder.service.dataValidity;

public interface TemplateDataValidity {
	
	public boolean IsBlank(String str);
	public boolean IsRegular(String str,String regular);
	public boolean IsInclude(String data, String substring);

}
