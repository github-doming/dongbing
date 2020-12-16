package com.ibs.plan.connector.admin.manage.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_client_capacity.service.IbspClientCapacityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 客户端修改表单
 * @Author: null
 * @Date: 2020-03-24 16:39
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/form", method = HttpConfig.Method.GET)
public class ClientFormAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String clientCode = StringTool.getString(dataMap, "clientCode", "");
		try {
			Map<String, Object> map = new IbspClientCapacityService().findcapacityInfo(clientCode);

			bean.setData(map);
			bean.success();
		} catch (Exception e) {
			log.error("客户端修改表单错误", e);
			return bean.error(e.getMessage());
		}
		return bean;
	}
}
