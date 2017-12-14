package org.springboot.esbcollecthelper.util.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.xmlbeans.impl.common.IOUtil;

public class FileHelper {

	public static List<String> listFiles(String dir) {
		List<String> result = new ArrayList<String>();
		File file = new File(dir);
		File[] fts = file.listFiles();
		for (File ft : fts) {
			if (ft.isDirectory()) {
				result.addAll(listFiles(ft.getAbsolutePath()));
			} else {
				result.add(ft.getAbsolutePath());
			}
		}
		return result;
	}

	public static File createFile(String fileName) throws IOException{
		String filePath = "";
		if(fileName.contains("/")) {
			filePath = fileName.substring(0, fileName.lastIndexOf("/"));
		}else {
			filePath = fileName.substring(0, fileName.lastIndexOf("\\"));
		}
		File path = new File(filePath);
		if(!path.exists()){
			path.mkdirs();
		}
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}
	
	public static void FileCopy(String sourceFilePath,String targetFilePath) throws IOException {
		
		FileInputStream fis = new FileInputStream(new File(sourceFilePath));
		FileOutputStream fos = new FileOutputStream(FileHelper.createFile(targetFilePath));
		IOUtil.copyCompletely(fis, fos);
		fos.flush();
		fis.close();
		fos.close();
	}
	
	public static byte[] FileByte(String filePath) throws IOException {
		InputStream is = new FileInputStream(new File(filePath));
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		int c = is.read();
		while (c != -1) {
			bo.write(c);
			c = is.read();
		}
		is.close();
		return bo.toByteArray();
	}
	
	
	public static void main(String[] args) {
		try {
			FileHelper.FileCopy("F:/工作计划.xlsx", "F:/temp.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
