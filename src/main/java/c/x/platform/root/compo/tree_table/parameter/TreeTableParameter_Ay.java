package c.x.platform.root.compo.tree_table.parameter;
import c.x.platform.root.compo.tree_table.parameter.TreeTableParameter_Img;
public class TreeTableParameter_Ay extends TreeTableParameter_Img {
	public boolean open_all = false;
	public boolean close_all = false;
	/**
	 * 树的key,区别出不同的树，防止树事件名雷同
	 */
	public String key = "";
	/**
	 * root是否显示
	 */
	public boolean root_enable;
	// -- set与get --//
	// -- { --//
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isRoot_enable() {
		return root_enable;
	}
	public void setRoot_enable(boolean rootEnable) {
		root_enable = rootEnable;
	}
	public boolean isClose_all() {
		return close_all;
	}
	public void setClose_all(boolean closeAll) {
		close_all = closeAll;
	}
	public boolean isOpen_all() {
		return open_all;
	}
	public void setOpen_all(boolean openAll) {
		open_all = openAll;
	}
	// -- } --//
	// -- set与get --//
}