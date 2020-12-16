package com.ibm.common.utils.http.utils.agent;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import org.doming.develop.http.httpclient.HttpClientConfig;
/**
 * 代理爬虫抽象类
 *
 * @Author: Dongming
 * @Date: 2020-04-24 16:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface IAgentCrawler {

	Integer MAX_RECURSIVE_SIZE = 5;
	long MAX_CHECK_TIME = 120 * 1000;
	long MIN_CHECK_TIME = 10 * 1000;

	/**
	 * 放入账户信息
	 *
	 * @param existHaId       盘口代理存在id
	 * @param agentAccount    代理账号
	 * @param agentPassword   代理密码
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 */
	 void accountInfo(String existHaId, String agentAccount, String agentPassword, String handicapUrl,
			String handicapCaptcha);
	/**
	 * 登陆
	 *
	 * @param existHaId       盘口代理存在id
	 * @param agentAccount    代理账号
	 * @param agentPassword   代理密码
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @return 登录结果
	 */
	JsonResultBeanPlus login(String existHaId, String agentAccount, String agentPassword, String handicapUrl,
							 String handicapCaptcha);

	/**
	 * 登陆
	 * @param existHaId 盘口代理存在id
	 * @return 登录结果
	 */
	JsonResultBeanPlus login(String existHaId);

	/**
	 * 登陆
	 *
	 * @param httpConfig       http请求配置类
	 * @param accountInfo      账号信息
	 * @return 登录结果
	 */
	JsonResultBeanPlus login(HttpClientConfig httpConfig, AccountInfo accountInfo);

	/**
	 * 验证登录
	 * @param agentAccount    代理账号
	 * @param agentPassword   代理密码
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @return 验证登录结果
	 */
	JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String agentAccount, String agentPassword);

	/**
	 * 获取会员账号信息
	 *
	 * @param existHaId 已存在盘口代理id
	 * @return 会员账号信息
	 */
	JsonResultBeanPlus memberListInfo(String existHaId);
    /**
     * 定时抓取会员列表信息
     * @param existHaId     已存在盘口代理id
     * @return 会员列表信息
     */
    JsonResultBeanPlus checkInfo(String existHaId);

    /**
     * 获取会员列表信息
     * @param existHaId     已存在盘口代理id
     * @return  会员列表信息
     */
    JSONArray getMemberList(String existHaId);
}
