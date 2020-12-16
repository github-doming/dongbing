package com.ibs.plan.module.mq.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.utils.ThreadExecuteUtil;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.core.controller.process.LogoutHmController;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import com.ibs.plan.module.cloud.ibsp_client_capacity.service.IbspClientCapacityService;
import com.ibs.plan.module.cloud.ibsp_client_handicap_capacity.service.IbspClientHandicapCapacityService;
import com.ibs.plan.module.cloud.ibsp_client_heartbeat.entity.IbspClientHeartbeat;
import com.ibs.plan.module.cloud.ibsp_client_heartbeat.service.IbspClientHeartbeatService;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_event_login.service.IbspEventLoginService;
import com.ibs.plan.module.cloud.ibsp_event_logout.service.IbspEventLogoutService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_log_heartbeat.entity.IbspLogHeartbeat;
import com.ibs.plan.module.cloud.ibsp_log_heartbeat.service.IbspLogHeartbeatService;
import com.ibs.plan.module.server.thread.LoginThread;
import com.ibs.plan.module.server.thread.LogoutThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;
/**
 * 客户端心跳检测
 *
 * @Author: null
 * @Date: 2020-05-22 11:28
 * @Version: v1.0
 */
public class ClientHeartbeatController {

	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		String clientCode = msgObj.getString("clientCode");
		String clientId = new IbspClientService().findId(clientCode);
		JSONObject data = msgObj.getJSONObject("data");
		JSONObject capacity = data.getJSONObject("capacity");
		JSONObject exitsHmInfo = data.getJSONObject("exitsHmInfo");

		Set<Object> reSendLoginHm = new HashSet<>(20);
		Set<Object> reSendCloseHm = new HashSet<>(20);
		Map<Object, Object> idMaps = new HashMap<>(20);

		//更新盘口容量
		IbspClientHandicapCapacityService clientHandicapCapacityService = new IbspClientHandicapCapacityService();
		IbspClientHmService clientHmService = new IbspClientHmService();
		IbspHandicapMemberService handicapMemberService = new IbspHandicapMemberService();

		int capacityMax = NumberTool.getInteger(capacity.remove("CAPACITY"));
		int usedCapacity = 0;
		Date nowTime = new Date();
		for (String handicapCode : capacity.keySet()) {
			String handicapCapacity=capacity.getString(handicapCode);
			if (StringTool.isEmpty(handicapCapacity)||!exitsHmInfo.containsKey(handicapCode)) {
				clientHandicapCapacityService.updateCapacity(clientId, handicapCode, NumberTool.getInteger(handicapCapacity),
						0, nowTime);
				continue;
			}
			//容量
			JSONObject handicapExitsHmInfo = exitsHmInfo.getJSONObject(handicapCode);
			clientHandicapCapacityService.updateCapacity(clientId, handicapCode, NumberTool.getInteger(handicapCapacity),
							handicapExitsHmInfo.size(), nowTime);
			usedCapacity += handicapExitsHmInfo.size();
			idMaps.putAll(handicapExitsHmInfo);

			//会员
			List<String> exitsIds = clientHmService.listExitsId(clientId, handicapCode);
			//本机存在-客户端不存在的数据-重新发送登录数据
			reSendLoginHm.addAll(ContainerTool.getDifferent(exitsIds, handicapExitsHmInfo.keySet()));
			if (ContainerTool.notEmpty(reSendLoginHm)) {
				List<String> operatingIds = handicapMemberService.listOperatingId(reSendLoginHm);
				reSendLoginHm.removeAll(operatingIds);
			}
			//客户端存在-本机不存在的数据-发送关闭登录数据
			reSendCloseHm.addAll(ContainerTool.getDifferent(handicapExitsHmInfo.keySet(), exitsIds));
			if (ContainerTool.notEmpty(reSendCloseHm)) {
				List<String> operatingIds = handicapMemberService.listOperatingId(reSendCloseHm);
				reSendCloseHm.removeAll(operatingIds);
			}
		}

		//更新客户端容量
		new IbspClientCapacityService().updateCapacity(clientId, capacityMax, usedCapacity, nowTime);

		JSONObject result = reSend(idMaps, reSendLoginHm, reSendCloseHm, clientCode, nowTime);

		//保存心跳检测信息
		IbspLogHeartbeat logHeartbeat = new IbspLogHeartbeat();
		String heartbeatResult;
		if (ContainerTool.notEmpty(result)) {
			heartbeatResult = IbsStateEnum.FAIL.name();
			logHeartbeat.setErrorContext(result);
		} else {
			heartbeatResult = IbsStateEnum.SUCCESS.name();
		}
		logHeartbeat.setHeartbeatResult(heartbeatResult);
		logHeartbeat.setClientCode(clientCode);
		logHeartbeat.setCreateTime(nowTime);
		logHeartbeat.setCreateTimeLong(nowTime.getTime());
		logHeartbeat.setUpdateTime(nowTime);
		logHeartbeat.setUpdateTimeLong(nowTime.getTime());
		logHeartbeat.setState(IbsStateEnum.OPEN.name());
		new IbspLogHeartbeatService().save(logHeartbeat);

		//更新检测信息
		IbspClientHeartbeatService clientHeartbeatService = new IbspClientHeartbeatService();
		IbspClientHeartbeat clientHeartbeat = clientHeartbeatService.findByClientCode(clientCode);
		if (clientHeartbeat == null) {
			clientHeartbeat = new IbspClientHeartbeat();
			clientHeartbeat.setClientCode(clientCode);
			clientHeartbeat.setHeartbeatResult(heartbeatResult);
			clientHeartbeat.setErrorContext(logHeartbeat.getErrorContext());
			clientHeartbeat.setCreateTime(nowTime);
			clientHeartbeat.setCreateTimeLong(nowTime.getTime());
			clientHeartbeat.setUpdateTime(nowTime);
			clientHeartbeat.setUpdateTimeLong(nowTime.getTime());
			clientHeartbeat.setState(IbsStateEnum.OPEN.name());
			clientHeartbeatService.save(clientHeartbeat);
		} else {
			clientHeartbeat.setHeartbeatResult(heartbeatResult);
			clientHeartbeat.setErrorContext(logHeartbeat.getErrorContext());
			clientHeartbeat.setUpdateTime(nowTime);
			clientHeartbeat.setUpdateTimeLong(nowTime.getTime());
			clientHeartbeatService.update(clientHeartbeat);
		}

		return new JsonResultBeanPlus().success();
	}

	/**
	 * 重新发送需要 修改的事件
	 *
	 * @param idMaps        id集合E
	 * @param reSendLoginHm 需要重新登录的盘口会员
	 * @param reSendCloseHm 需要关闭客户端的盘口会员
	 * @param clientCode    客户端编码
	 * @param nowTime       发送事件
	 */
	private JSONObject reSend(Map<Object, Object> idMaps, Set<Object> reSendLoginHm, Set<Object> reSendCloseHm,
			String clientCode, Date nowTime) throws Exception {
		JSONObject result = new JSONObject();
		JSONObject content = new JSONObject();
		//重新发送登录数据
		List<String> eventIds = new ArrayList<>();
		if (ContainerTool.notEmpty(reSendLoginHm)) {
			IbspEventLoginService eventLoginService = new IbspEventLoginService();
			result.put("reSendLoginHm", reSendLoginHm);
			for (Object existHmId : reSendLoginHm) {
				String handicapMemberId = idMaps.get(existHmId).toString();
				//写入登录
				content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
				String eventId = EventThreadDefine
						.saveLoginEvent(eventLoginService, handicapMemberId, content, nowTime);
				eventIds.add(eventId);
			}
		}
		// 开启登录线程 - 运行登录事件  eventIds
		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventIds));
			eventIds = new ArrayList<>();
			content.clear();
		}

		//发送登出数据
		if (ContainerTool.notEmpty(reSendCloseHm)) {
			result.put("reSendCloseHm", reSendCloseHm);
			LogoutHmController logoutHmController = new LogoutHmController();
			IbspEventLogoutService eventLogoutService = new IbspEventLogoutService();
			content.put("CLIENT_CODE_", clientCode);
			for (Object existHmId : reSendCloseHm) {
				String handicapMemberId = idMaps.get(existHmId).toString();
				//用户登出清理数据
				logoutHmController.clearData(handicapMemberId, nowTime);

				//写入登出
				content.put("EXIST_HM_ID_", existHmId);
				String eventId = EventThreadDefine
						.saveLogoutEvent(eventLogoutService, handicapMemberId, content, nowTime);
				eventIds.add(eventId);
			}
		}
		//开启登出线程 - 运行登出事件  eventIds
		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LogoutThread(eventIds));
		}
		return result;
	}
}
