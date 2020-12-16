package com.cloud.recharge.connector.report;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_recharge_daily.service.CardRechargeDailyService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * 代理分类查询充值卡报表
 *
 * @Author: Dongming
 * @Date: 2020-06-20 17:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/reportListAgent")
public class CardReportListAgentAction extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		String cardTreeId = StringTool.getString(dataMap.get("cardTreeId"), "");
		String agentId = StringTool.getString(dataMap.get("agentId"), "");
		long timeStart = NumberTool.getLong(dataMap.get("timeStart"), 0L);
		long timeEnd = NumberTool.getLong(dataMap.get("timeEnd"), 0L);

		Long startTime = null, endTime = null;
		if (timeStart != 0) {
			startTime = DateTool.getDayStart(new Date(timeStart)).getTime();
			if (timeEnd == 0) {
				timeEnd = System.currentTimeMillis();
			}
			endTime = DateTool.getDayEnd(new Date(timeEnd)).getTime();
		}
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);

		Map<String, Object> data = new HashMap<>(6);
		try {
			List<String> subUserIds=new ArrayList<>();
			CardRechargeDailyService rechargeDailyService=new CardRechargeDailyService();
			CardAdminService cardAdminService=new CardAdminService();

			int activateCardTotal = 0;
			int pointTotal = 0;
			double priceDownTotal = 0;
			double priceUpTotal = 0;
			double profit = 0;
			if (StringTool.notEmpty(agentId)) {
				subUserIds.add(agentId);
				Map<String, Object> subUserInfo = cardAdminService.subUserInfo(subUserIds);

				PageCoreBean<Map<String, Object>> pageCoreBean = rechargeDailyService.listReportItem4Agent(cardTreeId, subUserIds, startTime,
						endTime, pageIndex, pageSize, "card_recharge_daily_sum");
				List<Map<String, Object>> reportItems = pageCoreBean.getList();

				for (Map<String, Object> reportItem : reportItems) {
					reportItem.put("PRICE_TOTAL_T_", NumberTool.doubleT(reportItem.get("PRICE_TOTAL_T_")));
					reportItem.put("USER_ALIAS_", subUserInfo.get(reportItem.get("USER_ID_")));

					activateCardTotal += NumberTool.getInteger(reportItem.get("ACTIVATE_TOTAL_"));
					pointTotal += NumberTool.getInteger(reportItem.get("POINT_TOTAL_"));
					priceDownTotal = NumberTool.doubleT(reportItem.get("PRICE_TOTAL_DOWN_T_"));
					priceUpTotal = NumberTool.doubleT(reportItem.get("PRICE_TOTAL_T_"));
					profit = priceDownTotal - priceUpTotal;
				}
				data.put("rows", reportItems);
				data.put("total", reportItems.size());
				data.put("index", pageIndex);
				data.put("activateCardTotal", activateCardTotal);
				data.put("pointTotal", pointTotal);
				data.put("priceDownTotal", priceDownTotal);
				data.put("priceUpTotal", priceUpTotal);
				data.put("profit", profit);
				return data;
			}

			String totalType=userId;
			subUserIds.add(userId);
			PageCoreBean<Map<String, Object>> pageCoreBean = rechargeDailyService.listReportItem4Agent(cardTreeId, subUserIds, startTime,
					endTime, pageIndex, pageSize, "card_recharge_daily");
			if (CardTool.Type.ADMIN.getUserType().equals(user.getUserType())) {
				subUserIds.add(CardTool.Type.ADMIN.name());
				totalType=CardTool.Type.ADMIN.name();
			}
			List<Map<String, Object>> report = new ArrayList<>(pageCoreBean.getList());
			subUserIds = cardAdminService.listSubUserId(subUserIds);

			Map<String, Object> subUserInfo = cardAdminService.subUserInfo(subUserIds);
			subUserIds.remove(userId);
			if(ContainerTool.notEmpty(subUserIds)){
				pageCoreBean = rechargeDailyService.listReportItem4Agent(cardTreeId, subUserIds, startTime,
						endTime, pageIndex, pageSize,"card_recharge_daily_sum");
				List<Map<String, Object>> reportItems = pageCoreBean.getList();
				report.addAll(reportItems);
			}
			for (Map<String, Object> reportItem : report) {
				reportItem.put("PRICE_TOTAL_T_", NumberTool.doubleT(reportItem.get("PRICE_TOTAL_T_")));
				reportItem.put("USER_ALIAS_", subUserInfo.get(reportItem.get("USER_ID_")));
			}

			Map<String, Object> totalData = rechargeDailyService.listReportTotal4Agent(cardTreeId, totalType, startTime, endTime);
			activateCardTotal = NumberTool.getInteger(totalData.remove("ACTIVATE_TOTAL_"));
			pointTotal = NumberTool.getInteger(totalData.remove("POINT_TOTAL_"));
			priceDownTotal = NumberTool.doubleT(totalData.remove("PRICE_TOTAL_DOWN_T_"));
			priceUpTotal = NumberTool.doubleT(totalData.remove("PRICE_TOTAL_T_"));
			profit = priceDownTotal - priceUpTotal;

			data.put("rows", report);
			data.put("total", report.size());
			data.put("index", pageIndex);
			data.put("activateCardTotal", activateCardTotal);
			data.put("pointTotal", pointTotal);
			data.put("priceDownTotal", priceDownTotal);
			data.put("priceUpTotal", priceUpTotal);
			data.put("profit", profit);
		} catch (Exception e) {
			log.error("代理分类查询充值卡报表出错", e);
			data.put("rows", null);
			data.put("index", pageIndex);
			data.put("total", 0);
		}
		return data;
	}
}
