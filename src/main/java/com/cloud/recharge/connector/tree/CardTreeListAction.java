package com.cloud.recharge.connector.tree;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin_price.service.CardAdminPriceService;
import com.cloud.recharge.card_tree.service.CardTreeService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpConfig;

import java.util.List;
import java.util.Map;
/**
 * 获取用户拥有的卡种卡种列表
 *
 * @Author: Dongming
 * @Date: 2020-06-18 17:43
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/card/treeList", method = HttpConfig.Method.GET) public class CardTreeListAction
		extends BaseUserAction {
	@Override public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			List<Map<String, Object>> cardTreeInfos;
			if (CardTool.Type.ADMIN.getUserType().equals(user.getUserType())) {
				cardTreeInfos =  new CardTreeService().listCardTree();
			} else {
				//查出所有下级代理拥有的卡类
				cardTreeInfos = new CardAdminPriceService().listCardTreeByUser(userId);
			}

			for (Map<String, Object> info : cardTreeInfos) {
				info.put("CARD_PRICE_T_", NumberTool.doubleT(info.get("CARD_PRICE_T_")));
			}
			bean.success(cardTreeInfos);
		} catch (Exception e) {
			log.error("获取用户拥有的卡种卡种列表出错", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
