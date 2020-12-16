package c.a.util.core.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * 随机数工具类
 * 
 * @Description:
 * @ClassName: RandomUtil
 * @date 2018年6月25日 下午6:17:52
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class RandomUtil {

	private static char[] numberCharArray = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	/**
	 * 获取随机数
	 * 
	 * @Title: findRandomIntegerList
	 * @Description:
	 *
	 * 				参数说明
	 * @param start
	 *            随机数起点 5
	 * @param end
	 *            随机数终点 10
	 * @param size
	 *            随机数个数 2
	 * @return 返回类型 List<Integer>
	 */
	public static List<Integer> findRandomIntegerList(int start, int end, int size) {
		Random rand = new Random();
		List<Integer> numberList = new ArrayList<Integer>(size);
		int len = end - start + 1;
		for (int i = 0; i < size; i++) {
			int number = start + rand.nextInt(len);
			numberList.add(number);
		}
		return numberList;
	}
	/**
	 * 
	 * 
	 * @Title: findRandomIntegerArray
	 * @Description:
	 *
	 * 				参数说明
	 * @param start
	 *            随机数起点 5
	 * @param end
	 *            随机数终点 10
	 * @param size
	 *            随机数个数 2
	 * @return 返回类型 Integer[]
	 */
	public static Integer[] findRandomIntegerArray(int start, int end, int size) {
		Random rand = new Random();
		Integer[] numberArray = new Integer[size];
		int len = end - start + 1;
		for (int i = 0; i < size; i++) {
			numberArray[i] = start + rand.nextInt(len);
		}
		return numberArray;
	}
	/**
	 * 获取随机整数
	 * 
	 * @Title: findRandomLong
	 * @Description:
	 *
	 * 				参数说明
	 * @return 返回类型 Long
	 */
	public static Long findRandomLong() {
		Random random = new Random();
		long returnLong = random.nextLong();
		return returnLong;
	}
	/**
	 * 获取随机整数
	 * 
	 * @Title: findRandNum
	 * @Description:
	 *
	 * 				参数说明
	 * @param end
	 *            最大值
	 * @return 返回类型 Integer
	 */
	public static Integer findRandomInteger(int end) {
		end = end - 1;
		Random random = new Random();
		int returnInt = random.nextInt(end) + 1;
		return returnInt;
	}

	/**
	 * 获取随机小数
	 * 
	 * @Title: findRandomDouble
	 * @Description:
	 *
	 * 				参数说明
	 * @param end
	 *            最大值
	 * @return 返回类型 Double
	 */
	public static Double findRandomDouble(int end) {
		Random rand = new Random();
		return rand.nextInt(end) + rand.nextDouble();

	}

	/**
	 * 随机生成size长度的数字和字符码
	 * 
	 * @Title: findRandomChar
	 * @Description:
	 *
	 * 				参数说明
	 * @param size
	 * @return 返回类型 String
	 */
	public static String findRandomChar(Integer size) {
		String str = "";
		if (size == null || size == 0)
			return null;
		for (int i = 0; i < size; i++)
			str += numberCharArray[findRandomInteger(36)];
		return str;
	}

}
