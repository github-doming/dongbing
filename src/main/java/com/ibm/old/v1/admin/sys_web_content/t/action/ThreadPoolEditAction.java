package com.ibm.old.v1.admin.sys_web_content.t.action;
import com.ibm.old.v1.common.doming.core.servlet.BaseAsynMvcTransactions;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.common.servlet.WebServletContent;

import javax.servlet.ServletRequest;
/**
 * @Description: 线程池管理类
 * @Author: Dongming
 * @Date: 2019-05-23 17:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ThreadPoolEditAction
		extends BaseAsynMvcTransactions {
	public ThreadPoolEditAction(){
		super.code = "core";
	}

	@Override public Object run() throws Exception {
		ServletRequest request = asyncContext.getRequest();
		String code = request.getParameter("code");
		String corePoolSize = request.getParameter("corePoolSize");
		String maximumPoolSize = request.getParameter("maximumPoolSize");
		String keepAliveTimeSeconds = request.getParameter("keepAliveTimeSeconds");

//		WebServletContent.setCorePoolSize(code,Integer.parseInt(corePoolSize));
//		WebServletContent.setKeepAliveTimeSeconds(code,Integer.parseInt(keepAliveTimeSeconds));
//		WebServletContent.setMaximumPoolSize(code,Integer.parseInt(maximumPoolSize));
		return new ModelAndView("/pages/com/ibm/admin/sys_web_content/ThreadPool.jsp");
	}
}
