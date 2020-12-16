package com.common.handicap.crawler.entity;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用户爬虫画像
 *
 * @Author: Dongming
 * @Date: 2020-05-09 16:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class UserCrawlerImage {

	private Lock lock = new ReentrantLock();

	/**
	 * 客户唯一键
	 */
	private String existId;

	/**
	 * Http请求信息
	 */
	private HttpClientConfig httpConfig;

	/**
	 * 主机地址
	 */
	private String projectHost;

	/**
	 * 爬虫信息
	 */
	private Map<String, String> crawlerInfo;

	/**
	 * 是否登录
	 */
	private boolean needLogin = false;

	/**
	 * 账号信息
	 */
	private HandicapUser accountInfo;

	public UserCrawlerImage(String existId) {
		this.existId = existId;
	}

	/**
	 * 登录的爬虫信息
	 *
	 * @param data 登录成功数据
	 */
	public void loginCrawlerInfo(Map<String, String> data) {
		projectHost = data.remove("projectHost");
		// TODO: 2020/5/9 可分离出来其他的爬虫信息
		crawlerInfo = new HashMap<>(data);
		needLogin = false;
	}
	/**
	 * 重新指定盘口账户信息，爬虫画像重绘
	 */
	public void handicapInfo(String handicapUrl, String handicapCaptcha, String account, String password) {
		if (accountInfo == null) {
			accountInfo = new HandicapUser(handicapUrl, handicapCaptcha, account, password);
		} else {
			accountInfo.handicapInfo(handicapUrl, handicapCaptcha, account, password);
		}
		crawlerInfo = null;
		projectHost = null;
		if (httpConfig != null) {
			httpConfig.headers(null);
			httpConfig.httpContext(null);
		}
	}

	public String existId() {
		return existId;
	}

	public HttpClientConfig httpConfig() {
		if (httpConfig == null) {
			lock.lock();
			try {
				if (httpConfig == null) {
					HttpClientConfig httpConfig = new HttpClientConfig();
					httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
					httpConfig(httpConfig);
				}
			} finally {
				lock.unlock();
			}
		}
		return httpConfig;
	}

	public void httpConfig(HttpClientConfig httpConfig) {
		this.httpConfig = httpConfig;
	}

	public HandicapUser accountInfo(){
		return accountInfo;
	}
	public void accountInfo(HandicapUser accountInfo) {
		this.accountInfo = accountInfo;
	}
	public String projectHost() {
		return projectHost;
	}
	public Map<String, String> crawlerInfo() {
		return crawlerInfo;
	}
	public void crawlerInfo(String key, String value) {
		crawlerInfo.put(key,value);
	}
	public boolean needLogin() {
		return needLogin;
	}

	public void needLogin(boolean needLogin) {
		this.needLogin = needLogin;
	}

}
