package com.ibs.plan.connector.admin.manage.base.handicap.item;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_handicap.entity.IbspHandicap;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_handicap_item.service.IbspHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 盘口详情列表
 * @Author: null
 * @Date: 2020-03-21 17:21
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/item/list")
public class HandicapItemListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口id
		String handicapId = StringTool.getString(dataMap, "handicapId", "");

		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(5);
		try {
			IbspHandicapService handicapService = new IbspHandicapService();
			IbspHandicap handicap = handicapService.find(handicapId);
			if (handicap == null) {
				bean.putEnum(CodeEnum.IBS_404_HANDICAP);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			IbspHandicapItemService handicapItemService = new IbspHandicapItemService();
			PageCoreBean<Map<String, Object>> basePage = handicapItemService.findHandicapItem(handicapId, pageIndex, pageSize);

			data.put("HANDICAP_CODE_", handicap.getHandicapCode());
			data.put("rows", basePage.getList());
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
		} catch (Exception e) {
			log.error("盘口详情列表失败", e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}
}
