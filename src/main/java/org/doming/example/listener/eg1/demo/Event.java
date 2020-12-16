package org.doming.example.listener.eg1.demo;
/**
 * @Description: 事件对象
 * @Author: Dongming
 * @Date: 2019-12-06 11:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Event {
	private Person person;
	public Event(Person person){
		this.person = person;
	}

	public Event() {
	}

	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
}
