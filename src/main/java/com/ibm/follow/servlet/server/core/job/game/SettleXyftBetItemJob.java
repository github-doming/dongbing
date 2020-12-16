package com.ibm.follow.servlet.server.core.job.game;

import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.Xyft;
import com.ibm.common.utils.game.tools.XYFTTool;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_xyft.service.IbmRepDrawXyftService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

/**
 * @Description:
 * @Author: null
 * @Date: 2019-10-24 17:34
 * @Version: v1.0
 */
public class SettleXyftBetItemJob extends SettleTypeBetItemJob {
    private HandicapUtil.Code handicapCode;
    private String type;

    public SettleXyftBetItemJob(HandicapUtil.Code handicapCode,String type) {
        super(GameUtil.Code.XYFT,handicapCode,type);
        this.handicapCode = handicapCode;
        this.type=type;
    }

    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        if (System.currentTimeMillis() - PeriodTool.getLotteryDrawTime(game, null) > Xyft.PERIOD) {
            log.info("盘口【" + handicapCode.getName() + "】游戏【" + game.getName() + "】已超过结算时间");
            return;
        }

        IbmRepDrawXyftService repDrawXyftService = new IbmRepDrawXyftService();
        String period = PeriodTool.findLotteryPeriod(game, handicapCode).toString();
        //60 * 2s = 2min
        for (int i = 0; i < 60; i++) {
            if (StringTool.notEmpty(XYFTTool.getLottery(period,type))) {
                break;
            }
            if (i % 20 == 0) {
                CurrentTransaction.transactionCommit();
                if (repDrawXyftService.isExist(period,type)) {
                    break;
                }
            }
            Thread.sleep(SLEEP_TIME);
        }
        super.executeJob(context);
    }
}
