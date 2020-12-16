package com.ibm.old.v1.common.zjj.quertz.eg2;

import com.ibm.old.v1.common.zjj.quertz.HelloJob;
import org.quartz.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
public class QuertzTest {
	public static void main(String[] args) throws SchedulerException {
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

		Scheduler sched = schedFact.getScheduler();

		sched.start();

		// define the job and tie it to our HelloJob class
		JobDetail job = newJob(HelloJob.class).withIdentity("myJob", "group1").build();

		// Trigger the job to run now, and then every 40 seconds
		Trigger trigger = newTrigger().withIdentity("myTrigger", "group1").startNow()
				.withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();

		
		// Tell quartz to schedule the job using our trigger
		sched.scheduleJob(job, trigger);
		
	}
}
