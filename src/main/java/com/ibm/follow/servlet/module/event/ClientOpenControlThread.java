package com.ibm.follow.servlet.module.event;
import com.ibm.follow.servlet.cloud.ibm_event_client_open.service.IbmEventClientOpenService;
import com.ibm.follow.servlet.cloud.ibm_log_event.entity.IbmLogEvent;
import com.ibm.follow.servlet.cloud.ibm_log_event.service.IbmLogEventService;
import com.ibm.follow.servlet.module.tools.EventDriver;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * 读取开启客户端事件 - 开启线程执行该任务
 *
 * @Author: Dongming
 * @Date: 2019-12-24 10:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientOpenControlThread extends ControlEventThread {

	private int runCount = 0;

	@Override public String execute(String ignore) throws Exception {
		try {
			IbmEventClientOpenService clientOpenService = new IbmEventClientOpenService();

			//循环大于1000 - 自动弹出 - 清理数据，等待事件心跳重新启动
			while (isRun && runCount++ < 1000) {
				//获取新的事件
				Map<String, Object> newInfo = clientOpenService.findNewEvent();
				if (ContainerTool.isEmpty(newInfo)) {
					sleep();
					continue;
				}
				//线程池被置空
				ThreadPoolExecutor executor = getExecutor();
				if (executor == null) {
					break;
				}
				//TODO 数据预处理

				//开启-打开客户端子线程
				executor.execute(new ClientOpenThread(newInfo));
			}
		} catch (Exception e) {
			IbmLogEvent logEvent = new IbmLogEvent();
			logEvent.setThreadName(Thread.currentThread().getName() + ":" + EventDriver.Code.OPEN_CLIENT.name());
			logEvent.setEventCode("OpenClient");
			logEvent.setExecuteClassCode(this.hashCode());
			logEvent.setErrorContext(e.getMessage());
			new IbmLogEventService().saveError(logEvent);
			log.error("开启客户端事件错误", e);
		} finally {
			EventDriver.register().removeEvent(EventDriver.Code.OPEN_CLIENT, this);
		}
		return null;
	}
}
