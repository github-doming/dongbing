package com.common.handicap;

import com.common.core.JsonResultBeanPlus;

/**
 * 盘口操作
 *
 * @Author: Dongming
 * @Date: 2020-05-11 16:29
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface HandicapOption {

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
							 String password);


	/**
	 * 验证登录
	 *
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @param account         盘口账户
	 * @param password        盘口密码
	 * @return 登录结果
	 */
	JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String account, String password);
}
