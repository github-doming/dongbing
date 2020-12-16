package org.doming.example.annotation.eg5;
import java.util.Arrays;
import java.util.List;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-09-27 15:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Hero {

	@Deprecated
	public void say(){
		System.out.println("hello,world");
	}

	public void speak(){
		System.out.println("My name is doming");
	}


	@SuppressWarnings("deprecation")
	public void test1(){
		Hero hero = new Hero();
		hero.say();
	}

	/**
	 * Not actually safe!
	 */
	@SafeVarargs
	static void m(List<String>... stringLists) {
		Object[] array = stringLists;
		List<Integer> tmpList = Arrays.asList(42);
		// Semantically invalid, but compiles without warnings
		array[0] = tmpList;
		// Oh no, ClassCastException at runtime!
		String s = stringLists[0].get(0);
	}

}
