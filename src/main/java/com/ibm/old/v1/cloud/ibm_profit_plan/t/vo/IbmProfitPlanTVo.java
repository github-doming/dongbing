package com.ibm.old.v1.cloud.ibm_profit_plan.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_profit_plan 
 * vo类
 * @author Robot
 */

public class IbmProfitPlanTVo implements Serializable {

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
	 * IBM_盘口会员方案盈亏主键
	 */
	private String ibmProfitPlanId;
	public void setIbmProfitPlanId(String ibmProfitPlanId) {
		this.ibmProfitPlanId=ibmProfitPlanId;
	}
	public String getIbmProfitPlanId() {
		return this.ibmProfitPlanId ;
	}
	
	/**
	 * 盘口会员盈亏主键
	 */
	private String profitId;
	public void setProfitId(String profitId) {
		this.profitId=profitId;
	}
	public String getProfitId() {
		return this.profitId ;
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
	 * 盘口会员主键
	 */
	private String handicapMemberId;
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
	}
	
	/**
	 * 盈亏总额
	 */
	private String profitT;
	public void setProfitT(String profitT) {
		this.profitT=profitT;
	}
	public String getProfitT() {
		return this.profitT ;
	}
	
	/**
	 * 投注总额
	 */
	private String betFundsT;
	public void setBetFundsT(String betFundsT) {
		this.betFundsT=betFundsT;
	}
	public String getBetFundsT() {
		return this.betFundsT ;
	}
	
	/**
	 * 投注总数
	 */
	private String betLen;
	public void setBetLen(String betLen) {
		this.betLen=betLen;
	}
	public String getBetLen() {
		return this.betLen ;
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