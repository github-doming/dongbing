package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_capacity.service.IbmClientCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_heartbeat.entity.IbmClientHeartbeat;
import com.ibm.follow.servlet.cloud.ibm_client_heartbeat.service.IbmClientHeartbeatService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_client_close.service.IbmEventClientCloseService;
import com.ibm.follow.servlet.cloud.ibm_event_login.entity.IbmEventLogin;
import com.ibm.follow.servlet.cloud.ibm_event_login.service.IbmEventLoginService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_log_heartbeat.entity.IbmLogHeartbeat;
import com.ibm.follow.servlet.cloud.ibm_log_heartbeat.service.IbmLogHeartbeatService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 客户端心跳检测
 * @Author: Dongming
 * @Date: 2020-01-10 14:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class VrClientHeartbeatController {
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		String clientCode = msgObj.getString("clientCode");
		String clientId = new IbmClientService().findId(clientCode);

		String capacityMax = msgObj.getString("capacityMax");
		JSONObject exitsHmInfo = msgObj.getJSONObject("exitsHmInfo");

		Date nowTime = new Date();
		//更新客户端容量
		new IbmClientCapacityService().updateCapacity(clientId, NumberTool.getInteger(capacityMax),exitsHmInfo.size(), nowTime);

        IbmLogHeartbeat logHeartbeat=new IbmLogHeartbeat();
        logHeartbeat.setHeartbeatResult(IbmStateEnum.SUCCESS.name());
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
            clientHeartbeat.setHeartbeatResult(IbmStateEnum.SUCCESS.name());
            clientHeartbeat.setErrorContext(logHeartbeat.getErrorContext());
            clientHeartbeat.setCreateTime(nowTime);
            clientHeartbeat.setCreateTimeLong(nowTime.getTime());
            clientHeartbeat.setUpdateTime(nowTime);
            clientHeartbeat.setUpdateTimeLong(nowTime.getTime());
            clientHeartbeat.setState(IbmStateEnum.OPEN.name());
            clientHeartbeatService.save(clientHeartbeat);
        }else{
            clientHeartbeat.setHeartbeatResult(IbmStateEnum.SUCCESS.name());
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
		IbmEventLoginService eventLoginService = new IbmEventLoginService();
		if (ContainerTool.notEmpty(reSendLoginHa)) {
            result.put("reSendLoginHa",reSendLoginHa);
			IbmHandicapAgentService handicapAgentService = new IbmHandicapAgentService();
			for (Object existHaId : reSendLoginHa) {
				String handicapAgentId = clientHaService.logout2Heartbeat(existHaId.toString(), nowTime);
				if (StringTool.isEmpty(handicapAgentId)) {
					continue;
				}
				Map<String, Object> loginInfo = handicapAgentService.findLoginInfo(handicapAgentId);
				//写入登录
				content.putAll(loginInfo);
				content.put("HANDICAP_AGENT_ID_", handicapAgentId);
				saveLoginEvent(eventLoginService, content, IbmTypeEnum.AGENT, nowTime,handicapAgentId);
                content.clear();
			}
		}
		if (ContainerTool.notEmpty(reSendLoginHm)) {
            result.put("reSendLoginHm",reSendLoginHm);
			IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
			for (Object existHmId : reSendLoginHm) {
				String handicapMemberId = clientHmService.logout2Heartbeat(existHmId.toString(), nowTime);
				if (StringTool.isEmpty(handicapMemberId)) {
					continue;
				}
				Map<String, Object> loginInfo = handicapMemberService.findLoginInfo(handicapMemberId);
				//写入登录
				content.putAll(loginInfo);
				content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
				saveLoginEvent(eventLoginService, content, IbmTypeEnum.MEMBER, nowTime,handicapMemberId);
                content.clear();
			}
		}

		//发送关闭数据
		IbmEventClientCloseService eventClientCloseService = new IbmEventClientCloseService();
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
