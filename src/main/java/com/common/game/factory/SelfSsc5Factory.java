package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.Period;
import com.common.game.SelfSsc5;
import com.common.game.idc.SuperFast55;
import com.common.game.type.BallGame;
import com.common.util.BaseHandicapUtil;

/**
 * 自有5分彩时时彩
 * @Author: Dongming
 * @Date: 2020-05-13 17:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SelfSsc5Factory implements GameFactory<SelfSsc5<?>> {
	SuperFast55 superFast55 = new SuperFast55();

	private static final SelfSsc5Factory INSTANCE = new SelfSsc5Factory();
	private SelfSsc5Factory() {
	}
	public static SelfSsc5Factory build() {
		return INSTANCE;
	}

	@Override public Period<?> period(BaseHandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
				return superFast55;
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
	@Override public BallGame game() {
		return superFast55;
	}


	@Override
	public long getInterval() {
		return PeriodConfig.PERIOD_TIME_5;
	}
}
