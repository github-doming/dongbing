package com.ibm.old.v1.cloud.ibm_pi_location_bet_number.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_pi_location_bet_number 
 * vo类
 * @author Robot
 */

public class IbmPiLocationBetNumberTVo implements Serializable {

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
	 * 位置投注_号码主键IBM_PI_LOCATION_BET_NUMBER_ID_
	 */
	private String ibmPiLocationBetNumberId;
	public void setIbmPiLocationBetNumberId(String ibmPiLocationBetNumberId) {
		this.ibmPiLocationBetNumberId=ibmPiLocationBetNumberId;
	}
	public String getIbmPiLocationBetNumberId() {
		return this.ibmPiLocationBetNumberId ;
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
	 * 资金列表
	 */
	private String fundsList;
	public void setFundsList(String fundsList) {
		this.fundsList=fundsList;
	}
	public String getFundsList() {
		return this.fundsList ;
	}
	
	/**
	 * 资金方案ID
	 */
	private String fundsListId;
	public void setFundsListId(String fundsListId) {
		this.fundsListId=fundsListId;
	}
	public String getFundsListId() {
		return this.fundsListId ;
	}
	
	/**
	 * 跟进期数
	 */
	private String followPeriod;
	public void setFollowPeriod(String followPeriod) {
		this.followPeriod=followPeriod;
	}
	public String getFollowPeriod() {
		return this.followPeriod ;
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
	 * 资金切换方式
	 */
	private String fundSwapMode;
	public void setFundSwapMode(String fundSwapMode) {
		this.fundSwapMode=fundSwapMode;
	}
	public String getFundSwapMode() {
		return this.fundSwapMode ;
	}
	
	/**
	 * 期期滚选项
	 */
	private String periodRollMode;
	public void setPeriodRollMode(String periodRollMode) {
		this.periodRollMode=periodRollMode;
	}
	public String getPeriodRollMode() {
		return this.periodRollMode ;
	}
	
	/**
	 * 方案组数据
	 */
	private String planGroupData;
	public void setPlanGroupData(String planGroupData) {
		this.planGroupData=planGroupData;
	}
	public String getPlanGroupData() {
		return this.planGroupData ;
	}
	
	/**
	 * 开启方案组key
	 */
	private String planGroupActiveKey;
	public void setPlanGroupActiveKey(String planGroupActiveKey) {
		this.planGroupActiveKey=planGroupActiveKey;
	}
	public String getPlanGroupActiveKey() {
		return this.planGroupActiveKey ;
	}
	
	/**
	 * 不中禁止新增
	 */
	private String unIncrease;
	public void setUnIncrease(String unIncrease) {
		this.unIncrease=unIncrease;
	}
	public String getUnIncrease() {
		return this.unIncrease ;
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