package com.ibm.follow.connector.admin.manage.core.quertz;

import all.gen.sys_quartz_job.t.entity.SysQuartzJobT;
import all.gen.sys_quartz_job.t.service.SysQuartzJobTService;
import all.gen.sys_quartz_trigger.t.entity.SysQuartzTriggerT;
import all.gen.sys_quartz_trigger.t.service.SysQuartzTriggerTService;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * 定时器删除 物理删除
 *
 * @Author: Dongming
 * @Date: 2020-05-16 11:28
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/core/quartz/del")
public class QuartzDelAction extends CommAdminAction {
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
			SysQuartzTriggerTService quartzTriggerTService = new SysQuartzTriggerTService();

			SysQuartzTriggerT quartzTriggerT = quartzTriggerTService.find(quartzTriggerId);
			if (quartzTriggerT == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			quartzTriggerTService.delPhysical(quartzTriggerId);

			SysQuartzJobTService quartzJobTService = new SysQuartzJobTService();
			SysQuartzJobT quartzJobT = quartzJobTService.find(quartzTriggerT.getTriggerName(), quartzTriggerT.getJobName());
			if (quartzJobT != null) {
				quartzJobTService.delPhysical(quartzJobT.getSysQuartzJobId());
			}

			bean.success();
		} catch (Exception e) {
			log.error("定时器删除错误");
			bean.error(e.getMessage());
		}
		return bean;
	}
}