package c.x.platform.admin.feng.fas.business_account.account.example.e1;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import c.a.tools.log.custom.config.LogConfig;
import c.a.util.core.data_source.ay.JdbcDataSourceAy;
import c.a.util.core.data_source.ay.JdbcDataSourceListAy;
import c.a.util.core.test.CommTest;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;

/**
 * 
 * 
 * 
 * 统计余额前先更新ID;
 * 
 * 
 * 
 */
public class account_example_e1 extends CommTest {
	@Test
	public void execute() {
		try {
			LogConfig.trace = false;
			LogConfig.debug = false;
			AyDao dao = new AyDao();
			String sql = "select i.row ,i.create_time from fas_bill_info i "
					+ " order by i.create_time asc,i.row asc";
			List<Object> parameterList = new ArrayList<Object>();
			JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance()
					.findLocal();

			Connection conn = jdbcDataSource.getConnection();

			List<Map<String, Object>> listMap = dao.findMapList(sql,
					parameterList);
			// 更新
			int i = 1;
			for (Map<String, Object> map : listMap) {
				parameterList = new ArrayList<Object>();
				Long create_time = (Long) map.get("create_time");
				Long row = (Long) map.get("row");
				// log.trace("create_time="+create_time);
				sql = "update fas_bill_info i set i.id=?"
						+ " where   i.create_time=? and  row=?";
				parameterList.add(i);
				parameterList.add(create_time);
				parameterList.add(row);
				dao.execute(sql, parameterList);
				i = i + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.trace("end");
	}
}
