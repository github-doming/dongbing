package com.common.config.handicap;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @Description: Un盘口默认值
 * @Author: wwj
 * @Date: 2019/12/27 15:48
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public interface UnConfig {

	Map<String, String> GAME_TYPE = ImmutableMap.<String, String>builder().put("GDKLC", "G_1").put("CQSSC", "G_2")
			.put("PK10", "G_3").put("XYFT", "G_24").build();

	Map<String, String> GAME_TYPE_BetSummary = ImmutableMap.<String, String>builder().put("GDKLC", "KLC").put("CQSSC", "SSC")
			.put("PK10", "BJC").build();

	/**
	 * 投注信息
	 */
	Map<String, String> BET_INFO_CODE = ImmutableMap.<String, String>builder().put("冠军", "第一名").put("亚军", "第二名")
			.put("冠、亚和", "冠亚").build();
	/**
	 * 球号位置
	 */
	Map<String, String> BALL_CODE_PLACE = ImmutableMap.<String, String>builder().put("第一名", "B_0").put("第二名", "B_1")
			.put("第三名", "B_2").put("第四名", "B_3").put("第五名", "B_4").put("第六名", "B_5")
			.put("第七名", "B_6").put("第八名", "B_7").put("第九名", "B_8").put("第十名", "B_9")
			.put("第一球", "B_0").put("第二球", "B_1").put("第三球", "B_2").put("第四球", "B_3").put("第五球", "B_4")
			.put("总和", "B_10").put("前三", "B_11").put("中三", "B_12").put("后和", "B_13").build();
	/**
	 * 球号范围
	 */
	Map<String, String> BALL_CODE_RANGE = ImmutableMap.<String, String>builder().put("0", "N_0").put("1", "N_1").put("2", "N_2")
			.put("3", "N_3").put("4", "N_4").put("5", "N_5").put("6", "N_6").put("7", "N_7")
			.put("8", "N_8").put("9", "N_9").put("10", "N_10").put("11", "N_11").put("12", "N_12")
			.put("13", "N_13").put("14", "N_14").put("15", "N_15").put("16", "N_16").put("17", "N_17")
			.put("18", "N_18").put("19", "N_19")
			.put("大", "N_21").put("小", "N_22").put("单", "N_23").put("双", "N_24")

			.put("龙", "N_29").put("虎", "N_30").put("和", "N_64")

			.put("豹子", "N_65").put("顺子", "N_66").put("对子", "N_67").put("半顺", "N_68")
			.put("杂六", "N_69").build();

}
