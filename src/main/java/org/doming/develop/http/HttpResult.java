package org.doming.develop.http;
/**
 * @Description: http请求结果类
 * @Author: Dongming
 * @Date: 2019-12-07 10:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpResult {
	String html;
	int statusCode;
	String location;
	Object data;

	public HttpResult() {
	}
	public HttpResult(String html, int statusCode, String location) {
		this.html = html;
		this.statusCode = statusCode;
		this.location = location;
	}

	public String getHtml() {
		return html;
	}
	protected void setHtml(String html) {
		this.html = html;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	protected void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getLocation() {
		return location;
	}
	protected void setLocation(String location) {
		this.location = location;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
