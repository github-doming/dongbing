package com.ibm.old.v1.cloud.core.job.manage;
import com.ibm.old.v1.cloud.core.job.EverydayResetJob;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import org.quartz.JobExecutionContext;
/**
 * @Description: 每天六点重置方案
 * @Author: zjj
 * @Date: 2019-05-21 17:23
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class EverydayResetAsSixTimeJob extends EverydayResetJob {

	public EverydayResetAsSixTimeJob() {
		super(new IbmGameEnum[]{IbmGameEnum.PK10, IbmGameEnum.XYFT});
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		log.info("游戏【" + IbmGameEnum.PK10.getName()+","+IbmGameEnum.XYFT.getName() + "】开启每天重置方案定时器");
		super.executeJob(context);
	}

}
