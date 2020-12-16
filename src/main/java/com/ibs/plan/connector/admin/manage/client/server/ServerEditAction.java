package com.ibs.plan.connector.admin.manage.client.server;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_sys_servlet_ip.entity.IbspSysServletIp;
import com.ibs.plan.module.cloud.ibsp_sys_servlet_ip.service.IbspSysServletIpService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 服务器修改
 * @Author: wwj
 * @Date: 2020/5/9 14:17
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/server/edit", method = HttpConfig.Method.POST)
public class ServerEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String pk = StringTool.getString(dataMap, "IBM_SYS_SERVLET_IP_ID_", "");
		String serverState = StringTool.getString(dataMap, "serverState", "");
		long timeStart = NumberTool.getLong(dataMap.get("timeStart"), 0L);
		long timeEnd = NumberTool.getLong(dataMap.get("timeEnd"), 0L);
		try {
			IbspSysServletIpService service = new IbspSysServletIpService();
			IbspSysServletIp entity = service.find(pk);
			entity.setStartTime(new Date(timeStart));
			entity.setStartTimeLong(timeStart);
			entity.setEndTime(new Date(timeEnd));
			entity.setEndTimeLong(timeStart);
			entity.setState(serverState);
			service.update(entity);
			bean.success();
		} catch (Exception e) {
			log.error("服务器信息修改错误", e);
			throw e;
		}
		return bean;
	}
}
