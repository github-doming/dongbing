package org.doming.example.thread.eg8.forkandjoin;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-11-05 11:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ForkAndJoinTest {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		int len = 100000;
		long startTime = System.currentTimeMillis();
		ForkJoinPool pool = new ForkJoinPool();
		Future<Integer> result = pool.submit(new Calculator(1, len));
		System.out.println(result.get());
		long endTime = System.currentTimeMillis();
		System.out.println("耗时：" + (endTime - startTime));
		int sum = 0;
		for(int i = 0; i <= len; i++) {
			sum+=i;
		}
		System.out.println(sum);
		startTime = System.currentTimeMillis();
		System.out.println("耗时：" + (startTime - endTime));
	}
}
