package all.sys_admin.sys.dict.sys_dict_tree.action;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import all.sys_admin.sys.dict.sys_dict_tree.entity.SysDictTreeT;
import all.ui.easyui.bean.EasyuiMenuBean;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.json.JsonThreadLocal;
import c.x.platform.root.common.action.CommAction;
public class SysDictTreeTListJsonAction extends CommAction {
	@Override
	public String execute() throws Exception {
		// 查找树
		String sql = "select * from sys_dict_tree t1 where state_!='DEL'  order by t1.SN_ asc,t1.SYS_DICT_TREE_ID_ asc";
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		List<SysDictTreeT> fristList = jdbcTool.findObjectList(SysDictTreeT.class, conn, sql, null);
		List<EasyuiMenuBean> easyuiMenuBeanList = toListNode(fristList);
		List<EasyuiMenuBean> treeEasyuiMenuBeanList = tree(easyuiMenuBeanList, "0");
		String jsonStr = JsonThreadLocal.findThreadLocal().get().list2json(treeEasyuiMenuBeanList);
		response.getWriter().print(jsonStr);
		return null;
	}
	public List<EasyuiMenuBean> tree(List<EasyuiMenuBean> easyuiMenuBeanList, String id) {
		// 递归转化为树形
		List<EasyuiMenuBean> treeEasyuiMenuBeanList = new ArrayList<EasyuiMenuBean>();
		for (EasyuiMenuBean easyuiMenuBean : easyuiMenuBeanList) {
			EasyuiMenuBean treeEasyuiMenuBean = new EasyuiMenuBean();
			treeEasyuiMenuBean.setId(easyuiMenuBean.getId());
			treeEasyuiMenuBean.setPid(easyuiMenuBean.getPid());
			treeEasyuiMenuBean.setText(easyuiMenuBean.getText());
			treeEasyuiMenuBean.setUrl(easyuiMenuBean.getUrl());
			treeEasyuiMenuBean.setAttributes(easyuiMenuBean.getAttributes());
			treeEasyuiMenuBean.setState(easyuiMenuBean.getState());
			if (id.equals(easyuiMenuBean.getPid())) {
				treeEasyuiMenuBean.setChildren(tree(easyuiMenuBeanList, treeEasyuiMenuBean.getId()));
				treeEasyuiMenuBeanList.add(treeEasyuiMenuBean);
			}
		}
		return treeEasyuiMenuBeanList;
	}
	// 转化为TreeNode节点
	public EasyuiMenuBean toNode(SysDictTreeT menu) {
		EasyuiMenuBean easyuiMenuBean = new EasyuiMenuBean();
		easyuiMenuBean.setId(menu.getSysDictTreeId());
		easyuiMenuBean.setPid(menu.getParent());
		easyuiMenuBean.setText(menu.getSysDictTreeName());
		// easyuiMenuBean.setState(menu.getState());
		return easyuiMenuBean;
	}
	public List<EasyuiMenuBean> toListNode(List<SysDictTreeT> menuList) {
		List<EasyuiMenuBean> easyuiMenuBeanList = new ArrayList<EasyuiMenuBean>();
		for (SysDictTreeT menu : menuList) {
			easyuiMenuBeanList.add(toNode(menu));
		}
		return easyuiMenuBeanList;
	}
}
