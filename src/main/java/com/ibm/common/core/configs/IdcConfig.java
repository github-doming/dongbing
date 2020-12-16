package com.ibm.common.core.configs;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
/**
 * @Description: Idc盘口默认值
 * @Author: Dongming
 * @Date: 2629-09-21 17:47
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface IdcConfig {

	String PERMISSION_CODE = "BYZN";


	String PERMISSION_KEY = "EF64586B3AF643FD9A8F365086C8FC0A";

	Map<String, String> BET_CODE = ImmutableMap.<String, String>builder()
			.put("CQSSC", "6").put("GDKLC", "8").put("GXKLSF", "9").put("PK10", "11")
			.put("XYNC", "20").put("XYFT", "21").put("JS10", "22").put("JSSSC", "23")
			.put("SELF_10_2", "26").put("SELF_SSC_2", "27").put("SELF_SSC_5", "37").put("COUNTRY_SSC", "38").put("COUNTRY_10", "40")
			.build();

}
