package com.cloud.recharge.connector.report;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_recharge_daily.service.CardRechargeDailyService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * 卡种分类查询充值卡详情
 *
 * @Author: Dongming
 * @Date: 2020-06-22 11:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/reportItemCard")
public class CardReportItemCardAction extends BaseUserAction {
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
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(cardTreeId)) {
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
		Map<String, Object> data = new HashMap<>(3);
		try {
			if (CardTool.Type.isAdmin(user.getUserType())) {
				userId = CardTool.Type.ADMIN.name();
			}
			List<String> subUserIds=new ArrayList<>();
			CardRechargeDailyService rechargeDailyService=new CardRechargeDailyService();
			PageCoreBean<Map<String, Object>> pageCoreBean;
			List<Map<String,Object>> onselfReportItem=new ArrayList<>();
			List<String> parentUserIds=new ArrayList<>();
			if (StringTool.isEmpty(agentId)) {
				parentUserIds.add(userId);
				if (CardTool.Type.ADMIN.getUserType().equals(user.getUserType())) {
					parentUserIds.add(CardTool.Type.ADMIN.name());
				}
				subUserIds.add(userId);
				pageCoreBean =rechargeDailyService
						.listItem4Card(cardTreeId, subUserIds, startTime, endTime,pageIndex,pageSize,"card_recharge_daily");
				onselfReportItem = pageCoreBean.getList();
				subUserIds = new CardAdminService().listSubUserId(parentUserIds);
			}else{
				parentUserIds.add(agentId);
				subUserIds.add(agentId);
			}

			Map<String,Object> subUserInfo = new CardAdminService().subUserInfo(parentUserIds);
			pageCoreBean = rechargeDailyService
					.listItem4Card(cardTreeId, subUserIds, startTime, endTime,pageIndex,pageSize,"card_recharge_daily_sum");
			List<Map<String,Object>> reportItems = pageCoreBean.getList();
			reportItems.addAll(onselfReportItem);
			for (Map<String, Object> reportItem : reportItems) {
				reportItem.put("PRICE_TOTAL_T_", NumberTool.doubleT(reportItem.get("PRICE_TOTAL_T_")));
				reportItem.put("USER_ALIAS_",subUserInfo.get(reportItem.get("USER_ID_")));
			}
			data.put("rows", reportItems);
			data.put("index", pageIndex);
			data.put("total", reportItems.size());

		} catch (Exception e) {
			log.error("代理分类查询充值卡报表出错", e);
			data.put("rows", null);
			data.put("index", pageIndex);
			data.put("total", null);
		}
		return data;
	}
}
