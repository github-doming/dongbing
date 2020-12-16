package com.common.handicap.crawler.entity;

import com.common.util.BaseGameUtil;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员爬虫画像
 *
 * @Author: Dongming
 * @Date: 2020-05-11 11:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MemberCrawlerImage {
	private MemberUser member;
	/**
	 * 赔率信息
	 */
	private Map<BaseGameUtil.Code, Map<String, Object>> oddsInfo;

	/**
	 * 其他信息
	 */
	private Map<BaseGameUtil.Code, Map<String, Object>> otherInfo;
	private UserCrawlerImage userImage;
	public MemberCrawlerImage() {
		oddsInfo = new HashMap<>();
	}

	public MemberUser member() {
		return member;
	}
	public MemberCrawlerImage member(MemberUser member) {
		this.member = member;
		return this;
	}
	public UserCrawlerImage userImage() {
		return userImage;
	}
	public MemberCrawlerImage userImage(UserCrawlerImage userImage) {
		this.userImage = userImage;
		return this;
	}
	public Map<BaseGameUtil.Code, Map<String, Object>> oddsInfo() {
		return oddsInfo;
	}
	public Map<BaseGameUtil.Code, Map<String, Object>> otherInfo() {
		return otherInfo;
	}
	public void otherInfo(Map<BaseGameUtil.Code, Map<String, Object>> otherInfo) {
		this.otherInfo = otherInfo;
	}

	public void checkTime(long checkTime) {
		member.checkTime(checkTime);
	}
	public long checkTime() {
		return member.checkTime();
	}
	public double usedQuota() {
		return member.usedQuota();
	}
	public boolean needLogin() {
		return userImage.needLogin();
	}

	public HttpClientConfig httpConfig() {
		return userImage.httpConfig();
	}
}
