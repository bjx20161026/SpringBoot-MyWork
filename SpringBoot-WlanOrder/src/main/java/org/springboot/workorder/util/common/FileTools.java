package org.springboot.workorder.util.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FileTools {
	/*
	 * List all filenames under the path and subfolders
	 */
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
/* 
 * read properties file then return properties object
 */
	public static Properties getProperties(String path) {
		Properties pro = new Properties();
		FileTools fileTools = new FileTools();
		InputStream in = fileTools.getClass().getClassLoader().getResourceAsStream(path);
		try {
			pro.load(in);
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pro;
	}
	
	/*
	 * change or add properties parameters
	 */
	public static void setProperties(Map<String,String> map,String path) throws Exception{
		    Properties prop = new Properties();
	        InputStream fis = new FileInputStream("config/"+path);
	        // read original parameters
	        prop.load(fis);
	        OutputStream fos = new FileOutputStream("config/"+path);
	        for(String key:map.keySet()){
	        	prop.setProperty(key, map.get(key));
	        }
	        prop.store(fos,"last update");
	        fis.close();
	        fos.close();
	}
	
	public static File CtreatFile(String fileName) throws IOException{
		String filePath = fileName.substring(0, fileName.lastIndexOf("/"));
		System.out.println(filePath);
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
	
	public static void main(String[] args){
		try {
			FileTools.CtreatFile("F:/justTest/asdf.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
