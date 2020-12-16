package com.ibm.common.test.zjj.handicap.agent;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.utils.agent.ComAgentUtil;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.junit.Test;

import java.util.Map;

/**
 * @Description: COM代理测试类
 * @Author: null
 * @Date: 2020-04-25 15:43
 * @Version: v1.0
 */
public class ComAgentTest {

    @Test
    public void test() throws InterruptedException {
        String id = "123";

        String agentAccount = "ddff258";
        String agentPassword = "qwqw1212.";
        String handicapUrl = "http://863126.com/";
        String handicapCaptcha = "782495";

        HandicapUtil.Code handicapCode=HandicapUtil.Code.COM;
        GameUtil.Code gameCode=GameUtil.Code.CQSSC;
		Object period =gameCode.getGameFactory().period(handicapCode).findPeriod();

        ComAgentUtil agentUtil=ComAgentUtil.findInstance();
        JsonResultBeanPlus bean=agentUtil.login(id,agentAccount,agentPassword,handicapUrl,handicapCaptcha);
        System.out.println("登录结果="+bean.toJsonString());

        agentUtil.checkInfo(id);

        //盘口时间字符串
        String date = PeriodUtil.getHandicapGameDateStr(HandicapUtil.Code.COM, GameUtil.Code.CQSSC, period);

        bean =agentUtil.getBetSummary(id,gameCode,period,date);

        Map<String, SummaryInfo> member= (Map<String, SummaryInfo>) bean.getData();

        for (Map.Entry<String, SummaryInfo> entry : member.entrySet()) {
            bean=agentUtil.getBetDetail(id,gameCode,"",entry.getValue(),period,date);
            System.out.println("结果="+bean.toJsonString());
        }

    }

}
