package com.ibm.follow.servlet.server.core.job.game;
import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_country_ssc.service.IbmRepDrawCountrySscService;
import org.doming.core.common.CurrentTransaction;
import org.quartz.JobExecutionContext;
/**
 * 国家时时彩结算工作
 *
 * @Author: Dongming
 * @Date: 2020-04-30 17:29
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SettleCountrySscBetItemJob extends SettleTypeBetItemJob {
	private HandicapUtil.Code handicapCode;
	private String type;
	public SettleCountrySscBetItemJob(HandicapUtil.Code handicapCode, String type) {
		super(GameUtil.Code.COUNTRY_SSC, handicapCode, type);
		this.handicapCode = handicapCode;
		this.type = type;
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.getLotteryDrawTime(game, handicapCode) > game.getGameFactory().getInterval()) {
			log.info("游戏【" + game.getName() + "】已超过结算时间");
			return;
		}

		IbmRepDrawCountrySscService drawCountrySscService = new IbmRepDrawCountrySscService();
		String period = PeriodTool.findLotteryPeriod(game, handicapCode).toString();
		//60 * 2s = 2min
		for (int i = 0; i < 60; i++) {
			if (i % 20 == 0) {
				CurrentTransaction.transactionCommit();
				if (drawCountrySscService.isExist(period, type)) {
					break;
				}
			}
			Thread.sleep(SLEEP_TIME);
		}
		super.executeJob(context);
	}
}
