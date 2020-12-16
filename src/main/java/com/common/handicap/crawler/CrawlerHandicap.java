package com.common.handicap.crawler;


import com.common.core.JsonResultBeanPlus;
import com.common.handicap.HandicapOption;

/**
 * 盘口爬虫
 *
 * @Author: Dongming
 * @Date: 2020-05-09 16:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface CrawlerHandicap extends HandicapOption {

	/**
	 * 登陆
	 *
	 * @param existId     存在主键
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @param account         盘口账户
	 * @param password        盘口密码
	 * @return 登录结果
	 */
	JsonResultBeanPlus login(String existId, String handicapUrl, String handicapCaptcha, String account, String password);

	/**
	 * 放入基本信息
	 *
	 * @param existId         存在主键
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @param account         盘口账户
	 * @param password        盘口密码
	 */
	void userInfo(String existId, String handicapUrl, String handicapCaptcha, String account,
				  String password);

}
