package com.ibs.common.core;
import org.doming.core.common.servlet.AsynAction;
import org.doming.core.tools.DateTool;
import org.doming.develop.http.HttpConfig;

import java.sql.Connection;
/**
 * 核心 MVC基类
 *
 * @Author: null
 * @Date: 2020-05-22 16:44
 * @Version: v1.0
 */
@AsynAction(code = HttpConfig.Code.CORE) public abstract class CommCoreAction extends BaseUserAction {

	public CommCoreAction() {
		// 需要上下文操作
		super.context = true;
		// 需要数据库操作
		super.database = true;
		// 事物等级
		super.transaction = Connection.TRANSACTION_REPEATABLE_READ;
	}

	/**
	 * 判断时间是否在4点10分-7点之间
	 *
	 * @return
	 */
	protected boolean denyTime() {
		return System.currentTimeMillis() > DateTool.getTime("04:10:00").getTime()
				&& System.currentTimeMillis() < DateTool.getTime("07:00:00").getTime();
	}
}
