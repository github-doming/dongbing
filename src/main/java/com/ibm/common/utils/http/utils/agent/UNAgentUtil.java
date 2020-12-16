package com.ibm.common.utils.http.utils.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.UNConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.agent.UNAgentTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.Map;

/**
 * @Description: UN代理工具类
 * @Author: wwj
 * @Date: 2020/5/14 11:29
 * @Version: v1.0
 */
public class UNAgentUtil extends BaseAgentUtil {
	private static volatile UNAgentUtil instance = null;

	private HandicapUtil.Code handicapCode = HandicapUtil.Code.UN;

	public static UNAgentUtil findInstance() {
		if (instance == null) {
			synchronized (UNAgentUtil.class) {
				if (instance == null) {
					UNAgentUtil instance = new UNAgentUtil();
					// 初始化
					instance.init();
					UNAgentUtil.instance = instance;
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
			agent.setProjectHost(bean.getData());
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
		if (handicapUrl.startsWith("http://")) {
			handicapUrl = handicapUrl.replace("http", "https");
		}
		if (!handicapUrl.endsWith("/")) {
			handicapUrl = handicapUrl.concat("/");
		}
		httpConfig.headers(null);
		httpConfig.httpContext(null);
		try {
			//获取验证码
			String verifyCode = UNAgentTool.getVerifyCode(httpConfig, handicapUrl, null);
			//获取线路选择页面
			String routeHtml = UNAgentTool.getSelectRoutePage(httpConfig, handicapUrl, verifyCode, accountInfo.getHandicapCaptcha());
			if (StringTool.isEmpty(routeHtml)) {
				log.info(message, handicapCode.name(), accountInfo.getAccount(), "获取线路页面失败" + routeHtml);
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			//4条代理线路数组
			String[] routes = UNAgentTool.getAgentRoute(httpConfig, routeHtml);
			if (ContainerTool.isEmpty(routes)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			//获取登录信息map
			Map<String, String> loginInfoMap = UNAgentTool.getLoginHtml(httpConfig, routes);
			if (ContainerTool.isEmpty(loginInfoMap)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			String loginSrc = loginInfoMap.get("loginSrc");

			verifyCode = UNAgentTool.getVerifyCode(httpConfig, loginSrc, null);

			//登录
			String loginInfo = UNAgentTool.getLogin(httpConfig, accountInfo.getAccount(), accountInfo.getPassword(), verifyCode, loginSrc);
			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//错误处理 1@@^登录成功，请稍候…
			if (StringTool.notEmpty(loginInfo) && !StringTool.contains(loginInfo, "登录成功")) {
				log.error(message, handicapCode.name(), accountInfo.getAccount(), "登录的URL=" + loginSrc);
				bean.putEnum(UNAgentTool.loginError(loginInfo));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			// 加载主页
			UNAgentTool.toMain(httpConfig, loginSrc);

			bean.success(loginSrc);
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
			agent.setProjectHost(bean.getData());
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
			//获取会员列表 TODO
			JSONObject poList = UNAgentTool.getMemberList(agent, "1");
			if (poList == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			JSONArray agentInfoPoList = poList.getJSONArray("agentInfoPoList");

			JSONArray memberArray = new JSONArray();
			JSONArray array;
			for (int i = 0; i < agentInfoPoList.size(); i++) {
				JSONObject agentInfo = agentInfoPoList.getJSONObject(i);
				array = new JSONArray();
				array.add(agentInfo.getString("username"));
				array.add(agentInfo.getInteger("io") == 1 ? "online" : "offline");
				memberArray.add(array);
			}
			int totalPage = poList.getInteger("totalPage");
			if (totalPage > 1) {
				for (int i = 1; i <= totalPage; i++) {
					poList = UNAgentTool.getMemberList(agent, i + 1);
					agentInfoPoList = poList.getJSONArray("agentInfoPoList");
					for (int j = 0; j < agentInfoPoList.size(); j++) {
						JSONObject agentInfo = agentInfoPoList.getJSONObject(j);
						array = new JSONArray();
						array.add(agentInfo.getString("username"));
						array.add(agentInfo.getInteger("io") == 1 ? "online" : "offline");

						memberArray.add(array);
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
		String game = UNConfig.GAME_CODE_ID_BetSummary.get(gameCode.name());
		try {
			//只获取到会员信息
			Map<String, SummaryInfo> betSummary = UNAgentTool.getBetSummary(agent, game, date, period);
			if (betSummary == null) {
				// 重新登录
				agent.setProjectHost(null);
				bean = login(existHaId);
				if (!bean.isSuccess()) {
					return bean;
				}
				bean.clear();
				betSummary = UNAgentTool.getBetSummary(agent, game, date, period);
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
	 * @param date        时间
	 * @return
	 */
	public JsonResultBeanPlus getBetDetail(String existHaId, GameUtil.Code gameCode, String oddNumber,
										   SummaryInfo summaryInfo, String date) {
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
		String game = UNConfig.GAME_CODE_ID_BetSummary.get(gameCode.name());
		try {

			//获取未结算明细
			String html = UNAgentTool.getBetDetail(agent, summaryInfo, game, 1,"undefined", date);
			//解析
			DetailBox betDetail = UNAgentTool.analyzeDetail(null, html, oddNumber);
			if (betDetail == null) {
				return bean.success();
			}
			//循环所有的页码
			while (betDetail.hasNext()) {
				html = UNAgentTool.getBetDetail(agent, summaryInfo, game, betDetail.getPages().nextPage(),betDetail.getPages().getTotalCount(), date);
				// 没有找到 -数据弹出
				betDetail = UNAgentTool.analyzeDetail(betDetail, html, oddNumber);
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
