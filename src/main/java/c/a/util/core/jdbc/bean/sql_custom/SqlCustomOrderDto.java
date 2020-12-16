package c.a.util.core.jdbc.bean.sql_custom;

import c.a.util.core.jdbc.bean.nut.ColumnBean;

public class SqlCustomOrderDto extends ColumnBean {

	private String orderFieldKey;

	public String getOrderFieldKey() {
		return orderFieldKey;
	}

	public void setOrderFieldKey(String orderFieldKey) {
		this.orderFieldKey = orderFieldKey;
	}

}
