package com.ibm.follow.connector.pc.agent;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHaController;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * 删除账户
 * @Author: Dongming
 * @Date: 2020-04-21 10:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/agent/deleteAccount", method = HttpConfig.Method.POST) public class HandicapAccountDelAction
		extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();
		if (StringTool.isEmpty(handicapAgentId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			IbmHandicapAgentService handicapAgentService=new IbmHandicapAgentService();
			String handicapId = handicapAgentService.findHandicapId(handicapAgentId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			//会员登出状态
			logoutHappening(handicapAgentId);

			String desc = this.getClass().getSimpleName().concat(",").concat(appUser.getNickname()).concat(",删除代理信息:")
					.concat(handicapAgentId);

			//删除盘口代理信息

			handicapAgentService.delHaInfo(handicapAgentId,desc);

			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("获取盘口代理的盘口信息错误"),e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	private void logoutHappening(String handicapAgentId) throws Exception {

		String loginState = new IbmHaInfoService().findLoginState(handicapAgentId);
		if (StringTool.notEmpty(loginState) && IbmStateEnum.LOGIN.name().equals(loginState)) {
			//用户登出清理数据
			new LogoutHaController().execute(handicapAgentId);
		}
	}
}
