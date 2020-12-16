package c.a.tools.jdbc.transaction;

import java.sql.Connection;

public class TransactionBean {
	private Connection conn = null;
	
	private  Connection connSlave = null;

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Connection getConnSlave() {
		return connSlave;
	}

	public void setConnSlave(Connection connSlave) {
		this.connSlave = connSlave;
	}
	
	
	
}
