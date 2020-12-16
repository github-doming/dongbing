package org.doming.core.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @Description: 数字工具类
 * @Author: Dongming
 * @Date: 2018-12-04 14:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class NumberTool {

	// TODO 比较区
	//比较大小判断区

	/**
	 * 比较大小
	 *
	 * @param obj1 整数1
	 * @param obj2 整数2
	 * @return 整数1-整数2
	 */
	public static int compareInteger(Object obj1, Object obj2) {
		return getInteger(obj1).compareTo(getInteger(obj2));
	}

	//比较大小判断区

	// TODO 获取区
	//获取对象数字值区

	/**
	 * 获取整数
	 *
	 * @param obj 想要装换为整数的对象
	 * @return 整数对象
	 */
	public static Integer getInteger(Object obj) {
		if (!isInteger(obj)) {
			return 0;
		}
		if (obj instanceof Integer) {
			return (Integer) obj;
		} else {
			return Integer.parseInt(obj.toString());
		}
	}

	/**
	 * 获取整数
	 *
	 * @param obj 想要装换为整数的对象
	 * @return 整数对象
	 */
	public static Long getLong(Object obj) {
		if (!isInteger(obj)) {
			return 0L;
		}
		if (obj instanceof Long) {
			return (Long) obj;
		} else {
			return Long.parseLong(obj.toString());
		}
	}

	/**
	 * 获取整数 如果为空返回默认值
	 *
	 * @param integer 想要装换为整数的对象
	 * @param def     默认值
	 * @return 整数
	 */
	public static Integer getInteger(Object integer, Integer def) {
		if (integer == null) {
			return def;
		}
		return getInteger(integer);
	}

	/**
	 * 获取整数 如果为空返回默认值
	 *
	 * @param obj 想要装换为整数的对象
	 * @param def 默认值
	 * @return 整数
	 */
	public static Long getLong(Object obj, Long def) {
		if (obj == null) {
			return def;
		}
		return getLong(obj);
	}


	/**
	 * 获取双精度小数
	 *
	 * @param num 数值
	 * @return 小数
	 */
	public static Double getDouble(Object num) {
		if (num == null || StringTool.isEmpty(num)) {
			return 0d;
		} else if (num instanceof Double) {
			return (Double) num;
		} else if (num instanceof Number) {
			return ((Number) num).doubleValue();
		} else {
			return Double.parseDouble(num.toString());
		}
	}

	/**
	 * 获取双精度小数
	 *
	 * @param num 数值
	 * @param def 默认值
	 * @return 小数
	 */
	public static double getDouble(Object num, double def) {
		if (num == null) {
			return def;
		}
		return getDouble(num);
	}

	/**
	 * 获取map集合中的key键的值，若不存在则为def
	 *
	 * @param map map集合
	 * @param key 查询key键
	 * @param def 默认值
	 * @return 整数
	 */
	public static Integer getInteger(Map<String, Object> map, String key, int def) {
		if (map == null) {
			return def;
		}
		return getInteger(map.getOrDefault(key, def), def);
	}

	/**
	 * 获取map集合中的key键的值，若不存在则为def
	 *
	 * @param map map集合
	 * @param key 查询key键
	 * @param def 默认值
	 * @return 整数
	 */
	public static Long getLong(Map<String, Object> map, String key, Long def) {
		if (map == null) {
			return def;
		}
		return getLong(map.getOrDefault(key, def), def);
	}

	/**
	 * 获取双精度小数
	 * @param map map集合
	 * @param key 查询key键
	 * @param def 默认值
	 * @return 小数
	 */
	public static double getDouble(Map<String, Object> map, String key, double def) {
		if (map == null) {
			return def;
		}
		return getDouble(map.getOrDefault(key, def), def);
	}

	//获取对象数字值区

	// TODO 判断区
	//判断区

	/**
	 * 判断是否为整数
	 *
	 * @param obj 传入的字符串
	 * @return 是整数返回true, 否则返回false
	 */
	public static boolean isInteger(Object obj) {
		if (StringTool.isEmpty(obj)) {
			return false;
		}
		Pattern pattern = compile("^[-+]?[\\d]*$");
		return pattern.matcher(obj.toString()).matches();
	}

	//判断区

	// TODO 计算区
	//计算区

	/**
	 * 将num1对象与num2对象相加
	 *
	 * @param num1 数值1
	 * @param num2 数值2
	 * @return 和
	 */
	public static int addInt(Object num1, Object num2) {
		return getInteger(num1) + getInteger(num2);
	}

	/**
	 * 整数的除法运算
	 *
	 * @param dividend 被除数
	 * @param divisor  除数
	 * @return 商
	 */
	public static double divInt(Object dividend, Object divisor) {
		int num1 = getInteger(dividend);
		int num2 = getInteger(divisor);
		if (num2 == 0) {
			throw new IllegalArgumentException("除数不能为0");
		}
		return 1.0d * num1 / num2;
	}

	//  计算区

	// TODO 转换区
	//转换区

	/**
	 * 将数字乘以1000转换为 int类型
	 *
	 * @param num 待转换数字
	 * @return int类型结果
	 */
	public static int intValueT(Object num) {
		return Double.valueOf((getDouble(num) * 1000)).intValue();
	}

	/**
	 * 将数字乘以1000转换为 long类型
	 *
	 * @param num 待转换数字
	 * @return int类型结果
	 */
	public static long longValueT(Object num) {
		if (StringTool.isEmpty(num)) {
			return 0L;
		}
		return Double.valueOf((getDouble(num) * 1000)).longValue();
	}

	/**
	 * 将数字除以1000转换为 double类型
	 *
	 * @param num 待转换数字
	 * @return double类型结果
	 */
	public static double doubleT(Object num) {
		if (StringTool.isEmpty(num)) {
			return 0d;
		}
		return (getDouble(num) / 1000);
	}

	/**
	 * 转换为整数数组
	 *
	 * @param objs 数组
	 * @return 整数数组
	 */
	public static Integer[] intValue(Object[] objs) {
		if (ContainerTool.isEmpty(objs)) {
			return null;
		}
		int len = objs.length;
		Integer[] ints = new Integer[len];
		for (int i = 0; i < len; i++) {
			ints[i] = getInteger(objs[i]);
		}
		return ints;
	}

	/**
	 * 基本数词表
	 */
	private static final String[] EN_NUM = {"ZERO", "ONE", "TOW", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT",
			"NINE", "TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN",
			"NINETEEN", "TWENTY", "", "", "", "", "", "", "", "", "", "THIRTY", "", "", "", "", "", "", "", "", "",
			"FORTY", "", "", "", "", "", "", "", "", "", "FIFTY", "", "", "", "", "", "", "", "", "", "SIXTY", "", "",
			"", "", "", "", "", "", "", "SEVENTY", "", "", "", "", "", "", "", "", "", "EIGHTY", "", "", "", "", "", "",
			"", "", "", "NINETY"};
	/**
	 * 单位表
	 */
	private static final String[] EN_UNIT = {"hundred", "thousand", "million", "billion", "trillion", "quintillion"};

	/**
	 * 将数字转换为英文
	 *
	 * @param num 数字
	 * @return 英文单位
	 */
	public static String getEn(long num) {
		// long型参数，
		return getEn(String.valueOf(num));
		// 因为long型有极限，所以以字符串参数方法为主
	}

	/**
	 * 将数字转换为英文
	 *
	 * @param num 数字
	 * @return 英文单位
	 */
	public static String getEn(String num) {
		// 数字字符串参数
		// 判断字符串是否为数字
		if (!num.matches("\\d+")) {
			return String.format("%s is not number", num);
		}
		num = num.replaceAll("^[0]*([1-9]*)", "$1");
		// 把字符串前面的0去掉
		if (num.length() == 0) {
			// 如果长度为0，则原串都是0
			return EN_NUM[0];
		}
		// 按3位分割分组
		int count = (num.length() % 3 == 0) ? num.length() / 3 : num.length() / 3 + 1;
		// 判断组单位是否超过
		// 可以根据需求适当追加EN_UNIT
		if (count > EN_UNIT.length) {
			return "too big";
		}
		String[] group = new String[count];
		for (int i = num.length(), j = group.length - 1; i > 0; i -= 3) {
			group[j--] = num.substring(Math.max(i - 3, 0), i);
		}
		// 结果保存
		StringBuilder buf = new StringBuilder();
		// 遍历分割的组
		for (int i = 0; i < count; i++) {
			int v = Integer.parseInt(group[i]);
			// 因为按3位分割，所以这里不会有超过999的数
			if (v >= 100) {
				buf.append(EN_NUM[v / 100]).append("-").append(EN_UNIT[0]).append("-");
				// 获取百位，并得到百位以后的数
				v = v % 100;
				// 如果百位后的数不为0，则追加and
				if (v != 0) {
					buf.append("and-");
				}
			}
			// 前提是v不为0才作解析
			if (v != 0) {
				if (v < 20 || v % 10 == 0) {
					// 如果小于20或10的整数倍，直接取基本数词表的单词
					buf.append(EN_NUM[v]).append(" ");
				} else { // 否则取10位数词，再取个位数词
					buf.append(EN_NUM[v - v % 10]).append("-");
					buf.append(EN_NUM[v % 10]).append("-");
				}
				// 百位以上的组追加相应的单位
				if (i != count - 1) {
					buf.append(EN_UNIT[count - 1 - i]).append("-");
				}
			}
		}
		return buf.toString().trim();
	}

	/**
	 * 基本数词表
	 */
	private static final String[] CN_NUM = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
	/**
	 * 单位表
	 */
	private static final String[] CN_UNIT = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

	public static String getCn(int num) {
		String dst = "";
		int count = 0;
		while (num > 0) {
			dst = (CN_NUM[num % 10] + CN_UNIT[count]) + dst;
			num = num / 10;
			count++;
		}
		return dst.replaceAll("零[千百十]", "零").replaceAll("零+万", "万")
				.replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
				.replaceAll("零+", "零").replaceAll("零$", "");
	}

	/**
	 * 将数字乘以1000转换为 long类型
	 * 为空或为0都返回null
	 *
	 * @param num 待转换数字
	 * @return int类型结果
	 */
	public static Long notEmptyLongValue(Object num) {
		if (StringTool.isEmpty(num) || "0".equals(num)) {
			return null;
		}
		return Double.valueOf((Double.parseDouble(num.toString()) * 1000)).longValue();
	}

	//  转换区

	// TODO 功能区

	/**
	 * 获取大于0 的最小值
	 *
	 * @param array 数组
	 * @return 大于0 的最小值
	 */
	public static int findMin(int[] array) {
		int tmp = Integer.MAX_VALUE;
		for (int num : array) {
			if (num == 0) {
				return 0;
			}
			if (tmp > num) {
				tmp = num;
			}
		}
		return tmp;
	}

	/**
	 * 获取大于0 的最小值
	 *
	 * @param array 数组
	 * @return 大于0 的最小值
	 */
	public static double findMin(double[] array) {
		double tmp = Double.MAX_VALUE;
		for (double num : array) {
			if (num == 0) {
				return 0;
			}
			if (tmp > num) {
				tmp = num;
			}
		}
		return tmp;
	}

	/**
	 * 获取最大值
	 *
	 * @param array 数组
	 * @return 大于0 的最小值
	 */
	public static double findMax(double[] array) {
		double tmp = 0;
		for (double num : array) {
			if (tmp < num) {
				tmp = num;
			}
		}
		return tmp;
	}
	/**
	 * 获取最大值
	 *
	 * @param array 数组
	 * @return 大于0 的最小值
	 */
	public static int findMax(int[] array) {
		int tmp = 0;
		for (int num : array) {
			if (tmp < num) {
				tmp = num;
			}
		}
		return tmp;
	}
	/**
	 * 获取最大值
	 *
	 * @param array 数组
	 * @return 最大值
	 */
	public static int findMax(Object[] array) {
		Integer[] nums = intValue(array);
		int tmp = Integer.MIN_VALUE;
		if (ContainerTool.isEmpty(nums)) {
			return tmp;
		}
		for (int num : nums) {
			if (tmp < num) {
				tmp = num;
			}
		}
		return tmp;

	}

	/**
	 * set数组整体减少某个num
	 *
	 * @param array 数组
	 * @param num   减少的数
	 */
	public static void less(int[] array, int num) {
		for (int i = 0; i < array.length; i++) {
			array[i] -= num;
		}
	}
	/**
	 * 某个数整体被减某个数组
	 *
	 * @param num   被减少的数
	 * @param array 数组
	 */
	public static void less(int num, int[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = num - array[i];
		}
	}

	/**
	 * set数组整体减少某个num
	 *
	 * @param array 数组
	 * @param num   减少的数
	 */
	public static void less(double[] array, double num) {
		for (int i = 0; i < array.length; i++) {
			array[i] -= num;
		}
	}

	/**
	 * 某个数整体被减某个数组
	 *
	 * @param num   被减少的数
	 * @param array 数组
	 */
	public static void less(double num, double[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = num - array[i];
		}
	}
	/**
	 * 某个数整体被减某个数组
	 *
	 * @param num    被减少的数
	 * @param matrix 矩阵
	 * @param col    指定索引列
	 */
	public static void less(int num, int[][] matrix, int col) {
		for (int i = 0; i < matrix.length; i++) {
			matrix[i][col] = num - matrix[i][col];
		}
	}

	/**
	 * 某个数整体被减某个数组
	 *
	 * @param num    被减少的数
	 * @param matrix 矩阵
	 * @param col    指定索引列
	 */
	public static void less(double num, double[][] matrix, int col) {
		for (int i = 0; i < matrix.length; i++) {
			matrix[i][col] = num - matrix[i][col];
		}
	}

	/**
	 * matrix矩阵的index列整体减少某个num
	 *
	 * @param matrix 矩阵
	 * @param col    指定索引列
	 * @param num    减少的数
	 */
	public static void less(int[][] matrix, int col, int num) {
		for (int i = 0; i < matrix.length; i++) {
			matrix[i][col] -= num;
		}
	}

	/**
	 * matrix矩阵的index列整体减少某个num
	 *
	 * @param matrix 矩阵
	 * @param col    指定索引列
	 * @param num    减少的数
	 */
	public static void less(double[][] matrix, int col, double num) {
		for (int i = 0; i < matrix.length; i++) {
			matrix[i][col] -= num;
		}
	}

	/**
	 * 矩阵转置
	 *
	 * @param matrix 矩阵
	 * @return 转置矩阵
	 */
	public static int[][] transpose(int[][] matrix) {
		int x = matrix.length;
		int y = matrix[0].length;
		int[][] transpose = new int[y][x];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				transpose[j][i] = matrix[i][j];
			}
		}
		return transpose;
	}

	/**
	 * 矩阵转置
	 *
	 * @param matrix 矩阵
	 * @return 转置矩阵
	 */
	public static double[][] transpose(double[][] matrix) {
		int x = matrix.length;
		int y = matrix[0].length;
		double[][] transpose = new double[y][x];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				transpose[j][i] = matrix[i][j];
			}
		}
		return transpose;
	}

	/**
	 * 矩阵转置
	 *
	 * @param matrix    矩阵
	 * @param transpose 转置矩阵
	 */
	public static <T extends Number> void transpose(T[][] matrix, T[][] transpose) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				transpose[j][i] = matrix[i][j];
			}
		}
	}

	/**
	 * 获取set集合中非0 索引
	 *
	 * @param array 数组
	 * @return 非0索引
	 */
	public static List<Integer> findNoZeroValIndex(int[] array) {
		List<Integer> list = new ArrayList<>(array.length / 2);
		for (int i = 0; i < array.length; i++) {
			if (array[i] != 0) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * 获取set集合中非0 索引
	 *
	 * @param array 数组
	 * @return 非0索引
	 */
	public static List<Integer> findNoZeroValIndex(double[] array) {
		List<Integer> list = new ArrayList<>(array.length / 2);
		for (int i = 0; i < array.length; i++) {
			if (array[i] != 0) {
				list.add(i);
			}
		}
		return list;
	}
	/**
	 * 获取数组中非零的个数
	 *
	 * @param array 数组
	 * @return 非零的个数
	 */
	public static int findNoZeroValSize(int[] array) {
		int size = 0;
		for (double num : array) {
			if (num != 0) {
				++size;
			}
		}
		return size;
	}

	/**
	 * 获取数组中非零的个数
	 *
	 * @param array 数组
	 * @return 非零的个数
	 */
	public static int findNoZeroValSize(double[] array) {
		int size = 0;
		for (double num : array) {
			if (num != 0) {
				++size;
			}
		}
		return size;
	}

	/**
	 * 获取矩阵中非零的个数
	 *
	 * @param matrix 矩阵
	 * @return 非零的个数
	 */
	public static int findNoZeroValSize(double[][] matrix) {
		int size = 0;
		for (double[] array : matrix) {
			size +=findNoZeroValSize(array);
		}
		return size;
	}

	/**
	 * 矩阵切割
	 *
	 * @param srcMatrix 源矩阵
	 * @param xStart    一维起点
	 * @param xEnd      一维终点
	 * @param yStart    二维起点
	 * @param yEnd      二维终点
	 * @return 切割后的矩阵
	 */
	public static double[][] catMatrix(double[][] srcMatrix, int xStart, int xEnd, int yStart, int yEnd) {
		if (xStart >= xEnd || yStart >= yEnd) {
			return null;
		}
		if (srcMatrix.length < xEnd || srcMatrix[0].length < yEnd) {
			return null;
		}
		double[][] matrix = new double[xEnd - xStart][yEnd - yStart];
		for (int i = xStart; i < xEnd; i++) {
			System.arraycopy(srcMatrix[i], yStart, matrix[i - xStart], 0, yEnd - yStart);
		}
		return matrix;
	}

	//  功能区
}
