package com.common.game;

import com.common.game.type.BallGame;

/**
 * 国家时时彩
 *
 * @Author: Dongming
 * @Date: 2020-05-13 17:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class CountrySsc<T> extends BallGame implements Period<T> {

	@Override
	public String getDrawTableName() {
		return "rep_draw_country_ssc";
	}
}
