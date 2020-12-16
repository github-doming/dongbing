package com.ibm.follow.connector.admin.manage.core.thread;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_sys_thread_pool.entity.IbmSysThreadPool;
import com.ibm.follow.servlet.cloud.ibm_sys_thread_pool.service.IbmSysThreadPoolService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.WebServletContent;
import org.doming.core.tools.StringTool;

/**
 * @Description: 修改线程池容量信息
 * @Author: null
 * @Date: 2020-05-14 14:10
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/core/thread/edit")
public class ThreadPoolEditAction extends CommAdminAction {
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
		String coreSize = dataMap.getOrDefault("coreSize", "").toString();
		String maxSize = dataMap.getOrDefault("maxSize", "").toString();

		if (StringTool.isEmpty(threadCode, serverIp, coreSize, maxSize)) {
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
			//模块类型，moduleType
			switch (IbmMainConfig.Module.valueOf(threadPool.getModuleCode())) {
				case REQUEST:
					WebServletContent.setConfig(threadPool.getThreadCode(), Integer.parseInt(coreSize), Integer.parseInt(maxSize), 20);
					break;
				case MQ:
					JSONObject content = new JSONObject();
					content.put("METHOD_", IbmMethodEnum.THREAD_SET.name());
					content.put("coreCapacity", coreSize);
					content.put("maxCapacity", maxSize);
					RabbitMqTool.sendConfigReceipt(content.toString(), "set");
					break;
				case SERVER:
				default:
					bean.putEnum(IbmCodeEnum.IBM_404_METHOD);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return bean;
			}
			threadPool.setCoreSize( Integer.parseInt(coreSize));
			threadPool.setMaxSize( Integer.parseInt(maxSize));
			threadPoolService.update(threadPool);
			bean.success();
		} catch (Exception e) {
			log.error("服务器信息列表错误", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
