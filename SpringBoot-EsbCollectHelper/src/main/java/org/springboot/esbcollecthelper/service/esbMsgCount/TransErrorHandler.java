package org.springboot.esbcollecthelper.service.esbMsgCount;

import java.io.IOException;
import java.util.Map;

public interface TransErrorHandler {
	
	byte[] handle() throws IOException;
	
	byte[] handleOther() throws IOException;
	
	String getTargetFileName();
	
	Map<String,Integer> errorLeftCount();

}
