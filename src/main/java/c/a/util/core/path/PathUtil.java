package c.a.util.core.path;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import c.a.config.core.CharsetConfigAy;
/**
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class PathUtil {
	/**
	 * 读取文件绝对路径(必须指定文件的相对路径)
	 * 
	 * @Title: findPathAbsolute
	 * @Description:
	 * @param path
	 * @return
	 * @throws UnsupportedEncodingException 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public String findPathAbsolute(String path)
			throws UnsupportedEncodingException {
		/**
		 * 去掉 根/
		 * 
		 * name = resolveName(name);
		 * 
		 * 
		 * ClassLoader.getSystemResource(name);
		 */
		URL urlSourceName = this.getClass().getResource(path);
		String pathStr = null;
		if (urlSourceName == null) {
			// String logStr = "找不到文件sourceName=" + path;
			return null;
		} else {
		}
		pathStr = java.net.URLDecoder.decode(urlSourceName.getPath(),
				CharsetConfigAy.utf8);
		return pathStr;
	}
	/**
	 * 读取文件绝对路径(必须指定文件的相对路径)
	 * 
	 * @Title: findPath
	 * @Description:
	 * @param path
	 * @return
	 * @throws UnsupportedEncodingException 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public String findPath(String path) throws UnsupportedEncodingException {
		return this.findPathAbsolute(path);
	}
	public String findPathTrunk() {
		String pathTrunk = System.getProperty("user.dir");
		return pathTrunk;
	}
	public String findPathTrunkSrc() {
		String pathTrunk = System.getProperty("user.dir");
		pathTrunk = pathTrunk + "/src/main/java";
		return pathTrunk;
	}
	public String findPathTrunkSrc(String path) {
		String pathTrunk = System.getProperty("user.dir");
		pathTrunk = pathTrunk + "/src/main/java" + path;
		return pathTrunk;
	}
}
