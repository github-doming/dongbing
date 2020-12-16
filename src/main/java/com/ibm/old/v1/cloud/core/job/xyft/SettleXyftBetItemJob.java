package com.ibm.old.v1.cloud.core.job.xyft;
import com.ibm.old.v1.cloud.core.job.SettleBetItemJob;
import com.ibm.old.v1.cloud.core.tool.XYFTTool;
import com.ibm.old.v1.cloud.ibm_rep_draw_xyft.t.service.IbmRepDrawXyftTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;
/**
 * @Description: 结算幸运飞艇投注项
 * @Author: Dongming
 * @Date: 2019-04-04 14:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SettleXyftBetItemJob  extends SettleBetItemJob {

	public SettleXyftBetItemJob()  {
		super(IbmGameEnum.XYFT);
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.getLotteryXYFTDrawTime() > PeriodTool.XYFT_PERIOD) {
			log.info("游戏【" + IbmGameEnum.PK10.getName() + "】已超过幸运飞艇结算时间");
			return;
		}
		IbmRepDrawXyftTService repDrawXyftService = new IbmRepDrawXyftTService();
		String period = PeriodTool.findLotteryXYFTPeriod();
		//60 * 2s = 2min
		for (int i = 0; i < 60; i++) {
			if (StringTool.notEmpty(XYFTTool.getLottery(period))) {
				break;
			}
			if (i % 20 == 0 && repDrawXyftService.isExist(period)) {
				break;
			}
			Thread.sleep(SLEEP_TIME);
		}
		super.executeJob(context);
	}
}
