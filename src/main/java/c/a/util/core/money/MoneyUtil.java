package c.a.util.core.money;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
public class MoneyUtil {
	public static void main(String[] args) {
		Logger log = LogManager.getLogger(MoneyUtil.class);
		log.trace("s3=" + MoneyUtil.doMoney2cent_v3("0.29"));
		log.trace("s3=" + MoneyUtil.doMoney2cent_v3("0.299"));
		log.trace("s3=" + MoneyUtil.doMoney2cent_v3("0.255"));
		log.trace("s=" + MoneyUtil.doMoney2cent("0.29"));
		log.trace("s=" + MoneyUtil.doMoney2cent("0.299"));
		log.trace("s=" + MoneyUtil.doMoney2cent("0.255"));
		log.trace("end");
	}
	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
	 *
	 * @param amount
	 * @return
	 */
	public static String doMoney2cent(String amount) {
		return doMoney2cent_v5(amount);
	}
	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
	 *
	 * @param amount
	 * @return
	 */
	public static String doMoney2cent_v5(String amount) {
		BigDecimal moneyBigDecimal = new BigDecimal(amount);
		moneyBigDecimal = moneyBigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
		Double d = mul(moneyBigDecimal.doubleValue(), 100);
		BigDecimal b = new BigDecimal(d);
		return String.valueOf(b);
	}
	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
	 *
	 * @param amount
	 * @return
	 */
	public static String doMoney2cent_v3(String amount) {
		// 处理包含, ￥ 或者$的金额
		String currentAmt = amount.replaceAll("\\$|\\￥|\\,", "");
		int index = currentAmt.indexOf(".");
		int length = currentAmt.length();
		Long amtLong = 0l;
		if (index == -1) {
			amtLong = Long.valueOf(currentAmt + "00");
		} else if (length - index >= 3) {
			amtLong = Long.valueOf((currentAmt.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amtLong = Long.valueOf((currentAmt.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amtLong = Long.valueOf((currentAmt.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amtLong.toString();
	}
	/**
	 * 
	 * 元转成分,去掉小数
	 * 
	 * @deprecated
	 * @Description:
	 * @Title: doMoney2cent
	 * @param str
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public static String doMoney2cent_v2(String moneyStr) {
		double moneyDouble = Double.valueOf(moneyStr) * 100;
		BigDecimal moneyBigDecimal = new BigDecimal(moneyDouble);
		int moneyInt = moneyBigDecimal.setScale(2, BigDecimal.ROUND_DOWN).intValue();
		String returnStr = String.valueOf(moneyInt);
		return returnStr;
	}
	/**
	 * 
	 * 元转成分,去掉小数
	 * 
	 * @deprecated
	 * @Description:
	 * @Title: doMoney2cent
	 * @param str
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public static String doMoney2cent_v2(double moneyStr) {
		double moneyDouble = moneyStr * 100;
		BigDecimal moneyBigDecimal = new BigDecimal(moneyDouble);
		int moneyInt = moneyBigDecimal.setScale(2, BigDecimal.ROUND_DOWN).intValue();
		String returnStr = String.valueOf(moneyInt);
		return returnStr;
	}
	/**
	 * 
	 * 元转成分,去掉小数
	 * 
	 * @deprecated
	 * @Description:
	 * @Title: doMoney2cent
	 * @param str
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public static String doMoney2cent_v1(String moneyStr) {
		double moneyDouble = Double.valueOf(moneyStr) * 100;
		DecimalFormat moneyDecimalFormat = new java.text.DecimalFormat();
		moneyDecimalFormat.setGroupingSize(0);
		moneyDecimalFormat.setMaximumFractionDigits(0);
		moneyDecimalFormat.setRoundingMode(RoundingMode.DOWN);
		String returnStr = moneyDecimalFormat.format(moneyDouble);
		return returnStr;
	}
	/**
	 * 错的 元转成分,去掉小数
	 * 
	 * @deprecated
	 * @Description:
	 * @Title: doMoney2cent
	 * @param str
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public static String doMoney2cent_v1(double moneyStr) {
		double moneyDouble = moneyStr * 100;
		DecimalFormat moneyDecimalFormat = new java.text.DecimalFormat();
		moneyDecimalFormat.setGroupingSize(0);
		moneyDecimalFormat.setMaximumFractionDigits(0);
		moneyDecimalFormat.setRoundingMode(RoundingMode.DOWN);
		String returnStr = moneyDecimalFormat.format(moneyDouble);
		return returnStr;
	}
}
