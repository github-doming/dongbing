package com.ibm.old.v1.cloud.core.job.xyft;
import com.ibm.old.v1.cloud.core.job.MergeBetItemJob;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.quartz.JobExecutionContext;
/**
 * @Description: 合并XYFT投注项
 * @Author: Dongming
 * @Date: 2019-01-08 13:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MergeXyftBetItemJob extends MergeBetItemJob {

	public MergeXyftBetItemJob() {
		super(IbmGameEnum.XYFT);
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		if (PeriodTool.getXYFTDrawTime() - System.currentTimeMillis() > PeriodTool.XYFT_PERIOD) {
			log.info("游戏【" + IbmGameEnum.XYFT.getName() + "】，未到可以合并投注项的时间");
			return;
		}
		super.executeJob(context);
	}

}
