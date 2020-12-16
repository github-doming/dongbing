package com.ibs.plan.connector.admin.manage.client.handicap;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_client_handicap_capacity.service.IbspClientHandicapCapacityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 盘口容量列表信息
 * @Author: null
 * @Date: 2020-03-24 16:47
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/handicap/list")
public class HandicapCapacityListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String clientCode = dataMap.getOrDefault("clientCode", "").toString();

		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(3);
		try {

			PageCoreBean basePage = new IbspClientHandicapCapacityService().findByClientCode(clientCode, pageIndex, pageSize);

			data.put("rows", basePage.getList());
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
		} catch (Exception e) {
			log.error("盘口容量列表信息错误", e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}
}
