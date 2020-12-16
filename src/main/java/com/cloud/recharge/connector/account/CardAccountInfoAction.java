package com.cloud.recharge.connector.account;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_tree.service.CardTreeService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值卡管理员信息 - 上级设置给自己的信息 - 和自己设置的默认信息
 *
 * @Author: Dongming
 * @Date: 2020-06-18 16:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/account/info", method = HttpConfig.Method.GET)
public class CardAccountInfoAction
		extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			// 加载代理资料
			Map<String, Object> adminInfo = new CardAdminService().findAdminInfo(userId);
			adminInfo.put("USER_TYPE_", getUserTypeCh(adminInfo.get("USER_TYPE_").toString()));
			if (CardTool.Type.ADMIN.name().equals(adminInfo.remove("PARENT_USER_ID_"))) {
				adminInfo.put("PARENT_USER_NAME_", CardTool.Type.ADMIN.name());
			}
			List<Map<String, Object>> adminPrices, parentPrices;
			// 代理或股东默认卡种价格
			if (!CardTool.Type.ADMIN.getUserType().equals(user.getUserType())) {
				//个人默认价格
				adminPrices = new CardAdminPriceService().listInfo(userId, StateEnum.DEF.name(), false);
				//上级设置价格
				parentPrices = new CardAdminPriceService().listInfo(null, userId, false);
				parentPrices.forEach(prices -> prices.put("CARD_PRICE_", NumberTool.doubleT(prices.remove("CARD_PRICE_T_"))));
			} else {
				// 管理员卡种价格
				adminPrices = new CardTreeService().listPrice();
				parentPrices = new ArrayList<>(adminPrices);
			}
			adminPrices.forEach(prices -> prices.put("CARD_PRICE_", NumberTool.doubleT(prices.remove("CARD_PRICE_T_"))));

			Map<String, Object> data = new HashMap<>(3);
			data.put("adminInfo", adminInfo);
			data.put("adminPrices", adminPrices);
			data.put("parentPrices", parentPrices);
			bean.success(data);
		} catch (Exception e) {
			log.error("充值卡管理员信息", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private String getUserTypeCh(String userType) {
		switch (userType) {
			case "CARD_ADMIN":
				return CardTool.Type.ADMIN.getName();
			case "CARD_AGENT":
				return CardTool.Type.AGENT.getName();
			default:
				return CardTool.Type.PARTNER.getName();
		}
	}

}
