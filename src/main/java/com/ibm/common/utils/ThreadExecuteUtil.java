package com.ibm.common.utils;
import org.doming.core.tools.ThreadTool;

import java.util.concurrent.ThreadPoolExecutor;
/**
 * @Description: 线程池
 * @Author: Dongming
 * @Date: 2019-12-06 16:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ThreadExecuteUtil {

	private ThreadPoolExecutor mqExecutor;
	private ThreadPoolExecutor logExecutor;
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
		ThreadTool.close(instance.logExecutor);
		ThreadTool.close(instance.mqExecutor);
		ThreadTool.close(instance.coreExecutor);

	}

	/**
	 * 获取保存日志线程池
	 *
	 * @return 线程池
	 */
	public ThreadPoolExecutor getLogExecutor() {
		if (logExecutor == null) {
			synchronized (ThreadExecuteUtil.class) {
				logExecutor = ThreadTool.createExecutor("save-log", 5, 100, 5, 2000);
			}
		}
		return logExecutor;
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
		if (mqExecutor == null) {
			synchronized (ThreadExecuteUtil.class) {
				mqExecutor = ThreadTool.createExecutor("job", 20, 20 * 4, 20 * 3, 2000);
			}
		}
		return mqExecutor;
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
