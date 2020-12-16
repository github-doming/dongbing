package com.ibm.common.core;

import com.ibm.common.core.servlet.BaseUserAction;
import org.doming.core.common.servlet.AsynAction;
import org.doming.develop.http.HttpConfig;

import java.sql.Connection;
/**
 * @Description: 系统 MVC基类
 * @Author: Dongming
 * @Date: 2019-06-19 13:47
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@AsynAction(code = HttpConfig.Code.BASE)
public abstract class CommBaseAction extends BaseUserAction {


	public CommBaseAction() {
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
