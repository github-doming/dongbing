package com.ibm.old.v1.cloud.ibm_available_time.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_available_time 
 * vo类
 * @author Robot
 */

public class IbmAvailableTimeTVo implements Serializable {

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
	 * IBM_可用时长主键
	 */
	private String ibmAvailableTimeId;
	public void setIbmAvailableTimeId(String ibmAvailableTimeId) {
		this.ibmAvailableTimeId=ibmAvailableTimeId;
	}
	public String getIbmAvailableTimeId() {
		return this.ibmAvailableTimeId ;
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
	 * 末次报表主键
	 */
	private String repAvailableTimeId;
	public void setRepAvailableTimeId(String repAvailableTimeId) {
		this.repAvailableTimeId=repAvailableTimeId;
	}
	public String getRepAvailableTimeId() {
		return this.repAvailableTimeId ;
	}
	
	/**
	 * 开始时间
	 */
	private String startTime;
	public void setStartTime(String startTime) {
		this.startTime=startTime;
	}
	public String getStartTime() {
		return this.startTime ;
	}
	
	/**
	 * 开始时间数字型
	 */
	private String startTimeLong;
	public void setStartTimeLong(String startTimeLong) {
		this.startTimeLong=startTimeLong;
	}
	public String getStartTimeLong() {
		return this.startTimeLong ;
	}
	
	/**
	 * 结束时间
	 */
	private String endTime;
	public void setEndTime(String endTime) {
		this.endTime=endTime;
	}
	public String getEndTime() {
		return this.endTime ;
	}
	
	/**
	 * 结束时间数字型
	 */
	private String endTimeLong;
	public void setEndTimeLong(String endTimeLong) {
		this.endTimeLong=endTimeLong;
	}
	public String getEndTimeLong() {
		return this.endTimeLong ;
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