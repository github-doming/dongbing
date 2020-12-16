package com.ibm.follow.connector.pc.home;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 首页，点击代理盘口
 * @Author: Dongming
 * @Date: 2019-08-30 14:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/home/clickAgentHandicap", method = HttpConfig.Method.GET) public class AgentClickHandicap
		extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		Object handicapCode = dataMap.getOrDefault("HANDICAP_CODE_", "");
		if (StringTool.isEmpty(handicapCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			String handicapId =HandicapUtil.id(handicapCode.toString(),IbmTypeEnum.AGENT);
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}

			//获取在线代理主键数组
			String onlineAgentIds = new IbmHaUserService().findOnlineAgentIds(appUserId, handicapId);
			IbmHaInfoService haInfoService = new IbmHaInfoService();
			Map<String, Object> data = new HashMap<>(2);
			List<Map<String, Object>> offlineInfo;
			if (StringTool.notEmpty(onlineAgentIds)) {
				//已托管代理
				List<Map<String, Object>> onlineInfo = haInfoService.listOnlineInfoByHaIds(onlineAgentIds.split(","));
				data.put("onHostingInfo", onlineInfo);
				//未托管代理
				offlineInfo = haInfoService.listOfflineInfo(appUserId, handicapId,
						ContainerTool.getValSet4Key(onlineInfo, "HANDICAP_AGENT_ID_"));
			} else {
				offlineInfo = haInfoService.listOfflineInfo(appUserId, handicapId, null);
			}
			data.put("offHostingInfo", offlineInfo);
			bean.setData(data);
			bean.success();

		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("点击代理盘口错误"),e);
			bean.error(e.getMessage());
		}
		return bean;

	}
}
