package org.doming.core.common.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.servlet.WorkedCallable;

import javax.servlet.AsyncContext;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * @Description: 线程池无法处理后，由此类来进行处理
 * @Author: Dongming
 * @Date: 2019-05-17 10:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PoolErrorExecutionHandler implements RejectedExecutionHandler {
    private static final Logger log = LogManager.getLogger(PoolErrorExecutionHandler.class);
	@Override public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
        log.error("线程池执行错误:{}", Thread.currentThread().getName());
		if (runnable instanceof WorkedCallable) {
			WorkedCallable callable = ((WorkedCallable) runnable);
			AsyncContext asyncContext = callable.getAsyncContext();
			if (asyncContext != null) {
				try {
					log.error("请求被拒绝：{}", asyncContext.getRequest().getRemoteAddr());
					callable.serverError();
				} finally {
					asyncContext.complete();
				}
			}
		} else if (runnable instanceof BaseCommThread) {
			log.error("线程池执行的类:{}", runnable.getClass().getName());
		}
    }
}
