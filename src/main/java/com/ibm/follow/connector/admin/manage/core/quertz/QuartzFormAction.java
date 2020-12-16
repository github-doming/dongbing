package com.ibm.follow.connector.admin.manage.core.quertz;

import all.job.service.SysQuartzTriggerService;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 定时器详情
 *
 * @Author: Dongming
 * @Date: 2020-05-16 11:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/core/quartz/form")
public class QuartzFormAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String quartzTriggerId = StringTool.getString(dataMap, "QUARTZ_TRIGGER_ID_", "");
		if (StringTool.isEmpty(quartzTriggerId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			SysQuartzTriggerService quartzTriggerService = new SysQuartzTriggerService();
			Map<String, Object> data = quartzTriggerService.findShow(quartzTriggerId);
			bean.success(data);
		} catch (Exception e) {
			log.error("定时器详情错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
