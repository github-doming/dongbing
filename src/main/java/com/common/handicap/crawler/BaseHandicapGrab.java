package com.common.handicap.crawler;

import com.common.handicap.crawler.entity.UserCrawlerImage;
import org.doming.develop.http.httpclient.HttpClientConfig;

/**
 * 盘口抓取操作抽象类
 * @Author: Dongming
 * @Date: 2020-05-18 11:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseHandicapGrab implements GrabHandicap {
	private UserCrawlerImage crawlerImage;
	@Override public void userImage(UserCrawlerImage userImage) {
		crawlerImage = userImage;
	}
	public String projectHost() {
		return crawlerImage.projectHost();
	}
	public HttpClientConfig httpConfig() {
		return crawlerImage.httpConfig();
	}
	protected UserCrawlerImage crawlerImage(){
		return crawlerImage;
	}

}
