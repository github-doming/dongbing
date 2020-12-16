package org.doming.example.design.pattern.observer.eg1.cainiao;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-12-06 14:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HexaObserver extends Observer {
	public HexaObserver(Subject subject) {
		this.subject = subject;
		this.subject.attach(this);
	}

	@Override public void update() {
		System.out.println( "Hex String: "
				+ Integer.toHexString( subject.getState() ).toUpperCase() );
	}
}
