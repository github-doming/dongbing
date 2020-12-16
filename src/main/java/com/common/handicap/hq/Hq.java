package com.common.handicap.hq;

import com.common.config.handicap.HqBallConfig;
import com.common.config.handicap.HqConfig;
import com.common.config.handicap.HqNumberConfig;
import com.common.handicap.Handicap;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.RandomTool;

import java.util.List;
import java.util.Map;

/**
 * HQ盘口
 *
 * @Author: Dongming
 * @Date: 2020-05-16 16:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Hq implements Handicap {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.HQ;

	private static final Hq INSTANCE = new Hq();
	private Hq() {
	}
	public static Hq getInstance() {
		return INSTANCE;
	}

	@Override public Object period(BaseGameUtil.Code gameCode) {
		return gameCode.getGameFactory().period(HANDICAP_CODE).findPeriod();
	}
	@Override public Object handicapPeriod(BaseGameUtil.Code gameCode) {
		return handicapPeriod(gameCode, period(gameCode).toString());
	}
	@Override public Object handicapPeriod(BaseGameUtil.Code gameCode, Object period) {
		switch (gameCode) {
			case PK10:
			case JS10:
			case JSSSC:
				return period;
			case XYFT:
			case CQSSC:
			case GDKLC:
				return period.toString().replace("-", "");
			default:
				throw new RuntimeException("错误的游戏类型捕捉" + gameCode);
		}
	}
	@Override public Map<String, String> gameType() {
		return HqConfig.GAME_TYPE;
	}

	@Override public Map<String, String> betType(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return HqNumberConfig.BET_TYPE;
			case CQSSC:
			case JSSSC:
				return HqBallConfig.BET_TYPE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public List<String> betRanks(BaseGameUtil.Code gameCode, String betType) {
		switch (gameCode) {
			case CQSSC:
				return HqConfig.CQSSC_CODE;
			case JSSSC:
				return HqConfig.JSSSC_CODE;
			case PK10:
				switch (betType) {
					case "doubleSides":
						return HqConfig.PK10_DOUBLE_SIDES;
					case "ballNO":
						return HqConfig.PK10_BALL_NO;
					case "sumDT":
						return HqConfig.PK10_SUM_DT;
					default:
						throw new RuntimeException("错误的投注类型捕捉");
				}
			case XYFT:
				switch (betType) {
					case "doubleSides":
						return HqConfig.XYFT_DOUBLE_SIDES;
					case "ballNO":
						return HqConfig.XYFT_BALL_NO;
					case "sumDT":
						return HqConfig.XYFT_SUM_DT;
					default:
						throw new RuntimeException("错误的投注类型捕捉");
				}
			case JS10:
				switch (betType) {
					case "doubleSides":
						return HqConfig.JS10_DOUBLE_SIDES;
					case "ballNO":
						return HqConfig.JS10_BALL_NO;
					case "sumDT":
						return HqConfig.JS10_SUM_DT;
					default:
						throw new RuntimeException("错误的投注类型捕捉");
				}
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}

	}
	@Override public Map<String, String> itemCodeMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return HqNumberConfig.ITEM_CODE;
			case CQSSC:
			case JSSSC:
				return HqBallConfig.ITEM_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public Map<String, String> codeItemMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return HqNumberConfig.CODE_ITEM;
			case CQSSC:
			case JSSSC:
				return HqBallConfig.CODE_ITEM;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public Map<String, String> infoItemMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return HqNumberConfig.RESULT_ITEM;
			case JSSSC:
			case CQSSC:
				return HqBallConfig.RESULT_ITEM;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public Map<String, String> itemLimitMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return HqNumberConfig.ITEM_LIMIT;
			case JSSSC:
			case CQSSC:
				return HqBallConfig.ITEM_LIMIT;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	@Override public String checkCron() {
		return RandomTool.getInt(10) + "/10 * 7-3 * * ? *";
	}
}
