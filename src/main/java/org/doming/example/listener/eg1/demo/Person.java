package org.doming.example.listener.eg1.demo;
/**
 * @Description: 事件源
 * @Author: Dongming
 * @Date: 2019-12-06 11:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Person {

	/**
	 * 1.1首先定义一个私有的、空的监听器对象，用来接收传递进来的事件监听器(相当于一个全局变量)
	 */
	private PersonListener listener;

	/**
	 * 1.2将传递进来的事件监听器付给1.1中的listener
	 * @param personListener  事件监听器
	 */
	public  void registerListener(PersonListener personListener){
		this.listener = personListener;
	}

	/**
	 * 1.3判断listener是否为null，如果不为空，则执行监听器中的方法,否则照常调用
	 */
	public void run(){
		if (listener !=null){
			Event event = new Event(this);
			this.listener.doRun(event);
		}
		System.out.println("跑起来");
	}

	/**
	 * 1.4和1.3一个道理
	 */
	public void eat(){
		if (listener !=null){
			Event event = new Event(this);
			this.listener.doEat(event);
		}
		System.out.println("吃起来");
	}

	public void warmUp() {
		System.out.println("拉升");
		System.out.println("揉腿");
	}
	public void cook() {
		System.out.println("放油");
		System.out.println("放食材");
		System.out.println("放调料");
		System.out.println("出锅");
	}

	public static void main(String[] args) {
		Person person=new Person();
		person.registerListener(new MyPersonListener());
		person.run();
		System.out.println("####################################");
		person.eat();

	}

}
