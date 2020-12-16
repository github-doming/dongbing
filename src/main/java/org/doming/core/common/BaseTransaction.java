package org.doming.core.common;
import c.a.config.core.CommContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.Executor;

import java.sql.Connection;
/**
 * @Description: 事务类
 * @Author: Dongming
 * @Date: 2018-12-13 15:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.1
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public abstract class BaseTransaction implements Executor {
	protected static final Logger log = LogManager.getLogger(BaseTransaction.class);
	private long startCalendarLong = 0;

	protected boolean context;
	protected boolean database;
	protected String dataSource;
	protected int transaction;

	/**
	 * 执行方法
	 *
	 * @param inVar 输入参数
	 * @return 执行结果
	 */
	@Override public String run(String inVar) {
		long startTime = System.currentTimeMillis();

		//如果需要事物，肯定需要上下文
		if (database) {
			context = true;
		}
		String result = null;
		if (context) {
			//获取上下文
			CommContextUtil commContextUtil = new CommContextUtil();
			try {
				startCalendarLong = commContextUtil.start();
				if (database) {
					// 需要数据库操作
					result = this.executeDatabase(inVar);
				} else {
					// 不需要需要数据库操作
					result = this.executeOnly(inVar);
				}
			} catch (Exception e) {
				log.error("上下文执行出错", e);
			} finally {
				commContextUtil.end(startCalendarLong);
			}
		} else {
			// 不需要上下文操作
			try {
				result = this.executeOnly(inVar);
			} catch (Exception e) {
				log.error("执行出错", e);
			} finally {
				long endTime = System.currentTimeMillis();
				log.debug("花费时间spend time=" + (endTime - startTime));
			}
		}
		return result;
	}

	/**
	 * 仅执行
	 *
	 * @param msg 执行消息
	 * @return 执行结果
	 */
	private String executeOnly(String msg) throws Exception {
		return execute(msg);
	}

	/**
	 * 需要数据库操作
	 *
	 * @param msg 执行消息
	 * @return 执行结果
	 */
	private String executeDatabase(String msg) throws Exception {
		String result;
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
					result = execute(msg);
					tb.endTransaction(connection);
				} catch (Exception e) {
					tb.rollTransaction(connection);
					throw e;
				}
			} else {
				//数据库连接不支持事物
				result = execute(msg);
			}
		} finally {
			tb.connectionEnd();
			CurrentTransaction.removeDataBase();
		}
		return result;
	}
}