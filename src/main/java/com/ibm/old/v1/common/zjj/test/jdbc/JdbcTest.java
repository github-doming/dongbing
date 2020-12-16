package com.ibm.old.v1.common.zjj.test.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-07-03 16:52
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class JdbcTest {

	public static void main(String[] args) throws SQLException {
		Connection connection=getConnection();

		String sql="insert into ibm_cloud_config (IBM_CLOUD_CONFIG_ID_,CLOUD_CONFIG_KEY_) value(?,?) ";

		PreparedStatement ps=connection.prepareStatement(sql);
		ps.setString(1,"111");
		ps.setString(2,"222");
		ps.execute();



	}








	private static Connection getConnection() {
		Connection connection=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection=DriverManager.getConnection(
					"jdbc:mysql://192.168.2.113:3306/ibm_cloud?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Hongkong&zeroDateTimeBehavior=CONVERT_TO_NULL",
					"root", "IEjdaknFnvXl69fpduzcAA==");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}

}
