package com.ibm.old.v1.common.doming.core;
import c.a.config.core.CommContextUtil;
import c.a.util.core.log.LogUtil;
import org.doming.core.common.TransactionsBase;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-06-11 14:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BaseTest extends TransactionsBase {
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
	}
	/**
	 * 测试结束
	 */
	@After public void endTest() {
		CommContextUtil commContextUtil = new CommContextUtil();
		commContextUtil.end(startCalendarLong);
	}
}
