package com.ibm.old.v1.cloud.ibm_role.w.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_role 
 * IBM_角色IBM_ROLE的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_role")
public class IbmRoleW implements Serializable {

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
		
			
//IBM_角色主键IBM_ROLE_ID_
@Column(name="IBM_ROLE_ID_")
	private String ibmRoleId;
	
	public void setIbmRoleId(String ibmRoleId) {
		this.ibmRoleId=ibmRoleId;
	}
	public String getIbmRoleId() {
		return this.ibmRoleId ;
	}
			
			
//角色名
@Column(name="ROLE_NAME_")
	private String roleName;
	
	public void setRoleName(String roleName) {
		this.roleName=roleName;
	}
	public String getRoleName() {
		return this.roleName ;
	}
			
			
//角色CODE
@Column(name="ROLE_CODE_")
	private String roleCode;
	
	public void setRoleCode(String roleCode) {
		this.roleCode=roleCode;
	}
	public String getRoleCode() {
		return this.roleCode ;
	}
			
			
//角色等级
@Column(name="ROLE_LEVEL_")
	private Integer roleLevel;
	
	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel=roleLevel;
	}
	public Integer getRoleLevel() {
		return this.roleLevel ;
	}
			
			
//授权方案ID
@Column(name="PLAN_ID_")
	private String planId;
	
	public void setPlanId(String planId) {
		this.planId=planId;
	}
	public String getPlanId() {
		return this.planId ;
	}
			
			
//授权盘口ID
@Column(name="HANDICAP_ID_")
	private String handicapId;
	
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public String getHandicapId() {
		return this.handicapId ;
	}
			
			
//盘口会员最大在线数量
@Column(name="ONLINE_NUMBER_MAX_")
	private Integer onlineNumberMax;
	
	public void setOnlineNumberMax(Integer onlineNumberMax) {
		this.onlineNumberMax=onlineNumberMax;
	}
	public Integer getOnlineNumberMax() {
		return this.onlineNumberMax ;
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
		
//创建时间
@Column(name="UPDATE_TIME_")
	private Date updateTime;
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}
			
			
//创建时间
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