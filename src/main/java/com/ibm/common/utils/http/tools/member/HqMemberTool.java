package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: hq盘口会员工具类
 * @Author: zjj
 * @Date: 2019-09-10 17:15
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HqMemberTool {
    private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";

	private static final long SHORT_SLEEP = 500L;
	private static final long SLEEP = 1000L;

	protected static final Logger log = LogManager.getLogger(HqMemberTool.class);
	private static final Integer MAX_RECURSIVE_SIZE = 5;
	private static final String CRACK_URL = "http://115.159.55.225/Code/GetVerifyCodeFromContent";

	// TODO 开启页面函数

	/**
	 * 打开页面获取会员href
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     url地址
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return
	 */
	public static String getMemberHref(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		try {
			//获取导航页面
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));
			if (StringTool.isEmpty(html)) {
				log.error("打开导航页面为空");
				return getMemberHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if (setCookie(httpConfig, handicapUrl, html)) {
				return getMemberHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if(StringTool.contains(html,"百度一下，你就知道","http://parked-content.godaddy.com/park/MaNmZmt4YaOv")){
				log.error("打开导航页面为错误");
				Thread.sleep(SLEEP);
				return getMemberHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			String action = navigationHtml(html);

			if (StringTool.isEmpty(action)) {
				log.error("打开导航页面失败" + html);
				Thread.sleep(SLEEP);
				return getMemberHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			Map<String, Object> join = new HashMap<>(1);
			join.put("SafeCode", handicapCaptcha);
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl + action).map(join));

			String href = getMemberLoginHref(html);
			if (StringTool.isEmpty(href)) {
				log.error("打开角色选择页面失败" + html);
				Thread.sleep(SLEEP);
				return getMemberHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			//只获取盘口会员href
			return href;
		} catch (Exception e) {
			log.error("打开角色选择页面失败", e);
			Thread.sleep(SHORT_SLEEP);
			return getMemberHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}
	}

	/**
	 * 获取线路
	 *
	 * @param httpConfig  http请求配置类
	 * @param href        请求路径
	 * @param handicapUrl 盘口url
	 * @param index       循环次数
	 * @return
	 */
	public static String[] getSelectRoutePage(HttpClientConfig httpConfig, String href, String handicapUrl,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		try {
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl + href));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "线路选择")) {
				log.error("打开选择线路界面失败" + html);
				Thread.sleep(SLEEP);
				return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
			}
			String url = handicapUrl.concat("/Url/GetUrlList?").concat(href.split("\\?")[1]).concat("&_=") + System
					.currentTimeMillis();

			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			String[] routes = getRoutes(httpConfig, html);
			if (ContainerTool.isEmpty(routes)) {
				log.error("获取线路失败");
				Thread.sleep(SLEEP);
				return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
			}
			return routes;
		} catch (IOException e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(SLEEP);
			return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
		}
	}

	/**
	 * 获取登录页面
	 *
	 * @param httpConfig      http请求配置类
	 * @param routes          线路
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return
	 */
	public static Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, String handicapCaptcha,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[1] >= MAX_RECURSIVE_SIZE) {
			index[1] = 0;
			index[0]++;
		}
		if (index[0] >= routes.length) {
			return new HashMap<>(1);
		}
		String url = "http://".concat(routes[index[0]])
				.concat("/Member/Login?_=" + System.currentTimeMillis() + "&token=" + handicapCaptcha);

		Map<String, String> map = new HashMap<>(2);
		try {
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			if (StringTool.isEmpty(html)) {
				log.error("获取登录页面失败");
				Thread.sleep(SLEEP);
				return getLoginHtml(httpConfig, routes, handicapCaptcha, index[0], ++index[1]);
			}
			if (setCookie(httpConfig, routes[index[0]], html)) {
				return getLoginHtml(httpConfig, routes, handicapCaptcha, index[0], ++index[1]);
			}
			map.put("html", html);
			map.put("hostUrl", routes[index[0]]);
		} catch (Exception e) {
			log.error("获取登录页面失败", e);
			Thread.sleep(SLEEP);
			return getLoginHtml(httpConfig, routes, handicapCaptcha, index[0], ++index[1]);
		}
		return map;
	}

	/**
	 * 获取登录加密key
	 *
	 * @param httpConfig http请求配置类
	 * @param sessionId  参数sessionid
	 * @param hostUrl    主机地址
	 * @param index      循环次数
	 * @return 加密key
	 */
	public static JSONObject getEncryptKey(HttpClientConfig httpConfig, String sessionId, String hostUrl, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		String url = "http://".concat(hostUrl).concat(sessionId).concat("/Member/GK");
		String html=null;
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url));
			if (ContainerTool.isEmpty(html)) {
				log.error("获取登录加密key失败");
				Thread.sleep(SLEEP);
				return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
			}
			JSONObject result = JSONObject.parseObject(html);
			if (ContainerTool.isEmpty(result) || result.getInteger("Status") - 1 != 0) {
				return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
			}
			return result.getJSONObject("Data");
		} catch (Exception e) {
			log.error("获取登录加密key失败,结果信息="+html,e);
			Thread.sleep(SLEEP);
			return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
		}
	}

	/**
	 * 登录盘口
	 *
	 * @param httpConfig      http请求配置类
	 * @param hostUrl         主机地址
	 * @param sessionId       参数sessionId
	 * @param handicapCaptcha 盘口验证码
	 * @param encryptKey      加密信息-vk
	 * @param logpk           参数logpk
	 * @param index           循环次数
	 * @return
	 * @throws IOException
	 */
	public static String getLogin(HttpClientConfig httpConfig, String hostUrl, String sessionId, String handicapCaptcha,
			String encryptKey, String logpk, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
        String url = "http://".concat(hostUrl).concat(sessionId).concat("/Member/DoLogin");
        Map<String, Object> join = new HashMap<>(8);
        join.put("token", handicapCaptcha);
        //TODO,验证码
        join.put("Captcha", "");
        join.put("info", encryptKey);
        join.put("pk", logpk);
        String html=null;
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("登录盘口失败");
				Thread.sleep(SLEEP);
				return getLogin(httpConfig, hostUrl, sessionId, handicapCaptcha, encryptKey, logpk, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("登录盘口失败", e);
			Thread.sleep(SLEEP);
			return getLogin(httpConfig, hostUrl, sessionId, handicapCaptcha, encryptKey, logpk, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,join,index[0],html));
        }
	}

	/**
	 * 获取协议页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param sessionid   sessionId
	 * @param index       循环次数
	 * @return
	 */
	public static String getAcceptAgreement(HttpClientConfig httpConfig, String projectHost, String sessionid,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html=null;

		String url = "http://".concat(projectHost).concat(sessionid).concat("/Member/AcceptAgreement");
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取协议页面失败");
				Thread.sleep(SLEEP);
				return getAcceptAgreement(httpConfig, projectHost, sessionid, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取协议页面失败");
			Thread.sleep(SLEEP);
			return getAcceptAgreement(httpConfig, projectHost, sessionid, ++index[0]);
		}
		httpConfig.setHeader("Referer",
				"http://".concat(projectHost).concat(sessionid).concat("/All/Agreement.html?s=").concat(sessionid));
		return html;
	}

	/**
	 * 获取index页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param sessionid   sessionId
	 * @param index       循环次数
	 * @return index页面
	 */
	public static String getIndex(HttpClientConfig httpConfig, String projectHost, String sessionid, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html;
		String url =
				"http://".concat(projectHost).concat(sessionid).concat("/App/Index?_=") + System.currentTimeMillis();

		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取主页面失败");
				Thread.sleep(SLEEP);
				return getIndex(httpConfig, projectHost, sessionid, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取主页面失败");
			Thread.sleep(SLEEP);
			return getIndex(httpConfig, projectHost, sessionid, ++index[0]);
		}
		httpConfig.setHeader("Referer", url);
		return html;
	}

	/**
	 * 获取用户信息（也可以获取到当前期该游戏投注未结算投注项信息）
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param game        盘口游戏code
	 * @param periodId    期数字符串
	 * @param index       循环次数
	 * @return
	 */
	public static JSONObject getUserInfo(HttpClientConfig httpConfig, String projectHost, String game, String periodId,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		String html = null;
		JSONObject data;
		String url =
				projectHost.concat("Member/GetMemberInfo?lotteryId=").concat(game).concat("&periodId=").concat(periodId)
						.concat("&_=") + System.currentTimeMillis();
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取用户信息失败");
				Thread.sleep(SLEEP);
				return getUserInfo(httpConfig, projectHost, game, periodId, ++index[0]);
			}
			if(StringTool.isContains(html,"系统升级中")){
                log.error("获取用户信息失败",html);
                data=new JSONObject();
                data.put("data","系统升级中");
                return data;
            }
            if(StringTool.contains(html,"Server:","robot7_session_id","document.cookie","SafeCode","callback7()")){
                log.error("获取用户信息失败",html);
                data=new JSONObject();
                data.put("data","login");
                return data;
            }
			data = JSONObject.parseObject(html).getJSONObject("Data");
		} catch (Exception e) {
			log.error("获取用户信息失败,结果信息="+html, e);
			Thread.sleep(SLEEP);
			return getUserInfo(httpConfig, projectHost, game, periodId, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,"",index[0],html));
        }
		return data;
	}

    /**
     *  http://f1.hy7994666.xyz/(S(pwxofckob0zgwx4drp15xi3o))/NetTest/NetTestNew?_=1585995849570
     * {"Status":1,"Data":{"GameId":4,"CompanyId":67,"Account":"gbd11hy02","ClientIP":"119.28.186.226","UrlLine":"f1.hy7994666.xyz","ServerIp":"10.10.56.93","ServerTime":"2020-04-04 18:25:29"}}
     * 获取会员账号信息
     * @param httpConfig    http请求配置类
     * @param projectHost   主机地址
     * @param index         循环次数
     * @return
     */
    public static JSONObject getMemberAccount(HttpClientConfig httpConfig, String projectHost,int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return new JSONObject();
        }
        JSONObject data;
        String html = null;
        String url=projectHost.concat("NetTest/NetTestNew?_=")+System.currentTimeMillis();
        try {
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
            if (StringTool.isEmpty(html)) {
                log.error("获取会员账号信息失败");
                Thread.sleep(SLEEP);
                return getMemberAccount(httpConfig, projectHost,++index[0]);
            }
            if(StringTool.isContains(html,"系统升级中")){
                log.error("获取会员账号信息失败",html);
                data=new JSONObject();
                data.put("data","系统升级中");
                return data;
            }
            if(StringTool.contains(html,"Server:","robot7_session_id","document.cookie","SafeCode","callback7()")){
                log.error("获取会员账号信息失败",html);
                data=new JSONObject();
                data.put("data","login");
                return data;
            }
            data = JSONObject.parseObject(html).getJSONObject("Data");
        } catch (Exception e) {
            log.error("获取会员账号信息失败,结果信息="+html, e);
            Thread.sleep(SLEEP);
            return getMemberAccount(httpConfig, projectHost,++index[0]);
        }finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,"",index[0],html));
        }
        return data;
    }

	/**
	 * 获取游戏限额
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param game        盘口游戏code
	 * @param index       循环次数
	 * @return 游戏限额信息
	 */
	public static JSONArray getQuotaList(HttpClientConfig httpConfig, String projectHost, String game, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONArray();
		}
		String html = null;
		String url = projectHost.concat("Member/GetMemberBetInfo/?lotteryId=").concat(game).concat("&_=") + System
				.currentTimeMillis();
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏限额失败");
				Thread.sleep(SLEEP);
				return getQuotaList(httpConfig, projectHost, game, ++index[0]);
			}
            if(StringTool.isContains(html,"系统升级中","请输入有效彩种")){
                log.error("获取游戏限额失败",html);
                JSONArray data=new JSONArray();
                data.add("系统升级中");
                return data;
            }
            if(StringTool.isContains(html,"robot7_session_id","document.cookie")){
                log.error("获取游戏限额失败",html);
                JSONArray data=new JSONArray();
                data.add("login");
                return data;
            }
            return getLimit(html, game);
		} catch (Exception e) {
		    log.error("获取游戏限额信息="+html);
			log.error("获取游戏限额失败", e);
			Thread.sleep(SLEEP);
			return getQuotaList(httpConfig, projectHost, game, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,"",index[0],html));
        }
	}

	/**
	 * 获取赔率信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param m           类别位置
	 * @param marketTypes 赔率code
	 * @param game        盘口游戏code
	 * @param index       循环次数
	 * @return
	 */
	public static JSONObject getOddsInfo(HttpClientConfig httpConfig, String projectHost, String m,
			JSONArray marketTypes, String game, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		String html=null;
		String url = projectHost.concat("Bet/GetBetPageRefresh/?m=").concat(m).concat("&statNo=0&marketTypes=")
				.concat(marketTypes.toString()).concat("&lotteryId=").concat(game).concat("&_=") + System
				.currentTimeMillis();
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏赔率失败");
				Thread.sleep(SLEEP);
				return getOddsInfo(httpConfig, projectHost, m, marketTypes, game, ++index[0]);
			}
            if(StringTool.isContains(html,"robot7_session_id","document.cookie","请输入有效彩种")){
                log.error("获取游戏赔率失败",html);
                return new JSONObject();
            }
            if(StringTool.isContains(html,"请输入有效彩种")){
                JSONObject json=new JSONObject();
                json.put("errorInfo",html);
                return json;
            }
            return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("获取游戏赔率失败,结果信息="+html, e);
			Thread.sleep(SLEEP);
			return getOddsInfo(httpConfig, projectHost, m, marketTypes, game, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,"",index[0],html));
        }
	}

	/**
	 * 投注
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param betParams   投注参数
	 * @param game        盘口游戏code
	 * @return 投注结果
	 */
	public static JSONObject betting(HttpClientConfig httpConfig, String projectHost, JSONObject betParams,
			String game) {
		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpConfig.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");

        httpConfig.httpTimeOut(20 * 1000);
		Map<String, Object> map = new HashMap<>(2);
		map.put("betParams", betParams.toString());
		map.put("lotteryId", Integer.parseInt(game));

		String html=null;
		String url=projectHost.concat("Bet/DoBet/");
		try {
            html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(map));
			if (ContainerTool.isEmpty(html)||StringTool.contains(html,"系统升级中","robot7_session_id")) {
				log.error("投注失败,结果="+html+",投注项为：" + betParams);
                return new JSONObject();
			}
            return JSONObject.parseObject(html);
		} finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,map,0,html));
        }
	}

	/**
	 * 获取投注成功项
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param game        盘口游戏code
	 * @param index       循环次数
	 * @return
	 */
	public static JSONObject getBettingResult(HttpClientConfig httpConfig, String projectHost, String game,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
            return new JSONObject();
		}
		String html=null;

		String url = projectHost.concat("Bet/GetBettingSuccess/?lotteryId=").concat(game).concat("&_=") + System
				.currentTimeMillis();
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取投注成功项失败");
				Thread.sleep(SLEEP);
				return getBettingResult(httpConfig, projectHost, game, ++index[0]);
			}
            if(StringTool.isContains(html,"系统升级中")){
                log.error("获取投注成功项失败",html);
                JSONObject data=new JSONObject();
                data.put("data","系统升级中");
                return data;
            }
            if(StringTool.isContains(html,"robot7_session_id","document.cookie")){
                log.error("获取投注成功项失败",html);
                JSONObject data=new JSONObject();
                data.put("data","login");
                return data;
            }
            return JSONObject.parseObject(html);
		} catch (Exception e) {
            log.error("获取投注成功项信息=", html);
			log.error("获取投注成功项失败", e);
			Thread.sleep(SLEEP);
			return getBettingResult(httpConfig, projectHost, game, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,"",index[0],html));
        }
	}

	// TODO Document解析页面函数

	/**
	 * 解析导航页面获取action
	 *
	 * @param navigationHtml 导航页面
	 * @return
	 */
	private static String navigationHtml(String navigationHtml) {
		Document document = Jsoup.parse(navigationHtml);

		if (!StringTool.isContains(document.body().html(), "action")) {
			log.info("解析导航页面出错，页面为：" + navigationHtml);
			return null;
		}
		Element body = document.body();

		Elements select = body.select("form");

		return select.attr("action");
	}

	/**
	 * 获取登录href
	 *
	 * @param html 角色选择页面
	 * @return
	 */
	private static String getMemberLoginHref(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		if (!StringTool.isContains(html, "为了更好的浏览体验，请使用竖屏浏览！")) {
			return null;
		}
		Document document = Jsoup.parse(html);
		Element a = document.body().selectFirst("a");

		return a.attr("href");
	}

	/**
	 * 获取线路并排序
	 *
	 * @param httpConfig http请求配置类
	 * @param html       线路
	 * @return
	 */
	private static String[] getRoutes(HttpClientConfig httpConfig, String html) throws IOException {
		if (ContainerTool.isEmpty(html)) {
			return null;
		}
		JSONArray array;
		try {
			array = JSONObject.parseObject(html).getJSONArray("Data");
		} catch (Exception e) {
			log.error("转换结果页面失败【" + html + "】", e);
			return null;
		}

		String[] routes = new String[array.size()];

		long[] arr = new long[array.size()];

		//判断时间延迟
		long t1, t2;
		for (int i = 0; i < array.size(); i++) {
			if (i == 5 || i == 6) {
				routes[i] = array.getString(i);
//				arr[i] = 500;
				continue;
			}
			t1 = System.currentTimeMillis();
			String url =
					"http://" + array.getString(i).concat("/Test/GetNetSpeed?jsonp=callback").concat(String.valueOf(i))
							.concat("&_=") + System.currentTimeMillis();

			HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			t2 = System.currentTimeMillis();
			long mytime = t2 - t1;
			if (i == 3 || i == 4 || i == 7) {
//				mytime += 200;
			}
			routes[i] = array.getString(i);
			arr[i] = mytime;
		}

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
	 * 需要设置cookie
	 *
	 * @param httpConfig http请求配置类
	 * @param url        url地址
	 * @param html       需要解析的页面
	 * @return 设置结果
	 */
	private static boolean setCookie(HttpClientConfig httpConfig, String url, String html) {
		//是否要放入cookie
		String cookieStr = StringUtils.substringBetween(html, "document.cookie='", "'");
		if (StringTool.notEmpty(cookieStr)) {
			HttpClientContext context = httpConfig.httpContext();
			if (context == null) {
				context = HttpClientContext.create();
				httpConfig.httpContext(context);
			}
			String name = "", value = null, path = null, domain = url.replace("http://", "");
			for (String item : cookieStr.split(";")) {
				int index = item.indexOf('=');
				String key = item.substring(0, index).trim();
				switch (key) {
					case ClientCookie.PATH_ATTR:
						path = item.substring(index + 1).trim();
						break;
					case ClientCookie.DOMAIN_ATTR:
						break;
					default:
						name = key;
						value = item.substring(index + 1).trim();

				}
			}
			BasicClientCookie cookie = new BasicClientCookie(name, value);
			cookie.setDomain(domain);
			cookie.setPath(path);
			context.getCookieStore().addCookie(cookie);
			return true;
		}
		return false;
	}

	/**
	 * 获取SESSIONID和VERSION
	 *
	 * @param html 登录页面
	 */
	public static Map<String, String> getScriptInfo(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		Map<String, String> map = new HashMap<>(2);
		Document document = Jsoup.parse(html);

		Element script = document.selectFirst("script");

		String sessionid = StringUtils.substringBetween(script.html(), "var SESSIONID = \"", "\";");
		String version = StringUtils.substringBetween(script.html(), "var VERSION = \"", "\";");
		map.put("SESSIONID", "/".concat(sessionid));
		map.put("VERSION", version);
		return map;
	}

	/**
	 * 解析获取盘口游戏限额
	 *
	 * @param html 会员投注信息页面
	 * @param game 盘口游戏code
	 * @return
	 */
	private static JSONArray getLimit(String html, String game) {

		JSONObject data = JSONObject.parseObject(html).getJSONObject("Data");

		if (!data.containsKey("BetLimits")) {
            return new JSONArray();
		}
		JSONArray betLimits = data.getJSONArray("BetLimits");
		JSONArray quota = new JSONArray();
		JSONArray array;
		JSONObject infos;
		for (int i = 0; i < betLimits.size(); i++) {
			if (!String.valueOf(betLimits.getJSONObject(i).getInteger("LotteryId")).equals(game)) {
				continue;
			}
			JSONArray limits = betLimits.getJSONObject(i).getJSONArray("BetLimitList");
			for (int j = 0; j < limits.size(); j++) {
				array = new JSONArray();
				infos = limits.getJSONObject(j);
				array.add(infos.get("OneBetMin"));
				array.add(infos.get("OneBetMax"));
				array.add(infos.get("OneItemMax"));
				quota.add(array);
			}
			break;
		}
		return quota;
	}

	/**
	 * @param gameCode
	 * @param betItems
	 * @param oddsData
	 * @return
	 */
	public static JSONArray getBetItemInfo(GameUtil.Code gameCode, String game, List<String> betItems,
			JSONArray oddsData) {
		JSONArray betsArray = new JSONArray();
		Map<String, String> ballCode = BallCodeTool.getBallCode(HandicapUtil.Code.HQ, gameCode);
		JSONObject betInfo;
		JSONObject odds;
		for (String betItem : betItems) {
			betInfo = new JSONObject();
			String[] items = betItem.split("\\|");
			String bet = items[0].concat("|").concat(items[1]);
			//单注金额须为整数值
			int fund = (int) NumberTool.doubleT(items[2]);
			String betCode = ballCode.get(bet);
			if (StringTool.isEmpty(betCode)) {
				log.error("错误的投注项" + betItem);
				continue;
			}
			for (int i = 0; i < oddsData.size(); i++) {
				odds = oddsData.getJSONObject(i);
				if (!game.concat(betCode).equals(String.valueOf(odds.getInteger("BetNo")))) {
					continue;
				}
				betInfo.put("BetNo", game.concat(betCode));
				betInfo.put("Odds", String.valueOf(odds.getDouble("Odds")));
				betInfo.put("BetMoney", fund);
				betsArray.add(betInfo);
				break;
			}
		}
		return betsArray;
	}

	/**
	 * 获取投注结果
	 *
	 * @param gameCode 游戏code
	 * @param items    投注结果
	 * @param game     盘口游戏code
	 * @return
	 */
	public static JSONArray getBetResult(GameUtil.Code gameCode, JSONArray items, String game) {
		Map<String, String> ballInfo = BallCodeTool.getBallInfoCode(HandicapUtil.Code.HQ, gameCode);

		JSONArray betResult = new JSONArray();
		JSONObject info;
		JSONArray betInfo;
		for (int i = 0; i < items.size(); i++) {
			info = items.getJSONObject(i);
			String item = ballInfo.get(String.valueOf(info.getInteger("BetNo")).substring(game.length()));
			if (StringTool.isEmpty(item)) {
				log.error("错误的投注项：" + info);
				continue;
			}
			betInfo = new JSONArray();
			//注单号
			betInfo.add(info.getString("SerialNo"));
			//投注项
			betInfo.add(item);
			//投注金额
			betInfo.add(info.get("BetMoney"));
			//赔率
			betInfo.add(info.get("Odds"));
			betResult.add(betInfo);
		}
		return betResult;
	}

	/**
	 * 过滤投注成功项
	 *
	 * @param data     投注成功项
	 * @param betItems 投注详情信息
	 * @param gameCode 游戏编码
	 */
	public static JSONArray filterSuccessInfo(JSONObject data, List<String> betItems, GameUtil.Code gameCode) {
		Map<String, String> ballCode = BallCodeTool.getResultCode(HandicapUtil.Code.HQ, gameCode);

		JSONArray bettingResult = data.getJSONArray("BettingResult");

		JSONArray betResult = new JSONArray();
		JSONArray betInfo;
		JSONObject info;
		for (int i = 0; i < bettingResult.size(); i++) {
			info = bettingResult.getJSONObject(i);
			String item = info.getString("BetNoFullName").replace(" ", "");
			long fund = NumberTool.longValueT(info.getInteger("BetMoney"));
			String betCode = ballCode.get(item);
			if (StringTool.isEmpty(betCode)) {
				log.error("未解析的投注项" + betCode);
				continue;
			}
			String betItem = betCode.concat("|").concat(String.valueOf(fund));
			if (betItems.contains(betItem)) {
				betInfo = new JSONArray();
				//注单号
				betInfo.add(info.getString("SerialNo"));
				//投注项
				betInfo.add(betCode);
				//投注金额
				betInfo.add(info.get("BetMoney"));
				//赔率
				betInfo.add(info.get("Odds"));
				betResult.add(betInfo);
				betItems.remove(betCode);
			}
		}
		return betResult;
	}

	/**
	 * 登陆错误
	 *
	 * @param msg 错误信息
	 * @return 错误信息
	 */
	public static HcCodeEnum loginError(String msg) {
		if (StringTool.contains(msg, "用户名或密码不正确")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "帐号被锁定")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "请求过于频繁")) {
			return HcCodeEnum.IBS_403_LOGIN_OFTEN;
		} else if (StringTool.contains(msg, "您的帐户为初次登陆", "密码由后台重新设定")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}
}
