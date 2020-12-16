package org.doming.core.common.quartz;

import all.gen.sys_quartz_log.t.entity.SysQuartzLogT;
import all.gen.sys_quartz_log.t.service.SysQuartzLogTService;
import c.a.tools.jdbc.IJdbcTool;
import c.a.util.core.enums.bean.TaskStateEnum;
import org.doming.core.CommMethod;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.TransactionsBase;
import org.doming.core.tools.RandomTool;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;

import java.sql.Connection;
import java.util.Date;
/**
 * @Description: job中心类
 * @Author: Dongming
 * @Date: 2018-12-07 19:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseCommJob extends BaseTransactionJob implements CommMethod {

	protected boolean saveLog;

	// -- 下面的方法不再更新 --//

	public BaseCommJob() {
		// 需要上下文操作
		super.context = true;
		// 需要数据库操作
		super.database = true;
		// 事物等级
		super.transaction = Connection.TRANSACTION_REPEATABLE_READ;
		//保存日志
		this.saveLog = true;
	}

	/**
	 * 记录日志
	 *
	 * @param context 工作传参
	 */

	@Override public void run(JobExecutionContext context) throws Exception {
		if (context == null) {
			try {
				executeJob(null);
			} catch (Exception e) {
				log.error("执行工作失败", e);
			}
			return;
		}
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
			executeJob(context);
			long timeEnd = System.currentTimeMillis();
			Date timeEndDate = new Date();
			// 记录日志
			long runTime = (timeEnd - timeStart) / 1000;
			if (saveLog) {
				saveLog(jobClassName, jobName, triggerName, timeStartDate, timeEndDate, runTime, "任务执行成功!",
						TaskStateEnum.SUCCESS.toString());
			}
		} catch (Exception e) {
			long timeEnd = System.currentTimeMillis();
			Date timeEndDate = new Date();
			long timeRun = (timeEnd - timeStart) / 1000;
			saveLog(jobClassName, jobName, triggerName, timeStartDate, timeEndDate, timeRun, e.toString(),
					TaskStateEnum.ERROR.toString());
			throw e;
		}
	}

	/**
	 * 保存日志
	 *
	 * @param jobClassName 工作累文件
	 * @param jobName      工作名称
	 * @param triggerName  触发器名称
	 * @param startTime    开始时间
	 * @param endTime      结束时间
	 * @param runTime      运行时间
	 * @param content      日志正文
	 * @param state        状态
	 */
	private void saveLog(String jobClassName, String jobName, String triggerName, Date startTime, Date endTime,
			long runTime, String content, String state) {
		TransactionsBase db = CurrentTransaction.getDataBase();
		IJdbcTool jdbcTool = db.transactionStart(null);
		try {
			SysQuartzLogT sysQuartzLog = new SysQuartzLogT();
			sysQuartzLog.setJobName(jobName);
			sysQuartzLog.setJobClassName(jobClassName);
			sysQuartzLog.setTriggerName(triggerName);
			sysQuartzLog.setStartTime(startTime);
			sysQuartzLog.setEndTime(endTime);
			sysQuartzLog.setContent(content);
			sysQuartzLog.setCreateTime(new Date());
			sysQuartzLog.setCreateTimeLong(System.currentTimeMillis());
			sysQuartzLog.setUpdateTime(new Date());
			sysQuartzLog.setCreateTimeLong(System.currentTimeMillis());
			sysQuartzLog.setState(state);
			sysQuartzLog.setRunTime(runTime);
			new SysQuartzLogTService().save(sysQuartzLog);
		} catch (Exception e) {
			log.error("保存定时器执行日志失败", e);
			db.transactionRoll();
		} finally {
			db.transactionFinish(jdbcTool);
		}
	}
	/**
	 * 睡眠
	 */
	protected void sleep() throws InterruptedException {
		Thread.sleep(RandomTool.getInt(500, 1500));
	}

	/**
	 * 工作执行内容
	 *
	 * @param context 工作传参
	 * @throws Exception 执行出错
	 */
	public abstract void executeJob(JobExecutionContext context) throws Exception;

	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
