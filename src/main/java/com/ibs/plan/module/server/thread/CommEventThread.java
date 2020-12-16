package com.ibs.plan.module.server.thread;

import org.doming.core.common.thread.BaseCommThread;

import java.sql.Connection;

/**
 * @Description: 事件线程基类
 * @Author: null
 * @Date: 2020-05-23 10:54
 * @Version: v1.0
 */
public class CommEventThread extends BaseCommThread {

	public CommEventThread(){
		super.transaction = Connection.TRANSACTION_NONE;
		super.dataSource = "base";
	}
}
