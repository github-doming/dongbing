package com.ibm.follow.servlet.cloud.core;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.cloud.ibm_config.service.IbmConfigService;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 中心端工具类
 * @Author: Dongming
 * @Date: 2019-09-21 13:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CloudTool {

	public static Map<HandicapUtil.Code, Map<GameUtil.Code, Long>> SEAL_TIME_MAP = new HashMap<>();

	/**
	 * 获取盘口游戏的封盘时间
	 * @param handicapCode 盘口编码
	 * @param gameCode 游戏编码
	 * @return 封盘时间
	 */
	public static long getSealTime(HandicapUtil.Code handicapCode, GameUtil.Code gameCode) throws Exception {
		if (SEAL_TIME_MAP.containsKey(handicapCode)) {
			if (SEAL_TIME_MAP.get(handicapCode).containsKey(gameCode)) {
				return SEAL_TIME_MAP.get(handicapCode).get(gameCode);
			} else {
				long sealTime = new IbmConfigService().findSealTime(handicapCode.name(), gameCode.name())*1000;
				SEAL_TIME_MAP.get(handicapCode).put(gameCode, sealTime);
				return sealTime;
			}
		} else {
			Map<GameUtil.Code, Long> map = new HashMap<>(5);
			long sealTime = new IbmConfigService().findSealTime(handicapCode.name(), gameCode.name())*1000;
			map.put(gameCode, sealTime);
			SEAL_TIME_MAP.put(handicapCode, map);
			return sealTime;
		}
	}
}
