package com.ibm.follow.connector.admin.manage.client.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 新增盘口容量信息
 * @Author: null
 * @Date: 2020-04-11 16:17
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/handicap/save", method = HttpConfig.Method.POST)
public class HandicapCapacitySaveAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String clientCode = dataMap.getOrDefault("clientCode", "").toString();
        String handicapCode = dataMap.getOrDefault("handicapCode", "").toString();
        String capacityHandicapMax = dataMap.getOrDefault("capacityHandicapMax", "").toString();
        String capacityHaMax = dataMap.getOrDefault("capacityHaMax", "").toString();
        String capacityHmMax = dataMap.getOrDefault("capacityHmMax", "").toString();
        if (StringTool.isEmpty(clientCode, handicapCode, capacityHandicapMax, capacityHaMax, capacityHmMax)) {
            bean.putEnum(IbmCodeEnum.IBM_404_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_404);
            return bean.toJsonString();
        }
        try {
            IbmClientService clientService=new IbmClientService();
            Map<String, Object> clientInfo= clientService.findInfo(clientCode);
            if(ContainerTool.isEmpty(clientInfo)){
                bean.putEnum(IbmCodeEnum.IBM_401_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_401);
                return bean.toJsonString();
            }
            JSONObject content=new JSONObject();
            content.put("handicapCode",handicapCode);
            content.put("capacityHandicapMax",capacityHandicapMax);
            content.put("capacityHaMax",capacityHaMax);
            content.put("capacityHmMax",capacityHmMax);
            content.put("METHOD_", IbmMethodEnum.HANDICAP_CAPACITY_SET.name());
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
        } catch (Exception e) {
            log.error("盘口容量列表信息错误", e);
            return bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
