package com.ibm.old.v1.cloud.core.job.manage;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_exec_result.t.service.IbmExecResultTService;
import com.ibm.old.v1.cloud.ibm_profit.t.service.IbmProfitTService;
import com.ibm.old.v1.cloud.ibm_profit_info.t.service.IbmProfitInfoTService;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.service.IbmProfitPlanTService;
import com.ibm.old.v1.cloud.ibm_sys_database.t.service.IbmSystemService;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppVerifyAccountService;
import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobExecutionContext;

import java.util.Date;
/**
 * @Description: 数据清洗工作
 * @Author: Dongming
 * @Date: 2019-05-09 16:51
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class DataCleaningJob extends BaseCommJob {

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		Date nowTime = new Date();

		IbmExecBetItemTService execBetItemService = new IbmExecBetItemTService();
		IbmProfitTService profitService = new IbmProfitTService();
		IbmProfitPlanTService profitPlanService = new IbmProfitPlanTService();
		IbmProfitInfoTService profitInfoService = new IbmProfitInfoTService();
		//TODO 迁移数据

		// 迁移投注数据
		execBetItemService.migration(nowTime);



		//TODO 清理过期数据

		// 清理投注过期数据
		execBetItemService.clearRedundancy(nowTime);
		new IbmExecPlanGroupTService().clearRedundancy(nowTime,"d-2");
		new IbmExecResultTService().clearRedundancy(nowTime,"m-5");

		// 清理盈利过期数据
		profitService.clearRedundancy(nowTime, IbmTypeEnum.REAL,"d-15");
		profitService.clearRedundancy(nowTime, IbmTypeEnum.VIRTUAL,"d-5");
		profitPlanService.clearRedundancy(nowTime, IbmTypeEnum.REAL,"d-15");
		profitPlanService.clearRedundancy(nowTime, IbmTypeEnum.VIRTUAL,"d-5");
		profitInfoService.clearRedundancy(nowTime, IbmTypeEnum.REAL,"d-2");
		profitInfoService.clearRedundancy(nowTime, IbmTypeEnum.VIRTUAL,"d-2");

		// 清理用户数据
		new AppVerifyAccountService().clearRedundancy(nowTime,"m-5");


		//TODO 数据库碎片整理
		IbmSystemService systemService = new IbmSystemService();
		systemService.defragmentation("ibm_exec_bet_item");
		systemService.defragmentation("ibm_exec_plan_group");
		systemService.defragmentation("ibm_exec_result");
		systemService.defragmentation("ibm_profit");
		systemService.defragmentation("ibm_profit_vr");
		systemService.defragmentation("ibm_profit_plan");
		systemService.defragmentation("ibm_profit_plan_vr");
		systemService.defragmentation("ibm_profit_info");
		systemService.defragmentation("ibm_profit_info_vr");
		systemService.defragmentation("app_verify_account");
	}
}
