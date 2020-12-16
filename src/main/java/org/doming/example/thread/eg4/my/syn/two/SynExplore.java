package org.doming.example.thread.eg4.my.syn.two;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @Description: 锁
 * @Author: Dongming
 * @Date: 2019-07-30 10:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SynExplore {
	static class ClassExecute1 extends BasePlus {
		public Object execute(String msg) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("msg=".concat(msg));
			}
			return true;
		}

	}
	static class ClassExecute2 extends BasePlus {
		public Object execute(String msg) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("msg=".concat(msg));
			}
			return true;
		}

	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(6);
		for (int i = 0; i < 3; i++) {
			int finalI = i;
			service.execute(() -> {
				ClassExecute1 execute = new ClassExecute1();
				execute.runSyn(() -> {
					return execute.execute(ClassExecute1.class + "-执行-" + finalI);
				});

			});
		}

		for (int i = 0; i < 3; i++) {
			int finalI = i;
			service.execute(() -> {
				ClassExecute2 execute = new ClassExecute2();
				execute.runSyn(() -> {
					return execute.execute(ClassExecute2.class + "-执行-" + finalI);
				});

			});
		}
		service.shutdown();
		while (!service.isTerminated()) {
			Thread.sleep(1000);
			System.out.println();
		}
		System.out.println("运行完成");

	}

}
