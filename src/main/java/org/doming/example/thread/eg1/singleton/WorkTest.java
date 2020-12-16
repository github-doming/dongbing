package org.doming.example.thread.eg1.singleton;
/**
 * @Description: 并发测试
 * @Author:
 * @Date: 2019-04-26 10:15
 * @Version: v1.0
 */
public class WorkTest {
	public static void main(String[] args) throws InterruptedException {
		Work work = Work.getInstance();
		new Thread(() -> work.jobA()).start();
		Thread.sleep(3000);
		new Thread(() -> work.jobB()).start();
	}
}
