package com.ibm.old.v1.cloud.ibm_plan_user.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_plan_user 
 * vo类
 * @author Robot
 */

public class IbmPlanUserTVo implements Serializable {

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
	 * IBM_方案与玩家主键IBM_PLAN_USER_ID_
	 */
	private String ibmPlanUserId;
	public void setIbmPlanUserId(String ibmPlanUserId) {
		this.ibmPlanUserId=ibmPlanUserId;
	}
	public String getIbmPlanUserId() {
		return this.ibmPlanUserId ;
	}
	
	/**
	 * 方案主键
	 */
	private String planId;
	public void setPlanId(String planId) {
		this.planId=planId;
	}
	public String getPlanId() {
		return this.planId ;
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
	 * 方案详情表主键
	 */
	private String planItemTableId;
	public void setPlanItemTableId(String planItemTableId) {
		this.planItemTableId=planItemTableId;
	}
	public String getPlanItemTableId() {
		return this.planItemTableId ;
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
	 * 止盈上限
	 */
	private String profitLimitMaxT;
	public void setProfitLimitMaxT(String profitLimitMaxT) {
		this.profitLimitMaxT=profitLimitMaxT;
	}
	public String getProfitLimitMaxT() {
		return this.profitLimitMaxT ;
	}
	
	/**
	 * 止损下限
	 */
	private String lossLimitMinT;
	public void setLossLimitMinT(String lossLimitMinT) {
		this.lossLimitMinT=lossLimitMinT;
	}
	public String getLossLimitMinT() {
		return this.lossLimitMinT ;
	}
	
	/**
	 * 监控次数
	 */
	private String monitorPeriod;
	public void setMonitorPeriod(String monitorPeriod) {
		this.monitorPeriod=monitorPeriod;
	}
	public String getMonitorPeriod() {
		return this.monitorPeriod ;
	}
	
	/**
	 * 投注模式
	 */
	private String betMode;
	public void setBetMode(String betMode) {
		this.betMode=betMode;
	}
	public String getBetMode() {
		return this.betMode ;
	}
	
	/**
	 * 操作频率
	 */
	private String operateFrequency;
	public void setOperateFrequency(String operateFrequency) {
		this.operateFrequency=operateFrequency;
	}
	public String getOperateFrequency() {
		return this.operateFrequency ;
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