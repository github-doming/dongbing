package com.common.tools;

import org.doming.core.tools.ContainerTool;

/**
 * @Description: 数组复制工具类
 * @Author: null
 * @Date: 2020-09-26 10:13
 */
public class ArrayCopyTool {

	/**
	 * 开奖数组复制
	 * 按开奖顺序进行复制
	 *
	 * @param hotAndCold        冷热排名信息
	 * @param lotteryHotAndCold 历史冷热排名信息
	 * @param recentDraw        开奖信息
	 */
	protected static void arrayCopy(String[][] hotAndCold, String[][] lotteryHotAndCold, String[] recentDraw) {
		String[] newRank;
		String[] hotColdNumber;
		for (int i = 0; i < lotteryHotAndCold.length; i++) {
			newRank = new String[lotteryHotAndCold[0].length];
			String pNumber = recentDraw[i];
			newRank[0] = pNumber;
			hotColdNumber = lotteryHotAndCold[i];
			int h = 1;
			for (int j = 0; j < hotColdNumber.length; j++) {
				if (pNumber.equals(hotColdNumber[j])) {
					h--;
					continue;
				}
				newRank[j + h] = hotColdNumber[j];
			}
			hotAndCold[i] = newRank;
		}
	}

	/**
	 * 数组复制
	 * 号码上次出现间隔
	 * @param interval				间隔信息
	 * @param lotteryLastInterval	历史间隔信息
	 * @param recentDraw				开奖号码
	 * @param dif						起始号码
	 */
	protected static void rankArrayCopy(int[][] interval, int[][] lotteryLastInterval, String[] recentDraw, int dif) {
		int[] newRank;
		for (int i = 0; i < lotteryLastInterval.length; i++) {
			newRank = lotteryLastInterval[i];
			for (int j = 0, h = newRank.length; j < h; j++) {
				newRank[j]++;
			}
			String pNumber = recentDraw[i];
			newRank[Integer.parseInt(pNumber) - dif] = 1;

			interval[i] = newRank;
		}
	}

	/**
	 * 开奖数组复制
	 *
	 *
	 * @param hotAndCold        冷热排名信息
	 * @param lotteryHotAndCold 历史冷热排名信息
	 * @param recentDraw        开奖信息
	 */
	protected static void arrayCopyLocation(String[][] hotAndCold, String[][] lotteryHotAndCold, String[] recentDraw) {
		String[] newRank;
		String[] hotColdNumber;
		for (int i = 0; i < lotteryHotAndCold.length; i++) {
			newRank = new String[lotteryHotAndCold[0].length];
			String pNumber = String.valueOf(ContainerTool.findIndex(recentDraw, String.valueOf(i + 1)) + 1);
			newRank[0] = pNumber;
			hotColdNumber = lotteryHotAndCold[i];
			int h = 1;
			for (int j = 0; j < hotColdNumber.length; j++) {
				if (pNumber.equals(hotColdNumber[j])) {
					h--;
					continue;
				}
				newRank[j + h] = hotColdNumber[j];
			}
			hotAndCold[i] = newRank;
		}
	}

	/**
	 * 开奖数组复制
	 *
	 * @param hotAndCold        冷热排名信息
	 * @param lotteryHotAndCold 历史冷热排名信息
	 * @param recentDraw        开奖信息
	 */
	protected static void arrayCopyNumber(String[][] hotAndCold, String[][] lotteryHotAndCold, String[] recentDraw,String[] valiNumber) {
		String[] newRank;
		String[] hotColdNumber;
		for (int i = 0; i < lotteryHotAndCold.length; i++) {
			newRank = new String[lotteryHotAndCold[0].length];
			int index = ContainerTool.findIndex(valiNumber, String.valueOf(i + 1));
			String pNumber = recentDraw[index];
			newRank[0] = pNumber;
			hotColdNumber = lotteryHotAndCold[i];
			int h = 1;
			for (int j = 0; j < hotColdNumber.length; j++) {
				if (pNumber.equals(hotColdNumber[j])) {
					h--;
					continue;
				}
				newRank[j + h] = hotColdNumber[j];
			}
			hotAndCold[i] = newRank;
		}
	}


}
