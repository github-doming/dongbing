package com.ibm.old.v1.cloud.ibm_exec_bet_item.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_exec_bet_item 
 * vo类
 * @author Robot
 */

public class IbmExecBetItemTVo implements Serializable {

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
	 * IBM_执行投注项主键
	 */
	private String ibmExecBetItemId;
	public void setIbmExecBetItemId(String ibmExecBetItemId) {
		this.ibmExecBetItemId=ibmExecBetItemId;
	}
	public String getIbmExecBetItemId() {
		return this.ibmExecBetItemId ;
	}
	
	/**
	 * IBM_执行盘口会员方案组主键
	 */
	private String execPlanGroupId;
	public void setExecPlanGroupId(String execPlanGroupId) {
		this.execPlanGroupId=execPlanGroupId;
	}
	public String getExecPlanGroupId() {
		return this.execPlanGroupId ;
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
	 * 期数
	 */
	private String period;
	public void setPeriod(String period) {
		this.period=period;
	}
	public String getPeriod() {
		return this.period ;
	}
	
	/**
	 * 金额
	 */
	private String fundT;
	public void setFundT(String fundT) {
		this.fundT=fundT;
	}
	public String getFundT() {
		return this.fundT ;
	}
	
	/**
	 * 方案组说明
	 */
	private String planGroupDesc;
	public void setPlanGroupDesc(String planGroupDesc) {
		this.planGroupDesc=planGroupDesc;
	}
	public String getPlanGroupDesc() {
		return this.planGroupDesc ;
	}
	
	/**
	 * 投注内容
	 */
	private String betContent;
	public void setBetContent(String betContent) {
		this.betContent=betContent;
	}
	public String getBetContent() {
		return this.betContent ;
	}
	
	/**
	 * 投注内容编码
	 */
	private String betContentCode;
	public void setBetContentCode(String betContentCode) {
		this.betContentCode=betContentCode;
	}
	public String getBetContentCode() {
		return this.betContentCode ;
	}
	
	/**
	 * 投注内容长度
	 */
	private String betContentLen;
	public void setBetContentLen(String betContentLen) {
		this.betContentLen=betContentLen;
	}
	public String getBetContentLen() {
		return this.betContentLen ;
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
	 * 投注类型
	 */
	private String betType;
	public void setBetType(String betType) {
		this.betType=betType;
	}
	public String getBetType() {
		return this.betType ;
	}
	
	/**
	 * 开奖号码
	 */
	private String drawNumber;
	public void setDrawNumber(String drawNumber) {
		this.drawNumber=drawNumber;
	}
	public String getDrawNumber() {
		return this.drawNumber ;
	}
	
	/**
	 * 资金轮次
	 */
	private String fundsIndex;
	public void setFundsIndex(String fundsIndex) {
		this.fundsIndex=fundsIndex;
	}
	public String getFundsIndex() {
		return this.fundsIndex ;
	}
	
	/**
	 * 执行状态
	 */
	private String execState;
	public void setExecState(String execState) {
		this.execState=execState;
	}
	public String getExecState() {
		return this.execState ;
	}
	
	/**
	 * 盈亏
	 */
	private String profitT;
	public void setProfitT(String profitT) {
		this.profitT=profitT;
	}
	public String getProfitT() {
		return this.profitT ;
	}
	
	/**
	 * 赔率
	 */
	private String odds;
	public void setOdds(String odds) {
		this.odds=odds;
	}
	public String getOdds() {
		return this.odds ;
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