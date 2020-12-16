package c.x.platform.root.compo.tree_table.parameter;
public class TreeTableParameter_menu extends TreeTableParameter_table {
	private String model = null;
	/**
	 * 
	 * 模型;
	 * 
	 * 简单;
	 * 
	 * 
	 */
	private boolean model$simple = false;
	/**
	 * 
	 * 模型;
	 * 
	 * 菜单;
	 * 
	 * 
	 */
	private boolean model$tree_menu = false;
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public boolean isModel$tree_menu() {
		return model$tree_menu;
	}
	public void setModel$tree_menu(boolean model$treeMenu) {
		model$tree_menu = model$treeMenu;
	}
	public boolean isModel$simple() {
		return model$simple;
	}
	public void setModel$simple(boolean model$simple) {
		this.model$simple = model$simple;
	}
}