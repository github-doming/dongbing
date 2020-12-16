package all.archit.complex.spring.jdbc_template.example.e2;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.WebContentGenerator;

import c.a.util.core.data_source.ay.JdbcDataSourceAy;
import c.a.util.core.data_source.ay.JdbcDataSourceListAy;
import all.archit.complex.spring.jdbc.threadlocal.JdbcTemplateThreadLocal;
/**
 * 
 * 同一个连接conn
 * 
 * http://localhost:8080/a/index.htm http://localhost:8080/a/index.action 切换数据源
 * 
 * 
 * 
 */
public class MvcServletAction extends WebContentGenerator implements Controller {
	private DataSource dataSource;
	// public DataSource getDataSource() {
	// return dataSource;
	// }
	//
	// public void setDataSource(DataSource dataSource) {
	// this.dataSource = dataSource;
	// }
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance()
				.findLocal();

		Connection conn = jdbcDataSource.getConnection();

		System.out.println("demo");
		ServletContext sc = this.getServletContext();
		// 返回
		ModelAndView return_ModelAndView = null;
		// 注入数据源
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// 保存jdbcTemplate
		JdbcTemplateThreadLocal.findThreadLocalJdbcTemplate().set(
				jdbcTemplate);
		DataSourceTransactionManager transactionManager = null;
		// 事务状态
		TransactionStatus ts = null;
		// if (false) {
		if (true) {
			// 注入数据源到事务管理器
			transactionManager = new DataSourceTransactionManager(dataSource);
			// 事务定义类
			DefaultTransactionDefinition cDefaultTransactionDefinition = new DefaultTransactionDefinition();
			ts = transactionManager
					.getTransaction(cDefaultTransactionDefinition);
		}
		try {
			// 业务开始
			return_ModelAndView = this.execute(sc, request, response);
			// 业务结束
			if (true) {
				transactionManager.commit(ts);
			}
		} catch (java.lang.RuntimeException e) {
			e.printStackTrace();
			System.out.println("RuntimeException 事务");
			if (true) {
				transactionManager.rollback(ts);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception 事务");
			if (true) {
				if (ts.isCompleted()) {
				} else {
					transactionManager.rollback(ts);
				}
			}
		} finally {
			return return_ModelAndView;
		}
	}
	public ModelAndView execute(ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager
				.getResource(dataSource);
		Connection con = conHolder.getConnection();
		System.out.println("a1 con=" + con);
		System.out.println("a2 con=" + con.hashCode());
		System.out.println("a3 con.getAutoCommit=" + con.getAutoCommit());
		JdbcTemplate jt = JdbcTemplateThreadLocal
				.findThreadLocalJdbcTemplate().get();
		jt.execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection con) throws SQLException,
					DataAccessException {
				System.out.println("b1 con=" + con);
				System.out.println("b2 con=" + con.hashCode());
				System.out.println("b3 con.getAutoCommit="
						+ con.getAutoCommit());
				String sql = "insert into fun_type_str_t(id,name) values(17,'a')";
				Statement s = con.createStatement();
				return s.execute(sql);
			}
		});
		// int i=1/0;
		jt.execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection con) throws SQLException,
					DataAccessException {
				System.out.println("c1 con=" + con);
				System.out.println("c2 con=" + con.hashCode());
				System.out.println("c3 con.getAutoCommit="
						+ con.getAutoCommit());
				String sql = "insert into fun_type_str_t(id,name) values(18,'a')";
				Statement s = con.createStatement();
				return s.execute(sql);
			}
		});
		return new ModelAndView("/sss/tools/mvc/example/v1/demo");
	}
}
