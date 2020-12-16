package com.ibm.follow.connector.admin.manage.core.servlet;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.servlet.cloud.ibm_sys_monitor_servlet.service.IbmSysMonitorServletService;
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
@ActionMapping(value = "/ibm/admin/manage/core/server/list")
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
			IbmSysMonitorServletService monitorServletService = new IbmSysMonitorServletService();
			List<Map<String, Object>> infos = monitorServletService.listShow(ip);

			bean.success(infos);
		} catch (Exception e) {
			log.error("服务器信息列表错误", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
