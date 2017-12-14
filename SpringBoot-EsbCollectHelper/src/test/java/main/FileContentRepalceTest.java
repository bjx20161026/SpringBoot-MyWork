package main;

import java.io.IOException;

import org.springboot.esbcollecthelper.util.file.FileContentReplaceHelper;

public class FileContentRepalceTest {
	
	public static void main(String[] args) {
		FileContentReplaceHelper fileContentReplaceHelper = new FileContentReplaceHelper();
		fileContentReplaceHelper.setCharSet("utf-8");
		fileContentReplaceHelper.setSourcePath("F:\\REPLACE\\public");
		fileContentReplaceHelper.setTargetPath("F:\\REPLACE\\public_bak");
		fileContentReplaceHelper.setFilePatten(".*?\\.js");
		fileContentReplaceHelper.setFormat("localhost:8080");
		fileContentReplaceHelper.setTargetStr("10.221.18.29:10080");
		try {
			fileContentReplaceHelper.handle();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
