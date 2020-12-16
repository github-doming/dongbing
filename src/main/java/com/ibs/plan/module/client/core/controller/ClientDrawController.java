package com.ibs.plan.module.client.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.service.CacheService;
import com.common.tools.CacheTool;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.utils.ThreadExecuteUtil;
import com.ibs.plan.module.client.core.PlatformCache;
import com.ibs.plan.module.client.core.thread.PlanItemThread;
import com.ibs.plan.module.client.ibsc_bet.service.IbscBetService;
import com.ibs.plan.module.client.ibsc_hm_game_set.service.IbscHmGameSetService;
import com.ibs.plan.module.client.ibsc_hm_plan.service.IbscHmPlanService;
import com.ibs.plan.module.client.ibsc_hm_set.service.IbscHmSetService;
import com.ibs.plan.module.client.ibsc_plan_group_result.service.IbscPlanGroupResultService;
import com.ibs.plan.module.client.ibsc_plan_item.service.IbscPlanItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * 客户端开奖信息控制器
 *
 * @Author: Dongming
 * @Date: 2020-06-01 16:00
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientDrawController extends BaseCommThread {
	private static final Logger log = LogManager.getLogger(ClientDrawController.class);
	private GameUtil.Code gameCode;
	private Object period;
	private String drawType;
	private HandicapUtil.Code handicapCode;
	private String drawNumber;
	private JSONObject drawInfo;
	public ClientDrawController(GameUtil.Code gameCode, Object period, String drawType, HandicapUtil.Code handicapCode,
			String drawNumber,JSONObject drawInfo) {
		this.gameCode = gameCode;
		this.period = period;
		this.drawType = drawType;
		this.handicapCode = handicapCode;
		this.drawNumber = drawNumber;
		this.drawInfo=drawInfo;
	}
	@Override
	public String execute(String ignore) throws Exception {
		//添加冷热数据
		CacheTool.saveHotAndCold(gameCode,handicapCode, drawType, drawNumber, period);
		//添加号码出现间隔次序
		CacheTool.saveLastInterval(gameCode,handicapCode, drawType, drawNumber, period);
		//保存开奖数据
		new CacheService().save(gameCode,drawType,drawInfo);
		//重启事务
		CurrentTransaction.transactionCommit();

		String drawItem = drawInfo.getString("drawItem");
		//获取上一期投注信息
		IbscBetService betService = new IbscBetService();
		Map<String, List<Map<String, Object>>> betItemMap = betService.mapBetInfo(gameCode, drawType, period);
		if (ContainerTool.notEmpty(betItemMap)) {
			//获取该游戏的赔率信息
			Map<String, Integer> oddsMap = CacheTool.odds(gameCode);
			List<Map<String, Object>> betList=new ArrayList<>();
			IbscPlanGroupResultService execResultService = new IbscPlanGroupResultService();
			List<Map<String, Object>> execPlanResults=new ArrayList<>();
			//盈亏信息也需要结算
			for (Map.Entry<String, List<Map<String, Object>>> entry : betItemMap.entrySet()) {
				// 更新投注项
				updateResult(entry.getValue(), oddsMap, drawItem);
				betList.addAll(entry.getValue());
				//结算盈亏
				execPlanResults.addAll(betProfit(entry.getValue(), entry.getKey()));
			}
			if(ContainerTool.notEmpty(execPlanResults)){
				execResultService.updateResult(execPlanResults);
			}
			if(ContainerTool.notEmpty(betList)){
				betService.updateResult(betList, drawNumber);
			}
			//重启事务
			CurrentTransaction.transactionCommit();
		}
		period = gameCode.getGameFactory().period(handicapCode).findPeriod();
		//处理封盘时间
		long drawTime = gameCode.getGameFactory().period(handicapCode).getDrawTime();
		long sealTime = PlatformCache.sealTime(handicapCode, gameCode);
		long sealTimeLong = drawTime - System.currentTimeMillis() - sealTime * 1000;
		if (sealTimeLong <= 0) {
			log.warn("保存游戏【{}】开奖结果失败，已到封盘时间", gameCode.name());
		}
		//获取开启投注并且开启该游戏的方案信息
		List<String> openItemIds = new IbscHmPlanService().getOpenItemIds(gameCode, drawType);
		if (ContainerTool.isEmpty(openItemIds)) {
			log.trace("未发现【{}】游戏【{}】类型有开启方案的盘口会员", gameCode.name(), drawType);
			return null;
		}
		//已开启基础信息列表
		Map<String, List<Map<String, Object>>> openPlanItemInfos = new IbscPlanItemService().listInfo(openItemIds);
		//获取会员游戏设置信息
		Map<String, Map<String, Object>> gameInfos = new IbscHmGameSetService()
				.findGameInfo(openPlanItemInfos.keySet(), gameCode);
		//获取会员设置信息
		Map<String, Map<String, Object>> setInfos = new IbscHmSetService().findSetInfo(openPlanItemInfos.keySet());

		//开启一个线程，进行计算。
		ThreadPoolExecutor executorService = ThreadExecuteUtil.findInstance().getCoreExecutor();

		//开启线程进行分用户处理
		openPlanItemInfos.forEach((existHmId, planItems) -> executorService.execute(
				new PlanItemThread(existHmId, planItems, gameCode, drawType, period, gameInfos.get(existHmId),
						setInfos.get(existHmId))));
		return null;
	}
	/**
	 * 更新结算信息
	 *
	 * @param betList    投注项列表
	 * @param oddsMap    赔率
	 */
	private void updateResult(List<Map<String, Object>> betList,Map<String, Integer> oddsMap, String drawItem){
		double profit;
		double funds;
		double odds;
		for (Map<String, Object> betInfo : betList) {
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

	/**
	 * 投注方案盈亏结算
	 *
	 * @param betList   投注项列表
	 * @param existHmId 已存在盘口会员
	 */
	private List<Map<String, Object>> betProfit(List<Map<String, Object>> betList, String existHmId) throws Exception {
		IbscPlanGroupResultService execResultService = new IbscPlanGroupResultService();
		Map<String,Object> historyResult=execResultService.findHistoryResult(existHmId,gameCode);

		List<Map<String, Object>> execPlanResults=new ArrayList<>();
		for (Map<String, Object> betInfo : betList) {
			long profitTh = NumberTool.getLong(betInfo.get("PROFIT_T_"));
			//更新执行方案组
			String execResult = profitTh > 0 ? IbsTypeEnum.TRUE.name() :profitTh < 0 ? IbsTypeEnum.FALSE.name():IbsTypeEnum.NONE.name();
			betInfo.put("EXEC_RESULT_",execResult);
			String planGroupKey=betInfo.get("PLAN_CODE_").toString().concat("#").concat(betInfo.get("PLAN_GROUP_KEY_").toString());
			if(historyResult.containsKey(planGroupKey)){
				betInfo.put("PLAN_GROUP_RESULT_ID_",historyResult.get(planGroupKey));
				execPlanResults.add(betInfo);
			}
		}
		//存在历史信息，需要进行修改的,在外面进行批量修改
		betList.removeAll(execPlanResults);

		//需要进行添加的
		if(ContainerTool.notEmpty(betList)){
			execResultService.batchSave(existHmId,gameCode,betList);
		}

		return execPlanResults;
	}
}
