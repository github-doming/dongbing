package org.doming.example.thread.eg4.my.syn;
import java.util.List;
import java.util.Map;
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
	static class ClassExecute extends BasePlus {
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
				ClassExecute execute = new ClassExecute();
				execute.runSyn(() -> {
					return execute.execute(Map.class + "-执行-"+ finalI);
				}, Map.class);

			});
		}

		for (int i = 0; i < 3; i++) {
			int finalI = i;
			service.execute(() -> {
				ClassExecute execute = new ClassExecute();
				execute.runSyn(() -> {
					return execute.execute(List.class + "-执行-"+ finalI);
				}, List.class);

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
