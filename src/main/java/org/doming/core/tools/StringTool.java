package org.doming.core.tools;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author Dongming
 * @Description: 字符串攻击类
 * @date 2018年5月4日 下午3:23:15
 * @Email: job.dongming@foxmail.com
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class StringTool {

	public static final String[] CHINESE_NUMBER_RANK = {"冠亚", "第一名", "第二名", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名",
			"第十名", "第十一名", "第十二名", "第十三名", "第十四名", "第十五名", "第十六名", "第十七名", "第十八名", "第十九名", "第二十名"};

	public static final String[] CHINESE_BALL_RANK = {"总和", "第一球", "第二球", "第三球", "第四球", "第五球", "龙虎和", "前三", "中三", "后三"};
	public static final String[] CHINESE_HAPPY_RANK = {"总和", "第一球", "第二球", "第三球", "第四球", "第五球", "第六球", "第七球", "第八球"};

	public static final String[] NUMBER_BET_CONTENT = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13","14","15","16","17"
			,"18","19","大", "小", "单", "双", "龙", "虎"};

	public static final String[] BALL_BET_CONTENT = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "大", "小", "单", "双", "龙", "虎", "和"};

	public static final String[] HAPPY_BET_CONTENT = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
			"大", "小", "单", "双", "合单", "合双", "尾大", "尾小"};

	private static final String[] PK_TYPE = {"号码", "大小", "单双", "龙虎"};
	private static final String[] TYPE_EN = {"NUMBER", "SIZE", "PARITY", "DRAGON"};

	private static final char[] RADIX36 = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0'};

	public static final List<String> TWO_SIDE_AND_WAY = Arrays.asList("大", "小", "单", "双", "龙", "虎", "正投大小", "反投大小", "正投单双", "反投单双","正投龙虎","反投龙虎");

	public static final String[] HIGH_WAY = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
			"大", "小", "单", "双", "龙", "虎", "和", "合单", "合双", "尾大", "尾小"};
	// TODO 是否为空判断区
	//是否为空判断区

	/**
	 * 判定字符串为空
	 *
	 * @param inStr 输入字符串
	 * @return 返回类型 Boolean
	 */
	public static boolean isEmpty(String inStr) {
		return null == inStr || "".equals(inStr.trim()) || "null".equalsIgnoreCase(inStr.trim()) || "NaN"
				.equalsIgnoreCase(inStr.trim()) || "".equals(inStr.trim().replace("\"", ""));
	}

	/**
	 * 判断输入的字对象参数为空
	 *
	 * @param obj 输入对象参数
	 * @return 返回类型 Boolean
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		String str;
		if (obj instanceof String) {
			str = (String) obj;
		} else {
			str = obj.toString();
		}
		return isEmpty(str);
	}

	/**
	 * 判断输入的字符串参数为空
	 *
	 * @param inObjs 输入字符参数
	 * @return 存在空true    全不空false
	 */
	public static boolean isEmpty(Object... inObjs) {
		if (inObjs == null) {
			return true;
		}
		for (Object inStr : inObjs) {
			if (isEmpty(inStr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判定字符串不为空
	 *
	 * @param inStr 输入字符串
	 * @return 返回类型 Boolean
	 */
	public static boolean notEmpty(String inStr) {
		return !isEmpty(inStr);
	}

	/**
	 * 判断对象字符不为空
	 *
	 * @param obj 输入对象
	 * @return 判断类型
	 */
	public static boolean notEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 判定字符串数组不为空
	 *
	 * @param inStrs 输入字符串数组
	 * @return 返回类型 Boolean
	 */
	public static boolean notEmpty(Object... inStrs) {
		if (inStrs == null) {
			return false;
		}
		for (Object inStr : inStrs) {
			if (isEmpty(inStr)) {
				return false;
			}
		}
		return true;
	}

	//是否为空判断区

	// TODO 判断区
	//判断区

	/**
	 * 判断是否为整数
	 *
	 * @param str 传入的字符串
	 * @return 是整数返回true, 否则返回false
	 * @date 2018年7月21日 上午11:05:19
	 * @author Dongming
	 * @version v1.0
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = compile("^[-+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 是否全部包含字符串
	 * <p> valiStr中是否包含在str中
	 *
	 * @param valiStr 验证字符串
	 * @return 返回类型 boolean
	 */
	public static boolean isContains(String str, String valiStr) {
		return str.contains(valiStr);
	}

	/**
	 * 是否全部包含字符串
	 * <p> valiStr中是否包含在str中
	 *
	 * @param valiStrs 验证字符串数组
	 * @return 返回类型 boolean
	 */
	public static boolean isContains(String str, String... valiStrs) {
		if (valiStrs == null) {
			return false;
		}
		for (String valiStr : valiStrs) {
			if (!isContains(str, valiStr)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否包含字符串
	 * <p> valiStr中是否包含在str中
	 *
	 * @param valiStrs 验证字符串数组
	 * @return 返回类型 boolean
	 */
	public static boolean contains(String str, String... valiStrs) {
		if (valiStrs == null) {
			return false;
		}
		for (String valiStr : valiStrs) {
			if (isContains(str, valiStr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字符串是否包含在字符串数组中
	 * <p> item中是否包含在filterProjects中
	 *
	 * @param array   验证字符串数组
	 * @param valiStr 验证字符串
	 * @return 返回类型 boolean
	 */
	public static boolean contains(String[] array, String valiStr) {
		if (isEmpty(array)) {
			return false;
		}
		for (String str : array) {
			if (str.equals(valiStr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否匹配字符串
	 * <p> valiStr中是否匹配 str的字符串
	 *
	 * @param valiStrs 验证字符串数组
	 * @return 返回类型 boolean
	 */
	public static boolean match(String str, String... valiStrs) {
		if (StringTool.isEmpty(valiStrs)) {
			return false;
		}
		for (String valiStr : valiStrs) {
			if (str.equals(valiStr)) {
				return true;
			}
		}
		return false;
	}

	//判断区

	// TODO 字符串操作区
	//字符串操作区

	/**
	 * 字符串进行数值加一
	 *
	 * @param str 数组字符串
	 * @return 字符串进行数值加一
	 */
	public static String addOne(String str) {
		String[] strs = str.split("[^0-9]");
		String numStr = strs[strs.length - 1];
		if (numStr != null && numStr.length() > 0) {
			int n = numStr.length();
			int num = Integer.parseInt(numStr) + 1;
			String added = String.valueOf(num);
			n = Math.min(n, added.length());
			return str.subSequence(0, str.length() - n) + added;
		} else {
			throw new NumberFormatException();
		}
	}

	/**
	 * 字符串进行数值加一
	 *
	 * @param num 数字对象
	 * @return 字符串进行数值加一
	 */
	public static Integer addOne(Object num) {
		return NumberTool.getInteger(num) + 1;
	}

	/**
	 * 字符串进行数值加num
	 *
	 * @param str 数组字符串
	 * @param num 加的值
	 * @return 结果
	 */
	public static int add(String str, int num) {
		if (isInteger(str)) {
			return Integer.parseInt(str) + num;
		} else {
			throw new NumberFormatException();
		}
	}

	/**
	 * 字符串进行数值减num
	 *
	 * @param str 数组字符串
	 * @param num 加的值
	 * @return 结果
	 */
	public static int minus(String str, int num) {
		return add(str, 0 - num);
	}

	/**
	 * 去除所有空格
	 *
	 * @param str 待处理字符串
	 * @return 处理结果
	 */
	public static String trimAll(String str) {
		return str.replaceAll(" +", "");
	}

	/**
	 * 格式化，去除空格
	 *
	 * @param str 字符串
	 * @return 结果
	 */
	public static String format(String str) {
		Pattern pattern = compile("[\t\r\n]");
		Matcher matcher = pattern.matcher(str);
		str = matcher.replaceAll("");
		str = str.replaceAll(" +", " ");
		return str;
	}

	/**
	 * 定位切割
	 *
	 * @param str   字符串
	 * @param index 位置
	 * @return 字符串数组
	 */
	public static String[] split(String str, int index) {
		String[] strs = new String[2];
		strs[0] = str.substring(0, index);
		strs[1] = str.substring(index);
		return strs;
	}

	/**
	 * 定位切割
	 *
	 * @param str 字符串
	 * @return 第一位，后面
	 */
	public static String[] split(String str) {
		return split(str, 1);
	}

	/**
	 * 切割尾端的字符串
	 *
	 * @param str 字符串
	 * @param len 末尾长度
	 * @return 字符串
	 */
	public static String subLast(String str, int len) {
		if (isEmpty(str) || str.length() < len) {
			return str;
		}
		return str.substring(str.length() - len);
	}

	/**
	 * 截取字符串
	 *
	 * @param src      要截取的字符串
	 * @param startIdx 开始坐标（包括该坐标)
	 * @param endIdx   截止坐标（包括该坐标）
	 * @return 截取的字符串
	 */
	public static String subString(String src, int startIdx, int endIdx) {
		byte[] b = src.getBytes();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = startIdx; i <= endIdx; i++) {
			stringBuilder.append((char) b[i]);
		}
		return stringBuilder.toString().trim();
	}

	/**
	 * 装换为字符串数组
	 *
	 * @param objs 对象数组
	 * @return 字符串数组
	 */
	public static String[] toString(Object[] objs) {
		String[] strs = new String[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (notEmpty(objs[i])) {
				strs[i] = objs[i].toString();
			}
		}
		return strs;
	}

	/**
	 * 将字符串首字母转换为小写
	 *
	 * @param str 将类名称首字母转换为小写
	 * @return 首字母转换为小写
	 */
	public static String toLowerCaseFirstOne(String str) {
		if (Character.isLowerCase(str.charAt(0))) {
			return str;
		} else {
			return Character.toLowerCase(str.charAt(0)) + str.substring(1);
		}
	}
	//字符串操作区

	//TODO 功能区
	//功能区

	/**
	 * 获取类型索引
	 *
	 * @param type 类型
	 * @return 类型索引
	 */
	public static Integer getTypeCn(String type) {
		if (isEmpty(type)) {
			return Integer.MIN_VALUE;
		}
		for (int i = 0; i < PK_TYPE.length; i++) {
			if (PK_TYPE[i].contains(type)) {
				return i;
			}
		}
		return Integer.MAX_VALUE;
	}

	/**
	 * 获取类型索引
	 *
	 * @param typeIndex 类型
	 * @return 类型索引
	 */
	public static String getTypeEn(int typeIndex) {
		if (typeIndex < 0 || typeIndex >= TYPE_EN.length) {
			return null;
		}
		return TYPE_EN[typeIndex];
	}

	/**
	 * 获取类型
	 *
	 * @return 类型
	 */
	public static String[] getTypeEn() {
		return Arrays.copyOf(TYPE_EN, TYPE_EN.length);
	}

	/**
	 * 获取中文排名的数字索引
	 *
	 * @param rank 中文排名
	 * @return 数字索引
	 * @date 2018年7月27日 下午2:38:13
	 * @author Dongming
	 * @version v1.0
	 */
	public static Integer getRankCn(String rank) {
		if (isEmpty(rank)) {
			return Integer.MIN_VALUE;
		}
		for (int i = 0; i < CHINESE_NUMBER_RANK.length; i++) {
			if (rank.equals(CHINESE_NUMBER_RANK[i])) {
				return i;
			}
		}
		return Integer.MAX_VALUE;
	}


	/**
	 * 获取PK游戏类型的数字索引
	 *
	 * @param type PK游戏类型
	 * @return 数字索引
	 * @date 2018年12月25日16:23:37
	 * @author Dongming
	 * @version v1.0
	 */
	public static Integer getPKTypeCn(String type) {
		if (isEmpty(type)) {
			return Integer.MIN_VALUE;
		}
		for (int i = 0; i < PK_TYPE.length; i++) {
			if (type.equals(PK_TYPE[i])) {
				return i;
			}
		}
		return Integer.MAX_VALUE;
	}

	/**
	 * 获取带http的主机地址
	 *
	 * @param url 完全路径
	 * @return 带http的主机地址
	 */
	public static String getHost(String url) {
		String http = "http://";
		String https = "https://";
		if (url.toLowerCase().startsWith(http)) {
			url = url.replace(http, "");
		} else if (url.toLowerCase().startsWith(https)) {
			url = url.replace(https, "");
		}
		int tmp = url.indexOf("/", 1);
		return tmp < 0 ? url : url.substring(0, tmp);
	}

	/**
	 * 获取带http的主机地址
	 *
	 * @param url 完全路径
	 * @return 带http的主机地址
	 */
	public static String getHttpHost(String url) {
		String http = "http://";
		String https = "https://";
		if (url.toLowerCase().startsWith(http) || url.toLowerCase().startsWith(https)) {
			int tmp = url.indexOf("/", 8);
			return tmp < 0 ? url : url.substring(0, tmp);
		} else {
			int tmp = url.indexOf("/", 1);
			return http + (tmp < 0 ? url : url.substring(0, tmp));
		}
	}

	/**
	 * 获取键左边的字符串
	 *
	 * @param str 源字符串
	 * @param key 截取键
	 * @return 键左边的字符串
	 */
	public static String getLeft(String str, String key) {
		return str.substring(0, str.indexOf(key));
	}

	/**
	 * 获取键中间的字符串
	 *
	 * @param str   源字符串
	 * @param left  截取左边键
	 * @param right 截取右边键
	 * @return 键左边的字符串
	 */
	public static String getBetween(String str, String left, String right) {
		if (str == null || left == null || right == null) {
			return null;
		}
		int start = str.indexOf(left);
		if (start != -1) {
			int end = str.indexOf(right, start + left.length());
			if (end != -1) {
				return str.substring(start + left.length(), end);
			}
		}
		return null;
	}


	/**
	 * 获取带http的主机项目地址
	 *
	 * @param url 完全路径
	 * @return 带http的主机项目地址
	 */
	public static String projectHost(String url) {
		String http = "http://";
		String https = "https://";
		if (url.toLowerCase().startsWith(http) || url.toLowerCase().startsWith(https)) {
			return url.substring(0, url.indexOf("/", url.indexOf("/", 8) + 1));
		} else {
			return http + url.substring(0, url.indexOf("/", url.indexOf("/", 2) + 1));
		}
	}

	/**
	 * 将obj对象转化为Str字符串
	 *
	 * @param obj 带转化对象
	 * @return Str字符串
	 */
	public static String getString(Object obj) {
		if (isEmpty(obj)) {
			return null;
		}
		return obj instanceof String ? (String) obj : obj.toString();

	}

	/**
	 * 将obj对象转化为Str字符串
	 *
	 * @param obj 带转化对象
	 * @return Str字符串
	 */
	public static String getString(Object obj, String def) {
		if (obj == null) {
			return def;
		}
		return obj instanceof String ? (String) obj : obj.toString();

	}

	/**
	 * 获取map集合中的key键的值，若不存在则为def
	 *
	 * @param map map集合
	 * @param key 查询key键
	 * @param def 默认值
	 * @return Str字符串
	 */
	public static String getString(Map<String, Object> map, String key, String def) {
		if (map == null) {
			return def;
		}
		return getString(map.getOrDefault(key, def), def);
	}


	/**
	 * unicode码转string码
	 *
	 * @param unicode unicode码
	 * @return string码
	 */
	public static String unicode2String(String unicode) {
		Pattern pattern = compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(unicode);
		char ch;
		while (matcher.find()) {
			String group = matcher.group(2);
			ch = (char) Integer.parseInt(group, 16);
			String group1 = matcher.group(1);
			unicode = unicode.replace(group1, ch + "");
		}
		return unicode;

	}

	/**
	 * string转unicode
	 *
	 * @param string string码
	 * @return unicode码
	 */
	public static String string2Unicode(String string) {
		try {
			StringBuilder unicode = new StringBuilder();
			//直接获取字符串的unicode二进制
			byte[] bytes = string.getBytes("unicode");
			//然后将其byte转换成对应的16进制表示即可
			for (int i = 0; i < bytes.length - 1; i += 2) {
				unicode.append("\\u");
				String str = Integer.toHexString(bytes[i + 1] & 0xff);
				for (int j = str.length(); j < 2; j++) {
					unicode.append("0");
				}
				String str1 = Integer.toHexString(bytes[i] & 0xff);
				unicode.append(str1);
				unicode.append(str);
			}
			return unicode.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * string转unicode
	 *
	 * @param string string码
	 * @return unicode码
	 */
	public static String string2Hax(String string) {
		StringBuilder hax = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			int ch = (int) string.charAt(i);
			String s4 = Integer.toHexString(ch);
			hax.append(s4);
		}
		return hax.toString();
	}




	//功能区

}