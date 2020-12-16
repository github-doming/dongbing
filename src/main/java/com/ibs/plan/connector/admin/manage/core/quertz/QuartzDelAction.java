package com.ibs.plan.connector.admin.manage.core.quertz;

import all.gen.sys_quartz_job.t.entity.SysQuartzJobT;
import all.gen.sys_quartz_job.t.service.SysQuartzJobTService;
import all.gen.sys_quartz_trigger.t.entity.SysQuartzTriggerT;
import all.gen.sys_quartz_trigger.t.service.SysQuartzTriggerTService;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
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
@ActionMapping(value = "/ibs/sys/manage/core/quartz/del")
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
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			SysQuartzTriggerTService quartzTriggerTService = new SysQuartzTriggerTService();

			SysQuartzTriggerT quartzTriggerT = quartzTriggerTService.find(quartzTriggerId);
			if (quartzTriggerT == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
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
			throw e;
		}
		return bean;
	}
}