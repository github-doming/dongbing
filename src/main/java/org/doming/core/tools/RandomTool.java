package org.doming.core.tools;

import org.doming.develop.key.SnowflakeIdWorker;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Dongming
 * @Description: 随机工具类
 * @ClassName: RandomTool
 * @date 2018年5月25日 下午3:58:30
 * @Email: job.dongming@foxmail.com
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class RandomTool {

	private final static Random rand = new Random();

	private static char[] numLetter = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	private static char[] num = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

	private static char[] letter = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	private static final SimpleDateFormat ID_SDF = new SimpleDateFormat("yyyyMMddHHmmssS");

	//FIXME 基础区
	//基础区

	/**
	 * 获取0-1随机整数
	 *
	 * @return 返回类型 Integer
	 * @Description: 参数说明
	 */
	public static Integer getInt() {
		return rand.nextInt(2);
	}

	/**
	 * 获取随机小数
	 *
	 * @return 0-1之间的
	 */
	public static Double getDouble() {
		return rand.nextDouble();
	}

	/**
	 * 获取长度为32的随机字符数字字符串
	 *
	 * @return 32长字符数字字符串
	 */
	public static String getNumLetter32() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	/**
	 * 获取主键
	 *
	 * @return 主键
	 */
	public static String getId() {
		String str = DateTool.getStr(new Date(), ID_SDF);
		return str + getInt(100, 999);
	}

	//基础区

	//FIXME 范围区
	//范围区

	/**
	 * 获取随机整数
	 *
	 * @param end 最大值
	 * @return 返回类型 Integer
	 * @Description: 参数说明
	 */
	public static Integer getInt(int end) {
		return rand.nextInt(end);
	}

	/**
	 * 获取随机整数
	 *
	 * @param end 最大值
	 * @return 返回类型 Integer
	 * @Description: 参数说明
	 */
	public static Integer getInt(int start, int end) {
		if (end < start) {
			throw new IllegalArgumentException("最大值必须必最小值大");
		}
		return rand.nextInt(end - start) + start;
	}

	/**
	 * 获取随机小数
	 *
	 * @param end 最大值
	 * @return 随机小数
	 */
	public static Double getDouble(int end) {
		return rand.nextInt(end) + getDouble();
	}


	public static String getVerifyCode() {
		return String.format("%4d",getInt(10000));
	}
	//范围区

	//FIXME 长度区
	//长度区

	/**
	 * 随机生成size长度的数字码
	 *
	 * @param size 长度
	 * @return 数字码
	 */
	public static String getNum(Integer size) {
		StringBuilder str = new StringBuilder();
		if (size == null || size == 0) {
			return str.toString();
		}
		for (int i = 0; i < size; i++) {
			str.append(num[getInt(num.length)]);
		}
		return str.toString();
	}

	/**
	 * 随机生成size长度的字符码
	 *
	 * @param size 长度
	 * @return 字母码
	 */
	private static String getLetter(Integer size) {
		StringBuilder str = new StringBuilder();
		if (size == null || size == 0) {
			return str.toString();
		}
		for (int i = 0; i < size; i++) {
			str.append(letter[getInt(letter.length)]);
		}
		return str.toString();
	}

	/**
	 * 随机生成size长度的不全为数字的数字和字符码
	 *
	 * @param size 长度
	 * @return 不全为数字的数字和字符码
	 * @date 2018年7月21日 上午11:04:40
	 * @author Dongming
	 * @version v1.0
	 */
	private static String getNotIntNumLetter(int size) {
		String str = getLetter(size);
		if (StringTool.isInteger(str)) {
			return getNotIntNumLetter(size);
		}
		return str;
	}

	/**
	 * 随机生成size长度的数字和字符码
	 *
	 * @param size 长度
	 *             数字和字符码
	 */
	public static String getNumLetter(Integer size) {
		StringBuilder str = new StringBuilder();
		if (size == null || size == 0) {
			return str.toString();
		}
		for (int i = 0; i < size; i++) {
			str.append(numLetter[getInt(numLetter.length)]);
		}
		return str.toString();
	}

	//长度区

	//FIXME 范围长度区
	//范围长度区

	/**
	 * 获取随机数
	 *
	 * @param start 随机数起点
	 * @param end   随机数终点
	 * @param size  随机数个数
	 * @return 返回类型 Integer[]
	 * @Title: findRandNum
	 * @Description: 参数说明
	 */
	public static int[] getInt(int start, int end, int size) {
		int[] num = new int[size];
		int len = end - start + 1;
		for (int i = 0; i < size; i++) {
			num[i] = start + getInt(len);
		}
		return num;
	}

	//范围长度区

	// FIXME 获取列表区
	// 获取列表区

	/**
	 * 获取长度为32的随机字符数字字符串列表
	 *
	 * @param size 列表个数
	 * @return 32长字符数字字符串列表
	 */
	public static List<String> listNumLetter32(Integer size) {
		List<String> randCharNum32 = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			randCharNum32.add(getNumLetter32());
		}
		return randCharNum32;
	}

	/**
	 * 获取长度为16的随机字符数字字符串列表
	 *
	 * @param size 列表个数
	 * @return 16长字符数字字符串列表
	 */
	public static List<String> listNumLetter16(Integer size) {
		List<String> randCharNum16 = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			randCharNum16.add(getNumLetter32().substring(16));
		}
		return randCharNum16;
	}

	/**
	 * 获取长度为32的随机字符串列表
	 *
	 * @param size 列表个数
	 * @return 32长的随机字符串列表
	 */
	public static List<String> listLetter32(Integer size) {
		List<String> randCharNum32 = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			randCharNum32.add(getLetter(32));
		}
		return randCharNum32;
	}

	/**
	 * 获取长度为32的随机数字串列表
	 *
	 * @param size 列表个数
	 * @return 32长的随机数字串列表
	 */
	public static List<String> listNum32(Integer size) {
		List<String> randCharNum32 = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			randCharNum32.add(getNum(32));
		}
		return randCharNum32;
	}

	//获取列表区

	// FIXME 功能区

	public static Object getOneSelect(Object[] objects) {
		return objects[getInt(objects.length)];
	}

	private static final Long CODE_TIME_START = 1590451500000L;

	/**
	 * 唯一键
	 *
	 * @param module 模块
	 * @return 主键
	 */
	public static String onlyCode(String module) {
		return String.format("%9s%015d", module,getSnowflakeId()).replace(' ', '0');
	}

	private static final SnowflakeIdWorker SNOWFLAKE_ID_WORKER = new SnowflakeIdWorker();

	/**
	 * 获取雪花ID
	 *
	 * @return 雪花ID
	 */
	public static long getSnowflakeId() {
		return SNOWFLAKE_ID_WORKER.nextId();
	}

	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		for(int i = 0; i < 10000000; i++) {
			set.add(onlyCode("TEST"));
		}
		System.out.println(set.size());
	}
	//  功能区
}
