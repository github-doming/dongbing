package com.ibm.common.test.wwj.handicap.logger;
/**
 * @Description: http 请求 日志
 * @Author: Dongming
 * @Date: 2019-12-07 10:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpRequestLogger {
	private String url;
	private String parameter;
	private String html;
	private Integer state;
	private String location;

	public HttpRequestLogger(String url, String parameter, String html) {
		this.url = url;
		this.parameter = parameter;
		this.html = html;
		location = "";
	}
	public String getUrl() {
		return url;
	}
	protected void setUrl(String url) {
		this.url = url;
	}
	public String getParameter() {
		return parameter;
	}
	protected void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getHtml() {
		return html;
	}
	protected void setHtml(String html) {
		this.html = html;
	}
	public Integer getState() {
		return state;
	}
	protected void setState(Integer state) {
		this.state = state;
	}
	public String getLocation() {
		return location;
	}
	protected void setLocation(String location) {
		this.location = location;
	}

	protected void result(String html, int status, String location) {
		setHtml(html);
		setState(status);
		setLocation(location);
	}
}
