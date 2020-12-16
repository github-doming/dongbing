package com.ibm.old.v1.cloud.core.job.xyft;

import com.ibm.old.v1.cloud.core.job.TurnCodeJob;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.quartz.JobExecutionContext;
/**
 * @author zjj
 * @ClassName: TurnXyftCodeJob
 * @Description: 转换幸运飞艇编码信息为投注信息
 * @date 2019年2月20日 下午1:56:33
 */
public class TurnXyftCodeJob extends TurnCodeJob {
	public TurnXyftCodeJob() {
		super(IbmGameEnum.XYFT);
	}
	@Override public void executeJob(JobExecutionContext context) throws Exception {

		if (PeriodTool.getXYFTDrawTime() - System.currentTimeMillis() > PeriodTool.XYFT_PERIOD) {
			log.info("游戏【" + IbmGameEnum.XYFT.getName() + "】，未到可以开始转换的时间");
			return;
		}
		super.executeJob(context);
	}

}
