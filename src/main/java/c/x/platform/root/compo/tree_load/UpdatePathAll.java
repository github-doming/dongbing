package c.x.platform.root.compo.tree_load;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.http.util.Asserts;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.jdbc.bean.nut.JdbcPrepareStatementDto;
import c.a.util.core.jdbc.nut.IJdbcUtil;

/**
 * 
 * 更新所有path
 * 
 * @author yourname
 * 
 */
public class UpdatePathAll {
	protected Logger log = LogManager.getLogger(this.getClass());
	public static void main(String[] args) {
		UpdatePathAll u = new UpdatePathAll();
		// u.update("gen_single_simple");
		u.update("gen_single_simple", "id");
		// log.trace("end");
	}

	/**
	 * 
	 * 更新
	 * 
	 * @param table_name
	 *            表名
	 */
	public void update(String table_name, String pk_key) {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();

		List<Object> parameterList = new ArrayList<Object>();
		try {

			// 关闭自动提交
			jdbcUtil.doTransactionStart(conn);
			/**
			 * 业务开始
			 */
			String parent_id = "1";
			// 1
			// 更新本身的path
			String sql = "update " + table_name + " set PATH_=? where "
					+ pk_key + "=?";
			parameterList = new ArrayList<Object>();
			parameterList.add(parent_id + ".");
			parameterList.add(parent_id);
			jdbcUtil.execute(conn, sql, parameterList);
			// 更新本身的path
			// 2更新孩子
			update(table_name, pk_key, parent_id);
			/**
			 * 业务结束
			 */
			jdbcUtil.doTransactionCommit(conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			try {
				jdbcUtil.doTransactionRollback(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 更新
	 * 
	 * @param tableName
	 *            表名
	 * @param parentId
	 * @param connection
	 * @param cJdbcPrepareStatement
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void update(String tableName, String pk_key, String parentId)
			throws ClassNotFoundException, SQLException {
		Asserts.notBlank(pk_key, "不能为空");
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();

		/**
		 * 业务开始
		 */
		// 找出父母
		Object pathParent = "";
		String sqlParent = "select * from " + tableName + " where " + pk_key
				+ "=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(parentId);
		JdbcPrepareStatementDto bean = jdbcUtil.findResultSet(conn, sqlParent,
				parameterList);
		ResultSet resultSet = bean.getResultSet();
		while (resultSet.next()) {
			pathParent = resultSet.getObject("PATH_");
			log.trace("parent_path=" + pathParent);
		}
		/**
		 * 业务结束
		 */
		/**
		 * 业务开始
		 */
		// 找出所有孩子
		Object id = null;
		String sqlQuery = "select * from " + tableName + " where PARENT_=?";
		parameterList = new ArrayList<Object>();
		parameterList.add(parentId);
		bean = jdbcUtil.findResultSet(conn, sqlQuery, parameterList);
		resultSet = bean.getResultSet();
		while (resultSet.next()) {
			// 先更新孩子本身，再更新孙子
			id = resultSet.getObject(pk_key);
			log.trace("id=" + id);
			String path = pathParent.toString() + id + ".";
			log.trace("path=" + path);
			// 更新孩子本身的path
			String sql = "update " + tableName + " set PATH_=? where " + pk_key
					+ "=?";
			parameterList = new ArrayList<Object>();
			parameterList.add(path);
			parameterList.add(id);
			jdbcUtil.execute(conn, sql, parameterList);
			// 更新孩子本身的path
			// 更新孙子
			this.update(tableName, pk_key, id.toString());
			// 更新孙子
		}
		// oracle下必须关闭游标
		jdbcUtil.close(resultSet, bean.getPreparedStatement());
		/**
		 * 业务结束
		 */
	}
}
