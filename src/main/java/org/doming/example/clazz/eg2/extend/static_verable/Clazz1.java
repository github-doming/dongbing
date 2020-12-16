package org.doming.example.clazz.eg2.extend.static_verable;
/**
 * @Author: Dongming
 * @Date: 2019-12-24 11:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Clazz1 {
	static  class Parent{
		protected static int count = 0;

		public int getCount() {
			return count;
		}
	}

	static class ChildA extends Parent {
		protected static int count = 0;

		void add(){
			count++;
		}
	}

	static class ChildB extends Parent {

		void add(){
			count++;
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
