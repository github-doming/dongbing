package c.a.util.core.copy;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import java.math.BigDecimal;

/**
 * 
 * 
 * 两个对象不相同复制，包括日期转换
 * 
 * 只能浅层复制
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */

public class CopyUtil {
	public static void copyProperties(Object destination, Object origin) {
		CopyConverter typeConverter = new CopyConverter();
		// 注册类型
		ConvertUtils.register(typeConverter, java.util.Date.class);
		ConvertUtils.register(typeConverter, String.class);
		ConvertUtils.register(typeConverter, byte[].class);
		ConvertUtils.register(typeConverter, BigDecimal.class);
		try {
			BeanUtils.copyProperties(destination, origin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
