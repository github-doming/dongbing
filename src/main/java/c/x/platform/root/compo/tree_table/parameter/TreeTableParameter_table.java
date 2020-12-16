package c.x.platform.root.compo.tree_table.parameter;
import java.util.Map;
public class TreeTableParameter_table extends TreeTableParameter_search_edit {
	/**
	 * 
	 * 模型;
	 * 
	 * 表格;
	 * 
	 * 
	 */
	private boolean model$tree_table = false;
	private Map<String, Object> tds = null;
	public Map<String, Object> getTds() {
		return tds;
	}
	public void setTds(Map<String, Object> tds) {
		this.tds = tds;
	}
	public boolean isModel$tree_table() {
		return model$tree_table;
	}
	public void setModel$tree_table(boolean model$treeTable) {
		model$tree_table = model$treeTable;
	}
}