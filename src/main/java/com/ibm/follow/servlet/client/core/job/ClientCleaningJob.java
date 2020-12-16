package com.ibm.follow.servlet.client.core.job;

import com.ibm.follow.servlet.server.core.job.service.MigrateService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Description: 客户端 数据清理
 * @Author: Dongming
 * @Date: 2019-08-27 10:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientCleaningJob extends BaseCommJob {
	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		// 天凌晨4-7点进行迁移工作,同时删除过期历史数据

		List<String> tableNames = new ArrayList<>();
		tableNames.add("ibmc_ha_follow_bet");
		tableNames.add("ibmc_hm_bet");
		tableNames.add("ibmc_hm_bet_error");
		tableNames.add("ibmc_hm_bet_fail");
		tableNames.add("ibmc_hm_bet_info");
		tableNames.add("ibmc_hm_bet_item");

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);

		//表名_时间
		String tableTime = DateTool.getMDDate(calendar.getTime());

		MigrateService migrateService = new MigrateService();

		// 检测表数据是否超过1W条 ,没有则不备份
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
