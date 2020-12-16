package com.cloud.recharge.connector.report;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_recharge.service.CardRechargeService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 查询特殊卡种的报表
 *
 * @Author: Dongming
 * @Date: 2020-06-22 11:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/reportListSpecial")
public class CardReportListSpecialAction extends BaseUserAction {
	@Override public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		String cardTreeId = StringTool.getString(dataMap.get("cardTreeId"), "");
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
			String ownerUserId=userId;
			if (CardTool.Type.ADMIN.getUserType().equals(user.getUserType())) {
				ownerUserId=CardTool.Type.ADMIN.name();
			}

			//自己创建的特殊开卡
			CardRechargeService rechargeService = new CardRechargeService();
			List<Map<String, Object>> createReports = rechargeService
					.listSpecialReport4Create(userId,ownerUserId, cardTreeId, startTime, endTime);
			Map<String, Object> createData = new HashMap<>(3);
			int pointTotal = 0;
			double priceDownTotal = 0;
			double price;
			for (Map<String, Object> createReport : createReports) {
				price = NumberTool.doubleT(createReport.get("PRICE_T_"));
				pointTotal += NumberTool.getInteger(createReport.get("POINT_TOTAL_"));
				priceDownTotal += price;

				createReport.put("PRICE_T_",price);

			}
			createData.put("pointTotal", pointTotal);
			createData.put("priceDownTotal", priceDownTotal);
			createData.put("reports", createReports);

			//自己拥有的特殊开卡
			List<Map<String, Object>> ownerReports = rechargeService
					.listSpecialReport4Owner(userId, cardTreeId, startTime, endTime);
			Map<String, Object> ownerData = new HashMap<>(3);
			pointTotal = 0;
			priceDownTotal = 0;
			for (Map<String, Object> ownerReport : ownerReports) {
				price = NumberTool.doubleT(ownerReport.get("PRICE_T_"));
				pointTotal += NumberTool.getInteger(ownerReport.get("POINT_TOTAL_"));
				priceDownTotal += price;

				ownerReport.put("PRICE_T_",price);

			}
			ownerData.put("pointTotal", pointTotal);
			ownerData.put("priceDownTotal", priceDownTotal);
			ownerData.put("reports", ownerReports);

			Map<String,Object> data = new HashMap<>(2);
			data.put("createData",createData);
			data.put("ownerData",ownerData);
			bean.success(data);
		} catch (Exception e) {
			log.error("代理分类查询充值卡报表出错", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
