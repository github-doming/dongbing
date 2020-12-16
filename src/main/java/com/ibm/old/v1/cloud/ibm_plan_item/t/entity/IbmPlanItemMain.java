package com.ibm.old.v1.cloud.ibm_plan_item.t.entity;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import org.doming.core.tools.StringTool;

import javax.persistence.Column;
import java.util.Map;
/**
 * @Description: 方案详情主体信息
 * @Author: Dongming
 * @Date: 2019-01-17 14:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmPlanItemMain extends IbmPlanItemT {

	/**
	 * 资金列表
	 */
	@Column(name = "FUNDS_LIST_") private String fundsList;
	public void setFundsList(String fundsList) {
		this.fundsList = fundsList;
	}
	public void setFundsList(Object fundsList) {
		if (fundsList != null) {
			this.fundsList = fundsList.toString();
		}
	}
	public String getFundsList() {
		return this.fundsList;
	}

	/**
	 * 资金切换方式
	 */
	@Column(name = "FUND_SWAP_MODE_") private String fundSwapMode;
	public void setFundSwapMode(String fundSwapMode) {
		this.fundSwapMode = fundSwapMode;
	}
	public void setFundSwapMode(Object fundSwapMode) {
		if (fundSwapMode != null) {
			this.fundSwapMode = fundSwapMode.toString();
		}
	}
	public String getFundSwapMode() {
		return this.fundSwapMode;
	}
	/**
	 * 跟进期数
	 */
	@Column(name = "FOLLOW_PERIOD_") private Integer followPeriod;
	public void setFollowPeriod(Integer followPeriod) {
		this.followPeriod = followPeriod;
	}
	public void setFollowPeriod(Object followPeriod) {
		if (followPeriod != null) {
			if (followPeriod instanceof Integer) {
				this.followPeriod = (Integer) followPeriod;
			} else if (StringTool.isInteger(followPeriod.toString())) {
				this.followPeriod = Integer.parseInt(followPeriod.toString());
			}
		}
	}
	public Integer getFollowPeriod() {
		return this.followPeriod;
	}

	/**
	 * 期期滚选项
	 */
	@Column(name = "PERIOD_ROLL_MODE_") private String periodRollMode;
	public void setPeriodRollMode(String periodRollMode) {
		this.periodRollMode = periodRollMode;
	}
	public void setPeriodRollMode(Object periodRollMode) {
		if (periodRollMode != null) {
			this.periodRollMode = periodRollMode.toString();
		}
	}
	public String getPeriodRollMode() {
		return this.periodRollMode;
	}

	/**
	 * 方案组数据
	 */
	@Column(name = "PLAN_GROUP_DATA_") private String planGroupData;
	public void setPlanGroupData(String planGroupData) {
		this.planGroupData = planGroupData;
	}
	public void setPlanGroupData(Object planGroupData) {
		if (planGroupData != null) {
			this.planGroupData = planGroupData.toString();
		}
	}
	public String getPlanGroupData() {
		return this.planGroupData;
	}

	/**
	 * 开启方案组key
	 */
	@Column(name = "PLAN_GROUP_ACTIVE_KEY_") private String planGroupActiveKey;
	public void setPlanGroupActiveKey(String planGroupActiveKey) {
		this.planGroupActiveKey = planGroupActiveKey;
	}
	public void setPlanGroupActiveKey(Object planGroupActiveKey) {
		if (planGroupActiveKey != null) {
			this.planGroupActiveKey = planGroupActiveKey.toString();
		}
	}
	public String getPlanGroupActiveKey() {
		return this.planGroupActiveKey;
	}

	/**
	 * 描述
	 */
	@Column(name = "DESC_") private String desc;
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setDesc(Object desc) {
		if (desc != null) {
			this.desc = desc.toString();
		}
	}
	public String getDesc() {
		return this.desc;
	}

	public PlanTool.Code gePlanCode() {
		return PlanTool.Code.valueOfTableName(getName());
	}

	public IbmPlanItemMain(String name, String id, Long profitLimitMaxTh, Long lossLimitMinTh, String fundsList,
			Object followPeriod, Object monitorPeriod, Object betMode, String fundSwapMode, String periodRollMode,
			String planGroupData) {
		super.setName(name);
		super.setId(id);
		super.setProfitLimitMaxT(profitLimitMaxTh);
		super.setLossLimitMinT(lossLimitMinTh);
		this.fundsList = fundsList;
		this.setFollowPeriod(followPeriod);
		super.setMonitorPeriod(monitorPeriod);
		super.setBetMode(betMode);
		this.fundSwapMode = fundSwapMode;
		this.periodRollMode = periodRollMode;
		this.planGroupData = planGroupData;
	}

	public IbmPlanItemMain(Map<String, Object> planItemInfo) {
		super.setProfitLimitMaxT(planItemInfo.get("PROFIT_LIMIT_MAX_T_"));
		super.setLossLimitMinT(planItemInfo.get("LOSS_LIMIT_MIN_T_"));
		this.setFundsList(planItemInfo.get("FUNDS_LIST_"));
		this.setFollowPeriod(planItemInfo.get("FOLLOW_PERIOD_"));
		this.setMonitorPeriod(planItemInfo.get("MONITOR_PERIOD_"));
		this.setBetMode(planItemInfo.get("BET_MODE_"));
		this.setFundSwapMode(planItemInfo.get("FUND_SWAP_MODE_"));
		this.setPeriodRollMode(planItemInfo.get("PERIOD_ROLL_MODE_"));
		this.setPlanGroupActiveKey(planItemInfo.get("PLAN_GROUP_ACTIVE_KEY_"));
		this.setPlanGroupData(planItemInfo.get("PLAN_GROUP_DATA_"));
	}
}
