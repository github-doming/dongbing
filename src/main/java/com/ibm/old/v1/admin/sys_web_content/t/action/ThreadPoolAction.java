package com.ibm.old.v1.admin.sys_web_content.t.action;
import com.ibm.old.v1.common.doming.core.servlet.BaseAsynMvcTransactions;
import org.doming.core.common.servlet.ModelAndView;
/**
 * @Description: 线程池管理类
 * @Author: Dongming
 * @Date: 2019-05-23 17:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ThreadPoolAction extends BaseAsynMvcTransactions {
	public ThreadPoolAction() {
		super.code = "core";
	}
	@Override public Object run() throws Exception {
		return new ModelAndView("/pages/com/ibm/admin/sys_web_content/ThreadPool.jsp");
	}
}
