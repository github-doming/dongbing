package org.doming.example.quartz.eg5;

import org.doming.example.quartz.eg1.ram.RAMJob;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
/**
 * @Description: HelloJob测试类
 * @Author: Dongming
 * @Date: 2018-10-29 10:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CronTriggerTest {

	@Test public void test() throws SchedulerException, InterruptedException {
		//从工厂获取Scheduler实例
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		initJob(scheduler);

		//开始
		scheduler.start();
		Thread.sleep(15000);
		scheduler.shutdown();
	}

	private void initJob(Scheduler scheduler) throws SchedulerException {

		//定义作业并将其绑定到HelloJob类
		JobDetail job = newJob(RAMJob.class).withIdentity("myJob").build();

		long nowTime = System.currentTimeMillis();

		//建立一个触发器，每隔一分钟，每天上午8点至下午5点之间：
		CronTrigger trigger = newTrigger().withIdentity("trigger", "group")
				.withSchedule(cronSchedule("0 0/2 8-17 * * ?")).forJob("job", "group").build();

		//建立一个触发器，将在上午10:42每天触发
		CronTrigger trigger1 = newTrigger().withIdentity("trigger1", "group").withSchedule(dailyAtHourAndMinute(10, 42))
				.forJob("myJob").build();
		trigger1 = newTrigger().withIdentity("trigger1", "group").withSchedule(cronSchedule("0 42 10 * * ?"))
				.forJob("myJob").build();

		CronTrigger trigger2 = newTrigger().withIdentity("trigger2", "group")
				.withSchedule(weeklyOnDayAndHourAndMinute(DateBuilder.WEDNESDAY, 10, 42)).forJob("myJob").build();
		trigger2 = newTrigger().withIdentity("trigger2", "group").withSchedule(cronSchedule("0 42 10 ? * WED"))
				.forJob("myJob").build();


		scheduler.scheduleJob(job, trigger);

	}

}
