//package com.ibm.common.test.wwj.handicap.sgwin;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.ibm.common.core.JsonResultBeanPlus;
//import com.ibm.common.core.configs.SgWinConfig;
//import com.ibm.common.enums.IbmHcCodeEnum;
//import com.ibm.common.test.wwj.handicap.AbsMemberCrawler;
//import com.ibm.common.test.wwj.handicap.DataCustomer;
//import com.ibm.common.utils.HandicapUtil;
//import com.ibm.common.utils.game.GameUtil;
//import com.ibm.common.utils.game.tools.BallCodeTool;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.doming.core.tools.ContainerTool;
//import org.doming.core.tools.StringTool;
//import org.doming.develop.http.httpclient.HttpClientConfig;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Description:
// * @Author: wwj
// * @Date: 2019/12/3 16:28
// * @Email: 97085010@qq.com
// * @Version: v1.0
// */
//public class SgWinMemberCrawler  extends AbsMemberCrawler<SgWinMemberGrab> {
//
//    public SgWinMemberCrawler() {
//        super.handicap(HandicapUtil.Code.SGWIN);
//    }
//
//    /**
//     * 登陆
//     *
//     * @param httpConfig      请求配置类
//     * @param handicapUrl     盘口地址
//     * @param handicapCaptcha 盘口验证码
//     * @param account         盘口账户
//     * @param password        盘口密码
//     * @return 登录结果
//     */
//    @Override
//    protected JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, String account, String password) {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        //如果盘口地址不包含http://则添加
//        String http = "http://";
//        if (!handicapUrl.startsWith(http) && !handicapUrl.startsWith("https://")) {
//            handicapUrl = http.concat(handicapUrl);
//        }
//        httpConfig.headers(null);
//        httpConfig.httpContext(null);
//        try {
//            //获取线路选择页面
//            String routeHtml = grab.selectRoute(httpConfig, handicapUrl, handicapCaptcha);
//            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "线路选择")) {
//                log.info("SgWin获取线路页面失败=" + routeHtml);
//                bean.putEnum(IbmHcCodeEnum.IBM_403_PAGE_NAVIGATE);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//                return bean;
//            }
//            //4条线路数组
//            String[] routes = grab.routes(httpConfig, routeHtml);
//            if (ContainerTool.isEmpty(routes)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_ROUTE);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            //获取登录信息map
//            Map<String, String> loginInfoMap = grab.loginInfo(httpConfig, routes);
//            if (ContainerTool.isEmpty(loginInfoMap)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            String loginSrc = loginInfoMap.get("loginSrc");
//            //获取验证码
//            String verifyCode = grab.getVerifyCode(httpConfig, loginSrc, loginInfoMap.remove("code"));
//
//            httpConfig.setHeader("Referer", loginSrc.concat("login"));
//            httpConfig.httpContext(HttpClientContext.create());
//            //盘口协议页面
//            String loginInfo = grab.login(httpConfig, loginSrc.concat(loginInfoMap.remove("action")), verifyCode, account, password);
//
//            if (StringTool.isEmpty(loginInfo)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            //错误处理和是否等一次登录盘口
//            if (StringTool.contains(loginInfo, "alert", "修改密码")) {
//                bean.putEnum(grab.loginError(loginInfo));
//                bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//                return bean;
//            }
//            Map<String, String> userData = new HashMap<>(5);
//
//            //通过index主页面获取用户信息
//            String indexHtml = grab.home(httpConfig, loginSrc);
//            if (ContainerTool.notEmpty(indexHtml)) {
//                Document document = Jsoup.parse(indexHtml);
//                String accountInfo = document.getElementsByClass("inline-name").text();
//                //会员盘
//                userData.put("memberType", accountInfo.split(" ")[1]);
//                //信用额度
//                userData.put("creditQuota", document.getElementsByClass("balance").first().text());
//                //使用金额
//                userData.put("usedAmount", document.getElementsByClass("betting").first().text());
//            }
//            httpConfig.setHeader("Referer", loginSrc.concat("member/index"));
//
//            userData.put("projectHost", loginSrc);
//            //会员账户
//            userData.put("memberAccount", account);
//
//            bean.setData(userData);
//            bean.success();
//        } catch (Exception e) {
//            log.error(getMsgTitle() + "账号【" + account + "】登录失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        } finally {
//            httpConfig.defTimeOut();
//        }
//        return bean;
//    }
//
//    /**
//     * 用户基本信息
//     *
//     * @return 用户信息
//     */
//    @Override
//    protected JsonResultBeanPlus userInfo() {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        if(member.customer()==null){
//            bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//            bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//            return bean;
//        }
//        try {
//            DataCustomer customer = member.customer();
//            if (customer.crawler() == null) {
//                bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
//                if (!bean.isSuccess()) {
//                    return bean;
//                }
//                bean.setSuccess(false);
//            }
//
//            JSONObject userObj = grab.getUserInfo(customer.httpConfig(), customer.crawler().get("projectHost"));
//            if(ContainerTool.isEmpty(userObj)){
//                log.info(getMsgTitle()+"获取会员账号信息失败");
//                bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//
//            Map<String, String> userInfo = new HashMap<>(4);
//            //信用额度
//            userInfo.put("creditQuota", String.valueOf(userObj.getDouble("maxLimit")));
//            //可用额度
//            userInfo.put("usedQuota", String.valueOf(userObj.getDouble("balance")));
//            //使用金额
//            userInfo.put("usedAmount", String.valueOf(userObj.getDouble("betting")));
//            //盈亏金额
//            userInfo.put("profitAmount",
//                    String.valueOf(userObj.containsKey("result") ? userObj.getDouble("result") : 0));
//            member.userMap(userInfo);
//
//            bean.success(userInfo);
//        } catch (Exception e) {
//            log.error(getMsgTitle()+"获取会员账号信息失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        }
//        return bean;
//    }
//    /**
//     * 某个游戏的限额信息
//     *
//     * @param gameCode 游戏编码
//     * @return 游戏限额信息
//     */
//    @Override
//    public JsonResultBeanPlus gameLimit(GameUtil.Code gameCode) {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        if(member.customer()==null){
//            bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//            bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//            return bean;
//        }
//        try {
//            DataCustomer customer = member.customer();
//            if (customer.crawler() == null) {
//                bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
//                if (!bean.isSuccess()) {
//                    return bean;
//                }
//                bean.setSuccess(false);
//            }
//            //获取盘口游戏code
//            String game = SgWinConfig.BET_CODE.get(gameCode.name());
//            JSONArray gameLimit = grab.getGameLimit(customer.httpConfig(), customer.crawler().get("projectHost"), game);
//            log.trace(getMsgTitle()+"游戏【" + game + "】限额信息为：" + gameLimit);
//            if (ContainerTool.isEmpty(gameLimit)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_404_BET_LIMIT);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            bean.success(gameLimit);
//        } catch (Exception e) {
//            log.error(getMsgTitle()+"获取游戏限额信息失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        }
//        return bean;
//    }
//
//    /**
//     * 游戏赔率信息
//     *
//     * @param gameCode 游戏编码
//     * @param betType
//     * @return 游戏赔率信息
//     */
//    @Override
//    public JsonResultBeanPlus oddsInfo(GameUtil.Code gameCode, String betType) {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        if(member.customer()==null){
//            bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//            bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//            return bean;
//        }
//        try {
//            DataCustomer customer = member.customer();
//            if (customer.crawler() == null) {
//                bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
//                if (!bean.isSuccess()) {
//                    return bean;
//                }
//                bean.setSuccess(false);
//            }
//            //获取盘口游戏code
//            String game = SgWinConfig.BET_CODE.get(gameCode.name());
//            //赔率信息
//            Map<GameUtil.Code, Map<String, Object>> userOdds = member.oddsMap();
//            Map<String, Object> odds;
//            if(userOdds == null){
//                userOdds = new HashMap<>();
//                odds = new HashMap<>();
//                userOdds.put(gameCode,odds);
//            }else{
//                odds = userOdds.get(game);
//            }
//            String oddsCode = BallCodeTool.getGameOddsCode(HandicapUtil.Code.SGWIN, gameCode, betType);
//
//            JSONObject oddsInfo = grab.getOddsInfo(customer.httpConfig(),customer.crawler().get("projectHost"),game,oddsCode);
//            if (ContainerTool.isEmpty(oddsInfo)) {
//                log.info(getMsgTitle()+"游戏【" + gameCode + "】获取赔率信息失败");
//                bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            odds.put(betType, oddsInfo);
//            userOdds.put(gameCode,odds);
//            member.oddsMap(userOdds);
//
//            bean.success(odds);
//        } catch (Exception e) {
//            log.error(getMsgTitle()+"获取盘口赔率失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        }
//        return bean;
//    }
//
//    /**
//     * 投注
//     *
//     * @param gameCode 游戏编码
//     * @param betItems 投注项列表
//     * @param odds     赔率信息
//     * @param period   期数
//     * @return 投注结果
//     */
//    @Override
//    public JsonResultBeanPlus betting(GameUtil.Code gameCode, List<String> betItems, Object odds, String period) {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        if(member.customer()==null){
//            bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//            bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//            return bean;
//        }
//        DataCustomer customer = member.customer();
//        HttpClientConfig httpConfig = customer.httpConfig();
//        httpConfig.httpTimeOut(20 * 1000);
//        try {
//            if (customer.crawler() == null) {
//                bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
//                if (!bean.isSuccess()) {
//                    return bean;
//                }
//                bean.setSuccess(false);
//            }
//            //获取盘口游戏code
//            String game = SgWinConfig.BET_CODE.get(gameCode.name());
//            JSONArray betsArray = grab.getBetItemInfo(gameCode, betItems, (JSONObject)odds);
//            if (ContainerTool.isEmpty(betsArray)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_403_BET_FAIL);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//                return bean;
//            }
//
//            JSONObject betInfo = grab.betting(httpConfig, customer.crawler().get("projectHost"), betsArray, period, game);
//            //投注结果
//            log.trace(getMsgTitle()+" 投注结果为：" + betInfo);
//            if (ContainerTool.isEmpty(betInfo)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_403_BET_FAIL);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//                return bean;
//            }
//            if (betInfo.containsKey("message")) {
//                if (StringTool.isContains(betInfo.getString("message"), "已被上级禁止投注，请与上级联系")) {
//                    bean.putEnum(IbmHcCodeEnum.IBM_403_USER_BAN_BET);
//                    bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//                    return bean;
//                }
//                if (StringTool.contains(betInfo.getString("message"), "最高", "最低")) {
//                    bean.putEnum(IbmHcCodeEnum.IBM_403_MORE_THAN_LIMIT);
//                    bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//                    return bean;
//                }
//            }
//            //注单号
//            JSONArray ids = betInfo.getJSONArray("ids");
//            if (ContainerTool.isEmpty(ids)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_403_BET_FAIL);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//                return bean;
//            }
//            //获取未结算信息
//            String isSettle = grab.getIsSettle(httpConfig, customer.crawler().get("projectHost"), game);
//            if (StringTool.isEmpty(isSettle)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_403_PAGE_FAIL);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//                return bean;
//            }
//            //投注成功项信息
//            JSONArray betResult = grab.getBetResult(gameCode, isSettle, ids.toString());
//            if (ContainerTool.isEmpty(betResult)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_403_BET_FAIL);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//                return bean;
//            }
//            //获取用户信息
//            Map<String, String> userInfo = new HashMap<>(4);
//
//            JSONObject memberInfo = betInfo.getJSONObject("account");
//            //信用额度
//            userInfo.put("creditQuota", String.valueOf(memberInfo.getDouble("maxLimit")));
//            //可用额度
//            userInfo.put("usedQuota", String.valueOf(memberInfo.getDouble("balance")));
//            //使用金额
//            userInfo.put("usedAmount", String.valueOf(memberInfo.getDouble("betting")));
//            //盈亏金额
//            userInfo.put("profitAmount",
//                    String.valueOf((userInfo.containsKey("result") ? memberInfo.getDouble("result") : 0)));
//            member.userMap(userInfo);
//            //投注结果返回信息
//            bean.success(betResult);
//        } catch (Exception e) {
//            log.error(getMsgTitle()+" 投注失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        } finally {
//            httpConfig.defTimeOut();
//        }
//        return bean;
//    }
//}
