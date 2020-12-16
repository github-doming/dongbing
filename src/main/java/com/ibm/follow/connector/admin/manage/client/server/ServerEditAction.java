package com.ibm.follow.connector.admin.manage.client.server;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.entity.IbmSysServletIp;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.service.IbmSysServletIpService;
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
@ActionMapping(value = "/ibm/admin/manage/server/edit", method = HttpConfig.Method.POST)
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
			IbmSysServletIpService service = new IbmSysServletIpService();
			IbmSysServletIp entity = service.find(pk);
			entity.setStartTime(new Date(timeStart));
			entity.setStartTimeLong(timeStart);
			entity.setEndTime(new Date(timeEnd));
			entity.setEndTimeLong(timeStart);
			entity.setState(serverState);
			service.update(entity);
			bean.success();
		} catch (Exception e) {
			log.error("服务器信息修改错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
