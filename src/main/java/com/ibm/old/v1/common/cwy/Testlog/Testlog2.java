package com.ibm.old.v1.common.cwy.Testlog;

import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.entity.IbmLogPlanUserT;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.service.IbmLogPlanUserTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.junit.Test;

import java.util.Date;

/**
 * @Description: 测试方案状态记录日志
 * @Author: cwy
 * @Date: 2019-08-01 14:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Testlog2 extends CommTest {

    @Test
    public void demo() {
        try {
            jdbcTool = this.findJdbcToolLocal();
            this.transactionStart(jdbcTool);
            test01();
            this.transactionEnd(jdbcTool);
        } catch (Exception e) {
            e.printStackTrace();
            this.transactionRoll(jdbcTool);
        } finally {
            this.transactionClose(jdbcTool);
        }

    }

    private void test01() throws Exception {
        String planId="1269d5107b4b4161b091ab16872787f5";
        String state="OPEN";
        String planCode="RANK_HOT_AND_COLD";
        Date nowTime = new Date();
        //新增：用户方案操作记录日志中
        IbmLogPlanUserT ibmLogPlanUserT = new IbmLogPlanUserT();
        IbmLogPlanUserTService ibmLogPlanUserTService = new IbmLogPlanUserTService();
        ibmLogPlanUserT.setPlanId(planId);//方案主键
        ibmLogPlanUserT.setAppUserId("cccccccccc");//app用户id
        ibmLogPlanUserT.setGameId("cccccccc");//游戏主键id  根据方案详情id获得游戏主键
        ibmLogPlanUserT.setHandleType("REPLAYPLAN");
        ibmLogPlanUserT.setCreateTime(nowTime);
        ibmLogPlanUserT.setCreateTimeLong(nowTime.getTime());
        ibmLogPlanUserT.setState(IbmStateEnum.OPEN.name());
        ibmLogPlanUserT.setDesc(",appUserId:" + "cccccc" + ",GAME_CODE_:" + "PK10" +
                "，STATE_：" + state + ",PLAN_CODE_" + planCode);
        ibmLogPlanUserTService.save(ibmLogPlanUserT);

    }



}
