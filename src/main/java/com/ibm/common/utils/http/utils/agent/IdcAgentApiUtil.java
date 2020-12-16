package com.ibm.common.utils.http.utils.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.IdcConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.agent.IdcAgentApiTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: IDC代理工具类
 * @Author: null
 * @Date: 2020-04-24 11:31
 * @Version: v1.0
 */
public class IdcAgentApiUtil extends BaseAgentUtil {

    private static volatile IdcAgentApiUtil instance = null;

	private HandicapUtil.Code handicapCode=HandicapUtil.Code.IDC;

    public static IdcAgentApiUtil findInstance() {
        if (instance == null) {
            synchronized (IdcAgentApiUtil.class) {
                if (instance == null) {
                    IdcAgentApiUtil instance = new IdcAgentApiUtil();
                    // 初始化
                    instance.init();
                    IdcAgentApiUtil.instance = instance;
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
     * @return
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
            agent.setTicket(data.get("ticket"));
            agent.setMemberno(data.get("memberno"));
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
            JSONObject loginHtml = IdcAgentApiTool.getLoginUrl(httpConfig, accountInfo.getHandicapUrl(), accountInfo.getHandicapCaptcha());
            if (ContainerTool.isEmpty(loginHtml) || "1".equals(loginHtml.getString("code"))) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取登陆URL"+loginHtml);
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            String loginUrl = loginHtml.getString("login_url");

            String sign = String.format("accounts=%s&pwd=%s&extwagerno=%s&extwagerkey=%s", accountInfo.getAccount(), accountInfo.getPassword(),
                    IdcConfig.PERMISSION_CODE, IdcConfig.PERMISSION_KEY);

            sign = Md5Tool.md5Hex(sign);

            String parameters = String.format("&accounts=%s&pwd=%s&extwagerno=%s&sign=%s", accountInfo.getAccount(), accountInfo.getPassword(),
                    IdcConfig.PERMISSION_CODE, sign);

            JSONObject loginInfo = IdcAgentApiTool.getLoginTicket(httpConfig, loginUrl, parameters);
            if (ContainerTool.isEmpty(loginInfo)) {
                bean.putEnum(HcCodeEnum.IBS_403_LOGIN_FAIL);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            if ("1".equals(loginInfo.get("code"))) {
				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取登录信息=" + loginInfo);
                bean.putEnum(IdcAgentApiTool.loginError(loginInfo.getString("msg")));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            Map<String, String> data = new HashMap<>(2);
            data.put("projectHost", loginInfo.getString("api_url"));
            data.put("ticket", loginInfo.getString("ticket"));
            if(loginInfo.getBoolean("issub")){
                data.put("memberno", loginInfo.getString("memberno"));
            }else{
                data.put("memberno", accountInfo.getAccount());
            }
            bean.setData(data);
            bean.success();
        } catch (Exception e) {
			log.error(message,handicapCode.name(),accountInfo.getAccount(), "登录失败,失败原因为：" + e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    /**
     * 检验登录
     *
     * @param handicapUrl     盘口url
     * @param handicapCaptcha 盘口验证码
     * @param agentAccount    代理账号
     * @param agentPassword   代理密码
     * @return
     */
    @Override
    public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String agentAccount,
                                        String agentPassword) {
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
            agent.setTicket(data.get("ticket"));
            agent.setMemberno(data.get("memberno"));
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
     * @return
     */
    @Override
    public JsonResultBeanPlus memberListInfo(String existHaId) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        AgentCrawler agent=agentCrawlers.get(existHaId);
        int i=0;
        JSONArray memberArray;
        while (i<5){
            if(StringTool.isEmpty(agent.getProjectHost(),agent.getHcConfig(),agent.getTicket())){
                bean = login(existHaId);
                if (!bean.isSuccess()) {
                    return bean;
                }
                bean.setData(null);
                bean.setSuccess(false);
            }
            try {
                JSONObject memberList = IdcAgentApiTool.getMemberList(agent.getHcConfig(),agent.getProjectHost(), agent.getTicket());
                if (ContainerTool.isEmpty(memberList)||"2".equals(memberList.get("code"))) {
					log.info(message,handicapCode.name(),existHaId, "获取会员列表信息失败=" + memberList);
                    agent.setProjectHost(null);
                    i++;
                    continue;
                }
                if ("1".equals(memberList.get("code"))) {
					log.info(message,handicapCode.name(),existHaId, "获取会员列表信息错误=" + memberList);
                    bean.setCode(IbmCodeEnum.IBM_404_DATA.getCode());
                    bean.setMsg(memberList.getString("msg"));
                    bean.putSysEnum(CodeEnum.CODE_404);
                    return bean;
                }
                if (StringTool.notEmpty(memberList.get("ticket"))) {
                    agent.setTicket(memberList.getString("ticket"));
                }
                memberArray = IdcAgentApiTool.getMemberDetailed(memberList.getJSONArray("data"));
                agent.setMemberArray(memberArray);
                bean.setData(memberArray);
                bean.success();
                break;
            } catch (Exception e) {
				log.error(message,handicapCode.name(),existHaId, "获取会员账号信息失败=" + e);
                break;
            }
        }
        return bean;
    }
    /**
     * 获取当前期数未结明细
     *
     * @param existHaId     已存在盘口代理id
     * @param gameCode      游戏code
     * @param memberList    指定会员,为""则获取所有会员投注信息
     * @param lastOddNumber 最后一笔的注单号
     * @param index         循环次数
     * @return
     */
    public JsonResultBeanPlus getUnsettledDetailed(String existHaId, GameUtil.Code gameCode, String memberList,
                                                   String lastOddNumber, int... index) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        if(!agentCrawlers.containsKey(existHaId)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        AgentCrawler agent=agentCrawlers.get(existHaId);
        if(StringTool.isEmpty(agent.getProjectHost(),agent.getHcConfig(),agent.getTicket(),agent.getMemberno())){
            bean = login(existHaId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        String game = IdcConfig.BET_CODE.get(gameCode.name());
        try {
            JSONObject unSettle = IdcAgentApiTool
                    .getUnsettledDetailed(agent.getHcConfig(),agent.getProjectHost(), agent.getTicket(),
                            agent.getMemberno(), memberList, game, lastOddNumber);
            if (ContainerTool.isEmpty(unSettle)||"2".equals(unSettle.get("code"))) {
				log.info(message,handicapCode.name(),existHaId, "获取未结算明细失败=" + unSettle);
                agent.setProjectHost(null);
                return getUnsettledDetailed(existHaId, gameCode, memberList, lastOddNumber, ++index[0]);
            }
            if ("1".equals(unSettle.get("code"))) {
				log.info(message,handicapCode.name(),existHaId, "获取未结算明细错误=" + unSettle);
                bean.setCode(CodeEnum.IBS_404_DATA.getCode());
                bean.setMsg(unSettle.getString("msg"));
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            //更换旧票证
            if (StringTool.notEmpty(unSettle.get("ticket"))) {
                agent.setTicket(unSettle.getString("ticket"));
            }
			Map<String,Object> data=new HashMap<>(2);

			JSONArray unsettledDetailed=new JSONArray();
            int oddNumberMax=IdcAgentApiTool.getUnsettledDetailedInfo(gameCode, unSettle.getJSONArray("data"),agent.getMemberno(),unsettledDetailed);
			data.put("oddNumberMax",oddNumberMax);
			data.put("unsettledDetailed",unsettledDetailed);
            bean.success();
            bean.setData(data);
        } catch (Exception e) {
			log.error(message,handicapCode.name(),existHaId, "获取获取未结算投注信息失败=" + e);
        }
        return bean;
    }
}
