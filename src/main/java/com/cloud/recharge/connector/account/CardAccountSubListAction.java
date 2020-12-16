package com.cloud.recharge.connector.account;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 充值卡管理下级列表
 *
 * @Author: Dongming
 * @Date: 2020-06-17 17:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/account/subList", method = HttpConfig.Method.GET)
public class CardAccountSubListAction
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
				agentInfos = new ArrayList<>();
				agentInfos(adminService, agentInfos, userId);
			}
			bean.success(agentInfos);
		} catch (Exception e) {
			log.error("下级管理列表出错", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	/**
	 * 获取子代理信息
	 *
	 * @param adminService 服务类
	 * @param agentInfos   回传列表
	 * @param userId       查询父id
	 */
	private void agentInfos(CardAdminService adminService, List<Map<String, Object>> agentInfos, String userId)
			throws SQLException {
		List<Map<String, Object>> childAgentInfo = adminService.listAgentInfo(userId);
		if (ContainerTool.notEmpty(childAgentInfo)) {
			agentInfos.addAll(childAgentInfo);
			for (Map<String, Object> agentInfo : childAgentInfo) {
				agentInfos(adminService, agentInfos, agentInfo.get("APP_USER_ID_").toString());
			}
		}
	}
}
