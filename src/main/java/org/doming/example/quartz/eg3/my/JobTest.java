package org.doming.example.quartz.eg3.my;
import c.a.config.core.JdbcDataSourceConfig;
import c.a.util.core.test.CommTest;
import org.doming.core.tools.DateTool;
import org.doming.develop.job.QuartzUtil;
import org.doming.example.quartz.eg2.jdbc.MyJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.Date;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-01-03 10:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class JobTest extends CommTest {

	public static void main(String[] args) throws Exception {
		JobTest test = new JobTest();
		test.startTest();
		test.testJob();
		test.endTest();
	}

	private void testJob() throws Exception {
		String driver = JdbcDataSourceConfig.findInstance().getDriver();
		String url = JdbcDataSourceConfig.findInstance().getUrl();
		String username = JdbcDataSourceConfig.findInstance().getUsername();
		String password = JdbcDataSourceConfig.findInstance().getPassword();
		String machineCode = "doming" ;
		QuartzUtil.dbConfig(driver, url, username, password, machineCode);

		System.out.println("begin");
		String betAutoStartTimeStr = "11:14:46" ;
		Date betAutoStartTime = DateTool.getTime(betAutoStartTimeStr);
		System.out.println(DateTool.get(betAutoStartTime));

		JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withDescription("工作").build();
		Trigger trigger = TriggerBuilder.newTrigger().withDescription("触发器").startAt(betAutoStartTime).build();

		QuartzUtil.findInstance().scheduleJob(jobDetail, trigger);
//		QuartzUtil.findInstance().shutdown();
	}

}
