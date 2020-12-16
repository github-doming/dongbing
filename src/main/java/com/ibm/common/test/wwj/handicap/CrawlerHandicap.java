package com.ibm.common.test.wwj.handicap;

import com.common.core.JsonResultBeanPlus;

/**
 * @Description: 盘口爬虫
 * @Author: Dongming
 * @Date: 2019-11-22 10:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface CrawlerHandicap {

	/**
	 * 放入存在主键
	 * @param existId 存在主键
	 * @return 放入结果
	 * 成功则说明该爬虫没有指定消费者
	 */
	boolean putExistId(String existId);


	/**
	 * 登陆
	 *
	 * @return 登录结果
	 * @throws HandicapException 盘口错误信息
	 */
	JsonResultBeanPlus login() throws HandicapException;

	/**
	 * 登陆
	 *
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @param account         盘口账户
	 * @param password        盘口密码
	 * @return 登录结果
	 */
	JsonResultBeanPlus login(String handicapUrl, String handicapCaptcha, String account,
                             String password) throws HandicapException;


	/**
	 * 登陆
	 *
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @param account         盘口账户
	 * @param password        盘口密码
	 * @return 登录结果
	 */
	JsonResultBeanPlus verifyLogin(String handicapUrl, String handicapCaptcha, String account, String password);

}
