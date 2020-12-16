package c.a.tools.string;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.util.core.string.StringUtil;
public class StringSub {
	protected Logger log = LogManager.getLogger(StringSub.class);
	/**
	 * 匹配,是否包含字符串
	 * 
	 * @param source
	 * @param substring
	 * @return
	 */
	public boolean hasSubString(String source, String substring) {
		if (StringUtil.isBlank(substring) || StringUtil.isBlank(source)) {
			return false;
		}
		int index = source.indexOf(substring);
		if (index == 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 匹配,是否包含字符串
	 * 
	 * @param source
	 * @param subStrList
	 * @return
	 */
	public boolean hasSubStringList(String source, List<String> subStrList) {
		for (String str : subStrList) {
			boolean b = hasSubString(source, str);
			if (b) {
				return b;
			}
		}
		return false;
	}
	/**
	 * 匹配,是否包含字符串
	 * 
	 * @param source
	 * @param substring
	 *            数组
	 * @return
	 */
	public boolean hasSubStringArray(String source, String[] substring) {
		for (String str : substring) {
			boolean b = hasSubString(source, str);
			if (b) {
				return b;
			}
		}
		return false;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringSub cfss = new StringSub();
		boolean b = cfss.hasSubString("/index", "/");
		// log.trace(b);
	}
}
