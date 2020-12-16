package com.ibs.plan.common.core.servlet.boot;

import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.util.job.QuartzUtil;
import com.common.tools.CacheTool;
import com.ibs.plan.common.core.configs.PlanMainConfig;
import com.ibs.plan.common.tools.QuartzTool;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.client.core.controller.ClientModuleController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.servlet.WebServletContent;
import org.doming.core.tools.*;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * 智能投注服务器-获取应用服务器生命周期更改的通知事件
 *
 * @Author: null
 * @Date: 2020-05-09 09:58
 * @Version: v1.0
 */

@WebListener public class IbsModuleListener implements ServletContextListener {
	private boolean isStart;
	private static String module;
	private static String servletCode;
	private Map<String, Object> sysMap;

	private Logger log = LogManager.getLogger(this.getClass());

	@Override public void contextInitialized(ServletContextEvent sce) {
		ContextUtil contextUtil = null;
		try {
			//是否启动
			Object start = SysConfig.findInstance().findMap().getOrDefault("ibs.boot.start", PlanMainConfig.START);
			isStart = Boolean.parseBoolean(start.toString());
			if (!isStart) {
				return;
			}
			//获取上下文工具类
			contextUtil = ContextThreadLocal.findThreadLocal().get();
			if (contextUtil == null) {
				contextUtil = new ContextUtil();
				contextUtil.init();
				ContextThreadLocal.findThreadLocal().set(contextUtil);
			}
			//获取系统配置文件Map
			sysMap = SysConfig.findInstance().findMap();
			log.debug("启动智能投注初始化模块开始");
			//配置MQ配置文件路径
			Object mqXml = SysConfig.findInstance().findMap().getOrDefault("ibs.boot.xml", PlanMainConfig.MQ_XML);
			AssertTool.notNull(mqXml, "消息队列配置文件路径为空");
			RabbitMqTool.setMqXmlPath(mqXml.toString());

			setModule(sysMap.getOrDefault("ibs.boot.module", PlanMainConfig.MODULE).toString());
			//region 请求模块
			if (StringTool.isContains(module, PlanMainConfig.Module.REQUEST.name())) {
				log.trace("加载请求模块开始");
				//region 解析注解包

				Object requestPool = sysMap.getOrDefault("ibs.request.pool", PlanMainConfig.REQUEST_POOL);
				AssertTool.notNull(requestPool, "加载请求模块错误，请求池类型为空");
				loadRequestModule(requestPool);
				log.info("加载请求模块完成");

			}
			//endregion

			//region 消息队列模块
			if (StringTool.isContains(module, PlanMainConfig.Module.MQ.name())) {
				log.trace("加载消息队列模块开始");
				//配置信息
				RabbitMqTool.receive4Mq();
				log.info("加载消息队列模块完成");

			}
			//endregion

			//region 客户端模块
			if (StringTool.isContains(module, PlanMainConfig.Module.CLIENT.name())) {
				log.trace("加载客户端模块开始");
				loadClientModule();
				log.info("加载客户端模块完成");

			}
			//endregion

			//region 服务端模块
			if (StringTool.isContains(module, PlanMainConfig.Module.SERVER.name())) {
				log.trace("加载服务端模块开始");
				//开启后台定时器
				QuartzUtil.findInstance().start();
				log.trace("后台定时器开启");
				log.info("加载服务端模块完成");

			}
			//endregion

		} catch (Exception e) {
			log.error("初始化智能投注模块服务器错误", e);
		} finally {
			if (contextUtil != null) {
				contextUtil.remove();
				ContextThreadLocal.findThreadLocal().remove();
			}
		}
	}

	@Override public void contextDestroyed(ServletContextEvent sce) {
		try {
			if (isStart) {
				if (StringTool.isContains(module, PlanMainConfig.Module.REQUEST.name())) {
					log.trace("销毁请求模块开始");
					WebServletContent.destroyed();
					log.info("销毁请求模块完成");
				}
				if (StringTool.isContains(module, PlanMainConfig.Module.MQ.name())) {
					log.trace("销毁消息队列模块开始");
					RabbitMqTool.destroy4Mq();
					log.info("销毁消息队列模块完成");
				}
				if (StringTool.isContains(module, PlanMainConfig.Module.CLIENT.name()) && StringTool
						.notEmpty(servletCode)) {
					log.trace("销毁客户端模块开始");
					destroyClientModule();
					log.info("销毁客户端模块完成");
				}
				if (StringTool.isContains(module, PlanMainConfig.Module.SERVER.name())) {
					log.trace("销毁服务端模块开始");
					QuartzUtil.destroy();
					log.info("销毁服务端模块完成");
				}
//				ThreadExecuteUtil.destroy();
				RabbitMqUtil.destroy();
			}
		} catch (Exception e) {
			log.error("注销智能投注模块服务器错误", e);
		}
	}

	/**
	 * 开启请求模块
	 * <p>1.解析所有的路径</p>
	 * <p>2.绑定线程池</p>
	 * <p>3.开启线程池异步异常监听</p>
	 *
	 * @param requestPool 请求模块池
	 */
	private void loadRequestModule(Object requestPool) throws IOException, ClassNotFoundException {
		AssertTool.isFalse(WebServletContent.hasInstance(), "加载请求模块错误，请求正文已存在，无法再次加载");
		WebServletContent content = WebServletContent.getInstance();

		// region 解析注释
		Object packageName = sysMap.getOrDefault("ibs.request.package", PlanMainConfig.REQUEST_PACKAGE);
		AssertTool.notNull(packageName, "加载请求模块错误，解析包为空");
		log.trace("解析指定包《{}}》内注解", packageName);
		content.resolveAnnotation(packageName.toString());
		log.trace("解析注解包:{},完成", packageName);
		//endregion

		//region 绑定线程池
		log.trace("绑定线程池:{}", requestPool);
		//获取线程池配置
		String[] pools = requestPool.toString().split(",");
		Map<String, Map<String, Object>> executorConfig = new HashMap<>(pools.length * 3 / 4 + 1);
		for (String pool : pools) {
			//获取线程池配置
			Map<String, Object> config = new HashMap<>(4);
			Object poolConfig = sysMap
					.getOrDefault(String.format("ibs.%s.poolConfig", pool), PlanMainConfig.getPoolConfig(sysMap, pool));
			AssertTool.notEmpty(poolConfig, "线程池《%s》配置信息不存在", pool);
			String[] poolConfigs = poolConfig.toString().split(",");
			if (ContainerTool.isEmpty(poolConfigs) || poolConfigs.length != 4) {
				throw new IllegalArgumentException(String.format("线程池《%s》错误的线程池配置:{%s}", pool, poolConfig));
			}
			config.put("corePoolSize", NumberTool.getInteger(poolConfigs[0]));
			config.put("maximumPoolSize", NumberTool.getInteger(poolConfigs[1]));
			config.put("queueCapacity", NumberTool.getInteger(poolConfigs[2]));
			config.put("keepAliveTimeInSeconds", NumberTool.getInteger(poolConfigs[3]));

			executorConfig.put(pool, config);
		}
		//绑定
		content.bindingExecutor(executorConfig);
		log.debug("绑定线程池完成");
		//endregion

		//开启线程池异步异常监听
		content.openAsyncListener();
		log.debug("开启线程池异步异常监听");

	}
	/**
	 * 开启客户端模块
	 * <p>1.注册客户端编码</p>
	 * <p>2.启动消息队列</p>
	 * <p>3.启动定时器</p>
	 */
	private void loadClientModule() throws Exception {
		CurrentTransaction.transactionBegin();
		try {
			String ip = IpTool.getIpExtranet();
			AssertTool.notNull(ip, "加载客户端模块错误，获取到的IP为空");
			Object code = sysMap.getOrDefault("ibs.client.code", PlanMainConfig.CLIENT_CODE);
			AssertTool.notNull(code, "加载客户端模块错误，获取到的CODE为空");
			String clientCode = ip.replace(".", "_").concat("_").concat(code.toString());

			//客户端类型
			Object clientType = sysMap.getOrDefault("ibs.client.type", PlanMainConfig.CLIENT_TYPE);
			//注册客户机
			ClientModuleController servletController = new ClientModuleController();
			boolean result = servletController.register(clientCode,clientType);
			AssertTool.isTrue(result, "加载客户端模块错误，注册客户机失败");
			servletCode = clientCode;
			log.trace("注册客户机完成");

			//加载数据
			servletController.reload();
			log.trace("加载数据完成");

			//开启定时器
			QuartzTool.startClient(clientCode);
			log.trace("定时器开启");

			// 客户端信息监听服务
			RabbitMqTool.receive4Client(clientCode);
			log.trace("消息队列开启");
		} finally {
			CurrentTransaction.transactionEnd();
		}
	}

	/**
	 * 销毁客户机模块
	 */
	private void destroyClientModule() throws Exception {
		CurrentTransaction.transactionBegin();
		try {
			ClientModuleController servletController = new ClientModuleController();
			boolean flag = servletController.stop(servletCode);
			AssertTool.isTrue(flag, "销毁客户端模块错误，停止客户机失败");
			QuartzTool.shutdownClient();
			HttpClientUtil.destroy();
			RabbitMqTool.destroy4Client(servletCode);
		} finally {
			CurrentTransaction.transactionEnd();
		}
	}

	public static String servletCode() {
		return servletCode;
	}

	public static String getModule() {
		return IbsModuleListener.module;
	}
	public void setModule(String module) {
		IbsModuleListener.module = module;
		CacheTool.setModeInfo(module);
	}
}
