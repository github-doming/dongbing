package org.doming.core.common.servlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletResponse;
/**
 * @Description: 异步操作异常监听类
 * @Author: Dongming
 * @Date: 2019-05-17 09:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AsyncErrorListener implements AsyncListener {
	protected Logger log = LogManager.getLogger(this.getClass());

	@Override public void onComplete(AsyncEvent event) {
		log.trace("异步监听器已完成异步操作");
	}
	@Override public void onTimeout(AsyncEvent event) {
		AsyncContext asyncContext = event.getAsyncContext();
		if (asyncContext != null) {
			log.error("异步监听器超时");
			try {
				HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} finally {
				asyncContext.complete();
			}
		}
	}
	@Override public void onError(AsyncEvent event) {
		AsyncContext asyncContext = event.getAsyncContext();
		if (asyncContext != null) {
			log.error("异步监听器错误");
			try {
				HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} finally {
				asyncContext.complete();
			}
		}
	}
	@Override public void onStartAsync(AsyncEvent event) {
		log.trace("异步监听器启动新的异步循环");
	}
}
