package org.doming.example.quartz.eg;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
/**
 * @Description: Job与JobDetail介绍
 * @Author: Dongming
 * @Date: 2018-10-29 10:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class DumbJob1 implements Job {
	@Override public void execute(JobExecutionContext context) {
		JobKey key = context.getJobDetail().getKey();
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();

		String jobSays = dataMap.getString("jobSays");
		float myFloatValue = dataMap.getFloat("myFloatValue");
		System.out.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
	}
}
