package com.ibs.plan.connector.admin.manage.core.thread;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.plan.common.core.configs.PlanMainConfig;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.ibsp_sys_thread_pool.entity.IbspSysThreadPool;
import com.ibs.plan.module.cloud.ibsp_sys_thread_pool.service.IbspSysThreadPoolService;
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
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			IbspSysThreadPoolService threadPoolService = new IbspSysThreadPoolService();
			IbspSysThreadPool threadPool = threadPoolService.find(serverIp, threadCode);
			if (threadPool == null){
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			//模块类型，moduleType
			switch (PlanMainConfig.Module.valueOf(threadPool.getModuleCode())) {
				case REQUEST:
					WebServletContent.setConfig(threadPool.getThreadCode(), Integer.parseInt(coreSize), Integer.parseInt(maxSize), 20);
					break;
				case MQ:
					JSONObject content = new JSONObject();
					content.put("command", IbsMethodEnum.THREAD_SET.name());
					content.put("requestType",  IbsMethodEnum.THREAD_SET.name());
					content.put("coreCapacity", coreSize);
					content.put("maxCapacity", maxSize);
					RabbitMqTool.sendReceipt(content.toString(), "handle");
					break;
				case SERVER:
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					return bean;
			}
			threadPool.setCoreSize( Integer.parseInt(coreSize));
			threadPool.setMaxSize( Integer.parseInt(maxSize));
			threadPoolService.update(threadPool);
			bean.success();
		} catch (Exception e) {
			log.error("服务器信息列表错误", e);
			return bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
