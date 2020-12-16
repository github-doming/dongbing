package com.common.handicap.fc;

import com.common.config.handicap.FcBallConfig;
import com.common.config.handicap.FcConfig;
import com.common.config.handicap.FcHappyConfig;
import com.common.config.handicap.FcNumberConfig;
import com.common.handicap.Handicap;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-07-13 16:23
 * @Version: v1.0
 */
public class Fc implements Handicap {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.FC;

	private static final Fc INSTANCE = new Fc();
	private Fc() {
	}

	public static Fc getInstance() {
		return INSTANCE;
	}

	@Override
	public Object period(BaseGameUtil.Code gameCode) {
		return gameCode.getGameFactory().period(HANDICAP_CODE).findPeriod();
	}

	@Override
	public Object handicapPeriod(BaseGameUtil.Code gameCode) {
		return handicapPeriod(gameCode, period(gameCode).toString());
	}

	@Override
	public Object handicapPeriod(BaseGameUtil.Code gameCode, Object period) {
		return null;
	}

	@Override
	public Map<String, String> gameType() {
		return FcConfig.GAME_TYPE;
	}

	@Override
	public Map<String, String> betType(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case JS10:
			case XYFT:
			case COUNTRY_10:
				return FcNumberConfig.GAME_TYPE_CODE;
			case CQSSC:
			case JSSSC:
			case COUNTRY_SSC:
				return FcBallConfig.GAME_TYPE_CODE;
			case GDKLC:
				return FcHappyConfig.GAME_TYPE_CODE;
			default:
				return null;
		}
	}

	@Override
	public List<String> betRanks(BaseGameUtil.Code gameCode, String betType) {
		return null;
	}

	@Override
	public Map<String, String> itemCodeMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case COUNTRY_10:
				return FcNumberConfig.ITEM_CODE;
			case CQSSC:
			case JSSSC:
			case COUNTRY_SSC:
				return FcBallConfig.ITEM_CODE;
			case GDKLC:
				return FcHappyConfig.ITEM_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	@Override
	public Map<String, String> codeItemMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return FcNumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
			case COUNTRY_SSC:
				return FcBallConfig.BET_INFO_CODE;
			case GDKLC:
				return FcHappyConfig.BET_INFO_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	@Override
	public Map<String, String> infoItemMap(BaseGameUtil.Code gameCode) {

		return null;
	}

	@Override
	public Map<String, String> itemLimitMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case JS10:
			case COUNTRY_10:
				return FcNumberConfig.BET_LIMIT_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return FcBallConfig.BET_LIMIT_CODE;
			case GDKLC:
				return FcHappyConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	@Override
	public String checkCron() {
		return null;
	}
}
