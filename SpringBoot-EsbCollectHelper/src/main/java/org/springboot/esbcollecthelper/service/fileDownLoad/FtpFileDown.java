package org.springboot.esbcollecthelper.service.fileDownLoad;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.net.ftp.FTPFile;
import org.bjx.helper.date.DateHelper;
import org.bjx.helper.ftp.FileOperate;
import org.springboot.esbcollecthelper.service.cache.MsgInfoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FtpFileDown {
	@Autowired
	SimpleXmlPrase simpleXmlPrase;

	public ByteArrayOutputStream ftpFile(String ftpUrl) throws Exception{
		FileOperate fo = new FileOperate(ftpUrl);
		fo.login();
		fo.cd();
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		fo.get(bo);
		return bo;
	}
	
	
	public ByteArrayOutputStream downByFileName(String fileName) throws Exception {
		return ftpFile((String)MsgInfoCache.ftpUrlMap.get(fileName));
	}

	
	public Map<String,Object> filePreview(String fileName,int start,String charset) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		FileOperate fo = new FileOperate((String)MsgInfoCache.ftpUrlMap.get(fileName));
		fo.login();
		fo.cd();
		for(FTPFile ftpfile:fo.lsFTPFiles()) {
			if(ftpfile.getName().equals(fileName)) {
				map.put("fileInfo", String.format("File Size : %d Byte   &nbsp;&nbsp;&nbsp;  Last Modification Time : %s", ftpfile.getSize(),new DateHelper().getHourString(ftpfile.getTimestamp(),8)));
			}
		}
		InputStream is = fo.get(fileName);
		if (fileName.endsWith("gz")) {
			is = new GZIPInputStream(is);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
		List<String> listData = new ArrayList<String>();
		String temp="";
		for (int i = 0; i < start+500; i++) {
			if ((temp = br.readLine()) == null)
				break;
			System.out.println("temp : "+temp);
			listData.add(temp);
		}
		map.put("datas", listData);
		is.close();
		fo.disConnect();
		return map;
	}
	
	public Map<String,Object> xmlKey2Value(String fileName,int start,String charset) throws Exception{
		FileOperate fileOperate = new FileOperate((String)MsgInfoCache.ftpUrlMap.get(fileName));
		fileOperate.login();
		fileOperate.cd();
		InputStream inputStream = fileOperate.get(fileName);
		if (fileName.endsWith("gz")) {
			inputStream = new GZIPInputStream(inputStream);
		}
		Map<String,Object> map = simpleXmlPrase.parse(inputStream, charset, start);
		inputStream.close();
		fileOperate.disConnect();
		return map;	
	}

	public static void main(String[] args) {
		//Calendar.getInstance();
		String  str = new DateHelper().getHourString(Calendar.getInstance(), 8);
		//String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		System.out.println(str);
	}
}
