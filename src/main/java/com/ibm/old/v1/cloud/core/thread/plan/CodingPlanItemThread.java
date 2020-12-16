package com.ibm.old.v1.cloud.core.thread.plan;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.entity.SpliceGroupDate;
import com.ibm.old.v1.cloud.core.tool.FundsTool;
import com.ibm.old.v1.cloud.core.tool.HandicapTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_result.service.IbmExecBetResultService;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.entity.IbmExecPlanGroupT;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_exec_result.t.entity.IbmExecResultT;
import com.ibm.old.v1.cloud.ibm_exec_result.t.service.IbmExecResultTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_hm_info.t.entity.IbmHmInfoT;
import com.ibm.old.v1.cloud.ibm_hm_info.t.service.IbmHmInfoTService;
import com.ibm.old.v1.cloud.ibm_plan_item.t.service.IbmPlanItemService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * @Description: 编码 - 方案详情
 * @Author: Dongming
 * @Date: 2019-07-24 15:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CodingPlanItemThread extends BaseCommThread {

	private String planId;
	private String gameId;
	private Object period;
	private String handicapId;
	private IbmGameEnum game;
	private PlanTool.Code planCode;

	public CodingPlanItemThread(String gameId, String planId, String handicapId, Object period, IbmGameEnum game,
			PlanTool.Code planCode) {
		this.planId = planId;
		this.gameId = gameId;
		this.period = period;
		this.handicapId = handicapId;
		this.game = game;
		this.planCode = planCode;
	}
	@Override public String execute(String ignore) throws Exception {
		IbmHandicapEnum handicap = HandicapTool.findCode(handicapId);
		long codingStart = System.currentTimeMillis(), codingEnd;
		IbmExecResultTService execResultService = new IbmExecResultTService();
		try {
			String result = execResultService.findExecResult(planId, gameId, handicapId, period);
			//已经编码过
			if (StringTool.notEmpty(result)) {
				log.debug("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中【" + planCode.getName()
						+ "】方案已经编码过数据");
				return null;
			}
			for (int i = 0; i < 13; i++) {
				result = execResultService.findExecResult(planId, gameId, handicapId, PeriodTool.findLastPeriod(game,period));
				if (IbmTypeEnum.SETTLE.name().equals(result)){
					break;
				}else {
					Thread.sleep(1000);
					CurrentTransaction.transactionCommit();
				}
			}
			//已开启的用户,开启本方案的id列表
			Map<String, String> openItemIds = new IbmHmGameSetTService().mapOpenItemId(gameId, planId, handicapId);
			if (ContainerTool.isEmpty(openItemIds)) {
				log.trace("未发现【" + handicap.getName() + "】盘口【" + game.getName() + "】游戏有开启【" + planCode.getName()
						+ "】方案的盘口会员");
				return null;
			}
			//已开启基础信息列表
			IbmPlanItemService planItemService = new IbmPlanItemService();
			List<Map<String, Object>> openPlanItemInfos = planItemService.listInfo(planCode, openItemIds.keySet());
			if (ContainerTool.isEmpty(openPlanItemInfos)) {
				log.trace("未发现【" + handicap.getName() + "】盘口【" + game.getName() + "】游戏有开启【" + planCode.getName()
						+ "】方案的已开启基础信息");
				return null;
			}
			coding(openItemIds, openPlanItemInfos, planItemService);
			return null;
		} finally {
			IbmExecResultT execResult = new IbmExecResultT();
			execResult.setGameId(gameId);
			execResult.setHandicapId(handicapId);
			execResult.setPlanId(planId);
			execResult.setPeriod(period);
			execResult.setCreateTime(new Date());
			execResult.setCreateTimeLong(execResult.getCreateTime().getTime());
			execResult.setExecResult(IbmTypeEnum.CODING.name());
			execResult.setUpdateTime(new Date());
			execResult.setUpdateTimeLong(execResult.getUpdateTime().getTime());
			execResult.setState(IbmStateEnum.OPEN.name());
			execResultService.save(execResult);
			codingEnd = System.currentTimeMillis();
			log.trace("执行时长=" + (codingEnd - codingStart) + "ms,盘口【" + handicap.getName() + "】中【" + game.getName()
					+ "】游戏的【" + planCode.getName() + "】方案已编码完成");
		}

	}

	/**
	 * 编码数据
	 *
	 * @param openItemIds     方案详情id-盘口会员id
	 * @param openPlanItemInfos       开启基础信息列表
	 * @param planItemService 方案详情服务类
	 */
	public void coding(Map<String, String> openItemIds, List<Map<String, Object>> openPlanItemInfos,
			IbmPlanItemService planItemService) throws Exception {
		//方案资金详情
		Map<Object, Object> fundsInfoMap = planItemService.mapFundsList(planCode, openItemIds.keySet());

		//开启游戏停止新增的盘口会员
		Map<Object, Object> execResult = new IbmExecBetResultService().mapStopIncrease(gameId, openItemIds.values());

		//清除重复
		Map<String, Set<Map<String, String>>> planItemMap = PlanTool.spliceGroupRepeat(planCode, openPlanItemInfos);

		//上一期的期数
		Object lastPeriod = PeriodTool.findLotteryPeriod(game);

		IbmExecPlanGroupTService execPlanGroupService = new IbmExecPlanGroupTService();
		IbmExecPlanGroupT execPlanGroup;
		for (Map.Entry<String, Set<Map<String, String>>> entry : planItemMap.entrySet()) {
			String planDetail = entry.getKey();
			JSONObject planGroupItem = JSONObject.parseObject(planDetail);
			//解析数据
			int followPeriod = planGroupItem.getInteger("followPeriod");
			int monitorPeriod = planGroupItem.getInteger("monitorPeriod");
			//算出跟进期数
			Object basePeriod = PeriodTool.findBeforePeriod(game, period, followPeriod);

			//拼接方案组数据
			JSONObject groupData = new SpliceGroupDate(planGroupItem, basePeriod, monitorPeriod, game).splice(planCode);
			if (ContainerTool.isEmpty(groupData)) {
				continue;
			}
			Date nowTime = new Date();
			//该方案详情的所拥有的值[详情表id_方案组key]
			for (Map<String, String> planItemVal : entry.getValue()) {
				String planItemId = planItemVal.get("itemId");
				String activeKey = planItemVal.get("activeKey");
				String fundSwapMode = planItemVal.get("fundSwapMode");
				//盘口会员主键s
				String handicapMemberIds = openItemIds.get(planItemId);
				for (String handicapMemberId : handicapMemberIds.split("#")) {

					//停止新增，且最近一次中
					String execResultKey = String.format("%s#%s#%s", planItemId, handicapMemberId, activeKey);
					if (IbmTypeEnum.TRUE.name().equals(execResult.get(execResultKey))) {
						continue;
					}

					execPlanGroup = new IbmExecPlanGroupT();
					String fundsInfo = fundsInfoMap.get(planItemId).toString();
					if (StringTool.isEmpty(fundsInfo)) {
						continue;
					}
					String betMode = fundsInfo.split("#")[0];
					String fundsList = fundsInfo.split("#")[1];
					String fundsListId = fundsInfo.split("#")[2];
					//历史执行详情
					Map<String, Object> historyInfo = execPlanGroupService
							.findHistory(handicapMemberId, planItemId, activeKey);
					Object fundsKey = "0" ;
					if (ContainerTool.notEmpty(historyInfo)) {
						fundsKey = FundsTool.fundsKey(fundsList, fundsListId, fundSwapMode,
								historyInfo.get("FUND_GROUP_KEY_").toString(),
								Boolean.parseBoolean(historyInfo.get("EXEC_RESULT_").toString()));
						if (StringTool.isEmpty(fundsKey)) {
							continue;
						}
						if (fundsKey instanceof IbmTypeEnum) {
							//炸
							if (IbmTypeEnum.BLAST.equals(fundsKey)) {
								if (execPlanGroupService.isBlastStop(handicapMemberId)) {
									blastStop(handicapMemberId);
									continue;
								}
							}
							fundsKey = "0" ;
						}
						//新数据写入
						execPlanGroup.setRepExecPlanGroupId(historyInfo.get("IBM_EXEC_PLAN_GROUP_ID_"));
					}
					String fundsValue = FundsTool.fundsValue(fundsList, fundsListId, fundsKey.toString());
					execPlanGroup.setGameId(gameId);
					execPlanGroup.setHandicapId(handicapId);
					execPlanGroup.setHandicapMemberId(handicapMemberId);
					execPlanGroup.setPlanId(planId);
					execPlanGroup.setPlanItemTableName(planCode.getTableName());
					execPlanGroup.setPlanItemTableId(planItemId);
					execPlanGroup.setPeriod(period);
					execPlanGroup.setBasePeriod(basePeriod);
					execPlanGroup.setBetMode(betMode);
					execPlanGroup.setFundSwapMode(fundSwapMode);
					execPlanGroup.setPlanGroupKey(activeKey);
					execPlanGroup.setFundGroupKey(fundsKey);
					execPlanGroup.setPlanGroupValue(groupData.toJSONString());
					execPlanGroup.setFundGroupValue(fundsValue);
					execPlanGroup.setFundsList(fundsList);
					execPlanGroup.setFundsListId(fundsListId);
					execPlanGroup.setExecResult(IbmTypeEnum.READY.name());
					execPlanGroup.setCreateTime(nowTime);
					execPlanGroup.setCreateTimeLong(nowTime.getTime());
					execPlanGroup.setUpdateTime(nowTime);
					execPlanGroup.setUpdateTimeLong(nowTime.getTime());
					execPlanGroup.setState(IbmStateEnum.OPEN.name());
					execPlanGroupService.save(execPlanGroup);
				}
			}

		}
		//期期滚信息刷新<重置校验>
		List<IbmExecPlanGroupT> periodRollList = execPlanGroupService
				.listPeriodRoll(gameId, handicapId, lastPeriod, planId);
		//没有期期滚信息
		if (ContainerTool.isEmpty(periodRollList)) {
			return;
		}
		Object fundsKey;
		for (IbmExecPlanGroupT info : periodRollList) {
			info.setRepExecPlanGroupId(info.getIbmExecPlanGroupId());
			info.setIbmExecPlanGroupId(null);
			info.setPeriod(period);
			info.setBasePeriod(info.getBasePeriod());
			fundsKey = FundsTool.fundsKey(info.getFundsList(), info.getFundsListId(), info.getFundSwapMode(),
					info.getFundGroupKey(), Boolean.parseBoolean(info.getExecResult()));
			if (StringTool.isEmpty(fundsKey)) {
				continue;
			}
			if (fundsKey instanceof IbmTypeEnum) {
				//炸
				if (IbmTypeEnum.BLAST.equals(fundsKey)) {
					if (execPlanGroupService.isBlastStop(info.getHandicapMemberId())) {
						blastStop(info.getHandicapMemberId());
						continue;
					}
				}
				fundsKey = "0" ;
			}

			info.setFundGroupKey(fundsKey);
			info.setFundGroupValue(
					FundsTool.fundsValue(info.getFundsList(), info.getFundsListId(), info.getFundGroupKey()));
			info.setExecResult(IbmStateEnum.READY.name());
			info.setCreateTime(new Date());
			info.setCreateTimeLong(info.getCreateTime().getTime());
			info.setUpdateTime(new Date());
			info.setUpdateTimeLong(info.getUpdateTime().getTime());
			info.setDesc(null);
			info.setState(IbmStateEnum.OPEN.name());
			execPlanGroupService.save(info);
		}
	}

	/**
	 * 炸停止
	 *
	 * @param handicapMemberId 盘口会员id
	 */
	private void blastStop(String handicapMemberId) throws Exception {
		IbmHmInfoT hmInfo = new IbmHmInfoT();
		IbmHmInfoTService hmInfoService = new IbmHmInfoTService();
		hmInfo.setHandicapMemberId(handicapMemberId);
		hmInfo.setInfoType("ALL_BETTING_STATE");
		hmInfo.setInfoContent(IbmTypeEnum.FALSE.name());
		hmInfo.setInfoState(IbmStateEnum.PROCESS.name());
		hmInfo.setCreateTime(new Date());
		hmInfo.setCreateTimeLong(hmInfo.getCreateTime().getTime());
		hmInfo.setUpdateTime(new Date());
		hmInfo.setUpdateTimeLong(hmInfo.getUpdateTime().getTime());
		hmInfo.setState(IbmStateEnum.OPEN.name());
		hmInfo.setDesc("BLAST");
		hmInfoService.save(hmInfo);
	}

}
