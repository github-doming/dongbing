package org.doming.example.thread.eg9.volatile_synchronized;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-03-12 16:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ThreadTest {
	public static void main(String[] args) throws InterruptedException {
		int size = 10000;
		ExecutorService service = Executors.newFixedThreadPool(size);
		long time1, time2;

		time1 = System.currentTimeMillis();
		Counter1 counter1 = new Counter1();
		for (int i = 0; i < size; i++) {
			service.execute(counter1);
		}
		time2 = System.currentTimeMillis();
		System.out.println("counter1消耗时间=" + (time2 - time1));

		time1 = System.currentTimeMillis();
		 counter1 = new Counter1();
		for (int i = 0; i < size; i++) {
			service.execute(counter1);
		}
		time2 = System.currentTimeMillis();
		System.out.println("counter1消耗时间=" + (time2 - time1));

		time1 = System.currentTimeMillis();
		Counter2 counter2 = new Counter2();
		for (int i = 0; i < size; i++) {
			service.execute(counter2);
		}
		time2 = System.currentTimeMillis();
		System.out.println("counter2消耗时间=" + (time2 - time1));

		time1 = System.currentTimeMillis();
		Counter3 counter3 = new Counter3();
		for (int i = 0; i < size; i++) {
			service.execute(counter3);
		}
		time2 = System.currentTimeMillis();
		System.out.println("counter3消耗时间=" + (time2 - time1));

		Thread.sleep(2 * 1000L);
		System.out.println("counter1" + counter1);
		System.out.println("counter2" + counter2);
		System.out.println("counter3" + counter3);
		service.shutdown();

	}
}
