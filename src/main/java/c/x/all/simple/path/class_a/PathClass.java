package c.x.all.simple.path.class_a;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import c.a.config.core.CharsetConfigAy;

public class PathClass {

	/**
	 * 读取文件路径(必须指定文件的相对路径)
	 * 
	 * @param sourceNameStr
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public String findPath_Relative_getClassLoader(String sourceNameStr)
			throws UnsupportedEncodingException {

		Class class_object = Object.class;
		URL sourceNameUrl = class_object.getClassLoader().getSystemResource(
				sourceNameStr);

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
	 * 读取文件路径(必须指定文件的绝对路径)
	 * 
	 * @param sourceNameStr
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public String findPath_Absolute_getResource(String sourceNameStr)
			throws UnsupportedEncodingException {

		Class class_object = Object.class;
		/**
		 * 去掉 根/
		 * 
		 * name = resolveName(name);
		 * 
		 * 
		 * ClassLoader.getSystemResource(name);
		 */

		URL sourceNameUrl = class_object.getResource(sourceNameStr);

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

}
