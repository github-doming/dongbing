package com.ibm.old.v1.cloud.ibm_handicap_user.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_handicap_user 
 * vo类
 * @author Robot
 */

public class IbmHandicapUserTVo implements Serializable {

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
	 * IBM_盘口用户主键IBM_盘口用户主键
	 */
	private String ibmHandicapUserId;
	public void setIbmHandicapUserId(String ibmHandicapUserId) {
		this.ibmHandicapUserId=ibmHandicapUserId;
	}
	public String getIbmHandicapUserId() {
		return this.ibmHandicapUserId ;
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
	 * 盘口名称
	 */
	private String handicapName;
	public void setHandicapName(String handicapName) {
		this.handicapName=handicapName;
	}
	public String getHandicapName() {
		return this.handicapName ;
	}
	
	/**
	 * 在线会员主键组
	 */
	private String onlineMembersIds;
	public void setOnlineMembersIds(String onlineMembersIds) {
		this.onlineMembersIds=onlineMembersIds;
	}
	public String getOnlineMembersIds() {
		return this.onlineMembersIds ;
	}
	
	/**
	 * 在线会员数量
	 */
	private String onlineMembersCount;
	public void setOnlineMembersCount(String onlineMembersCount) {
		this.onlineMembersCount=onlineMembersCount;
	}
	public String getOnlineMembersCount() {
		return this.onlineMembersCount ;
	}

	//盘口会员最大在线数量
	private String onlineNumberMax;
	public void setOnlineNumberMax(String onlineNumberMax) {
		this.onlineNumberMax=onlineNumberMax;
	}
	public String getOnlineNumberMax() {
		return this.onlineNumberMax ;
	}
	/**
	 * 使用频次
	 */
	private String frequency;
	public void setFrequency(String frequency) {
		this.frequency=frequency;
	}
	public String getFrequency() {
		return this.frequency ;
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