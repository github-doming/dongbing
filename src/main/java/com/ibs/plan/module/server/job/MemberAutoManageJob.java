package com.ibs.plan.module.server.job;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.TypeEnum;
import com.common.tools.CacheTool;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.common.utils.PlanUtil;
import com.ibs.plan.common.tools.RecordNotifyTool;
import com.ibs.plan.common.utils.ThreadExecuteUtil;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.service.IbspCmsUserNotifyService;
import com.ibs.plan.module.cloud.ibsp_event_config_set.service.IbspEventConfigSetService;
import com.ibs.plan.module.cloud.ibsp_event_login.service.IbspEventLoginService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.service.IbspHmModeCutoverService;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
import com.ibs.plan.module.cloud.ibsp_plan_hm.service.IbspPlanHmService;
import com.ibs.plan.module.cloud.ibsp_profit.service.IbspProfitService;
import com.ibs.plan.module.cloud.ibsp_profit_plan.service.IbspProfitPlanService;
import com.ibs.plan.module.cloud.ibsp_profit_vr.service.IbspProfitVrService;
import com.ibs.plan.module.server.thread.ConfigSetThread;
import com.ibs.plan.module.server.thread.LoginThread;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.quartz.JobExecutionContext;

import java.util.*;

/**
 * 会员自动管理工作类
 *
 * @Author: Dongming
 * @Date: 2020-06-02 17:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MemberAutoManageJob extends BaseCommJob {
	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		Date nowTime = new Date();
		List<String> eventIds = new ArrayList<>();
		IbspCmsNotifyService notifyService = new IbspCmsNotifyService();
		IbspCmsUserNotifyService userNotifyService = new IbspCmsUserNotifyService();
		String userId, account;
		/*
			1.会员定时登录
			2.自动开始投注会员
			3.自动停止投注会员
			4.自动停止新增会员
			5.虚拟个人止盈止损
			6.方案重置情况
			7.真实模拟轮盘情况
			8.方案止盈止损
		 */

		//region 1.会员定时登录
		IbspHandicapMemberService handicapMemberService = new IbspHandicapMemberService();
		IbspEventLoginService eventLoginService = new IbspEventLoginService();
		JSONObject content = new JSONObject();
		List<Map<String, Object>> hmInfos = handicapMemberService.listTimeLanded();
		for (Map<String, Object> hmInfo : hmInfos) {
			String handicapMemberId = hmInfo.get("HANDICAP_MEMBER_ID_").toString();
			content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
			String eventId = EventThreadDefine.saveLoginEvent(eventLoginService, handicapMemberId, content, nowTime);
			eventIds.add(eventId);

			//消息
			userId = hmInfo.get("APP_USER_ID_").toString();
			account = hmInfo.get("MEMBER_ACCOUNT_").toString();
			String message = String
					.format(RecordNotifyTool.MESSAGE_FORMAT, DateTool.getStr(nowTime, RecordNotifyTool.SDF), "定时登录",
							HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName(), account);
			RecordNotifyTool.recordTriggerNotify(notifyService, userNotifyService,
					CacheTool.onlyCode(TypeEnum.MESSAGE, "LOGIN"), message, userId, account, nowTime);
		}
		//endregion
		//开启线程，登录处理事件
		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new LoginThread(eventIds));
			eventIds = new ArrayList<>();
		}

		//region 2.自动开始投注会员
		List<String> ids = new ArrayList<>(hmInfos.size());
		IbspHmGameSetService hmGameSetService = new IbspHmGameSetService();
		hmInfos = hmGameSetService.listAutoStart();

		content.clear();
		content.put("METHOD_", IbsMethodEnum.SET_BET_STATE.name());
		content.put("BET_STATE_", IbsTypeEnum.TRUE.name());
		IbspEventConfigSetService configSetService = new IbspEventConfigSetService();
		//记录开始投注事件，存储事件消息
		for (Map<String, Object> hmInfo : hmInfos) {
			ids.add(hmInfo.get("IBSP_HM_GAME_SET_ID_").toString());
			content.put("GAME_CODE_", GameUtil.code(hmInfo.get("GAME_ID_").toString()).name());
			content.put("HANDICAP_MEMBER_ID_", hmInfo.get("HANDICAP_MEMBER_ID_"));
			String eventId = EventThreadDefine
					.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("，自动开始投注"),
							configSetService);
			eventIds.add(eventId);

			//消息
			userId = hmInfo.get("APP_USER_ID_").toString();
			account = hmInfo.get("MEMBER_ACCOUNT_").toString();
			String message = String
					.format(RecordNotifyTool.MESSAGE_FORMAT, DateTool.getStr(nowTime, RecordNotifyTool.SDF), "自动开始投注会员",
							HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName(), account);
			RecordNotifyTool.recordTriggerNotify(notifyService, userNotifyService,
					CacheTool.onlyCode(TypeEnum.MESSAGE, "BET"), message, userId, account, nowTime);
		}
		//记录信息
		if (ContainerTool.notEmpty(ids)) {
			hmGameSetService.updateAutoStart(ids, nowTime, this.getClass().getName().concat("自动开始投注"));
			ids.clear();
		}
		//endregion

		//region 3.自动停止投注会员
		hmInfos = hmGameSetService.listAutoStop();
		content.put("BET_STATE_", IbsTypeEnum.FALSE.name());
		for (Map<String, Object> hmInfo : hmInfos) {
			ids.add(hmInfo.get("IBSP_HM_GAME_SET_ID_").toString());
			content.put("GAME_CODE_", GameUtil.code(hmInfo.get("GAME_ID_").toString()).name());
			content.put("HANDICAP_MEMBER_ID_", hmInfo.get("HANDICAP_MEMBER_ID_"));
			String eventId = EventThreadDefine
					.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("，自动停止投注"),
							configSetService);
			eventIds.add(eventId);

			//消息
			userId = hmInfo.get("APP_USER_ID_").toString();
			account = hmInfo.get("MEMBER_ACCOUNT_").toString();
			String message = String
					.format(RecordNotifyTool.MESSAGE_FORMAT, DateTool.getStr(nowTime, RecordNotifyTool.SDF), "自动停止投注会员",
							HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName(), account);
			RecordNotifyTool.recordTriggerNotify(notifyService, userNotifyService,
					CacheTool.onlyCode(TypeEnum.MESSAGE, "BET"), message, userId, account, nowTime);
		}
		//记录信息
		if (ContainerTool.notEmpty(ids)) {
			hmGameSetService.updateAutoStop(ids, nowTime, this.getClass().getName().concat("自动停止投注"));
			ids.clear();
		}
		//endregion

		//region 4.自动停止新增会员
		hmInfos = hmGameSetService.listAutoIncrease();
		content.clear();
		content.put("METHOD_", IbsMethodEnum.SET_INCREASE.name());
		content.put("INCREASE_", IbsStateEnum.NOW.name());
		for (Map<String, Object> hmInfo : hmInfos) {
			ids.add(hmInfo.get("IBSP_HM_GAME_SET_ID_").toString());
			content.put("GAME_CODE_", GameUtil.code(hmInfo.get("GAME_ID_").toString()).name());
			content.put("HANDICAP_MEMBER_ID_", hmInfo.get("HANDICAP_MEMBER_ID_"));
			String eventId = EventThreadDefine
					.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("，自动停止新增"),
							configSetService);
			eventIds.add(eventId);

			//消息
			userId = hmInfo.get("APP_USER_ID_").toString();
			account = hmInfo.get("MEMBER_ACCOUNT_").toString();
			String message = String
					.format(RecordNotifyTool.MESSAGE_FORMAT, DateTool.getStr(nowTime, RecordNotifyTool.SDF), "自动停止新增会员",
							HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName(), account);
			RecordNotifyTool.recordTriggerNotify(notifyService, userNotifyService,
					CacheTool.onlyCode(TypeEnum.MESSAGE, "INCREASE"), message, userId, account, nowTime);
		}

		//记录信息
		if (ContainerTool.notEmpty(ids)) {
			hmGameSetService.updateAutoIncrease(ids, nowTime, this.getClass().getName().concat("自动停止新增"));
			ids.clear();
		}
		//endregion

		//region 5.真实个人止盈止损
		List<String> profitIds = new IbspProfitService().listProfitLimit();
		if (ContainerTool.notEmpty(profitIds)) {
			hmInfos = hmGameSetService.listOnBet(profitIds, IbsTypeEnum.REAL.name());
			content.clear();
			content.put("METHOD_", IbsMethodEnum.SET_BET_STATE.name());
			content.put("BET_STATE_", IbsTypeEnum.FALSE.name());
			for (Map<String, Object> hmInfo : hmInfos) {
				ids.add(hmInfo.get("IBSP_HM_GAME_SET_ID_").toString());
				content.put("GAME_CODE_", GameUtil.code(hmInfo.get("GAME_ID_").toString()).name());
				content.put("HANDICAP_MEMBER_ID_", hmInfo.get("HANDICAP_MEMBER_ID_"));
				String eventId = EventThreadDefine
						.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("，真实止盈或止损达到限额"),
								configSetService);
				eventIds.add(eventId);

				//消息
				userId = hmInfo.get("APP_USER_ID_").toString();
				account = hmInfo.get("MEMBER_ACCOUNT_").toString();
				String message = String
						.format(RecordNotifyTool.MESSAGE_FORMAT, DateTool.getStr(nowTime, RecordNotifyTool.SDF),
								"真实止盈或止损达到限额", HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName(), account);
				RecordNotifyTool.recordTriggerNotify(notifyService, userNotifyService,
						CacheTool.onlyCode(TypeEnum.MESSAGE, "PROFIT_"), message, userId, account, nowTime);
			}
			//记录信息
			if (ContainerTool.notEmpty(ids)) {
				hmGameSetService.updateBetState(ids, nowTime, this.getClass().getName().concat("个人止盈止损"));
				ids.clear();
			}
		}
		//模拟个人止盈止损
		List<String> profitVrIds = new IbspProfitVrService().listProfitLimit();
		if (ContainerTool.notEmpty(profitVrIds)) {
			hmInfos = hmGameSetService.listOnBet(profitVrIds, IbsTypeEnum.VIRTUAL.name());
			content.clear();
			content.put("METHOD_", IbsMethodEnum.SET_BET_STATE.name());
			content.put("BET_STATE_", IbsTypeEnum.FALSE.name());
			for (Map<String, Object> hmInfo : hmInfos) {
				ids.add(hmInfo.get("IBSP_HM_GAME_SET_ID_").toString());
				content.put("GAME_CODE_", GameUtil.code(hmInfo.get("GAME_ID_").toString()).name());
				content.put("HANDICAP_MEMBER_ID_", hmInfo.get("HANDICAP_MEMBER_ID_"));
				String eventId = EventThreadDefine
						.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("，模拟止盈或止损达到限额"),
								configSetService);
				eventIds.add(eventId);

				//消息
				userId = hmInfo.get("APP_USER_ID_").toString();
				account = hmInfo.get("MEMBER_ACCOUNT_").toString();
				String message = String
						.format(RecordNotifyTool.MESSAGE_FORMAT, DateTool.getStr(nowTime, RecordNotifyTool.SDF),
								"模拟止盈或止损达到限额", HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName(), account);
				RecordNotifyTool.recordTriggerNotify(notifyService, userNotifyService,
						CacheTool.onlyCode(TypeEnum.MESSAGE, "PROFIT_VR"), message, userId, account, nowTime);
			}
			//记录信息
			if (ContainerTool.notEmpty(ids)) {
				hmGameSetService.updateBetState(ids, nowTime, this.getClass().getName().concat("个人止盈止损"));
				ids.clear();
			}
		}
		//endregion

		//开启线程，设置处理事件
		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new ConfigSetThread(eventIds));
			eventIds = new ArrayList<>();
		}

		//region 6.方案重置情况
		IbspHmSetService hmSetService = new IbspHmSetService();
		hmInfos = hmSetService.listPlanResetInfo();
		content.clear();
		content.put("METHOD_", IbsMethodEnum.SET_PLAN_RESET.name());
		for (Map<String, Object> hmInfo : hmInfos) {
			content.put("HANDICAP_MEMBER_ID_", hmInfo.get("HANDICAP_MEMBER_ID_"));
			String eventId = EventThreadDefine
					.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("，虚拟止盈或止损达到限额"),
							configSetService);
			eventIds.add(eventId);

			userId = hmInfo.get("APP_USER_ID_").toString();
			account = hmInfo.get("MEMBER_ACCOUNT_").toString();
			String message = String.format(RecordNotifyTool.MESSAGE_FORMAT,
					HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName(),
					DateTool.getStr(nowTime, RecordNotifyTool.SDF), "方案重置到自定义限额", account);
			RecordNotifyTool.recordTriggerNotify(notifyService, userNotifyService,
					CacheTool.onlyCode(TypeEnum.MESSAGE, "PLAN_RESET"), message, userId, account, nowTime);
		}
		//修改检验值
		if (ContainerTool.notEmpty(hmInfos)) {
			hmSetService.updateCorrection(hmInfos);
		}
		//endregion

		//region 7.真实模拟轮盘
		/*
			1.查询是否又开启轮盘的
			2.安装开启的，分模式进行查询是否达到切换金额
			3.发送事件，修改数据
		 */
		Map<String, Map<String, Object>> cutoverInfo = hmSetService.listModeCutover();
		if (ContainerTool.notEmpty(cutoverInfo)) {
			IbspHmModeCutoverService modeCutoverService = new IbspHmModeCutoverService();
			//真实转模拟
			Map<String, List<Map<String, Object>>> cutoverIdInfo = modeCutoverService.listCutoverInfo(cutoverInfo.keySet());
			//模拟转真实
			Map<String, List<Map<String, Object>>> cutoverVrIdInfo = modeCutoverService.listCutoverVrInfo(cutoverInfo.keySet());
			//真实转停止
			Map<String, List<Map<String, Object>>> cutoverStopInfo = modeCutoverService.listCutoverStopInfo(cutoverInfo.keySet());
			//模拟转停止
			Map<String, List<Map<String, Object>>> cutoverVrStopInfo = modeCutoverService.listCutoverVrStopInfo(cutoverInfo.keySet());

			content.clear();
			content.put("METHOD_", IbsMethodEnum.SET_BET_MODE.name());
			content.put("BET_MODE_", IbsTypeEnum.VIRTUAL);
			putCutoverInfo(nowTime, eventIds, ids, notifyService, userNotifyService, content, configSetService,
					cutoverInfo, cutoverIdInfo, "，真实转为模拟");
			if (ContainerTool.notEmpty(ids)) {
				hmGameSetService.updateBetMode(ids, IbsTypeEnum.VIRTUAL, IbsTypeEnum.REAL, nowTime, "真实转为模拟");
				ids.clear();
			}
			content.put("BET_MODE", IbsTypeEnum.REAL);
			putCutoverInfo(nowTime, eventIds, ids, notifyService, userNotifyService, content, configSetService,
					cutoverInfo, cutoverVrIdInfo, "，模拟转为真实");
			if (ContainerTool.notEmpty(ids)) {
				hmGameSetService.updateBetMode(ids, IbsTypeEnum.REAL, IbsTypeEnum.VIRTUAL, nowTime, "模拟转为真实");
				ids.clear();
			}
			//转停止投注，需要发送停止投注消息，在修改投注状态
			content.remove("BET_MODE_");
			content.put("METHOD_", IbsMethodEnum.SET_BET_STATE.name());
			content.put("BET_STATE_", IbsTypeEnum.FALSE.name());
			stopCutoverInfo(nowTime, eventIds, ids, notifyService, userNotifyService, content, configSetService,
					cutoverInfo, cutoverStopInfo, "真实转为停止");
			if (ContainerTool.notEmpty(ids)) {
				hmGameSetService.updateBetState(ids, nowTime, "真实转为停止");
				ids.clear();
			}
			stopCutoverInfo(nowTime, eventIds, ids, notifyService, userNotifyService, content, configSetService,
					cutoverInfo, cutoverVrStopInfo, "模拟转为停止");
			if (ContainerTool.notEmpty(ids)) {
				hmGameSetService.updateBetState(ids, nowTime, "模拟转为停止");
				ids.clear();
			}

		}
		//endregion

		//region 8.方案止盈止损
		IbspProfitPlanService profitPlanService = new IbspProfitPlanService();
		hmInfos = profitPlanService.listPlainLimit();

		content.clear();
		content.put("METHOD_", IbsMethodEnum.SET_PLAN_STATE.name());
		content.put("PLAN_STATE_", IbsStateEnum.CLOSE.name());
		for (Map<String, Object> hmInfo : hmInfos) {
			String handicapMemberId = hmInfo.get("HANDICAP_MEMBER_ID_").toString();

			ids.add(hmInfo.get("PLAN_HM_ID_").toString());
			content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
			content.put("GAME_CODE_", GameUtil.code(hmInfo.get("GAME_ID_").toString()).name());
			content.put("PLAN_CODE_", PlanUtil.code(hmInfo.get("PLAN_ID_").toString()).name());
			String eventId = EventThreadDefine.saveMemberConfigSetEvent
					(content, nowTime, this.getClass().getName().concat("，方案止盈止损达到限额"), configSetService);
			eventIds.add(eventId);

			userId = hmInfo.get("APP_USER_ID_").toString();
			account = hmInfo.get("MEMBER_ACCOUNT_").toString();
			String message = String.format(RecordNotifyTool.MESSAGE_FORMAT,
					HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName(),
					DateTool.getStr(nowTime, RecordNotifyTool.SDF), "方案止盈止损达到限额", account);
			RecordNotifyTool.recordTriggerNotify(notifyService, userNotifyService,
					CacheTool.onlyCode(TypeEnum.MESSAGE, "PLAN_PROFIT"), message, userId, account, nowTime);
		}

		//记录信息
		if (ContainerTool.notEmpty(ids)) {
			new IbspPlanHmService().updateState(ids, nowTime, IbsStateEnum.CLOSE, IbsStateEnum.OPEN,
					this.getClass().getName().concat("方案止盈止损达到限额"));
			ids.clear();
		}
		//endregion

		//开启线程，设置处理事件
		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new ConfigSetThread(eventIds));
		}
		//清理十分钟前操作为登陆或登出的会员
		handicapMemberService.updateOperating();
	}

	private void stopCutoverInfo(Date nowTime, List<String> eventIds, List<String> gameSetIds, IbspCmsNotifyService notifyService,
								 IbspCmsUserNotifyService userNotifyService, JSONObject content, IbspEventConfigSetService configSetService,
								 Map<String, Map<String, Object>> cutoverInfo, Map<String, List<Map<String, Object>>> cutoverStopInfo, String desc) throws Exception {
		String account, appUserId, message, handicapMemberId;

		for (Map.Entry<String, List<Map<String, Object>>> entry : cutoverStopInfo.entrySet()) {
			handicapMemberId = entry.getKey();
			//消息
			content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
			account = cutoverInfo.get(handicapMemberId).get("MEMBER_ACCOUNT_").toString();
			message = String
					.format(RecordNotifyTool.MESSAGE_FORMAT, DateTool.getStr(nowTime, RecordNotifyTool.SDF), desc,
							HandicapUtil.code(cutoverInfo.get(handicapMemberId).get("HANDICAP_ID_").toString())
									.getName(), account);
			appUserId = cutoverInfo.get(handicapMemberId).get("APP_USER_ID_").toString();
			for (Map<String, Object> gameIdInfo : entry.getValue()) {

				content.put("GAME_CODE_", GameUtil.code(gameIdInfo.get("GAME_ID_").toString()).name());
				String eventId = EventThreadDefine
						.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat(desc),
								configSetService);
				eventIds.add(eventId);
				RecordNotifyTool.recordTriggerNotify(notifyService, userNotifyService,
						CacheTool.onlyCode(TypeEnum.MESSAGE, "MODE_CUTOVER"), message, appUserId, account, nowTime);
				gameSetIds.add(gameIdInfo.get("IBSP_HM_GAME_SET_ID_").toString());
			}
		}
	}

	/**
	 * 记录轮盘信息
	 */
	private void putCutoverInfo(Date nowTime, List<String> eventIds, List<String> gameSetIds,
								IbspCmsNotifyService notifyService, IbspCmsUserNotifyService userNotifyService, JSONObject content,
								IbspEventConfigSetService configSetService, Map<String, Map<String, Object>> cutoverInfo,
								Map<String, List<Map<String, Object>>> cutoverIdInfo, String desc) throws Exception {
		String account, appUserId, message, handicapMemberId;

		Map<String, JSONObject> cutoverMap = new HashMap<>(cutoverInfo.size());
		List<String> stopModeCutoverHm = new ArrayList<>();
		Map<String, Object> hmCutoverInfo;
		for (Map.Entry<String, List<Map<String, Object>>> entry : cutoverIdInfo.entrySet()) {
			handicapMemberId = entry.getKey();

			hmCutoverInfo = cutoverInfo.get(handicapMemberId);
			JSONObject cutoverGroupData = JSONObject.parseObject(hmCutoverInfo.get("CUTOVER_GROUP_DATA_").toString());
			String[] cutoverKey = hmCutoverInfo.get("CUTOVER_KEY_").toString().split(",");
			int index = Arrays.binarySearch(cutoverKey, hmCutoverInfo.get("CURRENT_INDEX_"));
			//索引加一后小于或等于的，默认为-1
			if (cutoverKey.length > index + 1) {
				JSONObject cutoverData = cutoverGroupData.getJSONObject(cutoverKey[index + 1]);
				cutoverData.put("CURRENT_INDEX_", index + 1);
				//判断是否重置资金
				if (IbsTypeEnum.FALSE.name().equals(hmCutoverInfo.get("RESET_PROFIT_"))) {
					cutoverData.put("PROFIT_T_", hmCutoverInfo.get("PROFIT_T_"));
				} else {
					cutoverData.put("PROFIT_T_", 0);
				}
				cutoverMap.put(handicapMemberId, cutoverData);
			} else {
				stopModeCutoverHm.add(handicapMemberId);
			}
			//消息
			content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
			account = cutoverInfo.get(handicapMemberId).get("MEMBER_ACCOUNT_").toString();
			message = String
					.format(RecordNotifyTool.MESSAGE_FORMAT, DateTool.getStr(nowTime, RecordNotifyTool.SDF), desc,
							HandicapUtil.code(cutoverInfo.get(handicapMemberId).get("HANDICAP_ID_").toString())
									.getName(), account);
			appUserId = cutoverInfo.get(handicapMemberId).get("APP_USER_ID_").toString();
			for (Map<String, Object> gameIdInfo : entry.getValue()) {

				content.put("GAME_CODE_", GameUtil.code(gameIdInfo.get("GAME_ID_").toString()).name());
				String eventId = EventThreadDefine
						.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat(desc),
								configSetService);
				eventIds.add(eventId);
				RecordNotifyTool.recordTriggerNotify(notifyService, userNotifyService,
						CacheTool.onlyCode(TypeEnum.MESSAGE, "MODE_CUTOVER"), message, appUserId, account, nowTime);
				gameSetIds.add(gameIdInfo.get("IBSP_HM_GAME_SET_ID_").toString());
			}
		}
		//重新设置模式切换信息
		IbspHmModeCutoverService modeCutoverService = new IbspHmModeCutoverService();
		if (ContainerTool.notEmpty(cutoverMap)) {
			modeCutoverService.updateCutoverInfo(cutoverMap);
		}
		if (ContainerTool.notEmpty(stopModeCutoverHm)) {
			modeCutoverService.stopCutover(stopModeCutoverHm);
		}
	}
}
