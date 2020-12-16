package com.ibm.follow.servlet.server.core.job.game;

import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.Cqssc;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.CQSSCTool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_cqssc.service.IbmRepDrawCqsscService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

/**
 * @Description: CQSSC结算
 * @Author: null
 * @Date: 2020-03-09 17:24
 * @Version: v1.0
 */
public class SettleCqsscBetItemJob extends SettleTypeBetItemJob {
	private HandicapUtil.Code handicapCode;
	private String type;

	public SettleCqsscBetItemJob(HandicapUtil.Code handicapCode, String type) {
		super(GameUtil.Code.CQSSC, handicapCode, type);
		this.handicapCode = handicapCode;
		this.type = type;
	}

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.getLotteryDrawTime(game, null) > Cqssc.PERIOD) {
			log.info("游戏【" + game.getName() + "】已超过结算时间");
			return;
		}
		IbmRepDrawCqsscService repDrawCqsscService = new IbmRepDrawCqsscService();
		String period = PeriodTool.findLotteryPeriod(game, handicapCode).toString();
		//60 * 2s = 2min
		for (int i = 0; i < 60; i++) {
			if (StringTool.notEmpty(CQSSCTool.getLottery(period))) {
				break;
			}
			if (i % 20 == 0) {
				CurrentTransaction.transactionCommit();
				if (repDrawCqsscService.isExist(period, type)) {
					break;
				}
			}
			Thread.sleep(SLEEP_TIME);
		}
		super.executeJob(context);

	}
}
