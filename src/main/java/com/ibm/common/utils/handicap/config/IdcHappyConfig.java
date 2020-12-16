package com.ibm.common.utils.handicap.config;

import com.google.common.collect.ImmutableMap;
import com.ibm.common.core.configs.IdcConfig;

import java.util.Map;

/**
 * IDC快乐彩类配置类
 *
 * @Author: Dongming
 * @Date: 2020-04-23 15:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface IdcHappyConfig extends IdcConfig, HappyConfig {

	Map<String, String> BALL_CODE = ImmutableMap.<String, String>builder().put("第一球|1", "401:1").put("第一球|2", "401:2")
			.put("第一球|3", "401:3").put("第一球|4", "401:4").put("第一球|5", "401:5").put("第一球|6", "401:6")
			.put("第一球|7", "401:7").put("第一球|8", "401:8").put("第一球|9", "401:9").put("第一球|10", "401:10")
			.put("第一球|11", "401:11").put("第一球|12", "401:12").put("第一球|13", "401:13").put("第一球|14", "401:14")
			.put("第一球|15", "401:15").put("第一球|16", "401:16").put("第一球|17", "401:17").put("第一球|18", "401:18")
			.put("第一球|19", "401:19").put("第一球|20", "401:20")

			.put("第一球|单", "409:1").put("第一球|双", "409:2").put("第一球|大", "417:1").put("第一球|小", "417:2")
			.put("第一球|合单", "425:1").put("第一球|合双", "425:2").put("第一球|尾大", "433:1").put("第一球|尾小", "433:2")
			.put("第一球|东", "441:1").put("第一球|南", "441:2").put("第一球|西", "441:3").put("第一球|北", "441:4")
			.put("第一球|中", "449:1").put("第一球|发", "449:2").put("第一球|白", "449:3")

			.put("第二球|1", "402:1").put("第二球|2", "402:2").put("第二球|3", "402:3").put("第二球|4", "402:4")
			.put("第二球|5", "402:5").put("第二球|6", "402:6").put("第二球|7", "402:7").put("第二球|8", "402:8")
			.put("第二球|9", "402:9").put("第二球|10", "402:10").put("第二球|11", "402:11").put("第二球|12", "402:12")
			.put("第二球|13", "402:13").put("第二球|14", "402:14").put("第二球|15", "402:15").put("第二球|16", "402:16")
			.put("第二球|17", "402:17").put("第二球|18", "402:18").put("第二球|19", "402:19").put("第二球|20", "402:20")

			.put("第二球|单", "410:1").put("第二球|双", "410:2").put("第二球|大", "418:1").put("第二球|小", "418:2")
			.put("第二球|合单", "426:1").put("第二球|合双", "426:2").put("第二球|尾大", "434:1").put("第二球|尾小", "434:2")
			.put("第二球|东", "442:1").put("第二球|南", "442:2").put("第二球|西", "442:3").put("第二球|北", "442:4")
			.put("第二球|中", "450:1").put("第二球|发", "450:2").put("第二球|白", "450:3")

			.put("第三球|1", "403:1").put("第三球|2", "403:2").put("第三球|3", "403:3").put("第三球|4", "403:4")
			.put("第三球|5", "403:5").put("第三球|6", "403:6").put("第三球|7", "403:7").put("第三球|8", "403:8")
			.put("第三球|9", "403:9").put("第三球|10", "403:10").put("第三球|11", "403:11").put("第三球|12", "403:12")
			.put("第三球|13", "403:13").put("第三球|14", "403:14").put("第三球|15", "403:15").put("第三球|16", "403:16")
			.put("第三球|17", "403:17").put("第三球|18", "403:18").put("第三球|19", "403:19").put("第三球|20", "403:20")

			.put("第三球|单", "411:1").put("第三球|双", "411:2").put("第三球|大", "419:1").put("第三球|小", "419:2")
			.put("第三球|合单", "427:1").put("第三球|合双", "427:2").put("第三球|尾大", "435:1").put("第三球|尾小", "435:2")
			.put("第三球|东", "443:1").put("第三球|南", "443:2").put("第三球|西", "443:3").put("第三球|北", "443:4")
			.put("第三球|中", "451:1").put("第三球|发", "451:2").put("第三球|白", "451:3")

			.put("第四球|1", "404:1").put("第四球|2", "404:2").put("第四球|3", "404:3").put("第四球|4", "404:4")
			.put("第四球|5", "404:5").put("第四球|6", "404:6").put("第四球|7", "404:7").put("第四球|8", "404:8")
			.put("第四球|9", "404:9").put("第四球|10", "404:10").put("第四球|11", "404:11").put("第四球|12", "404:12")
			.put("第四球|13", "404:13").put("第四球|14", "404:14").put("第四球|15", "404:15").put("第四球|16", "404:16")
			.put("第四球|17", "404:17").put("第四球|18", "404:18").put("第四球|19", "404:19").put("第四球|20", "404:20")

			.put("第四球|单", "412:1").put("第四球|双", "412:2").put("第四球|大", "420:1").put("第四球|小", "420:2")
			.put("第四球|合单", "428:1").put("第四球|合双", "428:2").put("第四球|尾大", "436:1").put("第四球|尾小", "436:2")
			.put("第四球|东", "444:1").put("第四球|南", "444:2").put("第四球|西", "444:3").put("第四球|北", "444:4")
			.put("第四球|中", "452:1").put("第四球|发", "452:2").put("第四球|白", "452:3")

			.put("第五球|1", "405:1").put("第五球|2", "405:2").put("第五球|3", "405:3").put("第五球|4", "405:4")
			.put("第五球|5", "405:5").put("第五球|6", "405:6").put("第五球|7", "405:7").put("第五球|8", "405:8")
			.put("第五球|9", "405:9").put("第五球|10", "405:10").put("第五球|11", "405:11").put("第五球|12", "405:12")
			.put("第五球|13", "405:13").put("第五球|14", "405:14").put("第五球|15", "405:15").put("第五球|16", "405:16")
			.put("第五球|17", "405:17").put("第五球|18", "405:18").put("第五球|19", "405:19").put("第五球|20", "405:20")

			.put("第五球|单", "413:1").put("第五球|双", "413:2").put("第五球|大", "421:1").put("第五球|小", "421:2")
			.put("第五球|合单", "429:1").put("第五球|合双", "429:2").put("第五球|尾大", "437:1").put("第五球|尾小", "437:2")
			.put("第五球|东", "445:1").put("第五球|南", "445:2").put("第五球|西", "445:3").put("第五球|北", "445:4")
			.put("第五球|中", "453:1").put("第五球|发", "453:2").put("第五球|白", "453:3")

			.put("第六球|1", "406:1").put("第六球|2", "406:2").put("第六球|3", "406:3").put("第六球|4", "406:4")
			.put("第六球|5", "406:5").put("第六球|6", "406:6").put("第六球|7", "406:7").put("第六球|8", "406:8")
			.put("第六球|9", "406:9").put("第六球|10", "406:10").put("第六球|11", "406:11").put("第六球|12", "406:12")
			.put("第六球|13", "406:13").put("第六球|14", "406:14").put("第六球|15", "406:15").put("第六球|16", "406:16")
			.put("第六球|17", "406:17").put("第六球|18", "406:18").put("第六球|19", "406:19").put("第六球|20", "406:20")

			.put("第六球|单", "414:1").put("第六球|双", "414:2").put("第六球|大", "422:1").put("第六球|小", "422:2")
			.put("第六球|合单", "430:1").put("第六球|合双", "430:2").put("第六球|尾大", "438:1").put("第六球|尾小", "438:2")
			.put("第六球|东", "446:1").put("第六球|南", "446:2").put("第六球|西", "446:3").put("第六球|北", "446:4")
			.put("第六球|中", "454:1").put("第六球|发", "454:2").put("第六球|白", "454:3")

			.put("第七球|1", "407:1").put("第七球|2", "407:2").put("第七球|3", "407:3").put("第七球|4", "407:4")
			.put("第七球|5", "407:5").put("第七球|6", "407:6").put("第七球|7", "407:7").put("第七球|8", "407:8")
			.put("第七球|9", "407:9").put("第七球|10", "407:10").put("第七球|11", "407:11").put("第七球|12", "407:12")
			.put("第七球|13", "407:13").put("第七球|14", "407:14").put("第七球|15", "407:15").put("第七球|16", "407:16")
			.put("第七球|17", "407:17").put("第七球|18", "407:18").put("第七球|19", "407:19").put("第七球|20", "407:20")

			.put("第七球|单", "415:1").put("第七球|双", "415:2").put("第七球|大", "423:1").put("第七球|小", "423:2")
			.put("第七球|合单", "431:1").put("第七球|合双", "431:2").put("第七球|尾大", "439:1").put("第七球|尾小", "439:2")
			.put("第七球|东", "447:1").put("第七球|南", "447:2").put("第七球|西", "447:3").put("第七球|北", "447:4")
			.put("第七球|中", "455:1").put("第七球|发", "455:2").put("第七球|白", "455:3")

			.put("第八球|1", "408:1").put("第八球|2", "408:2").put("第八球|3", "408:3").put("第八球|4", "408:4")
			.put("第八球|5", "408:5").put("第八球|6", "408:6").put("第八球|7", "408:7").put("第八球|8", "408:8")
			.put("第八球|9", "408:9").put("第八球|10", "408:10").put("第八球|11", "408:11").put("第八球|12", "408:12")
			.put("第八球|13", "408:13").put("第八球|14", "408:14").put("第八球|15", "408:15").put("第八球|16", "408:16")
			.put("第八球|17", "408:17").put("第八球|18", "408:18").put("第八球|19", "408:19").put("第八球|20", "408:20")

			.put("第八球|单", "416:1").put("第八球|双", "416:2").put("第八球|大", "424:1").put("第八球|小", "424:2")
			.put("第八球|合单", "432:1").put("第八球|合双", "432:2").put("第八球|尾大", "440:1").put("第八球|尾小", "440:2")
			.put("第八球|东", "448:1").put("第八球|南", "448:2").put("第八球|西", "448:3").put("第八球|北", "448:4")
			.put("第八球|中", "456:1").put("第八球|发", "456:2").put("第八球|白", "456:3")

			.put("总和|大", "458:1").put("总和|小", "458:2").put("总和|单", "457:1").put("总和|双", "457:2").put("总和|尾大", "459:1")
			.put("总和|尾小", "459:2").put("总和|龙", "460:1").put("总和|虎", "460:2")

			.build();

	Map<String, String> BET_INFO_CODE = ImmutableMap.<String, String>builder().put("第一球01", "第一球|1")
			.put("第一球02", "第一球|2").put("第一球03", "第一球|3").put("第一球04", "第一球|4").put("第一球05", "第一球|5")
			.put("第一球06", "第一球|6").put("第一球07", "第一球|7").put("第一球08", "第一球|8").put("第一球09", "第一球|9")
			.put("第一球10", "第一球|10").put("第一球11", "第一球|11").put("第一球12", "第一球|12").put("第一球13", "第一球|13")
			.put("第一球14", "第一球|14").put("第一球15", "第一球|15").put("第一球16", "第一球|16").put("第一球17", "第一球|17")
			.put("第一球18", "第一球|18").put("第一球19", "第一球|19").put("第一球20", "第一球|20")

			.put("第1球单双单", "第一球|单").put("第1球单双双", "第一球|双").put("第1球大小大", "第一球|大").put("第1球大小小", "第一球|小")
			.put("第1球合数单双单", "第一球|合单").put("第1球合数单双双", "第一球|合双").put("第1球尾数大小大", "第一球|尾大").put("第1球尾数大小小", "第一球|尾小")
			.put("第1球方向东", "第一球|东").put("第1球方向南", "第一球|南").put("第1球方向西", "第一球|西").put("第1球方向北", "第一球|北")
			.put("第1球中发白中", "第一球|中").put("第1球中发白发", "第一球|发").put("第1球中发白白", "第一球|白")

			.put("第二球01", "第二球|1").put("第二球02", "第二球|2").put("第二球03", "第二球|3").put("第二球04", "第二球|4")
			.put("第二球05", "第二球|5").put("第二球06", "第二球|6").put("第二球07", "第二球|7").put("第二球08", "第二球|8")
			.put("第二球09", "第二球|9").put("第二球10", "第二球|10").put("第二球11", "第二球|11").put("第二球12", "第二球|12")
			.put("第二球13", "第二球|13").put("第二球14", "第二球|14").put("第二球15", "第二球|15").put("第二球16", "第二球|16")
			.put("第二球17", "第二球|17").put("第二球18", "第二球|18").put("第二球19", "第二球|19").put("第二球20", "第二球|20")

			.put("第2球单双单", "第二球|单").put("第2球单双双", "第二球|双").put("第2球大小大", "第二球|大").put("第2球大小小", "第二球|小")
			.put("第2球合数单双单", "第二球|合单").put("第2球合数单双双", "第二球|合双").put("第2球尾数大小大", "第二球|尾大").put("第2球尾数大小小", "第二球|尾小")
			.put("第2球方向东", "第二球|东").put("第2球方向南", "第二球|南").put("第2球方向西", "第二球|西").put("第2球方向北", "第二球|北")
			.put("第2球中发白中", "第二球|中").put("第2球中发白发", "第二球|发").put("第2球中发白白", "第二球|白")

			.put("第三球01", "第三球|1").put("第三球02", "第三球|2").put("第三球03", "第三球|3").put("第三球04", "第三球|4")
			.put("第三球05", "第三球|5").put("第三球06", "第三球|6").put("第三球07", "第三球|7").put("第三球08", "第三球|8")
			.put("第三球09", "第三球|9").put("第三球10", "第三球|10").put("第三球11", "第三球|11").put("第三球12", "第三球|12")
			.put("第三球13", "第三球|13").put("第三球14", "第三球|14").put("第三球15", "第三球|15").put("第三球16", "第三球|16")
			.put("第三球17", "第三球|17").put("第三球18", "第三球|18").put("第三球19", "第三球|19").put("第三球20", "第三球|20")

			.put("第3球单双单", "第三球|单").put("第3球单双双", "第三球|双").put("第3球大小大", "第三球|大").put("第3球大小小", "第三球|小")
			.put("第3球合数单双单", "第三球|合单").put("第3球合数单双双", "第三球|合双").put("第3球尾数大小大", "第三球|尾大").put("第3球尾数大小小", "第三球|尾小")
			.put("第3球方向东", "第三球|东").put("第3球方向南", "第三球|南").put("第3球方向西", "第三球|西").put("第3球方向北", "第三球|北")
			.put("第3球中发白中", "第三球|中").put("第3球中发白发", "第三球|发").put("第3球中发白白", "第三球|白")

			.put("第四球01", "第四球|1").put("第四球02", "第四球|2").put("第四球03", "第四球|3").put("第四球04", "第四球|4")
			.put("第四球05", "第四球|5").put("第四球06", "第四球|6").put("第四球07", "第四球|7").put("第四球08", "第四球|8")
			.put("第四球09", "第四球|9").put("第四球10", "第四球|10").put("第四球11", "第四球|11").put("第四球12", "第四球|12")
			.put("第四球13", "第四球|13").put("第四球14", "第四球|14").put("第四球15", "第四球|15").put("第四球16", "第四球|16")
			.put("第四球17", "第四球|17").put("第四球18", "第四球|18").put("第四球19", "第四球|19").put("第四球20", "第四球|20")

			.put("第4球单双单", "第四球|单").put("第4球单双双", "第四球|双").put("第4球大小大", "第四球|大").put("第4球大小小", "第四球|小")
			.put("第4球合数单双单", "第四球|合单").put("第4球合数单双双", "第四球|合双").put("第4球尾数大小大", "第四球|尾大").put("第4球尾数大小小", "第四球|尾小")
			.put("第4球方向东", "第四球|东").put("第4球方向南", "第四球|南").put("第4球方向西", "第四球|西").put("第4球方向北", "第四球|北")
			.put("第4球中发白中", "第四球|中").put("第4球中发白发", "第四球|发").put("第4球中发白白", "第四球|白")

			.put("第五球01", "第五球|1").put("第五球02", "第五球|2").put("第五球03", "第五球|3").put("第五球04", "第五球|4")
			.put("第五球05", "第五球|5").put("第五球06", "第五球|6").put("第五球07", "第五球|7").put("第五球08", "第五球|8")
			.put("第五球09", "第五球|9").put("第五球10", "第五球|10").put("第五球11", "第五球|11").put("第五球12", "第五球|12")
			.put("第五球13", "第五球|13").put("第五球14", "第五球|14").put("第五球15", "第五球|15").put("第五球16", "第五球|16")
			.put("第五球17", "第五球|17").put("第五球18", "第五球|18").put("第五球19", "第五球|19").put("第五球20", "第五球|20")

			.put("第5球单双单", "第五球|单").put("第5球单双双", "第五球|双").put("第5球大小大", "第五球|大").put("第5球大小小", "第五球|小")
			.put("第5球合数单双单", "第五球|合单").put("第5球合数单双双", "第五球|合双").put("第5球尾数大小大", "第五球|尾大").put("第5球尾数大小小", "第五球|尾小")
			.put("第5球方向东", "第五球|东").put("第5球方向南", "第五球|南").put("第5球方向西", "第五球|西").put("第5球方向北", "第五球|北")
			.put("第5球中发白中", "第五球|中").put("第5球中发白发", "第五球|发").put("第5球中发白白", "第五球|白")

			.put("第六球01", "第六球|1").put("第六球02", "第六球|2").put("第六球03", "第六球|3").put("第六球04", "第六球|4")
			.put("第六球05", "第六球|5").put("第六球06", "第六球|6").put("第六球07", "第六球|7").put("第六球08", "第六球|8")
			.put("第六球09", "第六球|9").put("第六球10", "第六球|10").put("第六球11", "第六球|11").put("第六球12", "第六球|12")
			.put("第六球13", "第六球|13").put("第六球14", "第六球|14").put("第六球15", "第六球|15").put("第六球16", "第六球|16")
			.put("第六球17", "第六球|17").put("第六球18", "第六球|18").put("第六球19", "第六球|19").put("第六球20", "第六球|20")

			.put("第6球单双单", "第六球|单").put("第6球单双双", "第六球|双").put("第6球大小大", "第六球|大").put("第6球大小小", "第六球|小")
			.put("第6球合数单双单", "第六球|合单").put("第6球合数单双双", "第六球|合双").put("第6球尾数大小大", "第六球|尾大").put("第6球尾数大小小", "第六球|尾小")
			.put("第6球方向东", "第六球|东").put("第6球方向南", "第六球|南").put("第6球方向西", "第六球|西").put("第6球方向北", "第六球|北")
			.put("第6球中发白中", "第六球|中").put("第6球中发白发", "第六球|发").put("第6球中发白白", "第六球|白")

			.put("第七球01", "第七球|1").put("第七球02", "第七球|2").put("第七球03", "第七球|3").put("第七球04", "第七球|4")
			.put("第七球05", "第七球|5").put("第七球06", "第七球|6").put("第七球07", "第七球|7").put("第七球08", "第七球|8")
			.put("第七球09", "第七球|9").put("第七球10", "第七球|10").put("第七球11", "第七球|11").put("第七球12", "第七球|12")
			.put("第七球13", "第七球|13").put("第七球14", "第七球|14").put("第七球15", "第七球|15").put("第七球16", "第七球|16")
			.put("第七球17", "第七球|17").put("第七球18", "第七球|18").put("第七球19", "第七球|19").put("第七球20", "第七球|20")

			.put("第7球单双单", "第七球|单").put("第7球单双双", "第七球|双").put("第7球大小大", "第七球|大").put("第7球大小小", "第七球|小")
			.put("第7球合数单双单", "第七球|合单").put("第7球合数单双双", "第七球|合双").put("第7球尾数大小大", "第七球|尾大").put("第7球尾数大小小", "第七球|尾小")
			.put("第7球方向东", "第七球|东").put("第7球方向南", "第七球|南").put("第7球方向西", "第七球|西").put("第7球方向北", "第七球|北")
			.put("第7球中发白中", "第七球|中").put("第7球中发白发", "第七球|发").put("第7球中发白白", "第七球|白")

			.put("第八球01", "第八球|1").put("第八球02", "第八球|2").put("第八球03", "第八球|3").put("第八球04", "第八球|4")
			.put("第八球05", "第八球|5").put("第八球06", "第八球|6").put("第八球07", "第八球|7").put("第八球08", "第八球|8")
			.put("第八球09", "第八球|9").put("第八球10", "第八球|10").put("第八球11", "第八球|11").put("第八球12", "第八球|12")
			.put("第八球13", "第八球|13").put("第八球14", "第八球|14").put("第八球15", "第八球|15").put("第八球16", "第八球|16")
			.put("第八球17", "第八球|17").put("第八球18", "第八球|18").put("第八球19", "第八球|19").put("第八球20", "第八球|20")

			.put("第8球单双单", "第八球|单").put("第8球单双双", "第八球|双").put("第8球大小大", "第八球|大").put("第8球大小小", "第八球|小")
			.put("第8球合数单双单", "第八球|合单").put("第8球合数单双双", "第八球|合双").put("第8球尾数大小大", "第八球|尾大").put("第8球尾数大小小", "第八球|尾小")
			.put("第8球方向东", "第八球|东").put("第8球方向南", "第八球|南").put("第8球方向西", "第八球|西").put("第8球方向北", "第八球|北")
			.put("第8球中发白中", "第八球|中").put("第8球中发白发", "第八球|发").put("第8球中发白白", "第八球|白")

			.put("总和大小大", "总和|大").put("总和大小小", "总和|小").put("总和单双单", "总和|单").put("总和单双双", "总和|双").put("总和尾数大小大", "总和|尾大")
			.put("总和尾数大小小", "总和|尾小").put("龙虎虎", "总和|虎").put("龙虎龙", "总和|龙").build();

	Map<String, String> BALL_ITEM = ImmutableMap.<String, String>builder().put("401:1", "第一球|1").put("401:2", "第一球|2")
			.put("401:3", "第一球|3").put("401:4", "第一球|4").put("401:5", "第一球|5").put("401:6", "第一球|6")
			.put("401:7", "第一球|7").put("401:8", "第一球|8").put("401:9", "第一球|9").put("401:10", "第一球|10")
			.put("401:11", "第一球|11").put("401:12", "第一球|12").put("401:13", "第一球|13").put("401:14", "第一球|14")
			.put("401:15", "第一球|15").put("401:16", "第一球|16").put("401:17", "第一球|17").put("401:18", "第一球|18")
			.put("401:19", "第一球|19").put("401:20", "第一球|20").put("409:1", "第一球|单").put("409:2", "第一球|双")
			.put("417:1", "第一球|大").put("417:2", "第一球|小").put("425:1", "第一球|合单").put("425:2", "第一球|合双")
			.put("433:1", "第一球|尾大").put("433:2", "第一球|尾小").put("441:1", "第一球|东").put("441:2", "第一球|南")
			.put("441:3", "第一球|西").put("441:4", "第一球|北").put("449:1", "第一球|中").put("449:2", "第一球|发")
			.put("449:3", "第一球|白")

			.put("402:1", "第二球|1").put("402:2", "第二球|2").put("402:3", "第二球|3").put("402:4", "第二球|4")
			.put("402:5", "第二球|5").put("402:6", "第二球|6").put("402:7", "第二球|7").put("402:8", "第二球|8")
			.put("402:9", "第二球|9").put("402:10", "第二球|10").put("402:11", "第二球|11").put("402:12", "第二球|12")
			.put("402:13", "第二球|13").put("402:14", "第二球|14").put("402:15", "第二球|15").put("402:16", "第二球|16")
			.put("402:17", "第二球|17").put("402:18", "第二球|18").put("402:19", "第二球|19").put("402:20", "第二球|20")
			.put("410:1", "第二球|单").put("410:2", "第二球|双").put("418:1", "第二球|大").put("418:2", "第二球|小")
			.put("426:1", "第二球|合单").put("426:2", "第二球|合双").put("434:1", "第二球|尾大").put("434:2", "第二球|尾小")
			.put("442:1", "第二球|东").put("442:2", "第二球|南").put("442:3", "第二球|西").put("442:4", "第二球|北")
			.put("450:1", "第二球|中").put("450:2", "第二球|发").put("450:3", "第二球|白")

			.put("403:1", "第三球|1").put("403:2", "第三球|2").put("403:3", "第三球|3").put("403:4", "第三球|4")
			.put("403:5", "第三球|5").put("403:6", "第三球|6").put("403:7", "第三球|7").put("403:8", "第三球|8")
			.put("403:9", "第三球|9").put("403:10", "第三球|10").put("403:11", "第三球|11").put("403:12", "第三球|12")
			.put("403:13", "第三球|13").put("403:14", "第三球|14").put("403:15", "第三球|15").put("403:16", "第三球|16")
			.put("403:17", "第三球|17").put("403:18", "第三球|18").put("403:19", "第三球|19").put("403:20", "第三球|20")
			.put("411:1", "第三球|单").put("411:2", "第三球|双").put("419:1", "第三球|大").put("419:2", "第三球|小")
			.put("427:1", "第三球|合单").put("427:2", "第三球|合双").put("435:1", "第三球|尾大").put("435:2", "第三球|尾小")
			.put("443:1", "第三球|东").put("443:2", "第三球|南").put("443:3", "第三球|西").put("443:4", "第三球|北")
			.put("451:1", "第三球|中").put("451:2", "第三球|发").put("451:3", "第三球|白")

			.put("404:1", "第四球|1").put("404:2", "第四球|2").put("404:3", "第四球|3").put("404:4", "第四球|4")
			.put("404:5", "第四球|5").put("404:6", "第四球|6").put("404:7", "第四球|7").put("404:8", "第四球|8")
			.put("404:9", "第四球|9").put("404:10", "第四球|10").put("404:11", "第四球|11").put("404:12", "第四球|12")
			.put("404:13", "第四球|13").put("404:14", "第四球|14").put("404:15", "第四球|15").put("404:16", "第四球|16")
			.put("404:17", "第四球|17").put("404:18", "第四球|18").put("404:19", "第四球|19").put("404:20", "第四球|20")
			.put("412:1", "第四球|单").put("412:2", "第四球|双").put("420:1", "第四球|大").put("420:2", "第四球|小")
			.put("428:1", "第四球|合单").put("428:2", "第四球|合双").put("436:1", "第四球|尾大").put("436:2", "第四球|尾小")
			.put("444:1", "第四球|东").put("444:2", "第四球|南").put("444:3", "第四球|西").put("444:4", "第四球|北")
			.put("452:1", "第四球|中").put("452:2", "第四球|发").put("452:3", "第四球|白")

			.put("405:1", "第五球|1").put("405:2", "第五球|2").put("405:3", "第五球|3").put("405:4", "第五球|4")
			.put("405:5", "第五球|5").put("405:6", "第五球|6").put("405:7", "第五球|7").put("405:8", "第五球|8")
			.put("405:9", "第五球|9").put("405:10", "第五球|10").put("405:11", "第五球|11").put("405:12", "第五球|12")
			.put("405:13", "第五球|13").put("405:14", "第五球|14").put("405:15", "第五球|15").put("405:16", "第五球|16")
			.put("405:17", "第五球|17").put("405:18", "第五球|18").put("405:19", "第五球|19").put("405:20", "第五球|20")
			.put("413:1", "第五球|单").put("413:2", "第五球|双").put("421:1", "第五球|大").put("421:2", "第五球|小")
			.put("429:1", "第五球|合单").put("429:2", "第五球|合双").put("437:1", "第五球|尾大").put("437:2", "第五球|尾小")
			.put("445:1", "第五球|东").put("445:2", "第五球|南").put("445:3", "第五球|西").put("445:4", "第五球|北")
			.put("453:1", "第五球|中").put("453:2", "第五球|发").put("453:3", "第五球|白")

			.put("406:1", "第六球|1").put("406:2", "第六球|2").put("406:3", "第六球|3").put("406:4", "第六球|4")
			.put("406:5", "第六球|5").put("406:6", "第六球|6").put("406:7", "第六球|7").put("406:8", "第六球|8")
			.put("406:9", "第六球|9").put("406:10", "第六球|10").put("406:11", "第六球|11").put("406:12", "第六球|12")
			.put("406:13", "第六球|13").put("406:14", "第六球|14").put("406:15", "第六球|15").put("406:16", "第六球|16")
			.put("406:17", "第六球|17").put("406:18", "第六球|18").put("406:19", "第六球|19").put("406:20", "第六球|20")
			.put("414:1", "第六球|单").put("414:2", "第六球|双").put("422:1", "第六球|大").put("422:2", "第六球|小")
			.put("430:1", "第六球|合单").put("430:2", "第六球|合双").put("438:1", "第六球|尾大").put("438:2", "第六球|尾小")
			.put("446:1", "第六球|东").put("446:2", "第六球|南").put("446:3", "第六球|西").put("446:4", "第六球|北")
			.put("454:1", "第六球|中").put("454:2", "第六球|发").put("454:3", "第六球|白")

			.put("407:1", "第七球|1").put("407:2", "第七球|2").put("407:3", "第七球|3").put("407:4", "第七球|4")
			.put("407:5", "第七球|5").put("407:6", "第七球|6").put("407:7", "第七球|7").put("407:8", "第七球|8")
			.put("407:9", "第七球|9").put("407:10", "第七球|10").put("407:11", "第七球|11").put("407:12", "第七球|12")
			.put("407:13", "第七球|13").put("407:14", "第七球|14").put("407:15", "第七球|15").put("407:16", "第七球|16")
			.put("407:17", "第七球|17").put("407:18", "第七球|18").put("407:19", "第七球|19").put("407:20", "第七球|20")
			.put("415:1", "第七球|单").put("415:2", "第七球|双").put("423:1", "第七球|大").put("423:2", "第七球|小")
			.put("431:1", "第七球|合单").put("431:2", "第七球|合双").put("439:1", "第七球|尾大").put("439:2", "第七球|尾小")
			.put("447:1", "第七球|东").put("447:2", "第七球|南").put("447:3", "第七球|西").put("447:4", "第七球|北")
			.put("455:1", "第七球|中").put("455:2", "第七球|发").put("455:3", "第七球|白")

			.put("408:1", "第八球|1").put("408:2", "第八球|2").put("408:3", "第八球|3").put("408:4", "第八球|4")
			.put("408:5", "第八球|5").put("408:6", "第八球|6").put("408:7", "第八球|7").put("408:8", "第八球|8")
			.put("408:9", "第八球|9").put("408:10", "第八球|10").put("408:11", "第八球|11").put("408:12", "第八球|12")
			.put("408:13", "第八球|13").put("408:14", "第八球|14").put("408:15", "第八球|15").put("408:16", "第八球|16")
			.put("408:17", "第八球|17").put("408:18", "第八球|18").put("408:19", "第八球|19").put("408:20", "第八球|20")
			.put("416:1", "第八球|单").put("416:2", "第八球|双").put("424:1", "第八球|大").put("424:2", "第八球|小")
			.put("432:1", "第八球|合单").put("432:2", "第八球|合双").put("440:1", "第八球|尾大").put("440:2", "第八球|尾小")
			.put("448:1", "第八球|东").put("448:2", "第八球|南").put("448:3", "第八球|西").put("448:4", "第八球|北")
			.put("456:1", "第八球|中").put("456:2", "第八球|发").put("456:3", "第八球|白")

			.put("458:1", "总和|大").put("458:2", "总和|小").put("457:1", "总和|单").put("457:2", "总和|双").put("459:1", "总和|尾大")
			.put("459:2", "总和|尾小").put("460:1", "总和|龙").put("460:2", "总和|虎")

			.build();

	/**
	 * 暂定义为 IDC的 限额顺序与 父类一致
	 */
	Map<String, String> BET_LIMIT_CODE = ITEM_TYPE_CODE;
}