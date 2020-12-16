package c.a.util.core.asserts;
import c.a.util.core.string.StringUtil;
/**
 * 断言输出
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class AssertUtil {
	/**
	 * 不相等
	 * 
	 * @param expected
	 * @param actual
	 * @param message
	 */
	public static void equalNot(Integer actual, Integer expected, String message) {
		boolean flag = false;
		if (expected == actual) {
			flag = true;
		}
		if (flag) {
		} else {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 不相等
	 * 
	 * @param expected
	 * @param actual
	 * @param message
	 */
	public static void equalNot(Long actual, Long expected, String message) {
		boolean flag = false;
		if (expected == actual) {
			flag = true;
		}
		if (flag) {
		} else {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 不相等
	 * 
	 * @param expected
	 * @param actual
	 * @param message
	 */
	public static void equalNot(String actual, String expected, String message) {
		boolean flag = false;
		if (expected != null && actual != null) {
			if (expected.equals(actual)) {
				flag = true;
			}
		}
		if (flag) {
		} else {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 相等
	 * 
	 * @param expected
	 * @param actual
	 * @param message
	 */
	public static void equal(Integer actual, Integer expected, String message) {
		boolean flag = false;
		if (expected == actual) {
			flag = true;
		}
		if (flag) {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 相等
	 * 
	 * @param expected
	 * @param actual
	 * @param message
	 */
	public static void equal(Long actual, Long expected, String message) {
		boolean flag = false;
		if (expected == actual) {
			flag = true;
		}
		if (flag) {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 
	 * 相等
	 * 
	 * @Description:
	 * @Title equals
	 * @param expected
	 * @param actual
	 * @param message
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void equal(String actual, String expected, String message) {
		boolean flag = false;
		if (expected != null && actual != null) {
			if (expected.equals(actual)) {
				flag = true;
			}
		}
		if (flag) {
			throw new RuntimeException(message);
		}
	}
	
	/**
	 * 大于
	 * 
	 * @Description
	 * @Title greater
	 * @param actual
	 * @param expected
	 * @param message
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void greater(Long actual, Long expected, String message) {
		if (actual > expected) {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 大于
	 * 
	 * @Description
	 * @Title greater
	 * @param actual
	 * @param expected
	 * @param message
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void greater(Integer actual, Integer expected, String message) {
		if (actual > expected) {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 大于或等于
	 * 
	 * @Description
	 * @Title greater
	 * @param actual
	 * @param expected
	 * @param message
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void equalGreater(Long actual, Long expected, String message) {
		if (actual >= expected) {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 大于或等于
	 * 
	 * @Description
	 * @Title greater
	 * @param actual
	 * @param expected
	 * @param message
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void equalGreater(Integer actual, Integer expected, String message) {
		if (actual >= expected) {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 小于
	 * 
	 * @Description
	 * @Title greater
	 * @param actual
	 * @param expected
	 * @param message
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void less (Long actual, Long expected, String message) {
		if (actual <expected) {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 小于
	 * 
	 * @Description
	 * @Title greater
	 * @param actual
	 * @param expected
	 * @param message
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void less (Integer actual, Integer expected, String message) {
		if (actual <expected) {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 小于或等于
	 * 
	 * @Description
	 * @Title greater
	 * @param actual
	 * @param expected
	 * @param message
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void equalLess(Long actual, Long expected, String message) {
		if (actual <=expected) {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 小于或等于
	 * 
	 * @Description
	 * @Title greater
	 * @param actual
	 * @param expected
	 * @param message
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public static void equalLess(Integer actual, Integer expected, String message) {
		if (actual <=expected) {
			throw new RuntimeException(message);
		}
	}
	/**
	 * 不能为空
	 * 
	 * @Description
	 * @Title isNull 
	 * @param object
	 * @param message  参数说明 
	 * @return void    返回类型 
	 * @throws
	 */
	public static void isNull(Object object, String message) {
		if (object == null) {
			throw new NullPointerException(message);
		}
	}
	/**
	 * 文本不能为空
	 * 
	 * @Description
	 * @Title isBlank 
	 * @param text
	 * @param message  参数说明 
	 * @return void    返回类型 
	 * @throws
	 */
	public static void isBlank(String text, String message) {
		if (StringUtil.isBlank(text)) {
			throw new RuntimeException(message);
		}
	}
}
