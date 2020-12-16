package com.ibm.old.v1.cloud.ibm_cloud_client.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_cloud_client 
 * vo类
 * @author Robot
 */

public class IbmCloudClientTVo implements Serializable {

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
	 * IBM_中心端客户端主键
	 */
	private String ibmCloudClientId;
	public void setIbmCloudClientId(String ibmCloudClientId) {
		this.ibmCloudClientId=ibmCloudClientId;
	}
	public String getIbmCloudClientId() {
		return this.ibmCloudClientId ;
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
	 * 客户机IP地址主键
	 */
	private String clientIpId;
	public void setClientIpId(String clientIpId) {
		this.clientIpId=clientIpId;
	}
	public String getClientIpId() {
		return this.clientIpId ;
	}
	
	/**
	 * IP
	 */
	private String ip;
	public void setIp(String ip) {
		this.ip=ip;
	}
	public String getIp() {
		return this.ip ;
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