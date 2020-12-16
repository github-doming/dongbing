package com.ibm.common.utils.http.utils.agent;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 代理工具类
 * @Author: null
 * @Date: 2019-12-30 17:56
 * @Version: v1.0
 */
public abstract class BaseAgentUtil implements IAgentCrawler {
    protected Logger log = LogManager.getLogger(this.getClass());
	public String message="盘口【{}】代理【{}】结果信息:{}";
    /**
     *
     */
    public Map<String, AgentCrawler> agentCrawlers;


    public void init() {
        agentCrawlers = new HashMap<>(10);
    }
    /**
     * 清楚盘口代理用户数据
     *
     * @param existHaId 已存在盘口代理id
     */
    public void removeHaInfo(String existHaId) {
        if (StringTool.isEmpty(existHaId)) {
            return;
        }
        agentCrawlers.remove(existHaId);
    }


    @Override public void accountInfo(String existHaId, String agentAccount, String agentPassword, String handicapUrl,
            String handicapCaptcha) {
        AgentCrawler agent;
        if(agentCrawlers.containsKey(existHaId)){
            agent=agentCrawlers.get(existHaId);
        }else{
            agent=new AgentCrawler();
            agentCrawlers.put(existHaId,agent);
        }
        AccountInfo accountInfo=agent.getAccountInfo();
        accountInfo.setItemInfo(agentAccount,agentPassword,handicapUrl,handicapCaptcha);
    }

    @Override public JsonResultBeanPlus login(String existHaId) {
        synchronized (existHaId) {
            JsonResultBeanPlus bean = new JsonResultBeanPlus();
            if(!agentCrawlers.containsKey(existHaId)){
                bean.putEnum(HcCodeEnum.IBS_404_EXIST_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            AgentCrawler agent= agentCrawlers.get(existHaId);

            if (StringTool.isEmpty(agent.getProjectHost())) {
                AccountInfo accountInfo = agent.getAccountInfo();
                return login(existHaId, accountInfo.getAccount(), accountInfo.getPassword(),
                        accountInfo.getHandicapUrl(), accountInfo.getHandicapCaptcha());
            }
        }
        return new JsonResultBeanPlus().success();
    }

    @Override public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String agentAccount, String agentPassword) {
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
            agent.setHcConfig(httpConfig);
            agent.setExistId(existHaId);
            agentCrawlers.put(existHaId,agent);
            bean.setData(existHaId);
        }
        return bean;
    }

    @Override
    public JsonResultBeanPlus checkInfo(String existHaId) {
        synchronized (existHaId) {
            if(!agentCrawlers.containsKey(existHaId)){
                return new JsonResultBeanPlus();
            }
            JsonResultBeanPlus bean;
            AgentCrawler agent= agentCrawlers.get(existHaId);
            //上次校验时间
            long lastTime;
            if(agent.getCheckTime()==0){
                lastTime = System.currentTimeMillis();
                agent.setCheckTime(lastTime);
            }else{
                lastTime=agent.getCheckTime();
            }
            //是否大于上次校验时间
            boolean flag = System.currentTimeMillis() - lastTime > MIN_CHECK_TIME;

            //重新获取数据
            if(flag||agent.getMemberArray()==null){
                //获取会员列表信息
                bean = memberListInfo(existHaId);
                //获取会员列表信息失败，返回空
                if (!bean.isSuccess()) {
                    return bean;
                }
            }else{
                //使用内存数据
                bean = new JsonResultBeanPlus().success(agent.getMemberArray());
            }
            if (flag) {
                agent.setCheckTime(System.currentTimeMillis());
            }
            return bean;
        }
    }

    @Override
    public JSONArray getMemberList(String existHaId) {
        if(!agentCrawlers.containsKey(existHaId)){
            return new JSONArray();
        }
        AgentCrawler agent=agentCrawlers.get(existHaId);
        if(ContainerTool.notEmpty(agent.getMemberArray())){
            return agent.getMemberArray();
        }
        //获取用户信息
        JsonResultBeanPlus bean = memberListInfo(existHaId);
        //获取用户信息失败，返回空
        if (!bean.isSuccess()) {
            return new JSONArray();
        }
        return agent.getMemberArray();
    }
}
