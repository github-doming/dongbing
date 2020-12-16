package com.common.handicap;

import com.common.core.JsonResultBeanPlus;

/**
 * 盘口工厂类
 *
 * @Author: Dongming
 * @Date: 2020-05-11 18:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface HandicapFactory<T> {

	/**
	 * 盘口操作
	 *
	 * @return 盘口操作对象
	 */
	Handicap handicap();

	/**
	 * 登陆
	 *
	 * @param existId         存在主键
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @param account         盘口账户
	 * @param password        盘口密码
	 * @return 登录结果
	 */
	JsonResultBeanPlus login(String existId, String handicapUrl, String handicapCaptcha, String account,
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


	/**
	 * 移除操作人员的爬虫对象
	 *
	 * @param existId 存在主键
	 */
	void removeCrawler(String existId);

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
