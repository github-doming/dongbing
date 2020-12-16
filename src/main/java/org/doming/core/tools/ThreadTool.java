package org.doming.core.tools;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.doming.core.common.thread.PoolErrorExecutionHandler;

import java.util.Map;
import java.util.concurrent.*;
/**
 * @Description: 线程池工具类
 * @Author: Dongming
 * @Date: 2019-10-23 10:00
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ThreadTool {

	/**
	 * 创建执行器
	 *
	 * @param executorCode 执行器编码
	 * @param corePoolSize 核心池大小
	 * @return 执行器
	 */
	public static ThreadPoolExecutor createExecutor(String executorCode, Integer corePoolSize) {
		return createExecutor(executorCode, corePoolSize, corePoolSize * 4, corePoolSize * 3, 20 * 1000);
	}

	/**
	 * 创建执行器
	 *
	 * @param executorCode           执行器编码
	 * @param corePoolSize           核心池大小
	 * @param maximumPoolSize        可执行最大容量
	 * @param queueCapacity          队列容量
	 * @param keepAliveTimeInSeconds 线程保持时间
	 * @return 执行器
	 */
	public static ThreadPoolExecutor createExecutor(String executorCode, Integer corePoolSize, Integer maximumPoolSize,
			Integer queueCapacity, Integer keepAliveTimeInSeconds) {
		//队列类型
		BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>(queueCapacity);
		//线程创建工厂类
		ThreadFactory threadFactory = new BasicThreadFactory.Builder().namingPattern(executorCode.concat("-pool-%2d"))
				.daemon(true).build();
		//为执行错误的任务，进行错误处理
		RejectedExecutionHandler handler = new PoolErrorExecutionHandler();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTimeInSeconds,
				TimeUnit.SECONDS, queue, threadFactory, handler);
		executor.allowCoreThreadTimeOut(true);
		return executor;

	}

	/**
	 * 获取定时执行器
	 *
	 * @param executorCode 执行器编码
	 * @param corePoolSize 核心池大小
	 * @return 执行器
	 */
	public static ScheduledThreadPoolExecutor createScheduledExecutor(String executorCode, Integer corePoolSize) {
		//线程创建工厂类
		ThreadFactory threadFactory = new BasicThreadFactory.Builder()
				.namingPattern(executorCode.concat("-schedule-pool-%2d")).daemon(true).build();
		//为执行错误的任务，进行错误处理
		RejectedExecutionHandler handler = new PoolErrorExecutionHandler();
		//这里可以对定时器线程对象进行其他设置
		return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory, handler);
	}

	/**
	 * 关闭执行器
	 *
	 * @param executor 执行器
	 */
	public static void close(ExecutorService executor) throws InterruptedException {
		if (executor != null && executor.isTerminated()) {
			//执行此函数后线程池不再接收新任务，并等待所有任务执行完毕后销毁线程。此函数并不会等待线程销毁完毕，而是立即返回的
			executor.shutdown();
			//如想要同步等待线程池完成关闭，可使用下面的函数判断是否都执行完毕了，该函数等待timeout后，返回是否所有任务都执行完毕了
			executor.awaitTermination(1, TimeUnit.SECONDS);
			//尝试结束所有活动线程，并返回等待队列里的任务
			executor.shutdownNow();
		}
	}

	/**
	 * 获取线程池的配置信息
	 *
	 * @param executor 线程池执行器
	 * @return 配置信息
	 */
	public static Map<String, Object> getConfig(ThreadPoolExecutor executor) {
		Map<String, Object> config = new HashedMap(6);
		config.put("poolSize", executor.getPoolSize());
		config.put("corePoolSize", executor.getCorePoolSize());
		config.put("maximumPoolSize", executor.getMaximumPoolSize());
		config.put("maxSize", executor.getQueue().size());
		config.put("activeCount", executor.getActiveCount());
		config.put("taskCount", executor.getTaskCount());
		config.put("completedTaskCount", executor.getCompletedTaskCount());
		config.put("keepAliveTimeSeconds", executor.getKeepAliveTime(TimeUnit.SECONDS));
		return config;
	}

	/**
	 * 设置线程池核心大小
	 *
	 * @param executor     线程池执行器
	 * @param corePoolSize 核心大小
	 * @return 设置成功 true
	 */
	public synchronized static boolean setCorePoolSize(ThreadPoolExecutor executor, int corePoolSize) {
		if (corePoolSize <= 0) {
			return false;
		}
		executor.setCorePoolSize(corePoolSize);
		return true;
	}

	/**
	 * 设置线程池最大容量
	 *
	 * @param executor        线程池执行器
	 * @param maximumPoolSize 最大容量
	 * @return 设置成功 true
	 */
	public synchronized static boolean setMaximumPoolSize(ThreadPoolExecutor executor, int maximumPoolSize) {
		if (maximumPoolSize <= 0 || maximumPoolSize < executor.getCorePoolSize()) {
			return false;
		}
		executor.setMaximumPoolSize(maximumPoolSize);
		return true;
	}

	/**
	 * 设置线程池空闲时间
	 *
	 * @param executor               线程池执行器
	 * @param keepAliveTimeInSeconds 空闲时间（s）
	 * @return 设置成功 true
	 */
	protected synchronized static boolean setKeepAliveTimeSeconds(ThreadPoolExecutor executor,
			long keepAliveTimeInSeconds) {
		if (keepAliveTimeInSeconds < 0) {
			return false;
		}
		executor.setKeepAliveTime(keepAliveTimeInSeconds, TimeUnit.SECONDS);
		return true;
	}

}
