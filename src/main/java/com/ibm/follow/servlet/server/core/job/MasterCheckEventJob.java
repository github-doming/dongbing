package com.ibm.follow.servlet.server.core.job;

import com.ibm.follow.servlet.cloud.ibm_config.service.IbmConfigService;
import com.ibm.follow.servlet.cloud.ibm_log_event.entity.IbmLogEvent;
import com.ibm.follow.servlet.cloud.ibm_log_event.service.IbmLogEventService;
import com.ibm.follow.servlet.module.tools.EventDriver;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 主校验事件线程
 * @Author: null
 * @Date: 2020-01-09 17:37
 * @Version: v1.0
 */
public class MasterCheckEventJob extends BaseCommJob {
    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        try {
            IbmConfigService configService = new IbmConfigService();
            Map<Object, Object> eventInfo = configService.mapEventSize();
            EventDriver driver = EventDriver.register();
            for (Map.Entry<Object, Object> entry : eventInfo.entrySet()) {
                String eventCode = entry.getKey().toString().replace("_EVENT", "");
                Integer eventSize = NumberTool.getInteger(entry.getValue());
                checkEvent(driver, eventCode, eventSize);
            }

        } catch (Exception e) {
            IbmLogEvent logEvent = new IbmLogEvent();
            logEvent.setThreadName(Thread.currentThread().getName());
            logEvent.setEventCode("MasterCheck");
            logEvent.setExecuteClassCode(this.hashCode());
            logEvent.setErrorContext(e.getMessage());
            new IbmLogEventService().saveError(logEvent);
            log.error("主事件校验错误", e);
        }
    }

    /**
     * 校验事件运行数量
     *
     * @param driver    事件驱动
     * @param eventCode      事件编码
     * @param eventSize 事件运行总大小
     */
    public void checkEvent(EventDriver driver, String eventCode, Integer eventSize) {
        EventDriver.Code code = driver.valueCodeOf(eventCode);
        int runCount = driver.getEventRunCount(code);
        int tmp = runCount - eventSize;
        //运行中的数量比设置的数量多
        if (tmp > 0) {
            for (int i = 0; i < tmp; i++) {
                driver.removeEvent(code, RandomTool.getInt(runCount - i));
            }
        }
        //运行中的数量比设置的数量少
        else if (tmp < 0) {
            tmp = Math.abs(tmp);
            for (int i = 0; i < tmp; i++) {
                driver.addEvent(code);

            }
        }
        //相同-不执行任何操作
    }
}
