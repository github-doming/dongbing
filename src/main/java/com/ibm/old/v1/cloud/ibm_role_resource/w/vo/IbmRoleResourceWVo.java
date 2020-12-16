package com.ibm.old.v1.cloud.ibm_role_resource.w.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_role_resource 
 * vo类
 */

public class IbmRoleResourceWVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//资源和角色关联表主键IBM_ROLE_RESOURCE_ID_
	private String ibmRoleResourceId;
	public void setIbmRoleResourceId(String ibmRoleResourceId) {
		this.ibmRoleResourceId=ibmRoleResourceId;
	}
	public String getIbmRoleResourceId() {
		return this.ibmRoleResourceId ;
	}
	
//角色ID
	private String roleId;
	public void setRoleId(String roleId) {
		this.roleId=roleId;
	}
	public String getRoleId() {
		return this.roleId ;
	}
	
//资源ID
	private String resourceId;
	public void setResourceId(String resourceId) {
		this.resourceId=resourceId;
	}
	public String getResourceId() {
		return this.resourceId ;
	}
	
//资源类型
	private String resourceType;
	public void setResourceType(String resourceType) {
		this.resourceType=resourceType;
	}
	public String getResourceType() {
		return this.resourceType ;
	}
	
//资源信息
	private String resourceInfo;
	public void setResourceInfo(String resourceInfo) {
		this.resourceInfo=resourceInfo;
	}
	public String getResourceInfo() {
		return this.resourceInfo ;
	}
	
//创建时间
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间数字型
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
	
//更新时间数字型
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