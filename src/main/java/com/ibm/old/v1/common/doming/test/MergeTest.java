package com.ibm.old.v1.common.doming.test;
import c.a.util.core.test.CommTest;
import c.x.platform.root.common.dao.BaseDao;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.entity.IbmExecBetItemT;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.LogTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;
/**
 * @Description: 合并测试
 * @Author: Dongming
 * @Date: 2019-03-21 13:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MergeTest extends CommTest {

	private static final List<Integer> SMALL = Arrays.asList(0, 1, 2, 3, 4);
	private static final List<Integer> BIG = Arrays.asList(5, 6, 7, 8, 9);
	private static final List<Integer> DOUBLE = Arrays.asList(1, 3, 5, 7, 9);
	private static final List<Integer> SINGLE = Arrays.asList(0, 2, 4, 6, 8);

	@Test public void test() {
		int[][] matrix = new int[10][10];
		initMatrix(matrix);

		Map<String, int[][]> turn = new HashMap<>(1);
		turn.put("test", matrix);
		print(turn.get("test"));
		removeUnreasonable(turn);
		System.out.println("___________________________________________");
		print(turn.get("test"));
		mergeTwoSize(turn);
		System.out.println("___________________________________________");
		print(turn.get("test"));
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		print(turn.get("SIZE"));
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		print(turn.get("PARITY"));
	}
	/**
	 * 根据金额将投注项分组
	 *
	 * @param set     资金列表
	 * @param typeKey 类型键
	 * @param rank    排行名称
	 * @return 一个金额一组数据
	 */
	private Map<Integer, String> getFundsMap(int[] set, String typeKey, String rank) {
		Map<Integer, String> fundsMap = new HashMap<>();
		List<Integer> indexList = findNoZeroIndex(set);
		for (Integer index : indexList) {
			String number = GameTool.getNumber(typeKey, index);
			//以资金为key，放入map，组合投注正文
			int funds = set[index];
			String content = rank + "|" + number;
			fundsMap.put(funds, fundsMap.containsKey(funds) ? fundsMap.get(funds) + "#" + content : content);
		}
		return fundsMap;
	}

	/**
	 * 玩家投注项双面合并
	 *
	 * @param turn 玩家投注项
	 */
	private void mergeTwoSize(Map<String, int[][]> turn) {
		int[][] fundsMatrix = turn.get("NUMBER");
		if (ContainerTool.isEmpty(fundsMatrix)) {
			return;
		}
		for (int i = 0; i < fundsMatrix.length; i++) {
			List<Integer> indexList = findNoZeroIndex(fundsMatrix[i]);
			if (indexList.size() < 5) {
				continue;
			}
			IbmTypeEnum type = getMergeType(indexList);
			if (type == null) {
				continue;
			}
			switch (type) {
				case BIG:
					mergeTwoSizeItem(turn, fundsMatrix[i], i, 0, StringTool.getTypeEn(1), BIG);
					break;
				case SMALL:
					mergeTwoSizeItem(turn, fundsMatrix[i], i, 1, StringTool.getTypeEn(1), SMALL);
					break;
				case SINGLE:
					mergeTwoSizeItem(turn, fundsMatrix[i], i, 0, StringTool.getTypeEn(2), SINGLE);
					break;
				case DOUBLE:
					mergeTwoSizeItem(turn, fundsMatrix[i], i, 1, StringTool.getTypeEn(2), DOUBLE);
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
	private void removeUnreasonable(Map<String, int[][]> turn) {
		int[][] fundsMatrix;
		for (String typeKey : StringTool.getTypeEn()) {
			// 号码，大小，单双，龙虎
			fundsMatrix = turn.get(typeKey);
			if (ContainerTool.isEmpty(fundsMatrix)) {
				return;
			}
			//横向
			for (int[] ints : fundsMatrix) {
				int min = findMin(ints);
				if (min == 0) {
					continue;
				}
				lessMin(ints, min);
			}
			int[][] fundsTranspose = transpose(fundsMatrix);
			//纵向
			for (int i = 0; i < fundsTranspose.length; i++) {
				int min = findMin(fundsTranspose[i]);
				if (min == 0) {
					continue;
				}
				lessMin(fundsMatrix, i, min);
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
	/**
	 * 获取合并类型
	 *
	 * @param indexList 索引列表
	 * @return 合并类型
	 */
	private IbmTypeEnum getMergeType(List<Integer> indexList) {
		if (indexList.containsAll(SMALL)) {
			return IbmTypeEnum.SMALL;
		}
		if (indexList.containsAll(BIG)) {
			return IbmTypeEnum.BIG;
		}
		if (indexList.containsAll(SINGLE)) {
			return IbmTypeEnum.SINGLE;
		}
		if (indexList.containsAll(DOUBLE)) {
			return IbmTypeEnum.DOUBLE;
		}
		return null;
	}

	/**
	 * 获取set集合中非0 索引
	 *
	 * @param array 数组
	 * @return 非0索引
	 */
	private List<Integer> findNoZeroIndex(int[] array) {
		List<Integer> list = new ArrayList<>(array.length / 2);
		for (int i = 0; i < array.length; i++) {
			if (array[i] != 0) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * 矩阵转置
	 *
	 * @param matrix 矩阵
	 * @return 转置矩阵
	 */
	private int[][] transpose(int[][] matrix) {
		int x = matrix.length;
		int y = matrix[0].length;
		int[][] transpose = new int[y][x];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				transpose[j][i] = matrix[i][j];
			}
		}
		return transpose;
	}

	/**
	 * set数组整体减少某个num
	 *
	 * @param array 数组
	 * @param num   减少的数
	 */
	private void lessMin(int[] array, int num) {
		for (int i = 0; i < array.length; i++) {
			array[i] -= num;
		}
	}

	/**
	 * matrix矩阵的index列整体减少某个num
	 *
	 * @param matrix 矩阵
	 * @param col    指定索引列
	 * @param num    减少的数
	 */
	private void lessMin(int[][] matrix, int col, int num) {
		for (int i = 0; i < matrix.length; i++) {
			matrix[i][col] -= num;
		}
	}

	/**
	 * 获取大于0 的最小值
	 *
	 * @param array 数组
	 * @return 大于0 的最小值
	 */
	private int findMin(int[] array) {
		int tmp = Integer.MAX_VALUE;
		for (int num : array) {
			if (num == 0) {
				return 0;
			}
			if (tmp > num) {
				tmp = num;
			}
		}
		return tmp;
	}
	private void initMatrix(int[][] matrix) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				matrix[i][j] = RandomTool.getInt(5, 10);
			}
		}
	}
	private void print(int[][] matrix) {

		for (int[] arr : matrix) {
			for (int num : arr) {
				System.out.print(String.format("%2d|", num));
			}
			System.out.println();
		}
	}

	@Test public void test02() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			mergeBall();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}

	private void mergeBall() throws Exception {
		String gameId = "2ddb654f1c44497c879dab19298dd186";
		String period = "20190409-095";
		String handicapId = "15200330aa69441793eb35fc5ecacd83";
		String mergeFormat = "合并-%2d";
		String tableName = "ibm_exec_bet_item_xyft";
		Date nowTime = new Date();

		IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();

		Map<String, Map<String, int[][]>> mergeInfo = execBetItemTService.listBallMergeInfo(period, tableName);

		for (Map.Entry<String, Map<String, int[][]>> entry : mergeInfo.entrySet()) {
			String key = entry.getKey();
			Map<String, int[][]> turn = entry.getValue();
			// 去掉不合理的投注项
			removeUnreasonable(turn);

			// 双面合并
			mergeTwoSize(turn);

			// 去掉不合理的投注项
			removeUnreasonable(turn);

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
					Map<Integer, String> fundsMap = getFundsMap(fundsMatrix[i], typeKey, rank);
					mergeList.add(fundsMap);
				}
			}
			// 冠亚和
			fundsMatrix = turn.get("CHAMPION_SUM");
			if (ContainerTool.notEmpty(fundsMatrix)) {
				Map<Integer, String> fundsMap = getFundsMap(fundsMatrix[0], "CHAMPION_SUM", "冠亚");
				mergeList.add(fundsMap);
			}

			IbmExecBetItemT execBetItemT;
			int rounds = 0;

			//写入数据库
			String handicapMemberId = key.split("#")[0];
			String betMode = key.split("#")[1];
			for (Map<Integer, String> map : mergeList) {
				for (Map.Entry<Integer, String> bet : map.entrySet()) {
					execBetItemT = new IbmExecBetItemT();
					execBetItemT.setExecPlanGroupId(null);
					execBetItemT.setHandicapId(handicapId);
					execBetItemT.setGameId(gameId);
					execBetItemT.setHandicapMemberId(handicapMemberId);
					execBetItemT.setPeriod(period);
					execBetItemT.setFundT(bet.getKey());
					execBetItemT.setPlanGroupDesc(String.format(mergeFormat, ++rounds));
					execBetItemT.setBetContent(bet.getValue());
					execBetItemT.setBetContentLen(bet.getValue().split("#").length);
					execBetItemT.setBetMode(betMode);
					execBetItemT.setFundsIndex("0");
					execBetItemT.setExecState(IbmStateEnum.READY.name());
					execBetItemT.setCreateTime(nowTime);
					execBetItemT.setCreateTimeLong(nowTime.getTime());
					execBetItemT.setUpdateTime(nowTime);
					execBetItemT.setUpdateTimeLong(nowTime.getTime());
					execBetItemT.setState(IbmStateEnum.OPEN.name());
					execBetItemTService.save(execBetItemT, tableName);
				}
			}
		}
	}

	@Test public void test03() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			mergeBall3();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}
	private void mergeBall3() throws SQLException {
		String handicapMemberId = "6724bab7e0504295a4970ad76b739a53";
		String period = "20190808-180";
		int betType = PlanTool.BET_TYPE_MERGE;
		BaseDao dao = new BaseDao();
		String sql = "SELECT HANDICAP_MEMBER_ID_,IBM_EXEC_BET_ITEM_ID_,BET_CONTENT_,BET_MODE_,FUND_T_ "
				+ " FROM `sub_iebi_idc_xyft` WHERE PERIOD_ = ? AND HANDICAP_MEMBER_ID_ = ? and BET_TYPE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(period);
		parameterList.add(handicapMemberId);
		parameterList.add(betType);
		List<Map<String, Object>> info = dao.findMapList(sql, parameterList);

		Map<String, Map<String, int[][]>> mergeInfo = new HashMap<>();

		info.forEach(turnMap -> {
			String betMode = turnMap.get("BET_MODE_").toString();
			String key = handicapMemberId + "#" + betMode;
			String[] betContents = turnMap.get("BET_CONTENT_").toString().split("#");
			int fundsT = Integer.parseInt(turnMap.get("FUND_T_").toString());
			Map<String, int[][]> turn;
			String typeKey;
			Integer typeIndex;
			if (mergeInfo.containsKey(key)) {
				turn = mergeInfo.get(key);
			} else {
				turn = new HashMap<>(5);
			}
			for (String betContent : betContents) {
				String[] bets = betContent.split("\\|");
				int index = StringTool.getRankCn(bets[0]);
				//冠亚和
				if (index == 0) {
					Integer championSumIndex = GameTool.getChampionSumIndex(bets[1]);
					putFunds(fundsT, turn, "CHAMPION_SUM", championSumIndex, index, 1, 21);
					continue;
				}
				//去除第一个冠亚,从0开始
				index--;
				int type = StringTool.getTypeCn(bets[1]);
				typeKey = StringTool.getTypeEn(type);
				switch (type) {
					case 1:
						typeIndex = GameTool.sizeIndex(bets[1]);
						putFunds(fundsT, turn, typeKey, typeIndex, index, 10, 2);
						break;
					case 2:
						typeIndex = GameTool.parityIndex(bets[1]);
						putFunds(fundsT, turn, typeKey, typeIndex, index, 10, 2);
						break;
					case 3:
						typeIndex = GameTool.dragonIndex(bets[1]);
						putFunds(fundsT, turn, typeKey, typeIndex, index, 5, 2);
						break;
					default:
						typeKey = StringTool.getTypeEn(0);
						typeIndex = Integer.parseInt(bets[1]) - 1;
						putFunds(fundsT, turn, typeKey, typeIndex, index, 10, 10);
						break;
				}
			}
			mergeInfo.put(key, turn);
		});

		for (Map.Entry<String, Map<String, int[][]>> entry : mergeInfo.entrySet()) {

			String key = entry.getKey();
			Map<String, int[][]> turn = entry.getValue();

			System.out.println(LogTool.printHashtagM());
			System.out.println("原投注项");
			for (int[][] matrix : turn.values()){
				print(matrix);
			}
			System.out.println(LogTool.printHashtagM());
			System.out.println("去掉不合理的投注项");
			// 去掉不合理的投注项
			removeUnreasonable(turn);
			for (int[][] matrix : turn.values()){
				print(matrix);
			}
		}


	}


	/**
	 * 放入资金<br>
	 * 往turn 对象中key所对应的值的数组[index][typeIndex] 中加入资金
	 *
	 * @param fundsT    金额
	 * @param turn      存储map
	 * @param key       类型key
	 * @param typeIndex 类型索引
	 * @param index     位置索引
	 * @param x         排名范围
	 * @param y         类型范围
	 */
	private void putFunds(int fundsT, Map<String, int[][]> turn, String key, Integer typeIndex, int index, int x,
			int y) {
		if (typeIndex == null) {
			return;
		}
		if (turn.containsKey(key)) {
			turn.get(key)[index][typeIndex] += fundsT;
		} else {
			int[][] arr = new int[x][y];
			arr[index][typeIndex] = fundsT;
			turn.put(key, arr);
		}
	}
}
