package com.ibm.common.test.wwj.handicap;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.Map;
/**
 * @Description: 顾客
 * @Author: Dongming
 * @Date: 2019-11-26 16:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class DataCustomer {

	/**
	 * 客户唯一键
	 */
	private String existId;

	/**
	 * 客户基础信息
	 */
	private String handicapUrl;
	private String handicapCaptcha;
	private String account;
	private String password;

	/**
	 * Http请求信息
	 */
	private HttpClientConfig httpConfig;

	/**
	 * 爬虫信息
	 */
	private Map<String, String> crawler;

	public DataCustomer(String existId) {
		this.existId = existId;
	}

	public String existId() {
		if (existId == null){
			return account();
		}
		return existId;
	}
	public String account() {
		if (account == null){
			return "验证登录没有存在信息";
		}
		return account;
	}
	public String handicapUrl() {
		return handicapUrl;
	}
	public String handicapCaptcha() {
		return handicapCaptcha;
	}
	public String password() {
		return password;
	}

	public HttpClientConfig httpConfig() {
		return httpConfig;
	}
	public DataCustomer httpConfig(HttpClientConfig httpConfig) {
		this.httpConfig = httpConfig;
		return this;
	}
	public Map<String, String> crawler() {
		return crawler;
	}
	public DataCustomer crawler(Map<String, String> crawler) {
		this.crawler = crawler;
		return this;
	}

	public void source(String account, String password, String handicapUrl, String handicapCaptcha) {
		this.handicapUrl = handicapUrl;
		this.handicapCaptcha = handicapCaptcha;
		this.account = account;
		this.password = password;
	}
}
