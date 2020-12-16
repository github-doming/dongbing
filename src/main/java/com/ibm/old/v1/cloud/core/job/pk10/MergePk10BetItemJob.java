package com.ibm.old.v1.cloud.core.job.pk10;
import com.ibm.old.v1.cloud.core.job.MergeBetItemJob;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.quartz.JobExecutionContext;
/**
 * @Description: 合并PK10投注项
 * @Author: Dongming
 * @Date: 2019-02-22 14:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MergePk10BetItemJob extends MergeBetItemJob {

	public MergePk10BetItemJob() {
		super(IbmGameEnum.PK10);
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		if (PeriodTool.getPK10DrawTime() - System.currentTimeMillis() > PeriodTool.PK10_PERIOD) {
			log.info("游戏【" + IbmGameEnum.PK10.getName() + "】，未到可以合并投注项的时间");
			return;
		}
		super.executeJob(context);
	}
}
