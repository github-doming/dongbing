package com.common.game;

import com.common.game.type.NumberGame;

/**
 * @Description: 自有10-5
 * @Author: null
 * @Date: 2020-07-20 11:37
 * @Version: v1.0
 */
public abstract class Self105<T> extends NumberGame implements Period<T>{

	@Override
	public String getDrawTableName() {
		return "rep_draw_self_10_5";
	}
}
