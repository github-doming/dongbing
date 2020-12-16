package org.doming.example.thread.eg9.volatile_synchronized;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-03-12 16:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Counter1 implements Runnable{

	private volatile int count = 1;

	@Override public void run() {
		try {
			count++;
			// 为了使运行结果更随即，延迟3毫秒
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
		return "[count=" + count + "]";
	}
}
