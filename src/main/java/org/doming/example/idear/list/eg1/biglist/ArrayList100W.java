package org.doming.example.idear.list.eg1.biglist;
import org.doming.core.tools.RandomTool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-04-30 14:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ArrayList100W {
	//数组长度

	private static final int LIST_LENGTH = 100000;
	//线程数量

	private static  int THREAD_NUMBER = 100;
	//每个线程读取的list个数

	public static  int SLICE_LENGTH = LIST_LENGTH / THREAD_NUMBER;

	public static void main(String[] args) throws InterruptedException {
		List<MyObject> list = new ArrayList<>();
		for (int i = 0; i < LIST_LENGTH; i++) {
			list.add(new MyObject());
		}

		long start, end;
		//第一种方法，直接遍历
		start = System.currentTimeMillis();
		int numberEquals2 = 0;
		for (int i = 0; i < LIST_LENGTH; i++) {
			if (list.get(i).type == 2) {
				numberEquals2++;
			}
		}
		end = System.currentTimeMillis();
		System.out.println("线程数量1，线性遍历，花费的时间:" + (end - start) + " milliseconds, " + "type等于2的个数有:" + numberEquals2);

		//第二种方法，用100个线程来分别跑，用来计数的变量是原子变量
		start = System.currentTimeMillis();
		AtomicInteger atomicNumberEquals2 = new AtomicInteger(0);
		ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUMBER);
		for (int i = 0; i < THREAD_NUMBER; i++) {
			final int threadNumber = i;
			pool.execute(new Runnable() {
				@Override
				public void run() {
					for (int j = threadNumber*SLICE_LENGTH; j < ((threadNumber+1)*SLICE_LENGTH ); j++) {
						if (list.get(j).type == 2) {
							atomicNumberEquals2.addAndGet(1);
						}
					}
				}
			});

		}
		pool.shutdown();
		pool.awaitTermination(1, TimeUnit.DAYS);
		end = System.currentTimeMillis();
		System.out.println("线程数量:" + THREAD_NUMBER + "(原子变量)花费的时间:" + (end - start) + " milliseconds, " + "type等于2的个数有:"
				+ atomicNumberEquals2.get());

		start = System.currentTimeMillis();
		int result[] = new int[THREAD_NUMBER];
		ExecutorService pool2 = Executors.newFixedThreadPool(THREAD_NUMBER);
		for (int i = 0; i < THREAD_NUMBER; i++) {
			final int threadNumber = i;
			pool2.execute(new Runnable() {
				@Override
				public void run() {
					for (int j = threadNumber*SLICE_LENGTH; j < ((threadNumber+1)*SLICE_LENGTH); j++) {
						if (list.get(j).type == 2) {
							result[threadNumber]++;
						}
					}
				}
			});

		}
		pool2.shutdown();
		pool2.awaitTermination(1, TimeUnit.DAYS);
		numberEquals2 = 0;
		for (int i = 0; i < THREAD_NUMBER; i++) {
			numberEquals2 += result[i];
		}
		end = System.currentTimeMillis();
		System.out.println("线程数量:"+THREAD_NUMBER+"(数组存储)花费的时间:"+(end-start)+" milliseconds, "+"type等于2的个数有:"+numberEquals2);


		//第四种方法，获取本机CPU核心数*2，设置为线程数量
		THREAD_NUMBER =  Runtime.getRuntime().availableProcessors()*2;
		SLICE_LENGTH = LIST_LENGTH/THREAD_NUMBER;
		start = System.currentTimeMillis();
		int[] result2 = new int[THREAD_NUMBER];
		ExecutorService pool3 = Executors.newFixedThreadPool(THREAD_NUMBER);
		for (int i = 0; i < THREAD_NUMBER; i++) {
			final int threadNumber = i;
			pool3.execute(() -> {
				for (int j = threadNumber*SLICE_LENGTH; j < ((threadNumber+1)*SLICE_LENGTH); j++) {
					if (list.get(j).type == 2) {
						result2[threadNumber]++;
					}
				}
			});
		}
		pool3.shutdown();
		pool3.awaitTermination(1, TimeUnit.DAYS);
		numberEquals2 = 0;
		for (int i = 0; i < THREAD_NUMBER; i++) {
			numberEquals2 += result2[i];
		}
		end = System.currentTimeMillis();
		System.out.println("线程数量:"+THREAD_NUMBER+"(CPU核心*2)花费的时间:"+(end-start)+" milliseconds, "+"type等于2的个数有:"+numberEquals2);

		//第五种方法，使用parallelStream
		AtomicInteger atomicResult = new AtomicInteger(0);
		start = System.currentTimeMillis();
		list.parallelStream().forEach( element ->{
			if (element.type ==2){
				atomicResult.addAndGet(1);
			}
		});
		end = System.currentTimeMillis();
		System.out.println("线程数量(默认为CPU核心数):, 花费的时间:" + (end - start) + " milliseconds, " + "type等于2的个数有:" + atomicResult.get());

		//第六种方法，使用filter
		start = System.currentTimeMillis();
		long totalCount = list.parallelStream().filter(element-> 2== element.type).count();
		end = System.currentTimeMillis();
		System.out.println("filter方法(默认为CPU核心数):, 花费的时间:" + (end - start) + " milliseconds, " + "type等于2的个数有:" + totalCount);

	}

}
class MyObject {
	int type;
	public MyObject() {
		this.type = RandomTool.getInt(100);
	}
}
