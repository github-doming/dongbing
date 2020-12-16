package c.a.util.core.thread.common;

import c.a.util.core.json.JsonTcpBean;

/**
 * 
 * 支持事务
 * 
 * 不需要登录
 * 
 * 不需要菜单允许
 * 
 * 
 */
public abstract class CommThread extends TransactionThread {
	public CommThread() {
		// 需要数据库操作
		this.database = true;
		// 需要事务操作	
		transaction = true;
	}
	// -- 下面添加新的方法 --/
	// -- 下面的方法不再更新 --//
	// {
	@Override
	public  JsonTcpBean executeTransaction() throws Exception {
		return this.returnJsonTcpBean(this.execute());
	}
	public abstract String execute() throws Exception;
	// }
	// -- 上面的方法不再更新 --/
}
