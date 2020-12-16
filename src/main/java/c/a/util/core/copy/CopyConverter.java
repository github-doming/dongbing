package c.a.util.core.copy;
import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.beanutils.Converter;

import c.a.util.core.collections.CollectionUtil;
import c.a.util.core.date.DateThreadLocal;
import c.a.util.core.string.StringUtil;
/**
 * 复制转换
 * 
 * @author cxy
 * @Email:  使用范围：
 */
public class CopyConverter implements Converter {

	@Override
	public Object convert(Class typeDestination, Object valueOrigin) {

		/**
		 * 数据类型是日期类型;目标bean;
		 */
		if (typeDestination.equals(java.util.Date.class)) {
			if (valueOrigin == null) {
				return null;
			}
			if (valueOrigin instanceof String) {
				if (valueOrigin.equals("")) {
					return null;
				}
			}
			try {
				return DateThreadLocal.findThreadLocal().get().datetime24hEnDateFormat.parse(valueOrigin
						.toString().trim());
			} catch (ParseException e1) {
				try {
					return DateThreadLocal.findThreadLocal().get().dateEnDateFormat.parse(valueOrigin
							.toString().trim());
				} catch (ParseException e2) {
					try {
						return DateThreadLocal.findThreadLocal().get().time24hEnDateFormat.parse(valueOrigin
								.toString().trim());
					} catch (ParseException e3) {
						try {
							return DateThreadLocal.findThreadLocal().get().datetime24hCnDateFormat
									.parse(valueOrigin.toString().trim());
						} catch (ParseException e) {
							System.out.println("valueOrigin="+valueOrigin);
							e.printStackTrace();
						}
					}
				}
			}
		}
		/**
		 * 数据类型是String;目标bean;
		 */
		if (typeDestination.equals(String.class)) {
			if (valueOrigin == null) {
				return "";
			}
			/**
			 * 数据类型是String
			 */
			if (valueOrigin instanceof String) {
				return valueOrigin;
			}
			if (valueOrigin instanceof java.util.Date) {
				return DateThreadLocal.findThreadLocal().get().datetime24hEnDateFormat
						.format((java.util.Date) valueOrigin);
			}
			if (valueOrigin instanceof byte[]) {
				String returnStr = CollectionUtil
						.doByteObject2String(valueOrigin);
				return returnStr;
			}
			if (valueOrigin instanceof BigDecimal) {
				return valueOrigin.toString();
			}
			return String.valueOf(valueOrigin);
		}
		/**
		 * 数据类型是byte[];目标bean;
		 */
		if (typeDestination.equals(byte[].class)) {
			if (valueOrigin instanceof String) {
				return CollectionUtil.doStringObject2byteArray(valueOrigin);
			}
			return new byte[]{0};
		}
		/**
		 * 数据类型是BigDecimal;目标bean;
		 */
		if (typeDestination.equals(BigDecimal.class)) {
			if (StringUtil.isBlank(valueOrigin)) {
				return new BigDecimal(0.0);
			}
			return new BigDecimal(String.valueOf(valueOrigin));
		}
		return null;
	}
}
