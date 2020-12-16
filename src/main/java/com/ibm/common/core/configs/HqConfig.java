package com.ibm.common.core.configs;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-26 16:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface HqConfig {

	Map<String, String> BET_CODE = ImmutableMap.<String, String>builder().put("ALL", "0").put("CQSSC", "1")
			.put("PK10", "2").put("XYNC", "3").put("KL10", "4").put("YG10", "5").put("YGSSC", "6").put("YGKLC", "7")
			.put("XYFT", "8").put("JS10", "9").put("JSSSC", "10").build();

	/**
	 *
	 */
	Map<String, String> BET_TYPE_CODE = ImmutableMap.<String, String>builder()
			.put("CQSSC_dobleSides", "1").put("CQSSC_ballNO", "1")
			.put("PK10_dobleSides", "1").put("PK10_ballNO", "2").put("PK10_sumDT", "5")
			.put("XYFT_dobleSides", "1").put("XYFT_ballNO", "2").put("XYFT_sumDT", "5")
			.put("YG10_dobleSides", "1").put("YG10_ballNO", "2").put("YG10_sumDT", "5")
			.put("YGSSC_dobleSides", "1").put("YGSSC_ballNO", "1")
			.put("JSSSC_dobleSides", "1").put("JSSSC_ballNO", "1")
			.put("JS10_dobleSides", "1").put("JS10_ballNO", "2").put("JS10_sumDT", "5").build();
	/**
	 * PK10双面
	 */
	List<String> PK10_DOUBLE_SIDES = ImmutableList.<String>builder().add("2002").add("2003").add("2012").add("2013")
			.add("2014").add("2022").add("2023").add("2024").add("2032").add("2033").add("2034").add("2042").add("2043")
			.add("2044").add("2052").add("2053").add("2054").add("2062").add("2063").add("2072").add("2073").add("2082")
			.add("2083").add("2092").add("2093").add("2102").add("2103").build();

	/**
	 * PK10特号
	 */
	List<String> PK10_BALL_NO = ImmutableList.<String>builder().add("2012").add("2013").add("2014").add("2011")
			.add("2022").add("2023").add("2024").add("2021").add("2032").add("2033").add("2034").add("2031").add("2042")
			.add("2043").add("2044").add("2041").add("2052").add("2053").add("2054").add("2051").add("2062").add("2063")
			.add("2061").add("2072").add("2073").add("2071").add("2082").add("2083").add("2081").add("2092").add("2093")
			.add("2091").add("2102").add("2103").add("2101").build();

	/**
	 * PK10冠亚
	 */
	List<String> PK10_SUM_DT = ImmutableList.<String>builder().add("2001").add("2002").add("2003").build();

	/**
	 * XYFT双面
	 */
	List<String> XYFT_DOUBLE_SIDES = ImmutableList.<String>builder().add("8002").add("8003").add("8012").add("8013")
			.add("8014").add("8022").add("8023").add("8024").add("8032").add("8033").add("8034").add("8042").add("8043")
			.add("8044").add("8052").add("8053").add("8054").add("8062").add("8063").add("8072").add("8073").add("8082")
			.add("8083").add("8092").add("8093").add("8102").add("8103").build();

	/**
	 * XYFT特号
	 */
	List<String> XYFT_BALL_NO = ImmutableList.<String>builder().add("8012").add("8013").add("8014").add("8011")
			.add("8022").add("8023").add("8024").add("8021").add("8032").add("8033").add("8034").add("8031").add("8042")
			.add("8043").add("8044").add("8041").add("8052").add("8053").add("8054").add("8051").add("8062").add("8063")
			.add("8061").add("8072").add("8073").add("8071").add("8082").add("8083").add("8081").add("8092").add("8093")
			.add("8091").add("2102").add("8103").add("8101").build();

	/**
	 * XYFT冠亚
	 */
	List<String> XYFT_SUM_DT = ImmutableList.<String>builder().add("8001").add("8002").add("8003").build();

	/**
	 * YGJS10双面
	 */
	List<String> JS10_DOUBLE_SIDES = ImmutableList.<String>builder().add("9002").add("9003").add("9012").add("9013")
			.add("9014").add("9022").add("9023").add("9024").add("9032").add("9033").add("9034").add("9042").add("9043")
			.add("9044").add("9052").add("9053").add("9054").add("9062").add("9063").add("9072").add("9073").add("9082")
			.add("9083").add("9092").add("9093").add("9102").add("9103").build();

	/**
	 * YGJS10特号
	 */
	List<String> JS10_BALL_NO = ImmutableList.<String>builder().add("9012").add("9013").add("9014").add("9011")
			.add("9022").add("9023").add("9024").add("9021").add("9032").add("9033").add("9034").add("9031").add("9042")
			.add("9043").add("9044").add("9041").add("9052").add("9053").add("9054").add("9051").add("9062").add("9063")
			.add("9061").add("9072").add("9073").add("9071").add("9082").add("9083").add("9081").add("9092").add("9093")
			.add("9091").add("9102").add("9103").add("9101").build();

	/**
	 * YGJS10冠亚
	 */
	List<String> JS10_SUM_DT = ImmutableList.<String>builder().add("9001").add("9002").add("9003").build();

	/**
	 * YG10双面
	 */
	List<String> YG10_DOUBLE_SIDES = ImmutableList.<String>builder().add("5002").add("5003").add("5012").add("5013")
			.add("5014").add("5022").add("5023").add("5024").add("5032").add("5033").add("5034").add("5042").add("5043")
			.add("5044").add("5052").add("5053").add("5054").add("5062").add("5063").add("5072").add("5073").add("5082")
			.add("5083").add("5092").add("5093").add("5102").add("5103").build();

	/**
	 * YG10特号
	 */
	List<String> YG10_BALL_NO = ImmutableList.<String>builder().add("5012").add("5013").add("5014").add("5011")
			.add("5022").add("5023").add("5024").add("5021").add("5032").add("5033").add("5034").add("5031").add("5042")
			.add("5043").add("5044").add("5041").add("5052").add("5053").add("5054").add("5051").add("5062").add("5063")
			.add("5061").add("5072").add("5073").add("5071").add("5082").add("5083").add("5081").add("5092").add("5093")
			.add("5091").add("5102").add("5103").add("5101").build();

	/**
	 * YG10冠亚
	 */
	List<String> YG10_SUM_DT = ImmutableList.<String>builder().add("5001").add("5002").add("5003").build();

	/**
	 * CQSSC
	 */
	List<String> CQSSC_CODE = ImmutableList.<String>builder().add("1012").add("1013").add("1011").add("1022")
			.add("1023").add("1021").add("1032").add("1033").add("1031").add("1042").add("1043").add("1041").add("1052")
			.add("1053").add("1051").add("1002").add("1003").add("1014").add("1125").add("1135").add("1145").build();

	/**
	 * YGSSC
	 */
	List<String> YGSSC_CODE = ImmutableList.<String>builder().add("6012").add("6013").add("6011").add("6022")
			.add("6023").add("6021").add("6032").add("6033").add("6031").add("6042").add("6043").add("6041").add("6052")
			.add("6053").add("6051").add("6002").add("6003").add("6014").add("6125").add("6135").add("6145").build();

	/**
	 * JSSSC
	 */
	List<String> JSSSC_CODE = ImmutableList.<String>builder().add("10012").add("10013").add("10011").add("10022")
			.add("10023").add("10021").add("10032").add("10033").add("10031").add("10042").add("10043").add("10041")
			.add("10052").add("10053").add("10051").add("10002").add("10003").add("10014").add("10125").add("10135")
			.add("10145").build();

}
