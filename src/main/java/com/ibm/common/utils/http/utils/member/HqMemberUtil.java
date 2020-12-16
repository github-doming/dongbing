package com.ibm.common.utils.http.utils.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.HqConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.member.HqMemberTool;
import com.ibm.common.utils.http.utils.RSAUtils;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import com.ibm.common.utils.http.utils.entity.MemberUserInfo;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: HQ会员工具类
 * @Author: null
 * @Date: 2020-04-23 17:17
 * @Version: v1.0
 */
public class HqMemberUtil extends BaseMemberUtil{
	private HandicapUtil.Code handicapCode=HandicapUtil.Code.HQ;

    private static volatile HqMemberUtil instance = null;

    public static HqMemberUtil findInstance() {
        if (instance == null) {
            synchronized (HqMemberUtil.class) {
                if (instance == null) {
                    HqMemberUtil instance = new HqMemberUtil();
                    // 初始化
                    instance.init();
                    HqMemberUtil.instance = instance;
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
        if(ContainerTool.notEmpty(instance.memberCrawlers)){
            for (MemberCrawler memberCrawler : instance.memberCrawlers.values()) {
                memberCrawler.getHcConfig().destroy();
            }
        }
        instance.memberCrawlers=null;
        instance = null;
    }
    //region 登录
    /**
     * 登陆
     *
     * @param existHmId       盘口会员存在id
     * @param memberAccount   会员账号
     * @param memberPassword  会员密码
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     */
    @Override
    public JsonResultBeanPlus login(String existHmId, String memberAccount, String memberPassword, String handicapUrl, String handicapCaptcha) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //已存在数据
        MemberCrawler member;
        if(memberCrawlers.containsKey(existHmId)){
            member=  memberCrawlers.get(existHmId);
            if(StringTool.notEmpty(member.getProjectHost())){
                bean.setData(memberCrawlers.get(existHmId));
                bean.success();
                return bean;
            }
        }else{
            member=  new MemberCrawler();
        }
        AccountInfo accountInfo=member.getAccountInfo();
        accountInfo.setItemInfo(memberAccount,memberPassword,handicapUrl,handicapCaptcha);

        try {
            //获取配置类
            HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(member);
            httpConfig.httpTimeOut(10 * 1000);
            bean = login(httpConfig, accountInfo);
            if (!bean.isSuccess()) {
                return bean;
            }
            Map<String, String> data = (Map<String, String>) bean.getData();

            member.setProjectHost(data.get("projectHost"));
            if(!memberCrawlers.containsKey(existHmId)){
                memberCrawlers.put(existHmId,member);
            }
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "登录失败,失败原因为："+e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    /**
     * 登录
     *
     * @param httpConfig      请求配置类
     * @param accountInfo     账号信息
     * @return 登录
     */
    @Override
    public JsonResultBeanPlus login(HttpClientConfig httpConfig, AccountInfo accountInfo) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        httpConfig.headers(null);
        httpConfig.httpContext(null);

        try {
            httpConfig.httpContext(HttpClientContext.create());
            //获取会员登入href
            String href = HqMemberTool.getMemberHref(httpConfig, accountInfo.getHandicapUrl(), accountInfo.getHandicapCaptcha());
            if (StringTool.isEmpty(href)) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取会员登入页面失败");
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //获取线路
            String[] routes = HqMemberTool.getSelectRoutePage(httpConfig, href, accountInfo.getHandicapUrl());

            if (ContainerTool.isEmpty(routes)) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取会员线路失败");
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            httpConfig.httpContext().getCookieStore().clear();
            //获取登录页面
            Map<String, String> loginInfo = HqMemberTool.getLoginHtml(httpConfig, routes, accountInfo.getHandicapCaptcha());
            if (ContainerTool.isEmpty(loginInfo) || !loginInfo.containsKey("hostUrl")) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取登录页面信息失败");
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }

            Map<String, String> scriptInfo = HqMemberTool.getScriptInfo(loginInfo.get("html"));
            if (ContainerTool.isEmpty(scriptInfo)) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取登录页面失败");
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            String projectHost = loginInfo.get("hostUrl");
            //加密key
            JSONObject encryptKey = HqMemberTool.getEncryptKey(httpConfig, scriptInfo.get("SESSIONID"), projectHost);
            if (ContainerTool.isEmpty(encryptKey)) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取登录加密key失败");
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            String pke = encryptKey.getString("e");
            String pk = encryptKey.getString("m");
            String logpk = pke.concat(",").concat(pk);

            RSAPublicKey pubKey = RSAUtils
                    .getPublicKey(new BigInteger(pk, 16).toString(), Integer.parseInt(pke, 16) + "");

            String account = URLEncoder.encode(accountInfo.getAccount().trim(), "UTF-8").concat(",")
                    .concat(URLEncoder.encode(accountInfo.getPassword().trim(), "UTF-8"));

            String mi = RSAUtils.encryptByPublicKey(account, pubKey);

            //登录
            String loginHtml = HqMemberTool
                    .getLogin(httpConfig, projectHost, scriptInfo.get("SESSIONID"), accountInfo.getHandicapCaptcha(), mi, logpk);

            if (StringTool.isEmpty(loginHtml)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            if (StringTool.isContains(loginHtml, "用户名或密码不正确")) {
                bean.putEnum(HcCodeEnum.IBS_403_USER_ACCOUNT);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            if (StringTool.isContains(loginHtml, "账号已被禁用")) {
                bean.putEnum(HcCodeEnum.IBS_403_USER_STATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //协议页面
            String agreement = HqMemberTool.getAcceptAgreement(httpConfig, projectHost, scriptInfo.get("SESSIONID"));
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
            String index = HqMemberTool.getIndex(httpConfig, projectHost, scriptInfo.get("SESSIONID"));
            if (StringTool.isEmpty(index)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            Map<String, String> data = new HashMap<>(1);
            data.put("projectHost", "http://".concat(projectHost).concat(scriptInfo.get("SESSIONID")).concat("/"));

            bean.setData(data);
            bean.success();
        } catch (Exception e) {
			log.error(message,handicapCode.name(),accountInfo.getAccount(), "登录失败,失败原因为："+e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    /**
     * 校验登录
     *
     * @param handicapUrl     盘口url
     * @param handicapCaptcha 盘口验证码
     * @param memberAccount   盘口账号
     * @param memberPassword  盘口密码
     * @return 校验登录结果
     */
    @Override
    public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String memberAccount, String memberPassword) {
        // 获取配置类
        HttpClientConfig httpConfig = new HttpClientConfig();
        httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
        AccountInfo accountInfo=new AccountInfo();
        accountInfo.setItemInfo(memberAccount,memberPassword,handicapUrl,handicapCaptcha);

        JsonResultBeanPlus bean = login(httpConfig, accountInfo);
        if (bean.isSuccess()) {
            String existHmId = RandomTool.getNumLetter32();
            //存储账号信息
            MemberCrawler member=  new MemberCrawler();
            //存储爬虫信息
            Map<String, String> data = (Map<String, String>) bean.getData();
            member.setAccountInfo(accountInfo);
            member.setProjectHost(data.get("projectHost"));

            member.setHcConfig(httpConfig);
            member.setExistId(existHmId);
            memberCrawlers.put(existHmId,member);
            bean.setData(existHmId);
        }
        return bean;
    }
    //endregion

    //region 个人信息
    /**
     * 用户基本信息
     *
     * @param existHmId 盘口会员存在id
     * @param game      游戏code
     * @param periodId  期数字符串
     * @return 用户基本信息
     */
    private JsonResultBeanPlus userInfo(String existHmId, String game, String periodId) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        // 获取用户信息
        MemberCrawler member=memberCrawlers.get(existHmId);
        if(StringTool.isEmpty(member.getProjectHost(),member.getHcConfig())){
            bean = login(existHmId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        try {
            JSONObject userObj = HqMemberTool.getUserInfo(member.getHcConfig(), member.getProjectHost(), game, periodId);
            if (ContainerTool.isEmpty(userObj)) {
				log.info(message,handicapCode.name(),existHmId, "获取会员用户信息失败");
                bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            if (userObj.containsKey("data")) {
                if ("login".equals(userObj.get("data"))) {
                    member.setProjectHost(null);
                }
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            //获取用户信息
            MemberUserInfo memberUserInfo=member.getMemberUserInfo();

            //获取账号信息
            if(StringTool.isEmpty(memberUserInfo.getMemberAccount())){
                JSONObject accountObj=HqMemberTool.getMemberAccount(member.getHcConfig(),member.getProjectHost());
                if (ContainerTool.isEmpty(accountObj)) {
					log.info(message,handicapCode.name(),existHmId, "获取会员账号信息失败");
                    bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
                    bean.putSysEnum(HcCodeEnum.CODE_404);
                    return bean;
                }
                if (accountObj.containsKey("data")) {
                    if ("login".equals(accountObj.get("data"))) {
                        member.setProjectHost(null);
                    }
                    bean.putEnum(CodeEnum.IBS_404_DATA);
                    bean.putSysEnum(CodeEnum.CODE_404);
                    return bean;
                }
                memberUserInfo.setMemberAccount(accountObj.getString("Account"));
            }
            //信用额度
            memberUserInfo.setCreditQuota(String.valueOf(userObj.getDouble("Credit")));
            //可用额度
            memberUserInfo.setUsedQuota(String.valueOf(userObj.getDouble("Balance")));
            //使用金额
            memberUserInfo.setUsedAmount(String.valueOf(userObj.getDouble("BetMoney")));
            //盈亏金额
            memberUserInfo.setProfitAmount(String.format("%.1f",
                    userObj.getDouble("Balance") + userObj.getDouble("BetMoney") - userObj.getDouble("Credit")));

            bean.success();
            bean.setData(memberUserInfo);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "获取基本信息异常，失败原因为:"+e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    /**
     * 获取用户信息
     * @param existHmId         已存在盘口会员id
     * @param flag              执行状态
     * @return
     */
    @Override
    public MemberUserInfo getUserInfo(String existHmId, boolean flag) {
        return getUserInfo(existHmId,null,null,flag);
    }
    /**
     * 获取用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @param flag      执行状态
     * @return 用户信息
     */
    public MemberUserInfo getUserInfo(String existHmId, String game, String periodId, boolean flag) {
        if(!memberCrawlers.containsKey(existHmId)){
            return new MemberUserInfo();
        }
        if (StringTool.isEmpty(game, periodId)) {
			Object  period = GameUtil.Code.CQSSC.getGameFactory().period(HandicapUtil.Code.HQ).findPeriod();
            game = HqConfig.BET_CODE.get(GameUtil.Code.CQSSC.name());
            periodId = PeriodUtil.getHandicapGamePeriod(HandicapUtil.Code.HQ, GameUtil.Code.CQSSC, period);
        }
        if(flag){
            //获取用户信息
            JsonResultBeanPlus bean = userInfo(existHmId, game, periodId);
            //获取用户信息失败，返回空
            if (!bean.isSuccess()) {
                return new MemberUserInfo();
            }
        }
        return memberCrawlers.get(existHmId).getMemberUserInfo();
    }
    /**
     * 检验信息,上次成功检验时间超过两分钟，删除用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @return 校验信息
     */
    @Override
    public JsonResultBeanPlus checkInfo(String existHmId) {
        synchronized (existHmId) {
            if(!memberCrawlers.containsKey(existHmId)){
                return new JsonResultBeanPlus();
            }
            JsonResultBeanPlus bean;
            MemberCrawler member= memberCrawlers.get(existHmId);
            //上次校验时间
            long lastTime;
            if(member.getCheckTime()==0){
                lastTime = System.currentTimeMillis();
                member.setCheckTime(lastTime);
            }else{
                lastTime=member.getCheckTime();
            }
            //是否大于上次校验时间
            boolean flag = System.currentTimeMillis() - lastTime > MIN_CHECK_TIME;
            //重新获取数据
			String period =GameUtil.Code.CQSSC.getGameFactory().period(HandicapUtil.Code.HQ).findPeriod().toString();
            String game = HqConfig.BET_CODE.get(GameUtil.Code.CQSSC.name());
            String periodId = PeriodUtil.getHandicapGamePeriod(HandicapUtil.Code.HQ, GameUtil.Code.CQSSC, period);
            if(flag||member.getMemberUserInfo().getUsedQuota()==null){
                //获取用户信息
                bean = userInfo(existHmId, game, periodId);
                //获取用户信息失败，返回空
                if (!bean.isSuccess()) {
                    return bean;
                }
            }else {
                //使用内存数据
                bean = new JsonResultBeanPlus().success(member.getMemberUserInfo());
            }
            if (ContainerTool.isEmpty(bean.getData())) {
                if (System.currentTimeMillis() - lastTime > MAX_CHECK_TIME) {
                    member.setProjectHost(null);
                    member.setCheckTime(System.currentTimeMillis());
                }
            }
            if (flag) {
                member.setCheckTime(System.currentTimeMillis());
            }
            return bean;
        }
    }
    //endregion

    //region 投注信息
    /**
     * 获取赔率信息
     *
     * @param existHmId   已存在盘口会员id
     * @param m           类别位置
     * @param marketTypes 赔率code
     * @param game        盘口游戏code
     * @param gameCode    游戏code
     */
    public void getOddsInfo(String existHmId, String m, JSONArray marketTypes, String game, GameUtil.Code gameCode,
                            String betType, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
			log.error(message,handicapCode.name(),existHmId, "获取赔率信息失败");
            return;
        }
        MemberCrawler member=memberCrawlers.get(existHmId);
        if(StringTool.isEmpty(member.getProjectHost(),member.getHcConfig())){
            JsonResultBeanPlus bean = login(existHmId);
            if (!bean.isSuccess()) {
				log.error(message,handicapCode.name(),existHmId, "重新登录失败");
                return;
            }
        }
        //赔率信息
        Map<GameUtil.Code, Map<String, Object>> memberOdds=member.getOdds();
        Map<String, Object> odds;
		if(memberOdds.containsKey(gameCode)){
			odds=memberOdds.get(gameCode);
		}else{
			odds = new HashMap<>(3);
			memberOdds.put(gameCode,odds);
		}
        try {
            JSONObject oddsInfo = HqMemberTool
                    .getOddsInfo(member.getHcConfig(), member.getProjectHost(), m, marketTypes, game);
            if (ContainerTool.isEmpty(oddsInfo)) {
				log.info(message,handicapCode.name(),existHmId, "获取赔率信息为空");
                if (!odds.containsKey(betType)) {
                    member.setProjectHost(null);
                    getOddsInfo(existHmId, m, marketTypes, game, gameCode, betType, ++index[0]);
                }
                return;
            }
            if(oddsInfo.containsKey("errorInfo")){
				log.error(message,handicapCode.name(),existHmId, "获取赔率信息为空,错误信息="+oddsInfo);
                return;
            }
            odds.put(betType, oddsInfo.getJSONObject("Data").getJSONArray("OddsData"));
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "获取盘口赔率失败,失败原因为："+e);
        }

    }
    /**
     * 获取游戏限额
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @return 游戏限额
     */
    @Override
    public JsonResultBeanPlus getQuotaList(String existHmId, GameUtil.Code gameCode) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        if(!memberCrawlers.containsKey(existHmId)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        MemberCrawler member=  memberCrawlers.get(existHmId);
        if(StringTool.isEmpty(member.getProjectHost(),member.getHcConfig())){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        //获取盘口游戏code
        String game = HqConfig.BET_CODE.get(gameCode.name());

        try {
            JSONArray gameLimit = HqMemberTool.getQuotaList(member.getHcConfig(), member.getProjectHost(), game);
			log.trace(message,handicapCode.name(),existHmId, "游戏【" + game + "】限额信息为：" + gameLimit);
            if (ContainerTool.isEmpty(gameLimit)) {
                bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            if (StringTool.isContains(gameLimit.toJSONString(), "系统升级中")) {
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            } else if (StringTool.isContains(gameLimit.toJSONString(), "login")) {
                member.setProjectHost(null);
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            bean.setData(gameLimit);
            bean.success();
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "获取游戏限额信息失败,失败原因为：" + e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    //endregion

    //region 投注
    /**
     * 投注
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @param roundno   期数字符串
     * @param betItems  投注信息
     * @param betType   投注类型
     * @return 投注结果
     */
    public JsonResultBeanPlus betting(String existHmId, GameUtil.Code gameCode, String game, String roundno,
                                      List<String> betItems, String betType, int... index) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] >= MAX_RECURSIVE_SIZE) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        if(!memberCrawlers.containsKey(existHmId)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        MemberCrawler member=memberCrawlers.get(existHmId);

        if(StringTool.isEmpty(member.getProjectHost(),member.getHcConfig())){
            bean = login(existHmId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        if (ContainerTool.isEmpty(member.getOdds().get(gameCode).get(betType))) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        JSONArray odds = (JSONArray) member.getOdds().get(gameCode).get(betType);

        JSONArray betsArray = HqMemberTool.getBetItemInfo(gameCode, game, betItems, odds);
        if (ContainerTool.isEmpty(betsArray)) {
            bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }
        JSONObject betParams = new JSONObject();
        betParams.put("BetItems", betsArray);
        betParams.put("LotteryId", Integer.parseInt(game));
        betParams.put("PeriodId", roundno);
        try {
            JSONObject betInfo = HqMemberTool.betting(member.getHcConfig(), member.getProjectHost(), betParams, game);
            //投注结果
			log.trace(message,handicapCode.name(),existHmId, "投注结果为：" + betInfo);
            if (ContainerTool.isEmpty(betInfo)) {
                bean = getBettingSuccess(member, game, betItems, gameCode);
                if (!bean.isSuccess()) {
                    if (HcCodeEnum.IBS_403_BET_FAIL.getCode().equals(bean.getCode())) {
                        return betting(existHmId, gameCode, game, roundno, betItems, betType, ++index[0]);
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
            MemberUserInfo memberUserInfo=member.getMemberUserInfo();

            JSONObject data = betInfo.getJSONObject("Data");
            //可用额度
            memberUserInfo.setUsedQuota(String.valueOf(data.getDouble("Balance")));

            JSONArray items = data.getJSONArray("BetItems");
            //投注成功项信息
            JSONArray betResult = HqMemberTool.getBetResult(gameCode, items, game);

            //投注结果返回信息
            bean.success();
            bean.setData(betResult);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "投注失败,失败原因为：" + e);
            bean.error(e.getMessage());
        } finally {
            member.getHcConfig().defTimeOut();
        }
        return bean;
    }
	/**
     * 获取投注成功信息
     *
     * @param member      会员信息
     * @param game        盘口游戏code
     * @param betItems    投注信息
     * @param gameCode    游戏code
     * @return 投注成功信息
     */
    private JsonResultBeanPlus getBettingSuccess(MemberCrawler member,
                                                 String game, List<String> betItems, GameUtil.Code gameCode) throws InterruptedException {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //投注成功项
        JSONObject successResult = HqMemberTool.getBettingResult(member.getHcConfig(), member.getProjectHost(), game);
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
                member.setProjectHost(null);
                bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
        }
        JSONObject data = successResult.getJSONObject("Data");
        //投注项个数为0，补投
        if (data.getInteger("RecordCount") <= 0) {
            member.setProjectHost(null);
            bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }
        //过滤投注成功项(遇到相同投注项暂未处理)
        JSONArray betResult = HqMemberTool.filterSuccessInfo(data, betItems, gameCode);
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
