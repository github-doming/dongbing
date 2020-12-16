package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.follow.servlet.cloud.core.CloudCustomerTool;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.core.controller.init.LoginControllerDefine;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service.IbmHandicapAgentMemberService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.entity.IbmHandicapMember;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.service.IbmHmProfitService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_vr.service.IbmHmProfitVrService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import com.ibm.follow.servlet.module.event_new.ConfigSetThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 盘口会员登出信息控制器
 * @Author: zjj
 * @Date: 2019-09-03 10:40
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class LogoutHmController implements CloudExecutor {
	protected static final Logger log = LogManager.getLogger(LogoutHmController.class);

	private String handicapMemberId;

	@Override public String execute(String handicapMemberId) throws Exception {
		Date nowTime=new Date();
		IbmHandicapMemberService handicapMemberService=new IbmHandicapMemberService();
		IbmHandicapMember handicapMember=handicapMemberService.find(handicapMemberId);
		if(handicapMember==null){
			log.error("盘口会员登出失败，不存在盘口会员："+handicapMemberId);
			return null;
		}
        this.handicapMemberId=handicapMemberId;
        handicapMember.setOperating(IbmStateEnum.LOGOUT.name());
        handicapMember.setUpdateTimeLong(System.currentTimeMillis());
        handicapMemberService.update(handicapMember);
		//修改登录状态
		IbmHmInfoService hmInfoService=new IbmHmInfoService();
		hmInfoService.updateLogout(handicapMemberId,nowTime);

		//修改盘口会员用户登录在线信息
		new IbmHmUserService().updateLogout(handicapMemberId,nowTime);

		//修改盘口会员所有游戏投注状态
		new IbmHmGameSetService().updateBetState(handicapMemberId,nowTime);

		//重置真实投注盈亏信息
		new IbmHmProfitService().updateLogout(handicapMemberId,nowTime);

		//重置模拟投注盈亏信息
		new IbmHmProfitVrService().updateLogout(handicapMemberId,nowTime);

		//解绑用户会员
		unbindUserAgent(nowTime);

		//清除内存数据
        CloudCustomerTool.removeCustomerInfo(handicapMemberId);

		//添加登出日志信息
		LoginControllerDefine.saveHmLog(handicapMemberId,handicapMember.getAppUserId(),IbmStateEnum.LOGOUT.name());

        IbmExpUserService expUserService=new IbmExpUserService();
        expUserService.updateLogoutOnline(handicapMember.getAppUserId(), IbmTypeEnum.MEMBER);

        logoutClient();
		return null;
	}

	private void logoutClient() throws Exception {
		Map<String,Object> existInfo =new IbmClientHmService().findExistHmId(handicapMemberId);
		if (ContainerTool.isEmpty(existInfo)){
			log.error("盘口会员登出失败，不存在盘口会员登录信息："+handicapMemberId);
			return;
		}
		String clientCode = existInfo.remove("CLIENT_CODE_").toString();

		JSONObject content = new JSONObject();
		content.put("EXIST_HM_ID_", existInfo.get("EXIST_HM_ID_"));
		content.put("METHOD_", IbmMethodEnum.LOGOUT.name());
		content.put("CUSTOMER_TYPE_", IbmTypeEnum.MEMBER.name());

		//保存关闭客户端事件
		String eventId = EventThreadDefine.saveLogoutEvent(IbmTypeEnum.MEMBER, content, this.getClass().getName().concat("，登出"));
		content.put("EVENT_ID_", eventId);

		RabbitMqTool.sendAgentInfo(content.toString(), clientCode, "login");
	}

	/**
	 * 绑定用户代理
	 *
	 * @param nowTime 解绑时间
	 */
	private void unbindUserAgent(Date nowTime) throws Exception {
		Map<String, Object> map= new IbmClientHmService().findExistHmId(handicapMemberId);
		if(ContainerTool.isEmpty(map)){
			return ;
		}
		String existHmId = map.get("EXIST_HM_ID_").toString();
		//获取已有代理id列表
		IbmHandicapAgentMemberService handicapAgentMemberService = new IbmHandicapAgentMemberService();
		List<String> handicapAgentIds = handicapAgentMemberService.listHaId(handicapMemberId);
		if (ContainerTool.isEmpty(handicapAgentIds)) {
			//尚未开启代理
			return;
		}
		//解绑消息主体
		JSONObject content = new JSONObject();
		content.put("SET_ITEM_", IbmMethodEnum.SET_UNBIND.name());
		content.put("METHOD_", IbmMethodEnum.MANAGE_SET.name());
		content.put("EXIST_HM_ID_", existHmId);

		IbmEventConfigSetService configSetService = new IbmEventConfigSetService();
		List<String> eventIds = new ArrayList<>();
		String eventId;
		for (String handicapAgentId : handicapAgentIds) {
			// 解绑代理会员
			handicapAgentMemberService.unbindByHaId( handicapAgentId, handicapMemberId, nowTime);
			//发送解绑数据到客户机
			content.put("HANDICAP_AGENT_ID_", handicapAgentId);
			eventId = EventThreadDefine.saveAgentConfigSetEvent(content,nowTime,this.getClass().getName().concat("，解绑数据设置"),configSetService);
			eventIds.add(eventId);
		}
		if(ContainerTool.notEmpty(eventIds)){
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new ConfigSetThread(eventIds,IbmTypeEnum.AGENT));
		}

	}
}
