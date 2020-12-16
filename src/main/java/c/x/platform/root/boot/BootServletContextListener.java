package c.x.platform.root.boot;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.log.LogUtil;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import c.a.util.job.QuartzUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
public class BootServletContextListener implements ServletContextListener {
	private org.apache.commons.logging.Log logCommon = org.apache.commons.logging.LogFactory.getLog(this.getClass());
	private org.apache.log4j.Logger log1 = org.apache.log4j.Logger.getLogger(BootServletContextListener.class);
	private org.apache.logging.log4j.Logger log2 = org.apache.logging.log4j.LogManager.getLogger(this.getClass());

	private org.apache.log4j.Logger thirdbankLog1 = org.apache.log4j.Logger.getLogger("thirdbank_all");
	private org.apache.logging.log4j.Logger thirdbankLog = org.apache.logging.log4j.LogManager
			.getLogger("thirdbank_all");
	@Override public void contextDestroyed(ServletContextEvent sce) {
		log2.trace("监听器ServletContextListener 销毁contextDestroyed");
		// 定时器销毁,队列销毁
		QuartzUtil.destroy();
		ThreadPoolExecutorServiceListUtil.destroy();
	}
	@Override public void contextInitialized(ServletContextEvent servletContextEvent) {
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		try {
			System.out.println("监听器BootServletContextListener 初始化开始");
			String servletContextPath = servletContextEvent.getServletContext().getRealPath("/");
			SysConfig.servletContextPath = servletContextPath;
			String webappRoot = servletContextPath + "..\\";
			// MvcHttpServlet.ConfigMvcXml = "/config/mvc/mvc.xml";
			// MvcHttpServlet.configMvcProperties =
			// "/config/mvc/mvc.properties";
			SysConfig.url = "/config/platform/core.properties";
			// log4j日志
			LogUtil.findInstance().init(servletContextPath);
			log1.info("1 监听器BootServletContextListener 初始化结束 ");
			log2.info("2 监听器BootServletContextListener 初始化结束 ");
			System.out.println("监听器BootServletContextListener 初始化结束 ");
			String quartzStart = BeanThreadLocal
					.find(SysConfig.findInstance().findMap().get("quartz.scheduler.start"), "false");
			// log2.trace("quartzStart="+quartzStart+",class="+this.getClass().getName());
			if ("true".equals(quartzStart)) {
				// 启动定时器
				QuartzUtil.findInstance().doSchedulerStart();
			}
			log1.trace("1 servletContextPath=" + servletContextPath);
			log2.trace("2 servletContextPath=" + servletContextPath);
			thirdbankLog.trace("t class=" + this.getClass().getName());
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
