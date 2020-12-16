package com.ibm.common.core;

import com.ibm.common.core.servlet.BaseUserAction;
import org.doming.core.common.servlet.AsynAction;
import org.doming.develop.container.LruMap;
import org.doming.develop.http.HttpConfig;

import java.sql.Connection;

/**
 * @Description: 查询 MVC基类
 * @Author: Dongming
 * @Date: 2019-06-19 13:47
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@AsynAction(code = HttpConfig.Code.QUERY)
public abstract class CommQueryAction extends BaseUserAction {
	static {
		lruMap = new LruMap<>(500);
	}
	public CommQueryAction() {
		// 需要上下文操作
		super.context = true;
		// 需要数据库操作
		super.database = true;
		// 事物等级
		super.transaction = Connection.TRANSACTION_NONE;
		//数据源	查询
		super.dataSource = "query";

		accessAttr(true,5, 1000);
	}
}
