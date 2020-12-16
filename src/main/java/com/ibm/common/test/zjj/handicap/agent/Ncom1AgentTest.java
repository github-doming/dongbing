package com.ibm.common.test.zjj.handicap.agent;


import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.http.utils.agent2.Ncom1AgentUtil2;
import org.junit.Test;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-01-07 14:49
 * @Version: v1.0
 */
public class Ncom1AgentTest {

    @Test
    public void test(){
        String id = "123";

        String agentAccount = "hao9999";
        String agentPassword = "Vic123456";
        String handicapUrl = "http://fg58.vip/";
        String handicapCaptcha = "956133";

        Ncom1AgentUtil2 agentUtil=Ncom1AgentUtil2.findInstance();

        JsonResultBeanPlus bean= agentUtil.login(id,agentAccount,agentPassword,handicapUrl,handicapCaptcha);
        System.out.println("登录结果="+bean.toJsonString());


        bean=agentUtil.memberListInfo(id);
        System.out.println("用户列表信息="+bean.toJsonString());
    }

}
