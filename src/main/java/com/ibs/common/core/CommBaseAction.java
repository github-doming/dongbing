package com.ibs.common.core;
import org.doming.core.common.servlet.AsynAction;
import org.doming.develop.http.HttpConfig;

import java.sql.Connection;
/**
 * 核心 MVC基类
 *
 * @Author: null
 * @Date: 2020-05-19 17:21
 * @Version: v1.0
 */
@AsynAction(code = HttpConfig.Code.BASE) public abstract class CommBaseAction extends BaseUserAction {

	public CommBaseAction() {
		// 需要上下文操作
		super.context = true;
		// 需要数据库操作
		super.database = true;
		// 事物等级
		super.transaction = Connection.TRANSACTION_READ_COMMITTED;
		//数据源	基类
		super.dataSource = "base";
	}
}
