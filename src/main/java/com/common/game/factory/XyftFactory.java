package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.Period;
import com.common.game.Xyft;
import com.common.util.BaseHandicapUtil;

/**
 * 幸运飞艇
 *
 * @Author: Dongming
 * @Date: 2020-05-13 16:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class XyftFactory implements GameFactory<Xyft>{
	private Xyft xyft = new Xyft();

	private static final XyftFactory INSTANCE = new XyftFactory();
	private XyftFactory() {
	}
	public static XyftFactory build() {
		return INSTANCE;
	}
	@Override public Period<String> period(BaseHandicapUtil.Code handicapCode) {
		return xyft;
	}
	@Override public Xyft game() {
		return xyft;
	}


	@Override
	public long getInterval() {
		return PeriodConfig.PERIOD_TIME_5;
	}

}
