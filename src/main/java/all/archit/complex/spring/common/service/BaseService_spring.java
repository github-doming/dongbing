package all.archit.complex.spring.common.service;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import all.archit.complex.spring.jdbc.threadlocal.JdbcTemplateThreadLocal;
import c.x.platform.root.compo.tree_load.UpdatePathAll;
import c.x.platform.root.common.dao.BaseDao;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.jdbc.nut.IJdbcUtil;

public class BaseService_spring {
	protected BaseDao dao = new BaseDao();

	/**
	 * 
	 * 更新所有path
	 * 
	 * @param table_name
	 * @param parent_id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void update_path(String table_name, String parent_id)
			throws ClassNotFoundException, SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		UpdatePathAll cUpdatePath_all = new UpdatePathAll();
		cUpdatePath_all.update(table_name, parent_id);
	}

	// 添加新的方法
	private JdbcTemplate jdbcTemplate = null;

	/**
	 * 
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate() {
		return this.findJdbcTemplate();
	}

	/**
	 * 
	 * @return
	 */
	public JdbcTemplate findJdbcTemplate() {
		jdbcTemplate = JdbcTemplateThreadLocal.findThreadLocalJdbcTemplate()
				.get();
		return jdbcTemplate;
	}
}
