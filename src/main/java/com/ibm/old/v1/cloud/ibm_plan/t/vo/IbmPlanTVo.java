package com.ibm.old.v1.cloud.ibm_plan.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_plan 
 * vo类
 * @author Robot
 */

public class IbmPlanTVo implements Serializable {

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
	 * IBM_方案主键IBM_PLAN_ID_
	 */
	private String ibmPlanId;
	public void setIbmPlanId(String ibmPlanId) {
		this.ibmPlanId=ibmPlanId;
	}
	public String getIbmPlanId() {
		return this.ibmPlanId ;
	}
	
	/**
	 * 游戏主键
	 */
	private String gameId;
	public void setGameId(String gameId) {
		this.gameId=gameId;
	}
	public String getGameId() {
		return this.gameId ;
	}
	
	/**
	 * 方案名称
	 */
	private String planName;
	public void setPlanName(String planName) {
		this.planName=planName;
	}
	public String getPlanName() {
		return this.planName ;
	}
	
	/**
	 * 方案编码
	 */
	private String planCode;
	public void setPlanCode(String planCode) {
		this.planCode=planCode;
	}
	public String getPlanCode() {
		return this.planCode ;
	}
	
	/**
	 * 方案说明
	 */
	private String planExplain;
	public void setPlanExplain(String planExplain) {
		this.planExplain=planExplain;
	}
	public String getPlanExplain() {
		return this.planExplain ;
	}
	
	/**
	 * 方案详情表名
	 */
	private String planItemTableName;
	public void setPlanItemTableName(String planItemTableName) {
		this.planItemTableName=planItemTableName;
	}
	public String getPlanItemTableName() {
		return this.planItemTableName ;
	}
	
	/**
	 * 方案类型
	 */
	private String planType;
	public void setPlanType(String planType) {
		this.planType=planType;
	}
	public String getPlanType() {
		return this.planType ;
	}
	
	/**
	 * 方案价值
	 */
	private String planWorthT;
	public void setPlanWorthT(String planWorthT) {
		this.planWorthT=planWorthT;
	}
	public String getPlanWorthT() {
		return this.planWorthT ;
	}
	
	/**
	 * 方案图标
	 */
	private String planIcon;
	public void setPlanIcon(String planIcon) {
		this.planIcon=planIcon;
	}
	public String getPlanIcon() {
		return this.planIcon ;
	}
	
	/**
	 * 次序
	 */
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
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