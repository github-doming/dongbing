package com.common.handicap.sgwin;

import com.common.handicap.crawler.BaseHandicapGrab;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * SgWin盘口 抓取操作抽象类
 *
 * @Author: Dongming
 * @Date: 2020-06-09 14:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseSgWinGrab extends BaseHandicapGrab {
	protected static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.SGWIN;

	/**
	 * 获取线路页面
	 * https://138111.co/user-search-result.php?search=160127
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 会员可用线路
	 */
	public String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
									 int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String routeHtml = null;
		try {
			String navigationHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));

			if (StringTool.isEmpty(navigationHtml) || StringTool.isContains(navigationHtml, "Bad Gateway")) {
				log.error("获取导航页面失败：{}", navigationHtml);
				sleep();
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if (StringTool.isContains(navigationHtml, "百度")) {
				return null;
			}
			//多次输入验证码,可能跳过导航页面，判断是否为线路选择页面
			if (StringTool.isContains(navigationHtml, "线路选择")) {
				return navigationHtml;
			}

			//导航页action
			String actionUrl = getNavigationAction(navigationHtml);
			//获取线路页面url
			String url = handicapUrl.concat(actionUrl).concat("?search=").concat(handicapCaptcha);
			//获取线路页面
			routeHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "线路选择")) {
				log.error("获取线路页面失败:{}", routeHtml);
				sleep();
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			sleep();
			return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), handicapUrl, handicapCaptcha, index[0],
					routeHtml);
		}
		//会员可用线路页面
		return routeHtml;
	}

	/**
	 * 获取导航页action
	 *
	 * @param navigationHtml 导航页面
	 * @return action路径
	 */
	private static String getNavigationAction(String navigationHtml) {
		Document navigationDocument = Jsoup.parse(navigationHtml);
		Element select = navigationDocument.selectFirst("form");
		return select.attr("action");
	}
}
