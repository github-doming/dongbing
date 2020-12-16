package com.ibm.follow.servlet.vrc.core.thread;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.ModeEnum;
import com.common.enums.TypeEnum;
import com.common.game.Game;
import com.common.game.Period;
import com.common.plan.Plan;
import com.common.plan.PlanGroup;
import com.common.util.BasePlanUtil;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.vrc.vrc_bet.entity.VrcBet;
import com.ibm.follow.servlet.vrc.vrc_bet.service.VrcBetService;
import com.ibm.follow.servlet.vrc.vrc_member_coding_item.service.VrcMemberCodingItemService;
import com.ibm.follow.servlet.vrc.vrc_plan_group_result.service.VrcPlanGroupResultService;
import org.apache.commons.collections.map.HashedMap;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 方案执行线程
 * @Author: null
 * @Date: 2020-07-10 13:56
 * @Version: v1.0
 */
public class PlanItemThread extends BaseCommThread {
	private VrcBetService betService=new VrcBetService();

	private String existMemberVrId;
	private List<Map<String, Object>> planItems;
	private GameUtil.Code gameCode;
	private HandicapUtil.Code handicapCode;
	private Object period;
	private String drawType;
	private List<Map<String, Object>> gameInfos;

	public PlanItemThread(String existMemberVrId, List<Map<String, Object>> planItems, GameUtil.Code gameCode,
						  String drawType, Object period,Object handicap,List<Map<String, Object>> gameInfos) {
		this.existMemberVrId = existMemberVrId;
		this.planItems = planItems;
		this.gameCode = gameCode;
		this.drawType = drawType;
		this.period = period;
		this.handicapCode= HandicapUtil.Code.value(handicap.toString());
		this.gameInfos=gameInfos;
	}

	@Override
	public String execute(String inVar) throws Exception {
		//添加会员编码详情
		new VrcMemberCodingItemService().save(existMemberVrId,gameCode,period);

		//获取会员方案组历史投注信息
		Map<String, Map<String, Object>> execResults =new VrcPlanGroupResultService().findHistory(existMemberVrId, gameCode);

		Period<?> periodOption = gameCode.getGameFactory().period(handicapCode);
		Map<String, Object> historyMap;
		boolean execResult;
		int fundsKey;
		int historyFundKey;
		List<String> betContents = new ArrayList<>();
		List<Map<String, Object>> betItems = new ArrayList<>();
		for (Map<String, Object> planItem : planItems) {
			if (ContainerTool.isEmpty(planItem.get("PLAN_GROUP_ACTIVE_DATA_")) || StringTool
					.isEmpty(planItem.get("FUNDS_LIST_"))) {
				continue;
			}
			//方案组，需要过滤掉当期已经处理过的方案组
			JSONObject activePlanGroup = JSONObject.parseObject(planItem.get("PLAN_GROUP_ACTIVE_DATA_").toString());
			//资金列表
			String fundsList = planItem.get("FUNDS_LIST_").toString();
			//资金切换模式
			String fundSwapMode = planItem.get("FUND_SWAP_MODE_").toString();
			//监控期数
			int monitorPeriod = NumberTool.getInteger(planItem.get("MONITOR_PERIOD_"));
			//跟随期数
			int followPeriod = NumberTool.getInteger(planItem.get("FOLLOW_PERIOD_"));
			if (followPeriod == 0) {
				followPeriod = 1;
			}
			//算出跟进期数
			Object basePeriod = periodOption.findBeforePeriod(period, followPeriod);
			//方案编码
			BasePlanUtil.Code planCode = BasePlanUtil.Code.valueOf(planItem.get("PLAN_CODE_").toString());
			Plan plan = planCode.getPlan();
			//设置方案组信息
			PlanGroup planGroupInfo=new PlanGroup(basePeriod, monitorPeriod, gameCode, handicapCode, drawType);
			//投注模式
			Object betMode = planItem.get("BET_MODE_");
			//期期滚模式
			String periodRollMode = planItem.getOrDefault("PERIOD_ROLL_MODE_", "").toString();
			//扩展信息
			Object expandInfo = planItem.get("EXPAND_INFO_");
			for (Map.Entry<String, Object> planGroup : activePlanGroup.entrySet()) {
				execResult = false;
				fundsKey = 0;
				historyFundKey = -1;
				//激活组
				String activeKey = planGroup.getKey();

				String planGroupKey = planCode.name().concat("#").concat(activeKey);
				historyMap = new HashMap<>(8);
				//开启停止新增，并且最近一次执行结果为中
				if (execResults.containsKey(planGroupKey)) {
					//历史执行结果
					historyMap = execResults.get(planGroupKey);
					execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
					//历史资金信息
					historyFundKey = NumberTool.getInteger(historyMap, "FUND_GROUP_KEY_", -1);
					//添加期期滚信息
					savePeriodRoll(planCode, betMode, historyMap, activeKey, execResult, fundsList, fundSwapMode,
							historyFundKey, betItems);
				}
				//组装数据
				JSONObject planGroupItem = JSONObject.parseObject(planGroup.getValue().toString());
				planGroupInfo.setPlanGroupItem(planGroupItem);

				if (plan.planGroup(planGroupInfo)) {
					continue;
				}
				JSONObject groupData = plan.splice(historyMap,expandInfo);
				if (ContainerTool.isEmpty(groupData)) {
					continue;
				}
				//资金处理
				if (historyFundKey!=-1) {
					int fundsLen = fundsList.split(",").length;
					fundsKey = ModeEnum.fundSwap(fundSwapMode, historyFundKey, execResult, fundsLen);
					if (StringTool.isEmpty(fundsKey)) {
						continue;
					}
					//判断用户是否为炸停止,炸停止之后处理
					if (fundsLen <= fundsKey) {
						fundsKey = 0;
					}
				}
				String fundsValue = fundsValue(fundsList, fundsKey);
				long fundTh = NumberTool.longValueT(fundsValue);
				//turn 方案组详情
				String betContent = plan.betContent(groupData, fundTh,expandInfo,historyMap);
				if (StringTool.isEmpty(betContent)) {
					continue;
				}
				//判断期期滚是否为“重复内容不投注”
				if (StringTool.notEmpty(periodRollMode) && ModeEnum.PERIOD_ROLL_MODE_NO_REPEAT.name()
						.equals(periodRollMode)) {
					//判断是否有期期滚投注内容
					if (periodOption.findLotteryPeriod().equals(historyMap.get("LAST_PERIOD_"))) {
						if (betContent.equals(historyMap.get("BET_CONTENT_"))) {
							betContent = null;
						}
					}
				}
				if (StringTool.isEmpty(betContent)) {
					continue;
				}
				//存储数据
				String[] betContentArr = betContent.split("#");
				int len = betContentArr.length;
				Map<String, Object> betItem = new HashMap<>(6);
				betItem.put("planCode", planCode.name());
				betItem.put("activeKey", activeKey);
				betItem.put("fundsKey", fundsKey);
				betItem.put("betContentLen", len);
				betItem.put("betContent", betContent);
				betItem.put("betFundT", fundTh * len);
				betItems.add(betItem);
				betContents.addAll(Arrays.asList(betContentArr));
			}
		}
		if (ContainerTool.isEmpty(betItems)) {
			return null;
		}
		//批量保存投注数据 bets
		betService.batchSave(existMemberVrId, gameCode, drawType, period,  betItems);

		Map<String, int[][]> betInfo = new HashedMap(5);
		//转化投注项
		Game game = gameCode.getGameFactory().game();
		game.putBetInfo(betInfo,betContents.toArray(new String[0]));
		//合并投注项资金
		game.mergeInfo(betInfo);
		//合并投注项
		List<Object> info = game.mergeItem(betInfo, 1d);
		String betItem = (String) info.get(0);
		Integer fundsTh = (Integer) info.get(1);

		VrcBet bet=new VrcBet();
		bet.setExistMemberVrId(existMemberVrId);
		bet.setPlanCode(TypeEnum.MERGE.name());
		bet.setGameCode(gameCode.name());
		bet.setGameDrawType(drawType);
		bet.setPeriod(period);
		bet.setBetType(TypeEnum.MERGE.name());
		bet.setBetContent(betItem);
		bet.setBetFundT(fundsTh);
		bet.setCreateTime(new Date());
		bet.setCreateTimeLong(System.currentTimeMillis());
		bet.setUpdateTime(new Date());
		bet.setUpdateTimeLong(System.currentTimeMillis());
		String mergeBetId = betService.save(bet);

		//发送投注数据到主服务器
		JSONObject content = new JSONObject();
		content.put("GAME_CODE_", gameCode.name());
		content.put("HANDICAP_CODE_", handicapCode.name());
		content.put("PERIOD_", period);
		content.put("EXIST_MEMBER_VR_ID_", existMemberVrId);
		content.put("BET_CONTENT_", betItem);
		content.put("FUNDS_T_", fundsTh);
		content.put("FOLLOW_BET_ID_", mergeBetId);
		content.put("METHOD_", IbmMethodEnum.VR_MEMBER_BET_INFO.name());
		content.remove("ROW_NUM");
		RabbitMqTool.sendVrMemberBetInfo(content.toString());

		if(ContainerTool.isEmpty(gameInfos)){
			return null;
		}
		content.remove("HANDICAP_CODE_");
		//转化为资金数组
		String[] betContent=betItem.split("#");
		double[][] fundsMatrix = GameTool.getFundsMatrix(gameCode);
		for(String items:betContent){
			String[] item = items.split("\\|");
			int[] result = new int[2];
			result[0] = GameTool.rank(gameCode, item[0]);
			result[1] =GameTool.type(gameCode, item[1]);
			fundsMatrix[result[0]][result[1]] +=  NumberTool.getDouble(item[2]);
		}
		//开启一个线程，进行计算。
		ThreadPoolExecutor executorService = ThreadExecuteUtil.findInstance().getJobExecutor();
		//开启线程进行分用户处理
		gameInfos.forEach(gameInfo -> executorService.execute(new VrMemberBetThread(gameInfo,gameCode,content,fundsMatrix)));
		return null;
	}
	/**
	 * 添加期期滚投注内容
	 *
	 * @param betMode    投注模式
	 * @param historyMap 历史信息
	 * @param activeKey  激活方案组key
	 */
	private void savePeriodRoll(BasePlanUtil.Code planCode, Object betMode, Map<String, Object> historyMap,
								String activeKey, boolean execResult, String fundsList, String fundSwapMode, int historyFundKey,
								List<Map<String, Object>> betItems) {
		if (!ModeEnum.BET_MODE_PERIOD_ROLL.name().equals(betMode)) {
			return;
		}
		if (!gameCode.getGameFactory().period(handicapCode).findLotteryPeriod()
				.equals(historyMap.get("LAST_PERIOD_"))) {
			return;
		}
		//上次结果为true就不滚了
		if (execResult|| historyFundKey==-1) {
			return;
		}
		//判断资金是否炸了
		int fundsLen = fundsList.split(",").length;
		int fundsKey = ModeEnum.fundSwap(fundSwapMode, historyFundKey,false, fundsLen);
		//判断用户是否为炸停止,炸停止之后处理
		if (fundsLen <= fundsKey) {
			fundsKey = 0;
		}
		String fundsValue = fundsValue(fundsList, fundsKey);
		long fundTh = NumberTool.longValueT(fundsValue);

		String betContent = historyMap.get("BET_CONTENT_").toString();
		StringBuilder result = new StringBuilder();
		String[] betContents = betContent.split("#");
		int len = 0;
		for (String contents : betContents) {
			result.append(contents, 0, contents.lastIndexOf("|") + 1).append(fundTh).append("#");
			len++;
		}
		Map<String, Object> betItem = new HashMap<>(6);
		betItem.put("planCode", planCode.name());
		betItem.put("activeKey", activeKey);
		betItem.put("fundsKey", fundsKey);
		betItem.put("betContentLen", len);
		betItem.put("betContent", result.toString());
		betItem.put("betFundT", fundTh * len);
		betItems.add(betItem);
	}
	/**
	 * 获取 资金组value<br>
	 * fundsList不为空则读取list<br>
	 * 仅有fundsListId时执行高级资金方案
	 *
	 * @param fundsList 资金列表
	 * @param fundKey   资金组key
	 * @return 资金组value
	 */
	private static String fundsValue(String fundsList, int fundKey) {
		if (StringTool.notEmpty(fundsList)) {
			if (fundsList.split(",").length < fundKey) {
				return fundsList.split(",")[0];
			}
			return fundsList.split(",")[fundKey];
		}
		return null;
	}
}
