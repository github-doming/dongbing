package com.ibm.old.v1.cloud.core.job;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.old.v1.cloud.core.thread.plan.SendBetItemThread;
import com.ibm.old.v1.cloud.core.thread.plan.SendMergeBetItemThread;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.quartz.JobExecutionContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
/**
 * @Description: 发送投注项
 * @Author: Dongming
 * @Date: 2019-04-08 17:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SendBetItemJob extends BaseCommJob {
	private IbmGameEnum game;

	protected SendBetItemJob(IbmGameEnum game) {
		this.game = game;
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		String gameId = GameTool.findId(game.name());
		//已开启的游戏方案
		List<Map<String, Object>> planInfo = new IbmPlanTService().listInfoByGameId(gameId);
		if (ContainerTool.isEmpty(planInfo)) {
			log.info("游戏【" + game.getName() + "】已开启的方案列表为空");
			return;
		}

		//已开启的盘口id数组
		List<String> handicapIds = new IbmHandicapTService().listOpenId();
		if (ContainerTool.isEmpty(handicapIds)) {
			log.info("游戏【" + game.getName() + "】已开启的盘口列表为空");
			return;
		}

		//获取当前期的期数
		Object period = PeriodTool.findPeriod(game);

		//开启一个线程，进行计算。
		ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
		ExecutorService executorService = threadExecutorService.findExecutorService();

		//发送当前方案信息
		for (Map<String, Object> plan : planInfo) {
			for (String handicapId : handicapIds) {
				executorService.execute(
						new SendBetItemThread(gameId, plan.get("IBM_PLAN_ID_").toString(), handicapId, period, game,
								PlanTool.Code.valueOf(plan.get("PLAN_CODE_").toString())));
			}
		}

		//发送合并项投注信息
		for (String handicapId : handicapIds) {
			executorService.execute(new SendMergeBetItemThread(gameId, handicapId, period, game));
		}

	}
}
