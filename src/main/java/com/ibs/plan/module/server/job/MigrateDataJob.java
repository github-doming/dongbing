package com.ibs.plan.module.server.job;

import com.ibs.plan.module.server.job.service.MigrateService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: 迁移投注数据job
 * @Author: null
 * @Date: 2020-04-14 17:17
 * @Version: v1.0
 */
public class MigrateDataJob extends BaseCommJob {
    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        //库名
        String baseName="bak";
        //表名
        List<String> tableName = Arrays.asList("ibsp_event_client_open","ibsp_event_config_set",
                "ibsp_event_login","ibsp_event_login_vali","ibsp_event_logout","ibsp_hm_bet_item");
		List<String> tableNames = new ArrayList<>(tableName);
        //当天0点时间
        Date nowTime=DateTool.getDayStart(new Date());

        //数据切割时间
        Date cutDataTime= DateTool.getBeforeDays(nowTime,2);
        //表时间
        String tableTime = DateTool.getMDDate(DateTool.getBeforeDays(nowTime,3));

        MigrateService migrateService=new MigrateService();
        // 检测表数据是否超过10W条 ,没有则不备份
        migrateService.checkTableData(tableNames);
        if (ContainerTool.isEmpty(tableNames)) {
            return;
        }
        //创建表，迁移数据
        migrateService.createTable(cutDataTime,tableTime,tableNames,baseName);

        //删除表迁移数据
        migrateService.delMigrateData(cutDataTime,tableNames);

        //优化表
        migrateService.optimizeTable(tableNames);

        //表名
        tableNames = Arrays.asList("ibsp_client_capacity","ibsp_client_handicap_capacity","ibsp_client_heartbeat","ibsp_profit","ibsp_profit_period",
                "ibsp_profit_period_vr","ibsp_profit_plan","ibsp_profit_plan_vr","ibsp_profit_vr");

        //清除7天前已删除废弃数据
        cutDataTime= DateTool.getBeforeDays(new Date(),6);

        migrateService.delData(cutDataTime,tableNames);
        //优化表
        migrateService.optimizeTable(tableNames);

    }
}
