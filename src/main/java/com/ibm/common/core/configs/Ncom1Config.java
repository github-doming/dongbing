package com.ibm.common.core.configs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * @Description: Ncom1盘口默认值
 * @Author: null
 * @Date: 2019-12-26 17:18
 * @Version: v1.0
 */
public interface Ncom1Config {

    Map<String, String> GAME_NAME = ImmutableMap.<String, String>builder().put("PK10", "北京赛车").put("XYFT", "幸运飞艇")
            .put("JS10", "极速赛车").put("CQSSC", "重庆时时彩").put("JSSSC", "极速时时彩").build();

    Map<String, String> BET_CODE = ImmutableMap.<String, String>builder().put("PK10", "1").put("XYFT", "6")
            .put("JS10", "11").put("CQSSC", "2").put("JSSSC", "12").build();

    /**
     * 游戏code
     */
    Map<String, String> GAME_TYPE_CODE = ImmutableMap.<String, String>builder()
            .put("dobleSides", "lm").put("ballNO", "p").put("sumDT", "gy").put("threeBall","p1").build();


    /**
     * 双面
     */
    List<String> DOUBLE_SIDE = ImmutableList.<String>builder().add("大").add("小").add("单").add("双").add("龙").add("虎")
            .add("和").build();

    /**
     * 三球
     */
    List<String> THREE_BALL = ImmutableList.<String>builder().add("豹子").add("顺子").add("对子").add("半顺").add("杂六").build();

    /**
     * 冠亚和
     */
    List<String> SUM_DT = ImmutableList.<String>builder().add("冠亚|3").add("冠亚|4").add("冠亚|5").add("冠亚|6").add("冠亚|7")
            .add("冠亚|8").add("冠亚|9").add("冠亚|10").add("冠亚|11").add("冠亚|12").add("冠亚|13").add("冠亚|14").add("冠亚|15")
            .add("冠亚|16").add("冠亚|17").add("冠亚|18").add("冠亚|19").build();

}
