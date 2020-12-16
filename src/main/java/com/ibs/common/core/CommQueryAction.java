package com.ibs.common.core;
import org.doming.core.common.servlet.AsynAction;
import org.doming.develop.container.LruMap;
import org.doming.develop.http.HttpConfig;

import java.sql.Connection;
/**
 * 查询 MVC基类
 *
 * @Author: null
 * @Date: 2020-05-25 10:35
 * @Version: v1.0
 */
@AsynAction(code = HttpConfig.Code.QUERY) public abstract class CommQueryAction extends BaseUserAction {
	static {
		lruMap = new LruMap<>(500);
	}

	public CommQueryAction() {
		// 需要上下文操作
		super.context = true;
		// 需要数据库操作
		super.database = true;
		// 事物等级
		super.transaction = Connection.TRANSACTION_READ_UNCOMMITTED;
		//数据源	查询
		super.dataSource = "query";

		/*
		 * 访问控制
		 * 三秒内只允许请求 5 次
		 */
		accessAttr(true, 5, 3000);
	}
}
