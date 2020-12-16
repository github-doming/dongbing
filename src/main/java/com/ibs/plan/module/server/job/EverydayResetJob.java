package com.ibs.plan.module.server.job;

import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.plan.common.utils.ThreadExecuteUtil;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_event_config_set.service.IbspEventConfigSetService;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
import com.ibs.plan.module.cloud.ibsp_profit_plan.service.IbspProfitPlanService;
import com.ibs.plan.module.server.thread.ConfigSetThread;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 每天重置定时器
 * @Author: null
 * @Date: 2020-07-06 15:50
 * @Version: v1.0
 */
public class EverydayResetJob extends BaseCommJob {
	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		log.info("每天重置方案定时器开始");

		IbspHmSetService hmSetService = new IbspHmSetService();
		List<String> handicapMemberIds = hmSetService.everyDayResetHmIds();
		if (ContainerTool.notEmpty(handicapMemberIds)) {
			IbspProfitPlanService profitPlanService = new IbspProfitPlanService();
			profitPlanService.everyDayResetProfit(handicapMemberIds, "ibsp_profit_plan");
			profitPlanService.everyDayResetProfit(handicapMemberIds, "ibsp_profit_plan_vr");

			IbspEventConfigSetService configSetService = new IbspEventConfigSetService();
			List<String> eventIds = new ArrayList<>();
			Date nowTime = new Date();
			JSONObject content = new JSONObject();
			content.put("METHOD_", IbsMethodEnum.SET_PLAN_RESET.name());
			for (String handicapMemberId : handicapMemberIds) {
				content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
				String eventId = EventThreadDefine
						.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("，虚拟止盈或止损达到限额"),
								configSetService);
				eventIds.add(eventId);
			}
			ThreadExecuteUtil.findInstance().getCoreExecutor().execute(new ConfigSetThread(eventIds));
		}


		log.info("每天重置方案定时器完成");
	}
}
