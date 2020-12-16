package com.ibm.old.v1.common.zjj.quertz.eg5;
import com.ibm.old.v1.common.zjj.quertz.HelloJob;
import org.doming.core.tools.DateTool;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
public class QuertzTest {
	public static void main(String[] args) throws SchedulerException {

		String timeStr="16:05:00";
		Date time= DateTool.getTime(timeStr);
		
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();

		JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job", "group").build();

//		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group").startNow()
//				.withSchedule(CronScheduleBuilder.cronSchedule("0 59 15 * * ?")).build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group").startNow()
				.startAt(time).build();
		
		scheduler.scheduleJob(job, trigger);
		scheduler.start();
		System.out.println("end--------------");
	}

}
