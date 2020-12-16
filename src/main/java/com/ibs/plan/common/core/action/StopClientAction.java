package com.ibs.plan.common.core.action;

import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import com.ibs.plan.module.cloud.ibsp_client_capacity.service.IbspClientCapacityService;
import com.ibs.plan.module.cloud.ibsp_client_handicap_capacity.service.IbspClientHandicapCapacityService;
import com.ibs.plan.module.cloud.ibsp_client_heartbeat.service.IbspClientHeartbeatService;
import com.ibs.plan.module.cloud.ibsp_sys_monitor_client.service.IbspSysMonitorClientService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;
/**
 * 客户端模块停止
 * @Author: Dongming
 * @Date: 2020-05-22 10:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/cloud/manage/stopClient", method = HttpConfig.Method.GET)
public class StopClientAction extends BaseClientAction {
	@Override protected Object execute() throws Exception {
		Object clientCode = jsonData.getOrDefault("clientCode", "");
		Object clientIp = jsonData.getOrDefault("ip", "");
		if (StringTool.isEmpty(clientCode,clientIp)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			IbspClientService clientService = new IbspClientService();
			Map<String, Object> clientInfo = 	clientService.findInfo(clientCode,clientIp);
			//客户端不存在
			if (ContainerTool.isEmpty(clientInfo)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Date nowTime = new Date();
			//  注销客户端
			Object clientId = clientInfo.getOrDefault("IBSP_CLIENT_ID_", "");
			clientService.cancelClient(clientId, nowTime, IbsStateEnum.STOP);


			//清理容量
			new IbspClientCapacityService().cancelClient(clientId, nowTime);
			new IbspClientHandicapCapacityService().cancelClient(clientId, nowTime);
			new IbspClientHeartbeatService().cancelClient(clientCode,nowTime);
			new IbspSysMonitorClientService().cancelClient(clientCode,nowTime);


		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "客户端模块停止" + e.getMessage());
			throw e;
		}
		return bean.success();
	}
}
