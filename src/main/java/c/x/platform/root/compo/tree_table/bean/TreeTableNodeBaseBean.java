package c.x.platform.root.compo.tree_table.bean;
import java.util.ArrayList;
import java.util.List;

import c.x.platform.root.compo.tree_table.bean.TreeTableNodeEventBean;
public class TreeTableNodeBaseBean extends TreeTableNodeEventBean {
	/**
	 * 父节点
	 */
	private TreeTableNodeBaseBean parent;
	/**
	 * 所有孩子节点
	 */
	private List<TreeTableNodeBaseBean> childs = new ArrayList<TreeTableNodeBaseBean>();
	// -- set/get --//
	// -- { --//
	public TreeTableNodeBaseBean getParent() {
		return parent;
	}
	public void setParent(TreeTableNodeBaseBean parent) {
		this.parent = parent;
	}
	public List<TreeTableNodeBaseBean> getChilds() {
		return childs;
	}
	public void setChilds(List<TreeTableNodeBaseBean> childs) {
		this.childs = childs;
	}
	// -- set/get --//
}
