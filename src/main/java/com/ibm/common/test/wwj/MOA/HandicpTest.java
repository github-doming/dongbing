package com.ibm.common.test.wwj.MOA;

import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.agent.NewMoaAgentTool;
import com.ibm.common.utils.http.utils.RSAUtils;
import com.ibm.common.utils.http.utils.js.ScriptEngineManagerTool;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
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
import sun.management.resources.agent;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;


public class HandicpTest {


	@Test
	public void test02() throws Exception {



	}


	@Test
	public void test01() throws Exception {


	}

	@Test
	public void testMOA1() {
		String account = "asas1111";
		String password = "asas121212";
		String handicapUrl = "http://ak3.qkqk666.com";
		String handicapCaptcha = "2218";

		HttpClientConfig config = new HttpClientConfig();
		config.httpClient(HttpClientUtil.findInstance().createHttpClient());
		config.httpContext(HttpClientContext.create());
		try {
			// 加载导航栏页面

			// 获取导航栏验证码
//			String code = getVerifyCode1(config,handicapUrl.concat("/attachImage.jsp?t="+System.currentTimeMillis()),"NewMOANav","");
//			// 验证安全码
//
			Map<String, Object> map = new HashMap<>(20);
//			map.put("attach", handicapCaptcha);
//			map.put("sys_attach", code);
//			String navigationHtml = HttpClientUtil.findInstance().postHtml(config.url(handicapUrl.concat("/loginAction.do")).map(map));
//			System.out.println(navigationHtml);
//
//			// 解析获取线路 [http://3m.nmlfx.com/, https://ka4.nmlfx.com/, https://hw10m.nmlfx.com/, http://5m.nmlfx.com/, https://dgd114.nmlfx.com/]
//			String[] router = getMemberrout(config,navigationHtml);
			String[] router = {"http://3m36.gxgfjy.com/"};
			System.out.println(Arrays.toString(router));

			map.clear();
			// 获取登录验证码

			// ecb85c41498fd140c67e9e8d4239163e2335e96da84dbd99ec22e222b7fe95e43ffb3c3ac1c6672361e55700736660ed1f0870b355cfa103b5f52977b4b6a204a8f54423e204e672c2a8e0dfc63a648debb28fab2b41509a62f279ab40bc2e5e5e75c97cee2feb2f717635fbf7de2e81b5639dbfe3acc2ffaa2a6b55ca78ee89
			String html = HttpClientUtil.findInstance().getHtml(config.url(router[0].concat("100")));
			String publicKey = StringUtils.substringBetween(html, "10001\", \"\", \"", "\");");
			String rsaPwd = ScriptEngineManagerTool.findInstance().getRSAPassword(publicKey, password);


			String code = getVerifyCode1(config, router[0].concat("public/attachImage.do?t=" + System.currentTimeMillis()), "NewMOA", "");

			map.put("page_type", "1");
			map.put("login_page_name", "LG_A");
			map.put("offsetHeight", "");
			map.put("offsetWidth", "");
			map.put("request_locale", "zh_TW");
			map.put("txt_pass", "");

			map.put("user_id", account);
			map.put("attach", code);
			map.put("password", rsaPwd);


			HttpResult result = HttpClientUtil.findInstance().post(config.url(router[0].concat("userLoginAction.do")).map(map));
			System.out.println(result.getHtml());
			System.out.println(result.getLocation());
			System.out.println(result.getStatusCode());


			// 会员列表
			result = HttpClientUtil.findInstance().get(config.url(router[0].concat("userparam/user_manage.do?leader_type=80&user_type=90&type=0")));
			System.out.println(result.getHtml());
			System.out.println(result.getLocation());
			System.out.println(result.getStatusCode());
			// 未结算摘要

			Map<String, Object> join = new HashMap<>(16);
			join.put("q_game_type", "B");
			join.put("q_pro_type", "-1");
			join.put("q_by_dateOrIssue", "BY_ISSUE");
			join.put("q_issue_id", "746754");
			join.put("q_start_date","");
			join.put("q_end_date", "");
			join.put("q_report_type", "U");
			join.put("q_is_settled", "0");
			join.put("q_user_id", "");
			join.put("gameType", "B");
			join.put("proType", "-1");
			join.put("query_cond_type", "0");
			join.put("issueId", "-1");
			join.put("q_FT", "746754");
			join.put("contain_today_check","1");
			join.put("contain_today", "1");
			join.put("ReportType", "U");
			join.put("t_Balance", "0");

			html = HttpClientUtil.findInstance().postHtml(config.url(router[0].concat("report/report_001_user.do")).map(join));


			// 页面解析
			Map<String, SummaryInfo> member = getBetSummary(html);
			// 未结算明细
			//https://ehka.gxgfjy.com/report/order_detail.do?v=96&is_report=1&is_ext=0&user_id=eded1516&game_type=B&pro_type=-1&issue_id=746748&start_date=&end_date=&is_settled=0&report_type=U&by_dateOrIssue=BY_ISSUE&contain_today=1
			html = HttpClientUtil.findInstance().getHtml(config.url(router[0].concat("report/order_detail.do?v=253&is_report=1&is_ext=0&user_id=eded1516&game_type=B&pro_type=-1&issue_id=746753&start_date=&end_date=&is_settled=0&report_type=U&by_dateOrIssue=BY_ISSUE&contain_today=1")));
			// 解析

			DetailBox betDetail = NewMoaAgentTool.analyzeDetail(null, GameUtil.Code.PK10, html, "");
			if (betDetail == null) {
				System.out.println("11111111111");
			}
			//循环所有的页码
			// =0&=&=1&game_type=B&issue_id=746754&pro_type=-1&start_date=&end_date=&is_settled=0&report_type=U&by_dateOrIssue=BY_ISSUE&filter_date=&contain_today=1&curPage=1&pageCount=20&totalPage=2&totalCount=40&pageMaxCount=30&limitMaxCount=0
			while (betDetail.hasNext()) {
				join = new HashMap<>();
				join.put("is_ext",0);
				join.put("user_id","eded1516");
				join.put("is_report","1");
				join.put("game_type","B");
				join.put("issue_id","746754");
				join.put("pro_type","-1");
				join.put("start_date","");
				join.put("end_date","");
				join.put("is_settled",0);
				join.put("report_type","U");
				join.put("by_dateOrIssue","BY_ISSUE");
				join.put("filter_date","");
				join.put("contain_today",1);
				join.put("curPage",betDetail.getPages().nextPage());
				join.put("pageCount",20);
				join.put("totalPage",2);
				join.put("totalCount",40);
				join.put("pageMaxCount",30);
				join.put("limitMaxCount",0);
				//Request URL: https://ehka.gxgfjy.com/report/order_detail.do
				html = HttpClientUtil.findInstance().postHtml(config.url(router[0].concat("report/order_detail.do")).map(join));

				// 没有找到 -数据弹出
				betDetail = NewMoaAgentTool.analyzeDetail(betDetail, GameUtil.Code.PK10, html, "");
				if (betDetail == null) {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void get(String html) {
		Document document = Jsoup.parse(html);

		// <label id="page_tip">共27條 第1/2 頁 </label>
		String totalPage = document.getElementById("totalPage").val();
		Elements trs = document.getElementsByClass("t_list_tr_0");
		for(Element tr:trs){

		}


	}

	/**
	 * 未结算摘要
	 *
	 * @param html 未结算摘要页面
	 * @return 未结算摘要
	 */
	private static Map<String, SummaryInfo> getBetSummary(String html) {
		Document document = Jsoup.parse(html);
		Elements trs = document.getElementsByClass("t_list_tr_0");
		trs.remove(trs.last());

		Map<String, SummaryInfo> member = new HashMap<>();
		for (Element tr :trs){
			SummaryInfo summaryInfo = new SummaryInfo();
			String memberName = tr.child(0).text().split(" ")[1];
			summaryInfo.setMemberId(memberName);
			summaryInfo.setAccount(memberName);
			summaryInfo.setBetCount(tr.child(2).text());
			summaryInfo.setBetAmount(NumberTool.intValueT(tr.child(3).text()) / 1000);
			member.put(memberName, summaryInfo);
		}

		return member;
	}

	@Test
	public void testMOA() {
		String account = "llmm1122";
		String password = "llmm112233";
		String handicapUrl = "http://bw1.hxqdzp.com";
		String handicapCaptcha = "4822";

		HttpClientConfig config = new HttpClientConfig();
		config.httpClient(HttpClientUtil.findInstance().createHttpClient());
		config.httpContext(HttpClientContext.create());
		try {
			// 加载导航栏页面

			// 获取导航栏验证码
//			String code = getVerifyCode1(config,handicapUrl.concat("/attachImage.jsp?t="+System.currentTimeMillis()),"NewMOANav","");
//			// 验证安全码
//
			Map<String, Object> map = new HashMap<>(20);
//			map.put("attach", handicapCaptcha);
//			map.put("sys_attach", code);
//			String navigationHtml = HttpClientUtil.findInstance().postHtml(config.url(handicapUrl.concat("/loginAction.do")).map(map));
//			System.out.println(navigationHtml);
//
//			// 解析获取线路
//			String[] router = getMemberrout(config,navigationHtml);
			String[] router = {"https://msam4822.productbang.com/"};
			System.out.println(Arrays.toString(router));

			map.clear();
			// 获取登录验证码

			String html = HttpClientUtil.findInstance().getHtml(config.url(router[0]));
			String publicKey = StringUtils.substringBetween(html, "10001\", \"\", \"", "\");");


			String rsaPwd = ScriptEngineManagerTool.findInstance().getRSAPassword(publicKey, password);

			String code = getVerifyCode1(config, router[0].concat("public/attachImage.do?t=" + System.currentTimeMillis()), "NewMOA", "");

			map.put("page_type", "1");
			map.put("login_page_name", "LG");
			map.put("offsetHeight", "");
			map.put("offsetWidth", "");
			map.put("request_locale", "zh_TW");
			map.put("txt_pass", "");

			map.put("user_id", account);
			map.put("attach", code);
			map.put("password", rsaPwd);


			HttpResult result = HttpClientUtil.findInstance().post(config.url(router[0].concat("userLoginAction.do")).map(map));
			System.out.println(result.getHtml());
			System.out.println(result.getLocation());
			System.out.println(result.getStatusCode());

			// 协议 https://msam4822.productbang.com/member3/member_index.do
			result = HttpClientUtil.findInstance().get(config.url(router[0].concat("member3/member_index.do")).map(map));
			System.out.println(result.getHtml());
			System.out.println(result.getLocation());
			System.out.println(result.getStatusCode());

			// 解析首页
			Map<String, Object> info = getMemInfo(result.getHtml());

			//赔率 Request URL: https://msam4822.productbang.com/member3/game/get_rate_data.do
//			oper_type: GET_ZH_RATE
//			game_type:B
//			issue_id: 745828
//			user_set: A
//			ball_idx: 10
//			is_detail_rate:1
			// GD 8 cq 4 pk 10
			map.clear();
			map.put("oper_type", "GET_ZH_RATE");
			map.put("game_type", "B");
			map.put("issue_id", "745907");
			map.put("user_set", "A");
			map.put("ball_idx", "10");
			map.put("is_detail_rate", "1");

			config.setHeader("Accept", ": text/html, */*; q=0.01");

			result = HttpClientUtil.findInstance().post(config.url(router[0].concat("member3/game/get_rate_data.do")).map(map));
			System.out.println(result.getHtml());
			System.out.println(result.getLocation());
			System.out.println(result.getStatusCode());
			String[] strArr = result.getHtml().split("##");


			// 投注
			// oper_type: ORDER
			//game_type: B
			//issue_id: 745906
			//order_info: DX;21;1.988;2;0;;21;
			//order_attach: 503597
			// http://ab77.productbang.com/member3/game/orderServlet
			Map<String, Object> bets = new HashMap<>(6);
			bets.put("TRACK_TYPE", "");
			bets.put("oper_type", "ORDER");
			bets.put("game_type", "B");
			bets.put("issue_id", "745908");
			bets.put("order_attach", RandomTool.getInt(1000) + "" + RandomTool.getInt(1000));
			bets.put("order_info", "H12;13;10.2;2;0;;13;@H12;14;13.8;2;0;;14;");

			result = HttpClientUtil.findInstance().post(config.url(router[0].concat("member3/game/orderServlet")).map(bets));
			System.out.println(result.getHtml());
			System.out.println(result.getLocation());
			System.out.println(result.getStatusCode());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private Map<String, Object> getMemInfo(String html) {
		Document document = Jsoup.parse(html);
		Map<String, Object> info = new HashMap<>();
		String account = document.getElementById("td_user_set").text();
		String ableAmount = document.getElementById("td_able_amount").text();
		String frozenAmount = document.getElementById("td_frozen_amount").text();
		String surplus = document.getElementById("td_surplus").text();
		info.put("account", account);
		info.put("ableAmount", ableAmount);
		info.put("surplus", surplus);
		info.put("frozenAmount", frozenAmount);
		return info;
	}


	private String[] getMemberrout(HttpClientConfig httpConfig, String navigationHtml) {
		Document document = Jsoup.parse(navigationHtml);
		Elements as = document.select("a");
		List<String> hrefs = new ArrayList<>();

		for (Element a : as) {
			if (StringTool.contains(a.text(), "会")) {
				String on = a.attr("onclick");
				String url = on.substring(on.indexOf("http"), on.indexOf("')"));
				hrefs.add(url);
			}
		}
		String[] routes = new String[hrefs.size()];
		long[] arr = new long[hrefs.size()];

		//判断时间延迟 public/check.htm?d=0.7056380597312475
		long t1, t2;
		for (int i = 0; i < hrefs.size(); i++) {
			t1 = System.currentTimeMillis();
			String href = hrefs.get(i);
			String url = href.concat("public/check.htm?d=")
					.concat(Integer.toHexString((int) Math.floor((1 + Math.random()) * 0x10000)));
			try {
//				HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
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

			content = HttpClientUtil.findInstance()
					.getImage(httpConfig.url(projectHost));
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
		String html = null;

		try {
			html = HttpClientTool.doPost(CRACK_CONTENT, join);
		} catch (Exception e) {
			System.out.println("获取验证码失败");
		}
		if (StringTool.isEmpty(html)) {
			return getVerifyCode1(httpConfig, projectHost, type, "");
		}
		if (StringTool.isEmpty(html)) {
			System.out.println("获取验证码失败");
		}
		html = html.replace("\"", "");

		return html;
	}


}


