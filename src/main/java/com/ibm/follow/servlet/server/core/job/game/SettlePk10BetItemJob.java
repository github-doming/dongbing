package com.ibm.follow.servlet.server.core.job.game;

import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.Pk10;
import com.ibm.common.utils.game.tools.PK10Tool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_pk10.service.IbmRepDrawPk10Service;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

/**
 * @Description: 结算北京赛车投注项
 * @Author: Dongming
 * @Date: 2019-10-10 17:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SettlePk10BetItemJob extends SettleTypeBetItemJob {
    private String type;

    public SettlePk10BetItemJob(HandicapUtil.Code handicapCode,String type) {
        super(GameUtil.Code.PK10,handicapCode,type);
        this.type=type;
    }

    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        if (System.currentTimeMillis() - PeriodTool.getLotteryDrawTime(game, null) > Pk10.PERIOD) {
            log.info("游戏【" + game+ "】已超过结算时间");
            return;
        }
        IbmRepDrawPk10Service repDrawPk10Service = new IbmRepDrawPk10Service();
		Integer period= NumberTool.getInteger(PeriodTool.findLotteryPeriod(game,handicap));
        //180 * 2s = 6min
        for (int i = 0; i < 180; i++) {
            if (StringTool.notEmpty(PK10Tool.getLottery(period))) {
                break;
            }
            if (i % 20 == 0) {
                CurrentTransaction.transactionCommit();
                if (repDrawPk10Service.isExist(period.toString(),type)) {
                    break;
                }
            }
            Thread.sleep(SLEEP_TIME);
        }
        super.executeJob(context);
    }
}
