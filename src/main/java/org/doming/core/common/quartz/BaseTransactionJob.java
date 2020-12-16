package org.doming.core.common.quartz;
import c.a.config.core.CommContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.TransactionsBase;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.sql.Connection;
/**
 * @Description: job事务类
 * @Author: Dongming
 * @Date: 2018-12-07 18:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseTransactionJob implements Job {
	protected static final Logger log = LogManager.getLogger(BaseTransactionJob.class);
	private long startCalendarLong = 0;

	protected boolean context;
	protected boolean database;
	protected String dataSource;
	protected int transaction;

	/**
	 * 执行定时器工作
	 *
	 * @param context 工作传参
	 * @throws Exception sql执行错误,io错误
	 */
	public abstract void run(JobExecutionContext context) throws Exception;

	@Override public void execute(JobExecutionContext executionContext)  {
		long startTime = System.currentTimeMillis();

		//如果需要事物，肯定需要上下文
		if (database) {
			context = true;
		}
		if (context) {
			//获取上下文
			CommContextUtil commContextUtil = new CommContextUtil();
			try {
				startCalendarLong = commContextUtil.start();
				if (database) {
					// 需要数据库操作
					this.executeDatabase(executionContext);
				} else {
					// 不需要需要数据库操作
					this.executeOnly(executionContext);
				}
			} catch (Exception e) {
				log.error("上下文执行出错", e);
			} finally {
				commContextUtil.end(startCalendarLong);
			}
		} else {
			// 不需要上下文操作
			try {
				this.executeOnly(executionContext);
			} catch (Exception e) {
				log.error("执行出错", e);
			} finally {
				long endTime = System.currentTimeMillis();
				log.debug("花费时间spend time=" + (endTime - startTime));
			}
		}
	}

	/**
	 * 仅执行
	 *
	 * @param context 执行消息
	 */
	private void executeOnly(JobExecutionContext context) throws Exception {
		run(context);
	}

	/**
	 * 有数据库参与执行
	 *
	 * @param context 执行消息
	 */
	private void executeDatabase(JobExecutionContext context) throws Exception {
		TransactionsBase tb = new TransactionsBase();
		CurrentTransaction.setDatabase(tb);
		try {
			//开始jdbc链接
			Connection connection = tb.connectionBegin(dataSource);
			if (transaction != Connection.TRANSACTION_NONE
					&& connection.getTransactionIsolation() != Connection.TRANSACTION_NONE) {
				//数据库连接支持事物
				try {
					tb.beginTransaction(connection, transaction);
					run(context);
					tb.endTransaction(connection);
				} catch (Exception e) {
					tb.rollTransaction(connection);
					throw e;
				}
			} else {
				//数据库连接不支持事物
				run(context);
			}
		} finally {
			tb.connectionEnd();
			CurrentTransaction.removeDataBase();
		}

	}

}
