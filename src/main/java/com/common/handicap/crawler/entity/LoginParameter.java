package com.common.handicap.crawler.entity;
import org.doming.develop.http.httpclient.HttpClientConfig;

/**
 * 登录参数
 *
 * @Author: Dongming
 * @Date: 2020-05-11 10:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LoginParameter {

	/**
	 * Http请求信息
	 */
	private HttpClientConfig httpConfig;

	/**
	 * 账号信息
	 */
	private HandicapUser accountInfo;

	public LoginParameter(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, String account, String password) {
		this.httpConfig = httpConfig;
		accountInfo = new HandicapUser(handicapUrl,handicapCaptcha,account,password);
	}

	public LoginParameter(HttpClientConfig httpConfig, HandicapUser accountInfo) {
		this.httpConfig = httpConfig;
		this.accountInfo = accountInfo;
	}

	public HttpClientConfig httpConfig() {
		return httpConfig;
	}
	public HandicapUser accountInfo() {
		return accountInfo;
	}
}
