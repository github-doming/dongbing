package com.cloud.recharge.connector.account;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_tree.service.CardTreeService;
import com.cloud.recharge.connector.core.BaseUserAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取个人或子代理信息 - 显示自己设置的数据
 *
 * @Author: Dongming
 * @Date: 2020-06-17 18:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/account/subInfo", method = HttpConfig.Method.GET)
public class CardAccountSubInfoAction
		extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String subUserId = StringTool.getString(dataMap, "subUserId", "");
		if (StringTool.isEmpty(subUserId)) {
			subUserId = userId;
		}
		try {
			//加载查询人资料
			CardAdminService adminService = new CardAdminService();
			if (adminService.hasEditPermission(bean, subUserId, userId)) {
				return bean;
			}
			Map<String, Object> result = (Map<String, Object>) bean.getData();
			//加载子代理信息
			List<Map<String, Object>> subAdminPrices;
			boolean isAdmin = (boolean) result.get("isAdmin"), isSelf = (boolean) result.get("isSelf");
			if (isAdmin && isSelf) {
				// 管理员查卡种价格
				subAdminPrices = new CardTreeService().listPrice();
			} else {
				if (isAdmin) {
					userId = null;
				}
				if (isSelf) {
					subUserId = StateEnum.DEF.name();
				}
				subAdminPrices = new CardAdminPriceService().listInfo(userId, subUserId, isAdmin);
			}
			for (Map<String, Object> prices : subAdminPrices) {
				prices.put("CARD_PRICE_", NumberTool.doubleT(prices.remove("CARD_PRICE_T_")));
			}

			Map<String, Object> data = new HashMap<>(2);
			data.put("subAdminInfo", result.get("subInfo"));
			data.put("subAdminPrices", subAdminPrices);
			bean.success(data);
		} catch (Exception e) {
			log.error("获取个人或子代理信息出错", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
