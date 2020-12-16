package com.ibm.old.v1.common.cwy.Testlog;

import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.entity.IbmLogPlanUserT;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.service.IbmLogPlanUserTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.ibm_plan_user.t.service.IbmPcPlanUserTService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 测试选中的多个修改状态记录日志
 * @Author: Dongming
 * @Date: 2019-08-01 15:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TestManyLog extends CommTest {

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
        List<String> planIdList = new ArrayList<String>();
        planIdList.add("2c95fb9b6a404d0885327ed6f3491226");
        planIdList.add("383607a9d60043c98086187745383f87");
        IbmPcPlanUserTService planUserTService = new IbmPcPlanUserTService();
        String appUserId = "03644330f76847fea7c7d50eb71559d3";
        for (String planId : planIdList) {
            //获得用户方案详情id
            String userPlanItemInfoById = planUserTService.findUserPlanItemInfoById(planId, appUserId);
            Date nowTime = new Date();
            //新增：用户方案操作记录日志中
            IbmLogPlanUserT ibmLogPlanUserT = new IbmLogPlanUserT();
            IbmLogPlanUserTService ibmLogPlanUserTService = new IbmLogPlanUserTService();
            ibmLogPlanUserT.setPlanId(planId);//方案主键
            ibmLogPlanUserT.setAppUserId("1111");//app用户id
            ibmLogPlanUserT.setGameId("111");//游戏主键id  根据方案详情id获得游戏主键
            ibmLogPlanUserT.setHandleType("REPLAYPLAN");
            ibmLogPlanUserT.setCreateTime(nowTime);
            ibmLogPlanUserT.setCreateTimeLong(nowTime.getTime());
            ibmLogPlanUserT.setState(IbmStateEnum.OPEN.name());
            ibmLogPlanUserT.setDesc(",appUserId:" + appUserId + ",GAME_CODE_:" + "xyft" +
                    "，STATE_：" + "open" + ",PLAN_CODE_（玩法）:" + planUserTService.findByPlanId(planId, appUserId).getPlanName()//用户修改的玩法
            );
            ibmLogPlanUserTService.save(ibmLogPlanUserT);//保存用户方案操作日志实体类
            System.out.println(ibmLogPlanUserT);
        }
    }
}
