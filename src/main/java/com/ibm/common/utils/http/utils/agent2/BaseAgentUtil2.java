package com.ibm.common.utils.http.utils.agent2;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 代理工具类
 * @Author: null
 * @Date: 2019-12-30 17:56
 * @Version: v1.0
 */
public abstract class BaseAgentUtil2 {
    protected Logger log = LogManager.getLogger(this.getClass());

    public static final Integer MAX_RECURSIVE_SIZE = 5;
    public static final long MAX_CHECK_TIME = 120 * 1000;
    public static final long MIN_CHECK_TIME = 10 * 1000;

    public Map<String, HttpClientConfig> hcConfigMap;
    /**
     * 账户名-密码-网址-验证码
     */
    public Map<String, Map<String, String>> accountMap;
    /**
     * 用户信息-会员列表信息
     */
    public Map<String, JSONArray> userMap;
    /**
     * projectHost：主机地址
     */
    public Map<String, Map<String, String>> agentMap;
    /**
     * 上次成功检验时间
     */
    public Map<String, Long> checkMap;

    public void init() {
        hcConfigMap = new HashMap<>(10);
        userMap = new HashMap<>(10);
        accountMap = new HashMap<>(10);
        agentMap = new HashMap<>(10);
        checkMap= new HashMap<>(10);
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
        userMap.remove(existHaId);
        accountMap.remove(existHaId);
        hcConfigMap.remove(existHaId);
        agentMap.remove(existHaId);
        checkMap.remove(existHaId);
    }



    /**
     * 放入账户信息
     *
     * @param existHaId       盘口代理存在id
     * @param agentAccount    代理账号
     * @param agentPassword   代理密码
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     */
    public void accountInfo(String existHaId, String agentAccount, String agentPassword, String handicapUrl,
            String handicapCaptcha) {
        if (accountMap.containsKey(existHaId)) {
            Map<String, String> data = accountMap.get(existHaId);
            data.put("agentAccount", agentAccount);
            data.put("agentPassword", agentPassword);
            data.put("handicapUrl", handicapUrl);
            data.put("handicapCaptcha", handicapCaptcha);
        } else {
            Map<String, String> data = new HashMap<>(4);
            data.put("agentAccount", agentAccount);
            data.put("agentPassword", agentPassword);
            data.put("handicapUrl", handicapUrl);
            data.put("handicapCaptcha", handicapCaptcha);
            accountMap.put(existHaId, data);
        }
    }

    /**
     * 登陆
     *
     * @param existHaId       盘口代理存在id
     * @param agentAccount    代理账号
     * @param agentPassword   代理密码
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     * @return 登录结果
     */
    public abstract JsonResultBeanPlus login(String existHaId, String agentAccount, String agentPassword, String handicapUrl,
											 String handicapCaptcha);

    /**
     * 验证登录
     * @param handicapUrl       盘口地址
     * @param handicapCaptcha   盘口验证码
     * @param agentAccount      代理账号
     * @param agentPassword     代理密码
     * @return
     */
    public abstract JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String agentAccount,
                                        String agentPassword);

    /**
     * 获取会员列表信息
     * @param existHaId     已存在盘口代理id
     * @return
     */
    public abstract JSONArray getMemberList(String existHaId);

    /**
     * 定时检验
     * @param existHaId     已存在盘口代理id
     * @return
     */
    public abstract JsonResultBeanPlus checkInfo(String existHaId);
}
