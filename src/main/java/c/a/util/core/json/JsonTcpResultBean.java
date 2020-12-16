package c.a.util.core.json;
/**
 * 返回的Json格式字符串,包括是否成功的状态及返回的格式字符串
 * 
 * @author cxy
 * @Email:
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class JsonTcpResultBean {
	// 追踪ID
	private String traceId = null;
	// 所有用户的数据
	private Object data = null;
	// 自己的数据
	private Object dataSelf = null;
	private String url;
	private String urlSend;
	private String token;
	private String appUserId;
	private String channelId;
	protected boolean success = false;
	protected String code = null;
	protected String msg = null;
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getDataSelf() {
		return dataSelf;
	}
	public void setDataSelf(Object dataSelf) {
		this.dataSelf = dataSelf;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlSend() {
		return urlSend;
	}
	public void setUrlSend(String urlSend) {
		this.urlSend = urlSend;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}