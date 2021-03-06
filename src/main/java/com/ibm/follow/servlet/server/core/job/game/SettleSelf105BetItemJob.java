package com.ibm.follow.servlet.server.core.job.game;
import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.PeriodConfig;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_self_10_2.service.IbmRepDrawSelf102Service;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_self_10_5.service.IbmRepDrawSelf105Service;
import org.doming.core.common.CurrentTransaction;
import org.quartz.JobExecutionContext;

/**
 * 自有2分彩赛车结算工作
 *
 * @Author: Dongming
 * @Date: 2020-04-29 16:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SettleSelf105BetItemJob extends SettleTypeBetItemJob {
	private HandicapUtil.Code handicapCode;
	private String type;
	public SettleSelf105BetItemJob(HandicapUtil.Code handicapCode, String type) {
		super(GameUtil.Code.SELF_10_5, handicapCode, type);
		this.handicapCode = handicapCode;
		this.type = type;
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.getLotteryDrawTime(game, handicapCode) > PeriodConfig.PERIOD_TIME_5) {
			log.info("游戏【" + game.getName() + "】已超过结算时间");
			return;
		}

		IbmRepDrawSelf105Service drawSelf105Service = new IbmRepDrawSelf105Service();
		String period = PeriodTool.findLotteryPeriod(game, handicapCode).toString();
		//60 * 2s = 2min
		for (int i = 0; i < 60; i++) {
			if (i % 20 == 0) {
				CurrentTransaction.transactionCommit();
				if (drawSelf105Service.isExist(period, type)) {
					break;
				}
			}
			Thread.sleep(SLEEP_TIME);
		}
		super.executeJob(context);
	}
}
