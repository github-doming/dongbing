package org.doming.example.listener.eg2.csdn;
import java.util.Vector;
/**
 * @Description: 事件源是是事件对象的入口，包含监听器的注册、撤销、通知
 * @Author: Dongming
 * @Date: 2019-12-06 15:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class EventSource {
	//监听器列表，如果监听事件源的事件，注册监听器可以加入此列表

	private Vector<MonitorListener> listeners = new Vector<>();

	/**
	 * 注册监听器
	 * @param eventListener 监听器
	 */
	public void addListener(MonitorListener eventListener) {
		listeners.add(eventListener);
	}

	/**
	 * 删除监听器
	 * @param eventListener  监听器
	 */
	public void removeListener(MonitorListener eventListener) {
		int i = listeners.indexOf(eventListener);
		if(i >= 0) {
			listeners.remove(eventListener);
		}
	}

	/**
	 * 接受外部事件，通知所有的监听器
	 * @param event 事件
	 */
	public void notifyListenerEvents(PrintEvent event) {
		for(MonitorListener listener : listeners) {
			listener.handleEvent(event);
		}
	}
	public void addCloseWindowListener(MonitorListener eventListener) {
		System.out.println("关注关闭窗口事件");
		listeners.add(eventListener);
	}

	public void doCloseWindows() {
		this.notifyListenerEvents(new PrintEvent("closeWindows"));
	}



}
