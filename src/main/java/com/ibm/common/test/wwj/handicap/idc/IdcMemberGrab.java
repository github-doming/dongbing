package com.ibm.common.test.wwj.handicap.idc;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.test.wwj.handicap.GrabMember;
import com.ibm.common.test.wwj.handicap.HttpType;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/4 16:01
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class IdcMemberGrab extends AbsIdcGrab implements GrabMember{

    /**
     * 获取用户信息。10秒超过6次，锁定10秒
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 登录url
     * @param ticket      票证
     * @param index       循环次数
     * @return 用户信息
     */
    public JSONObject getUserInfo(HttpClientConfig httpConfig, String projectHost, String ticket, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("ticket", ticket);
        String result=null;
        String url=projectHost.concat("GetUserInfo.ashx");
        try {
            result = html(httpConfig.url(url).map(map).method(HttpConfig.Method.POST), HttpType.UserInfo);
            if (StringTool.isEmpty(result)) {
                log.error("获取用户信息失败");
                longSleep("获取用户信息失败");
                return getUserInfo(httpConfig, projectHost, ticket, ++index[0]);
            }
            if (StringTool.isContains(result, "504 Gateway Time-out")) {
                log.error("获取用户信息失败，nginx超时");
                log.fatal("页面为=[" + result);
                longSleep("获取用户信息失败");
                return getUserInfo(httpConfig, projectHost, ticket, ++index[0]);
            }
            if (!StringTool.isContains(result, "code", "msg")) {
                log.error("获取用户信息失败，错误信息=" + result);
                longSleep("获取用户信息失败");
                return getUserInfo(httpConfig, projectHost, ticket, ++index[0]);
            }
            return JSONObject.parseObject(result);
        } catch (Exception e) {
            log.error("获取用户信息失败,结果信息="+result, e);
            longSleep("获取用户信息失败");
            return getUserInfo(httpConfig, projectHost, ticket, ++index[0]);
        }
    }

    /**
     * 获取游戏限额，60秒超过15次，锁定60秒
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 接口url
     * @param ticket      票证
     * @param gameno      游戏编号code
     * @param index       循环次数
     * @return 游戏限额
     */
    public JSONObject getGameLimit(HttpClientConfig httpConfig, String projectHost, String ticket, int gameno,
                                          int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        Map<String, Object> join = new HashMap<>(2);
        join.put("ticket", ticket);
        join.put("gameno", gameno);
        String result=null;
        String url=projectHost.concat("GetQuotaList.ashx");
        try {
            result = html(httpConfig.url(url).map(join).method(HttpConfig.Method.POST),HttpType.GameLimit);
            if (StringTool.isEmpty(result)) {
                log.error("获取游戏限额失败");
                sleep("获取游戏限额失败",4 * SLEEP);
                return getGameLimit(httpConfig, projectHost, ticket, gameno, ++index[0]);
            }
            if (!StringTool.isContains(result, "code", "msg")) {
                log.error("获取游戏限额失败，错误信息=" + result);
                sleep("获取游戏限额失败",4 * SLEEP);
                return getGameLimit(httpConfig, projectHost, ticket, gameno, ++index[0]);
            }
            return JSONObject.parseObject(result);
        } catch (Exception e) {
            log.error("获取游戏限额失败,结果信息="+result, e);
            sleep("获取游戏限额失败",4 * SLEEP);
            return getGameLimit(httpConfig, projectHost, ticket, gameno, ++index[0]);
        }
    }
}
