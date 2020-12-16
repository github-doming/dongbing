package com.ibm.common.test.wwj.handicap;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.game.GameUtil;

import java.util.List;
import java.util.Map;
/**
 * @Description: 会员盘口爬虫
 * @Author: Dongming
 * @Date: 2019-11-22 10:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface CrawlerMember extends CrawlerHandicap {

	/**
	 * 会员基本信息
	 *
	 * @param refresh 刷新数据
	 * @return 基本信息
	 */
	Map<String, String> userInfo(boolean refresh) throws HandicapException;

	/**
	 * 某个游戏的限额信息
	 *
	 * @param gameCode 游戏编码
	 * @return 游戏限额信息
	 */
	JsonResultBeanPlus gameLimit(GameUtil.Code gameCode);

	/**
	 * 游戏赔率信息
	 *
	 * @param gameCode 游戏编码
	 * @return 游戏赔率信息
	 */
	JsonResultBeanPlus oddsInfo(GameUtil.Code gameCode,String betType);

	/**
	 * 投注
	 *
	 * @param gameCode 游戏编码
	 * @param betItems 投注项列表
	 * @param odds     赔率信息
	 * @param period   期数
	 * @return 投注结果
	 */
	JsonResultBeanPlus betting(GameUtil.Code gameCode, List<String> betItems, Object odds, String period);
}
