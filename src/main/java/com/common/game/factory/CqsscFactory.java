package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.Cqssc;
import com.common.game.Period;
import com.common.util.BaseHandicapUtil;

/**
 * 重庆时时彩
 * @Author: Dongming
 * @Date: 2020-05-13 16:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CqsscFactory  implements GameFactory<Cqssc> {

	private Cqssc cqssc = new Cqssc();

	private static final CqsscFactory INSTANCE = new CqsscFactory();
	private CqsscFactory() {
	}
	public static CqsscFactory build() {
		return INSTANCE;
	}

	@Override public Period<String> period(BaseHandicapUtil.Code handicapCode) {
		return cqssc;
	}
	@Override public Cqssc game() {
		return cqssc;
	}


	@Override
	public long getInterval() {
		return PeriodConfig.PERIOD_TIME_20;
	}
}
