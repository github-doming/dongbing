package com.ibm.follow.servlet.module.event;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.thread.CommEventThread;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户设置任务
 *
 * @Author: Dongming
 * @Date: 2019-12-24 16:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ConfigSetThread extends CommEventThread {
    private Map<String, Object> eventInfo;
    private IbmEventConfigSetService configSetService = new IbmEventConfigSetService();

    public ConfigSetThread(Map<String, Object> eventInfo) {
        this.eventInfo = eventInfo;
    }

    @Override
    public String execute(String ignore) throws Exception {
        //事件id
        String eventId = eventInfo.get("EVENT_ID_").toString();

        //执行事件，获取结果
        JSONObject result = configSet(eventId);
        if (ContainerTool.isEmpty(result)) {
            result = new JSONObject();
            result.put("code", "error");
            result.put("msg", "客户设置失败,请稍后再试");
        }
        //写入结果
        configSetService.updateResult(eventId, result);

        return null;
    }

    private JSONObject configSet(String eventId) {
        JSONObject resultJson = new JSONObject();
        try {
            //发送客户设置信息
            String result = sendConfigSetInfo(eventId);
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
                log.info("客户设置失败=" + eventMap.get("EVENT_RESULT_"));
                resultJson.put("code", "error");
                resultJson.put("msg", "客户设置参数错误");
                return resultJson;
            }
            //返回处理结果
            resultJson.put("code", eventResultJson.getString("codeSys"));
            resultJson.put("msg", eventResultJson.getString("msg"));
        } catch (Exception e) {
            log.error("客户设置失败", e);
        }
        return resultJson;
    }

    /**
     * 监听事件结果
     *
     * @param eventId 事件id
     * @return 事件结果
     */
    private Map<String, Object> listeningEventResult(String eventId) throws Exception {
        Map<String, Object> eventMap = null;
        int pollNum = 15;
        for (int i = 0; i < pollNum; i++) {
            eventMap = configSetService.findEventResult(eventId);
            if (ContainerTool.isEmpty(eventMap)) {
                log.error("客户设置，发送消息异常，事件id=".concat(eventId));
                return new HashMap<>(1);
            }
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
                    log.error("客户设置错误，事件Id=" + eventId + ",结果=" + eventMap.get("EVENT_RESULT_"));
                    return new HashMap<>(1);
                case SUCCESS:
                    break;
                default:
                    log.error("客户设置，事件状态异常，事件id=".concat(eventId));
                    return new HashMap<>(1);
            }
            break;
        }
        return eventMap;

    }

    private String sendConfigSetInfo(String eventId) throws Exception {
        IbmTypeEnum customerType=IbmTypeEnum.valueOf(eventInfo.get("CUSTOMER_TYPE_").toString());
        //事件内容
        JSONObject content = JSONObject.parseObject(eventInfo.get("EVENT_CONTENT_").toString());
        content.put("EVENT_ID_", eventId);
        switch (customerType) {
            case MEMBER:
                return sendMemberInfo(content);
            case AGENT:
                return sendAgentInfo(content);
            default:
                return IbmTypeEnum.FALSE.name();
        }
    }

    /**
     * 发送盘口代理设置消息
     *
     * @param content 事件内容
     * @return 发送结果
     */
    private String sendAgentInfo(JSONObject content) throws Exception {
        IbmClientHaService clientHaService = new IbmClientHaService();
        String handicapAgentId = content.remove("HANDICAP_AGENT_ID_").toString();
        Map<String, Object> existInfo = clientHaService.findExistHaId(handicapAgentId);
        if (ContainerTool.isEmpty(existInfo)) {
            log.info("发送盘口代理设置消息失败，不存在盘口代理id：".concat(handicapAgentId));
            return IbmTypeEnum.FALSE.name();
        }
        String clientCode = existInfo.get("CLIENT_CODE_").toString();
        content.put("EXIST_HA_ID_", existInfo.get("EXIST_HA_ID_"));
        return RabbitMqTool.sendClientConfig(content.toString(), clientCode, "set");
    }

    /**
     * 发送盘口会员设置消息
     *
     * @param content 事件内容
     * @return 发送结果
     */
    private String sendMemberInfo(JSONObject content) throws Exception {
        IbmClientHmService clientHmService = new IbmClientHmService();
        String handicapMemberId = content.remove("HANDICAP_MEMBER_ID_").toString();
        Map<String, Object> existInfo = clientHmService.findExistHmId(handicapMemberId);
        if (ContainerTool.isEmpty(existInfo)) {
            log.info("发送盘口会员设置消息失败，不存在盘口会员id：".concat(handicapMemberId));
            return IbmTypeEnum.FALSE.name();
        }
        String clientCode = existInfo.get("CLIENT_CODE_").toString();
        content.put("EXIST_HM_ID_", existInfo.get("EXIST_HM_ID_"));
        return RabbitMqTool.sendClientConfig(content.toString(), clientCode, "set");
    }
}
