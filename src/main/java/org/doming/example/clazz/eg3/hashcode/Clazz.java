package org.doming.example.clazz.eg3.hashcode;
/**
 * @Author: Dongming
 * @Date: 2019-12-24 11:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Clazz {

	public static void main(String[] args) {
		Clazz a = new Clazz();
		Clazz b = new Clazz();
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());

	}
}
