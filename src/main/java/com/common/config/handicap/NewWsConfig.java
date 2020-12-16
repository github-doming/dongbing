package com.common.config.handicap;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2020/5/15 11:26
 * @Version: v1.0
 */
public interface NewWsConfig {

	/**
	 * 最终球号的  int gamecode + code的值
	 */
	Map<String, String> GAME_TYPE = ImmutableMap.<String, String>builder().put("GDKLC", "1_1")
			.put("CQSSC", "1_2").put("PK10", "1_3")
			.put("SELF_10_5", "1_7").build();

	Map<String, String> SSC_ODDS_CODE = ImmutableMap.<String, String>builder()
			.put("CQSSC", "12").build();

	Map<String, String> PK10_ODDS_CODE = ImmutableMap.<String, String>builder()
			.put("dobleSides", "18").put("ballNO", "35").put("sumDT", "21").build();

	Map<String, String> SELF_10_5_ODDS_CODE = ImmutableMap.<String, String>builder()
			.put("dobleSides", "36").put("ballNO", "37").put("sumDT", "40").build();

	Map<String, String> GDKLC_ODDS_CODE = ImmutableMap.<String, String>builder()
			.put("dobleSides", "1").put("第一球", "2").put("第二球", "3").put("第三球", "4")
			.put("第四球", "5").put("第五球", "6").put("第六球", "7").put("第七球", "8").put("第八球", "9").put("总和", "10").build();


	/**
	 * 球号位置
	 */
	Map<String, String> BALL_CODE_PLACE = ImmutableMap.<String, String>builder().put("第一名", "1").put("第二名", "2")
			.put("第三名", "3").put("第四名", "4").put("第五名", "5").put("第六名", "6")
			.put("第七名", "7").put("第八名", "8").put("第九名", "9").put("第十名", "10")
			.put("第一球", "1").put("第二球", "2").put("第三球", "3").put("第四球", "4").put("第五球", "5")
			.put("第六球", "6").put("第七球", "7").put("第八球", "8")
			.put("前三", "6").put("中三", "7").put("后和", "8")
			.put("总和", "otherCode").put("冠亚", "otherCode").build();
	/**
	 * 球号范围
	 */
	Map<String, String> BALL_CODE_RANGE = ImmutableMap.<String, String>builder().put("1", "1").put("2", "2")
			.put("3", "3").put("4", "4").put("5", "5").put("6", "6").put("7", "7").put("8", "8").put("9", "9").put("10", "10")
			.put("大", "11").put("小", "12").put("单", "13").put("双", "14").put("龙", "15").put("虎", "16")
			.put("豹子", "0").put("顺子", "1").put("对子", "2").put("半顺", "3").put("杂六", "4").build();


	/**
	 * 球号范围 klc
	 */
	Map<String, String> KLC_BALL_CODE_RANGE = ImmutableMap.<String, String>builder().put("1", "1").put("2", "2")
			.put("3", "3").put("4", "4").put("5", "5").put("6", "6").put("7", "7").put("8", "8").put("9", "9").put("10", "10")
			.put("11", "11").put("12", "12").put("13", "13").put("14", "14").put("15", "15").put("16", "16").put("17", "17")
			.put("18", "18").put("19", "19").put("20", "20")

			.put("大", "21").put("小", "22").put("单", "23").put("双", "24").put("尾大", "25").put("尾小", "26")
			.put("合单", "27").put("合双", "28").put("东", "29").put("南", "30").put("西", "31").put("北", "32")
			.put("中", "33").put("发", "34").put("白", "35").put("龙", "36").put("虎", "37").build();

	/**
	 * 球号范围 ssc
	 */
	Map<String, String> SSC_BALL_CODE_RANGE = ImmutableMap.<String, String>builder().put("0", "0").put("1", "1").put("2", "2")
			.put("3", "3").put("4", "4").put("5", "5").put("6", "6").put("7", "7").put("8", "8").put("9", "9")
			.put("大", "10").put("小", "11").put("单", "12").put("双", "13").build();


	/**
	 * 特殊处理
	 */
	Map<String, String> BALL_CODE_OTHER = ImmutableMap.<String, String>builder().put("冠亚|3", "1").put("冠亚|4", "11_4").put("冠亚|5", "11_5")
			.put("冠亚|6", "11_6").put("冠亚|7", "11_7")
			.put("冠亚|8", "11_8").put("冠亚|9", "11_9").put("冠亚|10", "11_10").put("冠亚|11", "11_11").put("冠亚|12", "11_12")
			.put("冠亚|13", "11_13").put("冠亚|14", "11_14").put("冠亚|15", "11_15").put("冠亚|16", "11_16")
			.put("冠亚|17", "11_17").put("冠亚|18", "11_18").put("冠亚|19", "11_19").put("冠亚|大", "11_20").put("冠亚|小", "11_21")
			.put("冠亚|单", "11_22").put("冠亚|双", "11_23")

			.put("总和|大", "9_1").put("总和|小", "9_2").put("总和|单", "9_3").put("总和|双", "9_4")
			.put("龙虎和|龙", "9_5").put("龙虎和|虎", "9_6").put("龙虎和|和", "9_7").build();


}
