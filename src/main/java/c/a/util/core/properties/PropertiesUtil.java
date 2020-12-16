package c.a.util.core.properties;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import c.a.util.core.file.FileThreadLocal;
import c.a.util.core.file.FileUtil;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
import c.a.util.core.string.StringUtil;
/**
 * 
 * @author cxy
 * @Email:
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class PropertiesUtil {
	/**
	 * 
	 * 读取properties的全部信息
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> findProperties2Map(String fileName) throws IOException {
		Map returnMap = new HashMap();
		PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
		if (pathUtil == null) {
			pathUtil = new PathUtil();
		}
		String filePath = pathUtil.findPath(fileName);
		if (StringUtil.isNotBlank(filePath)) {
			Properties properties = this.findProperties(filePath);
			if (properties != null) {
				Set set = properties.keySet();
				for (Object keyObj : set) {
					String keyStr = (String) keyObj;
					Object valueObj = properties.getProperty(keyStr);
					if (valueObj == null) {
						returnMap.put(keyStr, null);
					} else {
						String value = (String) valueObj;
						returnMap.put(keyStr, value);
					}
				}
				return returnMap;
			}
		}
		return null;
	}
	/**
	 * 
	 * 读取Properties 所有的key
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Set<String> findKeyList2Set(String fileName) throws Exception {
		PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
		String filePath = pathUtil.findPath(fileName);
		if (StringUtil.isNotBlank(filePath)) {
			Properties properties = this.findProperties(filePath);
			// 转成中文
			// properties = this.toUTF8(properties);
			Set<String> keyList = properties.stringPropertyNames();
			return keyList;
		}
		return null;
	}
	/**
	 * 读取properties的全部信息
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	public Properties findProperties(String filePath) throws IOException {
		if (StringUtil.isNotBlank(filePath)) {
			FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
			if (fileUtil == null) {
				fileUtil = new FileUtil();
			}
			Properties properties = new Properties();
			InputStream is = fileUtil.findBufferedInputStream(filePath);
			if (is != null) {
				// 因为是通过字节流来读取文件;
				// 由于字节流是无法读取中文的，
				// 所以采取reader把inputStream转换成reader用字符流来读取中文
				Reader reader = new InputStreamReader(is, "UTF-8");
				properties.load(reader);
				return properties;
			}
		}
		return null;
	}
}
