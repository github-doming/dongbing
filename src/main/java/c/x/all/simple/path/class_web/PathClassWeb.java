package c.x.all.simple.path.class_web;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import c.a.config.core.CharsetConfigAy;

/**
 * 
 * 启动容器，比如tomcat,jboss
 * 
 * 
 * 
 */
public class PathClassWeb {

	/**
	 * 读取文件路径(必须指定文件的绝对路径)
	 * 
	 * @param sourceNameStr
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public String findPath(String sourceNameStr)
			throws UnsupportedEncodingException {

		/**
		 * 去掉 根/
		 * 
		 * name = resolveName(name);
		 * 
		 * 
		 * ClassLoader.getSystemResource(name);
		 */

		URL sourceNameUrl = this.getClass().getResource(sourceNameStr);

		String pathStr = null;
		if (sourceNameUrl == null) {
			String str = "找不到文件sourceName=" + sourceNameStr;
			System.out.println(str);
			return null;

		} else {

		}

		pathStr = java.net.URLDecoder.decode(sourceNameUrl.getPath(),
				CharsetConfigAy.utf8);
		return pathStr;

	}

	/**
	 * 
	 * 启动容器时，找不到文件;
	 * 
	 * 读取文件路径(必须指定文件的相对路径);
	 * 
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param sourceNameStr
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public String findPath_Relative_getClassLoader_web(String sourceNameStr)
			throws UnsupportedEncodingException {

		URL sourceNameUrl = this.getClass().getClassLoader()
				.getSystemResource(sourceNameStr);

		String pathStr = null;
		if (sourceNameUrl == null) {
			String str = "找不到文件sourceName=" + sourceNameStr;
			System.out.println(str);
			return null;

		} else {

		}

		pathStr = java.net.URLDecoder.decode(sourceNameUrl.getPath(),
				CharsetConfigAy.utf8);
		return pathStr;

	}
	// -- 上面的方法不再更新 --//
}
