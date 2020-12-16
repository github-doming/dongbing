package com.common.game.factory;


import com.common.config.PeriodConfig;
import com.common.game.Period;
import com.common.game.Self102;
import com.common.game.idc.Eps2;
import com.common.game.type.NumberGame;
import com.common.util.BaseHandicapUtil;

/**
 * 自有2分彩赛车
 * @Author: Dongming
 * @Date: 2020-05-13 17:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Self102Factory  implements GameFactory<Self102<?>> {
	private Eps2 eps2 = new Eps2();


	private static final Self102Factory INSTANCE = new Self102Factory();
	private Self102Factory() {
	}
	public static Self102Factory build() {
		return INSTANCE;
	}

	@Override public Period<?> period(BaseHandicapUtil.Code handicapCode) {
		switch (handicapCode) {
			case IDC:
				return eps2;
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
	@Override public NumberGame game() {
		return eps2;
	}


	@Override
	public long getInterval() {
		return PeriodConfig.PERIOD_TIME_2;
	}
}
