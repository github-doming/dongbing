package com.ibm.common.utils.handicap.config;

import com.google.common.collect.ImmutableMap;
import com.ibm.common.core.configs.BWConfig;

import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/30 11:27
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public interface NewWsHappyConfig extends BWConfig {


    /**
     * 投注信息
     */
    Map<String, String> BET_INFO_CODE = ImmutableMap.<String, String>builder().put("第1球-1", "第一球|1")
            .put("第1球-2", "第一球|2").put("第1球-3", "第一球|3").put("第1球-4", "第一球|4").put("第1球-5", "第一球|5")
            .put("第1球-6", "第一球|6").put("第1球-7", "第一球|7").put("第1球-8", "第一球|8").put("第1球-9", "第一球|9")
            .put("第1球-10", "第一球|10").put("第1球-11", "第一球|11").put("第1球-12", "第一球|12").put("第1球-13", "第一球|13")
            .put("第1球-14", "第一球|14").put("第1球-15", "第一球|15").put("第1球-16", "第一球|16").put("第1球-17", "第一球|17")
            .put("第1球-18", "第一球|18").put("第1球-19", "第一球|19").put("第1球-20", "第一球|20")

			.put("第1球-龍", "第一球|龙").put("第1球-虎", "第一球|虎")
            .put("第1球-單", "第一球|单").put("第1球-雙", "第一球|双").put("第1球-大", "第一球|大").put("第1球-小", "第一球|小")

            .put("第1球-合數單", "第一球|合单").put("第1球-合數雙", "第一球|合双").put("第1球-尾大", "第一球|尾大").put("第1球-尾小", "第一球|尾小")
            .put("第1球-東", "第一球|东").put("第1球-南", "第一球|南").put("第1球-西", "第一球|西").put("第1球-北", "第一球|北")
            .put("第1球-中", "第一球|中").put("第1球-發", "第一球|发").put("第1球-白", "第一球|白")

            .put("第2球-1", "第二球|1").put("第2球-2", "第二球|2").put("第2球-3", "第二球|3")
            .put("第2球-4", "第二球|4").put("第2球-5", "第二球|5").put("第2球-6", "第二球|6").put("第2球-7", "第二球|7")
            .put("第2球-8", "第二球|8").put("第2球-9", "第二球|9")
            .put("第2球-10", "第二球|10").put("第2球-11", "第二球|11").put("第2球-12", "第二球|12").put("第2球-13", "第二球|13")
			.put("第2球-14", "第二球|14").put("第2球-15", "第二球|15").put("第2球-16", "第二球|16").put("第2球-17", "第二球|17")
			.put("第2球-18", "第二球|18").put("第2球-19", "第二球|19").put("第2球-20", "第二球|20")

			.put("第2球-龍", "第二球|龙").put("第2球-虎", "第二球|虎")
            .put("第2球-單", "第二球|单").put("第2球-雙", "第二球|双").put("第2球-大", "第二球|大").put("第2球-小", "第二球|小")

            .put("第2球-合數單", "第二球|合单").put("第2球-合數雙", "第二球|合双").put("第2球-尾大", "第二球|尾大").put("第2球-尾小", "第二球|尾小")
            .put("第2球-東", "第二球|东").put("第2球-南", "第二球|南").put("第2球-西", "第二球|西").put("第2球-北", "第二球|北")
            .put("第2球-中", "第二球|中").put("第2球-發", "第二球|发").put("第2球-白", "第二球|白")


            .put("第3球-1", "第三球|1").put("第3球-2", "第三球|2").put("第3球-3", "第三球|3")
            .put("第3球-4", "第三球|4").put("第3球-5", "第三球|5").put("第3球-6", "第三球|6").put("第3球-7", "第三球|7")
            .put("第3球-8", "第三球|8").put("第3球-9", "第三球|9")
            .put("第3球-10", "第三球|10").put("第3球-11", "第三球|11").put("第3球-12", "第三球|12").put("第3球-13", "第三球|13")
            .put("第3球-14", "第三球|14").put("第3球-15", "第三球|15").put("第3球-16", "第三球|16").put("第3球-17", "第三球|17")
            .put("第3球-18", "第三球|18").put("第3球-19", "第三球|19").put("第3球-20", "第三球|20")

			.put("第3球-龍", "第三球|龙").put("第3球-虎", "第三球|虎")
            .put("第3球-單", "第三球|单").put("第3球-雙", "第三球|双").put("第3球-大", "第三球|大").put("第3三球-小", "第三球|小")
            .put("第3球-合數單", "第三球|合单").put("第3球-合數雙", "第三球|合双").put("第3球-尾大", "第三球|尾大").put("第3球-尾小", "第三球|尾小")
            .put("第3球-東", "第三球|东").put("第3球-南", "第三球|南").put("第3球-西", "第三球|西").put("第3球-北", "第三球|北")
            .put("第3球-中", "第三球|中").put("第3球-發", "第三球|发").put("第3球-白", "第三球|白")

            .put("第4球-1", "第四球|1").put("第4球-2", "第四球|2").put("第4球-3", "第四球|3")
            .put("第4球-4", "第四球|4").put("第4球-5", "第四球|5").put("第4球-6", "第四球|6").put("第4球-7", "第四球|7")
            .put("第4球-8", "第四球|8").put("第4球-9", "第四球|9")
            .put("第4球-10", "第四球|10").put("第4球-11", "第4球|11").put("第4球-12", "第四球|12").put("第4球-13", "第四球|13")
            .put("第4球-14", "第四球|14").put("第4球-15", "第4球|15").put("第4球-16", "第四球|16").put("第4球-17", "第四球|17")
            .put("第4球-18", "第四球|18").put("第4球-19", "第4球|19").put("第4球-20", "第四球|20")

			.put("第4球-龍", "第四球|龙").put("第4球-虎", "第四球|虎")
            .put("第4球-單", "第四球|单").put("第4四球-雙", "第四球|双").put("第4球-大", "第四球|大").put("第4球-小", "第四球|小")
            .put("第4球-合數單", "第四球|合单").put("第4球-合數雙", "第四球|合双").put("第4球-尾大", "第四球|尾大").put("第4球-尾小", "第四球|尾小")
            .put("第4球-東", "第四球|东").put("第4球-南", "第四球|南").put("第4球-西", "第四球|西").put("第4球-北", "第四球|北")
            .put("第4球-中", "第四球|中").put("第4球-發", "第四球|发").put("第4球-白", "第四球|白")

            .put("第5球-0", "第五球|0").put("第5球-1", "第五球|1").put("第5球-2", "第五球|2").put("第5球-3", "第五球|3")
            .put("第5球-4", "第五球|4").put("第5球-5", "第五球|5").put("第5球-6", "第五球|6").put("第5球-7", "第五球|7")
            .put("第5球-8", "第五球|8").put("第5球-9", "第五球|9")
            .put("第五球-10", "第五球|10").put("第五球-11", "第五球|11").put("第五球-12", "第五球|12")
            .put("第五球-13", "第五球|13").put("第五球-14", "第五球|14").put("第五球-15", "第五球|15").put("第五球-16", "第五球|16")
            .put("第五球-17", "第五球|17").put("第五球-18", "第五球|18").put("第五球-19", "第五球|19").put("第五球-20", "第五球|20")

            .put("第5球-單", "第五球|单").put("第5球-雙", "第五球|双").put("第5球-大", "第五球|大").put("第5球-小", "第五球|小")
            .put("第5球-合數單", "第五球|合单").put("第5球-合數雙", "第五球|合双").put("第5球-尾大", "第五球|尾大").put("第5球-尾小", "第五球|尾小")
            .put("第5球-東", "第五球|东").put("第5球-南", "第五球|南").put("第5球-西", "第五球|西").put("第5球-北", "第五球|北")
            .put("第5球-中", "第五球|中").put("第5球-發", "第五球|发").put("第5球-白", "第五球|白")

            .put("第6球-01", "第六球|1").put("第6球-02", "第六球|2").put("第6球-03", "第六球|3").put("第6球-04", "第六球|4")
            .put("第6球-05", "第六球|5").put("第6球-06", "第六球|6").put("第6球-07", "第六球|7").put("第6球-08", "第六球|8")
            .put("第6球-09", "第六球|9").put("第6球-10", "第六球|10").put("第6球-11", "第六球|11").put("第6球-12", "第六球|12")
            .put("第6球-13", "第六球|13").put("第6球-14", "第六球|14").put("第6球-15", "第六球|15").put("第6球-16", "第六球|16")
            .put("第6球-17", "第六球|17").put("第6球-18", "第六球|18").put("第6球-19", "第六球|19").put("第6球-20", "第六球|20")

            .put("第6球-單", "第六球|单").put("第6球-雙", "第六球|双").put("第6球-大", "第六球|大").put("第6球-小", "第六球|小")
            .put("第6球-合數單", "第六球|合单").put("第6球-合數雙", "第六球|合双").put("第6球-尾大", "第六球|尾大").put("第6球-尾小", "第六球|尾小")
            .put("第6球-東", "第六球|东").put("第6球-南", "第六球|南").put("第6球-西", "第六球|西").put("第6球-北", "第六球|北")
            .put("第6球-中", "第六球|中").put("第6球-發", "第六球|发").put("第6球-白", "第六球|白")

            .put("第七球-01", "第七球|1").put("第七球-02", "第七球|2").put("第七球-03", "第七球|3").put("第七球-04", "第七球|4")
            .put("第七球-05", "第七球|5").put("第七球-06", "第七球|6").put("第七球-07", "第七球|7").put("第七球-08", "第七球|8")
            .put("第七球-09", "第七球|9").put("第七球-10", "第七球|10").put("第七球-11", "第七球|11").put("第七球-12", "第七球|12")
            .put("第七球-13", "第七球|13").put("第七球-14", "第七球|14").put("第七球-15", "第七球|15").put("第七球-16", "第七球|16")
            .put("第七球-17", "第七球|17").put("第七球-18", "第七球|18").put("第七球-19", "第七球|19").put("第七球-20", "第七球|20")

            .put("第7球-單", "第七球|单").put("第7球-雙", "第七球|双").put("第7球-大", "第七球|大").put("第7球-小", "第七球|小")
            .put("第7球-合數單", "第七球|合单").put("第7球-合數雙", "第七球|合双").put("第7球-尾大", "第七球|尾大").put("第7球-尾小", "第七球|尾小")
            .put("第7球-東", "第七球|东").put("第7球-南", "第七球|南").put("第7球-西", "第七球|西").put("第7球-北", "第七球|北")
            .put("第7球-中", "第七球|中").put("第7球-發", "第七球|发").put("第7球-白", "第七球|白")

            .put("第8球-01", "第8球|1").put("第8球-02", "第八球|2").put("第8球-03", "第八球|3").put("第8球-04", "第八球|4")
            .put("第8球-05", "第8球|5").put("第8球-06", "第八球|6").put("第8球-07", "第八球|7").put("第8球-08", "第八球|8")
            .put("第8球-09", "第8球|9").put("第8球-10", "第八球|10").put("第8球-11", "第八球|11").put("第8球-12", "第八球|12")
            .put("第8球-13", "第8球|13").put("第8球-14", "第八球|14").put("第8球-15", "第八球|15").put("第8球-16", "第八球|16")
            .put("第8球-17", "第8球|17").put("第8球-18", "第八球|18").put("第8球1-9", "第八球|19").put("第8球-20", "第八球|20")

            .put("第8球-單", "第八球|单").put("第8球-雙", "第八球|双").put("第8球-大", "第八球|大").put("第8球-小", "第八球|小")
            .put("第8球-合數單", "第八球|合单").put("第8球-合數雙", "第八球|合双").put("第8球-尾大", "第八球|尾大").put("第8球-尾小", "第八球|尾小")
            .put("第8球-東", "第八球|东").put("第8球-南", "第八球|南").put("第8球-西", "第八球|西").put("第8球-北", "第八球|北")
            .put("第8球-中", "第八球|中").put("第8球-發", "第八球|发").put("第8球-白", "第八球|白")

            .put("總和-大", "总和|大").put("總和-小", "总和|小").put("總和-單", "总和|单").put("總和-雙", "总和|双").put("總和-尾大", "总和|尾大")
            .put("總和-尾小", "总和|尾小").build();



	Map<String, String> BET_LIMIT_CODE = ImmutableMap.<String, String>builder()
			.put("第一球|1", "0").put("第一球|2", "0").put("第一球|3", "0").put("第一球|4", "0").put("第一球|5", "0").put("第一球|6", "0")
			.put("第一球|7", "0").put("第一球|8", "0").put("第一球|9", "0").put("第一球|10", "0").put("第一球|11", "0")
			.put("第一球|12", "0").put("第一球|13", "0").put("第一球|14", "0").put("第一球|15", "0").put("第一球|16", "0")
			.put("第一球|17", "0").put("第一球|18", "0").put("第一球|19", "0").put("第一球|20", "0")
			.put("第一球|龙", "6").put("第一球|虎", "6")
			.put("第一球|单", "2").put("第一球|双", "2").put("第一球|大", "2").put("第一球|小", "2").put("第一球|合单", "2")
			.put("第一球|合双", "2").put("第一球|尾大", "2").put("第一球|尾小", "2").put("第一球|东", "5").put("第一球|南", "5")
			.put("第一球|西", "5").put("第一球|北", "5").put("第一球|中", "4").put("第一球|发", "4").put("第一球|白", "4")

			.put("第二球|1", "0").put("第二球|2", "0").put("第二球|3", "0").put("第二球|4", "0").put("第二球|5", "0").put("第二球|6", "0")
			.put("第二球|7", "0").put("第二球|8", "0").put("第二球|9", "0").put("第二球|10", "0").put("第二球|11", "0")
			.put("第二球|12", "0").put("第二球|13", "0").put("第二球|14", "0").put("第二球|15", "0").put("第二球|16", "0")
			.put("第二球|17", "0").put("第二球|18", "0").put("第二球|19", "0").put("第二球|20", "0")
			.put("第二球|龙", "6").put("第二球|虎", "6")
			.put("第二球|单", "2").put("第二球|双", "2").put("第二球|大", "2").put("第二球|小", "2").put("第二球|合单", "2")
			.put("第二球|合双", "2").put("第二球|尾大", "2").put("第二球|尾小", "2").put("第二球|东", "5").put("第二球|南", "5")
			.put("第二球|西", "5").put("第二球|北", "5").put("第二球|中", "4").put("第二球|发", "4").put("第二球|白", "4")

			.put("第三球|1", "0").put("第三球|2", "0").put("第三球|3", "0").put("第三球|4", "0").put("第三球|5", "0").put("第三球|6", "0")
			.put("第三球|7", "0").put("第三球|8", "0").put("第三球|9", "0").put("第三球|10", "0").put("第三球|11", "0")
			.put("第三球|12", "0").put("第三球|13", "0").put("第三球|14", "0").put("第三球|15", "0").put("第三球|16", "0")
			.put("第三球|17", "0").put("第三球|18", "0").put("第三球|19", "0").put("第三球|20", "0")
			.put("第三球|龙", "6").put("第三球|虎", "6")
			.put("第三球|单", "2").put("第三球|双", "2").put("第三球|大", "2").put("第三球|小", "2").put("第三球|合单", "2")
			.put("第三球|合双", "2").put("第三球|尾大", "2").put("第三球|尾小", "2").put("第三球|东", "5").put("第三球|南", "5")
			.put("第三球|西", "5").put("第三球|北", "5").put("第三球|中", "4").put("第三球|发", "4").put("第三球|白", "4")

			.put("第四球|1", "0").put("第四球|2", "0").put("第四球|3", "0").put("第四球|4", "0").put("第四球|5", "0").put("第四球|6", "0")
			.put("第四球|7", "0").put("第四球|8", "0").put("第四球|9", "0").put("第四球|10", "0").put("第四球|11", "0")
			.put("第四球|12", "0").put("第四球|13", "0").put("第四球|14", "0").put("第四球|15", "0").put("第四球|16", "0")
			.put("第四球|17", "0").put("第四球|18", "0").put("第四球|19", "0").put("第四球|20", "0")
			.put("第四球|龙", "6").put("第四球|虎", "6")
			.put("第四球|单", "2").put("第四球|双", "2").put("第四球|大", "2").put("第四球|小", "2").put("第四球|合单", "2")
			.put("第四球|合双", "2").put("第四球|尾大", "2").put("第四球|尾小", "2").put("第四球|东", "5").put("第四球|南", "4")
			.put("第四球|西", "5").put("第四球|北", "5").put("第四球|中", "4").put("第四球|发", "4").put("第四球|白", "4")

			.put("第五球|1", "0").put("第五球|2", "0").put("第五球|3", "0").put("第五球|4", "0").put("第五球|5", "0").put("第五球|6", "0")
			.put("第五球|7", "0").put("第五球|8", "0").put("第五球|9", "0").put("第五球|10", "0").put("第五球|11", "0")
			.put("第五球|12", "0").put("第五球|13", "0").put("第五球|14", "0").put("第五球|15", "0").put("第五球|16", "0")
			.put("第五球|17", "0").put("第五球|18", "0").put("第五球|19", "0").put("第五球|20", "0")

			.put("第五球|单", "2").put("第五球|双", "2").put("第五球|大", "2").put("第五球|小", "2").put("第五球|合单", "2")
			.put("第五球|合双", "2").put("第五球|尾大", "2").put("第五球|尾小", "2").put("第五球|东", "5").put("第五球|南", "5")
			.put("第五球|西", "5").put("第五球|北", "5").put("第五球|中", "4").put("第五球|发", "4").put("第五球|白", "4")

			.put("第六球|1", "0").put("第六球|2", "0").put("第六球|3", "0").put("第六球|4", "0").put("第六球|5", "0").put("第六球|6", "0")
			.put("第六球|7", "0").put("第六球|8", "0").put("第六球|9", "0").put("第六球|10", "0").put("第六球|11", "0")
			.put("第六球|12", "0").put("第六球|13", "0").put("第六球|14", "0").put("第六球|15", "0").put("第六球|16", "0")
			.put("第六球|17", "0").put("第六球|18", "0").put("第六球|19", "0").put("第六球|20", "0")
			.put("第六球|单", "2").put("第六球|双", "2").put("第六球|大", "2").put("第六球|小", "2").put("第六球|合单", "2")
			.put("第六球|合双", "2").put("第六球|尾大", "2").put("第六球|尾小", "2").put("第六球|东", "5").put("第六球|南", "5")
			.put("第六球|西", "5").put("第六球|北", "5").put("第六球|中", "4").put("第六球|发", "4").put("第六球|白", "4")

			.put("第七球|1", "0").put("第七球|2", "0").put("第七球|3", "0").put("第七球|4", "0").put("第七球|5", "0").put("第七球|6", "0")
			.put("第七球|7", "0").put("第七球|8", "0").put("第七球|9", "0").put("第七球|10", "0").put("第七球|11", "0")
			.put("第七球|12", "0").put("第七球|13", "0").put("第七球|14", "0").put("第七球|15", "0").put("第七球|16", "0")
			.put("第七球|17", "0").put("第七球|18", "0").put("第七球|19", "0").put("第七球|20", "0")
			.put("第七球|单", "2").put("第七球|双", "2").put("第七球|大", "2").put("第七球|小", "2").put("第七球|合单", "2")
			.put("第七球|合双", "2").put("第七球|尾大", "2").put("第七球|尾小", "2").put("第七球|东", "5").put("第七球|南", "5")
			.put("第七球|西", "5").put("第七球|北", "5").put("第七球|中", "4").put("第七球|发", "4").put("第七球|白", "4")

			.put("第八球|1", "0").put("第八球|2", "0").put("第八球|3", "0").put("第八球|4", "0").put("第八球|5", "0").put("第八球|6", "0")
			.put("第八球|7", "0").put("第八球|8", "0").put("第八球|9", "0").put("第八球|10", "0").put("第八球|11", "0")
			.put("第八球|12", "0").put("第八球|13", "0").put("第八球|14", "0").put("第八球|15", "0").put("第八球|16", "0")
			.put("第八球|17", "0").put("第八球|18", "0").put("第八球|19", "0").put("第八球|20", "0")
			.put("第八球|单", "2").put("第八球|双", "2").put("第八球|大", "2").put("第八球|小", "2").put("第八球|合单", "2")
			.put("第八球|合双", "2").put("第八球|尾大", "2").put("第八球|尾小", "2").put("第八球|东", "5").put("第八球|南", "5")
			.put("第八球|西", "5").put("第八球|北", "5").put("第八球|中", "4").put("第八球|发", "4").put("第八球|白", "4")

			.put("总和|大", "3").put("总和|小", "3").put("总和|单", "3").put("总和|双", "3").put("总和|尾大", "3").put("总和|尾小", "3")
			.build();
}
