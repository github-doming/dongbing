package com.common.handicap;


import com.common.config.handicap.BallConfig;
import com.common.config.handicap.HappyConfig;
import com.common.config.handicap.NumberConfig;
import com.common.util.BaseGameUtil;

import java.util.List;
import java.util.Map;

/**
 * 盘口
 *
 * @Author: Dongming
 * @Date: 2020-05-12 15:04
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface Handicap {

	/**
	 * 获取期数
	 *
	 * @param gameCode 游戏code
	 * @return 期数
	 */
	Object period(BaseGameUtil.Code gameCode);

	/**
	 * 盘口期数
	 *
	 * @param gameCode 游戏code
	 * @return 盘口期数
	 */
	Object handicapPeriod(BaseGameUtil.Code gameCode);

	/**
	 * 盘口期数
	 *
	 * @param gameCode 游戏code
	 * @param period   期数
	 * @return 盘口期数
	 */
	Object handicapPeriod(BaseGameUtil.Code gameCode, Object period);

	/**
	 * 获取 游戏类型map
	 *
	 * @return 游戏类型map
	 */
	Map<String, String> gameType();

	/**
	 * 获取 投注类型map
	 *
	 * @param gameCode 游戏code
	 * @return 投注类型map
	 */
	Map<String, String> betType(BaseGameUtil.Code gameCode);

	/**
	 * 获取 投注排名列表
	 *
	 * @param gameCode 游戏code
	 * @param betType  投注类型
	 * @return 投注类型map
	 */
	List<String> betRanks(BaseGameUtil.Code gameCode, String betType);

	/**
	 * 获取 投注球号map
	 *
	 * @param gameCode 游戏code
	 * @return 投注球号map
	 */
	Map<String, String> itemCodeMap(BaseGameUtil.Code gameCode);

	/**
	 * 获取投注详情map
	 *
	 * @param gameCode 游戏code
	 * @return 投注详情map
	 */
	Map<String, String> codeItemMap(BaseGameUtil.Code gameCode);

	/**
	 * 获取球号信息map
	 *
	 * @param gameCode 游戏code
	 * @return 球号信息map
	 */
	Map<String, String> infoItemMap(BaseGameUtil.Code gameCode);

	/**
	 * 获取球号限额map
	 *
	 * @param gameCode 游戏code
	 * @return 球号限额map
	 */
	Map<String, String> itemLimitMap(BaseGameUtil.Code gameCode);

	/**
	 * 获取投注项类型集合 (与盘口无关，用来统一单双特号球的限额code)
	 *
	 * @param gameCode 游戏编码
	 * @return 投注项类型集合
	 */
	default Map<String, String> itemTypeMap(BaseGameUtil.Code gameCode){
		switch (gameCode) {
			case PK10:
			case JS10:
			case XYFT:
			case SELF_10_2:
			case COUNTRY_10:
				return NumberConfig.ITEM_TYPE;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return BallConfig.ITEM_TYPE;
			case XYNC:
			case GDKLC:
				return HappyConfig.ITEM_TYPE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}



	/**
	 * 校验CRON表达式
	 * @return 校验CRON表达式
	 */
	String checkCron();
}
