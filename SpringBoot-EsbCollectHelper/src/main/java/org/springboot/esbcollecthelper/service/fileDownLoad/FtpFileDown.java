package org.springboot.esbcollecthelper.service.fileDownLoad;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.net.ftp.FTPFile;
import org.bjx.helper.ftp.FileOperate;
import org.springboot.esbcollecthelper.service.cache.MsgInfoCache;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class FtpFileDown {

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
				map.put("fileInfo", String.format("File Size : %d Byte   &nbsp;&nbsp;&nbsp;  Last Modification Time : %s", ftpfile.getSize(),ftpfile.getTimestamp()));
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
}
