package com.ibm.old.v1.common.cwy.Testlog;

import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.entity.IbmLogPlanUserT;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.service.IbmLogPlanUserTService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.Date;

/**
 * @Description: 测试方案详情修改记录日志
 * @Author: cwy
 * @Date: 2019-08-01 14:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Testlog extends CommTest {
    private String planCode;
    private String id;
    private String profitLimitMax;
    private String lossLimitMin;
    private String fundsList;
    private String followPeriod;
    private String monitorPeriod;
    private String betMode;
    private String fundSwapMode;
    private String periodRollMode;
    private JSONObject planGroupData;
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
       if(valiParameter()){
           System.out.println("数据错误~");
       }
        IbmPlanUserTService planUserTService = new IbmPlanUserTService();
        Date nowTime =new Date();
        //新增：用户方案操作记录日志中
        IbmLogPlanUserT ibmLogPlanUserT = new IbmLogPlanUserT();
        IbmLogPlanUserTService ibmLogPlanUserTService = new IbmLogPlanUserTService();

        ibmLogPlanUserT.setPlanId(id);
        ibmLogPlanUserT.setAppUserId("aaaaaaaa");//app用户id
        ibmLogPlanUserT.setGameId("eeeeeeeeeeeeeee");//游戏主键id  新增方法：planUserTService.findTablePlanId(id)
        ibmLogPlanUserT.setHandleType("REPLAYPLAN");
        ibmLogPlanUserT.setCreateTime(nowTime);
        ibmLogPlanUserT.setCreateTimeLong(nowTime.getTime());
        ibmLogPlanUserT.setState(IbmStateEnum.OPEN.name());
        ibmLogPlanUserT.setDesc(//描述
                ",betMode:" + betMode + ",followPeriod:" + followPeriod + "," +
                        "monitorPeriod:" + monitorPeriod + ",fundSwapMode:" + fundSwapMode +
                        ",periodRollMode:" + periodRollMode + ",fundsList:" + fundsList +
                        ",profitLimitMax:" + profitLimitMax + ",lossLimitMin:" + lossLimitMin +
                        ",planGroupData" + planGroupData.toString());

//        ibmLogPlanUserTService.save(ibmLogPlanUserT);
//        IbmPlanUserTService planUserTService = new IbmPlanUserTService();
//        String tablePlanId = planUserTService.findTablePlanId("218443e277a74df29575e167f0603ed0");
//        System.out.println(tablePlanId);
        System.out.println(ibmLogPlanUserT);
    }

    private boolean valiParameter() {
        // 方案code
        planCode = "NUMBER_TO_TRACK";
        // 方案详情表主键
        id = "c55d98fc56704c8daa35f720f909a4cf";
        // 止盈上限
        profitLimitMax ="100";
        // 止损下限
        lossLimitMin ="0";
        // 资金列表
        fundsList ="10,20,40,80,160,320,640,1280,2560,5120,10240";
        // 跟进期数
        followPeriod = "1";
        // 监控次数
        monitorPeriod = "1";
        // 投注模式
        betMode = "BET_MODE_REGULAR";
        // 资金切换方式
        fundSwapMode = "FUND_SWAP_MODE_NO_SWAP_ON_RESET";
        // 期期滚选项
        periodRollMode ="";

        Object planGroupDataObj = "{\"0\":{\"state\":\"TRUE\",\"track\":\"1\",\"bet\":\"1\"}}";
        planGroupData = JSONObject.fromObject(planGroupDataObj);
        return false;
    }


}
