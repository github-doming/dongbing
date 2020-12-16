package all.gen.sys_log_request.t.entity;

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
 * The persistent class for the database table sys_log_request 
 * 后台日志_请求报文SYS_LOG_REQUEST的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_log_request")
public class SysLogRequestT implements Serializable {

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
		
			
//后台日志_请求主键SYS_LOG_REQUEST_ID_
@Column(name="SYS_LOG_REQUEST_ID")
	private String sysLogRequestId;
	
	public void setSysLogRequestId(String sysLogRequestId) {
		this.sysLogRequestId=sysLogRequestId;
	}
	public String getSysLogRequestId() {
		return this.sysLogRequestId ;
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
			
			
//追踪贯穿ID SPAN_ID_
@Column(name="SPAN_ID_")
	private String spanId;
	
	public void setSpanId(String spanId) {
		this.spanId=spanId;
	}
	public String getSpanId() {
		return this.spanId ;
	}
			
			
//追踪贯穿父ID SPAN_PARENT_ID_
@Column(name="SPAN_PARENT_ID_")
	private String spanParentId;
	
	public void setSpanParentId(String spanParentId) {
		this.spanParentId=spanParentId;
	}
	public String getSpanParentId() {
		return this.spanParentId ;
	}
			
			
//用户主键SYS_USER_ID_
@Column(name="SYS_USER_ID_")
	private String sysUserId;
	
	public void setSysUserId(String sysUserId) {
		this.sysUserId=sysUserId;
	}
	public String getSysUserId() {
		return this.sysUserId ;
	}
			
			
//用户名称SYS_USER_NAME_
@Column(name="SYS_USER_NAME_")
	private String sysUserName;
	
	public void setSysUserName(String sysUserName) {
		this.sysUserName=sysUserName;
	}
	public String getSysUserName() {
		return this.sysUserName ;
	}
			
			
//APP用户主键APP_USER_ID_
@Column(name="APP_USER_ID_")
	private String appUserId;
	
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
			
			
//APP用户名称APP_USER_NAME_
@Column(name="APP_USER_NAME_")
	private String appUserName;
	
	public void setAppUserName(String appUserName) {
		this.appUserName=appUserName;
	}
	public String getAppUserName() {
		return this.appUserName ;
	}
			
			
//SERVLET_CONTEXT_PATH_
@Column(name="SERVLET_CONTEXT_PATH_")
	private String servletContextPath;
	
	public void setServletContextPath(String servletContextPath) {
		this.servletContextPath=servletContextPath;
	}
	public String getServletContextPath() {
		return this.servletContextPath ;
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
			
			
//没有解密的数据DATA_
@Column(name="DATA_")
	private String data;
	
	public void setData(String data) {
		this.data=data;
	}
	public String getData() {
		return this.data ;
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
			
			
//IP_
@Column(name="IP_")
	private String ip;
	
	public void setIp(String ip) {
		this.ip=ip;
	}
	public String getIp() {
		return this.ip ;
	}
			
			
//TOMCAT_BIN_
@Column(name="TOMCAT_BIN_")
	private String tomcatBin;
	
	public void setTomcatBin(String tomcatBin) {
		this.tomcatBin=tomcatBin;
	}
	public String getTomcatBin() {
		return this.tomcatBin ;
	}
			
			
//连接ID CONNECTION_ID_
@Column(name="CONNECTION_ID_")
	private String connectionId;
	
	public void setConnectionId(String connectionId) {
		this.connectionId=connectionId;
	}
	public String getConnectionId() {
		return this.connectionId ;
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
			
			
//数字或编号NUMBER_
@Column(name="NUMBER_")
	private Long number;
	
	public void setNumber(Long number) {
		this.number=number;
	}
	public Long getNumber() {
		return this.number ;
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