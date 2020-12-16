package c.a.util.core.jdbc.bean.sql_custom;

import c.a.util.core.jdbc.bean.nut.ColumnBean;

public class SqlCustomWhereDto extends ColumnBean {

	private String whereFieldKey;

	public String getWhereFieldKey() {
		return whereFieldKey;
	}

	public void setWhereFieldKey(String whereFieldKey) {
		this.whereFieldKey = whereFieldKey;
	}

}
