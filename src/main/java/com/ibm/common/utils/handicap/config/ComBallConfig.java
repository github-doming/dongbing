package com.ibm.common.utils.handicap.config;

import com.google.common.collect.ImmutableMap;
import com.ibm.common.core.configs.ComConfig;

import java.util.Map;

/**
 * @Description: COM球类
 * @Author: zjj
 * @Date: 2020-04-27 17:44
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public interface ComBallConfig extends ComConfig {

    /**
     * 游戏code
     */
    Map<String, String> GAME_TYPE_CODE = ImmutableMap.<String, String>builder().put("dobleSides",
            "lmp").put("ballNO", "d1_5").put("sumDT", "d1").build();

    /**
     * 游戏code
     */
    Map<String, String> GAME_ODDS_CODE = ImmutableMap.<String, String>builder().put("dobleSides",
            "2,3,5,6,8,9,11,12,14,15,16,17,18")
            .put("ballNO", "1,4,7,10,13").put("sumDT", "1,2,3,16,17,18,19,20,21").build();


    Map<String, String> BALL_CODE = ImmutableMap.<String, String>builder().put("第一球|0", "1_1")
            .put("第一球|1", "1_2").put("第一球|2", "1_3").put("第一球|3", "1_4").put("第一球|4", "1_5")
            .put("第一球|5", "1_6").put("第一球|6", "1_7").put("第一球|7", "1_8").put("第一球|8", "1_9")
            .put("第一球|9", "1_10").put("第一球|大", "2_11").put("第一球|小", "2_12")
            .put("第一球|单", "3_13").put("第一球|双", "3_14")

            .put("第二球|0", "4_15").put("第二球|1", "4_16").put("第二球|2", "4_17").put("第二球|3", "4_18")
            .put("第二球|4", "4_19").put("第二球|5", "4_20").put("第二球|6", "4_21").put("第二球|7", "4_22")
            .put("第二球|8", "4_23").put("第二球|9", "4_24") .put("第二球|大", "5_25").put("第二球|小", "5_26")
            .put("第二球|单", "6_27").put("第二球|双", "6_28")


            .put("第三球|0", "7_29").put("第三球|1", "7_30").put("第三球|2", "7_31").put("第三球|3", "7_32")
            .put("第三球|4", "7_33").put("第三球|5", "7_34").put("第三球|6", "7_35").put("第三球|7", "7_36")
            .put("第三球|8", "7_37").put("第三球|9", "7_38").put("第三球|大", "8_39").put("第三球|小", "8_40")
            .put("第三球|单", "9_41").put("第三球|双", "9_42")

            .put("第四球|0", "10_43").put("第四球|1", "10_44").put("第四球|2", "10_45").put("第四球|3", "10_46")
            .put("第四球|4", "10_47").put("第四球|5", "10_48").put("第四球|6", "10_49").put("第四球|7", "10_50")
            .put("第四球|8", "10_51").put("第四球|9", "10_52").put("第四球|大", "11_53").put("第四球|小", "11_54")
            .put("第四球|单", "12_55").put("第四球|双", "12_56")

            .put("第五球|0", "13_57").put("第五球|1", "13_58").put("第五球|2", "13_59").put("第五球|3", "13_60")
            .put("第五球|4", "13_61").put("第五球|5", "13_62").put("第五球|6", "13_63").put("第五球|7", "13_64")
            .put("第五球|8", "13_65").put("第五球|9", "13_66").put("第五球|大", "14_67").put("第五球|小", "14_68")
            .put("第五球|单", "15_69").put("第五球|双", "15_70")

            .put("总和|大", "16_71").put("总和|小", "16_72").put("总和|单", "17_73").put("总和|双", "17_74")
            .put("龙虎和|龙", "18_75").put("龙虎和|虎", "18_76").put("龙虎和|和", "18_77")
            .put("前三|豹子", "19_78").put("中三|豹子", "20_83").put("后三|豹子", "21_88")
            .put("前三|顺子", "19_79").put("中三|顺子", "20_84").put("后三|顺子", "21_89")
            .put("前三|对子", "19_80").put("中三|对子", "20_85").put("后三|对子", "21_90")
            .put("前三|半顺", "19_81").put("中三|半顺", "20_86").put("后三|半顺", "21_91")
            .put("前三|杂六", "19_82").put("中三|杂六", "20_87").put("后三|杂六", "21_92").build();


    Map<String, String> BET_LIMIT_CODE = ImmutableMap.<String, String>builder().put("第一球|0", "00").put("第一球|1", "00")
            .put("第一球|2", "00").put("第一球|3", "00").put("第一球|4", "00").put("第一球|5", "00").put("第一球|6", "00")
            .put("第一球|7", "00").put("第一球|8", "00").put("第一球|9", "00").put("第一球|大", "01").put("第一球|小", "01")
            .put("第一球|单", "02").put("第一球|双", "02")

            .put("第二球|0", "00").put("第二球|1", "00").put("第二球|2", "00").put("第二球|3", "00").put("第二球|4", "00")
            .put("第二球|5", "00").put("第二球|6", "00").put("第二球|7", "00").put("第二球|8", "00").put("第二球|9", "00")
            .put("第二球|大", "01").put("第二球|小", "01").put("第二球|单", "02").put("第二球|双", "02")

            .put("第三球|0", "00").put("第三球|1", "00").put("第三球|2", "00").put("第三球|3", "00").put("第三球|4", "00")
            .put("第三球|5", "00").put("第三球|6", "00").put("第三球|7", "00").put("第三球|8", "00").put("第三球|9", "00")
            .put("第三球|大", "01").put("第三球|小", "01").put("第三球|单", "02").put("第三球|双", "02")

            .put("第四球|0", "00").put("第四球|1", "00").put("第四球|2", "00").put("第四球|3", "00").put("第四球|4", "00")
            .put("第四球|5", "00").put("第四球|6", "00").put("第四球|7", "00").put("第四球|8", "00").put("第四球|9", "00")
            .put("第四球|大", "01").put("第四球|小", "01").put("第四球|单", "02").put("第四球|双", "02")

            .put("第五球|0", "00").put("第五球|1", "00").put("第五球|2", "00").put("第五球|3", "00").put("第五球|4", "00")
            .put("第五球|5", "00").put("第五球|6", "00").put("第五球|7", "00").put("第五球|8", "00").put("第五球|9", "00")
            .put("第五球|大", "01").put("第五球|小", "01").put("第五球|单", "02").put("第五球|双", "02")

            .put("总和|大", "03").put("总和|小", "03").put("总和|单", "04").put("总和|双", "04").put("龙虎和|龙", "05")
            .put("龙虎和|虎", "05").put("龙虎和|和", "05").put("前三|豹子", "06").put("中三|豹子", "07").put("后三|豹子", "08")
            .put("前三|顺子", "06").put("中三|顺子", "07").put("后三|顺子", "08").put("前三|对子", "06").put("中三|对子", "07")
            .put("后三|对子", "08").put("前三|半顺", "06").put("中三|半顺", "07").put("后三|半顺", "08").put("前三|杂六", "06")
            .put("中三|杂六", "07").put("后三|杂六", "08").build();
}
