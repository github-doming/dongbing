package com.ibs.common.tools;

import com.alibaba.fastjson.JSONArray;
import com.ibs.common.utils.GameUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 游戏工具类
 * @Author: null
 * @Date: 2020-06-11 17:13
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

	private static final String[] BALL_TYPE = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "大", "小", "单", "双",
			"龙", "虎", "和", "豹子", "顺子", "对子", "半顺", "杂六"};

	private static final String[] KLC_RANK = {"第一球", "第二球", "第三球", "第四球", "第五球", "第六球", "第七球", "第八球", "总和"};

	private static final String[] KLC_TYPE = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
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
	 * 获取号码类游戏 数字排名
	 *
	 * @param rankStr 字符类型
	 * @return 数字排名
	 */
	private static Integer numberRank(String rankStr) {
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
	private static Integer ballRank(String rankStr) {
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


}
