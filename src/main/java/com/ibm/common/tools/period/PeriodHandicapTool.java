package com.ibm.common.tools.period;

import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.base.PeriodOperation;
import com.ibm.common.utils.game.hq.Js10Hq;
import com.ibm.common.utils.game.hq.JssscHq;
import com.ibm.common.utils.game.idc.*;
import com.ibm.common.utils.game.newws.Ft5;

/**
 * @Description: 期数盘口工具类
 * @Author: Dongming
 * @Date: 2019-10-17 18:08
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PeriodHandicapTool {

	//region 期数操作类

	/**
	 * 获取极速赛车期数操作类
	 * @param handicapCode 盘口编码
	 * @param <T> 期数泛型
	 * @return 每个盘口期数操作类
	 */
	public static<T> PeriodOperation<T> getJs10PeriodOperation(HandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
			case SGWIN:
			case NCOM1:
			case NEWCC:
			case UN:
			case NEWMOA:
			case FC:
				return (PeriodOperation<T>) Js10Idc.js10idc();
			case HQ:
				return (PeriodOperation<T>) Js10Hq.js10hq();
			case NCOM2:
			default:
				throw new RuntimeException("尚未完成的盘口");
		}
	}
	/**
	 * 获取极速时时彩期数操作类
	 * @param handicapCode 盘口编码
	 * @param <T> 期数泛型
	 * @return 每个盘口期数操作类
	 */
	public static <T> PeriodOperation<T> getJssscPeriodOperation(HandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
			case SGWIN:
			case NCOM1:
			case NEWCC:
			case UN:
			case NEWMOA:
			case FC:
				return (PeriodOperation<T>) JssscIdc.jssscIdc();
			case HQ:
				return (PeriodOperation<T>) JssscHq.jssscHq();
			case NCOM2:
			default:
				throw new RuntimeException("尚未完成的盘口");
		}
	}
	/**
	 * 获取广西快乐十分期数操作类
	 * @param handicapCode 盘口编码
	 * @param <T> 期数泛型
	 * @return 每个盘口期数操作类
	 */
	public static <T> PeriodOperation<T> getGxklsfPeriodOperation(HandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
				return (PeriodOperation<T>) Gxklsf.gxklsf();
			case SGWIN:
			case NCOM1:
			case HQ:
			case NCOM2:
			default:
				throw new RuntimeException("尚未完成的盘口");
		}
	}
	/**
	 * 获取自有2分赛车期数操作类
	 * @param handicapCode 盘口编码
	 * @param <T> 期数泛型
	 * @return 每个盘口期数操作类
	 */
	public static <T> PeriodOperation<T> getSelf102PeriodOperation(HandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
				return (PeriodOperation<T>) Eps2.eps2();
			case SGWIN:
			case NCOM1:
			case HQ:
			case NCOM2:
			default:
				throw new RuntimeException("尚未完成的盘口");
		}
	}

	/**
	 * 获取自有2分赛车期数操作类
	 * @param handicapCode 盘口编码
	 * @param <T> 期数泛型
	 * @return 每个盘口期数操作类
	 */
	public static <T> PeriodOperation<T> getSelf105PeriodOperation(HandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case NEWWS:
				return (PeriodOperation<T>) Ft5.ft5();
			default:
				throw new RuntimeException("尚未完成的盘口");
		}
	}

	/**
	 * 获取自有5分时时彩期数操作类
	 * @param handicapCode 盘口编码
	 * @param <T> 期数泛型
	 * @return 每个盘口期数操作类
	 */
	public static <T> PeriodOperation<T> getSelfSsc5PeriodOperation(HandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
			case NEWCC:
				return (PeriodOperation<T>) SuperFast5.superFast5();
			case SGWIN:
			case NCOM1:
			case HQ:
			case NCOM2:
			default:
				throw new RuntimeException("尚未完成的盘口");
		}
	}

	/**
	 * 获取国家赛车赛车期数操作类
	 * @param handicapCode 盘口编码
	 * @param <T> 期数泛型
	 * @return 每个盘口期数操作类
	 */
	public static <T> PeriodOperation<T> getCountry10PeriodOperation(HandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
			case NEWCC:
			case UN:
			case NEWMOA:
			case FC:
				return (PeriodOperation<T>) AustraliaLucky10.australiaLucky10();
			case SGWIN:
			case NCOM1:
			case HQ:
			case NCOM2:
			default:
				throw new RuntimeException("尚未完成的盘口");
		}
	}

	/**
	 * 获取国家赛车时时彩期数操作类
	 * @param handicapCode 盘口编码
	 * @param <T> 期数泛型
	 * @return 每个盘口期数操作类
	 */
	public static <T> PeriodOperation<T> getCountrySscPeriodOperation(HandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
			case NEWCC:
			case UN:
			case NEWMOA:
			case FC:
				return (PeriodOperation<T>) AustraliaLucky5.australiaLucky5();
			case SGWIN:
			case NCOM1:
			case HQ:
			case NCOM2:
			default:
				throw new RuntimeException("尚未完成的盘口");
		}
	}


	//endregion

	/**
	 * 获取极速赛车开奖期数
	 * @param handicapCode 盘口编码
	 *
	 * @return 开奖期数
	 */
	public static <T> T findLotteryJs10Period(HandicapUtil.Code handicapCode) {
		PeriodOperation<T>  operation = getJs10PeriodOperation(handicapCode);
		return operation.findLotteryPeriod();
	}

	/**
	 * 获取极速时时彩开奖期数
	 * @param handicapCode 盘口编码
	 *
	 * @return 开奖期数
	 */
	public static <T> T findLotteryJssscPeriod(HandicapUtil.Code handicapCode) {
		PeriodOperation<T>  operation = getJssscPeriodOperation(handicapCode);
		return operation.findLotteryPeriod();
	}

	/**
	 * 获取自有赛车两分彩开奖期数
	 * @param handicapCode 盘口编码
	 *
	 * @return 开奖期数
	 */
	public static  <T> T findLotterySelf102Period(HandicapUtil.Code handicapCode) {
		PeriodOperation<T>  operation = getSelf102PeriodOperation(handicapCode);
		return operation.findLotteryPeriod();
	}

	/**
	 * 获取自有赛车五分彩开奖期数
	 * @param handicapCode 盘口编码
	 *
	 * @return 开奖期数
	 */
	public static  <T> T findLotterySelf105Period(HandicapUtil.Code handicapCode) {
		PeriodOperation<T>  operation = getSelf105PeriodOperation(handicapCode);
		return operation.findLotteryPeriod();
	}

	/**
	 * 获取自有赛车两分彩开奖期数
	 * @param handicapCode 盘口编码
	 *
	 * @return 开奖期数
	 */
	public static  <T> T findLotterySelfSsc5Period(HandicapUtil.Code handicapCode) {
		PeriodOperation<T>  operation = getSelfSsc5PeriodOperation(handicapCode);
		return operation.findLotteryPeriod();
	}

	/**
	 * 获取国家赛车开奖期数
	 * @param handicapCode 盘口编码
	 *
	 * @return 开奖期数
	 */
	public static <T> T findLotteryCountry10Period(HandicapUtil.Code handicapCode) {
		PeriodOperation<T>  operation = getCountry10PeriodOperation(handicapCode);
		return operation.findLotteryPeriod();
	}

	/**
	 * 获取国家时时彩开奖期数
	 * @param handicapCode 盘口编码
	 *
	 * @return 开奖期数
	 */
	public static  <T> T  findLotteryCountrySscPeriod(HandicapUtil.Code handicapCode) {
		PeriodOperation<T>  operation = getCountrySscPeriodOperation(handicapCode);
		return operation.findLotteryPeriod();
	}
}
