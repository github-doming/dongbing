package org.doming.example.thread.eg3.countdownlatch;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
/**
 * @Description: CountDownLatch测试类
 * CountDownLatch是一种简单的同步模式，它让一个线程可以等待一个或多个线程完成它们的工作从而避免对临界资源并发访问所引发的各种问题。
 * @Author: Dongming
 * @Date: 2018-11-03 10:04
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CountDownLatchTest {
	public static final int MAX_WORK_DURATION = 5000;
	public static final int MIN_WORK_DURATION = 1000;

	/**
	 * 随机产生工作时间
	 */
	public static long getRandomWorkDuration(long min, long max) {
		return (long) (Math.random() * (max - min) + min);
	}

	@Test public void test(){
		CountDownLatch latch = new CountDownLatch(2);
		Worker worker1 = new Worker("张三",getRandomWorkDuration(MIN_WORK_DURATION,MAX_WORK_DURATION));
		Worker worker2 = new Worker("李四",getRandomWorkDuration(MIN_WORK_DURATION,MAX_WORK_DURATION));

		new Thread(new WorkerThread(worker1,latch)).start();
		new Thread(new WorkerThread(worker2,latch)).start();
		try {
			// 等待倒计时闩减到0
			latch.await();
			System.out.println("所有工作结束");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
