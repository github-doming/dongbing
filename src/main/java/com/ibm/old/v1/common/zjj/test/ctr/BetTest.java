package com.ibm.old.v1.common.zjj.test.ctr;

import c.a.util.core.test.CommTest;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.PK10Tool;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import org.doming.core.tools.ContainerTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class BetTest extends CommTest {
	@Test
	public void demo() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
//			bet();
			betItem();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}

	}

	private void betItem() throws Exception {
		IbmGameTService gameTService = new IbmGameTService();
		String gameId = GameTool.findId(IbmGameEnum.PK10.name());

		// 已开启的游戏方案
		IbmPlanTService planTService = new IbmPlanTService();
		List<Map<String, Object>> planInfo = planTService.listInfoByGameId(gameId);
		if (ContainerTool.isEmpty(planInfo)) {
			return;
		}

		Integer period = PeriodTool.findPK10Period();

		// 开启一个线程，进行计算。
		ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
		ExecutorService executorService = threadExecutorService.findExecutorService();

		// 已开启的盘口id数组
		IbmHandicapTService handicapTService = new IbmHandicapTService();
		List<String> handicapIds = handicapTService.listOpenId();
		if (ContainerTool.isEmpty(handicapIds)) {
			return;
		}
		System.out.println("-------------------------");
		
	}

	private void bet() throws Exception {

		IbmGameTService gameTService = new IbmGameTService();
		String gameId = GameTool.findId(IbmGameEnum.PK10.name());

		// 已开启的游戏方案
		IbmPlanTService planTService = new IbmPlanTService();
		List<Map<String, Object>> planInfo = planTService.listInfoByGameId(gameId);
		if (ContainerTool.isEmpty(planInfo)) {
			return;
		}

		Integer period = PeriodTool.findPK10Period();
		System.out.println("下一期期数：" + period);
		// 开启一个线程，进行计算。
		ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
		ExecutorService executorService = threadExecutorService.findExecutorService();

		// 已开启的盘口id数组
		IbmHandicapTService handicapTService = new IbmHandicapTService();
		List<String> handicapIds = handicapTService.listOpenId();
		if (ContainerTool.isEmpty(handicapIds)) {
			return;
		}

		PK10Tool.find20HistoryData(period);

		

	}
}
