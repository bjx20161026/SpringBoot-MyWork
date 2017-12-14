package main;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpTest {
	
	public static void main(String[] args) throws Exception {
		String urlNameString = "http://localhost:9002/esbTransMr/msg";
		String content_Type = "application/json;charset=UTF-8";
		String body = "<fileInfo><ftpInfo><Type>update</Type><DataCatalog>MR.RIPPRB</DataCatalog><WorkMode>sync</WorkMode><SystemID>GCNI</SystemID><SessionID>20171122_11511237843</SessionID><MsgSerial>1511319737642</MsgSerial><DeliveryTime>2017-11-22 11:02:17</DeliveryTime><ReadyStatusCode>1</ReadyStatusCode><ConnectionString>ftp://GCP_PUT:W1n3m5s#@10.221.232.135:21</ConnectionString><Path>GCP_APP/DATA.PM.OPT.MR.O_NOKIA_ENODEB_NOKIA_4G_LTE_MR_OMC1_MR_MR.RIPPRB.GCP_APP</Path><userName>PAS_GET</userName><password>W1n3m5s#</password><FileList>O_Nokia_ENODEB_Nokia-4GMR-NXTDLOMCR_MR_MR.RIPPRB_133781_15_20171122-1015.xml.gz</FileList><files><file><fileName>O_Nokia_ENODEB_Nokia-4GMR-NXTDLOMCR_MR_MR.RIPPRB_133781_15_20171122-1015.xml.gz</fileName><FileFormat>xml.gz</FileFormat><FileSize>10484969</FileSize><IsEncryption>false</IsEncryption><CipherKey></CipherKey><CipherFile></CipherFile><IsCompressed>true</IsCompressed><CompressKey></CompressKey><CharSet>utf-8</CharSet><DataInfo></DataInfo><FieldSeparator></FieldSeparator><LineSeparator></LineSeparator><XmlSchema></XmlSchema><FileCheckInfo></FileCheckInfo></file></files></ftpInfo></fileInfo>";
		
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		PrintWriter out = null;
		URL realUrl = new URL(urlNameString);
		URLConnection connection = realUrl.openConnection();
		//connection.setRequestProperty("User-Agent", request.getHeader("User-Agent"));
		connection.setRequestProperty("Content-Type", content_Type);
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.connect();
		out = new PrintWriter(connection.getOutputStream());
		out.print(body);
		out.flush();
		InputStream is = connection.getInputStream();
		String result = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		System.out.println(result);
		System.out.println("2343432");
	}

}
