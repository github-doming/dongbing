package org.doming.core.common.servlet;
import org.doming.core.configs.CharsetConfig;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * 异步请求 MVC服务类基类
 *
 * @Author: Dongming
 * @Date: 2020-05-19 13:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AsynMvcServletBase  extends MvcServletBase{
	@Override protected void executeMvc(HttpServletRequest request, HttpServletResponse response, String url)
			throws Exception {
		request.setCharacterEncoding(CharsetConfig.UTF8);
		//开启异步上下文
		AsyncContext asyncContext;
		if (!request.isAsyncStarted()) {
			asyncContext = request.startAsync();
		}else {
			asyncContext = request.getAsyncContext();
		}
		WebServletContent content = WebServletContent.getInstance();
		asyncContext.setTimeout(content.getAsyncTimeOut());
		if (content.getAsyncListener() != null) {
			asyncContext.addListener(content.getAsyncListener());
		}
		final ThreadPoolExecutor executor = content.getExecutorPool(content.getCode(url).code());
		Class<?> clazz = content.getClazz(url);
		WorkedRunnable runnable = new WorkedRunnable(asyncContext,clazz);
		executor.execute(runnable);
	}
}
