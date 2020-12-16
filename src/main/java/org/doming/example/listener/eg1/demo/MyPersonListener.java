package org.doming.example.listener.eg1.demo;
/**
 * @Description: 实现监听器接口中的方法
 * @Author: Dongming
 * @Date: 2019-12-06 11:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MyPersonListener implements PersonListener {

	@Override public void doRun(Event event) {
		Person person = event.getPerson();
		System.out.println("开始热身");
		person.warmUp();
	}
	@Override public void doEat(Event event) {
		Person person = event.getPerson();
		System.out.println("开始做饭");
		person.cook();
	}
}
