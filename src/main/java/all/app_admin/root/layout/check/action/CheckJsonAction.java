package all.app_admin.root.layout.check.action;
import java.util.Map;
import javax.servlet.http.Cookie;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.eclipse.jetty.util.StringUtil;

import all.app.ay.app_verify_account.service.AppVerifyAccountService;
import all.app_admin.root.login_not.current.CurrentAppUser;
import all.app_admin.root.util.AppUtil;
import c.a.config.SysConfig;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.redis.RedisUtil;
import c.x.platform.root.login_not.action.LoginNotAction;
import c.x.platform.root.login_not.current.CurrentSysUser;
import c.x.platform.root.util.CookieUtil;
import c.x.platform.root.util.SysUtil;
import redis.clients.jedis.Jedis;
public class CheckJsonAction extends LoginNotAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	public CheckJsonAction() {
		this.database = true;
		this.transaction = true;
		this.login = false;
	}
	@Override
	public JsonTcpBean executeLogin() throws Exception {
		return this.returnJsonTcpBean(SysConfig.configValueTrue);
	}
	@Override
	public String execute() throws Exception {
		log.trace("经过CheckJsonAction=" + this.getClass().getName());
		JsonTcpBean jrb = LogThreadLocal.findLog();
		String jsonStr = this.request.getParameter("json");
		Map<String, Object> jsonMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);
		// 验证码
		if ("true".equals(SysConfig.findInstance().findMap().get(SysConfig.commLocalDebug))) {
		} else {
			CookieUtil cookieUtil = new CookieUtil();
			Cookie cookie = cookieUtil.findCookieByName(request, SysConfig.CookieSession);
			AssertUtil.isNull(cookie, "comm.local.debug需要设置成true,否则界面需要验证码");
			String sessionId = cookie.getValue();
			// 因为集群问题，验证码不能用redis保存，只能用数据库;想用redis最好把验证码做成微服务;
			if (false) {
				RedisUtil redisUtil = RedisUtil.findInstance();
				Jedis jedis = redisUtil.findJedis();
				String verifyCodeSession = redisUtil.findSysAdminVerifyCode(jedis, sessionId);
			}
			AppVerifyAccountService appVerifyAccountService = new AppVerifyAccountService();
			String verifyCodeSession = appVerifyAccountService.findCodeBySessionId(sessionId);
			String verifyCode = BeanThreadLocal.findThreadLocal().get().find(jsonMap.get(SysConfig.CurrentUserCaptcha),
					"");
			if (verifyCode.equals(verifyCodeSession)) {
			} else {
				// 跳转
				jrb.setSuccess(false);
				jrb.setMsgSys("验证码出错");
				String resultJsonStr = JsonThreadLocal.bean2json(jrb);
				response.getWriter().print(resultJsonStr);
				return null;
			}
		}
		// 检查项目名
		String type = BeanThreadLocal.find(jsonMap.get(SysConfig.CurrentUserType), "");
		log.trace("type=" + type);
		if (StringUtil.isBlank(type)) {
			type = BeanThreadLocal.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalType), "");
		}
		// 检查机构名
		String tenantCode = BeanThreadLocal.find(jsonMap.get(SysConfig.CurrentUserTenant), "");
		log.trace("tenantCode=" + tenantCode);
		if (StringUtil.isBlank(tenantCode)) {
			tenantCode = BeanThreadLocal.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalTenant), "");
		}
		if (StringUtil.isBlank(tenantCode)) {
			// 跳转
			jrb.setSuccess(false);
			jrb.setMsgSys("机构名出错");
			String resultJsonStr = JsonThreadLocal.bean2json(jrb);
			response.getWriter().print(resultJsonStr);
			return null;
		}
		CookieUtil cookieUtil = new CookieUtil();
		cookieUtil.saveCookieCurrentType(response, type);
		cookieUtil.saveCookieCurrentTenant(response, tenantCode);
		if ("sys".equals(type)) {
			// 检查用户名
			SysUtil sysUtil = new SysUtil();
			String accName = BeanThreadLocal.find(jsonMap.get(SysConfig.CurrentUserName), "");
			log.trace("accName=" + accName);
			String userPassword = BeanThreadLocal.find(jsonMap.get(SysConfig.CurrentUserPassword), "");
			// 检查用户是否存在
			CurrentSysUser currentUser = sysUtil.findSysUserUpdateLoginTime(accName, userPassword);
			if (currentUser == null) {
				// 跳转
				jrb.setSuccess(false);
				jrb.setMsgSys("用户名或密码出错");
				String resultJsonStr = JsonThreadLocal.bean2json(jrb);
				response.getWriter().print(resultJsonStr);
				return null;
			} else {
				String sso = sysUtil.saveCurrentSysUserByUserId(request, response, currentUser.getSysUserId());
				sysUtil.saveOrUpdateSSO(currentUser.getSysUserId(), sso);
				String redisLocalStart = BeanThreadLocal
						.find(SysConfig.findInstance().findMap().get("redis.local.start"), "");
				if ("true".equals(redisLocalStart)) {
					// redis
					RedisUtil redisUtil = RedisUtil.findInstance();
					Jedis jedis = redisUtil.findJedis();
					redisUtil.saveCurrentUser(jedis, type, tenantCode);
				}
				// 跳转
				jrb.setSuccess(true);
				jrb.setMsgSys("成功通过");
				String resultJsonStr = JsonThreadLocal.bean2json(jrb);
				response.getWriter().print(resultJsonStr);
				return null;
			}
		}
		if ("app".equals(type)) {
			// 检查用户名
			AppUtil appUtil = new AppUtil();
			String userName = BeanThreadLocal.find(jsonMap.get(SysConfig.CurrentUserName), "");
			log.trace("userName=" + userName);
			String userPassword = BeanThreadLocal.find(jsonMap.get(SysConfig.CurrentUserPassword), "");
			// 检查用户是否存在
			CurrentAppUser currentUser = appUtil.findAppUserUpdateLoginTime(userName, userPassword);
			if (currentUser == null) {
				// 跳转
				jrb.setSuccess(false);
				jrb.setMsgSys("用户名或密码出错");
				String resultJsonStr = JsonThreadLocal.bean2json(jrb);
				response.getWriter().print(resultJsonStr);
				return null;
			} else {
				String sso = appUtil.saveCurrentAppUserByUserId(request, response, currentUser.getAppUserId());
				// appUtil.saveOrUpdateSSO(currentUser.getAppUserId(), sso);
				String redisLocalStart = BeanThreadLocal
						.find(SysConfig.findInstance().findMap().get("redis.local.start"), "");
				if ("true".equals(redisLocalStart)) {
					// redis
					RedisUtil redisUtil = RedisUtil.findInstance();
					Jedis jedis = redisUtil.findJedis();
					redisUtil.saveCurrentUser(jedis, type, tenantCode);
				}
				// 跳转
				jrb.setSuccess(true);
				jrb.setMsgSys("成功通过");
				String resultJsonStr = JsonThreadLocal.bean2json(jrb);
				response.getWriter().print(resultJsonStr);
				return null;
			}
		}
		// 跳转
		return null;
	}
}
