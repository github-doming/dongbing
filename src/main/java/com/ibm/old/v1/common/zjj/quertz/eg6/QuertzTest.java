package com.ibm.old.v1.common.zjj.quertz.eg6;

import c.a.util.core.test.CommTest;
import com.ibm.old.v1.common.zjj.quertz.HelloJob;
import org.doming.core.tools.DateTool;
import org.doming.develop.job.QuartzUtil;
import org.junit.Test;
import org.quartz.*;

import java.util.Date;


public class QuertzTest extends CommTest{
	@Test
	public void demo(){
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
		String timeStr="11:45:00";
		Date time= DateTool.getTime(timeStr);
		
		JobDataMap map = new JobDataMap();
		map.put("hmExistId", "111111");
		
//		SchedulerFactory factory = new StdSchedulerFactory();
//		Scheduler scheduler = factory.getScheduler();

		JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("job", "group").usingJobData(map).build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group").startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule("0 55 13 * * ?")).build();
//		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group").startNow()
//				.startAt(time).build();
		
		QuartzUtil quartzUtil= QuartzUtil.findInstance();
		
		quartzUtil.scheduleJob(jobDetail, trigger);

		System.out.println("end--------------");
		
	}

	public static void main(String[] args) throws Exception {

		String timeStr="14:20:00";
		Date time=DateTool.getTime(timeStr);
		
		JobDataMap map = new JobDataMap();
		map.put("hmExistId", "111111");
		
		
		
		
//		SchedulerFactory factory = new StdSchedulerFactory();
//		Scheduler scheduler = factory.getScheduler();

		JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("job", "group").usingJobData(map).build();

//		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group").startNow()
//				.withSchedule(CronScheduleBuilder.cronSchedule("0 59 15 * * ?")).build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group").startNow()
				.startAt(time).build();

		QuartzUtil quartzUtil= QuartzUtil.findInstance();
		
		quartzUtil.scheduleJob(jobDetail, trigger);

		System.out.println("end--------------");				
	
	}
	
}
