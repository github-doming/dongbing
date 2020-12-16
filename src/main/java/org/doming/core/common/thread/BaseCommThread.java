package org.doming.core.common.thread;
import java.sql.Connection;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-12-22 10:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseCommThread extends BaseTransactionThread {
	public BaseCommThread() {
		// 需要上下文操作
		super.context = true;
		// 需要数据库操作
		super.database = true;
		// 事物等级
		super.transaction = Connection.TRANSACTION_REPEATABLE_READ;
	}

	// -- 下面的方法不再更新 --//
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
