package com.ibm.follow.connector.admin.manage.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_heartbeat.service.IbmClientHeartbeatService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_set.service.IbmHaSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 切换客户机
 * @Author: null
 * @Date: 2020-03-24 17:14
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/clientReplace", method = HttpConfig.Method.POST)
public class ClientReplaceAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//发送端客户机active
		String sendClientCode = dataMap.getOrDefault("sendClientCode", "").toString();
		//接收端客户机passive
		String receiveClientCode = dataMap.getOrDefault("receiveClientCode", "").toString();
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			//检验客户机
			bean = checkClient(sendClientCode, receiveClientCode);
			if (!bean.isSuccess()) {
				return bean.toJsonString();
			}
			bean.success(null);

			//先登出清除旧存在会员信息，在迁移
			//获取需要迁移的客户
			IbmClientHmService clientHmService = new IbmClientHmService();
			List<String> handicapMemberIds = clientHmService.findHmIds(sendClientCode);
			if (ContainerTool.notEmpty(handicapMemberIds)) {
				clientHmService.updateByHmIds(handicapMemberIds);
			}
			//盘口代理ids
			IbmClientHaService clientHaService = new IbmClientHaService();
			List<String> handicapAgentIds = clientHaService.findHaIds(sendClientCode);
			if (ContainerTool.notEmpty(handicapAgentIds)) {
				clientHaService.updateByHaIds(handicapAgentIds);
			}

			//发消息到发送端
			JSONObject content = new JSONObject();
			content.put("METHOD_", IbmMethodEnum.REPLACE.name());
			content.put("methodType", IbmMethodEnum.SEND.name());
			String result = RabbitMqTool.sendClientConfig(content.toString(), sendClientCode, "info");
			if (StringTool.isEmpty(result)) {
				bean.putEnum(IbmCodeEnum.IBM_401_MESSAGE);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean.toJsonString();
			}
			if (!Boolean.parseBoolean(result)) {
				bean.putEnum(IbmCodeEnum.IBM_403_MQ);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean.toJsonString();
			}
			//重启事务
			transactionRestart();

			if (ContainerTool.notEmpty(handicapMemberIds)) {
				List<Map<String, Object>> handicapMemberInfos = new IbmHandicapMemberService().findMemberInfos(handicapMemberIds);
				//获取会员游戏设置信息
				List<Map<String, Object>> memberGameInfos = new IbmHmGameSetService().findByHmIds(handicapMemberIds);
				//获取会员设置信息
				List<Map<String, Object>> hmSetInfos = new IbmHmSetService().findByHmIds(handicapMemberIds);
				content.put("memberGameInfos", memberGameInfos);
				content.put("hmSetInfos", hmSetInfos);
				content.put("handicapMemberInfos", handicapMemberInfos);
			}
			if (ContainerTool.notEmpty(handicapAgentIds)) {
				List<Map<String, Object>> handicapAgentInfos = new IbmHandicapAgentService().findAgentInfos(handicapAgentIds);
				//获取代理游戏设置信息
				List<Map<String, Object>> agentGameInfos = new IbmHaGameSetService().findByHaIds(handicapAgentIds);
				//代理设置信息
				List<Map<String, Object>> haSetInfos = new IbmHaSetService().findByHaIds(handicapAgentIds);
				content.put("agentGameInfos", agentGameInfos);
				content.put("haSetInfos", haSetInfos);
				content.put("handicapAgentInfos", handicapAgentInfos);
			}
			//发消息到接收端
			content.put("methodType", IbmMethodEnum.RECEIVE.name());
			result = RabbitMqTool.sendClientConfig(content.toString(), receiveClientCode, "info");
			if (StringTool.isEmpty(result)) {
				bean.putEnum(IbmCodeEnum.IBM_401_MESSAGE);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean.toJsonString();
			}
			if (!Boolean.parseBoolean(result)) {
				bean.putEnum(IbmCodeEnum.IBM_403_MQ);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean.toJsonString();
			}

			bean.success();
		} catch (Exception e) {
			log.error("切换客户机错误", e);
			return bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}

	/**
	 * 检验客户端
	 *
	 * @param sendClientCode    发送端客户机
	 * @param receiveClientCode 接收端客户机
	 * @return
	 */
	public static JsonResultBeanPlus checkClient(String sendClientCode, String receiveClientCode) throws SQLException {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		IbmClientService clientService = new IbmClientService();
		// 判断发送端和接收端是否是同一个
		if (sendClientCode.equals(receiveClientCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}

		//不存在或者客户机状态不为CLOSE
		if (!IbmStateEnum.CLOSE.name().equals(clientService.findState(sendClientCode))
				|| !IbmStateEnum.CLOSE.name().equals(clientService.findState(receiveClientCode))) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}

		//判断两台客户机心跳检测是否停止
		IbmClientHeartbeatService clientHeartbeatService = new IbmClientHeartbeatService();
		Map<String, Object> heartbeatMap = clientHeartbeatService.findResult(sendClientCode);
		if (ContainerTool.isEmpty(heartbeatMap) || !IbmStateEnum.SUCCESS.name().equals(heartbeatMap.get("HEARTBEAT_RESULT_"))) {
			bean.putEnum(IbmCodeEnum.IBM_403_HEARTBEAT_ERROR);
			bean.putSysEnum(IbmCodeEnum.CODE_403);
			return bean;
		}
		if (System.currentTimeMillis() - Long.parseLong(heartbeatMap.get("UPDATE_TIME_LONG_").toString()) < 61000L) {
			bean.putEnum(IbmCodeEnum.IBM_403_HEARTBEAT_INTERVAL);
			bean.putSysEnum(IbmCodeEnum.CODE_403);
			return bean;
		}
		heartbeatMap = clientHeartbeatService.findResult(receiveClientCode);
		if (ContainerTool.isEmpty(heartbeatMap) || !IbmStateEnum.SUCCESS.name().equals(heartbeatMap.get("HEARTBEAT_RESULT_"))) {
			bean.putEnum(IbmCodeEnum.IBM_403_HEARTBEAT_ERROR);
			bean.putSysEnum(IbmCodeEnum.CODE_403);
			return bean;
		}
		if (System.currentTimeMillis() - Long.parseLong(heartbeatMap.get("UPDATE_TIME_LONG_").toString()) < 61000L) {
			bean.putEnum(IbmCodeEnum.IBM_403_HEARTBEAT_INTERVAL);
			bean.putSysEnum(IbmCodeEnum.CODE_403);
			return bean;
		}

		bean.success();
		return bean;
	}
}
