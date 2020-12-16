package com.ibs.plan.connector.admin.manage.client.server;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_sys_servlet_ip.service.IbspSysServletIpService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 获取修改表单
 * @Author: wwj
 * @Date: 2020/5/9 15:42
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/server/form", method = HttpConfig.Method.GET)
public class ServerFormAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String pk = StringTool.getString(dataMap, "IBM_SYS_SERVLET_IP_ID_", "");

		try {
			IbspSysServletIpService service = new IbspSysServletIpService();
			Map<String,Object> serverInfo = service.findServerInfo(pk);
			bean.success(serverInfo);
		} catch (Exception e) {
			log.error("获取修改表单错误", e);
			throw e;
		}
		return bean;
	}
}
