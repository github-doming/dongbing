package all.app_admin.root.util;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_user.t.entity.AppUserT;
import all.app_admin.root.login_not.current.CurrentAppUser;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.copy.CopyUtil;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.typeconst.TypeDatabaseConst;
import c.a.util.core.uuid.Uuid;
import c.x.platform.root.util.CookieUtil;
public class AppUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 保存 token到cookie
	 * 
	 */
	public String saveCookieCurrentAppUserBySSO(HttpServletRequest request, HttpServletResponse response, String tokenValue)
			throws Exception {
		CookieUtil cookieUtil = new CookieUtil();
		cookieUtil.saveCookieCurrentAppUser(response, tokenValue);
		return tokenValue;
	}
	/**
	 * 
	 * 保存 token到cookie和APP_TOKEN
	 * 
	 */
	public String saveCurrentAppUserByUserId(HttpServletRequest request, HttpServletResponse response, String userId)
			throws Exception {
		CookieUtil cookieUtil = new CookieUtil();
		String tokenValue = Uuid.create().toString();
		this.saveOrUpdateSSO(userId, tokenValue);
		cookieUtil.saveCookieCurrentAppUser(response, tokenValue);
		return tokenValue;
	}
	/**
	 * 
	 * 更新 CurrentUser
	 * 
	 * @deprecated
	 */
	public CurrentAppUser updateCurrentUser_updateRequestSession(HttpServletRequest request, String userId)
			throws Exception {
		// 检查用户是否存在
		CurrentAppUser currentUser = findAppUser(userId);
		if (currentUser != null) {
			// 把当前用户拥有的的菜单url放到session
			currentUser = findListMenuAllow(currentUser);
			// 找出sso
			String sso = this.findSSOByUserId(userId);
			currentUser.setSso(sso);
			// 放到redis
		}
		return currentUser;
	}
	/**
	 * 找出用户
	 * 
	 */
	public CurrentAppUser findAppUser(String userId) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		CurrentAppUser currentUser = new CurrentAppUser();
		// 查找
		String sql = "SELECT * FROM APP_USER u where u.app_user_id_=?";
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(userId);
		AppUserT user = (AppUserT) jdbcTool.findObject(AppUserT.class, conn, sql, parameterList);
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
	public CurrentAppUser findAppUserUpdateLoginTime(String accName, String passwordInput) throws Exception {
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
		CurrentAppUser currentUser = new CurrentAppUser();
		String sql = null;
		ArrayList<Object> parameterList = null;
		// 1查找账号表
		// 查找租户的连接
		sql = "SELECT * FROM APP_ACCOUNT u   where u.ACCOUNT_NAME_=? and u.PASSWORD_=?";
		parameterList = new ArrayList<Object>();
		parameterList.add(accName);
		parameterList.add(password);
		AppAccountT account = (AppAccountT) jdbcTool.findObject(AppAccountT.class, conn, sql, parameterList);
		if (account == null) {
			return null;
		}
		String userId = account.getAppUserId();
		// 2查找用户表
		sql = "SELECT * FROM APP_USER u   where u.APP_USER_ID_=?";
		parameterList = new ArrayList<Object>();
		parameterList.add(userId);
		AppUserT user = (AppUserT) jdbcTool.findObject(AppUserT.class, conn, sql, parameterList);
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
	public CurrentAppUser findAppUser(String accName, String passwordInput) throws Exception {
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
		CurrentAppUser currentUser = new CurrentAppUser();
		String sql = null;
		ArrayList<Object> parameterList = null;
		// 1查找账号表
		// 查找租户的连接
		sql = "SELECT * FROM APP_ACCOUNT u   where u.ACCOUNT_NAME_=? and u.PASSWORD_=?";
		parameterList = new ArrayList<Object>();
		parameterList.add(accName);
		parameterList.add(password);
		AppAccountT account = (AppAccountT) jdbcTool.findObject(AppAccountT.class, conn, sql, parameterList);
		if (account == null) {
			return null;
		}
		String userId = account.getAppUserId();
		// 2查找用户表
		sql = "SELECT * FROM APP_USER u   where u.APP_USER_ID_=?";
		parameterList = new ArrayList<Object>();
		parameterList.add(userId);
		AppUserT user = (AppUserT) jdbcTool.findObject(AppUserT.class, conn, sql, parameterList);
		if (user == null) {
			return null;
		}
		CopyUtil.copyProperties(currentUser, user);
		return currentUser;
	}
	/**
	 * 当前用户拥有的的菜单url
	 * 
	 * @param currentUser
	 * @return
	 * @throws Exception
	 */
	public CurrentAppUser findListMenuAllow(CurrentAppUser currentUser) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		ArrayList<String> menuAllowList = new ArrayList<String>();
		ArrayList<String> permissionCodeList = new ArrayList<String>();
		List<Map<String, Object>> listMap = null;
		StringBuilder sb = new StringBuilder();
		if (TypeDatabaseConst.MYSQL.equals(this.findBbType())) {
			sb.append(" select  DISTINCT m.*,SN_ from   ");
			sb.append(" APP_MENU m ");
			sb.append(" left join  APP_MENU_GROUP rm ");
			sb.append(" on m.APP_MENU_ID_=rm.APP_MENU_ID_  ");
			sb.append(" left join  APP_USER_GROUP ru  ");
			sb.append(" on ru.APP_GROUP_ID_=rm.APP_GROUP_ID_ ");
			sb.append(" where ru.APP_USER_ID_=? ");
			sb.append(" order by SN_ asc,APP_MENU_ID_ asc ");
		}
		if (TypeDatabaseConst.ORACLE.equals(this.findBbType())) {
			sb.append(" select   DISTINCT m.SN_,m.APP_MENU_ID_,m.URL_,m.PERMISSION_CODE_ from   ");
			sb.append(" APP_MENU m ");
			sb.append(" left join  APP_MENU_GROUP rm ");
			sb.append(" on m.APP_MENU_ID_=rm.APP_MENU_ID_ ");
			sb.append(" left join  APP_USER_GROUP ru  ");
			sb.append(" on ru.APP_GROUP_ID_=rm.APP_GROUP_ID_ ");
			sb.append(" where ru.APP_USER_ID_=? ");
			sb.append(" order by SN_ asc,APP_MENU_ID_ asc ");
		}
		String sql = sb.toString();
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(currentUser.getAppUserId());
		listMap = jdbcUtil.findMapList(conn, sql, parameterList);
		for (Map<String, Object> map : listMap) {
			String url = (String) map.get("ADMIN_URL_");
			menuAllowList.add(url);
			String permission_code = (String) map.get("PERMISSION_CODE_");
			if (StringUtil.isNotBlank(permission_code)) {
				permissionCodeList.add(permission_code);
			}
		}
		currentUser.setMenuAllowList(menuAllowList);
		currentUser.setPermissionCodeList(permissionCodeList);
		return currentUser;
	}
	/**
	 * 
	 * 数据库类型
	 * 
	 * @Description: @Title: findBbType @return 参数说明 @return String 返回类型 @throws
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
	 * 
	 */
	public String saveOrUpdateSSO(String userId, String value) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		List<Map<String, Object>> listMap = null;
		String sql = "select * from APP_TOKEN  where APP_USER_ID_=? and CHANNEL_TYPE_ =?";
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(userId);
		parameterList.add(ChannelTypeEnum.PC.getCode());
		listMap = jdbcUtil.findMapList(conn, sql, parameterList);
		if (listMap.size() == 1) {
			sql = "update APP_TOKEN set VALUE_=? where APP_USER_ID_=? and CHANNEL_TYPE_ =?";
			parameterList = new ArrayList<Object>();
			parameterList.add(value);
			parameterList.add(userId);
			parameterList.add(ChannelTypeEnum.PC.getCode());
			jdbcUtil.execute(conn, sql, parameterList);
			return value;
		}
		if (listMap.size() == 0) {
			// 机器key
			String machine_key = BeanThreadLocal.findThreadLocal().get()
					.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalMachine), "");
			if (StringUtil.isNotBlank(machine_key)) {
				// 主键
				sql = "insert into APP_TOKEN (APP_TOKEN_ID_,APP_USER_ID_,KEY_,VALUE_,CHANNEL_TYPE_ ) values(?,?,?,?,?)";
				parameterList = new ArrayList<Object>();
				parameterList.add(UUID.randomUUID().toString());
				parameterList.add(userId);
				parameterList.add(this.findKey(userId));
				parameterList.add(value);
				parameterList.add(ChannelTypeEnum.PC.getCode());
				jdbcUtil.execute(conn, sql, parameterList);
				return value;
			}
		}
		AssertUtil.isNull(null, "超出size个数");
		return null;
	}
	/**
	 * 支持集群
	 * 
	 * 检测其sso, 与用户的sso是否相等
	 * 
	 */
	public boolean checkSSO(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CookieUtil cookieUtil = new CookieUtil();
		String token = cookieUtil.findCookieValueCurrentAppUser(request, response);
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
			value = (String) resultMap.get("APP_USER_ID_");
		}
		return value;
	}
	public Map<String, Object> findSSO2MapBySSO(String value) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		String sql = "select * from APP_TOKEN where value_=? and CHANNEL_TYPE_ =?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(value);
		parameterList.add(ChannelTypeEnum.PC.getCode());
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
		String sql = "select * from APP_TOKEN  where APP_USER_ID_=? and CHANNEL_TYPE_ =? ";
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(userId);
		parameterList.add(ChannelTypeEnum.PC.getCode());
		Map<String, Object> returnMap = jdbcUtil.findMap(conn, sql, parameterList);
		return returnMap;
	}
}
