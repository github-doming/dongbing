package c.x.platform.root.compo.tree_table.core;
import java.util.List;
import c.x.platform.root.compo.tree_table.parameter.TreeTableParameter;
import c.x.platform.root.compo.tree_table.core.db.TreeTable_Jdbc_Search;
import c.x.platform.root.compo.tree_table.bean.TreeTableNodeBaseBean;
public abstract class TreeTable_Ay extends TreeTable_Jdbc_Search {
	/**
	 * 
	 * 需要重写
	 * 
	 * @param treeParameter
	 * @param buffer
	 * @param rootNode
	 */
	public abstract void findChilds_fromRootMenu(
			TreeTableParameter treeParameter, StringBuilder buffer,
			TreeTableNodeBaseBean rootNode);
	/**
	 * 1;
	 * 
	 * 调用;初始化;
	 * 
	 * @param list_treeNode
	 * @return
	 */
	public String init_tree(TreeTableParameter treeParameter,
			List<TreeTableNodeBaseBean> list_treeNode) {
		TreeTableNodeBaseBean rootMenu = this.sort_findRootMenu(treeParameter,
				list_treeNode);
		if (rootMenu == null) {
			return null;
		}
		StringBuilder buffer = new StringBuilder();
		this.findChilds_fromRootMenu(treeParameter, buffer, rootMenu);
		return buffer.toString();
	}
	/**
	 * 
	 * 找出根节点，排序后并返回根节点;
	 * 
	 * @param treeParameter
	 * @param listDbMenus
	 * @return
	 */
	public TreeTableNodeBaseBean sort_findRootMenu(
			TreeTableParameter treeParameter,
			List<TreeTableNodeBaseBean> listDbMenus) {
		TreeTableNodeBaseBean rootMenu = this.findRootMenu_fromDB(
				treeParameter, listDbMenus);
		if (rootMenu == null) {
			return null;
		}
		this.findChilds_fromDB(listDbMenus, rootMenu);
		return rootMenu;
	}
	/**
	 * 
	 * 2从无序的list中返回root;
	 * 
	 * 
	 * @param treeParameter
	 * @param list_treeNode
	 * @return
	 */
	public TreeTableNodeBaseBean findRootMenu_fromDB(
			TreeTableParameter treeParameter,
			List<TreeTableNodeBaseBean> list_treeNode) {
		for (TreeTableNodeBaseBean treeNode : list_treeNode) {
			if (treeNode.getId().equals(treeParameter.getRoot_id())) {
				// 设置root的真正深度
				treeNode.setLayer(1);
				if (treeParameter.isRoot_enable()) {
					// 设置root的显示深度
					if (false) {
						treeNode.setShow_layer(1);
					}
					treeNode.setShow_layer(0);
				} else {
					treeNode.setShow_layer(-1);
				}
				// 设置root的path
				treeNode.setPath(treeNode.getId() + ".");
				return treeNode;
			}
		}
		return null;
	}
	/**
	 * 
	 * 3;
	 * 
	 * 
	 * root添加所有节点;
	 * 
	 * 
	 * 从无序的list遍历,并返回root;
	 * 
	 * 树第二次排序，返回root树根节点;
	 * 
	 * @param list_treeNode
	 * @param rootTreeNode
	 */
	public void findChilds_fromDB(List<TreeTableNodeBaseBean> list_treeNode,
			TreeTableNodeBaseBean rootTreeNode) {
		if (rootTreeNode == null) {
			try {
				throw new Exception("root is null");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 是否叶子结点
		 */
		boolean isLeaf = true;
		for (TreeTableNodeBaseBean treeNode : list_treeNode) {
			if (rootTreeNode.getId().equals(treeNode.getParent_id())) {
				isLeaf = false;
				rootTreeNode.getChilds().add(treeNode);
				treeNode.setParent(rootTreeNode);
				// 真正深度+1;
				treeNode.setLayer(rootTreeNode.getLayer() + 1);
				// 显示深度+1;
				treeNode.setShow_layer(rootTreeNode.getShow_layer() + 1);
				// path+1;
				treeNode.setPath(rootTreeNode.getPath() + treeNode.getId()
						+ ".");
				this.findChilds_fromDB(list_treeNode, treeNode);
			}
		}
		/**
		 * 是否叶子结点
		 */
		if (isLeaf) {
			rootTreeNode.setLeaf(true);
		} else {
			rootTreeNode.setLeaf(false);
		}
		/**
		 * 是否最后子节点
		 */
		int number = 0;
		for (TreeTableNodeBaseBean treeNode : rootTreeNode.getChilds()) {
			number = number + 1;
			/**
			 * 是否最后子节点
			 */
			if (number == rootTreeNode.getChilds().size()) {
				treeNode.setLast_leaf(true);
			}
		}
	}
}
