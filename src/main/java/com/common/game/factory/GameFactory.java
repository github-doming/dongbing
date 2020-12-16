package com.common.game.factory;


import com.common.game.Game;
import com.common.game.Period;
import com.common.util.BaseHandicapUtil;

/**
 * @Author: Dongming
 * @Date: 2020-05-13 14:28
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface GameFactory<T extends Period<?> & Game> {

	/**
	 * 获取期数
	 *
	 * @param handicapCode 盘口编码
	 * @return 期数
	 */
	Period<?> period(BaseHandicapUtil.Code handicapCode);

	/**
	 * 获取游戏
	 *
	 * @return 游戏
	 */
	Game game();


	/**
	 * 获取游戏时间间隔
	 * @return 游戏时间间隔
	 */
	long getInterval();
}
