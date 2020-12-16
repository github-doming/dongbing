package all.gen.app_log_response.t.entity;

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
 * The persistent class for the database table app_log_response 
 * APP日志_返回报文APP_LOG_RESPONSE的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "app_log_response")
public class AppLogResponseT implements Serializable {

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
		
			
//APP日志_返回主键APP_LOG_RESPONSE_ID_
@Column(name="APP_LOG_RESPONSE_ID_")
	private String appLogResponseId;
	
	public void setAppLogResponseId(String appLogResponseId) {
		this.appLogResponseId=appLogResponseId;
	}
	public String getAppLogResponseId() {
		return this.appLogResponseId ;
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
			
			
//REQUEST_URL_
@Column(name="REQUEST_URL_")
	private String requestUrl;
	
	public void setRequestUrl(String requestUrl) {
		this.requestUrl=requestUrl;
	}
	public String getRequestUrl() {
		return this.requestUrl ;
	}
			
			
//REQUEST_URI_
@Column(name="REQUEST_URI_")
	private String requestUri;
	
	public void setRequestUri(String requestUri) {
		this.requestUri=requestUri;
	}
	public String getRequestUri() {
		return this.requestUri ;
	}
			
			
//REQUEST_PARAMETER_
@Column(name="REQUEST_PARAMETER_")
	private String requestParameter;
	
	public void setRequestParameter(String requestParameter) {
		this.requestParameter=requestParameter;
	}
	public String getRequestParameter() {
		return this.requestParameter ;
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
			
			
//线程 THREAD_
@Column(name="THREAD_")
	private String thread;
	
	public void setThread(String thread) {
		this.thread=thread;
	}
	public String getThread() {
		return this.thread ;
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
			
		@Temporal( TemporalType.TIMESTAMP)
		
//更新时间
@Column(name="UPDATE_TIME_")
	private Date updateTime;
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}
			
			
//更新时间
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
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