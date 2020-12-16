package com.ibm.old.v1.common.zjj.test.ctr;

import c.a.util.core.test.CommTest;
import com.ibm.old.v1.client.core.job.login.LoginHandicapWs2Job;
import com.ibm.old.v1.common.doming.configs.QuartzConfig;
import org.doming.core.tools.DateTool;
import org.doming.develop.job.QuartzUtil;
import org.junit.Test;
import org.quartz.*;

import java.util.Date;

public class JobTest extends CommTest {
	@Test
	public void demo() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			client();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}

	}

	private void client() throws Exception {
		System.out.println("------------start---------------");
		String landedTimeStr = "17:30:00";
		String code = "WS2";
		String gameId = "b509507aed594bf3ad60049e2af2ea45";
		Date landedTime = DateTool.getTime(landedTimeStr);
		if (DateTool.compare(landedTime, new Date())) {
			System.out.println(DateTool.compare(landedTime, new Date()));
		}
		
		

		// 启动定时器

		// 判断定时器所使用的类

		JobBuilder builder = JobBuilder.newJob(LoginHandicapWs2Job.class);

		JobDataMap map = new JobDataMap();
//		map.put("hmExistId", hmExistT.getIbmClientExistHmId());
		JobDetail jobDetail = builder
				.withDescription(String.format(QuartzConfig.DESCRIPTION_LOGIN, "handicapMemberId",
						"hmExistT.getHandicapCode()", "工作"))
				.withIdentity(
						String.format(QuartzConfig.IDENTITY_NAME_LOGIN, "handicapMemberId", "hmExistT.getHandicapCode()",
								"Job", DateTool.getLog()),
						String.format(QuartzConfig.IDENTITY_GROUP_LOGIN, "hmExistT.getHandicapCode()"))
				.usingJobData(map).build();
		Trigger trigger = TriggerBuilder.newTrigger()
				.withDescription(String.format(QuartzConfig.DESCRIPTION_LOGIN, "handicapMemberId",
						"hmExistT.getHandicapCode()", "触发器"))
				.withIdentity(
						String.format(QuartzConfig.IDENTITY_NAME_LOGIN, "handicapMemberId", "hmExistT.getHandicapCode()",
								"Trigger", DateTool.getLog()),
						String.format(QuartzConfig.IDENTITY_GROUP_LOGIN, "hmExistT.getHandicapCode()"))
				.startAt(landedTime).build();

		// 设置定时器
		QuartzUtil quartzUtil = QuartzUtil.findInstance();

		quartzUtil.scheduleJob(jobDetail, trigger);

		System.out.println("------------end---------------");
	}
}
