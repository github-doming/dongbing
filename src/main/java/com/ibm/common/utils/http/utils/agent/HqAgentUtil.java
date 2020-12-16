package com.ibm.common.utils.http.utils.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.HqConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.agent.HqAgentTool;
import com.ibm.common.utils.http.utils.RSAUtils;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: HQ代理工具类
 * @Author: null
 * @Date: 2020-04-24 14:08
 * @Version: v1.0
 */
public class HqAgentUtil extends BaseAgentUtil {
    private static volatile HqAgentUtil instance = null;
	private HandicapUtil.Code handicapCode=HandicapUtil.Code.HQ;

    public static HqAgentUtil findInstance() {
        if (instance == null) {
            synchronized (HqAgentUtil.class) {
                if (instance == null) {
                    HqAgentUtil instance = new HqAgentUtil();
                    // 初始化
                    instance.init();
                    HqAgentUtil.instance = instance;
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
        httpConfig.headers(null).httpContext(null);
        try {
            httpConfig.httpContext(HttpClientContext.create());
            //获取代理登入href
            String href = HqAgentTool.getAgentHref(httpConfig, accountInfo.getHandicapUrl(), accountInfo.getHandicapCaptcha());
            if (StringTool.isEmpty(href)) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取代理登入页面失败");
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //获取线路
            String[] routes = HqAgentTool.getSelectRoutePage(httpConfig, href, accountInfo.getHandicapUrl());
            if (ContainerTool.isEmpty(routes)) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取代理线路失败");
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            httpConfig.httpContext().getCookieStore().clear();
            //获取登录页面
            Map<String, String> loginInfo = HqAgentTool.getLoginHtml(httpConfig, routes, accountInfo.getHandicapCaptcha());
            if (ContainerTool.isEmpty(loginInfo) || !loginInfo.containsKey("hostUrl")) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取登录页面信息失败,错误信息="+loginInfo);
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            Map<String, String> scriptInfo = HqAgentTool.getScriptInfo(loginInfo.get("html"));
            if (ContainerTool.isEmpty(scriptInfo)) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取登录页面失败");
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            String projectHost = loginInfo.get("hostUrl");
            //加密key
            JSONObject encryptKey = HqAgentTool.getEncryptKey(httpConfig, scriptInfo.get("SESSIONID"), projectHost);
            if (ContainerTool.isEmpty(encryptKey)) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取登录加密key失败");
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            String pke = encryptKey.getString("e");
            String pk = encryptKey.getString("m");
            String logpk = pke.concat(",").concat(pk);

            RSAPublicKey pubKey = RSAUtils
                    .getPublicKey(new BigInteger(pk, 16).toString(), Integer.parseInt(pke, 16) + "");
            String account = URLEncoder.encode(accountInfo.getAccount().trim(), "UTF-8").concat(",")
                    .concat(URLEncoder.encode(accountInfo.getPassword().trim(), "UTF-8"));
            String mi = RSAUtils.encryptByPublicKey(account, pubKey).toLowerCase();
            //登录
            String loginHtml = HqAgentTool
                    .getLogin(httpConfig, projectHost, scriptInfo.get("SESSIONID"), accountInfo.getHandicapCaptcha(), mi, logpk);
            if (StringTool.isEmpty(loginHtml)) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "登录盘口失败");
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            if (!StringTool.isContains(loginHtml,"Status")) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取登录信息=" + loginInfo);
                bean.putEnum(HqAgentTool.loginError(loginHtml));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            Map<String, String> data = new HashMap<>(1);
            data.put("projectHost", "http://".concat(projectHost).concat(scriptInfo.get("SESSIONID")).concat("/"));

            bean.setData(data);
            bean.success();
        } catch (Exception e) {
			log.error(message,handicapCode.name(),accountInfo.getAccount(), "登录失败,失败原因为：" + e);
            bean.error(e.getMessage());
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
        JSONArray memberArray;
        try {
            //获取主页面
            Map<String, Object> indexMap = HqAgentTool.getIndex(agent.getHcConfig(),agent.getProjectHost());
            if (ContainerTool.isEmpty(indexMap)) {
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            memberArray = HqAgentTool.getMemberList(agent.getHcConfig(),agent.getProjectHost(), indexMap.get("moneyType"));
            if(memberArray==null){
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            agent.setMemberArray(memberArray);
            bean.success();
            bean.setData(memberArray);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHaId, "获取获取会员账号信息失败" + e);
            agent.setProjectHost(null);
        }
        return bean;
    }
    /**
     * 获取未结算摘要信息
     *
     * @param existHaId 已存在盘口代理id
     * @param gameCode  游戏编码
     * @param period    期数
     * @param ramAgent  已存在的代理数据
     * @return 未结算摘要信息
     */
    public JsonResultBeanPlus getBetSummary(String existHaId, GameUtil.Code gameCode, Object period, Map<String, SummaryInfo> ramAgent) {
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
            String day = DateTool.getDate(new Date());
            String lottery = HqConfig.BET_CODE.get(gameCode.name());
            //获取未结算摘要,这里只可能获取到代理列表
            Map<String, Map<String, SummaryInfo>> betSummary = HqAgentTool
                    .getBetSummary(agent.getHcConfig(), agent.getProjectHost(), lottery, period, day);
            if (betSummary == null) {
                // 重新登录
                agent.setProjectHost(null);
                bean = login(existHaId);
                if (!bean.isSuccess()) {
                    return bean;
                }
                bean.clear();
                betSummary = HqAgentTool.getBetSummary(agent.getHcConfig(), agent.getProjectHost(), lottery, period, day);
            }
            if (ContainerTool.isEmpty(betSummary)) {
                return bean.success();
            }
            Map<String, SummaryInfo> newAgent = betSummary.get("agent");
            //代理需要重新获取下级信息
            getBetSummary(agent, lottery, period,day, betSummary,ramAgent, newAgent);

            bean.success(betSummary);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHaId, "获取获取会员账号信息失败" + e);
            agent.setProjectHost(null);
        }
        return bean;
    }
    /**
     * 循环抓取子代理下会员信息
     * @param agent             代理信息
     * @param lottery           平台游戏编码
     * @param period             期数
     * @param day               日期
     * @param betSummary        未结算摘要
     * @param goAgent           将要获取的代理数据
     */
    private void getBetSummary(AgentCrawler agent, String lottery, Object period, String day,
                               Map<String, Map<String, SummaryInfo>> betSummary,Map<String, SummaryInfo> ramAgent, Map<String, SummaryInfo> goAgent) throws InterruptedException {
        if (ContainerTool.isEmpty(goAgent)) {
            return;
        }
        //抓到的代理就一定有投注项
        for (Map.Entry<String, SummaryInfo> entry : goAgent.entrySet()) {
            //子代理不存在新的投注
            String agentAccount = entry.getKey();
            SummaryInfo summaryInfo=entry.getValue();

            if (summaryInfo.getBetCount() <= 0 || summaryInfo.equal(ramAgent.get(agentAccount))) {
                continue;
            }
            Map<String, Map<String, SummaryInfo>> newBetSummary =HqAgentTool.getBetSummaryById(agent.getHcConfig(),agent.getProjectHost(),lottery,
                    period,day, summaryInfo.getMemberId());
            //页面读取为空
            if (newBetSummary == null) {
                continue;
            }
            //子代理存在新的会员投注-追加
            Map<String, SummaryInfo> newMember = newBetSummary.get("member");
            if (ContainerTool.notEmpty(newMember)) {
                betSummary.get("member").putAll(newMember);
            }
            Map<String, SummaryInfo> newAgent = newBetSummary.get("agent");
            getBetSummary(agent, lottery,period, day, betSummary, ramAgent, newAgent);
            if (ContainerTool.notEmpty(newAgent)) {
                //将子子代理未结算数据存储
                betSummary.get("agent").putAll(newAgent);
            }
        }
    }

    /**
     * 获取投注详情
     *
     * @param existHaId 已存在盘口代理id
     * @param gameCode  游戏编码
     * @param period    期数
     * @param memberId  会员ID
     * @param oddNumber 注单号
     * @return 投注详情
     */
    public JsonResultBeanPlus getBetDetail(String existHaId, GameUtil.Code gameCode, Object period, Object memberId,
                                           String oddNumber) {
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
            String day = DateTool.getDate(new Date());
            String lotteryId = HqConfig.BET_CODE.get(gameCode.name());

            //获取未结算明细
            String html = HqAgentTool
                    .getBetDetail(agent.getHcConfig(), agent.getProjectHost(), lotteryId, period, day, memberId);
            DetailBox betDetail = HqAgentTool.analyzeDetail(null, gameCode, html, oddNumber);
            if (betDetail == null) {
                return bean.success();
            }
            //循环所有的页码
            while (betDetail.hasNext()) {
                html = HqAgentTool.getBetDetail(agent.getHcConfig(), agent.getProjectHost(), lotteryId, period, day, memberId,
                                betDetail.getPages().nextPage(), betDetail.getPages().pageSize());
                // 没有找到 -数据弹出
                if (HqAgentTool.analyzeDetail(betDetail, gameCode, html, oddNumber) == null) {
                    break;
                }
            }
            bean.success(betDetail);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHaId, "获取投注详情失败" + e);
            agent.setProjectHost(null);
        }
        return bean;
    }
}
