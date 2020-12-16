package com.ibs.plan.module.client.core.job;
import com.ibs.plan.module.server.job.service.MigrateService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 客户端 数据清理
 *
 * @Author: Dongming
 * @Date: 2020-05-09 14:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientCleaningJob extends BaseCommJob {
	@Override public void executeJob(JobExecutionContext context) throws Exception {
// 天凌晨4-7点进行迁移工作,同时删除过期历史数据

		List<String> tableNames = new ArrayList<>();
		tableNames.add("ibsc_bet");
		tableNames.add("ibsc_bet_error");
		tableNames.add("ibsc_bet_fail");
		tableNames.add("ibsc_bet_info");
		tableNames.add("ibsc_bet_item");

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);

		//表名_时间
		String tableTime = DateTool.getMDDate(calendar.getTime());

		MigrateService migrateService = new MigrateService();

		// 检测表数据是否超过10W条 ,没有则不备份
		migrateService.checkTableData(tableNames);
		if (ContainerTool.isEmpty(tableNames)) {
			return;
		}
		//创建表，迁移数据
		migrateService.createTable(tableTime, tableNames);

		//删除表迁移数据
		migrateService.delMigrateData(tableNames);
		//优化表
		migrateService.optimizeTable(tableNames);

		// 删除过期备份数据 (30天前的备份)
		calendar.add(Calendar.DATE, -30);

		tableTime = DateTool.getMDDate(calendar.getTime());
		migrateService.dropTable(tableTime, tableNames);

	}

}
