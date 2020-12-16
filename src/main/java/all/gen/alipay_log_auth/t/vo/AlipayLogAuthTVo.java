package all.gen.alipay_log_auth.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table alipay_log_auth 
 * vo类
 */

public class AlipayLogAuthTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//支付宝支付日志_异步_授权主键ALIPAY_LOG_AUTH_ID_
	private String alipayLogNotifyId;
	public void setAlipayLogNotifyId(String alipayLogNotifyId) {
		this.alipayLogNotifyId=alipayLogNotifyId;
	}
	public String getAlipayLogNotifyId() {
		return this.alipayLogNotifyId ;
	}
	
//跟踪请求或回执TRACE_ID_
	private String traceId;
	public void setTraceId(String traceId) {
		this.traceId=traceId;
	}
	public String getTraceId() {
		return this.traceId ;
	}
	
//JSON_
	private String json;
	public void setJson(String json) {
		this.json=json;
	}
	public String getJson() {
		return this.json ;
	}
	
//没有解密的数据DATA_
	private String data;
	public void setData(String data) {
		this.data=data;
	}
	public String getData() {
		return this.data ;
	}
	
//REQUEST_URL_
	private String requestUrl;
	public void setRequestUrl(String requestUrl) {
		this.requestUrl=requestUrl;
	}
	public String getRequestUrl() {
		return this.requestUrl ;
	}
	
//REQUEST_URI_
	private String requestUri;
	public void setRequestUri(String requestUri) {
		this.requestUri=requestUri;
	}
	public String getRequestUri() {
		return this.requestUri ;
	}
	
//REQUEST_PARAMETER_
	private String requestParameter;
	public void setRequestParameter(String requestParameter) {
		this.requestParameter=requestParameter;
	}
	public String getRequestParameter() {
		return this.requestParameter ;
	}
	
//创建时间
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//更新时间
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
//描述
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}
	
//应用ID APP_ID_
	private String appId;
	public void setAppId(String appId) {
		this.appId=appId;
	}
	public String getAppId() {
		return this.appId ;
	}
	
//应用一次授权码APP_AUTH_CODE_
	private String appAuthCode;
	public void setAppAuthCode(String appAuthCode) {
		this.appAuthCode=appAuthCode;
	}
	public String getAppAuthCode() {
		return this.appAuthCode ;
	}
	
//SOURCE_
	private String source;
	public void setSource(String source) {
		this.source=source;
	}
	public String getSource() {
		return this.source ;
	}
	
//APPLICATION_TYPE_
	private String applicationType;
	public void setApplicationType(String applicationType) {
		this.applicationType=applicationType;
	}
	public String getApplicationType() {
		return this.applicationType ;
	}


}