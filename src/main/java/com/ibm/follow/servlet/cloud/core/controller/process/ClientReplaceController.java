package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service.IbmHandicapAgentMemberService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.ContainerTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 客户机切换
 * @Author: null
 * @Date: 2020-02-29 15:45
 * @Version: v1.0
 */
public class ClientReplaceController implements CloudExecutor {

    @Override
    public JsonResultBeanPlus execute(JSONObject msgObj, IbmStateEnum requestType) throws Exception {
        //错误类型保存日志信息
        if (IbmStateEnum.FAIL.name().equals(requestType.name())) {
            return new ClientInfoController().execute(msgObj, requestType);
        }
        String clientCode = msgObj.getString("token");
        String clientId = new IbmClientService().findId(clientCode);

        JSONObject result = msgObj.getJSONObject("data");

        //发送端暂无需处理
        IbmMethodEnum method = IbmMethodEnum.valueOf(result.getString("methodType"));
        if (IbmMethodEnum.SEND.name().equals(method.name())) {
            return new ClientInfoController().execute(msgObj, requestType);
        }
        List<String> handicapMemberIds=new ArrayList<>();
        if(ContainerTool.notEmpty(result.get("hmResultInfos"))){
            handicapMemberIds=new IbmClientHmService().save(result.getJSONArray("hmResultInfos"),clientId,clientCode);
        }
        List<String> handicapAgentIds=new ArrayList<>();
        if(ContainerTool.notEmpty(result.get("haResultInfos"))){
            handicapAgentIds=new IbmClientHaService().save(result.getJSONArray("haResultInfos"),clientId, clientCode);
        }
        //重启事务
        CurrentTransaction.transactionCommit();

        //发送绑定信息
        bindUserMember(handicapAgentIds);

        bindUserAgent(handicapMemberIds);

        return new ClientInfoController().execute(msgObj, requestType);
    }

    /**
     * 发送绑定信息
     *
     * @param handicapMemberIds 盘口会员ids
     */
    public static void bindUserAgent(List<String> handicapMemberIds) throws Exception {
        IbmEventConfigSetService configSetService = new IbmEventConfigSetService();
        IbmClientHmService clientHmService = new IbmClientHmService();
        IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
        IbmHandicapAgentMemberService handicapAgentMemberService = new IbmHandicapAgentMemberService();

        JSONObject content = new JSONObject();
        content.put("SET_ITEM_", IbmMethodEnum.SET_BIND.name());
        content.put("METHOD_", IbmMethodEnum.MANAGE_SET.name());
        Map<String,Object> memberInfo;
        for (String handicapMemberId : handicapMemberIds) {
            memberInfo=handicapMemberService.findMemberAccountInfo(handicapMemberId);
            //获取客户端盘口会员信息
            Map<String, Object> bindInfo = clientHmService.findBindInfo(handicapMemberId);
            if(ContainerTool.isEmpty(bindInfo)){
                continue;
            }
            //投注模式
            List<Map<String, Object>> betModeInfos = new IbmHmGameSetService().listBetModeInfo(handicapMemberId);

            content.putAll(bindInfo);
            content.put("MEMBER_HANDICAP_CODE_", memberInfo.get("HANDICAP_CODE_"));
            content.put("MEMBER_ACCOUNT_", memberInfo.get("MEMBER_ACCOUNT_"));

            List<String> handicapAgentIds = handicapAgentMemberService.findHaIdByHmId(handicapMemberId);
            for (String handicapAgentId : handicapAgentIds) {
                LoginHmController.bindUserAgent(handicapAgentId,content,betModeInfos);
            }
        }

    }

    /**
     * 发送绑定信息
     *
     * @param handicapAgentIds 盘口代理ids
     */
    public static void bindUserMember(List<String> handicapAgentIds) throws Exception {
        IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
        IbmClientHmService clientHmService = new IbmClientHmService();
        IbmEventConfigSetService configSetService = new IbmEventConfigSetService();
        IbmHandicapAgentMemberService handicapAgentMemberService = new IbmHandicapAgentMemberService();

        IbmHaGameSetService haGameSetService=new IbmHaGameSetService();
        Map<String,Object> memberInfo;
        //绑定消息主体
        JSONObject content = new JSONObject();
        content.put("SET_ITEM_", IbmMethodEnum.SET_BIND.name());
        content.put("METHOD_", IbmMethodEnum.MANAGE_SET.name());
        for (String handicapAgentId : handicapAgentIds) {
            content.put("HANDICAP_AGENT_ID_", handicapAgentId);
            //获取代理拥有游戏和类型
            Map<String,Object> agentGameInfo=haGameSetService.findGameType(handicapAgentId);

            List<String> handicapMemberIds = handicapAgentMemberService.listHmId(handicapAgentId);
            for (String handicapMemberId : handicapMemberIds) {
                memberInfo=handicapMemberService.findMemberAccountInfo(handicapMemberId);

                LoginHaController.bindUserMember(memberInfo,agentGameInfo,content,configSetService,clientHmService);
            }
        }
    }
}
