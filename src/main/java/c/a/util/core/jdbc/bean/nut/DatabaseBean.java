package c.a.util.core.jdbc.bean.nut;
/**
 * 
 * 
 * 描述：数据库信息
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class DatabaseBean {
	// 数据库产品
	private String databaseProductName;
	// 数据库产品版本
	private String databaseProductVersion;
	// 驱动版本
	private String driverVersion;
	public String getDatabaseProductName() {
		return databaseProductName;
	}
	public void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}
	public String getDatabaseProductVersion() {
		return databaseProductVersion;
	}
	public void setDatabaseProductVersion(String databaseProductVersion) {
		this.databaseProductVersion = databaseProductVersion;
	}
	public String getDriverVersion() {
		return driverVersion;
	}
	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}
}
