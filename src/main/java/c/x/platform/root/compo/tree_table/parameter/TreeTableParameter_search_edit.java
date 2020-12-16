package c.x.platform.root.compo.tree_table.parameter;
public class TreeTableParameter_search_edit extends TreeTableParameter_Ay {
	/**
	 * 搜索名字
	 */
	private String search;
	/**
	 * treeCode_RootValue
	 */
	private String tree_code$root_value;
	/**
	 * 编辑时,选择上一级时,不能选择本身或该节点所有的孩子;
	 */
	private String edit_path;
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getTree_code$root_value() {
		return tree_code$root_value;
	}
	public void setTree_code$root_value(String treeCode$rootValue) {
		tree_code$root_value = treeCode$rootValue;
	}
	public String getEdit_path() {
		return edit_path;
	}
	public void setEdit_path(String editPath) {
		edit_path = editPath;
	}
}