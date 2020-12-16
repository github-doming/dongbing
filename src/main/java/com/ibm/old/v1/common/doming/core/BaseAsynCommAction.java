package com.ibm.old.v1.common.doming.core;

import java.sql.Connection;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-12-19 10:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseAsynCommAction extends BaseAsynMvcAction {

	public BaseAsynCommAction() {
		// 需要上下文操作
		super.context = true;
		// 需要数据库操作
		super.database = true;
		// 事物等级
		super.transaction = Connection.TRANSACTION_REPEATABLE_READ;
		//跨域
		super.otherOrigin = true;
		//线程池	核心
		super.code = "core" ;
	}

}
