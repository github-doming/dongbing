package com.common.config;
/**
 * 期数配置类
 * @Author: Dongming
 * @Date: 2020-05-13 14:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface PeriodConfig {
	String PERIOD_FORMAT_D_3 = "%s-%03d";
	String PERIOD_FORMAT_D_2 = "%s-%02d";
	String PERIOD_FORMAT_D3 = "%s%03d";

	long PERIOD_TIME_20 = 20 * 60 * 1000L;
	long PERIOD_TIME_5 = 5 * 60 * 1000L;
	long PERIOD_TIME_2 = 2 * 60 * 1000L;
	long PERIOD_TIME_1 = 75 * 1000L;
}
