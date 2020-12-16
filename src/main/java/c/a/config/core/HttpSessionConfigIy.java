package c.a.config.core;
/**
 * 
 * HttpSession上下文
 * 
 * @Description:
 * @date 2017年2月6日 上午11:32:01
 * @author cxy
 * @Email: 
 * @Copyright
 * 
 */
public class HttpSessionConfigIy extends ServletContextConfigHy {
	// -- 下面的方法不再更新 --//
	/**
	 * 当前系统登录用户的CurrentType,保存在session
	 */
	public final static String CurrentType = "CurrentType";
	/**
	 * 当前系统登录用户的CurrentTenant,保存在session
	 */
	public final static String CurrentTenant = "CurrentTenant";
	/**
	 * 当前系统登录用户,保存在session
	 */
	public final static String CurrentSysUser = "CurrentSysUser";
	/**
	 * 当前系统登录用户,保存在session
	 */
	public final static String CurrentAppUser = "CurrentAppUser";
	/**
	 * 
	 */
	public final static String CurrentUser = "CurrentUser";
	/**
	 * 
	 */
	public final static String CookieSession = "CookieSession";
	// -- 上面的方法不再更新 --//
}
