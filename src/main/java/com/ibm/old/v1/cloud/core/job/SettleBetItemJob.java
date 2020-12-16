package com.ibm.old.v1.cloud.core.job;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.old.v1.cloud.core.thread.plan.SettleBetItemThread;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_sys_bet_odds_detail.w.service.IbmSysBetOddsDetailWService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
/**
 * @Description: 结算投注项
 * @Author: Dongming
 * @Date: 2019-04-03 18:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SettleBetItemJob extends BaseCommJob {
	protected static final long SLEEP_TIME = 2 * 1000;

	private IbmGameEnum game;

	public SettleBetItemJob(IbmGameEnum game) {
		this.game = game;
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		String gameId = GameTool.findId(game.name());

		//已开启的盘口id数组
		List<String> handicapIds = new IbmHandicapTService().listOpenId();
		if (ContainerTool.isEmpty(handicapIds)) {
			log.info("游戏【" + game.getName() + "】，已开启的盘口列表为空");
			return;
		}

		//获取开奖结果
		String sql = GameTool.findDrawInfoSql(game);
		if (StringTool.isEmpty(sql)) {
			log.error("游戏【" + game.getName() + "】，查找到的开奖sql语句为空");
			return;
		}

		//获取当前期的期数
		Object period = PeriodTool.findLotteryPeriod(game);
		IbmGameTService gameService = new IbmGameTService();
		Map<String, Object> drawInfo = gameService.findDrawInfo(sql, period);

		//60 * 2s = 2min
		for (int i = 0; i < 60; i++) {
			if (ContainerTool.notEmpty(drawInfo)) {
				break;
			} else {
				Thread.sleep(SLEEP_TIME);
				drawInfo = gameService.findDrawInfo(sql, period);
			}
		}
		if (ContainerTool.isEmpty(drawInfo)) {
			log.error("游戏【" + game.getName() + "】，查找到的开奖信息为空，开奖sql语句为：" + sql + "，期数为：" + period);
			return;
		}

		String drawNumber = drawInfo.get("DRAW_NUMBER_").toString();
		Collection<Object> drawItems = (Collection<Object>) drawInfo.get("DRAW_ITEMS_");

		Map<Object, Object>	oddsMap = new IbmSysBetOddsDetailWService().mapOddsByGame(gameId);
		String drawItem = StringUtils.join(drawItems,",");


		//开启一个线程，进行计算。
		ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
		ExecutorService executorService = threadExecutorService.findExecutorService();

		//结算当前方案信息
		for (String handicapId : handicapIds) {
			executorService.execute(new SettleBetItemThread(gameId, handicapId, period, drawNumber,drawItem,oddsMap,game));
		}

	}
}
