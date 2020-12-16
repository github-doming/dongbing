package com.common.entity;

import org.doming.core.tools.StringTool;

/**
 * @Description: 方案详情信息
 * @Author: null
 * @Date: 2020-07-10 15:55
 * @Version: v1.0
 */
public class PlanItem {

	/**
	 * 方案编码
	 */
	private String planCode;
	public void setPlanCode(String planCode) {
		this.planCode=planCode;
	}
	public void setPlanCode(Object planCode) {
		if (planCode != null) {
			this.planCode = planCode.toString();
		}
	}
	public String getPlanCode() {
		return this.planCode ;
	}

	/**
	 * 游戏类型
	 */
	private String gameType;
	public void setGameType(String gameType) {
		this.gameType=gameType;
	}
	public void setGameType(Object gameType) {
		if (gameType != null) {
			this.gameType = gameType.toString();
		}
	}
	public String getGameType() {
		return this.gameType ;
	}

	/**
	 * 方案详情表名
	 */
	private String planItemTableName;
	public void setPlanItemTableName(String planItemTableName) {
		this.planItemTableName=planItemTableName;
	}
	public void setPlanItemTableName(Object planItemTableName) {
		if (planItemTableName != null) {
			this.planItemTableName = planItemTableName.toString();
		}
	}
	public String getPlanItemTableName() {
		return this.planItemTableName ;
	}

	/**
	 * 止盈上限
	 */
	private Integer profitLimitMax;
	public void setProfitLimitMax(Integer profitLimitMax) {
		this.profitLimitMax=profitLimitMax;
	}
	public void setProfitLimitMax(Object profitLimitMax) {
		if (profitLimitMax != null) {
			if (profitLimitMax instanceof Integer) {
				this.profitLimitMax= (Integer) profitLimitMax;
			}else if (StringTool.isInteger(profitLimitMax.toString())) {
				this.profitLimitMax = Integer.parseInt(profitLimitMax.toString());
			}
		}
	}
	public Integer getProfitLimitMax() {
		return this.profitLimitMax ;
	}

	/**
	 * 止损下限
	 */
	private Integer lossLimitMin;
	public void setLossLimitMin(Integer lossLimitMin) {
		this.lossLimitMin=lossLimitMin;
	}
	public void setLossLimitMin(Object lossLimitMin) {
		if (lossLimitMin != null) {
			if (lossLimitMin instanceof Integer) {
				this.lossLimitMin= (Integer) lossLimitMin;
			}else if (StringTool.isInteger(lossLimitMin.toString())) {
				this.lossLimitMin = Integer.parseInt(lossLimitMin.toString());
			}
		}
	}
	public Integer getLossLimitMin() {
		return this.lossLimitMin ;
	}

	/**
	 * 资金列表
	 */
	private String fundsList;
	public void setFundsList(String fundsList) {
		this.fundsList=fundsList;
	}
	public void setFundsList(Object fundsList) {
		if (fundsList != null) {
			this.fundsList = fundsList.toString();
		}
	}
	public String getFundsList() {
		return this.fundsList ;
	}

	/**
	 * 跟进期数
	 */
	private Integer followPeriod;
	public void setFollowPeriod(Integer followPeriod) {
		this.followPeriod=followPeriod;
	}
	public void setFollowPeriod(Object followPeriod) {
		if (followPeriod != null) {
			if (followPeriod instanceof Integer) {
				this.followPeriod= (Integer) followPeriod;
			}else if (StringTool.isInteger(followPeriod.toString())) {
				this.followPeriod = Integer.parseInt(followPeriod.toString());
			}
		}
	}
	public Integer getFollowPeriod() {
		return this.followPeriod ;
	}

	/**
	 * 监控次数
	 */
	private Integer monitorPeriod;
	public void setMonitorPeriod(Integer monitorPeriod) {
		this.monitorPeriod=monitorPeriod;
	}
	public void setMonitorPeriod(Object monitorPeriod) {
		if (monitorPeriod != null) {
			if (monitorPeriod instanceof Integer) {
				this.monitorPeriod= (Integer) monitorPeriod;
			}else if (StringTool.isInteger(monitorPeriod.toString())) {
				this.monitorPeriod = Integer.parseInt(monitorPeriod.toString());
			}
		}
	}
	public Integer getMonitorPeriod() {
		return this.monitorPeriod ;
	}

	/**
	 * 投注模式
	 */
	private String betMode;
	public void setBetMode(String betMode) {
		this.betMode=betMode;
	}
	public void setBetMode(Object betMode) {
		if (betMode != null) {
			this.betMode = betMode.toString();
		}
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
	public void setFundSwapMode(Object fundSwapMode) {
		if (fundSwapMode != null) {
			this.fundSwapMode = fundSwapMode.toString();
		}
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
	public void setPeriodRollMode(Object periodRollMode) {
		if (periodRollMode != null) {
			this.periodRollMode = periodRollMode.toString();
		}
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
	public void setPlanGroupData(Object planGroupData) {
		if (planGroupData != null) {
			this.planGroupData = planGroupData.toString();
		}
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
	public void setPlanGroupActiveKey(Object planGroupActiveKey) {
		if (planGroupActiveKey != null) {
			this.planGroupActiveKey = planGroupActiveKey.toString();
		}
	}
	public String getPlanGroupActiveKey() {
		return this.planGroupActiveKey ;
	}

	/**
	 * 扩展信息
	 */
	private String expandInfo;
	public void setExpandInfo(String expandInfo) {
		this.expandInfo=expandInfo;
	}
	public void setExpandInfo(Object expandInfo) {
		if (expandInfo != null) {
			this.expandInfo = expandInfo.toString();
		}
	}
	public String getExpandInfo() {
		return this.expandInfo ;
	}
}
