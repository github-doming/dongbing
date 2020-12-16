package c.a.util.core.log;
public class LogAy {
	public static String out(Throwable t, String str) {
		StringBuilder stringBuffer = new StringBuilder();
		String message = t.getMessage();
		String cause = t.toString();
		StackTraceElement[] stacks = t.getStackTrace();
		stringBuffer.append(str + "错误\r\n");
		stringBuffer.append("原因如下:\r\n");
		stringBuffer.append("message=");
		stringBuffer.append(message);
		stringBuffer.append(";\r\n");
		stringBuffer.append("cause=");
		stringBuffer.append(cause);
		stringBuffer.append(";\r\n");
		stringBuffer.append("file=");
		stringBuffer.append(stacks[0].getFileName());
		stringBuffer.append(";\r\n");
		stringBuffer.append("class=");
		stringBuffer.append(stacks[0].getClassName());
		stringBuffer.append(";\r\n");
		stringBuffer.append("line=");
		stringBuffer.append(stacks[0].getLineNumber());
		stringBuffer.append(";\r\n");
		stringBuffer.append("thread=");
		stringBuffer.append(java.lang.Thread.currentThread().getName());
		stringBuffer.append(";\r\n");
		return stringBuffer.toString();
	}
}
