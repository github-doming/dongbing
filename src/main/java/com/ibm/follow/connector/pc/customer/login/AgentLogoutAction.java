package com.ibm.follow.connector.pc.customer.login;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHaController;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 验证登出
 * @Author: zjj
 * @Date: 2019-09-09 10:15
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/logout/agent", method = HttpConfig.Method.GET) public class AgentLogoutAction
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
			return super.returnJson(bean);
		}
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }

		try {
			String handicapId = new IbmHandicapAgentService().findHandicapId(handicapAgentId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			IbmHaInfoService haInfoService = new IbmHaInfoService();
			if (!haInfoService.isLogin(handicapAgentId)){
				bean.putEnum(IbmCodeEnum.IBM_404_CUSTOMER_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			//用户登出清理数据
			new LogoutHaController().execute(handicapAgentId);
			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("登出失败"),e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
