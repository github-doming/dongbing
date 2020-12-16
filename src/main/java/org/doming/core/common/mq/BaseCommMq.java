package org.doming.core.common.mq;
import java.sql.Connection;
/**
 * @Description:
 * @Author: null
 * @Date: 2018-12-03 14:44
 * @Version: v1.0
 */
public abstract class BaseCommMq extends BaseTransactionMq {


	public BaseCommMq() {
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
	/**
	 * 验证参数
	 * 传入验证信息
	 *
	 * @param message 待验证信息
	 * @return 初始化参数，并验证结果 失败	true
	 */
	protected boolean valiParameter(String message) {
		return true;
	}

	/**
	 * 获取log信息
	 * @param message 消息
	 * @return log信息
	 */
	protected  String getLog(String message){
		return "对象["+this.hashCode()+"] ~ ".concat(message);
	}

}
