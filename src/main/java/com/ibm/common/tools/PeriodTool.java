package com.ibm.common.tools;

import com.ibm.common.tools.period.PeriodHandicapTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.*;
import com.ibm.common.utils.game.base.PeriodOperation;

import java.text.ParseException;

/**
 * @Description: 期数工具类
 * @Author: Dongming
 * @Date: 2019-01-19 10:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class PeriodTool {

	//region 工具方法

	/**
	 * 比对期数
	 *
	 * @param period     原期数
	 * @param valiPeriod 校对期数
	 * @return 相同true
	 */
	public static boolean equals(Object period, Object valiPeriod) {
		return (Integer.parseInt(period.toString()) - Integer.parseInt(valiPeriod.toString())) == 0;
	}

	/**
	 * 获取期数操作类
	 *
	 * @param handicapCode 盘口编码
	 * @param gameCode     游戏编码
	 * @param <T>          期数泛型
	 * @return 每个盘口期数操作类
	 */
	public static <T> PeriodOperation<T> getPeriodOperation(GameUtil.Code gameCode, HandicapUtil.Code handicapCode) {
		switch (gameCode) {
			case PK10:
				return (PeriodOperation<T>) Pk10.pk10();
			case XYFT:
				return (PeriodOperation<T>) Xyft.xyft();
			case CQSSC:
				return (PeriodOperation<T>) Cqssc.cqssc();
			case XYNC:
				return (PeriodOperation<T>) Xync.xync();
			case GDKLC:
				return (PeriodOperation<T>) Gdklc.gdklc();
			case GXKLSF:
				return PeriodHandicapTool.getGxklsfPeriodOperation(handicapCode);
			case JS10:
				return PeriodHandicapTool.getJs10PeriodOperation(handicapCode);
			case JSSSC:
				return PeriodHandicapTool.getJssscPeriodOperation(handicapCode);
			case SELF_10_2:
				return PeriodHandicapTool.getSelf102PeriodOperation(handicapCode);
			case SELF_10_5:
				return PeriodHandicapTool.getSelf105PeriodOperation(handicapCode);
			case SELF_SSC_5:
				return PeriodHandicapTool.getSelfSsc5PeriodOperation(handicapCode);
			case COUNTRY_10:
				return PeriodHandicapTool.getCountry10PeriodOperation(handicapCode);
			case COUNTRY_SSC:
				return PeriodHandicapTool.getCountrySscPeriodOperation(handicapCode);
			default:
				throw new RuntimeException("不存在的游戏" + gameCode.getName());
		}
	}


	/**
	 * 期数时间间隔
	 *
	 * @param gameCode 游戏编码
	 * @return 时间间隔
	 */
	public static long getInterval(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case CQSSC:
			case GDKLC:
			case XYNC:
			case GXKLSF:
				return PeriodConfig.PERIOD_TIME_20;
			case XYFT:
			case COUNTRY_10:
			case COUNTRY_SSC:
			case SELF_SSC_5:
			case SELF_10_5:
				return PeriodConfig.PERIOD_TIME_5;
			case SELF_10_2:
				return PeriodConfig.PERIOD_TIME_2;
			case JS10:
			case JSSSC:
				return PeriodConfig.PERIOD_TIME_1;
			default:
				throw new RuntimeException("获取时间异常");
		}
	}

	//region 期数

	/**
	 * 根据 游戏 获取开奖期数
	 *
	 * @param game 游戏
	 * @return 当前期数
	 */
	public static <T> T findLotteryPeriod(GameUtil.Code game, HandicapUtil.Code handicapCode) {
		PeriodOperation<T> operation = getPeriodOperation(game, handicapCode);
		return operation.findLotteryPeriod();
	}

	/**
	 * 根据游戏获取期数
	 *
	 * @param game         游戏
	 * @param handicapCode 盘口code
	 * @return 期数
	 */
	public static <T> T findPeriod(GameUtil.Code game, HandicapUtil.Code handicapCode) {
		PeriodOperation<T> operation = getPeriodOperation(game, handicapCode);
		return operation.findPeriod();
	}

	/**
	 * 获取期数列表
	 *
	 * @param handicapCode 盘口编码
	 * @param gameCode     游戏编码
	 * @param size         列表大小
	 * @return 期数列表
	 */
	public static <T> T[] listPeriod(HandicapUtil.Code handicapCode, GameUtil.Code gameCode, int size)
			throws ParseException {
		PeriodOperation<T> operation = getPeriodOperation(gameCode, handicapCode);
		return operation.listPeriod(size);
	}

	//endregion

	//region 时间

	/**
	 * 根据游戏code获取下一期开奖时间戳
	 *
	 * @param handicapCode 盘口编码
	 * @param gameCode     游戏编码
	 * @return 当前期数
	 * @throws Exception code异常
	 */
	public static long getDrawTime(GameUtil.Code gameCode, HandicapUtil.Code handicapCode) throws Exception {
		PeriodOperation<Object> operation = getPeriodOperation(gameCode, handicapCode);
		return operation.getDrawTime();
	}

	/**
	 * 获取开奖时间
	 *
	 * @param handicapCode 盘口编码
	 * @param gameCode     游戏编码
	 * @return 开奖时间
	 */
	public static long getLotteryDrawTime(GameUtil.Code gameCode, HandicapUtil.Code handicapCode) throws Exception {
		PeriodOperation<Object> operation = getPeriodOperation(gameCode, handicapCode);
		return operation.getLotteryDrawTime();
	}

	/**
	 * 获取开奖时间
	 *
	 * @param handicapCode 盘口编码
	 * @param gameCode     游戏编码
	 * @param period       期数
	 * @return 开奖时间
	 */
	public static long getDrawTime(GameUtil.Code gameCode, Object period, HandicapUtil.Code handicapCode)
			throws ParseException {
		PeriodOperation<Object> operation = getPeriodOperation(gameCode, handicapCode);
		return operation.getDrawTime(period);
	}
	//endregion
	//endregion
}