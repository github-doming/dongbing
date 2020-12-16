package c.x.platform.root.layout.logout.action;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.redis.RedisUtil;
import c.x.platform.root.login_not.action.LoginNotAction;
import c.x.platform.root.util.CookieUtil;
import redis.clients.jedis.Jedis;
public class AdminLogoutAction extends LoginNotAction {
	public AdminLogoutAction() {
		transaction = true;
		this.login = true;
	}
	@Override
	public JsonTcpBean  executeLogin() throws Exception {
		return this.returnJsonTcpBean(SysConfig.configValueTrue);
	}
	@Override
	public String execute() throws Exception {
		/**
		 * 1从logout退出,清空session
		 */
		CookieUtil cookieUtil = new CookieUtil();
		cookieUtil.saveCookieCurrentSysUser(response, "");
		cookieUtil.saveCookieCurrentAppUser(response, "");
		request.getSession().setAttribute(SysConfig.CurrentSysUserId,"");
		request.getSession().setAttribute(SysConfig.CurrentSysUserName,"");
		request.getSession().setAttribute(SysConfig.CurrentAppUserId,"");
		request.getSession().setAttribute(SysConfig.CurrentAppUserName,"");
		//redis
		String redisLocalStart = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("redis.local.start"),
				"");
		if ("true".equals(redisLocalStart)) {
			RedisUtil redisUtil = RedisUtil.findInstance();
			Jedis jedis = redisUtil.findJedis();
			redisUtil.saveCurrentUser(jedis, "", "");
		}
		/**
		 * 2 跳转
		 */
		if ("true".equals(SysConfig.findInstance().findMap().get(SysConfig.commLocalDebug))) {
			return "loginNotDevelop";
		} else {
			return "loginNot";
		}
	}
}
