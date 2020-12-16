package c.a.tools.mvc_async.nut;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@WebListener
public class MvcAsyncListener implements AsyncListener {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public void onComplete(AsyncEvent asyncEvent) throws IOException {
		log.trace("MVC异步监听器 MvcAsyncListener onComplete");
		// we can do resource cleanup activity here
	}

	@Override
	public void onError(AsyncEvent asyncEvent) throws IOException {
		log.error("MVC异步监听器出错 MvcAsyncListener onError");
		// we can return error response to client
	}

	@Override
	public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
		log.trace("MVC异步监听器 MvcAsyncListener onStartAsync");
		// we can log the event here
	}

	@Override
	public void onTimeout(AsyncEvent asyncEvent) throws IOException {
		try {
			log.error("MVC异步监听器超时 MvcAsyncListener onTimeout");
			AsyncContext asyncContext=asyncEvent.getAsyncContext();
			ServletRequest servletRequest = asyncContext.getRequest();
			ServletResponse servletResponse = asyncContext.getResponse();
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			String servletPath = request.getServletPath();
			String logStr = "MVC异步监听器超时,出错的url=" + servletPath;
			log.error(logStr);
			
			// we can send appropriate response to client
			//ServletResponse servletResponse = asyncEvent.getAsyncContext().getResponse();
			PrintWriter out = servletResponse.getWriter();
			out.write("MVC异步监听器超时 MvcAsyncListener TimeOut Error in Processing");
		} catch (Exception e) {
			log.error("MVC异步监听器超时 ");
			e.printStackTrace();
		}
	}
}
