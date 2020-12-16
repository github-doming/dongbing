package com.ibm.old.v1.common.doming.test.data;
import c.a.tools.jdbc.IJdbcTool;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.common.doming.core.BaseComm;
import com.ibm.old.v1.common.doming.test.SimulateExecBetItemThread;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.DateTool;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * @Description: 大数据测试
 * @Author: Dongming
 * @Date: 2019-05-10 10:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BigDataTest extends BaseComm {

	@Test public void test() {
		try {
			super.transactionBegin("query");
			simulateExecBetItem();
		} catch (Exception e) {
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	private void simulateExecBetItem() throws Exception {
		IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
		//可以投注的数据
		List list = execBetItemTService.list();

		//盘口会员
		IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
		List<Map<String, Object>> ids = handicapMemberTService.listId();

		ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(10,
				new BasicThreadFactory.Builder().namingPattern("simulate-exec-bet-item-schedule-pool-%d").daemon(true)
						.build());
		for (Map<String, Object> idMap : ids) {
			executorService.scheduleAtFixedRate(new SimulateExecBetItemThread(list, idMap), 1, 15, TimeUnit.SECONDS);
		}
		System.out.println("创建循环线程完成，正在执行中");
		Thread.sleep(DateTool.getMillisecondsMinutes(300));
		// 向线程池传递关闭示意
		executorService.shutdown();
	}

	private void initData() throws Exception {
		IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
		List list = execBetItemTService.list();
		System.out.println("数据获取完毕");
		//开启一个线程，进行计算。
		ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
		int maxCount = 1000000;
		ExecutorService executorService = threadExecutorService.findExecutorService();
		for (int i = 0; i < maxCount / ThreadTest.MAX_COUNT; i++) {
			Thread.sleep(2 * 100);
			executorService.execute(new ThreadTest(list));
		}
		if (executorService.isShutdown()) {
			executorService.shutdown();
		}
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

	}

	@Test public void test02() {
		try {
			super.transactionBegin();
			IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
			//可以投注的数据
			List list = execBetItemTService.list();
			otherDataSource(list);
			execBetItemTService.saveBigData(list, list.size(), 20);
			super.transactionRoll();
			CurrentTransaction.transactionCommit();
			execBetItemTService.saveBigData(list, list.size(), 20);
		} catch (Exception e) {
			e.printStackTrace();
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	private void otherDataSource(List list) {
		IJdbcTool lastJdbcTool = super.transactionStart("data");
		try {
			IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
			execBetItemTService.saveBigData(list, list.size(), 200);
			CurrentTransaction.transactionCommit();
			execBetItemTService.saveBigData(list, list.size(), 150);
		} catch (Exception e) {
			super.transactionRoll();
		} finally {
			super.transactionFinish(lastJdbcTool);
		}
	}

}
