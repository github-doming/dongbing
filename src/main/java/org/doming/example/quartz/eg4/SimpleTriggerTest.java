package org.doming.example.quartz.eg4;

import org.doming.example.quartz.eg1.ram.RAMJob;
import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.evenHourDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
/**
 * @Description: HelloJob测试类
 * @Author: Dongming
 * @Date: 2018-10-29 10:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SimpleTriggerTest {

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

		//指定时间开始触发，不重复：
		SimpleTrigger trigger = (SimpleTrigger) newTrigger().withIdentity("trigger", "group")
				.startAt(new Date(nowTime + 1000))
				//使用名称，组字符串标识作业
				.forJob("job", "group").build();

		//指定时间触发，每隔2秒执行一次，重复5次
		SimpleTrigger trigger1 = newTrigger().withIdentity("trigger1", "group")
				//如果没有给出开始时间（如果省略该行），则表示“现在”
				.startAt(new Date(nowTime + 1000)).withSchedule(simpleSchedule()
						//间隔秒数
						.withIntervalInSeconds(2)
						//请注意，5次重复将总共发射6次
						.withRepeatCount(5))
				////使用其JobDetail本身识别作业
				.forJob(job).build();

		//1秒以后开始触发，仅执行一次
		SimpleTrigger trigger2 = (SimpleTrigger) newTrigger().withIdentity("trigger2", "group")
				.startAt(new Date(nowTime + 1000))
				//使用JobKey识别作业
				.forJob("myJob").build();

		//立即触发，每隔2秒执行一次，直到11秒后：
		SimpleTrigger trigger3 = newTrigger().withIdentity("trigger3", "group")
				.withSchedule(simpleSchedule().withIntervalInSeconds(2)
						//一直重复
						.repeatForever()).endAt(new Date(nowTime + 11000)).build();

		//建立一个触发器，将在下一个小时的整点触发，然后每22秒重复一次：
		SimpleTrigger trigger4 = newTrigger()
				////因为未指定group，“trigger4”将在默认组中
				.withIdentity("trigger4")
				//获取下一个偶数小时（分钟和秒零（“00:00”））
				.startAt(evenHourDate(null)).withSchedule(simpleSchedule().withIntervalInSeconds(2).repeatForever())
				.build();

		SimpleTrigger trigger5 = newTrigger().withIdentity("trigger5", "group").withSchedule(
				simpleSchedule().withIntervalInSeconds(2).repeatForever()
						.withMisfireHandlingInstructionNextWithExistingCount()).forJob("myJob").build();

		scheduler.scheduleJob(job, trigger5);

	}

}
