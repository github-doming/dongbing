package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.Country10;
import com.common.game.Game;
import com.common.game.Period;
import com.common.game.idc.AustraliaLucky10;
import com.common.util.BaseHandicapUtil;

/**
 * 国家赛车
 *
 * @Author: Dongming
 * @Date: 2020-05-13 17:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Country10Factory implements GameFactory<Country10<?>> {
	private AustraliaLucky10 australiaLucky10 = new AustraliaLucky10();

	private static final Country10Factory INSTANCE = new Country10Factory();
	private Country10Factory() {
	}
	public static Country10Factory build() {
		return INSTANCE;
	}

	@Override public Period<?> period(BaseHandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
				return australiaLucky10;
			case SGWIN:
			case NCOM1:
			case HQ:
			case NCOM2:
			case BW:
			case COM:
			default:
				throw new RuntimeException("尚未完成的盘口");

		}
	}
	@Override public Game game() {
		return australiaLucky10;
	}

	@Override
	public long getInterval() {
		return PeriodConfig.PERIOD_TIME_5;
	}
}
