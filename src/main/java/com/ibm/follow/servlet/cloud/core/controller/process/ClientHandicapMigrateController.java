package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.ContainerTool;

import java.util.List;

/**
 * @Description: 客户端盘口迁移控制器
 * @Author: null
 * @Date: 2020-02-25 17:28
 * @Version: v1.0
 */
public class ClientHandicapMigrateController implements CloudExecutor {

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
        if (IbmMethodEnum.SEND.name().equals(method.name())||ContainerTool.isEmpty(result.get("resultInfos"))) {
            return new ClientInfoController().execute(msgObj, requestType);
        }

        //接收端处理结果信息
        IbmTypeEnum customerType = IbmTypeEnum.valueOf(result.getString("customerType"));

        JSONArray resultInfos=result.getJSONArray("resultInfos");
        switch (customerType) {
            case MEMBER:
                List<String> handicapMemberIds=new IbmClientHmService().save(resultInfos,clientId,clientCode);
                //重启事务
                CurrentTransaction.transactionCommit();
                //发送绑定信息
                ClientReplaceController.bindUserAgent(handicapMemberIds);
                break;
            case AGENT:
                List<String> handicapAgentIds=new IbmClientHaService().save(resultInfos,clientId, clientCode);
                //重启事务
                CurrentTransaction.transactionCommit();
                //发送绑定信息
                ClientReplaceController.bindUserMember(handicapAgentIds);
                break;
            default:
                break;
        }
        return new ClientInfoController().execute(msgObj, requestType);
    }
}
