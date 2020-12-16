package com.ibm.common.test.wwj.handicap.hq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.HqConfig;
import com.ibm.common.test.wwj.handicap.AbsMemberCrawler;
import com.ibm.common.test.wwj.handicap.DataCustomer;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.utils.RSAUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 环球会员爬虫类
 * @Author: Dongming
 * @Date: 2019-11-22 14:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HqMemberCrawler extends AbsMemberCrawler<HqMemberGrab> {
	public HqMemberCrawler() {
		super.handicap(HandicapUtil.Code.HQ);
	}

	@Override protected JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl,
												 String handicapCaptcha, String account, String password) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//如果盘口地址不包含http://则添加
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
				log.info("获取会员线路失败");
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
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if(StringTool.isContains(loginHtml,"用户名或密码不正确")){
				bean.putEnum(HcCodeEnum.IBS_403_USER_ACCOUNT);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			//协议页面
			String agreement = grab.getAcceptAgreement(httpConfig, projectHost,sessionId);
			if (StringTool.isEmpty(agreement)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			JSONObject agreementResult = JSONObject.parseObject(agreement);
			if (agreementResult.getInteger("Status") == 1) {
				//需要修改密码
				if (agreementResult.getInteger("Data") == 1 || agreementResult.getInteger("Data") == 3) {
					bean.putEnum(HcCodeEnum.IBS_403_CHANGE_PASSWORD);
					bean.putSysEnum(HcCodeEnum.CODE_403);
					return bean;
				}
			}
			String index = grab.getIndex(httpConfig, projectHost,sessionId);
			if (StringTool.isEmpty(index)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			Map<String, String> data = new HashMap<>(1);
			data.put("projectHost", "http://".concat(projectHost).concat(sessionId).concat("/"));

			bean.success(data);
		} catch (Exception e) {
			log.error(getMsgTitle() + "账号【" + account + "】登录失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override protected JsonResultBeanPlus userInfo() {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if(member.customer()==null){
			bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		try {
			DataCustomer customer = member.customer();
			if (customer.crawler() == null) {
				bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
				if (!bean.isSuccess()) {
					return bean;
				}
				bean.setSuccess(false);
			}

			JSONObject userObj = grab.getUserInfo(customer.httpConfig(), customer.crawler().get("projectHost"));
			if(ContainerTool.isEmpty(userObj)){
				log.info(getMsgTitle()+"获取会员账号信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if(userObj.containsKey("data")){
				if ("login".equals(userObj.getString("data"))){
					member.customer(null);
				}
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Map<String, String> userInfo = new HashMap<>(4);
			//信用额度
			userInfo.put("creditQuota", String.valueOf(userObj.getDouble("Credit")));
			//可用额度
			userInfo.put("usedQuota", String.valueOf(userObj.getDouble("Balance")));
			//使用金额
			userInfo.put("usedAmount", String.valueOf(userObj.getDouble("BetMoney")));
			//盈亏金额
			userInfo.put("profitAmount", String.valueOf(
					userObj.getDouble("Balance") + userObj.getDouble("BetMoney") - userObj.getDouble("Credit")));
			member.userMap(userInfo);

			bean.success(userInfo);
		} catch (Exception e) {
			log.error(getMsgTitle()+"获取会员账号信息失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override public JsonResultBeanPlus gameLimit(GameUtil.Code gameCode) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if(member.customer()==null){
			bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		try {
			DataCustomer customer = member.customer();
			if (customer.crawler() == null) {
				bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
				if (!bean.isSuccess()) {
					return bean;
				}
				bean.setSuccess(false);
			}

			//获取盘口游戏code
			String game = HqConfig.BET_CODE.get(gameCode.name());
			JSONArray gameLimit = grab.getGameLimit(customer.httpConfig(), customer.crawler().get("projectHost"), game);
			log.trace(getMsgTitle()+"游戏【" + game + "】限额信息为：" + gameLimit);
			if (ContainerTool.isEmpty(gameLimit)) {
				bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if (StringTool.isContains(gameLimit.toJSONString(), "系统升级中")) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			bean.success(gameLimit);
		} catch (Exception e) {
			log.error(getMsgTitle()+"获取游戏限额信息失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 游戏赔率信息
	 *
	 * @param gameCode 游戏编码
	 * @param betType
	 * @return 游戏赔率信息
	 */
	@Override
	public JsonResultBeanPlus oddsInfo(GameUtil.Code gameCode, String betType) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if(member.customer()==null){
			bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		try {
			DataCustomer customer = member.customer();
			if (customer.crawler() == null) {
				bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
				if (!bean.isSuccess()) {
					return bean;
				}
				bean.setSuccess(false);
			}
			//获取盘口游戏code
			String game = HqConfig.BET_CODE.get(gameCode.name());
			//赔率信息
			Map<GameUtil.Code, Map<String, Object>> userOdds = member.oddsMap();
			Map<String, Object> odds;
			if(userOdds == null){
				userOdds = new HashMap<>();
				odds = new HashMap<>();
				userOdds.put(gameCode,odds);
			}else{
				odds = userOdds.get(game);
			}

			String m = HqConfig.BET_TYPE_CODE.get(gameCode.name().concat("_").concat(betType));
			List<String> marketTypes = grab.getMarketTypes(gameCode, betType);
			JSONObject oddsInfo = grab.getOddsInfo(customer.httpConfig(), customer.crawler().get("projectHost"),
					m, JSONArray.parseArray(String.valueOf(marketTypes)), game);
			if (ContainerTool.isEmpty(oddsInfo)) {
				log.info(getMsgTitle()+"获取赔率信息为空");
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			odds.put(betType, oddsInfo.getJSONObject("Data").getJSONArray("OddsData"));

			userOdds.put(gameCode,odds);
			member.oddsMap(userOdds);
			bean.success(userOdds);
		} catch (Exception e) {
			log.error(getMsgTitle()+"获取盘口赔率失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 投注
	 *
	 * @param gameCode 游戏编码
	 * @param betItems 投注项列表
	 * @param odds     赔率信息
	 * @param period   期数
	 * @return 投注结果
	 */
	@Override
	public JsonResultBeanPlus betting(GameUtil.Code gameCode, List<String> betItems, Object odds, String period) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if(member.customer()==null){
			bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		try {
			DataCustomer customer = member.customer();
			if (customer.crawler() == null) {
				bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
				if (!bean.isSuccess()) {
					return bean;
				}
				bean.setSuccess(false);
			}
			HttpClientConfig httpConfig = customer.httpConfig();
			httpConfig.httpTimeOut(20 * 1000);
			String game = HqConfig.BET_CODE.get(gameCode.name());
			JSONArray betsArray = grab.getBetItemInfo(gameCode, game, betItems,(JSONArray)odds);

			if (ContainerTool.isEmpty(betsArray)) {
				bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

			JSONObject betParams = new JSONObject();
			betParams.put("BetItems", betsArray);
			betParams.put("LotteryId", Integer.parseInt(game));
			betParams.put("PeriodId", period);


			JSONObject betInfo = grab.betting(httpConfig, customer.crawler().get("projectHost"), betParams, game);
			//投注结果
			log.trace(getMsgTitle()+" 投注结果为：" + betInfo);

			if (ContainerTool.isEmpty(betInfo)) {
				bean = getBettingSuccess(httpConfig, customer.crawler().get("projectHost"), game, betItems, gameCode);
				if (!bean.isSuccess()) {
					if (HcCodeEnum.IBS_403_BET_FAIL.getCode().equals(bean.getCode())) {
						return betting(gameCode,betItems, odds,period);
					}
				}
				return bean;
			}

			if (StringTool.contains(betInfo.get("Data").toString(), "最高", "最低")) {
				bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			} else if (StringTool.contains(betInfo.get("Data").toString(), "停押", "停用")) {
				bean.putEnum(HcCodeEnum.IBS_403_USER_BAN_BET);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			//获取用户信息
			Map<String, String> userInfo = new HashMap<>(1);

			JSONObject data = betInfo.getJSONObject("Data");
			//可用额度
			userInfo.put("usedQuota", String.valueOf(data.getDouble("Balance")));
			JSONArray items = data.getJSONArray("BetItems");
			//投注成功项信息
			JSONArray betResult = grab.getBetResult(gameCode, items, game);

			//投注结果返回信息
			bean.success();
			bean.setData(betResult);

		} catch (Exception e) {
			log.error(getMsgTitle()+"获取会员账号信息失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 获取投注成功信息
	 *
	 * @param httpConfig  http工具类
	 * @param projectHost 主机地址
	 * @param game        盘口游戏code
	 * @param betItems    投注信息
	 * @param gameCode    游戏code
	 * @return
	 */
	private JsonResultBeanPlus getBettingSuccess(HttpClientConfig httpConfig, String projectHost,
												 String game, List<String> betItems, GameUtil.Code gameCode) throws InterruptedException {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//投注成功项
		JSONObject successResult = grab.getBettingResult(httpConfig, projectHost, game);
		if (ContainerTool.isEmpty(successResult)) {
			bean.putEnum(HcCodeEnum.IBS_404_DATA);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		if (successResult.containsKey("data")) {
			if (StringTool.isContains(successResult.getString("data"), "系统升级中")) {
				bean.putEnum(HcCodeEnum.IBS_403_HANDICAP_UPDATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			} else if (StringTool.isContains(successResult.getString("data"), "login")) {
				member.customer().crawler(null);
				bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
		}
		JSONObject data = successResult.getJSONObject("Data");
		//投注项个数为0，补投
		if (data.getInteger("RecordCount") <= 0) {
			member.customer().crawler(null);
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		//过滤投注成功项(遇到相同投注项暂未处理)
		JSONArray betResult = grab.filterSuccessInfo(data, betItems, gameCode);
		if (ContainerTool.isEmpty(betResult)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		bean.success();
		bean.setData(betResult);
		return bean;
	}

}
