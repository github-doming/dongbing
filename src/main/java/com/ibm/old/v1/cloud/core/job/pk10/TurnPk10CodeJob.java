package com.ibm.old.v1.cloud.core.job.pk10;
import com.ibm.old.v1.cloud.core.job.TurnCodeJob;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.quartz.JobExecutionContext;
/**
 * @Description: 转换pk10编码信息为投注信息
 * @Author: Dongming
 * @Date: 2018-12-26 17:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TurnPk10CodeJob extends TurnCodeJob {
	public TurnPk10CodeJob(){
		super(IbmGameEnum.PK10);
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		if (PeriodTool.getPK10DrawTime() - System.currentTimeMillis() > PeriodTool.PK10_PERIOD) {
			log.info("游戏【" + IbmGameEnum.PK10.getName() + "】，未到可以开始转换的时间");
			return;
		}
		super.executeJob(context);
	}
}
