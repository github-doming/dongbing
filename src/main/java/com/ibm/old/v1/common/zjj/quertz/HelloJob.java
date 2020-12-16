package com.ibm.old.v1.common.zjj.quertz;

import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;



public class HelloJob extends BaseCommJob {

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		// TODO Auto-generated method stub
		JobDataMap map = context.getMergedJobDataMap();
		String hmExistId = map.getString("hmExistId");
		
		System.out.println(hmExistId);
		for(int i=0;i<10;i++){

			System.out.println("test-------------------");
		}
	}

}
