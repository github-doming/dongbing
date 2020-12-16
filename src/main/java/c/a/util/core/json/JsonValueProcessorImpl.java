package c.a.util.core.json;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * 描述: JSON 时间解析器具
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class JsonValueProcessorImpl implements JsonValueProcessor {
	public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	private DateFormat dateFormat;
	/**
	 * 构造方法
	 * 
	 * @param datePattern
	 *            日期格式
	 */
	public JsonValueProcessorImpl(String datePattern) {
		if (null == datePattern) {
			dateFormat = new SimpleDateFormat(DATE_PATTERN_DEFAULT);
		} else {
			dateFormat = new SimpleDateFormat(datePattern);
		}
	}
	private Object process(Object value) {
		if (value instanceof java.util.Date) {
			String str = dateFormat.format((java.util.Date) value);
			return str;
		}
		if (value instanceof java.sql.Date) {
			String str = dateFormat.format((java.sql.Date) value);
			return str;
		}
		if (value == null) {
			return null;
		} else {
			return value.toString();
		}

	}
	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value);
	}
	@Override
	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		return process(value);
	}
}