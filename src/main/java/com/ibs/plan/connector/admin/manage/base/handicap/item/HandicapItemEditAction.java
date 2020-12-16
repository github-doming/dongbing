package com.ibs.plan.connector.admin.manage.base.handicap.item;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_handicap_item.entity.IbspHandicapItem;
import com.ibs.plan.module.cloud.ibsp_handicap_item.service.IbspHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 盘口详情修改
 * @Author: null
 * @Date: 2020-03-21 17:36
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/item/edit", method = HttpConfig.Method.POST)
public class HandicapItemEditAction extends CommAdminAction {
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
		String handicapUrl = StringTool.getString(dataMap, "handicapUrl", "");
		String handicapCaptcha = StringTool.getString(dataMap, "handicapCaptcha", "");
		try {
			IbspHandicapItemService handicapItemService = new IbspHandicapItemService();
			IbspHandicapItem handicapItem = handicapItemService.find(handicapItemId);
			if (handicapItem == null) {
				bean.putEnum(CodeEnum.IBS_404_HANDICAP_ITEM);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			handicapItem.setHandicapUrl(handicapUrl);
			handicapItem.setHandicapCaptcha(handicapCaptcha);
			handicapItem.setUpdateTime(new Date());
			handicapItem.setUpdateTimeLong(System.currentTimeMillis());
			handicapItemService.update(handicapItem);

			bean.success();
		} catch (Exception e) {
			log.error("盘口详情修改失败", e);
			throw e;
		}
		return bean.toString();
	}
}
