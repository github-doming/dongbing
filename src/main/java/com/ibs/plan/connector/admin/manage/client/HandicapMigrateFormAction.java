package com.ibs.plan.connector.admin.manage.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口迁移表单
 * @Author: null
 * @Date: 2020-03-24 16:55
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/handicapMigrateForm", method = HttpConfig.Method.GET)
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
			IbspHandicapService handicapService = new IbspHandicapService();
			List<String> clientList = new IbspClientService().findByState(IbsStateEnum.CLOSE.name());

			Map<String, Object> map = new HashMap<>(3);
			map.put("clientList", clientList);
			map.put("hmCodes", handicapService.findHandicapCode());
			map.put("customers", IbsTypeEnum.MEMBER.name());
			bean.setData(map);
			bean.success();
		} catch (Exception e) {
			log.error("盘口迁移表单错误", e);
			return bean.error(e.getMessage());
		}
		return bean;
	}
}
