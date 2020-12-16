package c.x.platform.root.util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.copy.CopyUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.typeconst.TypeDatabaseConst;
import c.a.util.core.uuid.Uuid;
import c.x.platform.root.login_not.current.CurrentSysUser;
import all.gen.sys_account.t.entity.SysAccountT;
import all.gen.sys_user.t.entity.SysUserT;
public class SysUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 保存 sso到cookie
	 * 
	 */
	public String saveCookieCurrentAppUserBySSO(HttpServletRequest request, HttpServletResponse response, String tokenValue)
			throws Exception {
		CookieUtil cookieUtil = new CookieUtil();
		cookieUtil.saveCookieCurrentSysUser(response, tokenValue);
		return tokenValue;

	}
	/**
	 * 
	 * 保存 token到cookie和SYS_SSO
	 * 
	 */
	public String saveCurrentSysUserByUserId(HttpServletRequest request, HttpServletResponse response, String userId)
			throws Exception {
		CookieUtil cookieUtil = new CookieUtil();
		String sso = Uuid.create().toString();
		this.saveOrUpdateSSO(userId, sso);
		cookieUtil.saveCookieCurrentSysUser(response, sso);
		return sso;
	}
	/**
	 * 找出用户
	 * 
	 */
	public CurrentSysUser findSysUser(String userId) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		CurrentSysUser currentUser = new CurrentSysUser();
		// 查找
		String sql = "SELECT * FROM SYS_USER u where u.sys_user_id_=?";
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(userId);
		SysUserT user = (SysUserT) jdbcTool.findObject(SysUserT.class, conn, sql, parameterList);
		if (user == null) {
			return null;
		}
		CopyUtil.copyProperties(currentUser, user);
		return currentUser;
	}
	/**
	 * 找出用户并更新登录时间
	 * 
	 * 
	 * @param accName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public CurrentSysUser findSysUserUpdateLoginTime(String accName, String passwordInput) throws Exception {
		String password = null;
		String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
		String commLocalASE_key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
				"");
		if ("true".equals(commLocalASE)) {
			password = CommASEUtil.encode(commLocalASE_key, passwordInput.trim()).trim();
		} else {
			password = passwordInput;
		}
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		CurrentSysUser currentUser = new CurrentSysUser();
		String sql = null;
		ArrayList<Object> parameterList = null;
		// 1查找账号表
		// 查找租户的连接
		sql = "SELECT * FROM SYS_ACCOUNT u   where u.SYS_ACCOUNT_NAME_=? and u.PASSWORD_=?";
		parameterList = new ArrayList<Object>();
		parameterList.add(accName);
		parameterList.add(password);
		SysAccountT account = (SysAccountT) jdbcTool.findObject(SysAccountT.class, conn, sql, parameterList);
		if (account == null) {
			return null;
		}
		String userId = account.getSysUserId();
		// 2查找用户表
		sql = "SELECT * FROM SYS_USER u   where u.SYS_USER_ID_=?";
		parameterList = new ArrayList<Object>();
		parameterList.add(userId);
		SysUserT user = (SysUserT) jdbcTool.findObject(SysUserT.class, conn, sql, parameterList);
		if (user == null) {
			return null;
		}
		CopyUtil.copyProperties(currentUser, user);
		// 更新登录时间
		Date date = new Date();
		account.setLoginTime(date);
		account.setLoginTimeLong(date.getTime());
		user.setLoginTime(date);
		user.setLoginTimeLong(date.getTime());
		jdbcTool.updateObject(conn, account);
		jdbcTool.updateObject(conn, user);
		currentUser.setLoginTime(date);
		currentUser.setLoginTimeLong(date.getTime());
		return currentUser;
	}
	/**
	 * 找出用户
	 * 
	 * 
	 * @param accName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public CurrentSysUser findSysUser(String accName, String passwordInput) throws Exception {
		if (StringUtil.isBlank(accName)) {
			return null;
		}
		if (StringUtil.isBlank(passwordInput)) {
			return null;
		}
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		String password = null;
		String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
		String commLocalASE_key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
				"");
		if ("true".equals(commLocalASE)) {
			password = CommASEUtil.encode(commLocalASE_key, passwordInput.trim()).trim();
		} else {
			password = passwordInput;
		}
		CurrentSysUser currentUser = new CurrentSysUser();
		String sql = null;
		ArrayList<Object> parameterList = null;
		// 1查找账号表
		// 查找租户的连接
		sql = "SELECT * FROM SYS_ACCOUNT u   where u.SYS_ACCOUNT_NAME_=? and u.PASSWORD_=?";
		parameterList = new ArrayList<Object>();
		parameterList.add(accName);
		parameterList.add(password);
		SysAccountT account = (SysAccountT) jdbcTool.findObject(SysAccountT.class, conn, sql, parameterList);
		if (account == null) {
			return null;
		}
		String userId = account.getSysUserId();
		// 2查找用户表
		sql = "SELECT * FROM SYS_USER u   where u.SYS_USER_ID_=?";
		parameterList = new ArrayList<Object>();
		parameterList.add(userId);
		SysUserT user = (SysUserT) jdbcTool.findObject(SysUserT.class, conn, sql, parameterList);
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
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		ArrayList<String> menuAllowList = new ArrayList<String>();
		ArrayList<String> permissionCodeList = new ArrayList<String>();
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = new StringBuilder();
		String dbType = this.findBbType();
		if (TypeDatabaseConst.MYSQL.equals(dbType)) {
			sb.append(" select  DISTINCT m.*,SN_ from   ");
			sb.append(" SYS_MENU m ");
			sb.append(" left join  SYS_GROUP_MENU rm ");
			sb.append(" on m.SYS_MENU_ID_=rm.SYS_MENU_ID_  ");
			sb.append(" left join  SYS_GROUP_USER ru  ");
			sb.append(" on ru.SYS_GROUP_ID_=rm.SYS_GROUP_ID_ ");
			sb.append(" where ru.SYS_USER_ID_=? ");
			sb.append(" order by SN_ asc,SYS_MENU_ID_ asc ");
		}
		if (TypeDatabaseConst.H2.equals(dbType)) {
			sb.append(" select  DISTINCT m.*,SN_ from   ");
			sb.append(" SYS_MENU m ");
			sb.append(" left join  SYS_GROUP_MENU rm ");
			sb.append(" on m.SYS_MENU_ID_=rm.SYS_MENU_ID_  ");
			sb.append(" left join  SYS_GROUP_USER ru  ");
			sb.append(" on ru.SYS_GROUP_ID_=rm.SYS_GROUP_ID_ ");
			sb.append(" where ru.SYS_USER_ID_=? ");
			sb.append(" order by SN_ asc,SYS_MENU_ID_ asc ");
		}
		if (TypeDatabaseConst.ORACLE.equals(dbType)) {
			sb.append(" select   DISTINCT m.SN_,m.SYS_MENU_ID_,m.URL_,m.PERMISSION_CODE_ from   ");
			sb.append(" SYS_MENU m ");
			sb.append(" left join  SYS_GROUP_MENU rm ");
			sb.append(" on m.SYS_MENU_ID_=rm.SYS_MENU_ID_ ");
			sb.append(" left join  SYS_GROUP_USER ru  ");
			sb.append(" on ru.SYS_GROUP_ID_=rm.SYS_GROUP_ID_ ");
			sb.append(" where ru.SYS_USER_ID_=? ");
			sb.append(" order by SN_ asc,SYS_MENU_ID_ asc ");
		}
		String sql = sb.toString();
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(cu.getSysUserId());
		listMap = jdbcUtil.findMapList(conn, sql, parameterList);
		for (Map<String, Object> map : listMap) {
			String url = (String) map.get("URL_");
			menuAllowList.add(url);
			String permission_code = (String) map.get("PERMISSION_CODE_");
			if (StringUtil.isNotBlank(permission_code)) {
				permissionCodeList.add(permission_code);
			}
		}
		cu.setMenuAllowList(menuAllowList);
		cu.setPermissionCodeList(permissionCodeList);
		return cu;
	}
	/**
	 * 
	 * 数据库类型
	 * 
	 */
	public String findBbType() {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		String dbType = jdbcUtil.getDbType();
		return dbType;
	}
	/**
	 * KEY设置
	 * 
	 * @param userid
	 * @return
	 */
	public String findKey(String userid) {
		String key = "#" + userid;
		return key;
	}
	/**
	 * 
	 * 保存或更新sso
	 * 
	 */
	public String saveOrUpdateSSO(String userId, String value) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		List<Map<String, Object>> listMap = null;
		String sql = "select * from SYS_SSO  where SYS_USER_ID_=?";
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(userId);
		listMap = jdbcUtil.findMapList(conn, sql, parameterList);
		if (listMap.size() == 1) {
			sql = "update SYS_SSO set VALUE_=? where SYS_USER_ID_=?";
			parameterList = new ArrayList<Object>();
			parameterList.add(value);
			parameterList.add(userId);
			jdbcUtil.execute(conn, sql, parameterList);
			return value;
		}
		if (listMap.size() == 0) {
			// 机器key
			String machine_key = BeanThreadLocal.findThreadLocal().get()
					.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalMachine), "");
			if (StringUtil.isNotBlank(machine_key)) {
				// 主键
				sql = "insert into SYS_SSO (sys_sso_ID_,SYS_USER_ID_,KEY_,VALUE_) values(?,?,?,?)";
				parameterList = new ArrayList<Object>();
				parameterList.add(UUID.randomUUID().toString());
				parameterList.add(userId);
				parameterList.add(this.findKey(userId));
				parameterList.add(value);
				jdbcUtil.execute(conn, sql, parameterList);
				return value;
			}
		}
		AssertUtil.isNull(null, "超出size个数");
		return null;
	}
	public boolean checkSSO(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CookieUtil cookieUtil = new CookieUtil();
		String token = cookieUtil.findCookieValueCurrentSysUser(request, response);
		Map<String, Object> map = this.findSSO2MapBySSO(token);
		if (map != null && map.size() > 0) {
			return true;
		}
		return false;
	}
	public String findUserIdBySSO(String sso) throws Exception {
		String value = null;
		Map<String, Object> resultMap = this.findSSO2MapBySSO(sso);
		if (resultMap != null) {
			// 得到
			value = (String) resultMap.get("SYS_USER_ID_");
		}
		return value;
	}
	public Map<String, Object> findSSO2MapBySSO(String sso) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		String sql = "select * from SYS_SSO where value_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(sso);
		Map<String, Object> resultMap = jdbcUtil.findMap(conn, sql, parameterList);
		return resultMap;
	}
	public String findSSOByUserId(String userId) throws Exception {
		String value = null;
		Map<String, Object> resultMap = this.findSSO2MapByUserId(userId);
		// 如果列表没有记录，表示该表未创建Sequance，则新增表Sequance
		if (resultMap != null) {
			// 得到
			value = (String) resultMap.get("VALUE_");
		}
		return value;
	}
	public Map<String, Object> findSSO2MapByUserId(String userId) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		String sql = "select * from SYS_SSO  where SYS_USER_ID_=?";
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(userId);
		Map<String, Object> returnMap = jdbcUtil.findMap(conn, sql, parameterList);
		return returnMap;
	}
}
