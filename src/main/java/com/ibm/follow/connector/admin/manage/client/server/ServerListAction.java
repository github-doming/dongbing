package com.ibm.follow.connector.admin.manage.client.server;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.follow.servlet.cloud.ibm_sys_monitor_servlet.entity.IbmSysMonitorServlet;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.service.IbmSysServletIpService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 服务器列表
 * @Author: wwj
 * @Date: 2020/5/9 14:17
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/server/list")
public class ServerListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String ip = StringTool.getString(dataMap, "ip", "");
		String serverState = StringTool.getString(dataMap, "serverState", "");

		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(5);
		try {
			PageCoreBean<Map<String, Object>> basePage = new IbmSysServletIpService().listShow(ip, serverState, pageIndex, pageSize);
			data.put("ip", ip);
			data.put("serverState", serverState);
			data.put("rows", basePage.getList());
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
		} catch (Exception e) {
			log.error("服务器信息列表错误", e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}
}
