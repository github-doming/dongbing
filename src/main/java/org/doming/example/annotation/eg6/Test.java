package org.doming.example.annotation.eg6;
import org.doming.example.annotation.eg4.Check;
import org.doming.example.annotation.eg4.Perform;
import org.doming.example.annotation.eg4.TestAnnotation;
import org.doming.example.annotation.eg5.Hero;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-09-27 15:51
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@TestAnnotation(id = 123)
@Deprecated
public class Test {

	@Check("hello")
	int a;

	@Perform(name = "world")
	public void testMethod(){}

	public void test1(){
		Hero hero = new Hero();
		hero.say();
		hero.speak();
	}

	public static void main(String[] args) {
		System.out.println("============类注解的获取============");
		boolean hasAnnotation = Test.class.isAnnotationPresent(TestAnnotation.class);
		if (hasAnnotation){
			TestAnnotation testAnnotation = Test.class.getAnnotation(TestAnnotation.class);
			// 获取类的注解
			System.out.println("id="+testAnnotation.id());
			System.out.println("msg="+testAnnotation.msg());
		}else{
			System.out.println("没有TestAnnotation注解");
		}

		try {
			System.out.println("===========字段注解的获取===========");
			Field a = Test.class.getDeclaredField("a");
			// 设置外部可见
			a.setAccessible(true);
			//获取一个成员变量上的注解
			Check check = a.getAnnotation(Check.class);
			if(check != null){
				System.out.println("Check.value="+check.value());
			}

			System.out.println("===========函数注解的获取===========");
			Method testMethod = Test.class.getDeclaredMethod("testMethod");
			if(testMethod != null){
				Annotation[] annotations = testMethod.getAnnotations();
				for (Annotation annotation : annotations){
					System.out.println("方法testMethod的注解："+annotation.toString());
				}
			}



		} catch (NoSuchFieldException | NoSuchMethodException e) {
			e.printStackTrace();
		}

	}
}
