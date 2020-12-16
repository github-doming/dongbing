package com.cloud.recharge.connector.report;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_recharge_daily.service.CardRechargeDailyService;
import com.cloud.recharge.card_tree.service.CardTreeService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import com.google.common.collect.ImmutableList;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * 卡种分类查询充值卡报表
 *
 * @Author: Dongming
 * @Date: 2020-06-20 18:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/reportListCard")
public class CardReportListCardAction extends BaseUserAction {
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

		Long startTime = null, endTime = null;
		if (timeStart != 0) {
			startTime = DateTool.getDayStart(new Date(timeStart)).getTime();
			if (timeEnd == 0) {
				timeEnd = System.currentTimeMillis();
			}
			endTime = DateTool.getDayEnd(new Date(timeEnd)).getTime();
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			List<String> cardTreeIds;
			if (StringTool.isEmpty(cardTreeId)) {
				if (CardTool.Type.isAdmin(user.getUserType())) {
					cardTreeIds = new CardTreeService().listOpenId();
				} else {
					cardTreeIds = new CardAdminPriceService().listCardTreeIdByUser(userId);
				}
			} else {
				cardTreeIds = ImmutableList.<String>builder().add(cardTreeId).build();
			}
			List<String> subUserIds=new ArrayList<>();
			if (StringTool.isEmpty(agentId)) {
				if (CardTool.Type.ADMIN.getUserType().equals(user.getUserType())) {
					subUserIds.add(CardTool.Type.ADMIN.name());
				}
				subUserIds.add(userId);
			} else {
				subUserIds.add(agentId);
			}
			List<Map<String, Object>> reportItems = new CardRechargeDailyService()
					.listReportItem4Card(cardTreeIds, subUserIds, startTime, endTime);

			int activateCardTotal = 0;
			int pointTotal = 0;
			double priceDownTotal = 0;
			double priceUpTotal = 0;

			for (Map<String, Object> reportItem : reportItems) {
				reportItem.put("PRICE_TOTAL_T_", NumberTool.doubleT(reportItem.get("PRICE_TOTAL_T_")));
				activateCardTotal += NumberTool.getInteger(reportItem.get("ACTIVATE_TOTAL_"));
				pointTotal += NumberTool.getInteger(reportItem.get("POINT_TOTAL_"));
				priceDownTotal += NumberTool.doubleT(reportItem.get("PRICE_TOTAL_DOWN_T_"));
				priceUpTotal += NumberTool.getDouble(reportItem.get("PRICE_TOTAL_T_"));
			}
			double profit =priceDownTotal - priceUpTotal;
			Map<String, Object> data = new HashMap<>(6);
			data.put("reportItems", reportItems);
			data.put("activateCardTotal", activateCardTotal);
			data.put("pointTotal", pointTotal);
			data.put("priceDownTotal", priceDownTotal);
			data.put("priceUpTotal", priceUpTotal);
			data.put("profit", profit);
			bean.success(data);
		} catch (Exception e) {
			log.error("卡种分类查询充值卡报表出错", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
