package com.ibm.follow.servlet.client.core.cache;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户缓存
 *
 * @Author: Dongming
 * @Date: 2020-01-03 16:27
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

    /**
     * 清理信息
     *
     * @param existId 客户存在id
     */
    public static void clearUp(String existId) {
        CustomerState.CACHE.clearUp(existId);
        CustomerData.CACHE.clearUp(existId);
    }

    //region 客户数据信息

    //region 封盘时间

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
                                     Object period) throws ParseException, SQLException {
		long drawTime =gameCode.getGameFactory().period(handicapCode).getDrawTime(period);
        long sealTime = PlatformCache.sealTime(handicapCode, gameCode);
        long sealTimeLong = drawTime - sealTime * 1000;
        CustomerData.CACHE.sealTime(existId, gameCode, sealTime);
        return sealTimeLong;
    }
    //endregion

    //region 额度操作

    /**
     * 获取可用额度
     *
     * @param existId      存在ID
     * @param period       期数
     * @param handicapCode 盘口编码
     * @param gameCode     游戏编码
     * @return 可用额度
     */
    public static double usedQuota(String existId, Object period, HandicapUtil.Code handicapCode,
                                   GameUtil.Code gameCode) {
        return CustomerData.CACHE.usedQuota(existId, period, handicapCode, gameCode);
    }

    /**
     * 获取用户余额信息
     *
     * @param existId      存在ID
     * @param handicapCode 盘口编码
     * @param gameCode     游戏编码
     * @return 用户余额信息
     */
    public static Double usedQuota(String existId, HandicapUtil.Code handicapCode, GameUtil.Code gameCode) {
        return CustomerData.CACHE.usedQuota(existId, handicapCode, gameCode);
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
    //endregion

    private static class CustomerData {
        private static final CustomerData CACHE = new CustomerData();
        private Map<String, UseCacheData> userCacheInfo;

        private CustomerData() {
            userCacheInfo = new HashMap<>();
        }

        public void cache() {

        }

        /**
         * 获取封盘时间
         *
         * @param existId  存在ID
         * @param gameCode 游戏编码
         * @return 封盘时间戳
         */
        public Long sealTime(String existId, GameUtil.Code gameCode) {
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
            UseCacheData cacheData;
            if (userCacheInfo.containsKey(existId)) {
                cacheData = userCacheInfo.get(existId);
            } else {
                cacheData = new UseCacheData();
                userCacheInfo.put(existId, cacheData);
            }
            cacheData.sealTime(gameCode, sealTime);
        }

        /**
         * 获取可用额度
         *
         * @param existId      存在ID
         * @param period       期数
         * @param handicapCode 盘口编码
         * @param gameCode     游戏编码
         * @return 可用额度
         */
        public double usedQuota(String existId, Object period, HandicapUtil.Code handicapCode, GameUtil.Code gameCode) {
            UseCacheData cacheData;
            if (userCacheInfo.containsKey(existId)) {
                cacheData = userCacheInfo.get(existId);
                if (period.equals(cacheData.period())) {
                    return cacheData.usedQuota();
                }
            } else {
                cacheData = new UseCacheData();
                userCacheInfo.put(existId, cacheData);
            }
            double usedQuota = HandicapHttpClientTool.getUsedQuota(existId, handicapCode, gameCode, false);
            cacheData.refresh(usedQuota, period);
            return usedQuota;
        }

        /**
         * 获取用户余额信息
         * 不进行期数校验
         * s
         *
         * @param existId      存在ID
         * @param handicapCode 盘口编码
         * @param gameCode     游戏编码
         * @return 用户余额信息
         */
        public Double usedQuota(String existId, HandicapUtil.Code handicapCode, GameUtil.Code gameCode) {
            UseCacheData cacheData;
            if (userCacheInfo.containsKey(existId)) {
                cacheData = userCacheInfo.get(existId);
            } else {
                cacheData = new UseCacheData();
                userCacheInfo.put(existId, cacheData);
            }
            double usedQuota = HandicapHttpClientTool.getUsedQuota(existId, handicapCode, gameCode, true);
            cacheData.usedQuota(usedQuota);
            return usedQuota;
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
                UseCacheData cacheData = new UseCacheData();
                userCacheInfo.put(existId, cacheData);
                cacheData.usedQuota(usedQuota);
            }
        }

        /**
         * 清理信息
         *
         * @param existId 客户存在id
         */
        public UseCacheData clearUp(String existId) {
            return userCacheInfo.remove(existId);
        }
    }
    //endregion

    //region 客户状态信息

    /**
     * 放入客户状态
     *
     * @param existId 客户存在id
     * @param state   客户状态
     */
    public static void stateInfo(String existId, IbmStateEnum state) {
        CustomerState.CACHE.stateInfo(existId, state);
    }

    /**
     * 获取客户状态
     *
     * @param existId 客户存在id
     * @return 客户状态
     */
    public static IbmStateEnum stateInfo(String existId) {
        return CustomerState.CACHE.stateInfo(existId);
    }

    private static class CustomerState {
        private static final CustomerState CACHE = new CustomerState();
        private Map<String, IbmStateEnum> customerStateInfo;

        private CustomerState() {
            customerStateInfo = new HashMap<>();
        }

        /**
         * 缓存数据
         */
        public void cache() throws SQLException {
            Map<Object, Object> stateMap = new IbmcExistHaService().mapState();
            for (Map.Entry<Object, Object> entry : stateMap.entrySet()) {
                customerStateInfo.put(entry.getKey().toString(), IbmStateEnum.valueOf(entry.getValue().toString()));
            }
            stateMap = new IbmcExistHmService().mapState();
            for (Map.Entry<Object, Object> entry : stateMap.entrySet()) {
                customerStateInfo.put(entry.getKey().toString(), IbmStateEnum.valueOf(entry.getValue().toString()));
            }
        }

        /**
         * 放入客户状态
         *
         * @param existId 客户存在ID
         * @param state   客户状态
         */
        public CustomerState stateInfo(String existId, IbmStateEnum state) {
            customerStateInfo.put(existId, state);
            return this;
        }

        /**
         * 获取客户状态
         *
         * @param existId 客户存在id
         * @return 客户状态
         */
        public IbmStateEnum stateInfo(String existId) {
            return customerStateInfo.get(existId);
        }

        /**
         * 清理信息
         *
         * @param existId 客户存在id
         * @return 客户状态
         */
        public IbmStateEnum clearUp(String existId) {
            return customerStateInfo.remove(existId);
        }
    }
    //endregion

}
