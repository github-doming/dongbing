package com.ibm.old.v1.common.doming.core.servlet;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.util.core.bean.BeanThreadLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.servlet.WebServletContent;
import org.doming.core.tools.ClassTool;
import org.doming.core.tools.StringTool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 异步监听器初始化
 * @Author: Dongming
 * @Date: 2019-05-16 17:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
//@WebListener
public class AsyncContextListener implements ServletContextListener {
	protected Logger log = LogManager.getLogger(this.getClass());

	@Override public void contextInitialized(ServletContextEvent servletContextEvent) {

		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}

		try {
			log.trace("初始化配置");
			WebServletContent content = WebServletContent.getInstance();
			log.trace("初始化上下文");

			String packageName = BeanThreadLocal
					.find(SysConfig.findInstance().findMap().get("annotation.mvc.packageName"), "com.ibm");
			if (StringTool.isEmpty(packageName)) {
				log.trace("解析注解未开启");
			} else {
				//解析注释
				resolveAnnotation(content, packageName);
			}

			String coreStart = BeanThreadLocal
					.find(SysConfig.findInstance().findMap().get("asyn.pool.start"), "false");
			boolean isCoreStart = Boolean.parseBoolean(coreStart);
			if (!isCoreStart) {
				log.trace("主线程池未开启");
			}else {
				//获取线程池配置
				Map<String, Map<String, Object>> executorConfig = getPoolConfig();
				//绑定线程池
				bindingExecutor(content, executorConfig);
			}

			log.info("异步监听器初始化完成");
		} catch (Exception e) {
			log.error("初始化智能投注信息错误", e);
		}

	}

	@Override public void contextDestroyed(ServletContextEvent servletContextEvent) {
		destroyedContent();
		log.info("异步监听器销毁完成");
	}

	/**
	 * 解析注解
	 *
	 * @param content     web应用初始化上下文
	 * @param packageName 注解所在包
	 */
	private void resolveAnnotation(WebServletContent content, String packageName) {
		try {
			// 1.定义一个扫包的路径，进行扫包获取全部的类
			List<Class<?>> classList = ClassTool.getClasses4Package(packageName);
			// 2.将获取到的所有类全部注入到mvc容器中，并且存放在mvcBeans集合中
			content.mappingAction(classList);
			log.trace("解析注解所在包:".concat(packageName).concat("完成"));
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			log.error("解析注解错误",e);
		}
	}


	/**
	 * 获取线程池配置
	 * @return 线程池配置
	 */
	private Map<String, Map<String, Object>> getPoolConfig() throws Exception {
		Map<String, Map<String, Object>> executorConfig = new HashMap<>(2);
		getCorePoolSize(executorConfig);
		getQueryPoolSize(executorConfig);
		log.trace("获取线程池配置完成");
		return executorConfig;
	}

	/**
	 * 获取核心线程池配置
	 *
	 * @param executorConfig 存储map
	 */
	private void getCorePoolSize(Map<String, Map<String, Object>> executorConfig) throws Exception {
		Map<String, Object> coreExecutor = new HashMap<>(4);

		String corePoolSize = BeanThreadLocal
				.find(SysConfig.findInstance().findMap().get("asynPool.core.corePoolSize"), "200");

		String maximumPoolSize = BeanThreadLocal
				.find(SysConfig.findInstance().findMap().get("asynPool.core.maximumPoolSize"), "10000");

		String queueCapacity = BeanThreadLocal
				.find(SysConfig.findInstance().findMap().get("asynPool.core.capacity"), "100000");

		String keepAliveTimeInSeconds = BeanThreadLocal
				.find(SysConfig.findInstance().findMap().get("asynPool.core.keepAliveTimeInSeconds"), "20");

		if (StringTool.isEmpty(corePoolSize) || StringTool.isEmpty(maximumPoolSize) || StringTool.isEmpty(queueCapacity)
				|| StringTool.isEmpty(keepAliveTimeInSeconds)) {
			return;
		}
		coreExecutor.put("corePoolSize", Integer.parseInt(corePoolSize));
		coreExecutor.put("maximumPoolSize", Integer.parseInt(maximumPoolSize));
		coreExecutor.put("queueCapacity", Integer.parseInt(queueCapacity));
		coreExecutor.put("keepAliveTimeInSeconds", Integer.parseInt(keepAliveTimeInSeconds));
		executorConfig.put("core", coreExecutor);
	}

	/**
	 * 获取查询线程池配置
	 *
	 * @param executorConfig 存储map
	 */
	private void getQueryPoolSize(Map<String, Map<String, Object>> executorConfig) throws Exception {
		Map<String, Object> queryExecutor = new HashMap<>(4);
		String corePoolSize = BeanThreadLocal
				.find(SysConfig.findInstance().findMap().get("asynPool.query.corePoolSize"), "10");
		String maximumPoolSize = BeanThreadLocal
				.find(SysConfig.findInstance().findMap().get("asynPool.query.maximumPoolSize"), "500");
		String queueCapacity = BeanThreadLocal
				.find(SysConfig.findInstance().findMap().get("asynPool.query.queueCapacity"), "10000");
		String keepAliveTimeInSeconds = BeanThreadLocal
				.find(SysConfig.findInstance().findMap().get("asynPool.query.keepAliveTimeInSeconds"), "40");
		if (StringTool.isEmpty(corePoolSize) || StringTool.isEmpty(maximumPoolSize) || StringTool.isEmpty(queueCapacity)
				|| StringTool.isEmpty(keepAliveTimeInSeconds)) {
			return;
		}
		queryExecutor.put("corePoolSize", Integer.parseInt(corePoolSize));
		queryExecutor.put("maximumPoolSize", Integer.parseInt(maximumPoolSize));
		queryExecutor.put("queueCapacity", Integer.parseInt(queueCapacity));
		queryExecutor.put("keepAliveTimeInSeconds", Integer.parseInt(keepAliveTimeInSeconds));
		executorConfig.put("query", queryExecutor);
	}


	/**
	 * 绑定线程池
	 *
	 * @param content        web应用初始化上下文
	 * @param executorConfig 线程池配置
	 */
	private void bindingExecutor(WebServletContent content, Map<String, Map<String, Object>> executorConfig) {
		//核心线程类
		content.startExecutor("core",executorConfig.get("core"));
		//查询线程类
		content.startExecutor("query",executorConfig.get("query"));
		//开启线程池异步异常监听
		content.openAsyncListener();
		log.trace("绑定线程池");
	}

	/**
	 * 关闭web应用内容完成
	 */
	private void destroyedContent() {
		WebServletContent.destroyed();
		log.trace("关闭web应用内容完成");
	}
}
