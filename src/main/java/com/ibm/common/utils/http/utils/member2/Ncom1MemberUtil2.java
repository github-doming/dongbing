package com.ibm.common.utils.http.utils.member2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.Ncom1Config;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.member.Ncom1MemberTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: ncom1会员工具类
 * @Author: null
 * @Date: 2019-12-26 18:09
 * @Version: v1.0
 */
public class Ncom1MemberUtil2 extends BaseMemberUtil2 {

    private static volatile Ncom1MemberUtil2 instance = null;

    public static Ncom1MemberUtil2 findInstance() {
        if (instance == null) {
            synchronized (Ncom1MemberUtil2.class) {
                if (instance == null) {
                    Ncom1MemberUtil2 instance = new Ncom1MemberUtil2();
                    // 初始化
                    instance.init();
                    Ncom1MemberUtil2.instance = instance;
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
        if (ContainerTool.notEmpty(instance.hcConfigMap)) {
            for (HttpClientConfig clientConfig : instance.hcConfigMap.values()) {
                clientConfig.destroy();
            }
        }
        instance.userMap = null;
        instance.accountMap = null;
        instance.memberMap = null;
        instance.oddsMap = null;
        instance.checkMap = null;
        instance = null;
    }

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
    public JsonResultBeanPlus login(String existHmId, String memberAccount, String memberPassword, String handicapUrl,
                                    String handicapCaptcha) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //已存在数据
        if (memberMap.containsKey(existHmId)) {
            bean.setData(memberMap.get(existHmId));
            bean.success();
            return bean;
        }
        accountInfo(existHmId,memberAccount,memberPassword,handicapUrl,handicapCaptcha);
        try {
            //获取配置类
            HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHmId);
            httpConfig.httpTimeOut(10 * 1000);
            bean = login(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
            if (!bean.isSuccess()) {
                return bean;
            }
            Map<String, String> data = (Map<String, String>) bean.getData();

            Map<String, String> hostMap = new HashMap<>(1);
            hostMap.put("projectHost", data.remove("projectHost"));

            userMap.put(existHmId, data);
            memberMap.put(existHmId, hostMap);
        } catch (Exception e) {
            log.error("Ncom1盘口会员【" + existHmId + "】登录失败,失败原因为：", e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 登录
     *
     * @param httpConfig      请求配置类
     * @param handicapUrl     盘口url
     * @param handicapCaptcha 盘口验证码
     * @param memberAccount   账号
     * @param memberPassword  密码
     * @return 登录
     */
    public JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
                                    String memberAccount, String memberPassword) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        httpConfig.headers(null);
        httpConfig.httpContext(null);
        try {
            //获取线路选择页面
            String routeHtml = Ncom1MemberTool.getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha);
            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "線路選擇")) {
                log.info("Ncom1获取线路页面失败=" + routeHtml);
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //4条会员线路数组
            String[] routes = Ncom1MemberTool.getMemberRoute(httpConfig, routeHtml);
            if (ContainerTool.isEmpty(routes)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //选择登录线路
            String loginSrc = Ncom1MemberTool.getLoginHtml(httpConfig, routes);
            if (StringTool.isEmpty(loginSrc)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //盘口协议页面
            String loginInfo = Ncom1MemberTool.getLogin(httpConfig, memberAccount, memberPassword, loginSrc);
            if (StringTool.isEmpty(loginInfo)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //错误处理和是否第一次登录盘口
            if (!StringTool.isContains(loginInfo, "用戶協議與規則") || StringTool.isContains(loginInfo, "修改密码")) {
                bean.putEnum(Ncom1MemberTool.loginError(loginInfo));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //主页面获取token
            Ncom1MemberTool.getIndex(httpConfig,loginSrc);
            Map<String, String> userData = new HashMap<>(5);
            userData.put("projectHost", loginSrc);
            //会员账户
            userData.put("memberAccount", memberAccount);

            bean.setData(userData);
            bean.success();
        } catch (Exception e) {
            log.error("Ncom1盘口账号【" + memberAccount + "】登录失败,失败原因为：", e);
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
    public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String memberAccount,
                                        String memberPassword) {
        // 获取配置类
        HttpClientConfig httpConfig = new HttpClientConfig();
        httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
        JsonResultBeanPlus bean = login(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
        if (bean.isSuccess()) {
            String existHmId = RandomTool.getNumLetter32();
            //存储账号信息
            Map<String, String> account = new HashMap<>(4);
            account.put("memberAccount", memberAccount);
            account.put("memberPassword", memberPassword);
            account.put("handicapUrl", handicapUrl);
            account.put("handicapCaptcha", handicapCaptcha);
            accountMap.put(existHmId, account);

            //存储爬虫信息
            Map<String, String> data = (Map<String, String>) bean.getData();
            Map<String, String> hostMap = new HashMap<>(1);
            hostMap.put("projectHost", data.remove("projectHost"));

            userMap.put(existHmId, data);
            memberMap.put(existHmId, hostMap);

            hcConfigMap.put(existHmId, httpConfig);
            bean.setData(existHmId);
        }
        return bean;
    }

    /**
     * 登陆
     *
     * @param existHmId 盘口会员存在id
     */
    public JsonResultBeanPlus login(String existHmId) {
        synchronized (existHmId) {
            if (!memberMap.containsKey(existHmId)) {
                JsonResultBeanPlus bean = new JsonResultBeanPlus();
                if (!accountMap.containsKey(existHmId)) {
                    bean.putEnum(HcCodeEnum.IBS_404_EXIST_INFO);
                    bean.putSysEnum(HcCodeEnum.CODE_404);
                    return bean;
                }
                Map<String, String> data = accountMap.get(existHmId);
                return login(existHmId, data.get("memberAccount"), data.get("memberPassword"), data.get("handicapUrl"),
                        data.get("handicapCaptcha"));
            }
        }
        return new JsonResultBeanPlus().success();
    }

    /**
     * 用户基本信息
     * https://mem1.1188fg.com/index/info?_=1577355686455
     *
     * @param existHmId 盘口会员存在id
     * @return 用户基本信息
     */
    public JsonResultBeanPlus userInfo(String existHmId, GameUtil.Code gameCode, String period, String betType) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        // 获取用户信息
        if (!memberMap.containsKey(existHmId)) {
            bean = login(existHmId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        Map<String, String> accountInfo = memberMap.get(existHmId);
        if (ContainerTool.isEmpty(accountInfo)) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        // 获取配置类
        HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHmId);
        String gameId = Ncom1Config.BET_CODE.get(gameCode.name());
        String type = BallCodeTool.getGameOddsCode(HandicapUtil.Code.NCOM1, gameCode, betType);
        try {
            //获取可用金额
            JSONObject userObj = Ncom1MemberTool.getUserInfo(httpConfig, accountInfo.get("projectHost"));
            if (ContainerTool.isEmpty(userObj)) {
                log.info("Ncom1盘口会员【" + existHmId + "】获取会员账号信息失败");
                bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            if(userObj.containsKey("data")&&StringTool.isContains(userObj.getString("data"),"login")){
                memberMap.remove(existHmId);
                bean.putEnum(HcCodeEnum.IBS_403_OTHER_PLACE_LOGIN);
                bean.putSysEnum(CodeEnum.CODE_403);
                return bean;
            }
            //获取当天盈亏金额
            JSONObject profitObj = Ncom1MemberTool.getOddsInfo(httpConfig, accountInfo.get("projectHost"), gameId, period, type);

            if(profitObj.containsKey("data")&&StringTool.isContains(profitObj.getString("data"),"login")){
                memberMap.remove(existHmId);
                bean.putEnum(HcCodeEnum.IBS_403_OTHER_PLACE_LOGIN);
                bean.putSysEnum(CodeEnum.CODE_403);
                return bean;
            }
            //获取用户信息,该盘口无法获取信用额度和使用金额
            Map<String, String> userInfo;
            if (userMap.containsKey(existHmId)) {
                userInfo = userMap.get(existHmId);
            } else {
                userInfo = new HashMap<>(6);
                userMap.put(existHmId, userInfo);
                //使用金额
                userInfo.put("usedAmount", "0");
                //信用额度
                userInfo.put("creditQuota", "0");
            }
            if (profitObj.containsKey("today_money")) {
                //盈亏金额
                userInfo.put("profitAmount", String.valueOf(profitObj.getDouble("today_money")));
            }
            if(userObj.containsKey("money")){
                //可用额度
                userInfo.put("usedQuota", String.valueOf(userObj.getDouble("money")));
            }

            bean.success();
            bean.setData(userInfo);
        } catch (Exception e) {
            log.error("Ncom1盘口会员【" + existHmId + "】获取基本信息异常，失败原因为:", e);
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
    public Map<String, String> getUserInfo(String existHmId, boolean flag) {
        return getUserInfo(existHmId,null,null,null,flag);
    }
    /**
     * 获取用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @param flag      执行状态
     * @return 用户信息
     */
    public Map<String, String> getUserInfo(String existHmId, GameUtil.Code gameCode, String period, String betType, boolean flag) {
        if (StringTool.isEmpty(gameCode, period, betType)) {
            gameCode = GameUtil.Code.PK10;
			period =gameCode.getGameFactory().period(HandicapUtil.Code.NCOM1).findPeriod().toString();
            betType = "dobleSides";
        }
        if (!userMap.containsKey(existHmId) || flag) {
            //获取用户信息
            JsonResultBeanPlus bean = userInfo(existHmId, gameCode, period, betType);
            //获取用户信息失败，返回空
            if (!bean.isSuccess()) {
                return new HashMap<>(1);
            }
        }
        return userMap.get(existHmId);
    }

    /**
     * 获取游戏限额
     * https://mem1.1188fg.com/user/?game_id=1&_=1577355686466
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @return 游戏限额
     */
    @Override
    public JsonResultBeanPlus getQuotaList(String existHmId, GameUtil.Code gameCode) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        // 获取用户信息
        if (!memberMap.containsKey(existHmId)) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        Map<String, String> accountInfo = memberMap.get(existHmId);
        if (ContainerTool.isEmpty(accountInfo)) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        //获取盘口游戏code
        String gameId = Ncom1Config.BET_CODE.get(gameCode.name());

        // 获取配置类
        HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHmId);

        try {
            JSONArray gameLimit = Ncom1MemberTool.getQuotaList(httpConfig, accountInfo.get("projectHost"), gameId);
            log.trace("Ncom1盘口会员【" + existHmId + "】游戏【" + gameCode.name() + "】限额信息为：" + gameLimit);
            if (ContainerTool.isEmpty(gameLimit)) {
                bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            bean.setData(gameLimit);
            bean.success();
        } catch (Exception e) {
            log.error("Ncom1盘口会员【" + existHmId + "】获取游戏限额信息失败,失败原因为：", e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 获取盘口赔率
     * https://mem1.1188fg.com/order/info/
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @param betType   投注类型
     * @return
     */
    public void getOddsInfo(String existHmId, GameUtil.Code gameCode, String period, String betType, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] >= MAX_RECURSIVE_SIZE) {
            log.error("Ncom1盘口会员【" + existHmId + "】获取赔率信息失败");
            return;
        }
        if (!memberMap.containsKey(existHmId)) {
            JsonResultBeanPlus bean = login(existHmId);
            if (!bean.isSuccess()) {
                log.error("Ncom1盘口会员【" + existHmId + "】重新登录失败");
                return;
            }
        }
        Map<String, String> accountInfo = memberMap.get(existHmId);
        if (ContainerTool.isEmpty(accountInfo)) {
            log.info("Ncom1盘口会员【" + existHmId + "】会员信息为空");
            return;
        }
        //赔率信息
        Map<String, Object> odds;
        if (oddsMap.containsKey(existHmId)) {
            if (oddsMap.get(existHmId).containsKey(gameCode)) {
                odds = oddsMap.get(existHmId).get(gameCode);
            } else {
                odds = new HashMap<>(3);
                oddsMap.get(existHmId).put(gameCode, odds);
            }
        } else {
            Map<GameUtil.Code, Map<String, Object>> map = new HashMap<>(10);
            odds = new HashMap(3);
            map.put(gameCode, odds);
            oddsMap.put(existHmId, map);
        }
        HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHmId);
        String gameId = Ncom1Config.BET_CODE.get(gameCode.name());
        String type = BallCodeTool.getGameOddsCode(HandicapUtil.Code.NCOM1, gameCode, betType);
        try {
            JSONObject oddsInfo = Ncom1MemberTool.getOddsInfo(httpConfig, accountInfo.get("projectHost"), gameId, period, type);
            if (ContainerTool.isEmpty(oddsInfo)||!oddsInfo.containsKey("odds")) {
                log.info("Ncom1盘口会员【" + existHmId + "】游戏【" + gameCode + "】获取赔率信息失败");
                if (!odds.containsKey(betType)) {
                    memberMap.remove(existHmId);
                    hcConfigMap.remove(existHmId);
                    getOddsInfo(existHmId, gameCode, period, betType, ++index[0]);
                }
                return;
            }
            odds.put(betType, oddsInfo.getJSONObject("odds"));
        } catch (Exception e) {
            log.error("Ncom1盘口会员【" + existHmId + "】获取盘口赔率失败,失败原因为：", e);
        }
    }

    /**
     * 投注
     *
     * @param existHmId  已存在id
     * @param gameCode   游戏code
     * @param roundno 开奖期数
     * @param betItems   投注项
     * @param betType    投注类型
     * @return 投注结果
     */
    public JsonResultBeanPlus betting(String existHmId, GameUtil.Code gameCode, String roundno,
                                      List<String> betItems, String betType) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if (!memberMap.containsKey(existHmId)) {
            bean = login(existHmId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        Map<String, String> accountInfo = memberMap.get(existHmId);
        if (ContainerTool.isEmpty(accountInfo) || ContainerTool.isEmpty(oddsMap.get(existHmId).get(gameCode).get(betType))) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHmId);
        httpConfig.httpTimeOut(20 * 1000);
        JSONObject oddsObj = (JSONObject) oddsMap.get(existHmId).get(gameCode).get(betType);
        String gameId = Ncom1Config.BET_CODE.get(gameCode.name());

        String betItem = Ncom1MemberTool.getBetItemInfo(gameCode, betItems, oddsObj);
        if (StringTool.isEmpty(betItem)) {
            bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }
        try {
            JSONObject betInfo = Ncom1MemberTool.betting(httpConfig, accountInfo.get("projectHost"), betItem, roundno, gameId);
            log.trace("Ncom1盘口会员【" + existHmId + "】投注结果为：" + betInfo);
            //处理投注结果
            bean = resultProcess(existHmId, betInfo);
            if (!bean.isSuccess()) {
                return bean;
            }
            String gameName=Ncom1Config.GAME_NAME.get(gameCode.name());
            Map<String, String> ballInfo = BallCodeTool.getBallInfoCode(HandicapUtil.Code.NCOM1, gameCode);
            //投注成功数
            int count=Integer.parseInt(bean.getData().toString());
            //获取未结算信息
            int page = 1;
            int pageSize = 20;
            JSONArray betResult = new JSONArray();
            while (pageSize >= 20 && count > betResult.size()) {
                JSONArray result=Ncom1MemberTool.getIsSettlePage(httpConfig, accountInfo.get("projectHost"),gameId, page);
                if(ContainerTool.isEmpty(result)){
                    break;
                }
                pageSize = result.size();
                page++;
                Ncom1MemberTool.matchIsSettleInfo(result,ballInfo,betItems,betResult,gameName,roundno);
            }
            //投注结果返回信息
            bean.success();
            bean.setData(betResult);
        } catch (Exception e) {
            log.error("Ncom1盘口会员【" + existHmId + "】投注失败,失败原因为：", e);
            bean.error(e.getMessage());
        } finally {
            httpConfig.defTimeOut();
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
        if(betInfo.containsKey("data")&&StringTool.isContains(betInfo.getString("data"),"login")){
            log.error("投注失败，错误信息="+betInfo);
            memberMap.remove(existHmId);
            bean.putEnum(HcCodeEnum.IBS_403_OTHER_PLACE_LOGIN);
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }
        //ok==1,投注成功
        int status = betInfo.getInteger("ok");
        if (status == 0) {
            String message = betInfo.getString("data");
            if (StringTool.contains(message, "停用", "冻结")) {
                bean.putEnum(HcCodeEnum.IBS_403_USER_BAN_BET);
            }else if(StringTool.contains(message, "单注限额", "单期限额")){
                bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
            }else if(StringTool.contains(message, "已经封盘","当前时间段不允许下注")){
                bean.putEnum(HcCodeEnum.IBS_403_SEAL_HANDICAP);
            }else{
                log.error("未知的错误信息="+betInfo);
                bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
            }
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }
        //获取用户信息
        Map<String, String> userInfo;
        if (userMap.containsKey(existHmId)) {
            userInfo = userMap.get(existHmId);
        } else {
            userInfo = new HashMap<>(6);
            userMap.put(existHmId, userInfo);
        }
        JSONObject data=betInfo.getJSONObject("data");

        if(data.containsKey("money")){
            //可用额度
            userInfo.put("usedQuota", String.valueOf(data.getDouble("money")));
        }
        bean.setData(data.getJSONObject("title").size());
        bean.success();
        return bean;
    }


    /**
     * 检验信息,上次成功检验时间超过两分钟，删除用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @return
     */
    @Override
    public JsonResultBeanPlus checkInfo(String existHmId) {
        synchronized (existHmId) {
            JsonResultBeanPlus bean;

            long lastTime;
            if (checkMap.containsKey(existHmId)) {
                lastTime = checkMap.get(existHmId);
            } else {
                lastTime = System.currentTimeMillis();
                checkMap.put(existHmId, lastTime);
            }
            GameUtil.Code gameCode = GameUtil.Code.PK10;
			String period =gameCode.getGameFactory().period(HandicapUtil.Code.NCOM1).findPeriod().toString();
            String betType = "dobleSides";

            boolean flag = System.currentTimeMillis() - lastTime > MIN_CHECK_TIME;
            //重新获取数据
            if(!userMap.containsKey(existHmId)||flag){
                //获取用户信息
                bean = userInfo(existHmId, gameCode, period, betType);
                //获取用户信息失败，返回空
                if (!bean.isSuccess()) {
                    return bean;
                }
            }else {
                //使用内存数据
                bean = new JsonResultBeanPlus().success(userMap.get(existHmId));
            }
            if (ContainerTool.isEmpty(bean.getData())) {
                if (System.currentTimeMillis() - lastTime > MAX_CHECK_TIME) {
                    memberMap.remove(existHmId);
                    checkMap.put(existHmId, System.currentTimeMillis());
                }
            }
            if (flag) {
                checkMap.put(existHmId, System.currentTimeMillis());
            }
            return bean;
        }
    }


}
