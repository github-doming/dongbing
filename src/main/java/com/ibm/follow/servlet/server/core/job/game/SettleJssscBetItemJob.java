package com.ibm.follow.servlet.server.core.job.game;

import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.idc.Js10Idc;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_jsssc.service.IbmRepDrawJssscService;
import org.doming.core.common.CurrentTransaction;
import org.quartz.JobExecutionContext;

/**
 * @Description:
 * @Author: null
 * @Date: 2019-10-23 09:36
 * @Version: v1.0
 */
public class SettleJssscBetItemJob extends SettleTypeBetItemJob {
    private HandicapUtil.Code handicapCode;
    private String type;

    public SettleJssscBetItemJob(HandicapUtil.Code handicapCode,String type) {
        super(GameUtil.Code.JSSSC, handicapCode,type);
        this.handicapCode = handicapCode;
        this.type=type;
    }

    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        if (System.currentTimeMillis() - PeriodTool.getLotteryDrawTime(game, handicapCode) > Js10Idc.PERIOD) {
            log.info("盘口【" + handicapCode.getName() + "】游戏【" + game.getName() + "】已超过结算时间");
            return;
        }

        IbmRepDrawJssscService repDrawJssscService = new IbmRepDrawJssscService();
        Object period = PeriodTool.findLotteryPeriod(game, handicapCode);

        for (int i = 0; i < 15; i++) {
            if (repDrawJssscService.isExist(period.toString(),type)) {
                break;
            }
            CurrentTransaction.transactionCommit();
            Thread.sleep(SLEEP_TIME);
        }

        super.executeJob(context);
    }
}
