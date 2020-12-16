package org.doming.example.annotation.demo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-09-27 18:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TestTool {

	public static void main(String[] args) {
		NoBug testObj = new NoBug();

		Class clazz = testObj.getClass();

		Method[] methods = clazz.getDeclaredMethods();

		//用来记录测试产生的 log 信息
		StringBuilder log = new StringBuilder();

		// 记录异常的次数
		int errorCount = 0;

		for (Method method : methods){
			// 只有被 @Jiecha 标注过的方法才进行测试
			if (method.isAnnotationPresent(Jiecha.class)){
				method.setAccessible(true);
				try {
					method.invoke(testObj,null);
				} catch (IllegalAccessException | InvocationTargetException e) {
					errorCount ++;
					//记录测试过程中，发生的异常的方法
					log.append(method.getName()).append(" has error:\n\r")
							//记录测试过程中，发生的异常类的名称
							.append(e.getCause().getClass().getSimpleName()).append("\n\r");
					//记录测试过程中，发生的异常的具体信息
					log.append(e.getCause().getMessage()).append("\n\r");
				}
			}

		}
		log.append(clazz.getSimpleName()).append(" has  ").append(errorCount).append(" error.");

		// 生成测试报告
		System.out.println(log.toString());
	}
}
