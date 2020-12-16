package com.ibs.plan.module.cloud.core.controller.init;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.entity.IbspHandicapMember;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.entity.IbspHmGameSet;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.entity.IbspHmModeCutover;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.service.IbspHmModeCutoverService;
import com.ibs.plan.module.cloud.ibsp_hm_set.entity.IbspHmSet;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
import com.ibs.plan.module.cloud.ibsp_log_hm.entity.IbspLogHm;
import com.ibs.plan.module.cloud.ibsp_log_hm.service.IbspLogHmService;
import com.ibs.plan.module.cloud.ibsp_plan_hm.service.IbspPlanHmService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import com.ibs.plan.module.cloud.ibsp_profit.entity.IbspProfit;
import com.ibs.plan.module.cloud.ibsp_profit.service.IbspProfitService;
import com.ibs.plan.module.cloud.ibsp_profit_plan.entity.IbspProfitPlan;
import com.ibs.plan.module.cloud.ibsp_profit_plan.service.IbspProfitPlanService;
import com.ibs.plan.module.cloud.ibsp_profit_plan_vr.entity.IbspProfitPlanVr;
import com.ibs.plan.module.cloud.ibsp_profit_plan_vr.service.IbspProfitPlanVrService;
import com.ibs.plan.module.cloud.ibsp_profit_vr.entity.IbspProfitVr;
import com.ibs.plan.module.cloud.ibsp_profit_vr.service.IbspProfitVrService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @Description: 登录控制器工具类
 * @Author: null
 * @Date: 2020-05-23 16:37
 * @Version: v1.0
 */
public class LoginControllerDefine {
	protected static final Logger log = LogManager.getLogger(LoginControllerDefine.class);

	/**
	 * 盘口会员登录初始化数据
	 *
	 * @param handicapMember   盘口会员信息
	 * @param hmSetService     盘口设置服务类
	 * @param hmGameSetService 盘口游戏设置服务类
	 * @return
	 */
	public static Map<String, Object> loginHmInit(IbspHandicapMember handicapMember, IbspHmSetService hmSetService, IbspHmGameSetService hmGameSetService) throws Exception {
		Date nowTime = new Date();
		//获取盘口设置初始化信息
		Map<String, Object> hmSetInfo = hmSetService.findInitInfo();
		if (ContainerTool.isEmpty(hmSetInfo)) {
			log.error("初始化盘口会员设置信息不存在");
			hmSetInfo=initHmSet();
		}
		//添加盘口会员设置
		IbspHmSet hmSet = saveHmSet(handicapMember.getIbspHandicapMemberId(), hmSetInfo, hmSetService, nowTime);

		//添加盘口模式智能切换信息
		saveModeCutover(handicapMember.getIbspHandicapMemberId());

		List<String> gameIds = new ArrayList<>();
		//获取用户可开启游戏,方案
		IbspExpUserService expUserService = new IbspExpUserService();
		Map<String, Object> availableMap = expUserService.findAvailableHandicap(handicapMember.getAppUserId());
		if (StringTool.notEmpty(availableMap.get("AVAILABLE_GAME_"))) {
			String[] availableGames = availableMap.get("AVAILABLE_GAME_").toString().split(",");

			//获取盘口所有开启游戏id
			IbspHmGameSetService handicapGameService = new IbspHmGameSetService();
			gameIds = handicapGameService.findGameIds(handicapMember.getHandicapId(), availableGames);

		}
		if (ContainerTool.isEmpty(gameIds)) {
			throw new RuntimeException("游戏信息不存在");
		}
		//获取盘口游戏设置初始化信息
		Map<String, Object> hmGameSetInfo = hmGameSetService.findInitInfo();
		if (ContainerTool.isEmpty(hmGameSetInfo)) {
			log.error("初始化盘口会员游戏设置信息不存在");
			hmGameSetInfo=initHmGameSet();
		}
		//添加盘口游戏设置信息
		saveHmGameSet(handicapMember.getIbspHandicapMemberId(), handicapMember.getHandicapId(), hmGameSetInfo, gameIds,
				hmGameSetService, nowTime);

		// 真实投注信息
		Map<String, Object> hmSetMap = new HashMap<>(3);
		hmSetMap.put("PROFIT_LIMIT_MAX_", hmSet.getProfitLimitMax());
		hmSetMap.put("PROFIT_LIMIT_MIN_", hmSet.getProfitLimitMin());
		hmSetMap.put("LOSS_LIMIT_MIN_", hmSet.getLossLimitMin());
		hmSetMap.put("BET_RATE_T_", hmSet.getBetRateT());
		hmSetMap.put("BET_MERGER_", hmSet.getBetMerger());
		hmSetMap.put("BLAST_STOP_", hmSet.getBlastStop());

		return hmSetMap;

	}

	/**
	 * 初始化会员游戏设置信息
	 * @return
	 */
	private static Map<String, Object> initHmGameSet() {
		Map<String, Object> hmGameSet = new HashMap<>(10);
		hmGameSet.put("BET_STATE_","FALSE");
		hmGameSet.put("BET_MODE_","REAL");
		hmGameSet.put("IS_AUTO_STOP_BET_","FALSE");
		hmGameSet.put("IS_AUTO_START_BET_","FALSE");
		hmGameSet.put("IS_INVERSE_","FALSE");
		hmGameSet.put("INCREASE_STATE_","NONE");
		hmGameSet.put("BET_SECOND_","300");
		hmGameSet.put("SPLIT_TWO_SIDE_AMOUNT_","30000");
		hmGameSet.put("SPLIT_NUMBER_AMOUNT_","30000");
		return hmGameSet;
	}

	/**
	 * 添加初始化会员设置信息
	 */
	private static Map<String, Object> initHmSet() {
		Map<String, Object> hmSetInfo = new HashMap<>(10);
		hmSetInfo.put("BET_RATE_T_","1000");
		hmSetInfo.put("PROFIT_LIMIT_MAX_","1000000");
		hmSetInfo.put("PROFIT_LIMIT_MIN_","0");
		hmSetInfo.put("LOSS_LIMIT_MIN_","-1000000");
		hmSetInfo.put("RESET_TYPE_","CUSTOM");
		hmSetInfo.put("RESET_PROFIT_MAX_","0");
		hmSetInfo.put("RESET_LOSS_MIN_","0");
		hmSetInfo.put("MODE_CUTOVER_STATE_","CLOSE");
		hmSetInfo.put("BLAST_STOP_","CLOSE");
		hmSetInfo.put("BET_MERGER_","CLOSE");

		return hmSetInfo;
	}

	/**
	 * 添加模式切换信息
	 * @param handicapMemberId	盘口会员id
	 */
	private static void saveModeCutover(String handicapMemberId) throws Exception {
		IbspHmModeCutoverService hmModeCutoverService=new IbspHmModeCutoverService();
		String cutoverGroupData=hmModeCutoverService.findInitInfo();
		if(StringTool.isEmpty(cutoverGroupData)){
			cutoverGroupData="{\"0\":{\"CURRENT_\":\"REAL\",\"CUTOVER_\":\"VIRTUAL\",\"CUTOVER_LOSS_\":-100,\"CUTOVER_PROFIT_\":100"
					+ ",\"RESET_PROFIT_\":\"FALSE\"},\"1\":{\"CURRENT_\":\"VIRTUAL\",\"CUTOVER_\":\"REAL\",\"CUTOVER_LOSS_\":-100,"
					+ "\"CUTOVER_PROFIT_\":100,\"RESET_PROFIT_\":\"FALSE\"}}";
		}
		IbspHmModeCutover hmModeCutover=new IbspHmModeCutover();
		hmModeCutover.setHandicapMemberId(handicapMemberId);
		hmModeCutover.setCutoverGroupData(cutoverGroupData);
		hmModeCutover.setProfitT(0);
		hmModeCutover.setCreateTime(new Date());
		hmModeCutover.setCreateTimeLong(System.currentTimeMillis());
		hmModeCutover.setUpdateTime(new Date());
		hmModeCutover.setUpdateTimeLong(System.currentTimeMillis());
		hmModeCutover.setState(IbsStateEnum.OPEN.name());
		hmModeCutoverService.save(hmModeCutover);
	}

	/**
	 * 初始化盘口会员游戏设置
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param handicapId       盘口id
	 * @param hmGameSetInfo    初始化盘口游戏信息
	 * @param gameIds          游戏ids
	 * @param hmGameSetService 盘口会员游戏设置服务类
	 * @param nowTime          当前时间
	 */
	private static void saveHmGameSet(String handicapMemberId, String handicapId, Map<String, Object> hmGameSetInfo, List<String> gameIds, IbspHmGameSetService hmGameSetService, Date nowTime) throws Exception {
		IbspHmGameSet hmGameSet = new IbspHmGameSet();
		hmGameSet.setHandicapMemberId(handicapMemberId);
		hmGameSet.setHandicapId(handicapId);
		hmGameSet.setBetState(hmGameSetInfo.get("BET_STATE_"));
		hmGameSet.setBetMode(hmGameSetInfo.get("BET_MODE_"));
		hmGameSet.setIsAutoStopBet(hmGameSetInfo.get("IS_AUTO_STOP_BET_"));
		hmGameSet.setAutoStopBetTimeLong(DateTool.getDayStart(nowTime).getTime());
		hmGameSet.setIsAutoStartBet(hmGameSetInfo.get("IS_AUTO_START_BET_"));
		hmGameSet.setAutoStartBetTimeLong(DateTool.getDayStart(nowTime).getTime());
		hmGameSet.setIsInverse(hmGameSetInfo.get("IS_INVERSE_"));
		hmGameSet.setIncreaseState(hmGameSetInfo.get("INCREASE_STATE_"));
		hmGameSet.setIncreaseStopTimeLong(DateTool.getDayStart(nowTime).getTime());
		hmGameSet.setBetSecond(Integer.parseInt(hmGameSetInfo.get("BET_SECOND_").toString()));
		hmGameSet.setSplitTwoSideAmount(Integer.parseInt(hmGameSetInfo.get("SPLIT_TWO_SIDE_AMOUNT_").toString()));
		hmGameSet.setSplitNumberAmount(Integer.parseInt(hmGameSetInfo.get("SPLIT_NUMBER_AMOUNT_").toString()));
		hmGameSet.setCreateTime(nowTime);
		hmGameSet.setCreateTimeLong(nowTime.getTime());
		hmGameSet.setUpdateTime(nowTime);
		hmGameSet.setUpdateTimeLong(nowTime.getTime());
		hmGameSet.setState(IbsStateEnum.OPEN.name());
		for (String gameId : gameIds) {
			hmGameSet.setGameId(gameId);
			hmGameSetService.save(hmGameSet);
		}
	}

	/**
	 * 初始化盘口会员设置信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param hmSetInfo        盘口会员设置初始化信息
	 * @param hmSetService     盘口设置服务类
	 * @param nowTime          当前时间
	 * @return
	 */
	private static IbspHmSet saveHmSet(String handicapMemberId, Map<String, Object> hmSetInfo, IbspHmSetService hmSetService, Date nowTime) throws Exception {
		IbspHmSet hmSet = new IbspHmSet();
		hmSet.setHandicapMemberId(handicapMemberId);
		hmSet.setBetRateT(Integer.parseInt(hmSetInfo.get("BET_RATE_T_").toString()));
		hmSet.setProfitLimitMax(Integer.parseInt(hmSetInfo.get("PROFIT_LIMIT_MAX_").toString()));
		hmSet.setProfitLimitMin(Integer.parseInt(hmSetInfo.get("PROFIT_LIMIT_MIN_").toString()));
		hmSet.setLossLimitMin(Integer.parseInt(hmSetInfo.get("LOSS_LIMIT_MIN_").toString()));
		hmSet.setResetType(hmSetInfo.get("RESET_TYPE_"));
		hmSet.setResetProfitMax(hmSetInfo.get("RESET_PROFIT_MAX_"));
		hmSet.setResetLossMin(hmSetInfo.get("RESET_LOSS_MIN_"));
		hmSet.setModeCutoverState(IbsStateEnum.CLOSE.name());
		hmSet.setBlastStop(hmSetInfo.get("BLAST_STOP_"));
		hmSet.setBetMerger(hmSetInfo.get("BET_MERGER_"));
		hmSet.setCreateTime(nowTime);
		hmSet.setCreateTimeLong(nowTime.getTime());
		hmSet.setUpdateTime(nowTime);
		hmSet.setUpdateTimeLong(nowTime.getTime());
		hmSet.setState(IbsStateEnum.OPEN.name());
		hmSetService.save(hmSet);
		return hmSet;
	}

	/**
	 * 添加盘口会员盈亏信息
	 *
	 * @param hmSetMap       盘口设置信息
	 * @param handicapMember 盘口会员信息
	 * @param profitAmount   盘口盈亏
	 * @param nowTime        当前时间
	 */
	public static void saveHmProfit(Map<String, Object> hmSetMap, IbspHandicapMember handicapMember, String profitAmount, Date nowTime) throws Exception {
		IbspProfitService profitService = new IbspProfitService();
		String profitId = profitService.findByHmId(handicapMember.getIbspHandicapMemberId());
		if (StringTool.notEmpty(profitId)) {
			profitService.updateLogout(handicapMember.getIbspHandicapMemberId(), nowTime);
		}
		IbspProfit profit = new IbspProfit();
		profit.setHandicapMemberId(handicapMember.getIbspHandicapMemberId());
		profit.setHandicapProfitT(NumberTool.longValueT(profitAmount));
		profit.setProfitT(0);
		profit.setBetFundsT(0);
		profit.setBetLen(0);
		profit.setProfitLimitMaxT(NumberTool.longValueT(hmSetMap.get("PROFIT_LIMIT_MAX_")));
		profit.setProfitLimitMinT(NumberTool.longValueT(hmSetMap.get("PROFIT_LIMIT_MIN_")));
		profit.setLossLimitMinT(NumberTool.longValueT(hmSetMap.get("LOSS_LIMIT_MIN_")));
		profit.setCreateTime(nowTime);
		profit.setCreateTimeLong(nowTime.getTime());
		profit.setUpdateTime(nowTime);
		profit.setUpdateTimeLong(nowTime.getTime());
		profit.setState(IbsStateEnum.OPEN.name());
		profitService.save(profit);

		IbspProfitVr profitVr=new IbspProfitVr();
		profitVr.setHandicapMemberId(handicapMember.getIbspHandicapMemberId());
		profitVr.setProfitT(0);
		profitVr.setBetFundsT(0);
		profitVr.setBetLen(0);
		profitVr.setProfitLimitMaxT(NumberTool.longValueT(hmSetMap.get("PROFIT_LIMIT_MAX_")));
		profitVr.setProfitLimitMinT(NumberTool.longValueT(hmSetMap.get("PROFIT_LIMIT_MIN_")));
		profitVr.setLossLimitMinT(NumberTool.longValueT(hmSetMap.get("LOSS_LIMIT_MIN_")));
		profitVr.setCreateTime(nowTime);
		profitVr.setCreateTimeLong(nowTime.getTime());
		profitVr.setUpdateTime(nowTime);
		profitVr.setUpdateTimeLong(nowTime.getTime());
		profitVr.setState(IbsStateEnum.OPEN.name());
		new IbspProfitVrService().save(profitVr);
	}

	/**
	 * 添加方案盈亏信息
	 * @param handicapMember		盘口会员
	 * @param userPlanInfo		用户方案信息
	 */
	public static void saveProfitPlan(IbspHandicapMember handicapMember, List<Map<String, Object>> userPlanInfo,Date nowTime) throws Exception {
		IbspPlanUserService planUserService=new IbspPlanUserService();
		// 获取方案止盈止损信息
		List<Map<String,Object>> planLimitInfo=planUserService.findPlanLimit(userPlanInfo);
		//获取方案会员信息
		Map<String,Map<String,Object>> planHmInfo=new IbspPlanHmService()
				.findPlanHmInfo(handicapMember.getIbspHandicapMemberId());


		IbspProfitPlan profitPlan=new IbspProfitPlan();
		profitPlan.setAppUserId(handicapMember.getAppUserId());
		profitPlan.setHandicapMemberId(handicapMember.getIbspHandicapMemberId());
		profitPlan.setProfitT(0);
		profitPlan.setBetFundsT(0);
		profitPlan.setBetLen(0);
		profitPlan.setCreateTime(nowTime);
		profitPlan.setCreateTimeLong(nowTime.getTime());
		profitPlan.setUpdateTime(nowTime);
		profitPlan.setUpdateTimeLong(nowTime.getTime());
		profitPlan.setState(IbsStateEnum.OPEN.name());

		IbspProfitPlanVr profitPlanVr=new IbspProfitPlanVr();
		profitPlanVr.setAppUserId(handicapMember.getAppUserId());
		profitPlanVr.setHandicapMemberId(handicapMember.getIbspHandicapMemberId());
		profitPlanVr.setProfitT(0);
		profitPlanVr.setBetFundsT(0);
		profitPlanVr.setBetLen(0);
		profitPlanVr.setCreateTime(nowTime);
		profitPlanVr.setCreateTimeLong(nowTime.getTime());
		profitPlanVr.setUpdateTime(nowTime);
		profitPlanVr.setUpdateTimeLong(nowTime.getTime());
		profitPlanVr.setState(IbsStateEnum.OPEN.name());

		IbspProfitPlanService profitPlanService=new IbspProfitPlanService();
		IbspProfitPlanVrService profitPlanVrService=new IbspProfitPlanVrService();
		for(Map<String,Object> planLimit:planLimitInfo){
			String planHmId=planHmInfo.get(planLimit.get("PLAN_ID_")).get(planLimit.get("GAME_ID_")).toString();
			profitPlan.setPlanHmId(planHmId);
			profitPlan.setPlanId(planLimit.get("PLAN_ID_"));
			profitPlan.setGameId(planLimit.get("GAME_ID_"));
			profitPlan.setProfitLimitMaxT(NumberTool.longValueT(planLimit.get("PROFIT_LIMIT_MAX_")));
			profitPlan.setLossLimitMinT(NumberTool.longValueT(planLimit.get("LOSS_LIMIT_MIN_")));
			profitPlanService.save(profitPlan);

			profitPlanVr.setPlanHmId(planHmId);
			profitPlanVr.setPlanId(planLimit.get("PLAN_ID_"));
			profitPlanVr.setGameId(planLimit.get("GAME_ID_"));
			profitPlanVr.setProfitLimitMaxT(NumberTool.longValueT(planLimit.get("PROFIT_LIMIT_MAX_")));
			profitPlanVr.setLossLimitMinT(NumberTool.longValueT(planLimit.get("LOSS_LIMIT_MIN_")));
			profitPlanVrService.save(profitPlanVr);
		}
	}

	/**
	 * 添加盘口会员日志信息
	 * @param handicapMemberId	盘口会员id
	 * @param appUserId			用户id
	 * @param handleType			操作类型
	 */
	public static void saveHmLog(String handicapMemberId, String appUserId, String handleType,Date nowTime) throws Exception {
		IbspLogHm logHm=new IbspLogHm();
		logHm.setHandicapMemberId(handicapMemberId);
		logHm.setAppUserId(appUserId);
		logHm.setHandleType(handleType);
		logHm.setCreateTime(nowTime);
		logHm.setCreateTimeLong(nowTime.getTime());
		logHm.setUpdateTime(nowTime);
		logHm.setUpdateTimeLong(nowTime.getTime());
		logHm.setState(IbsStateEnum.OPEN.name());
		new IbspLogHmService().save(logHm);
	}
}
