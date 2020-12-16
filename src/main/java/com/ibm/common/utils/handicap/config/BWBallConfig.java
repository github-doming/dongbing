package com.ibm.common.utils.handicap.config;

import com.google.common.collect.ImmutableMap;
import com.ibm.common.core.configs.BWConfig;
import com.ibm.common.core.configs.Ncom2Config;

import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/30 11:27
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public interface BWBallConfig extends BWConfig {

    /**
     * 游戏code
     */
    Map<String, String> BALL_CODE = ImmutableMap.<String, String>builder()
            .put("第一球|0", "280100").put("第一球|1", "280101").put("第一球|2", "280102").put("第一球|3", "280103")
            .put("第一球|4", "280104").put("第一球|5", "280105").put("第一球|6", "280106").put("第一球|7", "280107")
            .put("第一球|8", "280108").put("第一球|9", "280109").put("第一球|大", "210101").put("第一球|小", "210102")
            .put("第一球|单", "220101").put("第一球|双", "220102")

            .put("第二球|0", "280200").put("第二球|1", "280201").put("第二球|2", "280202").put("第二球|3", "280203")
            .put("第二球|4", "280204").put("第二球|5", "280205").put("第二球|6", "280206").put("第二球|7", "280207")
            .put("第二球|8", "280208").put("第二球|9", "280209").put("第二球|大", "210201").put("第二球|小", "210202")
            .put("第二球|单", "220201").put("第二球|双", "220202")

            .put("第三球|0", "280300").put("第三球|1", "280301").put("第三球|2", "280302").put("第三球|3", "280303")
            .put("第三球|4", "280304").put("第三球|5", "280305").put("第三球|6", "280306").put("第三球|7", "280307")
            .put("第三球|8", "280308").put("第三球|9", "280309").put("第三球|大", "210301").put("第三球|小", "210302")
            .put("第三球|单", "220301").put("第三球|双", "220302")

            .put("第四球|0", "280400").put("第四球|1", "280401").put("第四球|2", "280402").put("第四球|3", "280403")
            .put("第四球|4", "280404").put("第四球|5", "280405").put("第四球|6", "280406").put("第四球|7", "280407")
            .put("第四球|8", "280408").put("第四球|9", "280409").put("第四球|大", "210401").put("第四球|小", "210402")
            .put("第四球|单", "220401").put("第四球|双", "220402")

            .put("第五球|0", "280500").put("第五球|1", "280501").put("第五球|2", "280502").put("第五球|3", "280503")
            .put("第五球|4", "280504").put("第五球|5", "280505").put("第五球|6", "280506").put("第五球|7", "280507")
            .put("第五球|8", "280508").put("第五球|9", "280509").put("第五球|大", "210501").put("第五球|小", "210502")
            .put("第五球|单", "220501").put("第五球|双", "220502")

            .put("总和|大", "210001").put("总和|小", "210002").put("总和|单", "220001").put("总和|双", "220002")
            .put("龙虎和|龙", "250001").put("龙虎和|虎", "250002").put("龙虎和|和", "250003")
            .put("前三|豹子", "290101").put("前三|顺子", "290201").put("前三|对子", "290301").put("前三|半顺", "290401").put("前三|杂六", "290501")
            .put("中三|豹子", "290102").put("中三|顺子", "290202").put("中三|对子", "290302").put("中三|半顺", "290402").put("中三|杂六", "290502")
            .put("后三|豹子", "290103").put("后三|顺子", "290203").put("后三|对子", "290303").put("后三|半顺", "290403").put("后三|杂六", "290503").build();

    /**
     * 投注信息
     */
    Map<String, String> BET_INFO_CODE = ImmutableMap.<String, String>builder().put("第一球0", "第一球|0").put("第一球1", "第一球|1")
            .put("第一球2", "第一球|2").put("第一球3", "第一球|3").put("第一球4", "第一球|4").put("第一球5", "第一球|5")
            .put("第一球6", "第一球|6").put("第一球7", "第一球|7").put("第一球8", "第一球|8").put("第一球9", "第一球|9")
            .put("第一球單", "第一球|单").put("第一球雙", "第一球|双").put("第一球大", "第一球|大").put("第一球小", "第一球|小")

            .put("第二球0", "第二球|0").put("第二球1", "第二球|1").put("第二球2", "第二球|2").put("第二球3", "第二球|3")
            .put("第二球4", "第二球|4").put("第二球5", "第二球|5").put("第二球6", "第二球|6").put("第二球7", "第二球|7")
            .put("第二球8", "第二球|8").put("第二球9", "第二球|9").put("第二球單", "第二球|单").put("第二球雙", "第二球|双")
            .put("第二球大", "第二球|大").put("第二球小", "第二球|小")

            .put("第三球0", "第三球|0").put("第三球1", "第三球|1").put("第三球2", "第三球|2").put("第三球3", "第三球|3")
            .put("第三球4", "第三球|4").put("第三球5", "第三球|5").put("第三球6", "第三球|6").put("第三球7", "第三球|7")
            .put("第三球8", "第三球|8").put("第三球9", "第三球|9").put("第三球單", "第三球|单").put("第三球雙", "第三球|双")
            .put("第三球大", "第三球|大").put("第三球小", "第三球|小")

            .put("第四球0", "第四球|0").put("第四球1", "第四球|1").put("第四球2", "第四球|2").put("第四球3", "第四球|3")
            .put("第四球4", "第四球|4").put("第四球5", "第四球|5").put("第四球6", "第四球|6").put("第四球7", "第四球|7")
            .put("第四球8", "第四球|8").put("第四球9", "第四球|9").put("第四球單", "第四球|单").put("第四球雙", "第四球|双")
            .put("第四球大", "第四球|大").put("第四球小", "第四球|小")

            .put("第五球0", "第五球|0").put("第五球1", "第五球|1").put("第五球2", "第五球|2").put("第五球3", "第五球|3")
            .put("第五球4", "第五球|4").put("第五球5", "第五球|5").put("第五球6", "第五球|6").put("第五球7", "第五球|7")
            .put("第五球8", "第五球|8").put("第五球9", "第五球|9").put("第五球單", "第五球|单").put("第五球雙", "第五球|双")
            .put("第五球大", "第五球|大").put("第五球小", "第五球|小")

            .put("總和大", "总和|大").put("總和小", "总和|小").put("總和單", "总和|单").put("總和雙", "总和|双")
            .put("總和龍", "龙虎和|龙").put("總和虎", "龙虎和|虎").put("總和和局", "龙虎和|和")
            .put("前三豹子", "前三|豹子").put("中三豹子", "中三|豹子").put("後三豹子", "后三|豹子")
            .put("前三順子", "前三|顺子").put("中三順子", "中三|顺子").put("後三順子", "后三|顺子")
            .put("前三對子", "前三|对子").put("中三對子", "中三|对子").put("後三對子", "后三|对子")
            .put("前三半順", "前三|半顺").put("中三半順", "中三|半顺").put("後三半順", "后三|半顺")
            .put("前三雜六", "前三|杂六").put("中三雜六", "中三|杂六").put("後三雜六", "后三|杂六").build();

    /**
     * 限额信息
     */
    Map<String, String> BET_LIMIT_CODE = ImmutableMap.<String, String>builder().put("第一球|0", "00").put("第一球|1", "00")
            .put("第一球|2", "00").put("第一球|3", "00").put("第一球|4", "00").put("第一球|5", "00").put("第一球|6", "00")
            .put("第一球|7", "00").put("第一球|8", "00").put("第一球|9", "00").put("第一球|大", "00").put("第一球|小", "00")
            .put("第一球|单", "00").put("第一球|双", "00")

            .put("第二球|0", "00").put("第二球|1", "00").put("第二球|2", "00").put("第二球|3", "00").put("第二球|4", "00")
            .put("第二球|5", "00").put("第二球|6", "00").put("第二球|7", "00").put("第二球|8", "00").put("第二球|9", "00")
            .put("第二球|大", "00").put("第二球|小", "00").put("第二球|单", "00").put("第二球|双", "00")

            .put("第三球|0", "00").put("第三球|1", "00").put("第三球|2", "00").put("第三球|3", "00").put("第三球|4", "00")
            .put("第三球|5", "00").put("第三球|6", "00").put("第三球|7", "00").put("第三球|8", "00").put("第三球|9", "00")
            .put("第三球|大", "00").put("第三球|小", "00").put("第三球|单", "00").put("第三球|双", "00")

            .put("第四球|0", "00").put("第四球|1", "00").put("第四球|2", "00").put("第四球|3", "00").put("第四球|4", "00")
            .put("第四球|5", "00").put("第四球|6", "00").put("第四球|7", "00").put("第四球|8", "00").put("第四球|9", "00")
            .put("第四球|大", "00").put("第四球|小", "00").put("第四球|单", "00").put("第四球|双", "00")

            .put("第五球|0", "00").put("第五球|1", "00").put("第五球|2", "00").put("第五球|3", "00").put("第五球|4", "00")
            .put("第五球|5", "00").put("第五球|6", "00").put("第五球|7", "00").put("第五球|8", "00").put("第五球|9", "00")
            .put("第五球|大", "00").put("第五球|小", "00").put("第五球|单", "00").put("第五球|双", "00")

            .put("总和|大", "00").put("总和|小", "00").put("总和|单", "00").put("总和|双", "00")
            .put("龙虎和|龙", "00").put("龙虎和|虎", "00").put("龙虎和|和", "00")
            .put("前三|豹子", "00").put("中三|豹子", "00").put("后三|豹子", "00")
            .put("前三|顺子", "00").put("中三|顺子", "00").put("后三|顺子", "00")
            .put("前三|对子", "00").put("中三|对子", "00").put("后三|对子", "00")
            .put("前三|半顺", "00").put("中三|半顺", "00").put("后三|半顺", "00")
            .put("前三|杂六", "00").put("中三|杂六", "00").put("后三|杂六", "00").build();
}