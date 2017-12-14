package org.springboot.esbcollecthelper.service.xmlMsgParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomMsg {
	@Autowired
	SendEsbMsg sendEsbMsg;
	
	String msgModel = "<ftpInfo>\n" + 
			"	<Type>WebService+FTP</Type>\n" + 
			"	<DataCatalog>DATA.LOCAL.SEND.TEST_APP</DataCatalog>\n" + 
			"	<WorkMode>WSFTP</WorkMode>\n" + 
			"	<SystemID>TEST_APP</SystemID>\n" + 
			"	<SessionID></SessionID>\n" + 
			"	<MsgSerial></MsgSerial>\n" + 
			"	<DeliveryTime></DeliveryTime>\n" + 
			"	<ReadyStatusCode></ReadyStatusCode>\n" + 
			"	<ReadyStatusDescription></ReadyStatusDescription>\n" + 
			"	<ConnectionString></ConnectionString>\n" + 
			"	<Path></Path>\n" + 
			"	<userName></userName>\n" + 
			"	<password></password>\n" + 
			"	<FileList></FileList>\n" + 
			"		<files>\n" + 
			"				<file>\n" + 
			"					<fileName></fileName>\n" + 
			"					<FileFormat></FileFormat>\n" + 
			"					<FileSize></FileSize>\n" + 
			"					<IsEncryption></IsEncryption>\n" + 
			"					<CipherKey></CipherKey>\n" + 
			"					<CipherFile></CipherFile>\n" + 
			"					<IsCompressed></IsCompressed>\n" + 
			"					<CompressKey></CompressKey>\n" + 
			"					<DataInfo></DataInfo>\n" + 
			"					<FieldSeparator></FieldSeparator>\n" + 
			"					<LineSeparator></LineSeparator>\n" + 
			"					<XmlSchema></XmlSchema>\n" + 
			"					<CharSet></CharSet>\n" + 
			"					<FileCheckInfo></FileCheckInfo>\n" + 
			"				</file>\n" + 
			"		</files>\n" + 
			"</ftpInfo>";
	
	public String ConnectionString;
	public String Path;
	public String userName;
	public String password;
	public String fileName;
	public String FileFormat;
	public String FieldSeparator;
	public String CharSet;
	 
	public String send() {	
		try {
			return "Succeed"+sendEsbMsg.send(creat());
		}catch (Exception e) {
			return "Failed : "+e.getMessage();
		}
	}
	
	public String creat() {
		String msg = msgModel;
		msg = msg.replace("<ConnectionString></ConnectionString>", "<ConnectionString>"+ConnectionString+"</ConnectionString>");
		msg = msg.replace("<Path></Path>", "<Path>"+Path+"</Path>");
		msg = msg.replace("<userName></userName>", "<userName>"+userName+"</userName>");
		msg = msg.replace("<password></password>", "<password>"+password+"</password>");
		msg = msg.replace("<fileName></fileName>", "<fileName>"+fileName+"</fileName>");
		msg = msg.replace("<FileList></FileList>", "<FileList>"+fileName+"</FileList>");
		msg = msg.replace("<FileFormat></FileFormat>", "<FileFormat>"+FileFormat+"</FileFormat>");
		if(FieldSeparator == null) FieldSeparator = "";
		msg = msg.replace("<FieldSeparator></FieldSeparator>", "<FieldSeparator>"+FieldSeparator+"</FieldSeparator>");
		msg = msg.replace("<CharSet></CharSet>", "<CharSet>"+CharSet+"</CharSet>");
		return msg;
	}
	
	public String getMsgModel() {
		return msgModel;
	}

	public void setMsgModel(String msgModel) {
		this.msgModel = msgModel;
	}

	public String getConnectionString() {
		return ConnectionString;
	}

	public void setConnectionString(String connectionString) {
		ConnectionString = connectionString;
	}

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileFormat() {
		return FileFormat;
	}

	public void setFileFormat(String fileFormat) {
		FileFormat = fileFormat;
	}

	public String getFieldSeparator() {
		return FieldSeparator;
	}

	public void setFieldSeparator(String fieldSeparator) {
		FieldSeparator = fieldSeparator;
	}

	public String getCharSet() {
		return CharSet;
	}

	public void setCharSet(String charSet) {
		CharSet = charSet;
	}

}
