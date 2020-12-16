package com.ibs.common.core;

import org.doming.core.common.servlet.AsynAction;
import org.doming.develop.http.HttpConfig;

import java.sql.Connection;

/**
 * 后台管理 MVC基类
 *
 * @Author: Dongming
 * @Date: 2020-03-27 10:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@AsynAction(code = HttpConfig.Code.BASE)
public abstract class CommAdminAction extends BaseAdminAction {

	public CommAdminAction() {
		// 需要上下文操作
		super.context = true;
		// 需要数据库操作
		super.database = true;
		// 事物等级
		super.transaction = Connection.TRANSACTION_READ_UNCOMMITTED;
		//数据源	基类
		super.dataSource = "base";
	}
}
