package c.a.config.session_not;

import c.a.config.core.CharsetConfigAy;

public class SessionNotUrlBy extends CharsetConfigAy{
	/**
	 * login页面jsp地址(没有session)
	 */
	public static String RequestJspLoginSessionNot = "/pages/c/x/platform/root/login_not/session_not/login_not_session_not.jsp";
	
	//public static String RequestJspLoginSessionNot =null;
	// public final static String RequestJspLoginSessionNotMiniui =
	// "/pages/c/x/miniui/layout/login_not/login_not.jsp";

	/**
	 * 该方法暂时弃用 login页面result地址(没有session)
	 * 
	 */
	public final static String TargetDoSessionNot = "/session_not.do";
	/**
	 * 
	 * 该方法暂时弃用 login页面jsp地址(没有session)
	 */
	public final static String TargetJspSessionNot = "/pages/c/x/platform/root/login_not/session_not/login_session_not.jsp";
}
