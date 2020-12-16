package com.cloud.lottery.cloud_ip.t.entity;

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
 * The persistent class for the database table cloud_ip 
 * IBM_客户机IP地址的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "cloud_ip")
public class CloudIpT implements Serializable {

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
		
			
//IBM_客户机IP地址主键
@Column(name="CLOUD_IP_ID_")
	private String cloudIpId;
	
	public void setCloudIpId(String cloudIpId) {
		this.cloudIpId=cloudIpId;
	}
	public String getCloudIpId() {
		return this.cloudIpId ;
	}
			
			
//IP
@Column(name="IP_")
	private String ip;
	
	public void setIp(String ip) {
		this.ip=ip;
	}
	public String getIp() {
		return this.ip ;
	}
			
			
//PORT_
@Column(name="PORT_")
	private Integer port;
	
	public void setPort(Integer port) {
		this.port=port;
	}
	public Integer getPort() {
		return this.port ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//开始时间
@Column(name="START_TIME_")
	private Date startTime;
	
	public void setStartTime(Date startTime) {
		this.startTime=startTime;
	}
	public Date getStartTime() {
		return this.startTime ;
	}
			
			
//开始时间数字型
@Column(name="START_TIME_LONG_")
	private Long startTimeLong;
	
	public void setStartTimeLong(Long startTimeLong) {
		this.startTimeLong=startTimeLong;
	}
	public Long getStartTimeLong() {
		return this.startTimeLong ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//结束时间
@Column(name="END_TIME_")
	private Date endTime;
	
	public void setEndTime(Date endTime) {
		this.endTime=endTime;
	}
	public Date getEndTime() {
		return this.endTime ;
	}
			
			
//结束时间数字型
@Column(name="END_TIME_LONG_")
	private Long endTimeLong;
	
	public void setEndTimeLong(Long endTimeLong) {
		this.endTimeLong=endTimeLong;
	}
	public Long getEndTimeLong() {
		return this.endTimeLong ;
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