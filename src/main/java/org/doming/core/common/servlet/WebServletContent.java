package org.doming.core.common.servlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.*;
import org.doming.develop.http.HttpConfig;

import javax.servlet.AsyncListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: web 应用内容
 * @Author: Dongming
 * @Date: 2019-05-17 16:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class WebServletContent extends ThreadTool {

	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * mvc 容器对象 key:类名id ,value 对象clazz
	 */
	private ConcurrentHashMap<String, Class<?>> mvcBeans = new ConcurrentHashMap<>();

	/**
	 * mvc 容器对象 key:请求地址 ,value 对象名称
	 */
	private ConcurrentHashMap<String, String> nameBeans = new ConcurrentHashMap<>();
	/**
	 * mvc 容器对象 key:请求地址 ,value 请求类型
	 */
	private ConcurrentHashMap<String, HttpConfig.Method[]> methodsBeans = new ConcurrentHashMap<>();
	/**
	 * mvc 容器对象 key:请求地址 ,value 请求类型
	 */
	private ConcurrentHashMap<String, HttpConfig.Code> codeBeans = new ConcurrentHashMap<>();

	/**
	 * 线程池
	 */
	private Map<String, ThreadPoolExecutor> executorMap = new ConcurrentHashMap<>(3);

	/**
	 * 异步操作异常监听
	 */
	private AsyncListener asyncListener;
	/**
	 * 异步请求超时
	 */
	private long asyncTimeOut = 5000 * 1000;

	private WebServletContent() {
	}
	private static volatile WebServletContent instance;

	/**
	 * 获取实例对象
	 *
	 * @return 实例对象
	 */
	public static WebServletContent getInstance() {
		if (instance == null) {
			synchronized (WebServletContent.class) {
				if (instance == null) {
					instance = new WebServletContent();
				}
			}
		}
		return instance;
	}
	/**
	 * 销毁
	 */
	public static void destroyed() {
		if (instance == null) {
			return;
		}
		if (ContainerTool.notEmpty(instance.executorMap)) {
			for (ThreadPoolExecutor executor : instance.executorMap.values()) {
				try {
					close(executor);
				} catch (InterruptedException e) {
					instance.log.error("关闭线程池错误", e);
				}
			}
		}
		instance.methodsBeans = null;
		instance.nameBeans = null;
		instance.methodsBeans = null;
		instance.asyncListener = null;
		instance = null;
	}

	public static boolean hasInstance() {
		return instance != null;
	}

	//TODO 解析包

	// 解析包
	/**
	 * 解析注解
	 *
	 * @param packageName 注解所在包
	 */
	public void resolveAnnotation(String packageName) throws IOException, ClassNotFoundException {
		// 1.定义一个扫包的路径，进行扫包获取全部的类
		List<Class<?>> classList = ClassTool.getClasses4Package(packageName);
		// 2.将获取到的所有类全部注入到mvc容器中，并且存放在mvcBeans集合中
		mappingAction(classList);
	}

	/**
	 * 将获取到的所有类全部注入到mvc容器中，并且存放在Bean集合中
	 *
	 * @param classList 获取到的所有类
	 */
	public void mappingAction(List<Class<?>> classList) {
		for (Class<?> clazz : classList) {
			ActionMapping annotation = clazz.getDeclaredAnnotation(ActionMapping.class);
			AsynAction asynAction= clazz.getAnnotation(AsynAction.class);
			if (annotation != null) {
				if (ContainerTool.notEmpty(annotation.value())) {
					//对象名称
					String simpleName;
					if (StringTool.notEmpty(annotation.name())) {
						simpleName = annotation.name();
					} else {
						//将类名称首字母转换为小写
						simpleName = StringTool.toLowerCaseFirstOne(clazz.getSimpleName());
					}
					//请求类型
					HttpConfig.Method[] methods = annotation.method();
					for (String uri : annotation.value()) {
						mvcBeans.put(uri, clazz);
						nameBeans.put(uri, simpleName);
						methodsBeans.put(uri, methods);
						if (asynAction != null){
							codeBeans.put(uri, asynAction.code());
						}

					}
				}
			}
		}
	}
	// 解析包

	//TODO 线程池

	// 线程池
	/**
	 * 绑定线程池
	 *
	 * @param executorConfig 线程池配置
	 */
	public void bindingExecutor(Map<String, Map<String, Object>> executorConfig) {
		for (Map.Entry<String, Map<String, Object>> entry : executorConfig.entrySet()) {
			startExecutor(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 开始执行器
	 *
	 * @param executorCode   执行code
	 * @param executorConfig 执行配置信息
	 * @return 执行器
	 */
	public ThreadPoolExecutor startExecutor(String executorCode, Map<String, Object> executorConfig) {
		//初始线程池大小
		int corePoolSize = NumberTool.getInteger(executorConfig.get("corePoolSize"));
		//最大线程池大小
		int maximumPoolSize = NumberTool.getInteger(executorConfig.get("maximumPoolSize"));
		//队列大小
		int queueCapacity = NumberTool.getInteger(executorConfig.get("queueCapacity"));
		//线程维持时间
		int keepAliveTimeInSeconds = NumberTool.getInteger(executorConfig.get("keepAliveTimeInSeconds"));

		ThreadPoolExecutor executor = createExecutor(executorCode, corePoolSize, maximumPoolSize, queueCapacity,
				keepAliveTimeInSeconds);
		executorMap.put(executorCode, executor);
		return executor;
	}
	// 线程池

	//TODO 异步操作异常监听

	// 异步操作异常监听
	/**
	 * 开启异步操作异常监听类
	 */
	public synchronized void openAsyncListener() {
		if (asyncListener == null) {
			asyncListener = new AsyncErrorListener();
		}
	}

	public AsyncListener getAsyncListener() {
		if (instance == null) {
			return null;
		}
		return instance.asyncListener;
	}
	// 异步操作异常监听

	/**
	 * 获取线程池
	 *
	 * @param code 类型
	 * @return 线程池
	 */
	public synchronized ThreadPoolExecutor getExecutorPool(String code) {
		return executorMap.get(code);
	}

	/**
	 * 设置线程池配置
	 *
	 * @param code                 线程池类型
	 * @param corePoolSize         核心大小
	 * @param maximumPoolSize      最大容量
	 * @param keepAliveTimeSeconds 空闲时间
	 * @return 设置结果
	 */
	public static boolean setConfig(String code, Integer corePoolSize, Integer maximumPoolSize,
			Integer keepAliveTimeSeconds) {
		synchronized (code) {
			if (instance == null) {
				return false;
			}
			ThreadPoolExecutor executor = instance.getExecutorPool(code);
			if (executor == null) {
				return false;
			}
			if (!setCorePoolSize(executor, corePoolSize)) {
				throw new RuntimeException("设置线程池核心大小失败,设置值小于0");
			}
			if (!setMaximumPoolSize(executor, maximumPoolSize)) {
				throw new RuntimeException("设置线程池最大容量失败,设置值小于0或小于核心大小");
			}
			if (!setKeepAliveTimeSeconds(executor, keepAliveTimeSeconds)) {
				throw new RuntimeException("设置线程空闲时间失败,设置值小于0");
			}
			return true;
		}
	}

	/**
	 * 获取线程池配置
	 *
	 * @param code 线程池类型
	 * @return 线程池配置
	 */
	public static Map<String, Object> getConfig(String code) {
		Map<String, Object> config = new HashMap<>(7);
		config.put("corePoolSize", 0);
		config.put("poolSize", 0);
		config.put("activeCount", 0);
		config.put("keepAliveTimeSeconds", 0);
		config.put("maxSize", 0);
		config.put("taskCount", 0);
		config.put("completedTaskCount", 0);
		if (instance == null) {
			return config;
		}
		ThreadPoolExecutor executor = instance.getExecutorPool(code);
		if (executor == null) {
			return config;
		}
		return getConfig(executor);
	}

	/**
	 * 获取mvc的请求路径的 执行Class
	 *
	 * @param uri 访问路径
	 * @return 执行Class
	 */
	public Class<?> getClazz(String uri) {
		if (instance == null) {
			return null;
		}
		if (ContainerTool.isEmpty(instance.mvcBeans)) {
			return null;
		}
		return instance.mvcBeans.get(uri);
	}

	/**
	 * 获取mvc的请求路径的 执行Class 名称
	 *
	 * @param uri 访问路径
	 * @return 执行Class名称
	 */
	String getName(String uri) {
		if (instance == null) {
			return null;
		}
		if (ContainerTool.isEmpty(instance.nameBeans)) {
			return null;
		}
		return instance.nameBeans.get(uri);
	}

	/**
	 * 获取mvc的请求路径的 执行Class 的请求方法
	 *
	 * @param uri 访问路径
	 * @return 执行Class 的请求方法
	 */
	public HttpConfig.Method[] getMethod(String uri) {
		if (instance == null) {
			return null;
		}
		if (ContainerTool.isEmpty(instance.methodsBeans)) {
			return null;
		}
		return instance.methodsBeans.get(uri);
	}

	/**
	 * 获取mvc的请求路径的 执行Class 的请求编码
	 *
	 * @param uri 访问路径
	 * @return 执行Class 的请求编码
	 */
	public HttpConfig.Code getCode(String uri) {
		if (instance == null) {
			return null;
		}
		if (ContainerTool.isEmpty(instance.codeBeans)) {
			return null;
		}
		return instance.codeBeans.get(uri);
	}

	/**
	 * 获取异步超时
	 *
	 * @return 异步超时
	 */
	public long getAsyncTimeOut() {
		if (instance == null) {
			return 5000 * 1000L;
		}
		return instance.asyncTimeOut;
	}
	/**
	 * 设置异步超时
	 *
	 * @param asyncTimeOut 异步超时
	 * @return 异步超时
	 */
	public synchronized static boolean setAsyncTimeOut(long asyncTimeOut) {
		if (instance == null) {
			return false;
		}
		synchronized (WebServletContent.class) {
			instance.asyncTimeOut = asyncTimeOut;
		}
		return true;
	}
}
