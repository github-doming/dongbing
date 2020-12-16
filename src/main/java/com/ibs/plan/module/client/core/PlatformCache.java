package com.ibs.plan.module.client.core;

import com.common.tools.CacheTool;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.client.ibsc_config.service.IbscConfigService;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 * 平台缓存
 *
 * @Author: Dongming
 * @Date: 2020-05-18 18:08
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PlatformCache {

	public static void cache() throws SQLException {
		SealTime.CACHE.cache();
		CacheTool.cache();
	}
	/**
	 * 获取封盘时间
	 *
	 * @param handicapCode 盘口编码
	 * @param gameCode     游戏编码
	 * @return 封盘时间
	 */
	public static long sealTime(HandicapUtil.Code handicapCode, GameUtil.Code gameCode) throws SQLException {
		return SealTime.CACHE.sealTime(handicapCode, gameCode);
	}

	private static class SealTime {
		private static final SealTime CACHE = new SealTime();
		private Map<HandicapUtil.Code, Map<GameUtil.Code, Long>> sealTimeInfo;
		private SealTime() {
			sealTimeInfo = new HashMap<>();
		}
		/**
		 * 缓存数据
		 */
		public void cache() throws SQLException {
			IbscConfigService configService = new IbscConfigService();
			Map<String, String> sealTimeMap = configService.mapByType("SEAL_TIME");
			for (Map.Entry<String, String> entry : sealTimeMap.entrySet()) {
				String[] keys = entry.getKey().split("#");
				HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(keys[0]);
				GameUtil.Code gameCode = GameUtil.Code.valueOf(keys[1]);
				long sealTime = NumberTool.getLong(entry.getValue());
				//存储数据
				if (sealTimeInfo.containsKey(handicapCode)) {
					sealTimeInfo.get(handicapCode).put(gameCode, sealTime);
				} else {
					Map<GameUtil.Code, Long> map = new HashMap<>(5);
					map.put(gameCode, sealTime);
					sealTimeInfo.put(handicapCode, map);
				}


			}

		}
		/**
		 * 获取封盘时间
		 *
		 * @param handicapCode 盘口编码
		 * @param gameCode     游戏编码
		 * @return 封盘时间
		 */
		public long sealTime(HandicapUtil.Code handicapCode, GameUtil.Code gameCode) throws SQLException {
			Map<GameUtil.Code, Long> map;
			if (sealTimeInfo.containsKey(handicapCode)) {
				if (sealTimeInfo.get(handicapCode).containsKey(gameCode)) {
					return sealTimeInfo.get(handicapCode).get(gameCode);
				}
				map = sealTimeInfo.get(handicapCode);
			} else {
				map = new HashMap<>(5);
				sealTimeInfo.put(handicapCode, map);
			}
			long sealTime = new IbscConfigService().findSealTime(handicapCode.name(), gameCode.name());
			map.put(gameCode, sealTime);
			return sealTime;
		}
	}

}
