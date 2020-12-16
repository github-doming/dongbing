package com.ibs.plan.connector.admin.manage.base.price;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_sys_card_price.service.IbspSysCardPriceService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.List;
import java.util.Map;

/**
 * @Description: 点数价格列表
 * @Author: null
 * @Date: 2020-08-01 14:56
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/points/list" ,method = HttpConfig.Method.GET)
public class CardPriceListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			IbspSysCardPriceService cardPriceService=new IbspSysCardPriceService();
			List<Map<String, Object>> pointsInfos=cardPriceService.findAllInfo();

			bean.success(pointsInfos);
		} catch (Exception e) {
			log.error("获取点数价格列表出错", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
