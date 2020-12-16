package all.gen.test_concurrent_request.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table test_concurrent_request 
 * vo类
 */

public class TestConcurrentRequestTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//测试_并发_请求报文主键TEST_CONCURRENT_REQUEST_ID_
	private String testConcurrentRequestId;
	public void setTestConcurrentRequestId(String testConcurrentRequestId) {
		this.testConcurrentRequestId=testConcurrentRequestId;
	}
	public String getTestConcurrentRequestId() {
		return this.testConcurrentRequestId ;
	}
	
//追踪贯穿父ID SPAN_PARENT_ID_
	private String spanParentId;
	public void setSpanParentId(String spanParentId) {
		this.spanParentId=spanParentId;
	}
	public String getSpanParentId() {
		return this.spanParentId ;
	}
	
//追踪贯穿ID SPAN_ID_
	private String spanId;
	public void setSpanId(String spanId) {
		this.spanId=spanId;
	}
	public String getSpanId() {
		return this.spanId ;
	}
	
//追踪请求或回执TRACE_ID_
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
	
//线程 THREAD_
	private String thread;
	public void setThread(String thread) {
		this.thread=thread;
	}
	public String getThread() {
		return this.thread ;
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


}