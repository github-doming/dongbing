package all.ui.cyui.layout.app_menu;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.util.StringUtil;
import all.gen.app_menu.t.entity.AppMenuT;
import all.ui.easyui.bean.EasyuiMenuBean;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.json.JsonThreadLocal;
import c.x.platform.root.common.action.CommAction;
public class AppMenuCommListJsonAction extends CommAction {
	@Override
	public String execute() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		String parentId = BeanThreadLocal.findThreadLocal().get().find(request.getParameter("pid"), "0");
		// 查找菜单
		String sql = toSqlShow();
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		List<AppMenuT> menuList = jdbcTool.findObjectList(AppMenuT.class, conn, sql, null);
		List<EasyuiMenuBean> inputEasyuiMenuBeanList = toListNode(menuList);
		List<EasyuiMenuBean> treeEasyuiMenuBeanList = doTree(inputEasyuiMenuBeanList, parentId);
		String jsonStr = JsonThreadLocal.findThreadLocal().get().list2json(treeEasyuiMenuBeanList);
		response.getWriter().print(jsonStr);
		return null;
	}
	public List<EasyuiMenuBean> doTree(List<EasyuiMenuBean> inputEasyuiMenuBeanList, String id) {
		List<EasyuiMenuBean> returnEasyuiMenuBeanList = new ArrayList<EasyuiMenuBean>();
		EasyuiMenuBean root = this.findRoot(inputEasyuiMenuBeanList, id);
		root.setChildren(this.findChild(inputEasyuiMenuBeanList, id));
		returnEasyuiMenuBeanList.add(root);
		return returnEasyuiMenuBeanList;
	}
	public EasyuiMenuBean findRoot(List<EasyuiMenuBean> inputEasyuiMenuBeanList, String id) {
		for (EasyuiMenuBean inputEasyuiMenuBean : inputEasyuiMenuBeanList) {
			EasyuiMenuBean treeEasyuiMenuBean = new EasyuiMenuBean();
			treeEasyuiMenuBean.setId(inputEasyuiMenuBean.getId());
			treeEasyuiMenuBean.setPid(inputEasyuiMenuBean.getPid());
			treeEasyuiMenuBean.setText(inputEasyuiMenuBean.getText());
			treeEasyuiMenuBean.setUrl(inputEasyuiMenuBean.getUrl());
			treeEasyuiMenuBean.setAttributes(inputEasyuiMenuBean.getAttributes());
			treeEasyuiMenuBean.setState(inputEasyuiMenuBean.getState());
			if (id.equals(inputEasyuiMenuBean.getId())) {
				return treeEasyuiMenuBean;
			}
		}
		return null;
	}
	public List<EasyuiMenuBean> findChild(List<EasyuiMenuBean> inputEasyuiMenuBeanList, String id) {
		// 递归转化为树形
		List<EasyuiMenuBean> treeEasyuiMenuBeanList = new ArrayList<EasyuiMenuBean>();
		// 递归转化为树形
		for (EasyuiMenuBean inputEasyuiMenuBean : inputEasyuiMenuBeanList) {
			EasyuiMenuBean treeEasyuiMenuBean = new EasyuiMenuBean();
			treeEasyuiMenuBean.setId(inputEasyuiMenuBean.getId());
			treeEasyuiMenuBean.setPid(inputEasyuiMenuBean.getPid());
			treeEasyuiMenuBean.setText(inputEasyuiMenuBean.getText());
			treeEasyuiMenuBean.setUrl(inputEasyuiMenuBean.getUrl());
			treeEasyuiMenuBean.setAttributes(inputEasyuiMenuBean.getAttributes());
			treeEasyuiMenuBean.setState(inputEasyuiMenuBean.getState());
			if (id.equals(inputEasyuiMenuBean.getPid())) {
				treeEasyuiMenuBean.setChildren(findChild(inputEasyuiMenuBeanList, treeEasyuiMenuBean.getId()));
				treeEasyuiMenuBeanList.add(treeEasyuiMenuBean);
			}
		}
		return treeEasyuiMenuBeanList;
	}
	// 转化为TreeNode节点
	public EasyuiMenuBean toNode(AppMenuT menu) {
		EasyuiMenuBean easyuiMenuBean = new EasyuiMenuBean();
		easyuiMenuBean.setId(menu.getAppMenuId());
		easyuiMenuBean.setPid(menu.getParent());
		easyuiMenuBean.setText(menu.getAppMenuName());
		// easyuiMenuBean.setUrl(menu.getAppUrl());
		easyuiMenuBean.setUrl(menu.getAdminUrl());
		// easyuiMenuBean.setUrl(menu.getUrl());
		if(StringUtil.isNotBlank(menu.getAdminUrl())){
			if (menu.getAdminUrl().indexOf("http") == 0) {
				easyuiMenuBean.setUrl(menu.getAdminUrl());
			} else {
				String menuUrl = findContextPath() + "" + menu.getAdminUrl();
				// System.out.println("menuUrl="+menuUrl);
				easyuiMenuBean.setUrl(menuUrl);
			}
		}
		easyuiMenuBean.setState(menu.getState());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("url", menu.getAppUrl());
		easyuiMenuBean.setAttributes(attributes);
		return easyuiMenuBean;
	}
	public List<EasyuiMenuBean> toListNode(List<AppMenuT> menuList) {
		List<EasyuiMenuBean> easyuiMenuBeanList = new ArrayList<EasyuiMenuBean>();
		for (AppMenuT menu : menuList) {
			easyuiMenuBeanList.add(toNode(menu));
		}
		return easyuiMenuBeanList;
	}
	/**
	 * 菜单sql;
	 * 
	 * 是否显示;
	 * 
	 * @return
	 */
	public String toSqlShow() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT m.* from    ");
		sb.append(" APP_MENU m");
		sb.append(" where  m.STATE_='" + TaskStateEnum.OPEN.toString() + "'");
		sb.append(" order by m.SN_ asc,m.APP_MENU_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
	/**
	 * 菜单sql;
	 * 
	 * 是否显示;
	 * 
	 * @return
	 */
	public String toSqlShow1() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT m.* from    ");
		sb.append(" APP_MENU m");
		sb.append(" where m.PATH_ like '1.2.5.%' and m.STATE_='" + TaskStateEnum.OPEN.toString() + "'");
		sb.append(" order by m.SN_ asc,m.APP_MENU_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
}
