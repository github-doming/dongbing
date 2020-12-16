package com.ibm.old.v1.common.doming.test.data;
import c.a.util.core.test.CommTest;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.doming.core.tools.DateTool;
import org.junit.Test;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * @Description: 清理数据
 * @Author: Dongming
 * @Date: 2019-05-10 18:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class DataCleaningTest extends CommTest {

	@Test public void test() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			dataCleaning();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}
	private void dataCleaning() throws InterruptedException {
		ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(6,
				new BasicThreadFactory.Builder().namingPattern("simulate-exec-bet-item-schedule-pool-%d").daemon(true)
						.build());
		executorService.scheduleAtFixedRate(new ExecBetItemCleaningThread(), 0, 5, TimeUnit.MINUTES);

		System.out.println("创建循环线程完成，正在执行中");
		Thread.sleep(DateTool.getMillisecondsMinutes(300));
		// 向线程池传递关闭示意
		executorService.shutdown();

	}
}
