package c.a.tools.mvc_async.nut;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.tools.mvc.action.MvcAction;
import c.a.tools.mvc.dto.MvcActionDto;
import c.a.tools.mvc.dto.MvcResultDto;
import c.a.tools.mvc.exception.BizRuntimeException;
import c.a.tools.mvc.nut.MvcConfig;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.string.StringUtil;
/**
 * 
 * Mvc线程
 */
public class MvcAsyncAyThread implements java.lang.Runnable {
	protected Logger log = LogManager.getLogger(this.getClass());
	private String logFun = "MVC异步功能,";
	private String logFunError = "MVC异步功能出错,";
	private AsyncContext asyncContext;
	public MvcAsyncAyThread() {
	}
	public AsyncContext getAsyncContext() {
		return asyncContext;
	}
	public void setAsyncContext(AsyncContext asyncContext) {
		this.asyncContext = asyncContext;
	}
	public void run() {
		this.executeTime();
	}
	public void executeTime() {
		// Calendar calendar = Calendar.getInstance();
		// long timeStart = calendar.getTimeInMillis();
		long timeStart = System.currentTimeMillis();
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		log.trace("MvcAsync Supported=" + asyncContext.getRequest().isAsyncSupported());
		try {
			ServletRequest servletRequest = asyncContext.getRequest();
			ServletResponse servletResponse = asyncContext.getResponse();
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			ServletContext servletContext = servletRequest.getServletContext();
			this.executeInit(request, response, servletContext);
		} catch (ServletException e) {
			String logStr = "ServletException,init出错";
			logStr = logFunError + logStr;
			log.error(logStr);
			log.error(logStr, e);
			// 打印到控制台
			e.printStackTrace();
			// 不需要跳转,直接打印JSON
			// 必须重新抛出异常给系统才能跳转
			// throw e;
		} catch (IOException e) {
			String logStr = "IOException,init出错";
			logStr = logFunError + logStr;
			log.error(logStr);
			log.error(logStr, e);
			// 打印到控制台
			e.printStackTrace();
			// 不需要跳转,直接打印JSON
			// 必须重新抛出异常给系统才能跳转
			// throw e;
		} catch (Exception e) {
			String logStr = "Exception,init出错";
			logStr = logFunError + logStr;
			log.error(logStr);
			log.error(logStr, e);
			// 打印到控制台
			e.printStackTrace();
			// 不需要跳转,直接打印JSON
			// 必须重新抛出异常给系统才能跳转
			// throw e;
		}
		// complete the processing
		try {
			asyncContext.complete();
		} catch (Exception e) {
			String logStr = "Exception,complete出错";
			logStr = logFunError + logStr;
			log.error(logStr);
			log.error(logStr, e);
			// 打印到控制台
			e.printStackTrace();
			// 不需要跳转,直接打印JSON
			// 必须重新抛出异常给系统才能跳转
			// throw e;
		}
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
	public void executeInit(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws IOException, ServletException{
		String servletPath = request.getServletPath();
		// 返回页面;
		String returnPageRelative = null;
		// 返回方法的值
		String returnObject = null;
		MvcResultDto mvcResult = new MvcResultDto();
		MvcActionDto mvcAction = new MvcActionDto();
		try {
			mvcAction = MvcConfig.findInstance().findMvcAction(servletPath);
		} catch (Exception e) {
			String logStr = "Exception，找action出错,出错的url=" + servletPath;
			logStr = logFunError + logStr;
			log.error(logStr);
			log.error(logStr, e);
			e.printStackTrace();
			this.doExceptionForwardJsp_v2(request, response);
			return;
		}
		if (mvcAction == null) {
			String logStr = "运行中RuntimeException，找不到action,出错的url=" + servletPath;
			logStr = logFunError + logStr;
			log.error(logStr);
			if (false) {
				this.doExceptionForwardJsp_v2(request, response);
				return;
			}
			if (true) {
				throw new RuntimeException(logStr);
			}
		}
		Class classObj = null;
		try {
			String logStr = "调用action=" + mvcAction.getActionClass();
			log.info(logStr);
			classObj = Class.forName(mvcAction.getActionClass());
		} catch (ClassNotFoundException e) {
			String logStr = "找不到类异常，加载action类出错,出错的url=" + servletPath;
			logStr = logFunError + logStr;
			log.error(logStr);
			log.error(logStr, e);
			e.printStackTrace();
			this.doExceptionForwardJsp_v2(request, response);
			return;
		}
		MvcAction baseAction = null;
		try {
			baseAction = (MvcAction) classObj.newInstance();
		} catch (InstantiationException e) {
			String logStr = "实例化异常，实例化action出错,出错的url=" + servletPath;
			logStr = logFunError + logStr;
			log.error(logStr);
			log.error(logStr, e);
			e.printStackTrace();
			this.doExceptionForwardJsp_v2(request, response);
			return;
		} catch (IllegalAccessException e) {
			String logStr = "不合法访问异常，实例化action出错,出错的url=" + servletPath;
			logStr = logFunError + logStr;
			log.error(logStr);
			log.error(logStr, e);
			e.printStackTrace();
			this.doExceptionForwardJsp_v2(request, response);
			return;
		}
		// 初始化参数
		baseAction.setRequest(request);
		baseAction.setResponse(response);
		baseAction.setServletContext(servletContext);
		// 不要用反射,因为反射不能与业务异常bizException绑定起来
		if (true) {
			try {
				returnObject = baseAction.doExecute().getMvcResult();
			} catch (BizRuntimeException e1) {
				String logStr = "BizRuntimeException，业务出错,出错的url=" + servletPath;
				logStr = logFunError + logStr;
				log.error(logStr);
				log.error(logStr, e1);
				//
				e1.printStackTrace();
				// 写日志
				this.doLog2database_v2(logStr, servletContext, e1, servletPath);
				// 必须重新抛出异常给系统才能跳转
				throw e1;
			} catch (RuntimeException e2) {
				String logStr = "运行中RuntimeException，业务出错,出错的url=" + servletPath;
				logStr = logFunError + logStr;
				log.error(logStr);
				log.error(logStr, e2);
				// 打印到控制台
				e2.printStackTrace();
				// 写日志
				this.doLog2database_v2(logStr, servletContext, e2, servletPath);
				// 不需要跳转,直接打印JSON
				// 必须重新抛出异常给系统才能跳转
				// throw e2;
				// this.doExceptionForwardJsp_v2(request, response);
			} catch (Throwable t) {
				String logStr = "Throwable，业务出错,出错的url=" + servletPath;
				logStr = logFunError + logStr;
				log.error(logStr);
				log.error(logStr, t);
				// 打印到控制台
				t.printStackTrace();
				// 写日志
				this.doLog2database_v2(logStr, servletContext, t, servletPath);
				this.doExceptionForwardJsp_v2(request, response);
				return;
			}
		}
		if (StringUtil.isBlank(returnObject)) {
			// 返回json等等
			if (true) {
				log.trace("[" + servletPath + "]json跳转");
			}
		} else {
			// 返回result_url
			try {
				mvcResult = MvcConfig.findInstance().findMvcResult(returnObject.toString(), mvcAction.getResultList());
			} catch (Exception e) {
				String logStr = "Exception，找result出错,出错的url=" + servletPath;
				logStr = logFunError + logStr;
				log.error(logStr);
				log.error(logStr, e);
				e.printStackTrace();
				this.doExceptionForwardJsp_v2(request, response);
				return;
			}
			if (mvcResult == null) {
				String logStr = "运行中RuntimeException,找不到result,出错的url=" + servletPath;
				logStr = logFunError + logStr;
				log.error(logStr);
				log.error(logStr);
				throw new RuntimeException(logStr);
			}
			returnPageRelative = mvcResult.getUrl();
			if ("forward".equals(mvcResult.getType())) {
				// 调用RequestDispatcher.forward 方法的请求转发过程结束后，
				// 浏览器地址栏保持初始的URL地址不变;
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(returnPageRelative);
				requestDispatcher.forward(request, response);
			}
			if ("send".equals(mvcResult.getType())) {
				// 跳转;
				// response.sendRedirect跳转;
				response.sendRedirect(request.getContextPath() + returnPageRelative);
			}
		}
		if (baseAction.async) {
			// 执行异步方法
			try {
				// baseAction.asynchronous();
				ThreadPoolExecutor executorService = (ThreadPoolExecutor) request.getServletContext()
						.getAttribute(SysConfig.ThreadLocalExecutor);
				AssertUtil.isNull(executorService, "local线程池没有初始化,请检查配置thread.local.start");
				MvcAsyncByThread mvcAsyncByThread = new MvcAsyncByThread();
				mvcAsyncByThread.setAsyncContext(asyncContext);
				mvcAsyncByThread.setServletPath(servletPath);
				mvcAsyncByThread.setJsonTcpBean(LogThreadLocal.findLog());
				executorService.execute(mvcAsyncByThread);
			} catch (Exception e) {
				String logStr = "执行异步方法出错,出错的url=" + servletPath;
				logStr = logFunError + logStr;
				log.error(logStr);
				log.error(logStr, e);
				e.printStackTrace();
				// 写日志
				this.doLog2database_v2(logStr, servletContext, e, servletPath);
			}
		}
		// 对象设置为空，自动回收内存
		baseAction = null;
	}
	/**
	 * Exception异常时，返回jsp页面
	 * 
	 * @param request
	 * @param response
	 */
	public void doExceptionForwardJsp_v2(HttpServletRequest request, HttpServletResponse response) {
		log.error("ExceptionForwardJsp");
		// 判断是否已经提交，如果提交不重复则提交
		if (response.isCommitted()) {
		} else {
			// httpServletResponse.sendError(404);
			String returnPage_relative = "/pages/c/x/platform/root/common/error/500_mvc.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(returnPage_relative);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Exception异常时，返回jsp页面
	 * 
	 * @param request
	 * @param response
	 */
	public void doExceptionForwardJsp_v1(HttpServletRequest request, HttpServletResponse response) {
		log.error("ExceptionForwardJsp");
	}
	/**
	 * 日志写到数据库或elk
	 * 
	 */
	public void doLog2database_v2(String logStr, ServletContext sc, Throwable exception, String servletPath) {
		log.error(logStr);
		log.error(logStr, exception);
	}
	/**
	 * 日志写到数据库或elk
	 * 
	 */
	public void doLog2database_v1(String logStr, ServletContext sc, Throwable exception, String servletPath) {
		log.error(logStr);
		log.error(logStr, exception);
	}
}
