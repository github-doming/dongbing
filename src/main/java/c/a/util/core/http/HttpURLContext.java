package c.a.util.core.http;

/**
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class HttpURLContext {
	private String JSESSIONID = null;
	private String content;
	private byte[] arrayByte;
	private String respCode;
	public String getJSESSIONID() {
		return JSESSIONID;
	}

	public void setJSESSIONID(String jSESSIONID) {
		JSESSIONID = jSESSIONID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String result) {
		this.content = result;
	}

	public byte[] getArrayByte() {
		return arrayByte;
	}

	public void setArrayByte(byte[] arrayByte) {
		this.arrayByte = arrayByte;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	
	
}
