package com.ibm.follow.connector.admin.manage3.sealTime;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_config.service.IbmConfigService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 发送所有封盘时间信息到所有客户机
 * @Author: null
 * @Date: 2020-01-18 18:30
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/sealTime/send")
public class SendSealTimeAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        IbmConfigService configService=new IbmConfigService();
        Map<Object,Object> sealTimeMap=configService.mapAllSealTime();

        //发送config.*.info消息
        JSONObject content=new JSONObject();
        content.put("sealTime",sealTimeMap);
        content.put("METHOD_", IbmMethodEnum.SEAL_TIME.name());
        String result= RabbitMqTool.sendClientConfig(content.toString(), "info");
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
