package org.doming.example.listener.eg2.csdn;
import java.util.EventObject;
/**
 * @Description: 事件对象
 * @Author: Dongming
 * @Date: 2019-12-06 15:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PrintEvent extends EventObject {
	/**
	 * Constructs a prototypical Event.
	 *
	 * @param source The object on which the Event initially occurred.
	 * @throws IllegalArgumentException if source is null.
	 */
	public PrintEvent(Object source) {
		super(source);
	}

	public void doEvent(){
		System.out.println("通知一个事件源 source: " + this.getSource());
	}

}
