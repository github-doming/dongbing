package org.doming.example.thread.eg3.countdownlatch;
import java.util.concurrent.CountDownLatch;
/**
 * @Description: 测试线程
 * @Author: Dongming
 * @Date: 2018-11-03 10:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class WorkerThread implements Runnable {

	private Worker worker;
	private CountDownLatch latch;

	public WorkerThread(Worker worker, CountDownLatch latch) {
		this.worker = worker;
		this.latch = latch;
	}
	@Override public void run() {
		// 让工人开始工作
		worker.doWork();
		// 工作完成后倒计时次数减1
		latch.countDown();
	}
}
