package com.ibm.common.utils.http.utils.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.agent.NewCcAgentTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import com.ibm.common.utils.http.utils.entity.ComGameInfo;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 新CC代理工具类
 * @Author: wwj
 * @Date: 2020/5/14 11:29
 * @Version: v1.0
 */
public class NewCcAgentUtil extends BaseAgentUtil {
	private static volatile NewCcAgentUtil instance = null;

	private HandicapUtil.Code handicapCode = HandicapUtil.Code.NEWCC;

	public static NewCcAgentUtil findInstance() {
		if (instance == null) {
			synchronized (NewCcAgentUtil.class) {
				if (instance == null) {
					NewCcAgentUtil instance = new NewCcAgentUtil();
					// 初始化
					instance.init();
					NewCcAgentUtil.instance = instance;
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
			Map<String, String> data = (Map<String, String>) bean.getData();

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
			//获取线路选择页面
			String routeHtml = NewCcAgentTool.getSelectRoutePage(httpConfig, handicapUrl, accountInfo.getHandicapCaptcha());
			if (StringTool.isEmpty(routeHtml)) {
				log.info(message, handicapCode.name(), accountInfo.getAccount(), "获取线路页面失败" + routeHtml);
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			//4条代理线路数组
			String[] routes = NewCcAgentTool.getAgentRoute(httpConfig, routeHtml);

			if (ContainerTool.isEmpty(routes)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//获取登录信息map
			Map<String, String> loginInfoMap = NewCcAgentTool.getLoginHtml(httpConfig, routes);
			if (ContainerTool.isEmpty(loginInfoMap)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			String loginSrc = loginInfoMap.get("loginSrc");

			httpConfig.httpContext(HttpClientContext.create());
			//获取验证码
			String verifyCode = NewCcAgentTool.getVerifyCode(httpConfig, loginSrc, "");
			// 密码md5Hex加密
			String loginPwd = DigestUtils.md5Hex(DigestUtils.md5Hex(accountInfo.getPassword()) + verifyCode.toLowerCase());
			//首页
			String loginInfo = NewCcAgentTool.getLogin(httpConfig, accountInfo.getAccount(), loginPwd, verifyCode, loginSrc);

			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//错误处理和是否等一次登录盘口
			if (StringTool.isContains(loginInfo, "修改密码")) {
				log.error(message, handicapCode.name(), accountInfo.getAccount(), "登录的URL=" + loginSrc);
				bean.putEnum(NewCcAgentTool.loginError(loginInfo));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

			Map<String, String> data = new HashMap<>(1);
			data.put("projectHost", loginSrc);
			bean.success(data);
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
			Map<String, String> data = (Map<String, String>) bean.getData();
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
			JSONArray memberArray = NewCcAgentTool.getMemberList(agent);
			if (memberArray == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
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
		String game = BallCodeTool.getNewCcGameNo(gameCode, "A");
		try {
			//获取游戏期数阶段码
			bean = getPhaseoption(agent, gameCode, period, game);
			if (!bean.isSuccess()) {
				return bean;
			}
			String phaseoption = bean.getData().toString();
			bean.setData(null);
			bean.setSuccess(false);

			//只获取到会员信息
			Map<String, SummaryInfo> betSummary = NewCcAgentTool.getBetSummary(agent, game, phaseoption, date);
			if (betSummary == null) {
				// 重新登录
				agent.setProjectHost(null);
				bean = login(existHaId);
				if (!bean.isSuccess()) {
					return bean;
				}
				bean.clear();
				betSummary = NewCcAgentTool.getBetSummary(agent, game, phaseoption, date);
			}

			bean.success(betSummary);
		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHaId, "获取未结算摘要信息" + e);
			agent.setProjectHost(null);
		}
		return bean;
	}

	/**
	 * 获取期数阶段码
	 *
	 * @param agent    盘口代理信息
	 * @param gameCode 游戏编码
	 * @param period   期数
	 * @param game     盘口游戏
	 * @return
	 */
	private JsonResultBeanPlus getPhaseoption(AgentCrawler agent, GameUtil.Code gameCode, Object period, String game, int... index)
			throws InterruptedException {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			log.error(message, handicapCode.name(), "获取期数阶段码");
			return bean;
		}


		String phaseoption;
		if (agent.getPhaseoptionMap().containsKey(gameCode)) {
			ComGameInfo gameInfo = agent.getPhaseoptionMap().get(gameCode);
			if (period.equals(gameInfo.getPeriod())) {
				return bean.success(gameInfo.getPhaseoption());
			}
		}
		//获取期数阶段码
		JSONObject result = NewCcAgentTool.getPhaseoption(agent, game);
		if (StringTool.isEmpty(result)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		if (result.getInteger("ExecStatus") != 1 || result.getJSONArray("award_phaseList") == null) {
			agent.setProjectHost(null);
			bean.putEnum(HcCodeEnum.IBS_404_BET_INFO);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		JSONObject phaseoptions = (JSONObject) result.getJSONArray("award_phaseList").get(0);

		// 期数不匹配重新获取
		if (NewCcAgentTool.checkPeriod(gameCode,period.toString(),NumberTool.getInteger(phaseoptions.getString("NN").split(" ")[0]))) {
			getPhaseoption(agent, gameCode, period, game, ++index[0]);
		}
		phaseoption = phaseoptions.getString("k_ID");
		ComGameInfo gameInfo;
		if (agent.getPhaseoptionMap().containsKey(gameCode)) {
			gameInfo = agent.getPhaseoptionMap().get(gameCode);
		} else {
			gameInfo = new ComGameInfo();
			agent.getPhaseoptionMap().put(gameCode, gameInfo);
		}
		gameInfo.setPhaseoption(phaseoption);
		gameInfo.setPeriod(period);

		return bean.success(phaseoption);
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
		String game = BallCodeTool.getNewCcGameNo(gameCode, "A");
		try {
			//获取游戏期数阶段码
			bean = getPhaseoption(agent, gameCode, period, game);
			if (!bean.isSuccess()) {
				return bean;
			}
			String phaseoption = bean.getData().toString();
			bean.setData(null);
			bean.setSuccess(false);

			//获取未结算明细
			String html = NewCcAgentTool.getBetDetail(agent, summaryInfo, game, null, phaseoption, date);
			//解析
			DetailBox betDetail = NewCcAgentTool.analyzeDetail(null, gameCode, html, oddNumber);
			if (betDetail == null) {
				return bean.success();
			}
			//循环所有的页码
			while (betDetail.hasNext()) {
				html = NewCcAgentTool.getBetDetail(agent, summaryInfo, game, betDetail.getPages().nextPage(), phaseoption, date);
				// 没有找到 -数据弹出
				betDetail = NewCcAgentTool.analyzeDetail(betDetail, gameCode, html, oddNumber);
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
