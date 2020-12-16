package com.ibm.follow.servlet.server.core.job.game;

import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.idc.Js10Idc;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_js10.service.IbmRepDrawJs10Service;
import org.doming.core.common.CurrentTransaction;
import org.quartz.JobExecutionContext;

/**
 * @Description: 结算JS10投注项
 * @Author: null
 * @Date: 2019-10-22 11:28
 * @Version: v1.0
 */
public class SettleJs10BetItemJob extends SettleTypeBetItemJob {
    private HandicapUtil.Code handicapCode;
    private String type;

    public SettleJs10BetItemJob(HandicapUtil.Code handicapCode,String type) {
        super(GameUtil.Code.JS10, handicapCode,type);
        this.handicapCode = handicapCode;
        this.type=type;
    }

    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        if (System.currentTimeMillis() - PeriodTool.getLotteryDrawTime(game, handicapCode) > Js10Idc.PERIOD) {
            log.info("盘口【" + handicapCode.getName() + "】游戏【" +game.getName() + "】已超过结算时间");
            return;
        }

        IbmRepDrawJs10Service repDrawJs10Service = new IbmRepDrawJs10Service();
        Object period = PeriodTool.findLotteryPeriod(game, handicapCode);

        for (int i = 0; i < 15; i++) {
            if (repDrawJs10Service.isExist(period.toString(),type)) {
                break;
            }
            CurrentTransaction.transactionCommit();
            Thread.sleep(SLEEP_TIME);
        }

        super.executeJob(context);
    }
}
