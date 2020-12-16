package com.ibm.follow.connector.admin.manage.client.handicap;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 盘口容量列表信息
 * @Author: null
 * @Date: 2020-03-24 16:47
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/handicap/list")
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

			PageCoreBean basePage = new IbmClientHandicapCapacityService().findByClientCode(clientCode, pageIndex, pageSize);

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
