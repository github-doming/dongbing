package org.doming.core.common.thread;
import org.doming.core.common.BaseTransaction;
/**
 * @Description: 线程事务类
 * @Author: Dongming
 * @Date: 2018-12-22 10:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseTransactionThread extends BaseTransaction implements Runnable {

	@Override public void run() {
		try {
			super.run(null);
		} catch (Exception e) {
			log.error("执行线程错误=".concat(this.getClass().getName()));
		}

	}
}
