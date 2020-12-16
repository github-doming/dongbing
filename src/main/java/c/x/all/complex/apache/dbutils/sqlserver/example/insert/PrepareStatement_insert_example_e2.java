package c.x.all.complex.apache.dbutils.sqlserver.example.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.util.core.jdbc.nut.IJdbcUtil;

/**
 * 
 * org.apache.commons.dbUtil
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
public class PrepareStatement_insert_example_e2 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String url = "jdbc:oracle:thin:@192.168.1.20:1521:jsaas";
		String username = "jsaas_test";
		String password = "jsaas_test";

		IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();

		String sql = null;
		Connection conn = null;
		int id = 0;
		try {
			conn = jdbcUtil.getConnection();
			// 关闭自动提交
			jdbcUtil.doTransactionStart(conn);
			// 业务开始
			/**
			 * 查询主键
			 */
			sql = "SELECT IDENT_CURRENT('WechatMoData')";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				java.math.BigDecimal PackageId = (java.math.BigDecimal) resultSet
						.getObject(1);
				id = PackageId.intValue();
				System.out.println("id =" + id);

			}
			/**
			 * 新增记录
			 */
			Object[] arrayParameters = new Object[]{"2a"};
			sql = "insert into WechatMoData (PackageId) values(?) ";
			ArrayList<Object> parameterList = new ArrayList<Object>();
			// 创建SQL执行工具
			QueryRunner qRunner = new QueryRunner();
			// 执行SQL插入
			int n = qRunner.update(conn, sql, arrayParameters);
			System.out.println("成功插入" + n + "条数据！");
			/**
			 * 查询
			 */
			sql = "select * from  WechatMoData where id=?";
			arrayParameters = new Object[]{id};
			// 创建SQL执行工具
			QueryRunner cQueryRunner = new QueryRunner();
			// 执行SQL查询，并获取结果
			List<Map<String, Object>> list = (List<Map<String, Object>>) cQueryRunner
					.query(conn, sql, new MapListHandler(), arrayParameters);
			// 输出查询结果
			for (Map<String, Object> map : list) {

				System.out.println("PackageId=" + map.get("PackageId"));
			}

			// 业务结束
			// int i = 1 / 0;
			jdbcUtil.doTransactionCommit(conn);
		} catch (SQLException e) {
			try {
				jdbcUtil.doTransactionRollback(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
