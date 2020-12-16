package c.x.all.simple.pattern.core;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import c.a.tools.log.custom.common.BaseLog;
/**
 * 
 * 
 * 描述:
 * 
 * 正则表达式
 * 
 * 
 */
public class Pattern_ay {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// CfPattern.test_parentheses();
		// CfPattern.test_parentheses_replaceAll();
		Pattern_ay.test_subStr();
	}
	/**
	 * 测试求额外的子字符串的解析表达式
	 * 
	 */
	public static void test_subStr() {
		//
		Pattern_ay cp = new Pattern_ay();
		String expr = cp.subStr("/pages/ss/sysuser.action", "/pages");
		BaseLog.out(expr);
	}
	/**
	 * 求额外的子字符串的解析表达式
	 * 
	 * @param expr
	 * @return
	 */
	public static String subStr(String parent, String expr) {
		Pattern pattern = Pattern.compile("^(" + expr + ")(\\S+?)$");
		Matcher matcher = pattern.matcher(parent);
		if (matcher.find()) {
			return matcher.group(2);
		}
		return expr;
	}
	/**
	 * 测试替代圆括号
	 * 
	 */
	public static void test_parentheses_replaceAll() {
		//
		String expr = "(abc)";
		Pattern_ay cp = new Pattern_ay();
		expr = cp.parentheses_replaceAll(expr);
		BaseLog.out(expr);
		//
		expr = "（(abc)）";
		cp = new Pattern_ay();
		expr = cp.parentheses_replaceAll(expr);
		BaseLog.out(expr);
		//
		expr = "(（abc）)";
		cp = new Pattern_ay();
		expr = cp.parentheses_replaceAll(expr);
		BaseLog.out(expr);
	}
	/**
	 * 替代圆括号
	 * 
	 * @param expr
	 * @return
	 */
	public String parentheses_replaceAll(String expr) {
		Pattern pattern = Pattern.compile("[()（）]");
		Matcher matcher = pattern.matcher(expr);
		if (matcher.find()) {
			return matcher.replaceAll("#").trim();
		}
		return null;
	}
	/**
	 * 
	 * 测试去掉圆括号的解析表达式;
	 * 
	 * 不能去掉双重括号;
	 */
	public static void test_parentheses() {
		//
		String expr = "(abc)";
		Pattern_ay cp = new Pattern_ay();
		expr = cp.parentheses(expr);
		BaseLog.out(expr);
		//
		expr = "（abc）";
		cp = new Pattern_ay();
		expr = cp.parentheses(expr);
		BaseLog.out(expr);
		//
		expr = "（abc)";
		cp = new Pattern_ay();
		expr = cp.parentheses(expr);
		BaseLog.out(expr);
		//
		expr = "(abc）";
		cp = new Pattern_ay();
		expr = cp.parentheses(expr);
		BaseLog.out(expr);
		//
		expr = "（(abc)）";
		cp = new Pattern_ay();
		expr = cp.parentheses(expr);
		System.out.println(expr);
		//
		expr = "(（abc）)";
		cp = new Pattern_ay();
		expr = cp.parentheses(expr);
		System.out.println(expr);
	}
	public String parentheses(String expr) {
		return this.parentheses_v1(expr);
		// return this.parentheses_v2(expr);
	}
	/**
	 * 去掉圆括号的解析表达式
	 * 
	 * @param expr
	 * @return
	 */
	public String parentheses_v2(String expr) {
		Pattern pattern = Pattern.compile("^([\\S]*?)([\\S]+?)([\\S]*?)$");
		Matcher matcher = pattern.matcher(expr);
		if (matcher.find()) {
			return matcher.group(3);
		}
		return expr;
	}
	/**
	 * 去掉圆括号的解析表达式;
	 * 
	 * 不能去掉双重括号;
	 * 
	 * @param expr
	 * @return
	 */
	public String parentheses_v1(String expr) {
		// group(0)指全部
		Pattern pattern = Pattern
				.compile("^([\\S]*?)([\\(\\（])([\\S]+?)([\\）\\)])([\\S]*?)$");
		Matcher matcher = pattern.matcher(expr);
		if (matcher.find()) {
			return matcher.group(3);
		}
		return expr;
	}
}
