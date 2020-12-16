package com.ibm.follow.servlet.cloud.core.controller.init;
import com.alibaba.fastjson.JSONArray;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.entity.IbmHaGameSet;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_set.entity.IbmHaSet;
import com.ibm.follow.servlet.cloud.ibm_ha_set.service.IbmHaSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.entity.IbmHandicapAgent;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.entity.IbmHandicapMember;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.entity.IbmHmGameSet;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.entity.IbmHmProfit;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.service.IbmHmProfitService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_vr.entity.IbmHmProfitVr;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_vr.service.IbmHmProfitVrService;
import com.ibm.follow.servlet.cloud.ibm_hm_set.entity.IbmHmSet;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import com.ibm.follow.servlet.cloud.ibm_log_ha.entity.IbmLogHa;
import com.ibm.follow.servlet.cloud.ibm_log_ha.service.IbmLogHaService;
import com.ibm.follow.servlet.cloud.ibm_log_hm.entity.IbmLogHm;
import com.ibm.follow.servlet.cloud.ibm_log_hm.service.IbmLogHmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 登录控制器工具类
 * @Author: null
 * @Date: 2019-09-24 10:43
 * @Version: v1.0
 */
public class LoginControllerDefine {
	protected static final Logger log = LogManager.getLogger(LoginControllerDefine.class);
	//TODO,会员方法
	/**
	 * 盘口会员登录初始化数据
	 *
	 * @param handicapMember   盘口会员信息
	 * @param hmSetService     盘口设置服务类
	 * @param hmGameSetService 盘口游戏设置服务类
	 * @return
	 */
	public static Map<String, Object> loginHmInit(IbmHandicapMember handicapMember, IbmHmSetService hmSetService,
			IbmHmGameSetService hmGameSetService) throws Exception {
		Date nowTime = new Date();
		//获取盘口设置初始化信息
		Map<String, Object> hmSetInfo = hmSetService.findInitInfo();
		if (ContainerTool.isEmpty(hmSetInfo)) {
			throw new RuntimeException("初始化盘口会员设置信息不存在");
		}
		//添加盘口会员设置
		IbmHmSet hmSet = saveHmSet(handicapMember.getIbmHandicapMemberId(), handicapMember.getHandicapId(), hmSetInfo,
				hmSetService, nowTime);

		//获取用户可开启游戏
        IbmExpUserService expUserService=new IbmExpUserService();
        Map<String,Object> availableMap=expUserService.findAvailableHandicap(handicapMember.getAppUserId());
        String[] availableGame=availableMap.get("AVAILABLE_GAME_").toString().split(",");

		//获取盘口所有开启游戏id
		IbmHandicapGameService handicapGameService=new IbmHandicapGameService();
		List<String> gameIds=handicapGameService.findGameIds(handicapMember.getHandicapId(),availableGame);
		if (ContainerTool.isEmpty(gameIds)) {
			throw new RuntimeException("游戏信息不存在");
		}
		//获取盘口游戏设置初始化信息
		Map<String, Object> hmGameSetInfo = hmGameSetService.findInitInfo();
		if (ContainerTool.isEmpty(hmGameSetInfo)) {
			throw new RuntimeException("初始化盘口游戏设置信息不存在");
		}
		//添加盘口游戏设置信息
		saveHmGameSet(handicapMember.getIbmHandicapMemberId(), handicapMember.getAppUserId(),handicapMember.getHandicapId(), hmGameSetInfo, gameIds,
				hmGameSetService, nowTime);
		// 真实投注信息
		Map<String, Object> hmSetMap = new HashMap<>(3);
		hmSetMap.put("PROFIT_LIMIT_MAX_", hmSet.getProfitLimitMax());
		hmSetMap.put("PROFIT_LIMIT_MIN_", hmSet.getProfitLimitMin());
		hmSetMap.put("LOSS_LIMIT_MIN_", hmSet.getLossLimitMin());
		hmSetMap.put("BET_RATE_T_", hmSet.getBetRateT());
		hmSetMap.put("BET_MERGER_", hmSet.getBetMerger());

		return hmSetMap;
	}
	/**
	 * 初始化盘口会员游戏设置
	 *  @param handicapMemberId 盘口会员id
	 * @param appUserId        用户id
	 * @param handicapId
	 * @param hmGameSetInfo    初始化盘口游戏信息
	 * @param gameIds          游戏ids
	 * @param hmGameSetService 盘口会员游戏设置服务类
	 * @param nowTime          当前时间
	 */
	private static void saveHmGameSet(String handicapMemberId, String appUserId, String handicapId, Map<String, Object> hmGameSetInfo,
			List<String> gameIds, IbmHmGameSetService hmGameSetService, Date nowTime) throws Exception {
		IbmHmGameSet hmGameSet = new IbmHmGameSet();
		hmGameSet.setHandicapMemberId(handicapMemberId);
		hmGameSet.setUserId(appUserId);
		hmGameSet.setHandicapId(handicapId);
		hmGameSet.setBetState(hmGameSetInfo.get("BET_STATE_"));
		hmGameSet.setBetMode(hmGameSetInfo.get("BET_MODE_"));
		hmGameSet.setBetAutoStop(hmGameSetInfo.get("BET_AUTO_STOP_"));
		hmGameSet.setBetAutoStopTimeLong(DateTool.getDayStart(nowTime).getTime());
		hmGameSet.setBetAutoStart(hmGameSetInfo.get("BET_AUTO_START_"));
		hmGameSet.setBetAutoStartTimeLong(DateTool.getDayStart(nowTime).getTime());
		hmGameSet.setBetSecond(Integer.parseInt(hmGameSetInfo.get("BET_SECOND_").toString()));
		hmGameSet.setSplitTwoSideAmount(Integer.parseInt(hmGameSetInfo.get("SPLIT_TWO_SIDE_AMOUNT_").toString()));
		hmGameSet.setSplitNumberAmount(Integer.parseInt(hmGameSetInfo.get("SPLIT_NUMBER_AMOUNT_").toString()));
		hmGameSet.setCreateTime(nowTime);
		hmGameSet.setCreateTimeLong(nowTime.getTime());
		hmGameSet.setUpdateTime(nowTime);
		hmGameSet.setUpdateTimeLong(nowTime.getTime());
		hmGameSet.setState(IbmStateEnum.OPEN.name());
		for (String gameId : gameIds) {
			hmGameSet.setGameId(gameId);
			hmGameSetService.save(hmGameSet);
		}
	}
	/**
	 * 初始化盘口会员设置信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param handicapId       盘口id
	 * @param hmSetInfo        盘口会员设置初始化信息
	 * @param hmSetService     盘口设置服务类
	 * @param nowTime          当前时间
	 * @return
	 */
	private static IbmHmSet saveHmSet(String handicapMemberId, String handicapId, Map<String, Object> hmSetInfo,
			IbmHmSetService hmSetService, Date nowTime) throws Exception {
		IbmHmSet hmSet = new IbmHmSet();
		hmSet.setHandicapMemberId(handicapMemberId);
		hmSet.setHandicapId(handicapId);
		hmSet.setBetRecordRows(Integer.parseInt(hmSetInfo.get("BET_RECORD_ROWS_").toString()));
		hmSet.setBetRateT(Integer.parseInt(hmSetInfo.get("BET_RATE_T_").toString()));
		hmSet.setProfitLimitMax(Integer.parseInt(hmSetInfo.get("PROFIT_LIMIT_MAX_").toString()));
		hmSet.setProfitLimitMin(Integer.parseInt(hmSetInfo.get("PROFIT_LIMIT_MIN_").toString()));
		hmSet.setLossLimitMin(Integer.parseInt(hmSetInfo.get("LOSS_LIMIT_MIN_").toString()));
		hmSet.setBetMerger(hmSetInfo.get("BET_MERGER_"));
		hmSet.setCreateTime(nowTime);
		hmSet.setCreateTimeLong(nowTime.getTime());
		hmSet.setUpdateTime(nowTime);
		hmSet.setUpdateTimeLong(nowTime.getTime());
		hmSet.setState(IbmStateEnum.OPEN.name());
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
	public static void saveHmProfit(Map<String, Object> hmSetMap, IbmHandicapMember handicapMember, String profitAmount,
			Date nowTime) throws Exception {
		IbmHmProfitService hmProfitService = new IbmHmProfitService();
		String profitId = hmProfitService.findByHmId(handicapMember.getIbmHandicapMemberId());
		if (StringTool.notEmpty(profitId)) {
			hmProfitService.updateLogout(handicapMember.getIbmHandicapMemberId(), nowTime);
		}
		IbmHmProfit hmProfit = new IbmHmProfit();
		hmProfit.setHandicapMemberId(handicapMember.getIbmHandicapMemberId());
		hmProfit.setHandicapProfitT(NumberTool.longValueT(profitAmount));
		hmProfit.setProfitT(0);
		hmProfit.setBetFundsT(0);
		hmProfit.setBetLen(0);
		hmProfit.setProfitLimitMax(hmSetMap.get("PROFIT_LIMIT_MAX_"));
		hmProfit.setProfitLimitMin(hmSetMap.get("PROFIT_LIMIT_MIN_"));
		hmProfit.setLossLimitMin(hmSetMap.get("LOSS_LIMIT_MIN_"));
		hmProfit.setCreateTime(nowTime);
		hmProfit.setCreateTimeLong(nowTime.getTime());
		hmProfit.setUpdateTime(nowTime);
		hmProfit.setUpdateTimeLong(nowTime.getTime());
		hmProfit.setState(IbmStateEnum.OPEN.name());
		hmProfitService.save(hmProfit);
	}
	/**
	 * 添加盘口会员模拟盈亏信息
	 *
	 * @param hmSetMap       盘口设置信息
	 * @param handicapMember 盘口会员信息
	 * @param nowTime        当前时间
	 */
	public static void saveHmProfitVr(Map<String, Object> hmSetMap, IbmHandicapMember handicapMember, Date nowTime)
			throws Exception {
		IbmHmProfitVrService hmProfitVrService = new IbmHmProfitVrService();
		String profitVrId = hmProfitVrService.findByHmId(handicapMember.getIbmHandicapMemberId());
		if (StringTool.notEmpty(profitVrId)) {
			hmProfitVrService.updateLogout(handicapMember.getIbmHandicapMemberId(),nowTime);
		}
		IbmHmProfitVr hmProfitVr = new IbmHmProfitVr();
		hmProfitVr.setHandicapMemberId(handicapMember.getIbmHandicapMemberId());
		hmProfitVr.setProfitT(0);
		hmProfitVr.setBetFundsT(0);
		hmProfitVr.setBetLen(0);
		hmProfitVr.setProfitLimitMax(hmSetMap.get("PROFIT_LIMIT_MAX_"));
		hmProfitVr.setProfitLimitMin(hmSetMap.get("PROFIT_LIMIT_MIN_"));
		hmProfitVr.setLossLimitMin(hmSetMap.get("LOSS_LIMIT_MIN_"));
		hmProfitVr.setCreateTime(nowTime);
		hmProfitVr.setCreateTimeLong(nowTime.getTime());
		hmProfitVr.setUpdateTime(nowTime);
		hmProfitVr.setUpdateTimeLong(nowTime.getTime());
		hmProfitVr.setState(IbmStateEnum.OPEN.name());
		hmProfitVrService.save(hmProfitVr);
	}
	/**
	 * 添加盘口会员日志信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param appUserId        用户id
	 * @param handleType       操作类型
	 */
	public static void saveHmLog(String handicapMemberId, String appUserId, String handleType) throws Exception {
		IbmLogHmService logHmService = new IbmLogHmService();
		IbmLogHm logHm = new IbmLogHm();
		logHm.setHandicapMemberId(handicapMemberId);
		logHm.setAppUserId(appUserId);
		logHm.setHandleType(handleType);
		logHm.setCreateTime(new Date());
		logHm.setCreateTimeLong(System.currentTimeMillis());
		logHm.setUpdateTime(new Date());
		logHm.setUpdateTimeLong(System.currentTimeMillis());
		logHm.setState(IbmStateEnum.OPEN.name());
		logHmService.save(logHm);
	}
	//TODO,代理方法
	/**
	 * 盘口代理登录初始化信息
	 *
	 * @param handicapAgent  盘口代理信息
	 * @param memberListInfo 会员列表信息
	 * @return
	 */
	public static void loginHaInit(IbmHandicapAgent handicapAgent, JSONArray memberListInfo) throws Exception {
		Date nowTime = new Date();
		//添加盘口代理设置
		saveHaSet(handicapAgent.getIbmHandicapAgentId(), handicapAgent.getHandicapId(), memberListInfo, nowTime);
        //获取用户可开启游戏
        IbmExpUserService expUserService=new IbmExpUserService();
        Map<String,Object> availableMap=expUserService.findAvailableHandicap(handicapAgent.getAppUserId());
        String[] availableGame=availableMap.get("AVAILABLE_GAME_").toString().split(",");

        //获取所有开启游戏id
		IbmHandicapGameService handicapGameService=new IbmHandicapGameService();
		List<String> gameIds=handicapGameService.findGameIds(handicapAgent.getHandicapId(),availableGame);
		if (ContainerTool.isEmpty(gameIds)) {
			throw new RuntimeException("游戏信息不存在");
		}
		//获取盘口游戏设置初始化信息
		IbmHaGameSetService haGameSetService = new IbmHaGameSetService();
		Map<String, Object> haGameSetInfo = haGameSetService.findInitInfo();
		if (ContainerTool.isEmpty(haGameSetInfo)) {
			throw new RuntimeException("初始化盘口游戏设置信息不存在");
		}
		//添加盘口游戏设置信息
		saveHaGameSet(handicapAgent, haGameSetInfo, gameIds, haGameSetService, nowTime);
	}
	/**
	 * 初始化盘口代理设置信息
	 *
	 * @param handicapAgentId 盘口代理id
	 * @param handicapId      盘口id
	 * @param memberListInfo  会员列表信息
	 * @param nowTime         当前时间
	 */
	private static void saveHaSet(String handicapAgentId, String handicapId, JSONArray memberListInfo, Date nowTime)
			throws Exception {
		IbmHaSetService haSetService = new IbmHaSetService();
		IbmHaSet haSet = new IbmHaSet();
		haSet.setHandicapAgentId(handicapAgentId);
		haSet.setHandicapId(handicapId);
		haSet.setFollowMemberType(IbmTypeEnum.ALL.name());
		haSet.setMemberListInfo(memberListInfo);
		haSet.setCreateTime(nowTime);
		haSet.setCreateTimeLong(nowTime.getTime());
		haSet.setUpdateTime(nowTime);
		haSet.setUpdateTimeLong(nowTime.getTime());
		haSet.setState(IbmStateEnum.OPEN.name());
		haSetService.save(haSet);
	}
	/**
	 * 初始化盘口代理游戏设置信息
	 *
	 * @param handicapAgent    盘口代理信息
	 * @param haGameSetInfo    盘口游戏设置信息
	 * @param gameIds          游戏ids
	 * @param haGameSetService 盘口代理游戏设置服务类
	 * @param nowTime          当前时间
	 */
	private static void saveHaGameSet(IbmHandicapAgent handicapAgent, Map<String, Object> haGameSetInfo,
			List<String> gameIds, IbmHaGameSetService haGameSetService, Date nowTime) throws Exception {
		IbmHaGameSet haGameSet = new IbmHaGameSet();
		haGameSet.setHandicapAgentId(handicapAgent.getIbmHandicapAgentId());
		haGameSet.setHandicapId(handicapAgent.getHandicapId());
		haGameSet.setUserId(handicapAgent.getAppUserId());
		haGameSet.setBetState(haGameSetInfo.get("BET_STATE_"));
		haGameSet.setBetFollowMultipleT(haGameSetInfo.get("BET_FOLLOW_MULTIPLE_T_"));
		haGameSet.setBetLeastAmountT(haGameSetInfo.get("BET_LEAST_AMOUNT_T_"));
		haGameSet.setBetMostAmountT(haGameSetInfo.get("BET_MOST_AMOUNT_T_"));
		haGameSet.setBetFilterNumber(haGameSetInfo.get("BET_FILTER_NUMBER_"));
		haGameSet.setBetFilterTwoSide(haGameSetInfo.get("BET_FILTER_TWO_SIDE_"));
		haGameSet.setNumberOpposing(haGameSetInfo.get("NUMBER_OPPOSING_"));
		haGameSet.setTwoSideOpposing(haGameSetInfo.get("TWO_SIDE_OPPOSING_"));
		haGameSet.setBetRecordRows(Integer.parseInt(haGameSetInfo.get("BET_RECORD_ROWS_").toString()));
		haGameSet.setCreateTime(nowTime);
		haGameSet.setCreateTimeLong(nowTime.getTime());
		haGameSet.setUpdateTime(nowTime);
		haGameSet.setUpdateTimeLong(nowTime.getTime());
		haGameSet.setState(IbmStateEnum.OPEN.name());
		for (String gameId : gameIds) {
			haGameSet.setGameId(gameId);
			haGameSetService.save(haGameSet);
		}
	}
	/**
	 * 添加盘口代理日志信息
	 *
	 * @param handicapAgentId 盘口代理id
	 * @param appUserId       用户id
	 * @param handleType      操作类型
	 */
	public static void saveHaLog(String handicapAgentId, String appUserId, String handleType) throws Exception {
		IbmLogHaService logHaService = new IbmLogHaService();
		IbmLogHa logHa = new IbmLogHa();
		logHa.setHandicapAgentId(handicapAgentId);
		logHa.setAppUserId(appUserId);
		logHa.setHandleType(handleType);
		logHa.setCreateTime(new Date());
		logHa.setCreateTimeLong(System.currentTimeMillis());
		logHa.setUpdateTime(new Date());
		logHa.setUpdateTimeLong(System.currentTimeMillis());
		logHa.setState(IbmStateEnum.OPEN.name());
		logHaService.save(logHa);
	}
}
