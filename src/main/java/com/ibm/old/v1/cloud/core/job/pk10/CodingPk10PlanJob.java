package com.ibm.old.v1.cloud.core.job.pk10;
import com.ibm.old.v1.cloud.core.job.CodingPlanJob;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.quartz.JobExecutionContext;
/**
 * @Description: 编码游戏方案信息
 * @Author: Dongming
 * @Date: 2018-12-22 10:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CodingPk10PlanJob extends CodingPlanJob {
	public CodingPk10PlanJob() {
		super(IbmGameEnum.PK10, 2000);
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		if (PeriodTool.getPK10DrawTime() - System.currentTimeMillis() > PeriodTool.PK10_PERIOD) {
			log.info("游戏【" + IbmGameEnum.PK10.getName() + "】，未到可以开始编码的时间");
			return;
		}
		super.executeJob(context);
	}
}
