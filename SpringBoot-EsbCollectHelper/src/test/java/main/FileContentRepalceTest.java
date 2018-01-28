package main;

import java.io.IOException;

import org.bjx.helper.file.FileContentReplaceHelper;


public class FileContentRepalceTest {
	
	public static void main(String[] args) {
		FileContentReplaceHelper fileContentReplaceHelper = new FileContentReplaceHelper();
		fileContentReplaceHelper.setCharSet("utf-8");
		fileContentReplaceHelper.setSourcePath("F:\\REPLACE\\public");
		fileContentReplaceHelper.setTargetPath("F:\\REPLACE\\public_bak");
		fileContentReplaceHelper.setFilePatten(".*?\\.html");
		fileContentReplaceHelper.setFormat("<li><a href=\"../table/datatable.html\">Datatable</a></li>");
		fileContentReplaceHelper.setTargetStr("<li><a href=\"../table/datatable.html\">Datatable</a></li>"+
		"<li><a href=\"../table/FtpFileBrowser.html\">FtpFileBrowser</a></li>");
//		fileContentReplaceHelper.setFormat("localhost:8080");
//		fileContentReplaceHelper.setTargetStr("10.221.18.29:10080");
		try {
			fileContentReplaceHelper.handle();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
