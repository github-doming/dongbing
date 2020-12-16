package com.ibs.plan.module.client.core;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.client.ibsc_exist_hm.service.IbscExistHmService;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
/**
 * 客户缓存
 *
 * @Author: Dongming
 * @Date: 2020-05-18 18:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CustomerCache {
	/**
	 * 加载缓存
	 */
	public static void cache() throws SQLException {
		CustomerState.CACHE.cache();
		CustomerData.CACHE.cache();
	}

	public static void clearUp(String existHmId) {
		CustomerState.CACHE.clearUp(existHmId);
		CustomerData.CACHE.clearUp(existHmId);
	}

	/**
	 * 放入客户状态
	 *
	 * @param existId 客户存在id
	 * @param state   客户状态
	 */
	public static void stateInfo(String existId, IbsStateEnum state) {
		CustomerState.CACHE.stateInfo(existId, state);
	}

	/**
	 * 获取客户状态
	 *
	 * @param existId 客户存在id
	 * @return 客户状态
	 */
	public static IbsStateEnum stateInfo(String existId) {
		return CustomerState.CACHE.stateInfo(existId);
	}

	/**
	 * 放入可用额度
	 *
	 * @param existId   存在ID
	 * @param usedQuota 可用额度
	 */
	public static void usedQuota(String existId, double usedQuota) {
		CustomerData.CACHE.usedQuota(existId, usedQuota);
	}
	/**
	 * 获取用户余额信息
	 *
	 * @param existId      存在ID
	 * @param handicapCode 盘口编码
	 * @return 用户余额信息
	 */
	public static double usedQuota(String existId, HandicapUtil.Code handicapCode) {
		return CustomerData.CACHE.usedQuota(existId, handicapCode);
	}

	/**
	 * 获取可用额度
	 *
	 * @param existId      存在ID
	 * @param period       期数
	 * @param handicapCode 盘口编码
	 * @return 可用额度
	 */
	public static double usedQuota(String existId, HandicapUtil.Code handicapCode, Object period) {
		return CustomerData.CACHE.usedQuota(existId, handicapCode, period);
	}

	/**
	 * 获取封盘时间
	 *
	 * @param existId  存在ID
	 * @param gameCode 游戏编码
	 * @return 封盘时间戳
	 */
	public static long sealTime(String existId, GameUtil.Code gameCode) {
		return CustomerData.CACHE.sealTime(existId, gameCode);
	}

	/**
	 * 重置封盘时间
	 *
	 * @param existId      存在ID
	 * @param gameCode     游戏编码
	 * @param handicapCode 盘口编码
	 * @param period       期数
	 * @return 封盘时间
	 */
	public static long resetSealTime(String existId, GameUtil.Code gameCode, HandicapUtil.Code handicapCode,
			Object period) throws SQLException, ParseException {
		return CustomerData.CACHE.resetSealTime(existId, gameCode, handicapCode, period);
	}

	//region 内部对象
	/**
	 * 客户状态数据类
	 */
	private static class CustomerState {
		private static final CustomerState CACHE = new CustomerState();
		private Map<String, IbsStateEnum> customerStateInfo;

		private CustomerState() {
			customerStateInfo = new HashMap<>();
		}
		/**
		 * 缓存数据
		 */
		public void cache() throws SQLException {
			Map<String, String> stateMap = new IbscExistHmService().mapState();
			for (Map.Entry<String, String> entry : stateMap.entrySet()) {
				customerStateInfo.put(entry.getKey(), IbsStateEnum.valueOf(entry.getValue()));
			}
		}
		/**
		 * 清理信息
		 *
		 * @param existId 客户存在id
		 * @return 客户状态
		 */
		public IbsStateEnum clearUp(String existId) {
			return customerStateInfo.remove(existId);
		}

		/**
		 * 放入客户状态
		 *
		 * @param existId 客户存在ID
		 * @param state   客户状态
		 */
		public CustomerState stateInfo(String existId, IbsStateEnum state) {
			customerStateInfo.put(existId, state);
			return this;
		}
		/**
		 * 获取客户状态
		 *
		 * @param existId 客户存在ID
		 * @return 客户状态
		 */
		public IbsStateEnum stateInfo(String existId) {
			return customerStateInfo.get(existId);
		}
	}

	/**
	 * 客户缓存数据
	 */
	private static class CustomerData {
		private static final CustomerData CACHE = new CustomerData();
		private Map<String, UseCache> userCacheInfo;

		private CustomerData() {
			userCacheInfo = new HashMap<>();
		}

		public void cache() {

		}

		/**
		 * 清理信息
		 *
		 * @param existId 客户存在id
		 */
		public UseCache clearUp(String existId) {
			return userCacheInfo.remove(existId);
		}

		/**
		 * 放入可用额度
		 *
		 * @param existId   存在ID
		 * @param usedQuota 可用额度
		 */
		public void usedQuota(String existId, double usedQuota) {
			if (userCacheInfo.containsKey(existId)) {
				userCacheInfo.get(existId).usedQuota(usedQuota);
			} else {
				UseCache cacheData = new UseCache();
				userCacheInfo.put(existId, cacheData);
				cacheData.usedQuota(usedQuota);
			}
		}

		/**
		 * 获取用户余额信息
		 * 不进行期数校验
		 *
		 * @param existId      存在ID
		 * @param handicapCode 盘口编码
		 * @return 用户余额信息
		 */
		public double usedQuota(String existId, HandicapUtil.Code handicapCode) {
			UseCache cacheData;
			if (userCacheInfo.containsKey(existId)) {
				cacheData = userCacheInfo.get(existId);
			} else {
				cacheData = new UseCache();
				userCacheInfo.put(existId, cacheData);
			}
			double usedQuota = handicapCode.getMemberFactory().memberOption(existId).userInfo(true).usedQuota();
			cacheData.usedQuota(usedQuota);
			return usedQuota;
		}

		/**
		 * 获取可用额度
		 *
		 * @param existId      存在ID
		 * @param period       期数
		 * @param handicapCode 盘口编码
		 * @return 可用额度
		 */
		public double usedQuota(String existId, HandicapUtil.Code handicapCode, Object period) {
			UseCache cacheData;
			if (userCacheInfo.containsKey(existId)) {
				cacheData = userCacheInfo.get(existId);
				if (period.equals(cacheData.period())) {
					return cacheData.usedQuota();
				}
			} else {
				cacheData = new UseCache();
				userCacheInfo.put(existId, cacheData);
			}
			double usedQuota = handicapCode.getMemberFactory().memberOption(existId).userInfo(false).usedQuota();
			cacheData.refresh(usedQuota, period);
			return usedQuota;
		}

		/**
		 * 获取封盘时间
		 *
		 * @param existId  存在ID
		 * @param gameCode 游戏编码
		 * @return 封盘时间戳
		 */
		public long sealTime(String existId, GameUtil.Code gameCode) {
			if (userCacheInfo.containsKey(existId)) {
				return userCacheInfo.get(existId).sealTime(gameCode);
			}
			return 0L;
		}

		/**
		 * 放入封盘时间
		 *
		 * @param existId  存在ID
		 * @param gameCode 游戏编码
		 * @param sealTime 封盘时间戳
		 */
		public void sealTime(String existId, GameUtil.Code gameCode, long sealTime) {
			UseCache cacheData;
			if (userCacheInfo.containsKey(existId)) {
				cacheData = userCacheInfo.get(existId);
			} else {
				cacheData = new UseCache();
				userCacheInfo.put(existId, cacheData);
			}
			cacheData.sealTime(gameCode, sealTime);
		}

		/**
		 * 重置封盘时间
		 *
		 * @param existId      存在ID
		 * @param gameCode     游戏编码
		 * @param handicapCode 盘口编码
		 * @param period       期数
		 * @return 封盘时间
		 */
		public long resetSealTime(String existId, GameUtil.Code gameCode, HandicapUtil.Code handicapCode, Object period)
				throws SQLException, ParseException {
			long drawTime = gameCode.getGameFactory().period(handicapCode).getDrawTime(period);
			long sealTime = PlatformCache.sealTime(handicapCode, gameCode);
			long sealTimeLong = drawTime - sealTime * 1000;
			sealTime(existId, gameCode, sealTimeLong);
			return sealTimeLong;
		}
	}
	/**
	 * 用户缓存对象
	 */
	private static class UseCache {
		private Map<GameUtil.Code, Long> gameSealTime;
		private double usedQuota;
		private Object period;

		public UseCache() {
			gameSealTime = new HashMap<>();
		}

		public double usedQuota() {
			return usedQuota;
		}
		public void usedQuota(double usedQuota) {
			this.usedQuota = usedQuota;
		}
		public Object period() {
			return period;
		}
		public void period(Object period) {
			this.period = period;
		}
		public Long sealTime(GameUtil.Code gameCode) {
			return gameSealTime.getOrDefault(gameCode, 0L);
		}
		public void sealTime(GameUtil.Code gameCode, long sealTime) {
			gameSealTime.put(gameCode, sealTime);
		}

		public void refresh(double usedQuota, Object period) {
			this.usedQuota = usedQuota;
			this.period = period;
		}
	}
	//endregion

}
