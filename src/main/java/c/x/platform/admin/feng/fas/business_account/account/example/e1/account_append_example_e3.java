package c.x.platform.admin.feng.fas.business_account.account.example.e1;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import c.a.config.SysConfig;
import c.a.tools.log.custom.config.LogConfig;

import c.x.platform.root.compo.jdbc_dao.common.AyDao;
import c.a.util.core.data_source.ay.JdbcDataSourceAy;
import c.a.util.core.data_source.ay.JdbcDataSourceListAy;
import c.a.util.core.test.CommTest;

/**
 * 
 * 
 * 不如重新全部计算，用account_example_e2更好;
 * 
 * 增量;
 * 
 * 统计余额;
 * 
 * 
 * 
 */
public class account_append_example_e3 extends CommTest {
	@Test
	public void execute() {
		try {
			/**
			 * 余额为空的最小的id;
			 * 
			 * 
			 * 数据如果出错则执行下面的3条sql;
			 * 
			 * delete from fas_bill_info where id>=405;
			 * 
			 * update fas_bill_info set account_balance=null where id>401;
			 * 
			 * select id, account_balance from fas_bill_info where id= ( select
			 * max(id) as max_id from fas_bill_info where account_balance is not
			 * null and business_id=101 )
			 * 
			 */
			String temp_id = "485";
			LogConfig.trace = false;
			LogConfig.debug = false;
			AyDao dao = new AyDao();
			String sql = "select id,business_id,account_amount from fas_bill_info"
					+ " where id>=" + temp_id + " " + " order by id asc";
			List<Object> parameterList = new ArrayList<Object>();
			JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance()
					.findLocal();

			Connection conn = jdbcDataSource.getConnection();

			List<Map<String, Object>> listMap = dao.findMapList(sql,
					parameterList);
			// 更新
			for (Map<String, Object> map : listMap) {
				Long id = (Long) map.get("id");
				Long business_id = (Long) map.get("business_id");
				java.math.BigDecimal account_amount = (java.math.BigDecimal) map
						.get("account_amount");
				// 查找上一次交易的ID
				sql = "select id, account_balance from  fas_bill_info "
						+ "where id="
						+ "("
						+ "select max(id) as max_id from fas_bill_info "
						+ "where account_balance is not null   and business_id=?"
						+ ")";
				parameterList = new ArrayList<Object>();
				parameterList.add(business_id);
				Map<String, Object> map$max = dao.findMap(sql, parameterList);
				java.math.BigDecimal account_balance = null;
				java.math.BigDecimal account_balance$pre = null;
				Long id$pre = 0l;
				if (map$max == null) {
					// 没有上一次交易，初始化余额
					int i = 1 / 0;
					account_balance$pre = new java.math.BigDecimal(0);
				} else {
					id$pre = (Long) map$max.get("id");
					// log.trace("id$pre=" + id$pre.longValue());
					account_balance$pre = (java.math.BigDecimal) map$max
							.get("account_balance");
				}
				// 把上一次交易的余额，增减
				account_balance = account_balance$pre.add(account_amount);
				// log.trace("account_balance ="+account_balance
				// .longValue());
				// 更新本次交易
				sql = "update fas_bill_info i "
						+ "set i.account_balance=?,i.account_begin=?,i.account_begin_id=?"
						+ " where   i.id=?";
				parameterList = new ArrayList<Object>();
				parameterList.add(account_balance.floatValue());
				parameterList.add(account_balance$pre.floatValue());
				parameterList.add(id$pre);
				parameterList.add(id);
				dao.execute(sql, parameterList);
				// break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.trace("end");
	}
}
