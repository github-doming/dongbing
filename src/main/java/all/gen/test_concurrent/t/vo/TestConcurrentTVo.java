package all.gen.test_concurrent.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table test_concurrent 
 * vo类
 */

public class TestConcurrentTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//测试_并发主键TEST_CONCURRENT_ID_
	private String testConcurrentId;
	public void setTestConcurrentId(String testConcurrentId) {
		this.testConcurrentId=testConcurrentId;
	}
	public String getTestConcurrentId() {
		return this.testConcurrentId ;
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
	
//用户主键SYS_USER_ID_
	private String sysUserId;
	public void setSysUserId(String sysUserId) {
		this.sysUserId=sysUserId;
	}
	public String getSysUserId() {
		return this.sysUserId ;
	}
	
//用户名称SYS_USER_NAME_
	private String sysUserName;
	public void setSysUserName(String sysUserName) {
		this.sysUserName=sysUserName;
	}
	public String getSysUserName() {
		return this.sysUserName ;
	}
	
//APP用户主键APP_USER_ID_
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
	
//APP用户名称APP_USER_NAME_
	private String appUserName;
	public void setAppUserName(String appUserName) {
		this.appUserName=appUserName;
	}
	public String getAppUserName() {
		return this.appUserName ;
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
	
//IP_
	private String ip;
	public void setIp(String ip) {
		this.ip=ip;
	}
	public String getIp() {
		return this.ip ;
	}
	
//IP_LOCAL_
	private String ipLocal;
	public void setIpLocal(String ipLocal) {
		this.ipLocal=ipLocal;
	}
	public String getIpLocal() {
		return this.ipLocal ;
	}
	
//名称
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//SERVLET_CONTEXT_PATH_
	private String servletContextPath;
	public void setServletContextPath(String servletContextPath) {
		this.servletContextPath=servletContextPath;
	}
	public String getServletContextPath() {
		return this.servletContextPath ;
	}
	
//TOMCAT_BIN_
	private String tomcatBin;
	public void setTomcatBin(String tomcatBin) {
		this.tomcatBin=tomcatBin;
	}
	public String getTomcatBin() {
		return this.tomcatBin ;
	}
	
//连接ID CONNECTION_ID_
	private String connectionId;
	public void setConnectionId(String connectionId) {
		this.connectionId=connectionId;
	}
	public String getConnectionId() {
		return this.connectionId ;
	}
	
//线程 THREAD_
	private String thread;
	public void setThread(String thread) {
		this.thread=thread;
	}
	public String getThread() {
		return this.thread ;
	}
	
//MACHINE_KEY_
	private String machineKey;
	public void setMachineKey(String machineKey) {
		this.machineKey=machineKey;
	}
	public String getMachineKey() {
		return this.machineKey ;
	}
	
//MAC_
	private String mac;
	public void setMac(String mac) {
		this.mac=mac;
	}
	public String getMac() {
		return this.mac ;
	}
	
//数字或编号NUMBER_
	private String number;
	public void setNumber(String number) {
		this.number=number;
	}
	public String getNumber() {
		return this.number ;
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
	
//名称2NAME2_
	private String name2;
	public void setName2(String name2) {
		this.name2=name2;
	}
	public String getName2() {
		return this.name2 ;
	}
	
//状态2STATE2_
	private String state2;
	public void setState2(String state2) {
		this.state2=state2;
	}
	public String getState2() {
		return this.state2 ;
	}
	
//数字或编号2NUMBER2_
	private String number2;
	public void setNumber2(String number2) {
		this.number2=number2;
	}
	public String getNumber2() {
		return this.number2 ;
	}


}