package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.Jsssc;
import com.common.game.Period;
import com.common.game.hq.BritishSpeedSsc;
import com.common.game.idc.SpeedSsc;
import com.common.game.type.BallGame;
import com.common.util.BaseHandicapUtil;

/**
 * 急速时时彩
 *
 * @Author: Dongming
 * @Date: 2020-05-13 17:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class JssscFactory implements GameFactory<Jsssc<?>> {
	SpeedSsc speedSsc = new SpeedSsc();
	BritishSpeedSsc britishSpeedSsc = new BritishSpeedSsc();

	private static final JssscFactory INSTANCE = new JssscFactory();
	private JssscFactory() {
	}
	public static JssscFactory build() {
		return INSTANCE;
	}

	@Override public Period<?> period(BaseHandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
			case SGWIN:
			case NCOM1:
			case UN:
				return speedSsc;
			case HQ:
				return britishSpeedSsc;
			case BW:
			case COM:
			case NCOM2:
			default:
				throw new RuntimeException("尚未完成的盘口");
		}
	}

	@Override public BallGame game() {
		return speedSsc;
	}


	@Override
	public long getInterval() {
		return PeriodConfig.PERIOD_TIME_1;
	}
}
