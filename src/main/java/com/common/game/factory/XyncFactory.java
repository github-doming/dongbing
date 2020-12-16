package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.Period;
import com.common.game.Xync;
import com.common.util.BaseHandicapUtil;

/**
 * 幸运农场
 *
 * @Author: Dongming
 * @Date: 2020-05-13 16:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class XyncFactory implements GameFactory<Xync> {
	private Xync xync = new Xync();

	private static final XyncFactory INSTANCE = new XyncFactory();
	private XyncFactory() {
	}
	public static XyncFactory build() {
		return INSTANCE;
	}

	@Override public Period<String> period(BaseHandicapUtil.Code handicapCode) {
		return xync;
	}
	@Override public Xync game() {
		return xync;
	}


	@Override
	public long getInterval() {
		return PeriodConfig.PERIOD_TIME_20;
	}
}
