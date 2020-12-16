package com.common.game;

import com.common.game.type.NumberGame;

/**
 * 自有2分彩赛车
 * @Author: Dongming
 * @Date: 2020-05-13 17:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class Self102<T> extends NumberGame implements Period<T>{

	@Override
	public String getDrawTableName() {
		return "rep_draw_self_10_2";
	}
}
