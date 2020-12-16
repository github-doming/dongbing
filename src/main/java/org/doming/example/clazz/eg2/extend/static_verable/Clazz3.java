package org.doming.example.clazz.eg2.extend.static_verable;
/**
 * @Author: Dongming
 * @Date: 2019-12-24 11:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Clazz3 {
	static class Parent {
		protected static int count = 0;

		public int getCount() {
			return count;
		}
	}

	static class ChildA extends Parent {
		void add() {
			count++;
		}
	}

	static class ChildB extends Parent {

		void add() {
			count++;
		}
	}

	public static void main(String[] args) throws InterruptedException {

		ChildA a = new ChildA();
		ChildB b = new ChildB();
		new Thread(() -> a.add()).start();
		new Thread(() -> b.add()).start();

		Thread.sleep(1000);
		System.out.println(a.getCount());

		System.out.println(b.getCount());

	}
}
