package com.ibs.common.core;
import c.a.config.core.CommContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.TransactionsBase;
import org.doming.core.common.servlet.AsynAction;
import org.doming.core.common.servlet.MvcExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
/**
 * MVC 执行事物基类
 *
 * @Author: null
 * @Date: 2020-05-19 13:37
 * @Version: v1.0
 */
@AsynAction public abstract class BaseMvcTransactions extends TransactionsBase implements MvcExecutor {
	protected static final Logger log = LogManager.getLogger(BaseMvcTransactions.class);

	private long startCalendarLong = 0;

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	boolean context = false;
	boolean database = false;
	String dataSource;
	int transaction;

	/**
	 * 执行方法
	 *
	 * @return 执行结果
	 * @throws Exception 执行失败
	 */
	public abstract Object run() throws Exception;

	@Override public Boolean otherOrigin() {
		return true;
	}

	@Override public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long startTime = System.currentTimeMillis();
		//如果需要事物，肯定需要上下文
		this.request = request;
		this.response = response;
		if (database) {
			context = true;
		}
		Object result = null;
		if (context) {
			//获取上下文
			CommContextUtil commContextUtil = new CommContextUtil();
			try {
				startCalendarLong = commContextUtil.start();
				if (database) {
					// 需要数据库操作
					result = this.executeDatabase();
				} else {
					// 不需要需要数据库操作
					result = this.executeOnly();
				}
			} catch (Exception e) {
				log.warn("上下文执行出错", e);
			} finally {
				commContextUtil.end(startCalendarLong);
			}
		} else {
			// 不需要上下文操作
			try {
				result = this.executeOnly();
			} catch (Exception e) {
				log.warn("执行出错", e);
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
		TransactionsBase tb = new TransactionsBase();
		CurrentTransaction.setDatabase(tb);
		try {
			//开始jdbc链接
			Connection connection = tb.connectionBegin(dataSource);
			if (transaction != Connection.TRANSACTION_NONE && connection.getTransactionIsolation() != Connection.TRANSACTION_NONE) {
				//数据库连接支持事物
				try {
					tb.beginTransaction(connection, transaction);
					result = run();
					tb.endTransaction(connection);
				} catch (Exception e) {
					tb.rollTransaction(connection);
					throw e;
				}
			} else {
				//数据库连接不支持事物
				result = run();
			}
		} finally {
			tb.connectionEnd();
			CurrentTransaction.removeDataBase();
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
