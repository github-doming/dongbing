package c.a.tools.log.custom.core;
public class LogCustom_ay {
	/**
	 * 默认线
	 * 
	 * @return
	 */
	public static String line() {
		return lineAsterisk();
	}
	/**
	 * 
	 * 打印井号线
	 */
	public static String lineHashKey() {
		// 50个井号组成
		String str = "#################################################";
		return str;
	}
	/**
	 * 
	 * 打印星号线
	 */
	public static String lineAsterisk() {
		// 50个星号组成
		String str = "*************************************************";
		return str;
	}
	/**
	 * 
	 * 打印双下横线
	 */
	public static String lineDividingDouble() {
		// 50个短线组成
		String str = "=================================================";
		return str;
	}
	/**
	 * 
	 * 打印分隔线
	 */
	public static String lineDividing() {
		// 50个短线组成
		String str = "-------------------------------------------------";
		return str;
	}
	/**
	 * 获得调用者的方法名
	 * 
	 * @return
	 */
	public static void doPrintMethod() {
		StringBuilder sb = new StringBuilder();
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		sb.append("类class=").append(stacks[1].getClassName())
				.append("; 方法method= ").append(stacks[1].getMethodName())
				.append("; 行数number=").append(stacks[1].getLineNumber());
		System.out.println("[日志]" + sb.toString());
	}
}
