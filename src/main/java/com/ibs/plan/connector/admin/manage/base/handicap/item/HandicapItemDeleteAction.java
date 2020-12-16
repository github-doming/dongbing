package com.ibs.plan.connector.admin.manage.base.handicap.item;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_handicap_item.service.IbspHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 删除盘口详情
 * @Author: null
 * @Date: 2020-03-21 17:29
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/item/del", method = HttpConfig.Method.POST)
public class HandicapItemDeleteAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口详情id
		String handicapItemId = StringTool.getString(dataMap, "handicapItemId", "");

		try {
			IbspHandicapItemService handicapItemService = new IbspHandicapItemService();
			handicapItemService.del(handicapItemId);

			bean.success();
		} catch (Exception e) {
			log.error("删除盘口详情失败", e);
			throw e;
		}
		return bean.toString();
	}
}
