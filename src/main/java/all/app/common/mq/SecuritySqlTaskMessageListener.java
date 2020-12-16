package all.app.common.mq;
import java.sql.Connection;
import java.util.ArrayList;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.transaction.TransactionBase;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.mq.common.CommMq;
/**
 * 
 * 从队列取出sql并执行
 * @Description: 
 * @ClassName: SecuritySqlTaskMessageListener 
 * @date 2018年11月7日 下午9:54:07 
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class SecuritySqlTaskMessageListener extends CommMq {
	@Override
	public String execute(TextMessage textMessage) throws Exception {
		try {
			String sql = textMessage.getText();
			this.executeSql(sql);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void executeSql(String sql) {
		TransactionBase transactionBase=new TransactionBase();
		IJdbcTool jdbcToolSecurity = null;
		IJdbcUtil jdbcUtilSecurity = null;
		Connection connSecurity = null;
		ArrayList<Object> parameterList = null;
		try {
			jdbcToolSecurity = transactionBase.findJdbcTool("security");
			jdbcUtilSecurity = jdbcToolSecurity.getJdbcUtil();
			connSecurity = jdbcUtilSecurity.getConnection();
			transactionBase.transactionStart(jdbcToolSecurity);
			parameterList = new ArrayList<Object>();
			jdbcUtilSecurity.execute(connSecurity, sql, parameterList);
			transactionBase.transactionEnd(jdbcToolSecurity);
		} catch (Exception e) {
			e.printStackTrace();
			transactionBase.transactionRoll(jdbcToolSecurity);
		}
		transactionBase.transactionClose(jdbcToolSecurity);
	}
}
