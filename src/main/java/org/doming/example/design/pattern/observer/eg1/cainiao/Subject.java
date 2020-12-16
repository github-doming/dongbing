package org.doming.example.design.pattern.observer.eg1.cainiao;
import java.util.ArrayList;
import java.util.List;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-12-06 14:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Subject {
	private List<Observer> observers = new ArrayList<>();

	private int state;

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
		notifyAllObservers();
	}

	public void attach(Observer observer){
		observers.add(observer);
	}
	private void notifyAllObservers() {
		for (Observer observer:observers){
			observer.update();
		}
	}
}
