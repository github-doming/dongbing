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
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 盘口容量设置
 * @Author: null
 * @Date: 2020-03-24 16:51
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/handicap/edit",method = HttpConfig.Method.POST)
public class HandicapCapacityEditAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String clientCode = dataMap.getOrDefault("clientCode","").toString();
        String handicapCode = dataMap.getOrDefault("handicapCode","").toString();
        String capacityHandicapMax = dataMap.getOrDefault("capacityHandicapMax","").toString();
        String capacityHaMax = dataMap.getOrDefault("capacityHaMax","").toString();
        String capacityHmMax = dataMap.getOrDefault("capacityHmMax","").toString();

        try{
            Map<String, Object> capacityMap=new IbmClientHandicapCapacityService().findHandicapCapacityInfo(clientCode,handicapCode);
            if(ContainerTool.isEmpty(capacityMap)){
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean.toJsonString();
            }
            Map<String, Object> clientInfo= new IbmClientService().findInfo(clientCode);
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
            log.error("盘口容量设置错误", e);
            return bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
