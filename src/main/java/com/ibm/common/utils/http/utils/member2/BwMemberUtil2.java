package com.ibm.common.utils.http.utils.member2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.BWConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.member.BwMemberTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: BW会员工具类
 * @Author: null
 * @Date: 2020-01-06 16:05
 * @Version: v1.0
 */
public class BwMemberUtil2 extends BaseMemberUtil2 {

    private static volatile BwMemberUtil2 instance = null;

    public static BwMemberUtil2 findInstance() {
        if (instance == null) {
            synchronized (BwMemberUtil2.class) {
                if (instance == null) {
                    BwMemberUtil2 instance = new BwMemberUtil2();
                    // 初始化
                    instance.init();
                    BwMemberUtil2.instance = instance;
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
     * 登录
     *
     * @param existHmId       盘口会员存在id
     * @param memberAccount   会员账号
     * @param memberPassword  会员密码
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     * @return
     */
    @Override
    public JsonResultBeanPlus login(String existHmId, String memberAccount, String memberPassword,
                                    String handicapUrl, String handicapCaptcha) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //已存在数据
        if (memberMap.containsKey(existHmId)) {
            bean.setData(memberMap.get(existHmId));
            bean.success();
            return bean;
        }
        accountInfo(existHmId, memberAccount, memberPassword, handicapUrl, handicapCaptcha);
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
            log.error("BW盘口会员【" + existHmId + "】登录失败,失败原因为：", e);
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
     * @return
     */
    public JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
                                    String memberAccount, String memberPassword) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        httpConfig.headers(null);
        httpConfig.httpContext(null);
        try {
            //获取线路选择页面
            String routeHtml =BwMemberTool.getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha);
            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "tab_member_10")) {
                log.info("BW获取线路页面失败=" + routeHtml);
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }

            //获取会员线路
            String[] routes =BwMemberTool.getMemberRoute(routeHtml);
            if (ContainerTool.isEmpty(routes)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //选择登录线路
            Map<String, String> loginInfoMap =BwMemberTool.getLoginHtml(httpConfig, routes);
            if (ContainerTool.isEmpty(loginInfoMap)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            String loginSrc = loginInfoMap.get("loginSrc");
            String verifyCode =BwMemberTool.getVerifyCode(httpConfig, loginSrc);

            String loginInfo = BwMemberTool.getLogin(httpConfig, memberAccount, memberPassword, verifyCode, loginSrc, loginInfoMap.get("info"));
            if (StringTool.isEmpty(loginInfo)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            if (!StringTool.isContains(loginInfo, "登陸成功") || StringTool.isContains(loginInfo, "false")) {
                bean.putEnum(BwMemberTool.loginError(loginInfo));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }

            String uid = StringTool.getBetween(loginInfo,"=","\",");
            Map<String, String> userData = new HashMap<>(2);
            userData.put("projectHost", loginSrc);
            userData.put("memberAccount", memberAccount);
            userData.put("uid",uid);

            bean.setData(userData);
            bean.success();
        } catch (Exception e) {
            log.error("BW盘口账号【" + memberAccount + "】登录失败,失败原因为：", e);
            bean.error(e.getMessage());
        } finally {
            httpConfig.defTimeOut();
        }
        return bean;

    }

    /**
     * 验证登录
     *
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     * @param memberAccount   会员账号
     * @param memberPassword  会员密码
     * @return
     */
    @Override
    public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String memberAccount, String memberPassword) {
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
     * 获取用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @param flag      执行状态
     * @return
     */
    @Override
    public Map<String, String> getUserInfo(String existHmId, boolean flag) {
        if(!userMap.containsKey(existHmId)||flag){
            //获取用户信息
            JsonResultBeanPlus bean = userInfo(existHmId,flag);
            //获取用户信息失败，返回空
            if (!bean.isSuccess()) {
                return new HashMap<>(1);
            }
        }
        return userMap.get(existHmId);
    }

    private JsonResultBeanPlus userInfo(String existHmId, boolean flag, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] >= MAX_RECURSIVE_SIZE) {
            log.error("BW盘口会员【" + existHmId + "】用户基本信息获取失败！");
            return null;
        }
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        // 获取用户信息
        if (!memberMap.containsKey(existHmId) || flag) {
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

        try {
            Map<String, String> userInfo = new HashMap<>();
            if (userMap.containsKey(existHmId)) {
                userInfo = userMap.get(existHmId);
            }
            String uid = userMap.get(existHmId).get("uid");
            // 获取登录信息
            Map<String, String> grabUserInfo = BwMemberTool.getUserInfo(httpConfig, accountInfo.get("projectHost"),uid);

            if(ContainerTool.isEmpty(grabUserInfo)){
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            if(grabUserInfo.containsKey("snatchMsg")){
                // 登陸超時、抢登、請重新登陸
                memberMap.remove(existHmId);
                userMap.remove(existHmId);
                return userInfo(existHmId,flag,++index[0]);
            }
            userInfo.putAll(grabUserInfo);
            userMap.put(existHmId, userInfo);

            bean.success();
            bean.setData(userInfo);
        } catch (Exception e) {
            log.error("Bw盘口会员【" + existHmId + "】获取基本信息异常，失败原因为:", e);
            bean.error(e.getMessage());
        }
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
            boolean flag = System.currentTimeMillis() - lastTime > MIN_CHECK_TIME;
            //重新获取数据
            if(!userMap.containsKey(existHmId)||flag){
                //获取用户信息
                bean = userInfo(existHmId, flag);
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


    /**
     * 获取难度过大，int默认值
     * 获取游戏限额
     * @return 游戏限额
     */
    @Override
    public JsonResultBeanPlus getQuotaList(String existHmId, GameUtil.Code gameCode) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        JSONArray gameLimit = new JSONArray();
        JSONArray arr = new JSONArray();
        arr.add(Integer.MIN_VALUE);
        arr.add(Integer.MAX_VALUE);
        arr.add(Integer.MAX_VALUE);
        gameLimit.add(arr);
        bean.setData(gameLimit);
        bean.success();

        return bean;
    }

    /**
     * 获取盘口赔率
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @param betType   投注类型
     * @return
     */
    public  void getOddsInfo(String existHmId, GameUtil.Code gameCode, String betType, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] >= MAX_RECURSIVE_SIZE) {
            log.error("Bw盘口会员【" + existHmId + "】获取赔率信息失败");
            return;
        }
        if (!memberMap.containsKey(existHmId)) {
            JsonResultBeanPlus bean = login(existHmId);
            if (!bean.isSuccess()) {
                log.error("Bw盘口会员【" + existHmId + "】重新登录失败");
                return;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        Map<String, String> accountInfo = memberMap.get(existHmId);
        if (ContainerTool.isEmpty(accountInfo)) {
            log.info("Bw盘口会员【" + existHmId + "】会员信息为空");
            return ;
        }
        //赔率信息
        Map<String, Object> odds;
        if (!oddsMap.containsKey(existHmId)) {
            Map<GameUtil.Code, Map<String, Object>> map = new HashMap<>(10);
            odds = new HashMap(3);
            map.put(gameCode, odds);
            oddsMap.put(existHmId, map);
        } else {
            if (!oddsMap.get(existHmId).containsKey(gameCode)) {
                odds = new HashMap<>(3);
                oddsMap.get(existHmId).put(gameCode, odds);
            } else {
                odds = oddsMap.get(existHmId).get(gameCode);
            }
        }
        HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHmId);
        try {
            String uid = userMap.get(existHmId).get("uid");
            // 获取赔率信息
            JSONArray data = BwMemberTool.getOddsInfo(httpConfig,gameCode, accountInfo.get("projectHost"),uid);
            Map<String,Object> newOdds = new HashMap<>();
            for (int i = 0; i <data.size() ; i++) {
                JSONObject odd = (JSONObject) data.get(i);
                newOdds.put((String) odd.get("gid"),odd.get("ov"));
            }
            odds.put(betType, newOdds);

            return;
        } catch (Exception e) {
            log.error("Bw盘口会员【" + existHmId + "】获取盘口赔率失败,失败原因为：", e);
        }
    }

    /**
     * 投注
     *
     * @param existHmId  已存在id
     * @param gameCode   游戏code
     * @param betItems   投注项
     * @param betType    投注类型
     * @return 投注结果
     */
    public JsonResultBeanPlus betting(String existHmId, GameUtil.Code gameCode, List<String> betItems,Object roundno, String betType) {
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
        try {
            String uid = userMap.get(existHmId).get("uid");
            Map<String,Object> odds = (Map<String, Object>) oddsMap.get(existHmId).get(gameCode).get(betType);
            //获取投注信息
            Map<String, String> ballCode = BallCodeTool.getBallCode(HandicapUtil.Code.BW, gameCode);
            Map<String,String> betsMap = BwMemberTool.getBetItemInfo(ballCode, betItems, odds);
            if (ContainerTool.isEmpty(betsMap)) {
                bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            String period = roundno.toString().replace("-","");
            JSONObject betInfo =  BwMemberTool.beting(httpConfig, accountInfo.get("projectHost"), gameCode,betsMap,period,uid);
            log.trace("Bw盘口会员【" + existHmId + "】投注结果为：" + betInfo);
            //处理投注结果
            bean = resultProcess(existHmId,betInfo);
            if (!bean.isSuccess()) {
                return bean;
            }
            String gameName= BWConfig.GAME_NAME.get(gameCode.name());
            Map<String, String> ballInfo = BallCodeTool.getBallInfoCode(HandicapUtil.Code.BW, gameCode);
            // 投注成功数
            int count=betInfo.getJSONArray("newpldata").size();
            //获取未结算信息
            int page = 1;
            int pageSize = 20;
            JSONArray betResult = new JSONArray();
            while (pageSize >= 20 && count > betResult.size()) {
                JSONArray result = BwMemberTool.getIsSettlePage(httpConfig,accountInfo.get("projectHost"),uid,page);
                if(ContainerTool.isEmpty(result)){
                    break;
                }
                pageSize = result.size();
                page++;
                BwMemberTool.matchIsSettleInfo(result,ballInfo,betItems,betResult,gameName,period);
            }
            //投注结果返回信息
            bean.success();
            bean.setData(betResult);
        } catch (Exception e) {
            log.error("SgWin盘口会员【" + existHmId + "】投注失败,失败原因为：", e);
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
        if(betInfo.containsKey("success")&& "false".equals(betInfo.getString("success"))){
            log.error("投注失败，错误信息="+betInfo);
            memberMap.remove(existHmId);
            bean.putEnum(HcCodeEnum.IBS_403_OTHER_PLACE_LOGIN);
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }
        //ok==0,投注成功
        int status = betInfo.getInteger("status");
        if (status != 0) { //msg: "<font color='red'>下注金額低于最低限額</font>"
            String message = betInfo.getString("msg");
            if(StringTool.contains(message, "限額")){
                bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
            }else if(StringTool.contains(message, "當前彩種無開盤")){
                bean.putEnum(HcCodeEnum.IBS_403_SEAL_HANDICAP);
            }else{
                log.error("未知的错误信息="+betInfo);
                bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
            }
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }

        bean.success();
        return bean;
    }



}
