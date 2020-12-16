package com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_cloud_client_hm 
 * vo类
 * @author Robot
 */

public class IbmCloudClientHmTVo implements Serializable {

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
	 * IBM_中心端客户端盘口用户主键
	 */
	private String ibmCloudClientHmId;
	public void setIbmCloudClientHmId(String ibmCloudClientHmId) {
		this.ibmCloudClientHmId=ibmCloudClientHmId;
	}
	public String getIbmCloudClientHmId() {
		return this.ibmCloudClientHmId ;
	}
	
	/**
	 * 中心端客户端盘口容量记录主键
	 */
	private String cloudClientHandicapCapacityId;
	public void setCloudClientHandicapCapacityId(String cloudClientHandicapCapacityId) {
		this.cloudClientHandicapCapacityId=cloudClientHandicapCapacityId;
	}
	public String getCloudClientHandicapCapacityId() {
		return this.cloudClientHandicapCapacityId ;
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
	 * 客户端已存在盘口会员主键
	 */
	private String clientExistHmId;
	public void setClientExistHmId(String clientExistHmId) {
		this.clientExistHmId=clientExistHmId;
	}
	public String getClientExistHmId() {
		return this.clientExistHmId ;
	}
	
	/**
	 * 盘口会员主键
	 */
	private String handicapMemberId;
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
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
	 * 用户主键
	 */
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
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