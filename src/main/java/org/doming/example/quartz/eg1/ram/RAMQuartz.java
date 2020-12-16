package org.doming.example.quartz.eg1.ram;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Description: 这是一个RAM Quartz 实例！
 * @Author: Dongming
 * @Date: 2018-12-06 15:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RAMQuartz {
	private static Logger log = LoggerFactory.getLogger(RAMQuartz.class);

	public static void main(String[] args) throws SchedulerException {

		/*
			1.创建Scheduler的工厂<StdSchedulerFactory>
				它根据properties文件的内容完成创建QuartzScheduler实例的所有工作。
				默认情况下，从“当前工作目录”加载名为“quartz.properties”的属性文件。
				如果失败，则加载org.quartz包中的（quartz.properties）文件（作为资源）。
				如果您希望使用这些默认值以外的文件，则必须定义系统属性“org.quartz.properties”以指向所需的文件。
				或者，您可以在调用getScheduler()之前通过调用其中一个initialize(xx)方法显式初始化工厂。
		 */

		SchedulerFactory schedulerFactory = new StdSchedulerFactory();

		/*
			2.从工厂中获取调度器实例 调度器实例
				返回此工厂生成的Scheduler的句柄。
				如果之前没有调用其中一个initialize方法，则此方法将调用默认（无参的）initialize()方法。
		 */
		Scheduler scheduler = schedulerFactory.getScheduler();

		/*
			3.创建JobDetail Job实例的详细属性
				定义JobDetail，并设置要执行的Job的类名。
		 */
		JobDetail jobDetail = JobBuilder.newJob(RAMJob.class)
				//job的描述
				.withDescription("这是一个 ram Job实例")
				//job 的name和group
				.withIdentity("ramJob", "ramGroup").build();

		//任务运行的时间，SimpleSchedule类型触发器有效<3秒后启动任务>
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
		log.info("启动时间 : " + new Date());

	}

}
