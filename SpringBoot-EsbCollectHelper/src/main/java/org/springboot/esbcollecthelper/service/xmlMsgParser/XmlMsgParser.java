package org.springboot.esbcollecthelper.service.xmlMsgParser;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springboot.esbcollecthelper.util.common.RegularHelper;
import org.springframework.stereotype.Service;

@Service
public class XmlMsgParser {
public String charset;
	
	public String myFileName;

	public String getMyFileName() {
		return myFileName;
	}

	public void setMyFileName(String myFileName) {
		this.myFileName = myFileName;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String GetFtpInfo(String xml) throws DocumentException{
		String ftpUrl = "";
		Document dom = null;
		dom = DocumentHelper.parseText(xml);
		Element root=dom.getRootElement();
		Element ftpInfo;
		if(root.getName().equals("fileInfo")){
			 ftpInfo=root.element("ftpInfo");
		}else{
			 ftpInfo=root;
		}
		Element ConnectionString = ftpInfo.element("ConnectionString");
		Element Path = ftpInfo.element("Path");
		Element UserName = ftpInfo.element("userName");
		Element Password = ftpInfo.element("password");
		Element files = ftpInfo.element("files");
		Element file = files.element("file");
		Element FileName = file.element("fileName");
		Element CharSet = file.element("CharSet");
		String connection = ConnectionString.getTextTrim();
		String path = Path.getTextTrim();
		String userName = UserName.getTextTrim();
		String password = Password.getTextTrim();
		String fileName = FileName.getTextTrim();
		setMyFileName(fileName);
		String connectionA;
		if(CharSet!=null){
		setCharset(CharSet.getTextTrim());
		}else{
			setCharset("gbk");
		}
		if(path.endsWith("/")){
			path = path.substring(0,path.length()-1);
		}
		if(path.startsWith("/")){
			path = path.substring(1,path.length());
		}
		if(RegularHelper.isMatcher(connection, "ftp://(.*?):(.*?)@(\\d+\\.\\d+\\.\\d+\\.\\d+):21")){
			ftpUrl = connection+"/"+path+"/"+fileName;
		}else if((connectionA = RegularHelper.MatcherValue(connection,".*?(\\d+\\.\\d+\\.\\d+\\.\\d+:21)"))!=null){
			String format = "ftp://%s:%s@%s/%s/%s";
			ftpUrl = String.format(format, userName,password,connectionA,path,fileName);
		}else{
			connectionA = RegularHelper.MatcherValue(connection,".*?(\\d+\\.\\d+\\.\\d+\\.\\d+)");
			String format = "ftp://%s:%s@%s:21/%s/%s";
			ftpUrl = String.format(format, userName,password,connectionA,path,fileName);
		}
		return ftpUrl;
	}
}
