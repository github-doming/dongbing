package c.a.util.core.thread.pool;
import c.a.config.SysConfig;
import c.a.config.core.ConfigParameterMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 多个ThreadExecutorService
 *
 * @author cxy
 * @Description:
 * @ClassName: ThreadExecutorServiceListUtil
 * @date 2018年6月28日 上午9:28:47
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class ThreadPoolExecutorServiceListUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	public String Mvc = "mvc";
	public String Local = "local";
	public static Map<String, ThreadPoolExecutorService> map = null;
	// 单例
	private static ThreadPoolExecutorServiceListUtil instance = null;
	// key 最好用Object
	private final static Object key = new Object();
	private ThreadPoolExecutorServiceListUtil() {
	}
	/**
	 * 获取ThreadExecutorService单例
	 *
	 * @return
	 * @throws Exception 返回类型 JdbcThreadExecutorServiceUtil
	 * @Title: findInstance
	 * @Description: 参数说明
	 */
	public static ThreadPoolExecutorServiceListUtil findInstance() throws Exception {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new ThreadPoolExecutorServiceListUtil();
				}
			}
		}
		return instance;
	}
	public static void destroy() {
		if (instance != null && map != null && !map.isEmpty()) {
			for (ThreadPoolExecutorService executorService : map.values()) {
				executorService.shutdown();
			}
		}
	}

	public static Map<String, ThreadPoolExecutorService> getMap() {
		return map;
	}

	public void setMap(Map<String, ThreadPoolExecutorService> mapInput) {
		map = mapInput;
	}
	public ThreadPoolExecutorService findThreadExecutorService(String capacityInput, String corePoolSizeInput,
			String maximumPoolSizeInput, String keepAliveTimeInput, String rejectedExecutionHandlerInput,
			String threadIdInput, String threadNameInput) throws Exception {
		ThreadPoolExecutorService threadPoolExecutorService = new ThreadPoolExecutorService();
		threadPoolExecutorService.init(capacityInput, corePoolSizeInput, maximumPoolSizeInput, keepAliveTimeInput,
				rejectedExecutionHandlerInput, threadIdInput, threadNameInput);
		return threadPoolExecutorService;
	}
	public synchronized ThreadPoolExecutorService findThreadExecutorService(String id) throws Exception {
		if (map == null) {
			map = new HashMap<String, ThreadPoolExecutorService>();
		}
		ThreadPoolExecutorService jdbcThreadExecutorService = map.get(id);
		if (jdbcThreadExecutorService == null) {
			List<ConfigParameterMap> configList = SysConfig.findInstance().findList();
			// 线程id
			String threadId = null;
			// 线程名称
			String threadName = null;
			// 队列
			String capacity = null;
			String corePoolSize = null;
			// 最大线程
			String maximumPoolSize = null;
			String keepAliveTime = null;
			String rejectedExecutionHandler = null;
			for (ConfigParameterMap configMap : configList) {
				if ("thread".equals(configMap.getName()) && id.equals(configMap.getId())) {
					if ("capacity".equals(configMap.getKey())) {
						capacity = configMap.getValue();
					}
					if ("corePoolSize".equals(configMap.getKey())) {
						threadId = configMap.getId();
						corePoolSize = configMap.getValue();
					}
					if ("maximumPoolSize".equals(configMap.getKey())) {
						maximumPoolSize = configMap.getValue();
					}
					if ("keepAliveTime".equals(configMap.getKey())) {
						keepAliveTime = configMap.getValue();
					}
					if ("rejectedExecutionHandler".equals(configMap.getKey())) {
						rejectedExecutionHandler = configMap.getValue();
					}
					if ("threadName".equals(configMap.getKey())) {
						threadName = configMap.getValue();
					}
				}
			}
			jdbcThreadExecutorService = this
					.findThreadExecutorService(capacity, corePoolSize, maximumPoolSize, keepAliveTime,
							rejectedExecutionHandler, threadId, threadName);
			map.put(id, jdbcThreadExecutorService);
		}
		log.trace("线程配置 id=" + id);
		return jdbcThreadExecutorService;
	}
	public ThreadPoolExecutorService findMvc() throws Exception {
		return this.findThreadExecutorService(Mvc);
	}
	public ThreadPoolExecutorService findLocal() throws Exception {
		return this.findThreadExecutorService(Local);
	}
}
