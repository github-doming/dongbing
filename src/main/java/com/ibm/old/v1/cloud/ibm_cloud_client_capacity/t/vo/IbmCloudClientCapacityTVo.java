package com.ibm.old.v1.cloud.ibm_cloud_client_capacity.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_cloud_client_capacity 
 * vo类
 * @author Robot
 */

public class IbmCloudClientCapacityTVo implements Serializable {

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
	 * IBM_中心端客户端容量记录主键
	 */
	private String ibmCloudClientCapacityId;
	public void setIbmCloudClientCapacityId(String ibmCloudClientCapacityId) {
		this.ibmCloudClientCapacityId=ibmCloudClientCapacityId;
	}
	public String getIbmCloudClientCapacityId() {
		return this.ibmCloudClientCapacityId ;
	}
	
	/**
	 * 中心端客户端主键
	 */
	private String cloudClientId;
	public void setCloudClientId(String cloudClientId) {
		this.cloudClientId=cloudClientId;
	}
	public String getCloudClientId() {
		return this.cloudClientId ;
	}
	
	/**
	 * 客户端编码
	 */
	private String clientCode;
	public void setClientCode(String clientCode) {
		this.clientCode=clientCode;
	}
	public String getClientCode() {
		return this.clientCode ;
	}
	
	/**
	 * 客户端最大容量
	 */
	private String clientCapacityMax;
	public void setClientCapacityMax(String clientCapacityMax) {
		this.clientCapacityMax=clientCapacityMax;
	}
	public String getClientCapacityMax() {
		return this.clientCapacityMax ;
	}
	
	/**
	 * 客户端使用容量
	 */
	private String clientCapacity;
	public void setClientCapacity(String clientCapacity) {
		this.clientCapacity=clientCapacity;
	}
	public String getClientCapacity() {
		return this.clientCapacity ;
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