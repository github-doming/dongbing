package com.ibm.old.v1.cloud.ibm_exec_plan_group.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_exec_plan_group 
 * vo类
 * @author Robot
 */

public class IbmExecPlanGroupTVo implements Serializable {

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
	 * IBM_执行盘口会员方案组主键
	 */
	private String ibmExecPlanGroupId;
	public void setIbmExecPlanGroupId(String ibmExecPlanGroupId) {
		this.ibmExecPlanGroupId=ibmExecPlanGroupId;
	}
	public String getIbmExecPlanGroupId() {
		return this.ibmExecPlanGroupId ;
	}
	
	/**
	 * 父执行盘口会员方案组主键
	 */
	private String repExecPlanGroupId;
	public void setRepExecPlanGroupId(String repExecPlanGroupId) {
		this.repExecPlanGroupId=repExecPlanGroupId;
	}
	public String getRepExecPlanGroupId() {
		return this.repExecPlanGroupId ;
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
	 * 跟随期数
	 */
	private String basePeriod;
	public void setBasePeriod(String basePeriod) {
		this.basePeriod=basePeriod;
	}
	public String getBasePeriod() {
		return this.basePeriod ;
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
	 * 方案组key
	 */
	private String planGroupKey;
	public void setPlanGroupKey(String planGroupKey) {
		this.planGroupKey=planGroupKey;
	}
	public String getPlanGroupKey() {
		return this.planGroupKey ;
	}
	
	/**
	 * 资金组key
	 */
	private String fundGroupKey;
	public void setFundGroupKey(String fundGroupKey) {
		this.fundGroupKey=fundGroupKey;
	}
	public String getFundGroupKey() {
		return this.fundGroupKey ;
	}
	
	/**
	 * 方案组详情
	 */
	private String planGroupValue;
	public void setPlanGroupValue(String planGroupValue) {
		this.planGroupValue=planGroupValue;
	}
	public String getPlanGroupValue() {
		return this.planGroupValue ;
	}
	
	/**
	 * 资金组详情
	 */
	private String fundGroupValue;
	public void setFundGroupValue(String fundGroupValue) {
		this.fundGroupValue=fundGroupValue;
	}
	public String getFundGroupValue() {
		return this.fundGroupValue ;
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
	 * 执行结果
	 */
	private String execResult;
	public void setExecResult(String execResult) {
		this.execResult=execResult;
	}
	public String getExecResult() {
		return this.execResult ;
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