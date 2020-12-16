package com.ibm.common.utils.handicap.config;
import com.google.common.collect.ImmutableMap;
import com.ibm.common.core.configs.SgWinConfig;

import java.util.Map;
/**
 * @Description: 号码
 * @Author: zjj
 * @Date: 2019-10-25 17:13
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public interface SgWinNumberConfig extends SgWinConfig {

	/**
	 * 游戏code
	 */
	Map<String, String> GAME_ODDS_CODE = ImmutableMap.<String, String>builder().put("dobleSides",
			"DX1,DX2,DX3,DX4,DX5,DX6,DX7,DX8,DX9,DX10,DS1,DS2,DS3,DS4,DS5,DS6,DS7,DS8,DS9,DS10,GDX,GDS,LH1,LH2,LH3,LH4,LH5")
			.put("ballNO", "B1,B2,B3,B4,B5,B6,B7,B8,B9,B10").put("sumDT", "GDX,GDS,GYH").build();

	/**
	 * 所有球码code
	 */
	Map<String, String> BALL_CODE = ImmutableMap.<String, String>builder().put("第一名|1", "冠军:B1:1")
			.put("第一名|2", "冠军:B1:2").put("第一名|3", "冠军:B1:3").put("第一名|4", "冠军:B1:4").put("第一名|5", "冠军:B1:5")
			.put("第一名|6", "冠军:B1:6").put("第一名|7", "冠军:B1:7").put("第一名|8", "冠军:B1:8").put("第一名|9", "冠军:B1:9")
			.put("第一名|10", "冠军:B1:10").put("第一名|大", "冠军:DX1:D").put("第一名|小", "冠军:DX1:X").put("第一名|单", "冠军:DS1:D")
			.put("第一名|双", "冠军:DS1:S").put("第一名|龙", "冠军:LH1:L").put("第一名|虎", "冠军:LH1:H")

			.put("第二名|1", "亚军:B2:1").put("第二名|2", "亚军:B2:2").put("第二名|3", "亚军:B2:3").put("第二名|4", "亚军:B2:4")
			.put("第二名|5", "亚军:B2:5").put("第二名|6", "亚军:B2:6").put("第二名|7", "亚军:B2:7").put("第二名|8", "亚军:B2:8")
			.put("第二名|9", "亚军:B2:9").put("第二名|10", "亚军:B2:10").put("第二名|大", "亚军:DX2:D").put("第二名|小", "亚军:DX2:X")
			.put("第二名|单", "亚军:DS2:D").put("第二名|双", "亚军:DS2:S").put("第二名|龙", "亚军:LH2:L").put("第二名|虎", "亚军:LH2:H")

			.put("第三名|1", "第三名:B3:1").put("第三名|2", "第三名:B3:2").put("第三名|3", "第三名:B3:3").put("第三名|4", "第三名:B3:4")
			.put("第三名|5", "第三名:B3:5").put("第三名|6", "第三名:B3:6").put("第三名|7", "第三名:B3:7").put("第三名|8", "第三名:B3:8")
			.put("第三名|9", "第三名:B3:9").put("第三名|10", "第三名:B3:10").put("第三名|大", "第三名:DX3:D").put("第三名|小", "第三名:DX3:X")
			.put("第三名|单", "第三名:DS3:D").put("第三名|双", "第三名:DS3:S").put("第三名|龙", "第三名:LH3:L").put("第三名|虎", "第三名:LH3:H")

			.put("第四名|1", "第四名:B4:1").put("第四名|2", "第四名:B4:2").put("第四名|3", "第四名:B4:3").put("第四名|4", "第四名:B4:4")
			.put("第四名|5", "第四名:B4:5").put("第四名|6", "第四名:B4:6").put("第四名|7", "第四名:B4:7").put("第四名|8", "第四名:B4:8")
			.put("第四名|9", "第四名:B4:9").put("第四名|10", "第四名:B4:10").put("第四名|大", "第四名:DX4:D").put("第四名|小", "第四名:DX4:X")
			.put("第四名|单", "第四名:DS4:D").put("第四名|双", "第四名:DS4:S").put("第四名|龙", "第四名:LH4:L").put("第四名|虎", "第四名:LH4:H")

			.put("第五名|1", "第五名:B5:1").put("第五名|2", "第五名:B5:2").put("第五名|3", "第五名:B5:3").put("第五名|4", "第五名:B5:4")
			.put("第五名|5", "第五名:B5:5").put("第五名|6", "第五名:B5:6").put("第五名|7", "第五名:B5:7").put("第五名|8", "第五名:B5:8")
			.put("第五名|9", "第五名:B5:9").put("第五名|10", "第五名:B5:10").put("第五名|大", "第五名:DX5:D").put("第五名|小", "第五名:DX5:X")
			.put("第五名|单", "第五名:DS5:D").put("第五名|双", "第五名:DS5:S").put("第五名|龙", "第五名:LH5:L").put("第五名|虎", "第五名:LH5:H")

			.put("第六名|1", "第六名:B6:1").put("第六名|2", "第六名:B6:2").put("第六名|3", "第六名:B6:3").put("第六名|4", "第六名:B6:4")
			.put("第六名|5", "第六名:B6:5").put("第六名|6", "第六名:B6:6").put("第六名|7", "第六名:B6:7").put("第六名|8", "第六名:B6:8")
			.put("第六名|9", "第六名:B6:9").put("第六名|10", "第六名:B6:10").put("第六名|大", "第六名:DX6:D").put("第六名|小", "第六名:DX6:X")
			.put("第六名|单", "第六名:DS6:D").put("第六名|双", "第六名:DS6:S")

			.put("第七名|1", "第七名:B7:1").put("第七名|2", "第七名:B7:2").put("第七名|3", "第七名:B7:3").put("第七名|4", "第七名:B7:4")
			.put("第七名|5", "第七名:B7:5").put("第七名|6", "第七名:B7:6").put("第七名|7", "第七名:B7:7").put("第七名|8", "第七名:B7:8")
			.put("第七名|9", "第七名:B7:9").put("第七名|10", "第七名:B7:10").put("第七名|大", "第七名:DX7:D").put("第七名|小", "第七名:DX7:X")
			.put("第七名|单", "第七名:DS7:D").put("第七名|双", "第七名:DS7:S")

			.put("第八名|1", "第八名:B8:1").put("第八名|2", "第八名:B8:2").put("第八名|3", "第八名:B8:3").put("第八名|4", "第八名:B8:4")
			.put("第八名|5", "第八名:B8:5").put("第八名|6", "第八名:B8:6").put("第八名|7", "第八名:B8:7").put("第八名|8", "第八名:B8:8")
			.put("第八名|9", "第八名:B8:9").put("第八名|10", "第八名:B8:10").put("第八名|大", "第八名:DX8:D").put("第八名|小", "第八名:DX8:X")
			.put("第八名|单", "第八名:DS8:D").put("第八名|双", "第八名:DS8:S")

			.put("第九名|1", "第九名:B9:1").put("第九名|2", "第九名:B9:2").put("第九名|3", "第九名:B9:3").put("第九名|4", "第九名:B9:4")
			.put("第九名|5", "第九名:B9:5").put("第九名|6", "第九名:B9:6").put("第九名|7", "第九名:B9:7").put("第九名|8", "第九名:B9:8")
			.put("第九名|9", "第九名:B9:9").put("第九名|10", "第九名:B9:10").put("第九名|大", "第九名:DX9:D").put("第九名|小", "第九名:DX9:X")
			.put("第九名|单", "第九名:DS9:D").put("第九名|双", "第九名:DS9:S")

			.put("第十名|1", "第十名:B10:1").put("第十名|2", "第十名:B10:2").put("第十名|3", "第十名:B10:3").put("第十名|4", "第十名:B10:4")
			.put("第十名|5", "第十名:B10:5").put("第十名|6", "第十名:B10:6").put("第十名|7", "第十名:B10:7").put("第十名|8", "第十名:B10:8")
			.put("第十名|9", "第十名:B10:9").put("第十名|10", "第十名:B10:10").put("第十名|大", "第十名:DX10:D").put("第十名|小", "第十名:DX10:X")
			.put("第十名|单", "第十名:DS10:D").put("第十名|双", "第十名:DS10:S")

			.put("冠亚|3", ":GYH:3").put("冠亚|4", ":GYH:4").put("冠亚|5", ":GYH:5").put("冠亚|6", ":GYH:6")
			.put("冠亚|7", ":GYH:7").put("冠亚|8", ":GYH:8").put("冠亚|9", ":GYH:9").put("冠亚|10", ":GYH:10")
			.put("冠亚|11", ":GYH:11").put("冠亚|12", ":GYH:12").put("冠亚|13", ":GYH:13").put("冠亚|14", ":GYH:14")
			.put("冠亚|15", ":GYH:15").put("冠亚|16", ":GYH:16").put("冠亚|17", ":GYH:17").put("冠亚|18", ":GYH:18")
			.put("冠亚|19", ":GYH:19").put("冠亚|大", ":GDX:D").put("冠亚|小", ":GDX:X").put("冠亚|单", ":GDS:D")
			.put("冠亚|双", ":GDS:S").build();

	Map<String, String> BET_INFO_CODE = ImmutableMap.<String, String>builder().put("冠军1", "第一名|1").put("冠军2", "第一名|2")
			.put("冠军3", "第一名|3").put("冠军4", "第一名|4").put("冠军5", "第一名|5").put("冠军6", "第一名|6").put("冠军7", "第一名|7")
			.put("冠军8", "第一名|8").put("冠军9", "第一名|9").put("冠军10", "第一名|10").put("冠军虎", "第一名|虎").put("冠军龙", "第一名|龙")
			.put("冠军单", "第一名|单").put("冠军双", "第一名|双").put("冠军大", "第一名|大").put("冠军小", "第一名|小")

			.put("亚军1", "第二名|1").put("亚军2", "第二名|2").put("亚军3", "第二名|3").put("亚军4", "第二名|4").put("亚军5", "第二名|5")
			.put("亚军6", "第二名|6").put("亚军7", "第二名|7").put("亚军8", "第二名|8").put("亚军9", "第二名|9").put("亚军10", "第二名|10")
			.put("亚军虎", "第二名|虎").put("亚军龙", "第二名|龙").put("亚军单", "第二名|单").put("亚军双", "第二名|双").put("亚军大", "第二名|大")
			.put("亚军小", "第二名|小")

			.put("第三名1", "第三名|1").put("第三名2", "第三名|2").put("第三名3", "第三名|3").put("第三名4", "第三名|4").put("第三名5", "第三名|5")
			.put("第三名6", "第三名|6").put("第三名7", "第三名|7").put("第三名8", "第三名|8").put("第三名9", "第三名|9").put("第三名10", "第三名|10")
			.put("第三名虎", "第三名|虎").put("第三名龙", "第三名|龙").put("第三名单", "第三名|单").put("第三名双", "第三名|双").put("第三名大", "第三名|大")
			.put("第三名小", "第三名|小")

			.put("第四名1", "第四名|1").put("第四名2", "第四名|2").put("第四名3", "第四名|3").put("第四名4", "第四名|4").put("第四名5", "第四名|5")
			.put("第四名6", "第四名|6").put("第四名7", "第四名|7").put("第四名8", "第四名|8").put("第四名9", "第四名|9").put("第四名10", "第四名|10")
			.put("第四名虎", "第四名|虎").put("第四名龙", "第四名|龙").put("第四名单", "第四名|单").put("第四名双", "第四名|双").put("第四名大", "第四名|大")
			.put("第四名小", "第四名|小")

			.put("第五名1", "第五名|1").put("第五名2", "第五名|2").put("第五名3", "第五名|3").put("第五名4", "第五名|4").put("第五名5", "第五名|5")
			.put("第五名6", "第五名|6").put("第五名7", "第五名|7").put("第五名8", "第五名|8").put("第五名9", "第五名|9").put("第五名10", "第五名|10")
			.put("第五名虎", "第五名|虎").put("第五名龙", "第五名|龙").put("第五名单", "第五名|单").put("第五名双", "第五名|双").put("第五名大", "第五名|大")
			.put("第五名小", "第五名|小")

			.put("第六名1", "第六名|1").put("第六名2", "第六名|2").put("第六名3", "第六名|3").put("第六名4", "第六名|4").put("第六名5", "第六名|5")
			.put("第六名6", "第六名|6").put("第六名7", "第六名|7").put("第六名8", "第六名|8").put("第六名9", "第六名|9").put("第六名10", "第六名|10")
			.put("第六名单", "第六名|单").put("第六名双", "第六名|双").put("第六名大", "第六名|大").put("第六名小", "第六名|小")

			.put("第七名1", "第七名|1").put("第七名2", "第七名|2").put("第七名3", "第七名|3").put("第七名4", "第七名|4").put("第七名5", "第七名|5")
			.put("第七名6", "第七名|6").put("第七名7", "第七名|7").put("第七名8", "第七名|8").put("第七名9", "第七名|9").put("第七名10", "第七名|10")
			.put("第七名单", "第七名|单").put("第七名双", "第七名|双").put("第七名大", "第七名|大").put("第七名小", "第七名|小")

			.put("第八名1", "第八名|1").put("第八名2", "第八名|2").put("第八名3", "第八名|3").put("第八名4", "第八名|4").put("第八名5", "第八名|5")
			.put("第八名6", "第八名|6").put("第八名7", "第八名|7").put("第八名8", "第八名|8").put("第八名9", "第八名|9").put("第八名10", "第八名|10")
			.put("第八名单", "第八名|单").put("第八名双", "第八名|双").put("第八名大", "第八名|大").put("第八名小", "第八名|小")

			.put("第九名1", "第九名|1").put("第九名2", "第九名|2").put("第九名3", "第九名|3").put("第九名4", "第九名|4").put("第九名5", "第九名|5")
			.put("第九名6", "第九名|6").put("第九名7", "第九名|7").put("第九名8", "第九名|8").put("第九名9", "第九名|9").put("第九名10", "第九名|10")
			.put("第九名单", "第九名|单").put("第九名双", "第九名|双").put("第九名大", "第九名|大").put("第九名小", "第九名|小")

			.put("第十名1", "第十名|1").put("第十名2", "第十名|2").put("第十名3", "第十名|3").put("第十名4", "第十名|4").put("第十名5", "第十名|5")
			.put("第十名6", "第十名|6").put("第十名7", "第十名|7").put("第十名8", "第十名|8").put("第十名9", "第十名|9").put("第十名10", "第十名|10")
			.put("第十名单", "第十名|单").put("第十名双", "第十名|双").put("第十名大", "第十名|大").put("第十名小", "第十名|小")

			.put("冠亚军和3", "冠亚|3").put("冠亚军和4", "冠亚|4").put("冠亚军和5", "冠亚|5").put("冠亚军和6", "冠亚|6").put("冠亚军和7", "冠亚|7")
			.put("冠亚军和8", "冠亚|8").put("冠亚军和9", "冠亚|9").put("冠亚军和10", "冠亚|10").put("冠亚军和11", "冠亚|11")
			.put("冠亚军和12", "冠亚|12").put("冠亚军和13", "冠亚|13").put("冠亚军和14", "冠亚|14").put("冠亚军和15", "冠亚|15")
			.put("冠亚军和16", "冠亚|16").put("冠亚军和17", "冠亚|17").put("冠亚军和18", "冠亚|18").put("冠亚军和19", "冠亚|19")
			.put("冠亚单", "冠亚|单").put("冠亚双", "冠亚|双").put("冠亚大", "冠亚|大").put("冠亚小", "冠亚|小").build();

	/**
	 * 投注限制
	 */
	Map<String, String> BET_LIMIT_CODE = ImmutableMap.<String, String>builder().put("第一名|1", "00").put("第一名|2", "00")
			.put("第一名|3", "00").put("第一名|4", "00").put("第一名|5", "00").put("第一名|6", "00").put("第一名|7", "00")
			.put("第一名|8", "00").put("第一名|9", "00").put("第一名|10", "00").put("第一名|大", "01").put("第一名|小", "01")
			.put("第一名|单", "01").put("第一名|双", "01").put("第一名|龙", "01").put("第一名|虎", "01")

			.put("第二名|1", "00").put("第二名|2", "00").put("第二名|3", "00").put("第二名|4", "00").put("第二名|5", "00")
			.put("第二名|6", "00").put("第二名|7", "00").put("第二名|8", "00").put("第二名|9", "00").put("第二名|10", "00")
			.put("第二名|大", "01").put("第二名|小", "01").put("第二名|单", "01").put("第二名|双", "01").put("第二名|龙", "01")
			.put("第二名|虎", "01")

			.put("第三名|1", "00").put("第三名|2", "00").put("第三名|3", "00").put("第三名|4", "00").put("第三名|5", "00")
			.put("第三名|6", "00").put("第三名|7", "00").put("第三名|8", "00").put("第三名|9", "00").put("第三名|10", "00")
			.put("第三名|大", "01").put("第三名|小", "01").put("第三名|单", "01").put("第三名|双", "01").put("第三名|龙", "01")
			.put("第三名|虎", "01")

			.put("第四名|1", "00").put("第四名|2", "00").put("第四名|3", "00").put("第四名|4", "00").put("第四名|5", "00")
			.put("第四名|6", "00").put("第四名|7", "00").put("第四名|8", "00").put("第四名|9", "00").put("第四名|10", "00")
			.put("第四名|大", "01").put("第四名|小", "01").put("第四名|单", "01").put("第四名|双", "01").put("第四名|龙", "01")
			.put("第四名|虎", "01")

			.put("第五名|1", "00").put("第五名|2", "00").put("第五名|3", "00").put("第五名|4", "00").put("第五名|5", "00")
			.put("第五名|6", "00").put("第五名|7", "00").put("第五名|8", "00").put("第五名|9", "00").put("第五名|10", "00")
			.put("第五名|大", "01").put("第五名|小", "01").put("第五名|单", "01").put("第五名|双", "01").put("第五名|龙", "01")
			.put("第五名|虎", "01")

			.put("第六名|1", "00").put("第六名|2", "00").put("第六名|3", "00").put("第六名|4", "00").put("第六名|5", "00")
			.put("第六名|6", "00").put("第六名|7", "00").put("第六名|8", "00").put("第六名|9", "00").put("第六名|10", "00")
			.put("第六名|大", "01").put("第六名|小", "01").put("第六名|单", "01").put("第六名|双", "01")

			.put("第七名|1", "00").put("第七名|2", "00").put("第七名|3", "00").put("第七名|4", "00").put("第七名|5", "00")
			.put("第七名|6", "00").put("第七名|7", "00").put("第七名|8", "00").put("第七名|9", "00").put("第七名|10", "00")
			.put("第七名|大", "01").put("第七名|小", "01").put("第七名|单", "01").put("第七名|双", "01")

			.put("第八名|1", "00").put("第八名|2", "00").put("第八名|3", "00").put("第八名|4", "00").put("第八名|5", "00")
			.put("第八名|6", "00").put("第八名|7", "00").put("第八名|8", "00").put("第八名|9", "00").put("第八名|10", "00")
			.put("第八名|大", "01").put("第八名|小", "01").put("第八名|单", "01").put("第八名|双", "01")

			.put("第九名|1", "00").put("第九名|2", "00").put("第九名|3", "00").put("第九名|4", "00").put("第九名|5", "00")
			.put("第九名|6", "00").put("第九名|7", "00").put("第九名|8", "00").put("第九名|9", "00").put("第九名|10", "00")
			.put("第九名|大", "01").put("第九名|小", "01").put("第九名|单", "01").put("第九名|双", "01")

			.put("第十名|1", "00").put("第十名|2", "00").put("第十名|3", "00").put("第十名|4", "00").put("第十名|5", "00")
			.put("第十名|6", "00").put("第十名|7", "00").put("第十名|8", "00").put("第十名|9", "00").put("第十名|10", "00")
			.put("第十名|大", "01").put("第十名|小", "01").put("第十名|单", "01").put("第十名|双", "01")

			.put("冠亚|3", "04").put("冠亚|4", "04").put("冠亚|5", "04").put("冠亚|6", "04").put("冠亚|7", "04").put("冠亚|8", "04")
			.put("冠亚|9", "04").put("冠亚|10", "04").put("冠亚|11", "04").put("冠亚|12", "04").put("冠亚|13", "04")
			.put("冠亚|14", "04").put("冠亚|15", "04").put("冠亚|16", "04").put("冠亚|17", "04").put("冠亚|18", "04")
			.put("冠亚|19", "04").put("冠亚|大", "02").put("冠亚|小", "02").put("冠亚|单", "03").put("冠亚|双", "03").build();

	/**
	 * 球码code-特号
	 */
	Map<String, String> BALL_NO_CODE = ImmutableMap.<String, String>builder().put("第一名|1", "B1_1").put("第一名|2", "B1_2")
			.put("第一名|3", "B1_3").put("第一名|4", "B1_4").put("第一名|5", "B1_5").put("第一名|6", "B1_6").put("第一名|7", "B1_7")
			.put("第一名|8", "B1_8").put("第一名|9", "B1_9").put("第一名|10", "B1_10")

			.put("第二名|1", "B2_1").put("第二名|2", "B2_2").put("第二名|3", "B2_3").put("第二名|4", "B2_4").put("第二名|5", "B2_5")
			.put("第二名|6", "B2_6").put("第二名|7", "B2_7").put("第二名|8", "B2_8").put("第二名|9", "B2_9").put("第二名|10", "B2_10")

			.put("第三名|1", "B3_1").put("第三名|2", "B3_2").put("第三名|3", "B3_3").put("第三名|4", "B3_4").put("第三名|5", "B3_5")
			.put("第三名|6", "B3_6").put("第三名|7", "B3_7").put("第三名|8", "B3_8").put("第三名|9", "B3_9").put("第三名|10", "B3_10")

			.put("第四名|1", "B4_1").put("第四名|2", "B4_2").put("第四名|3", "B4_3").put("第四名|4", "B4_4").put("第四名|5", "B4_5")
			.put("第四名|6", "B4_6").put("第四名|7", "B4_7").put("第四名|8", "B4_8").put("第四名|9", "B4_9").put("第四名|10", "B4_10")

			.put("第五名|1", "B5_1").put("第五名|2", "B5_2").put("第五名|3", "B5_3").put("第五名|4", "B5_4").put("第五名|5", "B5_5")
			.put("第五名|6", "B5_6").put("第五名|7", "B5_7").put("第五名|8", "B5_8").put("第五名|9", "B5_9").put("第五名|10", "B5_10")

			.put("第六名|1", "B6_1").put("第六名|2", "B6_2").put("第六名|3", "B6_3").put("第六名|4", "B6_4").put("第六名|5", "B6_5")
			.put("第六名|6", "B6_6").put("第六名|7", "B6_7").put("第六名|8", "B6_8").put("第六名|9", "B6_9").put("第六名|10", "B6_10")

			.put("第七名|1", "B7_1").put("第七名|2", "B7_2").put("第七名|3", "B7_3").put("第七名|4", "B7_4").put("第七名|5", "B7_5")
			.put("第七名|6", "B7_6").put("第七名|7", "B7_7").put("第七名|8", "B7_8").put("第七名|9", "B7_9").put("第七名|10", "B7_10")

			.put("第八名|1", "B8_1").put("第八名|2", "B8_2").put("第八名|3", "B8_3").put("第八名|4", "B8_4").put("第八名|5", "B8_5")
			.put("第八名|6", "B8_6").put("第八名|7", "B8_7").put("第八名|8", "B8_8").put("第八名|9", "B8_9").put("第八名|10", "B8_10")

			.put("第九名|1", "B9_1").put("第九名|2", "B9_2").put("第九名|3", "B9_3").put("第九名|4", "B9_4").put("第九名|5", "B9_5")
			.put("第九名|6", "B9_6").put("第九名|7", "B9_7").put("第九名|8", "B9_8").put("第九名|9", "B9_9").put("第九名|10", "B9_10")

			.put("第十名|1", "B10_1").put("第十名|2", "B10_2").put("第十名|3", "B10_3").put("第十名|4", "B10_4")
			.put("第十名|5", "B10_5").put("第十名|6", "B10_6").put("第十名|7", "B10_7").put("第十名|8", "B10_8")
			.put("第十名|9", "B10_9").put("第十名|10", "B10_10").build();

	/**
	 * 球码code-双面
	 */
	Map<String, String> DOUBLE_SIDES_CODE = ImmutableMap.<String, String>builder().put("第一名|大", "DX1_D")
			.put("第一名|小", "DX1_X").put("第一名|单", "DS1_D").put("第一名|双", "DS1_S").put("第一名|龙", "LH1_L")
			.put("第一名|虎", "LH1_H")

			.put("第二名|大", "DX2_D").put("第二名|小", "DX2_X").put("第二名|单", "DS2_D").put("第二名|双", "DS2_S")
			.put("第二名|龙", "LH2_L").put("第二名|虎", "LH2_H")

			.put("第三名|大", "DX3_D").put("第三名|小", "DX3_X").put("第三名|单", "DS3_D").put("第三名|双", "DS3_S")
			.put("第三名|龙", "LH3_L").put("第三名|虎", "LH3_H")

			.put("第四名|大", "DX4_D").put("第四名|小", "DX4_X").put("第四名|单", "DS4_D").put("第四名|双", "DS4_S")
			.put("第四名|龙", "LH4_L").put("第四名|虎", "LH4_H")

			.put("第五名|大", "DX5_D").put("第五名|小", "DX5_X").put("第五名|单", "DS5_D").put("第五名|双", "DS5_S")
			.put("第五名|龙", "LH5_L").put("第五名|虎", "LH5_H")

			.put("第六名|大", "DX6_D").put("第六名|小", "DX6_X").put("第六名|单", "DS6_D").put("第六名|双", "DS6_S")

			.put("第七名|大", "DX7_D").put("第七名|小", "DX7_X").put("第七名|单", "DS7_D").put("第七名|双", "DS7_S")

			.put("第八名|大", "DX8_D").put("第八名|小", "DX8_X").put("第八名|单", "DS8_D").put("第八名|双", "DS8_S")

			.put("第九名|大", "DX9_D").put("第九名|小", "DX9_X").put("第九名|单", "DS9_D").put("第九名|双", "DS9_S")

			.put("第十名|大", "DX10_D").put("第十名|小", "DX10_X").put("第十名|单", "DS10_D").put("第十名|双", "DS10_S")

			.put("冠亚|大", "GDX_D").put("冠亚|小", "GDX_X").put("冠亚|单", "GDS_D").put("冠亚|双", "GDS_S").build();

	/**
	 * 球码code-冠亚和
	 */
	Map<String, String> SUM_DT_CODE = ImmutableMap.<String, String>builder().put("冠亚|3", "GYH_3").put("冠亚|4", "GYH_4")
			.put("冠亚|5", "GYH_5").put("冠亚|6", "GYH_6").put("冠亚|7", "GYH_7").put("冠亚|8", "GYH_8").put("冠亚|9", "GYH_9")
			.put("冠亚|10", "GYH_10").put("冠亚|11", "GYH_11").put("冠亚|12", "GYH_12").put("冠亚|13", "GYH_13")
			.put("冠亚|14", "GYH_14").put("冠亚|15", "GYH_15").put("冠亚|16", "GYH_16").put("冠亚|17", "GYH_17")
			.put("冠亚|18", "GYH_18").put("冠亚|19", "GYH_19").build();

	/**
	 * 盘口排名-用在获取投注参数：title
	 */
	Map<String, String> BET_RANK_CODE = ImmutableMap.<String, String>builder().put("第一名", "冠军").put("第二名", "亚军")
			.put("第三名", "第三名").put("第四名", "第四名").put("第五名", "第五名").put("第六名", "第六名").put("第七名", "第七名").put("第八名", "第八名")
			.put("第九名", "第九名").put("第十名", "第十名").put("冠亚", "").build();

	/**
	 * 盘口排名-用在匹配注单号
	 */
	Map<String, String> BET_RESULT_CODE = ImmutableMap.<String, String>builder().put("冠军 1", "第一名|1")
			.put("冠军 2", "第一名|2").put("冠军 3", "第一名|3").put("冠军 4", "第一名|4").put("冠军 5", "第一名|5").put("冠军 6", "第一名|6")
			.put("冠军 7", "第一名|7").put("冠军 8", "第一名|8").put("冠军 9", "第一名|9").put("冠军 10", "第一名|10").put("冠军 大", "第一名|大")
			.put("冠军 小", "第一名|小").put("冠军 单", "第一名|单").put("冠军 双", "第一名|双").put("冠军 龙", "第一名|龙").put("冠军 虎", "第一名|虎")

			.put("亚军 1", "第二名|1").put("亚军 2", "第二名|2").put("亚军 3", "第二名|3").put("亚军 4", "第二名|4").put("亚军 5", "第二名|5")
			.put("亚军 6", "第二名|6").put("亚军 7", "第二名|7").put("亚军 8", "第二名|8").put("亚军 9", "第二名|9").put("亚军 10", "第二名|10")
			.put("亚军 大", "第二名|大").put("亚军 小", "第二名|小").put("亚军 单", "第二名|单").put("亚军 双", "第二名|双").put("亚军 龙", "第二名|龙")
			.put("亚军 虎", "第二名|虎")

			.put("第三名 1", "第三名|1").put("第三名 2", "第三名|2").put("第三名 3", "第三名|3").put("第三名 4", "第三名|4")
			.put("第三名 5", "第三名|5").put("第三名 6", "第三名|6").put("第三名 7", "第三名|7").put("第三名 8", "第三名|8")
			.put("第三名 9", "第三名|9").put("第三名 10", "第三名|10").put("第三名 大", "第三名|大").put("第三名 小", "第三名|小")
			.put("第三名 单", "第三名|单").put("第三名 双", "第三名|双").put("第三名 龙", "第三名|龙").put("第三名 虎", "第三名|虎")

			.put("第四名 1", "第四名|1").put("第四名 2", "第四名|2").put("第四名 3", "第四名|3").put("第四名 4", "第四名|4")
			.put("第四名 5", "第四名|5").put("第四名 6", "第四名|6").put("第四名 7", "第四名|7").put("第四名 8", "第四名|8")
			.put("第四名 9", "第四名|9").put("第四名 10", "第四名|10").put("第四名 大", "第四名|大").put("第四名 小", "第四名|小")
			.put("第四名 单", "第四名|单").put("第四名 双", "第四名|双").put("第四名 龙", "第四名|龙").put("第四名 虎", "第四名|虎")

			.put("第五名 1", "第五名|1").put("第五名 2", "第五名|2").put("第五名 3", "第五名|3").put("第五名 4", "第五名|4")
			.put("第五名 5", "第五名|5").put("第五名 6", "第五名|6").put("第五名 7", "第五名|7").put("第五名 8", "第五名|8")
			.put("第五名 9", "第五名|9").put("第五名 10", "第五名|10").put("第五名 大", "第五名|大").put("第五名 小", "第五名|小")
			.put("第五名 单", "第五名|单").put("第五名 双", "第五名|双").put("第五名 龙", "第五名|龙").put("第五名 虎", "第五名|虎")

			.put("第六名 1", "第六名|1").put("第六名 2", "第六名|2").put("第六名 3", "第六名|3").put("第六名 4", "第六名|4")
			.put("第六名 5", "第六名|5").put("第六名 6", "第六名|6").put("第六名 7", "第六名|7").put("第六名 8", "第六名|8")
			.put("第六名 9", "第六名|9").put("第六名 10", "第六名|10").put("第六名 大", "第六名|大").put("第六名 小", "第六名|小")
			.put("第六名 单", "第六名|单").put("第六名 双", "第六名|双")

			.put("第七名 1", "第七名|1").put("第七名 2", "第七名|2").put("第七名 3", "第七名|3").put("第七名 4", "第七名|4")
			.put("第七名 5", "第七名|5").put("第七名 6", "第七名|6").put("第七名 7", "第七名|7").put("第七名 8", "第七名|8")
			.put("第七名 9", "第七名|9").put("第七名 10", "第七名|10").put("第七名 大", "第七名|大").put("第七名 小", "第七名|小")
			.put("第七名 单", "第七名|单").put("第七名 双", "第七名|双")

			.put("第八名 1", "第八名|1").put("第八名 2", "第八名|2").put("第八名 3", "第八名|3").put("第八名 4", "第八名|4")
			.put("第八名 5", "第八名|5").put("第八名 6", "第八名|6").put("第八名 7", "第八名|7").put("第八名 8", "第八名|8")
			.put("第八名 9", "第八名|9").put("第八名 10", "第八名|10").put("第八名 大", "第八名|大").put("第八名 小", "第八名|小")
			.put("第八名 单", "第八名|单").put("第八名 双", "第八名|双")

			.put("第九名 1", "第九名|1").put("第九名 2", "第九名|2").put("第九名 3", "第九名|3").put("第九名 4", "第九名|4")
			.put("第九名 5", "第九名|5").put("第九名 6", "第九名|6").put("第九名 7", "第九名|7").put("第九名 8", "第九名|8")
			.put("第九名 9", "第九名|9").put("第九名 10", "第九名|10").put("第九名 大", "第九名|大").put("第九名 小", "第九名|小")
			.put("第九名 单", "第九名|单").put("第九名 双", "第九名|双")

			.put("第十名 1", "第十名|1").put("第十名 2", "第十名|2").put("第十名 3", "第十名|3").put("第十名 4", "第十名|4")
			.put("第十名 5", "第十名|5").put("第十名 6", "第十名|6").put("第十名 7", "第十名|7").put("第十名 8", "第十名|8")
			.put("第十名 9", "第十名|9").put("第十名 10", "第十名|10").put("第十名 大", "第十名|大").put("第十名 小", "第十名|小")
			.put("第十名 单", "第十名|单").put("第十名 双", "第十名|双")

			.put("冠亚军和 3", "冠亚|3").put("冠亚军和 4", "冠亚|4").put("冠亚军和 5", "冠亚|5").put("冠亚军和 6", "冠亚|6")
			.put("冠亚军和 7", "冠亚|7").put("冠亚军和 8", "冠亚|8").put("冠亚军和 9", "冠亚|9").put("冠亚军和 10", "冠亚|10")
			.put("冠亚军和 11", "冠亚|11").put("冠亚军和 12", "冠亚|12").put("冠亚军和 13", "冠亚|13").put("冠亚军和 14", "冠亚|14")
			.put("冠亚军和 15", "冠亚|15").put("冠亚军和 16", "冠亚|16").put("冠亚军和 17", "冠亚|17").put("冠亚军和 18", "冠亚|18")
			.put("冠亚军和 19", "冠亚|19").put("冠亚大", "冠亚|大").put("冠亚小", "冠亚|小").put("冠亚单", "冠亚|单").put("冠亚双", "冠亚|双")
			.build();
}
