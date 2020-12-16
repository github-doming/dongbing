package org.doming.example.listener.eg2.csdn;
import java.util.EventListener;
/**
 * @Description: 事件监听器接口
 * 	所有事件监听器接口都要继承 EventListener 这个标签接口
 * @Author: Dongming
 * @Date: 2019-12-06 15:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface MonitorListener extends EventListener {

	public void handleEvent(PrintEvent event);

}
