package c.a.util.core.data_source.ay;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.util.core.data_source.ay.JdbcDataSourceCoreAy;
public class JdbcDataSource extends JdbcDataSourceCoreAy {
	// 日志
	protected Logger log = LogManager.getLogger(this.getClass());
	// 连接池
	//private LinkedList<Connection> pool = null;
	//ConcurrentLinkedQueue是线程安全的
	private ConcurrentLinkedQueue<Connection> pool = null;
	private String driver = null;
	private String url = null;
	private String username = null;
	private String password = null;
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void init(String driverInput, String urlInput, String usernameInput,
			String passwordInput) throws Exception {
		// 新建pool
		//pool = new LinkedList<Connection>();
		pool = new ConcurrentLinkedQueue<Connection>();
		// 初始化url,username, password
		// 数据库驱动
		driver = driverInput;
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			log.error("找不到数据库驱动类=", e);
			e.printStackTrace();
		}
		url = urlInput;
		username = usernameInput;
		password = passwordInput;
	}
	/**
	 * 得到连接
	 * 
	 * @Description:
	 * @Title: findConnection 
	 * @return
	 * @throws SQLException  参数说明 
	 * @return Connection    返回类型 
	 * @throws
	 */
	public Connection findConnection() throws SQLException {
		return getConnection();
	}
	/**
	 * 
	 * 得到连接
	 */
	@Override
	public Connection getConnection() throws SQLException {
	 log.trace("数据连接pool.size()=" + pool.size());
		if (pool.size() > 0) {
			// Connection  conn= pool.removeFirst();
			Connection conn = pool.remove();
			if (conn.isClosed()) {
				pool.remove(conn);
				conn = connectionCreate();
			}
			 return conn;
		} else {
			return connectionCreate();
		}
	}
	/**
	 * 创建连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection connectionCreate() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	/**
	 * 连接归池(释放)
	 * 
	 * @param conn
	 */
	public void connectionClose(Connection conn) {
		//pool.addLast(conn);
		pool.add(conn);
	}
	/**
	 * 连接归池
	 * 
	 * @param conn
	 */
	public void connectionFree(Connection conn) {
	}
	/**
	 * 连接归池
	 * 
	 * @param conn
	 */
	public void connectionRelease(Connection conn) {
	}
	/**
	 * 关闭连接并删除
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public void connectionRemove(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
			// 删除
			pool.remove(conn);
		}
	}
}
