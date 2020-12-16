package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.core.controller.init.LoginControllerDefine;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.entity.IbmHandicapAgent;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service.IbmHandicapAgentMemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 盘口代理登出信息控制器
 * @Author: zjj
 * @Date: 2019-09-03 11:02
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class LogoutHaController implements CloudExecutor {
	protected static final Logger log = LogManager.getLogger(LogoutHaController.class);

	@Override public String execute(String handicapAgentId) throws Exception {
		Date nowTime=new Date();
		IbmHandicapAgentService handicapAgentService=new IbmHandicapAgentService();
		IbmHandicapAgent handicapAgent=handicapAgentService.find(handicapAgentId);
		if(handicapAgent==null){
			log.error("盘口代理登出失败，不存在盘口代理："+handicapAgentId);
			return null;
		}
        handicapAgent.setOperating(IbmStateEnum.LOGOUT.name());
        handicapAgent.setUpdateTimeLong(System.currentTimeMillis());
        handicapAgentService.update(handicapAgent);
		//修改登录状态
		IbmHaInfoService haInfoService=new IbmHaInfoService();
		haInfoService.updateLogout(handicapAgentId,nowTime);

		//修改盘口代理用户登录在线信息
		IbmHaUserService haUserService=new IbmHaUserService();
		haUserService.updateLogout(handicapAgentId,nowTime);

		//修改盘口游戏投注状态
		IbmHaGameSetService haGameSetService=new IbmHaGameSetService();
		haGameSetService.updateLogoutBetState(handicapAgentId,nowTime);


		//解绑用户代理
		IbmHandicapAgentMemberService handicapAgentMemberService = new IbmHandicapAgentMemberService();
		handicapAgentMemberService.unbindByHaId(handicapAgentId,nowTime);

		//添加登出日志信息
		LoginControllerDefine.saveHaLog(handicapAgentId,handicapAgent.getAppUserId(),IbmStateEnum.LOGOUT.name());

        IbmExpUserService expUserService=new IbmExpUserService();
        expUserService.updateLogoutOnline(handicapAgent.getAppUserId(), IbmTypeEnum.AGENT);

        //登出客户端
        logoutClient(handicapAgentId);
		return null;
	}

	private void logoutClient(String handicapAgentId) throws Exception {
		Map<String,Object> existInfo =new IbmClientHaService().findExistHaId(handicapAgentId);
		if (ContainerTool.isEmpty(existInfo)){
			log.error("盘口代理登出失败，不存在盘口代理登录信息："+handicapAgentId);
			return ;
		}
		String clientCode = existInfo.remove("CLIENT_CODE_").toString();

		JSONObject content = new JSONObject();
		content.put("EXIST_HA_ID_", existInfo.get("EXIST_HA_ID_"));
		content.put("METHOD_", IbmMethodEnum.LOGOUT.name());
		content.put("CUSTOMER_TYPE_", IbmTypeEnum.AGENT.name());
		//保存关闭客户端事件
		String eventId = EventThreadDefine.saveLogoutEvent(IbmTypeEnum.AGENT, content, this.getClass().getName().concat("，登出"));
		content.put("EVENT_ID_", eventId);

		RabbitMqTool.sendAgentInfo(content.toString(), clientCode, "login");
	}
}
