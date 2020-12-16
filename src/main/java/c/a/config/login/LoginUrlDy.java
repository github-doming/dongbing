package c.a.config.login;

public class LoginUrlDy extends RequestLoginConfigCy {
	/**
	 * 开发模式
	 * 
	 * @return
	 */
	// public static Boolean develop_model = true;
	//public static Boolean DevelopModel = false;
	/**
	 * login页面do动作地址
	 */
	public final static String RequestDoLogin = "/platform/admin/login.do";
	/**
	 * login页面jsp地址
	 */
	public final static String RequestJspLogin = "/pages/c/x/platform/root/login_not/login_not.jsp";
	/**
	 * 开发模式:login页面jsp地址
	 */
	public final static String RequestJspLoginDevelopModel = "/pages/c/x/platform/root/login_not/develop/login_not.jsp";
	/**
	 * login.jsp提交到login.action(login动作action)
	 */
	public final static String TargetDoLogin = "/platform/admin/check.do";
}
