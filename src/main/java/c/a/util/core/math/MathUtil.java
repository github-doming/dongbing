package c.a.util.core.math;

import java.util.Formatter;

public class MathUtil {
	/**
	 * 格式化浮点数(float 和 double)，保留两位小数
	 * 
	 * @param obj
	 * @return
	 */
	public static String doFormatNumber(Object obj) {
		String result = "";
		if (obj.getClass().getSimpleName().equals("Float")) {
			result = new Formatter().format("%.2f", (float) obj).toString();
		} else if (obj.getClass().getSimpleName().equals("Double")) {
			result = new Formatter().format("%.2f", (double) obj).toString();
		}
		return result;
	}
}
