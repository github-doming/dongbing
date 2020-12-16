package com.common.enums;

/**
 * @Description: 方案信息
 * @Author: null
 * @Date: 2020-09-07 14:43
 * @Version: v1.0
 */
public enum  PlanInfoEnum {

	/**
	 * 方案扩展信息
	 */
	BET_PERIOD_("betPeriod","投注期数"),LIMIT_AMOUNT_("limitAmount","限制数量"),

	OMISSION_PERIOD_("omissionPeriod","最少遗漏"), RENEW_PERIOD_("renewPeriod","换新期数"),

	STOP_BUY_("stopBuy","停买长度"),TAKE_NUMBER_ADD_("takeNumberAdd","取号相加"),

	SELECT_AMOUNT_("selectAmount","选号数量"),STATISTICS_PERIOD_("statisticsPeriod","统计期数"),

	MONITOR_LENGTH_("monitorLength","监控长度"),ADVANCE_MODE_("advanceMode","行进方式"),

	MONITOR_MODE_("monitorMode","监控模式"),SELECT_MODE_("selectMode","选号方式"),

	FUNDS_OPTIONS_("fundsOptions","资金选项"),TRACK_MODE_("trackMode","追踪模式"),

	SAME_DOUBLE_("sameDouble","下注相同内容时才翻倍"),UN_BETTING_("unBetting","龙虎和不下注"),

	INVERSE_("inverse","反投"),UN_INCREASE_("unIncrease","不中停止新增"), UN_REPEAT_("unRepeat","不管号码重复"),
	/**
 	* 方案基本信息
 	*/
	FOLLOW_PERIOD_("followPeriod","跟进期数"),MONITOR_PERIOD_("monitorPeriod","监控期数"),

	BET_MODE_("betMode","投注模式"),FUND_SWAP_MODE_("fundSwapMode","金额切换"),

	PERIOD_ROLL_MODE_("periodRollMode","期期滚");


	String code;
	String msg;

	PlanInfoEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	public String getCode() {
		return code;
	}
}
