package com.ibm.old.v1.cloud.ibm_role_resource.w.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_role_resource 
 * IBM_资源和角色关联表IBM_ROLE_RESOURCE的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_role_resource")
public class IbmRoleResourceW implements Serializable {

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
		
			
//资源和角色关联表主键IBM_ROLE_RESOURCE_ID_
@Column(name="IBM_ROLE_RESOURCE_ID_")
	private String ibmRoleResourceId;
	
	public void setIbmRoleResourceId(String ibmRoleResourceId) {
		this.ibmRoleResourceId=ibmRoleResourceId;
	}
	public String getIbmRoleResourceId() {
		return this.ibmRoleResourceId ;
	}
			
			
//角色ID
@Column(name="ROLE_ID_")
	private String roleId;
	
	public void setRoleId(String roleId) {
		this.roleId=roleId;
	}
	public String getRoleId() {
		return this.roleId ;
	}
			
			
//资源ID
@Column(name="RESOURCE_ID_")
	private String resourceId;
	
	public void setResourceId(String resourceId) {
		this.resourceId=resourceId;
	}
	public String getResourceId() {
		return this.resourceId ;
	}
			
			
//资源类型
@Column(name="RESOURCE_TYPE_")
	private String resourceType;
	
	public void setResourceType(String resourceType) {
		this.resourceType=resourceType;
	}
	public String getResourceType() {
		return this.resourceType ;
	}
			
			
//资源信息
@Column(name="RESOURCE_INFO_")
	private String resourceInfo;
	
	public void setResourceInfo(String resourceInfo) {
		this.resourceInfo=resourceInfo;
	}
	public String getResourceInfo() {
		return this.resourceInfo ;
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
			
			
//创建时间数字型
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
			
			
//更新时间数字型
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
			
			
//状态
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