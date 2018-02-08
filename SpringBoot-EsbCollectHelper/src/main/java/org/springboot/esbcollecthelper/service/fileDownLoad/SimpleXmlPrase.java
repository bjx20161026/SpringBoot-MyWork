package org.springboot.esbcollecthelper.service.fileDownLoad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bjx.helper.common.RegularHelper;
import org.springframework.stereotype.Service;
@Service
public class SimpleXmlPrase {
	
	public Map<String,Object> parse(InputStream inputStream,String charset,int start) throws Exception{
		start +=10;
		int num = 0;
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
		Map<Integer, String> heads = new  LinkedHashMap<Integer, String>();
		String temp="";
		List<Map<Integer, String>> matchList = new ArrayList<Map<Integer, String>>();
		Map<Integer, String> matchMap = new  HashMap<>();
		while((temp = bufferedReader.readLine()) != null) {
			if(RegularHelper.isMatcher(temp, ".*?>(.*?)</N>")){
				Map<Integer, String> tempMap = RegularHelper.MatcherValues(temp, ".*?i=\"(.*?)\">(.*?)</N>");
				heads.put(Integer.parseInt(tempMap.get(1)), tempMap.get(2));
				continue;
			}			
			if(RegularHelper.isMatcher(temp, ".*?<V i=\"(.*?)\">(.*?)</v>")){
				Map<Integer, String> tempMap =  RegularHelper.MatcherValues(temp, ".*?<V i=\"(.*?)\">(.*?)</v>");
				matchMap.put(Integer.parseInt(tempMap.get(1)), tempMap.get(2));
				continue;
			}
			if(RegularHelper.isMatcher(temp, ".*?(<Pm|<Cm|<Object)( |>).*?")) {
				matchMap = new HashMap<>();
				continue;
			}		
			try {
			if(RegularHelper.isMatcher(temp, ".*?(</Pm>|</Cm>|</Object>).*?")) {
				matchList.add(matchMap);
				num++;
				if(num>=start) break;
				continue;
			}
			}catch (Exception ee) {
				// TODO: handle exception
				System.out.println("temp : "+temp);
				ee.printStackTrace();
			}
		}
	    Map<String,Object> resultMap = new HashMap<>();
	    List<String> reHeads = new ArrayList<>();
	    List<Integer> reData = new ArrayList<>();
	    for(Entry<Integer, String> entry : heads.entrySet()) {
	    	reData.add(entry.getKey());
	    	reHeads.add(entry.getValue());
	    }
	    resultMap.put("heads", reHeads);
	    List<Object> datas = new ArrayList<Object>();
	    List<String> data ;
	    for(Map<Integer, String> map:matchList) {	 
	    	data = new ArrayList<>();
	    	for(int j:reData) {
	    		data.add(map.get(j)==null?"":map.get(j));
	    	}
	    	datas.add(data);
	    }
	    resultMap.put("datas", datas);
		return resultMap;
	}
	
	public static void main(String[] args) {
		SimpleXmlPrase simpleXmlPrase = new SimpleXmlPrase();
		try {
			Map<String,Object> result = simpleXmlPrase.parse(new FileInputStream("F:/W_Huawei_RNC_Huawei-4G-LTE-OMC4_PM_Carrier_311_15_20180114-1430.xml"), "utf-8", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
