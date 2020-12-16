package com.ibs.plan.connector.admin.manage.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import com.ibs.plan.module.cloud.ibsp_client_heartbeat.service.IbspClientHeartbeatService;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
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
@ActionMapping(value = "/ibs/sys/manage/client/clientReplace", method = HttpConfig.Method.POST)
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
				return bean;
			}
			bean.success(null);

			//先登出清除旧存在会员信息，在迁移
			//获取需要迁移的客户
			IbspClientHmService clientHmService = new IbspClientHmService();
			List<String> handicapMemberIds = clientHmService.findHmIds(sendClientCode,"");
			if (ContainerTool.notEmpty(handicapMemberIds)) {
				clientHmService.updateByHmIds(handicapMemberIds);
			}
		

			//发消息到发送端
			JSONObject content = new JSONObject();
			content.put("METHOD_", IbsMethodEnum.REPLACE.name());
			content.put("methodType", IbsMethodEnum.SEND.name());
			String result = RabbitMqTool.sendClientConfig(content.toString(), sendClientCode, "info");
			if (StringTool.isEmpty(result)) {
				bean.putEnum(CodeEnum.IBS_401_MESSAGE);
				bean.putSysEnum(CodeEnum.CODE_401);
				return bean;
			}
			if (!Boolean.parseBoolean(result)) {
				bean.putEnum(CodeEnum.IBS_403_MQ);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			//重启事务
			super.transactionRestart();

			if (ContainerTool.notEmpty(handicapMemberIds)) {
				List<Map<String, Object>> handicapMemberInfos = new IbspHandicapMemberService().findMemberInfos(handicapMemberIds);
				//获取会员游戏设置信息
				List<Map<String, Object>> memberGameInfos = new IbspHmGameSetService().findByHmIds(handicapMemberIds);
				//获取会员设置信息
				List<Map<String, Object>> hmSetInfos = new IbspHmSetService().findByHmIds(handicapMemberIds);
				content.put("memberGameInfos", memberGameInfos);
				content.put("hmSetInfos", hmSetInfos);
				content.put("handicapMemberInfos", handicapMemberInfos);
			}

			//发消息到接收端
			content.put("methodType", IbsMethodEnum.RECEIVE.name());
			result = RabbitMqTool.sendClientConfig(content.toString(), receiveClientCode, "info");
			if (StringTool.isEmpty(result)) {
				bean.putEnum(CodeEnum.IBS_401_MESSAGE);
				bean.putSysEnum(CodeEnum.CODE_401);
				return bean;
			}
			if (!Boolean.parseBoolean(result)) {
				bean.putEnum(CodeEnum.IBS_403_MQ);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}

			bean.success();
		} catch (Exception e) {
			log.error("切换客户机错误", e);
			return bean.error(e.getMessage());
		}
		return bean;
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
		IbspClientService clientService = new IbspClientService();
		// 判断发送端和接收端是否是同一个
		if (sendClientCode.equals(receiveClientCode)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}


		//不存在或者客户机状态不为CLOSE
		if (!IbsStateEnum.CLOSE.name().equals(clientService.findInfo(sendClientCode).get("STATE_").toString())
				|| !IbsStateEnum.CLOSE.name().equals(clientService.findInfo(receiveClientCode).get("STATE_").toString())) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}

		//判断两台客户机心跳检测是否停止
		IbspClientHeartbeatService clientHeartbeatService = new IbspClientHeartbeatService();
		Map<String, Object> heartbeatMap = clientHeartbeatService.findResult(sendClientCode);
		if (ContainerTool.isEmpty(heartbeatMap) || !IbsStateEnum.SUCCESS.name().equals(heartbeatMap.get("HEARTBEAT_RESULT_"))) {
			bean.putEnum(CodeEnum.IBS_403_HEARTBEAT_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		if (System.currentTimeMillis() - Long.parseLong(heartbeatMap.get("UPDATE_TIME_LONG_").toString()) < 61000L) {
			bean.putEnum(CodeEnum.IBS_403_HEARTBEAT_INTERVAL);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		heartbeatMap = clientHeartbeatService.findResult(receiveClientCode);
		if (ContainerTool.isEmpty(heartbeatMap) || !IbsStateEnum.SUCCESS.name().equals(heartbeatMap.get("HEARTBEAT_RESULT_"))) {
			bean.putEnum(CodeEnum.IBS_403_HEARTBEAT_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		if (System.currentTimeMillis() - Long.parseLong(heartbeatMap.get("UPDATE_TIME_LONG_").toString()) < 61000L) {
			bean.putEnum(CodeEnum.IBS_403_HEARTBEAT_INTERVAL);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}

		bean.success();
		return bean;
	}
}
