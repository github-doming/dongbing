package com.ibm.common.utils;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.tools.PeriodTool;
import org.doming.core.tools.DateTool;

import java.util.Date;

/**
 * @Description: 期数工具类
 * @Author: null
 * @Date: 2019-09-19 11:16
 * @Version: v1.0
 */
public class PeriodUtil extends PeriodTool {
	/**
	 * 获取盘口游戏期数字符串
	 *
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param period       期数
	 * @return 期数字符串
	 */
	public static String getHandicapGamePeriod(HandicapUtil.Code handicapCode, GameUtil.Code gameCode, Object period) {
		return getPeriod(handicapCode, gameCode, period).toString();
	}

	/**
	 * 获取盘口游戏期数字符串
	 *
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param period       期数
	 * @return 期数字符串
	 */
	public static Object getPeriod(HandicapUtil.Code handicapCode, GameUtil.Code gameCode, Object period) {
		switch (handicapCode) {
			case NCOM2:
				return period;
			case IDC:
			case NEWMOA:
				return getIdcPeriod(gameCode, period);
			case SGWIN:
			case HQ:
            case COM:
			case UN:
			case FC:
				return getPeriod(gameCode, period);
			case NCOM1:
				return getNcom1Period(gameCode,period);
			case NEWWS:
				return getNewWsPeriod(gameCode,period);
			default:
				throw new RuntimeException("错误的盘口类型捕捉" + handicapCode);
		}
	}
	private static Object getNewWsPeriod(GameUtil.Code gameCode, Object period) {
		switch (gameCode) {
			case PK10:
			case SELF_10_5:
				return period;
			case CQSSC:
			case GDKLC:
				return period.toString().replace("-", "");
			default:
				throw new RuntimeException("错误的游戏类型捕捉" + gameCode);
		}
	}

	private static Object getNcom1Period(GameUtil.Code gameCode, Object period) {
		switch (gameCode) {
			case PK10:
			case JS10:
			case JSSSC:
				return period;
			case CQSSC:
			case XYFT:
			case XYNC:
				return period.toString().replace("-", "");
			default:
				throw new RuntimeException("错误的游戏类型捕捉" + gameCode);
		}
	}

	/**
	 * 获取IDC的期数
	 *
	 * @param gameCode 游戏code
	 * @param period   期数
	 * @return 期数字符串
	 */
	private static Object getIdcPeriod(GameUtil.Code gameCode, Object period) {
		switch (gameCode) {
			case PK10:
			case JS10:
			case JSSSC:
			case CQSSC:
			case XYNC:
			case GDKLC:
			case SELF_10_2:
			case COUNTRY_10:
			case COUNTRY_SSC:
			case SELF_SSC_5:
				return period;
			case XYFT:
				return period.toString().replace("-", "");
			default:
				throw new RuntimeException("错误的游戏类型捕捉" + gameCode);
		}
	}

	/**
	 * 获取通用的期数
	 *
	 * @param gameCode 游戏code
	 * @param period   期数
	 * @return 期数字符串
	 */
	private static Object getPeriod(GameUtil.Code gameCode, Object period) {
		switch (gameCode) {
			case PK10:
			case JS10:
			case JSSSC:
			case COUNTRY_10:
			case COUNTRY_SSC:
				return period;
			case XYFT:
			case CQSSC:
			case XYNC:
			case GDKLC:
				return period.toString().replace("-", "");
			default:
				throw new RuntimeException("错误的游戏类型捕捉" + gameCode);
		}
	}

	/**
	 * 获取盘口游戏时间字符串
	 *
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param period       期数
	 * @return 时间字符串
	 */
	public static String getHandicapGameDateStr(HandicapUtil.Code handicapCode, GameUtil.Code gameCode, Object period) {
		return getDate(handicapCode, gameCode, period);
	}

	/**
	 * 获取日期
	 *
	 * @param handicap 获取的盘口
	 * @param game     获取的游戏
	 * @param period   获取的期数
	 * @return 日期
	 */
	public static String getDate(HandicapUtil.Code handicap, GameUtil.Code game, Object period) {
        switch (handicap) {
            case SGWIN:
            case COM:
			case NEWCC:
			case UN:
			case NEWWS:
			case FC:
                return getDate(game, period);
            default:
                throw new RuntimeException("错误的盘口类型捕捉" + handicap);
        }

	}
	/**
	 * 获取日期
	 *
	 * @param game   获取的游戏
	 * @param period 获取的期数
	 * @return 日期
	 */
	private static String getDate(GameUtil.Code game, Object period) {
		switch (game) {
			case PK10:
			case CQSSC:
			case JS10:
			case JSSSC:
			case SELF_10_2:
			case SELF_10_5:
			case SELF_SSC_5:
			case COUNTRY_SSC:
			case COUNTRY_10:
				return DateTool.getDate(new Date());
			case XYFT:
			case XYNC:
			case GDKLC:
				String[] str = period.toString().split("-");
				StringBuilder stringBuilder = new StringBuilder(str[0]);
				stringBuilder.insert(6, "-");
				stringBuilder.insert(4, "-");
				return stringBuilder.toString();
			default:
				throw new RuntimeException("错误的游戏类型捕捉" + game);
		}
	}
}
