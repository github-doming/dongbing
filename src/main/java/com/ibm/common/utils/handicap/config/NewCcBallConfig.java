package com.ibm.common.utils.handicap.config;

import com.google.common.collect.ImmutableMap;
import com.ibm.common.core.configs.BWConfig;

import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/30 11:27
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public interface NewCcBallConfig extends BWConfig {


    /**
     * 游戏code
     */
    Map<String, String> BALL_CODE = ImmutableMap.<String, String>builder()
            .put("第一球|0", "00").put("第一球|1", "01").put("第一球|2", "02").put("第一球|3", "03")
            .put("第一球|4", "04").put("第一球|5", "05").put("第一球|6", "06").put("第一球|7", "07")
            .put("第一球|8", "08").put("第一球|9", "09").put("第一球|大", "10").put("第一球|小", "11")
            .put("第一球|单", "12").put("第一球|双", "13")

            .put("第二球|0", "14").put("第二球|1", "15").put("第二球|2", "16").put("第二球|3", "17")
            .put("第二球|4", "18").put("第二球|5", "19").put("第二球|6", "29").put("第二球|7", "21")
            .put("第二球|8", "22").put("第二球|9", "23").put("第二球|大", "24").put("第二球|小", "25")
            .put("第二球|单", "26").put("第二球|双", "27")

            .put("第三球|0", "28").put("第三球|1", "29").put("第三球|2", "30").put("第三球|3", "31")
            .put("第三球|4", "32").put("第三球|5", "33").put("第三球|6", "34").put("第三球|7", "35")
            .put("第三球|8", "36").put("第三球|9", "37").put("第三球|大", "38").put("第三球|小", "39")
            .put("第三球|单", "40").put("第三球|双", "41")

            .put("第四球|0", "42").put("第四球|1", "43").put("第四球|2", "44").put("第四球|3", "45")
            .put("第四球|4", "46").put("第四球|5", "47").put("第四球|6", "48").put("第四球|7", "49")
            .put("第四球|8", "50").put("第四球|9", "51").put("第四球|大", "52").put("第四球|小", "53")
            .put("第四球|单", "54").put("第四球|双", "55")

            .put("第五球|0", "56").put("第五球|1", "57").put("第五球|2", "58").put("第五球|3", "59")
            .put("第五球|4", "60").put("第五球|5", "61").put("第五球|6", "62").put("第五球|7", "63")
            .put("第五球|8", "64").put("第五球|9", "65").put("第五球|大", "66").put("第五球|小", "67")
            .put("第五球|单", "68").put("第五球|双", "69")

            .put("总和|大", "70").put("总和|小", "71").put("总和|单", "72").put("总和|双", "73")
            .put("龙虎和|龙", "74").put("龙虎和|虎", "75").put("龙虎和|和", "76")
            .put("前三|豹子", "77").put("前三|顺子", "78").put("前三|对子", "79").put("前三|半顺", "80").put("前三|杂六", "81")
            .put("中三|豹子", "82").put("中三|顺子", "83").put("中三|对子", "84").put("中三|半顺", "85").put("中三|杂六", "86")
            .put("后三|豹子", "87").put("后三|顺子", "88").put("后三|对子", "89").put("后三|半顺", "90").put("后三|杂六", "91").build();

    /**
     * 投注信息
     */
    Map<String, String> BET_INFO_CODE = ImmutableMap.<String, String>builder().put("第一球-0", "第一球|0").put("第一球-1", "第一球|1")
            .put("第一球-2", "第一球|2").put("第一球-3", "第一球|3").put("第一球-4", "第一球|4").put("第一球-5", "第一球|5")
            .put("第一球-6", "第一球|6").put("第一球-7", "第一球|7").put("第一球-8", "第一球|8").put("第一球-9", "第一球|9")
            .put("第一球-单", "第一球|单").put("第一球-双", "第一球|双").put("第一球-大", "第一球|大").put("第一球-小", "第一球|小")

            .put("第二球-0", "第二球|0").put("第二球-1", "第二球|1").put("第二球-2", "第二球|2").put("第二球-3", "第二球|3")
            .put("第二球-4", "第二球|4").put("第二球-5", "第二球|5").put("第二球-6", "第二球|6").put("第二球-7", "第二球|7")
            .put("第二球-8", "第二球|8").put("第二球-9", "第二球|9").put("第二球-单", "第二球|单").put("第二球-双", "第二球|双")
            .put("第二球-大", "第二球|大").put("第二球-小", "第二球|小")

            .put("第三球-0", "第三球|0").put("第三球-1", "第三球|1").put("第三球-2", "第三球|2").put("第三球-3", "第三球|3")
            .put("第三球-4", "第三球|4").put("第三球-5", "第三球|5").put("第三球-6", "第三球|6").put("第三球-7", "第三球|7")
            .put("第三球-8", "第三球|8").put("第三球-9", "第三球|9").put("第三球-单", "第三球|单").put("第三球-双", "第三球|双")
            .put("第三球-大", "第三球|大").put("第三球-小", "第三球|小")

            .put("第四球-0", "第四球|0").put("第四球-1", "第四球|1").put("第四球-2", "第四球|2").put("第四球-3", "第四球|3")
            .put("第四球-4", "第四球|4").put("第四球-5", "第四球|5").put("第四球-6", "第四球|6").put("第四球-7", "第四球|7")
            .put("第四球-8", "第四球|8").put("第四球-9", "第四球|9").put("第四球-单", "第四球|单").put("第四球-双", "第四球|双")
            .put("第四球-大", "第四球|大").put("第四球-小", "第四球|小")

            .put("第五球-0", "第五球|0").put("第五球-1", "第五球|1").put("第五球-2", "第五球|2").put("第五球-3", "第五球|3")
            .put("第五球-4", "第五球|4").put("第五球-5", "第五球|5").put("第五球-6", "第五球|6").put("第五球-7", "第五球|7")
            .put("第五球-8", "第五球|8").put("第五球-9", "第五球|9").put("第五球-单", "第五球|单").put("第五球-双", "第五球|双")
            .put("第五球-大", "第五球|大").put("第五球-小", "第五球|小")

            .put("总和大", "总和|大").put("总和小", "总和|小").put("总和单", "总和|单").put("总和双", "总和|双")
            .put("龙", "龙虎和|龙").put("虎", "龙虎和|虎").put("和", "龙虎和|和")
            .put("前三-豹子", "前三|豹子").put("中三-豹子", "中三|豹子").put("后三-豹子", "后三|豹子")
            .put("前三-顺子", "前三|顺子").put("中三-顺子", "中三|顺子").put("后三-顺子", "后三|顺子")
            .put("前三-对子", "前三|对子").put("中三-对子", "中三|对子").put("后三-对子", "后三|对子")
            .put("前三-半顺", "前三|半顺").put("中三-半顺", "中三|半顺").put("后三-半顺", "后三|半顺")
            .put("前三-杂六", "前三|杂六").put("中三-杂六", "中三|杂六").put("后三-杂六", "后三|杂六").build();


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

            .put("总和|大", "01").put("总和|小", "01").put("总和|单", "01").put("总和|双", "01").put("龙虎和|龙", "02")
            .put("龙虎和|虎", "02").put("龙虎和|和", "03").put("前三|豹子", "04").put("中三|豹子", "04").put("后三|豹子", "04")
            .put("前三|顺子", "04").put("中三|顺子", "04").put("后三|顺子", "04").put("前三|对子", "04").put("中三|对子", "04")
            .put("后三|对子", "04").put("前三|半顺", "04").put("中三|半顺", "04").put("后三|半顺", "04").put("前三|杂六", "04")
            .put("中三|杂六", "04").put("后三|杂六", "04").build();
}