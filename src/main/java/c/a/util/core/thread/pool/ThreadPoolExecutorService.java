package c.a.util.core.thread.pool;
import c.a.util.core.bean.BeanThreadLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;
public class ThreadPoolExecutorService {
	// 日志
	protected Logger log = LogManager.getLogger(this.getClass());
	// 线程id
	private String threadId = null;
	// 线程名称
	private String threadName = null;
	private ExecutorService service = null;
	// 队列
	private int capacity;
	private int corePoolSize;
	// 最大线程数
	private int maximumPoolSize;
	private long keepAliveTime;
	private String rejectedExecutionHandler;
	/**
	 * 初始化
	 *
	 * @param capacityInput
	 * @param corePoolSizeInput
	 * @param maximumPoolSizeInput
	 * @param keepAliveTimeInput
	 * @throws Exception 返回类型 void
	 * @Title: init
	 * @Description: 参数说明
	 */
	public void init(String capacityInput, String corePoolSizeInput, String maximumPoolSizeInput,
			String keepAliveTimeInput, String rejectedExecutionHandlerInput, String threadIdInput,
			String threadNameInput) throws Exception {
		capacity = BeanThreadLocal.find(capacityInput, 300);
		corePoolSize = BeanThreadLocal.find(corePoolSizeInput, 100);
		maximumPoolSize = BeanThreadLocal.find(maximumPoolSizeInput, 200);
		keepAliveTime = BeanThreadLocal.find(keepAliveTimeInput, 30000l);
		rejectedExecutionHandler = BeanThreadLocal
				.find(rejectedExecutionHandlerInput, "c.a.util.core.thread.pool.ThreadPoolRejectedExecutionHandler");
		threadName = BeanThreadLocal.find(threadNameInput, "defaultThreadName");
		threadId = BeanThreadLocal.find(threadIdInput, "defaultThreadId");
	}
	public ExecutorService findExecutorService() throws Exception {
		if (service == null) {
			// System.out.println("ExecutorService == null");
			/**
			 * 对于首先来的A,B来说直接运行，接下来，
			 *
			 * 如果来了C,D， 他们会被放到queue中，如果接下来再来E,F，则增加线程运行E，F。
			 *
			 * 但是如果再来任务，队列无法再接受了，
			 *
			 * 线程数也到达最大的限制了，所以就会使用拒绝策略来处理。
			 */
			Class rejectedExecutionHandlerClass = Class.forName(rejectedExecutionHandler);
			RejectedExecutionHandler handler = (RejectedExecutionHandler) rejectedExecutionHandlerClass.newInstance();
			service = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
					new ArrayBlockingQueue<Runnable>(capacity), new ThreadFactory() {
				int threadIdxInt = 0;
				@Override public Thread newThread(Runnable runnable) {
					threadIdxInt = threadIdxInt + 1;
					Thread invokeThread = new Thread(runnable);
					if (false) {
						invokeThread.setName("thread&" + threadId + "_" + threadName + "_" + threadIdxInt);
					}
					invokeThread.setName("thread&" + threadId + "_" + threadIdxInt);
					// java中线程分为两种类型：用户线程和守护线程。
					// 通过Thread.setDaemon(false)设置为用户线程；
					// 通过Thread.setDaemon(true)设置为守护线程。
					// 如果不设置次属性，默认为用户线程。
					// invokeThread.setDaemon(true);
					return invokeThread;
				}
			}, handler) {
				protected void beforeExecute(Thread thread, Runnable runnable) {
					log.trace("线程beforeExecute=" + thread.getName());
				}
			};
		}
		return service;
	}

	public void shutdown() {
		try {
			if (service != null && service.isTerminated()) {
				//执行此函数后线程池不再接收新任务，并等待所有任务执行完毕后销毁线程。此函数并不会等待线程销毁完毕，而是立即返回的
				service.shutdown();
				//如想要同步等待线程池完成关闭，可使用下面的函数判断是否都执行完毕了，该函数等待timeout后，返回是否所有任务都执行完毕了
				service.awaitTermination(1, TimeUnit.SECONDS);
				//尝试结束所有活动线程，并返回等待队列里的任务
				service.shutdownNow();
				log.debug("关闭线程池完成");
			}
		} catch (InterruptedException e) {
			log.error("关闭线程池错误", e);
		}
	}

}
