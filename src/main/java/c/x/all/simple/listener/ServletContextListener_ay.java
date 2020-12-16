package c.x.all.simple.listener;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import c.a.tools.log.custom.common.BaseLog;

import c.x.platform.root.database.config.JdbcConfig;
import c.x.platform.root.database.parameter.JdbcParameter;
import c.a.util.core.log.LogConfig;
import c.a.util.core.path.PathUtil;

public class ServletContextListener_ay implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		BaseLog.trace("监听器ServletContextListener 销毁contextDestroyed");

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			BaseLog.trace("监听器ServletContextListener 初始化contextInitialized");
			// 1;日志;

			try {
				PathUtil pathUtil = new PathUtil();
				String path = pathUtil.findPath(LogConfig.url);

				// System.out.println("path=" + path);

				// System.setProperty("webapp.root", "d:\\");
				System.setProperty("webapp.root", sce.getServletContext()
						.getRealPath("/"));

				PropertyConfigurator
						.configure(pathUtil.findPath(LogConfig.url));
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}

			// 2;数据库;
			// 数据库设置
			if (false) {
				JdbcParameter.database = JdbcConfig.database_mysql;

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
