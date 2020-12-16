package com.ibm.follow.servlet.server.core.job.game;
import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.Gdklc;
import com.ibm.common.utils.game.tools.GDKLCTool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_gdklc.service.IbmRepDrawGdklcService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;
/**
 * @Author: Dongming
 * @Date: 2020-04-28 16:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SettleGdklcBetItemJob extends SettleTypeBetItemJob {
	private HandicapUtil.Code handicapCode;
	private String type;
	public SettleGdklcBetItemJob(HandicapUtil.Code handicapCode,String type) {
		super(GameUtil.Code.GDKLC,handicapCode,type);
		this.handicapCode = handicapCode;
		this.type=type;
	}

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.getLotteryDrawTime(game, null) > Gdklc.PERIOD) {
			log.info("游戏【" + game.getName() + "】已超过结算时间");
			return;
		}

		IbmRepDrawGdklcService repDrawGdklcService = new IbmRepDrawGdklcService();
		String period = PeriodTool.findLotteryPeriod(game, handicapCode).toString();
		//60 * 2s = 2min
		for (int i = 0; i < 60; i++) {
			if (StringTool.notEmpty(GDKLCTool.getLottery(period,type))) {
				break;
			}
			if (i % 20 == 0) {
				CurrentTransaction.transactionCommit();
				if (repDrawGdklcService.isExist(period,type)) {
					break;
				}
			}
			Thread.sleep(SLEEP_TIME);
		}
		super.executeJob(context);
	}
}
