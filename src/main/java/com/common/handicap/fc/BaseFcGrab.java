package com.common.handicap.fc;

import com.common.enums.HcCodeEnum;
import com.common.handicap.crawler.BaseHandicapGrab;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-07-13 16:22
 * @Version: v1.0
 */
public class BaseFcGrab extends BaseHandicapGrab {
	protected static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.FC;

	/**
	 * 获取线路页面 -代理 会员共用
	 *
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 可用线路
	 */
	public String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
											int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String routeHtml;
		try {
			//导航页action
			Map<String, Object> dataMap = new HashMap<>(2);
			dataMap.put("q", handicapCaptcha);
			dataMap.put("Submit", "%E6%9F%A5+%E8%AF%A2");

			// 获取线路
			routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl).map(dataMap));

			if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "線路")) {
				log.error("获取线路页面失败：{}", routeHtml);
				longSleep();
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			longSleep();
			return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}
		//可用线路页面
		return routeHtml;
	}

	/**
	 * 获取登录页面
	 *
	 * @param httpConfig http请求配置类
	 * @param routes     线路
	 * @param index      循环次数
	 * @param type  区分关键词 会员："用戶"  --代理 ："Welcome"
	 * @return 登录信息map
	 */
	public String getLoginHtml(HttpClientConfig httpConfig, String[] routes,String type, int... index){
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[1] > MAX_RECURSIVE_SIZE) {
			index[0]++;
		}
		if (index[0] >= routes.length) {
			return "";
		}
		String html;
		try {
			//获取登录页面
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]]));
			if (StringTool.isEmpty(html) || !StringTool.isContains(html, type)) {
				log.error("打开登录页面失败", html);
				longSleep();
				return getLoginHtml(httpConfig, routes, type, index[0], ++index[1]);
			}
		} catch (Exception e) {
			log.error("打开登录页面失败", e);
			longSleep();
			return getLoginHtml(httpConfig, routes, type,index[0], ++index[1]);
		}
		return routes[index[0]];
	}


	/**
	 * 获取会员可用线路 代理会员共用，type区分
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  线路页面
	 * @param type  会员：w  --代理 ：g
	 * @return 线路数组
	 */
	public static String[] getRoute(HttpClientConfig httpConfig, String routeHtml,String type) {
		Document document = Jsoup.parse(routeHtml);
		Element trs = document.getElementById("form1");
		Elements as = trs.select("a");
		List<String> hrefs = new ArrayList<>();

		for (Element a : as) {
			String url = a.attr("href");
			if(url.contains(type)){
				hrefs.add(url);
			}
		}
		String[] routes = new String[hrefs.size()];
		long[] arr = new long[hrefs.size()];

		//判断时间延迟
		long t1, t2;
		for (int i = 0; i < hrefs.size(); i++) {
			t1 = System.currentTimeMillis();
			String href = hrefs.get(i).concat("/");
			String url = href.concat("?")
					.concat(System.currentTimeMillis()+"");
			try {
				HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			} catch (Exception e) {
				routes[i] = href;
				arr[i] = 1000 * 1000L;
				continue;
			}
			t2 = System.currentTimeMillis();
			long myTime = t2 - t1;
			routes[i] = href;
			arr[i] = myTime;
		}

		//线路按延时从小到大排序
		for (int x = 0; x < arr.length; x++) {
			for (int j = 0; j < arr.length - x - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					long t = arr[j];
					String route = routes[j];

					arr[j] = arr[j + 1];
					routes[j] = routes[j + 1];

					arr[j + 1] = t;
					routes[j + 1] = route;
				}
			}
		}
		return routes;
	}




	/**
	 * 登陆错误
	 *
	 * @param msg 登陆结果页面
	 * @return 错误信息
	 */
	public HcCodeEnum loginError(String msg) {
		log.error("FC盘口会员登陆异常，异常的登陆结果页面为：" + msg);
		if (StringTool.contains(msg, "帳號已經停用")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "帳號或者密碼錯誤")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "重置密碼")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}

	public void phaseid(String phaseid){
		crawlerImage().crawlerInfo("phaseid", phaseid);
	}
	public String phaseid(){
		return crawlerImage().crawlerInfo().get("phaseid");
	}

}
