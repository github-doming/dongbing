package c.a.util.core.thread.courser;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 * 
 * 猎狗
 * @Description: 
 * @ClassName: CourserUtil 
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class CourserUtil {
	private static int corePoolSize = 10;
	private static int maximumPoolSize = 500;
	private static long keepAliveTime = 1000 * 30;
	private static int capacity = 500;
	private CourserUtil() {
	}
	private static ExecutorService executorService = new ThreadPoolExecutor(
			corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(capacity), new ThreadFactory() {
				int thread_id = 0;
				@Override
				public Thread newThread(Runnable cRunnable) {
					thread_id = thread_id + 1;
					Thread invokeThread = new Thread(cRunnable);
					invokeThread.setName("invoker-thread-" + thread_id);
					invokeThread.setDaemon(true);
					return invokeThread;
				}
			}, new ThreadPoolExecutor.AbortPolicy()) {
		protected void beforeExecute(Thread t, Runnable r) {
		}
	};

	public static <T> T call(Callable<?> taskCallable, TimeUnit timeUnitInput, long timeOutLong)
			throws Exception {
		Future<?> future = executorService.submit(taskCallable);
		Object returnObject = null;
		try {
			returnObject = future.get(timeOutLong, timeUnitInput);
		} catch (InterruptedException e) {
			throw new Exception(e);
		} catch (ExecutionException e) {
			throw new Exception(e);
		} catch (TimeoutException e) {
			throw new TimeoutException("invoke timeout时间超出异常" + e);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return (T) returnObject;
	}
	public static void call(Runnable taskRunnable, TimeUnit timeUnit, long timeOutLong)
			throws Exception {
		Future<?> future = executorService.submit(taskRunnable);
		try {
			future.get(timeOutLong, timeUnit);
		} catch (InterruptedException e) {
			throw new Exception(e);
		} catch (ExecutionException e) {
			throw new Exception(e);
		} catch (TimeoutException e) {
			throw new TimeoutException("invoke timeout时间超出异常" + e);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
