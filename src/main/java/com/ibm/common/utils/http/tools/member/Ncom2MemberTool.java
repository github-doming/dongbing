package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.Ncom2Config;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.handicap.config.Ncom2BallConfig;
import com.ibm.common.utils.handicap.config.Ncom2NumberConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/27 14:17
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class Ncom2MemberTool {

    protected static final Logger log = LogManager.getLogger(SgWinMemberTool.class);
    private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";
    private static final long SLEEP = 1000L;

    private static final Integer MAX_RECURSIVE_SIZE = 5;
    private static final String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";


    /**
     * 获取线路页面
     * https://138111.co/user-search-result.php?search=160127
     *
     * @param httpConfig      http请求配置类
     * @param handicapUrl     盘口url
     * @param handicapCaptcha 盘口验证码
     * @param index           循环次数
     * @return 会员可用线路
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
//            String navigationHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));

            //导航页action
            String url = handicapUrl.concat("/index.php?s=/Home/Index/navi");
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("code", handicapCaptcha);
            dataMap.put("Submit", "登 録");

            // 获取线路
            routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(dataMap));

            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "線路系統")) {
                log.error("获取线路页面失败", routeHtml);
                Thread.sleep(2 * SLEEP);
                return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
            }
        } catch (Exception e) {
            log.error("打开选择线路界面失败", e);
            Thread.sleep(2 * SLEEP);
            return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
        }
        //会员可用线路页面
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
    public static String getLoginHtml(HttpClientConfig httpConfig, String[] routes, int... index)
            throws InterruptedException {
        if (index.length == 0) {
            index = new int[2];
        }
        if (index[1] > MAX_RECURSIVE_SIZE) {
            index[0]++;
        }
        if (index[0] >= routes.length) {
            return "";
        }
        String html;
        try {
            //获取登录页面
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]]));

            if (StringTool.isEmpty(html) || !StringTool.isContains(html, "登 錄")) {
                log.error("打开登录页面失败", html);
                Thread.sleep(2 * SLEEP);
                return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
            }

        } catch (Exception e) {
            log.error("打开登录页面失败", e);
            Thread.sleep(2 * SLEEP);
            return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
        }

        return routes[index[0]];
    }

    /**
     * 获取验证码
     * @deprecated
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param code        验证码地址
     * @param index       循环次数
     * @return 验证码
     */
    public static String getVerifyCode(HttpClientConfig httpConfig, String projectHost, String code, int... index)
            throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String content;
        //获取验证码内容,code等于空时，刷新验证码
        if (StringTool.isEmpty(code)) {
            content = HttpClientUtil.findInstance()
                    .getImage(httpConfig.url(projectHost.concat("/Handler/ValidateImgHandler.ashx?tm=%20") + String.format("%.15f", Math.random())));
        } else {
            content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
        }
        if (StringTool.isEmpty(content)) {
            log.error("获取验证码失败");
            Thread.sleep(SLEEP);
            return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
        }
        Map<String, Object> join = new HashMap<>(2);
        join.put("type", "SGWIN");
        join.put("content", content);

        String html;
        try {
            html = HttpClientTool.doPost(CRACK_CONTENT, join);
        } catch (Exception e) {
            log.error("破解验证码失败", e);
            Thread.sleep(SLEEP);
            return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
        }
        if (StringTool.isEmpty(html)) {
            log.error("破解验证码失败");
            Thread.sleep(SLEEP);
            return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
        }
        html = html.replace("\"", "");
        if (html.length() < 4) {
            return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
        }
        return html;
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
     * @throws IOException
     */
    public static JSONObject getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
                                  String projectHost, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded");
        Map<String, Object> join = new HashMap<>(4);
        join.put("ValidateCode", "");
        join.put("loginName", memberAccount);
        join.put("loginPwd", memberPassword);

        String url = projectHost.concat("/Handler/LoginHandler.ashx?action=user_login");
        String loginInfo;
        try {
            //登录信息
            loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
            if (StringTool.isEmpty(loginInfo)) {
                log.error("获取登陆信息失败");
                Thread.sleep(2 * SLEEP);
                return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
            }

            //账号冻结,账户已禁用,第一次登陆
            if (StringTool.contains(loginInfo, "帳號或者密碼錯誤", "帳號已經停用", "重置密碼")) {
                return JSONObject.parseObject(loginInfo);
            }
            //登录结果
            return JSONObject.parseObject(loginInfo);
        } catch (Exception e) {
            log.error("获取登陆信息失败");
            Thread.sleep(2 * SLEEP);
            return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
        }
    }


    /**
     * 获取用户信息
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param index       循环次数
     * @return 会员信息
     */
    public static Map<String, String> getUserInfo(HttpClientConfig httpConfig, String projectHost, int... index)
            throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String userInfoHtml;
        try {
            Map<String, Object> join = new HashMap<>(3);
            join.put("action", "get_ad");
            join.put("browserCode","");
            //获取用户信息
            userInfoHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(projectHost.concat("/Handler/QueryHandler.ashx")).map(join));
            if (StringTool.isEmpty(userInfoHtml)) {
                log.error("获取用户信息", userInfoHtml);
                Thread.sleep(2 * SLEEP);
                return getUserInfo(httpConfig, projectHost, ++index[0]);
            }// <script>alert("您的帳號在其他地方登陸，請重新登陸！");top.location.href="/"</script>
            if(StringTool.isContains(userInfoHtml,"請重新登陸")){
                Map<String, String> map = new HashMap<>();
                map.put("snatchMsg","true");
                return map;
            }

            return userInfo(userInfoHtml);
        } catch (Exception e) {
            log.error("获取用户信息", e);
            Thread.sleep(2 * SLEEP);
            return getUserInfo(httpConfig, projectHost, ++index[0]);
        }

    }

    /**
     * 获取游戏限额
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param game        盘口游戏code
     * @param index       循环次数
     * @return 游戏限额信息
     */
    public static JSONArray getQuotaList(HttpClientConfig httpConfig, String projectHost, String game, int... index)
            throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return new JSONArray();
        }
        String html = null;

        String url = projectHost.concat("/CreditInfo.aspx?p=1&id=").concat(game);
        try {
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
            if (StringTool.isEmpty(html)) {
                log.error("获取游戏限额失败");
                Thread.sleep(SLEEP);
                return getQuotaList(httpConfig, projectHost, game, ++index[0]);
            }

            return getLimit(html);
        } catch (Exception e) {
            log.error("获取游戏限额失败,错误信息=" + html, e);
            Thread.sleep(SLEEP);
            return getQuotaList(httpConfig, projectHost, game, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
        }
    }



    /**
     * 获取赔率信息
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param betType        类型
     * @param index       循环次数
     * @return
     */
    public static JSONObject getOddsInfo(HttpClientConfig httpConfig, String projectHost, GameUtil.Code gameCode,
                                          String betType, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return new JSONObject();
        }
        Map<String,String> betParameter = getBetParameter(gameCode,betType);
        String html = null;
        Map<String, Object> join = new HashMap<>(3);
        join.put("action", "get_oddsinfo");
        join.put("playid",betParameter.get("playid"));
        join.put("playpage",betParameter.get("playpage") );
        String url = projectHost.concat("/").concat(betParameter.get("codePath") ).concat("/Handler/Handler.ashx");
        try {

            html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
            if (ContainerTool.isEmpty(html)) {
                log.error("获取游戏赔率失败");
                Thread.sleep(SLEEP);
                return getOddsInfo(httpConfig, projectHost, gameCode,betType, ++index[0]);
            }
            JSONObject resultHtml = JSONObject.parseObject(html);
            int state = (int) resultHtml.get("success");
            if( state==200){
                return (JSONObject) resultHtml.get("data");
            }
            return null;

        } catch (Exception e) {
            log.error("获取游戏赔率失败,结果信息=" + html, e);
            Thread.sleep(SLEEP);
            return getOddsInfo(httpConfig, projectHost, gameCode, betType, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
        }
    }

    /**
     * 投注
     * @param httpConfig
     * @param projectHost
     * @param betsMap
     * @param playpage
     * @return
     */
    public static JSONObject betting(HttpClientConfig httpConfig, String projectHost,GameUtil.Code gameCode, Map<String, String> betsMap,
                                     String playpage,String codePath,String phaseid) {

        String url=projectHost.concat("/").concat(codePath);
        httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpConfig.setHeader("Referer",url+"/index.aspx?lid="+Ncom2Config.GAME_CODE_ID.get(gameCode.name())+"2&path="+codePath);

        Map<String,Object> info =new HashMap<>();
        info.put("action", "put_money");
        info.put("phaseid", phaseid);
        info.put("oddsid", betsMap.get("oddsid"));
        info.put("uPI_P",  betsMap.get("uPI_P"));
        info.put("uPI_M",  betsMap.get("uPI_M"));
        info.put("i_index",  betsMap.get("i_index"));
        info.put("JeuValidate", "07171024394568");
        info.put("playpage", playpage);

        String resultHtml = null;

        try {
            resultHtml = HttpClientUtil.findInstance()
                    .postHtml(httpConfig.url(url.concat("/Handler/Handler.ashx")).map(info));
            if (ContainerTool.isEmpty(resultHtml)) {
                log.error("投注结果页面为空,投注项为：" + betsMap);
                return new JSONObject();
            }
            return JSONObject.parseObject(resultHtml);
        }finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,info,0,resultHtml));
        }
    }

    /**
     * 投注
     * @param httpConfig
     * @param projectHost
     * @param playpage
     * @return
     */
    public static JSONObject getbetResult(HttpClientConfig httpConfig, String projectHost,GameUtil.Code gameCode,String playpage,String codePath) {
        String url=projectHost.concat("/").concat(codePath);
        httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpConfig.setHeader("Referer",url+"/index.aspx?lid="+Ncom2Config.GAME_CODE_ID.get(gameCode.name())+"2&path="+codePath);

        Map<String,Object> info =new HashMap<>();
        info.put("action", "get_putinfo");
        info.put("playpage", playpage);

        String resultHtml = null;

        try {
            resultHtml = HttpClientUtil.findInstance()
                    .postHtml(httpConfig.url(url.concat("/Handler/Handler.ashx")).map(info));
            if (ContainerTool.isEmpty(resultHtml)) {
                log.error("获取投注结果页面为空");
                return new JSONObject();
            }
            return JSONObject.parseObject(resultHtml);
        }finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,info,0,resultHtml));
        }
    }


    /*************解析************/

    /**
     * 解析游戏限额页面
     *
     * @param html 游戏限额页面
     * @return 游戏限额
     */
    private static JSONArray getLimit(String html) {
        Document document = Jsoup.parse(html);
        Elements tableClass = document.getElementsByClass("hover_list");
        JSONArray quota = new JSONArray();
        JSONArray array;
        for (Element table : tableClass) {
            Elements trs = table.select("tbody tr");
            for (Element tr : trs) {
                array = new JSONArray();
                String text = tr.text();
                if (StringTool.notEmpty(text)) {
                    String[] limits = text.split(" ");
                    if (limits.length == 6) {
                        array.add(limits[3]);
                        array.add(limits[4]);
                        array.add(limits[5]);
                    } else {
                        array.add(limits[2]);
                        array.add(limits[3]);
                        array.add(limits[4]);
                    }
                    quota.add(array);
                }
            }
        }
        return quota;
    }

    private static Map<String, String> userInfo(String userInfoHtml) {
        Map<String, String> userData = new HashMap<>(5);
        try {
            JSONObject userInfo = JSONObject.parseObject(userInfoHtml).getJSONObject("data").getJSONObject("game_2");

            //信用额度
            userData.put("creditQuota", userInfo.getString("credit"));
            //使用金额
            userData.put("usedQuota", String.valueOf(userInfo.getDouble("usable_credit")));
            //盈亏金额
            userData.put("profitAmount",String.valueOf(userInfo.getDouble("profit")));
        } catch (Exception e) {
            log.error("用户信息解析错误", e);
        }
        return userData;
    }

    /**
     * 获取会员可用线路
     *
     * @param routeHtml  线路页面
     * @return 线路数组
     */
    public static String[] getMemberRoute(String routeHtml){
        Document routeDocument = Jsoup.parse(routeHtml);
        Elements trs = routeDocument.getElementsByClass("left1");
        List<String> hrefs = new ArrayList<>();
        for (Element tr : trs) {
            String type = tr.getElementsByClass("title").text();
            if (StringTool.isContains(type, "會員")) {
                Elements lis = tr.select("li");
                for (Element li : lis) {
                    String href = li.select("a").attr("href");
                    hrefs.add(href);
                }
            }
        }
        String[] routes = new String[hrefs.size()];
        for (int i = 0; i < hrefs.size(); i++) {
            routes[i] = hrefs.get(i);
        }
        return routes;
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
        List<Object> iindex = new ArrayList<>();
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
            iindex.add(i);
        }
        betsMap.put("oddsid", StringUtils.join(oddsId.toArray(), ","));
        betsMap.put("uPI_P", StringUtils.join(uPIP.toArray(), ","));
        betsMap.put("uPI_M", StringUtils.join(uPIM.toArray(), ","));
        betsMap.put("i_index", StringUtils.join(iindex.toArray(), ","));
        return betsMap;
    }

    /**
     * 获取请求参数
     * @param gameCode
     * @param betType
     * @return
     */
    public static Map<String, String> getBetParameter(GameUtil.Code gameCode, String betType) {
        Map<String,String> betParameter = new HashMap<>();
        String playid="",playpage="",codePath="";
        switch (gameCode) {
            case JSSSC:
                playpage = "speed5_";
                codePath = "L_SPEED5";
                break;
            case PK10:
                playpage = "pk10_";
                codePath = "L_PK10";
                break;
            case XYFT:
                playpage = "xyft5_";
                codePath = "L_XYFT5";
                break;
            case CQSSC:
                playpage = "cqsc_";
                codePath = "L_CQSC";
                break;
            case JS10:
                playpage = "jscar_";
                codePath = "L_JSCAR";
                break;
                default:
                    break;
        }
        switch (gameCode){
            case PK10:
            case JS10:
            case XYFT:
                switch (betType){
                    case "dobleSides":
                        playpage += "lmp";
                        break;
                    case "ballNO":
                        playpage += "d1_10";
                        break;
                    case "sumDT":
                        playpage += "12";
                        break;
                    default:
                        break;
                }
                playid = Ncom2NumberConfig.GAME_ODDS_ID.get(betType);
                break;
            case CQSSC:
            case JSSSC:
                switch (betType){
                    case "dobleSides":
                        playpage += "lmp";
                        break;
                    case "ballNO":
                        playpage += "d1_5";
                        break;
                    case "sumDT":
                        playpage += "d1";
                        break;
                    default:
                        break;
                }
                playid = Ncom2BallConfig.GAME_ODDS_ID.get(betType);
                break;
            default:
                break;
        }
        betParameter.put("playid",playid);
        betParameter.put("playpage",playpage);
        betParameter.put("codePath",codePath);
        return betParameter;

    }


    /**
     * 登陆错误
     *
     * @param msg 登陆结果页面
     * @return 错误信息
     */
    public static HcCodeEnum loginError(String msg) {
        log.error("Ncom2盘口会员登陆异常，异常的登陆结果页面为：" + msg);
        if (StringTool.contains(msg, "帳號已經停用")) {
            return HcCodeEnum.IBS_403_USER_STATE;
        } else if (StringTool.contains(msg, "帳號或者密碼錯誤")) {
            return HcCodeEnum.IBS_403_USER_ACCOUNT;
        } else if (StringTool.contains(msg, "重置密碼")) {
            return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
        } else {
            return HcCodeEnum.IBS_403_UNKNOWN;
        }
    }
}
