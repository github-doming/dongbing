package com.ibs.plan.connector.admin.manage.core.client;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_sys_monitor_client.service.IbspSysMonitorClientService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 客户端详情列表
 * @Author: null
 * @Date: 2020-05-13 14:44
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/core/client/list")
public class ClientItemListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String ip = StringTool.getString(dataMap, "ip", "");
		String state = StringTool.getString(dataMap, "state", "");

		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(5);
		try {

			//获取所有客户机
			PageCoreBean<Map<String, Object>> basePage = new IbspSysMonitorClientService().listShow(ip, state, pageIndex, pageSize);
			List<Map<String, Object>> rows = basePage.getList();
			for (Map<String, Object> row : rows) {
				row.put("JVM_USED", NumberTool.getLong(row.get("JVM_TOTAL_")) - NumberTool.getLong(row.get("JVM_FREE_")));
			}

			data.put("ip", ip);
			data.put("state", state);
			data.put("rows", rows);
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
		} catch (Exception e) {
			log.error("客户端详情列表错误", e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}
}
