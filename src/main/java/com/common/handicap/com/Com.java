package com.common.handicap.com;


import com.common.config.handicap.ComBallConfig;
import com.common.config.handicap.ComConfig;
import com.common.config.handicap.ComHappyConfig;
import com.common.config.handicap.ComNumberConfig;
import com.common.handicap.Handicap;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.RandomTool;

import java.util.List;
import java.util.Map;

/**
 * Com盘口
 *
 * @Author: Dongming
 * @Date: 2020-06-10 10:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Com implements Handicap {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.COM;

	private static final Com INSTANCE = new Com();
	private Com() {
	}
	public static Com getInstance() {
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
			case GDKLC:
				return period;
			case XYFT:
			case CQSSC:
				return period.toString().replace("-", "");
			default:
				throw new RuntimeException("错误的游戏类型捕捉" + gameCode);
		}
	}
	@Override public Map<String, String> gameType() {
		return ComConfig.GAME_TYPE;
	}
	@Override public Map<String, String> betType(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return ComNumberConfig.BET_TYPE;
			case CQSSC:
			case JSSSC:
				return ComBallConfig.BET_TYPE;
			case GDKLC:
				return ComHappyConfig.BET_TYPE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public List<String> betRanks(BaseGameUtil.Code gameCode, String betType) {
		return null;
	}
	@Override public Map<String, String> itemCodeMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				return ComNumberConfig.ITEM_CODE;
			case CQSSC:
			case JSSSC:
				return ComBallConfig.ITEM_CODE;
			case GDKLC:
				return ComHappyConfig.ITEM_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Deprecated @Override public Map<String, String> codeItemMap(BaseGameUtil.Code gameCode) {
		return null;
	}
	@Deprecated @Override public Map<String, String> infoItemMap(BaseGameUtil.Code gameCode) {
		return null;
	}
	@Deprecated @Override public Map<String, String> itemLimitMap(BaseGameUtil.Code gameCode) {
		return null;
	}
	@Deprecated @Override public String checkCron() {
		return  RandomTool.getInt(30) + "/30 * 7-3 * * ? *";
	}
}
