package c.x.platform.root.compo.tree_table.parameter;
public class TreeTableParameter_Event extends TreeTableNodeColumn_Event {
	private String root_id = null;
	private String sql;
	private String sql_find_root;
	// -- set/get --//
	// -- { --//
	public String getRoot_id() {
		return root_id;
	}
	public void setRoot_id(String rootId) {
		root_id = rootId;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sqlInput) {
		// 全变成小写
		this.sql = sqlInput;
	}
	public String getSql_find_root() {
		return sql_find_root;
	}
	public void setSql_find_root(String sqlFindRoot) {
		sql_find_root = sqlFindRoot;
	}
	// -- } --//
	// -- set/get --//
}
