package com.ibm.old.v1.cloud.ibm_plan_item.t.entity;
import c.a.util.core.annotation.AnnotationEntity;
import org.doming.core.tools.StringTool;

import javax.persistence.Column;
/**
 * @Description: 方案详情公共信息
 * @Author: Dongming
 * @Date: 2018-12-21 11:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@AnnotationEntity
public class IbmPlanItemT {

	/**
	 * 主键
	 */
	@Column(name = "ID_") private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setId(Object id) {
		if (id != null) {
			if (id instanceof String) {
				this.id = (String) id;
			}
		}

	}
	/**
	 * 方案名称
	 */
	@Column(name = "NAME_") private String name;
	public void setName(String name) {
		this.name = name;
	}
	public void setName(Object name) {
		if (name != null) {
			this.name = name.toString();
		}
	}
	public String getName() {
		return this.name;
	}

	/**
	 * 止盈上限
	 */
	@Column(name = "PROFIT_LIMIT_MAX_T_") private Long profitLimitMaxT;
	public void setProfitLimitMaxT(Long profitLimitMaxT) {
		this.profitLimitMaxT = profitLimitMaxT;
	}
	public void setProfitLimitMaxT(Object profitLimitMaxT) {
		if (profitLimitMaxT != null) {
			if (profitLimitMaxT instanceof Long) {
				this.profitLimitMaxT = (Long) profitLimitMaxT;
			} else if (StringTool.isInteger(profitLimitMaxT.toString())) {
				this.profitLimitMaxT = Long.parseLong(profitLimitMaxT.toString());
			}
		}
	}
	public Long getProfitLimitMaxT() {
		return this.profitLimitMaxT;
	}

	/**
	 * 止盈下限
	 */
	@Column(name = "LOSS_LIMIT_MIN_T_") private Long lossLimitMinT;
	public void setLossLimitMinT(Long lossLimitMinT) {
		this.lossLimitMinT = lossLimitMinT;
	}
	public void setLossLimitMinT(Object lossLimitMinT) {
		if (lossLimitMinT != null) {
			if (lossLimitMinT instanceof Long) {
				this.lossLimitMinT = (Long) lossLimitMinT;
			} else if (StringTool.isInteger(lossLimitMinT.toString())) {
				this.lossLimitMinT = Long.parseLong(lossLimitMinT.toString());
			}
		}
	}
	public Long getLossLimitMinT() {
		return this.lossLimitMinT;
	}

	/**
	 * 监控次数
	 */
	@Column(name = "MONITOR_PERIOD_") private Integer monitorPeriod;
	public Integer getMonitorPeriod() {
		return this.monitorPeriod;
	}
	public void setMonitorPeriod(Integer monitorPeriod) {
		this.monitorPeriod = monitorPeriod;
	}
	public void setMonitorPeriod(Object monitorPeriod) {
		if (monitorPeriod != null) {
			if (monitorPeriod instanceof Integer) {
				this.monitorPeriod = (Integer) monitorPeriod;
			} else {
				this.monitorPeriod = Integer.parseInt(monitorPeriod.toString());
			}
		}
	}

	/**
	 * 投注模式
	 */
	@Column(name = "BET_MODE_") private String betMode;
	public String getBetMode() {
		return this.betMode;
	}
	public void setBetMode(String betMode) {
		this.betMode = betMode;
	}
	public void setBetMode(Object betMode) {
		if (betMode != null) {
			if (betMode instanceof String) {
				this.betMode = (String) betMode;
			}
		}
	}

	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	@Column(name = "STATE_") private String state;
	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return this.state;
	}

}
