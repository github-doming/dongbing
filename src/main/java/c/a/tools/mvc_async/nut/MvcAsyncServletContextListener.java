package c.a.tools.mvc_async.nut;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.PropertyConfigurator;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.log.LogConfig;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import c.a.util.job.QuartzUtil;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 
 * 在监听中初始化线程池
 */
@WebListener
public class MvcAsyncServletContextListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		if (false) {
			// create the thread pool
			ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 5000, 50000L, TimeUnit.MILLISECONDS,
					new ArrayBlockingQueue<Runnable>(1000));
			servletContextEvent.getServletContext().setAttribute("executor", threadPoolExecutor);
		}
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		try {
			String threadMvcStart = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("thread.mvc.start"),
					"true");
			if ("true".equals(threadMvcStart)) {
				ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance()
						.findMvc();
				ExecutorService executorService = threadExecutorService.findExecutorService();
				servletContextEvent.getServletContext().setAttribute(SysConfig.ThreadMvcExecutor, executorService);
			}
			String threadLocalStart = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("thread.local.start"),
					"true");
			if ("true".equals(threadLocalStart)) {
				ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance()
						.findLocal();
				ExecutorService executorService = threadExecutorService.findExecutorService();
				servletContextEvent.getServletContext().setAttribute(SysConfig.ThreadLocalExecutor, executorService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
		}
	}
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		if (false) {
			ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) servletContextEvent.getServletContext()
					.getAttribute("executor");
			threadPoolExecutor.shutdown();
		}
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		try {
			String threadMvcStart = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("thread.mvc.start"),
					"true");
			if ("true".equals(threadMvcStart)) {
				ExecutorService threadPoolExecutor = (ExecutorService) servletContextEvent.getServletContext()
						.getAttribute(SysConfig.ThreadMvcExecutor);
				if(threadPoolExecutor!=null){
					threadPoolExecutor.shutdown();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
		}
	}
}