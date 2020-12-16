package com.ibm.common.utils.http.tools.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * @Description: Ncom1盘口代理工具类
 * @Author: null
 * @Date: 2020-01-07 14:03
 * @Version: v1.0
 */
public class Ncom1AgentTool {
    protected static final Logger log = LogManager.getLogger(Ncom1AgentTool.class);
    private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";
    private static final long SLEEP = 1000L;

    private static final Integer MAX_RECURSIVE_SIZE = 5;
    private static final String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";
    // TODO 开启页面函数

    /**
     * 获取线路页面
     * @param httpConfig        http请求配置类
     * @param handicapUrl       盘口url
     * @param handicapCaptcha   盘口验证码
     * @param index             循环次数
     * @return
     */
    public static String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,int... index) throws InterruptedException {
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
     * @param httpConfig        http请求配置类
     * @param agentAccount      代理账号
     * @param agentPassword     代理密码
     * @param projectHost       主机地址
     * @param index             循环次数
     * @return
     */
    public static String getLogin(HttpClientConfig httpConfig, String agentAccount,
                                  String agentPassword, String projectHost, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        Map<String, Object> join = new HashMap<>(3);
        join.put("account", agentAccount);
        join.put("password", agentPassword);
        join.put("ValidateCode", "");
        String loginInfo;
        try{
            //登录信息
            String loginUrl = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("/?m=pub&a=login")).map(join));
            if (StringTool.isEmpty(loginUrl)) {
                log.error("获取登陆信息失败");
                Thread.sleep(2 * SLEEP);
                return getLogin(httpConfig, agentAccount, agentPassword, projectHost, ++index[0]);
            }
            JSONObject result=JSONObject.parseObject(loginUrl);
            if(result.getInteger("ok")!=1){
                //返回错误信息
                return result.getString("data");
            }

            loginInfo = HttpClientUtil.findInstance().getHtml(httpConfig.url(projectHost));
            if (StringTool.isEmpty(loginInfo)) {
                log.error("登录失败，错误的页面=" + loginInfo);
                Thread.sleep(2 * SLEEP);
                return getLogin(httpConfig, agentAccount, agentPassword, projectHost, ++index[0]);
            }
        } catch (Exception e) {
            log.error("获取登陆信息失败");
            Thread.sleep(2 * SLEEP);
            return getLogin(httpConfig, agentAccount, agentPassword, projectHost, ++index[0]);
        }
        //登录结果
        return loginInfo;
    }
    /**
     * 获取会员列表
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param index       循环次数
     * @return 会员列表
     */
    public static JSONArray getMemberList(HttpClientConfig httpConfig, String projectHost,int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return new JSONArray();
        }
        String url = projectHost.concat("?g=user&m=player&a=index&_=")+System.currentTimeMillis()+"&page=1&limit=100";
        String html = null;
        try {
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
            if (StringTool.isEmpty(html)) {
                log.debug("获取会员列表信息失败,结果信息为空");
                Thread.sleep(SLEEP);
                return getMemberList(httpConfig, projectHost, ++index[0]);
            }
            //TODO ,掉线处理
            return getMemberInfo(html);
        } catch (Exception e) {
            log.error("获取会员列表失败,结果信息=" + html, e);
            Thread.sleep(2 * SLEEP);
            return getMemberList(httpConfig, projectHost, ++index[0]);
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
    public static String[] getAgentRoute(HttpClientConfig httpConfig, String routeHtml) {
        Document document = Jsoup.parse(routeHtml);

        Elements as = document.select("div.right1>div.box1>ul>li>a");

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
     * 获取会员信息
     *
     * @param html 会员信息页面
     * @return 会员信息
     */
    private static JSONArray getMemberInfo(String html) {
        Document document = Jsoup.parse(html);
        //找到会员列表
        Elements trs = document.select("div.panel-content>table>tbody>tr");

        JSONArray memberList = new JSONArray();
        JSONArray member;
        for (Element tr : trs) {
            member = new JSONArray();
            String online=StringTool.isContains(tr.child(0).html(),"1.png")?"online":"offline";
            member.add(tr.child(4).text());
            member.add(online);
            memberList.add(member);
        }
        return memberList;
    }
}
