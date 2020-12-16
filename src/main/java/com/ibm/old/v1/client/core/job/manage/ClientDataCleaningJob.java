package com.ibm.old.v1.client.core.job.manage;
import com.ibm.old.v1.client.ibm_sys_database.t.service.IbmClientSystemService;
import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobExecutionContext;

import java.util.Date;
/**
 * @Description: 数据清理定时器
 * @Author: zjj
 * @Date: 2019-05-10 17:40
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class ClientDataCleaningJob extends BaseCommJob {

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		Date nowTime = new Date();
		IbmClientSystemService systemService=new IbmClientSystemService();

		systemService.clearRedundancy("ibm_client_bet",nowTime,"d-2");
		systemService.clearRedundancy("ibm_client_bet_error",nowTime,"d-2");
		systemService.clearRedundancy("ibm_client_bet_fail",nowTime,"d-2");
		systemService.clearRedundancy("ibm_client_exist_bet",nowTime,"d-2");

		systemService.defragmentation("ibm_client_bet");
		systemService.defragmentation("ibm_client_bet_error");
		systemService.defragmentation("ibm_client_bet_fail");
		systemService.defragmentation("ibm_client_exist_bet");
	}
}
