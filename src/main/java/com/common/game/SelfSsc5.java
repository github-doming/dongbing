package com.common.game;

import com.common.game.type.BallGame;

/**
 * 自有5分彩时时彩
 * @Author: Dongming
 * @Date: 2020-05-13 17:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class SelfSsc5<T> extends BallGame implements Period<T> {

	@Override
	public String getDrawTableName() {
		return "rep_draw_self_ssc_5";
	}
}
