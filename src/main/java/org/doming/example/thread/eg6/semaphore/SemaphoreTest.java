package org.doming.example.thread.eg6.semaphore;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @Description: 哲学家进餐
 * @Author: Dongming
 * @Date: 2018-11-03 11:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SemaphoreTest {

	/**
	 * 存放线程共享信号量的上下文
	 */
	static class AppContext {

		/**
		 * 叉子数量(资源)
		 */
		public static final int NUM_OF_FORKS = 5;
		/**
		 * 哲学家数量(线程)
		 */
		public static final int NUM_OF_PHILO = 5;

		/**
		 * 叉子的信号量
		 */
		public static Semaphore[] forks;

		/**
		 * 哲学家的信号量
		 */
		public static Semaphore counter;

		static {
			forks = new Semaphore[NUM_OF_FORKS];
			for (int i = 0, len = forks.length; i < len; ++i) {
				// 每个叉子的信号量为1
				forks[i] = new Semaphore(1);
			}

			// 如果有N个哲学家，最多只允许N-1人同时取叉子
			counter = new Semaphore(NUM_OF_PHILO - 1);
		}

		/**
		 * 取得叉子
		 *
		 * @param index     第几个哲学家
		 * @param leftFirst 是否先取得左边的叉子
		 */
		public static void putOnFork(int index, boolean leftFirst) throws InterruptedException {
			if (leftFirst) {
				forks[index].acquire();
				forks[(index + 1) % NUM_OF_PHILO].acquire();
			} else {
				forks[(index + 1) % NUM_OF_PHILO].acquire();
				forks[index].acquire();
			}
		}

		/**
		 * 放回叉子
		 *
		 * @param index     第几个哲学家
		 * @param leftFirst 是否先放回左边的叉子
		 */
		public static void putDownFork(int index, boolean leftFirst) {
			if (leftFirst) {
				forks[index].release();
				forks[(index + 1) % NUM_OF_PHILO].release();
			} else {
				forks[(index + 1) % NUM_OF_PHILO].release();
				forks[index].release();
			}
		}
	}

	static class Philosopher implements Runnable {

		private int index;
		private String name;

		public Philosopher(int index, String name) {
			this.index = index;
			this.name = name;
		}

		@Override public void run() {
			while (true) {
				try {
					AppContext.counter.acquire();
					boolean leftFirst = index % 2 == 0;
					AppContext.putOnFork(index, leftFirst);
					// 取到两个叉子就可以进食
					System.out.println(Thread.currentThread().getName() + "----" + name + "正在吃意大利面（通心粉）...");

					AppContext.putDownFork(index, leftFirst);
					AppContext.counter.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public static void main(String[] args) {

		// 5位哲学家的名字
		String[] names = {"张三", "李四", "王五", "赵六", "周七"};

		// 创建固定大小的线程池
		//		ExecutorService es = Executors.newFixedThreadPool(AppContext.NUM_OF_PHILO);

		ExecutorService executorService = new ThreadPoolExecutor(AppContext.NUM_OF_PHILO, AppContext.NUM_OF_PHILO, 0L,
				TimeUnit.MILLISECONDS, new LinkedTransferQueue<>(),
				new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build());

		for (int i = 0, len = names.length; i < len; ++i) {
			// 启动线程
			executorService.execute(new Philosopher(i, names[i]));
		}
		executorService.shutdown();
		/*
		for(int i = 0,len = names.length; i < len; i++) {
			new Thread(new Philosopher(i,names[i])).start();
		}*/
	}

}
