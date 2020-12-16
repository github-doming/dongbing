package org.doming.example.clazz.eg1.extend;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-04-28 09:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Parent {

	public void print(){
		functionA();
	}
	private void functionA() {
		System.out.println(this.getClass().getName());
		System.out.println(super.getClass().getName());
	}
}
