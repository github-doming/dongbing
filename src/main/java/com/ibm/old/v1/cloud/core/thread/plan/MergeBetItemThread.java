package com.ibm.old.v1.cloud.core.thread.plan;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.cloud.core.tool.HandicapTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.entity.IbmExecBetItemT;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_exec_result.t.service.IbmExecResultTService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-07-26 16:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MergeBetItemThread extends BaseCommThread {

	public static final String MERGE_FORMAT = "合并-%2d" ;

	private String gameId;
	private Object period;
	private String handicapId;
	private IbmGameEnum game;
	public MergeBetItemThread(String gameId, String handicapId, Object period, IbmGameEnum game) {
		this.gameId = gameId;
		this.handicapId = handicapId;
		this.period = period;
		this.game = game;
	}
	@Override public String execute(String ignore) throws Exception {
		long codingStart = System.currentTimeMillis(), codingEnd;
		IbmExecResultTService execResultService = new IbmExecResultTService();
		IbmHandicapEnum handicap = HandicapTool.findCode(handicapId);
		try {
			String result = null;
			for (int i = 0; i < 13; i++) {
				result = execResultService.findExecResult(gameId, handicapId, period);
				if (StringTool.isEmpty(result)) {
					Thread.sleep(1000);
					CurrentTransaction.transactionCommit();
					continue;
				}
				switch (IbmTypeEnum.valueOf(result)) {
					case CODING:
						Thread.sleep(1000);
						CurrentTransaction.transactionCommit();
						continue;
					case TURN:
						break;
					case MERGE:
						//已经合并过
						log.info("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏投注信息已合并");
						return null;
					default:
						log.info("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中获取合并错误【" + result + "】");
						return null;
				}
				break;
			}
			if (StringTool.isEmpty(result)) {
				log.info("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏合并未完成");
				return null;
			}
			//存储表
			String tableName = HandicapGameTool.getTableName(game.name(), handicap.name());
			if (tableName == null) {
				log.error("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中存储表不存在");
				return null;
			}
			IbmExecBetItemTService execBetItemService = new IbmExecBetItemTService();
			switch (game) {
				case PK10:
				case XYFT:
					Map<String, Map<String, int[][]>> ballMergeInfo = execBetItemService
							.listBallMergeInfo(period, tableName);
					if (ContainerTool.isEmpty(ballMergeInfo)) {
						log.trace("未发现【" + handicap.getName() + "】盘口【" + game.getName() + "】游戏有需要合并的数据");
						break;
					}
					mergeBallInfo(ballMergeInfo, tableName, execBetItemService);
					break;
				default:
					throw new RuntimeException("不存在的游戏" + game.getName());

			}
			return null;
		} finally {
			execResultService.updateExecResult(IbmTypeEnum.MERGE, IbmTypeEnum.TURN, handicapId, period,this.getClass().getName());
			codingEnd = System.currentTimeMillis();
			log.debug("执行时长=" + (codingEnd - codingStart) + "ms,盘口【" + handicap.getName() + "】中【" + game.getName()
					+ "】游戏已合并结束");
		}

	}
	/**
	 * 合并球类方案
	 *
	 * @param mergeInfo          需要合并的信息
	 * @param tableName          存储表名
	 * @param execBetItemService 投注详情服务类
	 */
	public void mergeBallInfo(Map<String, Map<String, int[][]>> mergeInfo, String tableName,
			IbmExecBetItemTService execBetItemService) throws SQLException {
		Date nowTime = new Date();
		for (Map.Entry<String, Map<String, int[][]>> entry : mergeInfo.entrySet()) {
			String key = entry.getKey();
			Map<String, int[][]> turn = entry.getValue();
			// 去掉不合理的投注项
			removeBallUnreasonable(turn);

			// 双面合并
			mergeBallTwoSize(turn);

			// 去掉不合理的投注项
			removeBallUnreasonable(turn);
			//获取写入数据库的项目
			int[][] fundsMatrix;
			List<Map<Integer, String>> mergeList = new ArrayList<>();
			for (String typeKey : StringTool.getTypeEn()) {

				// 号码，大小，单双，龙虎
				fundsMatrix = turn.get(typeKey);
				if (ContainerTool.isEmpty(fundsMatrix)) {
					continue;
				}
				for (int i = 0, len = fundsMatrix.length; i < len; i++) {
					String rank = StringTool.getRankCn(i + 1);
					Map<Integer, String> fundsMap = PlanTool.getFundsMap(fundsMatrix[i], typeKey, rank);
					mergeList.add(fundsMap);
				}
			}
			// 冠亚和
			fundsMatrix = turn.get("CHAMPION_SUM");
			if (ContainerTool.notEmpty(fundsMatrix)) {
				Map<Integer, String> fundsMap = PlanTool.getFundsMap(fundsMatrix[0], "CHAMPION_SUM", "冠亚");
				mergeList.add(fundsMap);
			}

			IbmExecBetItemT execBetItem;
			int rounds = 0;
			//写入数据库
			String handicapMemberId = key.split("#")[0];
			String betMode = key.split("#")[1];
			for (Map<Integer, String> map : mergeList) {
				for (Map.Entry<Integer, String> bet : map.entrySet()) {
					execBetItem = new IbmExecBetItemT();
					execBetItem.setExecPlanGroupId(null);
					execBetItem.setHandicapId(handicapId);
					execBetItem.setGameId(gameId);
					execBetItem.setPlanId("000_"+IbmTypeEnum.MERGE.name());
					execBetItem.setHandicapMemberId(handicapMemberId);
					execBetItem.setPeriod(period);
					execBetItem.setFundT(bet.getKey());
					execBetItem.setPlanGroupKey(rounds);
					execBetItem.setPlanGroupDesc(String.format(MERGE_FORMAT, ++rounds));
					execBetItem.setBetContent(bet.getValue());
					execBetItem.setBetContentLen(bet.getValue().split("#").length);
					execBetItem.setBetMode(betMode);
					execBetItem.setBetType(PlanTool.BET_TYPE_MERGE_CODE);
					execBetItem.setFundsIndex("0");
					execBetItem.setExecState(IbmStateEnum.READY.name());
					execBetItem.setCreateTime(nowTime);
					execBetItem.setCreateTimeLong(nowTime.getTime());
					execBetItem.setUpdateTime(nowTime);
					execBetItem.setUpdateTimeLong(nowTime.getTime());
					execBetItem.setState(IbmStateEnum.OPEN.name());
					execBetItemService.save(execBetItem, tableName);
				}
			}
		}
	}
	/**
	 * 玩家投注项双面合并
	 *
	 * @param turn 玩家投注项
	 */
	private void mergeBallTwoSize(Map<String, int[][]> turn) {
		int[][] fundsMatrix = turn.get("NUMBER");
		if (ContainerTool.isEmpty(fundsMatrix)) {
			return;
		}
		for (int i = 0; i < fundsMatrix.length; i++) {
			List<Integer> indexList = NumberTool.findNoZeroValIndex(fundsMatrix[i]);
			if (indexList.size() < 5) {
				continue;
			}
			IbmTypeEnum type = PlanTool.getMergeType(indexList);
			if (type == null) {
				continue;
			}
			switch (type) {
				case BIG:
					mergeTwoSizeItem(turn, fundsMatrix[i], i, 0, StringTool.getTypeEn(1), PlanTool.BIG);
					break;
				case SMALL:
					mergeTwoSizeItem(turn, fundsMatrix[i], i, 1, StringTool.getTypeEn(1), PlanTool.SMALL);
					break;
				case SINGLE:
					mergeTwoSizeItem(turn, fundsMatrix[i], i, 0, StringTool.getTypeEn(2), PlanTool.SINGLE);
					break;
				case DOUBLE:
					mergeTwoSizeItem(turn, fundsMatrix[i], i, 1, StringTool.getTypeEn(2), PlanTool.DOUBLE);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * 去掉不合理的投注项
	 *
	 * @param turn 玩家投注项
	 */
	private void removeBallUnreasonable(Map<String, int[][]> turn) {
		int[][] fundsMatrix;
		for (String typeKey : StringTool.getTypeEn()) {
			// 号码，大小，单双，龙虎
			fundsMatrix = turn.get(typeKey);
			if (ContainerTool.isEmpty(fundsMatrix)) {
				return;
			}
			//横向
			for (int[] ints : fundsMatrix) {
				int min = NumberTool.findMin(ints);
				if (min == 0) {
					continue;
				}
				NumberTool.less(ints, min);
			}
			int[][] fundsTranspose = NumberTool.transpose(fundsMatrix);
			//纵向
			for (int i = 0; i < fundsTranspose.length; i++) {
				int min = NumberTool.findMin(fundsTranspose[i]);
				if (min == 0) {
					continue;
				}
				NumberTool.less(fundsMatrix, i, min);
			}
		}
	}
	/**
	 * 合并双面详细做法
	 *
	 * @param turn      玩家投注项
	 * @param fundsArr  待合并资金数组
	 * @param index     合并名次
	 * @param typeIndex 合并类型
	 * @param type      类型
	 * @param nums      类型索引
	 */
	private void mergeTwoSizeItem(Map<String, int[][]> turn, int[] fundsArr, int index, int typeIndex, String type,
			List<Integer> nums) {
		int[][] fundsMatrix;
		if (turn.containsKey(type)) {
			fundsMatrix = turn.get(type);
		} else {
			fundsMatrix = new int[10][2];
		}
		int sum = 0;
		for (int num : nums) {
			sum += fundsArr[num];
			fundsArr[num] = 0;
		}
		fundsMatrix[index][typeIndex] += sum;
		turn.put(type, fundsMatrix);
	}

}
