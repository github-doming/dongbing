package com.ibm.follow.servlet.server.core.job.game;
import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_country_10.service.IbmRepDrawCountry10Service;
import org.doming.core.common.CurrentTransaction;
import org.quartz.JobExecutionContext;
/**
 * 国家赛车结算工作
 *
 * @Author: Dongming
 * @Date: 2020-04-30 17:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SettleCountry10BetItemJob  extends SettleTypeBetItemJob {
	private HandicapUtil.Code handicapCode;
	private String type;
	public SettleCountry10BetItemJob(HandicapUtil.Code handicapCode, String type) {
		super(GameUtil.Code.COUNTRY_10, handicapCode, type);
		this.handicapCode = handicapCode;
		this.type = type;
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - game.getGameFactory().period(handicapCode).getLotteryDrawTime() > game.getGameFactory().getInterval()) {
			log.info("游戏【" + game.getName() + "】已超过结算时间");
			return;
		}

		IbmRepDrawCountry10Service drawCountry10Service = new IbmRepDrawCountry10Service();
		String period = PeriodTool.findLotteryPeriod(game, handicapCode).toString();
		//60 * 2s = 2min
		for (int i = 0; i < 60; i++) {
			if (i % 20 == 0) {
				CurrentTransaction.transactionCommit();
				if (drawCountry10Service.isExist(period, type)) {
					break;
				}
			}
			Thread.sleep(SLEEP_TIME);
		}
		super.executeJob(context);
	}
}
