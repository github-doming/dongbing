package com.ibm.common.utils.http.tools.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.ComConfig;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.DetailInfo;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: COM代理工具类
 * @Author: null
 * @Date: 2020-04-25 15:44
 * @Version: v1.0
 */
public class ComAgentTool {
    private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";
    protected static final Logger log = LogManager.getLogger(SgWinAgentTool.class);

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
        String routeHtml;
        try {
            String navigationHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));
			if (StringTool.isEmpty(navigationHtml)) {
				log.error("获取导航页面失败:{}", navigationHtml);
				Thread.sleep(2 * SLEEP);
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if (!StringTool.isContains(navigationHtml, "登 錄")) {
				setCookie(httpConfig, handicapUrl);
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
            Map<String, Object> map = new HashMap<>(2);
            map.put("code", handicapCaptcha);
            map.put("Submit", "登 錄");
            routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl.concat("Web/SearchLine.aspx")).map(map));
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
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]]));
            //获取登录页面
            if (StringTool.isEmpty(html) ) {
                log.error("打开登录页面失败");
                Thread.sleep(SLEEP);
                return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
            }
			if (!StringTool.isContains(html, "用戶登錄")) {
				setCookie(httpConfig, routes[index[0]].concat("/"));
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
     * /Handler/LoginHandler.ashx?action=user_login
     *
     * @param httpConfig    http请求配置类
     * @param agentAccount  盘口代理账号
     * @param agentPassword 盘口代理密码
     * @param projectHost   线路地址
     * @param index         循环次数
     * @return 盘口主页面href
     */
    public static String getLogin(HttpClientConfig httpConfig, String agentAccount, String agentPassword,
                                  String projectHost, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        Map<String, Object> join = new HashMap<>(3);
        join.put("loginName", agentAccount);
        join.put("loginPwd", agentPassword);
        join.put("ValidateCode", "");
        String loginInfo;
        String url = projectHost.concat("/Handler/LoginHandler.ashx?action=user_login");
        try {
            //登录信息
            loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
            if (StringTool.isEmpty(loginInfo)) {
                log.error("获取登陆信息失败");
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
     * 盘口主页面
     * http://mem1.wzlkah517.hlbrhuanyou.com:88/Index.aspx
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 线路地址
     * @param index       循环次数
     * @return 盘口主页面href
     */
    public static String getIndex(HttpClientConfig httpConfig, String projectHost, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String indexPage;
        String url = projectHost.concat("/Index.aspx");
        try {
            indexPage = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
            if (StringTool.isEmpty(indexPage) || !StringTool.isContains(indexPage, "browserCode")) {
                log.error("获取盘口主页面失败,页面=", indexPage);
                Thread.sleep(2 * SLEEP);
                return getIndex(httpConfig, projectHost, ++index[0]);
            }
            return StringUtils.substringBetween(indexPage, "var browserCode='", "';");
        } catch (Exception e) {
            log.error("获取盘口主页面失败");
            Thread.sleep(2 * SLEEP);
            return getIndex(httpConfig, projectHost, ++index[0]);
        }
    }

    /**
     * 获取会员列表
     *
     * @param agent 代理信息
     * @param game  游戏信息
     * @param index 循环次数
     * @return
     */
    public static JSONArray getMemberList(AgentCrawler agent, String game, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String url = agent.getProjectHost().concat("account/hy_list.aspx?lid=").concat(game);
        String html ;
        try {
            html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url));
            if (StringTool.isEmpty(html)) {
                log.error("获取会员列表信息失败");
                Thread.sleep(SLEEP);
                return getMemberList(agent, game, ++index[0]);
            }
            if(StringTool.isContains(html,"<script>top.location.href='/'</script>")){
                log.error("获取会员列表信息失败,错误信息="+html);
                agent.setProjectHost(null);
                return null;
            }
            return getMemberInfo(html);
        } catch (Exception e) {
            log.error("获取会员列表信息失败", e);
            Thread.sleep(SLEEP);
            return getMemberList(agent, game, ++index[0]);
        }
    }

    /**
     * 获取期数阶段码
     *
     * @param agent 代理信息
     * @param game  盘口游戏id
     * @param index 循环次数
     * @return
     */
    public static JSONObject getPhaseoption(AgentCrawler agent, String game, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return new JSONObject();
        }
        Map<String, Object> join = new HashMap<>(2);
        join.put("lid", game);
        String url = agent.getProjectHost().concat("Handler/QueryHandler.ashx?action=get_phasebylottery");
        String html = null;
        try {
            HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(agent.getProjectHost().concat("/ReportSearch/ReportNew.aspx?lid=").concat(game)));

            agent.getHcConfig().setHeader("Referer",agent.getProjectHost().concat("/ReportSearch/ReportNew.aspx?lid=").concat(game));

            html = HttpClientUtil.findInstance().postHtml(agent.getHcConfig().url(url).map(join));
            if (StringTool.isEmpty(html)) {
                log.error("获取期数阶段码信息失败");
                Thread.sleep(SLEEP);
                return getPhaseoption(agent, game, ++index[0]);
            }
            if(StringTool.isContains(html,"<script>top.location.href='/'</script>")){
                log.error("获取期数阶段码信息失败,错误的信息="+html);
                agent.setProjectHost(null);
                return null;
            }
            return JSONObject.parseObject(html);
        } catch (Exception e) {
            log.error("获取期数阶段码信息失败,错误的信息="+html, e);
            Thread.sleep(SLEEP);
            return getPhaseoption(agent, game, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], ""));
        }
    }

    /**
     * 获取未结算摘要信息
     * &gID=0&t_FT=0&t_LID=172535&isQs=&BeginDate=2020-04-25&EndDate=2020-04-25&ReportType=B&t_Balance=0
     * @param agent       代理信息
     * @param game        盘口游戏id
     * @param phaseoption 期数阶段码
     * @param date        时间
     * @return 未结算摘要信息
     */
    public static Map<String, SummaryInfo> getBetSummary(AgentCrawler agent,
           String game, String phaseoption, String date,int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String url=agent.getProjectHost().concat("ReportSearch/ReportList_B_kcNew.aspx?");
        String parametersFormat ="t_TYPE=2&t_LT=%s&gID=0&t_FT=0&t_LID=%s&isQs=&BeginDate=%s&EndDate=%s&ReportType=B&t_Balance=0";
        String parameters = String.format(parametersFormat, game, phaseoption, date,date);
        String html = null;
        try {
            html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url.concat(parameters)));
            if (StringTool.isEmpty(html)) {
                log.debug("获取未结算摘要信息失败,结果信息为空");
                Thread.sleep(SLEEP);
                return getBetSummary(agent, game, phaseoption, date, ++index[0]);
            }
            if(StringTool.contains(html,"<script>top.location.href='/'</script>","抱歉，處理您的請求時出錯")){
                log.error("未结算摘要信息失败,错误信息="+html);
                return null;
            }
            if (!StringTool.isContains(html,"報表查詢")) {
                log.debug("获取未结算摘要信息失败,结果信息="+html);
                Thread.sleep(SLEEP);
                return getBetSummary(agent, game, phaseoption, date, ++index[0]);
            }
            Map<String, SummaryInfo> betSummary = getBetSummary(html);
            if (betSummary == null) {
                log.info("获取未结算摘要信息失败,结果信息：{}", html);
                Thread.sleep(SLEEP);
                return getBetSummary(agent, game, phaseoption, date, ++index[0]);
            }
            return betSummary;
        } catch (Exception e) {
            log.error("未结算摘要信息失败,错误信息="+html, e);
            Thread.sleep(2 * SLEEP);
            return getBetSummary(agent, game, phaseoption, date, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], ""));
        }
    }

    /**
     * 获取投注详情
     * @param agent     盘口代理信息
     * @param summaryInfo   摘要信息
     * @param game          盘口游戏id
     * @param pageIndex     页数
     * @param phaseoption   期数阶段码
     * @param date          时间
     * @param index         循环次数
     * @return
     */
    public static String getBetDetail(AgentCrawler agent, SummaryInfo summaryInfo, String game,Integer pageIndex,
                                      String phaseoption, String date, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String url=agent.getProjectHost().concat("ReportSearch/ReportDetail_B_kc.aspx?");
        String parametersFormat;
        String parameters;
        if(pageIndex==null){
            parametersFormat ="userid=%s&gID=0&m_type=hy&t_FT=0&t_LID=%s&BeginDate=%s&EndDate=%s&t_Balance=0&t_LT=%s&sltTabletype=&sltPlaytype=";
            parameters = String.format(parametersFormat, summaryInfo.getMemberId(),phaseoption,date,date,game);
        }else{
            parametersFormat="page=%s&t_LID=%s&t_FT=0&mID=&UP_ID=&FactSize=&t_Balance=0&userid=%s&m_type=hy&gID=0&uID=&BeginDate=%s&EndDate=%s&q_type=&t_LT=%s&sltTabletype=&sltPlaytype=";
            parameters = String.format(parametersFormat,pageIndex,phaseoption, summaryInfo.getMemberId(),date,date,game);
        }
        String html = null;
        try {
            html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url.concat(parameters)));
            if (StringTool.isEmpty(html)) {
                log.debug("获取投注详情失败,结果信息为空");
                Thread.sleep(SLEEP);
                return getBetDetail(agent,summaryInfo, game,pageIndex, phaseoption, date, ++index[0]);
            }
            if (!StringTool.isContains(html, "註單明細")) {
                log.debug("获取未结算详情信息失败,结果信息=" + html);
                Thread.sleep(SLEEP);
                return getBetDetail(agent,summaryInfo, game,pageIndex, phaseoption, date, ++index[0]);
            }
            return html;
        } catch (Exception e) {
            log.error("获取投注详情信息失败,错误信息="+html, e);
            Thread.sleep(2 * SLEEP);
            return getBetDetail(agent,summaryInfo, game,pageIndex, phaseoption, date, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], html));
        }
    }


    // TODO 功能函数
    /**
     *
     * @param betDetail     投注详情
     * @param gameCode      游戏编码
     * @param html          投注详情页面
     * @param subOddNumber  上一次请求的主单号
     * @return
     */
    public static DetailBox analyzeDetail(DetailBox betDetail, GameUtil.Code gameCode, String html, String subOddNumber) {
        Document document = Jsoup.parse(html);
        Elements trs=document.select("tbody.list_hover>tr");
        if (trs.isEmpty()) {
            return null;
        }
        if (betDetail == null) {
            betDetail = new DetailBox();
            Element pageElements = document.select("div.windowPager>span").first();

            String total =StringUtils.substringBetween(pageElements.text(),"共 "," 條記錄");
            String page =StringUtils.substringBetween(pageElements.text(),"分頁：1/","頁 ");
            betDetail.pages(page, total);
        }
        List<DetailInfo> details = betDetail.details();

        String maxOddNumber = betDetail.getMaxOddNumber(subOddNumber);
        int compare = maxOddNumber.compareTo(subOddNumber);

        //放入投注项
        for (Element tr : trs) {
            //注单号
            String oddNumber = tr.child(0).text().split("#")[0];
            int tmpCompare = oddNumber.compareTo(subOddNumber);
            if (tmpCompare <= 0) {
                betDetail.hasNext(false);
                break;
            } else {
                if (compare < tmpCompare) {
                    compare = tmpCompare;
                    maxOddNumber = oddNumber;
                }
            }
            //投注项
            String betInfo = tr.child(3).text().split("』")[0];
            //投注积分
            String funds = tr.child(4).text();

            details.add(new DetailInfo(getBetItem(gameCode, betInfo), NumberTool.getDouble(funds)));
        }
        betDetail.maxOddNumber(maxOddNumber);
        return betDetail;
    }
    /**
     * 获取投注项
     *
     * @param gameCode 投注编码
     * @param betInfo  投注信息
     * @return 投注项
     */
    private static String getBetItem(GameUtil.Code gameCode, String betInfo) {
        String[] infos = betInfo.split("『");

        String rank=infos[0].replace("大小","").replace("單雙","").replace("總","总")
                .replace(" ","");

        if(ComConfig.GAME_RANK_CODE.containsKey(rank)){
            rank= ComConfig.GAME_RANK_CODE.get(rank);
        }
        //單,雙,龍
        String item=infos[1].replace("單","单").replace("雙","双")
                .replace("龍","龙");
        switch (gameCode) {
            case PK10:
            case XYFT:
            case JS10:
                rank = rank.replace("龍虎","");
                return rank.concat("|").concat(item);
            case JSSSC:
            case CQSSC:
                rank=rank.replace("龍虎","龙虎和");
                //順子,對子,半順,雜六,總和,後三
                item = item.replace("順", "顺").replace("對", "对")
                        .replace("雜", "杂");
                return rank.concat("|").concat(item);
            case GDKLC:
            case XYNC:
                rank=rank.replace("龍虎","总和").replace("中發白","").replace("方位","");
                item=item.replace("東","东").replace("發","发");
                if(StringTool.isContains(rank,"尾數")){
                    rank=rank.replace("尾數","");
                    item="尾".concat(item);
                }
                if(StringTool.isContains(rank,"合數")){
                    rank=rank.replace("合數","");
                    item="合".concat(item);
                }
                if (item.startsWith("0")){
                    item = item.substring(1);
                }
                return rank.concat("|").concat(item);
            default:
                throw new RuntimeException("错误的游戏类型捕捉");
        }
    }

    /**
     * 未结算摘要
     *
     * @param html 未结算摘要页面
     * @return 未结算摘要
     */
    private static Map<String, SummaryInfo> getBetSummary(String html) {
        Document document = Jsoup.parse(html);
        Elements trs=document.select("tbody.list_hover>tr");

        if(trs.isEmpty()){
            //没有结算数据，页面不为空
            if(StringTool.isContains(html,"報表查詢")){
                return new HashMap<>(2);
            }
            return null;
        }
        //暂时没有获取到代理的
        Map<String, SummaryInfo> member = new HashMap<>(trs.size() * 3 / 4 + 1);
        for(Element tr:trs){
            SummaryInfo summaryInfo = new SummaryInfo();
            summaryInfo.setAccount(tr.child(0).text());
            summaryInfo.setBetCount(tr.child(2).text());
            summaryInfo.setBetAmount(NumberTool.intValueT(tr.child(3).text())/1000);
            summaryInfo.setMemberId(StringUtils.substringBetween(tr.child(3).child(0).attr("onclick"),"userid=","&"));
            member.put(tr.child(0).text(), summaryInfo);
        }
        return member;
    }
	/**
	 * 需要设置cookie
	 *
	 * @param httpConfig 			http请求配置类
	 * @param handicapUrl        url地址
	 * @return 设置结果
	 */
    private static void setCookie(HttpClientConfig httpConfig, String handicapUrl) {
        HttpClientContext context = httpConfig.httpContext();
        if (context == null) {
            context = HttpClientContext.create();
            httpConfig.httpContext(context);
        }
        BasicClientCookie cookie = new BasicClientCookie("srcurl",  StringTool.string2Hax(handicapUrl));
        cookie.setPath("/");
        cookie.setDomain("");
        context.getCookieStore().addCookie(cookie);

		int height = RandomTool.getInt(1000, 1500);
		int width = height / 9 * 16;
		String url =handicapUrl + "?security_verify_data=" + StringTool.string2Hax(width + "," + height);

		HttpClientUtil.findInstance().get(httpConfig.url(url));
    }

    /**
     * 获取代理可用线路
     *
     * @param routeHtml 线路页面
     * @return 线路数组
     */
    public static String[] getAgentRoute(String routeHtml) {
        Document document = Jsoup.parse(routeHtml);

        Elements as = document.select("div.right1>div.box1>ul>li>a");
        String[] routes = new String[as.size()];
        for (int i = 0; i < as.size(); i++) {
            routes[i] = as.get(i).attr("href");
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
        log.error("Com盘口会员登陆异常，异常的登陆结果页面为：" + msg);
        if (StringTool.isContains(msg, "验证码错误")) {
            return HcCodeEnum.IBS_403_VERIFY_CODE;
        } else if (StringTool.contains(msg, "冻结", "禁用")) {
            return HcCodeEnum.IBS_403_USER_STATE;
        } else if (StringTool.contains(msg, "用户名错误", "密码错误")) {
            return HcCodeEnum.IBS_403_USER_ACCOUNT;
        } else if (StringTool.contains(msg, "新密碼首次登錄,需重置密碼")) {
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
     * @param html 会员信息列表
     * @return 会员信息
     */
    private static JSONArray getMemberInfo(String html) {
        Document document = Jsoup.parse(html);

        Elements trs = document.select("table>tbody.list_hover>tr");

        JSONArray memberList = new JSONArray();
        JSONArray member;
        for (Element tr : trs) {
            member = new JSONArray();
            member.add(tr.child(4).text());
            member.add(StringTool.isContains(tr.child(0).html(), "USER_1.gif") ? "online" : "offline");
            memberList.add(member);
        }
        return memberList;
    }
}
