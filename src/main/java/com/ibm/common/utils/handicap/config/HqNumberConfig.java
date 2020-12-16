package com.ibm.common.utils.handicap.config;
import com.google.common.collect.ImmutableMap;
import com.ibm.common.core.configs.HqConfig;

import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-28 16:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface HqNumberConfig extends HqConfig {

	/**
	 * 游戏code
	 */

	Map<String, String> BALL_CODE = ImmutableMap.<String, String>builder().put("第一名|1", "01101").put("第一名|2", "01102")
			.put("第一名|3", "01103").put("第一名|4", "01104").put("第一名|5", "01105").put("第一名|6", "01106")
			.put("第一名|7", "01107").put("第一名|8", "01108").put("第一名|9", "01109").put("第一名|10", "01110")
			.put("第一名|大", "01201").put("第一名|小", "01202").put("第一名|单", "01301").put("第一名|双", "01302")
			.put("第一名|龙", "01401").put("第一名|虎", "01402")

			.put("第二名|1", "02101").put("第二名|2", "02102").put("第二名|3", "02103").put("第二名|4", "02104")
			.put("第二名|5", "02105").put("第二名|6", "02106").put("第二名|7", "02107").put("第二名|8", "02108")
			.put("第二名|9", "02109").put("第二名|10", "02110").put("第二名|大", "02201").put("第二名|小", "02202")
			.put("第二名|单", "02301").put("第二名|双", "02302").put("第二名|龙", "02401").put("第二名|虎", "02402")

			.put("第三名|1", "03101").put("第三名|2", "03102").put("第三名|3", "03103").put("第三名|4", "03104")
			.put("第三名|5", "03105").put("第三名|6", "03106").put("第三名|7", "03107").put("第三名|8", "03108")
			.put("第三名|9", "03109").put("第三名|10", "03110").put("第三名|大", "03201").put("第三名|小", "03202")
			.put("第三名|单", "03301").put("第三名|双", "03302").put("第三名|龙", "03401").put("第三名|虎", "03402")

			.put("第四名|1", "04101").put("第四名|2", "04102").put("第四名|3", "04103").put("第四名|4", "04104")
			.put("第四名|5", "04105").put("第四名|6", "04106").put("第四名|7", "04107").put("第四名|8", "04108")
			.put("第四名|9", "04109").put("第四名|10", "04110").put("第四名|大", "04201").put("第四名|小", "04202")
			.put("第四名|单", "04301").put("第四名|双", "04302").put("第四名|龙", "04401").put("第四名|虎", "04402")

			.put("第五名|1", "05101").put("第五名|2", "05102").put("第五名|3", "05103").put("第五名|4", "05104")
			.put("第五名|5", "05105").put("第五名|6", "05106").put("第五名|7", "05107").put("第五名|8", "05108")
			.put("第五名|9", "05109").put("第五名|10", "05110").put("第五名|大", "05201").put("第五名|小", "05202")
			.put("第五名|单", "05301").put("第五名|双", "05302").put("第五名|龙", "05401").put("第五名|虎", "05402")

			.put("第六名|1", "06101").put("第六名|2", "06102").put("第六名|3", "06103").put("第六名|4", "06104")
			.put("第六名|5", "06105").put("第六名|6", "06106").put("第六名|7", "06107").put("第六名|8", "06108")
			.put("第六名|9", "06109").put("第六名|10", "06110").put("第六名|大", "06201").put("第六名|小", "06202")
			.put("第六名|单", "06301").put("第六名|双", "06302")

			.put("第七名|1", "07101").put("第七名|2", "07102").put("第七名|3", "07103").put("第七名|4", "07104")
			.put("第七名|5", "07105").put("第七名|6", "07106").put("第七名|7", "07107").put("第七名|8", "07108")
			.put("第七名|9", "07109").put("第七名|10", "07110").put("第七名|大", "07201").put("第七名|小", "07202")
			.put("第七名|单", "07301").put("第七名|双", "07302")

			.put("第八名|1", "08101").put("第八名|2", "08102").put("第八名|3", "08103").put("第八名|4", "08104")
			.put("第八名|5", "08105").put("第八名|6", "08106").put("第八名|7", "08107").put("第八名|8", "08108")
			.put("第八名|9", "08109").put("第八名|10", "08110").put("第八名|大", "08201").put("第八名|小", "08202")
			.put("第八名|单", "08301").put("第八名|双", "08302")

			.put("第九名|1", "09101").put("第九名|2", "09102").put("第九名|3", "09103").put("第九名|4", "09104")
			.put("第九名|5", "09105").put("第九名|6", "09106").put("第九名|7", "09107").put("第九名|8", "09108")
			.put("第九名|9", "09109").put("第九名|10", "09110").put("第九名|大", "09201").put("第九名|小", "09202")
			.put("第九名|单", "09301").put("第九名|双", "09302")

			.put("第十名|1", "10101").put("第十名|2", "10102").put("第十名|3", "10103").put("第十名|4", "10104")
			.put("第十名|5", "10105").put("第十名|6", "10106").put("第十名|7", "10107").put("第十名|8", "10108")
			.put("第十名|9", "10109").put("第十名|10", "10110").put("第十名|大", "10201").put("第十名|小", "10202")
			.put("第十名|单", "10301").put("第十名|双", "10302")

			.put("冠亚|3", "00103").put("冠亚|4", "00104").put("冠亚|5", "00105").put("冠亚|6", "00106").put("冠亚|7", "00107")
			.put("冠亚|8", "00108").put("冠亚|9", "00109").put("冠亚|10", "00110").put("冠亚|11", "00111").put("冠亚|12", "00112")
			.put("冠亚|13", "00113").put("冠亚|14", "00114").put("冠亚|15", "00115").put("冠亚|16", "00116")
			.put("冠亚|17", "00117").put("冠亚|18", "00118").put("冠亚|19", "00119").put("冠亚|大", "00201").put("冠亚|小", "00202")
			.put("冠亚|单", "00301").put("冠亚|双", "00302").build();

	/**
	 * 投注限制
	 */
	Map<String, String> BET_LIMIT_CODE = ImmutableMap.<String, String>builder().put("第一名|1", "03").put("第一名|2", "03")
			.put("第一名|3", "03").put("第一名|4", "03").put("第一名|5", "03").put("第一名|6", "03").put("第一名|7", "03")
			.put("第一名|8", "03").put("第一名|9", "03").put("第一名|10", "03").put("第一名|大", "04").put("第一名|小", "04")
			.put("第一名|单", "04").put("第一名|双", "04").put("第一名|龙", "05").put("第一名|虎", "05")

			.put("第二名|1", "03").put("第二名|2", "03").put("第二名|3", "03").put("第二名|4", "03").put("第二名|5", "03")
			.put("第二名|6", "03").put("第二名|7", "03").put("第二名|8", "03").put("第二名|9", "03").put("第二名|10", "03")
			.put("第二名|大", "04").put("第二名|小", "04").put("第二名|单", "04").put("第二名|双", "04").put("第二名|龙", "05")
			.put("第二名|虎", "05")

			.put("第三名|1", "03").put("第三名|2", "03").put("第三名|3", "03").put("第三名|4", "03").put("第三名|5", "03")
			.put("第三名|6", "03").put("第三名|7", "03").put("第三名|8", "03").put("第三名|9", "03").put("第三名|10", "03")
			.put("第三名|大", "04").put("第三名|小", "04").put("第三名|单", "04").put("第三名|双", "04").put("第三名|龙", "05")
			.put("第三名|虎", "05")

			.put("第四名|1", "03").put("第四名|2", "03").put("第四名|3", "03").put("第四名|4", "03").put("第四名|5", "03")
			.put("第四名|6", "03").put("第四名|7", "03").put("第四名|8", "03").put("第四名|9", "03").put("第四名|10", "03")
			.put("第四名|大", "04").put("第四名|小", "04").put("第四名|单", "04").put("第四名|双", "04").put("第四名|龙", "05")
			.put("第四名|虎", "05")

			.put("第五名|1", "03").put("第五名|2", "03").put("第五名|3", "03").put("第五名|4", "03").put("第五名|5", "03")
			.put("第五名|6", "03").put("第五名|7", "03").put("第五名|8", "03").put("第五名|9", "03").put("第五名|10", "03")
			.put("第五名|大", "04").put("第五名|小", "04").put("第五名|单", "04").put("第五名|双", "04").put("第五名|龙", "05")
			.put("第五名|虎", "05")

			.put("第六名|1", "03").put("第六名|2", "03").put("第六名|3", "03").put("第六名|4", "03").put("第六名|5", "03")
			.put("第六名|6", "03").put("第六名|7", "03").put("第六名|8", "03").put("第六名|9", "03").put("第六名|10", "03")
			.put("第六名|大", "04").put("第六名|小", "04").put("第六名|单", "04").put("第六名|双", "04")

			.put("第七名|1", "03").put("第七名|2", "03").put("第七名|3", "03").put("第七名|4", "03").put("第七名|5", "03")
			.put("第七名|6", "03").put("第七名|7", "03").put("第七名|8", "03").put("第七名|9", "03").put("第七名|10", "03")
			.put("第七名|大", "04").put("第七名|小", "04").put("第七名|单", "04").put("第七名|双", "04")

			.put("第八名|1", "03").put("第八名|2", "03").put("第八名|3", "03").put("第八名|4", "03").put("第八名|5", "03")
			.put("第八名|6", "03").put("第八名|7", "03").put("第八名|8", "03").put("第八名|9", "03").put("第八名|10", "03")
			.put("第八名|大", "04").put("第八名|小", "04").put("第八名|单", "04").put("第八名|双", "04")

			.put("第九名|1", "03").put("第九名|2", "03").put("第九名|3", "03").put("第九名|4", "03").put("第九名|5", "03")
			.put("第九名|6", "03").put("第九名|7", "03").put("第九名|8", "03").put("第九名|9", "03").put("第九名|10", "03")
			.put("第九名|大", "04").put("第九名|小", "04").put("第九名|单", "04").put("第九名|双", "04")

			.put("第十名|1", "03").put("第十名|2", "03").put("第十名|3", "03").put("第十名|4", "03").put("第十名|5", "03")
			.put("第十名|6", "03").put("第十名|7", "03").put("第十名|8", "03").put("第十名|9", "03").put("第十名|10", "03")
			.put("第十名|大", "04").put("第十名|小", "04").put("第十名|单", "04").put("第十名|双", "04")

			.put("冠亚|3", "00").put("冠亚|4", "00").put("冠亚|5", "00").put("冠亚|6", "00").put("冠亚|7", "00").put("冠亚|8", "00")
			.put("冠亚|9", "00").put("冠亚|10", "00").put("冠亚|11", "00").put("冠亚|12", "00").put("冠亚|13", "00")
			.put("冠亚|14", "00").put("冠亚|15", "00").put("冠亚|16", "00").put("冠亚|17", "00").put("冠亚|18", "00")
			.put("冠亚|19", "00").put("冠亚|大", "01").put("冠亚|小", "01").put("冠亚|单", "02").put("冠亚|双", "02").build();

	Map<String, String> BET_INFO_CODE = ImmutableMap.<String, String>builder().put("01101", "第一名|1")
			.put("01102", "第一名|2").put("01103", "第一名|3").put("01104", "第一名|4").put("01105", "第一名|5")
			.put("01106", "第一名|6").put("01107", "第一名|7").put("01108", "第一名|8").put("01109", "第一名|9")
			.put("01110", "第一名|10").put("01201", "第一名|大").put("01202", "第一名|小").put("01301", "第一名|单")
			.put("01302", "第一名|双").put("01401", "第一名|龙").put("01402", "第一名|虎")

			.put("02101", "第二名|1").put("02102", "第二名|2").put("02103", "第二名|3").put("02104", "第二名|4")
			.put("02105", "第二名|5").put("02106", "第二名|6").put("02107", "第二名|7").put("02108", "第二名|8")
			.put("02109", "第二名|9").put("02110", "第二名|10").put("02401", "第二名|龙").put("02402", "第二名|虎")
			.put("02301", "第二名|单").put("02302", "第二名|双").put("02201", "第二名|大").put("02202", "第二名|小")

			.put("03101", "第三名|1").put("03102", "第三名|2").put("03103", "第三名|3").put("03104", "第三名|4")
			.put("03105", "第三名|5").put("03106", "第三名|6").put("03107", "第三名|7").put("03108", "第三名|8")
			.put("03109", "第三名|9").put("03110", "第三名|10").put("03402", "第三名|虎").put("03401", "第三名|龙")
			.put("03301", "第三名|单").put("03302", "第三名|双").put("03201", "第三名|大").put("03202", "第三名|小")

			.put("04101", "第四名|1").put("04102", "第四名|2").put("04103", "第四名|3").put("04104", "第四名|4")
			.put("04105", "第四名|5").put("04106", "第四名|6").put("04107", "第四名|7").put("04108", "第四名|8")
			.put("04109", "第四名|9").put("04110", "第四名|10").put("04402", "第四名|虎").put("04401", "第四名|龙")
			.put("04301", "第四名|单").put("04302", "第四名|双").put("04201", "第四名|大").put("04202", "第四名|小")

			.put("05101", "第五名|1").put("05102", "第五名|2").put("05103", "第五名|3").put("05104", "第五名|4")
			.put("05105", "第五名|5").put("05106", "第五名|6").put("05107", "第五名|7").put("05108", "第五名|8")
			.put("05109", "第五名|9").put("05110", "第五名|10").put("05402", "第五名|虎").put("05401", "第五名|龙")
			.put("05301", "第五名|单").put("05302", "第五名|双").put("05201", "第五名|大").put("05202", "第五名|小")

			.put("06101", "第六名|1").put("06102", "第六名|2").put("06103", "第六名|3").put("06104", "第六名|4")
			.put("06105", "第六名|5").put("06106", "第六名|6").put("06107", "第六名|7").put("06108", "第六名|8")
			.put("06109", "第六名|9").put("06110", "第六名|10").put("06301", "第六名|单").put("06302", "第六名|双")
			.put("06201", "第六名|大").put("06202", "第六名|小")

			.put("07101", "第七名|1").put("07102", "第七名|2").put("07103", "第七名|3").put("07104", "第七名|4")
			.put("07105", "第七名|5").put("07106", "第七名|6").put("07107", "第七名|7").put("07108", "第七名|8")
			.put("07109", "第七名|9").put("07110", "第七名|10").put("07301", "第七名|单").put("07302", "第七名|双")
			.put("07201", "第七名|大").put("07202", "第七名|小")

			.put("08101", "第八名|1").put("08102", "第八名|2").put("08103", "第八名|3").put("08104", "第八名|4")
			.put("08105", "第八名|5").put("08106", "第八名|6").put("08107", "第八名|7").put("08108", "第八名|8")
			.put("08109", "第八名|9").put("08110", "第八名|10").put("08301", "第八名|单").put("08302", "第八名|双")
			.put("08201", "第八名|大").put("08202", "第八名|小")

			.put("09101", "第九名|1").put("09102", "第九名|2").put("09103", "第九名|3").put("09104", "第九名|4")
			.put("09105", "第九名|5").put("09106", "第九名|6").put("09107", "第九名|7").put("09108", "第九名|8")
			.put("09109", "第九名|9").put("09110", "第九名|10").put("09301", "第九名|单").put("09302", "第九名|双")
			.put("09201", "第九名|大").put("09202", "第九名|小")

			.put("10101", "第十名|1").put("10102", "第十名|2").put("10103", "第十名|3").put("10104", "第十名|4")
			.put("10105", "第十名|5").put("10106", "第十名|6").put("10107", "第十名|7").put("10108", "第十名|8")
			.put("10109", "第十名|9").put("10110", "第十名|10").put("10301", "第十名|单").put("10302", "第十名|双")
			.put("10201", "第十名|大").put("10202", "第十名|小")

			.put("00103", "冠亚|3").put("00104", "冠亚|4").put("00105", "冠亚|5").put("00106", "冠亚|6").put("00107", "冠亚|7")
			.put("00108", "冠亚|8").put("00109", "冠亚|9").put("00110", "冠亚|10").put("00111", "冠亚|11").put("00112", "冠亚|12")
			.put("00113", "冠亚|13").put("00114", "冠亚|14").put("00115", "冠亚|15").put("00116", "冠亚|16")
			.put("00117", "冠亚|17").put("00118", "冠亚|18").put("00119", "冠亚|19").put("00301", "冠亚|单").put("00302", "冠亚|双")
			.put("00201", "冠亚|大").put("00202", "冠亚|小").build();

	Map<String, String> BET_RESULT_CODE = ImmutableMap.<String, String>builder().put("冠军1", "第一名|1").put("冠军2", "第一名|2")
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

			.put("冠亚和3", "冠亚|3").put("冠亚和4", "冠亚|4").put("冠亚和5", "冠亚|5").put("冠亚和6", "冠亚|6").put("冠亚和7", "冠亚|7")
			.put("冠亚和8", "冠亚|8").put("冠亚和9", "冠亚|9").put("冠亚和10", "冠亚|10").put("冠亚和11", "冠亚|11").put("冠亚和12", "冠亚|12")
			.put("冠亚和13", "冠亚|13").put("冠亚和14", "冠亚|14").put("冠亚和15", "冠亚|15").put("冠亚和16", "冠亚|16")
			.put("冠亚和17", "冠亚|17").put("冠亚和18", "冠亚|18").put("冠亚和19", "冠亚|19").put("冠亚和单", "冠亚|单").put("冠亚和双", "冠亚|双")
			.put("冠亚和大", "冠亚|大").put("冠亚和小", "冠亚|小").build();
}
