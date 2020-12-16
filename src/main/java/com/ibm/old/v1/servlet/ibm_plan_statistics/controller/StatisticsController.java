package com.ibm.old.v1.servlet.ibm_plan_statistics.controller;
import com.alibaba.fastjson.JSONArray;
/**
 * @Description: 统计控制器
 * @Author: Dongming
 * @Date: 2019-06-12 09:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface StatisticsController {
	/**
	 * 开始统计
	 *
	 * @return 统计结果
	 * @throws Exception 异常
	 */
	JSONArray statistics() throws Exception;
}
