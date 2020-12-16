package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import org.doming.core.tools.ContainerTool;

import java.util.List;

/**
 * @Description: 客户端删除控制器
 * @Author: null
 * @Date: 2020-04-13 11:08
 * @Version: v1.0
 */
public class ClientDelController implements CloudExecutor {

    @Override
    public JsonResultBeanPlus execute(JSONObject msgObj, IbmStateEnum requestType) throws Exception {
        //错误类型保存日志信息
        if (IbmStateEnum.FAIL.name().equals(requestType.name())) {
            return new ClientInfoController().execute(msgObj, requestType);
        }
        String clientCode = msgObj.getString("token");

        //清除会员信息
        IbmClientHmService clientHmService=new IbmClientHmService();
        List<String> handicapMemberIds= clientHmService.findHmIds(clientCode);
        if(ContainerTool.notEmpty(handicapMemberIds)){
            clientHmService.updateByHmIds(handicapMemberIds);
            new IbmHandicapMemberService().updateOperating(handicapMemberIds);
        }

        //清除代理信息
        IbmClientHaService clientHaService=new IbmClientHaService();
        List<String> handicapAgentIds=clientHaService.findHaIds(clientCode);
        if(ContainerTool.notEmpty(handicapAgentIds)){
            clientHaService.updateByHaIds(handicapAgentIds);
            new IbmHandicapAgentService().updateOperating(handicapAgentIds);
        }
        return new ClientInfoController().execute(msgObj, requestType);
    }
}
