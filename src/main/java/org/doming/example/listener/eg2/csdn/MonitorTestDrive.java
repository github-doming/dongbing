package org.doming.example.listener.eg2.csdn;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-12-06 15:25
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MonitorTestDrive {

	public static void main(String[] args) {
		/*
		 * result:
		 * 通知一个事件源 source: openWindows
		 * doOpen
		 * 这就是事件监听模式
		 * 回调接口类: MonitorListener
		 * 回调函数接口: handleEvent
		 * 通过回调模型，EventSource事件源便可回调具体监听器动作
		 */
		EventSource eventSource = new EventSource();
		eventSource.addListener(event -> {
			event.doEvent();
			if("openWindows".equals(event.getSource())) {
				System.out.println("doOpen");
			}
			if("closeWindows".equals(event.getSource())){
				System.out.println("doClose");
			}
		});

		/*
		 * 传入openWindows事件，通知所有的事件监听器
		 * 对open事件感兴趣的listener将会执行
		 */
		eventSource.notifyListenerEvents(new PrintEvent("openWindows"));


		// 有了了解后，这里还可以做一些变动。对特定的事件提供特定的关注方法和事件触发
		//关注关闭事件，实现回调接口
		EventSource windows = new EventSource();
		windows.addCloseWindowListener(event -> {
			event.doEvent();
			if("closeWindows".equals(event.getSource())){
				System.out.println("doClose");
			}
		});

		//窗口关闭动作,现在是不是类似按钮注册监听器，然后点击触发点击事件，执行监听器中对应事件的动作
		windows.doCloseWindows();


	}
}
