package com.cloud.common.core.boot;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import com.cloud.common.core.DefaultConfig;
import com.cloud.common.tool.RabbitMqTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.servlet.WebServletContent;
import org.doming.core.tools.AssertTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 初始化云服务信息
 * @Author: Dongming
 * @Date: 2019-01-25 14:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@WebServlet(name = "CloudBootInitServlet", loadOnStartup = 10, urlPatterns = {}) public class CloudBootInitServlet
		extends HttpServlet {
	protected Logger log = LogManager.getLogger(this.getClass());
	private boolean isStart = false;
	private Map<String, Object> sysMap;

	@Override public void init() throws ServletException {
		super.init();
		ContextUtil contextUtil = null;
		try {
			String configPath = SysConfig.findInstance().findMap()
					.getOrDefault("cloud.rabbitmq.xml", DefaultConfig.MQ_XML).toString();
			RabbitMqTool.setMqXmlPath(configPath);

			String start = SysConfig.findInstance().findMap()
					.getOrDefault("cloud.boot.start", DefaultConfig.CLOUD_START).toString();
			isStart = Boolean.parseBoolean(start);
			if (!isStart) {
				return;
			}
			contextUtil = ContextThreadLocal.findThreadLocal().get();
			if (contextUtil == null) {
				contextUtil = new ContextUtil();
				contextUtil.init();
				ContextThreadLocal.findThreadLocal().set(contextUtil);
			}

			//获取系统配置文件Map
			sysMap = SysConfig.findInstance().findMap();
			String modules = sysMap.getOrDefault("cloud.boot.module", DefaultConfig.MODULE).toString();
			if (modules.contains("lottery")) {
				// 初始化运行模块
				RabbitMqTool.receive4Lottery(configPath);
			}
			if (modules.contains("connector")) {
				log.trace("加载请求模块开始");
				//region 解析注解包
				loadConnectorModule();
				log.info("加载请求模块完成");
			}

			log.info("初始化云服务信息完成");

		} catch (Exception e) {
			e.printStackTrace();
			log.error("初始化云服务信息错误", e);
		} finally {
			if (contextUtil != null) {
				contextUtil.remove();
				ContextThreadLocal.findThreadLocal().remove();
			}
		}

	}
	private void loadConnectorModule() throws IOException, ClassNotFoundException {
		AssertTool.isFalse(WebServletContent.hasInstance(), "加载请求模块错误，请求正文已存在，无法再次加载");
		WebServletContent content = WebServletContent.getInstance();

		// region 解析注释
		Object packageName = sysMap.getOrDefault("cloud.connector.package", DefaultConfig.CONNECTOR_PACKAGE);
		AssertTool.notNull(packageName, "加载请求模块错误，解析包为空");
		log.trace("解析指定包《{}}》内注解", packageName);
		content.resolveAnnotation(packageName.toString());
		log.trace("解析注解包:{},完成", packageName);
		//endregion

		//region 绑定线程池
		//获取线程池配置
		Map<String, Map<String, Object>> executorConfig = new HashMap<>(1);

		//获取线程池配置
		Map<String, Object> config = new HashMap<>(4);
		Object poolConfig = sysMap
				.getOrDefault("cloud.connector.poolConfig", DefaultConfig.POOL_CONFIG);
		AssertTool.notEmpty(poolConfig, "线程池配置信息不存在");
		String[] poolConfigs = poolConfig.toString().split(",");
		if (ContainerTool.isEmpty(poolConfigs) || poolConfigs.length != 4) {
			throw new IllegalArgumentException(String.format("线程池错误的线程池配置:{%s}", poolConfig));
		}
		config.put("corePoolSize", NumberTool.getInteger(poolConfigs[0]));
		config.put("maximumPoolSize", NumberTool.getInteger(poolConfigs[1]));
		config.put("queueCapacity", NumberTool.getInteger(poolConfigs[2]));
		config.put("keepAliveTimeInSeconds", NumberTool.getInteger(poolConfigs[3]));

		executorConfig.put("core", config);
		//绑定
		content.bindingExecutor(executorConfig);
		log.debug("绑定线程池完成");
		//endregion

		//开启线程池异步异常监听
		content.openAsyncListener();
		log.debug("开启线程池异步异常监听");

	}

	@Override public void destroy() {
		super.destroy();
		if (isStart) {
			RabbitMqTool.destroy();

			log.info("销毁云服务信息完成");
		}

	}

}
