package com.ibm.common.utils.game.tools;
import com.ibm.common.utils.game.GameUtil;

import java.util.List;
import java.util.Map;
/**
 * @Description: 投注合并工具类
 * @Author: Dongming
 * @Date: 2019-09-12 20:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GameMergeTool {

	/**
	 * 放入投注资金
	 *
	 * @param gameCode    游戏编码
	 * @param betInfo     投注资金信息
	 * @param betContents 投注正文
	 */
	public static void putBetInfo(GameUtil.Code gameCode, Map<String, int[][]> betInfo, String[] betContents) {
		switch (gameCode) {
			case XYFT:
			case PK10:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				MergeNumberTool.putBetInfo(gameCode,betInfo, betContents);
				break;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				MergeBallTool.putBetInfo(gameCode,betInfo,betContents);
				break;
			case XYNC:
			case GDKLC:
				MergeHappyTool.putBetInfo(gameCode,betInfo,betContents);
				break;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 合并投注项
	 *
	 * @param gameCode 游戏编码
	 * @param betInfo  投注资金信息
	 */
	public static void mergeInfo(GameUtil.Code gameCode, Map<String, int[][]> betInfo) {
		switch (gameCode) {
			case XYFT:
			case PK10:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				MergeNumberTool.mergeInfo(betInfo);
				break;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				MergeBallTool.mergeInfo(betInfo);
				break;
			case XYNC:
			case GDKLC:
				MergeHappyTool.mergeInfo(betInfo);
				break;

			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	/**
	 * 合并投注项详情信息
	 *
	 * @param gameCode 游戏编码
	 * @param betInfo  投注资金信息
	 * @param betRate  投注比例
	 * @return 注项详情
	 */
	public static List<Object> mergeItem(GameUtil.Code gameCode, Map<String, int[][]> betInfo, double betRate) {
		switch (gameCode) {
			case XYFT:
			case PK10:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return MergeNumberTool.mergeItem(gameCode,betInfo, betRate);
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return MergeBallTool.mergeItem(gameCode,betInfo, betRate);
			case XYNC:
			case GDKLC:
				return MergeHappyTool.mergeItem(gameCode,betInfo, betRate);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}

	}
}
