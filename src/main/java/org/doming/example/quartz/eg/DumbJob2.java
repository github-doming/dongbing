package org.doming.example.quartz.eg;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @Description: Job与JobDetail介绍
 * @Author: Dongming
 * @Date: 2018-10-29 10:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class DumbJob2 implements Job {

	private String jobSays;
	private float myFloatValue;
	private List<Date> state;

	@Override public void execute(JobExecutionContext context) {

		JobKey key = context.getJobDetail().getKey();
		JobDataMap dataMap = context.getMergedJobDataMap();
		if (state == null) {
			state = new ArrayList<>();
		}
		state.add(new Date());
		System.out.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue
				+ ", and List length: " + state.size());
	}

	public void setJobSays(String jobSays) {
		this.jobSays = jobSays;
	}
	public void setMyFloatValue(float myFloatValue) {
		this.myFloatValue = myFloatValue;
	}
	public void setState(List<Date> state) {
		this.state = state;
	}
}
