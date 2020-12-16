package all.ui.cyui.layout.sys_menu;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.util.StringUtil;
import all.ui.easyui.bean.EasyuiMenuBean;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.json.JsonThreadLocal;
import c.x.platform.root.common.action.CommAction;
import all.gen.sys_menu.t.entity.SysMenuT;
public class SysMenuCommListJsonAction extends CommAction {
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
		List<SysMenuT> menuList = jdbcTool.findObjectList(SysMenuT.class, conn, sql, null);
		List<EasyuiMenuBean> easyuiMenuBeanList = toListNode(menuList);
		List<EasyuiMenuBean> treeEasyuiMenuBeanList = doTree(easyuiMenuBeanList, parentId);
		String jsonStr = JsonThreadLocal.findThreadLocal().get().list2json(treeEasyuiMenuBeanList);
		response.getWriter().print(jsonStr);
		return null;
	}
	public List<EasyuiMenuBean> doTree(List<EasyuiMenuBean> inputEasyuiMenuBeanList, String id) {
		List<EasyuiMenuBean> returnEasyuiMenuBeanList = new ArrayList<EasyuiMenuBean>();
		EasyuiMenuBean root = this.findRoot(inputEasyuiMenuBeanList, id);
		// log.trace("root="+root);
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
			// log.trace("id="+id);
			if (id.equals(inputEasyuiMenuBean.getId())) {
				return treeEasyuiMenuBean;
			}
		}
		return null;
	}
	public List<EasyuiMenuBean> findChild(List<EasyuiMenuBean> easyuiMenuBeanList, String id) {
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
				treeEasyuiMenuBean.setChildren(findChild(easyuiMenuBeanList, treeEasyuiMenuBean.getId()));
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
		// easyuiMenuBean.setUrl(menu.getUrl());
		if(StringUtil.isNotBlank(menu.getUrl())){
			if (menu.getUrl().indexOf("http") == 0) {
				easyuiMenuBean.setUrl(menu.getUrl());
			} else {
				String menuUrl = findContextPath() + "" + menu.getUrl();
				// System.out.println("menuUrl="+menuUrl);
				easyuiMenuBean.setUrl(menuUrl);
			}
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
	 * 是否显示;
	 * 
	 * @return
	 */
	public String toSqlShow() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT m.* from    ");
		sb.append(" SYS_MENU m");
		sb.append(" where  m.STATE_='" + TaskStateEnum.OPEN.toString() + "'");
		sb.append(" order by m.SN_ asc,m.SYS_MENU_ID_ asc  ");
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
		sb.append(" SYS_MENU m");
		sb.append(" where m.PATH_ like '1.2.5.%' and m.STATE_='" + TaskStateEnum.OPEN.toString() + "'");
		sb.append(" order by m.SN_ asc,m.SYS_MENU_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
}
