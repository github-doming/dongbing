package com.ibm.follow.connector.admin.manage.core.quertz;

import all.gen.sys_quartz_job.t.entity.SysQuartzJobT;
import all.gen.sys_quartz_job.t.service.SysQuartzJobTService;
import all.gen.sys_quartz_trigger.t.entity.SysQuartzTriggerT;
import all.gen.sys_quartz_trigger.t.service.SysQuartzTriggerTService;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.job.TriggerStateEnum;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * 定时器保存
 *
 * @Author: Dongming
 * @Date: 2020-05-16 10:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/core/quartz/save")
public class QuartzSaveAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String triggerName = StringTool.getString(dataMap, "TRIGGER_NAME_", "");
		String jobName = StringTool.getString(dataMap, "JOB_NAME_", "");
		String desc = StringTool.getString(dataMap, "DESC_", "");
		String jobClass = StringTool.getString(dataMap, "JOB_CLASS_", "");
		String cronExpression = StringTool.getString(dataMap, "CRON_EXPRESSION_", "");

		if (StringTool.isEmpty(triggerName, jobClass, cronExpression)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}

		try {
			if (StringTool.isEmpty(jobName)) {
				jobName = "job_" + jobClass;
			}
			String id = RandomTool.getId();
			Date nowTime = new Date();

			SysQuartzTriggerT quartzTriggerT = new SysQuartzTriggerT();
			quartzTriggerT.setSysTriggerName(triggerName);
			quartzTriggerT.setJobName(jobName);
			quartzTriggerT.setJobClassName(jobClass);
			quartzTriggerT.setTriggerId(id);
			quartzTriggerT.setTriggerName("trigger_" + id);
			quartzTriggerT.setCronExpression(cronExpression);
			quartzTriggerT.setCreateTime(nowTime);
			quartzTriggerT.setCreateTimeLong(System.currentTimeMillis());
			quartzTriggerT.setUpdateTime(nowTime);
			quartzTriggerT.setUpdateTimeLong(System.currentTimeMillis());
			quartzTriggerT.setState(TriggerStateEnum.NONE.name());
			quartzTriggerT.setDesc(desc);

			new SysQuartzTriggerTService().save(quartzTriggerT);

			SysQuartzJobT quartzJobT = new SysQuartzJobT();
			quartzJobT.setSysJobName(triggerName);
			quartzJobT.setJobName(jobName);
			quartzJobT.setJobClassName(jobClass);
			quartzJobT.setCreateTime(nowTime);
			quartzJobT.setCreateTimeLong(System.currentTimeMillis());
			quartzJobT.setUpdateTime(nowTime);
			quartzJobT.setUpdateTimeLong(System.currentTimeMillis());
			quartzJobT.setState(IbmStateEnum.NONE.name());
			quartzJobT.setDesc(desc);
			new SysQuartzJobTService().save(quartzJobT);


			bean.success();
		} catch (Exception e) {
			log.error("定时器保存错误");
			bean.error(e.getMessage());
		}


		return bean;
	}

}
