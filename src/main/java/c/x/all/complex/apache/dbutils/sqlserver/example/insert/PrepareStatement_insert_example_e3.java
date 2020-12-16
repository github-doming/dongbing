package c.x.all.complex.apache.dbutils.sqlserver.example.insert;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.jdbc.nut.JdbcNutUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;

/**
 * 
 * 
 * 新增数据并返回主键 ;
 * 
 * 
 * DROP TABLE [dbo].[WechatMoData] GO CREATE TABLE [dbo].[WechatMoData] (
 * [AgentType] varchar(255) NULL , [ToUserName] varchar(255) NULL , [ItemCount]
 * varchar(255) NULL , [PackageId] varchar(255) NULL , id int primary key
 * identity(1,1)
 * 
 * 
 * )
 * 
 * 
 * 
 * 
 * 
 */
public class PrepareStatement_insert_example_e3 {
	/**
	 * 
	 * @Title: save
	 * @Description:
	 * @param conn
	 * @throws Exception 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void save(Connection conn) throws Exception {

		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		String sql = null;
		int id = 0;
		try {
			// 关闭自动提交
			jdbcUtil.doTransactionStart(conn);
			// 业务开始
			/**
			 * 新增记录
			 */
			List<Object> parameterList = new ArrayList<Object>();
			parameterList.add("2c");
			sql = "insert into WechatMoData (PackageId) values(?) ";
			String sid = jdbcUtil.executeInsert(conn, sql, parameterList);
			id = Integer.parseInt(sid);
			/**
			 * 查询
			 */
			sql = "select * from  WechatMoData where KID=?";
			Object[] arrayParameters = new Object[]{id};
			// 创建SQL执行工具
			QueryRunner cQueryRunner = new QueryRunner();
			// 执行SQL查询，并获取结果
			List<Map<String, Object>> list = (List<Map<String, Object>>) cQueryRunner
					.query(conn, sql, new MapListHandler(), arrayParameters);
			// 输出查询结果
			for (Map<String, Object> map : list) {
				System.out.println("PackageId=" + map.get("PackageId"));
			}
			int i = 1 / 0;
			// 业务结束
			jdbcUtil.doTransactionCommit(conn);
		} catch (Exception e) {
			try {
				jdbcUtil.doTransactionRollback(conn);
			} catch (SQLException e1) {
				throw e1;
			}
			throw e;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Connection conn = null;
		try {
			String url = "jdbc:oracle:thin:@192.168.1.20:1521:jsaas";
			String username = "jsaas_test";
			String password = "jsaas_test";

			IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			PrepareStatement_insert_example_e3 service = new PrepareStatement_insert_example_e3();
			service.save(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}

}
