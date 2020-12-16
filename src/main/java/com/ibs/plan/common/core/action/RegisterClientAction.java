package com.ibs.plan.common.core.action;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_client.entity.IbspClient;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import com.ibs.plan.module.cloud.ibsp_client_capacity.entity.IbspClientCapacity;
import com.ibs.plan.module.cloud.ibsp_client_capacity.service.IbspClientCapacityService;
import com.ibs.plan.module.cloud.ibsp_client_handicap_capacity.entity.IbspClientHandicapCapacity;
import com.ibs.plan.module.cloud.ibsp_client_handicap_capacity.service.IbspClientHandicapCapacityService;
import com.ibs.plan.module.cloud.ibsp_config.service.IbspConfigService;
import com.ibs.plan.module.cloud.ibsp_sys_monitor_client.entity.IbspSysMonitorClient;
import com.ibs.plan.module.cloud.ibsp_sys_monitor_client.service.IbspSysMonitorClientService;
import com.ibs.plan.module.cloud.ibsp_sys_servlet_ip.service.IbspSysServletIpService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;
/**
 * 客户端模块注册
 *
 * @Author: Dongming
 * @Date: 2020-05-19 10:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/cloud/manage/registerClient", method = HttpConfig.Method.GET)
public class RegisterClientAction extends BaseClientAction {
	@Override protected Object execute() throws Exception {
		Object clientCode = jsonData.getOrDefault("clientCode", "");
		Object clientIp = jsonData.getOrDefault("ip", "");
		Object clientType = jsonData.getOrDefault("clientType", "");
		JSONObject capacity = jsonData.getJSONObject("capacity");
		if (StringTool.isEmpty(clientCode, clientIp,clientType)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			IbspClientService clientService = new IbspClientService();
			String clientId;
			Date nowTime = new Date();
			//编码已存在
			Map<String, Object> info = clientService.findInfo(clientCode);
			if (ContainerTool.notEmpty(info)) {
				String state = info.get("STATE_").toString();
				if (IbsStateEnum.OPEN.name().equals(state)) {
					//已存在
					bean.putEnum(CodeEnum.IBS_403_EXIST);
					bean.putSysEnum(CodeEnum.CODE_403);
					return bean;
				} else if (IbsStateEnum.STOP.name().equals(state) || IbsStateEnum.CANCEL.name().equals(state)) {
					//激活客户端
					clientService.activateClient(clientCode,clientType,nowTime);
					clientId = info.get("IBSP_CLIENT_ID_").toString();
				} else {
					//其他的错误
					bean.putEnum(CodeEnum.IBS_403_ERROR);
					bean.putSysEnum(CodeEnum.CODE_403);
					return bean;
				}
			} else {
				IbspSysServletIpService servletIpService = new IbspSysServletIpService();
				Map<String, Object> servletInfo = servletIpService.findServletInfo(clientIp);
				if (ContainerTool.isEmpty(servletInfo) || !IbsStateEnum.OPEN.equal(servletInfo.get("STATE_"))) {
					bean.putEnum(CodeEnum.IBS_404_DATA);
					bean.putSysEnum(CodeEnum.CODE_404);
					return bean;
				}
				String registerId = servletInfo.get("IBSP_SYS_SERVLET_IP_ID_").toString();

				//注册
				IbspClient client = new IbspClient();
				client.setRegisterIpId(registerId);
				client.setRegisterIp(clientIp);
				client.setClientCode(clientCode);
				client.setClientType(clientType);
				client.setCreateTime(nowTime);
				client.setCreateTimeLong(System.currentTimeMillis());
				client.setUpdateTimeLong(System.currentTimeMillis());
				client.setState(IbsStateEnum.OPEN.name());
				clientId = clientService.save(client);

				IbspSysMonitorClient monitorClient = new IbspSysMonitorClient();
				monitorClient.setClientCode(clientCode);
				monitorClient.setServerIp(clientIp);
				monitorClient.setCreateTime(nowTime);
				monitorClient.setCreateTimeLong(System.currentTimeMillis());
				monitorClient.setUpdateTime(nowTime);
				monitorClient.setUpdateTimeLong(System.currentTimeMillis());
				monitorClient.setState(IbsStateEnum.OPEN.name());
				new IbspSysMonitorClientService().save(monitorClient);
			}
			if (StringTool.notEmpty(capacity)) {
				// 保存客户端容量信息
				saveCapacity(clientCode, capacity, nowTime, clientId);
			}
			//发送封盘时间信息到客户端
			JSONObject content=new JSONObject();
			content.put("sealTime",new IbspConfigService().sealTime());
			bean.setData(content);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "客户端模块注册" + e.getMessage());
			throw e;
		}
		return bean.success();
	}

	/**
	 * 保存客户端容量信息
	 *
	 * @param clientCode 客户端编码
	 * @param capacity   客户端容量
	 * @param nowTime    更新时间
	 * @param clientId   客户端ID
	 */
	private void saveCapacity(Object clientCode, JSONObject capacity, Date nowTime, String clientId) throws Exception {
		//初始化容量信息
		IbspClientCapacity clientCapacity = new IbspClientCapacity();
		clientCapacity.setClientId(clientId);
		clientCapacity.setClientCode(clientCode);
		clientCapacity.setClientCapacityMax(NumberTool.getInteger(capacity.remove("CAPACITY")));
		clientCapacity.setClientCapacity(0);
		clientCapacity.setCreateTime(nowTime);
		clientCapacity.setCreateTimeLong(System.currentTimeMillis());
		clientCapacity.setUpdateTimeLong(System.currentTimeMillis());
		clientCapacity.setState(IbsStateEnum.OPEN.name());
		String clientCapacityId = new IbspClientCapacityService().save(clientCapacity);

		//初始化盘口容量信息
		IbspClientHandicapCapacityService clientHandicapCapacityService = new IbspClientHandicapCapacityService();
		IbspClientHandicapCapacity clientHandicapCapacity = new IbspClientHandicapCapacity();
		clientHandicapCapacity.setClientCapacityId(clientCapacityId);
		clientHandicapCapacity.setClientId(clientId);
		clientHandicapCapacity.setClientCode(clientCode);
		clientHandicapCapacity.setCapacityHandicap(0);
		clientHandicapCapacity.setCreateTime(nowTime);
		clientHandicapCapacity.setCreateTimeLong(System.currentTimeMillis());
		clientHandicapCapacity.setUpdateTimeLong(System.currentTimeMillis());
		clientHandicapCapacity.setState(IbsStateEnum.OPEN.name());
		for (Map.Entry<String, Object> entry : capacity.entrySet()) {
			clientHandicapCapacity.setHandicapCode(entry.getKey());
			clientHandicapCapacity.setCapacityHandicapMax(NumberTool.getInteger(entry.getValue()));
			clientHandicapCapacityService.save(clientHandicapCapacity);
		}

	}
}
