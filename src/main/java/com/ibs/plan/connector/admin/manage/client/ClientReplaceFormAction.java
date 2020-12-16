package com.ibs.plan.connector.admin.manage.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.List;

/**
 * @Description: 客户机切换表单
 * @Author: null
 * @Date: 2020-03-24 17:16
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/clientReplaceForm", method = HttpConfig.Method.GET)
public class ClientReplaceFormAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			IbspClientService clientService = new IbspClientService();
			List<String> clientList = clientService.findByState(IbsStateEnum.CLOSE.name());

			bean.setData(clientList);
			bean.success();
		} catch (Exception e) {
			log.error("客户机切换表单错误", e);
			return bean.error(e.getMessage());
		}
		return bean;
	}
}
