package c.x.all.simple.shell.windows.example.e1;
import c.a.tools.log.custom.common.BaseLog;
/**
 * 
 * 记事本
 * 
 * 
 */
public class Shell_windows_example_e1 {
	public static void main(String args[]) {
		Shell_windows_example_e1.callShell("notepad");
		BaseLog.out("end");
	}
	public static void callShell(String shellString) {
		try {
			Runtime runtime = Runtime.getRuntime();
			System.out.println("内存总共=" + runtime.totalMemory());
			System.out.println("内存空闲=" + runtime.freeMemory());
			System.out.println("内存最大=" + runtime.maxMemory());
			System.out.println("处理器=" + runtime.availableProcessors()); // 处理器数
			Process process = runtime.exec(shellString);
			int exitValue = process.waitFor();
			if (0 != exitValue) {
				System.out.println("call shell failed. error code is :"
						+ exitValue);
			}
			BaseLog.out("exitValue=" + exitValue);
		} catch (Throwable e) {
			System.out.println("call shell failed. " + e);
			e.printStackTrace();
		}
	}
}
