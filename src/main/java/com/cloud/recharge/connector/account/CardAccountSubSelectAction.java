package com.cloud.recharge.connector.account;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.List;
import java.util.Map;

/**
 * 管理员/股东 获取子级的信息
 *
 * @Author: Dongming
 * @Date: 2020-06-19 15:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/account/subSelect", method = HttpConfig.Method.GET)
public class CardAccountSubSelectAction
		extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			CardAdminService adminService = new CardAdminService();
			List<Map<String, Object>> agentInfos;
			if (CardTool.Type.isAdmin(user.getUserType())) {
				agentInfos = adminService.listAllAgentInfo();
			} else {
				agentInfos = adminService.listAgentInfo(userId);
			}
			bean.success(agentInfos);
		} catch (Exception e) {
			log.error("下级管理列表出错", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;

	}
}
