package all.cds;
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
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.jdbc.nut.IJdbcUtil;
public class AppMainAction extends SysMainAction {
	@Override
	public String execute() throws Exception {
		String currentUserName=this.findCurrentUserName();
		request.setAttribute("currentUserName", currentUserName);
		String type = this.findCurrentUserType();
		if ("sys".equals(type)) {
			return this.executeSys();
		}
		if ("app".equals(type)) {
			return this.executeByApp();
		}
		return null;
	}
	public String executeByApp() throws Exception {
		try {
			String parentId = BeanThreadLocal.findThreadLocal().get().find(request.getParameter("pid"),
					this.findRootMenuIdByApp());
			// 查找菜单
			String sql = toSqlRoleShowByApp();
			IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			Connection conn = jdbcUtil.getConnection();
			List<AppMenuT> menuList = jdbcTool.findObjectList(AppMenuT.class, conn, sql, null);
			List<EasyuiMenuBean> easyuiMenuBeanList = toListNodeByApp(menuList);
			List<EasyuiMenuBean> treeEasyuiMenuBeanList = doTreeByApp(easyuiMenuBeanList, parentId);
			request.setAttribute("menuList", treeEasyuiMenuBeanList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CommViewEnum.Default.toString();
	}
	public List<EasyuiMenuBean> doTreeByApp(List<EasyuiMenuBean> easyuiMenuBeanList, String id) {
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
				treeEasyuiMenuBean.setChildren(doTree(easyuiMenuBeanList, treeEasyuiMenuBean.getId()));
				treeEasyuiMenuBeanList.add(treeEasyuiMenuBean);
			}
		}
		return treeEasyuiMenuBeanList;
	}
	// 转化为TreeNode节点
	public EasyuiMenuBean toNodeByApp(AppMenuT menu) {
		EasyuiMenuBean easyuiMenuBean = new EasyuiMenuBean();
		easyuiMenuBean.setId(menu.getAppMenuId());
		easyuiMenuBean.setPid(menu.getParent());
		easyuiMenuBean.setText(menu.getAppMenuName());
		// easyuiMenuBean.setUrl(menu.getAdminUrl());
		if (StringUtil.isNotBlank(menu.getAdminUrl())) {
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
		attributes.put("url", menu.getAdminUrl());
		easyuiMenuBean.setAttributes(attributes);
		return easyuiMenuBean;
	}
	public List<EasyuiMenuBean> toListNodeByApp(List<AppMenuT> menuList) {
		List<EasyuiMenuBean> easyuiMenuBeanList = new ArrayList<EasyuiMenuBean>();
		for (AppMenuT menu : menuList) {
			easyuiMenuBeanList.add(toNodeByApp(menu));
		}
		return easyuiMenuBeanList;
	}
	/**
	 * 菜单sql;
	 * 
	 * 
	 * 有角色;
	 * 
	 * 通过角色过滤菜单;
	 * 
	 * 是否显示;
	 * 
	 * @return
	 */
	public String toSqlRoleShowByApp() throws Exception {
		String userId = this.findCurrentUserId();
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT m.* from    ");
		sb.append(" APP_MENU m");
		sb.append(" left join  APP_MENU_GROUP rm   ");
		sb.append(" on m. APP_MENU_ID_=rm.APP_MENU_ID_    ");
		sb.append(" left join  APP_USER_GROUP ru   ");
		sb.append("  on ru.APP_GROUP_ID_=rm.APP_GROUP_ID_ ");
		sb.append("  where m.STATE_='" + TaskStateEnum.OPEN.toString() + "'");
		sb.append("  and ru.APP_USER_ID_='" + userId + "'");
		sb.append("  order by m.SN_ asc,m.APP_MENU_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
	public String findRootMenuIdByApp() throws Exception {
		String tenantCode = this.findCurrentUserTenantCode();
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "select APP_MENU_ID_ FROM APP_MENU  where tree_code_=? and TENANT_CODE_=? ";
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		parameterList.add("0001");
		parameterList.add(tenantCode);
		String menuId = jdbcUtil.findString("APP_MENU_ID_", conn, sql, parameterList);
		return menuId;
	}
}
