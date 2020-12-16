package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.ibm_log_client.entity.IbmLogClient;
import com.ibm.follow.servlet.cloud.ibm_log_client.service.IbmLogClientService;

import java.util.Date;

/**
 * @Description: 客户端信息处理控制器
 * @Author: null
 * @Date: 2020-01-19 13:40
 * @Version: v1.0
 */
public class ClientInfoController implements CloudExecutor {

    @Override public JsonResultBeanPlus execute(JSONObject msgObj, IbmStateEnum requestType) throws Exception {

        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //接收到消息进行日志存储
        String clientCode=msgObj.getString("token");

        IbmLogClientService logClientService=new IbmLogClientService();
        IbmLogClient logClient=new IbmLogClient();
        logClient.setClientCode(clientCode);
        logClient.setMethod(msgObj.getString("command"));
        logClient.setResult(requestType.name());
        logClient.setCreateTime(new Date());
        logClient.setCreateTimeLong(System.currentTimeMillis());
        logClient.setUpdateTime(new Date());
        logClient.setUpdateTimeLong(System.currentTimeMillis());
        logClient.setState(IbmStateEnum.OPEN.name());
        logClientService.save(logClient);

        bean.success();
        return bean;

    }
}
