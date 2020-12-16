package com.ibm.old.v1.cloud.core.job;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobExecutionContext;
/**
 * @Description: 每天重置定时器
 * @Author: zjj
 * @Date: 2019-05-21 14:57
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class EverydayResetJob extends BaseCommJob {

	private IbmGameEnum[] games;
	protected EverydayResetJob(IbmGameEnum[] games) {
		this.games = games;
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		IbmHmSetTService hmSetService=new IbmHmSetTService();
		hmSetService.everyDayReset(IbmTypeEnum.REAL.name(),games,this.getClass().getName());
		hmSetService.everyDayReset(IbmTypeEnum.VIRTUAL.name(),games,this.getClass().getName());

	}
}
