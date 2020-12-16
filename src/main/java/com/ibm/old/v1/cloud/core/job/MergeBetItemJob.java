package com.ibm.old.v1.cloud.core.job;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.old.v1.cloud.core.thread.plan.MergeBetItemThread;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.quartz.JobExecutionContext;

import java.util.List;
import java.util.concurrent.ExecutorService;
/**
 * @Description: 合并投注项
 * @Author: Dongming
 * @Date: 2019-04-08 18:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MergeBetItemJob extends BaseCommJob {
	IbmGameEnum game;

	protected MergeBetItemJob(IbmGameEnum game) {
		this.game = game;
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		String gameId = GameTool.findId(game.name());

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

		//合并当前盘口信息
		for (String handicapId : handicapIds) {
			executorService.execute(new MergeBetItemThread(gameId, handicapId, period,game));
		}
	}
}
