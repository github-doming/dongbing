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
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 *
 * @Description: 客户机开启心跳
 * @Author: null
 * @Date: 2020-03-03 17:24
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/startHeartbeat1", method = HttpConfig.Method.GET)
public class ClientStartHeartbeatAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
//        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }
        String clientCode = request.getParameter("clientCode");
        Map<String,Object> client=new IbmClientService().findInfo(clientCode);
        //不存在或者客户机状态不为close
        if (ContainerTool.isEmpty(client) ||!IbmStateEnum.CLOSE.name().equals(client.get("STATE_"))) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean.toJsonString();
        }

        //发消息到发送端
        JSONObject content = new JSONObject();
        content.put("METHOD_", IbmMethodEnum.HEARTBEAT.name());
        content.put("methodType", IbmMethodEnum.OPEN.name());
        String result = RabbitMqTool.sendClientConfig(content.toString(), clientCode, "info");
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
