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
 * ---------------------------------------------- 农行2015-3-25增量;
 * 
 * 总余额204.79(数据库204.79);
 * 
 * ----------------------------------------------
 * 
 * 余额宝2015-3-25增量;
 * 
 * 总余额86355.58(数据库86356.58);
 * 
 * 86646.58-86756.58=110(相差)
 * 
 * 86646.58-86755.58=109(相差)
 * 
 * 
 * 
 * 2015-3-25实际收入86355.58(跟数据库一样)(86355.58+400=86755.58);
 * 
 * 网上查询, 总89363.43, 利息2716.85, 实际收入86646.58(89363.43-2716.85=86646.58);
 * 
 * 
 * 
 * 
 * ----------------------------------------------
 * 
 * 余额宝2014-9-7增量;
 * 
 * 
 * 
 * 2014-9-7实际收入65710.48(跟数据库一样)(65710.48+400=66110.48);
 * 
 * 网上查询, 总67100.04+110, 利息1098.56, 实际收入66111.48;
 * 
 * 
 * 
 * 
 * ---------------------------------------------- 2014-8-23余额宝记录导入;
 * 
 * 
 * 实际收入67090.48(跟数据库一样)(67090.48+400=67490.48);
 * 
 * 网上查询, 总68514.33, 利息1022.85, 实际收入67491.48;
 * 
 * 程序总计,
 * 
 * 400(开通余额宝前给冯俊越400) 67090.48+400
 * 
 * ----------------------------------------------
 * 
 * 统计余额; ----------------------------------------------
 * 
 * 
 * 
 */
public class account_example_e2 extends CommTest {
	@Test
	public void execute() {
		try {

			/**
			 * 
			 * 执行下面的sql;
			 * 
			 * update fas_bill_info set account_balance=null ;
			 * 
			 */

			LogConfig.trace = false;
			LogConfig.debug = false;
			AyDao dao = new AyDao();
			String sql = "select id,business_id,account_amount from fas_bill_info  "
					+ " order by id asc";
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
