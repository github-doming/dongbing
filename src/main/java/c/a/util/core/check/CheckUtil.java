package c.a.util.core.check;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import c.a.config.ProjectConfig;
import c.a.util.core.string.StringUtil;
/**
 * 验证工具类
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class CheckUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 验证是否  字母或数字;
	 * 
	 * 只允许输入英文+数字+ - 符号的组合
	 * 
	 * 长度至少6位,不能超过20
	 * 
	 * @param expr
	 * @return
	 */
	public static Boolean isLetterOrNumber(String expr) {
		if (expr == null) {
			return false;
		}
		if (StringUtil.isBlank(expr)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[A-Za-z0-9-]{6,20}$");
		Matcher matcher = pattern.matcher(expr);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 特殊字符检验
	 * 
	 * @Title: isSpecialChar
	 * @Description:
	 *
	 * 				参数说明
	 * @param str
	 * @return 返回类型 boolean
	 */
	public static boolean isSpecialChar(String str) {
		String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\]<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，？]|\n|\r|\t";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 验证是否18位数字
	 * 
	 * @param expr
	 * @return
	 */
	public static Boolean isNumberLong(String expr) {
		if (expr == null) {
			return false;
		}
		if (StringUtil.isBlank(expr)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9]{1,18}$");
		Matcher matcher = pattern.matcher(expr);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 验证是否9位数字
	 * 
	 * @param expr
	 * @return
	 */
	public static Boolean isNumberInt(String expr) {
		if (expr == null) {
			return false;
		}
		if (StringUtil.isBlank(expr)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9]{1,9}$");
		Matcher matcher = pattern.matcher(expr);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 验证是否是数字
	 * 
	 * @param expr
	 * @return
	 */
	public static Boolean isNumber(String expr) {
		if (expr == null) {
			return false;
		}
		if (StringUtil.isBlank(expr)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher matcher = pattern.matcher(expr);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
	public static void main(String[] args) {
		Logger log = LogManager.getLogger(CheckUtil.class);
		// log.trace(CheckUtil.isNumber("123456789"));
		// log.trace(CheckUtil.isNumberInt("123456789123456789"));
		// log.trace(CheckUtil.isNumberLong("123456789123456789"));
		// log.trace(CheckUtil.isNumberLong("1"));
		log.trace(CheckUtil.isSpecialChar("-"));
		log.trace(CheckUtil.isSpecialChar("-ab`"));
		log.trace(CheckUtil.isLetterOrNumber("-ab`"));
		log.trace(CheckUtil.isLetterOrNumber("-ab6"));
		log.trace(CheckUtil.isLetterOrNumber("12345"));
		log.trace(CheckUtil.isLetterOrNumber("123456"));
		log.trace("end");
	}
}
