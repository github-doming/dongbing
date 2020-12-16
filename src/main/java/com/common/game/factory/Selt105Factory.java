package com.common.game.factory;

import com.common.game.Game;
import com.common.game.Period;
import com.common.game.Self105;
import com.common.game.newws.Ft5;
import com.common.util.BaseHandicapUtil;

/**
 * @Description: 自有10-5
 * @Author: null
 * @Date: 2020-07-20 12:13
 * @Version: v1.0
 */
public class Selt105Factory implements GameFactory<Self105<?>>{
	Ft5 selt105 = new Ft5();

	private static final Selt105Factory INSTANCE = new Selt105Factory();
	private Selt105Factory() {
	}
	public static Selt105Factory build() {
		return INSTANCE;
	}

	@Override
	public Period<?> period(BaseHandicapUtil.Code handicapCode) {
		switch (handicapCode){
			case NEWWS:
				return selt105;
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

	@Override
	public Game game() {
		return selt105;
	}

	@Override
	public long getInterval() {
		return 5 * 60 * 1000L;
	}
}
