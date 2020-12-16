package com.ibm.common.test.wwj.handicap.idc;

import com.ibm.common.test.wwj.handicap.GrabAgent;
import com.ibm.common.test.wwj.handicap.HttpType;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/4 16:00
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class IdcAgentGrab extends AbsIdcGrab implements GrabAgent {

    /**
     * 获取会员列表信息
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 接口url
     * @param ticket      票证
     * @param index       循环次数
     * @return 会员列表信息
     */
    public List<Map<String,String>> getMemberList(HttpClientConfig httpConfig, String projectHost, String ticket, int... index)
            throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        Map<String, Object> join = new HashMap<>(5);
        join.put("ticket", ticket);
        //代理帐号（登入帐号的下线代理帐号），为“”为登入帐号（代理）
        join.put("agentno", "");
        //过滤帐号类型:“”:所有 1.代理 2.会员
        join.put("mbtype", "2");
        //过虑在线状态:“”:全部 1.在线 0.不在线
        join.put("online", "");
        //0.详细完整多栏位 1.只有帐号栏位（帐号编号）可用于查询更新在线会员状态
        join.put("returntype", 0);
        String url=projectHost.concat("GetMemberList.ashx");
        String result=null;
        try {
            result = html(httpConfig.url(url).map(join).method(HttpConfig.Method.POST), HttpType.MemberList);
            if (StringTool.isEmpty(result)) {
                log.error("获取会员列表信息失败");
                Thread.sleep(LONG_SLEEP);
                return getMemberList(httpConfig, projectHost, ticket, ++index[0]);
            }
            if(StringTool.isContains(result,"Gateway Time-out")){
                log.error("获取会员列表信息失败，网关超时");
                log.fatal("页面为="+result);
                Thread.sleep(SLEEP);
                return getMemberList(httpConfig, projectHost, ticket, ++index[0]);
            }
            if (!StringTool.isContains(result, "code", "msg")) {
                log.error("获取登录url失败，错误信息=" + result);
                Thread.sleep(LONG_SLEEP);
                return getMemberList(httpConfig, projectHost, ticket, ++index[0]);
            }
            //转换为json
            List<Map<String,String>> List = parseList(result);
            if (ContainerTool.isEmpty(List)) {
                Thread.sleep(LONG_SLEEP);
                return getMemberList(httpConfig, projectHost, ticket, ++index[0]);
            }
            return List;
        } catch (Exception e) {
            log.error("获取登录url失败", e);
            Thread.sleep(LONG_SLEEP);
            return getMemberList(httpConfig, projectHost, ticket, ++index[0]);
        }
    }

    /**
     * 将结果转换为json
     *
     * @param html 结果界面
     * @return 结果json
     */
    private List<Map<String,String>> parseList(String html) {
        // TODO 解析页面转换为List
        List<Map<String,String>> list = new ArrayList<>();
        return list;
    }
}
