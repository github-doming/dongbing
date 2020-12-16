package org.doming.example.thread.eg1.singleton;
/**
 * @Description: 单例多线程
 * @Author:
 * @Date: 2019-04-26 10:11
 * @Version: v1.0
 */
public class Work {
	private static volatile Work instance;
	private Work() {
	}
	public static Work getInstance() {
		if (instance == null) {
			synchronized (Work.class) {
				if (instance == null) {
					instance = new Work();
				}
			}
		}
		return instance;
	}

	public void jobA() {
		System.out.println("开始执行工作A");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("执行完成工作A");
	}

	public void jobB() {
		System.out.println("开始执行工作B");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("执行完成工作B");
	}

}
