package c.x.all.simple.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import c.a.config.core.HttpSessionConfigIy;
import c.a.tools.log.custom.common.BaseLog;
import c.a.util.core.date.DateThreadLocal;
import c.x.platform.root.login_not.current.CurrentSysUser;

public class HttpSessionListener_ay implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		BaseLog.trace("监听器HttpSessionListener创建 sessionCreated");

		BaseLog.trace("session创建,id=" + se.getSession().getId());

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

		BaseLog.trace("监听器HttpSessionListener 销毁sessionDestroyed");

		HttpSession session = se.getSession();

		ServletContext c_ServletContext = session.getServletContext();

		long long_LastAccessedTime = session.getLastAccessedTime();
		long long_CreationTime = session.getCreationTime();

		BaseLog.trace("最后访问时间long_LastAccessedTime="
				+ DateThreadLocal.findThreadLocal().get().doLongSecond2String(long_LastAccessedTime));

		BaseLog.trace("创建时间long_CreationTime="
				+ DateThreadLocal.findThreadLocal().get().doLongSecond2String(long_CreationTime));

		// 取得登录的用户名

		CurrentSysUser cu = (CurrentSysUser) session
				.getAttribute(HttpSessionConfigIy.CurrentAppUser);

		// 从在线列表中删除用户名
		BaseLog.trace("session=" + session);
		if (cu != null) {
			BaseLog.trace("用户[" + cu.getSysUserName() + "]" + "已经退出！");

		}

	}

}
