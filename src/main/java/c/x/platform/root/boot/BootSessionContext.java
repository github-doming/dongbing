package c.x.platform.root.boot;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
	public class BootSessionContext {
	private static BootSessionContext instance;
	private HashMap hashMap;
	private BootSessionContext() {
		hashMap = new HashMap();
	}
	public static BootSessionContext findInstance() {
		if (instance == null) {
			instance = new BootSessionContext();
		}
		return instance;
	}
	public synchronized void addSession(HttpSession session) {
		if (session != null) {
			hashMap.put(session.getId(), session);
		}
	}
	public synchronized void delSession(HttpSession session) {
		if (session != null) {
			hashMap.remove(session.getId());
		}
	}
	public synchronized HttpSession findSession(String sessionId) {
		if (sessionId == null)
			return null;
		return (HttpSession) hashMap.get(sessionId);
	}
}