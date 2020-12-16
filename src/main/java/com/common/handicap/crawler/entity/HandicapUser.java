package com.common.handicap.crawler.entity;
/**
 * 盘口用户
 *
 * @Author: Dongming
 * @Date: 2020-05-09 16:43
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HandicapUser {
	/**
	 * 客户基础信息
	 */
	private String handicapUrl;
	private String handicapCaptcha;
	private String account;
	private String password;
	public HandicapUser(String handicapUrl, String handicapCaptcha, String account, String password) {
		this.handicapUrl = handicapUrl;
		this.handicapCaptcha = handicapCaptcha;
		this.account = account;
		this.password = password;
	}
	protected void handicapInfo(String handicapUrl, String handicapCaptcha, String account, String password) {
		this.handicapUrl = handicapUrl;
		this.handicapCaptcha = handicapCaptcha;
		this.account = account;
		this.password = password;
	}

	public String handicapUrl() {
		return handicapUrl;
	}
	public String handicapCaptcha() {
		return handicapCaptcha;
	}
	public String account() {
		return account;
	}
	public String password() {
		return password;
	}
}
