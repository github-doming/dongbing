package com.common.handicap.newcc;


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
public class NewCc implements Handicap {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.NEWCC;

	private static final NewCc INSTANCE = new NewCc();

	private NewCc() {
	}

	public static NewCc getInstance() {
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
		return null;
	}

	@Override
	public Map<String, String> itemCodeMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return NewCcNumberConfig.ITEM_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
				return NewCcBallConfig.ITEM_CODE;
			case GDKLC:
				return NewCcHappyConfig.ITEM_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
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
			case COUNTRY_10:
				return NewCcNumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
				return NewCcBallConfig.BET_INFO_CODE;
			case GDKLC:
				return NewCcHappyConfig.BET_INFO_CODE;
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
