package c.x.platform.root.login_not.action;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import all.app_admin.root.login_not.current.CurrentAppUser;
import all.gen.alipay_log_auth.t.entity.AlipayLogAuthT;
import all.gen.alipay_log_auth.t.service.AlipayLogAuthTService;
import all.gen.sys_log_request.t.entity.SysLogRequestT;
import all.gen.sys_log_request.t.service.SysLogRequestTService;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.random.RandomUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.request.RequestUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
import c.x.platform.root.login_not.current.CurrentSysUser;
import c.x.platform.root.sso.action.SSOAction;
import c.x.platform.root.util.CookieUtil;
public abstract class LoginNotAction extends SSOAction {
	public boolean login = false;
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract JsonTcpBean executeLogin() throws Exception;
	/**
	 * 实现父类方法
	 * 
	 */
	@Override
	public JsonTcpBean executeSSO() throws Exception {
		if (false) {
			log.trace("login=" + login);
			log.trace("所访问的action=" + this.getClass().getName());
		}
		if (login) {
			/**
			 * 
			 * 需要login
			 */
			return this.login();
		} else {
			/**
			 * 
			 * 不需要login
			 */
			return this.loginNot();
		}
	}
	/**
	 * 
	 * 不需要login
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean loginNot() throws Exception {
		JsonTcpBean returnStr = this.executeLogin();
		return returnStr;
	}
	/**
	 * 
	 * 需要login
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean login() throws Exception {
		CookieUtil cookieUtil = new CookieUtil();
		String type = this.findCurrentUserType();
		if (StringUtil.isBlank(type)) {
			// 跳转
			String contextPath = RequestThreadLocal.findThreadLocal().get().findContextPath(request);
			String returnPage = SysConfig.RequestJspLoginSessionNot;
			response.sendRedirect(contextPath + returnPage);
		}
		if ("sys".equals(type)) {
			/**
			 * 
			 * 任一页面;
			 */
			String sso = cookieUtil.findCookieValueCurrentSysUser(request, response);
			if (StringUtil.isNotBlank(sso)) {
				this.saveSysLogRequest();
				/**
				 * 执行业务
				 */
				// log.trace("当前菜单名称="+this.findCurrentMenuName());
				request.setAttribute("menuDb_name", this.findCurrentMenuName());
				// 下一个过滤器
				JsonTcpBean returnStr = this.executeLogin();
				return returnStr;
			} else {
				// 跳转
				// String contextPath = (String)
				// SysConfig.findInstance().findMap().get(SysConfig.keyContextPath);
				String contextPath = RequestThreadLocal.findThreadLocal().get().findContextPath(request);
				String returnPage = SysConfig.RequestJspLoginSessionNot;
				response.sendRedirect(contextPath + returnPage);
			}
		}
		if ("app".equals(type)) {
			/**
			 * 
			 * 任一页面;
			 */
			String sso = cookieUtil.findCookieValueCurrentAppUser(request, response);
			if (StringUtil.isNotBlank(sso)) {
				this.saveSysLogRequest();
				/**
				 * 执行业务
				 */
				// log.trace("当前菜单名称="+this.findCurrentMenuName());
				request.setAttribute("menuDb_name", this.findCurrentMenuName());
				// 下一个过滤器
				JsonTcpBean returnStr = this.executeLogin();
				return returnStr;
			} else {
				// 跳转
				// String contextPath = (String)
				// SysConfig.findInstance().findMap().get(SysConfig.keyContextPath);
				String contextPath = RequestThreadLocal.findThreadLocal().get().findContextPath(request);
				String returnPage = SysConfig.RequestJspLoginSessionNot;
				response.sendRedirect(contextPath + returnPage);
			}
		}
		return null;
	}
	/**
	 * 保存日志
	 * 
	 * @Title: saveSysLogRequest
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void saveSysLogRequest() throws Exception {
		String commLocalSysRequest = BeanThreadLocal
				.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalSysLogRequest), "false");
		if ("true".equals(commLocalSysRequest)) {
			String commLocalProject = SysConfig.findInstance().findMap().get("comm.local.type").toString();
			String ip = null;
			String servletPath = null;
			String mac = null;
			if (request != null) {
				ip = RequestThreadLocal.getThreadLocal().get().findIP(request);
				servletPath = request.getServletPath();
				mac = RequestThreadLocal.getThreadLocal().get().findMAC();
			}
			StringBuilder stringBuffer = new StringBuilder();
			stringBuffer.append("项目comm.local.type=" + commLocalProject);
			stringBuffer.append(";");
			stringBuffer.append("当前IP=" + ip);
			stringBuffer.append(";");
			stringBuffer.append("ServletPath=" + servletPath);
			stringBuffer.append(";");
			log.trace(stringBuffer.toString());
			Date date = new Date();
			SysLogRequestTService service = new SysLogRequestTService();
			SysLogRequestT entity = new SysLogRequestT();
			// 追踪ID
			if (request != null) {
				JsonTcpBean logJsonTcpBean = LogThreadLocal.findLog();
				String traceId = BeanThreadLocal.find(request.getParameter("traceId"), "");
				String spanParentId = BeanThreadLocal.find(request.getParameter("spanId"), traceId);
				String spanId = Uuid.findInstance().toString();
				logJsonTcpBean.setTraceId(traceId);
				logJsonTcpBean.setSpanParentId(spanParentId);
				logJsonTcpBean.setSpanId(spanId);
				LogThreadLocal.setLog(logJsonTcpBean);
				entity.setTraceId(traceId);
				entity.setSpanId(spanId);
				entity.setSpanParentId(spanParentId);
			} else {
				JsonTcpBean logJsonTcpBean = LogThreadLocal.findLog();
				String traceId = logJsonTcpBean.getTraceId();
				String spanParentId = logJsonTcpBean.getSpanParentId();
				String spanId = Uuid.findInstance().toString();
				logJsonTcpBean.setTraceId(traceId);
				logJsonTcpBean.setSpanParentId(spanParentId);
				logJsonTcpBean.setSpanId(spanId);
				LogThreadLocal.setLog(logJsonTcpBean);
				entity.setTraceId(traceId);
				entity.setSpanId(spanId);
				entity.setSpanParentId(spanParentId);
			}
			// set
			if (request != null) {
				entity.setSysUserId(this.findCurrentSysUserId());
				entity.setSysUserName(this.findCurrentSysUserName());
				entity.setAppUserId(this.findCurrentAppUserId());
				entity.setAppUserName(this.findCurrentAppUserName());
				RequestUtil requestUtil = RequestThreadLocal.findThreadLocal().get();
				// String requestDataStr =requestUtil.findData(request);
				// entity.setData(requestDataStr);
				entity.setRequestParameter(requestUtil.findParameter(request));
				entity.setRequestUrl(request.getRequestURL().toString());
				entity.setRequestUri(request.getRequestURI());
				entity.setServletPath(request.getServletPath());
				String servletContextPath = request.getServletContext().getRealPath("/");
				entity.setServletContextPath(servletContextPath);
			}
			entity.setCreateTime(date);
			entity.setUpdateTime(date);
			entity.setCreateTimeLong(date.getTime());
			entity.setUpdateTimeLong(date.getTime());
			entity.setName(RandomUtil.findRandomInteger(1000000).toString());
			entity.setThread(Thread.currentThread().getName());
			entity.setIp(ip);
			entity.setMac(mac);
			// 系统属性
			Properties properties = System.getProperties();
			String userDir = properties.getProperty("user.dir");
			entity.setTomcatBin(userDir);
			entity.setState(TaskStateEnum.OPEN.getCode());
			service.save(entity);
		}
	}
	/**
	 * 打印日志
	 * 
	 * @deprecated
	 * @Title: saveSysLogRequest_v1
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void saveSysLogRequest_v1() throws Exception {
		String commLocalProject = SysConfig.findInstance().findMap().get("comm.local.type").toString();
		String ip = RequestThreadLocal.getThreadLocal().get().findIP(request);
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append("项目comm.local.type=" + commLocalProject);
		stringBuffer.append(";");
		stringBuffer.append("当前IP=" + ip);
		stringBuffer.append(";");
		stringBuffer.append("ContextPath=" + findContextPath());
		stringBuffer.append(";");
		stringBuffer.append("当前用户ID=" + this.findCurrentUserId());
		stringBuffer.append(";");
		stringBuffer.append("当前菜单名称=" + this.findCurrentMenuName());
		stringBuffer.append(";");
		stringBuffer.append("LoginNotAction");
		stringBuffer.append(";");
		log.trace(stringBuffer.toString());
	}
	/**
	 * 
	 * @Title: findContextIP
	 * @Description:
	 *
	 * 				参数说明
	 * @return 返回类型 String
	 * @throws UnknownHostException
	 */
	public String findIPLocal() throws UnknownHostException {
		String contextPath = RequestThreadLocal.findThreadLocal().get().findIPLocal();
		return contextPath;
	}
	/**
	 * 
	 * @Title: findContextPath
	 * @Description:
	 *
	 * 				参数说明
	 * @return 返回类型 String
	 */
	public String findContextPath() {
		String contextPath = RequestThreadLocal.findThreadLocal().get().findContextPath(request);
		return contextPath;
	}
	/**
	 * 当前所访问的url的菜单名称
	 * 
	 * @throws SQLException
	 */
	public String findCurrentMenuName() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentMenuName(this.findDao(), request, response);
	}
	/**
	 * 
	 * 当前登录用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public CurrentSysUser findCurrentSysUser() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentSysUserCore(request, response);
	}
	public String findCurrentSysUserId() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentSysUserId(request, response);
	}
	public String findCurrentSysUserName() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentSysUserName(request, response);
	}
	/**
	 * 
	 * 当前登录用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public CurrentAppUser findCurrentAppUser() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentAppUserCore(request, response);
	}
	public String findCurrentAppUserId() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentAppUserId(request, response);
	}
	public String findCurrentAppUserName() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentAppUserName(request, response);
	}
	/**
	 * 
	 * 当前登录用户
	 * 
	 * @return
	 */
	public Object findCurrentUser() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentUser(request, response);
	}
	/**
	 * 当前登录用户的id
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findCurrentUserId() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentUserId(request, response);
	}
	/**
	 * 当前登录用户的名称
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findCurrentUserName() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentUserName(request, response);
	}
	/**
	 * 当前登录用户的PermissionGrade
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer findCurrentUserPermissionGrade() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentUserPermissionGrade(request, response);
	}
	/**
	 * 当前登录用户的Project
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findCurrentUserType() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentUserType(request, response);
	}
	/**
	 * 当前登录用户的TenantCode
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findCurrentUserTenantCode() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentUserTenantCode(request, response);
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
