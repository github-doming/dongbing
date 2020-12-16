package com.ibm.old.v1.cloud.core.job.xyft;
import com.ibm.old.v1.cloud.core.job.CodingPlanJob;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.quartz.JobExecutionContext;
/**
 * @Description: 编码XYFT方案信息
 * @Author: Dongming
 * @Date: 2019-02-15 15:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CodingXyftPlanJob  extends CodingPlanJob {
	public CodingXyftPlanJob(){
		super(IbmGameEnum.XYFT,1000);
	}
	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (PeriodTool.getXYFTDrawTime() - System.currentTimeMillis() > PeriodTool.XYFT_PERIOD) {
			log.info("游戏【" + IbmGameEnum.XYFT.getName() + "】，未到可以开始投注的时间");
			return;
		}
		super.executeJob(context);
	}
}
