package c.a.util.activiti.util;
import java.sql.Connection;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
/**
 * 
 * 自定义事务工厂
 * @Description: 
 * @ClassName: ActJdbcTransactionFactory 
 * @date 2018年7月13日 下午5:51:15 
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class ActJdbcTransactionFactory extends JdbcTransactionFactory {
    @Override
    public void setProperties(Properties props) {
        super.setProperties(props);
    }
    @Override
    public Transaction newTransaction(Connection conn) {
//      return super.newTransaction(conn);
        return new ActJdbcTransaction(conn);
    }
    @Override
    public Transaction newTransaction(DataSource dataSource,
            TransactionIsolationLevel level, boolean autoCommit) {
//      return super.newTransaction(dataSource, level, autoCommit);
        return new ActJdbcTransaction(dataSource, level, autoCommit);
    }
}