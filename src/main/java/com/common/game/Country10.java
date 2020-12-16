package com.common.game;

import com.common.game.type.NumberGame;

/**
 * 国家赛车
 * @Author: Dongming
 * @Date: 2020-05-13 17:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class Country10<T> extends NumberGame implements Period<T>{

	@Override
	public String getDrawTableName() {
		return "rep_draw_country_10";
	}
}
