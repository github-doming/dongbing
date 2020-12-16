package com.ibm.follow.servlet.server.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.service.CacheService;
import com.common.tools.CacheTool;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.vrc.core.thread.PlanItemThread;
import com.ibm.follow.servlet.vrc.vrc_bet.service.VrcBetService;
import com.ibm.follow.servlet.vrc.vrc_exist_member.service.VrcExistMemberService;
import com.ibm.follow.servlet.vrc.vrc_fm_game_set.service.VrcFmGameSetService;
import com.ibm.follow.servlet.vrc.vrc_member_plan.service.VrcMemberPlanService;
import com.ibm.follow.servlet.vrc.vrc_plan_group_result.service.VrcPlanGroupResultService;
import com.ibm.follow.servlet.vrc.vrc_plan_item.service.VrcPlanItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 客户端开奖信息控制器
 * @Author: null
 * @Date: 2020-07-10 10:51
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
	public String execute(String inVar) throws Exception {
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
		VrcBetService betService=new VrcBetService();
		Map<String, List<Map<String, Object>>> betItemMap = betService.mapBetInfo(gameCode, drawType, period);
		if (ContainerTool.notEmpty(betItemMap)) {
			//获取该游戏的赔率信息

			Map<String, Integer> oddsMap = CacheTool.odds(gameCode);
			VrcPlanGroupResultService execResultService = new VrcPlanGroupResultService();
			List<Map<String, Object>> betList=new ArrayList<>();
			List<Map<String, Object>> execPlanResults=new ArrayList<>();
			//盈亏信息也需要结算
			for (Map.Entry<String, List<Map<String, Object>>> entry : betItemMap.entrySet()) {
				// 更新投注项
				updateResult(betService, entry.getValue(), oddsMap, drawItem, drawNumber);
				betList.addAll(entry.getValue());
				//结算盈亏
				execPlanResults.addAll(betProfit(execResultService,entry.getValue(), entry.getKey()));
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

		//获取开启投注并且开启该游戏的方案信息
		List<String> openItemIds =new VrcMemberPlanService().getOpenItemIds(gameCode, drawType);
		if (ContainerTool.isEmpty(openItemIds)) {
			log.trace("未发现【{}】游戏【{}】类型有开启方案的盘口会员", gameCode.name(), drawType);
			return null;
		}
		period=gameCode.getGameFactory().period(handicapCode).findPeriod();
		//已开启基础信息列表
		Map<String, List<Map<String, Object>>> openPlanItemInfos =new VrcPlanItemService().listInfo(openItemIds);

		//获取盘口信息
		Map<String, Object> handicapInfo =new VrcExistMemberService().findHandicapInfo(openPlanItemInfos.keySet());

		//获取用户游戏绑定信息
		Map<String, List<Map<String, Object>>> gameInfos =new VrcFmGameSetService().findInfos(openPlanItemInfos.keySet(),gameCode);
		//开启一个线程，进行计算。
		ThreadPoolExecutor executorService = ThreadExecuteUtil.findInstance().getJobExecutor();

		//开启线程进行分用户处理
		openPlanItemInfos.forEach((existMemberVrId,planItems)-> executorService.execute(
				new PlanItemThread(existMemberVrId, planItems, gameCode, drawType, period,
						handicapInfo.get(existMemberVrId),gameInfos.get(existMemberVrId))));

		return null;
	}

	/**
	 * 投注方案盈亏结算
	 * @param betList		投注项列表
	 * @param existHmVrId		已存在虚拟会员id
	 */
	private List<Map<String, Object>>  betProfit(VrcPlanGroupResultService planGroupResultService,List<Map<String, Object>> betList, String existHmVrId) throws Exception {
		Map<String,Object> historyResult=planGroupResultService.findHistoryResult(existHmVrId,gameCode);
		List<Map<String, Object>> execPlanResults=new ArrayList<>();
		for (Map<String, Object> betInfo : betList) {
			long profitTh = NumberTool.getLong(betInfo.get("PROFIT_T_"));
			//更新执行方案组
			String execResult = profitTh > 0 ? IbmTypeEnum.TRUE.name() : IbmTypeEnum.FALSE.name();
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
			planGroupResultService.batchSave(existHmVrId,gameCode,betList);
		}

		return execPlanResults;
	}

	/**
	 * 更新结算信息
	 * @param betService		服务类
	 * @param betList			投注项列表
	 * @param oddsMap			赔率信息
	 * @param drawItem		开奖信息
	 * @param drawNumber		开奖号码
	 */
	private void updateResult(VrcBetService betService, List<Map<String, Object>> betList, Map<String, Integer> oddsMap,
							  String drawItem, String drawNumber) throws SQLException {
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
				betInfo.put("PROFIT_T_", NumberTool.longValueT(profit));
			}
		}
		betService.updateResult(betList, drawNumber);
	}
}
