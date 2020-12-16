package c.a.config.core;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import all.app_admin.root.login_not.current.CurrentAppUser;
import all.app_admin.root.util.AppUtil;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.bean.BeanUtil;
import c.a.util.core.date.DateThreadLocal;
import c.a.util.core.date.DateUtil;
import c.a.util.core.enums.EnumThreadLocal;
import c.a.util.core.enums.EnumUtil;
import c.a.util.core.file.FileThreadLocal;
import c.a.util.core.file.FileUtil;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.json.JsonUtil;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
import c.a.util.core.reflect.ReflectThreadLocal;
import c.a.util.core.reflect.ReflectUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.request.RequestUtil;
import c.a.util.core.security.BASE64ThreadLocal;
import c.a.util.core.security.BASE64Util;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
import c.x.platform.root.login_not.current.CurrentSysUser;
import c.x.platform.root.util.CookieUtil;
import c.x.platform.root.util.SysUtil;
/**
 * 环境上下文
 * 
 * @author cxy
 * @Email:
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class ContextUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 当前登录用户的PermissionGrade
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer findCurrentUserPermissionGrade(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if ("sys".equals(this.findCurrentUserType(request, response))) {
			return this.findCurrentSysUserCore(request, response).getPermissionGrade();
		}
		if ("app".equals(this.findCurrentUserType(request, response))) {
			return this.findCurrentAppUserCore(request, response).getPermissionGrade();
		}
		return null;
	}
	/**
	 * 当前登录用户的id
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findCurrentUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if ("sys".equals(this.findCurrentUserType(request, response))) {
			return this.findCurrentSysUserId(request, response);
		}
		if ("app".equals(this.findCurrentUserType(request, response))) {
			return this.findCurrentAppUserId(request, response);
		}
		return null;
	}
	/**
	 * 当前登录用户的名称
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findCurrentUserName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if ("sys".equals(this.findCurrentUserType(request, response))) {
			return this.findCurrentSysUserName(request, response);
		}
		if ("app".equals(this.findCurrentUserType(request, response))) {
			return this.findCurrentAppUserName(request, response);
		}
		return null;
	}
	/**
	 * 
	 * 当前登录用户
	 * 
	 * @return
	 */
	public Object findCurrentUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if ("sys".equals(this.findCurrentUserType(request, response))) {
			return this.findCurrentSysUserCore(request, response);
		}
		if ("app".equals(this.findCurrentUserType(request, response))) {
			return this.findCurrentAppUserCore(request, response);
		}
		return null;
	}
	public String findCurrentSysUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if ("sys".equals(this.findCurrentUserType(request, response))) {
			//String currentSysUserId = (String) request.getSession().getAttribute(SysConfig.CurrentSysUserId);
			String currentSysUserId =null;
			if (StringUtil.isBlank(currentSysUserId)) {
				CurrentSysUser currentSysUser = this.findCurrentSysUserCore(request, response);
				if (currentSysUser != null) {
					//request.getSession().setAttribute(SysConfig.CurrentSysUserId, currentSysUser.getSysUserId());
					 currentSysUserId= currentSysUser.getSysUserId();
				}
			}
			return currentSysUserId;
		}
		return null;
	}
	public String findCurrentSysUserName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if ("sys".equals(this.findCurrentUserType(request, response))) {
			//String currentSysUserName = (String) request.getSession().getAttribute(SysConfig.CurrentSysUserName);
			String currentSysUserName =null;
			if (StringUtil.isBlank(currentSysUserName)) {
				CurrentSysUser currentSysUser = this.findCurrentSysUserCore(request, response);
				if (currentSysUser != null) {
					//request.getSession().setAttribute(SysConfig.CurrentSysUserName, currentSysUser.getSysUserName());
					currentSysUserName= currentSysUser.getSysUserName();
				}
			}
			return currentSysUserName;
		}
		return null;
	}
	public String findCurrentAppUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if ("app".equals(this.findCurrentUserType(request, response))) {
			//String currentAppUserId = (String) request.getSession().getAttribute(SysConfig.CurrentAppUserId);
			String currentAppUserId =null;
			if (StringUtil.isBlank(currentAppUserId)) {
				CurrentAppUser currentAppUser = this.findCurrentAppUserCore(request, response);
				if (currentAppUser != null) {
					//request.getSession().setAttribute(SysConfig.CurrentAppUserId, currentAppUser.getAppUserId());
					currentAppUserId= currentAppUser.getAppUserId();
				}
			}
			return currentAppUserId;
		}
		return null;
	}
	public String findCurrentAppUserName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if ("app".equals(this.findCurrentUserType(request, response))) {
			//String currentAppUserName = (String) request.getSession().getAttribute(SysConfig.CurrentAppUserName);
			String currentAppUserName =null;
			if (StringUtil.isBlank(currentAppUserName)) {
				CurrentAppUser currentAppUser = this.findCurrentAppUserCore(request, response);
				if (currentAppUser != null) {
					//request.getSession().setAttribute(SysConfig.CurrentAppUserName, currentAppUser.getAppUserName());
					currentAppUserName= currentAppUser.getAppUserName();
				}
			}
			return currentAppUserName;
		}
		return null;
	}
	/**
	 * 当前登录用户的Type
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findCurrentUserType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CookieUtil cookieUtil = new CookieUtil();
		String type = cookieUtil.findCookieValueCurrentType(request, response);
		if (StringUtil.isBlank(type)) {
			type = BeanThreadLocal.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalType), "");
		}
		return type;
	}
	/**
	 * 当前登录用户的TenantCode
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findCurrentUserTenantCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CookieUtil cookieUtil = new CookieUtil();
		String tenantCode = cookieUtil.findCookieValueCurrentTenant(request, response);
		if (StringUtil.isBlank(tenantCode)) {
			tenantCode = BeanThreadLocal.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalTenant), "");
		}
		return tenantCode;
	}
	/**
	 * 
	 * 当前登录用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public CurrentSysUser findCurrentSysUserCore(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUtil sysUtil = new SysUtil();
		CookieUtil cookieUtil = new CookieUtil();
		String sso = cookieUtil.findCookieValueCurrentSysUser(request, response);
		String userId = sysUtil.findUserIdBySSO(sso);
		if (StringUtil.isBlank(userId)) {
			return null;
		}
		CurrentSysUser currentUser = sysUtil.findSysUser(userId);
		currentUser = sysUtil.findListMenuAllow(currentUser);
		return currentUser;
	}
	/**
	 * 
	 * 当前登录用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public CurrentAppUser findCurrentAppUserCore(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppUtil appUtil = new AppUtil();
		CookieUtil cookieUtil = new CookieUtil();
		String sso = cookieUtil.findCookieValueCurrentAppUser(request, response);
		String userId = appUtil.findUserIdBySSO(sso);
		if (StringUtil.isBlank(userId)) {
			return null;
		}
		CurrentAppUser currentUser = appUtil.findAppUser(userId);
		currentUser = appUtil.findListMenuAllow(currentUser);
		return currentUser;
	}
	/**
	 * 当前所访问的url的菜单名称
	 * 
	 * @throws SQLException
	 */
	public String findCurrentMenuName(AyDao dao, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if ("sys".equals(this.findCurrentUserType(request, response))) {
			String returnStr = null;
			List<Object> parameterList = new ArrayList<Object>();
			Map<String, Object> map = null;
			String sql = null;
			String menuDb_id = request.getParameter("menuDb_id");
			log.trace("当前 menu_path=" + menuDb_id);
			if (StringUtil.isNotBlank(menuDb_id)) {
				parameterList.add(menuDb_id);
				sql = "SELECT SYS_MENU_NAME_ FROM SYS_MENU  where SYS_MENU_ID_=?";
			} else {
				String servletPath = request.getServletPath();
				// log.trace("当前url路径="+servletPath);
				parameterList.add(servletPath);
				sql = "SELECT SYS_MENU_NAME_ FROM SYS_MENU  where URL_=?";
			}
			map = dao.findMap(sql, parameterList);
			if (map != null) {
				returnStr = (String) map.get("SYS_MENU_NAME_");
			} else {
				// log.trace("map is null");
			}
			return returnStr;
		}
		if ("app".equals(this.findCurrentUserType(request, response))) {
			String returnStr = null;
			List<Object> parameterList = new ArrayList<Object>();
			Map<String, Object> map = null;
			String sql = null;
			String menuDb_id = request.getParameter("menuDb_id");
			log.trace("当前 menu_path=" + menuDb_id);
			if (StringUtil.isNotBlank(menuDb_id)) {
				parameterList.add(menuDb_id);
				sql = "SELECT APP_MENU_NAME_ FROM APP_MENU  where APP_MENU_ID_=?";
			} else {
				String servletPath = request.getServletPath();
				// log.trace("当前url路径="+servletPath);
				parameterList.add(servletPath);
				sql = "SELECT APP_MENU_NAME_ FROM APP_MENU  where ADMIN_URL_=?";
			}
			map = dao.findMap(sql, parameterList);
			if (map != null) {
				returnStr = (String) map.get("APP_MENU_NAME_");
			} else {
				// log.trace("map is null");
			}
			return returnStr;
		}
		return null;
	}
	/**
	 * 
	 * 开始
	 * 
	 * @return
	 * @throws Exception
	 */
	public long start() throws Exception {
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		// 时间
		Calendar startCalendar = Calendar.getInstance();
		long startCalendarLong = startCalendar.getTimeInMillis();
		return startCalendarLong;
	}
	/**
	 * 
	 * 结束
	 * 
	 * @return
	 * @throws Exception
	 */
	public void end(long startCalendarLong) {
		// 时间
		Calendar endCalendar = Calendar.getInstance();
		long endCalendarLong = endCalendar.getTimeInMillis();
		long ms = endCalendarLong - startCalendarLong;
		// mysql 花费时间spend time=710
		log.trace("花费时间spend time=" + ms);
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
		}
	}
	public void init() {
		// System.out.println("注册");
		PathUtil pathUtil = new PathUtil();
		PathThreadLocal.findThreadLocal().set(pathUtil);
		FileUtil fileUtil = new FileUtil();
		FileThreadLocal.findThreadLocal().set(fileUtil);
		EnumUtil enumUtil = new EnumUtil();
		EnumThreadLocal.findThreadLocal().set(enumUtil);
		DateUtil dateUtil = new DateUtil();
		DateThreadLocal.findThreadLocal().set(dateUtil);
		BeanUtil beanUtil = new BeanUtil();
		BeanThreadLocal.findThreadLocal().set(beanUtil);
		JsonUtil jsonUtil =new JsonUtil();
		JsonThreadLocal.findThreadLocal().set(jsonUtil);
		ReflectUtil reflectUtil = new ReflectUtil();
		ReflectThreadLocal.findThreadLocal().set(reflectUtil);
		BASE64Util base64Util = new BASE64Util();
		BASE64ThreadLocal.findThreadLocal().set(base64Util);
		RequestUtil requestUtil = new RequestUtil();
		RequestThreadLocal.findThreadLocal().set(requestUtil);
		// String location="";
		// StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		// location = "类名："+stacks[2].getClassName() + "\n函数名：" +
		// stacks[2].getMethodName()
		// + "\n文件名：" + stacks[2].getFileName() + "\n行号："
		// + stacks[2].getLineNumber() + "";
		// log.trace("init="+location);
		// log.trace("init
		// ThreadLocal="+Thread.currentThread().getName()+",class="+this.getClass().getName());
	}
	public void remove() {
		// String location="";
		// StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		// location = "类名："+stacks[2].getClassName() + "\n函数名：" +
		// stacks[2].getMethodName()
		// + "\n文件名：" + stacks[2].getFileName() + "\n行号："
		// + stacks[2].getLineNumber() + "";
		// log.trace("remove="+location);
		// log.trace("remove
		// ThreadLocal="+Thread.currentThread().getName()+",class="+this.getClass().getName());
		if (true) {
			// System.out.println("反注册");
			List<JsonTcpBean> jrbList = LogThreadLocal.findLogThreadLocalList().get();

			if (jrbList != null) {
				LogThreadLocal.findLogThreadLocalList().remove();
			}
			JsonTcpBean jrb = LogThreadLocal.findLogThreadLocalLast().get();
			if (jrb != null) {
				LogThreadLocal.findLogThreadLocalLast().remove();
			}
			PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
			if (pathUtil != null) {
				PathThreadLocal.findThreadLocal().remove();
				pathUtil = null;
			}
			FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
			if (fileUtil != null) {
				FileThreadLocal.findThreadLocal().remove();
				fileUtil = null;
			}
			EnumUtil enumUtil = EnumThreadLocal.findThreadLocal().get();
			if (enumUtil != null) {
				EnumThreadLocal.findThreadLocal().remove();
				enumUtil = null;
			}
			DateUtil dateUtil = DateThreadLocal.findThreadLocal().get();
			if (dateUtil != null) {
				DateThreadLocal.findThreadLocal().remove();
				dateUtil = null;
			}
			BeanUtil beanUtil = BeanThreadLocal.findThreadLocal().get();
			if (beanUtil != null) {
				BeanThreadLocal.findThreadLocal().remove();
				beanUtil = null;
			}
			JsonUtil jsonUtil = JsonThreadLocal.findThreadLocal().get();
			if (jsonUtil != null) {
				JsonThreadLocal.findThreadLocal().remove();
				jsonUtil = null;
			}
			ReflectUtil reflectUtil = ReflectThreadLocal.findThreadLocal().get();
			if (reflectUtil != null) {
				ReflectThreadLocal.findThreadLocal().remove();
				reflectUtil = null;
			}
			BASE64Util base64Util = BASE64ThreadLocal.findThreadLocal().get();
			if (base64Util != null) {
				BASE64ThreadLocal.findThreadLocal().remove();
				base64Util = null;
			}
			RequestUtil requestUtil = RequestThreadLocal.findThreadLocal().get();
			if (requestUtil != null) {
				RequestThreadLocal.findThreadLocal().remove();
				requestUtil = null;
			}
		}
	}
}
