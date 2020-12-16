package com.ibm.common.test.wwj.handicap.sgwin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.test.wwj.handicap.GrabMember;
import com.ibm.common.test.wwj.handicap.HttpType;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/3 16:26
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class SgWinMemberGrab extends AbsSgWinGrab implements GrabMember{

    /**
     * 根据线路页面解析的到线路并完成测速排序后输出
     *
     * @param httpConfig 请求配置
     * @param routeHtml  获取线路界面
     * @param index      循环次数
     * @return 测速后的线路数组
     */
    @Override
    public String[] routes(HttpClientConfig httpConfig, String routeHtml, int... index) {
        return routes(httpConfig, getHrefs(routeHtml, "会员线路"));
    }

    /**
     * 登录
     *
     * @param httpConfig
     * @param loginUrl   登录地址
     * @param verifyCode 验证码
     * @param account    盘口账号
     * @param password   盘口密码
     * @return 登录结果
     */
    @Override
    public String login(HttpClientConfig httpConfig, String loginUrl, String verifyCode, String account, String password) {
        Map<String, Object> join = new HashMap<>(4);
        join.put("type", 1);
        join.put("account", account);
        join.put("password", password);
        join.put("code", verifyCode);
        return login(httpConfig, join, verifyCode, loginUrl);
    }

    /**
     * 进入主页面
     *
     * @param httpConfig
     * @param homeUrl    主页面地址
     * @return 主页面
     */
    @Override
    public String home(HttpClientConfig httpConfig, String homeUrl) {
        return home(httpConfig, homeUrl, 0);
    }

    /**
     * 获取用户信息
     *
     * @param httpConfig
     * @param projectHost
     * @param index
     */
    public JSONObject getUserInfo(HttpClientConfig httpConfig, String projectHost, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String html = null;
        JSONObject userInfo = null;
        String url = projectHost.concat("member/accounts?_=") + System.currentTimeMillis();
        try {
            html = html(httpConfig.url(url).method(HttpConfig.Method.GET), HttpType.UserInfo);
            if (ContainerTool.isEmpty(html)) {
                log.error("获取用户信息失败");
                sleep("获取用户信息");
                return getUserInfo(httpConfig, projectHost, ++index[0]);
            }
            JSONArray array = JSONArray.parseArray(html);
            for (Object object : array) {
                userInfo = (JSONObject) object;
                if (userInfo.containsKey("maxLimit") && userInfo.getInteger("maxLimit") > 0) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("获取用户余额信息失败,错误信息=" + html, e);
            sleep("获取用户信息");
            return getUserInfo(httpConfig, projectHost, ++index[0]);
        }
        return userInfo;
    }

    /**
     * 获取游戏限额
     * @param httpConfig
     * @param projectHost
     * @param game
     * @param index
     * @return
     */
    public JSONArray getGameLimit(HttpClientConfig httpConfig, String projectHost, String game, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String html = null;
        String url = projectHost.concat("member/info?lottery=").concat(game);
        try {
            html = html(httpConfig.url(url).method(HttpConfig.Method.GET), HttpType.GameLimit);
            if (StringTool.isEmpty(html)) {
                log.error("获取游戏限额失败");
                sleep("获取游戏限额失败");
                return getGameLimit(httpConfig, projectHost, game, ++index[0]);
            }
        } catch (Exception e) {
            log.error("获取游戏限额失败,错误信息=" + html, e);
            sleep("获取游戏限额失败");
            return getGameLimit(httpConfig, projectHost, game, ++index[0]);
        }
        return getLimit(html);
    }

    /**
     * 获取赔率信息
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param gameCode    盘口游戏code
     * @param oddsCode    赔率code
     * @param index       循环次数
     * @return
     */
    public JSONObject getOddsInfo(HttpClientConfig httpConfig, String projectHost, String gameCode,
                                  String oddsCode, int... index){
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String html = null;
        String url = projectHost.concat("member/odds?lottery=").concat(gameCode).concat("&games=").concat(oddsCode)
                .concat("&_=") + System.currentTimeMillis();
        try {

            html = html(httpConfig.url(url).method(HttpConfig.Method.GET), HttpType.OddsInfo);
            if (StringTool.isEmpty(html)) {
                log.error("获取赔率信息页面失败");
                sleep("获取赔率信息页面");
                return getOddsInfo(httpConfig, projectHost, gameCode, oddsCode, ++index[0]);
            }
            return JSONObject.parseObject(html);
        } catch (Exception e) {
            log.error("获取赔率信息页面失败,结果信息=" + html, e);
            sleep("获取赔率信息页面");
            return getOddsInfo(httpConfig, projectHost, gameCode, oddsCode, ++index[0]);
        }
    }

    /**
     * 投注
     * @param httpConfig
     * @param projectHost
     * @param betsArray
     * @param period
     * @param gameCode
     * @return
     */
    public JSONObject betting(HttpClientConfig httpConfig, String projectHost, JSONArray betsArray,
                              String period, String gameCode) {
        httpConfig.setHeader("Content-Type", "application/json");
        JSONObject info = new JSONObject();
        info.put("bets", betsArray);
        info.put("drawNumber", period);
        info.put("ignore", false);
        info.put("lottery", gameCode);
        Map<String, Object> join = new HashMap<>(1);
        join.put("$ENTITY_JSON$", info);
        String resultHtml = null;
        String url=projectHost.concat("member/bet");
        try {
            resultHtml = html(httpConfig.url(url).method(HttpConfig.Method.POST).map(join),HttpType.Betting);
            if (ContainerTool.isEmpty(resultHtml)) {
                log.error("投注结果页面为空,投注项为：" + betsArray);
                return null;
            }
            return JSONObject.parseObject(resultHtml);
        } catch (Exception e) {
            log.error("投注失败,投注结果="+resultHtml, e);
            return null;
        }
    }

    /**
     * 获取未结算信息
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param game        盘口游戏code
     * @param index       循环次数
     * @return 未结算信息
     */
    protected String getIsSettle(HttpClientConfig httpConfig, String projectHost, String game, int... index){
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String url =
                projectHost.concat("member/lasts?lottery=").concat(game).concat("&_=") + System.currentTimeMillis();

        try {
            String html = html(httpConfig.url(url).method(HttpConfig.Method.GET),HttpType.IsSettle);
            if (StringTool.isEmpty(html)) {
                log.error("获取未结算页面失败", html);
                sleep("获取未结算页面");
                return getIsSettle(httpConfig, projectHost, game, ++index[0]);
            }
            return html;
        } catch (Exception e) {
            log.error("获取未结算页面失败", e);
            sleep("获取未结算页面");
            return getIsSettle(httpConfig, projectHost, game, ++index[0]);
        }
    }

}
