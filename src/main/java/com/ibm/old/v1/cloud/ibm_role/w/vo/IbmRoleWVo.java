package com.ibm.old.v1.cloud.ibm_role.w.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_role 
 * vo类
 */

public class IbmRoleWVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//IBM_角色主键IBM_ROLE_ID_
	private String ibmRoleId;
	public void setIbmRoleId(String ibmRoleId) {
		this.ibmRoleId=ibmRoleId;
	}
	public String getIbmRoleId() {
		return this.ibmRoleId ;
	}
	
//角色名
	private String roleName;
	public void setRoleName(String roleName) {
		this.roleName=roleName;
	}
	public String getRoleName() {
		return this.roleName ;
	}
	
//角色CODE
	private String roleCode;
	public void setRoleCode(String roleCode) {
		this.roleCode=roleCode;
	}
	public String getRoleCode() {
		return this.roleCode ;
	}
	
//角色等级
	private String roleLevel;
	public void setRoleLevel(String roleLevel) {
		this.roleLevel=roleLevel;
	}
	public String getRoleLevel() {
		return this.roleLevel ;
	}
	
//授权方案ID
	private String planId;
	public void setPlanId(String planId) {
		this.planId=planId;
	}
	public String getPlanId() {
		return this.planId ;
	}
	
//授权盘口ID
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public String getHandicapId() {
		return this.handicapId ;
	}
	
//盘口会员最大在线数量
	private String onlineNumberMax;
	public void setOnlineNumberMax(String onlineNumberMax) {
		this.onlineNumberMax=onlineNumberMax;
	}
	public String getOnlineNumberMax() {
		return this.onlineNumberMax ;
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
	
//创建时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//创建时间
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//状态
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