package com.ibm.common.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.handicap.config.BallConfig;
import com.ibm.common.utils.handicap.config.HappyConfig;
import com.ibm.common.utils.handicap.config.NumberConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @Description: 游戏工具类
 * @Author: Dongming
 * @Date: 2019-09-10 13:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GameTool {
    private static final Logger log = LogManager.getLogger(GameTool.class);
	//region 编码区
	/**
	 * 编码区
	 */
	private static final String[] NUMBER_RANK = {"第一名", "第二名", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名",
			"冠亚"};

	private static final String[] NUMBER_TYPE = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
			"14", "15", "16", "17", "18", "19", "大", "小", "单", "双", "龙", "虎"};

	private static final String[] BALL_RANK = {"第一球", "第二球", "第三球", "第四球", "第五球", "总和", "龙虎和", "前三", "中三", "后三"};

	public static final String[] BALL_TYPE = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "大", "小", "单", "双",
			"龙", "虎", "和", "豹子", "顺子", "对子", "半顺", "杂六"};

	private static final String[] KLC_RANK = {"第一球", "第二球", "第三球", "第四球", "第五球", "第六球", "第七球", "第八球", "总和"};

	public static final String[] KLC_TYPE = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20", "东", "南", "西", "北", "中", "发", "白", "合单", "合双",
			"大", "小", "单", "双", "尾大", "尾小", "龙", "虎"};

	/**
	 * 获取排名类型
	 *
	 * @param gameCode 游戏code
	 * @return 排名类型
	 */
	private static String[] getRank(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return NUMBER_RANK;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return BALL_RANK;
			case XYNC:
			case GDKLC:
				return KLC_RANK;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");

		}
	}

	/**
	 * 获取投注类型
	 *
	 * @param gameCode 游戏code
	 * @return 投注类型
	 */
	private static String[] getType(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return NUMBER_TYPE;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return BALL_TYPE;
			case XYNC:
			case GDKLC:
				return KLC_TYPE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取数字排名
	 *
	 * @param code    游戏编码
	 * @param rankStr 字符排名
	 * @return 数字排名
	 */
	public static Integer rank(GameUtil.Code code, String rankStr) {
		switch (code) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return numberRank(rankStr);
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return ballRank(rankStr);
			case XYNC:
			case GDKLC:
				return klcRank(rankStr);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");

		}
	}

	/**
	 * 获取字符排名
	 *
	 * @param code 游戏编码
	 * @param rank 数字排名
	 * @return 字符排名
	 */
	public static String rankStr(GameUtil.Code code, Integer rank) {
		return rank < getRank(code).length ? getRank(code)[rank] : null;
	}

	/**
	 * 获取 数字类型
	 *
	 * @param code    游戏编码
	 * @param typeStr 字符类型
	 * @return 数字类型
	 */
	public static Integer type(GameUtil.Code code, String typeStr) {
		switch (code) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return numberType(typeStr);
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return ballType(typeStr);
			case XYNC:
			case GDKLC:
				return klcType(typeStr);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");

		}
	}

	/**
	 * 获取 字符类型
	 *
	 * @param code 游戏编码
	 * @param type 数字类型
	 * @return 字符类型
	 */
	public static String typeStr(GameUtil.Code code, Integer type) {
		return type < getType(code).length ? getType(code)[type] : null;
	}

	/**
	 * 获取类型
	 *
	 * @param rankStr 字符排名
	 * @param typeStr 字符类型
	 * @return 数字类型
	 */
	public static Integer type(GameUtil.Code code, String rankStr, String typeStr) {
		Integer rank = rank(code, rankStr);
		if (rank == null) {
			return null;
		}
		return type(code, rank, typeStr);
	}

	/**
	 * 获取类型
	 *
	 * @param code    游戏类型
	 * @param rank    数字排名
	 * @param typeStr 字符类型
	 * @return 数字类型
	 */
	public static Integer type(GameUtil.Code code, Integer rank, String typeStr) {
		Integer type = type(code, typeStr);
		if (type == null) {
			return null;
		}
		switch (code) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return numberType(rank, type);
			case JSSSC:
			case CQSSC:
			case COUNTRY_SSC:
			case SELF_SSC_5:
				return ballType(rank, type);
			case XYNC:
			case GDKLC:
				return klcType(rank, type);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");

		}
	}


	/**
	 * 根据投注项获取排名和类型
	 *
	 * @param gameCode 游戏类型
	 * @param betItem  投注项
	 * @return 数字排名和数字类型
	 */
	public static int[] item(GameUtil.Code gameCode, String betItem) {
		String[] item = betItem.split("\\|");
		int[] result = new int[2];
		result[0] = rank(gameCode, item[0]);
		result[1] = type(gameCode, item[1]);
		return result;
	}

	/**
	 * 根据获取排名和类型获取投注项
	 *
	 * @param gameCode 游戏类型
	 * @param rank     数字排名
	 * @param type     数字类型
	 * @return 投注项
	 */
	public static String itemStr(GameUtil.Code gameCode, int rank, int type) {
		return getRank(gameCode)[rank].concat("|").concat(getType(gameCode)[type]);
	}

	/**
	 * 根据获取排名和类型获取投注项
	 *
	 * @param gameCode 游戏类型
	 * @param rank     数字排名
	 * @param type     数字类型
	 * @return 投注项
	 */
	public static String getItemStr(GameUtil.Code gameCode, int rank, int type) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return itemStr(gameCode, rank, type + 19);
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return itemStr(gameCode, rank, type + 10);
			case XYNC:
			case GDKLC:
				return itemStr(gameCode, rank, type + 20);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 校验游戏数据格式
	 *
	 * @param gameCode      游戏类型
	 * @param filterProject 过滤信息
	 * @return gameCode
	 */
	public static String gameRule(GameUtil.Code gameCode, String filterProject) {
		if (StringTool.isEmpty(filterProject)) {
			return "";
		}
		String[] filterProjects = filterProject.split("#");
		List<String> lists;
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				lists = NumberConfig.BET_ITEM;
				break;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				lists = BallConfig.BET_ITEM;
				break;
			case XYNC:
			case GDKLC:
				lists = HappyConfig.BET_ITEM;
				break;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
		StringBuilder filter = new StringBuilder();
		for (String project : filterProjects) {
			if (StringTool.notEmpty(project) && StringTool.isContains(lists.toString(), project)) {
				filter.append(project).append("#");
			}
		}
		return filter.deleteCharAt(filter.length() - 1).toString();
	}

	/**
	 * 根据获取排名和类型获取投注项
	 *
	 * @param gameCode 游戏类型
	 * @param rank     数字排名
	 * @param type     数字类型
	 * @param funds    投注金额
	 * @return 投注项
	 */
	public static String itemStr(GameUtil.Code gameCode, int rank, int type, int funds) {
		return getRank(gameCode)[rank].concat("|").concat(getType(gameCode)[type]).concat("|")
				.concat(Integer.toString(funds));
	}

	/**
	 * 获取号码类游戏 数字排名
	 *
	 * @param rankStr 字符类型
	 * @return 数字排名
	 */
	public static Integer numberRank(String rankStr) {
		for (int i = 0; i < NUMBER_RANK.length; i++) {
			if (NUMBER_RANK[i].equals(rankStr)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * 获取球类游戏 数字排名
	 *
	 * @param rankStr 字符类型
	 * @return 数字排名
	 */
	public static Integer ballRank(String rankStr) {
		for (int i = 0; i < BALL_RANK.length; i++) {
			if (BALL_RANK[i].equals(rankStr)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * 获取快乐彩类游戏 数字排名
	 *
	 * @param rankStr 字符类型
	 * @return 数字排名
	 */
	private static Integer klcRank(String rankStr) {
		for (int i = 0; i < KLC_RANK.length; i++) {
			if (KLC_RANK[i].equals(rankStr)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * 获取号码类游戏 数字类型
	 *
	 * @param typeStr 字符类型
	 * @return 数字类型
	 */
	public static Integer numberType(String typeStr) {
		for (int i = 0; i < NUMBER_TYPE.length; i++) {
			if (NUMBER_TYPE[i].equals(typeStr)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * 获取球类游戏 数字类型
	 *
	 * @param typeStr 字符类型
	 * @return 数字类型
	 */
	private static Integer ballType(String typeStr) {
		for (int i = 0; i < BALL_TYPE.length; i++) {
			if (BALL_TYPE[i].equals(typeStr)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * 获取快乐彩类游戏 数字类型
	 *
	 * @param typeStr 字符类型
	 * @return 数字类型
	 */
	private static Integer klcType(String typeStr) {
		for (int i = 0; i < KLC_TYPE.length; i++) {
			if (KLC_TYPE[i].equals(typeStr)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * 获取球类类型 数字类型
	 *
	 * @param rank 数字排名
	 * @param type 数字类型
	 * @return 数字类型
	 */
	private static Integer numberType(Integer rank, Integer type) {
		//号码
		if (rank < 10) {
			//10-19 号码不可能
			if (type > 9 && type <= 18) {
				return null;
			}
			//龙虎 5-10不可能
			if (rank > 4 && type >= 23) {
				return null;
			}
			return type;
		}
		//冠亚 1,2,龙,虎不可能
		if (type < 2 || type >= 23) {
			return null;
		}
		return type;
	}

	/**
	 * 获取球类类型 数字类型
	 *
	 * @param rank 数字排名
	 * @param type 数字类型
	 * @return 数字类型
	 */
	private static Integer ballType(Integer rank, Integer type) {
		//号码
		if (rank <= 4) {
			//双面之后都不可能
			if (type >= 14) {
				return null;
			}
			return type;
		}
		//总和
		if (rank == 5) {
			//0-9不可能，龙虎之后都不可能
			if (type <= 9 || type >= 14) {
				return null;
			}
			return type;
		}
		//龙虎和
		if (rank == 6) {
			if (type <= 13 || type >= 17) {
				return null;
			}
			return type;
		}
		//前三、中三、后三
		//0-9不可能，双面龙虎都不可能
		if (type <= 16) {
			return null;
		}
		return type;
	}


	/**
	 * 获取 快乐彩类型 数字类型
	 *
	 * @param rank 数字排名
	 * @param type 数字类型
	 * @return 数字类型
	 */
	private static Integer klcType(Integer rank, Integer type) {

		//0-3后 龙虎和不可能
		if (rank > 3 && type > 36) {
			return null;
		}
		//总和  数字-方位-中发白 不可能
		if (rank == 8 && type < 28) {
			return null;
		}
		return type;
	}

	//endregion

	//region 投注项

	/**
	 * 获取投注Code信息
	 *
	 * @param betContent 投注正文
	 * @param code       游戏编码
	 * @return 投注Code信息    rank,types
	 */
	public static Map<Integer, Set<Integer>> getBetCodeByJson(GameUtil.Code code, String betContent) {
		Map<Integer, Set<Integer>> betCode = new HashMap<>(10);
		JSONArray betContents = JSON.parseArray(betContent);
		for (int i = 0; i < betContents.size(); i++) {
			JSONArray contents = betContents.getJSONArray(i);
			String rankStr = contents.getString(0);
			String typeStr = contents.getString(1);
			Integer rank = rank(code, rankStr);
			if (rank == null) {
				continue;
			}
			Integer type = type(code, rank, typeStr);
			if (type == null) {
				continue;
			}
			if (betCode.containsKey(rank)) {
				betCode.get(rank).add(type);
			} else {
				Set<Integer> set = new HashSet<>();
				set.add(type);
				betCode.put(rank, set);
			}
		}
		return betCode;
	}

	/**
	 * 获取投注Code信息
	 *
	 * @param gameCode    游戏编码
	 * @param betContents 投注正文
	 * @param betRate     投注比例
	 * @return 投注Code信息    rank,type-funds
	 */
	public static Map<Integer, Map<Integer, Integer>> getBetCode(GameUtil.Code gameCode, String[] betContents,
																 double betRate) {
		Map<Integer, Map<Integer, Integer>> betCode = new HashMap<>(10);
		if (ContainerTool.isEmpty(betContents)) {
			return null;
		}
		for (String betContent : betContents) {
			String[] bet = betContent.split("\\|");
			Integer rank = rank(gameCode, bet[0]);
			Integer type = type(gameCode, rank, bet[1]);
			if (StringTool.isEmpty(rank, type)) {
			    log.error("错误的投注项" + betContent);
				continue;
			}
			//金额 向上取整
			int fundsTh = (int) (Math.ceil(NumberTool.getInteger(bet[2]) * (betRate / 1000)) * 1000);
			if (betCode.containsKey(rank)) {
				Map<Integer, Integer> typeFunds = betCode.get(rank);
				typeFunds.put(type, typeFunds.getOrDefault(type, 0) + fundsTh);
			} else {

				Map<Integer, Integer> typeFunds = new TreeMap<>();
				typeFunds.put(type, fundsTh);
				betCode.put(rank, typeFunds);
			}
		}
		return betCode;
	}

	/**
	 * 格式化投注信息
	 *
	 * @param gameCode    游戏code
	 * @param betContents 投注信息
	 * @return 投注信息字符串
	 */
	public static String getBetContent(GameUtil.Code gameCode, String[] betContents) {
		Map<Integer, String> betCode = new HashMap<>(10);
		if (ContainerTool.isEmpty(betContents)) {
			return null;
		}
		for (String betContent : betContents) {
			String[] bet = betContent.split("\\|");
			Integer rank = rank(gameCode, bet[0]);
			if (betCode.containsKey(rank)) {
				betCode.put(rank, betCode.get(rank).concat(",").concat(bet[1]));
			} else {
				betCode.put(rank, bet[1]);
			}
		}
		StringBuilder contents = new StringBuilder();
		for (Map.Entry<Integer, String> codeEntry : betCode.entrySet()) {
			Integer rank = codeEntry.getKey();
			contents.append(getRank(gameCode)[rank]).append("-").append(codeEntry.getValue()).append("#");
		}
		contents.delete(contents.length() - 1, contents.length());
		return contents.toString();
	}

	/**
	 * 获取展示信息
	 *
	 * @param gameCode    游戏code
	 * @param betContents 投注信息
	 * @return 展示信息
	 */
	public static JSONArray getBetShow(GameUtil.Code gameCode, String[] betContents) {
		Map<Integer, List<String>> betCode = new HashMap<>(10);
		if (ContainerTool.isEmpty(betContents)) {
			return null;
		}
		for (String betContent : betContents) {
			String[] bet = betContent.split("\\|");
			Integer rank = rank(gameCode, bet[0]);
			String show = bet[1].concat(":" + NumberTool.getInteger(bet[2]) / 1000);
			if (betCode.containsKey(rank)) {
				betCode.get(rank).add(show);
			} else {
				List<String> list = new ArrayList<>();
				list.add(show);
				betCode.put(rank, list);
			}
		}
		JSONArray contents = new JSONArray();
		for (Map.Entry<Integer, List<String>> codeEntry : betCode.entrySet()) {
			Integer rank = codeEntry.getKey();
			for (String show : codeEntry.getValue()) {
				contents.add(getRank(gameCode)[rank].concat("-").concat(show));
			}

		}
		return contents;
	}

	/**
	 * 根据分投获取投注项
	 *
	 * @param splitAmount 分投金额
	 * @param bet         投注项
	 * @param funds       资金
	 * @param limitMin    最低金额
	 * @return 投注项
	 */
	public static String[] getItem(int splitAmount, String bet, double funds, int limitMin) {
		String[] item;
		int len = (int) (funds / splitAmount);
		double surplus = funds % splitAmount;
		String betItem = bet.concat("|").concat(String.valueOf(NumberTool.longValueT(splitAmount)));
		if (surplus == 0) {
			item = new String[len];
			for (int j = 0; j < len; j++) {
				item[j] = betItem;
			}
		} else {
			item = new String[len + 1];
			for (int j = 0; j < len - 1; j++) {
				item[j] = betItem;
			}
			if (surplus > limitMin) {

				item[len - 1] = betItem;
				item[len] = bet.concat("|").concat(String.valueOf(NumberTool.longValueT(surplus)));
			} else {
				//切割后的余数小于最低限额
				item[len - 1] = bet.concat("|")
						.concat(String.valueOf(NumberTool.longValueT(splitAmount + surplus - limitMin)));
				item[len] = bet.concat("|").concat(String.valueOf(NumberTool.longValueT(limitMin)));
			}
		}
		return item;
	}
	//endregion

	//region 资金矩阵

	/**
	 * 初始化一个资金矩阵
	 *
	 * @param gameCode 游戏编码
	 * @return 资金矩阵
	 */
	public static double[][] getFundsMatrix(GameUtil.Code gameCode) {
		return new double[getRank(gameCode).length][getType(gameCode).length];
	}

	/**
	 * 获取号码矩阵
	 *
	 * @param gameCode    游戏编码
	 * @param fundsMatrix 金额矩阵
	 * @return 号码矩阵
	 */
	public static double[][] getNumberMatrix(GameUtil.Code gameCode, double[][] fundsMatrix) {
		switch (gameCode) {
			case XYFT:
			case PK10:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return NumberTool.catMatrix(fundsMatrix, 0, 10, 0, 10);
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return NumberTool.catMatrix(fundsMatrix, 0, 5, 0, 10);
			case XYNC:
			case GDKLC:
				return NumberTool.catMatrix(fundsMatrix, 0, 8, 0, 20);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");

		}
	}

	/**
	 * 获取双面矩阵
	 *
	 * @param gameCode    游戏编码
	 * @param fundsMatrix 金额矩阵
	 * @return 双面矩阵
	 */
	public static double[][][] getTwoSideMatrix(GameUtil.Code gameCode, double[][] fundsMatrix) {
		double[][][] matrix;
		switch (gameCode) {
			case XYFT:
			case PK10:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				matrix = new double[3][][];
				//大小
				matrix[0] = NumberTool.catMatrix(fundsMatrix, 0, 10, 19, 21);
				//单双
				matrix[1] = NumberTool.catMatrix(fundsMatrix, 0, 10, 21, 23);
				//龙虎
				matrix[2] = NumberTool.catMatrix(fundsMatrix, 0, 10, 23, 25);
				break;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				matrix = new double[2][][];
				//大小
				matrix[0] = NumberTool.catMatrix(fundsMatrix, 0, 5, 10, 12);
				//单双
				matrix[1] = NumberTool.catMatrix(fundsMatrix, 0, 5, 12, 14);
				break;
			case XYNC:
			case GDKLC:
				matrix = new double[7][][];
				//方位
				matrix[0] = NumberTool.catMatrix(fundsMatrix, 0, 8, 20, 24);
				//中发白
				matrix[1] = NumberTool.catMatrix(fundsMatrix, 0, 8, 24, 27);
				//合单双
				matrix[2] = NumberTool.catMatrix(fundsMatrix, 0, 8, 27, 29);
				//大小
				matrix[3] = NumberTool.catMatrix(fundsMatrix, 0, 8, 29, 31);
				//单双
				matrix[4] = NumberTool.catMatrix(fundsMatrix, 0, 8, 31, 33);
				//尾大小
				matrix[5] = NumberTool.catMatrix(fundsMatrix, 0, 8, 33, 35);
				//龙虎
				matrix[6] = NumberTool.catMatrix(fundsMatrix, 0, 8, 35, 37);
				break;

			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
		return matrix;
	}

	/**
	 * 获取其他矩阵
	 *
	 * @param fundsMatrix 金额矩阵
	 * @return 其他矩阵
	 */
	public static double[][] getBallOtherItem(double[][] fundsMatrix) {
		double[][] matrix = new double[5][];
		double[] srcMatrix = new double[7];
		System.arraycopy(fundsMatrix[5], 10, srcMatrix, 0, 7);
		matrix[0] = srcMatrix;
		srcMatrix = new double[3];
		System.arraycopy(fundsMatrix[6], 14, srcMatrix, 0, 3);
		matrix[1] = srcMatrix;
		for (int i = 7; i < 10; i++) {
			srcMatrix = new double[5];
			System.arraycopy(fundsMatrix[i], 17, srcMatrix, 0, 5);
			matrix[i - 5] = srcMatrix;
		}
		return matrix;
	}

	/**
	 * 获取其他矩阵
	 *
	 * @param fundsMatrix 金额矩阵
	 * @return 其他矩阵
	 */
	public static double[] getNumOtherItem(double[][] fundsMatrix) {
		return fundsMatrix[10];
	}

	/**
	 * 获取其他矩阵
	 *
	 * @param fundsMatrix 金额矩阵
	 * @return 其他矩阵
	 */
	public static double[] getHappyOtherItem(double[][] fundsMatrix) {
		return fundsMatrix[8];
	}

	//endregion

	//region 反投

	/**
	 * 号码反投
	 *
	 * @param gameCode    游戏编码
	 * @param fundsMatrix 金额矩阵
	 */
	public static void numberOpposing(GameUtil.Code gameCode, double[][] fundsMatrix) {
		switch (gameCode) {
			case XYFT:
			case PK10:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				numberNumberOpposing(fundsMatrix);
				break;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				ballNumberOpposing(fundsMatrix);
				break;
			case XYNC:
			case GDKLC:
				happyNumberOpposing(fundsMatrix);
				break;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");

		}

	}

	/**
	 * 双面反投
	 *
	 * @param gameCode    游戏编码
	 * @param fundsMatrix 金额矩阵
	 */
	public static void twoSideOpposing(GameUtil.Code gameCode, double[][][] fundsMatrix) {
		switch (gameCode) {
			case XYFT:
			case PK10:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				numberTwoSideOpposing(fundsMatrix);
				break;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				ballTwoSideOpposing(fundsMatrix);
				break;
			case XYNC:
			case GDKLC:
				happyTwoSideOpposing(fundsMatrix);
				break;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");

		}

	}

	/**
	 * 号码反投
	 *
	 * @param matrix 金额矩阵
	 */
	private static void numberNumberOpposing(double[][] matrix) {
		double[][] transpose = NumberTool.transpose(matrix);
		int[] rowSize = new int[10];
		int[] colSize = new int[10];
		for (int i = 0; i < 10; i++) {
			rowSize[i] = NumberTool.findNoZeroValSize(matrix[i]);
			colSize[i] = NumberTool.findNoZeroValSize(transpose[i]);
		}
		//横轴金额不为空的索引
		opposing(matrix, transpose, rowSize, colSize);
	}

	/**
	 * 号码反投
	 *
	 * @param matrix 金额矩阵
	 */
	private static void ballNumberOpposing(double[][] matrix) {
		for (double[] rows : matrix) {
			double funds = NumberTool.findMax(rows);
			if (funds - 0 == 0) {
				continue;
			}
			NumberTool.less(funds, rows);
		}
	}

	/**
	 * 号码反投
	 *
	 * @param matrix 金额矩阵
	 */
	private static void happyNumberOpposing(double[][] matrix) {
		double[][] transpose = NumberTool.transpose(matrix);
		int[] rowSize = new int[8];
		int[] colSize = new int[20];
		for (int i = 0; i < 8; i++) {
			rowSize[i] = NumberTool.findNoZeroValSize(matrix[i]);
			colSize[i] = NumberTool.findNoZeroValSize(transpose[i]);
		}
		opposing(matrix, transpose, rowSize, colSize);
	}

	/**
	 * 反投
	 *
	 * @param matrix    资金矩阵
	 * @param transpose 资金矩阵转置
	 * @param rowSize   行不为0 列表
	 * @param colSize   列不为0 列表
	 */
	private static void opposing(double[][] matrix, double[][] transpose, int[] rowSize, int[] colSize) {
		//横轴金额不为空的索引
		List<Integer> rowIndex = NumberTool.findNoZeroValIndex(rowSize);
		if (rowIndex.size() == 0) {
			return;
		}
		//纵轴金额不为空的索引
		List<Integer> colIndex = NumberTool.findNoZeroValIndex(colSize);

		if (rowIndex.size() <= colIndex.size()) {
			//横轴反投
			for (int row : rowIndex) {
				double funds = NumberTool.findMax(matrix[row]);
				NumberTool.less(funds, matrix[row]);
			}

		} else {
			//纵轴反投
			for (int col : colIndex) {
				double funds = NumberTool.findMax(transpose[col]);
				NumberTool.less(funds, matrix, col);
			}
		}
	}

	/**
	 * 双面反投
	 *
	 * @param matrix 金额矩阵
	 */
	private static void ballTwoSideOpposing(double[][][] matrix) {
		//反投大小
		double[][] transpose = NumberTool.transpose(matrix[0]);
		int[] colSize = new int[2];
		for (int i = 0; i < 2; i++) {
			colSize[i] = NumberTool.findNoZeroValSize(transpose[i]);
		}
		//纵轴非0个数大于5个
		if (colSize[0] > 2 || colSize[1] > 2) {
			for (int i = 0; i < 2; i++) {
				double funds = NumberTool.findMax(transpose[i]);
				NumberTool.less(funds, matrix[0], i);
			}
		} else {
			for (int i = 0; i < 5; i++) {
				double funds = NumberTool.findMax(matrix[0][i]);
				NumberTool.less(funds, matrix[0][i]);
			}
		}

		//反投单双
		transpose = NumberTool.transpose(matrix[1]);
		for (int i = 0; i < 2; i++) {
			colSize[i] = NumberTool.findNoZeroValSize(transpose[i]);
		}
		//纵轴非0个数大于5个
		if (colSize[0] > 2 || colSize[1] > 2) {
			for (int i = 0; i < 2; i++) {
				double funds = NumberTool.findMax(transpose[i]);
				NumberTool.less(funds, matrix[1], i);
			}
		} else {
			for (int i = 0; i < 5; i++) {
				double funds = NumberTool.findMax(matrix[1][i]);
				NumberTool.less(funds, matrix[1][i]);
			}
		}
	}

	/**
	 * 双面反投
	 *
	 * @param matrix 金额矩阵
	 */
	private static void numberTwoSideOpposing(double[][][] matrix) {
		//反投大小
		double[][] transpose = NumberTool.transpose(matrix[0]);
		int[] colSize = new int[2];
		for (int i = 0; i < 2; i++) {
			colSize[i] = NumberTool.findNoZeroValSize(transpose[i]);
		}
		//纵轴非0个数大于5个	纵向反投
		if (colSize[0] > 5 || colSize[1] > 5) {
			for (int i = 0; i < 2; i++) {
				double funds = NumberTool.findMax(transpose[i]);
				NumberTool.less(funds, matrix[0], i);
			}
		} else {
			for (int i = 0; i < 10; i++) {
				double funds = NumberTool.findMax(matrix[0][i]);
				NumberTool.less(funds, matrix[0][i]);
			}
		}

		//反投单双
		transpose = NumberTool.transpose(matrix[1]);
		for (int i = 0; i < 2; i++) {
			colSize[i] = NumberTool.findNoZeroValSize(transpose[i]);
		}
		//纵轴非0个数大于5个
		if (colSize[0] > 5 || colSize[1] > 5) {
			for (int i = 0; i < 2; i++) {
				double funds = NumberTool.findMax(transpose[i]);
				NumberTool.less(funds, matrix[1], i);
			}
		} else {
			for (int i = 0; i < 10; i++) {
				double funds = NumberTool.findMax(matrix[1][i]);
				NumberTool.less(funds, matrix[1][i]);
			}
		}
		//反投龙虎
		for (int i = 0; i < 5; i++) {
			double funds = NumberTool.findMax(matrix[2][i]);
			NumberTool.less(funds, matrix[2][i]);
		}
	}

	/**
	 * 双面反投
	 *
	 * @param matrix 金额矩阵
	 */
	private static void happyTwoSideOpposing(double[][][] matrix) {
		//方位反投
		double[][]  transpose = NumberTool.transpose(matrix[0]);
		int colSize = NumberTool.findNoZeroValSize(transpose);
		//算出非零总个数大于16 则纵向反投 否则，横向反投
		if (colSize > 16 ) {
			for (int i = 0; i < 4; i++) {
				double funds = NumberTool.findMax(transpose[i]);
				NumberTool.less(funds, matrix[0], i);
			}
		} else {
			for (int i = 0; i < 8; i++) {
				double funds = NumberTool.findMax(matrix[0][i]);
				NumberTool.less(funds, matrix[0][i]);
			}
		}

		//反投中发白
		transpose = NumberTool.transpose(matrix[1]);
		colSize =  NumberTool.findNoZeroValSize(transpose);
		if (colSize > 12 ) {
			for (int i = 0; i < 3; i++) {
				double funds = NumberTool.findMax(transpose[i]);
				NumberTool.less(funds, matrix[1], i);
			}
		} else {
			for (int i = 0; i < 8; i++) {
				double funds = NumberTool.findMax(matrix[1][i]);
				NumberTool.less(funds, matrix[1][i]);
			}
		}

		//合单双反投
		transpose = NumberTool.transpose(matrix[2]);
		colSize = NumberTool.findNoZeroValSize(transpose);
		if (colSize > 8) {
			for (int i = 0; i < 2; i++) {
				double funds = NumberTool.findMax(transpose[i]);
				NumberTool.less(funds, matrix[2], i);
			}
		} else {
			for (int i = 0; i < 8; i++) {
				double funds = NumberTool.findMax(matrix[2][i]);
				NumberTool.less(funds, matrix[2][i]);
			}
		}

		//反投大小
		transpose = NumberTool.transpose(matrix[3]);
		colSize = NumberTool.findNoZeroValSize(transpose);
		if (colSize > 8) {
			for (int i = 0; i < 2; i++) {
				double funds = NumberTool.findMax(transpose[i]);
				NumberTool.less(funds, matrix[3], i);
			}
		} else {
			for (int i = 0; i < 8; i++) {
				double funds = NumberTool.findMax(matrix[3][i]);
				NumberTool.less(funds, matrix[3][i]);
			}
		}

		//反投单双
		transpose = NumberTool.transpose(matrix[4]);
		colSize = NumberTool.findNoZeroValSize(transpose);
		if (colSize > 8) {
			for (int i = 0; i < 2; i++) {
				double funds = NumberTool.findMax(transpose[i]);
				NumberTool.less(funds, matrix[4], i);
			}
		} else {
			for (int i = 0; i < 8; i++) {
				double funds = NumberTool.findMax(matrix[4][i]);
				NumberTool.less(funds, matrix[4][i]);
			}
		}


		//尾大小反投
		transpose = NumberTool.transpose(matrix[5]);
		colSize = NumberTool.findNoZeroValSize(transpose);
		//纵轴非0个数大于4个
		if (colSize > 8) {
			for (int i = 0; i < 2; i++) {
				double funds = NumberTool.findMax(transpose[i]);
				NumberTool.less(funds, matrix[5], i);
			}
		} else {
			for (int i = 0; i < 8; i++) {
				double funds = NumberTool.findMax(matrix[5][i]);
				NumberTool.less(funds, matrix[5][i]);
			}
		}

		//反投龙虎
		transpose = NumberTool.transpose(matrix[6]);
		colSize = NumberTool.findNoZeroValSize(transpose);
		if (colSize > 4) {
			for (int i = 0; i < 2; i++) {
				double funds = NumberTool.findMax(transpose[i]);
				NumberTool.less(funds, matrix[6], i);
			}
		} else {
			for (int i = 0; i < 4; i++) {
				double funds = NumberTool.findMax(matrix[6][i]);
				NumberTool.less(funds, matrix[6][i]);
			}
		}







	}
	//endregion

	//region SQL区

	/**
	 * 获取开奖信息sql语句
	 *
	 * @param game 游戏
	 * @return 开奖信息sql语句
	 */
	public static String findDrawInfoSql(GameUtil.Code game) {
		switch (game) {
			case PK10:
				return getDrawInfoNumberSql("ibm_rep_draw_pk10");
			case XYFT:
				return getDrawInfoNumberSql("ibm_rep_draw_xyft");
			case JS10:
				return getDrawInfoNumberSql("ibm_rep_draw_js10");
			case SELF_10_2:
				return getDrawInfoNumberSql("ibm_rep_draw_self_10_2");
			case SELF_10_5:
				return getDrawInfoNumberSql("ibm_rep_draw_self_10_5");
			case COUNTRY_10:
				return getDrawInfoNumberSql("ibm_rep_draw_country_10");
			case CQSSC:
				return getDrawInfoBallSql("ibm_rep_draw_cqssc");
			case JSSSC:
				return getDrawInfoBallSql("ibm_rep_draw_jsssc");
			case SELF_SSC_5:
				return getDrawInfoBallSql("ibm_rep_draw_self_ssc_5");
			case COUNTRY_SSC:
				return getDrawInfoBallSql("ibm_rep_draw_country_ssc");
			case XYNC:
				return getDrawInfoHappySql("ibm_rep_draw_xync");
			case GDKLC:
				return getDrawInfoHappySql("ibm_rep_draw_gdklc");

			default:
				throw new RuntimeException("不存在的游戏" + game.getName());
		}
	}


	/**
	 * 获取号码的sql语句
	 *
	 * @param tableName 开奖表名
	 * @return 球的开奖信息sql语句
	 */
	private static String getDrawInfoNumberSql(String tableName) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT ");
		for (int i = 1; i <= 10; i++) {
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_NUMBER_RANK[i]).append("|',").append("P").append(i)
					.append("_NUMBER_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_NUMBER_RANK[i]).append("|',").append("P").append(i)
					.append("_SIZE_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_NUMBER_RANK[i]).append("|',").append("P").append(i)
					.append("_SINGLE_),");
		}
		for (int i = 1; i <= 5; i++) {
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_NUMBER_RANK[i]).append("|',").append("P").append(i)
					.append("_DRAGON_),");
		}
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_NUMBER_RANK[0]).append("|',")
				.append("CHAMPION_SUM_NUNMBER_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_NUMBER_RANK[0]).append("|',")
				.append("CHAMPION_SUM_SIZE_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_NUMBER_RANK[0]).append("|',")
				.append("CHAMPION_SUM_SINGLE_),");
		sqlBuilder.append("DRAW_NUMBER_ FROM ").append(tableName)
				.append(" WHERE PERIOD_ = ? AND STATE_ = ? AND TYPE_=? ");
		return sqlBuilder.toString();
	}

	/**
	 * 获取球号的sql语句
	 *
	 * @param tableName 开奖表名
	 * @return 球的开奖信息sql语句
	 */
	private static String getDrawInfoBallSql(String tableName) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT ");
		for (int i = 0; i <= 4; i++) {
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[i]).append("|',").append("P")
					.append(i + 1).append("_NUMBER_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[i]).append("|',").append("P")
					.append(i + 1).append("_SIZE_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[i]).append("|',").append("P")
					.append(i + 1).append("_SINGLE_),");
		}
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[5]).append("|',").append("TOTAL_SIZE_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[5]).append("|',").append("TOTAL_SINGLE_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[6]).append("|',").append("DRAGON_TIGER_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[7]).append("|',").append("TOP_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[8]).append("|',").append("CENTRE_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[9]).append("|',").append("LATER_),");
		sqlBuilder.append("DRAW_NUMBER_ FROM ").append(tableName)
				.append(" WHERE PERIOD_ = ? AND STATE_ = ? AND TYPE_=? ");
		return sqlBuilder.toString();
	}

	private static String getDrawInfoHappySql(String tableName) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT ");
		for (int i = 1; i <= 8; i++) {
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_HAPPY_RANK[i]).append("|',").append("P").append(i)
					.append("_NUMBER_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_HAPPY_RANK[i]).append("|',").append("P").append(i)
					.append("_SIZE_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_HAPPY_RANK[i]).append("|',").append("P").append(i)
					.append("_SINGLE_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_HAPPY_RANK[i]).append("|',").append("P").append(i)
					.append("_TAIL_SIZE_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_HAPPY_RANK[i]).append("|',").append("P").append(i)
					.append("_SUM_SINGLE_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_HAPPY_RANK[i]).append("|',").append("P").append(i)
					.append("_MSW_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_HAPPY_RANK[i]).append("|',").append("P").append(i)
					.append("_POSITION_),");
		}
		for (int i = 1; i <= 4; i++) {
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_HAPPY_RANK[i]).append("|',").append("P").append(i)
					.append("_DRAGON_),");
		}
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_HAPPY_RANK[0]).append("|',")
				.append("TOTAL_SIZE_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_HAPPY_RANK[0]).append("|',")
				.append("TOTAL_SINGLE_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_HAPPY_RANK[0]).append("|',")
				.append("TOTAL_TAIL_SIZE_),");
		sqlBuilder.append("DRAW_NUMBER_ FROM ").append(tableName)
				.append(" WHERE PERIOD_ = ? AND STATE_ = ? AND TYPE_=? ");
		return sqlBuilder.toString();
	}

	//endregion
}