package com.ibs.plan.connector.admin.manage.core.thread;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.common.core.configs.PlanMainConfig;
import com.ibs.plan.module.cloud.ibsp_sys_thread_pool.service.IbspSysThreadPoolService;
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
@ActionMapping(value = "/ibs/sys/manage/core/thread/list")
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
			IbspSysThreadPoolService threadPoolService = new IbspSysThreadPoolService();
			String[] requestCodes = PlanMainConfig.REQUEST_POOL.split(",");
			String  ip = IpTool.getIpExtranet();
			for (String requestCode : requestCodes) {
				Map<String, Object> config = WebServletContent.getConfig(requestCode);
				config.put("moduleCode", PlanMainConfig.Module.REQUEST.name());
				config.put("moduleName", PlanMainConfig.Module.REQUEST.getName());
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
			return bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
