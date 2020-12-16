package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_capacity.service.IbmClientCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_heartbeat.entity.IbmClientHeartbeat;
import com.ibm.follow.servlet.cloud.ibm_client_heartbeat.service.IbmClientHeartbeatService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_login.entity.IbmEventLogin;
import com.ibm.follow.servlet.cloud.ibm_event_login.service.IbmEventLoginService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_log_heartbeat.entity.IbmLogHeartbeat;
import com.ibm.follow.servlet.cloud.ibm_log_heartbeat.service.IbmLogHeartbeatService;
import com.ibm.follow.servlet.module.event_new.LoginThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.*;
/**
 * 客户端心跳检测
 * @Author: Dongming
 * @Date: 2020-01-10 14:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientHeartbeatController {
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		String clientCode = msgObj.getString("clientCode");
		String clientId = new IbmClientService().findId(clientCode);

		JSONObject data = msgObj.getJSONObject("data");
		JSONObject capacityMax = data.getJSONObject("capacityMax");
		JSONObject exitsHaInfo = data.getJSONObject("exitsHaInfo");
		JSONObject exitsHmInfo = data.getJSONObject("exitsHmInfo");

		int max = capacityMax.getInteger("CAPACITY_MAX");

		int exitsHaCount = exitsHaInfo.getInteger("EXITS_COUNT");
		int exitsHmCount = exitsHmInfo.getInteger("EXITS_COUNT");
		Date nowTime = new Date();

		//更新客户端容量
		new IbmClientCapacityService().updateCapacity(clientId, max, exitsHaCount + exitsHmCount, nowTime);

		Set<Object> reSendLoginHa = new HashSet<>();
		Set<Object> reSendLoginHm = new HashSet<>();
		Set<Object> reSendCloseHa = new HashSet<>();
		Set<Object> reSendCloseHm = new HashSet<>();

		//更新盘口容量
		IbmClientHandicapCapacityService clientHandicapCapacityService = new IbmClientHandicapCapacityService();
		IbmClientHaService clientHaService = new IbmClientHaService();
		IbmClientHmService clientHmService = new IbmClientHmService();
		IbmHandicapMemberService handicapMemberService=new IbmHandicapMemberService();
		IbmHandicapAgentService handicapAgentService=new IbmHandicapAgentService();
		for (HandicapUtil.Code code : HandicapUtil.codes()) {
			String handicapCode = code.name();
			JSONObject capacity = capacityMax.getJSONObject(handicapCode);
			if (ContainerTool.isEmpty(capacity)){
				continue;
			}
			JSONObject exitsHaIds=exitsHaInfo.getJSONObject(handicapCode);
			JSONObject exitsHmIds=exitsHmInfo.getJSONObject(handicapCode);

			//更新容量
			clientHandicapCapacityService
					.updateCapacity(clientId, handicapCode, capacity.getInteger(handicapCode.concat("_CAPACITY_MAX")),
							capacity.getInteger(handicapCode.concat("_AGENT_CAPACITY_MAX")), exitsHaIds.size(),
							capacity.getInteger(handicapCode.concat("_MEMBER_CAPACITY_MAX")), exitsHmIds.size(),
							nowTime);
			//代理
			List<String> exitsIds = clientHaService.listExitsId(clientId, code.name());
			//本机存在-客户端不存在的数据-重新发送登录数据
			reSendLoginHa.addAll(ContainerTool.getDifferent(exitsIds, exitsHaIds.values()));
			if(ContainerTool.notEmpty(reSendLoginHa) ){
				List<String> operatingIds = handicapAgentService.listOperatingHaId(reSendLoginHa);
				reSendLoginHa.removeAll(operatingIds);
			}
			//客户端存在-本机不存在的数据-发送关闭登录数据
			reSendCloseHa.addAll(ContainerTool.getDifferent(exitsHaIds.values(), exitsIds));
			if(ContainerTool.notEmpty(reSendCloseHa)){
				List<String> operatingIds = handicapAgentService.listOperatingHaId(reSendCloseHa);
				reSendCloseHa.removeAll(operatingIds);
			}

			//会员
			exitsIds = clientHmService.listExitsId(clientId, code.name());
			//本机存在-客户端不存在的数据-重新发送登录数据
			reSendLoginHm.addAll(ContainerTool.getDifferent(exitsIds, exitsHmIds.values()));
			if(ContainerTool.notEmpty(reSendLoginHm)){
				List<String> operatingIds =handicapMemberService.listOperatingHaId(reSendLoginHm);
				reSendLoginHm.removeAll(operatingIds);
			}
			//客户端存在-本机不存在的数据-发送关闭登录数据
			reSendCloseHm.addAll(ContainerTool.getDifferent(exitsHmIds.values(), exitsIds));
			if(ContainerTool.notEmpty(reSendCloseHm)){
				List<String> operatingIds =handicapMemberService.listOperatingHaId(reSendCloseHm);
				reSendCloseHm.removeAll(operatingIds);
			}

		}

		JSONObject result=reSend(clientCode, reSendLoginHa, reSendLoginHm, reSendCloseHa, reSendCloseHm, clientHaService, clientHmService,
				nowTime);

        String heartbeatResult;
        IbmLogHeartbeat logHeartbeat=new IbmLogHeartbeat();
		if(ContainerTool.notEmpty(result)){
            heartbeatResult=IbmStateEnum.FAIL.name();
            logHeartbeat.setErrorContext(result);
        }else{
            heartbeatResult=IbmStateEnum.SUCCESS.name();
        }
        logHeartbeat.setHeartbeatResult(heartbeatResult);
        logHeartbeat.setClientCode(clientCode);
        logHeartbeat.setCreateTime(nowTime);
        logHeartbeat.setCreateTimeLong(nowTime.getTime());
        logHeartbeat.setUpdateTime(nowTime);
        logHeartbeat.setUpdateTimeLong(nowTime.getTime());
        logHeartbeat.setState(IbmStateEnum.OPEN.name());
        new IbmLogHeartbeatService().save(logHeartbeat);

        IbmClientHeartbeatService clientHeartbeatService=new IbmClientHeartbeatService();
        IbmClientHeartbeat clientHeartbeat=clientHeartbeatService.findByClientCode(clientCode);
        if(clientHeartbeat==null){
            clientHeartbeat=new IbmClientHeartbeat();
            clientHeartbeat.setClientCode(clientCode);
            clientHeartbeat.setHeartbeatResult(heartbeatResult);
            clientHeartbeat.setErrorContext(logHeartbeat.getErrorContext());
            clientHeartbeat.setCreateTime(nowTime);
            clientHeartbeat.setCreateTimeLong(nowTime.getTime());
            clientHeartbeat.setUpdateTime(nowTime);
            clientHeartbeat.setUpdateTimeLong(nowTime.getTime());
            clientHeartbeat.setState(IbmStateEnum.OPEN.name());
            clientHeartbeatService.save(clientHeartbeat);
        }else{
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
	 * @param clientCode      客户端编码
	 * @param reSendLoginHa   需要重新登录的盘口代理
	 * @param reSendLoginHm   需要重新登录的盘口会员
	 * @param reSendCloseHa   需要关闭客户端的盘口代理
	 * @param reSendCloseHm   需要关闭客户端的盘口会员
	 * @param clientHaService 客户端盘口代理服务类
	 * @param clientHmService 客户端盘口会员服务类
	 * @param nowTime         发送事件
	 */
	private JSONObject reSend(String clientCode, Set<Object> reSendLoginHa, Set<Object> reSendLoginHm,
			Set<Object> reSendCloseHa, Set<Object> reSendCloseHm, IbmClientHaService clientHaService,
			IbmClientHmService clientHmService, Date nowTime) throws Exception {
	    JSONObject result=new JSONObject();
        JSONObject content = new JSONObject();
		//重新发送登录数据
		List<String> eventIds = new ArrayList<>();
		IbmEventLoginService eventLoginService = new IbmEventLoginService();
		if (ContainerTool.notEmpty(reSendLoginHa)) {
            result.put("reSendLoginHa",reSendLoginHa);
			for (Object existHaId : reSendLoginHa) {
				String handicapAgentId = clientHaService.logout2Heartbeat(existHaId.toString(), nowTime);
				if (StringTool.isEmpty(handicapAgentId)) {
					continue;
				}
				//写入登录
				content.put("HANDICAP_AGENT_ID_", handicapAgentId);
				String eventId = EventThreadDefine.saveAgentLoginEvent(content, new Date(),
						this.getClass().getName().concat("，登录"), eventLoginService,handicapAgentId);
				eventIds.add(eventId);
			}
		}
		// 开启登录线程 - 运行登录事件  eventIds
		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventIds, IbmTypeEnum.AGENT));
			eventIds = new ArrayList<>();
			content.clear();
		}
		if (ContainerTool.notEmpty(reSendLoginHm)) {
            result.put("reSendLoginHm",reSendLoginHm);
			for (Object existHmId : reSendLoginHm) {
				String handicapMemberId = clientHmService.logout2Heartbeat(existHmId.toString(), nowTime);
				if (StringTool.isEmpty(handicapMemberId)) {
					continue;
				}
				//写入登录
				content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
				String eventId = EventThreadDefine.saveMemberLoginEvent(content, new Date(),
						this.getClass().getName().concat("，登录"), eventLoginService,handicapMemberId);
				eventIds.add(eventId);
			}
		}
		// 开启登录线程 - 运行登录事件  eventIds
		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventIds, IbmTypeEnum.MEMBER));
		}
		//发送关闭数据
		if (ContainerTool.notEmpty(reSendCloseHa)) {
            result.put("reSendCloseHa",reSendCloseHa);
            LogoutHaController logoutHaController=new LogoutHaController();
			for (Object existHaId : reSendCloseHa) {
				String handicapAgentId = clientHaService.logout2Heartbeat(existHaId.toString(), nowTime);
				//用户登出清理数据
                logoutHaController.execute(handicapAgentId);
			}
		}
		if (ContainerTool.notEmpty(reSendCloseHm)) {
            result.put("reSendCloseHm",reSendCloseHm);
            LogoutHmController logoutHmController=new LogoutHmController();
			for (Object existHmId : reSendCloseHm) {
				String handicapMemberId = clientHmService.logout2Heartbeat(existHmId.toString(), nowTime);
				//用户登出清理数据
                logoutHmController.execute(handicapMemberId);
			}
		}
		return result;
	}
	/**
	 * 保存客户端登录事件
	 *
	 * @param eventLoginService 客户端登录事件服务类
	 * @param content           事件正文
	 * @param type              客户类型
	 * @param customerId        客户主键
	 * @param nowTime           更新时间
	 */
	private void saveLoginEvent(IbmEventLoginService eventLoginService, JSONObject content, IbmTypeEnum type,
			Date nowTime,String customerId) throws Exception {
		IbmEventLogin eventLogin = new IbmEventLogin();
		eventLogin.setCustomerId(customerId);
		eventLogin.setCustomerType(type.name());
		eventLogin.setEventContent(content);
		eventLogin.setEventState(IbmStateEnum.BEGIN.name());
		eventLogin.setExecNumber(0);
		eventLogin.setCreateTime(nowTime);
		eventLogin.setCreateTimeLong(System.currentTimeMillis());
		eventLogin.setUpdateTimeLong(System.currentTimeMillis());
		eventLogin.setState(IbmStateEnum.OPEN.name());
		eventLogin.setDesc(this.getClass().getName().concat("，登录"));
		eventLoginService.save(eventLogin);
	}

}
