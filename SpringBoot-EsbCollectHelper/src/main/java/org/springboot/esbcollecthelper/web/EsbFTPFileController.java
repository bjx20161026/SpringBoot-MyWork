package org.springboot.esbcollecthelper.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.net.ftp.FTPFile;
import org.bjx.helper.date.DateHelper;
import org.bjx.helper.ftp.FileOperate;
import org.springboot.esbcollecthelper.service.cache.MsgInfoCache;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EsbFTPFileController {
	
	public static Map<String,String> staticMap = new HashMap<>();
	
	@RequestMapping(value = "/api/folderBrowser",method = RequestMethod.GET)
	public List<Map<String,Object>> folderBrowser(@RequestParam("id") String id) throws Exception{
		System.out.println("id : "+id);
		String[] ids = id.split(",");
		id = ids[ids.length-1];
		String root = "";
		String temp = id.substring(0, 1);
		if(temp.equals("1")) {
			root = "135";
		}else if(temp.equals("2")){
			root = "136";
		}else if(temp.equals("3")){
			root = "137";
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(id.length()==1) {
			if(id.equals("0")) {
				list.add(initNode("1"));
				list.add(initNode("2"));
				list.add(initNode("3"));
				return list;
			}
			return rootNode(id,root);
		}
		if(id.length()==3) {
			return nextNode(id,root);
		}
		if(id.length()>3) {
			return finalNode(id,root);
		}
		return null;
	}
	
	
	public synchronized List<Map<String,Object>> finalNode(String id,String root) throws Exception{
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<String> rmList = new ArrayList<String>();
		for(Entry<String, String> entry:staticMap.entrySet()) {
			if(entry.getKey().startsWith(id)&&entry.getKey().length()>6) {
				staticMap.remove(entry.getKey());
				rmList.add(entry.getKey());
			}
		}
		for(String str:rmList) {
			staticMap.remove(str);
		}
		String ftpUrl = "ftp://"+staticMap.get(id.substring(0, 3)).replaceAll("APP", "PUT")+":W1n3m5s#@10.221.232."+root+":21/"+staticMap.get(id.substring(0, 3))+"/"+staticMap.get(id)+"/";
//		System.out.println("ftpUrl : "+ftpUrl);
		FileOperate fileOperate = new FileOperate(ftpUrl);
		fileOperate.login();
		fileOperate.cd();
		Map<String, Object> map = new HashMap<>();
		FTPFile[] ftpFiles = fileOperate.lsFTPFiles();
		int i = 0;
		DateHelper dateHelper = new DateHelper();
		System.out.println("ftpFiles.length : "+ftpFiles.length);
		if (ftpFiles.length > 0) {
			for (int j = (ftpFiles.length - 1); j > -1; j--) {
				FTPFile ftpFile = ftpFiles[j];
				i++;
				map = new HashMap<>();
				map.put("id", id + i);
				map.put("name", ftpFile.getName());
				staticMap.put(id + i, ftpFile.getName());
				map.put("date", dateHelper.getHourString(ftpFile.getTimestamp(), 8));
				if (!ftpFile.isFile()) {
					map.put("state", "closed");
				} else {
					MsgInfoCache.ftpUrlMap.put(ftpFile.getName(), ftpUrl+ftpFile.getName());
					if (ftpFile.getName().endsWith(".gz")) {
						map.put("iconCls", "icon-zip");
					}
					map.put("size", ftpFile.getSize() + " byte");
				}
				list.add(map);
			}
			fileOperate.disConnect();
			return list;
		}
		fileOperate.disConnect();
		return null;
	}
	
	public synchronized List<Map<String,Object>> nextNode(String id,String root) throws Exception{
		List<String> rmList = new ArrayList<String>();
		for(Entry<String, String> entry:staticMap.entrySet()) {
			if(entry.getKey().length()==6&&entry.getKey().startsWith(id)&&!entry.getKey().equals(id)) {
				rmList.add(entry.getKey());
			}
		}
		for(String str:rmList) {
			staticMap.remove(str);
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String ftpUrl = "ftp://"+staticMap.get(id).replaceAll("APP", "PUT")+":W1n3m5s#@10.221.232."+root+":21/"+staticMap.get(id)+"/";
		FileOperate fileOperate = new FileOperate(ftpUrl);
		fileOperate.login();
		fileOperate.cd();
		Map<String,Object> map = new HashMap<>();
		FTPFile[] ftpFiles=fileOperate.lsFTPFiles();
		int i = 0;
		String tempI = "";
		DateHelper dateHelper = new DateHelper();
		for(FTPFile ftpFile:ftpFiles) {
			 i++;
			 if(i<10) {
				 tempI = "00"+i;
			 }else if(i<100) {
				 tempI = "0"+i;
			 }
			 tempI = ""+i;
			 map = new HashMap<>();
			 map.put("id", id+tempI);
			 map.put("name", ftpFile.getName());
			 staticMap.put(id+tempI, ftpFile.getName());
			 map.put("date", dateHelper.getHourString(ftpFile.getTimestamp(), 8));
			 if(!ftpFile.isFile())
			 map.put("state", "closed");
			 list.add(map); 
		}
		fileOperate.disConnect();
		return list;
	}
	
	public synchronized  Map<String,Object> initNode(String id){
		//staticMap = new HashMap<>();
		Map<String,Object> rootMap = new HashMap<>();
		String root = "137";
		if(id.equals("1")) {
			root = "135";
		}else if(id.equals("2")){
			root = "136";
		}
		rootMap.put("iconCls", "icon-server");
		rootMap.put("id", id);
		rootMap.put("state", "closed");
		rootMap.put("name", "10.221.232."+root);
		return rootMap;
	}
	
	
	public synchronized List<Map<String,Object>> rootNode(String id,String root) throws Exception{
		List<String> rmList = new ArrayList<String>();
		for(Entry<String, String> entry:staticMap.entrySet()) {
			if(entry.getKey().length()==3&&entry.getKey().startsWith(id)) {
				rmList.add(entry.getKey());
			}
		}
		for(String str:rmList) {
			staticMap.remove(str);
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String ftpUrl = "ftp://GCP_PUT:W1n3m5s#@10.221.232."+root+":21/";
		FileOperate fileOperate = new FileOperate(ftpUrl);
		fileOperate.login();
		fileOperate.cd();
		Map<String,Object> map = new HashMap<>();
		FTPFile[] ftpFiles=fileOperate.lsFTPFiles();
		int i = 0;
		DateHelper dateHelper = new DateHelper();
		for(FTPFile ftpFile:ftpFiles) {
			 i++;
			 map = new HashMap<>();
			 map.put("id", id+""+(i<10?"0"+i:i));
			 map.put("name", ftpFile.getName());
			 staticMap.put(id+""+(i<10?"0"+i:i), ftpFile.getName());
			 map.put("date", dateHelper.getHourString(ftpFile.getTimestamp(), 8));
			 if(!ftpFile.isFile())
			 map.put("state", "closed");
			 list.add(map); 
		}
		fileOperate.disConnect();
		return list;
	}
	
    public static void main(String[] args) {
    	for(int i=0;i<1000;i++) {
    		staticMap.put(""+i, "i"+i);
    	}
    	
    	List<String> rmList = new ArrayList<String>();
    	for(Entry<String, String> entry:staticMap.entrySet()) {
				rmList.add(entry.getKey());
		}
		for(String str:rmList) {
			staticMap.put("", "i");
			staticMap.remove(str);
		}
		
    	for(int i=0;i<1000;i++) {
    		staticMap.put(""+i, "i"+i);
    	}
	}

}
