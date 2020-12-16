package com.cloud.common.core;
import c.a.config.core.CommContextUtil;
import c.a.util.core.log.LogUtil;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.TransactionsBase;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
/**
 * 公共测试类
 * @Author: Dongming
 * @Date: 2020-05-19 17:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CommTest extends TransactionsBase {

	private long startCalendarLong = 0;

	/**
	 * 测试开始
	 */
	@Before public void startTest() throws Exception {
		CommContextUtil commContextUtil = new CommContextUtil();
		startCalendarLong = commContextUtil.start();
		String servletContextPath="d:\\";
		LogUtil.findInstance().init(servletContextPath);
		transaction = Connection.TRANSACTION_REPEATABLE_READ;
		CurrentTransaction.setDatabase(this);
	}
	/**
	 * 测试结束
	 */
	@After public void endTest() {
		CurrentTransaction.removeDataBase();
		CommContextUtil commContextUtil = new CommContextUtil();
		commContextUtil.end(startCalendarLong);
	}
}
