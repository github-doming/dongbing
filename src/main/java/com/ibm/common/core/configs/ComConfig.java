package com.ibm.common.core.configs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * @Description: Com盘口默认值
 * @Author: null
 * @Date: 2020-04-20 14:57
 * @Version: v1.0
 */
public interface ComConfig {


    Map<String, String> BET_ID = ImmutableMap.<String, String>builder().put("GDKLC", "0").put("CQSSC", "1").put("PK10", "2")
            .put("XYFT", "9").put("JS10", "10").put("JSSSC", "11").build();


    Map<String, String> BET_URL = ImmutableMap.<String, String>builder().put("GDKLC", "L_KL10").put("CQSSC", "L_CQSC").put("PK10", "L_PK10")
            .put("XYFT", "L_XYFT5").put("JS10", "L_JSCAR").put("JSSSC", "L_SPEED5").build();


    Map<String, String> BET_CODE = ImmutableMap.<String, String>builder().put("GDKLC", "kl10").put("PK10", "pk10").put("XYFT", "xyft5")
            .put("JS10", "jscar").put("CQSSC", "cqsc").put("JSSSC", "speed5").build();


    Map<String, String> GAME_RANK_CODE = ImmutableMap.<String, String>builder().put("後三", "后三").put("冠、亞軍和","冠亚")
            .put("冠亞和值","冠亚").put("冠軍","第一名").put("冠軍龍虎","第一名").put("亞軍","第二名").put("亞軍龍虎","第二名")
            .put("季軍","第三名").put("季軍龍虎","第三名").build();


    /**
     * 双面
     */
    List<String> DOUBLE_SIDE = ImmutableList.<String>builder().add("大").add("小").add("单").add("双").add("龙").add("虎")
            .add("和").add("合单").add("合双").add("尾大").add("尾小").build();

    /**
     * 冠亚和
     */
    List<String> SUM_DT = ImmutableList.<String>builder().add("冠亚|3").add("冠亚|4").add("冠亚|5").add("冠亚|6").add("冠亚|7")
            .add("冠亚|8").add("冠亚|9").add("冠亚|10").add("冠亚|11").add("冠亚|12").add("冠亚|13").add("冠亚|14").add("冠亚|15")
            .add("冠亚|16").add("冠亚|17").add("冠亚|18").add("冠亚|19").add("豹子").add("顺子").add("对子").add("半顺").add("杂六")
			.add("东").add("南").add("西").add("北").add("中").add("发").add("白").build();

}
