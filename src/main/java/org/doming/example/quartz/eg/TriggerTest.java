package org.doming.example.quartz.eg;
import org.junit.Test;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

/**
 * @Description: HelloJob测试类
 * @Author: Dongming
 * @Date: 2018-10-29 10:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TriggerTest {

	@Test public void test() throws SchedulerException, InterruptedException {
		//从工厂获取Scheduler实例
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		initJob2(scheduler);

		//开始
		scheduler.start();
		Thread.sleep(4000);
		scheduler.shutdown();
	}

	private void initJob(Scheduler scheduler) throws SchedulerException {

		//定义作业并将其绑定到HelloJob类
		JobDetail job = newJob(DumbJob1.class)
				.withIdentity("myJob")
				.usingJobData("jobSays", "Hello World!")
				.usingJobData("myFloatValue", 3.141f)
				.build();
		//触发作业立即运行，然后每3秒重复一次
		Trigger trigger = newTrigger()
				.withIdentity(triggerKey("myTrigger", "myTriggerGroup"))
				.withSchedule(simpleSchedule()
						.withIntervalInSeconds(2)
						.repeatForever())
				.startAt(futureDate(1, IntervalUnit.SECOND))
				.build();
		//告诉quartz使用我们的触发器安排作业
		scheduler.scheduleJob(job, trigger);
	}

	private void initJob2(Scheduler scheduler) throws SchedulerException {

		//定义作业并将其绑定到HelloJob类
		JobDetail job = newJob(DumbJob2.class)
				.withIdentity("myJob")
				.usingJobData("jobSays", "Hello World!")
				.usingJobData("myFloatValue", 3.141f)
				.build();
		//触发作业立即运行，然后每3秒重复一次
		Trigger trigger = newTrigger()
				.withIdentity(triggerKey("myTrigger", "myTriggerGroup"))
				.withSchedule(simpleSchedule()
						.withIntervalInSeconds(2)
						.repeatForever())
				.startAt(futureDate(1, IntervalUnit.SECOND))
				.build();
		//告诉quartz使用我们的触发器安排作业
		scheduler.scheduleJob(job, trigger);
	}

}
