package org.doming.example.clazz.eg2.extend.static_verable;
/**
 * 静态变量继承测试
 *
 * @Author: Dongming
 * @Date: 2019-12-24 11:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Clazz2 {
	static abstract class Parent{

		public abstract   int getCount();
	}

	static class ChildA extends Parent{
		protected static int count = 0;

		void add(){
			count++;
		}
		@Override public int getCount() {
			return count;
		}
	}

	static class ChildB extends Parent{
		protected static int count = 0;

		void add(){
			count++;
		}
		@Override public int getCount() {
			return count;
		}
	}

	public static void main(String[] args) {
		ChildA a = new ChildA();
		ChildB b = new ChildB();

		a.add();
		System.out.println(a.getCount());

		b.add();
		System.out.println(b.getCount());

	}


}
