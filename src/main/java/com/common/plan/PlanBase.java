package com.common.plan;

import com.alibaba.fastjson.JSONObject;
import com.common.game.Period;
import com.common.tools.CacheTool;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;
/**
 * @Description: 方案父类
 * @Author: null
 * @Date: 2020-05-19 14:18
 * @Version: v1.0
 */
public abstract class PlanBase implements Plan {
	protected static final Logger log = LogManager.getLogger(PlanBase.class);
	private PlanGroup planGroup;
	public String[] baseData;

	@Override public boolean planGroup(PlanGroup planGroup) {
		this.planGroup = planGroup;
		//历史数据
		String baseDrawNumber;
		try {
			baseDrawNumber = CacheTool.getDraw(gameCode(),handicapCode(), drawType(), basePeriod());
		} catch (Exception e) {
			log.error("获取开奖数据错误",e);
			return true;
		}
		if (StringTool.isEmpty(baseDrawNumber)){
			return true;
		}
		baseData = baseDrawNumber.split(",");
		return false;
	}
	@Override
	public PlanGroup getPlanGroup(){
		return this.planGroup;
	}

	protected Period<?> period() {
		return gameCode().getGameFactory().period(handicapCode());
	}
	protected BaseGameUtil.Code gameCode() {
		return planGroup.gameCode();
	}
	protected String drawType() {
		return planGroup.drawType();
	}
	protected Object basePeriod() {
		return planGroup.basePeriod();
	}
	public void setBasePeriod(Object basePeriod){
		this.planGroup.setBasePeriod(basePeriod);
	}


	protected JSONObject planGroupItem() {
		return planGroup.planGroupItem();
	}
	protected int monitor() {
		return planGroup.monitor();
	}
	protected BaseHandicapUtil.Code handicapCode() {
		return planGroup.handicapCode();
	}
}
