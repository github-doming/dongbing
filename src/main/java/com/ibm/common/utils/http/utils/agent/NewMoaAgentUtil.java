package com.ibm.common.utils.http.utils.agent;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.NewMoaConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.agent.NewMoaAgentTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import com.ibm.common.utils.http.utils.js.ScriptEngineManagerTool;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: NewWs代理工具类
 * @Author: wwj
 * @Date: 2020/5/14 11:29
 * @Version: v1.0
 */
public class NewMoaAgentUtil extends BaseAgentUtil {
	private static volatile NewMoaAgentUtil instance = null;

	private HandicapUtil.Code handicapCode = HandicapUtil.Code.NEWMOA;

	public static NewMoaAgentUtil findInstance() {
		if (instance == null) {
			synchronized (NewMoaAgentUtil.class) {
				if (instance == null) {
					NewMoaAgentUtil instance = new NewMoaAgentUtil();
					// 初始化
					instance.init();
					NewMoaAgentUtil.instance = instance;
				}
			}
		}
		return instance;
	}

	/**
	 * 销毁工厂
	 */
	public static void destroy() {
		if (instance == null) {
			return;
		}
		if (ContainerTool.notEmpty(instance.agentCrawlers)) {
			for (AgentCrawler agentCrawler : instance.agentCrawlers.values()) {
				agentCrawler.getHcConfig().destroy();
			}
		}
		instance.agentCrawlers = null;
		instance = null;
	}

	/**
	 * 登陆
	 *
	 * @param existHaId       已存在盘口代理id
	 * @param agentAccount    代理账号
	 * @param agentPassword   代理密码
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @return 登录结果
	 */
	@Override
	public JsonResultBeanPlus login(String existHaId, String agentAccount, String agentPassword, String handicapUrl, String handicapCaptcha) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//已存在数据
		AgentCrawler agent;
		if (agentCrawlers.containsKey(existHaId)) {
			agent = agentCrawlers.get(existHaId);
			if (StringTool.notEmpty(agent.getProjectHost())) {
				bean.setData(agent.getProjectHost());
				bean.success();
				return bean;
			}
		} else {
			agent = new AgentCrawler();
		}
		AccountInfo accountInfo = agent.getAccountInfo();
		accountInfo.setItemInfo(agentAccount, agentPassword, handicapUrl, handicapCaptcha);
		try {
			//获取配置类
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(agent);

			bean = login(httpConfig, accountInfo);
			if (!bean.isSuccess()) {
				return bean;
			}
			Map<String, Object> data = (Map<String, Object>) bean.getData();
			agent.setProjectHost(data.get("projectHost"));
			if (!agentCrawlers.containsKey(existHaId)) {
				agentCrawlers.put(existHaId, agent);
			}
		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHaId, "登录失败,失败原因为：" + e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 登录
	 *
	 * @param httpConfig  htpp请求配置类
	 * @param accountInfo 账号信息对象
	 * @return
	 */
	@Override
	public JsonResultBeanPlus login(HttpClientConfig httpConfig, AccountInfo accountInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapUrl = accountInfo.getHandicapUrl();
		if (!handicapUrl.endsWith("/")) {
			handicapUrl = handicapUrl.concat("/");
		}
		httpConfig.headers(null);
		httpConfig.httpContext(null);
		try {
//获取验证码
			String verifyCode = NewMoaAgentTool.getVerifyCode(httpConfig, handicapUrl,null, "NewMOANav");
			//获取线路选择页面
			String routeHtml = NewMoaAgentTool.getSelectRoutePage(httpConfig, handicapUrl, verifyCode,accountInfo.getHandicapCaptcha());
			if (StringTool.isEmpty(routeHtml) ) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取线路页面失败" + routeHtml);
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			// 代理线路数组
			String[] routes = NewMoaAgentTool.getAgentRoute(httpConfig, routeHtml);
//			String[] routes = {"http://3m4822.productbang.com/"};
			if (ContainerTool.isEmpty(routes)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			// 获取RAS加密公钥
			Map<String, String> loginInfoMap = NewMoaAgentTool.getLoginPublicKey(httpConfig, routes);
			if (ContainerTool.isEmpty(loginInfoMap)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			String loginSrc = loginInfoMap.get("loginSrc");
			String publicKey = loginInfoMap.get("publicKey");
			// 登录密码进行加密
			String rsaPassword = ScriptEngineManagerTool.findInstance().getRSAPassword(publicKey, accountInfo.getPassword());

			verifyCode = NewMoaAgentTool.getVerifyCode(httpConfig, loginSrc, null, "NewMOA");
			//登录
			String loginInfo = NewMoaAgentTool.getLogin(httpConfig, accountInfo.getAccount(), rsaPassword, verifyCode, loginSrc);
			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			//错误处理
			if (StringTool.notEmpty(loginInfo) && !StringTool.contains(loginInfo, "此网页使用了框架，但您的浏览器不支持框架")) {
				log.error(message, handicapCode.name(), accountInfo.getAccount(), "登录的URL=" + loginSrc);
				bean.putEnum(NewMoaAgentTool.loginError(loginInfo));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			Map<String, String> userData = new HashMap<>(5);
			userData.put("projectHost", loginSrc);

			bean.success(userData);
		} catch (Exception e) {
			log.error(message, handicapCode.name(), accountInfo.getAccount(), "登录失败,失败原因为：" + e);
			bean.error(e.getMessage());
		} finally {
			httpConfig.defTimeOut();
		}
		return bean;
	}

	@Override
	public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String agentAccount, String agentPassword) {
		// 获取配置类
		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setItemInfo(agentAccount, agentPassword, handicapUrl, handicapCaptcha);
		JsonResultBeanPlus bean = login(httpConfig, accountInfo);
		if (bean.isSuccess()) {
			String existHaId = RandomTool.getNumLetter32();
			//存储账号信息
			AgentCrawler agent = new AgentCrawler();
			agent.setAccountInfo(accountInfo);
			//存储爬虫信息
			Map<String, Object> data = (Map<String, Object>) bean.getData();
			agent.setProjectHost(data.get("projectHost"));
			agent.setHcConfig(httpConfig);
			agent.setExistId(existHaId);
			agentCrawlers.put(existHaId, agent);
			bean.setData(existHaId);
		}
		return bean;
	}

	/**
	 * 获取会员账号信息
	 *
	 * @param existHaId 已存在盘口代理id
	 * @return 会员账号信息
	 */
	@Override
	public JsonResultBeanPlus memberListInfo(String existHaId) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		AgentCrawler agent = agentCrawlers.get(existHaId);
		if (StringTool.isEmpty(agent.getProjectHost(), agent.getHcConfig())) {
			bean = login(existHaId);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		try {

			//获取会员列表
			String html = NewMoaAgentTool.getMemberList(agent);
			if (StringTool.isEmpty(html)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Document document = Jsoup.parse(html);
			JSONArray memberArray = new JSONArray();
			JSONArray array;
			Elements elements = document.getElementsByClass("t_list_tr_0");
			elements.remove(elements.last());
			for (Element element : elements) {
				array = new JSONArray();
				String serName = element.getElementsByClass("f_left TD_r").text();
				String online = element.select("img").attr("src");
				online = online.contains("0") ? "online" : "offline";

				array.add(serName);
				array.add(online);
				memberArray.add(array);
			}
			String token = document.getElementById("token").val();
			String forward = document.getElementById("forward").val();
			int totalPage = NumberTool.getInteger(document.getElementById("totalPage").val());
			int totalCount = NumberTool.getInteger(document.getElementById("totalCount").val());
			if (totalPage > 1) {
				for (int i = 0; i < totalPage; i++) {
					html = NewMoaAgentTool.getMemberList(agent, i + 2, totalCount, totalCount, token, forward);
					if (StringTool.notEmpty(html)) {
						document = Jsoup.parse(html);
						token = document.getElementById("token").val();
						elements = document.getElementsByClass("t_list_tr_0");
						elements.remove(elements.last());
						for (Element element : elements) {
							String serName = element.getElementsByClass("f_left TD_r").text();
							String online = element.select("img").attr("src");
							online = online.contains("0") ? "online" : "offline";

							array = new JSONArray();
							array.add(serName);
							array.add(online);
							memberArray.add(array);
						}
					}
				}
			}


			agent.setMemberArray(memberArray);
			bean.success();
			bean.setData(memberArray);
		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHaId, "获取获取会员账号信息失败" + e);
			agent.setProjectHost(null);
		}
		return bean;
	}

	/**
	 * 获取未结算摘要信息
	 *
	 * @param existHaId 已存在盘口代理id
	 * @param gameCode  游戏编码
	 * @param period    游戏编码
	 * @param date      时间字符串
	 * @return 未结算摘要信息
	 */
	public JsonResultBeanPlus getBetSummary(String existHaId, GameUtil.Code gameCode, Object period, String date) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (!agentCrawlers.containsKey(existHaId)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		AgentCrawler agent = agentCrawlers.get(existHaId);
		if (StringTool.isEmpty(agent.getProjectHost(), agent.getHcConfig())) {
			bean = login(existHaId);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		//盘口游戏id
		String game = NewMoaConfig.GAME_CODE.get(gameCode.name());
		try {
			period = PeriodUtil.getHandicapGamePeriod(handicapCode, gameCode, period);
			//只获取到会员信息
			Map<String, SummaryInfo> betSummary = NewMoaAgentTool.getBetSummary(agent, game, date, period);
			if (betSummary == null) {
				// 重新登录
				agent.setProjectHost(null);
				bean = login(existHaId);
				if (!bean.isSuccess()) {
					return bean;
				}
				bean.clear();
				betSummary = NewMoaAgentTool.getBetSummary(agent, game, date, period);
			}
			bean.success(betSummary);
		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHaId, "获取未结算摘要信息" + e);
			agent.setProjectHost(null);
		}
		return bean;
	}

	/**
	 * 获取投注详情
	 *
	 * @param existHaId   已存在盘口代理id
	 * @param gameCode    游戏code
	 * @param oddNumber   注单号
	 * @param summaryInfo 投注摘要信息
	 * @param period      期数
	 * @param date        时间
	 * @return
	 */
	public JsonResultBeanPlus getBetDetail(String existHaId, GameUtil.Code gameCode, String oddNumber,
										   SummaryInfo summaryInfo, Object period, String date) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (!agentCrawlers.containsKey(existHaId)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		AgentCrawler agent = agentCrawlers.get(existHaId);
		if (StringTool.isEmpty(agent.getProjectHost(), agent.getHcConfig())) {
			bean = login(existHaId);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		//盘口游戏id
		String game = NewMoaConfig.GAME_CODE.get(gameCode.name());
		period = PeriodUtil.getHandicapGamePeriod(handicapCode, gameCode, period);
		try {

			//获取未结算明细
			String html = NewMoaAgentTool.getBetDetail(agent, summaryInfo, game, period);
			//解析
			DetailBox betDetail = NewMoaAgentTool.analyzeDetail(null, gameCode, html, oddNumber);
			if (betDetail == null) {
				return bean.success();
			}
			//循环所有的页码
			while (betDetail.hasNext()) {
				html = NewMoaAgentTool.getBetDetailByPage(agent, summaryInfo, game, period, betDetail.getPages());
				// 没有找到 -数据弹出
				betDetail = NewMoaAgentTool.analyzeDetail(betDetail, gameCode, html, oddNumber);
				if (betDetail == null) {
					break;
				}
			}
			bean.success(betDetail);
		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHaId, "获取投注详情失败" + e);
			agent.setProjectHost(null);
		}
		return bean;
	}


}
