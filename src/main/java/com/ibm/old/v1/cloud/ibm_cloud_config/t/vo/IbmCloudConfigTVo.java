package com.ibm.old.v1.cloud.ibm_cloud_config.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_cloud_config 
 * vo类
 * @author Robot
 */

public class IbmCloudConfigTVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 索引
	 */
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
	/**
	 * IBM_中心端配置主键
	 */
	private String ibmCloudConfigId;
	public void setIbmCloudConfigId(String ibmCloudConfigId) {
		this.ibmCloudConfigId=ibmCloudConfigId;
	}
	public String getIbmCloudConfigId() {
		return this.ibmCloudConfigId ;
	}
	
	/**
	 * 中心端配置KEY
	 */
	private String cloudConfigKey;
	public void setCloudConfigKey(String cloudConfigKey) {
		this.cloudConfigKey=cloudConfigKey;
	}
	public String getCloudConfigKey() {
		return this.cloudConfigKey ;
	}
	
	/**
	 * 中心端配置VALUE
	 */
	private String cloudConfigValue;
	public void setCloudConfigValue(String cloudConfigValue) {
		this.cloudConfigValue=cloudConfigValue;
	}
	public String getCloudConfigValue() {
		return this.cloudConfigValue ;
	}
	
	/**
	 * 创建者CREATE_USER_
	 */
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
	}
	
	/**
	 * 创建时间
	 */
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
	/**
	 * 创建时间
	 */
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
	/**
	 * 更新者UPDATE_USER_
	 */
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
	}
	
	/**
	 * 更新内容
	 */
	private String updateText;
	public void setUpdateText(String updateText) {
		this.updateText=updateText;
	}
	public String getUpdateText() {
		return this.updateText ;
	}
	
	/**
	 * 更新时间
	 */
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
	/**
	 * 更新时间
	 */
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
	/**
	 * 描述
	 */
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}
	}