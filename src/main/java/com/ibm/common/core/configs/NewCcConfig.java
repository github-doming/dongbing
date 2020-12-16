package com.ibm.common.core.configs;

import com.google.common.collect.ImmutableMap;
import com.ibm.common.utils.game.GameUtil;

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
	Map<String,String> A_GAME_BALL_CODE = ImmutableMap.<String, String>builder().put(GameUtil.Code.GDKLC.name(),"200")
			.put(GameUtil.Code.CQSSC.name(),"300").put(GameUtil.Code.PK10.name(),"400"). put(GameUtil.Code.XYFT.name(),"800")
			.put(GameUtil.Code.JS10.name(),"2900").put(GameUtil.Code.JSSSC.name(),"3600")
			.put(GameUtil.Code.SELF_SSC_5.name(),"3100").put(GameUtil.Code.COUNTRY_10.name(),"2600").build();

	/**
	 * B盘最终球号的  int gamecode + code的值
	 */
	Map<String,String> B_GAME_BALL_CODE = ImmutableMap.<String, String>builder().put(GameUtil.Code.GDKLC.name(),"200")
			.put(GameUtil.Code.CQSSC.name(),"300").put(GameUtil.Code.PK10.name(),"400"). put(GameUtil.Code.XYFT.name(),"800")
			.put(GameUtil.Code.JS10.name(),"1600").put(GameUtil.Code.JSSSC.name(),"1700")
			.put(GameUtil.Code.SELF_SSC_5.name(),"1800").put(GameUtil.Code.COUNTRY_10.name(),"2000").build();

	/**
	 * 最终球号的  int gamecode + code的值
	 */
	Map<String,String> GAME_CODE = ImmutableMap.<String, String>builder().put(GameUtil.Code.GDKLC.name(),"gd_lmp")
			.put(GameUtil.Code.CQSSC.name(),"cqss_zh").put(GameUtil.Code.PK10.name(),"bjpk10_lmp"). put(GameUtil.Code.XYFT.name(),"xyft_lmp")
			.put(GameUtil.Code.JS10.name(),"jssc_lmp").put(GameUtil.Code.JSSSC.name(),"jsss_zh")
			.put(GameUtil.Code.SELF_SSC_5.name(),"azxy5_zh").put(GameUtil.Code.COUNTRY_10.name(),"azxy10_lmp").build();




}
