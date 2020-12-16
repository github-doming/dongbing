package com.ibm.follow.servlet.module.event;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.thread.CommEventThread;
import com.ibm.follow.servlet.cloud.ibm_event_config_info.service.IbmEventConfigInfoService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 客户端信息设置
 * @Author: null
 * @Date: 2020-01-16 15:54
 * @Version: v1.0
 */
public class ConfigInfoThread extends CommEventThread {
    private Map<String, Object> eventInfo;
    private IbmEventConfigInfoService configInfoService = new IbmEventConfigInfoService();

    ConfigInfoThread(Map<String, Object> eventInfo) {
        this.eventInfo = eventInfo;
    }

    @Override public String execute(String ignore) throws Exception {
        //事件id
        String eventId = eventInfo.get("EVENT_ID_").toString();

        // 执行事件，获取结果
        JSONObject result = clientInfoSet(eventId);
        if (ContainerTool.isEmpty(result)) {
            result = new JSONObject();
            result.put("code", "error");
            result.put("msg", "客户端迁移失败,请稍后再试");
        }
        configInfoService.updateResult(eventId, result);

        return null;
    }

    private JSONObject clientInfoSet(String eventId) throws Exception {
        JSONObject resultJson = new JSONObject();

        JSONObject content = JSONObject.parseObject(eventInfo.get("EVENT_CONTENT_").toString());
        content.put("EVENT_ID_", eventId);
        content.put("METHOD_", eventInfo.get("EVENT_METHOD_"));

        String clientCode=content.remove("CLIENT_CODE_").toString();

        String result=RabbitMqTool.sendClientConfig(content.toString(), clientCode, "info");
        if (StringTool.isEmpty(result)) {
            return resultJson;
        }
        if (!Boolean.parseBoolean(result)) {
            log.error("客户设置，发送消息失败");
            return resultJson;
        }
        //监听事件结果- 200ms轮训一次-15次《状态没有改为PROCESS,FAIL,SUCCESS》
        Map<String, Object> eventMap = listeningEventResult(eventId);
        if (ContainerTool.isEmpty(eventMap)) {
            log.error("客户设置，事件状态异常，事件id=".concat(eventId));
            return resultJson;
        }
        //事件结果
        JSONObject eventResultJson = JSONObject.parseObject(eventMap.get("EVENT_RESULT_").toString());
        //参数错误
        if (StringTool.contains(eventResultJson.getString("codeSys"), CodeEnum.CODE_500.getCode(),
				CodeEnum.CODE_401.getCode())) {
            log.info("客户端迁移失败=" + eventMap.get("EVENT_RESULT_"));
            resultJson.put("code", "error");
            resultJson.put("msg", "客户端设置参数错误");
            return resultJson;
        }
        //返回处理结果
        resultJson.put("code", eventResultJson.getString("codeSys"));
        resultJson.put("msg", eventResultJson.getString("msg"));
        return resultJson;
    }
    /**
     * 监听事件结果
     *
     * @param eventId 事件id
     * @return 事件结果
     */
    private Map<String, Object> listeningEventResult(String eventId) throws SQLException, InterruptedException {
        Map<String, Object> eventMap = null;
        int pollNum = 15;
        for (int i = 0; i < pollNum; i++) {
            eventMap = configInfoService.findEventResult(eventId);
            //处理状态为send,process时，继续等待
            switch (IbmStateEnum.valueOf(eventMap.get("EVENT_STATE_").toString())) {
                case SEND:
                    Thread.sleep(RandomTool.getInt(1000, 2000));
                    continue;
                case PROCESS:
                    if (pollNum == 15) {
                        pollNum = 30;
                    }
                    Thread.sleep(RandomTool.getInt(500, 1500));
                    continue;
                case FAIL:
                    log.error("开启客户端错误，事件Id=" + eventId + ",结果=" + eventMap.get("EVENT_RESULT_"));
                    return new HashMap<>(1);
                case SUCCESS:
                    break;
                default:
                    log.error("开启客户端，事件状态异常，事件id=".concat(eventId));
                    return new HashMap<>(1);
            }
            break;
        }
        return eventMap;
    }
}
