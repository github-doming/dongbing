package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.Game;
import com.common.game.Gxklsf;
import com.common.game.Period;
import com.common.util.BaseHandicapUtil;

/**
 * 广西快乐十分
 * @Author: Dongming
 * @Date: 2020-06-02 16:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GxklsfFactory implements GameFactory<Gxklsf>{
	private Gxklsf gxklsf = new Gxklsf();

	private static final GxklsfFactory INSTANCE = new GxklsfFactory();
	private GxklsfFactory() {
	}
	public static GxklsfFactory build() {
		return INSTANCE;
	}
	@Override public Period<?> period(BaseHandicapUtil.Code handicapCode) {
		return gxklsf;
	}
	@Override public Game game() {
		return gxklsf;
	}
	@Override public long getInterval() {
		return  PeriodConfig.PERIOD_TIME_20;
	}
}
