package org.doming.example.quartz.eg6.job.runclass;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-12-16 10:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class JobWork implements Job {
	@Override public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(this);

		System.out.println("向Quartz说你好" + new Date());
	}
}
