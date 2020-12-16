package com.common.config.handicap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * @Description: Ncom2盘口默认值
 * @Author: wwj
 * @Date: 2019/12/27 15:48
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public interface FcConfig {

	Map<String, String> GAME_TYPE = ImmutableMap.<String, String>builder().put("GDKLC", "1").put("CQSSC", "2")
			.put("PK10", "3").put("XYFT", "10").put("JS10", "11").put("JSSSC", "12")
			.put("COUNTRY_SSC", "14").put("COUNTRY_10", "13").build();


	Map<String, String> GAME_CODE_PATH = ImmutableMap.<String, String>builder().put("GDKLC", "L_KL10").put("CQSSC", "L_CQSC")
			.put("PK10", "L_PK10").put("XYFT", "L_XYFT5").put("JS10", "L_JSCAR").put("JSSSC", "L_SPEED5")
			.put("COUNTRY_SSC", "L_JSCQSC").put("COUNTRY_10", "L_JSPK10").build();

	Map<String, String> GAME_CODE_PAGE = ImmutableMap.<String, String>builder().put("GDKLC", "kl10_").put("CQSSC", "cqsc_")
			.put("PK10", "pk10_").put("XYFT", "xyft5_").put("JS10", "jscar_").put("JSSSC", "speed5_")
			.put("COUNTRY_SSC", "jscqsc_").put("COUNTRY_10", "jspk10_").build();


	/**
	 * 双面
	 */
	List<String> DOUBLE_SIDE = ImmutableList.<String>builder().add("大").add("小").add("单").add("双").add("龙").add("虎")
			.add("和").build();

	/**
	 * 三球
	 */
	List<String> THREE_BALL = ImmutableList.<String>builder().add("豹子").add("顺子").add("对子").add("半顺").add("杂六").build();

	/**
	 * 冠亚和
	 */
	List<String> SUM_DT = ImmutableList.<String>builder().add("冠亚|3").add("冠亚|4").add("冠亚|5").add("冠亚|6").add("冠亚|7")
			.add("冠亚|8").add("冠亚|9").add("冠亚|10").add("冠亚|11").add("冠亚|12").add("冠亚|13").add("冠亚|14").add("冠亚|15")
			.add("冠亚|16").add("冠亚|17").add("冠亚|18").add("冠亚|19").build();


}
