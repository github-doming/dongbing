package c.a.tools.mvc.nut;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.tools.mvc_async.nut.MvcAsyncAyThread;
import c.a.tools.mvc_async.nut.MvcAsyncListener;
import c.a.util.core.asserts.AssertUtil;
/**
 * 
 * mvc的核心api
 * 
 * @Description:
 * @ClassName: MvcHttpServlet
 * @date 2017年3月10日 上午10:28:38
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class MvcHttpServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Logger log = LogManager.getLogger(this.getClass());
	private String logFun = "mvc异步功能,";
	private String logFunError = "mvc异步功能出错,";
	@Override
	public String getInitParameter(String name) {
		return super.getInitParameter(name);
	}
	@Override
	public void init() throws ServletException {
	}
	@Override
	public void destroy() {
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.executeTime(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.executeTime(request, response);
	}
	public void executeTime(HttpServletRequest request, HttpServletResponse response) {
		// Calendar calendar = Calendar.getInstance();
		// long timeStart = calendar.getTimeInMillis();
		long timeStart = System.currentTimeMillis();
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		// 执行业务
		// log.trace("start");
		String servletPath = null;
		if (request != null) {
			servletPath = request.getServletPath();
			String logStr = "调用url=" + servletPath;
			log.info(logStr);
		}
		this.execute(request, response);
		contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
			// 对象设置为空，自动回收内存
			contextUtil = null;
		}
		// calendar = Calendar.getInstance();
		// long timeEnd = calendar.getTimeInMillis();
		long timeEnd = System.currentTimeMillis();
		long timeSpend = timeEnd - timeStart;
		log.info("花费时间timeSpend=" + timeSpend);
	}
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		log.trace("异步线程ID=" + Thread.currentThread().getId());
		log.trace("异步线程名=" + Thread.currentThread().getName());
		if (request != null) {
			log.trace("1 request.isAsyncStarted=" + request.isAsyncStarted());
			log.trace("1 request.isAsyncSupported=" + request.isAsyncSupported());
			// log.trace("1 request.getAsyncContext=" +
			// request.getAsyncContext());
			request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
			log.trace("2 request.isAsyncStarted=" + request.isAsyncStarted());
			log.trace("2 request.isAsyncSupported=" + request.isAsyncSupported());
			// log.trace("2 request.getAsyncContext=" +
			// request.getAsyncContext());
			AsyncContext asyncContext = null;
			if (request.isAsyncStarted()) {
			} else {
				asyncContext = request.startAsync();
				log.trace("3 request.isAsyncStarted=" + request.isAsyncStarted());
				log.trace("3 request.isAsyncSupported=" + request.isAsyncSupported());
				log.trace("3 request.getAsyncContext=" + request.getAsyncContext());
			}
			asyncContext.addListener(new MvcAsyncListener());
			// 异步servlet的超时时间,异步Servlet有对应的超时时间，如果在指定的时间内没有执行完操作，
			// response依然会走原来Servlet的结束逻辑，后续的异步操作执行完再写回的时候，可能会遇到异常。
			asyncContext.setTimeout(50000);
			// asyncContext.setTimeout(1000);
			ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) request.getServletContext()
					.getAttribute(SysConfig.ThreadMvcExecutor);
			AssertUtil.isNull(threadPoolExecutor, "mvc线程池没有初始化,请检查配置thread.mvc.start");
			MvcAsyncAyThread mvcAsyncThread = new MvcAsyncAyThread();
			mvcAsyncThread.setAsyncContext(asyncContext);
			threadPoolExecutor.execute(mvcAsyncThread);
		}
	}
}
