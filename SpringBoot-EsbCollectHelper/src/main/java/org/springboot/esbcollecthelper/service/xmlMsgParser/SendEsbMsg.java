package org.springboot.esbcollecthelper.service.xmlMsgParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.springboot.esbcollecthelper.service.cache.MsgInfoCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendEsbMsg {
	Logger logger = Logger.getLogger(getClass());
	
	@Value("${SendEsbMsg.urlNameString}")
	String urlNameString;
	String content_Type = "application/json;charset=UTF-8";
	
	public String send(String body) throws Exception {
		URL realUrl = new URL(urlNameString);
		logger.info(body);
		URLConnection connection = realUrl.openConnection();
		connection.setRequestProperty("Content-Type", content_Type);
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.connect();
		PrintWriter out = new PrintWriter(connection.getOutputStream());
		out.print(body);
		out.flush();
		InputStream is = connection.getInputStream();
		String result = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
        return result;
	}
	
	public String sendByFileName(String fileName) {
		try {
			return "Succeed"+send((String)MsgInfoCache.MsgMap.get(fileName));
		}catch (Exception e) {
			return "Failed : "+e.getMessage();
		}
	}
	
	public String getUrlNameString() {
		return urlNameString;
	}

	public void setUrlNameString(String urlNameString) {
		this.urlNameString = urlNameString;
	}

	public String getContent_Type() {
		return content_Type;
	}

	public void setContent_Type(String content_Type) {
		this.content_Type = content_Type;
	}
	
}
