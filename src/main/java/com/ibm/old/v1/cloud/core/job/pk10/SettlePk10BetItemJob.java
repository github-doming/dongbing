package com.ibm.old.v1.cloud.core.job.pk10;
import com.ibm.old.v1.cloud.core.job.SettleBetItemJob;
import com.ibm.old.v1.cloud.core.tool.PK10Tool;
import com.ibm.old.v1.cloud.ibm_rep_draw_pk10.t.service.IbmRepDrawPk10TService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;
/**
 * @Description: 结算北京赛车投注项
 * @Author: Dongming
 * @Date: 2019-04-03 18:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SettlePk10BetItemJob extends SettleBetItemJob {

	public SettlePk10BetItemJob() {
		super(IbmGameEnum.PK10);
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.getLotteryPK10DrawTime() > PeriodTool.PK10_PERIOD) {
			log.info("游戏【" + IbmGameEnum.PK10.getName() + "】已超过北京赛车结算时间");
			return;
		}

		IbmRepDrawPk10TService repDrawPk10Service = new IbmRepDrawPk10TService();
		Integer period = PeriodTool.findLotteryPK10Period();
		//180 * 2s = 6min
		for (int i = 0; i < 180; i++) {
			if (StringTool.notEmpty(PK10Tool.getLottery(period))) {
				break;
			}
			if (i % 20 == 0 && repDrawPk10Service.isExist(period.toString())) {
				break;
			}
			Thread.sleep(SLEEP_TIME);
		}
		super.executeJob(context);
	}

}
