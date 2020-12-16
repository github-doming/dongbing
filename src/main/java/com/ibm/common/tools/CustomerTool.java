package com.ibm.common.tools;

import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.GameMergeTool;
import com.ibm.follow.servlet.cloud.core.MergeBetInfo;
import org.apache.commons.collections.map.HashedMap;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 客户工具类
 * @Author: Dongming
 * @Date: 2019-09-21 15:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CustomerTool {

    private static final Map<String, Map<GameUtil.Code, Map<String, int[][]>>> CUSTOMER_BET_MAP = new ConcurrentHashMap<>(
            20);

    private static final Map<String, Map<GameUtil.Code, MergeBetInfo>> FOLLOW_BET_ID_MAP = new ConcurrentHashMap<>(
            20);

    /**
     * 放入投注资金
     *
     * @param customerId  客户主键
     * @param gameCode    游戏编码
     * @param betContents 投注正文
     * @param followBetId 跟投id
     */
    public static void putBetItem(String customerId, GameUtil.Code gameCode, String[] betContents, String followBetId) {
        synchronized (customerId) {
            MergeBetInfo mergeBetInfo;
            if (CUSTOMER_BET_MAP.containsKey(customerId)) {
                if (CUSTOMER_BET_MAP.get(customerId).containsKey(gameCode)) {
                    Map<String, int[][]> betInfo = CUSTOMER_BET_MAP.get(customerId).get(gameCode);
                    GameMergeTool.putBetInfo(gameCode, betInfo, betContents);
                    //存储跟投id
                    mergeBetInfo = FOLLOW_BET_ID_MAP.get(customerId).get(gameCode);
                    if(StringTool.isEmpty(mergeBetInfo.getFollowBetIds())){
                        mergeBetInfo.setFollowBetIds(followBetId);
                    }else{
                        mergeBetInfo.setFollowBetIds(mergeBetInfo.getFollowBetIds().concat(",").concat(followBetId));
                    }
                } else {
                    Map<String, int[][]> betInfo = new HashedMap(5);
                    GameMergeTool.putBetInfo(gameCode, betInfo, betContents);
                    CUSTOMER_BET_MAP.get(customerId).put(gameCode, betInfo);
                    //存储跟投id
                    mergeBetInfo = new MergeBetInfo();
                    mergeBetInfo.setFollowBetIds(followBetId);
                    FOLLOW_BET_ID_MAP.get(customerId).put(gameCode, mergeBetInfo);
                }
            } else {
                Map<GameUtil.Code, Map<String, int[][]>> gameMap = new HashMap<>(5);
                Map<String, int[][]> betInfo = new HashedMap(3);
                GameMergeTool.putBetInfo(gameCode, betInfo, betContents);
                gameMap.put(gameCode, betInfo);
                CUSTOMER_BET_MAP.put(customerId, gameMap);
                //存储跟投id
                Map<GameUtil.Code, MergeBetInfo> followBetIdMap = new HashMap<>(5);
                mergeBetInfo = new MergeBetInfo();
                mergeBetInfo.setFollowBetIds(followBetId);
                followBetIdMap.put(gameCode, mergeBetInfo);
                FOLLOW_BET_ID_MAP.put(customerId, followBetIdMap);
            }
        }
    }

    /**
     * 获取投注项code
     *
     * @param customerId 客户主键
     * @param gameCode   游戏编码
     * @return 投注项code
     */
    public static Map<String, int[][]> getBetInfo(String customerId, GameUtil.Code gameCode) {
        Map<String, int[][]> betCode = null;
        if (CUSTOMER_BET_MAP.containsKey(customerId)) {
            Map<GameUtil.Code, Map<String, int[][]>> map = CUSTOMER_BET_MAP.get(customerId);
            if (map.containsKey(gameCode)) {
                betCode = map.get(gameCode);
                //重置数据
                map.put(gameCode, new HashMap<>(3));
            }
        }
        return betCode;
    }

    /**
     * 获取跟投ids
     *
     * @param customerId 客户主键
     * @param gameCode   游戏code
     * @return
     */
    public static String getFollowBetIds(String customerId, GameUtil.Code gameCode) {
        String followBetIds = null;
        if (FOLLOW_BET_ID_MAP.containsKey(customerId)) {
            if (FOLLOW_BET_ID_MAP.get(customerId).containsKey(gameCode)) {
                MergeBetInfo mergeBetInfo = FOLLOW_BET_ID_MAP.get(customerId).get(gameCode);
                followBetIds = mergeBetInfo.getFollowBetIds();
                mergeBetInfo.setFollowBetIds(null);
            }
        }
        return followBetIds;
    }
    /**
     * 移除客户信息
     *
     * @param customerId      客户存在id
     */
    public static void removeCustomerInfo(String customerId) {
        CUSTOMER_BET_MAP.remove(customerId);
        FOLLOW_BET_ID_MAP.remove(customerId);
    }
}
