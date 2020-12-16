package com.ibs.plan.common.utils;
import org.doming.core.tools.ThreadTool;

import java.util.concurrent.ThreadPoolExecutor;
/**
 * 线程池工具类
 *
 * @Author: null
 * @Date: 2020-05-09 13:40
 * @Version: v1.0
 */
public class ThreadExecuteUtil {

	private ThreadPoolExecutor mqExecutor;
	private ThreadPoolExecutor jobExecutor;
	private ThreadPoolExecutor coreExecutor;
	private ThreadExecuteUtil() {
	}

	private static volatile ThreadExecuteUtil instance = null;
	public static ThreadExecuteUtil findInstance() {
		if (instance == null) {
			synchronized (ThreadExecuteUtil.class) {
				if (instance == null) {
					// 初始化
					instance = new ThreadExecuteUtil();
				}
			}
		}
		return instance;
	}

	public static void destroy() throws InterruptedException {
		if (instance == null) {
			return;
		}
		ThreadTool.close(instance.mqExecutor);
		ThreadTool.close(instance.coreExecutor);
		ThreadTool.close(instance.jobExecutor);

	}

	/**
	 * 获取MQ线程池
	 *
	 * @return 线程池
	 */
	public ThreadPoolExecutor getMqExecutor() {
		if (mqExecutor == null) {
			synchronized (ThreadExecuteUtil.class) {
				mqExecutor = ThreadTool.createExecutor("mq", 20, 20 * 4, 20 * 3, 2000);
			}
		}
		return mqExecutor;
	}
	/**
	 * 获取JOB线程池
	 *
	 * @return 线程池
	 */
	public ThreadPoolExecutor getJobExecutor() {
		if (jobExecutor == null) {
			synchronized (ThreadExecuteUtil.class) {
				jobExecutor = ThreadTool.createExecutor("job", 5, 5 * 4, 5 * 3, 2000);
			}
		}
		return jobExecutor;
	}

	/**
	 * 获取JOB线程池
	 *
	 * @return 线程池
	 */
	public ThreadPoolExecutor getCoreExecutor() {
		if (coreExecutor == null) {
			synchronized (ThreadExecuteUtil.class) {
				coreExecutor = ThreadTool.createExecutor("core", 5, 5 * 4, 5 * 3, 2000);
			}
		}
		return coreExecutor;
	}
}
