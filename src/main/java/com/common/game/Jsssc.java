package com.common.game;

import com.common.config.PeriodConfig;
import com.common.game.type.BallGame;


/**
 * 急速时时彩
 *
 * @Author: Dongming
 * @Date: 2020-05-13 16:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class Jsssc<T>  extends BallGame implements Period<T>{
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_1;

	@Override
	public String getDrawTableName() {
		return "rep_draw_jsssc";
	}
}
