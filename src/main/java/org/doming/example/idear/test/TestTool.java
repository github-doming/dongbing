package org.doming.example.idear.test;

import java.util.concurrent.Callable;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-01-25 17:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TestTool {

	public static void main(String[] args) {
		TestToolBuilder builder = new TestToolBuilder();
		Object o = builder.functionA(() -> builder.functionB(123));
		System.out.println(o.getClass().getName());
		System.out.println("执行结果：" + o);
	}

	public static class TestToolBuilder<V> {

		public V functionA(final Callable<V> task) {
			System.out.println("开始事件");
			V o = null;
			try {
				o = task.call();
				System.out.println("事件结果：" + o);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("结束事件");
			return o;
		}

		public V functionB(V key) {
			return key;
		}
	}

}
