package com.ibm.follow.servlet.server.core.job;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.connector.admin.manage2.cms.RecordNotifyDefine;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_user_notify.service.IbmCmsUserNotifyService;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_event_login.service.IbmEventLoginService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.service.IbmHmProfitService;
import com.ibm.follow.servlet.module.event_new.ConfigSetThread;
import com.ibm.follow.servlet.module.event_new.LoginThread;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 自动管理工作类
 * @Author: Dongming
 * @Date: 2019-09-25 16:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AutoManageJob extends BaseCommJob {

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
		List<Map<String, Object>> hmInfos = handicapMemberService.listTimeLanded();

		List<String> eventIds = new ArrayList<>();
		String eventId;
		JSONObject content = new JSONObject();
		Date nowTime = new Date();
		IbmEventLoginService eventLoginService = new IbmEventLoginService();

		IbmCmsNotifyService cmsNotifyService = new IbmCmsNotifyService();
		IbmCmsUserNotifyService cmsUserNotifyService = new IbmCmsUserNotifyService();
		String handicapCode ,userId ,account;
		//用户会员定时登录
		if (ContainerTool.notEmpty(hmInfos)) {
			for (Map<String, Object> hmInfo : hmInfos) {
				hmInfo.remove("ROW_NUM");
				content.putAll(hmInfo);
				eventId = EventThreadDefine.saveMemberLoginEvent(content, nowTime, this.getClass().getName().concat("，定时登录"),
						eventLoginService,hmInfo.get("HANDICAP_MEMBER_ID_").toString());
				eventIds.add(eventId);
				// 记录定时触发
				handicapCode = HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName();
				userId = hmInfo.get("APP_USER_ID_").toString();
				account = hmInfo.get("MEMBER_ACCOUNT_").toString();
				RecordNotifyDefine.recordTriggerNotify(cmsNotifyService,cmsUserNotifyService,
						"会员","定时登录",handicapCode,userId,account,nowTime);
			}
			content.clear();
		}
		//开启线程，登录处理事件
		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventIds,IbmTypeEnum.MEMBER));
			eventIds.clear();
		}

		IbmHandicapAgentService handicapAgentService = new IbmHandicapAgentService();
		List<Map<String, Object>> haInfos = handicapAgentService.listTimeLanded();
		//用户代理定时登录
		if (ContainerTool.notEmpty(haInfos)) {
			for (Map<String, Object> haInfo : haInfos) {
				haInfo.remove("ROW_NUM");
				content.putAll(haInfo);
				eventId = EventThreadDefine.saveAgentLoginEvent(content, nowTime, this.getClass().getName().concat("，定时登录"),
						eventLoginService,haInfo.get("HANDICAP_AGENT_ID_").toString());
				eventIds.add(eventId);
				// 记录定时触发
				handicapCode = HandicapUtil.code(haInfo.get("HANDICAP_ID_").toString()).getName();
				userId = haInfo.get("APP_USER_ID_").toString();
				account = haInfo.get("AGENT_ACCOUNT_").toString();
				RecordNotifyDefine.recordTriggerNotify(cmsNotifyService,cmsUserNotifyService,
						"代理","定时登录",handicapCode,userId,account,nowTime);
			}
			content.clear();
		}
		//开启线程，登录处理事件
		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventIds,IbmTypeEnum.AGENT));
			eventIds.clear();
		}

		// 处理自动开始投注盘口会员
		IbmEventConfigSetService eventConfigSetService = new IbmEventConfigSetService();
		IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();

		content.put("SET_ITEM_", IbmMethodEnum.SET_BET_STATE.name());
		content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());
		content.put("BET_STATE_", IbmTypeEnum.TRUE.name());

		hmInfos = hmGameSetService.listAutoStart();
		List<String> gameSetIds = new ArrayList<>(hmInfos.size());
		if (ContainerTool.notEmpty(hmInfos)) {
			for (Map<String, Object> hmInfo : hmInfos) {
				gameSetIds.add(hmInfo.get("IBM_HM_GAME_SET_ID_").toString());
				content.put("GAME_CODE_", GameUtil.code(hmInfo.get("GAME_ID_")).name());
				content.put("HANDICAP_MEMBER_ID_", hmInfo.get("HANDICAP_MEMBER_ID_"));
				eventId = EventThreadDefine.saveMemberConfigSetEvent(content, new Date(),
						this.getClass().getName().concat("，自动开始投注"), eventConfigSetService);
				eventIds.add(eventId);
				 // 记录定时触发
				handicapCode = HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName();
				userId = hmInfo.get("USER_ID_").toString();
				account = hmInfo.get("MEMBER_ACCOUNT_").toString();
				RecordNotifyDefine.recordTriggerNotify(cmsNotifyService,cmsUserNotifyService,
						"盘口会员","自动开始投注",handicapCode,userId,account,nowTime);
			}
			if (ContainerTool.notEmpty(gameSetIds)) {
				hmGameSetService.updateAutoStart(gameSetIds, nowTime, this.getClass().getName().concat("自动开始投注"));
				gameSetIds.clear();
			}
		}

		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new ConfigSetThread(eventIds,IbmTypeEnum.MEMBER));
			eventIds.clear();
		}

		// 处理自动停止投注盘口会员
		content.put("BET_STATE_", IbmTypeEnum.FALSE.name());
		hmInfos = hmGameSetService.listAutoStop();
		if (ContainerTool.notEmpty(hmInfos)) {
			for (Map<String, Object> hmInfo : hmInfos) {
				gameSetIds.add(hmInfo.get("IBM_HM_GAME_SET_ID_").toString());
				content.put("GAME_CODE_", GameUtil.code(hmInfo.get("GAME_ID_")).name());
				content.put("HANDICAP_MEMBER_ID_", hmInfo.get("HANDICAP_MEMBER_ID_"));
				eventId = EventThreadDefine.saveMemberConfigSetEvent(content, nowTime,
						this.getClass().getName().concat("，自动停止投注"), eventConfigSetService);
				eventIds.add(eventId);
				// 记录定时触发
				handicapCode = HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName();
				userId = hmInfo.get("USER_ID_").toString();
				account = hmInfo.get("MEMBER_ACCOUNT_").toString();
				RecordNotifyDefine.recordTriggerNotify(cmsNotifyService,cmsUserNotifyService,
						"盘口会员","自动开始投注",handicapCode,userId,account,nowTime);
			}
			if (ContainerTool.notEmpty(gameSetIds)) {
				hmGameSetService.updateAutoStop(gameSetIds, nowTime, this.getClass().getName().concat("自动停止投注"));
			}
		}

		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new ConfigSetThread(eventIds,IbmTypeEnum.MEMBER));
			eventIds.clear();
		}

		// 盘口会员止盈止损限制
		IbmHmProfitService hmProfitService = new IbmHmProfitService();
		Map<String, Object> stopInfo = hmProfitService.profitLimit(IbmTypeEnum.REAL, nowTime);
		if (ContainerTool.notEmpty(stopInfo)) {
			for (Map.Entry<String, Object> entry : stopInfo.entrySet()) {
				content.put("GAME_CODE_", GameUtil.code(entry.getValue()).name());
				content.put("HANDICAP_MEMBER_ID_", entry.getKey());
				eventId = EventThreadDefine.saveMemberConfigSetEvent(content, nowTime,
						this.getClass().getName().concat("，真实盈利限制停止投注"), eventConfigSetService);
				eventIds.add(eventId);
			}
			String handicapMemberIdsStr = String.join("','", stopInfo.keySet());
			hmInfos = hmProfitService.listHmInfo(handicapMemberIdsStr);
			if (ContainerTool.notEmpty(hmInfos)) {

				for (Map<String,Object> hmInfo : hmInfos){
					// 记录定时触发
					handicapCode = HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName();
					userId = hmInfo.get("USER_ID_").toString();
					account = hmInfo.get("MEMBER_ACCOUNT_").toString();
					RecordNotifyDefine.recordTriggerNotify(cmsNotifyService,cmsUserNotifyService,
							"账号","停止投注",handicapCode,userId,account,nowTime);
				}

			}
		}

		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new ConfigSetThread(eventIds,IbmTypeEnum.MEMBER));
			eventIds.clear();
		}
		stopInfo = hmProfitService.profitLimit(IbmTypeEnum.VIRTUAL, nowTime);
		if (ContainerTool.notEmpty(stopInfo)) {
			for (Map.Entry<String, Object> entry : stopInfo.entrySet()) {
				content.put("GAME_CODE_", GameUtil.code(entry.getValue()).name());
				content.put("HANDICAP_MEMBER_ID_", entry.getKey());
				eventId = EventThreadDefine.saveMemberConfigSetEvent(content, nowTime,
						this.getClass().getName().concat("，模拟盈利限制停止投注"), eventConfigSetService);
				eventIds.add(eventId);
			}
		}

		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new ConfigSetThread(eventIds,IbmTypeEnum.MEMBER));
			eventIds.clear();
		}

		//清理十分钟前操作为登陆或登出的会员
        handicapMemberService.updateOperating();
		//清理十分钟前操作为登陆或登出的代理
        handicapAgentService.updateOperating();
	}
}
