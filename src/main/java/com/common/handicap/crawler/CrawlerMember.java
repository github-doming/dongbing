package com.common.handicap.crawler;

import com.common.handicap.MemberOption;

/**
 * 会员盘口爬虫
 *
 * @Author: Dongming
 * @Date: 2020-05-09 16:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface CrawlerMember extends MemberOption {

	long MAX_CHECK_TIME = 120 * 1000L;

	long MIN_CHECK_TIME = 10 * 1000L;


}
