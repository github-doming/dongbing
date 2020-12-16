package com.ibm.old.v1.cloud.ibm_hm_set.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_hm_set 
 * vo类
 * @author Robot
 */

public class IbmHmSetTVo implements Serializable {

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
	 * IBM_盘口会员设置主键
	 */
	private String ibmHmSetId;
	public void setIbmHmSetId(String ibmHmSetId) {
		this.ibmHmSetId=ibmHmSetId;
	}
	public String getIbmHmSetId() {
		return this.ibmHmSetId ;
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
	 * 保存账户
	 */
	private String saveAccount;
	public void setSaveAccount(String saveAccount) {
		this.saveAccount=saveAccount;
	}
	public String getSaveAccount() {
		return this.saveAccount ;
	}
	
	/**
	 * 定时登陆
	 */
	private String landedTime;
	public void setLandedTime(String landedTime) {
		this.landedTime=landedTime;
	}
	public String getLandedTime() {
		return this.landedTime ;
	}
	
	/**
	 * 投注记录保存行数
	 */
	private String betRecordRows;
	public void setBetRecordRows(String betRecordRows) {
		this.betRecordRows=betRecordRows;
	}
	public String getBetRecordRows() {
		return this.betRecordRows ;
	}
	
	/**
	 * 投注比例
	 */
	private String betRateT;
	public void setBetRateT(String betRateT) {
		this.betRateT=betRateT;
	}
	public String getBetRateT() {
		return this.betRateT ;
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
	 * 止盈下限
	 */
	private String profitLimitMinT;
	public void setProfitLimitMinT(String profitLimitMinT) {
		this.profitLimitMinT=profitLimitMinT;
	}
	public String getProfitLimitMinT() {
		return this.profitLimitMinT ;
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
	 * 每天重置-1，自定义重置-2
	 */
	private String resetType;
	public void setResetType(String resetType) {
		this.resetType=resetType;
	}
	public String getResetType() {
		return this.resetType ;
	}
	
	/**
	 * 重置盈利上限
	 */
	private String resetProfitMaxT;
	public void setResetProfitMaxT(String resetProfitMaxT) {
		this.resetProfitMaxT=resetProfitMaxT;
	}
	public String getResetProfitMaxT() {
		return this.resetProfitMaxT ;
	}
	
	/**
	 * 重置亏损下限
	 */
	private String resetLossMinT;
	public void setResetLossMinT(String resetLossMinT) {
		this.resetLossMinT=resetLossMinT;
	}
	public String getResetLossMinT() {
		return this.resetLossMinT ;
	}
	
	/**
	 * 炸停止
	 */
	private String blastStop;
	public void setBlastStop(String blastStop) {
		this.blastStop=blastStop;
	}
	public String getBlastStop() {
		return this.blastStop ;
	}
	
	/**
	 * 投注合并
	 */
	private String betMerger;
	public void setBetMerger(String betMerger) {
		this.betMerger=betMerger;
	}
	public String getBetMerger() {
		return this.betMerger ;
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