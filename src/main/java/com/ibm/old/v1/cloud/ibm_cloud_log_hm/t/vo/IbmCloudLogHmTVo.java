package com.ibm.old.v1.cloud.ibm_cloud_log_hm.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_cloud_log_hm 
 * vo类
 * @author Robot
 */

public class IbmCloudLogHmTVo implements Serializable {

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
	 * IBM_中心端盘口会员日志主键IBM_CLOUD_LOG_HM_ID_
	 */
	private String ibmCloudLogHmId;
	public void setIbmCloudLogHmId(String ibmCloudLogHmId) {
		this.ibmCloudLogHmId=ibmCloudLogHmId;
	}
	public String getIbmCloudLogHmId() {
		return this.ibmCloudLogHmId ;
	}
	
	/**
	 * 客户端已存在盘口会员主键CLIENT_EXIST_HM_ID_
	 */
	private String clientExistHmId;
	public void setClientExistHmId(String clientExistHmId) {
		this.clientExistHmId=clientExistHmId;
	}
	public String getClientExistHmId() {
		return this.clientExistHmId ;
	}
	
	/**
	 * 盘口会员主键HANDICAP_MEMBER_ID_
	 */
	private String handicapMemberId;
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
	}
	
	/**
	 * 盘口主键HANDICAP_ID_
	 */
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public String getHandicapId() {
		return this.handicapId ;
	}
	
	/**
	 * 用户主键APP_USER_ID_
	 */
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
	
	/**
	 * 日志类型LOG_TYPE_
	 */
	private String logType;
	public void setLogType(String logType) {
		this.logType=logType;
	}
	public String getLogType() {
		return this.logType ;
	}
	
	/**
	 * 日志级别LOG_LEVEL_
	 */
	private String logLevel;
	public void setLogLevel(String logLevel) {
		this.logLevel=logLevel;
	}
	public String getLogLevel() {
		return this.logLevel ;
	}
	
	/**
	 * 日志内容LOG_CONTENT_
	 */
	private String logContent;
	public void setLogContent(String logContent) {
		this.logContent=logContent;
	}
	public String getLogContent() {
		return this.logContent ;
	}
	
	/**
	 * 创建时间CREATE_TIME_
	 */
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
	/**
	 * 创建时间数字型CREATE_TIME_LONG_
	 */
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
	/**
	 * 更新时间UPDATE_TIME_
	 */
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
	/**
	 * 更新时间数字型UPDATE_TIME_LONG_
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