package com.cloud.recharge.connector.report;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_recharge_daily.service.CardRechargeDailyService;
import com.cloud.recharge.connector.core.BaseUserAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 代理分类查询充值卡详情
 *
 * @Author: Dongming
 * @Date: 2020-06-20 18:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/reportItemAgent", method = HttpConfig.Method.GET) public class CardReportItemAgentAction
		extends BaseUserAction {
	@Override public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		String cardTreeId = StringTool.getString(dataMap.get("cardTreeId"), "");
		String agentId = StringTool.getString(dataMap.get("agentId"), "");
		long timeStart = NumberTool.getLong(dataMap.get("timeStart"), 0L);
		long timeEnd = NumberTool.getLong(dataMap.get("timeEnd"), 0L);
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(agentId)) {
			return bean.put401Data();
		}
		Long startTime = null, endTime = null;
		if (timeStart != 0) {
			startTime = DateTool.getDayStart(new Date(timeStart)).getTime();
			if (timeEnd == 0) {
				timeEnd = System.currentTimeMillis();
			}
			endTime = DateTool.getDayEnd(new Date(timeEnd)).getTime();
		}
		try {
			String tableName="card_recharge_daily_sum";
			if(userId.equals(agentId)){
				tableName="card_recharge_daily";
			}
			List<Map<String, Object>> reportItems = new CardRechargeDailyService().
					listItem4Agent(cardTreeId, agentId, startTime, endTime,tableName);
			for (Map<String, Object> reportItem : reportItems) {
				reportItem.put("PRICE_TOTAL_T_", NumberTool.doubleT(reportItem.get("PRICE_TOTAL_T_")));
			}
			bean.success(reportItems);
		} catch (Exception e) {
			log.error("代理分类查询充值卡详情报表出错", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
