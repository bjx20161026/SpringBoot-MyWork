package org.springboot.esbcollecthelper.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springboot.esbcollecthelper.service.fileDownLoad.FtpFileDown;
import org.springboot.esbcollecthelper.service.xmlMsgParser.CustomMsg;
import org.springboot.esbcollecthelper.service.xmlMsgParser.KeyInformationPicker;
import org.springboot.esbcollecthelper.service.xmlMsgParser.SendEsbMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataTableController {
	
	@Autowired
	KeyInformationPicker keyInformationPicker;
	@Autowired
	FtpFileDown ftpFileDown;
	@Autowired
	SendEsbMsg sendEsbMsg;
	@Autowired
	CustomMsg customMsg;
	
	@RequestMapping(value = "/api/messageKeyInfo", method = RequestMethod.GET)
	public List<Map<String, Object>> msgKeyInformationPick(@RequestParam("protocol") String protocol,
			@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
		return keyInformationPicker.pickUp(protocol, startTime, endTime);
	}
	
	@RequestMapping(value = "/api/customMsg", method = RequestMethod.POST)
	public String customMsg(@RequestBody Map<String,String> map) {
		customMsg.setConnectionString(map.get("ConnectionString"));
		customMsg.setPath(map.get("Path"));
		customMsg.setUserName(map.get("userName"));
		customMsg.setPassword(map.get("password"));
		customMsg.setFileName(map.get("fileName"));
		customMsg.setFileFormat(map.get("FileFormat"));
		customMsg.setFieldSeparator(map.get("FieldSeparator"));
		customMsg.setCharSet(map.get("CharSet"));	
		return customMsg.send();
	}
	
	@RequestMapping(value = "/api/sendMsg", method = RequestMethod.GET)
	public String sendMsg(@RequestParam("fileName") String fileName) {
		return sendEsbMsg.sendByFileName(fileName);
	}
	
	
	@RequestMapping(value = "/api/downByFileName", produces = "application/octet-stream;charset=UTF-8", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downByFileName(@RequestParam("fileName") String fileName) throws Exception {
		try {
		byte[] bytes = ftpFileDown.downByFileName(fileName).toByteArray();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
		}catch (Exception e) {
			byte[] bytes = e.getMessage().getBytes();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "DownloadFailed.csv");
			return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/api/filePreview", method = RequestMethod.GET)
	public Map<String,Object> filePreview(@RequestParam("fileName") String fileName,@RequestParam("start") int start,@RequestParam("charset") String charset) throws Exception {
		return ftpFileDown.filePreview(fileName, start, charset);
	}
	
	@RequestMapping(value = "/api/xmlKeyMap", method = RequestMethod.GET)	
	public Map<String,Object> xmlKeyMap() throws Exception {
		Map<String,Object> map = new HashMap<>();
		List<String> heads = new ArrayList<String>();
		heads.add("A");
		heads.add("A");
		heads.add("A");
		heads.add("A");
		List<Object> datas = new ArrayList<Object>();
		datas.add(Arrays.asList("a","b","c","d"));
		datas.add(Arrays.asList("1","2","3","4"));
		map.put("heads", heads);
		map.put("datas", datas);
		return map;
	}

}
