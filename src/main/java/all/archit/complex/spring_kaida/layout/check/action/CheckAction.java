package all.archit.complex.spring_kaida.layout.check.action;
//package com.cxy.simple_gy.sysadmin.admin.layout.check.action;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import c.a.config.SysConfig;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.tools.log.custom.common.BaseLog;
import c.a.util.core.copy.CopyUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.dao.BaseDao;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
import c.a.util.core.json.JsonTcpBean;import c.x.platform.root.login_not.action.LoginNotAction;
import c.x.platform.root.login_not.current.CurrentSysUser;
import c.x.platform.root.util.SysUtil;
import all.gen.sys_user.t.entity.SysUserT;
/**
 * 	测试http://localhost:9680/b6/check.php
 * 
 *
 */
public class CheckAction extends LoginNotAction {
	public CheckAction() {
		this.database = true;
		transaction = true;
		this.login = false;
	}
	@Override
	public JsonTcpBean  executeLogin() throws Exception {
		return null;
	}
	@Override
	public String execute() throws Exception {
		if (true) {
			BaseLog.trace("经过CheckAction");
		}
		SysUtil sysUtil = new SysUtil();
		String tenantName = this.request
				.getParameter(SysConfig.CurrentUserTenant);
		String name = this.request
				.getParameter(SysConfig.CurrentUserName);
		String password = this.request
				.getParameter(SysConfig.CurrentUserPassword);
		// 检查用户是否存在
		CurrentSysUser currentUser = this.findSysUser(name, password);
		if (currentUser == null) {
			this.request.setAttribute(SysConfig.UserErrorKey,
					SysConfig.UserErrorValue);
			// 跳转
			this.request.getRequestDispatcher(SysConfig.RequestJspLogin)
					.forward(this.request, response);
			return null;
		} else {
			sysUtil.saveCurrentSysUserByUserId(request, response, currentUser.getSysUserId());
			return "index";
		}
	}
	/**
	 * 找出用户
	 * 
	 * 
	 * @param user_name
	 * @param user_password
	 * @return
	 * @throws Exception
	 */
	private CurrentSysUser findSysUser(String user_name, String user_password)
			throws Exception {
		// BaseLog.trace("user_name=" + user_name);
		BaseDao cBaseDao = new BaseDao();
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		CurrentSysUser currentUser = new CurrentSysUser();
		// 查找租户的连接
		Connection connection_tenant = jdbcUtil.getConnection();
		// String sql =
		// "SELECT * FROM auth_user_info u where u.user_name=? and u.user_password=?";
		String sql = "SELECT * FROM SYS_USER u where u.name=?";
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(user_name);
		// parameterList.add(user_password);
		SysUserT user = (SysUserT) cBaseDao.findObject(SysUserT.class,
				sql, parameterList);
		if (user == null) {
			return null;
		}
		CopyUtil.copyProperties(currentUser, user);
		return currentUser;
	}
	/**
	 * 当前用户拥有的的菜单url
	 * 
	 * @param cu
	 * @return
	 * @throws SQLException
	 */
	public CurrentSysUser findListMenuAllow(CurrentSysUser cu) throws Exception {
		ArrayList<String> MenuAllowList = new ArrayList<String>();
		ArrayList<String> list_permission_code = new ArrayList<String>();
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" select  DISTINCT m.*,sequence  from   ");
		sb.append(" sys_menu m ");
		sb.append(" left join  sys_group_menu rm ");
		sb.append(" on m.id=rm.menu_id  ");
		sb.append(" left join  SYS_GROUP_USER ru  ");
		sb.append(" on ru.group_id=rm.group_id ");
		sb.append(" where ru.user_id=" + cu.getSysUserId());
		sb.append(" order by sequence asc,id asc ");
		String sql = sb.toString();
		ArrayList<Object> parameterList = new ArrayList<Object>();
		AyDao dao = new AyDao();
		listMap = dao.findMapList(sql, parameterList);
		for (Map<String, Object> map : listMap) {
			String url = (String) map.get("url");
			MenuAllowList.add(url);
			String permission_code = (String) map.get("permission_code");
			if (StringUtil.isNotBlank(permission_code)) {
				list_permission_code.add(permission_code);
			}
		}
		cu.setMenuAllowList(MenuAllowList);
		cu.setPermissionCodeList(list_permission_code);
		return cu;
	}
}
