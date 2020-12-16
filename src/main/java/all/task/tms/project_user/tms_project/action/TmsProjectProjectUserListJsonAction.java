package all.task.tms.project_user.tms_project.action;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.json.JsonThreadLocal;
import all.ui.easyui.bean.EasyuiMenuBean;
import c.x.platform.root.common.action.CommAction;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import all.task.tms.project_user.tms_project.entity.TmsProjectProjectUser;
public class TmsProjectProjectUserListJsonAction extends CommAction {
	@Override
	public String execute() throws Exception {
		// 查找树
		String sql = "select * from tms_project t1 where state_!='DEL'  order by t1.SN_ asc,t1.TMS_PROJECT_ID_ asc";
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		List<TmsProjectProjectUser> fristList = jdbcTool.findObjectList(TmsProjectProjectUser.class, conn, sql, null);
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
	public EasyuiMenuBean toNode(TmsProjectProjectUser menu) {
		EasyuiMenuBean easyuiMenuBean = new EasyuiMenuBean();
		easyuiMenuBean.setId(menu.getTmsProjectId());
		easyuiMenuBean.setPid(menu.getParent());
		easyuiMenuBean.setText(menu.getProjectName());
		// easyuiMenuBean.setState(menu.getState());
		return easyuiMenuBean;
	}
	public List<EasyuiMenuBean> toListNode(List<TmsProjectProjectUser> menuList) {
		List<EasyuiMenuBean> easyuiMenuBeanList = new ArrayList<EasyuiMenuBean>();
		for (TmsProjectProjectUser menu : menuList) {
			easyuiMenuBeanList.add(toNode(menu));
		}
		return easyuiMenuBeanList;
	}
}
