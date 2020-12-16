package com.ibm.follow.servlet.module.event.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.controller.strategy.FindClientByPercent;
import com.ibm.follow.servlet.cloud.core.controller.strategy.FindClientController;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_client_open.service.IbmEventClientOpenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 开启客户端控制器
 * @Author: null
 * @Date: 2020-01-10 11:17
 * @Version: v1.0
 */
public class OpenClientController  {
    protected static final Logger log = LogManager.getLogger(OpenClientController.class);
    private IbmEventClientOpenService clientOpenService=new IbmEventClientOpenService();

    public JSONObject execute(String eventId) throws Exception {
        Map<String,Object> eventInfo=clientOpenService.findById(eventId);
        if(ContainerTool.isEmpty(eventInfo)){
            log.error("开启客户端失败，事件id="+eventId);
            return null;
        }
        // 执行事件，获取结果
        JSONObject result = openClient(eventId,eventInfo);
        if (ContainerTool.isEmpty(result)) {
            result = new JSONObject();
            result.put("code", "error");
            result.put("msg", "开启客户端失败,请稍后再试");
        }
        clientOpenService.updateResult(eventId, result);

        return result;
    }

    private JSONObject openClient(String eventId,Map<String,Object> eventInfo) throws Exception {
        JSONObject resultJson = new JSONObject();
        IbmTypeEnum customerType = IbmTypeEnum.valueCustomerTypeOf(eventInfo.get("CUSTOMER_TYPE_").toString());
        if (customerType == null) {
            log.info("开启客户端失败，错误的客户类型" + eventInfo.get("CUSTOMER_TYPE_"));
            return resultJson;
        }
        JSONObject content = JSONObject.parseObject(eventInfo.get("EVENT_CONTENT_").toString());
        String handicapCode = content.getString("HANDICAP_CODE_");
        String clientCode, clientId;
        //存在指定客户端编码
        if (StringTool.notEmpty(eventInfo.get("CLIENT_CODE_"))) {
            clientCode = eventInfo.get("CLIENT_CODE_").toString();
            clientId = new IbmClientService().findId(clientCode);
        } else {
            // 获取可用客户机
            Map<String, Object> clientInfo = new FindClientController().strategy(new FindClientByPercent())
                    .findUsableClient(handicapCode, customerType);
            if (ContainerTool.isEmpty(clientInfo)) {
                resultJson.put("code", "error");
                resultJson.put("msg", "开启客户端失败，客户机容量已满,请联系客服人员");
                return resultJson;
            }
            clientCode = clientInfo.get("CLIENT_CODE_").toString();
            clientId = clientInfo.get("CLIENT_ID_").toString();
        }
        //mq发送消息
        String result = sendOpenClientInfo(customerType, content, clientCode, eventId);
        if (!Boolean.parseBoolean(result)) {
            log.error("开启客户端，发送消息失败");
            return resultJson;
        }
        //监听事件结果 - 200ms轮训一次-15次《状态没有改为PROCESS,FAIL,SUCCESS》
        Map<String, Object> eventMap = listeningEventResult(eventId);
        if (ContainerTool.isEmpty(eventMap)) {
            log.error("开启客户端，事件状态异常，事件id=".concat(eventId));
            return resultJson;
        }
        //事件结果
        JSONObject eventResultJson = JSONObject.parseObject(eventMap.get("EVENT_RESULT_").toString());
        if (StringTool.contains(eventResultJson.getString("codeSys"), CodeEnum.CODE_500.getCode(),
				CodeEnum.CODE_401.getCode())) {
            log.info("开启客户端失败=" + eventMap.get("EVENT_RESULT_"));
            resultJson.put("code", "error");
            resultJson.put("msg", "开启客户端参数错误");
            return resultJson;
        }
        try {
            JSONObject data = eventResultJson.getJSONObject("data");
            //更新客户机信息
            EventThreadDefine.updateCapacity(data.getJSONObject("CAPACITY_"), clientId, clientCode, handicapCode);
            //403为容量已满，重新开启客户端
            if (StringTool.contains(eventResultJson.getString("codeSys"), CodeEnum.CODE_403.getCode())) {
                log.error("开启客户端失败，客户端=" + clientId + "容量已满");
                resultJson.put("code", "error");
                resultJson.put("msg", "开启客户端失败，客户端容量已满");
                return resultJson;
            }
            if (StringTool.contains(eventResultJson.getString("codeSys"), CodeEnum.CODE_404.getCode(),
					CodeEnum.CODE_403.getCode())) {
                log.info("开启客户端失败=" + eventMap.get("EVENT_RESULT_"));
                resultJson.put("code", eventResultJson.getString("code"));
                resultJson.put("msg", eventResultJson.getString("msg"));
                return resultJson;
            }
            if (eventResultJson.getBoolean("success")) {
                // 绑定客户端，添加已存在用户信息
                saveExistInfo(customerType, content, data, clientId, clientCode);
            }
            resultJson.put("code", eventResultJson.getString("codeSys"));
            resultJson.put("msg", eventResultJson.getString("msg"));
        } catch (Exception e) {
            log.info("开启客户端失败", e);
        }
        return resultJson;
    }
    /**
     * 保存已存在用户信息
     *
     * @param customerType 客户类型
     * @param content      消息内容
     * @param data         结果data
     * @param clientId     客户端id
     * @param clientCode   客户端编码
     */
    private void saveExistInfo(IbmTypeEnum customerType, JSONObject content,
                               JSONObject data, String clientId, String clientCode) throws Exception {
        switch (customerType) {
            case MEMBER:
                IbmClientHmService clientHmService = new IbmClientHmService();
                String handicapMemberId = content.get("HANDICAP_MEMBER_ID_").toString();
                String existHmId = data.getString("EXIST_HM_ID_");
                clientHmService.save(clientId, handicapMemberId, existHmId, clientCode, content.getString("HANDICAP_CODE_"));
                break;
            case AGENT:
                IbmClientHaService clientHaService = new IbmClientHaService();
                String handicapAgentId = content.get("HANDICAP_AGENT_ID_").toString();
                String existHaId = data.getString("EXIST_HA_ID_");
                clientHaService.save(clientId, handicapAgentId, existHaId, clientCode, content.getString("HANDICAP_CODE_"));
                break;
            default:
                break;
        }
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
            eventMap = clientOpenService.findEventResult(eventId);
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
    /**
     * 发送开启客户端请求信息
     *
     * @param customerType 客户类型
     * @param content      消息内容
     * @param eventId      事件id
     * @param clientCode   客户端编码
     * @return 发送结果
     */
    private String sendOpenClientInfo(IbmTypeEnum customerType, JSONObject content, String clientCode, String eventId) throws Exception {
        //存入事件id
        content.put("EVENT_ID_", eventId);
        content.put("CUSTOMER_TYPE_", customerType.name());
        content.put("METHOD_", IbmMethodEnum.OPEN.name());
        switch (customerType) {
            case MEMBER:
                return RabbitMqTool.sendMemberInfo(content.toString(), clientCode, "manage");
            case AGENT:
                return RabbitMqTool.sendAgentInfo(content.toString(), clientCode, "manage");
            default:
                log.info("开启客户端失败，错误的客户类型：".concat(customerType.name()));
                return TypeEnum.FALSE.name();
        }
    }
}
