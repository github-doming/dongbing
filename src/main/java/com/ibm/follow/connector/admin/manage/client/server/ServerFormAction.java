package com.ibm.follow.connector.admin.manage.client.server;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.service.IbmSysServletIpService;
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
@ActionMapping(value = "/ibm/admin/manage/server/form", method = HttpConfig.Method.GET)
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
			IbmSysServletIpService service = new IbmSysServletIpService();
			Map<String,Object> serverInfo = service.findServerInfo(pk);
			bean.success(serverInfo);
		} catch (Exception e) {
			log.error("获取修改表单错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
