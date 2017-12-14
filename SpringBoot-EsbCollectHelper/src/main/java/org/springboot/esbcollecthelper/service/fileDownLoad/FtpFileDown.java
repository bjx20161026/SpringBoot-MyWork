package org.springboot.esbcollecthelper.service.fileDownLoad;

import java.io.ByteArrayOutputStream;

import org.springboot.esbcollecthelper.service.cache.MsgInfoCache;
import org.springboot.esbcollecthelper.util.ftp.FileOperate;
import org.springframework.stereotype.Service;

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
}
