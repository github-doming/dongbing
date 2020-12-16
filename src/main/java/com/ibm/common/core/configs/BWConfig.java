package com.ibm.common.core.configs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * @Description: Ncom2盘口默认值
 * @Author: wwj
 * @Date: 2019/12/27 15:48
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public interface BWConfig {

    Map<String, String> GAME_NAME = ImmutableMap.<String, String>builder().put("PK10", "北京賽車(PK10)").put("XYFT", "幸運飛艇")
            .put("CQSSC", "重慶時時彩").build();

    Map<String, String> BET_CODE = ImmutableMap.<String, String>builder().put("CQSSC", "CQ").put("PK10", "BJ")
            .put("XYFT", "FT").build();

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
