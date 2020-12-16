package com.ibm.old.v1.cloud.ibm_rep_available_time.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_rep_available_time 
 * vo类
 * @author Robot
 */

public class IbmRepAvailableTimeTVo implements Serializable {

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
	 * IBM报表_可用时长情况主键
	 */
	private String ibmRepAvailableTimeId;
	public void setIbmRepAvailableTimeId(String ibmRepAvailableTimeId) {
		this.ibmRepAvailableTimeId=ibmRepAvailableTimeId;
	}
	public String getIbmRepAvailableTimeId() {
		return this.ibmRepAvailableTimeId ;
	}
	
	/**
	 * 报表_积分主键
	 */
	private String repPointId;
	public void setRepPointId(String repPointId) {
		this.repPointId=repPointId;
	}
	public String getRepPointId() {
		return this.repPointId ;
	}
	
	/**
	 * 上一次主键
	 */
	private String preId;
	public void setPreId(String preId) {
		this.preId=preId;
	}
	public String getPreId() {
		return this.preId ;
	}
	
	/**
	 * 用户主键USER_ID_
	 */
	private String userId;
	public void setUserId(String userId) {
		this.userId=userId;
	}
	public String getUserId() {
		return this.userId ;
	}
	
	/**
	 * 本次消耗积分
	 */
	private String usedPointT;
	public void setUsedPointT(String usedPointT) {
		this.usedPointT=usedPointT;
	}
	public String getUsedPointT() {
		return this.usedPointT ;
	}
	
	/**
	 * 增加时长数字型
	 */
	private String addTimeLong;
	public void setAddTimeLong(String addTimeLong) {
		this.addTimeLong=addTimeLong;
	}
	public String getAddTimeLong() {
		return this.addTimeLong ;
	}
	
	/**
	 * 开始时间START_TIME_
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
	 * 结束时间END_TIME_
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
	 * 原结束时间
	 */
	private String repEndTime;
	public void setRepEndTime(String repEndTime) {
		this.repEndTime=repEndTime;
	}
	public String getRepEndTime() {
		return this.repEndTime ;
	}
	
	/**
	 * 原结束时间数字型
	 */
	private String repEndTimeLong;
	public void setRepEndTimeLong(String repEndTimeLong) {
		this.repEndTimeLong=repEndTimeLong;
	}
	public String getRepEndTimeLong() {
		return this.repEndTimeLong ;
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