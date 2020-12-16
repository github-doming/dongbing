package c.a.config.core;

import c.a.config.request.config.RequestConfigGy;

public class ServletContextConfigHy extends RequestConfigGy {
	// -- 下面的方法不再更新 --//
	/**
	 * 当前系统登录的所有用户,保存在ServletContext
	 * 
	 * 全局context(context)
	 * 
	 * userid跟tenant
	 * 
	 * 查sessionId
	 * 
	 * (map)
	 */
	public final static String CurrentUserList = "CurrentUserList";
	/**
	 * 线程池
	 */
	public final static String ThreadMvcExecutor = "thread_mvc_executor";
	/**
	 * @deprecated
	 * 线程池
	 */
	public final static String ThreadMvcRequest = "thread.mvc.request";
	/**
	 * 线程池
	 */
	public final static String ThreadLocalExecutor = "thread_local_executor";
	/**
	 * @deprecated
	 * 线程池
	 */
	public final static String ThreadLocalRequest = "thread.local.request";

	// -- 上面的方法不再更新 --/
}
