package com.ibs.plan.module.mq.listener;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.CodeEnum;
import com.common.enums.TypeEnum;
import com.common.tools.CacheTool;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.core.IbsCommMq;
import com.ibs.plan.common.tools.RecordNotifyTool;
import com.ibs.plan.common.utils.ThreadExecuteUtil;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.core.controller.process.LogoutHmController;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.service.IbspCmsUserNotifyService;
import com.ibs.plan.module.cloud.ibsp_event_config_set.service.IbspEventConfigSetService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_profit.service.IbspProfitService;
import com.ibs.plan.module.server.thread.ConfigSetThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 会员信息消息监听器
 *
 * @Author: Dongming
 * @Date: 2020-05-09 15:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class InfoMemberListener extends IbsCommMq {
	private Date nowTime = new Date();
	@Override public String execute(String message) throws Exception {
		log.info(getLog("会员信息消息监听器，接收的消息是：" + message));
		if (valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			log.error(getLog("会员信息消息监听器，处理的结果是：".concat(bean.toString())));
			return null;
		}
		try {
			IbspClientHmService clientHmService = new IbspClientHmService();
			String handicapMemberId = clientHmService.findHmIdByExistId(msgObj.getString("EXIST_HM_ID_"));
			int number = new IbspHmInfoService().updateCheckInfo(handicapMemberId, msgObj, nowTime);
			if (number <= 0) {
				bean.putEnum(CodeEnum.IBS_404_LOGIN);
				bean.putSysEnum(CodeEnum.CODE_404);
				log.debug(getLog("会员信息消息监听器，处理完成，处理的结果是：".concat(bean.toString())));
				return null;
			}
			switch (method) {
				//  会员定时校验
				case CUSTOMER_INFO:
					updateMemberInfo(handicapMemberId);
					break;
				//  会员登出
				case LOGOUT:
					logoutMember(handicapMemberId);
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error("错误的会员登录方法接收:{}", method.name());
					return null;

			}
			log.debug(getLog("会员信息消息监听器，处理完成，处理的结果是：".concat(bean.toString())));
		} catch (Exception e) {
			log.error(getLog("会员信息消息监听器,处理错误:".concat(e.getMessage())));
			throw e;
		}
		return null;
	}
	/**
	 * 用户登出
 	 * @param handicapMemberId 会员主键
	 */
	private void logoutMember(String handicapMemberId) throws Exception {
		LogoutHmController logoutHmController = new LogoutHmController();
		logoutHmController.execute(handicapMemberId,nowTime);
	}

	/**
	 * 更新会员信息
	 * @param handicapMemberId 会员主键
	 */
	private void updateMemberInfo(String handicapMemberId) throws Exception {

		// 判断盈亏限额是否达到
		IbspProfitService profitService = new IbspProfitService();
		Map<String, Object> profitLimitInfo = profitService.findProfitLimit(handicapMemberId);
		if (ContainerTool.isEmpty(profitLimitInfo)) {
			bean.put404HandicapMember();
			return;
		}
		long profitAmount = NumberTool.longValueT(msgObj.get("profitAmount"));
		long profitMax = NumberTool.getLong(profitLimitInfo.get("PROFIT_LIMIT_MAX_T_"));
		long profitMin = NumberTool.getLong(profitLimitInfo.get("PROFIT_LIMIT_MIN_T_"));
		long lossMin = NumberTool.getLong(profitLimitInfo.get("LOSS_LIMIT_MIN_T_"));

		//是否最低限额成功
		boolean limitMin = profitAmount > 0 && profitMin != 0 && profitAmount < profitMin;
		//判断是否达到盈亏限额
		if (profitAmount > profitMax || limitMin || profitAmount < lossMin) {
			limitProcess(handicapMemberId);
		}

		profitService.updateProfit(handicapMemberId, profitAmount, nowTime);
		bean.success();
	}

	/**
	 * 限额处理
	 *
	 * @param handicapMemberId 盘口会员主键
	 */
	private void limitProcess(String handicapMemberId) throws Exception {
		IbspHmGameSetService gameSetService = new IbspHmGameSetService();
		List<Map<String, Object>> hmGameInfos = gameSetService.listOnBets(handicapMemberId);
		if (ContainerTool.isEmpty(hmGameInfos)) {
			return;
		}
		IbspCmsNotifyService notifyService = new IbspCmsNotifyService();
		IbspCmsUserNotifyService userNotifyService = new IbspCmsUserNotifyService();
		IbspEventConfigSetService eventConfigSetService = new IbspEventConfigSetService();

		JSONObject content = new JSONObject();
		content.put("METHOD_", IbsMethodEnum.SET_BET_STATE.name());
		content.put("BET_STATE_", IbsTypeEnum.FALSE.name());
		List<String> eventIds = new ArrayList<>();
		List<String> gameSetIds = new ArrayList<>(hmGameInfos.size());
		for (Map<String, Object> hmInfo : hmGameInfos) {
			gameSetIds.add(hmInfo.get("IBSP_HM_GAME_SET_ID_").toString());
			content.put("GAME_CODE_", GameUtil.code(hmInfo.get("GAME_ID_").toString()).name());
			content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
			String eventId = EventThreadDefine
					.saveMemberConfigSetEvent(content, new Date(), this.getClass().getName().concat("，止盈或止损达到限额"),
							eventConfigSetService);
			eventIds.add(eventId);

			String account = hmInfo.get("MEMBER_ACCOUNT_").toString();
			String message = String
					.format(RecordNotifyTool.MESSAGE_FORMAT, DateTool.getStr(nowTime, RecordNotifyTool.SDF),
							"止盈或止损达到限额", HandicapUtil.code(hmInfo.get("HANDICAP_ID_").toString()).getName(), account);
			RecordNotifyTool.recordTriggerNotify(notifyService, userNotifyService,
					CacheTool.onlyCode(TypeEnum.MESSAGE, "INCREASE"), message, hmInfo.get("APP_USER_ID_").toString(),
					account, nowTime);
		}
		if (ContainerTool.notEmpty(eventIds)) {
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new ConfigSetThread(eventIds));
		}
		if (ContainerTool.notEmpty(gameSetIds)) {
			gameSetService.updateBetState(gameSetIds, nowTime,"会员检验达到限制的盘口会员,停止投注状态");
		}

	}
}
