package com.cloud.common.game;
/**
 * 游戏操作
 *
 * @Author: Dongming
 * @Date: 2020-05-21 10:25
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface GameOperation {

	/**
	 * 获取开奖详情
	 * @param drawNumber 开奖号码
	 * @return 开奖详情
	 */
	String getDrawItem(String drawNumber);


}
