package com.ibm.follow.servlet.cloud.core.job;

import com.ibm.follow.connector.admin.manage3.service.IbmSysService;
import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobExecutionContext;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @Description: 周期(7天)定时清理数据任务
 * @Author: wwj
 * @Date: 2020/1/18 14:10
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class CleanDataJob extends BaseCommJob {
    /**
     * 工作执行内容
     *
     * @param context 工作传参
     * @throws Exception 执行出错
     */
    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        log.info("---------定时清理数据任务开始执行---------");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -30);
        List<String> tableNames = Arrays.asList("ibm_event_client_close", "ibm_event_client_open", "ibm_event_config_info",
                "ibm_event_config_set", "ibm_event_login", "ibm_event_login_vali", "ibm_ha_follow_period", "ibm_ha_member_bet_item",
                "ibm_hm_bet_item", "ibm_hm_profit", "ibm_hm_profit_item", "ibm_hm_profit_period", "ibm_hm_profit_period_vr",
                "ibm_hm_profit_vr");

        int num = new IbmSysService().cleanDataByTime(tableNames, c.getTime().getTime());

        log.info("---------定时清理数据任务执行成功,数据" + num + "条---------");
    }


}
