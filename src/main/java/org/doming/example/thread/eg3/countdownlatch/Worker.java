package org.doming.example.thread.eg3.countdownlatch;
/**
 * @Description: 工人类
 * @Author: Dongming
 * @Date: 2018-11-03 09:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Worker {
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 工作持续时间
	 */
	private long workDuration;

	public Worker(String name, long workDuration) {
		this.name = name;
		this.workDuration = workDuration;
	}

	/**
	 * 完成工作
	 */
	public void doWork() {
		System.out.println(name + "开始工作。。。");

		try {
			Thread.sleep(workDuration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(name + "结束工作。。。");

	}
}
