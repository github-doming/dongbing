package com.ibm.follow.connector.admin.card.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.service.IbmCardAdminPriceService;
import com.ibm.follow.servlet.cloud.ibm_card_tree.service.IbmCardTreeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上级给自己设置的价格列表
 *
 * @Author: Dongming
 * @Date: 2020-04-11 17:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/agent/price")
public class CardAgentPriceAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			//加载查询人资料
			IbmCardAdminService cardAdminService = new IbmCardAdminService();
			Map<String, Object> adminInfo = cardAdminService.findAdminInfo(adminUserId);
			if (ContainerTool.isEmpty(adminInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			List<Map<String, Object>> adminPrices;
			// 代理或股东默认卡种价格
			if (IbmTypeEnum.cardType(adminInfo.get("USER_TYPE_"))) {
				adminPrices = new IbmCardAdminPriceService().listUserDef(adminUserId);
			} else {
				// 管理员卡种价格
				adminPrices = new IbmCardTreeService().listPrice();
			}

			if (ContainerTool.notEmpty(adminPrices)) {
				for (Map<String, Object> prices : adminPrices) {
					prices.put("CARD_PRICE_", NumberTool.doubleT(prices.remove("CARD_PRICE_T_")));
				}
			}
			bean.success(adminPrices);

		} catch (Exception e) {
			log.error("下级管理列表出错", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
