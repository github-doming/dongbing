package org.doming.example.quartz.eg2.jdbc;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-12-06 16:00
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class QuartzJdbcTest {
	public static void main(String[] args) {
		startSchedule();
	}

	/**
	 * 开始一个simpleSchedule()调度
	 */
	private static void startSchedule() {

		try {
			// 1、创建一个JobDetail实例，指定Quartz
			JobDetail jobDetail = JobBuilder
					// 任务执行类
					.newJob(MyJob.class)
					// 任务名，任务组
					.withIdentity("myJob", "myJobGroup").build();

			/*
				触发器类型
					创建一个SimpleScheduleBuilder设置为重复给定的次数 -  1次，间隔为1秒。
							注意：总计数= 1（在开始时间）+重复计数
			 */
			SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
					//设置执行次数
					.repeatSecondlyForTotalCount(5);
			//CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");

			// 2、创建Trigger
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "myTriggerGroup").startNow()
					.withSchedule(scheduleBuilder).build();

			// 3、创建Scheduler
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();


			// 5、调度执行
			scheduler.start();

			// 4、注册任务和定时器
			scheduler.scheduleJob(jobDetail, trigger);


			Thread.sleep(60000);

			// 6、关闭调度器
			scheduler.shutdown();

		} catch (SchedulerException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
