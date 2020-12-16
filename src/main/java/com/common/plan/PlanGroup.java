package com.common.plan;

import com.alibaba.fastjson.JSONObject;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;


/**
 * 方案组数据
 *
 * @Author: Dongming
 * @Date: 2020-06-11 15:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PlanGroup {

	private JSONObject planGroupItem;
	private Object basePeriod;
	private int monitor;
	private BaseGameUtil.Code gameCode;
	private BaseHandicapUtil.Code handicapCode;
	private String drawType;

	public PlanGroup(Object basePeriod, int monitor, BaseGameUtil.Code gameCode,
					 BaseHandicapUtil.Code handicapCode, String drawType) {
		this.basePeriod = basePeriod;
		this.monitor = monitor;
		this.gameCode = gameCode;
		this.handicapCode = handicapCode;
		this.drawType = drawType;
	}

	public void setPlanGroupItem(JSONObject planGroupItem){
		this.planGroupItem = planGroupItem;
	}

	public JSONObject planGroupItem() {
		return planGroupItem;
	}
	public Object basePeriod() {
		return basePeriod;
	}
	public void setBasePeriod(Object basePeriod){
		this.basePeriod=basePeriod;
	}
	public int monitor() {
		return monitor;
	}
	public BaseGameUtil.Code gameCode() {
		return gameCode;
	}
	public BaseHandicapUtil.Code handicapCode() {
		return handicapCode;
	}
	public String drawType() {
		return drawType;
	}
}
