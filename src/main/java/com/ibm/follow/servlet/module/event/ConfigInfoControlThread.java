package com.ibm.follow.servlet.module.event;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.follow.servlet.cloud.ibm_event_config_info.service.IbmEventConfigInfoService;
import com.ibm.follow.servlet.cloud.ibm_log_event.entity.IbmLogEvent;
import com.ibm.follow.servlet.cloud.ibm_log_event.service.IbmLogEventService;
import com.ibm.follow.servlet.module.tools.EventDriver;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * 客户端配置信息
 * @Author: Dongming
 * @Date: 2020-01-10 14:43
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ConfigInfoControlThread extends ControlEventThread {
	private int runCount = 0;

	@Override public String execute(String ignore) throws Exception {
		try {
			IbmEventConfigInfoService configInfoService = new IbmEventConfigInfoService();

			//循环大于1000 - 自动弹出 - 清理数据，等待事件心跳重新启动
			while (isRun && runCount++ < 1000) {
				//获取新的事件
				Map<String, Object> newInfo = configInfoService.findNewEvent();
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
				String method = newInfo.getOrDefault("EVENT_METHOD_","").toString();
				if (IbmMethodEnum.CLIENT_MIGRATE.name().equals(method)){
					//开启-客户端迁移子线程
					executor.execute(new ConfigMigrateThread(newInfo));
				}else{
					//开启-客户端配置子线程
                    //客户端设置可不通过事件的方式进行处理
					executor.execute(new ConfigInfoThread(newInfo));
				}
			}
		} catch (Exception e) {
			IbmLogEvent logEvent = new IbmLogEvent();
			logEvent.setThreadName(Thread.currentThread().getName() + ":" + EventDriver.Code.INFO_CONFIG.name());
			logEvent.setEventCode("InfoConfig");
			logEvent.setExecuteClassCode(this.hashCode());
			logEvent.setErrorContext(e.getMessage());
			new IbmLogEventService().saveError(logEvent);
			log.error("客户端配置事件错误", e);
		} finally {
			EventDriver.register().removeEvent(EventDriver.Code.INFO_CONFIG, this);
		}
		return null;
	}
}
