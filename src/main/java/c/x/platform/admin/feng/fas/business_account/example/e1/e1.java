package c.x.platform.admin.feng.fas.business_account.example.e1;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import c.a.util.core.data_source.ay.JdbcDataSourceAy;
import c.a.util.core.data_source.ay.JdbcDataSourceListAy;
import c.a.util.core.test.CommTest;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;

/**
 * 
 * 查询余额
 * 
 * 
 * 
 */
public class e1 extends CommTest {
	@Test
	public void execute() {
		try {
			AyDao dao = new AyDao();
			String sql = "select account_balance from family_account_bill_info "
					+ "where business_id=? and create_time<? order by did desc";
			List<Object> parameterList = new ArrayList<Object>();
			parameterList.add(100);
			parameterList.add("2014-08-06 00:00:00");
			JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance()
					.findLocal();

			Connection conn = jdbcDataSource.getConnection();

			Map map = dao.findMap(sql, parameterList);
			log.trace(map.get("account_balance"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
