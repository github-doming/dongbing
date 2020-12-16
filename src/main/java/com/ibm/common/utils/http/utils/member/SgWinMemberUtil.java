package com.ibm.common.utils.http.utils.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.SgWinConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.member.SgWinMemberTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import com.ibm.common.utils.http.utils.entity.MemberUserInfo;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 138会员工具类
 * @Author: null
 * @Date: 2020-04-23 15:06
 * @Version: v1.0
 */
public class SgWinMemberUtil extends BaseMemberUtil {

	private HandicapUtil.Code handicapCode=HandicapUtil.Code.SGWIN;

    private static volatile SgWinMemberUtil instance = null;

    public static SgWinMemberUtil findInstance() {
        if (instance == null) {
            synchronized (SgWinMemberUtil.class) {
                if (instance == null) {
                    SgWinMemberUtil instance = new SgWinMemberUtil();
                    // 初始化
                    instance.init();
                    SgWinMemberUtil.instance = instance;
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
                bean.setData(member.getProjectHost());
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
            MemberUserInfo memberUserInfo=member.getMemberUserInfo();
            memberUserInfo.setMemberAccount(accountInfo.getAccount());
            memberUserInfo.setMemberType(data.get("memberType"));
            memberUserInfo.setCreditQuota(data.get("creditQuota"));
            memberUserInfo.setUsedAmount(data.get("usedAmount"));
            if(!memberCrawlers.containsKey(existHmId)){
                memberCrawlers.put(existHmId,member);
            }
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "登录失败,失败原因为：" + e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 登录
     * @param httpConfig        htpp请求配置类
     * @param accountInfo       账号信息对象
     * @return
     */
    @Override
    public JsonResultBeanPlus login(HttpClientConfig httpConfig, AccountInfo accountInfo) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        String handicapUrl=accountInfo.getHandicapUrl();
        if(!handicapUrl.endsWith("/")){
            handicapUrl=handicapUrl.concat("/");
        }
        httpConfig.headers(null);
        httpConfig.httpContext(null);
        try {
            //获取线路选择页面
            String routeHtml = SgWinMemberTool.getSelectRoutePage(httpConfig, handicapUrl, accountInfo.getHandicapCaptcha());
            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "线路选择")) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取线路页面失败" + routeHtml);
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //4条会员线路数组
            String[] routes = SgWinMemberTool.getMemberRoute(httpConfig, routeHtml);
            if (ContainerTool.isEmpty(routes)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //获取登录信息map
            Map<String, String> loginInfoMap = SgWinMemberTool.getLoginHtml(httpConfig, routes);
            if (ContainerTool.isEmpty(loginInfoMap)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            String loginSrc = loginInfoMap.get("loginSrc");
            //获取验证码
            String verifyCode = SgWinMemberTool.getVerifyCode(httpConfig, loginSrc, loginInfoMap.remove("code"));

            httpConfig.setHeader("Referer", loginSrc.concat("login"));
            httpConfig.httpContext(HttpClientContext.create());
            //盘口协议页面
            String loginInfo = SgWinMemberTool.getLogin(httpConfig, accountInfo.getAccount(), accountInfo.getPassword(), verifyCode, loginSrc,
                    loginInfoMap.remove("action"));

            if (StringTool.isEmpty(loginInfo)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //错误处理和是否第一次登录盘口
            if (!StringTool.isContains(loginInfo, "为避免出现争议") || StringTool.isContains(loginInfo, "修改密码")) {
				log.error(message,handicapCode.name(),accountInfo.getAccount(), "登录的URL="+loginSrc);
                bean.putEnum(SgWinMemberTool.loginError(loginInfo));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            Map<String, String> userData = new HashMap<>(5);

            //通过index主页面获取用户信息
            String indexHtml = SgWinMemberTool.getHomePage(httpConfig, loginSrc);
            if (ContainerTool.notEmpty(indexHtml)) {
                Document document = Jsoup.parse(indexHtml);
                String account = document.getElementsByClass("inline-name").text();
                //会员盘
                userData.put("memberType", account.split(" ")[1]);
                //信用额度
                userData.put("creditQuota", document.getElementsByClass("balance").first().text());
                //使用金额
                userData.put("usedAmount", document.getElementsByClass("betting").first().text());
            }
            httpConfig.setHeader("Referer", loginSrc.concat("member/index"));

            userData.put("projectHost", loginSrc);
            //会员账户
            userData.put("memberAccount", accountInfo.getAccount());

            bean.setData(userData);
            bean.success();
        } catch (Exception e) {
			log.error(message,handicapCode.name(),accountInfo.getAccount(), "登录失败,失败原因为："+e);
            bean.error(e.getMessage());
        } finally {
            httpConfig.defTimeOut();
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

            MemberUserInfo memberUserInfo=member.getMemberUserInfo();
            memberUserInfo.setMemberAccount(accountInfo.getAccount());
            memberUserInfo.setMemberType(data.get("memberType"));
            memberUserInfo.setCreditQuota(data.get("creditQuota"));
            memberUserInfo.setUsedAmount(data.get("usedAmount"));
            memberCrawlers.put(existHmId,member);
            bean.setData(existHmId);
        }
        return bean;
    }

    //endregion

    //region 用户信息
    /**
     * 用户基本信息
     *
     * @param existHmId 盘口会员存在id
     * @return 用户基本信息
     */
    public JsonResultBeanPlus userInfo(String existHmId) {
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
            JSONObject userObj = SgWinMemberTool.getUserInfo(member.getHcConfig(), member.getProjectHost());
            if (ContainerTool.isEmpty(userObj)) {
				log.info(message,handicapCode.name(),existHmId, "获取会员账号信息失败");
                bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //获取用户信息
            MemberUserInfo memberUserInfo=member.getMemberUserInfo();

            //信用额度
            memberUserInfo.setCreditQuota(String.valueOf(userObj.getDouble("maxLimit")));
            //可用额度
            memberUserInfo.setUsedQuota(String.valueOf(userObj.getDouble("balance")));
            //使用金额
            memberUserInfo.setUsedAmount(String.valueOf(userObj.getDouble("betting")));
            //盈亏金额
            memberUserInfo.setProfitAmount(String.valueOf(userObj.containsKey("result") ? userObj.getDouble("result") : 0));

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
     *
     * @param existHmId 已存在盘口会员id
     * @param flag      执行状态
     * @return 用户信息
     */
    @Override
    public MemberUserInfo getUserInfo(String existHmId, boolean flag) {
        if(!memberCrawlers.containsKey(existHmId)){
            return new MemberUserInfo();
        }
        if(flag){
            //获取用户信息
            JsonResultBeanPlus bean = userInfo(existHmId);
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
            if(flag||member.getMemberUserInfo().getUsedQuota()==null){
                //获取用户信息
                bean = userInfo(existHmId);
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

    /**
     * 获取游戏限额信息
     * @param existHmId     已存在盘口会员id
     * @param gameCode      游戏code
     * @return
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
        String game = SgWinConfig.BET_CODE.get(gameCode.name());

        try {
            JSONArray gameLimit = SgWinMemberTool.getQuotaList(member.getHcConfig(), member.getProjectHost(), game);
			log.trace(message,handicapCode.name(),existHmId, "游戏【" + game + "】限额信息为：" + gameLimit);
            if (ContainerTool.isEmpty(gameLimit)) {
                bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
                bean.putSysEnum(HcCodeEnum.CODE_404);
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

    /**
     * 获取盘口赔率
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @param betType   投注类型
     */
    public void getOddsInfo(String existHmId, GameUtil.Code gameCode, String betType, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] >= MAX_RECURSIVE_SIZE) {
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
        //盘口游戏code
        String game = SgWinConfig.BET_CODE.get(gameCode.name());
        String oddsCode = BallCodeTool.getGameOddsCode(HandicapUtil.Code.SGWIN, gameCode, betType);
        try {
            JSONObject oddsInfo = SgWinMemberTool.getOddsInfo(member.getHcConfig(),member.getProjectHost(), game, oddsCode);
            if (ContainerTool.isEmpty(oddsInfo)) {
				log.info(message,handicapCode.name(),existHmId, "游戏【" + gameCode + "】获取赔率信息失败");
                if (!odds.containsKey(betType)) {
                    member.setProjectHost(null);
                    member.setHcConfig(null);
                    getOddsInfo(existHmId, gameCode, betType, ++index[0]);
                }
                return;
            }
            odds.put(betType, oddsInfo);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "获取盘口赔率失败,失败原因为："+e);
        }
    }
    //endregion

    //region 投注
    /**
     * 投注
     *
     * @param existHmId  已存在id
     * @param gameCode   游戏code
     * @param drawNumber 开奖期数
     * @param betItems   投注项
     * @param betType    投注类型
     * @return 投注结果
     */
    public JsonResultBeanPlus betting(String existHmId, GameUtil.Code gameCode, String drawNumber,
                                      List<String> betItems, String betType) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

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
        if(ContainerTool.isEmpty(member.getOdds().get(gameCode).get(betType))){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        JSONObject odds = (JSONObject)member.getOdds().get(gameCode).get(betType);
        //盘口游戏code
        String game = SgWinConfig.BET_CODE.get(gameCode.name());

        try {
            //获取投注信息
            JSONArray betsArray = SgWinMemberTool.getBetItemInfo(gameCode, betItems, odds);
            if (ContainerTool.isEmpty(betsArray)) {
                bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            JSONObject betInfo = SgWinMemberTool
                    .betting(member.getHcConfig(), member.getProjectHost(), betsArray, drawNumber, game);
			log.trace(message,handicapCode.name(),existHmId, "投注结果为："+betInfo);
            //处理投注结果
            bean = resultProcess(existHmId, betInfo);
            if (!bean.isSuccess()) {
                return bean;
            }
            Map<String, String> ballInfo = BallCodeTool.getBallInfoCode(HandicapUtil.Code.SGWIN, gameCode);
            //注单号
            JSONArray ids = betInfo.getJSONArray("ids");
            //获取未结算信息
            int page = 1;
            int pageSize = 15;
            JSONArray betResult = new JSONArray();
            while (pageSize >= 15 && ids.size() > betResult.size()) {
                JSONArray data = SgWinMemberTool.getIsSettlePage(member.getHcConfig(), member.getProjectHost(), page);
                pageSize = data.size();
                page++;
                SgWinMemberTool.matchIsSettleInfo(betResult,ballInfo, data, ids);
            }

            //投注结果返回信息
            bean.success();
            bean.setData(betResult);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHmId, "投注失败,失败原因为："+e);
            bean.error(e.getMessage());
        } finally {
            member.getHcConfig().defTimeOut();
        }
        return bean;
    }

    /**
     * 处理投注结果
     *
     * @param existHmId 已存在盘口会员id
     * @param betInfo   投注结果
     * @return 投注结果
     */
    private JsonResultBeanPlus resultProcess(String existHmId, JSONObject betInfo) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if (ContainerTool.isEmpty(betInfo)) {
            bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }
        //status==0,投注成功
        int status = betInfo.getInteger("status");
        if (status != 0) {
            if (status == 3) {
                //低于或高于限额
                bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            } else if (status == 1) {
                //赔率不对，补投
                bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
        }
        if (betInfo.containsKey("message")) {
            String message = betInfo.getString("data");
            if (StringTool.isContains(message, "已被上级禁止投注，请与上级联系")) {
                bean.putEnum(HcCodeEnum.IBS_403_USER_BAN_BET);
            }else if(StringTool.contains(message, "最高", "最低")){
                bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
            }else{
				log.error(message,handicapCode.name(),existHmId, "未知的错误信息="+betInfo);
                bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
            }
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }
        if(!betInfo.containsKey("ids")){
            bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }
        //获取用户信息
        MemberUserInfo memberUserInfo=memberCrawlers.get(existHmId).getMemberUserInfo();
        JSONObject memberInfo = betInfo.getJSONObject("account");
        //信用额度
        memberUserInfo.setCreditQuota(String.valueOf(memberInfo.getDouble("maxLimit")));
        //可用额度
        memberUserInfo.setUsedQuota(String.valueOf(memberInfo.getDouble("balance")));
        //使用金额
        memberUserInfo.setUsedAmount(String.valueOf(memberInfo.getDouble("betting")));
        //盈亏金额
        memberUserInfo.setProfitAmount(String.valueOf((memberInfo.containsKey("result") ? memberInfo.getDouble("result") : 0)));

        bean.success();
        return bean;
    }
    //endregion

}
