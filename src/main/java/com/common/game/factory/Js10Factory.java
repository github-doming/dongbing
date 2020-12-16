package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.Js10;
import com.common.game.Period;
import com.common.game.hq.BritishSpeedRacing;
import com.common.game.idc.SpeedRacing;
import com.common.game.type.NumberGame;
import com.common.util.BaseHandicapUtil;

/**
 * @Author: Dongming
 * @Date: 2020-05-13 16:31
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Js10Factory implements GameFactory<Js10<?>> {
	SpeedRacing speedRacing = new SpeedRacing();
	BritishSpeedRacing britishSpeedRacing = new BritishSpeedRacing();

	private static final Js10Factory INSTANCE = new Js10Factory();
	private Js10Factory() {
	}
	public static Js10Factory build() {
		return INSTANCE;
	}



	@Override public Period<?> period(BaseHandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
			case SGWIN:
			case NCOM1:
			case UN:
				return speedRacing;
			case HQ:
				return britishSpeedRacing;
			case NCOM2:
			case BW:
			case COM:
			default:
				throw new RuntimeException("尚未完成的盘口");

		}
	}
	@Override public NumberGame game() {
		return speedRacing;
	}


	@Override
	public long getInterval() {
		return PeriodConfig.PERIOD_TIME_1;
	}
}
