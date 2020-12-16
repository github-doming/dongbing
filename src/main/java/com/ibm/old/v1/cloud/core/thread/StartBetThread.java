package com.ibm.old.v1.cloud.core.thread;
import com.ibm.old.v1.cloud.core.thread.plan.CodingPlanItemThread;
import com.ibm.old.v1.cloud.core.thread.plan.MergeBetItemThread;
import com.ibm.old.v1.cloud.core.thread.plan.SendBetItemThread;
import com.ibm.old.v1.cloud.core.thread.plan.TurnPlanGroupThread;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.cloud.core.tool.HandicapTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_exec_result.t.service.IbmExecResultTService;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.service.IbmHandicapGameTService;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.service.IbmPlanHmTService;
import com.ibm.old.v1.cloud.ibm_plan_item.t.service.IbmPlanItemService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import net.sf.json.JSONObject;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 修改投注状态，手动生成投注信息
 * @Author: zjj
 * @Date: 2019-06-06 15:22
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class StartBetThread extends BaseCommThread {
	private String gameCode;
	private String handicapId;
	private Object period;
	private String handicapMemberId;

	public StartBetThread(String gameCode, String handicapId, Object period, String handicapMemberId) {
		this.gameCode = gameCode;
		this.handicapId = handicapId;
		this.period = period;
		this.handicapMemberId = handicapMemberId;
	}

	@Override public String execute(String ignore) throws Exception {
		IbmGameEnum game = IbmGameEnum.valueOf(gameCode);
		//未到编码
		long drawTime = PeriodTool.getDrawTime(gameCode);
		if (drawTime - System.currentTimeMillis() > PeriodTool.getPeriodTime(gameCode)) {
			log.info("游戏【" + game.getName() + "】，未到可以编码的时间");
			return null;
		}

		//封盘时间
		String sealTime = new IbmHandicapGameTService().findSealTimeById(gameCode, handicapId);
		long sealTimeLong = NumberTool.longValueT(sealTime);
		if ((drawTime - System.currentTimeMillis()) < (sealTimeLong + PeriodTool.BET_TIME)) {
			log.info("游戏【" + game.getName() + "】，已到封盘时间");
			return null;
		}

		// 通过游戏Code查找游戏ID
		String gameId = GameTool.findId(gameCode);

		List<String> execResultList = new IbmExecResultTService().listExecResult(gameId, handicapId, period);
		IbmTypeEnum execResult;
		log.debug("盘口会员【" + handicapMemberId + "】盘口【" + HandicapTool.findCode(handicapId) + "】游戏【" + game.getName()
				+ "】期数【" + period + "】手动投注执行状态:" + execResultList);
		if (ContainerTool.isEmpty(execResultList)) {
			return null;
		} else if (execResultList.size() == 1) {
			execResult = IbmTypeEnum.valueOf(execResultList.get(0));
		} else if (execResultList.contains("SEND")) {
			execResult = IbmTypeEnum.SEND;
		} else if (execResultList.contains("MERGE")) {
			execResult = IbmTypeEnum.MERGE;
		} else if (execResultList.contains("TURN")) {
			execResult = IbmTypeEnum.TURN;
		} else {
			execResult = IbmTypeEnum.CODING;
		}

		switch (execResult) {
			case CODING:
				codingHmPlan(gameId, game);
				break;
			case TURN:
				turnHmPlan(gameId, game);
				break;
			case MERGE:
				mergerHmPlan(gameId, game);
				break;
			case SEND:
				sendHmPlan(gameId, game);
				break;
			default:
				log.info("盘口会员【" + handicapMemberId + "】手动生成投注项，执行状态" + execResult + "异常");
				break;
		}
		return null;
	}

	/**
	 * 编码盘口会员方案
	 *
	 * @param gameId 游戏id
	 * @param game   游戏
	 */
	private void codingHmPlan(String gameId, IbmGameEnum game) throws Exception {
		IbmPlanHmTService planHmService = new IbmPlanHmTService();
		List<Map<String, Object>> planInfos = planHmService.listHmOpenPlanInfo(handicapMemberId, gameId);
		for (Map<String, Object> plan : planInfos) {
			//方案id
			String planId = plan.get("PLAN_ID_").toString();
			//方案详情id
			String planItemId = plan.get("PLAN_ITEM_TABLE_ID_").toString();
			String tableName = plan.get("PLAN_ITEM_TABLE_NAME_").toString();

			//方案Code
			PlanTool.Code planCode = PlanTool.Code.valueOfTableName(tableName);
			if (planCode == null) {
				log.error("盘口会员【" + handicapMemberId + "】，游戏【" + game.getName() + "】，方案详情表名【" + tableName + "】找不到方案");
				continue;
			}

			Map<String, String> openItemIds = new HashMap<>(1);
			openItemIds.put(planItemId, handicapMemberId);

			IbmPlanItemService planItemService = new IbmPlanItemService();
			List<Map<String, Object>> openInfos = planItemService.listInfo(planCode, openItemIds.keySet());
			if (ContainerTool.isEmpty(openInfos)) {
				log.trace("盘口会员【" + handicapMemberId + "】，游戏【" + game.getName() + "】方案【" + planCode.getName()
						+ "】，未找到已开启基础信息");
				continue;
			}
			new CodingPlanItemThread(gameId, planId, handicapId, period, game, planCode)
					.coding(openItemIds, openInfos, planItemService);
		}
	}

	/**
	 * 转码盘口会员方案
	 *
	 * @param gameId 游戏id
	 * @param game   游戏
	 */
	private void turnHmPlan(String gameId, IbmGameEnum game) throws Exception {

		codingHmPlan(gameId, game);

		IbmPlanHmTService planHmService = new IbmPlanHmTService();
		List<Map<String, Object>> planInfos = planHmService.listHmOpenPlanInfo(handicapMemberId, gameId);
		for (Map<String, Object> plan : planInfos) {
			//方案id
			String planId = plan.get("PLAN_ID_").toString();
			//方案详情id
			String planItemId = plan.get("PLAN_ITEM_TABLE_ID_").toString();
			String tableName = plan.get("PLAN_ITEM_TABLE_NAME_").toString();

			//方案Code
			PlanTool.Code planCode = PlanTool.Code.valueOfTableName(tableName);
			if (planCode == null) {
				log.error("盘口会员【" + handicapMemberId + "】，游戏【" + game.getName() + "】，方案详情表名【" + tableName + "】找不到方案");
				continue;
			}
			IbmHandicapEnum handicap = HandicapTool.findCode(handicapId);
			//存储表
			String turnTableName = HandicapGameTool.getTableName(game.name(), handicap.name());
			//获取编码列表信息
			IbmExecPlanGroupTService execPlanGroupService = new IbmExecPlanGroupTService();
			List<Map<String, Object>> codingInfos = execPlanGroupService
					.listCodingInfoByHm(planItemId, period, handicapMemberId);
			if (ContainerTool.isEmpty(codingInfos)) {
				log.debug("盘口会员【" + handicapMemberId + "】，未发现【" + game.getName() + "】游戏有开启【" + planCode.getName()
						+ "】方案有需要转换的数据");
				continue;
			}
			new TurnPlanGroupThread(gameId, planId, handicapId, period, game, planCode)
					.turn(execPlanGroupService, codingInfos, turnTableName);

		}
	}

	/**
	 * 合并盘口会员方案
	 *
	 * @param gameId 游戏id
	 * @param game   游戏
	 */
	private void mergerHmPlan(String gameId, IbmGameEnum game) throws Exception {
		turnHmPlan(gameId, game);
		IbmHandicapEnum handicap = HandicapTool.findCode(handicapId);
		//存储表
		String tableName = HandicapGameTool.getTableName(game.name(), handicap.name());
		IbmExecBetItemTService execBetItemService = new IbmExecBetItemTService();

		//合并数据
		MergeBetItemThread mergeBetItem = new MergeBetItemThread(gameId, handicapId, period, game);
		switch (game) {
			case PK10:
			case XYFT:
				Map<String, Map<String, int[][]>> ballMergeInfo = execBetItemService
						.listMergeInfoByHm(period, tableName, handicapMemberId);
				if (ContainerTool.isEmpty(ballMergeInfo)) {
					log.trace("未发现【" + handicap.getName() + "】盘口【" + game.getName() + "】游戏有需要合并的数据");
					break;
				}
				mergeBetItem.mergeBallInfo(ballMergeInfo, tableName, execBetItemService);
				break;
			default:
				throw new RuntimeException("不存在的游戏" + game.getName());

		}
	}

	/**
	 * 发送盘口会员方案
	 *
	 * @param gameId 游戏id
	 * @param game   游戏
	 */
	private void sendHmPlan(String gameId, IbmGameEnum game) throws Exception {
		mergerHmPlan(gameId, game);
		IbmHandicapEnum handicap = HandicapTool.findCode(handicapId);
		//存储表
		String tableName = HandicapGameTool.getTableName(game.name(), handicap.name());
		// 获取没有合并的数据
		JSONObject sendObj = new IbmExecBetItemTService().listSendInfoByHm(period, tableName, handicapMemberId);
		if (ContainerTool.isEmpty(sendObj)) {
			log.trace("未发现【" + handicap.getName() + "】盘口【" + game.getName() + "】游戏有需要合并的数据");
		}
		Map<String, JSONObject> sendInfo = new HashMap<>();
		sendInfo.put(handicapMemberId, sendObj);
		SendBetItemThread.send(sendInfo, gameId, period);
	}

}
