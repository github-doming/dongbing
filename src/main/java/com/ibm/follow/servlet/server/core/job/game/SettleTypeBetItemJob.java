package com.ibm.follow.servlet.server.core.job.game;

import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.PeriodTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.service.IbmSysBetOddsService;
import com.ibm.follow.servlet.server.core.thread.SettleBetItemThread;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-03-09 14:11
 * @Version: v1.0
 */
public class SettleTypeBetItemJob extends BaseCommJob {
    protected static final long SLEEP_TIME = 2 * 1000;
	protected GameUtil.Code game;
    protected HandicapUtil.Code handicap;
    private String type;

    public SettleTypeBetItemJob(GameUtil.Code game, HandicapUtil.Code handicap,String type) {
        this.game = game;
        this.handicap = handicap;
        this.type=type;
    }

    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        String gameId = GameUtil.id(game);
        //获取当前期的期数
        Object period = PeriodTool.findLotteryPeriod(game, handicap);
        //获取相同类型的盘口code
        List<String> handicapIds= HandicapGameUtil.handicapIds(game,handicap);
        if (ContainerTool.isEmpty(handicapIds)) {
            log.info("游戏【" + game.getName() + "】，未结算的盘口信息为空");
            return;
        }
        //获取开奖sql
        String sql = GameTool.findDrawInfoSql(game);
        if (StringTool.isEmpty(sql)) {
            log.error("盘口【" + handicap.getName() + "】游戏【" + game.getName() + "】，查找到的开奖sql语句为空");
            return;
        }

        IbmGameService gameService = new IbmGameService();
        Map<String, Object> drawInfo = gameService.findDrawInfo(sql, period,type);
        //10 * 2s = 20s
        for (int i = 0; i < 10; i++) {
            if (ContainerTool.notEmpty(drawInfo)) {
                break;
            } else {
                Thread.sleep(SLEEP_TIME);
                drawInfo = gameService.findDrawInfo(sql, period,type);
            }
        }
        if (ContainerTool.isEmpty(drawInfo)) {
            log.error("盘口【" + handicap.getName() + "】游戏【" + game.getName() + "】，查找到的开奖信息为空，开奖sql语句为：" + sql + "，期数为：" + period);
            return;
        }
        String drawNumber = drawInfo.get("DRAW_NUMBER_").toString();
        Collection<Object> drawItems = (Collection<Object>) drawInfo.get("DRAW_ITEMS_");

        Map<String, Integer>	oddsMap = new IbmSysBetOddsService().mapOddsByGame(gameId);
        String drawItem = StringUtils.join(drawItems,",");

        //开启一个线程，进行计算。
        ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
        ExecutorService executorService = threadExecutorService.findExecutorService();

        //结算当前方案信息
        for (String handicapId : handicapIds) {
            executorService.execute(new SettleBetItemThread(handicapId, period, drawNumber,drawItem,oddsMap,game));
        }

    }
}
