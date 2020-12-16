package com.ibm.common.utils.handicap.config;

import com.google.common.collect.ImmutableMap;
import com.ibm.common.core.configs.Ncom1Config;

import java.util.Map;

/**
 * @Description: 新com1球号
 * @Author: zjj
 * @Date: 2019-12-27 14:00
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public interface Ncom1BallConfig extends Ncom1Config {




    Map<String, String> BALL_CODE = ImmutableMap.<String, String>builder().put("第一球|0", "p-1_0").put("第一球|1", "p-1_1")
            .put("第一球|2", "p-1_2").put("第一球|3", "p-1_3").put("第一球|4", "p-1_4").put("第一球|5", "p-1_5")
            .put("第一球|6", "p-1_6").put("第一球|7", "p-1_7").put("第一球|8", "p-1_8").put("第一球|9", "p-1_9")
            .put("第一球|单", "lm-1_dsd").put("第一球|双", "lm-1_dss").put("第一球|大", "lm-1_dxd").put("第一球|小", "lm-1_dxx")

            .put("第二球|0", "p-2_0").put("第二球|1", "p-2_1").put("第二球|2", "p-2_2").put("第二球|3", "p-2_3")
            .put("第二球|4", "p-2_4").put("第二球|5", "p-2_5").put("第二球|6", "p-2_6").put("第二球|7", "p-2_7")
            .put("第二球|8", "p-2_8").put("第二球|9", "p-2_9").put("第二球|单", "lm-2_dsd").put("第二球|双", "lm-2_dss")
            .put("第二球|大", "lm-2_dxd").put("第二球|小", "lm-2_dxx")

            .put("第三球|0", "p-3_0").put("第三球|1", "p-3_1").put("第三球|2", "p-3_2").put("第三球|3", "p-3_3")
            .put("第三球|4", "p-3_4").put("第三球|5", "p-3_5").put("第三球|6", "p-3_6").put("第三球|7", "p-3_7")
            .put("第三球|8", "p-3_8").put("第三球|9", "p-3_9").put("第三球|单", "lm-3_dsd").put("第三球|双", "lm-3_dss")
            .put("第三球|大", "lm-3_dxd").put("第三球|小", "lm-3_dxx")

            .put("第四球|0", "p-4_0").put("第四球|1", "p-4_1").put("第四球|2", "p-4_2").put("第四球|3", "p-4_3")
            .put("第四球|4", "p-4_4").put("第四球|5", "p-4_5").put("第四球|6", "p-4_6").put("第四球|7", "p-4_7")
            .put("第四球|8", "p-4_8").put("第四球|9", "p-4_9").put("第四球|单", "lm-4_dsd").put("第四球|双", "lm-4_dss")
            .put("第四球|大", "lm-4_dxd").put("第四球|小", "lm-4_dxx")

            .put("第五球|0", "p-5_0").put("第五球|1", "p-5_1").put("第五球|2", "p-5_2").put("第五球|3", "p-5_3")
            .put("第五球|4", "p-5_4").put("第五球|5", "p-5_5").put("第五球|6", "p-5_6").put("第五球|7", "p-5_7")
            .put("第五球|8", "p-5_8").put("第五球|9", "p-5_9").put("第五球|单", "lm-5_dsd").put("第五球|双", "lm-5_dss")
            .put("第五球|大", "lm-5_dxd").put("第五球|小", "lm-5_dxx")

            .put("总和|大", "lm-zh_dxd").put("总和|小", "lm-zh_dxx").put("总和|单", "lm-zh_dsd").put("总和|双", "lm-zh_dss")
            .put("龙虎和|龙", "lh-l").put("龙虎和|虎", "lh-h").put("龙虎和|和", "he-he")
            .put("前三|豹子", "bz-q3").put("中三|豹子", "bz-z3").put("后三|豹子", "bz-h3")
            .put("前三|顺子", "sz-q3").put("中三|顺子", "sz-z3").put("后三|顺子", "sz-h3")
            .put("前三|对子", "dz-q3").put("中三|对子", "dz-z3").put("后三|对子", "dz-h3")
            .put("前三|半顺", "bs-q3").put("中三|半顺", "bs-z3").put("后三|半顺", "bs-h3")
            .put("前三|杂六", "z6-q3").put("中三|杂六", "z6-z3").put("后三|杂六", "z6-h3").build();


    Map<String, String> BET_INFO_CODE = ImmutableMap.<String, String>builder().put("第一球0", "第一球|0").put("第一球1", "第一球|1")
            .put("第一球2", "第一球|2").put("第一球3", "第一球|3").put("第一球4", "第一球|4").put("第一球5", "第一球|5")
            .put("第一球6", "第一球|6").put("第一球7", "第一球|7").put("第一球8", "第一球|8").put("第一球9", "第一球|9")
            .put("第一球单", "第一球|单").put("第一球双", "第一球|双").put("第一球大", "第一球|大").put("第一球小", "第一球|小")

            .put("第二球0", "第二球|0").put("第二球1", "第二球|1").put("第二球2", "第二球|2").put("第二球3", "第二球|3")
            .put("第二球4", "第二球|4").put("第二球5", "第二球|5").put("第二球6", "第二球|6").put("第二球7", "第二球|7")
            .put("第二球8", "第二球|8").put("第二球9", "第二球|9").put("第二球单", "第二球|单").put("第二球双", "第二球|双")
            .put("第二球大", "第二球|大").put("第二球小", "第二球|小")

            .put("第三球0", "第三球|0").put("第三球1", "第三球|1").put("第三球2", "第三球|2").put("第三球3", "第三球|3")
            .put("第三球4", "第三球|4").put("第三球5", "第三球|5").put("第三球6", "第三球|6").put("第三球7", "第三球|7")
            .put("第三球8", "第三球|8").put("第三球9", "第三球|9").put("第三球单", "第三球|单").put("第三球双", "第三球|双")
            .put("第三球大", "第三球|大").put("第三球小", "第三球|小")

            .put("第四球0", "第四球|0").put("第四球1", "第四球|1").put("第四球2", "第四球|2").put("第四球3", "第四球|3")
            .put("第四球4", "第四球|4").put("第四球5", "第四球|5").put("第四球6", "第四球|6").put("第四球7", "第四球|7")
            .put("第四球8", "第四球|8").put("第四球9", "第四球|9").put("第四球单", "第四球|单").put("第四球双", "第四球|双")
            .put("第四球大", "第四球|大").put("第四球小", "第四球|小")

            .put("第五球0", "第五球|0").put("第五球1", "第五球|1").put("第五球2", "第五球|2").put("第五球3", "第五球|3")
            .put("第五球4", "第五球|4").put("第五球5", "第五球|5").put("第五球6", "第五球|6").put("第五球7", "第五球|7")
            .put("第五球8", "第五球|8").put("第五球9", "第五球|9").put("第五球单", "第五球|单").put("第五球双", "第五球|双")
            .put("第五球大", "第五球|大").put("第五球小", "第五球|小")

            .put("总和大", "总和|大").put("总和小", "总和|小").put("总和单", "总和|单").put("总和双", "总和|双")
            .put("龙", "龙虎和|龙").put("虎", "龙虎和|虎").put("和", "龙虎和|和")
            .put("豹子前三", "前三|豹子").put("豹子中三", "中三|豹子").put("豹子后三", "后三|豹子")
            .put("顺子前三", "前三|顺子").put("顺子中三", "中三|顺子").put("顺子后三", "后三|顺子")
            .put("对子前三", "前三|对子").put("对子中三", "中三|对子").put("对子后三", "后三|对子")
            .put("半顺前三", "前三|半顺").put("半顺中三", "中三|半顺").put("半顺后三", "后三|半顺")
            .put("杂六前三", "前三|杂六").put("杂六中三", "中三|杂六").put("杂六后三", "后三|杂六").build();


    Map<String, String> BET_LIMIT_CODE = ImmutableMap.<String, String>builder().put("第一球|0", "00").put("第一球|1", "00")
            .put("第一球|2", "00").put("第一球|3", "00").put("第一球|4", "00").put("第一球|5", "00").put("第一球|6", "00")
            .put("第一球|7", "00").put("第一球|8", "00").put("第一球|9", "00").put("第一球|大", "01").put("第一球|小", "01")
            .put("第一球|单", "01").put("第一球|双", "01")

            .put("第二球|0", "00").put("第二球|1", "00").put("第二球|2", "00").put("第二球|3", "00").put("第二球|4", "00")
            .put("第二球|5", "00").put("第二球|6", "00").put("第二球|7", "00").put("第二球|8", "00").put("第二球|9", "00")
            .put("第二球|大", "01").put("第二球|小", "01").put("第二球|单", "01").put("第二球|双", "01")

            .put("第三球|0", "00").put("第三球|1", "00").put("第三球|2", "00").put("第三球|3", "00").put("第三球|4", "00")
            .put("第三球|5", "00").put("第三球|6", "00").put("第三球|7", "00").put("第三球|8", "00").put("第三球|9", "00")
            .put("第三球|大", "01").put("第三球|小", "01").put("第三球|单", "01").put("第三球|双", "01")

            .put("第四球|0", "00").put("第四球|1", "00").put("第四球|2", "00").put("第四球|3", "00").put("第四球|4", "00")
            .put("第四球|5", "00").put("第四球|6", "00").put("第四球|7", "00").put("第四球|8", "00").put("第四球|9", "00")
            .put("第四球|大", "01").put("第四球|小", "01").put("第四球|单", "01").put("第四球|双", "01")

            .put("第五球|0", "00").put("第五球|1", "00").put("第五球|2", "00").put("第五球|3", "00").put("第五球|4", "00")
            .put("第五球|5", "00").put("第五球|6", "00").put("第五球|7", "00").put("第五球|8", "00").put("第五球|9", "00")
            .put("第五球|大", "01").put("第五球|小", "01").put("第五球|单", "01").put("第五球|双", "01")

            .put("总和|大", "01").put("总和|小", "01").put("总和|单", "01").put("总和|双", "01")
            .put("龙虎和|龙", "02").put("龙虎和|虎", "02").put("龙虎和|和", "03")
            .put("前三|豹子", "04").put("中三|豹子", "04").put("后三|豹子", "04")
            .put("前三|顺子", "05").put("中三|顺子", "05").put("后三|顺子", "05")
            .put("前三|对子", "06").put("中三|对子", "06").put("后三|对子", "06")
            .put("前三|半顺", "07").put("中三|半顺", "07").put("后三|半顺", "07")
            .put("前三|杂六", "08").put("中三|杂六", "08").put("后三|杂六", "08").build();


}
