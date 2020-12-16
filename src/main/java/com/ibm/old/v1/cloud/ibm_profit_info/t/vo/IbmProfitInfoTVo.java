package com.ibm.old.v1.cloud.ibm_profit_info.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_profit_info 
 * vo类
 */

public class IbmProfitInfoTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//IBM_盘口会员方案组投注结果主键IBM_HM_PLAN_PROFIT_INFO_ID_
	private String ibmProfitInfoId;
	public void setIbmProfitInfoId(String ibmProfitInfoId) {
		this.ibmProfitInfoId=ibmProfitInfoId;
	}
	public String getIbmProfitInfoId() {
		return this.ibmProfitInfoId ;
	}
	
//IBM_盘口会员方案盈亏主键
	private String profitPlanId;
	public void setProfitPlanId(String profitPlanId) {
		this.profitPlanId=profitPlanId;
	}
	public String getProfitPlanId() {
		return this.profitPlanId ;
	}
	
//执行投注项主键
	private String execBetItemId;
	public void setExecBetItemId(String execBetItemId) {
		this.execBetItemId=execBetItemId;
	}
	public String getExecBetItemId() {
		return this.execBetItemId ;
	}
	
//盘口会员主键
	private String handicapMemberId;
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
	}
	
//期数
	private String period;
	public void setPeriod(String period) {
		this.period=period;
	}
	public String getPeriod() {
		return this.period ;
	}
	
//盈亏额
	private String profitT;
	public void setProfitT(String profitT) {
		this.profitT=profitT;
	}
	public String getProfitT() {
		return this.profitT ;
	}
	
//投注额
	private String betFundsT;
	public void setBetFundsT(String betFundsT) {
		this.betFundsT=betFundsT;
	}
	public String getBetFundsT() {
		return this.betFundsT ;
	}
	
//投注数
	private String betLen;
	public void setBetLen(String betLen) {
		this.betLen=betLen;
	}
	public String getBetLen() {
		return this.betLen ;
	}
	
//创建时间
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//更新时间
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
//描述
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}


}