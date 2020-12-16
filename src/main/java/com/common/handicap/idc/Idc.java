package com.common.handicap.idc;

import com.common.config.handicap.IdcBallConfig;
import com.common.config.handicap.IdcConfig;
import com.common.config.handicap.IdcHappyConfig;
import com.common.config.handicap.IdcNumberConfig;
import com.common.handicap.Handicap;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.RandomTool;

import java.util.List;
import java.util.Map;

/**
 * idc盘口
 *
 * @Author: Dongming
 * @Date: 2020-05-12 14:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Idc implements Handicap {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.IDC;

	private static final Idc INSTANCE = new Idc();
	private Idc() {
	}
	public static Idc getInstance() {
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
	@Override public Map<String, String> gameType() {
		return IdcConfig.GAME_TYPE;
	}

	@Override public Map<String, String> itemCodeMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return IdcNumberConfig.ITEM_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return IdcBallConfig.ITEM_CODE;
			case XYNC:
			case GDKLC:
				return IdcHappyConfig.ITEM_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public Map<String, String> codeItemMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return IdcNumberConfig.CODE_ITEM;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return IdcBallConfig.CODE_ITEM;
			case XYNC:
			case GDKLC:
				return IdcHappyConfig.CODE_ITEM;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public Map<String, String> infoItemMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return IdcNumberConfig.INFO_ITEM;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return IdcBallConfig.INFO_ITEM;
			case XYNC:
			case GDKLC:
				return IdcHappyConfig.INFO_ITEM;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public Map<String, String> itemLimitMap(BaseGameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return IdcNumberConfig.ITEM_LIMIT;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return IdcBallConfig.ITEM_LIMIT;
			case XYNC:
			case GDKLC:
				return IdcHappyConfig.ITEM_LIMIT;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	@Override public String checkCron() {
		return  RandomTool.getInt(30) + "/30 * 7-3 * * ? *";
	}

	@Deprecated @Override public Map<String, String> betType(BaseGameUtil.Code gameCode) {
		return null;
	}
	@Deprecated @Override public List<String> betRanks(BaseGameUtil.Code gameCode, String betType) {
		return null;
	}

}
