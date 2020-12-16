package c.a.util.core.string;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * 
 * 字符串工具类
 * 
 * @Description:
 * @ClassName: StringUtil
 * @date 2017年2月22日 上午11:21:12
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class StringUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 不为空
	 * 
	 * @param obj
	 * @return
	 */
	public static Boolean isNotBlank(Object obj) {
		return !StringUtil.isBlank(obj);
	}
	/**
	 * 为空
	 * 
	 * @param obj
	 * @return
	 */
	public static Boolean isBlank(Object obj) {
		String str = null;
		// Integer i = null;
		if (obj == null) {
			return true;
		} else {
			if (obj instanceof String) {
				str = (String) obj;
			} else {
				// 非String类型
				// AssertUtil.notNull(null, "非String类型");
				str = obj.toString();
			}
		}
		Boolean isBlank = StringUtils.isBlank(str);
		if (isBlank) {
			return true;
		}
		if ("null".equalsIgnoreCase(str)) {
			return true;
		}
		return false;
	}
	/**
	 * 不为空
	 * 
	 */
	public static Boolean isNotBlank(Object[] obj) {
		return !StringUtil.isBlank(obj);
	}
	/**
	 * 为空
	 * 
	 */
	public static Boolean isBlank(Object[] objectArray) {
		if (objectArray == null) {
			return true;
		}
		Boolean isBlank = false;
		for (Object object : objectArray) {
			isBlank = isBlank(object);
			if (isBlank) {
				break;
			}
		}
		return isBlank;
	}
	/**
	 * 得到空值
	 * 
	 * @return
	 */
	public static String trim() {
		return "";
	}
	/**
	 * 1;只能是string,不能是string[]
	 * 
	 * 2;得到request的值
	 * 
	 * @param name
	 * @param valueDefault
	 *            缺省值
	 * @param request
	 * @return
	 */
	public static String trim2Str(String name, String valueDefault, HttpServletRequest request) {
		String value = request.getParameter(name);
		if (StringUtil.isBlank(value)) {
			return valueDefault;
		} else {
			return value.trim();
		}
	}
	/**
	 * 1;只能是string,不能是string[]
	 * 
	 * 2;得到request的值
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String trim2Str(String name, HttpServletRequest request) {
		String value = request.getParameter(name);
		if (StringUtil.isBlank(value)) {
			return "";
		} else {
			return value.trim();
		}
	}
	/**
	 * 1;只能是string,不能是string[]
	 * 
	 * 2;得到request的值
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String trim(String name, HttpServletRequest request) {
		String value = request.getParameter(name);
		if (StringUtil.isBlank(value)) {
			return null;
		} else {
			return value.trim();
		}
	}
	/**
	 * trim
	 * 
	 * @param strObject
	 * @return
	 */
	public static String trimObject2Str(Object strObject) {
		if (strObject != null) {
			if (strObject instanceof String) {
				String str = (String) strObject;
				return str.trim();
			} else {
				return null;
			}
		} else {
			;
			return null;
		}
	}
	/**
	 * trim
	 * 
	 * @param str
	 * @return
	 */
	public static String trim2str(String str) {
		if (str != null) {
			return str.trim();
		} else {
			return "";
		}
	}
	/**
	 * trim
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (str != null) {
			return str.trim();
		} else {
			return null;
		}
	}
	/**
	 * 默认线
	 * 
	 * @return
	 */
	public static String findLineStart() {
		return findLineAsterisk();
	}
	/**
	 * 默认线
	 * 
	 * @return
	 */
	public static String findLineEnd() {
		return findLineHashKey();
	}
	/**
	 * 默认线
	 * 
	 * @return
	 */
	public static String findLine() {
		return findLineDividing();
	}
	/**
	 * 默认线2
	 * 
	 * @return
	 */
	public static String findLine2() {
		return findLineDividingDouble();
	}
	/**
	 * 
	 * 打印井号线
	 */
	public static String findLineHashKey() {
		// 50个井号组成
		String str = "#################################################";
		return str;
	}
	/**
	 * 
	 * 打印星号线
	 */
	public static String findLineAsterisk() {
		// 50个星号组成
		String str = "*************************************************";
		return str;
	}
	/**
	 * 
	 * 打印双下横线
	 */
	public static String findLineDividingDouble() {
		// 50个短线组成
		String str = "=================================================";
		return str;
	}
	/**
	 * 
	 * 打印分隔线
	 */
	public static String findLineDividing() {
		// 50个短线组成
		String str = "-------------------------------------------------";
		return str;
	}
	/**
	 * 全部转换成小写，并且去掉下划线
	 * 
	 * 
	 * StringBuilder
	 * 
	 * @param columnName
	 * @return
	 */
	public static String findPackageName(String columnName) {
		return findPackageName_v2(columnName);
	}
	/**
	 * 全部转换成小写，并且去掉下划线
	 * 
	 * 
	 * StringBuilder
	 * 
	 * @param columnName
	 * @return
	 */
	private static String findPackageName_v2(String columnName) {
		StringBuilder target = new StringBuilder();
		String[] strArray = columnName.split("_");
		for (String str : strArray) {
			target.append(str.toLowerCase());
		}
		return target.toString();
	}
	/**
	 * 全部转换成小写，并且去掉下划线;
	 * 
	 * 规则:sys_user_id -> sysuserid;
	 * 
	 * String
	 * 
	 * @param columnName
	 * @return
	 */
	private static String findPackageName_v1(String columnName) {
		String target = "";
		String[] strArray = columnName.split("_");
		for (String str : strArray) {
			target = target + str.toLowerCase();
		}
		return target;
	}
	/**
	 * 头个字母转换成大写，并且去掉下划线;
	 * 
	 * 
	 * 规则:sys_user_id -> SysUserId;
	 * 
	 */
	public static String findClassName(String fieldName) {
		return findClassName_v2(fieldName);
	}
	/**
	 * 头个字母转换成大写，并且去掉下划线;
	 * 
	 * 
	 * 规则:sys_user_id -> SysUserId;
	 * 
	 * 
	 * StringBuilder 与split
	 * 
	 * @param columnName
	 * @return
	 */
	private static String findClassName_v2(String columnName) {
		StringBuilder target = new StringBuilder();
		String[] strArray = columnName.split("_");
		for (int i = 0; i < strArray.length; i++) {
			int str_l = strArray[i].length();
			if (str_l == 0) {
				continue;
			}
			if (i == 0) {
				target.append(strArray[i].substring(0, 1).toUpperCase());
				target.append(strArray[i].substring(1, str_l).toLowerCase());
			} else {
				target.append(strArray[i].substring(0, 1).toUpperCase());
				target.append(strArray[i].substring(1, str_l).toLowerCase());
			}
		}
		return target.toString();
	}
	/**
	 * 头个字母转换成大写，并且去掉下划线;
	 * 
	 * 规则:sys_user_id -> SysUserId;
	 * 
	 * 
	 * String
	 * 
	 * @param columnName
	 * @return
	 */
	private static String findClassName_v1(String columnName) {
		String target = "";
		String[] strArray = columnName.split("_");
		for (String str : strArray) {
			int lengthInt = str.length();
			target = target + str.substring(0, 1).toUpperCase() + str.substring(1, lengthInt);
		}
		return target;
	}
	/**
	 * 头个字母转换成大写，并且去掉下划线;
	 * 
	 * 
	 * 规则:sys_user_id -> SysUserId;
	 * 
	 */
	public static String findMethodNameByColumn(String columnName) {
		return findClassName_v2(columnName);
	}
	/**
	 * 头个字母转换成大写，并且去掉下划线;
	 * 
	 * 
	 * 规则:sys_user_id -> SysUserId;
	 * 
	 * 
	 * StringBuilder 与split
	 * 
	 * @param columnName
	 * @return
	 */
	public static String findMethodNameByField(String fieldName) {
		StringBuilder target = new StringBuilder();
		String[] strArray = fieldName.split("_");
		for (int i = 0; i < strArray.length; i++) {
			int str_l = strArray[i].length();
			if (str_l == 0) {
				continue;
			}
			if (i == 0) {
				target.append(strArray[i].substring(0, 1).toUpperCase());
				target.append(strArray[i].substring(1, str_l));
			} else {
				target.append(strArray[i].substring(0, 1).toUpperCase());
				target.append(strArray[i].substring(1, str_l));
			}
		}
		return target.toString();
	}
	/**
	 * 头个字母转换成大写，不要去掉下划线;
	 * 
	 * 规则:sys_user_id -> Sys_user_id ;
	 * 
	 * 步骤:
	 * 
	 * 1 不要去掉下划线;
	 * 
	 * 
	 * 2头个字母转换成大写，不要去掉下划线;
	 * 
	 * 
	 */
	public static String findMethodNameUnderline(String columnName) {
		// 先转成小写字母;
		// fieldName = fieldName.toLowerCase();
		int lengthInt = columnName.length();
		StringBuilder resultStringBuilder = new StringBuilder();
		// 头个字母转换成大写
		resultStringBuilder.append(columnName.substring(0, 1).toUpperCase());
		resultStringBuilder.append(columnName.substring(1, lengthInt));
		return resultStringBuilder.toString();
	}
	/**
	 * 首个单词首个字母小写，其它单词头个字母转换成大写，并且去掉下划线;
	 * 
	 * 规则:sys_user_id -> sysUserId
	 * 
	 */
	public static String findFieldName(String fieldName) {
		return findFieldName_v2(fieldName);
	}
	/**
	 * 首个单词首个字母小写，其它单词头个字母转换成大写，并且去掉下划线;
	 * 
	 * 规则:sys_user_id -> sysUserId;
	 * 
	 * 
	 * StringBuilder
	 * 
	 * @param columnName
	 * @return
	 */
	private static String findFieldName_v2(String columnName) {
		StringBuilder target = new StringBuilder();
		String[] strArray = columnName.split("_");
		for (int i = 0; i < strArray.length; i++) {
			int str_l = strArray[i].length();
			if (i == 0) {
				target.append(strArray[i].substring(0, 1).toLowerCase());
				target.append(strArray[i].substring(1, str_l).toLowerCase());
			} else {
				target.append(strArray[i].substring(0, 1).toUpperCase());
				target.append(strArray[i].substring(1, str_l).toLowerCase());
			}
		}
		return target.toString();
	}
	/**
	 * 首个单词首个字母小写，其它单词头个字母转换成大写，并且去掉下划线;
	 * 
	 * 
	 * 
	 * 规则:sys_user_id -> sysUserId;
	 * 
	 * 
	 * String
	 * 
	 * 
	 * @param objName
	 * @return
	 */
	private static String findFieldName_v1(String columnName) {
		String target = "";
		String[] strArray = columnName.split("_");
		for (int i = 0; i < strArray.length; i++) {
			int str_l = strArray[i].length();
			if (i == 0) {
				target = target + strArray[i];
			} else {
				target = target + strArray[i].substring(0, 1).toUpperCase() + strArray[i].substring(1, str_l);
			}
		}
		return target;
	}
	/**
	 * 头个字母转换成小写，不要去掉下划线;
	 * 
	 * 规则:SYS_USER_ID_ -> sYS_USER_ID_ ;
	 * 
	 * 步骤:
	 * 
	 * 1 不要去掉下划线;
	 * 
	 * 
	 * 2头个字母转换成小写，不要去掉下划线;
	 * 
	 */
	public static String findFieldNameUnderline(String columnName) {
		// 先转成小写字母;
		// fieldName = fieldName.toLowerCase();
		int lengthInt = columnName.length();
		StringBuilder resultStringBuilder = new StringBuilder();
		// 头个字母转换成大写
		resultStringBuilder.append(columnName.substring(0, 1).toLowerCase());
		resultStringBuilder.append(columnName.substring(1, lengthInt));
		return resultStringBuilder.toString();
	}
	/**
	 * 
	 * 编码; 将容易引起req漏洞的半角字符直接替换成全角字符;
	 * 
	 * @param str
	 * @return
	 */
	public static String requestEncode(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length() + 16);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
				case '>' :
					sb.append('＞');// 全角大于号
					break;
				case '<' :
					sb.append('＜');// 全角小于号
					break;
				case '\'' :
					sb.append('‘');// 全角单引号;&apos;
					break;
				case '\"' :
					sb.append('“');// 全角双引号
					break;
				case '&' :
					sb.append('＆');// 全角
					break;
				case '\\' :
					sb.append('＼');// 全角斜线
					break;
				case '#' :
					sb.append('＃');// 全角井号
					break;
				case '^' :
					sb.append('＾');// 全角＾号
					break;
				case '*' :
					sb.append('＊');// 全角＊号
					break;
				case '?' :
					sb.append('？');// 全角？号
					break;
				case '`' :
					sb.append('｀');// 全角｀号
					break;
				case ',' :
					sb.append('，');// 全角，号
					break;
				case '.' :
					sb.append('．');// 全角.号
					break;
				default :
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}
	/**
	 * 
	 * 解码; 将容易引起req漏洞的半角字符直接替换成全角字符;
	 * 
	 * 然后再转回来;
	 * 
	 * @param str
	 * @return
	 */
	public static String requestDecode(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length() + 16);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
				case '＞' :
					sb.append('>');// 大于号
					break;
				case '＜' :
					sb.append('<');// 小于号
					break;
				case '‘' :
					sb.append('\'');// 单引号;&apos;
					break;
				case '“' :
					sb.append('\"');// 双引号
					break;
				case '＆' :
					sb.append('&');//
					break;
				case '＼' :
					sb.append('\\');// 斜线
					break;
				case '＃' :
					sb.append('#');// 井号
					break;
				case '＾' :
					sb.append('^');// 全角＾号
					break;
				case '＊' :
					sb.append('*');// 全角＊号
					break;
				case '？' :
					sb.append('?');// 全角？号
					break;
				case '｀' :
					sb.append('`');// 全角｀号
					break;
				case '，' :
					sb.append(',');// 全角，号
					break;
				case '．' :
					sb.append('.');// 全角．号
				default :
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}
	public static String doReplaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	/**
	 * html转义
	 * 
	 * @param source
	 * @return
	 */
	public static String doEncodeHtml(String source) {
		if (source == null) {
			return "";
		}
		String html = "";
		StringBuilder bufferStringBuilder = new StringBuilder();
		for (int i = 0; i < source.length(); i++) {
			char htmlChar = source.charAt(i);
			switch (htmlChar) {
				case '<' :
					bufferStringBuilder.append("&lt;");
					break;
				case '>' :
					bufferStringBuilder.append("&gt;");
					break;
				case '&' :
					bufferStringBuilder.append("&amp;");
					break;
				case '"' :
					bufferStringBuilder.append("&quot;");
					break;
				case 10 :
				case 13 :
					break;
				default :
					bufferStringBuilder.append(htmlChar);
			}
		}
		html = bufferStringBuilder.toString();
		return html;
	}
}
