package c.x.all.simple.pattern.core;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
/**
 * 
 * 
 * 描述:
 * 
 * 正则表达式
 * 
 * 
 */
public class Pattern_by extends Pattern_ay {
	/**
	 * 过滤特殊字符
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	/**
	 * jbpm4的决定节点的表达式expr的值
	 * 
	 * @param expr
	 * @return
	 */
	public static String findExprFromPattern(String expr) {
		// Pattern pattern = Pattern.compile("^(\\#\\{)(\\D)(\\})$");
		Pattern pattern = Pattern.compile("^(\\#\\{)(\\S+?)(\\})$");
		Matcher matcher = pattern.matcher(expr);
		if (matcher.find()) {
			return matcher.group(2);
		}
		return expr;
	}
}
