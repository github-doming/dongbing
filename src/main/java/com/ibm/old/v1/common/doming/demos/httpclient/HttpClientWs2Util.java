package com.ibm.old.v1.common.doming.demos.httpclient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.EasyResultBean;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.doming.develop.http.jsoup.HttpJsoupTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Description: WS2盘口的httpunit工具类
 * @Author: Dongming
 * @Date: 2018-08-06 18:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpClientWs2Util {

	private static Integer RECURSIVE_SIZE = 0;
	private static final Integer MAX_RECURSIVE_SIZE = 5;
	private static EasyResultBean resultBean;

	// TODO 开启页面函数

	/**
	 * 打开选择线路界面
	 *
	 * @param httpConfig http请求配置类
	 * @param url        url地址
	 * @param code       验证码
	 * @return 选择线路界面
	 */
	public static String getSelectRoutePage(HttpClientConfig httpConfig, String url, String code) throws Exception {
		resultBean = EasyResultBean.init(resultBean);
		String html;
		try {
			String submitBt = "搜索";
			Map<String, Object> join = new HashMap<>(2);
			join.put("code", code);
			join.put("submit_bt", submitBt);
			HttpResult result = HttpClientUtil.findInstance().post(httpConfig.url(url).map(join));
			html = (String) result.getHtml();
			resultBean.succeeded();
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(e.getMessage());
			throw e;
		}
		resultBean.end();
		return html;

	}

	/**
	 * 获取登陆页面
	 *
	 * @param httpConfig http请求配置类
	 * @param routes     可用线路
	 * @return 登陆页面信息
	 */
	public static HttpResult getLoginPage(HttpClientConfig httpConfig, List<String> routes) {
		EasyResultBean.init(resultBean);
		HttpResult loginPage = null;
		try {
			routes.remove(0);
			loginPage = getLoginPage(httpConfig, routes, 0);
			resultBean.succeeded();
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(e.getMessage());

		}
		resultBean.end();
		return loginPage;
	}

	/**
	 * 获取登陆页面信息
	 *
	 * @param httpConfig http请求配置类
	 * @param routes     可用线路
	 * @param index      轮训索引
	 * @return 登陆页面信息
	 */
	private static HttpResult getLoginPage(HttpClientConfig httpConfig, List<String> routes, int index) {
		if (index >= routes.size()) {
			index = 1;
			RECURSIVE_SIZE++;
		}
		if (RECURSIVE_SIZE > MAX_RECURSIVE_SIZE) {
			RECURSIVE_SIZE = 0;
			return null;
		}
		HttpResult result = null;
		try {
			result = HttpClientUtil.findInstance().get(httpConfig.url(routes.get(index)));
		} catch (Exception e) {
			System.out.println("线路" + routes.get(index) + "打开失败,换另一条线路\t");
		}
		if (result == null) {
			return getLoginPage(httpConfig, routes, index + 1);
		}
		if (isLoginPage(result)) {
			result.setData( routes.get(index));
			return result;
		}
		return getLoginPage(httpConfig, routes, index + 1);
	}

	/**
	 * 登陆
	 *
	 * @param httpConfig http请求配置类
	 * @param toLoginUrl 登陆地址
	 * @return 登陆结果信息
	 */
	public static HttpResult getLoginResult(HttpClientConfig httpConfig, String toLoginUrl) {
		EasyResultBean.init(resultBean);
		HttpResult result = null;
		try {
			result = HttpClientUtil.findInstance().post(httpConfig.url(toLoginUrl));
			resultBean.succeeded();
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(e.getMessage());
		}
		resultBean.end();
		return result;
	}

	/**
	 * 根据返回信息获取再次请求地址
	 *
	 * @param httpConfig     http请求配置类
	 * @param hostUrl        登陆主机地址
	 * @param loginResultMap 登陆结果信息
	 * @return 再次跳转页面信息
	 */
	public static HttpResult getKPage(HttpClientConfig httpConfig, String hostUrl,
									  HttpResult loginResultMap) {
		EasyResultBean.init(resultBean);
		HttpResult result = null;
		String html = (String) loginResultMap.getHtml();
		try {
			String[] strs = html.split("://host");
			result = HttpClientUtil.findInstance().get(httpConfig.url(StringTool.format(hostUrl + strs[1])));
			resultBean.succeeded();
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(html);
		}
		resultBean.end();
		return result;
	}

	/**
	 * 获取盘口主界面
	 *
	 * @param httpConfig http请求配置类
	 * @param kPage      再次跳转页面信息
	 * @return 盘口主界面信息
	 */
	public static HttpResult getHomePage(HttpClientConfig httpConfig, Map<String, Object> kPage) {
		EasyResultBean.init(resultBean);
		HttpResult result = null;
		try {
			if (302 == ((Integer) kPage.get("statusCode"))) {
				result = HttpClientUtil.findInstance().get(httpConfig.url((String) kPage.get("location")));
				resultBean.succeeded();
			}
			resultBean.failed();
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(e.getMessage());
		}
		resultBean.end();
		return result;
	}

	/**
	 * 获取用户信息
	 *
	 * @param httpConfig http请求配置类
	 * @param hostUrl    主机地址
	 * @param userCode   用户识别码
	 * @param gameCode   游戏码
	 * @return 用户信息
	 */
	public static JSONObject getUserInfo(HttpClientConfig httpConfig, String hostUrl, String userCode, String gameCode) {
		EasyResultBean.init(resultBean);
		JSONObject jsonObject = null;
		try {
			/*
				网址例子
				http://pc4.wvvwv.xyz/sscas8137262f_6670/klc/order/leftInfo/?&_=1537520563727__ajax
					act: hand
					sys: pk10
			 */
			String url = hostUrl + userCode + gameCode + "/order/leftInfo/?&_=" + System.currentTimeMillis()
					+ "__ajax&act=hand&sys=" + gameCode;
			HttpResult result = HttpClientUtil.findInstance().get(httpConfig.url(StringTool.format(url)));

			jsonObject = resolveUserInfo(result);
			resultBean.succeeded();
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(e.getMessage());
		}
		resultBean.end();
		return jsonObject;
	}

	/**
	 * 获取游戏投注信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param hostUrl     主机地址
	 * @param userCode    用户识别码
	 * @param gameCode    游戏码
	 * @param gameBetCode 游戏识别码
	 * @return 游戏投注信息
	 */
	public static JSONObject getGameBetInfo(HttpClientConfig httpConfig, String hostUrl, String userCode, String gameCode,
			String gameBetCode) {
		EasyResultBean.init(resultBean);
		JSONObject jsonObject = null;
		try {
			/*
				网址例子
				http://pc4.wvvwv.xyz/sscas8137262f_6670/klc/order/list/?&_=1537520563731__ajax
				http://pc4.1ll11.com/sscas8137262f_4964/ pk/order/list/?&_=1537525914592__ajax&play=bothSides_pk10&cat=15
					play: bothSides
					ball:
					cat: 13
			 */
			String url =
					hostUrl + userCode + gameCode + "/order/list?" + gameBetCode + "&_=" + System.currentTimeMillis()
							+ "__ajax";
			HttpResult result = HttpClientUtil.findInstance().post(httpConfig.url(StringTool.format(url)));

			jsonObject = resolveBetInfo(result);
			resultBean.succeeded();
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.failed();
			resultBean.message(e.getMessage());
		}
		resultBean.end();
		return jsonObject;

	}

	/**
	 * 投注pk10
	 *
	 * @param pk10BallCode  pk10的球码
	 * @param httpConfig    http请求配置类
	 * @param hostUrl       主机地址
	 * @param userCode      用户识别码
	 * @param gameCode      游戏码
	 * @param betItems      投注基本信息
	 * @param versionNumber 版本号
	 * @return 投注结果
	 */
	public static JSONObject bettingPk10(Map<String, String> pk10BallCode, HttpClientConfig httpConfig, String hostUrl,
			String userCode, String gameCode, JSONArray betItems, String versionNumber) {
		EasyResultBean.init(resultBean);
		JSONObject jsonObject = null;
		try {
		/*
				网址例子
				http://pc4.wvvwv.xyz/sscas8137262f_4964/pk/order/leftInfo/?post_submit&&_=1537609552719__ajax
					t: 010|0|1.983|10;032|1|1.983|30;
					v: 13
					[{"item":"冠军|大","amount":"10","betCode":"010|0","oddCode":"0100"},{"item":"第三名|虎","amount":"30","betCode":"032|1","oddCode":"0321"}]
		*/
			String url = hostUrl + userCode + gameCode + "/order/leftInfo";

			StringBuilder t = new StringBuilder();
			Map<String, Object> data = new HashMap<>(4);

			for (int i = 0; i < betItems.size(); i++) {
				JSONObject betItem = betItems.getJSONObject(i);
				t.append(betItem.getString("betCode")).append("|").append(betItem.getString("odd")).append("|")
						.append(betItem.getString("amount")).append(";");
			}
			data.put("t", t.toString());
			data.put("v", versionNumber);
			data.put("post_submit", "");
			data.put("_", System.currentTimeMillis() + ("__ajax"));
			data.put("", null);

			HttpResult result = HttpClientUtil.findInstance()
					.post(httpConfig.map(data).url(StringTool.format(url)));

			jsonObject = resolveBetResult(result);
			resultBean.succeeded();
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.failed();
			resultBean.message(e.getMessage());
		}
		resultBean.end();
		return jsonObject;

	}

	//开启页面函数

	// TODO 解析页面函数
	/**
	 * 获取线路
	 *
	 * @param pageStr 页面字符串
	 * @return 线路列表
	 */
	public static List<String> findRoutes4Login(String pageStr) {
		EasyResultBean.init(resultBean);
		List<String> routes = null;
		try {
			Document doc = Jsoup.parse(pageStr);
			Elements uiList = doc.select(".datalist>ul");
			Elements liList = uiList.get(0).select("li [href]");
			routes = new ArrayList<>();
			for (Element element : liList) {
				routes.add(element.attr("href"));
			}
			resultBean.succeeded();
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(e.getMessage());
		}
		resultBean.end();
		return routes;
	}

	/**
	 * 判断是否打开了登陆页面
	 *
	 * @param map 登陆界面信息
	 * @return 是否打开了登陆页面
	 */
	private static boolean isLoginPage(HttpResult map) {
		String html = map.getHtml();
		Document doc = Jsoup.parse(html);
		Element cid = doc.selectFirst("div.sbt>input[name=cid]");
		return cid != null;
	}

	/**
	 * 获取登陆界面包含的数据
	 *
	 * @param map 登陆界面信息
	 * @return script数据信息
	 */
	public static Map<String, Object> findLoginDateMap4LoginPage(HttpResult map) {
		EasyResultBean.init(resultBean);
		Map<String, Object> loginDateMap = null;
		try {
			loginDateMap = new HashMap<>(4);
			String loginHtml = (String) map.getHtml();
			Document document = Jsoup.parseBodyFragment(loginHtml);
			Element scriptEle = document.selectFirst(("script"));
			String scriptStr = scriptEle.html();

			String patternStr, selectStr, findStr;
			String[] strMap;
			Pattern pattern;
			selectStr = "var systemversion =";
			if (scriptStr.contains(selectStr)) {
				patternStr = selectStr + " \"\\w{3}\"";
				pattern = Pattern.compile(patternStr);
				Matcher matcher = pattern.matcher(scriptStr);
				if (matcher.find()) {
					findStr = StringTool.trimAll(matcher.group()).replace("var", "").replace("\"", "");
					strMap = findStr.split("=");
					loginDateMap.put(strMap[0], strMap[1]);
				}
			}
			Element cid = document.selectFirst("div.sbt>input[name=cid]");
			Element cname = document.selectFirst("div.sbt>input[name=cname]");
			loginDateMap.put("cid", cid.val());
			loginDateMap.put("cname", cname.val());
			resultBean.succeeded();
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(e.getMessage());
		}
		resultBean.end();
		return loginDateMap;
	}

	/**
	 * 获取用户专属code
	 *
	 * @param html 登陆结果
	 * @return 用户专属code
	 */
	public static String getUserCode(String html) {
		EasyResultBean.init(resultBean);
		String userCode = null;
		try {
			String[] strs = html.split("\\r?\\n");
			resultBean.succeeded();
			userCode = strs[0];
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(e.getMessage());
		}
		resultBean.end();
		return userCode;
	}

	//解析页面函数

	// TODO 功能函数
	/**
	 * 获取验证码地址
	 *
	 * @param hostUrl      登陆界面主机地址
	 * @param loginDateMap script数据信息
	 * @return 验证码地址
	 */
	public static String getValiImageUrl(String hostUrl, Map<String, Object> loginDateMap) {
		EasyResultBean.init(resultBean);
		String imageUrl = null;
		try {
			String key = "systemversion", valiCodeUrl;
			String result = null;
			while (result == null) {
				try {
					valiCodeUrl = getValiCodeUrl(hostUrl, key, loginDateMap.get(key));
					result = HttpJsoupTool.doGetJson(valiCodeUrl);
				} catch (Exception ignored) {
				}
			}
			String[] results = result.split("_");
			loginDateMap.put("verifyvalue", results[1]);
			imageUrl = hostUrl + "/getVcode/.auth?t=" + results[0] + "&" + key + "=" + loginDateMap.get(key) + "&.auth";
			resultBean.succeeded();
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(e.getMessage());
			e.printStackTrace();
		}
		resultBean.end();
		return imageUrl;
	}

	/**
	 * 弹窗提醒用户输入验证码
	 *
	 * @param valiImgUrl 验证码地址
	 * @return 验证码
	 */
	public static String getVerifyCode(String valiImgUrl) {
		EasyResultBean.init(resultBean);
		HttpURLConnection httpUrl = null;
		String valiCodeStr = null;
		try {
			URL url = new URL(valiImgUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			BufferedImage bufferedImage = ImageIO.read(httpUrl.getInputStream());
			JFrame frame = new JFrame();
			JLabel label = new JLabel();
			label.setIcon(new ImageIcon(bufferedImage));
			frame.getContentPane().add(label);
			frame.setSize(100, 100);
			frame.setTitle("验证码");
			frame.setVisible(true);
			valiCodeStr = JOptionPane.showInputDialog("请输入验证码：");
			frame.setVisible(false);
			resultBean.succeeded();
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(e.getMessage());
		} finally {
			assert httpUrl != null;
			httpUrl.disconnect();
			resultBean.end();
		}
		return valiCodeStr;
	}

	/**
	 * 获取拼装后用户登陆地址
	 *
	 * @param hostUrl      登陆主机url地址
	 * @param loginDateMap 登陆页面数据
	 * @param username     用户名
	 * @param password     密码
	 * @param verifyCode   验证码
	 * @return 用户登陆地址
	 */
	public static String findUrl2Login(String hostUrl, Map<String, Object> loginDateMap, String username,
			String password, String verifyCode) {
		EasyResultBean.init(resultBean);
		String loginUrl = null;
		try {
			//编码密码
			password = codingPass(password);
			StringBuilder postStr = new StringBuilder();
			//拼装登陆信息
			postStr.append("?VerifyCode=").append(verifyCode).append("&__VerifyValue=")
					.append(loginDateMap.get("verifyvalue")).append("&__name=").append(username).append("&password=")
					.append(password).append("&isSec=0&cid=").append(loginDateMap.get("cid")).append("&cname=")
					.append(loginDateMap.get("cname")).append("&systemversion=")
					.append(loginDateMap.get("systemversion")).append("&");
			loginUrl = hostUrl + "/loginVerify/.auth" + postStr;
			resultBean.succeeded();
		} catch (Exception e) {
			resultBean.failed();
			resultBean.message(e.getMessage());
		}
		resultBean.end();
		return loginUrl;
	}

	/**
	 * 组装验证code地址
	 *
	 * @param hostUrl 主机url地址
	 * @param key     验证key
	 * @param value   验证value
	 * @return 验证code地址
	 */
	private static String getValiCodeUrl(String hostUrl, String key, Object value) {
		return hostUrl + "/getCodeInfo/.auth?u=" + RandomTool.getDouble() + "&" + key + "=" + value + "&.auth";
	}

	/**
	 * 密码编码便于传输
	 *
	 * @param password 原密码
	 * @return 编码密码
	 */
	private static String codingPass(String password) {
		//符号字符串
		String symbolStr = "'`': '!V$', '-': '!m$', '=': '!k$', '[': '!O$', ']': '!K$', ';': '!I$', '\\'': '!S$', '\\\\': '!T$', '/': '!r$', '.': '!Z$', ',': '!a$', '~': '!i$', '!': '!p$', '@': '!f$', '#': '!7$', '$': '!D$', '%': '!l$', '^': '!9$', '&': '!q$', '*': '!t$', '(': '!6$', ')': '!g$', '_': '!v$', '+': '!J$', '{': '!L$', '}': '!d$', '|': '!W$', '\"': '!E$', ':': '!0$', '?': '!H$', '>': '!y$', '<': '!b$'";
		//符号映射map
		Map<String, Object> symbolMap = new HashMap<>(32);
		String[] symbolStrs = symbolStr.replace("\'", "").split(", ");
		String[] symbolInfo;
		//将符号映射放入map中
		for (String s : symbolStrs) {
			symbolInfo = s.split(": ");
			symbolMap.put(symbolInfo[0].trim(), symbolInfo[1].trim());
		}

		String[] originalPasswordsCharArr = password.split("");
		StringBuilder replacePassword = new StringBuilder();
		//拼装映射后的字符串
		for (String originalPasswordChr : originalPasswordsCharArr) {
			if (symbolMap.get(originalPasswordChr) != null) {
				replacePassword.append(symbolMap.get(originalPasswordChr));
			} else {
				replacePassword.append(originalPasswordChr);
			}
		}
		return String.valueOf(replacePassword);
	}

	/**
	 * 解析获取用户信息数据
	 *
	 * @param result 获取用户信息
	 * @return 解析结果
	 */
	private static JSONObject resolveUserInfo(HttpResult result) throws Exception {
		Object obj = result.getHtml();
		/*
			obj结果例子
				{'data':{'user':{'account':'test_001(A\\u76e4)','credit':'100','re_credit':'100','is_cash':0,
					'total_amount':'0','odds_refresh':10,'game_limit':{'00':[2,20000],'01':[2,20000],
					'02':[2,20000],'03':[2,20000],'04':[2,20000],'05':[2,20000],'06':[2,20000],'07':[2,20000],
					'08':[2,50000],'09':[2,50000],'10':[2,50000],'11':[2,50000],'12':[2,50000],'13':[2,50000],
					'14':[2,50000],'15':[2,10000],'16':[2,10000],'17':[2,50000],'18':[2,5000],'20':[2,5000],
					'21':[2,5000],'23':[2,2000],'24':[2,2000],'25':[2,2000],'29':[2,20000]},'version_number':'2',
					'new_orders':[],'fail_orders':'0'},'success':true},'state':'1','errors':''}êêê
				<script type='text/javascript' charset='utf-8'>
					try {
						document.domain = document.domain.replace('html.', '');
					} catch (e) {
					}
				</script>
		 */
		if (obj == null) {
			throw new Exception("获取个人信息失败");
		}
		String html = (String) obj;
		//获取到第一行返回信息
		html = html.split("\\r?\\n")[0];
		//解码
		html = StringTool.unicode2String(html);
		//规范信息
		html = html.replaceAll("êêê", "");
		return JSONObject.fromObject(html);

	}

	/**
	 * 解析获取游戏投注信息
	 *
	 * @param result 获取游戏投注信息
	 * @return 游戏投注信息
	 */
	private static JSONObject resolveBetInfo(HttpResult result) throws Exception {
		Object obj = result.getHtml();
		/*
			obj结果例子
				{"data":{"betnotice":{"timesold":"2018092155","resultnum":["13","12","20","15","17","19","14","03"],
					"status":1,"timesnow":"2018092156","timeclose":237,"timeopen":327},"oddSet":"A","user_status":1,
					"play":"bothSides","drawStatus":"1","bigDrawStatus":"1","changlong":[["\u7b2c4\u7403","\u864e",
					"4\u671f"],["\u7b2c3\u7403","\u96d9","4\u671f"],["\u7b2c4\u7403","\u55ae","4\u671f"],
					["\u7b2c1\u7403","\u5927","3\u671f"],["\u7b2c2\u7403","\u96d9","3\u671f"],["\u7b2c5\u7403",
					"\u5c3e\u5927","3\u671f"],["\u7b2c3\u7403","\u9f8d","2\u671f"],["\u7b2c1\u7403","\u55ae","2\u671f"],
					["\u7b2c1\u7403","\u5c3e\u5c0f","2\u671f"],["\u7b2c1\u7403","\u5408\u6578\u96d9","2\u671f"],
					["\u7b2c2\u7403","\u5c3e\u5c0f","2\u671f"],["\u7b2c2\u7403","\u5408\u6578\u55ae","2\u671f"],
					["\u7b2c3\u7403","\u5927","2\u671f"],["\u7b2c5\u7403","\u55ae","2\u671f"],["\u7b2c6\u7403",
					"\u55ae","2\u671f"],["\u7b2c6\u7403","\u5c3e\u5927","2\u671f"],["\u7b2c7\u7403",
					"\u5408\u6578\u55ae","2\u671f"],["\u7b2c8\u7403","\u55ae","2\u671f"]],"integrate":{"0350":2.15,"0351":
					1.774,"0360":1.774,"0361":2.15,"0100":1.983,"0101":1.983,"0110":1.983,"0111":1.983,"0120":1.983,"0121":
					1.983,"0130":1.983,"0131":1.983,"0140":1.983,"0141":1.983,"0150":1.983,"0151":1.983,"0160":1.983,"0161":
					1.983,"0170":1.983,"0171":1.983,"0180":1.983,"0181":1.983,"0190":1.983,"0191":1.983,"0200":1.983,"0201":
					1.983,"0210":1.983,"0211":1.983,"0220":1.983,"0221":1.983,"0230":1.983,"0231":1.983,"0240":1.983,"0241":
					1.983,"0250":1.983,"0251":1.983,"0260":1.983,"0261":1.983,"0270":1.983,"0271":1.983,"0280":1.983,"0281":
					1.983,"0290":1.983,"0291":1.983,"0300":1.983,"0301":1.983,"0310":1.983,"0311":1.983,"0320":1.983,"0321":
					1.983,"0330":1.983,"0331":1.983,"0340":1.983,"0341":1.983},"ballqueue_result":[[14,1],[11,1],[12,1],
					[14,1],[5,1],[11,1],[10,1],[13,1],[6,1],[9,1],[15,2],[13,1],[7,1],[13,1],[8,1],[14,1],[13,2],[12,1],
					[17,1],[9,1],[12,1],[6,1],[14,1],[7,1],[10,1]],"win":"0","sys":"pk10","version_number":"7","game_limit":
					{"00":[2,20000],"01":[2,20000],"02":[2,20000],"03":[2,20000],"04":[2,20000],"05":[2,20000],"06":[2,20000],
					"07":[2,20000],"08":[2,20000],"09":[2,20000],"10":[2,50000],"11":[2,50000],"12":[2,50000],"13":[2,50000],
					"14":[2,50000],"15":[2,10000]},"success":true},"state":"1","errors":""}êêê
				<script type="text/javascript" charset="utf-8">
					try {
						document.domain = document.domain.replace("html.", "");
					} catch (e) {
					}
				</script>
		*/
		if (obj == null) {
			throw new Exception("获取游戏投注信息失败");
		}
		String html = (String) obj;
		//获取到第一行返回信息
		html = html.split("\\r?\\n")[0];
		//解码
		html = StringTool.unicode2String(html);
		//规范信息
		html = html.replaceAll("êêê", "");
		return JSONObject.fromObject(html);
	}

	private static JSONObject resolveBetResult(HttpResult result) throws Exception {
		Object obj = result.getHtml();
		/*
			{"data":{"user":{"suc_orders":[["00117418","\u7b2c\u4e09\u540d \u864e|1.983",30,29],["00117413",
				"\u51a0\u8ecd \u5927|1.983",10,9]],"suc_t_amount":40,"suc_zhus":2,"account":"cs1234567(A\u76e4)",
				"credit":"100","re_credit":"60","is_cash":0,"total_amount":"40","odds_refresh":10,
				"game_limit":{"00":[2,20000],"01":[2,20000],"02":[2,20000],"03":[2,20000],"04":[2,20000],"05":[2,20000],
				"06":[2,20000],"07":[2,20000],"08":[2,20000],"09":[2,20000],"10":[2,50000],"11":[2,50000],"12":[2,50000],
				"13":[2,50000],"14":[2,50000],"15":[2,10000]},"version_number":"11",
				"new_orders":[["\u7b2c\u4e09\u540d \u864e",1.983,30,"11:13:30"],
				["\u51a0\u8ecd \u5927",1.983,10,"11:13:30"]],"fail_orders":"0"},"success":true},"state":"1","errors":""}êêê
			<script type="text/javascript" charset="utf-8">
				try {
					document.domain = document.domain.replace("html.", "");
				} catch (e) {
				}
			</script>
		 */
		if (obj == null) {
			throw new Exception("获取结果失败");
		}
		String html = (String) obj;
		//获取到第一行返回信息
		html = html.split("\\r?\\n")[0];
		//解码
		html = StringTool.unicode2String(html);
		//规范信息
		html = html.replaceAll("êêê", "");
		return JSONObject.fromObject(html);

	}

	/**
	 * 获取投注项信息
	 *
	 * @param ballCode 球号信息
	 * @param items    投注项
	 * @param oddInfo  赔率信息
	 * @return 投注项信息
	 */
	public static JSONArray getBetItems(Map<String, String> ballCode, String[][] items, JSONObject oddInfo) {
		JSONArray betItems = new JSONArray();
		for (String[] item : items) {
			JSONObject jObj = new JSONObject();
			jObj.put("item", item[0]);
			jObj.put("amount", item[1]);
			String code = ballCode.get(item[0]);
			jObj.put("betCode", code);
			code = code.replace("|", "");
			jObj.put("oddCode", code);
			jObj.put("odd", oddInfo.getString(code));
			betItems.add(jObj);
		}

		return betItems;

	}
	public static EasyResultBean resultBean(){
		return resultBean;
	}

	//功能函数

}
