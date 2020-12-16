package c.x.platform.root.boot;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.tools.mvc.action.MvcAction;
import c.a.tools.mvc.dto.MvcActionDto;
import c.a.tools.mvc.exception.BizRuntimeException;
import c.a.tools.mvc.nut.MvcConfig;
/**
 * 
 * 所有action类的初始化;
 * 
 * 
 * 理论上访问顺序一般先Listener，后Servlet;
 * 
 * 测试时,访问顺序Listener和Servlet是不同线程开启,没有访问顺序,不过Listener一般先执行;
 * 
 * @Description:
 * @ClassName:
 * @date 2012年3月10日 上午10:28:38
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
@WebServlet(name = "BootServletBy", urlPatterns = {}, loadOnStartup = 2)
public class BootServletBy extends HttpServlet {
	protected Logger log = LogManager.getLogger(BootServletBy.class);
	private String logFun = "BootServletBy功能,";
	private String logFunError = "BootServletBy功能出错,";
	private static final long serialVersionUID = 1L;
	@Override
	public String getInitParameter(String name) {
		return super.getInitParameter(name);
	}
	@Override
	public void init() throws ServletException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		this.executeTime(request, response);
	}
	@Override
	public void destroy() {
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.executeTime(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.executeTime(request, response);
	}
	public void executeTime(HttpServletRequest request, HttpServletResponse response) {
		// Calendar calendar = Calendar.getInstance();
		// long timeStart = calendar.getTimeInMillis();
		long timeStart = System.currentTimeMillis();
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		// 执行业务
		//System.out.println("BootServletBy 初始化开始");
		log.trace("BootServletBy 初始化开始");
		log.trace("class=" + this.getClass().getName());
		// log.trace("start");
		String servletPath = null;
		if (request != null) {
			servletPath = request.getServletPath();
			String logStr = "调用url=" + servletPath;
			log.info(logStr);
		}
		try {
			this.execute(request, response);
		} catch (Exception e) {
			String logStr = "Exception,出错的url=" + servletPath;
			logStr = logFunError + logStr;
			log.error(logStr);
			log.error(logStr, e);
			// 打印到控制台
			e.printStackTrace();
			// 不需要跳转,直接打印JSON
			// 必须重新抛出异常给系统才能跳转
			// throw e;
		}
		contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
			// 对象设置为空，自动回收内存
			contextUtil = null;
		}
		// calendar = Calendar.getInstance();
		// long timeEnd = calendar.getTimeInMillis();
		long timeEnd = System.currentTimeMillis();
		long timeSpend = timeEnd - timeStart;
		log.info("花费时间timeSpend=" + timeSpend);
	}
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 返回方法的值
		String returnObject = null;
		List<MvcActionDto> actionList = MvcConfig.findInstance().findList();
		for (MvcActionDto mvcAction : actionList) {
			String url = mvcAction.getUrl();
			// System.out.println("url=" + url);
			String boot = mvcAction.getBoot();
			// System.out.println("boot=" + boot);
			if ("true".equals(boot)) {
				Class classObj = null;
				try {
					String logStr = "调用action=" + mvcAction.getActionClass();
					log.info(logStr);
					classObj = Class.forName(mvcAction.getActionClass());
				} catch (ClassNotFoundException e) {
					String logStr = "找不到类异常，加载action类出错,出错的url=" + url;
					logStr = logFunError + logStr;
					log.error(logStr);
					log.error(logStr, e);
					e.printStackTrace();
					return;
				}
				MvcAction baseAction = null;
				try {
					baseAction = (MvcAction) classObj.newInstance();
				} catch (InstantiationException e) {
					String logStr = "实例化异常，实例化action出错,出错的url=" + url;
					logStr = logFunError + logStr;
					log.error(logStr);
					log.error(logStr, e);
					e.printStackTrace();
					return;
				} catch (IllegalAccessException e) {
					String logStr = "不合法访问异常，实例化action出错,出错的url=" + url;
					logStr = logFunError + logStr;
					log.error(logStr);
					log.error(logStr, e);
					e.printStackTrace();
					return;
				}
				// 不要用反射,因为反射不能与业务异常bizException绑定起来
				if (true) {
					try {
						returnObject = baseAction.doExecute().getMvcResult();
					} catch (BizRuntimeException e1) {
						String logStr = "BizRuntimeException，业务出错,出错的url=" + url;
						logStr = logFunError + logStr;
						log.error(logStr);
						log.error(logStr, e1);
						// 打印到控制台
						e1.printStackTrace();
						// 必须重新抛出异常给系统才能跳转
						throw e1;
					} catch (RuntimeException e2) {
						String logStr = "运行中RuntimeException，业务出错,出错的url=" + url;
						logStr = logFunError + logStr;
						log.error(logStr);
						log.error(logStr, e2);
						// 打印到控制台
						e2.printStackTrace();
						// 不需要跳转,直接打印JSON
						// 必须重新抛出异常给系统才能跳转
						// throw e2;
					} catch (Throwable t) {
						String logStr = "Throwable，业务出错,出错的url=" + url;
						logStr = logFunError + logStr;
						log.error(logStr);
						log.error(logStr, t);
						// 打印到控制台
						t.printStackTrace();
						return;
					}
				}
			}
		}
	}
}