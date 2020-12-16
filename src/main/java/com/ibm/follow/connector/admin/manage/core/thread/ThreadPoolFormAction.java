package com.ibm.follow.connector.admin.manage.core.thread;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_thread_pool.entity.IbmSysThreadPool;
import com.ibm.follow.servlet.cloud.ibm_sys_thread_pool.service.IbmSysThreadPoolService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description: 修改线程池表单页面
 * @Author: null
 * @Date: 2020-05-14 14:01
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/core/thread/form")
public class ThreadPoolFormAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String threadCode = dataMap.getOrDefault("threadCode", "").toString();
		String serverIp = dataMap.getOrDefault("serverIp", "").toString();

		if (StringTool.isEmpty(threadCode, serverIp)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			IbmSysThreadPoolService threadPoolService = new IbmSysThreadPoolService();
			IbmSysThreadPool threadPool = threadPoolService.find(serverIp, threadCode);
			if (threadPool == null){
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			bean.success(threadPool);
		} catch (Exception e) {
			log.error("服务器信息列表错误", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
