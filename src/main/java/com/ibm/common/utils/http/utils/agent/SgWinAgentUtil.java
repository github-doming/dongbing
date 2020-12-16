package com.ibm.common.utils.http.utils.agent;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.SgWinConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.agent.SgWinAgentTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 138代理工具类
 * @Author: null
 * @Date: 2020-04-24 10:44
 * @Version: v1.0
 */
public class SgWinAgentUtil extends BaseAgentUtil {
    private static volatile SgWinAgentUtil instance = null;

	private HandicapUtil.Code handicapCode=HandicapUtil.Code.SGWIN;

    public static SgWinAgentUtil findInstance() {
        if (instance == null) {
            synchronized (SgWinAgentUtil.class) {
                if (instance == null) {
                    SgWinAgentUtil instance = new SgWinAgentUtil();
                    // 初始化
                    instance.init();
                    SgWinAgentUtil.instance = instance;
                }
            }
        }
        return instance;
    }
    /**
     * 销毁工厂
     */
    public static void destroy() {
        if (instance == null) {
            return;
        }
        if (ContainerTool.notEmpty(instance.agentCrawlers)) {
            for (AgentCrawler agentCrawler : instance.agentCrawlers.values()) {
                agentCrawler.getHcConfig().destroy();
            }
        }
        instance.agentCrawlers = null;
        instance = null;
    }
    /**
     * 登陆
     *
     * @param existHaId       已存在盘口代理id
     * @param agentAccount    代理账号
     * @param agentPassword   代理密码
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     * @return 登录结果
     */
    @Override
    public JsonResultBeanPlus login(String existHaId, String agentAccount, String agentPassword, String handicapUrl, String handicapCaptcha) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //已存在数据
        AgentCrawler agent;
        if(agentCrawlers.containsKey(existHaId)){
            agent=agentCrawlers.get(existHaId);
            if(StringTool.notEmpty(agent.getProjectHost())){
                bean.setData(agent.getProjectHost());
                bean.success();
                return bean;
            }
        }else{
            agent=new AgentCrawler();
        }
        AccountInfo accountInfo=agent.getAccountInfo();
        accountInfo.setItemInfo(agentAccount,agentPassword,handicapUrl,handicapCaptcha);
        try {
            //获取配置类
            HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(agent);

            bean = login(httpConfig,accountInfo);
            if (!bean.isSuccess()) {
                return bean;
            }
            Map<String, String> data = (Map<String, String>) bean.getData();

            agent.setProjectHost(data.get("projectHost"));
            if(!agentCrawlers.containsKey(existHaId)){
                agentCrawlers.put(existHaId,agent);
            }
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHaId, "登录失败,失败原因为："+e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    /**
     * 登录
     * @param httpConfig        htpp请求配置类
     * @param accountInfo       账号信息对象
     * @return
     */
    @Override
    public JsonResultBeanPlus login(HttpClientConfig httpConfig, AccountInfo accountInfo) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        String handicapUrl=accountInfo.getHandicapUrl();
        if (!handicapUrl.endsWith("/")) {
            handicapUrl = handicapUrl.concat("/");
        }
        httpConfig.headers(null);
        httpConfig.httpContext(null);
        try {
            //获取线路选择页面
            String routeHtml = SgWinAgentTool.getSelectRoutePage(httpConfig, handicapUrl, accountInfo.getHandicapCaptcha());
            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "线路选择")) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取线路失败,错误页面="+routeHtml);
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //4条代理线路数组
            List<String> hrefs = SgWinAgentTool.getAgentRoute(routeHtml);
            String[] routes = SgWinAgentTool.speedRoute(httpConfig, hrefs);
            if (ContainerTool.isEmpty(routes)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //获取登录信息map
            Map<String, String> loginInfoMap = SgWinAgentTool.getLoginHtml(httpConfig, routes);
            if (ContainerTool.isEmpty(loginInfoMap)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            String loginSrc = loginInfoMap.get("loginSrc");
            //获取验证码
            String verifyCode = SgWinAgentTool.getVerifyCode(httpConfig, loginSrc, loginInfoMap.remove("code"));

            httpConfig.setHeader("Referer", loginSrc.concat("login"));
            httpConfig.httpContext(HttpClientContext.create());
            //盘口协议页面
            String loginInfo = SgWinAgentTool.getLogin(httpConfig, accountInfo.getAccount(), accountInfo.getPassword(), verifyCode, loginSrc,
                    loginInfoMap.remove("action"));

            if (StringTool.isEmpty(loginInfo)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //错误处理和是否等一次登录盘口
            if (!StringTool.isContains(loginInfo, "个人管理") || StringTool.isContains(loginInfo, "修改密码")) {
				log.error(message,handicapCode.name(),accountInfo.getAccount(), "登录的URL="+loginSrc);
                bean.putEnum(SgWinAgentTool.loginError(loginInfo));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            httpConfig.setHeader("Referer", loginSrc.concat("agent/index"));

            Map<String, String> data = new HashMap<>(1);
            data.put("projectHost", loginSrc);
            bean.success(data);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),accountInfo.getAccount(), "登录失败,失败原因为："+e);
            bean.error(e.getMessage());
        } finally {
            httpConfig.defTimeOut();
        }
        return bean;
    }
    /**
     * 获取会员账号信息
     *
     * @param existHaId 已存在盘口代理id
     * @return 会员账号信息
     */
    @Override
    public JsonResultBeanPlus memberListInfo(String existHaId) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        AgentCrawler agent=agentCrawlers.get(existHaId);
        if(StringTool.isEmpty(agent.getProjectHost(),agent.getHcConfig())){
            bean = login(existHaId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        try {
            //获取会员列表
            JSONArray memberArray = SgWinAgentTool.getMemberList(agent.getHcConfig(),agent.getProjectHost());
            if(memberArray==null){
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            agent.setMemberArray(memberArray);
            bean.success();
            bean.setData(memberArray);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHaId, "获取获取会员账号信息失败"+e);
            agent.setProjectHost(null);
        }
        return bean;
    }
    /**
     * 获取未结算摘要
     *
     * @param existHaId 已存在盘口代理id
     * @param gameCode  游戏编码
     * @param ramAgent  已存在的代理数据
     * @return 未结算摘要信息
     */
    public JsonResultBeanPlus getBetSummary(String existHaId, GameUtil.Code gameCode, Map<String, SummaryInfo> ramAgent,
                                            String date) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if(!agentCrawlers.containsKey(existHaId)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        AgentCrawler agent=agentCrawlers.get(existHaId);
        if(StringTool.isEmpty(agent.getProjectHost(),agent.getHcConfig())){
            bean = login(existHaId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        try {
            String lottery = SgWinConfig.BET_CODE.get(gameCode.name());
            //获取未结算摘要
            Map<String, Map<String, SummaryInfo>> betSummary = SgWinAgentTool
                    .getBetSummary(agent.getHcConfig(),agent.getProjectHost(), lottery, date);
            if (betSummary == null) {
                // 重新登录
                agent.setProjectHost(null);
                bean = login(existHaId);
                if (!bean.isSuccess()) {
                    return bean;
                }
                bean.clear();
                betSummary = SgWinAgentTool.getBetSummary(agent.getHcConfig(),agent.getProjectHost(), lottery, date);

            }
            if (ContainerTool.isEmpty(betSummary)) {
                return bean.success();
            }
            Map<String, SummaryInfo> newAgent = betSummary.get("agent");
            //代理需要重新获取下级信息
            getBetSummary(agent, lottery, date, betSummary, ramAgent, newAgent);

            bean.success(betSummary);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHaId, "获取未结算摘要失败"+e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    /**
     * 获取子代理未结算摘要
     *
     * @param agent        代理信息
     * @param lottery     平台游戏编码
     * @param day         获取日期
     * @param betSummary  未结算摘要
     * @param ramAgent    已存在的代理数据
     * @param goAgent     将要获取的代理数据
     */
    private void getBetSummary(AgentCrawler agent, String lottery, String day,
                               Map<String, Map<String, SummaryInfo>> betSummary, Map<String, SummaryInfo> ramAgent,
                               Map<String, SummaryInfo> goAgent) throws InterruptedException {
        if (ContainerTool.isEmpty(goAgent)) {
            return;
        }
        for (Map.Entry<String, SummaryInfo> entry : goAgent.entrySet()) {
            //子代理不存在新的投注
            String agentAccount = entry.getKey();
            SummaryInfo summaryInfo = entry.getValue();
            if (summaryInfo.getBetCount() <= 0 || summaryInfo.equal(ramAgent.get(agentAccount))) {
                continue;
            }
            //获取子代理的未结算摘要
            Map<String, Map<String, SummaryInfo>> newBetSummary = SgWinAgentTool
                    .getBetSummary(agent.getHcConfig(), agent.getProjectHost(), lottery, day, agentAccount);
            //页面读取为空
            if (newBetSummary == null) {
                continue;
            }
            //数据读取为空
            if (newBetSummary.isEmpty()) {
                summaryInfo.setBetCount(0);
                summaryInfo.setBetAmount(0);
                continue;
            }

            //子代理存在新的会员投注-追加
            Map<String, SummaryInfo> newMember = newBetSummary.get("member");
            if (ContainerTool.notEmpty(newMember)) {
                betSummary.get("member").putAll(newMember);
            }
            //子代理存在新的子子代理的未结算摘要
            Map<String, SummaryInfo> newAgent = newBetSummary.get("agent");
            getBetSummary(agent,lottery, day, betSummary, ramAgent, newAgent);

            if (ContainerTool.notEmpty(newAgent)) {
                //将子子代理未结算数据存储
                betSummary.get("agent").putAll(newAgent);
            }
        }
    }
    /**
     * 获取投注详情
     *
     * @param existHaId     已存在盘口代理id
     * @param gameCode      游戏编码
     * @param memberAccount 会员账户
     * @param oddNumber     注单号
     * @param roundno       盘口期数字符串
     * @param date          时间字符串
     * @return 投注详情
     */
    public JsonResultBeanPlus getBetDetail(String existHaId, GameUtil.Code gameCode, String memberAccount,
                                           String oddNumber, Object roundno, String date) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if(!agentCrawlers.containsKey(existHaId)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        AgentCrawler agent=agentCrawlers.get(existHaId);
        if(StringTool.isEmpty(agent.getProjectHost(),agent.getHcConfig())){
            bean = login(existHaId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        try {
            String lottery = SgWinConfig.BET_CODE.get(gameCode.name());

            //获取未结算明细
            String html = SgWinAgentTool
                    .getBetDetail(agent.getHcConfig(),agent.getProjectHost(), null, lottery, date, memberAccount,
                            roundno);
            //解析
            DetailBox betDetail = SgWinAgentTool.analyzeDetail(null, gameCode, html, oddNumber);
            if (betDetail == null) {
                return bean.success();
            }
            //循环所有的页码
            while (betDetail.hasNext()) {
                html = SgWinAgentTool
                        .getBetDetail(agent.getHcConfig(),agent.getProjectHost(), betDetail.getPages().nextPage(),
                                lottery, date, memberAccount, roundno);
                // 没有找到 -数据弹出
                if (SgWinAgentTool.analyzeDetail(betDetail, gameCode, html, oddNumber) == null) {
                    break;
                }
            }
            bean.success(betDetail);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHaId, "获取投注详情失败"+e);
            bean.error(e.getMessage());
        }
        return bean;
    }

}
