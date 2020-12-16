package com.ibs.plan.connector.admin.manage.base.handicap.item;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_handicap_item.service.IbspHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 盘口详情表单信息
 * @Author: null
 * @Date: 2020-03-21 17:33
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/item/form", method = HttpConfig.Method.GET)
public class HandicapItemFormAction extends CommAdminAction {
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
			Map<String, Object> itemInfo = handicapItemService.findInfo(handicapItemId);
			itemInfo.remove("HANDICAP_ID_");
			itemInfo.put("HANDICAP_ITEM_ID_", handicapItemId);

			bean.setData(itemInfo);
			bean.success();
		} catch (Exception e) {
			log.error("盘口详情表单信息失败", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
