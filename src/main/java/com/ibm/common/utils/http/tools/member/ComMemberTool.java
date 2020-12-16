package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.ComConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
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
 * @Description: com会员工具类
 * @Author: null
 * @Date: 2020-04-18 13:52
 * @Version: v1.0
 */
public class ComMemberTool {
    protected static final Logger log = LogManager.getLogger(ComMemberTool.class);
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
    public static String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, int... index) throws InterruptedException {
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
            if (StringTool.isEmpty(html)) {
                log.error("打开登录页面失败");
                Thread.sleep(2 * SLEEP);
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
        Map<String, Object> join = new HashMap<>(3);
        join.put("loginName", memberAccount);
        join.put("loginPwd", memberPassword);
        join.put("ValidateCode", "");

        String loginInfo;
        String url = projectHost.concat("/Handler/LoginHandler.ashx?action=user_login");
        try {
            //登录信息
            loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
            if (StringTool.isEmpty(loginInfo)) {
                log.error("获取登陆信息失败");
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
     * 获取用户信息
     *
     * @param member  会员信息
     * @return
     */
    public static JSONObject getUserInfo(MemberCrawler member, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] >= MAX_RECURSIVE_SIZE) {
            return new JSONObject();
        }
        Map<String, Object> join = new HashMap<>(2);
        join.put("action", "get_ad");
        join.put("browserCode", member.getBrowserCode());
        String html = null;
        String url = member.getProjectHost().concat("Handler/QueryHandler.ashx");

        try {
            html = HttpClientUtil.findInstance().postHtml(member.getHcConfig().url(url).map(join));
            if (StringTool.isEmpty(html) || !StringTool.isContains(html, "success","data")) {
                log.error("获取用户信息失败，结果信息=" + html);
                Thread.sleep(SLEEP);
                return getUserInfo(member, ++index[0]);
            }
            return JSONObject.parseObject(html);
        } catch (Exception e) {
            log.error("获取用户余额信息失败,错误信息=" + html, e);
            Thread.sleep(SLEEP);
            return getUserInfo(member, ++index[0]);
        }
    }

    /**
     * 获取游戏限额
     *
     * @param member      会员信息
     * @param game        盘口游戏code
     * @param index       循环次数
     * @return 游戏限额信息
     */
    public static JSONArray getQuotaList(MemberCrawler member, String game,int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return new JSONArray();
        }
        String html = null;

        String url = member.getProjectHost().concat("CreditInfo.aspx?id=").concat(game);
        try {
            html = HttpClientUtil.findInstance().getHtml(member.getHcConfig().url(url));
            if (StringTool.isEmpty(html)) {
                log.error("获取游戏限额失败");
                Thread.sleep(SLEEP);
                return getQuotaList(member, game, ++index[0]);
            }
            if(StringTool.isContains(html,"<script>top.location.href='/'</script>","抱歉，處理您的請求時出錯")){
                log.error("获取游戏限额页面失败，错误页面=" + html);
                return null;
            }
            if (!StringTool.isContains(html, "信用資料")) {
                log.error("获取游戏限额页面失败，错误页面=" + html);
                Thread.sleep(SLEEP);
                return getQuotaList(member, game, ++index[0]);
            }
            return getLimit(html);
        } catch (Exception e) {
            log.error("获取游戏限额失败,错误信息=" + html, e);
            Thread.sleep(SLEEP);
            return getQuotaList(member, game, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
        }
    }

    /**
     * 获取赔率信息
     * @param member        会员信息
     * @param betUrl        游戏投注url
     * @param game          游戏类型
     * @param oddsCode      赔率code
     * @param index         循环次数
     * @return
     */
    public static JSONObject getOddsInfo(MemberCrawler member, String betUrl,
                                         String game, String oddsCode,int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return new JSONObject();
        }
        Map<String,Object> join=new HashMap<>(3);
        join.put("action","get_oddsinfo");
        join.put("playid",oddsCode);
        join.put("playpage",game);
        String html = null;
        String url=member.getProjectHost().concat(betUrl).concat("/Handler/Handler.ashx");
        try {
            html = HttpClientUtil.findInstance().postHtml(member.getHcConfig().url(url).map(join));
            if (StringTool.isEmpty(html)) {
                log.error("获取赔率信息页面失败");
                Thread.sleep(SLEEP);
                return getOddsInfo(member, betUrl,game, oddsCode, ++index[0]);
            }
            return JSONObject.parseObject(html);
        } catch (Exception e) {
            log.error("获取赔率信息页面失败,结果信息=" + html, e);
            Thread.sleep(SLEEP);
            return getOddsInfo(member, betUrl,game, oddsCode, ++index[0]);
        }
    }

    /**
     * 获取盘口验证数据
     * @param member    会员信息
     * @param lId       游戏id
     * @param gameUrl   游戏url
     * @param index     循环次数
     * @return
     */
    public static String getJeuValidate(MemberCrawler member, String lId, String gameUrl,int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }

        String url=member.getProjectHost().concat(gameUrl).concat("/index.aspx?lid=").concat(lId).concat("&path=").concat(gameUrl);
        String  html=null;
        try {
            html = HttpClientUtil.findInstance().getHtml(member.getHcConfig().url(url));
            if (StringTool.isEmpty(html)) {
                log.error("获取盘口验证信息失败");
                Thread.sleep(SLEEP);
                return getJeuValidate(member, lId, gameUrl, ++index[0]);
            }
            return StringUtils.substringBetween(html,"var JeuValidate = '","';");
        } catch (Exception e) {
            log.error("获取赔率信息页面失败,结果信息=" + html, e);
            Thread.sleep(SLEEP);
            return getJeuValidate(member, lId, gameUrl, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
        }
    }
    /**
     * 获取盘口验证数据
     * @param member    会员信息
     * @param betUrl    游戏id
     * @param betInfos  投注信息
     * @return
     */
    public static JSONObject betting(MemberCrawler member, String betUrl, Map<String, Object> betInfos) {
        String html=null;
        String url=member.getProjectHost().concat(betUrl).concat("/Handler/Handler.ashx");
        try {
            html = HttpClientUtil.findInstance().postHtml(member.getHcConfig().url(url).map(betInfos));
            if (ContainerTool.isEmpty(html)) {
                log.error("投注失败,结果="+html+",投注项为：" + betInfos);
                return new JSONObject();
            }
            return JSONObject.parseObject(html);
        } finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,betInfos,0,html));
        }
    }
    /**
     * 获取未结算页面
     *
     * @param member      会员信息
     * @param page        页数
     * @param index       循环次数
     * @return
     */
    public static String getIsSettlePage(MemberCrawler member, String game,int page,String masterid, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String url=member.getProjectHost().concat("Bet.aspx?");
        if(StringTool.isEmpty(masterid)){
            url+="Bet.aspx?id="+game;
        }else{
            url+="Bet.aspx?page=".concat(String.valueOf(page)).concat("&masterId=").concat(masterid);
        }
        String html=null;
        try {
            html = HttpClientUtil.findInstance().getHtml(member.getHcConfig().url(url));
            if (StringTool.isEmpty(html)) {
                log.error("获取未结算页面失败", html);
                Thread.sleep(SLEEP);
                return getIsSettlePage(member,game, page,masterid, ++index[0]);
            }
			return html;
        } catch (Exception e) {
            log.error("获取未结算页面失败", e);
            Thread.sleep(SLEEP);
            return getIsSettlePage(member,game, page,masterid, ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
        }
    }




	// TODO 功能函数

	/**
	 * 获取未结算信息
	 * @param html		未结算页面
	 * @return
	 */
	public static JSONArray getIsSettleInfo(String html,GameUtil.Code gameCode,Object roundno) {
		JSONArray array=new JSONArray();
		Document document = Jsoup.parse(html);

		Elements trList =document.getElementById("box_kc").select("tbody>tr");
		trList.remove(0);
		trList.remove(trList.size()-1);
		trList.remove(trList.size()-1);

		if(StringTool.isContains(trList.text(),"無未結算記錄")){
			return array;
		}
		for(Element tr:trList){
			String period=StringUtils.substringBetween(tr.child(1).text(),"# ","期");
			if(!roundno.equals(period)){
				continue;
			}
			String bet=getBetItem(gameCode,tr.child(2).text().split("】")[0]);
			array.add(bet.concat("|")+NumberTool.intValueT(tr.child(3).text()));
		}
		return array;
	}
	/**
	 * 获取投注项
	 *
	 * @param gameCode 投注编码
	 * @param betInfo  投注信息
	 * @return 投注项
	 */
	private static String getBetItem(GameUtil.Code gameCode, String betInfo) {
		String[] infos = betInfo.split("【");

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
     * 解析游戏限额页面
     *
     * @param html 游戏限额页面
     * @return 游戏限额
     */
    private static JSONArray getLimit(String html) {
        Document document = Jsoup.parse(html);

        Elements trs = document.select("tbody.hover_list>tr");
        JSONArray quota = new JSONArray();
        JSONArray array;
        for (Element tr : trs) {
            array = new JSONArray();
            String text = tr.text();
            String[] limits = text.split(" ");
            if(limits.length<5){
                continue;
            }
            array.add(Integer.parseInt(tr.child(2).text()));
            array.add(Integer.parseInt(tr.child(3).text()));
            array.add(Integer.parseInt(tr.child(4).text()));
            quota.add(array);
        }

        return quota;
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
        } else if (StringTool.contains(msg, "用户名错误","密码错误")) {
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
     * 获取会员可用线路
     *
     * @param routeHtml 线路页面
     * @return 线路数组
     */
    public static String[] getMemberRoute(String routeHtml) {
        Document document = Jsoup.parse(routeHtml);

        Elements as = document.select("div.left1>div.box>ul>li>a");
        String[] routes = new String[as.size()];
        for (int i = 0; i < as.size(); i++) {
            routes[i] = as.get(i).attr("href");
        }

        return routes;
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
        BasicClientCookie cookie = new BasicClientCookie("srcurl", StringTool.string2Hax(handicapUrl));
        cookie.setPath("/");
        cookie.setDomain("");
        context.getCookieStore().addCookie(cookie);

		int height = RandomTool.getInt(1000, 1500);
		int width = height / 9 * 16;
		String url =handicapUrl + "?security_verify_data=" + StringTool.string2Hax(width + "," + height);

		HttpClientUtil.findInstance().get(httpConfig.url(url));
    }

    /**
     * 获取赔率id
     * @param gameCode  游戏code
     * @param betItems  投注内容
     * @param odds      赔率
     * @return
     */
    public static Map<String,Object> getBetItemInfo(GameUtil.Code gameCode, List<String> betItems, JSONObject odds) {
        Map<String, String> ballCode=BallCodeTool.getBallCode(HandicapUtil.Code.COM,gameCode);

        Map<String,Object> betInfos=new HashMap<>(8);

        StringBuilder oddsId=new StringBuilder();
        StringBuilder upiP=new StringBuilder();
        StringBuilder upiM=new StringBuilder();
        StringBuilder index=new StringBuilder();

        JSONObject playOdds;
        for(int i=0, length=betItems.size();i<length;i++){
            String[] items =betItems.get(i).split("\\|");
            String bet = items[0].concat("|").concat(items[1]);

            //单注金额须为整数值
            int fund = (int) NumberTool.doubleT(items[2]);

            String betCode = ballCode.get(bet);
            if (StringTool.isEmpty(betCode)) {
                log.error("错误的投注项" + betItems.get(i));
                continue;
            }
            playOdds=odds.getJSONObject(betCode);

            oddsId.append(betCode.split("_")[1]).append(",");
            upiP.append(playOdds.getString("pl")).append(",");
            upiM.append(String.valueOf(fund)).append(",");
            index.append(String.valueOf(i)).append(",");
        }
        oddsId.delete(oddsId.length()-1,oddsId.length());
        upiP.delete(upiP.length()-1,upiP.length());
        upiM.delete(upiM.length()-1,upiM.length());
        index.delete(index.length()-1,index.length());

        betInfos.put("oddsid",oddsId.toString());
        betInfos.put("uPI_P",upiP.toString());
        betInfos.put("uPI_M",upiM.toString());
        betInfos.put("i_index",index.toString());

        return betInfos;
    }
}
