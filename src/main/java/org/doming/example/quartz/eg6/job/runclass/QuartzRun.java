package org.doming.example.quartz.eg6.job.runclass;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-12-16 10:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class QuartzRun {

	public static void main(String[] args) {

		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			JobDetail jobDetail = JobBuilder.newJob(JobWork.class)
					//job的描述
					.withDescription("这是一个 ram Job实例")
					//job 的name和group
					.withIdentity("ramJob", "ramGroup").build();

			long time = System.currentTimeMillis() + 3 * 1000L;
			Date statTime = new Date(time);

		/*
			4.创建Trigger 触发器
				定义Trigger的规范。
		 */
			Trigger trigger = TriggerBuilder.newTrigger()
					//trigger的描述
					.withDescription("这是一个 ram 触发器实例")
					//trigger 的name和group
					.withIdentity("ramTrigger", "ramTriggerGroup")
					//trigger 的启动时间
					.startAt(statTime)
					//定义触发器的计划，使用的不同的SchedulerBuilder将导致TriggerBuilder生成的具体Trigger类型。
					.withSchedule(CronScheduleBuilder
							//两秒执行一次
							.cronSchedule("0/2 * * * * ?")).build();

		/*
			5.注册任务和定时器
				将给定的JobDetail添加到Scheduler，并将给定的Trigger与它关联。
				如果给定的Trigger没有引用任何Job，那么它将被设置为引用与它一起传递的Job到这个方法中。
		 */
			scheduler.scheduleJob(jobDetail, trigger);

		/*
			6.启动 调度器
				启动调度程序触发触发器的线程。
				首次创建调度程序时，它处于“待机”模式，并且不会触发触发器。
				调度程序也可以通过调用standby()方法进入待机模式。
				如果在此调度程序实例上初始调用此方法，则将启动失火/恢复过程。
		 */
			scheduler.start();

		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}
}
