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
import com.ibm.common.enums.IbmStateEnum;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * 定时器修改
 *
 * @Author: Dongming
 * @Date: 2020-05-16 11:28
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/core/quartz/edit")
public class QuartzEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String quartzTriggerId = StringTool.getString(dataMap, "QUARTZ_TRIGGER_ID_", "");
		String triggerName = StringTool.getString(dataMap, "TRIGGER_NAME_", "");
		String desc = StringTool.getString(dataMap, "DESC_", "");
		String jobClass = StringTool.getString(dataMap, "JOB_CLASS_", "");
		String cronExpression = StringTool.getString(dataMap, "CRON_EXPRESSION_", "");

		if (StringTool.isEmpty(quartzTriggerId, triggerName, jobClass, cronExpression)) {
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
			String oldTriggerName = quartzTriggerT.getTriggerName();
			String oldJobName = quartzTriggerT.getJobName();
			Date nowTime = new Date();
			quartzTriggerT.setSysTriggerName(triggerName);
			quartzTriggerT.setJobName("job_" + jobClass);
			quartzTriggerT.setJobClassName(jobClass);
			quartzTriggerT.setCronExpression(cronExpression);
			quartzTriggerT.setUpdateTime(nowTime);
			quartzTriggerT.setUpdateTimeLong(System.currentTimeMillis());
			quartzTriggerT.setDesc(desc);
			quartzTriggerTService.update(quartzTriggerT);

			SysQuartzJobTService quartzJobTService = new SysQuartzJobTService();
			SysQuartzJobT quartzJobT = quartzJobTService.find(oldTriggerName, oldJobName);
			if (quartzJobT != null) {
				quartzJobT.setSysJobName(triggerName);
				quartzJobT.setJobName("job_" + jobClass);
				quartzJobT.setJobClassName(jobClass);
				quartzJobT.setUpdateTime(nowTime);
				quartzJobT.setUpdateTimeLong(System.currentTimeMillis());
				quartzJobT.setDesc(desc);
				quartzJobTService.update(quartzJobT);
			}else {
				quartzJobT = new SysQuartzJobT();
				quartzJobT.setSysJobName(triggerName);
				quartzJobT.setJobName("job_" + jobClass);
				quartzJobT.setJobClassName(jobClass);
				quartzJobT.setCreateTime(nowTime);
				quartzJobT.setCreateTimeLong(System.currentTimeMillis());
				quartzJobT.setUpdateTime(nowTime);
				quartzJobT.setUpdateTimeLong(System.currentTimeMillis());
				quartzJobT.setState(IbmStateEnum.NONE.name());
				quartzJobT.setDesc(desc);
				quartzJobTService.save(quartzJobT);
			}

			bean.success();
		} catch (Exception e) {
			log.error("定时器修改错误");
			bean.error(e.getMessage());
		}
		return bean;
	}
}