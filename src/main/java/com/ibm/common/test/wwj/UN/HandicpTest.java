package com.ibm.common.test.wwj.UN;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.utils.http.tools.agent.UNAgentTool;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
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
	public void testUN() {
		String account = "asas1212";
		String password = "asas12121";
		String handicapUrl = "https://www.aa95.net";
		String handicapCaptcha = "33695";

		HttpClientConfig config = new HttpClientConfig();
		config.httpClient(HttpClientUtil.findInstance().createHttpClient());
		config.httpContext(HttpClientContext.create());
		try {
//			// 获取导航栏验证码
			String code = getVerifyCode1(config,handicapUrl.concat("/captcha_image?d="+System.currentTimeMillis()),"UN","");

			config.setHeader("Accept" ,"application/json, text/javascript, */*; q=0.01");
			config.setHeader("Content-Type" ,"application/json;charset=utf-8");
			Map<String,Object> join = new HashMap<>(1);
			join.put("$ENTITY_STRING$","{\"lineCode\":\""+handicapCaptcha+"\",\"code\":\""+code+"\"}");

			String html = HttpClientUtil.findInstance().postHtml(config.url(handicapUrl.concat("/wb4/websiteLogin")).map(join));
			if(!StringTool.contains(html,"登录成功")){
				System.out.println("登录失败"+html);
				return;

			}
			html = HttpClientUtil.findInstance().getHtml(config.url(handicapUrl.concat("/wb4/index")));
			String[] routers = getMemberrout(config,html);
//			String[] routers = {"https://khwtul.58jxw.com","https://www.bbb76.net/iwm3","khwtul.ppltz.com/iwm3"};
			config.setHeader("Accept" ,"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n");


			HttpResult result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/iwm3")));

			code = getVerifyCode1(config,routers[0].concat("/captcha_image?d="+System.currentTimeMillis()),"UN","");
			Map<String,Object> map = new HashMap<>(10);
			map.put("userName",account);
			map.put("password",password);
			map.put("code",code);
			map.put("btnSubmit","%E7%AB%8B%E5%8D%B3%E7%99%BB%E5%BD%95");
			map.put("gotourl","");
			map.put("login_0521_type","3");
			map.put("language","cn");
			map.put("pubKey","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrZCuRaDnL09yPIbtOBqCPY2PFBecVh/iuPyfkehqgOE4/pUnKCZjnkv9gC+W6phTgZwMXxv51Hzqb2KZCu0b05ag5uThxvLK/+CZB8wcYiyNYogM6Hc7kwJmQ5O4JpBkKuSRUz6OCKdRSnYb2O4w/uut9CnyGt9jDWDhf5iugrQIDAQAB");

			config.setHeader("Accept" ,"*/*");
			config.setHeader("Content-Type" ,"application/x-www-form-urlencoded; charset=UTF-8");


			result = HttpClientUtil.findInstance().post(config.url(routers[0].concat("/login")).map(map));

			// 协议页
			result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/xzym001/m006")));

			// 选择游戏
			result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/wb3/us0001/m017")));

			// 首页 wb3/us0001
			result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/wb3/us0001")));

			// 解析首页
			getMmemberLogin(result.getHtml());

			result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/xzym001/m014/G_3_B_103")));

			// 获取赔率
			//https://khwtul.58jxw.com/wb3/001/m001?b=B_103&g=G_3&auto=0&hType=A&t=1591084182707

			result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/wb3/us0001/personalData?user_id=&t="+System.currentTimeMillis()+"&palyId=G_30")));

			getLimit(result.getHtml());

			result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/wb3/001/m001?b=B_109&g=G_3&auto=0&hType=A&t="+System.currentTimeMillis())));

			// 解析赔率
			getGameOdds(result.getHtml());

			config.setHeader("Accept" ,"application/json, text/javascript, */*; q=0.01");
			config.setHeader("Content-Type" ,"application/json");

			// 投注
			//  https://khwtul.58jxw.com/wb3/001/s001s
//			join = new HashMap<>(1);
//			join.put("$ENTITY_STRING$","{\"betPlays\":\"B_0,B_0\",\"numbers\":\"N_21,N_22\",\"moneys\":\"2,2\",\"rates\":\"1.9822,1.9822\",\"g\":\"G_3\",\"qh\":\"746742\",\"B_TYPE\":\"B_103\",\"H_rate_bh\":\"0\",\"B_hType\":\"A\"}");
//			result = HttpClientUtil.findInstance().post(config.url(routers[0].concat("/wb3/001/s001s")).map(join));
//			System.out.println(result.getHtml());
//			System.out.println(result.getLocation());
//			System.out.println(result.getStatusCode());
			// 下注明细
			// Request URL: https://www.aa95.net/wb3/us0001/m0010?t=1593573515249&palyId=G_3&tg=1
			result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/wb3/us0001/m0010?t=").concat(System.currentTimeMillis()+"&palyId=G_3&tg=1")));
			System.out.println(result.getHtml());
			System.out.println(result.getLocation());
			System.out.println(result.getStatusCode());

			// 解析
			getBetInfo(result.getHtml());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private JSONArray getBetInfo(String html) {
		JSONArray array=new JSONArray();

		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByClass("user_table").first().select("tr");
		elements.remove(elements.first());
		elements.remove(elements.first());
		if(elements.isEmpty()){
			return array;
		}
		for (Element el :elements){
			String ss= StringUtils.substringBetween(el.child(1).text()," ","期");
			String bet = el.getElementsByClass("font_blue").text().replace("【","|").replace("】","");
			array.add(bet.concat("|")+NumberTool.intValueT(el.child(5).text()));
		}
		return array;
	}

	@Test
	public void test02() throws Exception {

		String account = "lkj247";
		String password = "lkj2471";
		String handicapUrl = "https://www.aa95.net";
		String handicapCaptcha = "33695";

		HttpClientConfig config = new HttpClientConfig();
		config.httpClient(HttpClientUtil.findInstance().createHttpClient());
		config.httpContext(HttpClientContext.create());
		try {
//			// 获取导航栏验证码
			String code = getVerifyCode1(config,handicapUrl.concat("/captcha_image?d="+System.currentTimeMillis()),"UN","");

			config.setHeader("Accept" ,"application/json, text/javascript, */*; q=0.01");
			config.setHeader("Content-Type" ,"application/json;charset=utf-8");
			Map<String,Object> join = new HashMap<>(1);
			join.put("$ENTITY_STRING$","{\"lineCode\":\""+handicapCaptcha+"\",\"code\":\""+code+"\"}");

			String html = HttpClientUtil.findInstance().postHtml(config.url(handicapUrl.concat("/wb4/websiteLogin")).map(join));
			if(!StringTool.contains(html,"登录成功")){
				System.out.println("登录失败"+html);
				return;

			}
			html = HttpClientUtil.findInstance().getHtml(config.url(handicapUrl.concat("/wb4/index")));
			String[] routers = getAgent(config,html);
//			String[] routers = {"https://khwtul.58jxw.com","https://www.bbb76.net/iwm3","khwtul.ppltz.com/iwm3"};
			config.setHeader("Accept" ,"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n");


			HttpResult result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/rov2")));

			code = getVerifyCode1(config,routers[0].concat("/captcha_image?d="+System.currentTimeMillis()),"UN","");
			Map<String,Object> map = new HashMap<>(10);
			map.put("userName",account);
			map.put("password",password);
			map.put("code",code);
			map.put("btnSubmit","%E7%AB%8B%E5%8D%B3%E7%99%BB%E5%BD%95");
			map.put("gotourl","");
			map.put("login_0521_type","2");
			map.put("language","cn");
			map.put("pubKey","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrZCuRaDnL09yPIbtOBqCPY2PFBecVh/iuPyfkehqgOE4/pUnKCZjnkv9gC+W6phTgZwMXxv51Hzqb2KZCu0b05ag5uThxvLK/+CZB8wcYiyNYogM6Hc7kwJmQ5O4JpBkKuSRUz6OCKdRSnYb2O4w/uut9CnyGt9jDWDhf5iugrQIDAQAB");

			config.setHeader("Accept" ,"*/*");
			config.setHeader("Content-Type" ,"application/x-www-form-urlencoded; charset=UTF-8");


			result = HttpClientUtil.findInstance().post(config.url(routers[0].concat("/login")).map(map));

			config.setHeader("Accept" ,"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");

			// 加载主页
			result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/wb2/dttj001/daiLiToMain")));

			config.setHeader("Accept" ,"application/json, text/javascript, */*; q=0.01");
			config.setHeader("Content-Type" ,"application/json;charset=utf-8");

			// 会员列表 Request URL: https://khwtul.ppltz.com/wb2/hy0001hy/s001s
			join = new HashMap<>(1);
			join.put("$ENTITY_STRING$","{\"targetpage\":\"1\",\"state\":null,\"username\":\"\",\"utypes\":\"6\",\"sjUsername\":\"ww8877\",\"user_ip\":\"\",\"opType\":\"0\",\"desc_skxy\":\"0\",\"reback_tag\":\"0\",\"is_system\":\"1\"}");
			result = HttpClientUtil.findInstance().post(config.url(routers[0].concat("/wb2/hy0001hy/s001s")).map(join));
			System.out.println(result.getHtml());
			JSONObject poList = JSONObject.parseObject(result.getHtml());
			JSONArray memberArray = new JSONArray();
			JSONArray agentInfoPoList = poList.getJSONArray("agentInfoPoList");
			for (int i = 0; i < agentInfoPoList.size(); i++) {
				JSONObject agentInfo = agentInfoPoList.getJSONObject(i);
				memberArray.add(agentInfo.getInteger("io")==1 ? "online" : "offline");
				memberArray.add(agentInfo.getString("username"));
			}
			int totalPage = poList.getInteger("totalPage");
			if(totalPage>1){
				for (int i = 1; i <=totalPage ; i++) {
					String stringEntity =  "{\"targetpage\":\"%s\",\"state\":null,\"username\":\"\",\"utypes\":\"6\",\"sjUsername\":%s,\"user_ip\":\"\",\"opType\":\"0\",\"desc_skxy\":\"0\",\"reback_tag\":\"0\",\"is_system\":\"1\"}";
					join = new HashMap<>(1);
					join.put("$ENTITY_STRING$",String.format(stringEntity,i+1, "ww8877"));
					result = HttpClientUtil.findInstance().post(config.url(routers[0].concat("/wb2/hy0001hy/s001s")).map(join));
					poList = JSONObject.parseObject(result.getHtml());
					agentInfoPoList = poList.getJSONArray("agentInfoPoList");
					for (int j = 0; j < agentInfoPoList.size(); j++) {
						JSONObject agentInfo = agentInfoPoList.getJSONObject(j);
						memberArray.add(agentInfo.getInteger("io")==1 ? "online" : "offline");
						memberArray.add(agentInfo.getString("username"));
					}
				}
			}
			System.out.println(memberArray);


			// KLC 广东  SSC 重庆 BJC 北京
			// 投注摘要
			// Request URL: https://khwtul.ppltz.com/wb2/b1001/m001?gid=BJC&iss=745522&status=0&startTime=2020-06-03&endTime=2020-06-03&ziOrsj=0&uid=&uType=&orderType=&t01=1591178239095&userName=
			result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/wb2/b1001/m001?gid=BJC&iss=746745&status=0&startTime=2020-07-01&endTime=2020-07-01&ziOrsj=0&uid=&uType=&orderType=&t01=1593585700529&userName=")));
			System.out.println(result);
			// 解析摘要页
			Map<String, SummaryInfo> member = getBetSummary(result.getHtml());

			// 明细
			// Request URL: https://khwtul.ppltz.com/
			result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/wb2/b1001/m003?gid=BJC&iss=746745&status=0&startTime=2020-07-01&endTime=2020-07-01&uid=asas1212 &orderDate=2020-07-01&targetpage1=1&ziOrsj=0&gameId_old=BJC&uType=&orderType=&recordCount=undefined&hyName=&timeSep=1593585729257")));
			// 解析明细

			DetailBox betDetail = UNAgentTool.analyzeDetail(null, result.getHtml(), "");

			//循环所有的页码
			while (betDetail.hasNext()) {
				result = HttpClientUtil.findInstance().get(config.url(routers[0].concat("/wb2/b1001/m003?gid=BJC&iss=746745&status=0&startTime=2020-07-01&endTime=2020-07-01&uid=asas1212 &orderDate=2020-07-01&targetpage1="+betDetail.getPages().nextPage()+"&ziOrsj=0&gameId_old=BJC&uType=&orderType=&recordCount=undefined&hyName=&timeSep=1593585729257")));

				html = result.getHtml();
				// 没有找到 -数据弹出
				betDetail = UNAgentTool.analyzeDetail(betDetail, html, "");
				if (betDetail == null) {
					break;
				}
			}


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


	}

	private void getBetDetail(String html) {
		Document document = Jsoup.parse(html);
		Elements trs = document.getElementsByClass("clear_table comtable tabletrhover").select("tr");
		trs.remove(trs.first());
		trs.remove(trs.last());

	}

	private  Map<String, SummaryInfo>  getBetSummary(String html) {
		Document document = Jsoup.parse(html);
		Elements trs = document.getElementById("deliveryReport_tbody").select("tr");
		trs.remove(trs.first());
		trs.remove(trs.last());
		//
		Map<String, SummaryInfo> member = new HashMap<>(trs.size() * 3 / 4 + 1);
		for (Element tr : trs){
			SummaryInfo summaryInfo = new SummaryInfo();
			summaryInfo.setAccount(tr.child(1).text());
			summaryInfo.setBetCount(tr.child(3).text());
			summaryInfo.setBetAmount(NumberTool.intValueT(tr.child(4).text())/1000);
			member.put(tr.child(1).text(), summaryInfo);
		}
		System.out.println(member);
		return member;
	}

	/**
	 * 解析游戏限额页面
	 *
	 * @param html 游戏限额页面
	 * @return 游戏限额
	 */
	private JSONArray getLimit(String html) {
		Document document = Jsoup.parse(html);

		Element element = document.getElementsByClass("user_table").last();
		Elements trs = element.select("tr");
		trs.remove(0);
		JSONArray quota = new JSONArray();
		JSONArray array;
		for (Element tr : trs) {
			array = new JSONArray();
			array.add(tr.child(2).text());
			array.add(tr.child(3).text());
			array.add(tr.child(4).text());
			quota.add(array);
		}
		return quota;
	}

	private void getGameOdds(String html) {
		JSONObject jsonObject = JSONObject.parseObject(html);
		JSONObject odds= (JSONObject) jsonObject.get("result");
		System.out.println(odds);

	}

	private void getMmemberLogin(String html) {
		Document document = Jsoup.parse(html);
		Element element = document.getElementsByClass("info_table").first();
		String userName = document.getElementById("user_name").html();
		String pk = document.getElementById("pk").text();
		//可用额度
		String avacredits = document.getElementById("avacredits").val();
		// 下注金额
		String yixia_money = document.getElementById("yixia_money").html();
		//今日输赢
		String daty_sy = document.getElementById("daty_sy").html();
		System.out.println();

	}


	private String[] getAgent(HttpClientConfig httpConfig, String html) {
		Document document = Jsoup.parse(html);
		Elements as = document.select("a");
		List<String> hrefs = new ArrayList<>();

		for (Element el : as) {
			if (StringTool.contains(el.text(), "管理线路")) {
				String on = el.attr("onclick");
				String url = StringUtils.substringBetween(on,"goToUrlDL('", "/");
				hrefs.add("https://".concat(url));
			}
		}
		String[] routes = new String[hrefs.size()];
		long[] arr = new long[hrefs.size()];

		//判断时间延迟 public/check.htm?d=0.7056380597312475
		long t1, t2;
		for (int i = 0; i < hrefs.size(); i++) {
			t1 = System.currentTimeMillis();
			String href = hrefs.get(i);

			try {
				HttpClientUtil.findInstance().getHtml(httpConfig.url(href));
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


	private String[] getMemberrout(HttpClientConfig httpConfig, String html) {
		Document document = Jsoup.parse(html);
		Elements as = document.select("a");
		List<String> hrefs = new ArrayList<>();

		for (Element el : as) {
			if (StringTool.contains(el.text(), "会员线路")) {
				String on = el.attr("onclick");
				String url = StringUtils.substringBetween(on,"goToUrl('", "/");
				hrefs.add("https://".concat(url));
			}
		}
		String[] routes = new String[hrefs.size()];
		long[] arr = new long[hrefs.size()];

		//判断时间延迟 public/check.htm?d=0.7056380597312475
		long t1, t2;
		for (int i = 0; i < hrefs.size(); i++) {
			t1 = System.currentTimeMillis();
			String href = hrefs.get(i);

			try {
				HttpClientUtil.findInstance().getHtml(httpConfig.url(href));
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
//			System.out.println("获取验证码失败");
		}
		if (StringTool.isEmpty(html)) {
			return getVerifyCode1(httpConfig, projectHost, type, content);
		}
		if (StringTool.isEmpty(html)) {
			System.out.println("获取验证码失败");
		}
		html = html.replace("\"", "");
		if(html.length()!=4){
			return getVerifyCode1(httpConfig, projectHost, type, content);
		}
		return html;
	}


}
