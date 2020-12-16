package c.x.platform.root.tree_table.action;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import c.a.util.core.string.StringUtil;
import c.x.platform.root.menu_allow.action.MenuAllowAction;
import c.x.platform.root.compo.tree_table.core.TreeTable;
import c.x.platform.root.compo.tree_table.parameter.TreeTableParameter;
import c.x.platform.root.compo.tree_table.bean.TreeTableNodeBaseBean;
/**
 * TreeTableAction通用代码
 * 
 * 
 */
// public abstract class TreeTableAction_ax extends RepeatLoginAction {
public abstract class TreeTableAxAction extends MenuAllowAction {
	public abstract TreeTableParameter initParameter() throws Exception;
	public abstract String execute() throws Exception;
	public String execute_printlnForHTML(TreeTableParameter treeParameter)
			throws IOException, SQLException {
		String str = this.findTree(treeParameter);
		return str;
	}
	public String findTree(TreeTableParameter treeParameter)
			throws SQLException {
		TreeTable tt = new TreeTable();
		// 如果treeParameter.getRoot_id()为空，则用sql查找
		if (StringUtil.isBlank(treeParameter.getRoot_id())) {
			String rootId = tt.findRootId(treeParameter.getSql_find_root(),
					treeParameter);
			log.trace("根节点rootId=" + rootId);
			if (rootId == null) {
				// String returnStr = "很抱歉,根节点为空";
				String returnStr = "很抱歉,搜索不到值";
				return returnStr;
			}
			treeParameter.setRoot_id(rootId);
		}
		List<TreeTableNodeBaseBean> treeTableNodeBaseBeanList = null;
		/**
		 * list_CfMenus = db.list(request); 增加搜索;
		 */
		treeTableNodeBaseBeanList = tt.search_list(treeParameter);
		if (treeTableNodeBaseBeanList == null) {
			// log.trace("树_搜索不到值", this);
			String returnStr = "很抱歉,搜索不到值";
			return returnStr;
		} else {
			log.trace("menu size=" + treeTableNodeBaseBeanList.size());
			// for (ByTreeNode node : list_CfMenus) {
			// log.trace("value=" + node.getName(),this);
			// log.trace("pic=" + node.getPic(),this);
			// }
		}
		String str = tt.init_tree(treeParameter, treeTableNodeBaseBeanList);
		if (str == null) {
			String returnStr = "很抱歉,搜索不到值";
			return returnStr;
		}
		return str;
	}
}
