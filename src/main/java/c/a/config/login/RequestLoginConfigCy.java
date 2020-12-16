package c.a.config.login;

import c.a.config.session_not.SessionNotUrlBy;

/**
 * Request上下文
 * 
 * @date 2017年2月7日 上午11:22:04
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class RequestLoginConfigCy extends SessionNotUrlBy {
	/**
	 * login页项目名
	 */
	public final static String CurrentUserType = "j_project";
	/**
	 * login页机构
	 */
	public final static String CurrentUserTenant = "j_org";
	/**
	 * login页验证码
	 */
	public final static String CurrentUserCaptcha = "j_verifyCode";
	/**
	 * login页用户
	 */
	public final static String CurrentUserName = "j_username";
	/**
	 * login页密码
	 */
	public final static String CurrentUserPassword = "j_password";
	// -- 下面的方法不再更新 --//
	// -- login.jsp的参数 --//
	// -- {--//
	/**
	 * 当前租户不存在
	 */
	public final static String TenantErrorKey = "tenant_error";
	/**
	 * 当前租户不存在
	 */
	public final static String TenantErrorValue = "true";
	/**
	 * 当前user不存在
	 */
	public final static String UserErrorKey = "user_error";

	/**
	 * 当前user不存在
	 */
	public final static String UserErrorValue = "true";
	// -- } --//
	// -- login.jsp的参数--//
	// -- 上面的方法不再更新 --//
}
