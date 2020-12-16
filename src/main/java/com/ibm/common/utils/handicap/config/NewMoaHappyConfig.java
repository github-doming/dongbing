package com.ibm.common.utils.handicap.config;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @Description:
 * @Author: zjj
 * @Date: 2020-05-07 14:45
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public interface NewMoaHappyConfig {

	/**
	 * 所有球码code
	 */
	Map<String, String> BALL_CODE = ImmutableMap.<String, String>builder().put("第一球|1", "B1-01").put("第一球|2", "B1-02")
			.put("第一球|3", "B1-03").put("第一球|4", "B1-04").put("第一球|5", "B1-05").put("第一球|6", "B1-06")
			.put("第一球|7", "B1-07").put("第一球|8", "B1-08").put("第一球|9", "B1-09").put("第一球|10", "B1-10")
			.put("第一球|11", "B1-10").put("第一球|12", "B1-12").put("第一球|13", "B1-13").put("第一球|14", "B1-14")
			.put("第一球|15", "B1-15").put("第一球|16", "B1-16").put("第一球|17", "B1-17").put("第一球|18", "B1-18")
			.put("第一球|19", "B1-19").put("第一球|20", "B1-20")

			.put("第一球|龙", "LH14-1L").put("第一球|虎", "LH14-1H")
			.put("第一球|大", "DX-11").put("第一球|小", "DX-10").put("第一球|单", "DS-11").put("第一球|双", "DS-12")
			.put("第一球|尾大", "WDX-11").put("第一球|尾小", "WDX-10").put("第一球|合单", "HDS-11").put("第一球|合双", "HDS-12")
			.put("第一球|中", "ZFB-1Z").put("第一球|发", "ZFB-1F").put("第一球|白", "ZFB-1B")
			.put("第一球|东", "FW-1E").put("第一球|南", "FW-1S").put("第一球|西", "FW-1W").put("第一球|北", "FW-1N")

			.put("第二球|1", "B2-01").put("第二球|2", "B2-02").put("第二球|3", "B2-03").put("第二球|4", "B2-04")
			.put("第二球|5", "B2-05").put("第二球|6", "B2-03").put("第二球|7", "B2-07").put("第二球|8", "B2-08")
			.put("第二球|9", "B2-09").put("第二球|10", "B2-10").put("第二球|11", "B2-11").put("第二球|12", "B2-12")
			.put("第二球|13", "B2-13").put("第二球|14", "B2-14").put("第二球|15", "B2-15").put("第二球|16", "B2-16")
			.put("第二球|17", "B2-17").put("第二球|18", "B2-18").put("第二球|19", "B2-19").put("第二球|20", "B2-20")

			.put("第2球-龍", "第一球|龙").put("第2球-虎", "第一球|虎")
			.put("第二球|大", "DX-21").put("第二球|小", "DX-20").put("第二球|单", "DS-21").put("第二球|双", "DS-22")
			.put("第二球|尾大", "WDX-21").put("第二球|尾小", "WDX-20").put("第二球|合单", "HDS-21").put("第二球|合双", "HDS-22")
			.put("第二球|中", "ZFB-2Z").put("第二球|发", "ZFB-2F").put("第二球|白", "ZFB-2B")
			.put("第二球|东", "FW-2E").put("第二球|南", "FW-2S").put("第二球|西", "FW-2W").put("第二球|北", "FW-2N")

			.put("第三球|1", "B3-01").put("第三球|2", "B3-02").put("第三球|3", "B3-03").put("第三球|4", "B3-04")
			.put("第三球|5", "B3-05").put("第三球|6", "B3-06").put("第三球|7", "76").put("第三球|8", "B3-08")
			.put("第三球|9", "B3-09").put("第三球|10", "B3-10").put("第三球|11", "B3-11").put("第三球|12", "B3-12")
			.put("第三球|13", "B3-13").put("第三球|14", "B3-14").put("第三球|15", "B3-15").put("第三球|16", "B3-16")
			.put("第三球|17", "B3-17").put("第三球|18", "B3-18").put("第三球|19", "B3-19").put("第三球|20", "B3-20")

			.put("第3球-龍", "第一球|龙").put("第3球-虎", "第一球|虎")
			.put("第三球|大", "DX-31").put("第三球|小", "DX-30").put("第三球|单", "DS-21").put("第三球|双", "DS-22")
			.put("第三球|尾大", "WDX-31").put("第三球|尾小", "WDX-30").put("第三球|合单", "HDS-21").put("第三球|合双", "HDS-22")
			.put("第三球|中", "ZFB-3Z").put("第三球|发", "ZFB-3F").put("第三球|白", "ZFB-3B")
			.put("第三球|东", "FW-3E").put("第三球|南", "FW-3S").put("第三球|西", "FW-3W").put("第三球|北", "FW-3N")


			.put("第四球|1", "B4-01").put("第四球|2", "B4-02").put("第四球|3", "B4-03").put("第四球|4", "B4-04")
			.put("第四球|5", "B4-05").put("第四球|6", "B4-06").put("第四球|7", "B4-07").put("第四球|8", "B4-08")
			.put("第四球|9", "B4-09").put("第四球|10", "B4-10").put("第四球|11", "B4-11").put("第四球|12", "B4-12")
			.put("第四球|13", "B4-13").put("第四球|14", "B4-14").put("第四球|15", "B4-15").put("第四球|16", "B4-16")
			.put("第四球|17", "B4-17").put("第四球|18", "B4-18").put("第四球|19", "B4-19").put("第四球|20", "B4-20")

			.put("第4球-龍", "第一球|龙").put("第4球-虎", "第一球|虎")
			.put("第四球|大", "DX-41").put("第四球|小", "DX-40").put("第四球|单", "DS-21").put("第四球|双", "DS-22")
			.put("第四球|尾大", "WDX-41").put("第四球|尾小", "WDX-40").put("第四球|合单", "HDS-21").put("第四球|合双", "HDS-22")
			.put("第四球|中", "ZFB-4Z").put("第四球|发", "ZFB-4F").put("第四球|白", "ZFB-4B")
			.put("第四球|东", "FW-4E").put("第四球|南", "FW-4S").put("第四球|西", "FW-4W").put("第四球|北", "FW-4N")


			.put("第五球|1", "B5-01").put("第五球|2", "B5-02").put("第五球|3", "B5-03").put("第五球|4", "B5-04")
			.put("第五球|5", "B5-05").put("第五球|6", "B5-06").put("第五球|7", "B5-07").put("第五球|8", "B5-08")
			.put("第五球|9", "B5-09").put("第五球|10", "B5-10").put("第五球|11", "B5-11").put("第五球|12", "B5-12")
			.put("第五球|13", "B5-13").put("第五球|14", "B5-14").put("第五球|15", "B5-15").put("第五球|16", "B5-16")
			.put("第五球|17", "B5-17").put("第五球|18", "B5-18").put("第五球|19", "B5-19").put("第五球|20", "B5-20")

			.put("第五球|大", "DX-51").put("第五球|小", "DX-50").put("第五球|单", "DS-21").put("第五球|双", "DS-22")
			.put("第五球|尾大", "WDX-51").put("第五球|尾小", "WDX-50").put("第五球|合单", "HDS-21").put("第五球|合双", "HDS-22")
			.put("第五球|中", "ZFB-5Z").put("第五球|发", "ZFB-5F").put("第五球|白", "ZFB-5B")
			.put("第五球|东", "FW-5E").put("第五球|南", "FW-5S").put("第五球|西", "FW-5W").put("第五球|北", "FW-5N")


			.put("第六球|1", "B6-01").put("第六球|2", "B6-02").put("第六球|3", "B6-03").put("第六球|4", "B6-04")
			.put("第六球|5", "B6-05").put("第六球|6", "B6-06").put("第六球|7", "B6-07").put("第六球|8", "B6-08")
			.put("第六球|9", "B6-09").put("第六球|10", "B6-10").put("第六球|11", "B6-11").put("第六球|12", "B6-12")
			.put("第六球|13", "B6-13").put("第六球|14", "B6-14").put("第六球|15", "B6-15").put("第六球|16", "B6-16")
			.put("第六球|17", "B6-17").put("第六球|18", "B6-18").put("第六球|19", "B6-19").put("第六球|20", "B6-20")

			.put("第六球|大", "DX-61").put("第六球|小", "DX-60").put("第六球|单", "DS-").put("第六球|双", "DS-")
			.put("第六球|尾大", "WDX-61").put("第六球|尾小", "WDX-60").put("第六球|合单", "HDS-21").put("第六球|合双", "HDS-22")
			.put("第六球|中", "ZFB-6Z").put("第六球|发", "ZFB-6F").put("第六球|白", "ZFB-6B")
			.put("第六球|东", "FW-6E").put("第六球|南", "FW-6S").put("第六球|西", "FW-6W").put("第六球|北", "FW-6N")


			.put("第七球|1", "B7-01").put("第七球|2", "B7-02").put("第七球|3", "B7-03").put("第七球|4", "B7-04")
			.put("第七球|5", "B7-05").put("第七球|6", "B7-06").put("第七球|7", "B7-07").put("第七球|8", "B7-08")
			.put("第七球|9", "B7-09").put("第七球|10", "B7-10").put("第七球|11", "B7-11").put("第七球|12", "B7-12")
			.put("第七球|13", "B7-13").put("第七球|14", "B7-14").put("第七球|15", "B7-15").put("第七球|16", "B7-16")
			.put("第七球|17", "B7-17").put("第七球|18", "B7-18").put("第七球|19", "B7-19").put("第七球|20", "B7-20")

			.put("第七球|大", "DX-71").put("第七球|小", "DX-70").put("第七球|单", "DS-").put("第七球|双", "DS-")
			.put("第七球|尾大", "WDX-71").put("第七球|尾小", "WDX-70").put("第七球|合单", "HDS-21").put("第七球|合双", "HDS-22")
			.put("第七球|中", "ZFB-7Z").put("第七球|发", "ZFB-1F").put("第七球|白", "ZFB-7B")
			.put("第七球|东", "FW-7E").put("第七球|南", "FW-7S").put("第七球|西", "FW-7W").put("第七球|北", "FW-7N")


			.put("第八球|1", "B8-01").put("第八球|2", "B8-02").put("第八球|3", "B8-03").put("第八球|4", "B8-04")
			.put("第八球|5", "B8-05").put("第八球|6", "B8-06").put("第八球|7", "B8-07").put("第八球|8", "B8-08")
			.put("第八球|9", "B8-09").put("第八球|10", "B8-10").put("第八球|11", "B8-11").put("第八球|12", "B8-12")
			.put("第八球|13", "B8-13").put("第八球|14", "B8-14").put("第八球|15", "B8-15").put("第八球|16", "B8-16")
			.put("第八球|17", "B8-17").put("第八球|18", "B8-18").put("第八球|19", "B8-19").put("第八球|20", "B8-20")

			.put("第八球|大", "DX-81").put("第八球|小", "DX-80").put("第八球|单", "DS-81").put("第八球|双", "DS-82")
			.put("第八球|尾大", "WDX-81").put("第八球|尾小", "WDX-80").put("第八球|合单", "HDS-21").put("第八球|合双", "HDS-22")
			.put("第八球|中", "ZFB-8Z").put("第八球|发", "ZFB-8F").put("第八球|白", "ZFB-8B")
			.put("第八球|东", "FW-8E").put("第八球|南", "FW-8S").put("第八球|西", "FW-8W").put("第八球|北", "FW-8N")


			.put("总和|大", "ZDX-1").put("总和|小", "ZDX-0").put("总和|单", "ZDS-1").put("总和|双", "ZDS-2")
			.put("总和|尾大", "ZWDX-1").put("总和|尾小", "ZWDX-0").put("总和|龙", "LH-L").put("总和|虎", "LH-h")
			.build();

	/**
	 * 投注信息
	 */
	Map<String, String> BET_INFO_CODE = ImmutableMap.<String, String>builder().put("第一球-1", "第一球|1")
			.put("第一球-2", "第一球|2").put("第一球-3", "第一球|3").put("第一球-4", "第一球|4").put("第一球-5", "第一球|5")
			.put("第一球-6", "第一球|6").put("第一球-7", "第一球|7").put("第一球-8", "第一球|8").put("第一球-9", "第一球|9")
			.put("第一球-10", "第一球|10").put("第一球-11", "第一球|11").put("第一球-12", "第一球|12").put("第一球-13", "第一球|13")
			.put("第一球-14", "第一球|14").put("第一球-15", "第一球|15").put("第一球-16", "第一球|16").put("第一球-17", "第一球|17")
			.put("第一球-18", "第一球|18").put("第一球-19", "第一球|19").put("第一球-20", "第一球|20")

			.put("第一球-单", "第一球|单").put("第一球-双", "第一球|双").put("第一球-大", "第一球|大").put("第一球-小", "第一球|小")
			.put("第一球-合单", "第一球|合单").put("第一球-合双", "第一球|合双").put("第一球-尾大", "第一球|尾大").put("第一球-尾小", "第一球|尾小")
			.put("第一球-东", "第一球|东").put("第一球-南", "第一球|南").put("第一球-西", "第一球|西").put("第一球-北", "第一球|北")
			.put("第一球中-中", "第一球|中").put("第一球-发", "第一球|发").put("第一球-白", "第一球|白")

			.put("第二球-1", "第二球|1").put("第二球-2", "第二球|2").put("第二球-3", "第二球|3").put("第二球-4", "第二球|4")
			.put("第二球-5", "第二球|5").put("第二球-6", "第二球|6").put("第二球-7", "第二球|7").put("第二球-8", "第二球|8")
			.put("第二球-9", "第二球|9").put("第二球-10", "第二球|10").put("第二球-11", "第二球|11").put("第二球-12", "第二球|12")
			.put("第二球-13", "第二球|13").put("第二球-14", "第二球|14").put("第二球-15", "第二球|15").put("第二球-16", "第二球|16")
			.put("第二球-17", "第二球|17").put("第二球-18", "第二球|18").put("第二球-19", "第二球|19").put("第二球-20", "第二球|20")

			.put("第二球-单", "第二球|单").put("第二球双", "第二球|双").put("第二球大", "第二球|大").put("第二球小", "第二球|小")
			.put("第二球-合单", "第二球|合单").put("第二球-合双", "第二球|合双").put("第二球-尾大", "第二球|尾大").put("第二球-尾小", "第二球|尾小")
			.put("第二球-东", "第二球|东").put("第二球-南", "第二球|南").put("第二球-西", "第二球|西").put("第二球-北", "第二球|北")
			.put("第二球-中", "第二球|中").put("第二球-发", "第二球|发").put("第二球-白", "第二球|白")

			.put("第三球-1", "第三球|1").put("第三球-2", "第三球|2").put("第三球-3", "第三球|3").put("第三球-4", "第三球|4")
			.put("第三球-5", "第三球|5").put("第三球-6", "第三球|6").put("第三球-7", "第三球|7").put("第三球-8", "第三球|8")
			.put("第三球-9", "第三球|9").put("第三球-10", "第三球|10").put("第三球-11", "第三球|11").put("第三球-12", "第三球|12")
			.put("第三球-13", "第三球|13").put("第三球-14", "第三球|14").put("第三球-15", "第三球|15").put("第三球-16", "第三球|16")
			.put("第三球-17", "第三球|17").put("第三球-18", "第三球|18").put("第三球-19", "第三球|19").put("第三球-20", "第三球|20")

			.put("第三球-单", "第三球|单").put("第三球-双", "第三球|双").put("第三球-大", "第三球|大").put("第三球-小", "第三球|小")
			.put("第三球-合单", "第三球|合单").put("第三球-合双", "第三球|合双").put("第三球-尾大", "第三球|尾大").put("第三球-尾小", "第三球|尾小")
			.put("第三球-东", "第三球|东").put("第三球-南", "第三球|南").put("第三球-西", "第三球|西").put("第三球-北", "第三球|北")
			.put("第三球-中", "第三球|中").put("第三球中-发", "第三球|发").put("第三球-白", "第三球|白")

			.put("第四球-1", "第四球|1").put("第四球-2", "第四球|2").put("第四球-3", "第四球|3").put("第四球-4", "第四球|4")
			.put("第四球-5", "第四球|5").put("第四球-6", "第四球|6").put("第四球-7", "第四球|7").put("第四球-8", "第四球|8")
			.put("第四球-9", "第四球|9").put("第四球-10", "第四球|10").put("第四球-11", "第四球|11").put("第四球-12", "第四球|12")
			.put("第四球-13", "第四球|13").put("第四球-14", "第四球|14").put("第四球-15", "第四球|15").put("第四球-16", "第四球|16")
			.put("第四球-17", "第四球|17").put("第四球-18", "第四球|18").put("第四球-19", "第四球|19").put("第四球-20", "第四球|20")

			.put("第四球-单", "第四球|单").put("第四球-双", "第四球|双").put("第四球-大", "第四球|大").put("第四球-小", "第四球|小")
			.put("第四球-合单", "第四球|合单").put("第四球-合双", "第四球|合双").put("第四球-尾大", "第四球|尾大").put("第四球-尾小", "第四球|尾小")
			.put("第四球-东", "第四球|东").put("第四球-南", "第四球|南").put("第四球-西", "第四球|西").put("第四球-北", "第四球|北")
			.put("第四球-中", "第四球|中").put("第四球-发", "第四球|发").put("第四球-白", "第四球|白")

			.put("第五球-1", "第五球|1").put("第五球-2", "第五球|2").put("第五球-3", "第五球|3").put("第五球-4", "第五球|4")
			.put("第五球-5", "第五球|5").put("第五球-6", "第五球|6").put("第五球-7", "第五球|7").put("第五球-8", "第五球|8")
			.put("第五球-9", "第五球|9").put("第五球-10", "第五球|10").put("第五球-11", "第五球|11").put("第五球-12", "第五球|12")
			.put("第五球-13", "第五球|13").put("第五球-14", "第五球|14").put("第五球-15", "第五球|15").put("第五球-16", "第五球|16")
			.put("第五球-17", "第五球|17").put("第五球-18", "第五球|18").put("第五球-19", "第五球|19").put("第五球-20", "第五球|20")

			.put("第五球-单", "第五球|单").put("第五球-双", "第五球|双").put("第五球-大", "第五球|大").put("第五球-小", "第五球|小")
			.put("第五球-合单", "第五球|合单").put("第五球-合双", "第五球|合双").put("第五球-尾大", "第五球|尾大").put("第五球-尾小", "第五球|尾小")
			.put("第五球-东", "第五球|东").put("第五球-南", "第五球|南").put("第五球-西", "第五球|西").put("第五球-北", "第五球|北")
			.put("第五球-中", "第五球|中").put("第五球-发", "第五球|发").put("第五球-白", "第五球|白")

			.put("第六球-1", "第六球|1").put("第六球-2", "第六球|2").put("第六球-3", "第六球|3").put("第六球-4", "第六球|4")
			.put("第六球-5", "第六球|5").put("第六球-6", "第六球|6").put("第六球-7", "第六球|7").put("第六球-8", "第六球|8")
			.put("第六球-9", "第六球|9").put("第六球-10", "第六球|10").put("第六球-11", "第六球|11").put("第六球-12", "第六球|12")
			.put("第六球-13", "第六球|13").put("第六球-14", "第六球|14").put("第六球-15", "第六球|15").put("第六球-16", "第六球|16")
			.put("第六球-17", "第六球|17").put("第六球-18", "第六球|18").put("第六球-19", "第六球|19").put("第六球-20", "第六球|20")

			.put("第六球-单", "第六球|单").put("第六球-双", "第六球|双").put("第六球-大", "第六球|大").put("第六球-小", "第六球|小")
			.put("第六球-合单", "第六球|合单").put("第六球-合双", "第六球|合双").put("第六球-尾大", "第六球|尾大").put("第六球-尾小", "第六球|尾小")
			.put("第六球-东", "第六球|东").put("第六球-南", "第六球|南").put("第六球-西", "第六球|西").put("第六球-北", "第六球|北")
			.put("第六球-中", "第六球|中").put("第六球-发", "第六球|发").put("第六球-白", "第六球|白")

			.put("第七球-1", "第七球|1").put("第七球-2", "第七球|2").put("第七球-3", "第七球|3").put("第七球-4", "第七球|4")
			.put("第七球-5", "第七球|5").put("第七球-6", "第七球|6").put("第七球-7", "第七球|7").put("第七球-8", "第七球|8")
			.put("第七球-9", "第七球|9").put("第七球-10", "第七球|10").put("第七球-11", "第七球|11").put("第七球-12", "第七球|12")
			.put("第七球-13", "第七球|13").put("第七球-14", "第七球|14").put("第七球-15", "第七球|15").put("第七球-16", "第七球|16")
			.put("第七球-17", "第七球|17").put("第七球-18", "第七球|18").put("第七球-19", "第七球|19").put("第七球-20", "第七球|20")

			.put("第七球-单", "第七球|单").put("第七球-双", "第七球|双").put("第七球-大", "第七球|大").put("第七球-小", "第七球|小")
			.put("第七球-合单", "第七球|合单").put("第七球-合双", "第七球|合双").put("第七球-尾大", "第七球|尾大").put("第七球-尾小", "第七球|尾小")
			.put("第七球-东", "第七球|东").put("第七球-南", "第七球|南").put("第七球-西", "第七球|西").put("第七球-北", "第七球|北")
			.put("第七球-中", "第七球|中").put("第七球-发", "第七球|发").put("第七球-白", "第七球|白")

			.put("第八球-1", "第八球|1").put("第八球-2", "第八球|2").put("第八球-3", "第八球|3").put("第八球-4", "第八球|4")
			.put("第八球-5", "第八球|5").put("第八球-6", "第八球|6").put("第八球-7", "第八球|7").put("第八球-8", "第八球|8")
			.put("第八球-9", "第八球|9").put("第八球-10", "第八球|10").put("第八球-11", "第八球|11").put("第八球-12", "第八球|12")
			.put("第八球-13", "第八球|13").put("第八球-14", "第八球|14").put("第八球-15", "第八球|15").put("第八球-16", "第八球|16")
			.put("第八球-17", "第八球|17").put("第八球-18", "第八球|18").put("第八球-19", "第八球|19").put("第八球-20", "第八球|20")

			.put("第八球-单", "第八球|单").put("第八球-双", "第八球|双").put("第八球-大", "第八球|大").put("第八球-小", "第八球|小")
			.put("第八球-合单", "第八球|合单").put("第八球-合双", "第八球|合双").put("第八球-尾大", "第八球|尾大").put("第八球-尾小", "第八球|尾小")
			.put("第八球-东", "第八球|东").put("第八球-南", "第八球|南").put("第八球-西", "第八球|西").put("第八球-北", "第八球|北")
			.put("第八球-中", "第八球|中").put("第八球-发", "第八球|发").put("第八球-白", "第八球|白")

			.put("总和大", "总和|大").put("总和小", "总和|小").put("总和单", "总和|单").put("总和双", "总和|双").put("总和尾大", "总和|尾大")
			.put("总和尾小", "总和|尾小").put("虎", "总和|虎").put("龙", "总和|龙").build();


	Map<String, String> BET_LIMIT_CODE = ImmutableMap.<String, String>builder()
			.put("第一球|1", "03").put("第一球|2", "03").put("第一球|3", "03").put("第一球|4", "03").put("第一球|5", "03").put("第一球|6", "03")
			.put("第一球|7", "03").put("第一球|8", "03").put("第一球|9", "03").put("第一球|10", "03").put("第一球|11", "03")
			.put("第一球|12", "03").put("第一球|13", "03").put("第一球|14", "03").put("第一球|15", "03").put("第一球|16", "03")
			.put("第一球|17", "03").put("第一球|18", "03").put("第一球|19", "03").put("第一球|20", "03")
			.put("第一球|龙", "00").put("第一球|虎", "00")
			.put("第一球|单", "00").put("第一球|双", "00").put("第一球|大", "00").put("第一球|小", "00").put("第一球|合单", "00")
			.put("第一球|合双", "00").put("第一球|尾大", "00").put("第一球|尾小", "00").put("第一球|东", "00").put("第一球|南", "00")
			.put("第一球|西", "00").put("第一球|北", "00").put("第一球|中", "00").put("第一球|发", "00").put("第一球|白", "00")

			.put("第二球|1", "03").put("第二球|2", "03").put("第二球|3", "03").put("第二球|4", "03").put("第二球|5", "03").put("第二球|6", "03")
			.put("第二球|7", "03").put("第二球|8", "03").put("第二球|9", "03").put("第二球|10", "03").put("第二球|11", "03")
			.put("第二球|12", "03").put("第二球|13", "03").put("第二球|14", "03").put("第二球|15", "03").put("第二球|16", "03")
			.put("第二球|17", "03").put("第二球|18", "03").put("第二球|19", "03").put("第二球|20", "03")
			.put("第二球|龙", "00").put("第二球|虎", "00")
			.put("第二球|单", "00").put("第二球|双", "00").put("第二球|大", "00").put("第二球|小", "00").put("第二球|合单", "00")
			.put("第二球|合双", "00").put("第二球|尾大", "00").put("第二球|尾小", "00").put("第二球|东", "00").put("第二球|南", "00")
			.put("第二球|西", "00").put("第二球|北", "00").put("第二球|中", "00").put("第二球|发", "00").put("第二球|白", "00")

			.put("第三球|1", "03").put("第三球|2", "03").put("第三球|3", "03").put("第三球|4", "03").put("第三球|5", "03").put("第三球|6", "03")
			.put("第三球|7", "03").put("第三球|8", "03").put("第三球|9", "03").put("第三球|10", "03").put("第三球|11", "03")
			.put("第三球|12", "03").put("第三球|13", "03").put("第三球|14", "03").put("第三球|15", "03").put("第三球|16", "03")
			.put("第三球|17", "03").put("第三球|18", "03").put("第三球|19", "03").put("第三球|20", "03")
			.put("第三球|龙", "00").put("第三球|虎", "00")
			.put("第三球|单", "00").put("第三球|双", "00").put("第三球|大", "00").put("第三球|小", "00").put("第三球|合单", "00")
			.put("第三球|合双", "00").put("第三球|尾大", "00").put("第三球|尾小", "00").put("第三球|东", "00").put("第三球|南", "00")
			.put("第三球|西", "00").put("第三球|北", "00").put("第三球|中", "00").put("第三球|发", "00").put("第三球|白", "00")

			.put("第四球|1", "03").put("第四球|2", "03").put("第四球|3", "03").put("第四球|4", "03").put("第四球|5", "03").put("第四球|6", "03")
			.put("第四球|7", "03").put("第四球|8", "03").put("第四球|9", "03").put("第四球|10", "03").put("第四球|11", "03")
			.put("第四球|12", "03").put("第四球|13", "03").put("第四球|14", "03").put("第四球|15", "03").put("第四球|16", "03")
			.put("第四球|17", "03").put("第四球|18", "03").put("第四球|19", "03").put("第四球|20", "03")
			.put("第四球|龙", "00").put("第四球|虎", "00")
			.put("第四球|单", "00").put("第四球|双", "00").put("第四球|大", "00").put("第四球|小", "00").put("第四球|合单", "00")
			.put("第四球|合双", "00").put("第四球|尾大", "00").put("第四球|尾小", "00").put("第四球|东", "00").put("第四球|南", "00")
			.put("第四球|西", "00").put("第四球|北", "00").put("第四球|中", "00").put("第四球|发", "00").put("第四球|白", "00")

			.put("第五球|1", "03").put("第五球|2", "03").put("第五球|3", "03").put("第五球|4", "03").put("第五球|5", "03").put("第五球|6", "03")
			.put("第五球|7", "03").put("第五球|8", "03").put("第五球|9", "03").put("第五球|10", "03").put("第五球|11", "03")
			.put("第五球|12", "03").put("第五球|13", "03").put("第五球|14", "03").put("第五球|15", "03").put("第五球|16", "03")
			.put("第五球|17", "03").put("第五球|18", "03").put("第五球|19", "03").put("第五球|20", "03")

			.put("第五球|单", "00").put("第五球|双", "00").put("第五球|大", "00").put("第五球|小", "00").put("第五球|合单", "4")
			.put("第五球|合双", "00").put("第五球|尾大", "00").put("第五球|尾小", "00").put("第五球|东", "00").put("第五球|南", "00")
			.put("第五球|西", "00").put("第五球|北", "00").put("第五球|中", "00").put("第五球|发", "00").put("第五球|白", "00")

			.put("第六球|1", "03").put("第六球|2", "03").put("第六球|3", "03").put("第六球|4", "03").put("第六球|5", "03").put("第六球|6", "03")
			.put("第六球|7", "03").put("第六球|8", "03").put("第六球|9", "03").put("第六球|10", "03").put("第六球|11", "03")
			.put("第六球|12", "03").put("第六球|13", "03").put("第六球|14", "03").put("第六球|15", "03").put("第六球|16", "03")
			.put("第六球|17", "03").put("第六球|18", "03").put("第六球|19", "03").put("第六球|20", "03")

			.put("第六球|单", "00").put("第六球|双", "00").put("第六球|大", "00").put("第六球|小", "00").put("第六球|合单", "00")
			.put("第六球|合双", "00").put("第六球|尾大", "00").put("第六球|尾小", "00").put("第六球|东", "00").put("第六球|南", "00")
			.put("第六球|西", "00").put("第六球|北", "00").put("第六球|中", "00").put("第六球|发", "00").put("第六球|白", "00")

			.put("第七球|1", "03").put("第七球|2", "03").put("第七球|3", "03").put("第七球|4", "03").put("第七球|5", "03").put("第七球|6", "03")
			.put("第七球|7", "03").put("第七球|8", "03").put("第七球|9", "03").put("第七球|10", "03").put("第七球|11", "03")
			.put("第七球|12", "03").put("第七球|13", "03").put("第七球|14", "03").put("第七球|15", "03").put("第七球|16", "03")
			.put("第七球|17", "03").put("第七球|18", "03").put("第七球|19", "03").put("第七球|20", "03")

			.put("第七球|单", "00").put("第七球|双", "00").put("第七球|大", "00").put("第七球|小", "00").put("第七球|合单", "00")
			.put("第七球|合双", "00").put("第七球|尾大", "00").put("第七球|尾小", "00").put("第七球|东", "00").put("第七球|南", "00")
			.put("第七球|西", "00").put("第七球|北", "00").put("第七球|中", "00").put("第七球|发", "00").put("第七球|白", "00")

			.put("第八球|1", "03").put("第八球|2", "03").put("第八球|3", "03").put("第八球|4", "03").put("第八球|5", "03").put("第八球|6", "03")
			.put("第八球|7", "03").put("第八球|8", "03").put("第八球|9", "03").put("第八球|10", "03").put("第八球|11", "03")
			.put("第八球|12", "03").put("第八球|13", "03").put("第八球|14", "03").put("第八球|15", "03").put("第八球|16", "03")
			.put("第八球|17", "03").put("第八球|18", "03").put("第八球|19", "03").put("第八球|20", "03")

			.put("第八球|单", "00").put("第八球|双", "00").put("第八球|大", "00").put("第八球|小", "00").put("第八球|合单", "00")
			.put("第八球|合双", "00").put("第八球|尾大", "00").put("第八球|尾小", "00").put("第八球|东", "00").put("第八球|南", "00")
			.put("第八球|西", "00").put("第八球|北", "00").put("第八球|中", "00").put("第八球|发", "00").put("第八球|白", "00")

			.put("总和|大", "00").put("总和|小", "00").put("总和|单", "00").put("总和|双", "00").put("总和|尾大", "00").put("总和|尾小", "00")
			.put("总和|龙", "00").put("总和|虎", "00").build();

}
