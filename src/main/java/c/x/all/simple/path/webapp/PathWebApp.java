package c.x.all.simple.path.webapp;

import javax.servlet.ServletContext;

import c.a.util.core.uuid.Uuid;

public class PathWebApp {
	/**
	 * 取得WebApp下的绝对路径
	 * 
	 * @param servletContext
	 * @return
	 * @throws Exception
	 */
	public String findServletContextRealPath_root(ServletContext servletContext) {

		String cServletContext_RealPath = servletContext.getRealPath("/");
		return cServletContext_RealPath;
	}

	/**
	 * 取得WebApp下的绝对路径
	 * 
	 * @param path
	 * @param servletContext
	 * @return
	 * @throws Exception
	 */
	public String findServletContextRealPath(String path, ServletContext servletContext) {
		if (false) {

			String targetDirectory = servletContext.getRealPath("/upload");
		}

		String cServletContext_RealPath = servletContext.getRealPath(path);
		return cServletContext_RealPath;
	}

	public String findServletContextRealPath_pic(ServletContext servletContext) throws Exception {

		String cServletContext_RealPath = servletContext.getRealPath("/");

		cServletContext_RealPath = cServletContext_RealPath + "pic/" + Uuid.create() + ".jpg";

		return cServletContext_RealPath;
	}

}
