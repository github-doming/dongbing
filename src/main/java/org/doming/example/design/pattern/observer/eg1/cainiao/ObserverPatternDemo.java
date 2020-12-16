package org.doming.example.design.pattern.observer.eg1.cainiao;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-12-06 14:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ObserverPatternDemo {

	public static void main(String[] args) {
		Subject subject = new Subject();


		new HexaObserver(subject);
		new OctalObserver(subject);
		new BinaryObserver(subject);

		System.out.println("First state change: 15");
		subject.setState(15);

		System.out.println("#######################");
		System.out.println("Second state change: 10");
		subject.setState(10);

	}
}
