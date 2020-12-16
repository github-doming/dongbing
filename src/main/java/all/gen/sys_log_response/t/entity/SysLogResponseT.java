package all.gen.sys_log_response.t.entity;

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
 * The persistent class for the database table sys_log_response 
 * 后台日志_返回报文SYS_LOG_RESPONSE的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_log_response")
public class SysLogResponseT implements Serializable {

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
		
			
// 后台日志_返回主键SYS_LOG_RESPONSE_ID_
@Column(name="SYS_LOG_RESPONSE_ID_")
	private String sysLogResponseId;
	
	public void setSysLogResponseId(String sysLogResponseId) {
		this.sysLogResponseId=sysLogResponseId;
	}
	public String getSysLogResponseId() {
		return this.sysLogResponseId ;
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
			
			
//追踪贯穿父ID SPAN_PARENT_ID_
@Column(name="SPAN_PARENT_ID_")
	private String spanParentId;
	
	public void setSpanParentId(String spanParentId) {
		this.spanParentId=spanParentId;
	}
	public String getSpanParentId() {
		return this.spanParentId ;
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
			
			
//请求类型REQUEST_TYPE_
@Column(name="REQUEST_TYPE_")
	private String requestType;
	
	public void setRequestType(String requestType) {
		this.requestType=requestType;
	}
	public String getRequestType() {
		return this.requestType ;
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