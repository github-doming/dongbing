package com.ibm.old.v1.cloud.core.thread.plan;
import com.ibm.old.v1.cloud.core.entity.PlanGroupContent;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.cloud.core.tool.HandicapTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.entity.IbmExecBetItemT;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_exec_result.t.service.IbmExecResultTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;
/**
 * @Description: 转码 - 方案组
 * @Author: Dongming
 * @Date: 2019-07-25 11:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TurnPlanGroupThread extends BaseCommThread {

	private String planId;
	private String gameId;
	private Object period;
	private String handicapId;
	private IbmGameEnum game;
	private PlanTool.Code planCode;

	public TurnPlanGroupThread(String gameId, String planId, String handicapId, Object period, IbmGameEnum game,
			PlanTool.Code planCode) {
		this.planId = planId;
		this.gameId = gameId;
		this.period = period;
		this.handicapId = handicapId;
		this.game = game;
		this.planCode = planCode;
	}

	@Override public String execute(String ignore) throws Exception {
		long codingStart = System.currentTimeMillis(), codingEnd;
		IbmExecResultTService execResultService = new IbmExecResultTService();
		IbmHandicapEnum handicap = HandicapTool.findCode(handicapId);
		try {
			String result = null;
			for (int i = 0; i < 13; i++) {
				result = execResultService.findExecResult(planId, gameId, handicapId, period);
				if (StringTool.isEmpty(result)) {
					Thread.sleep(1000);
					CurrentTransaction.transactionCommit();
					continue;
				}
				switch (IbmTypeEnum.valueOf(result)) {
					case CODING:
						break;
					case TURN:
						log.info("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中【" + planCode.getName()
								+ "】方案已转换过数据");
						return null;
					default:
						log.info("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中【" + planCode.getName()
								+ "】方案获取转换错误【" + result + "】");
						return null;
				}
				break;
			}
			if (StringTool.isEmpty(result)) {
				log.info("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中【" + planCode.getName()
						+ "】方案编码未完成");
				return null;
			}

			//获取编码列表信息
			IbmExecPlanGroupTService execPlanGroupService = new IbmExecPlanGroupTService();
			List<Map<String, Object>> codingInfos = execPlanGroupService.listCodingInfo(planId, period, handicapId);
			if (ContainerTool.isEmpty(codingInfos)) {
				log.trace("未发现【" + handicap.getName() + "】盘口【" + game.getName() + "】游戏有开启【" + planCode.getName()
						+ "】方案有需要转换的数据");
				return null;
			}

			//存储表
			String tableName = HandicapGameTool.getTableName(game.name(), handicap.name());
			if (tableName == null) {
				log.error("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中存储表不存在");
				return null;
			}

			turn(execPlanGroupService, codingInfos, tableName);
			return null;
		} finally {
			execResultService.updateExecResult(IbmTypeEnum.TURN, IbmTypeEnum.CODING, planId, handicapId, period,this.getClass().getName());
			codingEnd = System.currentTimeMillis();
			log.debug("执行时长=" + (codingEnd - codingStart) + "ms,盘口【" + handicap.getName() + "】中【" + game.getName()
					+ "】游戏的【" + planCode.getName() + "】方案已转换结束");
		}
	}

	/**
	 * 转码数据
	 *
	 * @param execPlanGroupService 方案组执行服务类
	 * @param codingInfos          编码后的信息
	 * @param tableName            转码存储数据
	 */
	public void turn(IbmExecPlanGroupTService execPlanGroupService, List<Map<String, Object>> codingInfos,
			String tableName) throws SQLException {
		List<String> unBetHandicapMemberIds = new ArrayList<>();
		Set<Object> handicapMemberIds = ContainerTool.getValSet4Key(codingInfos, "HANDICAP_MEMBER_ID_");
		Map<Object, Object> betRateInfo = new IbmHmSetTService().mapHmBetRate(handicapMemberIds);
		Map<Object, Object> betModeInfo = new IbmHmGameSetTService().mapHmBetMode(handicapMemberIds, gameId);

		IbmExecBetItemTService execBetItemService = new IbmExecBetItemTService();
		IbmExecBetItemT execBetItem;

		//存储已经翻译过的项目
		Map<String, String> exitsMap = new HashMap<>(codingInfos.size() / 2);
		for (Map<String, Object> codingInfo : codingInfos) {
			String handicapMemberId = codingInfo.get("HANDICAP_MEMBER_ID_").toString();
			//投注状态已改为不投注
			if (!betModeInfo.containsKey(handicapMemberId)) {
				unBetHandicapMemberIds.add(handicapMemberId);
				continue;
			}
			//方案组详情
			String betContent = new PlanGroupContent(planCode, game, codingInfo.get("PLAN_GROUP_VALUE_").toString(),
					codingInfo.get("BASE_PERIOD_")).getBetContent(exitsMap);
			if (StringTool.isEmpty(betContent)) {
				continue;
			}
			//投注比例
			String betRate = betRateInfo.get(handicapMemberId).toString();
			long fundTh = NumberTool.longValueT(Math.ceil(
					Integer.parseInt(codingInfo.get("FUND_GROUP_VALUE_").toString()) * NumberTool.doubleT(betRate)
							/ 100));

			String planGroupDesc = String.format(PlanTool.PLAN_GROUP_DESC, planCode.getName(),
					StringTool.addOne(codingInfo.get("PLAN_GROUP_KEY_").toString()));

			execBetItem = new IbmExecBetItemT();
			execBetItem.setHandicapId(handicapId);
			execBetItem.setExecPlanGroupId(codingInfo.get("IBM_EXEC_PLAN_GROUP_ID_"));
			execBetItem.setPlanId(planId);
			execBetItem.setGameId(gameId);
			execBetItem.setHandicapMemberId(handicapMemberId);
			execBetItem.setPeriod(period);
			execBetItem.setFundT(fundTh);
			execBetItem.setFundsIndex(codingInfo.get("FUND_GROUP_KEY_"));
			execBetItem.setPlanItemTableId(codingInfo.get("PLAN_ITEM_TABLE_ID_"));
			execBetItem.setPlanGroupKey(codingInfo.get("PLAN_GROUP_KEY_"));
			execBetItem.setPlanGroupDesc(planGroupDesc);
			execBetItem.setBetContent(betContent);
			execBetItem.setBetContentLen(betContent.split("#").length);
			execBetItem.setBetMode(betModeInfo.get(handicapMemberId));
			execBetItem.setBetType(PlanTool.BET_TYPE_CODE);
			execBetItem.setExecState(IbmTypeEnum.READY.name());
			execBetItem.setCreateTime(new Date());
			execBetItem.setCreateTimeLong(execBetItem.getCreateTime().getTime());
			execBetItem.setUpdateTime(new Date());
			execBetItem.setUpdateTimeLong(execBetItem.getUpdateTime().getTime());
			execBetItem.setState(IbmStateEnum.OPEN.name());
			execBetItemService.save(execBetItem, tableName);
		}
		//关闭投注信息
		if (ContainerTool.notEmpty(unBetHandicapMemberIds)) {
			execPlanGroupService.closeUnBetInfo(unBetHandicapMemberIds);
		}
	}

}
