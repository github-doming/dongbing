package com.ibm.common.test.wwj.handicap.hq;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.HqConfig;
import com.ibm.common.test.wwj.handicap.AbsAgentCrawler;
import com.ibm.common.test.wwj.handicap.DataCustomer;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.utils.RSAUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 环球代理爬虫类
 * @Author: Dongming
 * @Date: 2019-11-26 18:08
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HqAgentCrawler extends AbsAgentCrawler<HqAgentGrab> {
	public HqAgentCrawler() {
		super.handicap(HandicapUtil.Code.HQ);
	}
	@Override protected JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl,
												 String handicapCaptcha, String account, String password) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String http = "http://";
		if (!handicapUrl.startsWith(http) && !handicapUrl.startsWith("https://")) {
			handicapUrl = http.concat(handicapUrl);
		}
		httpConfig.headers(null);
		httpConfig.httpContext(null);

		try {
			httpConfig.httpContext(HttpClientContext.create());
			httpConfig.url(handicapUrl);
			//获取线路
			String[] routes = grab.routes(httpConfig.url(handicapUrl), handicapCaptcha);

			if (ContainerTool.isEmpty(routes)) {
				log.info("获取代理线路失败");
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			httpConfig.httpContext().getCookieStore().clear();

			Map<String, String> loginInfo = grab.loginInfo(httpConfig.data(handicapCaptcha), routes);
			if (ContainerTool.isEmpty(loginInfo) || !loginInfo.containsKey("hostUrl")) {
				log.info("获取登录页面信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			String projectHost = loginInfo.get("hostUrl");
			String sessionId = loginInfo.get("SESSIONID");
			//加密key
			JSONObject encryptKey = grab.getEncryptKey(httpConfig, sessionId, projectHost);
			if (ContainerTool.isEmpty(encryptKey)) {
				log.info("获取登录加密key失败");
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			String pke = encryptKey.getString("e");
			String pk = encryptKey.getString("m");
			String logpk = pke.concat(",").concat(pk);
			RSAPublicKey pubKey = RSAUtils
					.getPublicKey(new BigInteger(pk, 16).toString(), Integer.parseInt(pke, 16) + "");
			String loginAccount = URLEncoder.encode(account.trim(), "UTF-8").concat(",")
					.concat(URLEncoder.encode(password.trim(), "UTF-8"));
			String mi = RSAUtils.encryptByPublicKey(loginAccount, pubKey);
			//登录
			String loginHtml = grab
					.getLogin(httpConfig, projectHost, sessionId, handicapCaptcha, mi, logpk);

			if (StringTool.isEmpty(loginHtml)) {
				log.info("登录盘口失败");
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if (!StringTool.isContains(loginHtml,"Status")) {
				log.info("获取登录信息=" + loginInfo);
				bean.putEnum(grab.loginError(loginHtml));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

			Map<String, String> data = new HashMap<>(1);
			data.put("projectHost", "http://".concat(projectHost).concat("/").concat(sessionId));

			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error(getMsgTitle()+"HQ盘口账号【" + account + "】登录失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	@Override protected JsonResultBeanPlus memberListInfo() {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			DataCustomer customer = agent.customer();
			List<Map<String,String>> memberList = grab.getMemberListInfo(customer.httpConfig(),customer.crawler().get("projectHost"));
			agent.memberList(memberList);
			bean.success(memberList);
		} catch (Exception e) {
			log.error(getMsgTitle()+"获取会员账号信息失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override public JsonResultBeanPlus betSummary(GameUtil.Code gameCode) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if(agent.customer()==null){
			bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		try {
			DataCustomer customer = agent.customer();
			if (customer.crawler() == null) {
				bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
				if (!bean.isSuccess()) {
					return bean;
				}
				bean.setSuccess(false);
			}
			String day = DateTool.getDate(new Date());
			String lottery = HqConfig.BET_CODE.get(gameCode.name());
			Map<String, Map<String, Integer>> betSummary = grab.getBetSummary(customer.httpConfig(), customer.crawler().get("projectHost"), lottery, day);
			if(ContainerTool.isEmpty(betSummary)){
				log.error(getMsgTitle()+"投注未结算摘要获取未空");
				bean.putEnum(HcCodeEnum.IBS_404_DATA);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			bean.success(betSummary);
		} catch (Exception e) {
			log.error(getMsgTitle()+"获取投注未结算摘要失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override public JsonResultBeanPlus betDetail(GameUtil.Code gameCode, String period, Object member,
			String oddNumber) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if(agent.customer()==null){
			bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		try {
			DataCustomer customer = agent.customer();
			if (customer.crawler() == null) {
				bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
				if (!bean.isSuccess()) {
					return bean;
				}
				bean.setSuccess(false);
			}
			String day = DateTool.getDate(new Date());
			String lottery = HqConfig.BET_CODE.get(gameCode.name());
			//获取未结算明细
			String html = grab.getBetDetail(customer.httpConfig(), customer.crawler().get("projectHost"), lottery,period, day,Integer.valueOf((String)member));
			Map<String, Object> betDetail = grab.getBetDetail(null, gameCode, html, oddNumber);
			if (betDetail == null) {
				return bean.success();
			}

			//循环所有的页码
			while (betDetail.containsKey("pageIndex")) {
				html = grab.getBetDetail(customer.httpConfig(), customer.crawler().get("projectHost"),lottery,period, day,Integer.valueOf((String)member));

				//数据弹出
				if (grab.getBetDetail(betDetail, gameCode, html, oddNumber) == null) {
					break;
				}
			}
			bean.success(betDetail);
		} catch (Exception e) {
			log.error(getMsgTitle()+"获取投注未结算摘要失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

}
