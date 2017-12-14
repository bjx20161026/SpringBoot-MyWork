package org.springboot.esbcollecthelper.web;

import java.io.IOException;
import java.util.Map;

import org.springboot.esbcollecthelper.service.esbMsgCount.TransErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransErrorController {
	@Autowired
	TransErrorHandler transErrorHandler;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/api/errorLeftCount", method = RequestMethod.GET)
	public Map errorLeftCount() {
		return transErrorHandler.errorLeftCount();
	}
	
	@RequestMapping(value = "/api/valueTooLarge", produces = "application/octet-stream;charset=UTF-8", method = RequestMethod.GET)
	public ResponseEntity<byte[]> valueTooLarge() throws IOException {
		byte[] bytes = transErrorHandler.handle();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", transErrorHandler.getTargetFileName());
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/otherError", produces = "application/octet-stream;charset=UTF-8", method = RequestMethod.GET)
	public ResponseEntity<byte[]> otherError() throws IOException {
		byte[] bytes = transErrorHandler.handleOther();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", transErrorHandler.getTargetFileName());
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
	

}
