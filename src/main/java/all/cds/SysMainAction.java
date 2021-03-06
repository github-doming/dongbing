package all.cds;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import all.ui.easyui.bean.EasyuiMenuBean;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.x.platform.root.common.action.BaseAction;
import all.gen.sys_menu.t.entity.SysMenuT;
public class SysMainAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String currentUserName=this.findCurrentUserName();
		request.setAttribute("currentUserName", currentUserName);
		return this.executeSys();
	}
	public String executeSys() throws Exception {
		try {
			String parentId = BeanThreadLocal.findThreadLocal().get().find(request.getParameter("pid"), this.findRootMenuId());
			// 查找菜单
			String sql = toSqlRoleShow();
			IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			Connection conn = jdbcUtil.getConnection();
			List<SysMenuT> menuList = jdbcTool.findObjectList(SysMenuT.class, conn, sql, null);
			List<EasyuiMenuBean> easyuiMenuBeanList = toListNode(menuList);
			List<EasyuiMenuBean> treeEasyuiMenuBeanList = doTree(easyuiMenuBeanList, parentId);
			request.setAttribute("menuList", treeEasyuiMenuBeanList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CommViewEnum.Default.toString();
	}
	public List<EasyuiMenuBean> doTree(List<EasyuiMenuBean> easyuiMenuBeanList, String id) {
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
	public EasyuiMenuBean toNode(SysMenuT menu) {
		EasyuiMenuBean easyuiMenuBean = new EasyuiMenuBean();
		easyuiMenuBean.setId(menu.getSysMenuId());
		easyuiMenuBean.setPid(menu.getParent());
		easyuiMenuBean.setText(menu.getSysMenuName());
		//easyuiMenuBean.setUrl(menu.getUrl());
		if(menu.getUrl().indexOf("http")==0){
			easyuiMenuBean.setUrl(menu.getUrl());
		}else{
			String menuUrl=findContextPath()+""+menu.getUrl();
			//System.out.println("menuUrl="+menuUrl);
			easyuiMenuBean.setUrl(menuUrl);
		}
		easyuiMenuBean.setState(menu.getState());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("url", menu.getUrl());
		easyuiMenuBean.setAttributes(attributes);
		return easyuiMenuBean;
	}
	public List<EasyuiMenuBean> toListNode(List<SysMenuT> menuList) {
		List<EasyuiMenuBean> easyuiMenuBeanList = new ArrayList<EasyuiMenuBean>();
		for (SysMenuT menu : menuList) {
			easyuiMenuBeanList.add(toNode(menu));
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
	public String toSqlRoleShow() throws Exception {
		String userId = this.findCurrentUserId();
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT m.* from    ");
		sb.append(" SYS_MENU m");
		sb.append(" left join  SYS_GROUP_MENU rm   ");
		sb.append(" on m. SYS_MENU_ID_=rm.SYS_MENU_ID_    ");
		sb.append(" left join  SYS_GROUP_USER ru   ");
		sb.append("  on ru.SYS_GROUP_ID_=rm.SYS_GROUP_ID_ ");
		sb.append("  where m.STATE_='" + TaskStateEnum.OPEN.toString() + "'");
		sb.append("  and ru.SYS_USER_ID_='" + userId + "'");
		sb.append("  order by m.SN_ asc,m.SYS_MENU_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
	public String findRootMenuId() throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "select SYS_MENU_ID_,PARENT_ FROM SYS_MENU  where tree_code_=? ";
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		parameterList.add("0001");
		String menuId = jdbcUtil.findString("SYS_MENU_ID_", conn, sql, parameterList);
		return menuId;
	}
}
