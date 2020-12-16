package org.doming.example.quartz.eg2.jdbc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.LogTool;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-12-06 15:38
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MyJob implements Job {

	protected static final Logger log = LogManager.getLogger(MyJob.class);

	@Override public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("MyJob 已启动" + LogTool.printHashtagS());
		log.info("你好 定时器 " + DateTool.getCnStr());
		log.info("MyJob 已结束" + LogTool.printHashtagS());
	}
}
