package com.ibm.follow.servlet.server.core.job.game;

import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.Xync;
import com.ibm.common.utils.game.tools.XYNCTool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_xync.service.IbmRepDrawXyncService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

/**
 * 结算幸运农场投注数据
 *
 * @Author: Dongming
 * @Date: 2020-04-22 17:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SettleXyncBetItemJob extends SettleTypeBetItemJob {
	private HandicapUtil.Code handicapCode;
	private String type;
	public SettleXyncBetItemJob(HandicapUtil.Code handicapCode,String type) {
		super(GameUtil.Code.XYNC,handicapCode,type);
		this.handicapCode = handicapCode;
		this.type=type;
	}

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.getLotteryDrawTime(game, null) > Xync.PERIOD) {
			log.info("游戏【" + game.getName() + "】已超过结算时间");
			return;
		}

		IbmRepDrawXyncService repDrawXyncService = new IbmRepDrawXyncService();
		String period = PeriodTool.findLotteryPeriod(game, handicapCode).toString();
		//60 * 2s = 2min
		for (int i = 0; i < 60; i++) {
			if (StringTool.notEmpty(XYNCTool.getLottery(period,type))) {
				break;
			}
			if (i % 20 == 0) {
				CurrentTransaction.transactionCommit();
				if (repDrawXyncService.isExist(period,type)) {
					break;
				}
			}
			Thread.sleep(SLEEP_TIME);
		}
		super.executeJob(context);
	}
}
