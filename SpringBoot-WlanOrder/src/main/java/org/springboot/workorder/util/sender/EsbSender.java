package org.springboot.workorder.util.sender;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;
import org.springframework.stereotype.Component;

@Component
public class EsbSender {
	private String esbEndPoint;
	private String esbSystemCode;
	private String esbAuthCode;
	private String esbOperation;
	private ThreadLocal<Integer> ldc=new ThreadLocal<Integer>(){
		@Override
		public Integer initialValue(){
			return 0;
		}
	};	
	public Object send(String serviceCode,String msg) throws Exception {
		Integer times=ldc.get();
		Object result=null;
		Service service=new Service();
		Call call=null;
		call = (Call) service.createCall();
		SOAPHeaderElement serviceEle = new SOAPHeaderElement(new QName("cn.com.boco.HermesService","ServiceCode"),serviceCode);	
		SOAPHeaderElement userEle = new SOAPHeaderElement(new QName("cn.com.boco.HermesService","UserName"),esbSystemCode);
		SOAPHeaderElement authEle = new SOAPHeaderElement(new QName("cn.com.boco.HermesService","AuthCode"),esbAuthCode);
		call.addHeader(serviceEle);
		call.addHeader(userEle);
		call.addHeader(authEle);
		call.setTargetEndpointAddress(esbEndPoint);
		call.setOperation(esbOperation);
		try{
		result=call.invoke(new Object[]{msg});
		}catch(Throwable t){
			Thread.sleep(1000);
			if(times>50){
				return result;
			}
			ldc.set(times+1);
			result=send(serviceCode, msg);
		}finally{
			ldc.remove();
		}
		return result;
	}

	public String getEsbEndPoint() {
		return esbEndPoint;
	}

	public void setEsbEndPoint(String esbEndPoint) {
		this.esbEndPoint = esbEndPoint;
	}

	public String getEsbSystemCode() {
		return esbSystemCode;
	}

	public void setEsbSystemCode(String esbSystemCode) {
		this.esbSystemCode = esbSystemCode;
	}

	public String getEsbAuthCode() {
		return esbAuthCode;
	}

	public void setEsbAuthCode(String esbAuthCode) {
		this.esbAuthCode = esbAuthCode;
	}

	public String getEsbOperation() {
		return esbOperation;
	}

	public void setEsbOperation(String esbOperation) {
		this.esbOperation = esbOperation;
	}
}
