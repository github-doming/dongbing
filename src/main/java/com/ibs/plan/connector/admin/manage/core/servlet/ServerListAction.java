package com.ibs.plan.connector.admin.manage.core.servlet;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_sys_monitor_servlet.service.IbspSysMonitorServletService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;


/**
 * @Description: 服务器信息列表
 * @Author: null
 * @Date: 2020-05-14 11:14
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/core/server/list")
public class ServerListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String ip = StringTool.getString(dataMap, "ip", "");
		try {
			IbspSysMonitorServletService monitorServletService = new IbspSysMonitorServletService();
			List<Map<String, Object>> infos = monitorServletService.listShow(ip);

			bean.success(infos);
		} catch (Exception e) {
			log.error("服务器信息列表错误", e);
			return bean.error(e.getMessage());
		}
		return bean;
	}
}
