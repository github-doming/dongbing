package all.archit.complex.spring.jdbc_template.example.e3;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import all.archit.complex.spring.crud.TransactionSpringByAction;
import all.archit.complex.spring.jdbc.threadlocal.JdbcTemplateThreadLocal;
import c.a.util.core.data_source.ay.JdbcDataSourceAy;
import c.a.util.core.data_source.ay.JdbcDataSourceListAy;
import c.a.util.core.json.JsonTcpBean;

/**
 * 
 * 同一个连接conn
 * 
 * http://localhost:8080/a/index.htm;
 * 
 * http://localhost:8080/a/index.action 切换数据源;
 * 
 * 
 * 
 */
public class MvcServletAction extends TransactionSpringByAction {
	public MvcServletAction() {
		this.database = true;
		// this.transaction=true;
	}

	@Override
	public  JsonTcpBean executeTransaction() throws Exception {
		JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance().findLocal();

		Connection conn = jdbcDataSource.getConnection();

		Connection con = jdbcDataSource.getConnection();
		System.out.println("a1 con=" + con);
		System.out.println("a2 con=" + con.hashCode());
		System.out.println("a3 con.getAutoCommit=" + con.getAutoCommit());
		JdbcTemplate jt = JdbcTemplateThreadLocal.findThreadLocalJdbcTemplate().get();
		jt.execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection con) throws SQLException, DataAccessException {
				System.out.println("b1 con=" + con);
				System.out.println("b2 con=" + con.hashCode());
				System.out.println("b3 con.getAutoCommit=" + con.getAutoCommit());
				// String sql = "insert into fun_type_str_t(id,name)
				// values(23,'a')";
				String sql = "insert into fun_type_str_t(name) values('a')";
				Statement s = con.createStatement();
				return s.execute(sql);
			}
		});
		int i = 1 / 0;
		jt.execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection con) throws SQLException, DataAccessException {
				System.out.println("c1 con=" + con);
				System.out.println("c2 con=" + con.hashCode());
				System.out.println("c3 con.getAutoCommit=" + con.getAutoCommit());
				// String sql = "insert into fun_type_str_t(id,name)
				// values(25,'a')";
				String sql = "insert into fun_type_str_t(name) values('a')";
				Statement s = con.createStatement();
				return s.execute(sql);
			}
		});
		return this.returnJsonTcpBean("/sss/tools/mvc/example/v1/demo");
	}
}
