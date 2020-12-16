package com.ibm.common.core.configs;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
/**
 * @Description: SgWin盘口默认值
 * @Author: zjj
 * @Date: 2019-10-25 17:08
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public interface SgWinConfig {

	Map<String, String> BET_CODE = ImmutableMap.<String, String>builder().put("PK10", "BJPK10").put("XYFT", "XYFT")
			.put("SGFT", "SGFT").put("JS10", "PK10JSC").put("AZXY5", "AULUCKY5").put("AZXY10", "AULUCKY10")
			.put("CQSSC", "CQHLSX").put("JSSSC", "SSCJSC").build();

	/**
	 * 双面
	 */
	List<String> DOUBLE_SIDE = ImmutableList.<String>builder().add("大").add("小").add("单").add("双").add("龙").add("虎")
			.add("和").add("豹子").add("顺子").add("对子").add("半顺").add("杂六").build();

	/**
	 * 冠亚和
	 */
	List<String> SUM_DT = ImmutableList.<String>builder().add("冠亚|3").add("冠亚|4").add("冠亚|5").add("冠亚|6").add("冠亚|7")
			.add("冠亚|8").add("冠亚|9").add("冠亚|10").add("冠亚|11").add("冠亚|12").add("冠亚|13").add("冠亚|14").add("冠亚|15")
			.add("冠亚|16").add("冠亚|17").add("冠亚|18").add("冠亚|19").build();

}
