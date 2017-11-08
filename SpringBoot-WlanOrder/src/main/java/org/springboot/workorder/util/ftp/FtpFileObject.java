package org.springboot.workorder.util.ftp;

public class FtpFileObject{
	
	public FtpFileObject(String url) {
		this.url=url;
		parseUrl();
	}
	/**
	 * 
	 */
		
	private int port=21;
	
	private String url;
	
	private String dir;
	
	private String username;
	
	private String password;
	
	private String name;
	
	private boolean passive;
	
	private String host;


	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isPassive() {
		return passive;
	}

	public void setPassive(boolean passive) {
		this.passive = passive;
	}

	public void parseUrl() {
		//   ftp://user:paaword@localhost:21/data/dir/file_001_d.csv
		String[] res = new String[6];
		String[] tps = url.split("://");
		if (tps.length < 2) {
			throw new IllegalArgumentException("url[" + url+ "].");
		}
		res[5] = tps[0].substring(3);
		int namelen = tps[1].indexOf(":");
		if (namelen == -1) {
			throw new IllegalArgumentException("url[" + url+ "].");
		}

		String userName = tps[1].substring(0, namelen);
		String passNPath = tps[1].substring(namelen + 1);
		this.username = userName;

		int passlen = passNPath.lastIndexOf("@");
		if (passlen < 0) {
			throw new IllegalArgumentException("url[" + url+ "].");
		}
		String password = passNPath.substring(0, passlen);
		String filePath = passNPath.substring(passlen + 1);
		this.password = password;
		int pathLen = filePath.indexOf("/");
		if (pathLen == -1)
			throw new IllegalArgumentException("url[" + url+ "].");
		String hostNPort = filePath.substring(0, pathLen);
		String hp = hostNPort;

		String[] hs = hp.split(":");
		if (hs.length < 2) {
			this.host = hp;
		} else {
			this.host = hs[0];
			this.port = Integer.parseInt(hs[1]);
		}
		int slen = filePath.indexOf("/") + 1;
		int elen = filePath.lastIndexOf("/");
		if (slen < elen)
		    	this.dir = filePath.substring(slen, elen);
	    else {
		    this.dir = "";
	    }
		if(!url.endsWith("/")){
			this.name=url.substring(url.lastIndexOf("/")+1);
		}
			
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	
}


