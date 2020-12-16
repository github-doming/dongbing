package com.ibs.plan.module.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.handicap.crawler.entity.MemberUser;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.core.controller.init.LoginControllerDefine;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import com.ibs.plan.module.cloud.ibsp_client_hm.entity.IbspClientHm;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.entity.IbspHandicapMember;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.entity.IbspHmInfo;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import com.ibs.plan.module.cloud.ibsp_plan_hm.service.IbspPlanHmService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import com.ibs.plan.module.cloud.ibsp_profit.service.IbspProfitService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 会员登录控制器
 * @Author: null
 * @Date: 2020-05-23 15:12
 * @Version: v1.0
 */
public class LoginHmController {
	protected static final Logger log = LogManager.getLogger(LoginHmController.class);

	private String handicapMemberId;

	public LoginHmController(String handicapMemberId) {
		this.handicapMemberId = handicapMemberId;
	}

	public String execute(Object data) throws Exception {
		Date nowTime = new Date();
		IbspHandicapMemberService handicapMemberService = new IbspHandicapMemberService();
		IbspHandicapMember handicapMember = handicapMemberService.find(handicapMemberId);
		if (handicapMember == null) {
			log.error("盘口会员登录失败，不存在盘口会员：{}", handicapMemberId);
			return null;
		}
		//获取用户状态
		String state = handicapMember.getState();
		//首次登陆修改状态
		if (IbsStateEnum.FIRST.name().equals(state)) {
			handicapMember.setState(IbsStateEnum.OPEN.name());
		}
		handicapMember.setOperating(IbsStateEnum.NONE.name());
		handicapMember.setUpdateTimeLong(System.currentTimeMillis());
		handicapMemberService.update(handicapMember);

		JSONObject dataObj = JSONObject.parseObject(data.toString());
		String existHmId = dataObj.getString("EXIST_HM_ID_");
		//添加会员存在信息
		if(!saveExistInfo(existHmId,dataObj,nowTime)){
			log.error("客户端信息错误，错误内容=【{}】",dataObj);
			return null;
		}

		String profitAmount = "0";
		if (dataObj.containsKey("MEMBER_INFO_")) {
			MemberUser userInfo =JSONObject.parseObject(dataObj.getString("MEMBER_INFO_"),MemberUser.class);
			//盈亏金额profitAmount
			profitAmount = String.valueOf(userInfo.getProfitAmount());
			//保存用户信息
			saveMemberInfo(userInfo,handicapMember,nowTime);
		}
		//2，修改盘口用户登录在线数量
		new IbspHmUserService().updateLogin(handicapMemberId,nowTime);
		Map<String, Object> hmSetMap;
		IbspHmSetService hmSetService=new IbspHmSetService();
		IbspHmGameSetService hmGameSetService=new IbspHmGameSetService();
		//3，首次登陆，初始化信息
		if (IbsStateEnum.FIRST.name().equals(state)) {
			hmSetMap = LoginControllerDefine.loginHmInit(handicapMember, hmSetService, hmGameSetService);
		}else{
			hmSetMap = hmSetService.findByHmId(handicapMemberId);
		}
		//清除历史盘口会员盈亏信息，新增新的盈亏信息
		LoginControllerDefine.saveHmProfit(hmSetMap, handicapMember, profitAmount, nowTime);

		IbspPlanUserService planUserService=new IbspPlanUserService();
		List<Map<String, Object>> userPlanInfo=planUserService.findPlanByUserId(handicapMember.getAppUserId());
		if(ContainerTool.notEmpty(userPlanInfo)){
			IbspPlanHmService planHmService=new IbspPlanHmService();
			Map<String,String> planHmInfo=planHmService.findPlanHm(handicapMemberId);
			planHmService.initPlanHm(handicapMember,userPlanInfo,planHmInfo);
			//清除会员方案盈亏信息
			new IbspProfitService().closeProfitPlan(handicapMember.getIbspHandicapMemberId(), nowTime);
//			LoginControllerDefine.saveProfitPlan(handicapMember,userPlanInfo,nowTime);
		}
		if (ContainerTool.isEmpty(hmSetMap)) {
			log.error("获取盘口会员【{}】设置信息失败",handicapMemberId);
			return null;
		}
		//4,写入客户设置事件
		List<Map<String, Object>> gameInfo = hmGameSetService.findByHmId(handicapMemberId);
		if (ContainerTool.isEmpty(gameInfo)) {
			log.error("获取盘口会员【{}】游戏设置信息失败",handicapMemberId);
			return null;
		}
		CurrentTransaction.transactionCommit();

		String clientCode=dataObj.getString("CLIENT_CODE_");
		//发送游戏设置消息和会员设置信息
		JSONObject content = new JSONObject();
		content.put("EXIST_HM_ID_", existHmId);
		content.put("GAME_INFO_", gameInfo);
		content.put("METHOD_", IbsMethodEnum.SET_GAME_INFO.name());
		String eventId=EventThreadDefine.saveMemberConfigSetEvent(content,nowTime);
		content.put("EVENT_ID_",eventId);

		RabbitMqTool.sendMember(content.toString(),clientCode,"set");


		//发送会员设置信息
		content.remove("GAME_INFO_");
		content.put("METHOD_", IbsMethodEnum.SET_HANDICAP.name());
		content.put("BET_RATE_T_", hmSetMap.get("BET_RATE_T_"));
		content.put("BET_MERGER_", hmSetMap.get("BET_MERGER_"));
		content.put("BLAST_STOP_", hmSetMap.get("BLAST_STOP_"));
		eventId=EventThreadDefine.saveMemberConfigSetEvent(content,nowTime);
		content.put("EVENT_ID_",eventId);
		RabbitMqTool.sendMember(content.toString(),clientCode,"set");
		//发送方案信息
		List<Map<String,Object>> planInfo=planUserService.findPlanInfo(handicapMemberId);
		if(ContainerTool.notEmpty(planInfo)){
			content.remove("BET_RATE_T_");
			content.remove("BET_MERGER_");
			content.remove("BLAST_STOP_");
			content.put("METHOD_", IbsMethodEnum.SET_PLAN_INFO.name());
			content.put("PLAN_INFO_",planInfo);
			List<Map<String,Object>> planItems=planUserService.findPlanItems(planInfo);
			content.put("PLAN_ITEM_",planItems);
			eventId=EventThreadDefine.saveMemberConfigSetEvent(content,nowTime);
			content.put("EVENT_ID_",eventId);
			RabbitMqTool.sendMember(content.toString(),clientCode,"set");
		}
		//修改在线数量
		IbspExpUserService expUserService=new IbspExpUserService();
		expUserService.updateLoginOnline(handicapMember.getAppUserId());
		//添加登录日志信息
		LoginControllerDefine.saveHmLog(handicapMemberId, handicapMember.getAppUserId(), IbsStateEnum.LOGIN.name(),nowTime);
		return handicapMemberId;
	}

	private void saveMemberInfo(MemberUser userInfo,IbspHandicapMember handicapMember,Date nowTime) throws Exception {
		//会员账户memberAccount
		String memberAccount = userInfo.getMemberAccount();
		//盈亏金额profitAmount
		long profitAmount = NumberTool.longValueT(userInfo.getProfitAmount());
		//可用额度usedQuota
		long usedQuota = NumberTool.longValueT(userInfo.getUsedQuota());
		//1，修改登录状态，获取盘口会员信息
		IbspHmInfoService hmInfoService = new IbspHmInfoService();
		String hmInfoId = hmInfoService.findIdByHmId(handicapMemberId);
		if (StringTool.isEmpty(hmInfoId)) {
			IbspHmInfo hmInfo = new IbspHmInfo();
			hmInfo.setAppUserId(handicapMember.getAppUserId());
			hmInfo.setHandicapMemberId(handicapMemberId);
			hmInfo.setHandicapId(handicapMember.getHandicapId());
			hmInfo.setMemberAccount(memberAccount);
			hmInfo.setHandicapProfitT(profitAmount);
			hmInfo.setAmountT(usedQuota);
			hmInfo.setLoginState(IbsStateEnum.LOGIN.name());
			hmInfo.setCreateTime(nowTime);
			hmInfo.setCreateTimeLong(nowTime.getTime());
			hmInfo.setUpdateTime(nowTime);
			hmInfo.setUpdateTimeLong(nowTime.getTime());
			hmInfo.setState(IbsStateEnum.OPEN.name());
			hmInfoService.save(hmInfo);
		} else {
			hmInfoService.updateById(hmInfoId, memberAccount, profitAmount, usedQuota, nowTime);
		}
	}

	/**
	 * 保存会员存在信息
	 * @param data		登录结果信息
	 * @param nowTime	当前时间
	 */
	private boolean saveExistInfo(String existHmId,JSONObject data,Date nowTime) throws Exception {
		String clientCode=data.getString("CLIENT_CODE_");
		String clientId=new IbspClientService().findId(clientCode);
		if(StringTool.isEmpty(clientId)){
			return false;
		}

		IbspClientHmService clientHmService = new IbspClientHmService();
		IbspClientHm clientHm = clientHmService.findByHmId(handicapMemberId);
		if (clientHm == null) {
			clientHm = new IbspClientHm();
			clientHm.setExistHmId(existHmId);
			clientHm.setHandicapMemberId(handicapMemberId);
			clientHm.setClientId(clientId);
			clientHm.setClientCode(clientCode);
			clientHm.setHandicapCode(data.getString("HANDICAP_CODE_"));
			clientHm.setCreateTime(nowTime);
			clientHm.setCreateTimeLong(nowTime.getTime());
			clientHm.setUpdateTime(nowTime);
			clientHm.setUpdateTimeLong(nowTime.getTime());
			clientHm.setState(IbsStateEnum.OPEN.name());
			clientHmService.save(clientHm);
		} else {
			clientHm.setExistHmId(existHmId);
			clientHm.setClientId(clientId);
			clientHm.setClientCode(clientCode);
			clientHm.setUpdateTime(nowTime);
			clientHm.setUpdateTimeLong(nowTime.getTime());
			clientHmService.update(clientHm);
		}
		return true;
	}
}
