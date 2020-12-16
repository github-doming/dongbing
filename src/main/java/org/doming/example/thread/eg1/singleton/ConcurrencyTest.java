package org.doming.example.thread.eg1.singleton;
import org.junit.Test;
/**
 * @Description: 并发测试
 * 原文：https://blog.csdn.net/upshi/article/details/53470584
 * @Author:
 * @Date: 2018-11-02 10:35
 * @Version: v1.0
 */
public class ConcurrencyTest {
	/**
	 * 执行次数
	 */
	private static final long COUNT = 1000000000L;

	@Test public void test() throws InterruptedException {
		//并发计算
		concurrency();
		//单线程计算
		serial();

	}

	public static void concurrency() throws InterruptedException {
		long start = System.currentTimeMillis();
		Thread thread = new Thread(() -> {
			int a = 0;
			for (long i = 0; i < COUNT; i++) {
				a += 5;
			}
			System.out.println(a);

		});
		thread.start();
		int b = 0;
		for (long i = 0; i < COUNT; i++) {
			b--;
		}
		int c = 0;
		for (long i = 0; i < COUNT; i++) {
			c++;
		}
		int d = 0;
		for (long i = 0; i < COUNT; i++) {
			d++;
		}
		int e = 0;
		for (long i = 0; i < COUNT; i++) {
			e++;
		}
		int f = 0;
		for (long i = 0; i < COUNT; i++) {
			f++;
		}
		long time = System.currentTimeMillis() - start;
		thread.join();
		System.out.println("concurrency:\t" + time + "ms,b=" + b);
	}

	private static void serial() {
		long start = System.currentTimeMillis();
		int a = 0;
		for (long i = 0; i < COUNT; i++) {
			a += 5;
		}
		int b = 0;
		for (long i = 0; i < COUNT; i++) {
			b--;
		}
		int c = 0;
		for (long i = 0; i < COUNT; i++) {
			c++;
		}
		int d = 0;
		for (long i = 0; i < COUNT; i++) {
			d++;
		}
		int e = 0;
		for (long i = 0; i < COUNT; i++) {
			e++;
		}
		int f = 0;
		for (long i = 0; i < COUNT; i++) {
			f++;
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("serial:\t\t\t" + time + "ms,b=" + b);
	}

}
