package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.Period;
import com.common.game.Pk10;
import com.common.util.BaseHandicapUtil;

/**
 * PK10
 *
 * @Author: Dongming
 * @Date: 2020-05-13 14:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Pk10Factory implements GameFactory<Pk10> {

	private Pk10 pk10 = new Pk10();

	private static final Pk10Factory INSTANCE = new Pk10Factory();
	private Pk10Factory() {
	}
	public static Pk10Factory build() {
		return INSTANCE;
	}

	@Override public Period<Integer> period(BaseHandicapUtil.Code handicapCode) {
		return pk10;
	}
	@Override public Pk10 game() {
		return pk10;
	}


	@Override
	public long getInterval() {
		return PeriodConfig.PERIOD_TIME_20;
	}


}
