package com.ibm.old.v1.cloud.ibm_rep_point.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_rep_point 
 * vo类
 * @author Robot
 */

public class IbmRepPointTVo implements Serializable {

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
	 * IBM报表_点数主键
	 */
	private String ibmRepPointId;
	public void setIbmRepPointId(String ibmRepPointId) {
		this.ibmRepPointId=ibmRepPointId;
	}
	public String getIbmRepPointId() {
		return this.ibmRepPointId ;
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
	 * 标题
	 */
	private String title;
	public void setTitle(String title) {
		this.title=title;
	}
	public String getTitle() {
		return this.title ;
	}
	
	/**
	 * 增减之前余额
	 */
	private String preT;
	public void setPreT(String preT) {
		this.preT=preT;
	}
	public String getPreT() {
		return this.preT ;
	}
	
	/**
	 * 增减数字
	 */
	private String numberT;
	public void setNumberT(String numberT) {
		this.numberT=numberT;
	}
	public String getNumberT() {
		return this.numberT ;
	}
	
	/**
	 * 余额
	 */
	private String balanceT;
	public void setBalanceT(String balanceT) {
		this.balanceT=balanceT;
	}
	public String getBalanceT() {
		return this.balanceT ;
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