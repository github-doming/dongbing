package com.ibm.old.v1.cloud.core.job.pk10;
import com.ibm.old.v1.cloud.core.job.SendBetItemJob;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.quartz.JobExecutionContext;
/**
 * @Description: 发送PK10投注项
 * @Author: Dongming
 * @Date: 2019-01-08 13:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SendPk10BetItemJob extends SendBetItemJob {

	public SendPk10BetItemJob() {
		super(IbmGameEnum.PK10);
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (PeriodTool.getPK10DrawTime() - System.currentTimeMillis() > PeriodTool.PK10_PERIOD) {
			log.info("游戏【" + IbmGameEnum.PK10.getName() + "】，未到可以开始投注的时间");
			return;
		}
		super.executeJob(context);
	}


}
