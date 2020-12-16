package com.ibm.follow.connector.admin.manage.core.thread;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.follow.servlet.cloud.ibm_sys_thread_pool.service.IbmSysThreadPoolService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.WebServletContent;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;

import java.util.List;
import java.util.Map;

/**
 * @Description: 线程池列表信息
 * @Author: null
 * @Date: 2020-05-14 11:08
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/core/thread/list")
public class ThreadPoolListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			//请求线程
			IbmSysThreadPoolService threadPoolService = new IbmSysThreadPoolService();
			String[] requestCodes = IbmMainConfig.REQUEST_POOL.split(",");
			String  ip = IpTool.getIpExtranet();
			for (String requestCode : requestCodes) {
				Map<String, Object> config = WebServletContent.getConfig(requestCode);
				config.put("moduleCode", IbmMainConfig.Module.REQUEST.name());
				config.put("moduleName", IbmMainConfig.Module.REQUEST.getName());
				config.put("threadCode", requestCode);
				threadPoolService.update(ip, config);
			}
			List<Map<String, Object>> poolInfoList = threadPoolService.show();
			for (Map<String, Object> info : poolInfoList){
				info.put("MAX_SIZE_", NumberTool.getInteger(info,"MAX_SIZE_",0));
			}
			bean.success(poolInfoList);
		} catch (Exception e) {
			log.error("服务器信息列表错误", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
