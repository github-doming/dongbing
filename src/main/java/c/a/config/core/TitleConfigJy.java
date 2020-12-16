package c.a.config.core;

import org.jsoup.helper.StringUtil;

import c.a.config.SysConfig;

public class TitleConfigJy extends HttpSessionConfigIy {
	private static String platformTitleLogin = null;
	private static String platformTitleMain = null;
	private static String platformTitleVersion = null;
	public static String title_login() {
		try {
			if (StringUtil.isBlank(platformTitleLogin)) {
				platformTitleLogin = SysConfig.findInstance().findMap().get("platform.title.login").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return platformTitleLogin;
	}
	public static String title_main() {
		try {
			if (StringUtil.isBlank(platformTitleMain)) {
				platformTitleMain = SysConfig.findInstance().findMap().get("platform.title.main").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return platformTitleMain;
	}
	public static String title_version() {
		try {
			if (StringUtil.isBlank(platformTitleVersion)) {
				platformTitleVersion = SysConfig.findInstance().findMap().get("platform.title.version").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return platformTitleVersion;
	}
}