package com.ibm.common.utils.http.utils.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.ComConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.agent.ComAgentTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import com.ibm.common.utils.http.utils.entity.ComGameInfo;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: COM代理工具类
 * @Author: null
 * @Date: 2020-04-25 15:41
 * @Version: v1.0
 */
public class ComAgentUtil extends BaseAgentUtil {
    private static volatile ComAgentUtil instance = null;

	private HandicapUtil.Code handicapCode=HandicapUtil.Code.COM;

    public static ComAgentUtil findInstance() {
        if (instance == null) {
            synchronized (ComAgentUtil.class) {
                if (instance == null) {
                    ComAgentUtil instance = new ComAgentUtil();
                    // 初始化
                    instance.init();
                    ComAgentUtil.instance = instance;
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
            agent.setBrowserCode(data.get("browserCode"));
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
        if(!accountInfo.getHandicapUrl().endsWith("/")){
            handicapUrl=handicapUrl.concat("/");
        }
        httpConfig.headers(null);
        httpConfig.httpContext(null);
        try {
            httpConfig.httpContext(HttpClientContext.create());
            //获取线路选择页面
            String routeHtml= ComAgentTool.getSelectRoutePage(httpConfig,handicapUrl,accountInfo.getHandicapCaptcha());
            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "線路選擇")) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "COM获取线路页面失败=" + routeHtml);
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //会员线路数组
            String[] routes =  ComAgentTool.getAgentRoute(routeHtml);
            httpConfig.httpContext().getCookieStore().clear();

            //选择登录线路
            String loginSrc = ComAgentTool.getLoginHtml(httpConfig,routes);
            if (StringTool.isEmpty(loginSrc)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //TODO,暂未处理验证码

            //处理登录结果json
            String loginHtml = ComAgentTool.getLogin(httpConfig,accountInfo.getAccount(),accountInfo.getPassword(),loginSrc);
            if (StringTool.isEmpty(loginHtml)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            JSONObject loginInfo=JSONObject.parseObject(loginHtml);
            //错误处理和是否第一次登录盘口
            if(loginInfo.getInteger("success")!=200||StringTool.notEmpty(loginInfo.get("tipinfo"))){
                bean.putEnum(ComAgentTool.loginError(loginInfo.getString("tipinfo")));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //获取browserCode
            String browserCode= ComAgentTool.getIndex(httpConfig,loginSrc);
            if (StringTool.isEmpty(browserCode)) {
                bean.putEnum(HcCodeEnum.IBS_404_INDEX_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            Map<String, String> data = new HashMap<>(1);
            data.put("projectHost", loginSrc.concat("/"));
            data.put("browserCode",browserCode);

            bean.setData(data);
            bean.success();
        } catch (Exception e) {
			log.error(message,handicapCode.name(),accountInfo.getAccount(), "登录失败,失败原因为：" + e);
            bean.error(e.getMessage());
        } finally {
            httpConfig.defTimeOut();
        }
        return bean;
    }
    @Override
    public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String agentAccount, String agentPassword) {
        // 获取配置类
        HttpClientConfig httpConfig = new HttpClientConfig();
        httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
        AccountInfo accountInfo=new AccountInfo();
        accountInfo.setItemInfo(agentAccount,agentPassword,handicapUrl,handicapCaptcha);
        JsonResultBeanPlus bean = login(httpConfig, accountInfo);
        if (bean.isSuccess()) {
            String existHaId = RandomTool.getNumLetter32();
            //存储账号信息
            AgentCrawler agent=new AgentCrawler();
            agent.setAccountInfo(accountInfo);
            //存储爬虫信息
            Map<String, String> data = (Map<String, String>) bean.getData();
            agent.setProjectHost(data.get("projectHost"));
            agent.setBrowserCode(data.get("browserCode"));
            agent.setHcConfig(httpConfig);
            agent.setExistId(existHaId);
            agentCrawlers.put(existHaId,agent);
            bean.setData(existHaId);
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
        String game=ComConfig.BET_ID.get(GameUtil.Code.XYFT.name());
        try {
            memberArray = ComAgentTool.getMemberList(agent,game);
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
     * @return 未结算摘要信息
     */
    public JsonResultBeanPlus getBetSummary(String existHaId, GameUtil.Code gameCode ,Object period,String date) {
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
        //盘口游戏id
        String game=ComConfig.BET_ID.get(gameCode.name());
        try {
            //获取游戏期数阶段码
            bean=getPhaseoption(agent,gameCode,period,game);
            if(!bean.isSuccess()){
                return bean;
            }
            String phaseoption=bean.getData().toString();
            bean.setData(null);
            bean.setSuccess(false);

            //只获取到会员信息
            Map<String, SummaryInfo> betSummary = ComAgentTool.getBetSummary(agent,game,phaseoption,date);
            if (betSummary == null) {
                // 重新登录
                agent.setProjectHost(null);
                bean = login(existHaId);
                if (!bean.isSuccess()) {
                    return bean;
                }
                bean.clear();
                betSummary = ComAgentTool.getBetSummary(agent,game,phaseoption,date);
            }

            bean.success(betSummary);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHaId, "获取获取会员账号信息失败" + e);
            agent.setProjectHost(null);
        }
        return bean;
    }
    /**
     * 获取期数阶段码
     * @param agent     盘口代理信息
     * @param gameCode  游戏编码
     * @param period    期数
     * @param game      盘口游戏
     * @return
     */
    private JsonResultBeanPlus getPhaseoption(AgentCrawler agent, GameUtil.Code gameCode ,Object period, String game)
            throws InterruptedException {
        JsonResultBeanPlus bean=new JsonResultBeanPlus();

        String phaseoption;
        if(agent.getPhaseoptionMap().containsKey(gameCode)){
            ComGameInfo gameInfo=agent.getPhaseoptionMap().get(gameCode);
            if(period.equals(gameInfo.getPeriod())){
                return bean.success(gameInfo.getPhaseoption());
            }
        }
        //获取期数阶段码
        JSONObject result=ComAgentTool.getPhaseoption(agent,game);
        if(StringTool.isEmpty(result)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        if(result.getInteger("success")!=200||StringTool.isEmpty(result.get("data"))||
                !result.getJSONObject("data").containsKey("phaseoption")){
            agent.setProjectHost(null);
            bean.putEnum(HcCodeEnum.IBS_404_BET_INFO);
            bean.putSysEnum(HcCodeEnum.CODE_404);
            return bean;
        }
        phaseoption=result.getJSONObject("data").getString("phaseoption").split(",")[0];
        ComGameInfo gameInfo;
        if(agent.getPhaseoptionMap().containsKey(gameCode)){
            gameInfo = agent.getPhaseoptionMap().get(gameCode);
        }else{
            gameInfo=new ComGameInfo();
            agent.getPhaseoptionMap().put(gameCode,gameInfo);
        }
        gameInfo.setPhaseoption(phaseoption);
        gameInfo.setPeriod(period);

        return bean.success(phaseoption);
    }
    /**
     * 获取投注详情
     * @param existHaId     已存在盘口代理id
     * @param gameCode      游戏code
     * @param oddNumber     注单号
     * @param summaryInfo   投注摘要信息
     * @param period        期数
     * @param date          时间
     * @return
     */
    public JsonResultBeanPlus getBetDetail(String existHaId,GameUtil.Code gameCode, String oddNumber,
                                           SummaryInfo summaryInfo,Object period ,String date) {
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
        //盘口游戏id
        String game=ComConfig.BET_ID.get(gameCode.name());
        try {
            //获取游戏期数阶段码
            bean=getPhaseoption(agent,gameCode,period,game);
            if(!bean.isSuccess()){
                return bean;
            }
            String phaseoption=bean.getData().toString();
            bean.setData(null);
            bean.setSuccess(false);

            //获取未结算明细
            String html  = ComAgentTool.getBetDetail(agent,summaryInfo,game,null,phaseoption,date);
            //解析
            DetailBox betDetail =ComAgentTool.analyzeDetail(null, gameCode, html, oddNumber);
            if (betDetail == null) {
                return bean.success();
            }
            //循环所有的页码
            while (betDetail.hasNext()) {
                html=  ComAgentTool.getBetDetail(agent,summaryInfo,game,betDetail.getPages().nextPage(),phaseoption,date);
                // 没有找到 -数据弹出
                betDetail =ComAgentTool.analyzeDetail(betDetail, gameCode, html, oddNumber);
                if(betDetail==null){
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
