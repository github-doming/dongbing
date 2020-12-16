package c.a.util.core.jdbc.bean.sql_custom;

import c.a.util.core.jdbc.bean.nut.ColumnBean;

public class SqlCustomQueryDto extends ColumnBean {

	private String queryFieldKey;

	public String getQueryFieldKey() {
		return queryFieldKey;
	}

	public void setQueryFieldKey(String queryFieldKey) {
		this.queryFieldKey = queryFieldKey;
	}

}
