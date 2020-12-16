package org.doming.example.thread.eg7.future;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
/**
 * @Description: Future类测试
 * @Author: Dongming
 * @Date: 2018-11-03 14:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class FutureTest {
	public static final int POOL_SIZE = 10;

	static class CalcThread implements Callable<Double> {
		List<Double> list = new ArrayList<>();
		public CalcThread() {
			for (int i = 0; i < 1000; i++) {
				list.add(Math.random());
			}
		}
		@Override public Double call() throws Exception {
			double tolal = 0;
			for (Double d : list) {
				tolal += d;
			}
			return tolal / list.size();
		}
	}

	public static void main(String[] args) {
		List<Future<Double>> futureList = new ArrayList<>();
		ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);
		/*ExecutorService service = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 0L,
				TimeUnit.MILLISECONDS, new LinkedTransferQueue<>(),
				new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build());*/
		for(int i = 0; i < POOL_SIZE ; i++) {
			futureList.add(service.submit(new CalcThread()));
		}

		futureList.forEach(future -> {
			try {
				System.out.println(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
		/*for (Future<Double> future : futureList){
			try {
				System.out.println(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}*/
		service.shutdown();
	}
}
