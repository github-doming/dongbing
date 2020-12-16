package com.ibm.old.v1.cloud.ibm_point.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_point 
 * vo类
 * @author Robot
 */

public class IbmPointTVo implements Serializable {

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
	 * IBM_点数主键
	 */
	private String ibmPointId;
	public void setIbmPointId(String ibmPointId) {
		this.ibmPointId=ibmPointId;
	}
	public String getIbmPointId() {
		return this.ibmPointId ;
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
	private String repPointId;
	public void setRepPointId(String repPointId) {
		this.repPointId=repPointId;
	}
	public String getRepPointId() {
		return this.repPointId ;
	}
	
	/**
	 * 点数总余额
	 */
	private String totalPointT;
	public void setTotalPointT(String totalPointT) {
		this.totalPointT=totalPointT;
	}
	public String getTotalPointT() {
		return this.totalPointT ;
	}
	
	/**
	 * 可用点数
	 */
	private String useablePointT;
	public void setUseablePointT(String useablePointT) {
		this.useablePointT=useablePointT;
	}
	public String getUseablePointT() {
		return this.useablePointT ;
	}
	
	/**
	 * 冻结点数
	 */
	private String frozenPointT;
	public void setFrozenPointT(String frozenPointT) {
		this.frozenPointT=frozenPointT;
	}
	public String getFrozenPointT() {
		return this.frozenPointT ;
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