package com.ibs.plan.module.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_event_logout.service.IbspEventLogoutService;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.entity.IbspHandicapMember;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import com.ibs.plan.module.cloud.ibsp_log_hm.entity.IbspLogHm;
import com.ibs.plan.module.cloud.ibsp_log_hm.service.IbspLogHmService;
import com.ibs.plan.module.cloud.ibsp_plan_hm.service.IbspPlanHmService;
import com.ibs.plan.module.cloud.ibsp_profit.service.IbspProfitService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.ContainerTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 会员登出控制器
 * @Author: null
 * @Date: 2020-06-02 11:01
 * @Version: v1.0
 */
public class LogoutHmController {
	private Logger log = LogManager.getLogger(this.getClass());

	public JsonResultBeanPlus execute(String handicapMemberId, Date nowTime) throws Exception {
		JsonResultBeanPlus bean=clearData(handicapMemberId,nowTime);

		Map<String,Object> existInfo =new IbspClientHmService().findExistHmInfo(handicapMemberId);
		if (ContainerTool.isEmpty(existInfo)){
			bean.putSysEnum(CodeEnum.IBS_404_DATA);
			bean.putEnum(CodeEnum.CODE_404);
			return bean;
		}
		String clientCode = existInfo.remove("CLIENT_CODE_").toString();

		JSONObject content=new JSONObject();
		content.put("EXIST_HM_ID_", existInfo.get("EXIST_HM_ID_"));
		content.put("METHOD_", IbsMethodEnum.LOGOUT.name());
		String eventId = EventThreadDefine
				.saveLogoutEvent(new IbspEventLogoutService(), handicapMemberId, content, new Date());
		content.put("EVENT_ID_",eventId);

		CurrentTransaction.transactionCommit();
		RabbitMqTool.sendMember(content.toString(),clientCode,"manage");


		return bean.success();
	}

	public JsonResultBeanPlus clearData(String handicapMemberId,Date nowTime) throws Exception {
		JsonResultBeanPlus bean=new JsonResultBeanPlus();
		IbspHandicapMemberService handicapMemberService = new IbspHandicapMemberService();
		IbspHandicapMember handicapMember = handicapMemberService.find(handicapMemberId);
		if (handicapMember == null) {
			log.error("盘口会员登出失败，不存在盘口会员：" + handicapMemberId);
			bean.putSysEnum(CodeEnum.IBS_404_DATA);
			bean.putEnum(CodeEnum.CODE_404);
			return bean;
		}
		handicapMember.setOperating(IbsStateEnum.LOGOUT.name());
		handicapMember.setUpdateTimeLong(System.currentTimeMillis());
		handicapMemberService.update(handicapMember);
		//修改登录状态
		IbspHmInfoService hmInfoService = new IbspHmInfoService();
		hmInfoService.updateLogout(handicapMemberId, nowTime);

		//修改盘口会员用户登录在线信息
		new IbspHmUserService().updateLogout(handicapMemberId, nowTime);

		//修改盘口会员所有游戏投注状态
		new IbspHandicapGameService().updateBetState(handicapMemberId,nowTime);

		//重置投注盈亏信息
		new IbspProfitService().updateLogout(handicapMemberId, nowTime);

		//关闭会员方案
		new IbspPlanHmService().closeHmPlan(handicapMemberId);

		//添加登出日志信息
		saveHmLog(handicapMemberId, handicapMember.getAppUserId());


		new IbspExpUserService().updateLogoutOnline(handicapMember.getAppUserId());

		return JsonResultBeanPlus.successConstant();
	}
	/**
	 * 保存操作日志
	 * @param handicapMemberId 盘口会员主键
	 * @param appUserId 用户主键
	 */
	private void saveHmLog(String handicapMemberId, String appUserId) throws Exception {
		IbspLogHm logHm = new IbspLogHm();
		logHm.setHandicapMemberId(handicapMemberId);
		logHm.setAppUserId(appUserId);
		logHm.setHandleType(IbsStateEnum.LOGOUT.name());
		logHm.setCreateTime(new Date());
		logHm.setCreateTimeLong(System.currentTimeMillis());
		logHm.setUpdateTime(new Date());
		logHm.setUpdateTimeLong(System.currentTimeMillis());
		logHm.setState(IbsStateEnum.OPEN.name());
		new IbspLogHmService().save(logHm);

	}
}
