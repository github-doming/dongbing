package org.doming.core.tools;
import org.springframework.util.Assert;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-06-19 10:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AssertTool extends Assert {

	/**
	 * 断言，如果为true，则抛出IllegalArgumentException
	 *
	 * @param expression 布尔表达式
	 * @param message    断言失败时要使用的异常消息
	 */
	public static void isFalse(boolean expression, String message) {
		isTrue(!expression, message);
	}

	/**
	 * 断言，如果为空，则抛出IllegalArgumentException
	 *
	 * @param obj    断言对象
	 * @param message    断言失败时要使用的异常消息
	 */
	public static void notEmpty(Object obj, String message) {
		if (StringTool.isEmpty(obj)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言，如果为空，则抛出IllegalArgumentException
	 *
	 * @param obj    断言对象
	 * @param format 异常信息格式
	 * @param args   异常信息补充
	 */
	public static void notEmpty(Object obj, String format, Object... args) {
		notEmpty(obj,String.format(format, args));
	}

}
