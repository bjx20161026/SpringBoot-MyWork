package org.springboot.esbcollecthelper.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.springboot.esbcollecthelper.util.common.RegularHelper;

public class FileContentReplaceHelper {
	
	private String sourcePath;
	private String targetPath;
	private String filePatten; 
	private int findRule = 0;//0--include;1--regular
	private String format;
	private String targetStr;
	private String charSet;
	private String lineBreak = "\r\n";

	public void handle() throws IOException {
		for(String fileName:FileHelper.listFiles(sourcePath)) {
			System.out.println("FILENAME : "+fileName);
			String targetFileName = fileName.replace(sourcePath.replace("/", "\\"), targetPath);
			System.out.println(targetFileName);
			if(filePatten!=null&&!RegularHelper.isMatcher(fileName, filePatten)) {
				FileHelper.FileCopy(fileName, targetFileName);
				continue;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), charSet));
			File file = FileHelper.createFile(targetFileName);
			OutputStream os = new FileOutputStream(file);
			OutputStreamWriter osw=new OutputStreamWriter(os,charSet);
			String str = null;
			while((str = br.readLine())!=null) {
				if(ismatch(str)) {
					if(findRule == 1) {
						str = regularReplace(str);
					}else {
						str = str.replace(format, targetStr);
					}
				}
				osw.write(str+lineBreak);
			}
			osw.flush();
			osw.close();
			os.flush();
			os.close();
			br.close();
		}
		System.out.println("FINISHED!");
	}
	
	private String regularReplace(String str) {
		Map<Integer, String> matcherStr = RegularHelper.MatcherValues(str, format);
		for(Integer i:matcherStr.keySet()) {
			targetStr = targetStr.replace("\\"+i+"", matcherStr.get(i));
		}
		return targetStr;		
	}
	
	private boolean ismatch(String str) {
		if(findRule == 0) {
			return str.contains(format);
		}
		if(findRule == 1) {
			return RegularHelper.isMatcher(str, format);
		}
		return false;
	}
	
	public String getLineBreak() {
		return lineBreak;
	}

	public void setLineBreak(String lineBreak) {
		this.lineBreak = lineBreak;
	}
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getTargetStr() {
		return targetStr;
	}

	public void setTargetStr(String targetStr) {
		this.targetStr = targetStr;
	}

	public int getFindRule() {
		return findRule;
	}

	public void setFindRule(int findRule) {
		this.findRule = findRule;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public String getFilePatten() {
		return filePatten;
	}

	public void setFilePatten(String filePatten) {
		this.filePatten = filePatten;
	}

	public static void main(String[] args) {
		FileContentReplaceHelper fileChangeHelper = new FileContentReplaceHelper();
		if(fileChangeHelper.getFilePatten().startsWith("")) {
			System.out.println("filePatten is null");
		}
	}

}
