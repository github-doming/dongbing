package com.ibm.follow.connector.admin.card.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
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
 * 充值卡管理员信息
 *
 * @Author: Dongming
 * @Date: 2020-04-10 19:12
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/agent/info")
public class CardAgentInfoAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			// 加载代理资料
			Map<String, Object> adminInfo = new IbmCardAdminService().findAdminShowInfo(adminUserId);
			if (ContainerTool.isEmpty(adminInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			List<Map<String, Object>> adminPrices;
			// 代理或股东默认卡种价格
			if (IbmTypeEnum.cardType(adminInfo.get("USER_TYPE_"))) {
				adminPrices = new IbmCardAdminPriceService().listInfo(adminUserId, IbmStateEnum.DEF.name());;
			} else {
				// 管理员卡种价格
				adminPrices = new IbmCardTreeService().listPrice();
			}

			if (ContainerTool.notEmpty(adminPrices)) {
				for (Map<String, Object> prices : adminPrices) {
					prices.put("CARD_PRICE_", NumberTool.doubleT(prices.remove("CARD_PRICE_T_")));
				}
			}
			Map<String, Object> data = new HashMap<>();
			data.put("adminInfo", adminInfo);
			data.put("adminPrices", adminPrices);
			bean.success(data);
		} catch (Exception e) {
			log.error("下级管理列表出错", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
