package c.a.util.mq.common;
import javax.jms.TextMessage;
/**
 * 执行基类，这个类采用模版模式进行实现。
 * 
 * 子类继承这个类后，执行的日志就会自动记录下来，
 * 
 * 不需要在子类中在进行记录。
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public abstract class CommMq extends TransactionMq {
	public CommMq() {
		// 需要数据库操作
		this.database = true;
		// 需要事务操作
		transaction = true;
	}
	// -- 下面添加新的方法 --/
	// -- 下面的方法不再更新 --//
	// {
	@Override
	public String executeTransaction(TextMessage textMessage) throws Exception {
		String returnStr = null;
		returnStr = execute(textMessage);
		return returnStr;
	}
	// }
	// -- 上面的方法不再更新 --/
	public abstract String execute(TextMessage textMessage) throws Exception;
}
