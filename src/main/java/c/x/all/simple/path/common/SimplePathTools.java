package c.x.all.simple.path.common;

import java.io.UnsupportedEncodingException;

import c.x.all.simple.path.class_web.PathClassWeb;

/**
 * 
 * 启动容器，比如tomcat,jboss
 * 
 * 
 * 
 */
public class SimplePathTools extends PathClassWeb {

	/**
	 * 
	 * 
	 * 读取文件路径(必须指定文件的绝对路径);
	 * 
	 * @param file_name
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public String findPath(String file_name)
			throws UnsupportedEncodingException {

		return this.findPath(file_name);

	}

}
