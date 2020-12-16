package com.common.handicap;


import com.common.core.JsonResultBeanPlus;
import com.common.handicap.crawler.CrawlerHandicap;

/**
 * 盘口操作抽象类
 *
 * @Author: Dongming
 * @Date: 2020-05-11 16:47
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseHandicapFactory<T extends CrawlerHandicap> implements HandicapFactory<T> {
	protected BaseHandicapFactory(){
	}

	/**
	 * 获取盘口爬虫
	 *
	 * @return 盘口爬虫
	 */
	protected abstract T newCrawler();

	/**
	 * 获取盘口爬虫
	 *
	 * @param existId 存在主键
	 * @return 盘口爬虫
	 */
	protected abstract T handicapCrawler(String existId);

	/**
	 * 放入爬虫信息
	 *
	 * @param existId         存在主键
	 * @param crawlerHandicap 爬虫信息
	 */
	protected abstract void handicapCrawler(String existId, T crawlerHandicap);

	/**
	 * 验证成功 记录爬虫<br>
	 * data中包含存在主键 <br>
	 * String existId = bean.getData().toString();
	 */
	@Override public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String account,
												  String password) {
		T crawlerHandicap = newCrawler();
		JsonResultBeanPlus bean = crawlerHandicap.valiLogin(handicapUrl, handicapCaptcha, account, password);
		if (bean.notSuccess()) {
			return bean;
		}
		String existId = bean.getData().toString();
		handicapCrawler(existId, crawlerHandicap);
		return bean;
	}


	/**
	 * 不存在 - 第一次登陆 - 成功记录爬虫<br>
	 * 存在直接去登陆
	 */
	@Override public JsonResultBeanPlus login(String existId, String handicapUrl, String handicapCaptcha,
			String account, String password) {
		T crawlerHandicap = handicapCrawler(existId);
		JsonResultBeanPlus bean;
		//不存在 - 第一次登陆
		if (crawlerHandicap == null) {
			crawlerHandicap = newCrawler();
			bean = crawlerHandicap.login(existId, handicapUrl, handicapCaptcha, account, password);
			if (bean.notSuccess()) {
				return bean;
			}
			//成功记录爬虫
			handicapCrawler(existId, crawlerHandicap);
			return bean;
		} else {
			//存在直接去登陆
			return crawlerHandicap.login(handicapUrl, handicapCaptcha, account, password);
		}
	}

	@Override public void userInfo(String existId, String handicapUrl, String handicapCaptcha, String account,
			String password) {
		T	crawlerHandicap = newCrawler();
		crawlerHandicap.userInfo(existId,handicapUrl,handicapCaptcha,account,password);
		handicapCrawler(existId, crawlerHandicap);

	}
}
