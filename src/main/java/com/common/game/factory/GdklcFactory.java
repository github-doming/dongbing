package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.Gdklc;
import com.common.game.Period;
import com.common.util.BaseHandicapUtil;

/**
 * 广东快乐十分
 * @Author: Dongming
 * @Date: 2020-05-13 16:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GdklcFactory implements GameFactory<Gdklc>{
	private Gdklc gdklc = new Gdklc();

	private static final GdklcFactory INSTANCE = new GdklcFactory();
	private GdklcFactory() {
	}
	public static GdklcFactory build() {
		return INSTANCE;
	}
	@Override public Period<String> period(BaseHandicapUtil.Code handicapCode) {
		return gdklc;
	}
	@Override public Gdklc game() {
		return gdklc;
	}


	@Override
	public long getInterval() {
		return PeriodConfig.PERIOD_TIME_20;
	}
}
