package com.ibs.plan.module.mq.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.service.IbspHmBetItemService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 错误投注
 *
 * @Author: null
 * @Date: 2020-06-08 15:27
 * @Version: v1.0
 */
public class ErrorBetResultController {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	private String msg;
	public ErrorBetResultController(String msg) {
		this.msg = msg;
	}
	public JsonResultBeanPlus execute(JSONObject msgObj) throws SQLException {
		if (valiParameter(msgObj)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		Date nowTime = new Date();
		IbspHmBetItemService betItemService = new IbspHmBetItemService();
		if (msgObj.get("data") instanceof List) {
			betItemService.updateResult(msgObj.getJSONArray("data"), IbsStateEnum.FAIL, nowTime, msg);
		}
		if (msgObj.get("data")instanceof Map){
			betItemService.updateResult(msgObj.getJSONObject("data"), nowTime, msg);
		}

		return bean.success();
	}


	protected boolean valiParameter(JSONObject msgObj) {
		if (!msgObj.containsKey("data")) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		return false;
	}
}
