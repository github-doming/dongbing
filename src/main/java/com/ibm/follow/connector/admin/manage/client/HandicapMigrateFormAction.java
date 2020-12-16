package com.ibm.follow.connector.admin.manage.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口迁移表单
 * @Author: null
 * @Date: 2020-03-24 16:55
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/handicapMigrateForm", method = HttpConfig.Method.GET)
public class HandicapMigrateFormAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			IbmHandicapService handicapService = new IbmHandicapService();
			List<String> clientList = new IbmClientService().findByState(IbmStateEnum.CLOSE.name());

			Map<String, Object> map = new HashMap<>(4);
			map.put("clientList", clientList);
			map.put("hmCodes", handicapService.findHandicapCode(IbmTypeEnum.MEMBER));
			map.put("haCodes", handicapService.findHandicapCode(IbmTypeEnum.AGENT));
			map.put("customers", Arrays.asList(IbmTypeEnum.MEMBER.name(), IbmTypeEnum.AGENT.name()));
			bean.setData(map);
			bean.success();
		} catch (Exception e) {
			log.error("盘口迁移表单错误", e);
			return bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
