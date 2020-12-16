package com.ibm.common.utils.http.utils.agent2;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.agent.Ncom1AgentTool;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: NCOM1代理工具类
 * @Author: null
 * @Date: 2020-01-07 13:49
 * @Version: v1.0
 */
public class Ncom1AgentUtil2 extends BaseAgentUtil2 {
	private static volatile Ncom1AgentUtil2 instance = null;

	public static Ncom1AgentUtil2 findInstance() {
		if (instance == null) {
			synchronized (Ncom1AgentUtil2.class) {
				if (instance == null) {
                    Ncom1AgentUtil2 instance = new Ncom1AgentUtil2();
					// 初始化
					instance.init();
                    Ncom1AgentUtil2.instance = instance;
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
		if (ContainerTool.notEmpty(instance.hcConfigMap)) {
			for (HttpClientConfig clientConfig : instance.hcConfigMap.values()) {
				clientConfig.destroy();
			}
		}
		instance.hcConfigMap = null;
		instance.userMap = null;
		instance.accountMap = null;
		instance.agentMap = null;
		instance = null;
	}

    /**
     * 登录
     * @param existHaId       盘口代理存在id
     * @param agentAccount    代理账号
     * @param agentPassword   代理密码
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     * @return
     */
	@Override
    public JsonResultBeanPlus login(String existHaId, String agentAccount, String agentPassword,
			String handicapUrl, String handicapCaptcha) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //已存在数据
        if (agentMap.containsKey(existHaId)) {
            bean.setData(agentMap.get(existHaId));
            bean.success();
            return bean;
        }
        accountInfo(existHaId,agentAccount,agentPassword,handicapUrl,handicapCaptcha);
        try {
            //获取配置类
            HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHaId);

            bean = login(httpConfig, handicapUrl, handicapCaptcha, agentAccount, agentPassword);
            if (!bean.isSuccess()) {
                return bean;
            }
            Map<String, String> data = (Map<String, String>) bean.getData();
            agentMap.put(existHaId, data);
        } catch (Exception e) {
            log.error("Ncom1盘口代理【" + existHaId + "】登录失败,失败原因为：", e);
            bean.error(e.getMessage());
        }
        return bean;
	}
	@Override
    public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String agentAccount,
			String agentPassword) {
        // 获取配置类
        HttpClientConfig httpConfig = new HttpClientConfig();
        httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
        JsonResultBeanPlus bean = login(httpConfig, handicapUrl, handicapCaptcha, agentAccount, agentPassword);
        if (bean.isSuccess()) {
            String existHaId = RandomTool.getNumLetter32();
            //存储账号信息
            Map<String, String> account = new HashMap<>(4);
            account.put("agentAccount", agentAccount);
            account.put("agentPassword", agentPassword);
            account.put("handicapUrl", handicapUrl);
            account.put("handicapCaptcha", handicapCaptcha);
            accountMap.put(existHaId, account);

            //存储爬虫信息
            Map<String, String> data = (Map<String, String>) bean.getData();
            agentMap.put(existHaId, data);
            hcConfigMap.put(existHaId, httpConfig);

            bean.setData(existHaId);
        }
        return bean;
	}
    /**
     * 登陆
     *
     * @param existHaId 盘口会员存在id
     */
    public JsonResultBeanPlus login(String existHaId) {
        synchronized (existHaId) {
            if (!agentMap.containsKey(existHaId)) {
                JsonResultBeanPlus bean = new JsonResultBeanPlus();
                if (!accountMap.containsKey(existHaId)) {
                    bean.putEnum(HcCodeEnum.IBS_404_EXIST_INFO);
                    bean.putSysEnum(HcCodeEnum.CODE_404);
                    return bean;
                }
                Map<String, String> data = accountMap.get(existHaId);
                return login(existHaId, data.get("agentAccount"), data.get("agentPassword"), data.get("handicapUrl"),
                        data.get("handicapCaptcha"));
            }
        }
        return new JsonResultBeanPlus().success();
    }
    /**
     * 登录
     *
     * @param httpConfig      http请求配置类
     * @param handicapUrl     盘口url
     * @param handicapCaptcha 盘口验证码
     * @param agentAccount    代理账号
     * @param agentPassword   代理密码
     * @return 登录信息
     */
    public JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
                                    String agentAccount, String agentPassword) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        httpConfig.headers(null);
        httpConfig.httpContext(null);
        try{
            //获取线路选择页面
            String routeHtml =Ncom1AgentTool.getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha);
            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "線路選擇")) {
                log.info("Ncom1获取线路页面失败=" + routeHtml);
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //4条代理线路数组
            String[] routes = Ncom1AgentTool.getAgentRoute(httpConfig, routeHtml);
            if (ContainerTool.isEmpty(routes)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //选择登录线路
            String loginSrc =Ncom1AgentTool.getLoginHtml(httpConfig, routes);
            if (StringTool.isEmpty(loginSrc)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //盘口协议页面
            String loginInfo = Ncom1AgentTool.getLogin(httpConfig, agentAccount, agentPassword, loginSrc);
            if (StringTool.isEmpty(loginInfo)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //错误处理和是否第一次登录盘口
            if (!StringTool.isContains(loginInfo, "用户管理") || StringTool.isContains(loginInfo, "修改密码")) {
                bean.putEnum(Ncom1AgentTool.loginError(loginInfo));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //页面token
            String token = StringUtils.substringBetween(loginInfo, "var token = '", "';");
            if(StringTool.notEmpty(token)){
                httpConfig.setHeader("token",token);
            }
            httpConfig.setHeader("referer",loginSrc);

            Map<String, String> data = new HashMap<>(1);
            data.put("projectHost", loginSrc);

            bean.success(data);
        } catch (Exception e) {
            log.error("Ncom1盘口账号【" + agentAccount + "】登录失败,失败原因为：", e);
            bean.error(e.getMessage());
        } finally {
            httpConfig.defTimeOut();
        }
        return bean;
    }

    /**
     * 获取会员列表信息
     *
     * @param existHaId 已存在盘口代理id
     * @return 会员列表信息
     */
	@Override
    public JSONArray getMemberList(String existHaId) {
        if (userMap.containsKey(existHaId)) {
            return userMap.get(existHaId);
        }
        //获取用户信息
        JsonResultBeanPlus bean = memberListInfo(existHaId);
        //获取用户信息失败，返回空
        if (!bean.isSuccess()) {
            return new JSONArray();
        }
        return userMap.get(existHaId);
	}

    /**
     * 定时抓取会员列表信息
     * @param existHaId     已存在盘口代理id
     * @return
     */
    @Override
    public JsonResultBeanPlus checkInfo(String existHaId) {
        synchronized (existHaId) {
            JsonResultBeanPlus bean;

            //上次校验时间
            long lastTime;
            if (checkMap.containsKey(existHaId)) {
                lastTime = checkMap.get(existHaId);
            } else {
                lastTime = System.currentTimeMillis();
                checkMap.put(existHaId, lastTime);
            }

            //是否大于上次校验时间
            boolean flag = System.currentTimeMillis() - lastTime > MIN_CHECK_TIME;

            //重新获取数据
            if(!userMap.containsKey(existHaId)||flag){
                //获取用户信息
                bean = memberListInfo(existHaId);
                //获取用户信息失败，返回空
                if (!bean.isSuccess()) {
                    return bean;
                }
            }else {
                //使用内存数据
                bean = new JsonResultBeanPlus().success(userMap.get(existHaId));
            }

            if (ContainerTool.isEmpty(bean.getData())) {
                if (System.currentTimeMillis() - lastTime > MAX_CHECK_TIME) {
                    agentMap.remove(existHaId);
                    checkMap.put(existHaId, System.currentTimeMillis());
                }
            }
            if (flag) {
                checkMap.put(existHaId, System.currentTimeMillis());
            }
            return bean;
        }
    }

    /**
     * 获取会员账号信息
     *
     * @param existHaId 已存在盘口代理id
     * @return 会员账号信息
     */
    public JsonResultBeanPlus memberListInfo(String existHaId) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        // 获取用户信息
        if (!agentMap.containsKey(existHaId)) {
            bean = login(existHaId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        Map<String, String> accountInfo = agentMap.get(existHaId);
        if (ContainerTool.isEmpty(accountInfo)) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        // 获取配置类
        HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHaId);
        try {
            //获取会员列表
            JSONArray memberArray = Ncom1AgentTool.getMemberList(httpConfig, accountInfo.get("projectHost"));

            userMap.put(existHaId, memberArray);
            bean.success();
            bean.setData(memberArray);
        } catch (Exception e) {
            log.error("Ncom1盘口代理【" + existHaId + "】获取获取会员账号信息失败", e);
            agentMap.remove(existHaId);
        }
        return bean;
    }
}
