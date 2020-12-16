package com.common.handicap.newws;


import com.common.config.handicap.*;
import com.common.handicap.Handicap;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.RandomTool;

import java.util.List;
import java.util.Map;

/**
 * un盘口
 *
 * @Author: Dongming
 * @Date: 2020-05-12 14:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class NewWs implements Handicap {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.NEWWS;

	private static final NewWs INSTANCE = new NewWs();

	private NewWs() {
	}

	public static NewWs getInstance() {
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

	@Override
	public Map<String, String> gameType() {
		return NewWsConfig.GAME_TYPE;
	}

	@Override
	public Map<String, String> itemCodeMap(BaseGameUtil.Code gameCode) {
		return null;
	}

	@Override
	public Map<String, String> codeItemMap(BaseGameUtil.Code gameCode) {
		return null;
	}

	@Override
	public Map<String, String> infoItemMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return NewWsNumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
				return NewWsBallConfig.BET_INFO_CODE;
			case GDKLC:
				return NewWsHappyConfig.BET_INFO_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	@Override
	public Map<String, String> itemLimitMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return UnNumberConfig.BET_LIMIT_CODE;
			case CQSSC:
			case JSSSC:
				return UnBallConfig.BET_LIMIT_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	@Override
	public String checkCron() {
		return RandomTool.getInt(30) + "/30 * 8-3 * * ? *";
	}

	@Deprecated
	@Override
	public Map<String, String> betType(BaseGameUtil.Code gameCode) {
		return null;
	}

	@Deprecated
	@Override
	public List<String> betRanks(BaseGameUtil.Code gameCode, String betType) {
		return null;
	}

}
