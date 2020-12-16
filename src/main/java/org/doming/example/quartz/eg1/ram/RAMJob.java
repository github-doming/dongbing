package org.doming.example.quartz.eg1.ram;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-12-06 14:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RAMJob implements Job {
	private static Logger log = LoggerFactory.getLogger(RAMJob.class);

	@Override public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("向Quartz说你好" + new Date());
		System.out.println("向Quartz说你好" + new Date());
	}
}
