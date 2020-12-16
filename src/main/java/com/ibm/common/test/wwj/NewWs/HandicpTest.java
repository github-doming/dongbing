package com.ibm.common.test.wwj.NewWs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HandicpTest {

	@Test
	public void testNewWs() {
		String account = "qq1122";
		String password = "Qw12121212";
		String handicapUrl = "http://at.a9t9.org/";
		String handicapCaptcha = "7t7t69";

		HttpClientConfig config = new HttpClientConfig();
		config.httpClient(HttpClientUtil.findInstance().createHttpClient());
		config.httpContext(HttpClientContext.create());
		try {

			Map<String,Object> join = new HashMap<>(1);

			join.put("codeY",handicapCaptcha);
			join.put("submit_bt","搜索");
			// Request URL: http://at.a9t9.org/hmclient/sys/line/lportal.do?d=

			String html = HttpClientUtil.findInstance().postHtml(config.url(handicapUrl.concat("/hmclient/sys/line/lportal.do?d=")).map(join));
			Map<String,Object> loginParam = new HashMap();
			loginParam.put("codeY",handicapCaptcha);
			loginParam.put("snn",StringUtils.substringBetween(handicapUrl,"//","/"));

			getMemberrout(config,html,loginParam);
			String[] routers = {"http://uc2-567.av9av9.com:217/hmclient"};

			join = new HashMap<>(10);
			join.put("codeY",handicapCaptcha);
			join.put("snn",loginParam.get("snn"));
			join.put("d","");
			join.put("uid","");
			join.put("ticket","");
			// 登录
			// 获取 ticketl数据
			html = HttpClientUtil.findInstance().postHtml(config.url(routers[0].concat("/f/login_new.jsp?")).map(join));
			System.out.println(html);
			String ticketl= StringUtils.substringBetween(html,"ticketL=\"","\";");
			System.out.println(ticketl);

			String code = getVerifyCode1(config,routers[0].concat("/user/getValidateCodeF.do?t="+System.currentTimeMillis()+"&ticket="+ticketl),"NewWs","");


			join = new HashMap<>(10);
			join.put("codeY",handicapCaptcha);
			join.put("snn",loginParam.get("snn"));
			join.put("zhanghao",account);
			join.put("mima", Md5Tool.md5Hex(password));
			join.put("validateCode",code);
			join.put("ticket",ticketl);
			html = HttpClientUtil.findInstance().postHtml(config.url(routers[0].concat("/user/loginf.do")).map(join));
			System.out.println(html);
			JSONObject result = JSONObject.parseObject(html);
			int memberId = result.getJSONObject("dataObject").getInteger("id");
			ticketl = result.getJSONObject("dataObject").getString("ticket");

			config.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
			config.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			join = new HashMap<>(10);
			join.put("uid",memberId);
			join.put("ticket",ticketl);
			join.put("gameCode","1_7");
			join.put("code",36);
			html = HttpClientUtil.findInstance().postHtml(config.url(routers[0].concat("/game/bet/loadBet.do")).map(join));
			System.out.println(html);
			//获取用户信息
			// Request URL: http://uc2-567.av9av9.com:217/hmclient/user/query/querySelfLeft.do
			join = new HashMap<>(10);
			join.put("uid",memberId);
			join.put("ticket",ticketl);
			html = HttpClientUtil.findInstance().postHtml(config.url(routers[0].concat("/user/query/querySelfLeft.do")).map(join));
			System.out.println(html);
			JSONObject jsonObject = JSONObject.parseObject(html);
			JSONArray info = jsonObject.getJSONArray("dataObject");
			System.out.println(info.get(2));
			System.out.println(info.getString(2));

			join = new HashMap<>(10);
			join.put("uid",memberId);
			join.put("ticket",ticketl);
			join.put("gameCode","1_7");
			html = HttpClientUtil.findInstance().postHtml(config.url(routers[0].concat("/user/query/listSelfWithIntime.do")).map(join));
			System.out.println(html);
			getLimit(html,"1_7");


			// 获取赔率 gameCode=1_7&code=36&ticket=72852ba1-812c-4944-a052-e27eb391bbc1&uid=91052
			// Request URL: http://uc2-567.av9av9.com:217/hmclient/game/bet/loadBet.do

			// 投注 Request URL: http://uc2-567.av9av9.com:217/hmclient/game/gamble.do
			// gambleString: {"zds":[{"seqNumber":"20200605113","ruleCode":"1_7_3_11","odds":1.9831,"amount":"2"},
			// {"seqNumber":"20200605113","ruleCode":"1_7_3_12","odds":1.9831,"amount":"2"}]}
			//ticket: 72852ba1-812c-4944-a052-e27eb391bbc1
			//uid: 91052
			join = new HashMap<>(10);
			join.put("uid",memberId);
			join.put("ticket",ticketl);
			Map<String,Object> gambleString = new HashMap<>();
			JSONArray arr = new JSONArray();
			Map<String,Object> gamble= new HashMap<>();
			gamble.put("seqNumber","20200608017");
			gamble.put("ruleCode","1_7_3_11");
			gamble.put("odds",1.9831);
			gamble.put("amount","2");

			arr.add(gamble);
			gambleString.put("zds",arr);
			join.put("gambleString",gambleString);

			html = HttpClientUtil.findInstance().postHtml(config.url(routers[0].concat("/game/gamble.do")).map(join));
			System.out.println(html);




		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * 解析游戏限额页面
	 *
	 * @param html 游戏限额页面
	 * @return 游戏限额
	 */
	private static JSONArray getLimit(String html,String gameCode) {
		JSONObject jsonObject = JSONObject.parseObject(html);
		JSONObject limitInfo = jsonObject.getJSONArray("dataObject").getJSONObject(5);
		JSONArray quota = new JSONArray();
		JSONArray array;
		for (int i = 0; i <limitInfo.size() ; i++) {
			array = limitInfo.getJSONArray(gameCode+"_"+(i+1));
			array.remove(5);
			array.remove(4);
			array.remove(3);
			quota.add(array);
		}

		return quota;
	}


	private String[] getMemberrout(HttpClientConfig httpConfig, String html,Map<String,Object> param) {

		List<String> hrefs = new ArrayList<>();
		Document document = Jsoup.parse(html);
		Elements as = document.getElementsByClass("datalist-contain datalist").select("a");

		for (int i = 0; i <4 ; i++) {
			hrefs.add(as.get(i).attr("name"));
		}
		String[] routes = new String[hrefs.size()];
		long[] arr = new long[hrefs.size()];

		//判断时间延迟 public/check.htm?d=0.7056380597312475
		long t1, t2;
		for (int i = 0; i < hrefs.size(); i++) {
			t1 = System.currentTimeMillis();
			String href = hrefs.get(i);
			String url = href.concat("/speed.gif?"+System.currentTimeMillis());
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
	 * 获取验证码 Request URL: http://ak3.qkqk666.com/attachImage.jsp?t=1590645217694
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param code        验证码地址
	 * @return 验证码
	 */
	public static String getVerifyCode1(HttpClientConfig httpConfig, String projectHost, String type, String code) {

		String content;
		//获取验证码内容,code等于空时，刷新验证码
		if (StringTool.isEmpty(code)) {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost));
		} else {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
		}
		if (StringTool.isEmpty(content)) {
			System.out.println("获取验证码失败");

		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("type", type);
		join.put("content", content);
		String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";
//		String CRACK_CONTENT = "http://192.168.14.100:60537/Code/GetVerifyCodeFromContent";
		String html = null;

		try {
			html = HttpClientTool.doPost(CRACK_CONTENT, join);
		} catch (Exception e) {
			System.out.println("获取验证码失败");
		}
		if (StringTool.isEmpty(html)) {
			return getVerifyCode1(httpConfig, projectHost, type, content);
		}
		if (StringTool.isEmpty(html)) {
			System.out.println("获取验证码失败");
		}
		html = html.replace("\"", "");
		if(html.length()!=5){
			return getVerifyCode1(httpConfig, projectHost, type, content);
		}
		return html;
	}


}
