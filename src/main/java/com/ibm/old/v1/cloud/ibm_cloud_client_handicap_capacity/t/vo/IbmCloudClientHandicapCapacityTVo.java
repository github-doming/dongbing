package com.ibm.old.v1.cloud.ibm_cloud_client_handicap_capacity.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_cloud_client_handicap_capacity 
 * vo类
 * @author Robot
 */

public class IbmCloudClientHandicapCapacityTVo implements Serializable {

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
	 * IBM_中心端客户端盘口主键
	 */
	private String ibmCloudClientHandicapCapacityId;
	public void setIbmCloudClientHandicapCapacityId(String ibmCloudClientHandicapCapacityId) {
		this.ibmCloudClientHandicapCapacityId=ibmCloudClientHandicapCapacityId;
	}
	public String getIbmCloudClientHandicapCapacityId() {
		return this.ibmCloudClientHandicapCapacityId ;
	}
	
	/**
	 * 中心端客户端容量记录主键
	 */
	private String cloudClientCapacityId;
	public void setCloudClientCapacityId(String cloudClientCapacityId) {
		this.cloudClientCapacityId=cloudClientCapacityId;
	}
	public String getCloudClientCapacityId() {
		return this.cloudClientCapacityId ;
	}
	
	/**
	 * IBM_中心端配置主键
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
	 * 盘口主键
	 */
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public String getHandicapId() {
		return this.handicapId ;
	}
	
	/**
	 * 盘口编码
	 */
	private String handicapCode;
	public void setHandicapCode(String handicapCode) {
		this.handicapCode=handicapCode;
	}
	public String getHandicapCode() {
		return this.handicapCode ;
	}
	
	/**
	 * 最大容量
	 */
	private String capacityMax;
	public void setCapacityMax(String capacityMax) {
		this.capacityMax=capacityMax;
	}
	public String getCapacityMax() {
		return this.capacityMax ;
	}
	
	/**
	 * 使用容量
	 */
	private String capacity;
	public void setCapacity(String capacity) {
		this.capacity=capacity;
	}
	public String getCapacity() {
		return this.capacity ;
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