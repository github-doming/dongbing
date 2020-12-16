package com.ibm.common.test.wwj.handicap.sgwin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.test.wwj.handicap.AbsHandicapGrab;
import com.ibm.common.test.wwj.handicap.HttpType;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
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
 * @Date: 2019/12/2 15:37
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public abstract class AbsSgWinGrab extends AbsHandicapGrab {
    private static final String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";

    AbsSgWinGrab() {
        super(HandicapUtil.Code.SGWIN);
    }

    // TODO 开启页面函数

    //  开启页面函数

    /**
     * 根据盘口导航地址和验证码打开获取线路界面
     *
     * @param httpConfig      请求配置
     * @param handicapUrl     盘口url
     * @param handicapCaptcha 盘口验证码
     * @param index           循环次数
     * @return 获取线路界面
     */
    @Override
    public String selectRoute(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String routeHtml;
        try {

            String navigationHtml =  html(httpConfig.url(handicapUrl).method(HttpConfig.Method.GET), HttpType.Normal);

            if (StringTool.isEmpty(navigationHtml)) {
                log.debug("获取导航页面失败,结果信息为空");
                longSleep("获取线路界面");
                return selectRoute(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
            }
            //多次输入验证码,可能跳过导航页面，判断是否为线路选择页面
            if (StringTool.isContains(navigationHtml, "线路选择")) {
                return navigationHtml;
            }
            //导航页action
            String actionUrl = getNavigationAction(navigationHtml);

            //获取线路页面
            httpConfig.url(handicapUrl.concat("/").concat(actionUrl).concat("?search=").concat(handicapCaptcha));
            routeHtml = html(httpConfig.method(HttpConfig.Method.GET), HttpType.Routes);
            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "线路选择")) {
                log.debug("获取线路页面失败" + routeHtml);
                longSleep("获取线路界面");
                return selectRoute(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
            }
        } catch (Exception e) {
            log.error("打开选择线路界面失败", e);
            longSleep("获取线路界面");
            return selectRoute(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
        }
        //会员可用线路页面
        return routeHtml;
    }

    /**
     * 根据线路页面解析的到线路并完成测速排序后输出
     *
     * @param httpConfig 请求配置
     * @param hrefs  获取线路
     * @return 测速后的线路数组
     */

    public String[] routes(HttpClientConfig httpConfig, List<String> hrefs){
        String[] routes = new String[hrefs.size()];
        long[] arr = new long[hrefs.size()];
        //判断时间延迟
        long t1, t2;
        for (int i = 0; i < hrefs.size(); i++) {
            t1 = System.currentTimeMillis();
            String href = hrefs.get(i).concat("/");
            httpConfig.url(href.concat("?random-no-cache=")
                    .concat(Integer.toHexString((int) Math.floor((1 + Math.random()) * 0x10000))));
            html(httpConfig.method(HttpConfig.Method.GET), HttpType.Normal);
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
     * 获取登录信息
     *
     * @param httpConfig 请求配置
     * @param routes     线路数组
     * @param index      循环次数
     * @return 登录信息
     */
    @Override
    public Map<String, String> loginInfo(HttpClientConfig httpConfig, String[] routes, int... index) {
        if (index.length == 0) {
            index = new int[2];
        }
        if (index[1] >= MAX_RECURSIVE_SIZE) {
            index[1] = 0;
            index[0]++;
        }
        if (index[0] >= routes.length) {
            return null;
        }
        String html;
        Map<String, String> loginInfoMap;

        try {
            //获取登录页面
            html = html(httpConfig.url(routes[index[0]].concat("login")).method(HttpConfig.Method.GET),HttpType.Normal);

            if (StringTool.isEmpty(html) || !StringTool.isContains(html, "Welcome")) {
                log.debug("获取登录信息=" + html);
                longSleep("获取登录信息");
                return loginInfo(httpConfig, routes, index[0], ++index[1]);
            }
            loginInfoMap = new HashMap<>(3);
            loginInfoMap.put("loginSrc", routes[index[0]]);

        } catch (Exception e) {
            log.error("获取登录信息", e);
            longSleep("获取登录信息");
            ++index[0];
            return loginInfo(httpConfig, routes, index[0], ++index[1]);
        }
        //获取验证码code和action
        getCodeAndAction(html, loginInfoMap);

        return loginInfoMap;
    }

    /**
     * 获取验证码
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param code        验证码地址
     * @param index       循环次数
     * @return 验证码
     */
    public String getVerifyCode(HttpClientConfig httpConfig, String projectHost, String code, int... index){
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        try {
            String content;
            //获取验证码内容,code等于空时，刷新验证码
            if (StringTool.isEmpty(code)) {
                content = getImage(httpConfig.url(projectHost.concat("code?_=") + System.currentTimeMillis()));
            } else {
                content = getImage(httpConfig.url(projectHost.concat(code)));
            }
            if (StringTool.isEmpty(content)) {
                log.debug("获取验证码失败,结果信息为空");
                sleep("获取验证码");
                return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
            }
            Map<String, Object> join = new HashMap<>(2);
            join.put("type", "SGWIN");
            join.put("content", content);

            String html = HttpClientTool.doPost(CRACK_CONTENT, join);

            if (StringTool.isEmpty(html)) {
                log.debug("破解验证码失败,结果信息为空");
                sleep("破解验证码");
                return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
            }
            html = html.replace("\"", "");
            //验证码位数低于4位
            if (html.length() < 4) {
                return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
            }
            return html;
        } catch (IOException e) {
            log.error("破解验证码失败", e);
            sleep("破解验证码");
            return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
        }
    }

    /**
     * 登录
     *
     * @param httpConfig
     * @param join    登录参数
     * @param verifyCode 验证码
     * @param loginUrl   登录地址
     * @return 登录结果
     */
    public String login(HttpClientConfig httpConfig,Map<String, Object> join,String verifyCode,String loginUrl,int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        try{
            String loginInfo = html(httpConfig.url(loginUrl).method(HttpConfig.Method.POST).map(join),HttpType.Normal);
            if(StringTool.isEmpty(loginInfo)){
                log.error("获取登陆信息失败");
                longSleep("获取登陆信息");
                return login(httpConfig,join,verifyCode,loginUrl,++index[0]);
            }
            //账号冻结,账户已禁用,第一次登陆
            if (StringTool.contains(loginInfo, "账号或密码错误","抱歉!你的帐号已被冻结", "账户已禁用", "修改密码")) {
                return loginInfo;
            }
            //验证码错误
            if (StringTool.isContains(loginInfo, "验证码")) {
                log.debug("验证码错误" + loginInfo);
                longSleep("验证码错误");
                verifyCode = getVerifyCode(httpConfig, loginUrl, null);
                return login(httpConfig,join,verifyCode,loginUrl,++index[0]);
            }
            return loginInfo;
        }catch (Exception e){
            log.error("获取登陆信息失败");
            longSleep("获取登陆信息");
            return login(httpConfig,join,verifyCode,loginUrl,++index[0]);
        }
    }

    /**
     * 进入主页面
     *
     * @param homeUrl 主页面地址
     * @return 主页面
     */
    public String home(HttpClientConfig httpConfig,String homeUrl,int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        try {
            //获取盘口主页面
            String indexHtml = html(httpConfig.url(homeUrl.concat("member/index")).method(HttpConfig.Method.GET),HttpType.Normal);
            if (StringTool.isEmpty(indexHtml) || !StringTool.isContains(indexHtml, "Welcome")) {
                log.error("获取盘口主页面失败", indexHtml);
                longSleep("获取盘口主页面");
                return home(httpConfig, homeUrl,++index[0]);
            }
            return indexHtml;
        } catch (Exception e) {
            log.error("获取盘口主页面失败", e);
            longSleep("获取盘口主页面");
            return home(httpConfig, homeUrl,++index[0]);
        }

    }


    //  开启页面函数

    // TODO 页面解析函数

    //  页面解析函数
    /**
     * 获取导航页action
     *
     * @param navigationHtml 导航页面
     * @return action路径
     */
    protected String getNavigationAction(String navigationHtml) {
        Document navigationDocument = Jsoup.parse(navigationHtml);

        Element select = navigationDocument.selectFirst("form");

        return select.attr("action");
    }

    /**
     * 获取验证码code和action
     *
     * @param loginHtml    登录页面
     * @param loginInfoMap 登录信息map
     */
    private void getCodeAndAction(String loginHtml, Map<String, String> loginInfoMap) {
        Document document = Jsoup.parse(loginHtml);
        Element select = document.selectFirst("form");

        String action = select.attr("action");

        String code = select.select("img").attr("src");

        loginInfoMap.put("action", action);
        loginInfoMap.put("code", code);
    }
    /**
     *  解析线路
     * @param routeHtml  线路页面
     * @param key 用户类型
     * @return
     */
    protected List<String> getHrefs(String routeHtml, String key){
        Document routeDocument = Jsoup.parse(routeHtml);

        Element tbody = routeDocument.selectFirst("tbody");
        //包括会员，代理，开奖网线路
        Elements trs = tbody.select("tr");

        List<String> hrefs = new ArrayList<>();

        for (Element tr : trs) {
            String type = tr.selectFirst("td").text();
            if (StringTool.isContains(type, key)) {
                String href = tr.select("a").attr("href");
                hrefs.add(href.split("=")[1]);
            }
        }
        return hrefs;
    }

    /**
     * 解析会员信息页面
     *
     * @param html 会员信息页面
     * @return 会员信息
     */
    protected List<Map<String,String>> getMemberInfo(String html) {
        Document document = Jsoup.parse(html);
        //找到会员列表
        Elements trElements = document.select("div.contents>div.user_list>table.data_table>tbody>tr");
        List<Map<String,String>>  memberList = new ArrayList<>();
        Map<String,String> member;
        //循环会员
        for (Element trElement : trElements) {
            String online = "offline";
            //会员在线
            Element onlineSpan = trElement.selectFirst("td.online>a>span");
            if (onlineSpan != null && "s1".equals(onlineSpan.className())) {
                online = "online";
            }

            String userName = trElement.selectFirst("td.username").text().split(" \\[")[0];
            member = new HashMap<>();
            member.put("Account",userName);
            member.put("IsOnline",online);
            memberList.add(member);
        }
        return memberList;
    }

    /**
     * 解析未结算摘要
     *
     * @param html 未结算摘要页面
     * @return 未结算摘要
     */
    protected Map<String, Map<String, Integer>> getBetSummary(String html) {
        Document document = Jsoup.parse(html);
        Elements trElements = document.select("div.contents>table.data_table>tbody>tr");
        Map<String, Map<String, Integer>> betSummary = new HashMap<>(2);
        if (trElements.isEmpty() ) {
            //没有未结算数据
            if (html.contains("会员输赢")){
                return new HashMap<>(2);
            }
            return null;
        }

        Map<String, Integer> member = new HashMap<>(trElements.size() / 4 + 1);
        Map<String, Integer> agent = new HashMap<>(trElements.size() * 2 / 4 + 1);
        for (Element trElement : trElements) {
            String type = trElement.child(0).text();
            if ("会员".equals(type)) {
                member.put(trElement.child(1).text(), NumberTool.getInteger(trElement.child(4).text()));
            } else {
                agent.put(trElement.child(1).text(), NumberTool.getInteger(trElement.child(4).text()));
            }
        }
        betSummary.put("member", member);
        betSummary.put("agent", agent);
        return betSummary;
    }

    /**
     * 获取投注详情
     *
     * @param betDetail    投注详情
     * @param gameCode     游戏编码
     * @param html         投注详情页面
     * @param subOddNumber 上一次请求的主单号
     * @return 投注详情
     */
    public Map<String, Object> getBetDetail(Map<String, Object> betDetail, GameUtil.Code gameCode, String html,
                                            String subOddNumber) {
        Document document = Jsoup.parse(html);
        Elements trElements = document.select("div.contents>table.data_table>tbody>tr");
        if (trElements.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> details;
        if (betDetail == null) {
            betDetail = new HashedMap(3);

            Elements pageElements = document.select("div.page>div.page_info>span");
            String total = StringUtils.substringBetween(pageElements.get(0).text(), "共 ", " 笔注单");
            String pages = StringUtils.substringBetween(pageElements.get(1).text(), "共 ", " 页");

            details = new ArrayList<>(NumberTool.getInteger(total) * 3 / 4 + 1);
            betDetail.put("pages", NumberTool.getInteger(pages));
        } else {
            details = (List<Map<String, Object>>) betDetail.get("details");
        }

        //放入投注项
        String maxOddNumber = betDetail.getOrDefault("maxOddNumber", subOddNumber).toString();

        int compare = maxOddNumber.compareTo(subOddNumber);
        boolean flag = true;
        for (Element trElement : trElements) {
            String oddNumber = trElement.child(0).text();
            int tmpCompare = oddNumber.compareTo(subOddNumber);
            if (tmpCompare <= 0) {
                flag = false;
                break;
            } else {
                if (compare < tmpCompare) {
                    compare = tmpCompare;
                    maxOddNumber = oddNumber;
                }
            }
            Map<String, Object> info = new HashMap<>(2);
            //投注项
            String betInfo = trElement.child(4).selectFirst("span").text();
            betInfo = betInfo.replace("』", "");
            info.put("betItem", getBetItem(gameCode, betInfo));
            //投注积分
            String funds = trElement.child(5).text();
            info.put("funds", funds);
            details.add(info);
        }
        if (!flag) {
            betDetail.remove("pages");
        }

        betDetail.put("maxOddNumber", maxOddNumber);
        betDetail.put("details", details);
        return betDetail;
    }

    /**
     * 解析游戏限额页面
     *
     * @param html 游戏限额页面
     * @return 游戏限额
     */
    protected JSONArray getLimit(String html) {
        Document document = Jsoup.parse(html);
        Element tableClass = document.getElementsByClass("list table data_table").first();

        Elements trs = tableClass.select("tbody tr");

        JSONArray quota = new JSONArray();
        JSONArray array;
        for(Element tr:trs){
            array=new JSONArray();
            String text=tr.text();
            String[] limits=text.split(" ");

            array.add(Integer.parseInt(limits[2]));
            array.add(Integer.parseInt(limits[3]));
            array.add(Integer.parseInt(limits[4]));
            quota.add(array);
        }
        return quota;
    }
    //  页面解析函数

    // TODO 功能函数

    //  功能函数
    /**
     * 登陆错误
     *
     * @param msg 登陆结果页面
     * @return 错误信息
     */
    public HcCodeEnum loginError(String msg) {
        log.error("SgWin盘口会员登陆异常，异常的登陆结果页面为：" + msg);
        if (StringTool.isContains(msg, "验证码错误")) {
            return HcCodeEnum.IBS_403_VERIFY_CODE;
        } else if (StringTool.contains(msg, "抱歉!你的帐号已被冻结", "账户已禁用")) {
            return HcCodeEnum.IBS_403_USER_STATE;
        } else if (StringTool.contains(msg, "账号或密码错误")) {
            return HcCodeEnum.IBS_403_USER_ACCOUNT;
        } else if (StringTool.contains(msg, "修改密码")) {
            return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
        } else {
            return HcCodeEnum.IBS_403_UNKNOWN;
        }
    }

    /**
     * 获取投注项
     *
     * @param gameCode 投注编码
     * @param betInfo  投注信息
     * @return 投注项
     */
    private String getBetItem(GameUtil.Code gameCode, String betInfo) {
        String[] infos = betInfo.split("『");
        switch (gameCode) {
            case PK10:
            case XYFT:
            case JS10:
                if (infos.length == 1) {
                    return betInfo.substring(0, 2).concat("|").concat(betInfo.substring(2));
                }
                switch (infos[0]) {
                    case "冠军":
                        return "第一名|".concat(infos[1]);
                    case "亚军":
                        return "第二名|".concat(infos[1]);
                    case "冠亚军和":
                        return "冠亚|".concat(infos[1]);
                    default:
                        return infos[0].concat("|").concat(infos[1]);
                }
            case JSSSC:
            case CQSSC:
                if (infos.length == 1) {
                    if (betInfo.contains("总和")) {
                        return betInfo.substring(0, 2).concat("|").concat(betInfo.substring(2));
                    }
                    return "龙虎和|".concat(betInfo);
                }
                return infos[0].concat("|").concat(infos[1]);
            default:
                throw new RuntimeException("错误的游戏类型捕捉");
        }
    }

    /**
     * 获取投注详情内容
     *
     * @param gameCode 游戏code
     * @param betItems 投注详情
     * @param odds     赔率
     * @return
     */
    protected JSONArray getBetItemInfo(GameUtil.Code gameCode, List<String> betItems, JSONObject odds) {
        JSONArray betsArray = new JSONArray();
        Map<String, String> ballCode = BallCodeTool.getBallCode(HandicapUtil.Code.SGWIN, gameCode);
        JSONObject betInfo;
        for (String betItem : betItems) {
            betInfo = new JSONObject();
            String[] items = betItem.split("\\|");
            String bet = items[0].concat("|").concat(items[1]);
            //单注金额须为整数值
            int fund = (int) NumberTool.doubleT(items[2]);
            String betCode = ballCode.get(bet);
            if (StringTool.isEmpty(betCode)) {
                log.error("错误的投注项" + betItem);
                continue;
            }
            String[] betCodes = betCode.split(":");
            betInfo.put("game", betCodes[1]);
            betInfo.put("contents", betCodes[2]);
            betInfo.put("amount", fund);
            betInfo.put("odds", odds.get(betCodes[1].concat("_").concat(betCodes[2])));
            betInfo.put("title", betCodes[0]);
            betsArray.add(betInfo);
        }
        return betsArray;
    }

    /**
     * 获取投注结果
     *
     * @param gameCode 游戏code
     * @param isSettle 未结算信息
     * @param ids      当前投注成功注单号
     * @return
     */
    protected JSONArray getBetResult(GameUtil.Code gameCode, String isSettle, String ids) {
        Map<String, String> ballInfo = BallCodeTool.getBallInfoCode(HandicapUtil.Code.SGWIN, gameCode);

        JSONArray betResult = new JSONArray();
        JSONArray data = JSONArray.parseArray(isSettle);
        JSONObject info;
        JSONArray betInfo;
        for (int i = 0; i < data.size(); i++) {
            info = data.getJSONObject(i);
            String item = ballInfo.get(info.getString("t").replace(" ", ""));
            if (StringTool.isEmpty(item)) {
                log.error("错误的投注项：" + info);
                continue;
            }
            if (!StringTool.isContains(ids, info.getString("id"))) {
                continue;
            }
            betInfo = new JSONArray();
            //注单号
            betInfo.add(info.get("id"));
            //投注项
            betInfo.add(item);
            //投注金额
            betInfo.add(info.get("a"));
            //赔率
            betInfo.add(info.get("o"));
            betResult.add(betInfo);
        }
        return betResult;
    }
    //  功能函数
}
