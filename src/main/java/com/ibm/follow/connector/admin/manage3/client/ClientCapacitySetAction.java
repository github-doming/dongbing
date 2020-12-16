package com.ibm.follow.connector.admin.manage3.client;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 *
 * @Description: 客户端容量设置
 * @Author: null
 * @Date: 2020-01-16 15:39
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/clientCapacitySet1",method = HttpConfig.Method.POST)
public class ClientCapacitySetAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
//        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }
        String clientCode=request.getParameter("clientCode");
        String clientCapacityMax=request.getParameter("clientCapacityMax");


        IbmClientService clientService=new IbmClientService();
        if(!IbmStateEnum.OPEN.name().equals(clientService.findState(clientCode))){
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean.toJsonString();
        }
        //直接发送消息，所有客户端设置信息不需要通过事件来处理
        JSONObject content=new JSONObject();
        content.put("clientCapacityMax",clientCapacityMax);
        content.put("METHOD_",IbmMethodEnum.CAPACITY_SET.name());
        String result= RabbitMqTool.sendClientConfig(content.toString(), clientCode, "info");
        if (StringTool.isEmpty(result)) {
            bean.putEnum(IbmCodeEnum.IBM_401_MESSAGE);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean.toJsonString();
        }
        if (!Boolean.parseBoolean(result)) {
            bean.putEnum(IbmCodeEnum.IBM_403_MQ);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean.toJsonString();
        }

        bean.success();
        return bean.toJsonString();
    }
}
