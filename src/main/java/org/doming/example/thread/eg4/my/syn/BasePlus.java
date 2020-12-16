package org.doming.example.thread.eg4.my.syn;
import java.util.concurrent.Callable;
/**
 * @Description: 基类
 * @Author: Dongming
 * @Date: 2019-07-30 11:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BasePlus {

	/**
	 * 在任务执行的时候加入事物
	 *
	 * @param task  任务
	 * @param clazz 执行对象
	 * @return 任务执行结果
	 */
	public Object runSyn(final Callable task, final Class<?> clazz) {
		synchronized (clazz) {
			Object result = null;
			try {
				System.out.println("begin=" + clazz);
				result = task.call();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("end=" + clazz);
			}
			return result;
		}
	}
}
