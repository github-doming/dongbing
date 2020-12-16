package com.ibm.old.v1.admin.sys_web_content.t.action;
import com.ibm.old.v1.common.doming.core.servlet.BaseAsynMvcTransactions;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.common.servlet.WebServletContent;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 线程池管理类
 * @Author: Dongming
 * @Date: 2019-05-23 17:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ThreadPoolListAction
		extends BaseAsynMvcTransactions {
	public ThreadPoolListAction(){
		super.code = "core";
	}

	@Override public Object run() throws Exception {
		Map<String, Map<String, Object>> data = new HashMap<>(2);
//
//		Map<String, Object> coreThreadPool = new HashMap<>(6);
//		coreThreadPool.put("corePoolSize", WebServletContent.getCorePoolSize("core"));
//		coreThreadPool.put("poolSize", WebServletContent.getPoolSize("core"));
//		coreThreadPool.put("activeCount", WebServletContent.getActiveCount("core"));
//		coreThreadPool.put("keepAliveTimeSeconds", WebServletContent.getKeepAliveTimeSeconds("core"));
//		coreThreadPool.put("maximumPoolSize", WebServletContent.getMaximumPoolSize("core"));
//		coreThreadPool.put("taskCount", WebServletContent.getTaskCount("core"));
//		data.put("core", coreThreadPool);
//
//		Map<String, Object> queryThreadPool = new HashMap<>(6);
//		queryThreadPool.put("corePoolSize", WebServletContent.getCorePoolSize("query"));
//		queryThreadPool.put("poolSize", WebServletContent.getPoolSize("query"));
//		queryThreadPool.put("activeCount", WebServletContent.getActiveCount("query"));
//		queryThreadPool.put("keepAliveTimeSeconds", WebServletContent.getKeepAliveTimeSeconds("query"));
//		queryThreadPool.put("maximumPoolSize", WebServletContent.getMaximumPoolSize("query"));
//		queryThreadPool.put("taskCount", WebServletContent.getTaskCount("query"));
//		data.put("query", queryThreadPool);
		return new ModelAndView(data);
	}
}
