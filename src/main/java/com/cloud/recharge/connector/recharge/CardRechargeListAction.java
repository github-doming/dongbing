package com.cloud.recharge.connector.recharge;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
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
 * 查询点卡列表
 *
 * @Author: Dongming
 * @Date: 2020-06-19 14:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/list")
public class CardRechargeListAction extends BaseUserAction {
	@Override public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}

		String cardPassword = StringTool.getString(dataMap.get("cardPassword"), "");
		String cardTreeId = StringTool.getString(dataMap.get("cardTreeId"), "");
		String cardState = StringTool.getString(dataMap.get("cardState"), "");

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

		Map<String, Object> data = new HashMap<>(3);
		try {
			if (CardTool.Type.ADMIN.getUserType().equals(user.getUserType())) {
				userId = null;
			}
			PageCoreBean<Map<String, Object>> basePage = new CardRechargeService()
					.listCard(cardTreeId, cardState, cardPassword, userId, startTime, endTime, pageIndex, pageSize);
			List<Map<String,Object>> cardList = basePage.getList();
			for(Map<String,Object> card:cardList){
				if("直充".equals(card.get("CARD_TREE_NAME_").toString())){
					card.put("CARD_PASSWORD_","-------------------------");
				}
			}
			//回传数据
			data.put("rows", basePage.getList());
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
		} catch (Exception e) {
			log.error("查询点卡列表出错", e);
			data.clear();
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}
}
