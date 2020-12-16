package com.ibm.follow.servlet.cloud.ibm_client.action;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommBaseAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_client.entity.IbmClient;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_capacity.entity.IbmClientCapacity;
import com.ibm.follow.servlet.cloud.ibm_client_capacity.service.IbmClientCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.entity.IbmClientHandicapCapacity;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import com.ibm.follow.servlet.cloud.ibm_config.service.IbmConfigService;
import com.ibm.follow.servlet.cloud.ibm_sys_monitor_client.entity.IbmSysMonitorClient;
import com.ibm.follow.servlet.cloud.ibm_sys_monitor_client.service.IbmSysMonitorClientService;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.service.IbmSysServletIpService;
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
 * @Date: 2020-01-02 11:25
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/cloud/manage/registerClient", method = HttpConfig.Method.GET) public class RegisterClientAction
		extends CommBaseAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		// 注册客户端-中心端登记
		super.findJson();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JSONObject jsonData = JSON.parseObject(json);
		Object clientCode = jsonData.getOrDefault("clientCode", "");
		String capacityMax = jsonData.getOrDefault("capacityMax", "").toString();
		if (StringTool.isEmpty(clientCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			IbmClientService clientService = new IbmClientService();

			String clientId;
			Date nowTime = new Date();
			//编码已存在
			Map<String,Object>  info = clientService.findInfo(clientCode);
			if(ContainerTool.notEmpty(info)){
				String state = info.get("STATE_").toString();
				if(IbmStateEnum.OPEN.name().equals(state)){
					//已存在
					bean.putEnum(IbmCodeEnum.IBM_403_EXIST_CODE);
					bean.putSysEnum(IbmCodeEnum.CODE_403);
					return bean;
				}else if(IbmStateEnum.STOP.name().equals(state)|| IbmStateEnum.CANCEL.name().equals(state)){
					//激活客户端
					clientService.activateClient(clientCode);
					clientId = info.get("IBM_CLIENT_ID_").toString();
				}else {
					//其他的错误
					bean.putEnum(IbmCodeEnum.IBM_403_REGISTER);
					bean.putSysEnum(IbmCodeEnum.CODE_403);
					return bean;
				}
			}else{
				String ip = findServletIp();
				IbmSysServletIpService servletIpService = new IbmSysServletIpService();
				Map<String, Object> servletInfo = servletIpService.findServletInfo(ip);
				if (ContainerTool.isEmpty(servletInfo) || !IbmStateEnum.OPEN.equal(servletInfo.get("STATE_"))) {
					bean.putEnum(IbmCodeEnum.IBM_404_DATA);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return bean;
				}
				String registerId = servletInfo.get("IBM_SYS_SERVLET_IP_ID_").toString();
				long startTime = NumberTool.getLong(servletInfo.get("START_TIME_LONG_"));
				long endTime = NumberTool.getLong(servletInfo.get("END_TIME_LONG_"));
				//注册
				IbmClient client = new IbmClient();
				client.setRegisterIpId(registerId);
				client.setRegisterIp(ip);
				client.setClientCode(clientCode);
				client.setStartTime(new Date(startTime));
				client.setStartTimeLong(startTime);
				client.setEndTime(new Date(endTime));
				client.setEndTimeLong(endTime);
				client.setCreateTime(nowTime);
				client.setCreateTimeLong(System.currentTimeMillis());
				client.setUpdateTimeLong(System.currentTimeMillis());
				client.setState(IbmStateEnum.OPEN.name());
				clientId = clientService.save(client);




				IbmSysMonitorClient monitorClient = new IbmSysMonitorClient();
				monitorClient.setClientCode(clientCode);
				monitorClient.setServerIp(ip);
				monitorClient.setCreateTime(nowTime);
				monitorClient.setCreateTimeLong(System.currentTimeMillis());
				monitorClient.setUpdateTime(nowTime);
				monitorClient.setUpdateTimeLong(System.currentTimeMillis());
				monitorClient.setState(IbmStateEnum.OPEN.name());
				new IbmSysMonitorClientService().save(monitorClient);
			}

			if (StringTool.notEmpty(capacityMax)) {
				// 保存客户端容量信息
				saveCapacity(clientCode, capacityMax, nowTime, clientId);
			}
			//发送封盘时间信息到客户端
			JSONObject content=new JSONObject();
            content.put("sealTime",new IbmConfigService().mapAllSealTime());
			bean.setData(content);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN, e);
			throw e;
		}
		return bean.success();
	}

	/**
	 * 保存客户端容量信息
	 *
	 * @param clientCode  客户端编码
	 * @param capacityMax 客户端最大容量
	 * @param nowTime     更新时间
	 * @param clientId    客户端ID
	 */
	private void saveCapacity(Object clientCode, String capacityMax, Date nowTime, String clientId) throws Exception {
		JSONObject capacity = JSON.parseObject(capacityMax);
		//初始化容量信息
		IbmClientCapacity clientCapacity = new IbmClientCapacity();
		clientCapacity.setClientId(clientId);
		clientCapacity.setClientCode(clientCode);
		clientCapacity.setClientCapacityMax(NumberTool.getInteger(capacity.remove("CAPACITY_MAX")));
		clientCapacity.setClientCapacity(0);
		clientCapacity.setCreateTime(nowTime);
		clientCapacity.setCreateTimeLong(System.currentTimeMillis());
		clientCapacity.setUpdateTimeLong(System.currentTimeMillis());
		clientCapacity.setState(IbmStateEnum.OPEN.name());
		String clientCapacityId = new IbmClientCapacityService().save(clientCapacity);

		//初始化盘口容量信息
		IbmClientHandicapCapacityService clientHandicapCapacityService = new IbmClientHandicapCapacityService();
		for (Map.Entry<String, Object> entry : capacity.entrySet()) {
			String handicapCode = entry.getKey();
			JSONObject handicapCapacity = capacity.getJSONObject(handicapCode);
			IbmClientHandicapCapacity clientHandicapCapacity = new IbmClientHandicapCapacity();
			clientHandicapCapacity.setClientCapacityId(clientCapacityId);
			clientHandicapCapacity.setClientId(clientId);
			clientHandicapCapacity.setClientCode(clientCode);
			clientHandicapCapacity.setHandicapCode(handicapCode);
			clientHandicapCapacity.setCapacityHandicapMax(handicapCapacity.get(handicapCode.concat("_CAPACITY_MAX")));
			clientHandicapCapacity.setCapacityHandicap(0);
			clientHandicapCapacity.setCapacityHaMax(handicapCapacity.get(handicapCode.concat("_AGENT_CAPACITY_MAX")));
			clientHandicapCapacity.setCapacityHa(0);
			clientHandicapCapacity.setCapacityHmMax(handicapCapacity.get(handicapCode.concat("_MEMBER_CAPACITY_MAX")));
			clientHandicapCapacity.setCapacityHm(0);
			clientHandicapCapacity.setCreateTime(nowTime);
			clientHandicapCapacity.setCreateTimeLong(System.currentTimeMillis());
			clientHandicapCapacity.setUpdateTimeLong(System.currentTimeMillis());
			clientHandicapCapacity.setState(IbmStateEnum.OPEN.name());
			clientHandicapCapacityService.save(clientHandicapCapacity);
		}
	}
}
