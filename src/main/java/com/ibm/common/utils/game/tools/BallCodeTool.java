package com.ibm.common.utils.game.tools;

import com.ibm.common.core.configs.*;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.handicap.config.*;

import java.util.Map;

/**
 * @Description: 球号code工具类
 * @Author: zjj
 * @Date: 2019-09-17 16:06
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class BallCodeTool {

	/**
	 * 获取投注球号map
	 *
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @return
	 */
	public static Map<String, String> getBallCode(HandicapUtil.Code handicapCode, GameUtil.Code gameCode) {
		switch (handicapCode) {
			case IDC:
				return getIdcBallCode(gameCode);
			case SGWIN:
				return getSgWinBallCode(gameCode);
			case HQ:
				return getHqBallCode(gameCode);
			case NCOM1:
				return getNcom1BallCode(gameCode);
			case NCOM2:
				return getNcom2BallCode(gameCode);
			case BW:
				return getBWBallCode(gameCode);
			case COM:
				return getComBallCode(gameCode);
			case NEWCC:
				return getNewCcBallCode(gameCode);
			case NEWMOA:
				return getNewMoaBallCode(gameCode);
			case FC:
				return getFCBallCode(gameCode);
			default:
				throw new RuntimeException("错误的盘口类型捕捉");
		}
	}

	private static Map<String, String> getFCBallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case COUNTRY_10:
				return FCNumberConfig.BALL_CODE;
			case CQSSC:
			case JSSSC:
			case COUNTRY_SSC:
				return FCBallConfig.BALL_CODE;
			case GDKLC:
				return FCHappyConfig.BALL_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getNewMoaBallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return NewMoaNumberConfig.BALL_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
				return NewMoaBallConfig.BALL_CODE;
			case GDKLC:
				return NewMoaHappyConfig.BALL_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}


	private static Map<String, String> getNewCcBallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return NewCcNumberConfig.BALL_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
				return NewCcBallConfig.BALL_CODE;
			case GDKLC:
				return NewCcHappyConfig.BALL_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getComBallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return ComNumberConfig.BALL_CODE;
			case CQSSC:
			case JSSSC:
				return ComBallConfig.BALL_CODE;
			case GDKLC:
				return ComHappyConfig.BALL_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getBWBallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case XYFT:
				return BWNumberConfig.XYFT_BALL_CODE;
			case PK10:
				return BWNumberConfig.PK10_BALL_CODE;
			case CQSSC:
				return BWBallConfig.BALL_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getNcom2BallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case JS10:
			case XYFT:
			case PK10:
				return Ncom2NumberConfig.BALL_CODE;
			case JSSSC:
			case CQSSC:
				return Ncom2BallConfig.BALL_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getNcom1BallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case JS10:
			case XYFT:
			case PK10:
				return Ncom1NumberConfig.BALL_CODE;
			case JSSSC:
			case CQSSC:
				return Ncom1BallConfig.BALL_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getHqBallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return HqNumberConfig.BALL_CODE;
			case CQSSC:
			case JSSSC:
				return HqBallConfig.BALL_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getSgWinBallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return SgWinNumberConfig.BALL_CODE;
			case CQSSC:
			case JSSSC:
				return SgWinBallConfig.BALL_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getIdcBallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return IdcNumberConfig.BALL_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return IdcBallConfig.BALL_CODE;
			case XYNC:
			case GDKLC:
				return IdcHappyConfig.BALL_CODE;

			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取投注详情map
	 *
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @return
	 */
	public static Map<String, String> getBallItem(HandicapUtil.Code handicapCode, GameUtil.Code gameCode) {
		switch (handicapCode) {
			case IDC:
				return getIdcBallItem(gameCode);
			default:
				throw new RuntimeException("错误的盘口类型捕捉");
		}
	}

	private static Map<String, String> getIdcBallItem(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return IdcNumberConfig.BALL_ITEM;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return IdcBallConfig.BALL_ITEM;
			case XYNC:
			case GDKLC:
				return IdcHappyConfig.BALL_ITEM;

			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取球号信息map
	 *
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @return
	 */
	public static Map<String, String> getBallInfoCode(HandicapUtil.Code handicapCode, GameUtil.Code gameCode) {
		switch (handicapCode) {
			case IDC:
				return getIdcBallInfoCode(gameCode);
			case SGWIN:
				return getSgWinBallInfoCode(gameCode);
			case HQ:
				return getHqBallInfoCode(gameCode);
			case NCOM1:
				return getNcom1BallInfoCode(gameCode);
			case NCOM2:
				return getNcom2BallInfoCode(gameCode);
			case BW:
				return getBwBallInfoCode(gameCode);
			default:
				throw new RuntimeException("错误的盘口类型捕捉");
		}
	}

	private static Map<String, String> getBwBallInfoCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case XYFT:
			case PK10:
				return BWNumberConfig.BET_INFO_CODE;
			case CQSSC:
				return BWBallConfig.BET_INFO_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getNcom2BallInfoCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return Ncom2NumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
				return Ncom2BallConfig.BET_INFO_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getNcom1BallInfoCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return Ncom1NumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
				return Ncom1BallConfig.BET_INFO_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getHqBallInfoCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return HqNumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
				return HqBallConfig.BET_INFO_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getSgWinBallInfoCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return SgWinNumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
				return SgWinBallConfig.BET_INFO_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getIdcBallInfoCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return IdcNumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return IdcBallConfig.BET_INFO_CODE;
			case XYNC:
			case GDKLC:
				return IdcHappyConfig.BET_INFO_CODE;

			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取限额code
	 *
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @return
	 */
	public static Map<String, String> getBallLimitCode(HandicapUtil.Code handicapCode, GameUtil.Code gameCode) {
		switch (handicapCode) {
			case IDC:
				return getIdcBallLimitCode(gameCode);
			case SGWIN:
				return getSgWinBallLimitCode(gameCode);
			case HQ:
				return getHqBallLimitCode(gameCode);
			case NCOM1:
				return getNcom1BallLimitCode(gameCode);
			case NCOM2:
				return getNcom2BallLimitCode(gameCode);
			case BW:
				return getBwBallLimitCode(gameCode);
			case COM:
				return getComLimitCode(gameCode);
			case NEWCC:
				return getNewCcBallLimitCode(gameCode);
			case UN:
				return getUNBallLimitCode(gameCode);
			case NEWWS:
				return getNewWsBallLimitCode(gameCode);
			case NEWMOA:
				return getNewMoaBallLimitCode(gameCode);
			case FC:
				return getFCBallLimitCode(gameCode);
			default:
				throw new RuntimeException("错误的盘口类型捕捉");
		}
	}

	private static Map<String, String> getFCBallLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case JS10:
			case COUNTRY_10:
				return FCNumberConfig.BET_LIMIT_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return FCBallConfig.BET_LIMIT_CODE;
			case GDKLC:
				return FCHappyConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getNewMoaBallLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case JS10:
			case SELF_10_5:
				return NewMoaNumberConfig.BET_LIMIT_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return NewMoaBallConfig.BET_LIMIT_CODE;
			case GDKLC:
				return NewMoaHappyConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getNewWsBallLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case JS10:
			case SELF_10_5:
				return NewWsNumberConfig.BET_LIMIT_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return NewWsBallConfig.BET_LIMIT_CODE;
			case GDKLC:
				return NewCcHappyConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getUNBallLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return UNNumberConfig.BET_LIMIT_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return UNBallConfig.BET_LIMIT_CODE;
			case GDKLC:
				// TODO
				return NewCcHappyConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getNewCcBallLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return NewCcNumberConfig.BET_LIMIT_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return NewCcBallConfig.BET_LIMIT_CODE;
			case GDKLC:
				return NewCcHappyConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getComLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return ComNumberConfig.BET_LIMIT_CODE;
			case CQSSC:
			case JSSSC:
				return ComBallConfig.BET_LIMIT_CODE;
			case GDKLC:
				return ComHappyConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getBwBallLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
				return BWNumberConfig.BET_LIMIT_CODE;
			case CQSSC:
				return BWBallConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getNcom2BallLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return Ncom2NumberConfig.BET_LIMIT_CODE;
			case JSSSC:
			case CQSSC:
				return Ncom2BallConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getNcom1BallLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return Ncom1NumberConfig.BET_LIMIT_CODE;
			case JSSSC:
			case CQSSC:
				return Ncom1BallConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getHqBallLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return HqNumberConfig.BET_LIMIT_CODE;
			case JSSSC:
				return HqBallConfig.BET_LIMIT_CODE;
			case CQSSC:
				return HqBallConfig.CQSSC_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}

	}

	private static Map<String, String> getSgWinBallLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case JS10:
			case XYFT:
				return SgWinNumberConfig.BET_LIMIT_CODE;
			case CQSSC:
			case JSSSC:
				return SgWinBallConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static Map<String, String> getIdcBallLimitCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return IdcNumberConfig.BET_LIMIT_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return IdcBallConfig.BET_LIMIT_CODE;
			case XYNC:
			case GDKLC:
				return IdcHappyConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取盘口游戏赔率code
	 *
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param type         投注类型
	 * @return
	 */
	public static String getGameOddsCode(HandicapUtil.Code handicapCode, GameUtil.Code gameCode, String type) {
		switch (handicapCode) {
			case SGWIN:
				return getSgWinOddsCode(gameCode, type);
			case NCOM1:
				return Ncom1Config.GAME_TYPE_CODE.get(type);
			case COM:
				return getComOddsCode(gameCode, type);
			case NEWCC:
				return getNewCcOddsCode(gameCode, type);
			case NEWWS:
				return getNewWsOddsCode(gameCode, type);
			default:
				throw new RuntimeException("错误的盘口类型捕捉");
		}
	}

	private static String getNewWsOddsCode(GameUtil.Code gameCode, String type) {
		switch (gameCode) {
			case PK10:
				return NewWsConfig.PK10_ODDS_CODE.get(type);
			case SELF_10_5:
				return NewWsConfig.SELF_10_5_ODDS_CODE.get(type);
			case CQSSC:
				return NewWsConfig.SSC_ODDS_CODE.get(gameCode.name());
			case GDKLC:
				return NewWsConfig.GDKLC_ODDS_CODE.get(type);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static String getNewCcOddsCode(GameUtil.Code gameCode, String type) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case COUNTRY_10:
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static String getComOddsCode(GameUtil.Code gameCode, String type) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return ComNumberConfig.GAME_ODDS_CODE.get(type);
			case CQSSC:
			case JSSSC:
				return ComBallConfig.GAME_ODDS_CODE.get(type);
			case GDKLC:
				return ComHappyConfig.GAME_ODDS_CODE.get(type);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private static String getSgWinOddsCode(GameUtil.Code gameCode, String type) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return SgWinNumberConfig.GAME_ODDS_CODE.get(type);
			case JSSSC:
			case CQSSC:
				return SgWinBallConfig.GAME_ODDS_CODE.get(type);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取投注项类型集合 (与盘口无关，用来统一单双特号球的限额code)
	 *
	 * @param gameCode 游戏编码
	 * @return 投注项类型集合
	 */
	public static Map<String, String> getItemTypeCodes(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case JS10:
			case XYFT:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return NumberConfig.ITEM_TYPE_CODE;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return BallConfig.ITEM_TYPE_CODE;
			case XYNC:
			case GDKLC:
				return HappyConfig.ITEM_TYPE_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取结果code
	 *
	 * @param handicapCode 盘口编码
	 * @param gameCode     游戏编码
	 * @return
	 */
	public static Map<String, String> getResultCode(HandicapUtil.Code handicapCode, GameUtil.Code gameCode) {
		switch (handicapCode) {
			case HQ:
				return getHqResultCode(gameCode);
			default:
				throw new RuntimeException("错误的盘口类型捕捉");
		}
	}

	private static Map<String, String> getHqResultCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return HqNumberConfig.BET_RESULT_CODE;
			case JSSSC:
			case CQSSC:
				return HqBallConfig.BET_RESULT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取游戏类型
	 *
	 * @param handicapCode 盘口code
	 * @param gameCode     游戏code
	 * @param betType      投注类型
	 * @return
	 */
	public static String getGameType(HandicapUtil.Code handicapCode, GameUtil.Code gameCode, String betType) {
		switch (handicapCode) {
			case IDC:
				return IdcConfig.BET_CODE.get(gameCode.name());
			case COM:
				return getComGameType(gameCode, betType);
			default:
				throw new RuntimeException("错误的盘口类型捕捉");
		}
	}

	private static String getComGameType(GameUtil.Code gameCode, String betType) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return ComConfig.BET_CODE.get(gameCode.name()).concat("_").concat(ComNumberConfig.GAME_TYPE_CODE.get(betType));
			case JSSSC:
			case CQSSC:
				return ComConfig.BET_CODE.get(gameCode.name()).concat("_").concat(ComBallConfig.GAME_TYPE_CODE.get(betType));
			case GDKLC:
				return ComHappyConfig.BET_CODE.get(gameCode.name()).concat("_").concat(ComHappyConfig.GAME_TYPE_CODE.get(betType));
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	public static String getNewCcGameNo(GameUtil.Code gameCode, String handicapType) {
		if ("A".equals(handicapType)) {
			return NewCcConfig.A_GAME_BALL_CODE.get(gameCode.name());
		} else {
			return NewCcConfig.B_GAME_BALL_CODE.get(gameCode.name());
		}
	}

	/**
	 * 获取投注项信息
	 *
	 * @param gameCode 游戏code
	 * @return betInfo
	 */
	public static Map<String, String> getNewMoaBallInfo(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return NewMoaNumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
				return NewMoaBallConfig.BET_INFO_CODE;
			case GDKLC:
				return NewMoaHappyConfig.BET_INFO_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

}
