package com.ibm.follow.servlet.module.event;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.thread.CommEventThread;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_client_close.service.IbmEventClientCloseService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 关闭户端任务
 *
 * @Author: Dongming
 * @Date: 2019-12-24 16:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientCloseThread extends CommEventThread {
    private Map<String, Object> eventInfo;
    private IbmClientHaService clientHaService = new IbmClientHaService();

    private IbmClientHmService clientHmService = new IbmClientHmService();

    private IbmEventClientCloseService clientCloseService = new IbmEventClientCloseService();

    private IbmTypeEnum customerType;

    public ClientCloseThread(Map<String, Object> eventInfo) {
        this.eventInfo = eventInfo;
    }

    @Override
    public String execute(String ignore) throws Exception {
        //事件id
        String eventId = eventInfo.get("EVENT_ID_").toString();

        // 执行事件，获取结果
        JSONObject result = closeClient(eventId);
        if (ContainerTool.isEmpty(result)) {
            result = new JSONObject();
            result.put("code", "error");
            result.put("msg", "关闭客户端失败,请稍后再试");
        }
        clientCloseService.updateResult(eventId, result);

        return null;
    }

    private JSONObject closeClient(String eventId) throws Exception {
        JSONObject resultJson = new JSONObject();
        //事件内容
        JSONObject content = JSONObject.parseObject(eventInfo.get("EVENT_CONTENT_").toString());
        //发送关闭客户端消息
        String result = sendCloseClientInfo(content, eventId);
        if (!Boolean.parseBoolean(result)) {
            log.error("关闭客户端，发送消息失败");
            return resultJson;
        }
        //监听事件结果- 200ms轮训一次-15次《状态没有改为PROCESS,FAIL,SUCCESS》
        Map<String, Object> eventMap = listeningEventResult(eventId);
        if (ContainerTool.isEmpty(eventMap)) {
            log.error("关闭客户端，事件状态异常，事件id=".concat(eventId));
            return resultJson;
        }
        //事件结果
        JSONObject eventResultJson = JSONObject.parseObject(eventMap.get("EVENT_RESULT_").toString());
        if (StringTool.contains(eventResultJson.getString("codeSys"), CodeEnum.CODE_500.getCode(),
				CodeEnum.CODE_401.getCode())) {
            log.info("关闭客户端失败=" + eventMap.get("EVENT_RESULT_"));
            resultJson.put("code", "error");
            resultJson.put("msg", "关闭客户端参数错误");
            return resultJson;
        }
        try {
            //开启事务
            CurrentTransaction.beginTransaction();
            //客户机信息
            JSONObject data = eventResultJson.getJSONObject("data");
            //清除客户信息,返回状态信息
            if (!clearCustomerInfo(data, content)) {
                resultJson.put("code", "error");
                resultJson.put("msg", "关闭客户端失败");
            } else {
                resultJson.put("code", eventResultJson.getString("codeSys"));
                resultJson.put("msg", eventResultJson.getString("msg"));
            }
        } catch (Exception e) {
            CurrentTransaction.rollTransaction();
            log.info("关闭客户端失败", e);
        } finally {
            CurrentTransaction.endTransaction();
        }
        return resultJson;
    }

    /**
     * 清除客户信息
     *
     * @param capacityInfo 容量信息
     * @param content      消息内容
     * @return 客户信息
     */
    private boolean clearCustomerInfo(JSONObject capacityInfo, JSONObject content) throws Exception {
        Map<String, Object> existInfo;
        switch (customerType) {
            case MEMBER:
                //修改已存在盘口会员信息
                existInfo = clientHmService.findByHmId(content.getString("HANDICAP_MEMBER_ID_"));
                clientHmService.updateByHmId(content.getString("HANDICAP_MEMBER_ID_"), new Date(), "盘口会员登出");
                new IbmHandicapMemberService().updateOperating(content.getString("HANDICAP_MEMBER_ID_"));
                break;
            case AGENT:
                //修改已存在盘口代理信息
                existInfo = clientHaService.findByHaId(content.getString("HANDICAP_AGENT_ID_"));
                clientHaService.updateByHaId(content.getString("HANDICAP_AGENT_ID_"), new Date(), "盘口代理登出");
                new IbmHandicapAgentService().updateOperating(content.getString("HANDICAP_AGENT_ID_"));
                break;
            default:
                log.info("关闭客户端失败，错误的客户类型：".concat(customerType.name()));
                return false;
        }
        if (ContainerTool.isEmpty(existInfo)) {
            log.info("关闭客户端，已存在信息为空，客户类型：".concat(customerType.name()));
            return false;
        }
        //更新客户机信息
        EventThreadDefine.updateCapacity(capacityInfo, existInfo.get("CLIENT_ID_").toString(),
                existInfo.get("CLIENT_CODE_").toString(), existInfo.get("HANDICAP_CODE_").toString());
        return true;
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
            eventMap = clientCloseService.findEventResult(eventId);
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
                    log.error("关闭客户端错误，事件Id=" + eventId + ",结果=" + eventMap.get("EVENT_RESULT_"));
                    return new HashMap<>(1);
                case SUCCESS:
                    break;
                default:
                    log.error("关闭客户端，事件状态异常，事件id=".concat(eventId));
                    return new HashMap<>(1);
            }
            break;
        }
        return eventMap;
    }

    /**
     * 发送关闭客户端消息
     *
     * @param content      事件内容
     * @param eventId      事件id
     * @return
     */
    private String sendCloseClientInfo(JSONObject content, String eventId) throws Exception {
        customerType=IbmTypeEnum.valueOf(eventInfo.get("CUSTOMER_TYPE_").toString());

        JSONObject message = new JSONObject();
        message.put("EVENT_ID_", eventId);
        message.put("CUSTOMER_TYPE_", customerType.name());
        message.put("METHOD_", IbmMethodEnum.CLOSE.name());
        switch (customerType) {
            case MEMBER:
                return sendMemberInfo(content, message);
            case AGENT:
                return sendAgentInfo(content, message);
            default:
                log.info("关闭客户端失败，错误的客户类型：".concat(customerType.name()));
                return IbmTypeEnum.FALSE.name();
        }
    }

    /**
     * 发送盘口代理信息
     *
     * @param content 事件内容
     * @param message 消息内容
     * @return 发送结果
     */
    private String sendAgentInfo(JSONObject content, JSONObject message) throws Exception {
        String existHaId;
        String clientCode;
        if(content.containsKey("EXIST_HA_ID_")&&content.containsKey("CLIENT_CODE_")){
            clientCode=content.getString("CLIENT_CODE_");
            message.put("EXIST_HA_ID_", content.getString("EXIST_HA_ID_"));
            return RabbitMqTool.sendAgentInfo(message.toString(), clientCode, "manage");
        }
        if(!content.containsKey("HANDICAP_AGENT_ID_")){
            if (!content.containsKey("EXIST_HA_ID_")) {
                log.error("关闭盘口代理客户端失败，错误的消息内容：".concat(content.toString()));
                return IbmTypeEnum.FALSE.name();
            }
            existHaId= content.getString("EXIST_HA_ID_");
            String handicapAgentId=clientHaService.findHaId(existHaId);
            if (StringTool.isEmpty(handicapAgentId)) {
                log.info("获取已存在盘口代理信息失败，错误的消息内容：".concat(content.toString()));
                return IbmTypeEnum.FALSE.name();
            }
            content.put("HANDICAP_AGENT_ID_", handicapAgentId);
            clientCode = content.remove("CLIENT_CODE_").toString();
        }else{
            String handicapAgentId = content.getString("HANDICAP_AGENT_ID_");
            Map<String, Object> existInfo = clientHaService.findExistHaId(handicapAgentId);
            if (ContainerTool.isEmpty(existInfo)) {
                log.info("获取已存在盘口代理信息失败，不存在盘口代理：".concat(handicapAgentId));
                return IbmTypeEnum.FALSE.name();
            }
            existHaId=existInfo.get("EXIST_HA_ID_").toString();
            clientCode = existInfo.remove("CLIENT_CODE_").toString();
        }
        message.put("EXIST_HA_ID_", existHaId);
        return RabbitMqTool.sendAgentInfo(message.toString(), clientCode, "manage");
    }

    /**
     * 发送盘口会员消息
     *
     * @param content 事件内容
     * @param message 消息内容
     * @return 发送结果
     */
    private String sendMemberInfo(JSONObject content, JSONObject message) throws Exception {
        String existHmId;
        String clientCode;
        if(content.containsKey("EXIST_HM_ID_")&&content.containsKey("CLIENT_CODE_")){
            clientCode=content.getString("CLIENT_CODE_");
            message.put("EXIST_HM_ID_", content.getString("EXIST_HM_ID_"));
            return RabbitMqTool.sendMemberInfo(message.toString(), clientCode, "manage");
        }
        if (!content.containsKey("HANDICAP_MEMBER_ID_")) {
            if (!content.containsKey("EXIST_HM_ID_")) {
                log.error("关闭盘口会员客户端失败，错误的消息内容：".concat(content.toString()));
                return IbmTypeEnum.FALSE.name();
            }
            existHmId = content.getString("EXIST_HM_ID_");
            String handicapMemberId = clientHmService.findHmId(existHmId);
            if (StringTool.isEmpty(handicapMemberId)) {
                log.info("获取已存在盘口会员信息失败，错误的消息内容：".concat(content.toString()));
                return IbmTypeEnum.FALSE.name();
            }
            content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
            clientCode = content.remove("CLIENT_CODE_").toString();
        } else {
            String handicapMemberId = content.getString("HANDICAP_MEMBER_ID_");
            Map<String, Object> existInfo = clientHmService.findExistHmId(handicapMemberId);
            if (ContainerTool.isEmpty(existInfo)) {
                log.info("获取已存在盘口会员信息失败，不存在盘口会员：".concat(handicapMemberId));
                return IbmTypeEnum.FALSE.name();
            }
            existHmId = existInfo.get("EXIST_HM_ID_").toString();
            clientCode = existInfo.remove("CLIENT_CODE_").toString();
        }
        message.put("EXIST_HM_ID_", existHmId);
        return RabbitMqTool.sendMemberInfo(message.toString(), clientCode, "manage");
    }
}
