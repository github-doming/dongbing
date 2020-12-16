package com.common.config.handicap;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2020/5/15 11:26
 * @Version: v1.0
 */
public interface NewCcConfig {


	/**
	 * A盘最终球号的  int gamecode + code的值
	 */
	Map<String,String> A_GAME_BALL_CODE = ImmutableMap.<String, String>builder().put("GDKLC","200")
			.put("CQSSC","300").put("PK10","400"). put("XYFT","800")
			.put("JS10","2900").put("JSSSC","3600")
			.put("SELF_SSC_5","3100").put("COUNTRY_10","2600").build();

	/**
	 * B盘最终球号的  int gamecode + code的值
	 */
	Map<String,String> B_GAME_BALL_CODE = ImmutableMap.<String, String>builder().put("GDKLC","200")
			.put("CQSSC","300").put("PK10","400"). put("XYFT","800")
			.put("JS10","1600").put("JSSSC","1700")
			.put("SELF_SSC_5","1800").put("COUNTRY_10","2000").build();

	/**
	 * 最终球号的  int gamecode + code的值
	 */
	Map<String,String> GAME_CODE = ImmutableMap.<String, String>builder().put("GDKLC","gd_lmp")
			.put("CQSSC","cqss_zh").put("PK10","bjpk10_lmp"). put("XYFT","xyft_lmp")
			.put("JS10","jssc_lmp").put("JSSSC","jsss_zh")
			.put("SELF_SSC_5","azxy5_zh").put("COUNTRY_10","azxy10_lmp").build();




}
