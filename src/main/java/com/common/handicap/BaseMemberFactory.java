package com.common.handicap;

import com.common.handicap.crawler.CrawlerHandicap;
import com.common.handicap.crawler.CrawlerMember;
import org.doming.core.tools.AssertTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Dongming
 * @Date: 2020-05-11 16:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseMemberFactory<T extends CrawlerMember & CrawlerHandicap> extends BaseHandicapFactory<T> implements
		MemberFactory {
	protected BaseMemberFactory(){
	}
	private Map<String, T> crawlers = new HashMap<>();

	@Override protected T handicapCrawler(String existId) {
		return crawlers.get(existId);
	}

	@Override protected void handicapCrawler(String existId, T crawler) {
		crawlers.put(existId, crawler);
	}

	@Override public void removeCrawler(String existId) {
		crawlers.remove(existId);
	}
	/**
	 * 会员操作
	 *
	 * @return 会员操作对象
	 */
	@Override public T memberOption(String existId) {
		T crawlerHandicap = handicapCrawler(existId);
		AssertTool.notNull(crawlerHandicap, "该会员的爬虫信息为空，无法获取会员操作对象");
		return crawlerHandicap;
	}

}
