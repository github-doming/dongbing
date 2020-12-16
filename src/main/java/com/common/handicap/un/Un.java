package com.common.handicap.un;


import com.common.config.handicap.UnBallConfig;
import com.common.config.handicap.UnConfig;
import com.common.config.handicap.UnNumberConfig;
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
public class Un implements Handicap {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.UN;

	private static final Un INSTANCE = new Un();

	private Un() {
	}

	public static Un getInstance() {
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
			case GDKLC:
				return period;
			case CQSSC:
			case XYFT:
				return period.toString().replace("-", "");
			default:
				throw new RuntimeException("错误的游戏类型捕捉" + gameCode);
		}
	}

	@Override
	public Map<String, String> gameType() {
		return UnConfig.GAME_TYPE;
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
		return null;
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
