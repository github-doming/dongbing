package com.ibm.common.test.wwj.handicap.sgwin;

import com.ibm.common.test.wwj.handicap.GrabAgent;
import com.ibm.common.test.wwj.handicap.HttpType;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/2 15:39
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class SgWinAgentGrab extends AbsSgWinGrab implements GrabAgent {

    private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";


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

        return routes(httpConfig, getHrefs(routeHtml,"代理线路"));
    }


    @Override
    public String login(HttpClientConfig httpConfig,String loginUrl, String verifyCode,  String account, String password) {
        Map<String, Object> join = new HashMap<>(5);
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
        return null;
    }

    /**
     * 获取会员列表
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @return 会员列表
     */
    public List<Map<String,String>> memberListInfo(HttpClientConfig httpConfig, String projectHost, int... index){
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String url = projectHost.concat("/agent/user/list?type=1");
        String html=null;
        try {
            html = html(httpConfig.url(url).method(HttpConfig.Method.GET), HttpType.MemberList);
            if (StringTool.isEmpty(html)) {
                log.debug("获取会员列表信息失败,结果信息为空");
                sleep("获取会员列表");
                return memberListInfo(httpConfig, projectHost, ++index[0]);
            }
            return getMemberInfo(html);
        } catch (Exception e) {
            log.error("获取会员列表失败,结果信息="+html, e);
            longSleep("获取会员列表");
            return memberListInfo(httpConfig, projectHost, ++index[0]);
        }
    }

    /**
     * 获取未结算摘要信息
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param lottery     抓取的游戏
     * @param day         抓取的天数
     * @return 未结算摘要信息
     */
    public Map<String, Map<String, Integer>> getBetSummary(HttpClientConfig httpConfig, String projectHost,
                                                        String lottery, String day, int... index){
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String parametersFormat = "begin=%s&end=%s&settle=false&amount=&dividend=&lottery=%s&period=";
        httpConfig.url(projectHost.concat("/agent/report/list?&").concat(String.format(parametersFormat, day, day, lottery)));
        try {
            String html = html(httpConfig.method(HttpConfig.Method.GET),HttpType.BetSummary);
            if (StringTool.isEmpty(html)) {
                log.debug("获取未结算摘要信息失败,结果信息为空");
                sleep("未结算摘要信息");
                return getBetSummary(httpConfig, projectHost, lottery, day, ++index[0]);
            }
            Map<String, Map<String, Integer>> betSummary = getBetSummary(html);
            if (betSummary == null){
                return getBetSummary(httpConfig, projectHost, lottery, day, ++index[0]);
            }
            return betSummary;
        } catch (Exception e) {
            log.error("未结算摘要信息失败", e);
            longSleep("未结算摘要信息");
            return getBetSummary(httpConfig, projectHost, lottery, day, ++index[0]);
        }
    }

    /**
     * 获取子代理未结算摘要信息
     *
     * @param httpConfig   http请求配置类
     * @param projectHost  主机地址
     * @param lottery      抓取的游戏
     * @param day          抓取的天数
     * @param agentAccount 子代理账户
     * @return 子代理未结算摘要信息
     */
    public Map<String, Map<String, Integer>> getBetSummary(HttpClientConfig httpConfig, String projectHost,
                                                                  String lottery, String day, String agentAccount, int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String url = projectHost.concat("agent/report/list?&");
        String parametersFormat = "username=%s&lottery=%s&begin=%s&end=%s&settle=false";
        httpConfig.url(url.concat(String.format(parametersFormat, agentAccount, lottery, day, day)));
        try {
            String html = html(httpConfig.method(HttpConfig.Method.GET),HttpType.BetSummary);
            if (StringTool.isEmpty(html)) {
                log.debug("获取未结算摘要信息失败,结果信息为空");
                sleep("获取子代理未结算摘要信息");
                return getBetSummary(httpConfig, projectHost, lottery, day, agentAccount, ++index[0]);
            }
            return getBetSummary(html);
        } catch (Exception e) {
            log.error("获取子代理未结算摘要信息失败", e);
            longSleep("获取子代理未结算摘要信息");
            return getBetSummary(httpConfig, projectHost, lottery, day, agentAccount, ++index[0]);
        }
    }

    /**
     * 获取投注详情
     *
     * @param httpConfig    http请求配置类
     * @param projectHost   主机地址
     * @param page          获取的页码
     * @param lottery       抓取的游戏
     * @param day           抓取的天
     * @param memberAccount 会员账户
     * @return 投注详情
     */
    public String betDetail(HttpClientConfig httpConfig, String projectHost, Integer page, String lottery,
                            String day, String memberAccount, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String url = projectHost.concat("/agent/report/bets?");
        String parametersFormat = "username=%s&lottery=%s&begin=%s&end=%s&settle=false";
        String parameters = String.format(parametersFormat, memberAccount, lottery, day, day, page);
        if (page != null) {
            parameters = parameters.concat("&page=" + page);
        }
        try {
            String html = html(httpConfig.url(url.concat(parameters)).method(HttpConfig.Method.GET),HttpType.BetDetail);
            if (StringTool.isEmpty(html)) {
                log.debug("获取未结算摘要信息失败,结果信息为空");
                sleep("获取投注详情");
                return betDetail(httpConfig, projectHost, page, lottery, day, memberAccount, ++index[0]);
            }
            return html;
        } catch (Exception e) {
            log.error("获取投注详情失败", e);
            longSleep("获取投注详情");
            return betDetail(httpConfig, projectHost, page, lottery, day, memberAccount, ++index[0]);
        }
    }

}
