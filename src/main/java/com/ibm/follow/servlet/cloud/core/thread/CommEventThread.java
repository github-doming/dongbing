package com.ibm.follow.servlet.cloud.core.thread;
import org.doming.core.common.thread.BaseCommThread;

import java.sql.Connection;
/**
 * @Description: 事件线程基类
 * @Author: Dongming
 * @Date: 2019-07-04 17:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class CommEventThread extends BaseCommThread {
	public CommEventThread() {
		super.transaction = Connection.TRANSACTION_NONE;
		super.dataSource = "base";
	}
}
