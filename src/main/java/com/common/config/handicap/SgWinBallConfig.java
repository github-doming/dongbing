package com.common.config.handicap;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * SgWin 盘口 球号类玩法配置类
 * @Author: Dongming
 * @Date: 2020-06-09 14:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface SgWinBallConfig extends SgWinConfig,BallConfig {
	/**
	 * 游戏code
	 */
	Map<String, String> BET_TYPE = ImmutableMap.<String, String>builder()
			.put("dobleSides", "DX1,DX2,DX3,DX4,DX5,DS1,DS2,DS3,DS4,DS5,ZDX,ZDS,LH,TS1,TS2,TS3")
			.put("ballNO", "X1,DX2,DX3,DX4,DX5,DS1,DS2,DS3,DS4,DS5,B1,B2,B3,B4,B5").build();

	Map<String, String> ITEM_CODE = ImmutableMap.<String, String>builder().put("第一球|0", "第一球:B1:0")
			.put("第一球|1", "第一球:B1:1").put("第一球|2", "第一球:B1:2").put("第一球|3", "第一球:B1:3").put("第一球|4", "第一球:B1:4")
			.put("第一球|5", "第一球:B1:5").put("第一球|6", "第一球:B1:6").put("第一球|7", "第一球:B1:7").put("第一球|8", "第一球:B1:8")
			.put("第一球|9", "第一球:B1:9").put("第一球|单", "第一球:DS1:D").put("第一球|双", "第一球:DS1:S").put("第一球|大", "第一球:DX1:D")
			.put("第一球|小", "第一球:DX1:X")

			.put("第二球|0", "第二球:B2:0").put("第二球|1", "第二球:B2:1").put("第二球|2", "第二球:B2:2").put("第二球|3", "第二球:B2:3")
			.put("第二球|4", "第二球:B2:4").put("第二球|5", "第二球:B2:5").put("第二球|6", "第二球:B2:6").put("第二球|7", "第二球:B2:7")
			.put("第二球|8", "第二球:B2:8").put("第二球|9", "第二球:B2:9").put("第二球|单", "第二球:DS2:D").put("第二球|双", "第二球:DS2:S")
			.put("第二球|大", "第二球:DX2:D").put("第二球|小", "第二球:DX2:X")

			.put("第三球|0", "第三球:B3:0").put("第三球|1", "第三球:B3:1").put("第三球|2", "第三球:B3:2").put("第三球|3", "第三球:B3:3")
			.put("第三球|4", "第三球:B3:4").put("第三球|5", "第三球:B3:5").put("第三球|6", "第三球:B3:6").put("第三球|7", "第三球:B3:7")
			.put("第三球|8", "第三球:B3:8").put("第三球|9", "第三球:B3:9").put("第三球|单", "第三球:DS3:D").put("第三球|双", "第三球:DS3:S")
			.put("第三球|大", "第三球:DX3:D").put("第三球|小", "第三球:DX3:X")

			.put("第四球|0", "第四球:B4:0").put("第四球|1", "第四球:B4:1").put("第四球|2", "第四球:B4:2").put("第四球|3", "第四球:B4:3")
			.put("第四球|4", "第四球:B4:4").put("第四球|5", "第四球:B4:5").put("第四球|6", "第四球:B4:6").put("第四球|7", "第四球:B4:7")
			.put("第四球|8", "第四球:B4:8").put("第四球|9", "第四球:B4:9").put("第四球|单", "第四球:DS4:D").put("第四球|双", "第四球:DS4:S")
			.put("第四球|大", "第四球:DX4:D").put("第四球|小", "第四球:DX4:X")

			.put("第五球|0", "第五球:B5:0").put("第五球|1", "第五球:B5:1").put("第五球|2", "第五球:B5:2").put("第五球|3", "第五球:B5:3")
			.put("第五球|4", "第五球:B5:4").put("第五球|5", "第五球:B5:5").put("第五球|6", "第五球:B5:6").put("第五球|7", "第五球:B5:7")
			.put("第五球|8", "第五球:B5:8").put("第五球|9", "第五球:B5:9").put("第五球|单", "第五球:DS5:D").put("第五球|双", "第五球:DS5:S")
			.put("第五球|大", "第五球:DX5:D").put("第五球|小", "第五球:DX5:X")

			.put("总和|大", ":ZDX:D").put("总和|小", ":ZDX:X").put("总和|单", ":ZDS:D").put("总和|双", ":ZDS:S")
			.put("龙虎和|龙", ":LH:L").put("龙虎和|虎", ":LH:H").put("龙虎和|和", ":LH:T").put("前三|豹子", "前三:TS1:0")
			.put("中三|豹子", "中三:TS2:0").put("后三|豹子", "后三:TS3:0").put("前三|顺子", "前三:TS1:1").put("中三|顺子", "中三:TS2:1")
			.put("后三|顺子", "后三:TS3:1").put("前三|对子", "前三:TS1:2").put("中三|对子", "中三:TS2:2").put("后三|对子", "后三:TS3:2")
			.put("前三|半顺", "前三:TS1:3").put("中三|半顺", "中三:TS2:3").put("后三|半顺", "后三:TS3:3").put("前三|杂六", "前三:TS1:4")
			.put("中三|杂六", "中三:TS2:4").put("后三|杂六", "后三:TS3:4").build();



	Map<String, String> INFO_ITEM = ImmutableMap.<String, String>builder().put("第一球0", "第一球|0").put("第一球1", "第一球|1")
			.put("第一球2", "第一球|2").put("第一球3", "第一球|3").put("第一球4", "第一球|4").put("第一球5", "第一球|5").put("第一球6", "第一球|6")
			.put("第一球7", "第一球|7").put("第一球8", "第一球|8").put("第一球9", "第一球|9").put("第一球单", "第一球|单").put("第一球双", "第一球|双")
			.put("第一球大", "第一球|大").put("第一球小", "第一球|小")

			.put("第二球0", "第二球|0").put("第二球1", "第二球|1").put("第二球2", "第二球|2").put("第二球3", "第二球|3").put("第二球4", "第二球|4")
			.put("第二球5", "第二球|5").put("第二球6", "第二球|6").put("第二球7", "第二球|7").put("第二球8", "第二球|8").put("第二球9", "第二球|9")
			.put("第二球单", "第二球|单").put("第二球双", "第二球|双").put("第二球大", "第二球|大").put("第二球小", "第二球|小")

			.put("第三球0", "第三球|0").put("第三球1", "第三球|1").put("第三球2", "第三球|2").put("第三球3", "第三球|3").put("第三球4", "第三球|4")
			.put("第三球5", "第三球|5").put("第三球6", "第三球|6").put("第三球7", "第三球|7").put("第三球8", "第三球|8").put("第三球9", "第三球|9")
			.put("第三球单", "第三球|单").put("第三球双", "第三球|双").put("第三球大", "第三球|大").put("第三球小", "第三球|小")

			.put("第四球0", "第四球|0").put("第四球1", "第四球|1").put("第四球2", "第四球|2").put("第四球3", "第四球|3").put("第四球4", "第四球|4")
			.put("第四球5", "第四球|5").put("第四球6", "第四球|6").put("第四球7", "第四球|7").put("第四球8", "第四球|8").put("第四球9", "第四球|9")
			.put("第四球单", "第四球|单").put("第四球双", "第四球|双").put("第四球大", "第四球|大").put("第四球小", "第四球|小")

			.put("第五球0", "第五球|0").put("第五球1", "第五球|1").put("第五球2", "第五球|2").put("第五球3", "第五球|3").put("第五球4", "第五球|4")
			.put("第五球5", "第五球|5").put("第五球6", "第五球|6").put("第五球7", "第五球|7").put("第五球8", "第五球|8").put("第五球9", "第五球|9")
			.put("第五球单", "第五球|单").put("第五球双", "第五球|双").put("第五球大", "第五球|大").put("第五球小", "第五球|小")

			.put("总和大", "总和|大").put("总和小", "总和|小").put("总和单", "总和|单").put("总和双", "总和|双").put("龙", "龙虎和|龙")
			.put("虎", "龙虎和|虎").put("和", "龙虎和|和").put("前三豹子", "前三|豹子").put("中三豹子", "中三|豹子").put("后三豹子", "后三|豹子")
			.put("前三顺子", "前三|顺子").put("中三顺子", "中三|顺子").put("后三顺子", "后三|顺子").put("前三对子", "前三|对子").put("中三对子", "中三|对子")
			.put("后三对子", "后三|对子").put("前三半顺", "前三|半顺").put("中三半顺", "中三|半顺").put("后三半顺", "后三|半顺").put("前三杂六", "前三|杂六")
			.put("中三杂六", "中三|杂六").put("后三杂六", "后三|杂六").build();



	Map<String, String> ITEM_LIMIT = ImmutableMap.<String, String>builder().put("第一球|0", "00").put("第一球|1", "00")
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
			.put("龙虎和|虎", "02").put("龙虎和|和", "02").put("前三|豹子", "03").put("中三|豹子", "03").put("后三|豹子", "03")
			.put("前三|顺子", "03").put("中三|顺子", "03").put("后三|顺子", "03").put("前三|对子", "03").put("中三|对子", "03")
			.put("后三|对子", "03").put("前三|半顺", "03").put("中三|半顺", "03").put("后三|半顺", "03").put("前三|杂六", "03")
			.put("中三|杂六", "03").put("后三|杂六", "03").build();

}
