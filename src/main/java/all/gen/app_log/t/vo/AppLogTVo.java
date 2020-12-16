package all.gen.app_log.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table app_log 
 * vo类
 */

public class AppLogTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//APP日志APP_LOG_ID_
	private String appLogId;
	public void setAppLogId(String appLogId) {
		this.appLogId=appLogId;
	}
	public String getAppLogId() {
		return this.appLogId ;
	}
	
//名称
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
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
	
//网络协议NETWORK_PROTOCOL_
	private String networkProtocol;
	public void setNetworkProtocol(String networkProtocol) {
		this.networkProtocol=networkProtocol;
	}
	public String getNetworkProtocol() {
		return this.networkProtocol ;
	}
	
//SERVLET_PATH_
	private String servletPath;
	public void setServletPath(String servletPath) {
		this.servletPath=servletPath;
	}
	public String getServletPath() {
		return this.servletPath ;
	}
	
//IP_
	private String ip;
	public void setIp(String ip) {
		this.ip=ip;
	}
	public String getIp() {
		return this.ip ;
	}
	
//线程 THREAD_
	private String thread;
	public void setThread(String thread) {
		this.thread=thread;
	}
	public String getThread() {
		return this.thread ;
	}
	
//物理地址MAC_
	private String mac;
	public void setMac(String mac) {
		this.mac=mac;
	}
	public String getMac() {
		return this.mac ;
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
	
//日志级别LOG_LEVEL_
	private String logLevel;
	public void setLogLevel(String logLevel) {
		this.logLevel=logLevel;
	}
	public String getLogLevel() {
		return this.logLevel ;
	}
	
//日志内容LOG_CONTENT_
	private String logContent;
	public void setLogContent(String logContent) {
		this.logContent=logContent;
	}
	public String getLogContent() {
		return this.logContent ;
	}
	
//日志异常LOG_ERROR_
	private String logError;
	public void setLogError(String logError) {
		this.logError=logError;
	}
	public String getLogError() {
		return this.logError ;
	}
	
//请求类型REQUEST_TYPE_
	private String requestType;
	public void setRequestType(String requestType) {
		this.requestType=requestType;
	}
	public String getRequestType() {
		return this.requestType ;
	}
	
//日志项目名称PROJECT_NAME_
	private String projectName;
	public void setProjectName(String projectName) {
		this.projectName=projectName;
	}
	public String getProjectName() {
		return this.projectName ;
	}
	
//日志模块名称MODULAR_NAME_
	private String modularName;
	public void setModularName(String modularName) {
		this.modularName=modularName;
	}
	public String getModularName() {
		return this.modularName ;
	}
	
//日志请求功能编码REQUEST_FUNCTION_CODE_
	private String requestFunctionCode;
	public void setRequestFunctionCode(String requestFunctionCode) {
		this.requestFunctionCode=requestFunctionCode;
	}
	public String getRequestFunctionCode() {
		return this.requestFunctionCode ;
	}
	
//日志请求功能名称REQUEST_FUNCTION_NAME_
	private String requestFunctionName;
	public void setRequestFunctionName(String requestFunctionName) {
		this.requestFunctionName=requestFunctionName;
	}
	public String getRequestFunctionName() {
		return this.requestFunctionName ;
	}
	
//日志返回功能编码RESPONSE_FUNCTION_CODE_
	private String responseFunctionCode;
	public void setResponseFunctionCode(String responseFunctionCode) {
		this.responseFunctionCode=responseFunctionCode;
	}
	public String getResponseFunctionCode() {
		return this.responseFunctionCode ;
	}
	
//日志返回功能名称RESPONSE_FUNCTION_NAME_
	private String responseFunctionName;
	public void setResponseFunctionName(String responseFunctionName) {
		this.responseFunctionName=responseFunctionName;
	}
	public String getResponseFunctionName() {
		return this.responseFunctionName ;
	}
	
//开发者名称DEVELOPER_NAME_
	private String developerName;
	public void setDeveloperName(String developerName) {
		this.developerName=developerName;
	}
	public String getDeveloperName() {
		return this.developerName ;
	}


}