package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 新com1盘口会员工具类
 * @Author: null
 * @Date: 2019-12-26 15:50
 * @Version: v1.0
 */
public class Ncom1MemberTool {
    protected static final Logger log = LogManager.getLogger(Ncom1MemberTool.class);
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
    public static String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
                                            int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String routeHtml;
        try {
            httpConfig.redirectsEnabled(false);
            HttpResult result = HttpClientUtil.findInstance().get(httpConfig.url(handicapUrl));
            handicapUrl = StringTool.notEmpty(result.getLocation()) ?result.getLocation() : handicapUrl;
            String navigationHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));
            if (StringTool.isEmpty(navigationHtml) || !StringTool.isContains(navigationHtml, "登 錄")) {
                log.error("获取导航页面失败:{}", navigationHtml);
                Thread.sleep(2 * SLEEP);
                return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
            }
            Map<String, Object> map = new HashMap<>(2);
            map.put("code", handicapCaptcha);
            map.put("Submit", "登 錄");
            routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl).map(map));
            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "線路選擇")) {
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
     * 获取登录页面
     *
     * @param httpConfig http请求配置类
     * @param routes     线路
     * @param index      循环次数
     * @return 登录信息map
     */
    public static String getLoginHtml(HttpClientConfig httpConfig, String[] routes, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[2];
        }
        if (index[1] > MAX_RECURSIVE_SIZE) {
            index[0]++;
        }
        if (index[0] >= routes.length) {
            return null;
        }
        String html;
        String loginSrc;
        try {
            //获取登录页面
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]]));
            if (StringTool.isEmpty(html) || !StringTool.isContains(html, "用戶登錄")) {
                log.error("打开登录页面失败", html);
                Thread.sleep(2 * SLEEP);
                return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
            }
            loginSrc = routes[index[0]];

        } catch (Exception e) {
            log.error("打开登录页面失败", e);
            Thread.sleep(2 * SLEEP);
            return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
        }
        return loginSrc;
    }

    /**
     * 盘口登录
     *
     * @param httpConfig     http请求配置类
     * @param memberAccount  盘口会员账号
     * @param memberPassword 盘口会员密码
     * @param projectHost    线路地址
     * @param index          循环次数
     * @return 盘口主页面href
     */
    public static String getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
                                  String projectHost, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        Map<String, Object> join = new HashMap<>(2);
        join.put("account", memberAccount);
        join.put("password", memberPassword);
        String loginInfo;
        try {
            //登录信息
            String loginUrl = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("pub/login/")).map(join));
            if (StringTool.isEmpty(loginUrl)) {
                log.error("获取登陆信息失败");
                Thread.sleep(2 * SLEEP);
                return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
            }
            //账号冻结,账户已禁用,第一次登陆
            if (StringTool.contains(loginUrl, "账号或密码错误", "抱歉!你的帐号已被冻结", "此账号上级已被禁用", "修改密码")) {
                return loginUrl;
            }
            if (!StringTool.isContains(loginUrl, "top.location.href")) {
                log.error("获取登陆信息失败,错误信息="+loginUrl);
                Thread.sleep(2 * SLEEP);
                return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
            }
            String forwordUrl = StringUtils.substringBetween(loginUrl, "top.location.href = \"", "\";");

            loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat(forwordUrl)).map(join));
            if (StringTool.isEmpty(loginInfo) || !StringTool.isContains(loginInfo, "用戶協議與規則")) {
                log.error("登录失败，错误的页面=" + loginInfo);
                Thread.sleep(2 * SLEEP);
                return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
            }
        } catch (Exception e) {
            log.error("获取登陆信息失败");
            Thread.sleep(2 * SLEEP);
            return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
        }
        //登录结果
        return loginInfo;
    }
    /**
     * 获取主页面
     * @param httpConfig    http请求配置类
     * @param projectHost   主机地址
     * @param index         循环次数
     * @return
     */
    public static void getIndex(HttpClientConfig httpConfig, String projectHost,int... index)
            throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return ;
        }
        try {
            //登录信息
            String loginUrl = HttpClientUtil.findInstance().getHtml(httpConfig.url(projectHost));
            if (StringTool.isEmpty(loginUrl)) {
                log.error("获取主页面信息失败");
                Thread.sleep(2 * SLEEP);
                getIndex(httpConfig, projectHost, ++index[0]);
            }
            //页面token
            String token = StringUtils.substringBetween(loginUrl, "var token = '", "';");
            if(StringTool.notEmpty(token)){
                httpConfig.setHeader("token",token);
            }
            httpConfig.setHeader("referer",projectHost);
        } catch (Exception e) {
            log.error("获取主页面失败");
            Thread.sleep(2 * SLEEP);
            getIndex(httpConfig, projectHost, ++index[0]);
        }
    }

    /**
     * 获取用户信息
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param index       循环次数
     * @return 用户信息
     */
    public static JSONObject getUserInfo(HttpClientConfig httpConfig, String projectHost, int... index)
            throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] >= MAX_RECURSIVE_SIZE) {
            return new JSONObject();
        }
        String html = null;
        String url = projectHost.concat("index/info?_=") + System.currentTimeMillis();
        try {
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
            if (StringTool.isEmpty(html)) {
                log.error("获取用户信息失败，结果信息=" + html);
                Thread.sleep(SLEEP);
                return getUserInfo(httpConfig, projectHost, ++index[0]);
            }
            //被挤下线处理
            if(StringTool.contains(html,"用戶登錄","您的账号已在其它地方登录")){
                log.error("获取用户信息失败",html);
                JSONObject data=new JSONObject();
                data.put("data","login");
                return data;
            }
            //盘口“ok"状态
            JSONObject result=JSONObject.parseObject(html);
            if(result.getInteger("ok")!=1){
                log.error("获取用户信息失败，结果信息=" + html);
                Thread.sleep(SLEEP);
                return getUserInfo(httpConfig, projectHost, ++index[0]);
            }
            return result.getJSONObject("data");
        } catch (Exception e) {
            log.error("获取用户余额信息失败,错误信息=" + html, e);
            Thread.sleep(SLEEP);
            return getUserInfo(httpConfig, projectHost, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
        }
    }

    /**
     * 获取赔率信息
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param gameId      盘口游戏id
     * @param period      期数字符串
     * @param type        类型
     * @param index       循环次数
     * @return
     */
    public static JSONObject getOddsInfo(HttpClientConfig httpConfig, String projectHost, String gameId,
                                         String period, String type, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return new JSONObject();
        }
        String html = null;
        Map<String, Object> join = new HashMap<>(3);
        join.put("game_id", gameId);
        join.put("last_prize_issue", period);
        join.put("type", type);
        String url = projectHost.concat("order/info/");
        try {
            html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
            if (ContainerTool.isEmpty(html)) {
                log.error("获取游戏赔率失败");
                Thread.sleep(SLEEP);
                return getOddsInfo(httpConfig, projectHost, gameId, period, type, ++index[0]);
            }
            //被挤下线处理
            if(StringTool.contains(html,"用戶登錄","您的账号已在其它地方登录")){
                log.error("获取赔率信息信息失败",html);
                JSONObject data=new JSONObject();
                data.put("data","login");
                return data;
            }
            //盘口“ok"状态
            JSONObject result=JSONObject.parseObject(html);
            if(result.getInteger("ok")!=1){
                log.error("获取游戏赔率失败,结果信息="+html);
                Thread.sleep(SLEEP);
                return getOddsInfo(httpConfig, projectHost, gameId, period, type, ++index[0]);
            }
            return result.getJSONObject("data");
        } catch (Exception e) {
            log.error("获取游戏赔率失败,结果信息=" + html, e);
            Thread.sleep(SLEEP);
            return getOddsInfo(httpConfig, projectHost, gameId, period, type, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
        }
    }

    /**
     * 获取游戏限额
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param gameId      盘口游戏code
     * @param index       循环次数
     * @return 游戏限额信息
     */
    public static JSONArray getQuotaList(HttpClientConfig httpConfig, String projectHost,
                                         String gameId, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return new JSONArray();
        }
        String html = null;

        String url = projectHost.concat("user/?game_id=").concat(gameId).concat("&_=") + System.currentTimeMillis();
        try {
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
            if (StringTool.isEmpty(html)) {
                log.error("获取游戏限额失败");
                Thread.sleep(SLEEP);
                return getQuotaList(httpConfig, projectHost, gameId, ++index[0]);
            }
            //被挤下线处理
            if(StringTool.contains(html,"用戶登錄","您的账号已在其它地方登录")){
                log.error("获取游戏限额信息失败",html);
                return new JSONArray();
            }
            if (!StringTool.isContains(html, "信用資料")) {
                log.error("获取游戏限额页面失败，错误页面=" + html);
                Thread.sleep(SLEEP);
                return getQuotaList(httpConfig, projectHost, gameId, ++index[0]);
            }
            return getLimit(html);
        } catch (Exception e) {
            log.error("获取游戏限额失败,错误信息=" + html, e);
            Thread.sleep(SLEEP);
            return getQuotaList(httpConfig, projectHost, gameId, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
        }
    }

    /**
     * 投注
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param betItem     投注信息
     * @param roundno     开奖期数
     * @param gameId      游戏id
     * @return
     */
    public static JSONObject betting(HttpClientConfig httpConfig, String projectHost, String betItem,
                                     String roundno, String gameId) {
        String html = null;
        Map<String, Object> join = new HashMap<>(3);
        join.put("game_id", gameId);
        join.put("issue", roundno);
        join.put("items", betItem);
        String url = projectHost.concat("order/order");
        try {
            html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
            if (ContainerTool.isEmpty(html)) {
                log.error("投注结果页面为空,投注项为：" + betItem);
                return new JSONObject();
            }
            //被挤下线处理
            if(StringTool.contains(html,"用戶登錄","您的账号已在其它地方登录")){
                JSONObject data=new JSONObject();
                data.put("data","login");
                return data;
            }
            return JSONObject.parseObject(html);
        } catch (Exception e) {
            log.error("投注失败,结果信息=" + html, e);
            return new JSONObject();
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, join, 0, html));
        }
    }

    /**
     * 获取未结算页面
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param gameId      游戏id
     * @param page        页数
     * @param index       循环次数
     * @return
     */
    public static JSONArray getIsSettlePage(HttpClientConfig httpConfig, String projectHost, String gameId,
                                             int page, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return new JSONArray();
        }
        String url = projectHost.concat("game/bet?game_id=").concat(gameId).concat("&&page=")
                .concat(String.valueOf(page)).concat("&_=") + System.currentTimeMillis();
        String html = null;
        try {
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
            if (StringTool.isEmpty(html)) {
                log.error("获取未结算页面失败", html);
                Thread.sleep(SLEEP);
                return getIsSettlePage(httpConfig, projectHost, gameId, page, ++index[0]);
            }
            if (StringTool.isContains(html, "無未結算記錄")) {
                return new JSONArray();
            }
            return getSettleInfo(html);
        } catch (Exception e) {
            log.error("获取未结算页面失败", e);
            Thread.sleep(SLEEP);
            return getIsSettlePage(httpConfig, projectHost, gameId, page, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
        }
    }


    // TODO 功能函数

    /**
     * 获取会员可用线路
     *
     * @param httpConfig http请求配置类
     * @param routeHtml  线路页面
     * @return 线路数组
     */
    public static String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml) {
        Document document = Jsoup.parse(routeHtml);

        Elements as = document.select("div.left1>div.box>ul>li>a");

        List<String> hrefs = new ArrayList<>();
        for (Element a : as) {
            hrefs.add(a.attr("href"));
        }
        String[] routes = new String[hrefs.size()];
        long[] arr = new long[hrefs.size()];

        //判断时间延迟
        long t1, t2;
        for (int i = 0; i < hrefs.size(); i++) {
            t1 = System.currentTimeMillis();
            String href = hrefs.get(i).concat("/");
            HttpClientUtil.findInstance().getHtml(httpConfig.url(href.concat("?random-no-cache=") + Math.random()));

            t2 = System.currentTimeMillis();
            long myTime = t2 - t1;
            routes[i] = href;
            arr[i] = myTime;
        }
        //线路按延时从小到大排序
        for (int x = 0; x < arr.length; x++) {
            for (int j = 0; j < arr.length - x - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    long t = arr[j];
                    String route = routes[j];

                    arr[j] = arr[j + 1];
                    routes[j] = routes[j + 1];

                    arr[j + 1] = t;
                    routes[j + 1] = route;
                }
            }
        }
        return routes;
    }

    /**
     * 登陆错误
     *
     * @param msg 登陆结果页面
     * @return 错误信息
     */
    public static HcCodeEnum loginError(String msg) {
        log.error("Ncom1盘口会员登陆异常，异常的登陆结果页面为：" + msg);
        if (StringTool.contains(msg, "已被冻结", "已被禁用")) {
            return HcCodeEnum.IBS_403_USER_STATE;
        } else if (StringTool.contains(msg, "账号或密码错误")) {
            return HcCodeEnum.IBS_403_USER_ACCOUNT;
        } else if (StringTool.contains(msg, "修改密码")) {
            return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
        } else if (StringTool.contains(msg, "变更密码")) {
            return HcCodeEnum.IBS_403_PASSWORD_EXPIRED;
        } else {
            return HcCodeEnum.IBS_403_UNKNOWN;
        }
    }

    /**
     * 解析页面
     *
     * @param html
     * @return
     */
    private static JSONArray getLimit(String html) {
        Document document = Jsoup.parse(html);
        Elements trs = document.select("tbody.hover_list>tr");

        JSONArray limit = new JSONArray();
        for (Element tr : trs) {
            JSONArray array = new JSONArray();
            array.add("1");
            array.add(tr.child(2).text());
            array.add(tr.child(3).text());
            limit.add(array);
        }
        return limit;
    }

    /**
     * 获取投注详情内容
     *
     * @param gameCode 游戏code
     * @param betItems 投注详情
     * @param oddsObj  赔率
     * @return
     */
    public static String getBetItemInfo(GameUtil.Code gameCode, List<String> betItems, JSONObject oddsObj) {
        StringBuilder bets = new StringBuilder();
        Map<String, String> ballCode = BallCodeTool.getBallCode(HandicapUtil.Code.NCOM1, gameCode);

        for (String betItem : betItems) {
            String[] items = betItem.split("\\|");
            String bet = items[0].concat("|").concat(items[1]);
            //单注金额须为整数值
            int fund = (int) NumberTool.doubleT(items[2]);
            String betCode = ballCode.get(bet);
            String odds = oddsObj.get(betCode).toString();
            if (StringTool.isEmpty(betCode, odds)) {
                log.error("错误的投注项" + betItem);
                continue;
            }
            String item = betCode.concat(",").concat(odds).concat(",").concat(String.valueOf(fund));
            if (bets.length() != 0) {
                bets.append("|").append(item);
            } else {
                bets.append(item);
            }
        }
        return bets.toString();
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
        trElements.remove(0);

        JSONArray jsonArray=new JSONArray();
        for(Element tr:trElements){
            JSONArray array=new JSONArray();
            //注单号
            array.add(tr.child(0).text().split("#")[0]);
            //游戏类型
            String[] types=tr.child(1).text().replace(" ","").replace("期","").split("#");
            array.add(types[0]);
            //游戏期数
            array.add(types[1]);
            //投注项
            String[] content = tr.child(2).text().replace("『", "").replace("』", "").split("@");
            array.add(content[0]);
            //赔率
            array.add(content[1]);
            //投注金额
            array.add(tr.child(3).text());
            jsonArray.add(array);
        }
        return jsonArray;
    }

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
                                     JSONArray betResult, String gameName, String roundno) {
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
}
