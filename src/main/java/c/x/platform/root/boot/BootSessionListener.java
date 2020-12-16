package c.x.platform.root.boot;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
public class BootSessionListener implements HttpSessionListener {
	public static Map userMap = new HashMap();
	private BootSessionContext bootSessionContext = BootSessionContext.findInstance();
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		bootSessionContext.addSession(httpSessionEvent.getSession());
	}
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();
		bootSessionContext.delSession(httpSession);
	}
}