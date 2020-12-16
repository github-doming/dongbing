package c.a.tools.mvc.action;
import javax.servlet.ServletContext;

import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
/**
 * 
 * 
 * 
 * 
 * 自定义MVC框架BaseAction
 * 
 * 
 */
public class MvcPathAction extends MvcInitializationAction {
	/**
	 * WebAppPath
	 * 
	 * @return
	 */
	public String findPathWebApp(ServletContext servletContext) {
		String pathRoot = servletContext.getRealPath("/");
		return pathRoot;
	}
	/**
	 * WebAppPath
	 * 
	 * @return
	 */
	public String findPathWebApp() {
		String pathRoot = this.getServletContext().getRealPath("/");
		return pathRoot;
	}
	/**
	 * 模板的ClassPath
	 * 
	 * 该方法暂时弃用
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findPathClass(String sourceName) throws Exception {
		PathUtil pathUtil =PathThreadLocal.findThreadLocal().get();
		return pathUtil.findPath(sourceName);
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
