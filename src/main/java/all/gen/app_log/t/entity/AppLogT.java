package all.gen.app_log.t.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import c.a.util.core.annotation.AnnotationEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import c.a.util.core.annotation.AnnotationTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the database table app_log 
 * APP日志APP_LOG的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "app_log")
public class AppLogT implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
//索引
@Column(name="IDX_")
	private Long idx;
	
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}
		@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//APP日志APP_LOG_ID_
@Column(name="APP_LOG_ID_")
	private String appLogId;
	
	public void setAppLogId(String appLogId) {
		this.appLogId=appLogId;
	}
	public String getAppLogId() {
		return this.appLogId ;
	}
			
			
//名称
@Column(name="NAME_")
	private String name;
	
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
			
			
//追踪请求或回执TRACE_ID_
@Column(name="TRACE_ID_")
	private String traceId;
	
	public void setTraceId(String traceId) {
		this.traceId=traceId;
	}
	public String getTraceId() {
		return this.traceId ;
	}
			
			
//JSON_
@Column(name="JSON_")
	private String json;
	
	public void setJson(String json) {
		this.json=json;
	}
	public String getJson() {
		return this.json ;
	}
			
			
//网络协议NETWORK_PROTOCOL_
@Column(name="NETWORK_PROTOCOL_")
	private String networkProtocol;
	
	public void setNetworkProtocol(String networkProtocol) {
		this.networkProtocol=networkProtocol;
	}
	public String getNetworkProtocol() {
		return this.networkProtocol ;
	}
			
			
//SERVLET_PATH_
@Column(name="SERVLET_PATH_")
	private String servletPath;
	
	public void setServletPath(String servletPath) {
		this.servletPath=servletPath;
	}
	public String getServletPath() {
		return this.servletPath ;
	}
			
			
//IP_
@Column(name="IP_")
	private String ip;
	
	public void setIp(String ip) {
		this.ip=ip;
	}
	public String getIp() {
		return this.ip ;
	}
			
			
//线程 THREAD_
@Column(name="THREAD_")
	private String thread;
	
	public void setThread(String thread) {
		this.thread=thread;
	}
	public String getThread() {
		return this.thread ;
	}
			
			
//物理地址MAC_
@Column(name="MAC_")
	private String mac;
	
	public void setMac(String mac) {
		this.mac=mac;
	}
	public String getMac() {
		return this.mac ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}
			
			
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
@Column(name="STATE_")
	private String state;
	
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
			
			
//描述
@Column(name="DESC_")
	private String desc;
	
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}
			
			
//日志级别LOG_LEVEL_
@Column(name="LOG_LEVEL_")
	private String logLevel;
	
	public void setLogLevel(String logLevel) {
		this.logLevel=logLevel;
	}
	public String getLogLevel() {
		return this.logLevel ;
	}
			
			
//日志内容LOG_CONTENT_
@Column(name="LOG_CONTENT_")
	private String logContent;
	
	public void setLogContent(String logContent) {
		this.logContent=logContent;
	}
	public String getLogContent() {
		return this.logContent ;
	}
			
			
//日志异常LOG_ERROR_
@Column(name="LOG_ERROR_")
	private String logError;
	
	public void setLogError(String logError) {
		this.logError=logError;
	}
	public String getLogError() {
		return this.logError ;
	}
			
			
//请求类型REQUEST_TYPE_
@Column(name="REQUEST_TYPE_")
	private String requestType;
	
	public void setRequestType(String requestType) {
		this.requestType=requestType;
	}
	public String getRequestType() {
		return this.requestType ;
	}
			
			
//日志项目名称PROJECT_NAME_
@Column(name="PROJECT_NAME_")
	private String projectName;
	
	public void setProjectName(String projectName) {
		this.projectName=projectName;
	}
	public String getProjectName() {
		return this.projectName ;
	}
			
			
//日志模块名称MODULAR_NAME_
@Column(name="MODULAR_NAME_")
	private String modularName;
	
	public void setModularName(String modularName) {
		this.modularName=modularName;
	}
	public String getModularName() {
		return this.modularName ;
	}
			
			
//日志请求功能编码REQUEST_FUNCTION_CODE_
@Column(name="REQUEST_FUNCTION_CODE_")
	private String requestFunctionCode;
	
	public void setRequestFunctionCode(String requestFunctionCode) {
		this.requestFunctionCode=requestFunctionCode;
	}
	public String getRequestFunctionCode() {
		return this.requestFunctionCode ;
	}
			
			
//日志请求功能名称REQUEST_FUNCTION_NAME_
@Column(name="REQUEST_FUNCTION_NAME_")
	private String requestFunctionName;
	
	public void setRequestFunctionName(String requestFunctionName) {
		this.requestFunctionName=requestFunctionName;
	}
	public String getRequestFunctionName() {
		return this.requestFunctionName ;
	}
			
			
//日志返回功能编码RESPONSE_FUNCTION_CODE_
@Column(name="RESPONSE_FUNCTION_CODE_")
	private String responseFunctionCode;
	
	public void setResponseFunctionCode(String responseFunctionCode) {
		this.responseFunctionCode=responseFunctionCode;
	}
	public String getResponseFunctionCode() {
		return this.responseFunctionCode ;
	}
			
			
//日志返回功能名称RESPONSE_FUNCTION_NAME_
@Column(name="RESPONSE_FUNCTION_NAME_")
	private String responseFunctionName;
	
	public void setResponseFunctionName(String responseFunctionName) {
		this.responseFunctionName=responseFunctionName;
	}
	public String getResponseFunctionName() {
		return this.responseFunctionName ;
	}
			
			
//开发者名称DEVELOPER_NAME_
@Column(name="DEVELOPER_NAME_")
	private String developerName;
	
	public void setDeveloperName(String developerName) {
		this.developerName=developerName;
	}
	public String getDeveloperName() {
		return this.developerName ;
	}

	private String tableNameMy;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}