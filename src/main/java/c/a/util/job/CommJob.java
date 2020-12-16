package c.a.util.job;
import all.gen.sys_quartz_log.t.entity.SysQuartzLogT;
import c.a.tools.jdbc.IJdbcTool;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.json.JsonTcpBean;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;

import java.sql.Connection;
import java.util.Date;
/**
 * 任务执行基类，这个类采用模版模式进行实现。
 * 
 * 子类继承这个类后，任务执行的日志就会自动记录下来，
 * 
 * 不需要在子类中在进行记录。
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
// quartz中设置Job不并发执行
@DisallowConcurrentExecution
public abstract class CommJob extends TransactionJob {
	public CommJob() {
		// 需要数据库操作
		this.database = true;
		// 需要事务操作
		transaction = true;
	}

	@Override
	public String executeTransaction(JobExecutionContext context) throws Exception {
		String returnStr = null;
		String jobName = context.getJobDetail().getKey().getName();
		String jobClassName = context.getJobDetail().getJobClass().getName();
		String triggerName = "exception_";
		Trigger trigger = context.getTrigger();
		if (trigger != null) {
			triggerName = trigger.getKey().getName();
		}
		Date timeStartDate = new Date();
		long timeStart = System.currentTimeMillis();
		try {
			returnStr = executeJob(context);
			long timeEnd = System.currentTimeMillis();
			Date timeEndDate = new Date();
			// 记录日志
			long runTime = (timeEnd - timeStart) / 1000;
			saveLog(jobClassName, jobName, triggerName, timeStartDate, timeEndDate, runTime, "任务执行成功!",
					TaskStateEnum.SUCCESS.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
			long timeEnd = System.currentTimeMillis();
			Date timeEndDate = new Date();
			long timeRun = (timeEnd - timeStart) / 1000;
			try {
				saveLog(jobClassName, jobName, triggerName, timeStartDate, timeEndDate, timeRun, e1.toString(),
						TaskStateEnum.ERROR.toString());
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return returnStr;
	}

	public abstract String executeJob(JobExecutionContext context) throws Exception;
	/**
	 * 
	 * 
	 */
	private void saveLog(String jobClassName, String jobName, String triggerName, Date startTime, Date endTime,
			long runTime, String content, String state) throws Exception {
		IJdbcUtil jdbcUtil = null;
		Connection conn = null;
		IJdbcTool jdbcTool = null;
		try {
			jdbcTool = this.findJdbcToolLocal();
			jdbcUtil = jdbcTool.getJdbcUtil();
			conn = jdbcUtil.getConnection();
			this.transactionStart(jdbcTool);
			SysQuartzLogT sysQuartzLog = new SysQuartzLogT();
			sysQuartzLog.setJobName(jobName);
			sysQuartzLog.setJobClassName(jobClassName);
			sysQuartzLog.setTriggerName(triggerName);
			sysQuartzLog.setStartTime(startTime);
			sysQuartzLog.setEndTime(endTime);
			sysQuartzLog.setContent(content);
			sysQuartzLog.setState(state);
			sysQuartzLog.setRunTime(runTime);
			jdbcTool.insertObject(conn , sysQuartzLog);
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			log.error("系统异常:",e);
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
	}
	/**
	 * 
	 */
	public String returnJson(JsonTcpBean _JsonTcpBean) throws Exception {
		return null;
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
