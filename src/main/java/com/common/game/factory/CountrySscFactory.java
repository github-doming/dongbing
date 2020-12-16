package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.CountrySsc;
import com.common.game.Period;
import com.common.game.idc.AustraliaLucky5;
import com.common.util.BaseHandicapUtil;

/**
 * 国家时时彩
 *
 * @Author: Dongming
 * @Date: 2020-05-13 17:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CountrySscFactory implements GameFactory<CountrySsc<?>> {
	private AustraliaLucky5 australiaLucky5 = new AustraliaLucky5();

	private static final CountrySscFactory INSTANCE = new CountrySscFactory();
	private CountrySscFactory() {
	}
	public static CountrySscFactory build() {
		return INSTANCE;
	}

	@Override public Period<?> period(BaseHandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
				return australiaLucky5;
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

	@Override public CountrySsc game() {
		return australiaLucky5;
	}

	@Override
	public long getInterval() {
		return PeriodConfig.PERIOD_TIME_5;
	}

}
