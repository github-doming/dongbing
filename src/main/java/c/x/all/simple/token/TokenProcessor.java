package c.x.all.simple.token;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * 
 * 
 * <B>
 * <P>
 * 描述: 利用JSP，防止重复提交的问题
 * 
 * 
 * 
 * </P>
 * </B>
 * 
 * 
 * 
 */
public class TokenProcessor {
	private long privious;// 上次生成表单标识号得时间值
	private static TokenProcessor instance = new TokenProcessor();
	public static String FORM_TOKEN_KEY = "FORM_TOKEN_KEY";
	private TokenProcessor() {
	}
	public static TokenProcessor getInstance() {
		return instance;
	}
	/**
	 * 验证请求中得标识号是否有效，如果请求中的表单标识与当前用户session中的相同，返回结果true
	 * 
	 * @param request
	 * @return
	 */
	public synchronized boolean isTokenValid(HttpServletRequest request) {
		// 未避免session对象不存在时候创建Session对象
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.out.println("1");
			return false;
		}
		String saved = (String) session.getAttribute(FORM_TOKEN_KEY);
		if (saved == null) {
			System.out.println("2");
			return false;
		}
		String token = (String) request.getParameter(FORM_TOKEN_KEY);
		if (token == null) {
			System.out.println("3");
			return false;
		}
		System.out.println("saved=" + saved);
		System.out.println("token=" + token);
		return saved.equals(token);
	}
	/**
	 * 清除存储在当前用户session中的表单标识号
	 * 
	 * @param request
	 */
	public synchronized void reset(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(FORM_TOKEN_KEY);
	}
	/**
	 * 产生表单标识号并将之保存在当前用户得session中
	 * 
	 * @param request
	 */
	public synchronized void saveToken(HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			byte id[] = session.getId().getBytes();
			long current = System.currentTimeMillis();
			if (current == privious) {
				current++;
			}
			privious = current;
			byte now[] = String.valueOf(current).getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(id);
			md.update(now);
			String token = toHex(md.digest());
			session.setAttribute(FORM_TOKEN_KEY, token);
			// BaseSystem.out.println("产生表单标识号，session.setAttribute(FORM_TOKEN_KEY, token)",this);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将一个字节数转换成十六进制得字符串
	 * 
	 * @param buffer
	 * @return
	 */
	public String toHex(byte buffer[]) {
		StringBuilder sb = new StringBuilder(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0x60) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();
	}
	/**
	 * 创建令牌
	 * 
	 * @throws Exception
	 */
	public String findToken(HttpServletRequest request) throws Exception {
		/**
		 * 创建令牌
		 */
		// BaseSystem.out.println("创建令牌", this);
		// 在Action中调用其方法，生成其隐含字段值:
		TokenProcessor tokemProcessor = TokenProcessor.getInstance();
		tokemProcessor.saveToken(request);
		String token = (String) request.getSession().getAttribute(
				tokemProcessor.FORM_TOKEN_KEY);
		return token;
	}
	/**
	 * 创建令牌
	 * 
	 * @throws Exception
	 */
	public void createToken(HttpServletRequest request) throws Exception {
		/**
		 * 创建令牌
		 */
		// BaseSystem.out.println("创建令牌", this);
		// 在Action中调用其方法，生成其隐含字段值:
		TokenProcessor tokemProcessor = TokenProcessor.getInstance();
		tokemProcessor.saveToken(request);
		String token = (String) request.getAttribute(
				tokemProcessor.FORM_TOKEN_KEY);
		request.setAttribute("form_token_key", tokemProcessor.FORM_TOKEN_KEY);
		request.setAttribute("token", token);
	}
}