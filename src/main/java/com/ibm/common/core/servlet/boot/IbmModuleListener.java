package com.ibm.common.core.servlet.boot;

import all.job.service.SysQuartzTriggerService;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.job.QuartzTriggerBean;
import c.a.util.job.QuartzUtil;
import com.common.tools.CacheTool;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.client.core.cache.PlatformCache;
import com.ibm.follow.servlet.client.core.controller.ClientModuleDefine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.servlet.WebServletContent;
import org.doming.core.tools.*;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.quartz.Job;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Dongming 智能跟单服务器-获取应用服务器生命周期更改的通知事件
 * @Date: 2019-12-21 16:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@WebListener
public class IbmModuleListener implements ServletContextListener {
	private Logger log = LogManager.getLogger(this.getClass());

	private static String servletCode;
	private boolean isStart;
	private String module;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ContextUtil contextUtil = null;
		try {
			//是否启动
			Object start = SysConfig.findInstance().findMap().getOrDefault("ibm.boot.start", IbmMainConfig.START);
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
			log.debug("启动智能跟单初始化模块开始");
			//配置MQ信息
			Object mqXml = SysConfig.findInstance().findMap().getOrDefault("ibm.mq.xml", IbmMainConfig.MQ_XML);
			AssertTool.notNull(mqXml, "消息队列配置文件路径为空");
			RabbitMqTool.setMqXmlPath(mqXml.toString());
			module = SysConfig.findInstance().findMap().getOrDefault("ibm.boot.module", IbmMainConfig.MODULE).toString();

			//region 请求模块
			if (StringTool.isContains(module, IbmMainConfig.Module.REQUEST.name())) {
				log.debug("加载请求模块开始");
				//region 解析注解包

				Object poolList = SysConfig.findInstance().findMap().getOrDefault("ibm.request.pool", IbmMainConfig.REQUEST_POOL);
				AssertTool.notNull(poolList, "加载请求模块错误，请求池类型为空");
				loadRequestModule(poolList);
				log.info("加载请求模块完成");
			}
			//endregion

			//region 事件模块
			//endregion

			//region 消息队列模块
			if (StringTool.isContains(module, IbmMainConfig.Module.MQ.name())) {
				log.debug("加载消息队列模块开始");
				//配置信息
				RabbitMqTool.receive4Mq();
				log.info("加载消息队列模块完成");

			}
			//endregion

			//region 客户端模块
			if (StringTool.isContains(module, IbmMainConfig.Module.CLIENT.name())) {
				log.debug("加载客户端模块开始");
				loadClientModule();
				log.info("加载客户端模块完成");

			}
			//endregion

			//region 虚拟客户端模块
			if (StringTool.isContains(module, IbmMainConfig.Module.VR.name())) {
				log.debug("加载虚拟客户端模块开始");
				setModule(IbmMainConfig.Module.CLIENT.name());
				loadVrClientModule();
				log.info("加载虚拟客户端模块完成");
			}
			//endregion

			//region 服务端模块
			if (StringTool.isContains(module, IbmMainConfig.Module.SERVER.name())) {
				if (!StringTool.isContains(module, IbmMainConfig.Module.REQUEST.name())) {
					log.debug("加载服务端模块开始");
					loadServerModule();
					log.info("加载服务端模块完成");
				}
				log.debug("加载事件模块开始");
				//取消用线程池的方式控制,通过用quertz
				loadEventModule();
				log.info("加载事件模块完成");
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


	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			if (isStart) {
				if (StringTool.isContains(module, IbmMainConfig.Module.REQUEST.name())) {
					log.debug("销毁请求模块开始");
					WebServletContent.destroyed();
					log.info("销毁请求模块完成");
				}
				if (StringTool.isContains(module, IbmMainConfig.Module.MQ.name())) {
					log.debug("销毁消息队列模块开始");
					RabbitMqTool.destroy4Mq();
					log.info("销毁消息队列模块完成");
				}
				if (StringTool.isContains(module, IbmMainConfig.Module.CLIENT.name()) && StringTool.notEmpty(servletCode)) {
					log.debug("销毁客户端模块开始");
					destroyClientModule();
					log.info("销毁客户端模块完成");
				}
				if (StringTool.isContains(module, IbmMainConfig.Module.VR.name()) && StringTool.notEmpty(servletCode)) {
					log.debug("销毁虚拟客户端模块开始");
					destroyVrClientModule();
					log.info("销毁虚拟客户端模块完成");
				}
				if (StringTool.isContains(module, IbmMainConfig.Module.SERVER.name())) {
					log.debug("销毁服务端模块开始");
					QuartzUtil.destroy();
					log.info("销毁服务端模块完成");
				}
			}
		} catch (Exception e) {
			log.error("注销智能投注模块服务器错误", e);
		}

	}

	//region 加载模块

	/**
	 * 开启请求模块
	 * <p>1.解析所有的路径</p>
	 */
	private void loadRequestModule(Object poolList) throws Exception {
		AssertTool.isFalse(WebServletContent.hasInstance(), "加载请求模块错误，请求正文");
		Object packageName = SysConfig.findInstance().findMap()
				.getOrDefault("ibm.request.package", IbmMainConfig.REQUEST_PACKAGE);
		AssertTool.notNull(packageName, "加载请求模块错误，解析包为空");
		WebServletContent content = WebServletContent.getInstance();
		log.debug("解析指定包《{}}》内注解", packageName);
		//解析注释
		content.resolveAnnotation(packageName.toString());
		log.debug("解析注解包:{},完成", packageName);
		//endregion

		//region 绑定线程池

		log.debug("绑定线程池:{}", poolList);
		//获取线程池配置
		String[] pools = poolList.toString().split(",");
		Map<String, Map<String, Object>> executorConfig = new HashMap<>(pools.length * 3 / 4 + 1);
		for (String pool : pools) {
			//获取线程池配置
			Map<String, Object> config = new HashMap<>(4);
			Object poolConfig = SysConfig.findInstance().findMap()
					.getOrDefault(String.format("ibm.%s.poolConfig", pool), IbmMainConfig.getPoolConfig(pool));
			if (StringTool.isEmpty(poolConfig)) {
				throw new IllegalArgumentException("线程池《".concat(pool).concat("》配置信息不存在"));
			}
			String[] poolConfigs = poolConfig.toString().split(",");
			if (ContainerTool.isEmpty(poolConfigs) || poolConfigs.length != 4) {
				throw new IllegalArgumentException("线程池《".concat(pool).concat("》错误的线程池配置"));
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
	 * 启动事件模块
	 */
	private void loadEventModule() throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		boolean isSchedulerStart = quartzUtil.doSchedulerIsStart();
		boolean isInStandbyMode = quartzUtil.doSchedulerIsInStandbyMode();
		if (!isSchedulerStart && isInStandbyMode) {
			//启动job
			quartzUtil.doSchedulerStart();
		}
		CurrentTransaction.transactionBegin();
		try {
			SysQuartzTriggerService service = new SysQuartzTriggerService();
			List<Map<String, Object>> list = service.findAll();
			for (Map<String, Object> map : list) {
				String className = map.get("JOB_CLASS_NAME_").toString();
				Class classObject = Class.forName(className);
				boolean isAssignable = Job.class.isAssignableFrom(classObject);
				if (!isAssignable) {
					AssertUtil.isBlank(null, "JOB必须实现JOB接口!");
				}
				QuartzTriggerBean bean = new QuartzTriggerBean();
				bean.setTriggerId(map.get("TRIGGER_ID_").toString());
				bean.setJobClassName(className);
				bean.setJobDescription("JobDescription_" + bean.getTriggerId());
				bean.setTriggerDescription("TriggerDescription_" + bean.getTriggerId());
				// 定时触发
				bean.setCronExpression(map.get("CRON_EXPRESSION_").toString());
				//创建定时定时任务
				quartzUtil.createTriggerCron("", bean);
				quartzUtil.doTriggerResume(bean.getTriggerId());
			}
		} finally {
			CurrentTransaction.transactionEnd();
		}
	}

	/**
	 * 开启虚拟客户端模块
	 * <p>1.注册客户端编码</p>
	 * <p>2.启动消息队列</p>
	 * <p>3.启动定时器</p>
	 */
	private void loadVrClientModule() throws Exception {
		CurrentTransaction.transactionBegin();
		try {
			String ip = IpTool.getIpAddress();
			AssertTool.notNull(ip, "加载虚拟客户端模块错误，获取到的IP为空");
			Object code = SysConfig.findInstance().findMap().getOrDefault("ibm.client.code", "9099");
			AssertTool.notNull(code, "加载虚拟客户端模块错误，获取到的CODE为空");
			String clientCode = ip.replace(".", "_").concat("_").concat(code.toString());

			//注册客户机
			ClientModuleDefine servletController = new ClientModuleDefine();
			boolean result = servletController.registerVr(clientCode);
			AssertTool.isTrue(result, "加载客户端模块错误，注册客户机失败");
			setServletCode(clientCode);

			//开启定时器
			QuartzTool.startVrClient(clientCode);
			log.debug("客户端,定时器开启");

			// 客户端信息监听服务
			RabbitMqTool.receive4Client(servletCode);
			log.debug("客户端,消息队列开启");
		} finally {
			CurrentTransaction.transactionEnd();
		}


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
			String ip = IpTool.getIpAddress();
			AssertTool.notNull(ip, "加载客户端模块错误，获取到的IP为空");
			Object code = SysConfig.findInstance().findMap().getOrDefault("ibm.client.code", IbmMainConfig.CLIENT_CODE);
			AssertTool.notNull(code, "加载客户端模块错误，获取到的CODE为空");
			String clientCode = ip.replace(".", "_").concat("_").concat(code.toString());

			//注册客户机
			ClientModuleDefine servletController = new ClientModuleDefine();
			boolean result = servletController.register(clientCode);
			AssertTool.isTrue(result, "加载客户端模块错误，注册客户机失败");
			setServletCode(clientCode);

			//加载数据
			servletController.reload();
			log.debug("客户端,加载数据完成");

			//开启定时器
			QuartzTool.startClient(clientCode);
			log.debug("客户端,定时器开启");

			// 客户端信息监听服务
			RabbitMqTool.receive4Client(servletCode);
			log.debug("客户端,消息队列开启");
		} finally {
			CurrentTransaction.transactionEnd();
		}
	}

	/**
	 * 开启服务端模块
	 * <p>1.添加后台请求的路径解析</p>
	 */
	private void loadServerModule() throws Exception {
		//加载请求
		Object poolList = SysConfig.findInstance().findMap().getOrDefault("ibm.server.pool", IbmMainConfig.SERVER_POOL);
		AssertTool.notNull(poolList, "加载请求模块错误，请求池类型为空");
		loadRequestModule(poolList);

	}
	//endregion

	private void destroyClientModule() throws Exception {
		CurrentTransaction.transactionBegin();
		try {
			ClientModuleDefine servletController = new ClientModuleDefine();

			Object isTest = SysConfig.findInstance().findMap().getOrDefault("ibm.client.isTest", IbmMainConfig.CLIENT_TEST);
			boolean flag = Boolean.parseBoolean(isTest.toString());
			if (flag) {
				flag = servletController.migrate(servletCode);
				//迁移数据
				servletController.migrateData(servletCode);

				servletController.destroy();

				QuartzTool.destroyClient();


			} else {
				if (PlatformCache.platformState().equal(IbmStateEnum.CANCEL.name())) {
					flag = servletController.cancelled(servletCode);
					servletController.destroy();
					QuartzTool.destroyClient();
				} else if (PlatformCache.platformState().equal(IbmStateEnum.STOP.name())) {
					flag = servletController.stop(servletCode);
					QuartzTool.shutdownClient();
				}
			}

			AssertTool.isTrue(flag, "销毁客户端模块错误，注销客户机失败");
			HttpClientUtil.destroy();
			RabbitMqTool.destroy4Client(servletCode);
		} finally {
			CurrentTransaction.transactionEnd();
		}

	}

	private void destroyVrClientModule() throws Exception {
		CurrentTransaction.transactionBegin();
		try {
			ClientModuleDefine servletController = new ClientModuleDefine();

			boolean flag = servletController.stop(servletCode);
			servletController.destroy();
			QuartzTool.shutdownClient();

			AssertTool.isTrue(flag, "销毁虚拟客户端模块错误，注销客户机失败");
			HttpClientUtil.destroy();
			RabbitMqTool.destroy4Client(servletCode);
		} finally {
			CurrentTransaction.transactionEnd();
		}

	}


	/**
	 * 设置 服务CODE
	 *
	 * @param servletCode 服务CODE
	 */
	private static void setServletCode(String servletCode) {
		IbmModuleListener.servletCode = servletCode;
	}

	public static String servletCode() {
		return IbmModuleListener.servletCode;
	}

	public void setModule(String module) {
		CacheTool.setModeInfo(module);
	}
}
