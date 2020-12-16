package com.common.game;

import com.common.config.PeriodConfig;
import com.common.game.type.NumberGame;

/**
 * 急速赛车
 * @Author: Dongming
 * @Date: 2020-05-13 16:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public  abstract class Js10<T> extends NumberGame implements Period<T>{
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_1;

	@Override
	public String getDrawTableName() {
		return "rep_draw_js10";
	}
}
