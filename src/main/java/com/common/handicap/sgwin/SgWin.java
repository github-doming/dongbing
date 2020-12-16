package com.common.handicap.sgwin;


import com.common.config.handicap.SgWinBallConfig;
import com.common.config.handicap.SgWinConfig;
import com.common.config.handicap.SgWinNumberConfig;
import com.common.handicap.Handicap;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.RandomTool;

import java.util.List;
import java.util.Map;

/**
 * SgWin 盘口
 *
 * @Author: Dongming
 * @Date: 2020-06-09 14:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SgWin implements Handicap {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.SGWIN;

	private static final SgWin INSTANCE = new SgWin();
	private SgWin() {
	}
	public static SgWin getInstance() {
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
		return SgWinConfig.GAME_TYPE;
	}

	@Override public Map<String, String> itemCodeMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return SgWinNumberConfig.ITEM_CODE;
			case CQSSC:
			case JSSSC:
				return SgWinBallConfig.ITEM_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public Map<String, String> codeItemMap(BaseGameUtil.Code gameCode) {
		return null;
	}
	@Override public Map<String, String> infoItemMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return SgWinNumberConfig.INFO_ITEM;
			case CQSSC:
			case JSSSC:
				return SgWinBallConfig.INFO_ITEM;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public Map<String, String> itemLimitMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return SgWinNumberConfig.ITEM_LIMIT;
			case CQSSC:
			case JSSSC:
				return SgWinBallConfig.ITEM_LIMIT;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public String checkCron() {
		return  RandomTool.getInt(30) + "/30 * 7-3 * * ? *";
	}

	@Override public Map<String, String> betType(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return SgWinNumberConfig.BET_TYPE;
			case CQSSC:
			case JSSSC:
				return SgWinBallConfig.BET_TYPE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Deprecated @Override public List<String> betRanks(BaseGameUtil.Code gameCode, String betType) {
		return null;
	}
}
