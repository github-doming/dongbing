package com.ibs.plan.module.cloud.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.service.CacheService;
import com.common.tools.CacheTool;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.service.IbspHmBetItemService;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.service.IbspHmModeCutoverService;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
import com.ibs.plan.module.cloud.ibsp_plan_hm.service.IbspPlanHmService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import com.ibs.plan.module.cloud.ibsp_profit.service.IbspProfitService;
import com.ibs.plan.module.cloud.ibsp_profit_period.service.IbspProfitPeriodService;
import com.ibs.plan.module.cloud.ibsp_profit_plan.service.IbspProfitPlanService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * 中心端开奖信息控制器
 *
 * @Author: Dongming
 * @Date: 2020-06-01 16:04
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CloudDrawController extends BaseCommThread {
	private static final Logger log = LogManager.getLogger(CloudDrawController.class);
	private GameUtil.Code gameCode;
	private Object period;
	private String drawType;
	private String drawNumber;
	private HandicapUtil.Code handicapCode;
	private JSONObject drawInfo;
	public CloudDrawController(GameUtil.Code gameCode, HandicapUtil.Code handicapCode, Object period,
							   String drawType, String drawNumber,JSONObject drawInfo) {
		this.gameCode = gameCode;
		this.period = period;
		this.drawType = drawType;
		this.drawNumber = drawNumber;
		this.handicapCode=handicapCode;
		this.drawInfo=drawInfo;
	}
	@Override
	public String execute(String ignore) throws Exception {
		Date nowTime = new Date();
		//保存开奖数据
		String drawTableName=gameCode.getGameFactory().period(handicapCode).getDrawTableName();
		new CacheService().save(drawType,drawInfo,drawTableName,nowTime);
		//重启事务
		CurrentTransaction.transactionCommit();
		//获取上一期投注信息
		IbspHmBetItemService betItemService = new IbspHmBetItemService();
		List<String> handicapIds = new IbspHandicapGameService().listHandicapId(gameCode, drawType);
		if (ContainerTool.isEmpty(handicapIds)) {
			log.info("游戏{}，开奖类型{}，开启的盘口为空", gameCode.getName(), drawType);
			return null;
		}
		String gameId =GameUtil.id(gameCode);
		if(StringTool.isContains(period.toString(),"-")){
			period=period.toString().substring(4);
		}
		Map<String, List<Map<String, Object>>> betItemMap = betItemService.mapBetInfo(gameId, period, handicapIds);
		if (ContainerTool.isEmpty(betItemMap)) {
			return null;
		}
		//获取开启轮盘的会员信息
		Map<String,String> cutoverHmMap=new IbspHmSetService().listModeCutover(betItemMap.keySet());
		//开启轮盘会员盈亏信息
		Map<String,Object> cutoverHmProfits=new HashMap<>(betItemMap.size());
		//会员投注信息
		List<Map<String, Object>> betInfos=new ArrayList<>();
		//会员方案盈亏信息
		List<Map<String, Object>> profitList=new ArrayList<>();
		//会员方案虚拟盈亏信息
		List<Map<String, Object>> profitVrList=new ArrayList<>();

		//盈亏信息容器
		Map<String,Map<String,Object>> hmProfitInfos=new HashMap<>(betItemMap.size());
		Map<String,Map<String,Object>> hmVrProfitInfos=new HashMap<>(betItemMap.size());
		//获取该游戏的赔率信息
		Map<String, Integer> oddsMap = CacheTool.odds(gameCode);
		//盈亏信息也需要结算
		for (Map.Entry<String, List<Map<String, Object>>> entry : betItemMap.entrySet()) {
			// 更新投注项
			updateResult(entry.getValue(), oddsMap, drawInfo.getString("drawItem"));
			betInfos.addAll(entry.getValue());
			//结算盈亏
			betProfit(entry.getValue(), entry.getKey(), gameId, profitList,profitVrList,cutoverHmMap,cutoverHmProfits,hmProfitInfos,hmVrProfitInfos);
		}
		//批量修改结算投注信息，并进行数组切割
		if(ContainerTool.notEmpty(betInfos)){
			List<List<Map<String, Object>>> betInfoList=splitList(betInfos,100);
			for(List<Map<String, Object>> betInfo:betInfoList){
				betItemService.updateResult(betInfo, drawNumber, nowTime);
			}
		}
		//批量修改方案盈亏信息
		if(ContainerTool.notEmpty(profitList)){
			new IbspProfitPlanService().update4Settlement(profitList,nowTime,IbsTypeEnum.REAL);
		}
		//批量修改虚拟方案盈亏信息
		if(ContainerTool.notEmpty(profitVrList)){
			new IbspProfitPlanService().update4Settlement(profitVrList,nowTime,IbsTypeEnum.VIRTUAL);
		}

		//批量修改结算盈亏信息
		if(ContainerTool.notEmpty(hmProfitInfos)){
			new IbspProfitService().update4Settlement(hmProfitInfos,nowTime, IbsTypeEnum.REAL);
			//批量新增当期盈亏信息
			new IbspProfitPeriodService().save4Settlement(hmProfitInfos, gameId, period, nowTime, IbsTypeEnum.REAL);
		}
		if(ContainerTool.notEmpty(hmVrProfitInfos)){
			new IbspProfitService().update4Settlement(hmVrProfitInfos,nowTime, IbsTypeEnum.VIRTUAL);
			//批量新增当期盈亏信息
			new IbspProfitPeriodService().save4Settlement(hmVrProfitInfos, gameId, period, nowTime, IbsTypeEnum.VIRTUAL);
		}
		//修改轮盘盈亏信息
		if(ContainerTool.notEmpty(cutoverHmProfits)){
			new IbspHmModeCutoverService().update4Settlement(cutoverHmProfits,nowTime);
		}
		return null;
	}

	/**
	 * 切割list
	 * @param betInfos	投注信息
	 * @param len			切割长度
	 * @return
	 */
	private List<List<Map<String, Object>>> splitList(List<Map<String, Object>> betInfos, int len) {
		List<List<Map<String, Object>>> betInfoList=new ArrayList<>();
		if(betInfos.size()<=len){
			betInfoList.add(betInfos);
			return betInfoList;
		}
		int size = betInfos.size();
		int count = (size + len - 1) / len;
		List<Map<String, Object>> subList;
		for(int i=0;i<count;i++){
			subList = betInfos.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
			betInfoList.add(subList);
		}
		return betInfoList;
	}

	/**
	 * 统计盈亏
	 * @param betInfos		投注信息
	 * @param handicapMemberId	盘口会员主键
	 * @param gameId				游戏主键
	 * @param profitList			盈亏信息
	 * @param profitVrList		虚拟盈亏信息
	 * @param cutoverHmMap		轮盘会员模式信息
	 * @param cutoverHmProfits	轮盘会员盈亏信息
	 * @param hmProfitInfos		真实盈亏信息
	 * @param hmVrProfitInfos		模拟盈亏信息
	 * @throws Exception
	 */
	private void betProfit(List<Map<String, Object>> betInfos, String handicapMemberId, String gameId,
						   List<Map<String, Object>> profitList,List<Map<String, Object>> profitVrList,Map<String,String> cutoverHmMap,Map<String,Object> cutoverHmProfits,
						   Map<String,Map<String,Object>> hmProfitInfos,Map<String,Map<String,Object>> hmVrProfitInfos) throws Exception {
		long sumProfitTh = 0, betFundsTh = 0, betLen = 0, profitBetLen = 0, lossBetLen = 0;
		long sumProfitThVr = 0, betFundsThVr = 0, betLenVr = 0, profitBetLenVr = 0, lossBetLenVr = 0;
		long profitTh, fundTh, betContentLen;
		List<Map<String, Object>> betInfosVr = new ArrayList<>();
		Iterator<Map<String, Object>> iterator= betInfos.iterator();
		while (iterator.hasNext()){
			Map<String, Object> betInfo=iterator.next();
			//核算盈亏
			profitTh = NumberTool.getLong(betInfo.get("PROFIT_T_"));
			fundTh = NumberTool.getLong(betInfo.get("FUND_T_"));
			betContentLen = NumberTool.getLong(betInfo.get("BET_CONTENT_LEN_"));
			if (IbsTypeEnum.VIRTUAL.name().equals(betInfo.remove("BET_MODE_"))) {
				//添加虚拟区域
				betInfosVr.add(betInfo);
				iterator.remove();
				//核算每期盈亏
				sumProfitThVr += profitTh;
				betFundsThVr += fundTh;
				betLenVr += betContentLen;
				if (profitTh > 0) {
					profitBetLenVr += betContentLen;
				} else {
					lossBetLenVr += betContentLen;
				}
			} else {
				//核算每期盈亏
				sumProfitTh += profitTh;
				betFundsTh += fundTh;
				betLen += betContentLen;
				if (profitTh > 0) {
					profitBetLen += betContentLen;
				} else {
					lossBetLen += betContentLen;
				}
			}
		}
		if (ContainerTool.notEmpty(betInfos)) {
			//处理方案盈亏信息，并且添加方案盈亏信息
			List<Map<String, Object>> updateList=processPlanProfit(betInfos,handicapMemberId,gameId,IbsTypeEnum.REAL);
			profitList.addAll(updateList);
		}
		if (ContainerTool.notEmpty(betInfosVr)) {
			List<Map<String, Object>> updateList=processPlanProfit(betInfosVr,handicapMemberId,gameId,IbsTypeEnum.VIRTUAL);
			profitVrList.addAll(updateList);
		}
		if (betLen != 0) {
			Map<String,Object> profitInfo=new HashMap<>(5);
			profitInfo.put("profitTh",sumProfitTh);
			profitInfo.put("betFundsTh",betFundsTh);
			profitInfo.put("betLen",betLen);
			profitInfo.put("profitBetLen",profitBetLen);
			profitInfo.put("lossBetLen",lossBetLen);
			hmProfitInfos.put(handicapMemberId,profitInfo);
		}
		if (betLenVr != 0) {
			Map<String,Object> profitInfo=new HashMap<>(5);
			profitInfo.put("profitTh",sumProfitThVr);
			profitInfo.put("betFundsTh",betFundsThVr);
			profitInfo.put("betLen",betLenVr);
			profitInfo.put("profitBetLen",profitBetLenVr);
			profitInfo.put("lossBetLen",lossBetLenVr);
			hmVrProfitInfos.put(handicapMemberId,profitInfo);
		}
		//累加轮盘盈亏金额
		if(cutoverHmMap.containsKey(handicapMemberId)){
			//判断当前轮盘模式
			if(IbsTypeEnum.REAL.name().equals(cutoverHmMap.get(handicapMemberId))){
				cutoverHmProfits.put(handicapMemberId,sumProfitTh);
			}else{
				cutoverHmProfits.put(handicapMemberId,sumProfitThVr);
			}
		}
	}

	/**
	 * 处理方案盈亏信息
	 *
	 * @param betInfos         投注信息
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏id
	 * @param betMode          投注模式
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, Object>> processPlanProfit(List<Map<String, Object>> betInfos,String handicapMemberId,String gameId, IbsTypeEnum betMode) throws SQLException {
		IbspProfitPlanService profitPlanService=new IbspProfitPlanService();
		Map<String,Map<String, Object>> planBetInfos=new HashMap<>();
		for(Map<String, Object> betInfo:betInfos){
			String planId=betInfo.remove("PLAN_ID_").toString();
			if(planBetInfos.containsKey(planId)){
				Map<String, Object> infos=planBetInfos.get(planId);
				infos.put("PROFIT_T_",NumberTool.getInteger(infos.get("PROFIT_T_"))+NumberTool.getInteger(betInfo.get("PROFIT_T_")));
				infos.put("FUND_T_",NumberTool.getInteger(infos.get("FUND_T_"))+NumberTool.getInteger(betInfo.get("FUND_T_")));
				infos.put("BET_CONTENT_LEN_",NumberTool.getInteger(infos.get("BET_CONTENT_LEN_"))+NumberTool.getInteger(betInfo.get("BET_CONTENT_LEN_")));
			}else{
				Map<String, Object> info=new HashMap<>();
				info.put("PROFIT_T_",betInfo.get("PROFIT_T_"));
				info.put("FUND_T_",betInfo.get("FUND_T_"));
				info.put("BET_CONTENT_LEN_",betInfo.get("BET_CONTENT_LEN_"));
				planBetInfos.put(planId,info);
			}
		}
		List<Map<String, Object>> updateList=new ArrayList<>();
		Map<String,Map<String, Object>> saveMap=new HashMap<>();

		Map<String,Object> historyProfitInfos=profitPlanService.
				findHistorys(handicapMemberId,gameId,planBetInfos.keySet(),betMode);
		for(String planId:planBetInfos.keySet()){
			Map<String, Object> betInfo=planBetInfos.get(planId);
			if(historyProfitInfos.containsKey(planId)){
				Object profitId=historyProfitInfos.get(planId);
				betInfo.put("PROFIT_PLAN_ID_",profitId);
				updateList.add(betInfo);
			}else{
				saveMap.put(planId,betInfo);
			}
		}
		if(ContainerTool.notEmpty(saveMap)){
			//获取用户id
			String userId=new IbspHandicapMemberService().findUserId(handicapMemberId);

			IbspPlanUserService planUserService=new IbspPlanUserService();
			List<Map<String, Object>> userPlanInfo=planUserService.findPlanByUserId(userId,gameId,saveMap.keySet());
			// 获取方案止盈止损信息
			List<Map<String,Object>> planLimitInfo=planUserService.findPlanLimit(userPlanInfo);

			//获取方案会员信息
			Map<String,Map<String,Object>> planHmInfo=new IbspPlanHmService().findPlanHmInfo(handicapMemberId);
			profitPlanService.batchSave(saveMap,userId,handicapMemberId,gameId,planLimitInfo,planHmInfo,betMode);
		}
		return updateList;
	}

	/**
	 * 更新投注项
	 */
	private void updateResult(List<Map<String, Object>> betInfos, Map<String, Integer> oddsMap, String drawItem) {
		double profit, odds, funds;
		for (Map<String, Object> betInfo : betInfos) {
			profit = 0d;
			String betContent = betInfo.get("BET_CONTENT_").toString();
			String[] betItems = betContent.split("#");
			for (String betItem : betItems) {
				//获取投注信息
				String fundsStr = betItem.substring(betItem.lastIndexOf("|") + 1);
				betItem = betItem.substring(0, betItem.lastIndexOf("|"));
				//获取赔率
				if(!oddsMap.containsKey(betItem)){
					continue;
				}
				odds = oddsMap.get(betItem) / 1000D;
				if (odds - 0 == 0) {
					//赔率异常
					continue;
				}
				//算出积分
				funds = NumberTool.doubleT(fundsStr);
				if (drawItem.concat(",").contains(betItem.concat(","))) {
					profit += funds * (odds - 1);
				} else {
					profit -= funds;
				}
			}
			betInfo.put("PROFIT_T_", NumberTool.longValueT(profit));
		}
	}
}
