package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.BWConfig;
import com.ibm.common.utils.game.GameUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: BW会员工具类
 * @Author: null
 * @Date: 2020-01-06 16:18
 * @Version: v1.0
 */
public class BwMemberTool {
    protected static final Logger log = LogManager.getLogger(BwMemberTool.class);
    private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";
    private static final long SLEEP = 1000L;

    private static final Integer MAX_RECURSIVE_SIZE = 5;
    private static final String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";

    // TODO 开启页面函数

    /**
     * 获取线路页面
     *
     * @param httpConfig      请求配置类
     * @param handicapUrl     盘口url
     * @param handicapCaptcha 盘口验证码
     * @param index           循环次数
     * @return
     */
    public static String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl,
                                            String handicapCaptcha, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        //获取导航页面
        Map<String, Object> join = new HashMap<>(1);
        join.put("aqm1", handicapCaptcha);
        join.put("aqm", getAqmCode(handicapCaptcha));
        String routeHtml;
        try {
            routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl + "/Nav/weblist").map(join));
            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "tab_member_10")) {
                log.error("获取线路页面失败:{}", routeHtml);
                Thread.sleep(2 * SLEEP);
                return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
            }
        } catch (Exception e) {
            log.error("打开选择线路界面失败", e);
            Thread.sleep(2 * SLEEP);
            return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
        }
        //线路页面
        return routeHtml;
    }

    /**
     * 获取登录页面信息
     *
     * @param httpConfig http请求配置类
     * @param routes     线路
     * @param index      循环次数
     * @return
     */
    public static Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[2];
        }
        if (index[1] >= MAX_RECURSIVE_SIZE) {
            index[1] = 0;
            index[0]++;
        }
        if (index[0] >= routes.length) {
            return new HashMap<>(1);
        }
        Map<String, String> map = new HashMap<>(2);
        try {
            String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]]));
            if (StringTool.isEmpty(html) || !StringTool.isContains(html, "gamecity_index")) {
                log.error("获取登录页面失败,错误页面=" + html);
                Thread.sleep(2 * SLEEP);
                return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
            }
            String urlTop = StringTool.getLeft(routes[index[0]], "/user");
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(urlTop + "/User/login/index"));
            if (StringTool.isEmpty(html) || !StringTool.isContains(html, "登錄")) {
                log.error("获取登录页面失败,错误页面=" + html);
                Thread.sleep(2 * SLEEP);
                return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
            }
            String info = StringUtils.substringBetween(html, "name=\"txtVer\" value=\"", "\"");
            map.put("info", info);
            map.put("loginSrc", urlTop);
        } catch (Exception e) {
            log.error("获取登录页面失败", e);
            Thread.sleep(2 * SLEEP);
            return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
        }
        return map;
    }

    /**
     * 获取验证码
     *
     * @param httpConfig http请求配置类
     * @param loginSrc   登录线路
     * @param index      循环次数
     * @return
     */
    public static String getVerifyCode(HttpClientConfig httpConfig, String loginSrc, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String html;
        try {
            String content = HttpClientUtil.findInstance()
                    .getImage(httpConfig.url(loginSrc.concat("/Image/VerifyCodeN?r=") + RandomTool.getDouble()));
            if (StringTool.isEmpty(content)) {
                log.error("获取验证码失败");
                return getVerifyCode(httpConfig, loginSrc, ++index[0]);
            }
            Map<String, Object> join = new HashMap<>(2);
            join.put("type", "BW");
            join.put("content", content);

            html = HttpClientTool.doPost(CRACK_CONTENT, join);
            if (StringTool.isEmpty(html)) {
                log.error("破解验证码失败");
                return getVerifyCode(httpConfig, loginSrc, ++index[0]);
            }
            html = html.replace("\"", "");
            if (html.length() < 4) {
                return getVerifyCode(httpConfig, loginSrc, ++index[0]);
            }
        } catch (Exception e) {
            log.error("破解验证码失败", e);
            return getVerifyCode(httpConfig, loginSrc, ++index[0]);
        }
        return html;
    }

    /**
     * 盘口登录
     *
     * @param httpConfig     请求配置类
     * @param memberAccount  盘口账号
     * @param memberPassword 盘口密码
     * @param verifyCode     验证码
     * @param projectHost    线路地址
     * @param info           盘口登录信息
     * @param index          循环次数
     * @return
     */
    public static String getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
                                  String verifyCode, String projectHost, String info, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        Map<String, Object> join = new HashMap<>(1);
        String code = getLoginCode(memberAccount, memberPassword, verifyCode, info);
        join.put("code", code);
        String loginInfo;
        try {
            httpConfig.setHeader("Referer", projectHost + "/User/login/index");
            loginInfo = HttpClientUtil.findInstance()
                    .postHtml(httpConfig.url(projectHost.concat("/User/login/CheckLogin")).map(join));
            if (StringTool.isEmpty(loginInfo)) {
                log.error("获取登陆信息失败");
                return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, info, ++index[0]);
            }
        } catch (Exception e) {
            log.error("获取登陆信息失败", e);
            return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, info, ++index[0]);
        }
        //账号冻结,账户已禁用,第一次登陆
        if (StringTool.contains(loginInfo, "帳號或密碼錯誤", "冻结", "禁用", "修改密码")) {
            return loginInfo;
        }
        //验证码错误
        if (StringTool.isContains(loginInfo, "驗證碼錯誤")) {
            log.error("验证码错误");
            Thread.sleep(2 * SLEEP);
            verifyCode = getVerifyCode(httpConfig, projectHost);
            return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, info, ++index[0]);
        }
        //登录结果
        return loginInfo;
    }

    /**
     * 获取用户信息
     *
     * @param httpConfig
     * @param loginSrc
     * @param uid
     * @param index
     * @return
     */
    public static Map<String, String> getUserInfo(HttpClientConfig httpConfig, String loginSrc, String uid, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        Map<String, Object> join = new HashMap<>(1);
        join.put("uid", uid);

        String html;
        try {
            html = HttpClientUtil.findInstance()
                    .postHtml(httpConfig.url(loginSrc.concat("/User/Home/getuserinfo")).map(join));
            if (StringTool.isEmpty(html)) {
                Map<String, String> map = new HashMap<>();
                map.put("snatchMsg", "true");
                return map;
            }
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            Thread.sleep(2 * SLEEP);
            return getUserInfo(httpConfig, loginSrc, uid, ++index[0]);
        }
        return getUserInfo(html);

    }

    /**
     * 获取用户赔率信息
     * @param httpConfig
     * @param loginSrc
     * @param uid
     * @param index
     * @return
     */
    public static JSONArray getOddsInfo(HttpClientConfig httpConfig,GameUtil.Code gameCode, String loginSrc, String uid, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        Map<String, Object> join = new HashMap<>(1);
        join.put("gt", BWConfig.BET_CODE.get(gameCode.name()));
        join.put("grpid", 2);
        join.put("prekjqs", "");
        join.put("uid", uid);
        try {
            String html = HttpClientUtil.findInstance()
                    .postHtml(httpConfig.url(loginSrc.concat("/User/Bet/getplinfo")).map(join));
            if (StringTool.isEmpty(html)) {
                log.error("用户赔率信息为空");
                Thread.sleep(SLEEP);
                return getOddsInfo(httpConfig,gameCode, loginSrc, uid, ++index[0]);
            }
            if (StringTool.contains(html, "帳戶歷史", "近兩周")) {
                log.error("用户被冻结");
                return null;
            }
            JSONObject json = (JSONObject) JSONObject.parse(html);
            return json.getJSONArray("data");
        } catch (Exception e) {
            log.error("获取赔率失败", e);
            return getOddsInfo(httpConfig, gameCode,loginSrc, uid, ++index[0]);
        }

    }

    /**
     * 投注
     * @param httpConfig
     * @param projectHost
     * @param gameCode
     * @param betsMap
     * @param uid
     * @return
     */
    public static JSONObject beting(HttpClientConfig httpConfig, String projectHost, GameUtil.Code gameCode, Map<String, String> betsMap, String period, String uid) {
        Map<String, Object> join = new HashMap<>(1);
        join.put("gt", BWConfig.BET_CODE.get(gameCode.name()));
        join.put("qs",period);
        join.put("uPI_ID", betsMap.get("uPI_ID"));
        join.put("uPI_P", betsMap.get("uPI_P"));
        join.put("uPI_M", betsMap.get("uPI_M"));
        join.put("uid", uid);

        try {
            String html = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("/User/Bet/Betsave")).map(join));
            if(StringTool.isEmpty(html)){
                log.error("投注结果页面为空,投注项为：" + betsMap);
                return new JSONObject();
            }
            JSONObject json = (JSONObject) JSONObject.parse(html);
            return json;
        } catch (Exception e) {
            log.error("投注失败", e);
            return beting(httpConfig, projectHost,gameCode,betsMap,period,uid);
        }
    }

    /**
     * 获取未结算页面
     *
     * @param httpConfig  http请求配置类
     * @param page        页数
     * @param index       循环次数
     * @return
     */
    public static JSONArray getIsSettlePage(HttpClientConfig httpConfig, String loginSrc, String uid,
                                         int page, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > 5) {
            return new JSONArray();
        }
        String url = loginSrc.concat("/User/Betlist?UID=").concat(uid).concat("&page="+page);
        String html;
        try {
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
            if (StringTool.isEmpty(html)) {
                log.trace("未结算为空，重新获取");
                return getIsSettlePage(httpConfig, loginSrc, uid, page, ++index[0]);
            }
            if (StringTool.isContains(html, "暂无數據")) {
                log.trace("暂无数据");
                return new JSONArray();
            }
            return getSettleInfo(html);
        } catch (Exception e) {
            return getIsSettlePage(httpConfig, loginSrc, uid, page, ++index[0]);
        }
    }



    // TODO 功能函数

    /**
     * 解析未结算明细
     *
     * @param result    未结算结果
     * @param ballInfo  球号信息
     * @param betItems  投注信息
     * @param betResult 投注结果
     * @param gameName  游戏名称
     * @param roundno   期数字符串
     */
    public static void matchIsSettleInfo(JSONArray result,  Map<String, String> ballInfo, List<String> betItems,
                                         JSONArray betResult, String gameName, Object roundno) {
        for(int i=0;i<result.size();i++){
            JSONArray info=result.getJSONArray(i);
            //判断游戏类型和期数是否一致
            if (!gameName.equals(info.getString(1)) || !roundno.equals(info.getString(2))) {
                continue;
            }
            String item = ballInfo.get(info.getString(3)).concat("|").concat(String.valueOf(NumberTool.longValueT(info.getString(5))));

            if (!betItems.contains(item)) {
                continue;
            }
            JSONArray array = new JSONArray();
            //注单号
            array.add(info.getString(0));
            //投注项
            array.add(ballInfo.get(info.getString(3)));
            //投注金额
            array.add(info.getString(5));
            //赔率
            array.add(info.getString(4));
            betResult.add(array);
            if (betResult.size() >= betItems.size()) {
                break;
            }
        }
    }

    /**
     * 解析未结算页面
     * @param html      未结算信息
     * @return
     */
    private static JSONArray getSettleInfo(String html) {
        Document document = Jsoup.parse(html);
        Elements trElements = document.select("tbody>tr");
        trElements.remove(trElements.size()-1);
        trElements.remove(trElements.size()-1);
        trElements.remove(trElements.size()-1);
        trElements.remove(trElements.size()-1);
        trElements.remove(0);
        trElements.remove(0);

        JSONArray jsonArray=new JSONArray();
        for(Element tr:trElements){
            JSONArray array=new JSONArray();
            //注单号
            array.add(tr.child(0).text().split("#")[0]);
            //游戏类型
            String[] types=tr.child(2).text().replace("期","").split(" ");
            array.add(types[0]);
            //游戏期数
            array.add(types[1]);
            //投注项
            String[] content = tr.child(3).text().replace("『", "").replace("』", "").split("@");
            array.add(content[0].trim());
            //赔率
            array.add(content[1]);
            //投注金额
            array.add(tr.child(5).text());
            jsonArray.add(array);
        }
        return jsonArray;
    }

    /**
     * 获取投注详情内容
     *
     * @param ballCode 球号code
     * @param betItems 投注详情
     * @param odds     赔率
     * @return
     */
    public static Map<String, String> getBetItemInfo(Map<String, String> ballCode, List<String> betItems, Map<String,Object> odds) {
        Map<String, String> betsMap = new HashMap<>();

        List<Object> oddsId = new ArrayList<>();
        List<Object> uPIP = new ArrayList<>();
        List<Object> uPIM = new ArrayList<>();
        for (int i = 0; i <betItems.size() ; i++) {
            String betItem = betItems.get(i);
            String[] items = betItem.split("\\|");
            String bet = items[0].concat("|").concat(items[1]);
            //单注金额须为整数值
            int fund = (int) NumberTool.doubleT(items[2]);
            String betCode = ballCode.get(bet);
            oddsId.add(betCode);
            uPIP.add(odds.get(betCode));
            uPIM.add(fund);
        }
        betsMap.put("uPI_ID", org.apache.commons.lang.StringUtils.join(oddsId.toArray(), ","));
        betsMap.put("uPI_P", org.apache.commons.lang.StringUtils.join(uPIP.toArray(), ","));
        betsMap.put("uPI_M", org.apache.commons.lang.StringUtils.join(uPIM.toArray(), ","));
        return betsMap;
    }

    /**
     * 解析用户信息
     *
     * @param html
     * @return
     */
    private static Map<String, String> getUserInfo(String html) {
        Map<String, String> map = new HashMap<>();
        JSONObject json = (JSONObject) JSONObject.parse(html);

        //信用额度
        map.put("creditQuota", json.getJSONObject("info").getString("money_xy"));
        //使用金额
        map.put("usedQuota", json.getJSONObject("info").getString("money_ky"));
        //盈亏金额
        map.put("profitAmount", json.getJSONObject("info").getString("money_win"));
        return map;

    }

    /**
     * 获取盘口aqm码
     *
     * @param handicapCaptcha 盘口验证码
     * @return
     */
    private static Object getAqmCode(String handicapCaptcha) {
        int rand = RandomTool.getInt(100000);
        String code = rand + "|" + handicapCaptcha;
        return Base64.encodeBase64String(code.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取会员可用线路
     *
     * @param routeHtml 线路页面
     * @return
     */
    public static String[] getMemberRoute(String routeHtml) {
        List<String> routes = new ArrayList<>();
        Document document = Jsoup.parse(routeHtml);
        Elements elements = document.select("div#div_member_list table a");
        for (Element element : elements) {
            if (StringTool.isContains(element.text(), "会员")) {
                routes.add(element.attr("href"));
            }
        }
        return routes.toArray(new String[0]);
    }

    /**
     * 获取盘口登录code
     *
     * @param memberAccount  账号
     * @param memberPassword 密码
     * @param verifyCode     验证码
     * @param info           盘口登录信息
     * @return
     */
    private static String getLoginCode(String memberAccount, String memberPassword, String verifyCode, String info) {
        String code = info + "|" + memberAccount + "|" + memberPassword + "|" + verifyCode;
        return Base64.encodeBase64String(code.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 登陆错误
     *
     * @param msg 登陆结果页面
     * @return 错误信息
     */
    public static HcCodeEnum loginError(String msg) {
        log.error("SgWin盘口会员登陆异常，异常的登陆结果页面为：" + msg);
        if (StringTool.isContains(msg, "驗證碼錯誤")) {
            return HcCodeEnum.IBS_403_VERIFY_CODE;
        } else if (StringTool.contains(msg, "帳號已被鎖定", "帳號被鎖定", "帳號被停用", "凍結")) {
            return HcCodeEnum.IBS_403_USER_STATE;
        } else if (StringTool.contains(msg, "帳號或密碼錯誤", "密碼錯誤")) {
            return HcCodeEnum.IBS_403_USER_ACCOUNT;
        } else if (StringTool.contains(msg, "登陸成功", "User/ChangePwd")) {
            return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
        } else {
            return HcCodeEnum.IBS_403_UNKNOWN;
        }
    }


}
