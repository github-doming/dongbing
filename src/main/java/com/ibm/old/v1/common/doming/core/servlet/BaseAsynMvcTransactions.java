package com.ibm.old.v1.common.doming.core.servlet;
import c.a.config.core.CommContextUtil;

import java.sql.Connection;
/**
 * @Description: 异步mvc执行事物基类
 * @Author: Dongming
 * @Date: 2019-05-18 13:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseAsynMvcTransactions extends BaseAsynMvcExecutor {
	private long startCalendarLong = 0;

	protected boolean context = false;
	protected boolean database = false;
	protected String source;



	/**
	 * 执行方法
	 *
	 * @return 执行结果
	 * @throws Exception 执行失败
	 */
	public abstract Object run() throws Exception;

	@Override public Object call() throws Exception {
		long startTime = System.currentTimeMillis();
		//如果需要事物，肯定需要上下文
		if (database) {
			context = true;
		}

		Object result;
		if (context) {
			//获取上下文
			CommContextUtil commContextUtil = new CommContextUtil();
			try {
				commContextUtil.start();
				if (database) {
					// 需要数据库操作
					result = this.executeDatabase();
				} else {
					// 不需要需要数据库操作
					result = this.executeOnly();
				}
			} catch (Exception e) {
				log.error("上下文执行出错", e);
				throw e;
			} finally {
				commContextUtil.end(startCalendarLong);
			}
		} else {
			// 不需要上下文操作
			try {
				result = this.executeOnly();
			} catch (Exception e) {
				log.error("执行出错", e);
				throw e;
			} finally {
				long endTime = System.currentTimeMillis();
				log.debug("花费时间spend time=" + (endTime - startTime));
			}
		}
		return result;
	}
	/**
	 * 需要数据库操作
	 *
	 * @return 执行结果
	 */
	private Object executeDatabase() throws Exception {
		Object result;
		try {
			//开始jdbc链接
			Connection connection = connectionBegin(source);
			if (connection.getTransactionIsolation() != Connection.TRANSACTION_NONE) {
				//数据库连接支持事物
				try {
					beginTransaction(connection, transaction);
					result = run();
					endTransaction(connection);
				} catch (Exception e) {
					rollTransaction(connection);
					throw e;
				}
			} else {
				//数据库连接不支持事物
				result = run();
			}
		} finally {
			this.connectionEnd();
		}
		return result;
	}
	/**
	 * 仅执行
	 *
	 * @return 执行结果
	 */
	private Object executeOnly() throws Exception {
		return run();
	}
}
