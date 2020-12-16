package c.a.tools.mvc_async.nut;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ThreadPoolExecutor;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.util.core.asserts.AssertUtil;
//@WebServlet(urlPatterns = "/MvcAsyncServlet", asyncSupported = true)
//@WebServlet(urlPatterns = "/list.do2", asyncSupported = true)
//@WebServlet(urlPatterns = "*.do2", asyncSupported = true)
public class MvcAsyncServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Logger log = LogManager.getLogger(this.getClass());
	private String logFunError = "功能出错,";
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			log.trace("异步线程名=" + Thread.currentThread().getName());
			log.trace("异步线程ID=" + Thread.currentThread().getId());
			request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
			AsyncContext asyncContext = request.getAsyncContext();
			if (asyncContext == null) {
				asyncContext = request.startAsync();
			}
			asyncContext.addListener(new MvcAsyncListener());
			// 异步servlet的超时时间,异步Servlet有对应的超时时间，如果在指定的时间内没有执行完操作，
			// response依然会走原来Servlet的结束逻辑，后续的异步操作执行完再写回的时候，可能会遇到异常。
			asyncContext.setTimeout(50000);
			//asyncContext.setTimeout(1000);
			ThreadPoolExecutor executor = (ThreadPoolExecutor) request.getServletContext()
					.getAttribute(SysConfig.ThreadMvcExecutor);
			AssertUtil.isNull(executor, "mvc线程池没有初始化,请检查配置thread.mvc.start");
			MvcAsyncAyThread mvcAsyncThread = new MvcAsyncAyThread();
			mvcAsyncThread.setAsyncContext(asyncContext);
			executor.execute(mvcAsyncThread);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void executeLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// log.trace("start");
		String logStr = "调用url=" + request.getServletPath();
		log.trace(logStr);
		this.execute(request, response);
		// log.trace("end");
	}
	public void executeTime(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		Calendar calendar = Calendar.getInstance();
		long startCalendarLong = calendar.getTimeInMillis();
		// 执行业务
		String servletPath = request.getServletPath();
		try {
			// 执行业务
			this.executeLog(request, response);
		} catch (Exception e) {
			String logStr = "运行中RuntimeException，业务出错,出错的url=" + servletPath;
			logStr = logFunError + logStr;
			log.error(logStr);
			log.error(logStr, e);
			e.printStackTrace();
		} finally {
			calendar = Calendar.getInstance();
			long endCalendarLong = calendar.getTimeInMillis();
			long timeSpend = endCalendarLong - startCalendarLong;
			log.trace("花费时间timeSpend=" + timeSpend);
			contextUtil = ContextThreadLocal.findThreadLocal().get();
			if (contextUtil != null) {
				contextUtil.remove();
				ContextThreadLocal.findThreadLocal().remove();
				// 对象设置为空，自动回收内存
				contextUtil = null;
			}
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.executeTime(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.executeTime(request, response);
	}
}
